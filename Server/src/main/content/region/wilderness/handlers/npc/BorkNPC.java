package content.region.wilderness.handlers.npc;

import content.data.BossKillCounter;
import core.api.consts.Regions;
import core.game.activity.ActivityPlugin;
import core.game.activity.CutscenePlugin;
import core.game.component.Component;
import core.game.dialogue.DialogueInterpreter;
import core.game.dialogue.Dialogue;
import core.game.dialogue.FacialExpression;
import core.game.interaction.Option;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.ChanceItem;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.map.build.DynamicRegion;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.network.packet.PacketRepository;
import core.network.packet.context.CameraContext;
import core.network.packet.context.CameraContext.CameraType;
import core.network.packet.context.MinimapStateContext;
import core.network.packet.outgoing.CameraViewPacket;
import core.network.packet.outgoing.MinimapState;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.tools.RandomFunction;

import java.util.ArrayList;
import java.util.List;

@Initializable
public class BorkNPC extends AbstractNPC {

    private static final String[] LEGION_CHATS = new String[]{"For Bork!", "Die, human!", "Resistance is futile!", "We are the collective!", "Form a triangle!!", "To the attack!", "Hup! 2... 3... 4!!"};

    private static final ChanceItem[] DROPS = new ChanceItem[]{new ChanceItem(532, 1, 1, 0.0), new ChanceItem(12163, 5, 5, 0.0), new ChanceItem(12160, 7, 7, 0.0), new ChanceItem(12159, 2, 2, 0.0), new ChanceItem(995, 2000, 10000, 0.0), new ChanceItem(1619, 1, 0.0), new ChanceItem(1621, 1, 1, 0.0), new ChanceItem(1623, 1, 1, 0.0)};

    private static final ChanceItem[] RING_DROPS = new ChanceItem[]{new ChanceItem(532, 1, 1, 0.0), new ChanceItem(12163, 5, 5, 0.0), new ChanceItem(12160, 10, 10, 0.0), new ChanceItem(12159, 3, 3, 0.0), new ChanceItem(1601, 1, 1, 0.0), new ChanceItem(995, 2000, 10000, 0.0), new ChanceItem(1619, 2, 2, 0.0), new ChanceItem(1621, 3, 3, 0.0)};

    private final List<NPC> legions = new ArrayList<>(20);

    private boolean spawnedLegion;

    private Player player;

    private BorkCutscene cutscene;

    public BorkNPC() {
        super(-1, null);
    }

    public BorkNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new BorkNPC(id, location);
    }

    @Override
    public void handleTickActions() {
        if (player == null) {
            return;
        }
        super.handleTickActions();
        if (!getLocks().isMovementLocked() && player != null) {
            if (!getProperties().getCombatPulse().isAttacking()) {
                getProperties().getCombatPulse().attack(player);
            }
        }
    }

    @Override
    public void finalizeDeath(Entity killer) {
        super.finalizeDeath(killer);
        BossKillCounter.addtoKillcount((Player) killer, this.getId());
    }

    @Override
    public void commenceDeath(Entity killer) {
        super.commenceDeath(killer);
        for (NPC l : legions) {
            l.clear();
        }
        player.lock();
        cutscene.wizard.clear();
        cutscene.wizard.lock();
        player.getInterfaceManager().removeTabs(0, 1, 2, 3, 4, 5, 6, 11, 12);
        PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 2));
        player.getInterfaceManager().open(new Component(693));
        if (player.getDialogueInterpreter().getDialogue() != null) {
            player.getDialogueInterpreter().getDialogue().end();
        }
        GameWorld.getPulser().submit(new Pulse(10, player) {

            @Override
            public boolean pulse() {
                player.unlock();
                player.getDialogueInterpreter().sendDialogues(player, FacialExpression.FURIOUS, "That monk - he called to Zamorak for revenge!");
                player.sendMessage("Something is shaking the whole cavern! You should get out of here quick!");
                PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.SHAKE, 3, 2, 2, 2, 2));
                player.getInterfaceManager().restoreTabs();
                PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 0));
                player.getInterfaceManager().close();
                return true;
            }

        });
    }

    @Override
    public void handleDrops(Player p, Entity killer) {
        if (player.getAttribute("first-bork", false)) {
            player.removeAttribute("first-bork");
            player.getSkills().addExperience(Skills.SLAYER, 5000, true);
        } else {
            player.getSkills().addExperience(Skills.SLAYER, 1500, true);
        }
        ChanceItem[] drops = new Item(player.getEquipment().getId(12)).getName().toLowerCase().contains("ring of wealth") ? RING_DROPS : DROPS;
        for (int i = 0; i < drops.length; i++) {
            Item item = new Item(drops[i].getId(), RandomFunction.random(drops[i].getMinimumAmount(), drops[i].getMaximumAmount()));
            GroundItemManager.create(item, getLocation(), player);
        }
        if (RandomFunction.random(5) == 1) {
            super.handleDrops(p, killer);
        }
    }

    @Override
    public void checkImpact(BattleState state) {
        super.checkImpact(state);
        if (!spawnedLegion && getSkills().getLifepoints() < (getSkills().getStaticLevel(Skills.HITPOINTS) / 2)) {
            spawnLegion();
        }
    }

    private void spawnLegion() {
        player.getInterfaceManager().removeTabs(0, 1, 2, 3, 4, 5, 6, 11, 12);
        PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 2));
        spawnedLegion = true;
        player.lock();
        player.getImpactHandler().setDisabledTicks(14);
        lock();
        cutscene.wizard.lock();
        getProperties().getCombatPulse().stop();
        player.getProperties().getCombatPulse().stop();
        getAnimator().forceAnimation(Animation.create(8757));
        GameWorld.getPulser().submit(new Pulse(1, player, this) {

            @Override
            public boolean pulse() {
                getAnimator().forceAnimation(Animation.create(8757));
                sendChat("Come to my aid, brothers!");
                player.sendMessage("Bork strikes the ground with his axe.");
                GameWorld.getPulser().submit(new Pulse(4, player) {

                    @Override
                    public boolean pulse() {
                        player.getInterfaceManager().open(new Component(691));
                        return true;
                    }

                });
                GameWorld.getPulser().submit(new Pulse(13, player) {

                    @Override
                    public boolean pulse() {
                        player.getInterfaceManager().close();
                        for (int i = 0; i < 3; i++) {
                            NPC legion = NPC.create(7135, getLocation().transform(RandomFunction.random(1, 3), RandomFunction.random(1, 3), 0), player);
                            legion.init();
                            legion.graphics(Graphic.create(1314));
                            legion.setAggressive(true);
                            legion.setRespawn(false);
                            legion.attack(player);
                            legions.add(legion);
                            legion.sendChat(RandomFunction.getRandomElement(LEGION_CHATS));
                        }
                        player.unlock();
                        cutscene.wizard.unlock();
                        unlock();
                        if (player != null) {
                            attack(player);
                            player.getInterfaceManager().restoreTabs();
                            PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 0));
                        }
                        for (NPC n : legions) {
                            n.getProperties().getCombatPulse().attack(player);
                        }
                        return true;
                    }

                });
                return true;
            }

        });
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ClassScanner.definePlugin(new BorkCutscene());
        ClassScanner.definePlugin(new DagonDialogue());
        ClassScanner.definePlugin(new OrkLegion());
        ClassScanner.definePlugin(new DagonElite());
        return super.newInstance(arg);
    }

    @Override
    public int[] getIds() {
        return new int[]{7133};
    }

    /**
     * The Ork legion.
     */
    public class OrkLegion extends AbstractNPC {

        private Player player;


        private int lastTalk = GameWorld.getTicks() + 30;


        /**
         * Instantiates a new Ork legion.
         *
         * @param id       the id
         * @param location the location
         */
        public OrkLegion(int id, Location location) {
            super(id, location);
            super.setAggressive(true);
        }


        /**
         * Instantiates a new Ork legion.
         */
        public OrkLegion() {
            super(-1, null);
        }

        @Override
        public void handleTickActions() {
            if (player == null) {
                return;
            }

            if (lastTalk < GameWorld.getTicks()) {
                sendChat(LEGION_CHATS[RandomFunction.random(LEGION_CHATS.length)]);
                lastTalk = GameWorld.getTicks() + 30;
            }
        }

        @Override
        public boolean isIgnoreMultiBoundaries(Entity victim) {
            return true;
        }

        @Override
        public boolean canAttack(Entity e) {
            return true;
        }

        @Override
        public AbstractNPC construct(int id, Location location, Object... objects) {
            OrkLegion legion = new OrkLegion(id, location);
            legion.player = (Player) objects[0];
            return legion;
        }

        @Override
        public int[] getIds() {
            return new int[]{7135};
        }

    }

    /**
     * The Dagon elite.
     */
    public class DagonElite extends AbstractNPC {

        private Player player;


        /**
         * Instantiates a new Dagon elite.
         *
         * @param id       the id
         * @param location the location
         */
        public DagonElite(int id, Location location) {
            super(id, location);
        }


        /**
         * Instantiates a new Dagon elite.
         */
        public DagonElite() {
            super(-1, null);
        }

        @Override
        public void checkImpact(BattleState state) {
            state.neutralizeHits();
        }

        @Override
        public boolean isIgnoreMultiBoundaries(Entity victim) {
            return true;
        }

        @Override
        public boolean canAttack(Entity e) {
            return true;
        }

        @Override
        public boolean isAttackable(Entity e, CombatStyle style, boolean message) {
            return false;
        }

        @Override
        public void handleTickActions() {
            if (player == null) {
                return;
            }
            super.handleTickActions();
            if (!getProperties().getCombatPulse().isAttacking()) {
                getProperties().getCombatPulse().attack(player);
            }
        }

        @Override
        public AbstractNPC construct(int id, Location location, Object... objects) {
            DagonElite elite = new DagonElite(id, location);
            elite.player = (Player) objects[0];
            return elite;
        }

        @Override
        public int[] getIds() {
            return new int[]{7137};
        }

    }

    /**
     * The Bork cutscene.
     */
    public class BorkCutscene extends CutscenePlugin {


        private BorkNPC bork;


        private NPC wizard;


        /**
         * Instantiates a new Bork cutscene.
         */
        public BorkCutscene() {
            super("Bork cutscene");
            this.setMulticombat(true);
        }


        /**
         * Instantiates a new Bork cutscene.
         *
         * @param player the player
         */
        public BorkCutscene(Player player) {
            this();
            this.player = player;
        }

        @Override
        public boolean interact(Entity e, Node target, Option option) {
            if (e instanceof Player) {
                switch (target.getId()) {
                    case 29537:
                        e.asPlayer().graphics(Graphic.create(110));
                        e.asPlayer().teleport(Location.create(3143, 5545, 0));
                        return true;
                }
            }
            return super.interact(e, target, option);
        }

        @Override
        public boolean leave(Entity entity, boolean logout) {
            if (entity instanceof Player) {
                PacketRepository.send(CameraViewPacket.class, new CameraContext(entity.asPlayer(), CameraType.RESET, 3, 2, 2, 2, 2));
            }
            return super.leave(entity, logout);
        }

        @Override
        public boolean start(Player player, boolean login, Object... args) {
            player.lock();
            bork = new BorkNPC(7133, base.transform(26, 33, 0));
            bork.init();
            bork.setPlayer(player);
            bork.setRespawn(false);
            bork.lock();
            bork.cutscene = this;
            wizard = NPC.create(7137, base.transform(38, 29, 0), player);
            wizard.init();
            wizard.lock();
            wizard.faceTemporary(player, 2);
            player.getImpactHandler().setDisabledTicks(5);
            player.faceTemporary(wizard, 2);
            return super.start(player, login, args);
        }

        @Override
        public void stop(boolean fade) {
            end();
        }


        /**
         * Commence fight.
         */
        public void commenceFight() {
            bork.unlock();
            wizard.unlock();
            player.unlock();
            wizard.attack(player);
            bork.attack(player);
            wizard.setRespawn(false);
            player.getInterfaceManager().restoreTabs();
            PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 0));
        }

        @Override
        public void open() {
            super.open();
            bork.cutscene.player = player;
            player.lock();
            player.getInterfaceManager().open(new Component(692));
            GameWorld.getPulser().submit(new Pulse(13, player) {

                @Override
                public boolean pulse() {
                    commenceFight();
                    player.getInterfaceManager().close();

                    player.getDialogueInterpreter().open("dagon-dialogue", wizard, BorkCutscene.this);
                    return true;
                }

            });
        }

        @Override
        public ActivityPlugin newInstance(Player p) throws Throwable {
            return new BorkCutscene(player);
        }

        @Override
        public Location getStartLocation() {
            return base.transform(36, 33, 0);
        }

        @Override
        public Location getSpawnLocation() {
            return null;
        }

        @Override
        public void configure() {
            region = DynamicRegion.create(Regions.NPC_BORK_REGION_12374);
            region.setMulticombat(true);
            setRegionBase();
            registerRegion(region.getId());
        }
    }

    /**
     * The Dagon dialogue.
     */
    public class DagonDialogue extends Dialogue {


        private BorkCutscene cutscene;


        /**
         * Instantiates a new Dagon dialogue.
         */
        public DagonDialogue() {

        }


        /**
         * Instantiates a new Dagon dialogue.
         *
         * @param player the player
         */
        public DagonDialogue(Player player) {
            super(player);
        }

        @Override
        public Dialogue newInstance(Player player) {
            return new DagonDialogue(player);
        }

        @Override
        public boolean open(Object... args) {
            npc = (NPC) args[0];
            cutscene = (BorkCutscene) args[1];
            npc("Our Lord Zamorak has power over life and death,", player.getUsername() + "! He has seen fit to resurrect Bork to", "continue his great work...and now you will fall before him");
            return true;
        }

        @Override
        public boolean handle(int interfaceId, int buttonId) {
            switch (stage) {
                case 0:
                    boolean played = player.getSavedData().activityData.hasKilledBork();
                    player(played ? "Uh -oh! Here we go again." : "Oh boy...");
                    stage++;
                    break;
                case 1:
                    end();
                    cutscene.commenceFight();
                    break;
            }
            return true;
        }

        @Override
        public int[] getIds() {
            return new int[]{DialogueInterpreter.getDialogueKey("dagon-dialogue")};
        }

    }
}
