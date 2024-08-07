package core.game.container.impl;

import core.ServerConstants;
import core.api.IfaceSettingsBuilder;
import core.api.consts.Vars;
import core.game.component.Component;
import core.game.container.*;
import core.game.container.access.InterfaceContainer;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.IronmanMode;
import core.game.node.item.Item;
import core.game.system.config.ItemConfigParser;
import core.game.world.GameWorld;
import core.network.packet.PacketRepository;
import core.network.packet.context.ContainerContext;
import core.network.packet.outgoing.ContainerPacket;
import kotlin.ranges.IntRange;

import java.nio.ByteBuffer;

import static core.api.ContentAPIKt.*;

/**
 * Bank container.
 */
public final class BankContainer extends Container {

    /**
     * The constant SIZE.
     */
    public static final int SIZE = ServerConstants.BANK_SIZE;

    /**
     * The constant TAB_SIZE.
     */
    public static final int TAB_SIZE = 11;

    private Player player;

    private final BankListener listener;

    private boolean open;

    private int lastAmountX = 50;

    private int tabIndex = 10;

    private final int[] tabStartSlot = new int[TAB_SIZE];

    /**
     * Instantiates a new Bank container.
     *
     * @param player the player
     */
    public BankContainer(Player player) {
        super(SIZE, ContainerType.ALWAYS_STACK, SortType.HASH);
        super.register(listener = new BankListener(player));
        this.player = player;
    }

    /**
     * Open deposit box.
     */
    public void openDepositBox() {
        player.getInterfaceManager().open(new Component(11)).setCloseEvent((player, c) -> {
            player.getInterfaceManager().openDefaultTabs();
            return true;
        });
        player.getInterfaceManager().removeTabs(0, 1, 2, 3, 4, 5, 6);
        refreshDepositBoxInterface();
    }

    /**
     * Refresh deposit box interface.
     */
    public void refreshDepositBoxInterface() {
        InterfaceContainer.generateItems(
            player,
            player.getInventory().toArray(),
            new String[]{
                "Examine",
                "Deposit-X",
                "Deposit-All",
                "Deposit-10",
                "Deposit-5",
                "Deposit-1"
            }, 11, 15, 5, 7
        );
    }

    /**
     * Open.
     */
    public void open() {
        if (open) {
            return;
        }
        if (player.getIronmanManager().checkRestriction(IronmanMode.ULTIMATE)) {
            return;
        }
        if (!player.getBankPinManager().isUnlocked() && !GameWorld.getSettings().isDevMode()) {
            player.getBankPinManager().openType(1);
            return;
        }
        player.getInterfaceManager().openComponent(762).setCloseEvent((player, c) -> {
            BankContainer.this.close();
            return true;
        });
        player.getInterfaceManager().openSingleTab(new Component(763));
        super.refresh();
        player.getInventory().getListeners().add(listener);
        player.getInventory().refresh();
        setVarp(player, 1249, lastAmountX);
        int settings = new IfaceSettingsBuilder().enableOptions(new IntRange(0, 5)).enableExamine().enableSlotSwitch().build();
        player.getPacketDispatch().sendIfaceSettings(settings, 0, 763, 0, 27);
        open = true;
    }

    /**
     * Open.
     *
     * @param player the player
     */
    public void open(Player player) {
        if (open) {
            return;
        }
        if (player.getIronmanManager().checkRestriction(IronmanMode.ULTIMATE)) {
            return;
        }
        if (!player.getBankPinManager().isUnlocked() && !GameWorld.getSettings().isDevMode()) {
            player.getBankPinManager().openType(1);
            return;
        }
        player.getInterfaceManager().openComponent(762).setCloseEvent((player1, c) -> {
            BankContainer.this.close();
            return true;
        });
        refresh(listener);
        player.getInterfaceManager().openSingleTab(new Component(763));
        player.getInventory().getListeners().add(player.getBank().listener);
        player.getInventory().refresh();
        setVarp(player, 1249, lastAmountX);
        player.getPacketDispatch().sendIfaceSettings(1278, 73, 762, 0, SIZE);
        int settings = new IfaceSettingsBuilder().enableOptions(new IntRange(0, 5)).enableExamine().enableSlotSwitch().build();
        player.getPacketDispatch().sendIfaceSettings(settings, 0, 763, 0, 27);
        player.getPacketDispatch().sendRunScript(1451, "");
        open = true;

    }

    @Override
    public long save(ByteBuffer buffer) {
        buffer.putInt(lastAmountX);
        buffer.put((byte) tabStartSlot.length);
        for (int j : tabStartSlot) {
            buffer.putShort((short) j);
        }
        return super.save(buffer);
    }

    @Override
    public int parse(ByteBuffer buffer) {
        lastAmountX = buffer.getInt();
        int length = buffer.get() & 0xFF;
        for (int i = 0; i < length; i++) {
            tabStartSlot[i] = buffer.getShort();
        }
        return super.parse(buffer);
    }

    /**
     * Close.
     */
    public void close() {
        open = false;
        player.getInventory().getListeners().remove(listener);
        player.getInterfaceManager().closeSingleTab();
        player.removeAttribute("search");
        player.getPacketDispatch().sendRunScript(571, "");
    }

    /**
     * Add item.
     *
     * @param slot   the slot
     * @param amount the amount
     */
    public void addItem(int slot, int amount) {
        if (slot < 0 || slot > player.getInventory().capacity() || amount < 1) {
            return;
        }
        Item item = player.getInventory().get(slot);
        if (item == null) {
            return;
        }

        if (!item.getDefinition().getConfiguration(ItemConfigParser.BANKABLE, true)) {
            player.sendMessage("A magical force prevents you from banking this item");
            return;
        }

        int maximum = player.getInventory().getAmount(item);
        if (amount > maximum) {
            amount = maximum;
        }

        item = new Item(item.getId(), amount, item.getCharge());
        boolean unnote = !item.getDefinition().isUnnoted();

        Item add = unnote ? new Item(item.getDefinition().getNoteId(), amount, item.getCharge()) : item;
        if (unnote && !add.getDefinition().isUnnoted()) {
            add = item;
        }

        int maxCount = super.getMaximumAdd(add);
        if (amount > maxCount) {
            add.setAmount(maxCount);
            item.setAmount(maxCount);
            if (maxCount < 1) {
                player.getPacketDispatch().sendMessage("There is not enough space left in your bank.");
                return;
            }
        }

        if (player.getInventory().remove(item, slot, true)) {
            int preferredSlot = -1;
            if (tabIndex != 0 && tabIndex != 10 && !super.contains(add.getId(), 1)) {
                preferredSlot = tabStartSlot[tabIndex] + getItemsInTab(tabIndex);
                insert(freeSlot(), preferredSlot, false);
                increaseTabStartSlots(tabIndex);
            }
            super.add(add, true, preferredSlot);
        }
    }

    /**
     * Take item.
     *
     * @param slot   the slot
     * @param amount the amount
     */
    public void takeItem(int slot, int amount) {
        if (slot < 0 || slot > super.capacity() || amount <= 0) {
            return;
        }
        Item item = get(slot);
        if (item == null) {
            return;
        }
        if (amount > item.getAmount()) {
            amount = item.getAmount(); // It always stacks in the bank.
        }
        item = new Item(item.getId(), amount, item.getCharge());
        int noteId = item.getDefinition().getNoteId();
        Item add = isNoteItems() && noteId > 0 ? new Item(noteId, amount, item.getCharge()) : item;
        int maxCount = player.getInventory().getMaximumAdd(add);
        if (amount > maxCount) {
            item.setAmount(maxCount);
            add.setAmount(maxCount);
            if (maxCount < 1) {
                player.getPacketDispatch().sendMessage("Not enough space in your inventory.");
                return;
            }
        }
        if (isNoteItems() && noteId < 0) {
            player.getPacketDispatch().sendMessage("This item can't be withdrawn as a note.");
            add = item;
        }
        if (super.remove(item, slot, false)) {
            player.getInventory().add(add);
        }
        if (get(slot) == null) {
            int tabId = getTabByItemSlot(slot);
            decreaseTabStartSlots(tabId);
            shift();
        } else update();
    }

    /**
     * Update last amount x.
     *
     * @param amount the amount
     */
    public void updateLastAmountX(int amount) {
        this.lastAmountX = amount;
        setVarp(player, 1249, amount);
    }

    /**
     * Gets tab by item slot.
     *
     * @param itemSlot the item slot
     * @return the tab by item slot
     */
    public int getTabByItemSlot(int itemSlot) {
        int tabId = 0;
        for (int i = 0; i < tabStartSlot.length; i++) {
            if (itemSlot >= tabStartSlot[i]) {
                tabId = i;
            }
        }
        return tabId;
    }

    /**
     * Increase tab start slots.
     *
     * @param startId the start id
     */
    public void increaseTabStartSlots(int startId) {
        for (int i = startId + 1; i < tabStartSlot.length; i++) {
            tabStartSlot[i]++;
        }
    }

    /**
     * Decrease tab start slots.
     *
     * @param startId the start id
     */
    public void decreaseTabStartSlots(int startId) {
        if (startId == 10) {
            return;
        }
        for (int i = startId + 1; i < tabStartSlot.length; i++) {
            tabStartSlot[i]--;
        }
        if (getItemsInTab(startId) == 0) {
            collapseTab(startId);
        }
    }

    /**
     * Gets array index.
     *
     * @param tabId the tab id
     * @return the array index
     */
    public static int getArrayIndex(int tabId) {
        if (tabId == 41 || tabId == 74) {
            return 10;
        }
        int base = 39;
        for (int i = 1; i < 10; i++) {
            if (tabId == base) {
                return i;
            }
            base -= 2;
        }
        return -1;
    }

    /**
     * Send bank space.
     */
    public void sendBankSpace() {
        setVarc(player, 192, capacity() - freeSlots());
    }

    /**
     * Collapse tab.
     *
     * @param tabId the tab id
     */
    public void collapseTab(int tabId) {
        int size = getItemsInTab(tabId);
        Item[] tempTabItems = new Item[size];
        for (int i = 0; i < size; i++) {
            tempTabItems[i] = get(tabStartSlot[tabId] + i);
            replace(null, tabStartSlot[tabId] + i, false);
        }
        shift();
        for (int i = tabId; i < tabStartSlot.length - 1; i++) {
            tabStartSlot[i] = tabStartSlot[i + 1] - size;
        }
        tabStartSlot[10] = tabStartSlot[10] - size;
        for (int i = 0; i < size; i++) {
            int slot = freeSlot();
            replace(tempTabItems[i], slot, false);
        }
        refresh(); //We only refresh once.
    }

    /**
     * Sets tab configurations.
     */
    public void setTabConfigurations() {
        for (int i = 0; i < 8; i++) {
            setVarbit(player, 4885 + i, getItemsInTab(i + 1));
        }
    }

    /**
     * Gets items in tab.
     *
     * @param tabId the tab id
     * @return the items in tab
     */
    public int getItemsInTab(int tabId) {
        return tabStartSlot[tabId + 1] - tabStartSlot[tabId];
    }

    /**
     * Can add boolean.
     *
     * @param item the item
     * @return the boolean
     */
    public boolean canAdd(Item item) {
        return item.getDefinition().getConfiguration(ItemConfigParser.BANKABLE, true);
    }

    /**
     * Gets last amount x.
     *
     * @return the last amount x
     */
    public int getLastAmountX() {
        return lastAmountX;
    }

    /**
     * Is note items boolean.
     *
     * @return the boolean
     */
    public boolean isNoteItems() {
        return getVarbit(player, Vars.VARBIT_IFACE_BANK_NOTE_MODE) == 1;
    }

    /**
     * Sets note items.
     *
     * @param noteItems the note items
     */
    public void setNoteItems(boolean noteItems) {
        setVarbit(player, Vars.VARBIT_IFACE_BANK_NOTE_MODE, noteItems ? 1 : 0, true);
    }

    /**
     * Get tab start slot int [ ].
     *
     * @return the int [ ]
     */
    public int[] getTabStartSlot() {
        return tabStartSlot;
    }

    /**
     * Gets tab index.
     *
     * @return the tab index
     */
    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * Sets tab index.
     *
     * @param tabIndex the tab index
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex == 0 ? 10 : tabIndex;
        setVarbit(player, 4893, tabIndex + 1);
        setAttribute(player, "bank:lasttab", tabIndex);
    }

    /**
     * Sets insert items.
     *
     * @param insertItems the insert items
     */
    public void setInsertItems(boolean insertItems) {
        setVarbit(player, Vars.VARBIT_IFACE_BANK_INSERT_MODE, insertItems ? 1 : 0, true);
    }

    /**
     * Is insert items boolean.
     *
     * @return the boolean
     */
    public boolean isInsertItems() {
        return getVarbit(player, Vars.VARBIT_IFACE_BANK_INSERT_MODE) == 1;
    }

    /**
     * Is open boolean.
     *
     * @return the boolean
     */
    public boolean isOpen() {
        return open;
    }

    private static class BankListener implements ContainerListener {

        private Player player;

        /**
         * Instantiates a new Bank listener.
         *
         * @param player the player
         */
        public BankListener(Player player) {
            this.player = player;
        }

        @Override
        public void update(Container c, ContainerEvent event) {
            if (c instanceof BankContainer) {
                PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 762, 64000, 95, event.getItems(), false, event.getSlots()));
            } else {
                PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 763, 64000, 93, event.getItems(), false, event.getSlots()));
            }
            player.getBank().setTabConfigurations();
            player.getBank().sendBankSpace();
        }

        @Override
        public void refresh(Container c) {
            if (c instanceof BankContainer) {
                PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 762, 64000, 95, c.toArray(), c.capacity(), false));
            } else {
                PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 763, 64000, 93, c.toArray(), 28, false));
            }
            player.getBank().setTabConfigurations();
            player.getBank().sendBankSpace();
        }
    }
}
