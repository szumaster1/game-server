package core.game.node.entity.player.link.emote;

import core.api.consts.*;
import core.game.container.impl.EquipmentContainer;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.info.Rights;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Direction;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;

import static core.api.ContentAPIKt.playJingle;
import static core.api.ContentAPIKt.setAttribute;

/**
 * Represents an emote.
 */
public enum Emotes {
    /**
     * Yes emotes.
     */
    YES(2, Animation.create(855)),
    /**
     * No emotes.
     */
    NO(3, Animation.create(856)),
    /**
     * The Bow.
     */
    BOW(4, Animation.create(858)) {
        @Override
        public void play(Player player) {
            Item legs = player.getEquipment().get(EquipmentContainer.SLOT_LEGS);
            if (legs != null && legs.getId() == Items.PANTALOONS_10396) {
                forceEmote(player, Animation.create(Animations.HUMAN_CURTSY_5312), null);
                return;
            }
            super.play(player);
        }
    },
    /**
     * The Angry.
     */
    ANGRY(5, Animation.create(859)) {
        @Override
        public void play(Player player) {
            Item hat = player.getEquipment().get(EquipmentContainer.SLOT_HAT);
            if (hat != null && hat.getId() == Items.A_POWDERED_WIG_10392) {
                forceEmote(player, Animation.create(Animations.HUMAN_ENHANCED_ANGRY_5315), null);
                return;
            }
            super.play(player);
        }
    },
    /**
     * Think emotes.
     */
    THINK(6, Animation.create(857)),
    /**
     * The Wave.
     */
    WAVE(7, Animation.create(863)) {
        @Override
        public void play(Player player) {
            Item weapon = player.getEquipment().get(EquipmentContainer.SLOT_WEAPON);
            if (weapon != null && weapon.getId() == Items.SLED_4084) {
                forceEmote(player, Animation.create(Animations.WAVE_ON_SLED_1483), null);
                return;
            }
            super.play(player);
        }
    },
    /**
     * Shrug emotes.
     */
    SHRUG(8, Animation.create(2113)),
    /**
     * The Cheer.
     */
    CHEER(9, Animation.create(862)) {
        @Override
        public void play(Player player) {
            Item weapon = player.getEquipment().get(EquipmentContainer.SLOT_WEAPON);
            if (weapon != null && weapon.getId() == Items.SLED_4084) {
                forceEmote(player, Animation.create(Animations.CHEER_ON_SLED_1482), null);
                return;
            }
            super.play(player);
        }
    },
    /**
     * The Beckon.
     */
    BECKON(10, Animation.create(864)) {
        @Override
        public void play(Player player) {
            Item weapon = player.getEquipment().get(EquipmentContainer.SLOT_WEAPON);
            Item hat = player.getEquipment().get(EquipmentContainer.SLOT_HAT);
            if (weapon != null && weapon.getId() == Items.SLED_4084) {
                forceEmote(player, Animation.create(Animations.BECKON_ON_SLED_1484), null);
                return;
            }
            if (hat != null && hat.getId() == Items.HARD_HAT_10862) {
                forceEmote(player, Animation.create(Animations.HUMAN_ENHANCED_BECKON_5845), null);
                return;
            }
            super.play(player);
        }
    },
    /**
     * The Jump for joy.
     */
    JUMP_FOR_JOY(11, Animation.create(Animations.JUMP_FOR_JOY_2109)),
    /**
     * Laugh emotes.
     */
    LAUGH(12, Animation.create(Animations.LAUGH_861)),
    /**
     * The Yawn.
     */
    YAWN(13, Animation.create(Animations.YAWN_2111)) {
        @Override
        public void play(Player player) {
            Item hat = player.getEquipment().get(EquipmentContainer.SLOT_HAT);
            if (hat != null && hat.getId() == Items.SLEEPING_CAP_10398) {
                forceEmote(player, Animation.create(Animations.HUMAN_ENHANCED_YAWN_5313), null);
                return;
            }
            super.play(player);
        }
    },
    /**
     * The Dance.
     */
    DANCE(14, Animation.create(Animations.DANCE_866)) {
        @Override
        public void play(Player player) {
            Item legs = player.getEquipment().get(EquipmentContainer.SLOT_LEGS);
            if (legs != null && legs.getId() == Items.FLARED_TROUSERS_10394) {
                forceEmote(player, Animation.create(Animations.HUMAN_ENHANCED_DANCE_5316), null);
                return;
            }
            super.play(player);
        }
    },
    /**
     * Jig emotes.
     */
    JIG(15, Animation.create(Animations.JIG_EMOTE_2106)),
    /**
     * Spin emotes.
     */
    SPIN(16, Animation.create(Animations.TWIRL_2107)),
    /**
     * Headbang emotes.
     */
    HEADBANG(17, Animation.create(Animations.HEADBANG_EMOTE_2108)),
    /**
     * Cry emotes.
     */
    CRY(18, Animation.create(Animations.CRY_860)),
    /**
     * Blow kiss emotes.
     */
    BLOW_KISS(19, Animation.create(Animations.RUNNING_WITH_WEAPON_1368), Graphic.create(Graphics.HEART_574)),
    /**
     * Panic emotes.
     */
    PANIC(20, Animation.create(Animations.PANIC_2105)),
    /**
     * Raspberry emotes.
     */
    RASPBERRY(21, Animation.create(Animations.RASPBERRY_2110)),
    /**
     * The Clap.
     */
    CLAP(22, Animation.create(Animations.CLAP_865)) {
        @Override
        public void play(Player player) {
            Item weapon = player.getEquipment().get(EquipmentContainer.SLOT_WEAPON);
            if (weapon != null && weapon.getId() == Items.SLED_4084) {
                forceEmote(player, Animation.create(Animations.CLAP_ON_SLED_1485), null);
                return;
            }
            super.play(player);
        }
    },
    /**
     * The Salute.
     */
    SALUTE(23, Animation.create(Animations.SALUTE_2112)) {
        @Override
        public void play(Player player) {
            if (!player.getAchievementDiaryManager().hasCompletedTask(DiaryType.FALADOR, 1, 8) &&
                player.getLocation().equals(new Location(2997, 3374, 0)) &&
                player.getDirection() == Direction.SOUTH &&
                player.getEquipment().get(EquipmentContainer.SLOT_HAT).getId() == Items.INITIATE_SALLET_5574 &&
                player.getEquipment().get(EquipmentContainer.SLOT_CHEST).getId() == Items.INITIATE_HAUBERK_5575 &&
                player.getEquipment().get(EquipmentContainer.SLOT_LEGS).getId() == Items.INITIATE_CUISSE_5576) {
                forceEmote(player, Animation.create(2112), null);
                player.getAchievementDiaryManager().finishTask(player, DiaryType.FALADOR, 1, 8);
                return;
            }
            super.play(player);
        }
    },
    /**
     * The Goblin bow.
     */
    GOBLIN_BOW(24, Animation.create(Animations.GOBLIN_BOW_EMOTE_E_2127), "This emote can be unlocked during the Lost Tribe quest.") {
        @Override
        public void play(Player player) {
            if (player.getLocation().getRegionId() == Regions.LUMBRIDGE_BASEMENT_13206 && !player.getAttribute("mistag-greeted", false)) {
                RegionManager.getLocalNpcs(player).forEach(npc -> {
                    if (npc.getId() == NPCs.MISTAG_2084 && npc.getLocation().withinDistance(player.getLocation(), 3) && player.getQuestRepository().getQuest("Lost Tribe").getStage(player) == 45) {
                        player.getDialogueInterpreter().open(NPCs.MISTAG_2084, npc, "greeting");
                        setAttribute(player, "/save:mistag-greeted", true);
                    }
                });
            }
            super.play(player);
        }
    },
    /**
     * The Goblin salute.
     */
    GOBLIN_SALUTE(25, Animation.create(Animations.GOBLIN_SALUTE_EMOTE_E_2128), "This emote can be unlocked during the Lost Tribe quest."),
    /**
     * The Glass box.
     */
    GLASS_BOX(26, Animation.create(Animations.GLASS_BOX_EMOTE_E_1131), "This emote can be unlocked during the Mime random event."),
    /**
     * The Climb rope.
     */
    CLIMB_ROPE(27, Animation.create(Animations.CLIMB_ROPE_EMOTE_E_1130), "This emote can be unlocked during the Mime random event."),
    /**
     * The Lean.
     */
    LEAN_ON_AIR(28, Animation.create(Animations.LEAN_EMOTE_E_1129), "This emote can be unlocked during the Mime random event."),
    /**
     * The Glass wall.
     */
    GLASS_WALL(29, Animation.create(Animations.HUMAN_GLASS_WALL_1128), "This emote can be unlocked during the Mime random event."),
    /**
     * The Idea.
     */
    IDEA(33, Animation.create(Animations.HUMAN_IDEA_4276), Graphic.create(Graphics.LIGHT_BULB_IDEA_EMOTE_712), "You can't use this emote yet. <br>Visit the Stronghold of Security to unlock it."),
    /**
     * The Stomp.
     */
    STOMP(31, Animation.create(Animations.HUMAN_STOMP_4278), "You can't use this emote yet. <br>Visit the Stronghold of Security to unlock it."),
    /**
     * The Flap.
     */
    FLAP(32, Animation.create(Animations.FLAP_EMOTE_E_4280), "You can't use this emote yet. <br>Visit the Strongshold of Security to unlock it.") {
        @Override
        public void play(Player player) {
            Item head = player.getEquipment().get(EquipmentContainer.SLOT_HAT);
            Item wings = player.getEquipment().get(EquipmentContainer.SLOT_CHEST);
            Item legs = player.getEquipment().get(EquipmentContainer.SLOT_LEGS);
            Item feet = player.getEquipment().get(EquipmentContainer.SLOT_FEET);
            if (head != null && wings != null && legs != null && feet != null) {
                if (head.getId() == Items.CHICKEN_HEAD_11021 && wings.getId() == Items.CHICKEN_WINGS_11020 && legs.getId() == Items.CHICKEN_LEGS_11022 && feet.getId() == Items.CHICKEN_FEET_11019) {
                    forceEmote(player, Animation.create(Animations.HUMAN_ENHANCED_FLAP_3859), null);
                    return;
                }
            }
            super.play(player);
        }
    },
    /**
     * The Slap head.
     */
    SLAP_HEAD(30, Animation.create(4275), "You can't use this emote yet. <br>Visit the Stronghold of Security to unlock it."),
    /**
     * The Zombie walk.
     */
    ZOMBIE_WALK(34, Animation.create(3544), "This emote can be unlocked during the Gravedigger random event."),
    /**
     * The Zombie dance.
     */
    ZOMBIE_DANCE(35, Animation.create(3543), "This emote can be unlocked during the Gravedigger random event."),
    /**
     * The Zombie hand.
     */
    ZOMBIE_HAND(36, Animation.create(7272), Graphic.create(1244), "This emote can be unlocked during the Gravedigger random event."),
    /**
     * The Scared.
     */
    SCARED(37, Animation.create(2836), "This emote can be unlocked by playing a Halloween holiday event."),
    /**
     * The Bunny hop.
     */
    BUNNY_HOP(38, Animation.create(6111), "This emote can be unlocked by playing an Easter holiday event."),
    /**
     * The Skillcape.
     */
    SKILLCAPE(39) {
        @Override
        public void play(Player player) {
            Item cape = player.getEquipment().get(EquipmentContainer.SLOT_CAPE);
            if (cape == null) {
                player.getPacketDispatch().sendMessage("You need to be wearing a skillcape in order to perform this emote.");
                return;
            }
            int capeId = cape.getId();
            for (int[] ints : SKILLCAPE_INFO) {
                if (capeId == ints[0] || capeId == ints[1]) {
                    player.getPacketDispatch().sendGraphic(ints[2]);
                    player.getPacketDispatch().sendAnimation(ints[3]);
                    int duration = Animation.create(ints[3]).getDuration();
                    player.getLocks().lock("emote", duration);
                    player.getLocks().lock(duration);
                    return;
                }
            }
            player.getPacketDispatch().sendMessage("You need to be wearing a skillcape in order to perform this emote.");
        }
    },
    /**
     * The Snowman dance.
     */
    SNOWMAN_DANCE(40, Animation.create(7531), "This emote can be unlocked by playing a Christmas holiday event."),
    /**
     * The Air guitar.
     */
    AIR_GUITAR(41, Animation.create(2414), Graphic.create(1537), "This emote can be accessed by unlocking 200 pieces of music.") {
        public void play(Player player) {
            playJingle(player, 302);
            super.play(player);
        }
    },    /**
     * The Safety first.
     */
    SAFETY_FIRST(42, Animation.create(8770), Graphic.create(1553), "You can't use this emote yet. Visit the Stronghold of Player safety to<br>unlock it."),
    /**
     * The Explore.
     */
    EXPLORE(43, Animation.create(9990), Graphic.create(1734), "You can't use this emote yet. You must complete all the Lumbridge <br>and Draynor beginner tasks to unlock it."),
    /**
     * The Trick.
     */
    TRICK(44, Animation.create(10530), Graphic.create(1863), "This emote can be unlocked by playing a Halloween holiday event."),
    /**
     * The Freeze.
     */
    FREEZE(45, Animation.create(11044), Graphic.create(1973), "This emote can be unlocked by playing a Christmas holiday event."),
    /**
     * The Give thanks.
     */
    GIVE_THANKS(46, "This emote can be unlocked by playing a Thanksgiving holiday event.") {
        @Override
        public void play(final Player player) {
            GameWorld.getPulser().submit(new Pulse(1, player) {
                int counter;

                @Override
                public boolean pulse() {
                    switch (counter++) {
                        case 1:
                            player.lock(17);
                            forceEmote(player, Animation.create(10994), Graphic.create(86));
                            break;
                        case 3:
                            player.getAppearance().transformNPC(8499);
                            forceEmote(player, Animation.create(10996), null);
                            break;
                        case 16:
                            player.getAppearance().transformNPC(-1);
                            forceEmote(player, Animation.create(10995), Graphic.create(86));
                            break;
                    }
                    return false;
                }
            });
        }
    };

    /**
     * Represents the skillcape info.
     */
    private static final int[][] SKILLCAPE_INFO = {{9747, 9748, 823, 4959}, {9750, 9751, 828, 4981}, {9753, 9754, 824, 4961}, {9756, 9757, 832, 4973}, {9759, 9760, 829, 4979}, {9762, 9763, 813, 4939}, {9765, 9766, 817, 4947}, {9768, 9769, 833, 4971}, {9771, 9772, 830, 4977}, {9774, 9775, 835, 4969}, {9777, 9778, 826, 4965}, {9780, 9781, 818, 4949}, {9783, 9784, 812, 4937}, {9786, 9787, 1656, 4967}, {9789, 9790, 820, 4953}, {9792, 9793, 814, 4941}, {9795, 9796, 815, 4943}, {9798, 9799, 819, 4951}, {9801, 9802, 821, 4955}, {9804, 9805, 831, 4975}, {9807, 9808, 822, 4957}, {9810, 9811, 825, 4963}, {12169, 12170, 1515, 8525}, {9813, -1, 816, 4945}, {9948, 9949, 907, 5158}};

    /**
     * The button id.
     */
    private final int buttonId;

    /**
     * The animation.
     */
    private final Animation animation;

    /**
     * The graphic to play.
     */
    private final Graphic graphic;

    /**
     * The message to show when the emote is locked.
     */
    private final String lockedMessage;

    /**
     * Constructs a new {@Code Emotes} {@Code Object}
     *
     * @param buttonId      the button id.
     * @param animation     the animation.
     * @param graphic      the graphic.
     * @param lockedMessage the locked message.
     */
    Emotes(int buttonId, Animation animation, Graphic graphic, String lockedMessage) {
        this.buttonId = buttonId;
        this.animation = animation;
        this.graphic = graphic;
        this.lockedMessage = lockedMessage;
    }

    /**
     * Constructs a new {@Code Emotes} {@Code Object}
     *
     * @param buttonId the button id.
     */
    Emotes(int buttonId) {
        this(buttonId, null, null, null);
    }

    /**
     * Constructs a new {@Code Emotes} {@Code Object}
     *
     * @param buttonId  the button id.
     * @param animation the animation.
     */
    Emotes(int buttonId, Animation animation) {
        this(buttonId, animation, null, null);
    }

    /**
     * Constructs a new {@Code Emotes} {@Code Object}
     *
     * @param buttonId  the button id.
     * @param animation the animation.
     * @param graphic  the graphic.
     */
    Emotes(int buttonId, Animation animation, Graphic graphic) {
        this(buttonId, animation, graphic, null);
    }

    /**
     * Constructs a new {@Code Emotes} {@Code Object}
     *
     * @param buttonId      the button id.
     * @param animation     the animation.
     * @param lockedMessage the locked message.
     */
    Emotes(int buttonId, Animation animation, String lockedMessage) {
        this(buttonId, animation, null, lockedMessage);
    }

    /**
     * Constructs a new {@Code Emotes} {@Code Object}
     *
     * @param buttonId      the button id.
     * @param lockedMessage the locked message.
     */
    Emotes(int buttonId, String lockedMessage) {
        this(buttonId, null, null, lockedMessage);
    }

    /**
     * Handles the reward button for an emote.
     *
     * @param player   the player.
     * @param buttonId the button id.
     */
    public static void handle(Player player, int buttonId) {
        if (player.getLocks().isLocked("emote")) {
            player.getPacketDispatch().sendMessage("You're already doing an emote!");
            return;
        }
        if (player.getProperties().getCombatPulse().isAttacking() || player.inCombat()) {
            player.getPacketDispatch().sendMessage("You can't perform an emote while being in combat.");
            return;
        }
        Emotes emote = Emotes.forId(buttonId);
        if (emote == null) {
            player.debug("Unhandled emote for button id - " + buttonId);
            return;
        }
        if (!player.getEmoteManager().isUnlocked(emote)) {
            if (player.getRights().equals(Rights.ADMINISTRATOR)) {
                player.getEmoteManager().unlock(emote);
                player.getPulseManager().clear();
                emote.play(player);
                return;
            }
            String message = emote.getLockedMessage();
            if (message == null) {
                message = "You can't use this emote.";
            }
            player.getDialogueInterpreter().sendDialogue(message);
            return;
        }
        if (!player.getAchievementDiaryManager().getDiary(DiaryType.VARROCK).isComplete(1, 6) && (buttonId >= 30 && buttonId <= 33)) {
            if (!player.getAttribute("emote-" + buttonId, false)) {
                setAttribute(player, "emote-" + buttonId, true);
            }
            boolean good = true;
            for (int i = 30; i <= 33; i++) {
                if (!player.getAttribute("emote-" + i, false)) {
                    good = false;
                    break;
                }
            }
            player.getAchievementDiaryManager().getDiary(DiaryType.VARROCK).updateTask(player, 1, 6, good);
        }
        player.getPulseManager().clear();
        emote.play(player);
    }

    /**
     * Plays the emote.
     *
     * @param player the player.
     */
    public void play(Player player) {
        forceEmote(player, animation, graphic);
    }

    /**
     * Forces the animation to be played.
     *
     * @param player    the player.
     * @param animation the animation.
     * @param graphic   the graphic.
     */
    private static void forceEmote(Player player, Animation animation, Graphic graphic) {
        if (animation != null) {
            player.getAnimator().animate(animation, graphic);
            player.getLocks().lock("emote", animation.getDuration());
        }
    }

    /**
     * Gets the emote for the button id.
     *
     * @param buttonId the button id.
     * @return the emote type.
     */
    public static Emotes forId(int buttonId) {
        for (Emotes emote : values()) {
            if (emote.getButtonId() == buttonId) {
                return emote;
            }
        }
        return null;
    }

    /**
     * Gets the buttonId.
     *
     * @return the buttonId.
     */
    public int getButtonId() {
        return buttonId;
    }

    /**
     * Gets the animation.
     *
     * @return the animation.
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Gets the graphic.
     *
     * @return the graphic.
     */
    public Graphic getGraphics() {
        return graphic;
    }

    /**
     * Gets the lockedMessage.
     *
     * @return the lockedMessage.
     */
    public String getLockedMessage() {
        return lockedMessage;
    }

}
