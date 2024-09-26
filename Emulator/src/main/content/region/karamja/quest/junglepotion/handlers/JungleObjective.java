package content.region.karamja.quest.junglepotion.handlers;

import content.global.skill.herblore.herbs.Herb;
import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.system.task.Pulse;
import core.game.world.update.flag.context.Animation;
import core.tools.RandomFunction;
import core.tools.StringUtils;

import static core.api.ContentAPIKt.sendMessage;

/**
 * The Jungle objects.
 */
public enum JungleObjective {
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
    PALM_TREE(2577, Herb.ARDRIGAL, 20, "You are looking for Ardrigal. It is related to the palm", "and grows in its brothers shady profusion."),
    SITO_FOIL(2579, Herb.SITO_FOIL, 30, "You are looking for Sito Foil, and it grows best where", "the ground has been blackened by the living flame."),
    VOLENCIA_MOSS(2581, Herb.VOLENCIA_MOSS, 40, "You are looking for Volencia Moss. It clings to rocks", "for its existence. It is difficult to see, so you must", "search for it well."),
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