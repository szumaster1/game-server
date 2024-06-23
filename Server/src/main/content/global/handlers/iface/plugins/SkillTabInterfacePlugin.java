package content.global.handlers.iface.plugins;

import core.game.component.Component;
import core.game.component.ComponentDefinition;
import core.game.component.ComponentPlugin;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.LevelUp;
import core.game.node.entity.skill.Skills;
import core.game.world.GameWorld;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.setVarp;

/**
 * The Skill tab interface plugin.
 */
@Initializable
public final class SkillTabInterfacePlugin extends ComponentPlugin {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ComponentDefinition.put(320, this);
        return this;
    }

    @Override
    public boolean handle(Player p, Component component, int opcode, int button, int slot, int itemId) {
        final SkillConfig config = SkillConfig.forId(button);
        if (config == null) {
            return true;
        }
        if (!GameWorld.getSettings().isPvp()) {
            if (p.getAttribute("levelup:" + config.getSkillId(), false)) {
                p.removeAttribute("levelup:" + config.getSkillId());
                LevelUp.sendFlashingIcons(p, -1);
                setVarp(p, 1230, ADVANCE_CONFIGS[config.getSkillId()]);
                p.getInterfaceManager().open(new Component(741));
            } else {
                p.getPulseManager().clear();
                p.getInterfaceManager().open(new Component(499));
                setVarp(p, 965, config.getConfig());
                p.getAttributes().put("skillMenu", config.getConfig());
            }
        } else {
            if (config.getSkillId() > 6) {
                p.getPacketDispatch().sendMessage("You cannot set a target level for this skill.");
                return false;
            }
            if (p.canSpawn()) {
                p.sendMessage("You must be inside Edgeville bank to set levels.");
                return false;
            }
        }
        return true;
    }

    /**
     * The constant ADVANCE_CONFIGS.
     */
    public static final int[] ADVANCE_CONFIGS = {9, 40, 17, 49, 25, 57, 33, 641, 659, 664, 121, 649, 89, 114, 107, 72, 64, 80, 673, 680, 99, 698, 689, 705,};

    /**
     * The enum Skill config.
     */
    public enum SkillConfig {
        /**
         * Attack skill config.
         */
        ATTACK(125, 1, Skills.ATTACK),
        /**
         * Strength skill config.
         */
        STRENGTH(126, 2, Skills.STRENGTH),
        /**
         * Defence skill config.
         */
        DEFENCE(127, 5, Skills.DEFENCE),
        /**
         * Range skill config.
         */
        RANGE(128, 3, Skills.RANGE),
        /**
         * Prayer skill config.
         */
        PRAYER(129, 7, Skills.PRAYER),
        /**
         * Magic skill config.
         */
        MAGIC(130, 4, Skills.MAGIC),
        /**
         * Runecrafting skill config.
         */
        RUNECRAFTING(131, 12, Skills.RUNECRAFTING),
        /**
         * Hitpoints skill config.
         */
        HITPOINTS(133, 6, Skills.HITPOINTS),
        /**
         * Agility skill config.
         */
        AGILITY(134, 8, Skills.AGILITY),
        /**
         * Herblore skill config.
         */
        HERBLORE(135, 9, Skills.HERBLORE),
        /**
         * Thieving skill config.
         */
        THIEVING(136, 10, Skills.THIEVING),
        /**
         * Crafting skill config.
         */
        CRAFTING(137, 11, Skills.CRAFTING),
        /**
         * Fletching skill config.
         */
        FLETCHING(138, 19, Skills.FLETCHING),
        /**
         * Slayer skill config.
         */
        SLAYER(139, 20, Skills.SLAYER),
        /**
         * Mining skill config.
         */
        MINING(141, 13, Skills.MINING),
        /**
         * Smithing skill config.
         */
        SMITHING(142, 14, Skills.SMITHING),
        /**
         * Fishing skill config.
         */
        FISHING(143, 15, Skills.FISHING),
        /**
         * Cooking skill config.
         */
        COOKING(144, 16, Skills.COOKING),
        /**
         * Firemaking skill config.
         */
        FIREMAKING(145, 17, Skills.FIREMAKING),
        /**
         * Woodcutting skill config.
         */
        WOODCUTTING(146, 18, Skills.WOODCUTTING),
        /**
         * Farming skill config.
         */
        FARMING(147, 21, Skills.FARMING),
        /**
         * Construction skill config.
         */
        CONSTRUCTION(132, 22, Skills.CONSTRUCTION),
        /**
         * Hunter skill config.
         */
        HUNTER(140, 23, Skills.HUNTER),
        /**
         * Summoning skill config.
         */
        SUMMONING(148, 24, Skills.SUMMONING);


        SkillConfig(int button, int config, int skillId) {
            this.button = button;
            this.config = config;
            this.skillId = skillId;
        }


        private int button;


        private int config;


        private int skillId;


        /**
         * For id skill config.
         *
         * @param id the id
         * @return the skill config
         */
        public static SkillConfig forId(int id) {
            for (SkillConfig config : SkillConfig.values()) {
                if (config.button == id) return config;
            }
            return null;
        }


        /**
         * Gets button.
         *
         * @return the button
         */
        public int getButton() {
            return button;
        }


        /**
         * Gets config.
         *
         * @return the config
         */
        public int getConfig() {
            return config;
        }


        /**
         * Gets skill id.
         *
         * @return the skill id
         */
        public int getSkillId() {
            return skillId;
        }
    }
}
