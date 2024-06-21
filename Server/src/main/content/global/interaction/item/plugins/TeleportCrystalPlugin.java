package content.global.interaction.item.plugins;

import core.cache.def.impl.ItemDefinition;
import core.game.dialogue.Dialogue;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.TeleportManager.TeleportType;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.game.world.map.zone.impl.WildernessZone;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.hasRequirement;

/**
 * The Teleport crystal plugin.
 */
@Initializable
public final class TeleportCrystalPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ItemDefinition.forId(6099).getHandlers().put("option:activate", this);
        ItemDefinition.forId(6100).getHandlers().put("option:activate", this);
        ItemDefinition.forId(6101).getHandlers().put("option:activate", this);
        ItemDefinition.forId(6102).getHandlers().put("option:activate", this);
        new TeleportCrystalDialogue().init();
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        if (!hasRequirement(player, "Mourning's End Part I")) return true;
        if (!WildernessZone.checkTeleport(player, 20)) {
            player.getPacketDispatch().sendMessage("The crystal is unresponsive.");
            return true;
        }
        player.getDialogueInterpreter().open(TeleportCrystalDialogue.ID, 1, node.asItem().getId());
        //degrade(player, node.asItem());
        return true;
    }


    @Override
    public boolean isWalk() {
        return false;
    }


    /**
     * The Teleport crystal dialogue.
     */
    public static final class TeleportCrystalDialogue extends Dialogue {


        /**
         * The constant ID.
         */
        public static final int ID = 3999111;
        private Integer itemId;


        /**
         * Instantiates a new Teleport crystal dialogue.
         */
        public TeleportCrystalDialogue() {

        }


        /**
         * Instantiates a new Teleport crystal dialogue.
         *
         * @param player the player
         */
        public TeleportCrystalDialogue(Player player) {
            super(player);
        }

        @Override
        public Dialogue newInstance(Player player) {
            return new TeleportCrystalDialogue(player);
        }

        @Override
        public boolean open(Object... args) {
            itemId = (Integer) args[1];
            switch ((Integer) args[0]) {
                case 1:
                    interpreter.sendOptions("Select an Option", "Teleport to Lletya", "Cancel");
                    stage = 100;
                    break;
            }
            return true;
        }

        @Override
        public boolean handle(int interfaceId, int buttonId) {
            NPC npc = new NPC(2376);
            NPC billTeach = new NPC(3155);
            switch (stage) {
                case 100:
                    switch (buttonId) {
                        case 1:
                            player.getTeleporter().send(new Location(2329, 3172), TeleportType.NORMAL);
                            degrade(player, new Item(itemId));
                            break;
                        case 2:
                            end();
                            break;
                    }
                    break;
            }
            return true;
        }


        private static void degrade(Player p, Item item) {
            int id = item.getId();
            int newItem = item.getId() + 1;
            if (id < 6102) {
                p.getInventory().remove(new Item(id, 1));
                p.getInventory().add(new Item(newItem, 1));
                p.getPacketDispatch().sendMessage("Your teleportation crystal has degraded from use.");
            } else {
                p.getInventory().remove(new Item(id, 1));
                p.getInventory().add(new Item(newItem, 1));
                p.getPacketDispatch().sendMessages("Your teleportation crystal has degraded to a tiny elf crystal,", "Eluned can re-enchant it.");
            }
        }

        @Override
        public int[] getIds() {
            return new int[]{ID};
        }

    }

}