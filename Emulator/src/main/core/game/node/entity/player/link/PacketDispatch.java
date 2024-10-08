package core.game.node.entity.player.link;

import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.EntityFlag;
import core.game.world.update.flag.chunk.AnimateSceneryUpdateFlag;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.net.packet.PacketRepository;
import core.net.packet.context.*;
import core.net.packet.context.DisplayModelContext.ModelType;
import core.net.packet.outgoing.*;
import core.tools.Log;

import static core.api.ContentAPIKt.log;
import static core.api.ContentAPIKt.setVarp;

/**
 * Represents the class used to dispatching packets.
 * @author Emperor, Vexia
 */
public final class PacketDispatch {

    /**
     * The instance of the {@code Player}.
     */
    private final Player player;

    /**
     * The player context.
     */
    private final PlayerContext context;

    /**
     * Constructs a new Packet dispatch.
     *
     * @param player the player
     */
    public PacketDispatch(Player player) {
        this.player = player;
        this.context = new PlayerContext(player);
    }

    /**
     * Send varp.
     *
     * @param index the index
     * @param value the value
     */
    public void sendVarp(int index, int value) {
        PacketRepository.send(Config.class, new ConfigContext(player, index, value));
    }

    /**
     * Send varc update.
     *
     * @param index the index
     * @param value the value
     */
    public void sendVarcUpdate(short index, int value) {
        PacketRepository.send(VarcUpdate.class, new VarcUpdateContext(player, index, value));
    }

    /**
     * Send a game message.
     *
     * @param message The game message.
     */
    public void sendMessage(String message) {
        if (message == null) {
            return;
        }
        if (player.getAttribute("chat_filter") != null && !message.contains("<col=CC6600>") && !message.contains("<col=FFFF00>")) {
            return;
        }
        if (message.length() > 255) {
            log(this.getClass(), Log.ERR, "Message length out of bounds (" + message + ")!");
            message = message.substring(0, 255);
        }
        PacketRepository.send(GameMessage.class, new GameMessageContext(player, message));
    }

    /**
     * Send messages.
     *
     * @param messages the messages
     */
    public void sendMessages(final String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    /**
     * Send message.
     *
     * @param message the message
     * @param ticks   the ticks
     */
    public void sendMessage(final String message, int ticks) {
        GameWorld.getPulser().submit(new Pulse(ticks, player) {
            @Override
            public boolean pulse() {
                sendMessage(message);
                return true;
            }
        });
    }

    /**
     * Send iface settings.
     *
     * @param settingsHash the settings hash
     * @param childId      the child id
     * @param interfaceId  the interface id
     * @param offset       the offset
     * @param length       the length
     */
    public void sendIfaceSettings(int settingsHash, int childId, int interfaceId, int offset, int length) {
        PacketRepository.send(AccessMask.class, new AccessMaskContext(player, settingsHash, childId, interfaceId, offset, length));
    }

    /**
     * Send windows pane.
     *
     * @param windowId the window id
     * @param type     the type
     */
    public void sendWindowsPane(int windowId, int type) {
        PacketRepository.send(WindowsPane.class, new WindowsPaneContext(player, windowId, type));
    }

    /**
     * Send system update.
     *
     * @param time the time
     */
    public void sendSystemUpdate(int time) {
        PacketRepository.send(SystemUpdatePacket.class, new SystemUpdateContext(player, time));
    }

    /**
     * Send music.
     *
     * @param musicId the music id
     */
    public void sendMusic(int musicId) {
        PacketRepository.send(MusicPacket.class, new MusicContext(player, musicId));
    }

    /**
     * Send temp music.
     *
     * @param musicId the music id
     */
    public void sendTempMusic(int musicId) {
        PacketRepository.send(MusicPacket.class, new MusicContext(player, musicId, true));
    }

    /**
     * Send script config.
     *
     * @param id         the id
     * @param value      the value
     * @param types      the types
     * @param parameters the parameters
     */
    public void sendScriptConfig(int id, int value, String types, Object... parameters) {
        PacketRepository.send(CSConfig.class, new CSConfigContext(player, id, value, types, parameters));
    }

    /**
     * Send run script.
     *
     * @param id      the id
     * @param string  the string
     * @param objects the objects
     */
    public void sendRunScript(int id, String string, Object... objects) {
        PacketRepository.send(RunScriptPacket.class, new RunScriptContext(player, id, string, objects));
    }

    /**
     * Send string.
     *
     * @param string      the string
     * @param interfaceId the interface id
     * @param lineId      the line id
     */
    public void sendString(String string, int interfaceId, int lineId) {
        PacketRepository.send(StringPacket.class, new StringContext(player, string, interfaceId, lineId));
    }

    /**
     * Send run energy.
     */
    public void sendRunEnergy() {
        PacketRepository.send(RunEnergy.class, getContext());
    }

    /**
     * Send logout.
     */
    public void sendLogout() {
        PacketRepository.send(LogoutPacket.class, getContext());
    }

    /**
     * Send animation interface.
     *
     * @param animationId the animation id
     * @param interfaceId the interface id
     * @param childId     the child id
     */
    public void sendAnimationInterface(int animationId, int interfaceId, int childId) {
        PacketRepository.send(AnimateInterface.class, new AnimateInterfaceContext(player, animationId, interfaceId, childId));
    }

    /**
     * Send player on interface.
     *
     * @param interfaceId the interface id
     * @param childId     the child id
     */
    public void sendPlayerOnInterface(int interfaceId, int childId) {
        PacketRepository.send(DisplayModel.class, new DisplayModelContext(player, interfaceId, childId));
    }

    /**
     * Send npc on interface.
     *
     * @param npcId       the npc id
     * @param interfaceId the interface id
     * @param childId     the child id
     */
    public void sendNpcOnInterface(int npcId, int interfaceId, int childId) {
        PacketRepository.send(DisplayModel.class, new DisplayModelContext(player, npcId, interfaceId, childId));
    }

    /**
     * Send model on interface.
     *
     * @param modelID     the model id
     * @param interfaceId the interface id
     * @param childId     the child id
     * @param zoom        the zoom
     */
    public void sendModelOnInterface(int modelID, int interfaceId, int childId, int zoom) {
        PacketRepository.send(DisplayModel.class, new DisplayModelContext(player, ModelType.MODEL, modelID, zoom, interfaceId, childId, new Object()));
    }

    /**
     * Send angle on interface.
     *
     * @param interfaceId the interface id
     * @param childId     the child id
     * @param zoom        the zoom
     * @param pitch       the pitch
     * @param yaw         the yaw
     */
    public void sendAngleOnInterface(int interfaceId, int childId, int zoom, int pitch, int yaw) {
        PacketRepository.send(InterfaceSetAngle.class, new DefaultContext(player, pitch, zoom, yaw, interfaceId, childId));
    }

    /**
     * Send item on interface.
     *
     * @param itemId      the item id
     * @param amount      the amount
     * @param interfaceId the interface id
     * @param childId     the child id
     */
    public void sendItemOnInterface(int itemId, int amount, int interfaceId, int childId) {
        PacketRepository.send(DisplayModel.class, new DisplayModelContext(player, ModelType.ITEM, itemId, amount, interfaceId, childId));
    }

    /**
     * Send item zoom on interface.
     *
     * @param itemId      the item id
     * @param zoom        the zoom
     * @param interfaceId the interface id
     * @param childId     the child id
     */
    public void sendItemZoomOnInterface(int itemId, int zoom, int interfaceId, int childId) {
        PacketRepository.send(DisplayModel.class, new DisplayModelContext(player, ModelType.ITEM, itemId, zoom, interfaceId, childId, zoom));
    }

    /**
     * Send inter set items options script.
     *
     * @param interfaceId the interface id
     * @param componentId the component id
     * @param key         the key
     * @param width       the width
     * @param height      the height
     * @param options     the options
     */
    public void sendInterSetItemsOptionsScript(int interfaceId, int componentId, int key, int width, int height, String... options) {
        sendInterSetItemsOptionsScript(interfaceId, componentId, key, false, width, height, options);
    }

    /**
     * Send inter set items options script.
     *
     * @param interfaceId the interface id
     * @param componentId the component id
     * @param key         the key
     * @param negativeKey the negative key
     * @param width       the width
     * @param height      the height
     * @param options     the options
     */
    public void sendInterSetItemsOptionsScript(int interfaceId, int componentId, int key, boolean negativeKey, int width, int height, String... options) {
        Object[] parameters = new Object[6 + options.length];
        int index = 0;
        for (int count = options.length - 1; count >= 0; count--)
            parameters[index++] = options[count];
        parameters[index++] = -1;
        parameters[index++] = 0;
        parameters[index++] = height;
        parameters[index++] = width;
        parameters[index++] = key;
        parameters[index++] = interfaceId << 16 | componentId;
        sendRunScript(negativeKey ? 695 : 150, parameters);
    }

    /**
     * Send run script.
     *
     * @param scriptId the script id
     * @param params   the params
     */
    public void sendRunScript(int scriptId, Object... params) {
        String parameterTypes = "";
        if (params != null) {
            for (int count = params.length - 1; count >= 0; count--) {
                if (params[count] instanceof String)
                    parameterTypes += "s"; // string
                else
                    parameterTypes += "i"; // integer
            }
        }
        sendRunScript(scriptId, parameterTypes, params);
    }


    /**
     * Send item zoom on interface.
     *
     * @param itemId      the item id
     * @param amount      the amount
     * @param zoom        the zoom
     * @param interfaceId the interface id
     * @param childId     the child id
     */
    public void sendItemZoomOnInterface(int itemId, int amount, int zoom, int interfaceId, int childId) {
        PacketRepository.send(DisplayModel.class, new DisplayModelContext(player, ModelType.ITEM, itemId, amount, interfaceId, childId, zoom));
    }

    /**
     * Send interface config.
     *
     * @param interfaceId the interface id
     * @param childId     the child id
     * @param hide        the hide
     */
    public void sendInterfaceConfig(int interfaceId, int childId, boolean hide) {
        PacketRepository.send(InterfaceConfig.class, new InterfaceConfigContext(player, interfaceId, childId, hide));
    }

    /**
     * Send animation.
     *
     * @param id the id
     */
    public void sendAnimation(int id) {
        player.getUpdateMasks().register(EntityFlag.Animate, new Animation(id));
    }

    /**
     * Send animation.
     *
     * @param id    the id
     * @param delay the delay
     */
    public void sendAnimation(int id, int delay) {
        player.getUpdateMasks().register(EntityFlag.Animate, new Animation(id, delay));
    }

    /**
     * Send graphic.
     *
     * @param id the id
     */
    public void sendGraphic(int id) {
        player.getUpdateMasks().register(EntityFlag.SpotAnim, new Graphic(id));
    }

    /**
     * Send positioned graphic.
     *
     * @param id       the id
     * @param height   the height
     * @param delay    the delay
     * @param location the location
     */
    public void sendPositionedGraphic(int id, int height, int delay, Location location) {
        PacketRepository.send(PositionedGraphic.class, new PositionedGraphicContext(player, new Graphic(id, height, delay), location, 0, 0));
    }

    /**
     * Send global position graphic.
     *
     * @param id       the id
     * @param location the location
     */
    public void sendGlobalPositionGraphic(int id, Location location) {
        for (Player player : RegionManager.getLocalPlayers(location)) {
            player.getPacketDispatch().sendPositionedGraphic(id, 0, 0, location);
        }
    }

    /**
     * Send positioned graphics.
     *
     * @param graphic  the graphic
     * @param location the location
     */
    public void sendPositionedGraphics(Graphic graphic, Location location) {
        PacketRepository.send(PositionedGraphic.class, new PositionedGraphicContext(player, graphic, location, 0, 0));
    }

    /**
     * Send scenery animation.
     *
     * @param object    the object
     * @param animation the animation
     */
    public void sendSceneryAnimation(Scenery object, Animation animation) {
        animation = new Animation(animation.getId(), animation.getDelay(), animation.getPriority());
        animation.setObject(object);
        RegionManager.getRegionChunk(object.getLocation()).flag(new AnimateSceneryUpdateFlag(animation));
    }

    /**
     * Send scenery animation.
     *
     * @param object    the object
     * @param animation the animation
     * @param global    the global
     */
    public void sendSceneryAnimation(Scenery object, Animation animation, boolean global) {
        if (global) {
            sendSceneryAnimation(object, animation);
            return;
        }
        animation.setObject(object);
        PacketRepository.send(AnimateScenery.class, new AnimateSceneryContext(player, animation));
    }

    /**
     * Send graphic.
     *
     * @param id     the id
     * @param height the height
     */
    public void sendGraphic(int id, int height) {
        player.getUpdateMasks().register(EntityFlag.SpotAnim, new Graphic(id, height));
    }

    /*
     * Send var client.
     *
     * @param id    the id
     * @param value the value
     * @param cs2   the cs 2
     *
     *  public void sendVarClient(int id, int value, boolean cs2) {
     *  	PacketRepository.send(Config.class, new ConfigContext(player, id, value, cs2));
     *  }
     */

    /**
     * Send left shifted varbit.
     *
     * @param varpIndex the varp index
     * @param offset    the offset
     * @param value     the value
     */
    public void sendLeftShiftedVarbit(int varpIndex, int offset, int value) {
        setVarp(player, varpIndex, (value << offset));
    }

    /**
     * Send right shifted varbit.
     *
     * @param varpIndex the varp index
     * @param offset    the offset
     * @param value     the value
     */
    public void sendRightShiftedVarbit(int varpIndex, int offset, int value) {
        setVarp(player, varpIndex, (value >> offset));
    }


    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public PlayerContext getContext() {
        return context;
    }

    /**
     * Send script configs.
     *
     * @param id     the id
     * @param value  the value
     * @param type   the type
     * @param params the params
     */
    public void sendScriptConfigs(int id, int value, String type, Object... params) {
        PacketRepository.send(CSConfig.class, new CSConfigContext(player, id, value, type, params));
    }

    /**
     * Reset interface.
     *
     * @param id the id
     */
    public void resetInterface(int id) {
        PacketRepository.send(ResetInterface.class, new InterfaceContext(player, 0, 0, id, false));
    }
}
