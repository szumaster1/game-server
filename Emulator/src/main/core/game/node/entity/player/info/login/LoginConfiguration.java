package core.game.node.entity.player.info.login;

import content.miniquest.knightwave.handlers.KnightWaves;
import core.Configuration;
import core.game.component.Component;
import core.game.interaction.InteractionListeners;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.SpellBookManager;
import core.game.node.entity.player.link.emote.Emotes;
import core.game.node.item.Item;
import core.game.world.GameWorld;
import core.game.world.map.RegionManager;
import core.game.world.repository.Repository;
import core.game.world.update.UpdateSequence;
import core.network.packet.PacketRepository;
import core.network.packet.context.InterfaceContext;
import core.network.packet.outgoing.Interface;
import core.plugin.Plugin;
import core.tools.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static core.api.ContentAPIKt.*;
import static core.tools.GlobalsKt.colorize;

/**
 * Login configuration.
 */
public final class LoginConfiguration {

    private static final List<Plugin<Object>> LOGIN_PLUGINS = new ArrayList<>(20);

    private static final Component LOBBY_PANE = new Component(549);

    private static final Component LOBBY_INTERFACE = new Component(378);

    private static final int[] MESSAGE_MODEL = {15, 16, 17, 18, 19, 20, 21, 22, 23, 405, 447, 622, 623, 679, 715, 800};
    private static int messModel;

    /**
     * Instantiates a new Login configuration.
     */
    public LoginConfiguration() {
        /*
         * empty.
         */
    }

    /**
     * Configure lobby.
     *
     * @param player the player
     */
    public static void configureLobby(Player player) {
        player.updateSceneGraph(true);
        if (!player.isArtificial() && player.getAttribute("tutorial:complete", false) && player.getAttribute("login_type", LoginType.NORMAL_LOGIN) != LoginType.RECONNECT_TYPE) {
            sendLobbyScreen(player);
        } else {
            configureGameWorld(player);
        }
    }

    /**
     * Send lobby screen.
     *
     * @param player the player
     */
    public static void sendLobbyScreen(Player player) {
        messModel = autoSelect();
        for (Player p : Repository.getLobbyPlayers()) {
            if (p.getName().equals(player.getName())) {
                p.clear();
                Repository.getLobbyPlayers().remove(p);
                break;
            }
        }
        Repository.getLobbyPlayers().add(player);
        setInterfaceText(player, "Welcome to " + Configuration.SERVER_NAME, 378, 115);
        setInterfaceText(player, getLastLogin(player), 378, 116);
        setInterfaceText(player, "", 378, 37);
        setInterfaceText(player, "Want to stay up to date with the latest version? Check the 'github.com/szumaster3/game' repository", 378, 38);
        setInterfaceText(player, "", 378, 39);
        setInterfaceText(player, "Discord Invite", 378, 14);
        setInterfaceText(player, "Discord Invite", 378, 129);
        setInterfaceText(player, "You can gain credits by reporting bugs and various other methods of contribution.", 378, 93);
        setInterfaceText(player, player.getDetails().getCredits() + "", 378, 96);
        setInterfaceText(player, "Credits", 378, 94);
        setInterfaceText(player, "", 378, 229);
        setInterfaceText(player, "Want to contribute to " + Configuration.SERVER_NAME + "? Visit the GitLab using the link on our website!", 378, 230);
        setInterfaceText(player, "", 378, 231);
        setInterfaceText(player, "Github", 378, 240);
        setInterfaceText(player, GameWorld.getSettings().getMessage_string(), messModel, getMessageChild(messModel));
        player.getInterfaceManager().openWindowsPane(LOBBY_PANE);
        player.getInterfaceManager().setOpened(LOBBY_INTERFACE);
        PacketRepository.send(Interface.class, new InterfaceContext(player, LOBBY_PANE.getId(), 2, 378, true));
        PacketRepository.send(Interface.class, new InterfaceContext(player, LOBBY_PANE.getId(), 3, messModel, true));//UPDATE `configs` SET `value`=FLOOR(RAND()*(25-10)+10) WHERE key_="messageInterface"
        player.getDetails().setLastLogin(System.currentTimeMillis());
    }

    /**
     * Configure game world.
     *
     * @param player the player
     */
    public static void configureGameWorld(final Player player) {
        sendGameConfiguration(player);
        Repository.getLobbyPlayers().remove(player);
        player.setPlaying(true);
        UpdateSequence.getRenderablePlayers().add(player);
        RegionManager.move(player);
        player.getMusicPlayer().init();
        player.updateAppearance();
        player.getPlayerFlags().setUpdateSceneGraph(true);
        player.getPacketDispatch().sendInterfaceConfig(226, 1, true);

        if (player.getGlobalData().getTestStage() == 3 && !player.getEmoteManager().isUnlocked(Emotes.SAFETY_FIRST)) {
            player.getEmoteManager().unlock(Emotes.SAFETY_FIRST);
        }

        for (Item item : player.getEquipment().toArray()) {
            //Run equip hooks for all items equipped on login.
            //We should have already been doing this.
            //Frankly, I don't even want to imagine the number of bugs us *not* doing this has caused.
            if (item == null) continue;
            player.getEquipment().remove(item);
            if (!InteractionListeners.run(item.getId(), player, item, true) || !player.getEquipment().add(item, true, false)) {
                player.sendMessage(colorize("%RAs you can no longer wear " + item.getName() + ", it has been unequipped."));
                addItemOrBank(player, item.getId(), item.getAmount());
            }
        }

        SpellBookManager.SpellBook currentSpellBook = SpellBookManager.SpellBook.forInterface(player.getSpellBookManager().getSpellBook());
        if (currentSpellBook == SpellBookManager.SpellBook.ANCIENT && !hasRequirement(player, "Desert Treasure")) {
            player.sendMessage(colorize("%RAs you can no longer use Ancient Magic, you have been set back to Modern."));
            player.getSpellBookManager().setSpellBook(SpellBookManager.SpellBook.MODERN);
        } else if (currentSpellBook == SpellBookManager.SpellBook.LUNAR && !hasRequirement(player, "Lunar Diplomacy")) {
            player.sendMessage(colorize("%RAs you can no longer use Lunar Magic, you have been set back to Modern."));
            player.getSpellBookManager().setSpellBook(SpellBookManager.SpellBook.MODERN);
        }
        player.getSpellBookManager().update(player);

        /*

         */
        if(getAttribute(player, KnightWaves.KW_COMPLETE, false)) {
            setVarbit(player, 3909, 8, false);
        }

        /*
         * if (TutorialSession.getExtension(player).getStage() != 73) {
         *     TutorialStage.load(player, TutorialSession.getExtension(player).getStage(), true);
         * }
         */
    }

    /**
     * Send game configuration.
     *
     * @param player the player
     */
    public static void sendGameConfiguration(final Player player) {
        player.getInterfaceManager().openWindowsPane(new Component(player.getInterfaceManager().isResizable() ? 746 : 548));
        player.getInterfaceManager().openChatbox(137);
        player.getInterfaceManager().openDefaultTabs();
        welcome(player);
        config(player);
        for (Plugin<Object> plugin : LOGIN_PLUGINS) {
            try {
                plugin.newInstance(player);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        player.getAppearance().sync();
    }

    /**
     * Welcome.
     *
     * @param player the player
     */
    public static final void welcome(final Player player) {
        if (player.isArtificial()) {
            return;
        }
        player.getPacketDispatch().sendMessage("Welcome to " + Configuration.SERVER_NAME + ".");
        if (player.getDetails().isMuted()) {
            player.getPacketDispatch().sendMessage("You are muted.");
            player.getPacketDispatch().sendMessage("To prevent further mutes please read the rules.");
        }
    }

    /**
     * Config.
     *
     * @param player the player
     */
    public static final void config(final Player player) {
        if (!player.isArtificial())
            log(LoginConfiguration.class, Log.INFO, "configuring player " + player.getUsername());
        player.getInventory().refresh();
        player.getEquipment().refresh();
        player.getSkills().refresh();
        player.getSkills().configure();
        player.getSettings().update();
        player.getInteraction().setDefault();
        player.getPacketDispatch().sendRunEnergy();
        player.getFamiliarManager().login();
        player.getInterfaceManager().openDefaultTabs();
        setInterfaceText(player, "Friends List - " + Configuration.SERVER_NAME + " " + GameWorld.getSettings().getWorldId(), 550, 3);
        setInterfaceText(player, "When you have finished playing " + Configuration.SERVER_NAME + ", always use the button below to logout safely.", 182, 0);
        player.getQuestRepository().syncronizeTab(player);
        player.getInterfaceManager().close();
        player.getEmoteManager().refresh();
        player.getInterfaceManager().openInfoBars();
        if (!player.isArtificial())
            log(LoginConfiguration.class, Log.INFO, "finished configuring player " + player.getUsername());
    }

    /**
     * Gets message child.
     *
     * @param interfaceId the interface id
     * @return the message child
     */
    public static int getMessageChild(int interfaceId) {
        if (interfaceId == 622) {
            return 8;
        } else if (interfaceId == 16) {
            return 6;
        } else if (interfaceId == 20 || interfaceId == 623) {
            return 5;
        } else if (interfaceId == 15 || interfaceId == 18 || interfaceId == 19 || interfaceId == 21 || interfaceId == 22 || interfaceId == 447 || interfaceId == 405) {
            return 4;
        } else if (interfaceId == 17 || interfaceId == 23 || interfaceId == 800) {
            return 3;
        } else if (interfaceId == 715) {
            return 2;
        } else if (interfaceId == 679) {
            return 1;
        }
        return 0;
    }

    private final static int autoSelect() {
        boolean contains = IntStream.of(MESSAGE_MODEL).anyMatch(x -> x == GameWorld.getSettings().getMessage_model());
        return contains ? GameWorld.getSettings().getMessage_model() : MESSAGE_MODEL[new Random().nextInt(MESSAGE_MODEL.length)];
    }

    /**
     * Gets last login.
     *
     * @param player the player
     * @return the last login
     */
    public static String getLastLogin(Player player) {
        String lastIp = player.getDetails().accountInfo.getLastUsedIp();
        if (lastIp.equals("")) {
            lastIp = player.getDetails().getIpAddress();
        }
        player.getDetails().accountInfo.setLastUsedIp(player.getDetails().getIpAddress());
        String string = "You last logged in @timeAgo from: " + lastIp;
        long time = player.getDetails().getLastLogin();
        Date lastLogin = new Date(time);
        Date current = new Date();
        long diff = current.getTime() - lastLogin.getTime();
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if (days < 1) {
            string = string.replace("@timeAgo", "earlier today");
        } else if (days == 1) {
            string = string.replace("@timeAgo", "yesterday");
        } else {
            string = string.replace("@timeAgo", days + " days ago");
        }
        return string;
    }


    /**
     * Gets login plugins.
     *
     * @return the login plugins
     */
    public static List<Plugin<Object>> getLoginPlugins() {
        return LOGIN_PLUGINS;
    }

}
