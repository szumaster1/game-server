package content.global.skill.gathering.hunter;

import core.game.node.Node;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.tools.Log;

import java.util.ArrayList;
import java.util.List;

import static core.api.ContentAPIKt.log;

/**
 * The enum Traps.
 */
public enum Traps {
    /**
     * The Bird snare.
     */
    BIRD_SNARE(new TrapSetting(10006, new int[]{19175}, new int[]{}, "lay", 19174, Animation.create(5208), Animation.create(5207), 1),
            new TrapNode(new int[]{5073}, 1, 34, new int[]{19179, 19180}, new Item[]{new Item(10088, 8), new Item(9978), new Item(526)}),
            new TrapNode(new int[]{5075}, 5, 48, new int[]{19183, 19184}, new Item[]{new Item(10090, 8), new Item(9978), new Item(526)}),
            new TrapNode(new int[]{5076}, 9, 61, new int[]{19185, 19186}, new Item[]{new Item(10091, 8), new Item(9978), new Item(526)}),
            new TrapNode(new int[]{5074}, 11, 64.7, new int[]{19181, 19182}, new Item[]{new Item(10089, 8), new Item(9978), new Item(526)}),
            new TrapNode(new int[]{5072}, 19, 95.2, new int[]{19177, 19178}, new Item[]{new Item(10087, 8), new Item(9978), new Item(526)}),
            new TrapNode(new int[]{7031}, 39, 167, new int[]{28931, 28930}, new Item[]{new Item(11525, 8), new Item(9978), new Item(526)}) {
                @Override
                public boolean canCatch(TrapWrapper wrapper, final NPC npc) {
                    return false;
                }
            }),
    /**
     * The Box trap.
     */
    BOX_TRAP(new TrapSetting(10008, new int[]{19187}, new int[]{1963, 12579, 1869, 9996, 5972, 12535}, "lay", 19192, Animation.create(5208), new Animation(9726), 27),
            new BoxTrapNode(new int[]{5081}, 27, 100, new Item[]{new Item(10092)}, 1),
            new BoxTrapNode(new int[]{6918, 7289, 7290, 7291, 7292}, 27, 100, new Item[]{new Item(12184)}, 10),
            new BoxTrapNode(new int[]{1487}, 27, 100, new Item[]{new Item(4033, 1)}, 95),
            new BoxTrapNode(new int[]{7021, 7022, 7023}, 48, 150, new Item[]{new Item(12551, 1)}, 1),
            new BoxTrapNode(new int[]{5079}, 53, 198, new Item[]{new Item(10033, 1)}, 1),
            new BoxTrapNode(new int[]{5428, 5430, 5449, 5450, 5451}, 56, 150, new Item[]{new Item(12188)}, 1),
            new BoxTrapNode(new int[]{5080}, 63, 265, new Item[]{new Item(10034, 1)}, 1),
            new BoxTrapNode(new int[]{7012, 7014}, 66, 400, new Item[]{new Item(12535)}, 1),
            new BoxTrapNode(new int[]{8654}, 73, 315, new Item[]{new Item(14861)}, 1),
            new BoxTrapNode(new int[]{7010, 7011}, 77, 0, new Item[]{new Item(12539, 1)}, 1) {
                @Override
                public boolean canCatch(TrapWrapper wrapper, final NPC npc) {
                    //old xp: 726
                    wrapper.getPlayer().sendMessage("Note: Giving 0 xp for grenwalls until this area and its requirements are implemented.");
                    return super.canCatch(wrapper, npc);
                }
            }),
    /**
     * The Rabbit snare.
     */
    RABBIT_SNARE(new TrapSetting(10031, new int[]{19333}, new int[]{}, "lay", -1, Animation.create(5208), Animation.create(9726), 27)),
    /**
     * The Imp box.
     */
    IMP_BOX(new MagicBoxSetting(),
            new TrapNode(new int[]{708, 709, 1531}, 71, 450, new int[]{-1, 19226}, new Item[]{new Item(10027)})),
    /**
     * The Dead fall.
     */
    DEAD_FALL(new DeadfallSetting(),
            new TrapNode(new int[]{5089}, 23, 128, new int[]{19213, 19214, 19218}, new Item[]{new Item(10113), new Item(526)}),
            new TrapNode(new int[]{5088}, 33, 168, new int[]{19211, 19212, 19217}, new Item[]{new Item(10129), new Item(526)}),
            new TrapNode(new int[]{5086}, 37, 204, new int[]{19208, 19208, 19217}, new Item[]{new Item(10105), new Item(526)}),
            new TrapNode(new int[]{7039}, 44, 200, new int[]{28939, 28940, 28941}, new Item[]{new Item(12567), new Item(526)}),
            new TrapNode(new int[]{5087}, 51, 200, new int[]{19209, 19210, 19216}, new Item[]{new Item(10109), new Item(526)})),
    /**
     * The Net trap.
     */
    NET_TRAP(new NetTrapSetting(), new TrapNode(new int[]{5117}, 29, 152, new int[]{}, new Item[]{new Item(10149)}),
            new TrapNode(new int[]{5114}, 47, 224, new int[]{}, new Item[]{new Item(10146)}),
            new TrapNode(new int[]{6921}, 29, 152, new int[]{}, new Item[]{new Item(12130)}),
            new TrapNode(new int[]{5115}, 59, 272, new int[]{}, new Item[]{new Item(10147)}),
            new TrapNode(new int[]{5116}, 67, 304, new int[]{}, new Item[]{new Item(10148)}));

    private final List<TrapHook> hooks = new ArrayList<>(5);

    private final TrapSetting settings;

    private final TrapNode[] nodes;


    Traps(TrapSetting settings, TrapNode... nodes) {
        this.settings = settings;
        this.nodes = nodes;
    }

    /**
     * Create.
     *
     * @param player the player
     * @param node   the node
     */
    public void create(Player player, Node node) {
        player.getPulseManager().run(new TrapCreatePulse(player, node, this));
    }

    /**
     * Dismantle.
     *
     * @param player the player
     * @param object the object
     */
    public void dismantle(Player player, Scenery object) {
        HunterManager instance = HunterManager.getInstance(player);

        if (!instance.isOwner(object)) {
            player.sendMessage("This isn't your trap!");
            return;
        }
        if (instance.getWrapper(object) == null) {
            log(this.getClass(), Log.ERR, "NO WRAPPER (HUNTER DISMANTLE)");
            return;
        }
        player.faceLocation(object.getLocation());
        player.getPulseManager().run(new TrapDismantlePulse(player, object, instance.getWrapper(object)));
    }

    /**
     * Investigate.
     *
     * @param player the player
     * @param object the object
     */
    public void investigate(Player player, Scenery object) {
        getSettings().investigate(player, object);
    }

    /**
     * Catch npc.
     *
     * @param wrapper the wrapper
     * @param npc     the npc
     */
    public void catchNpc(TrapWrapper wrapper, NPC npc) {
        final TrapNode trapNode = forNpc(npc);
        if (trapNode == null || !trapNode.canCatch(wrapper, npc) || !settings.canCatch(wrapper, npc)) {
            return;
        }
        settings.catchNpc(wrapper, trapNode, npc);
    }

    /**
     * Add hook trap hook.
     *
     * @param wrapper the wrapper
     * @return the trap hook
     */
    public TrapHook addHook(TrapWrapper wrapper) {
        TrapHook hook = settings.createHook(wrapper);
        hooks.add(hook);
        return hook;
    }

    /**
     * For npc trap node.
     *
     * @param npc the npc
     * @return the trap node
     */
    public TrapNode forNpc(NPC npc) {
        for (TrapNode node : nodes) {
            for (int npcId : node.getNpcIds()) {
                if (npcId == npc.getId()) {
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * For node traps.
     *
     * @param node the node
     * @return the traps
     */
    public static Traps forNode(Node node) {
        for (Traps trap : Traps.values()) {
            for (int nodeId : trap.getSettings().getNodeIds()) {
                if (node.getId() == nodeId) {
                    return trap;
                }
            }
            for (int objectId : trap.getSettings().getObjectIds()) {
                if (objectId == node.getId()) {
                    return trap;
                }
            }
            for (TrapNode n : trap.getNodes()) {
                for (int id : n.getObjectIds()) {
                    if (id == node.getId()) {
                        return trap;
                    }
                }
            }
            if (trap.getSettings().getFailId() == node.getId()) {
                return trap;
            }
            if (trap == NET_TRAP) {
                for (NetTrapSetting.NetTrap net : NetTrapSetting.NetTrap.values()) {
                    if (net.getOriginal() == node.getId() || net.getFailed() == node.getId() || net.getNet() == node.getId() || net.getBent() == node.getId() || net.getCaught() == node.getId()) {
                        return trap;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get node object [ ].
     *
     * @param id the id
     * @return the object [ ]
     */
    public static Object[] getNode(int id) {
        for (Traps trap : values()) {
            for (TrapNode t : trap.getNodes()) {
                for (int i : t.getNpcIds()) {
                    if (i == id) {
                        return new Object[]{trap, t};
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets by hook.
     *
     * @param location the location
     * @return the by hook
     */
    public TrapWrapper getByHook(Location location) {
        for (TrapHook hook : hooks) {
            if (hook.isHooked(location)) {
                return hook.getWrapper();
            }
        }
        return null;
    }

    /**
     * Gets settings.
     *
     * @return the settings
     */
    public TrapSetting getSettings() {
        return settings;
    }

    /**
     * Get nodes trap node [ ].
     *
     * @return the trap node [ ]
     */
    public TrapNode[] getNodes() {
        return nodes;
    }

    /**
     * Gets hooks.
     *
     * @return the hooks
     */
    public List<TrapHook> getHooks() {
        return hooks;
    }

}
