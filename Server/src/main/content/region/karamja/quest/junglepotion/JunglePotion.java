package content.region.karamja.quest.junglepotion;

import content.global.skill.production.herblore.data.Herb;
import content.region.karamja.quest.junglepotion.dialogue.TrufitusDialogue;
import content.region.karamja.quest.junglepotion.plugin.JunglePotionPlugin;
import core.api.consts.Vars;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.quest.Quest;
import core.game.node.entity.skill.Skills;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.system.task.Pulse;
import core.game.world.update.flag.context.Animation;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.tools.RandomFunction;
import core.tools.StringUtils;

import static core.api.ContentAPIKt.sendMessage;
import static core.api.ContentAPIKt.setVarbit;

/**
 * The Jungle potion.
 */
@Initializable
public final class JunglePotion extends Quest {

    /**
     * The constant NAME.
     */
    public static final String NAME = "Jungle Potion";

    /**
     * Instantiates a new Jungle potion.
     */
    public JunglePotion() {
        super(NAME, 81, 80, 1, Vars.VARP_QUEST_JUNGLE_POTION_PROGRESS, 0, 1, 12);
    }

    @Override
    public Quest newInstance(Object object) {
        ClassScanner.definePlugin(new TrufitusDialogue());
        ClassScanner.definePlugin(new JunglePotionPlugin.JogreCavernDialogue());
        ClassScanner.definePlugin(new JunglePotionPlugin());
        return this;
    }

    @Override
    public void drawJournal(Player player, int stage) {
        super.drawJournal(player, stage);
        switch (stage) {
            case 0:
                line(player, "<blue>I can start this quest by speaking to <red>Trufitus Shakaya<n><blue>who lives in the main hut in <red>Tai Bwo Wannai<n><blue>village on the island of <red>Karamja.", 11);
                break;
            case 10:
            case 20:
            case 30:
            case 40:
            case 50:
                JungleObjective objective = JungleObjective.forStage(stage);
                if (player.getInventory().containsItem(objective.getHerb().getProduct())) {
                    line(player, "<str>I spoke to Trufitus, he needs to commune with the<n><str>gods, he's asked me to help him by collecting herbs.<n><n><str>I picked some fresh " + objective.getName() + " for Trufitus.<n><n><blue>I need to give the <red>" + objective.getName() + " <blue> to <red>Trufitus.", 11);
                    return;
                }
                line(player, "<str>I spoke to Trufitus, he needs to commune with the<n><str>gods, he's asked me to help him by collecting herbs.<n><n><blue>I need to pick some fresh <red>" + objective.getName() + " <blue>for <red>Trufitus.", 11);
                break;
            case 60:
                line(player, "<str>I spoke to Trufitus, he needs to commune with the<n><str>gods, he's asked me to help him by collecting herbs.<n><n><str>I have given Trufitus Snakeweed, Ardrigal,<n><str>Sito Foil, Volencia moss and Rogues purse.<n><n><str>Trufitus needs to commune with the gods.<n><blue>I should speak to <red>Trufitus.", 11);
                break;
            case 100:
                line(player, "<str>Trufitus Shakaya of the Tai Bwo Wannai village needed<n><str>some jungle herbs in order to make a potion which would<n><str>help him commune with the gods. I collected five lots<n><str>of jungle herbs for him and he was able to<n><str>commune with the gods.<n><n><str>As a reward he showed me some herblore techniques.<n><n><col=FF0000>QUEST COMPLETE!</col>", 11);
                break;
        }
    }

    @Override
    public void finish(Player player) {
        super.finish(player);
        player.getPacketDispatch().sendString("1 Quest Point", 277, 8 + 2);
        player.getPacketDispatch().sendString("775 Herblore XP", 277, 9 + 2);
        player.getPacketDispatch().sendItemZoomOnInterface(Herb.VOLENCIA_MOSS.getProduct().getId(), 235, 277, 3 + 2);
        player.getSkills().addExperience(Skills.HERBLORE, 775);
        player.getQuestRepository().syncronizeTab(player);
        setVarbit(player, 897, 2);
        setVarbit(player, 898, 2);
        setVarbit(player, 899, 2);
        setVarbit(player, 900, 2);
        setVarbit(player, 896, 2, true);
    }

    @Override
    public int[] getConfig(Player player, int stage) {
        if (stage == 0) return new int[]{175, 0};
        if (stage == 100) return new int[]{175, 15};
        if (stage > 0) return new int[]{175, 1};
        return new int[]{175, 15};
    }

    /**
     * The enum Jungle objective.
     */
    public enum JungleObjective {
        /**
         * The Jungle vine.
         */
        JUNGLE_VINE(2575, Herb.SNAKE_WEED, 10, "It grows near vines in an area to the south west where", "the ground turns soft and the water kisses your feet.") {
            @Override
            public void search(final Player player, final Scenery object) {
                final Animation animation = Animation.create(2094);
                player.animate(animation);
                player.getPulseManager().run(new Pulse(animation.getDefinition().getDurationTicks(), player, object) {
                    @Override
                    public boolean pulse() {
                        boolean success = RandomFunction.random(3) == 1;
                        if (success) {
                            sendMessage(player, "You search the vine...");
                            switchObject(object);
                            findHerb(player);
                            return true;
                        }
                        player.animate(animation);
                        return false;
                    }
                });
            }
        },
        /**
         * The Palm tree.
         */
        PALM_TREE(2577, Herb.ARDRIGAL, 20, "You are looking for Ardrigal. It is related to the palm", "and grows in its brothers shady profusion."),
        /**
         * The Sito foil.
         */
        SITO_FOIL(2579, Herb.SITO_FOIL, 30, "You are looking for Sito Foil, and it grows best where", "the ground has been blackened by the living flame."),
        /**
         * The Volencia moss.
         */
        VOLENCIA_MOSS(2581, Herb.VOLENCIA_MOSS, 40, "You are looking for Volencia Moss. It clings to rocks", "for its existence. It is difficult to see, so you must", "search for it well."),
        /**
         * The Rogues purse.
         */
        ROGUES_PURSE(32106, Herb.ROGUES_PUSE, 50, "It inhabits the darkness of the underground, and grows", "in the caverns to the north. A secret entrance to the", "caverns is set into the northern cliffs, be careful Bwana.") {
            @Override
            public void search(final Player player, final Scenery object) {
                final Animation animation = Animation.create(2097);
                player.animate(animation);
                player.getPulseManager().run(new Pulse(animation.getDefinition().getDurationTicks(), player, object) {
                    @Override
                    public boolean pulse() {
                        boolean success = RandomFunction.random(4) == 1;
                        if (success) {
                            switchObject(object);
                            findHerb(player);
                            return true;
                        }
                        player.animate(animation, 1);
                        return false;
                    }
                });
            }
        };


        private final int objectId;


        private final Herb herb;


        private final int stage;


        private final String[] clue;


        JungleObjective(int objectId, Herb herb, int stage, final String... clue) {
            this.objectId = objectId;
            this.herb = herb;
            this.stage = stage;
            this.clue = clue;
        }


        /**
         * Search.
         *
         * @param player the player
         * @param object the object
         */
        public void search(final Player player, final Scenery object) {
            findHerb(player);
            switchObject(object);
        }


        /**
         * Switch object.
         *
         * @param object the object
         */
        public void switchObject(Scenery object) {
            if (object.isActive()) {
                SceneryBuilder.replace(object, object.transform(object.getId() + 1), 80);
            }
        }


        /**
         * Find herb.
         *
         * @param player the player
         */
        public void findHerb(final Player player) {
            player.getInventory().add(getHerb().getHerb());
            player.getDialogueInterpreter().sendItemMessage(getHerb().getHerb(), "You find a grimy herb.");
        }


        /**
         * For stage jungle objective.
         *
         * @param stage the stage
         * @return the jungle objective
         */
        public static JungleObjective forStage(int stage) {
            for (JungleObjective o : values()) {
                if (o.getStage() == stage) {
                    return o;
                }
            }
            return null;
        }


        /**
         * For id jungle objective.
         *
         * @param objectId the object id
         * @return the jungle objective
         */
        public static JungleObjective forId(int objectId) {
            for (JungleObjective s : values()) {
                if (s.getObjectId() == objectId) {
                    return s;
                }
            }
            return null;
        }


        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
            return StringUtils.formatDisplayName(herb.getProduct().getName().replace("Clean", "").trim());
        }


        /**
         * Gets object id.
         *
         * @return the object id
         */
        public int getObjectId() {
            return objectId;
        }


        /**
         * Gets herb.
         *
         * @return the herb
         */
        public Herb getHerb() {
            return herb;
        }


        /**
         * Gets stage.
         *
         * @return the stage
         */
        public int getStage() {
            return stage;
        }


        /**
         * Get clue string [ ].
         *
         * @return the string [ ]
         */
        public String[] getClue() {
            return clue;
        }
    }

}
