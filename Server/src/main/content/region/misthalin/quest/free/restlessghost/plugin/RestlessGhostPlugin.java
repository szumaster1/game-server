package content.region.misthalin.quest.free.restlessghost.plugin;

import content.region.misthalin.quest.free.restlessghost.RestlessGhost;
import core.api.consts.Sounds;
import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.playAudio;
import static core.api.ContentAPIKt.setVarp;

/**
 * The Restless ghost plugin.
 */
@Initializable
public final class RestlessGhostPlugin extends OptionHandler {

    private static final Animation OPEN_ANIM = new Animation(536);

    private static final Animation CLOSE_ANIM = new Animation(535);

    private static final Item SKULL = new Item(964);

    private static RestlessGhostNPC GHOST;

    private static final int[] COFFIN_IDS = new int[]{2145, 15061, 15052, 15053, 15050, 15051};

    private static final String[] OPTIONS = new String[]{"open", "close", "search"};

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (int coffin : COFFIN_IDS) {
            for (String option : OPTIONS) {
                SceneryDefinition.forId(coffin).getHandlers().put("option:" + option, this);
            }
        }
        new RestlessGhostNPC().newInstance(arg);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        if (GHOST == null) {
            GHOST = new RestlessGhostNPC(457, Location.create(3250, 3195, 0));
            GHOST.init();
            GHOST.setInvisible(true);
        }
        final int id = node.getId();
        final Scenery object = (Scenery) node;
        switch (option) {
            case "open":
                switch (id) {
                    default:
                        toggleCoffin(player, object);
                        break;
                }
            case "close":
                switch (id) {
                    case 15052:
                    case 15053:
                        toggleCoffin(player, object);
                        break;
                }
                break;
            case "search":
                switch (id) {
                    case 15052:
                        player.getPacketDispatch().sendMessage("You search the coffin and find some human remains.");
                        break;
                    case 15053:
                        player.getDialogueInterpreter().sendDialogue("There's a nice and complete skeleton in here!");
                        break;
                    case 15050:
                        searchAltar(player, object);
                        break;
                    case 15051:
                        if (!player.getQuestRepository().isComplete(RestlessGhost.NAME) && !player.getBank().containsItem(SKULL) && !player.getInventory().containsItem(SKULL)) {
                            player.getInventory().add(SKULL);
                            player.getPacketDispatch().sendMessage("You find another skull.");
                        }
                        player.getQuestRepository().getQuest(RestlessGhost.NAME).setStage(player, 40);
                        break;
                    case 2145:
                        toggleCoffin(player, object);
                        break;
                }
                break;
        }
        return true;
    }

    private void toggleCoffin(final Player player, final Scenery object) {
        final boolean open = object.getId() == 2145;
        player.lock(2);
        player.animate(open ? OPEN_ANIM : CLOSE_ANIM);
        SceneryBuilder.replace(object, object.transform(open ? 15061 : 2145));
        player.getPacketDispatch().sendMessage("You " + (open ? "open" : "close") + " the coffin.");
        if (open && !player.getQuestRepository().isComplete(RestlessGhost.NAME)) {
            playAudio(player, Sounds.RG_GHOST_APPROACH_1743, 1);
            sendGhost();
        }
    }

    private void sendGhost() {
        if (!GHOST.isInvisible()) {
            return;
        }
        GHOST.setInvisible(false);
        GameWorld.getPulser().submit(new Pulse(100, GHOST) {
            @Override
            public boolean pulse() {
                GHOST.setInvisible(true);
                return true;
            }
        });
    }

    private void searchAltar(final Player player, final Scenery object) {
        final boolean hasSkull = object.getId() == 15051;
        if (player.getQuestRepository().getQuest(RestlessGhost.NAME).getStage(player) != 30) {
            player.getPacketDispatch().sendMessage("You search the altar and find nothing.");
            return;
        }
        if (!hasSkull) {
            if (!player.getInventory().add(SKULL)) {
                GroundItemManager.create(SKULL, player);
            }
            setVarp(player, 728, 5, true);
            player.getQuestRepository().getQuest(RestlessGhost.NAME).setStage(player, 40);
            player.getPacketDispatch().sendMessage("The skeleton in the corner suddenly comes to life!");
            sendSkeleton(player);
        }
    }

    private void sendSkeleton(final Player player) {
        final NPC skeleton = NPC.create(459, Location.create(3120, 9568, 0));
        skeleton.setWalks(false);
        skeleton.setRespawn(false);
        skeleton.setAttribute("player", player);
        skeleton.init();
        playAudio(player, Sounds.RG_SKELETON_AWAKE_1746);
        skeleton.getProperties().getCombatPulse().setStyle(CombatStyle.MELEE);
        skeleton.getProperties().getCombatPulse().attack(player);
    }

    /**
     * The Restless ghost npc.
     */
    public static final class RestlessGhostNPC extends AbstractNPC {


        private static final int[] ID = {459, 457};


        /**
         * Instantiates a new Restless ghost npc.
         */
        public RestlessGhostNPC() {
            super(0, null);
        }


        private RestlessGhostNPC(int id, Location location) {
            super(id, location, false);
        }

        @Override
        public AbstractNPC construct(int id, Location location, Object... objects) {
            return new RestlessGhostNPC(id, location);
        }

        @Override
        public void init() {
            super.init();
            this.getProperties().getCombatPulse().setStyle(CombatStyle.MELEE);
        }

        @Override
        public void tick() {
            super.tick();
            final Player pl = getAttribute("player", null);
            if (pl != null) {
                if (getAttribute("dead", false) || !getLocation().withinDistance(pl.getLocation(), 16)) {
                    clear();
                }
            }
        }

        @Override
        public boolean isAttackable(Entity entity, CombatStyle style, boolean message) {
            final Player player = ((Player) entity);
            final Player pl = getAttribute("player", null);
            return pl != null && pl == player;
        }

        @Override
        public void finalizeDeath(Entity killer) {
            super.finalizeDeath(killer);
            removeAttribute("player");
        }

        @Override
        public boolean isHidden(final Player player) {
            final Player pl = getAttribute("player", null);
            if (this.getRespawnTick() > GameWorld.getTicks()) {
                return true;
            }
            return player.getQuestRepository().isComplete(RestlessGhost.NAME) || (pl != null && player != pl);
        }

        @Override
        public int[] getIds() {
            return ID;
        }

    }

}
