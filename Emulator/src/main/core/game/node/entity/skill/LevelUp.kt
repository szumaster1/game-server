package core.game.node.entity.skill

import cfg.consts.Components
import cfg.consts.Graphics
import cfg.consts.Vars
import core.api.*
import core.game.global.Skillcape
import core.game.node.entity.player.Player
import core.game.world.GameWorld
import core.game.world.update.flag.context.Graphic
import core.tools.DARK_RED

/**
 * Represents a leveling up reward.
 * @author Emperor
 */
object LevelUp {

    private val SKILLCAPES = intArrayOf(9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765, 9948, 9789, 12169)
    private val SKILL_ICON = intArrayOf(67108864, 335544320, 134217728, 402653184, 201326592, 469762048, 268435456, 1073741824, 1207959552, 1275068416, 1006632960, 1140850688, 738197504, 939524096, 872415232, 603979776, 536870912, 671088640, 1342177280, 1409286144, 805306368, 1543503872, 1476395008, 1610612736, 1677721600)
    private val FLASH_ICONS = intArrayOf(1, 4, 2, 64, 8, 16, 32, 32768, 131072, 2048, 16384, 65536, 1024, 8192, 4096, 256, 128, 512, 524288, 1048576, 262144, 4194304, 2097152, 8388608, 16777216)
    private val ADVANCE_CONFIGS = intArrayOf(9, 40, 17, 49, 25, 57, 33, 641, 659, 664, 121, 649, 89, 114, 107, 72, 64, 80, 673, 680, 99, 698, 689, 705)
    private val CLIENT_ID = intArrayOf(1, 5, 2, 6, 3, 7, 4, 16, 18, 19, 15, 17, 11, 14, 13, 9, 8, 10, 20, 21, 12, 23, 22, 24, 24)
    private val SKILL_MILESTONES = intArrayOf(1, 50, 75, 100)
    private val COMBAT_MILESTONES = intArrayOf(3, 5, 10, 15, 25, 75, 90, 100, 110, 120, 126, 130, 138)
    private val TOTAL_LEVEL_MILESTONES = intArrayOf(50, 75, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2300)
    private val GRAPHIC = 1630..1637

    /**
     * This function is called when a player levels up in a skill.
     * It performs various actions such as displaying a level up message,
     * playing a jingle, updating milestones, and sending flashing icons.
     *
     * @param player The player who leveled up.
     * @param slot The skill slot that was leveled up.
     * @param amount The amount of levels gained.
     */
    @JvmStatic
    fun levelUp(player: Player, slot: Int, amount: Int) {
        if (!getAttribute(player, "tutorial:complete", false)) {
            return
        }

        // Display fireworks graphic when a level is gained.
        Graphic.send(Graphic(Graphics.FIREWORKS_WHEN_A_LVL_IS_GAINED_199), player.location)

        // Play a jingle based on the skill that was leveled up.
        playJingle(player, getSkillJingle(player, slot))

        // Update milestones for combat and total level.
        handleMilestones(player, slot, amount)

        // Display level up message in the interface.
        sendInterfaceText(player, "<col=00008B>Congratulations, you've just advanced a " + Skills.SKILL_NAME[slot] + " level!", 740, 0)
        sendInterfaceText(player, "Your " + Skills.SKILL_NAME[slot] + " level is now " + player.getSkills().getStaticLevel(slot) + ".", 740, 1)

        // Send level up message to the player.
        sendMessage(player, "You've just advanced a " + Skills.SKILL_NAME[slot] + " level! You have reached level " + player.getSkills()
                .getStaticLevel(slot) + ".")

        // Increment prayer points if prayer skill is leveled up.
        if (slot == Skills.PRAYER) {
            player.getSkills().incrementPrayerPoints(1.0)
        }

        // Check if the skill level is 99 and the player is not artificial.
        if (getStatLevel(player, slot) == 99 && !player.isArtificial) {
            // Send news message for achieving level 99 in a skill.
            sendNews(player.username + " has just achieved level 99 " + Skills.SKILL_NAME[slot] + "!")

            // Trim skillcape and unlock emote for achieving level 99 in all skills.
            Skillcape.trim(player)
            unlockEmote(player, 39)

            // Display random graphic.
            Graphic.send(Graphic(GRAPHIC.random(), 0, 50), player.location)

        }

        // Send news message for achieving skill milestone.
        if (TOTAL_LEVEL_MILESTONES.contains(player.skills.getTotalLevel())) {
            Graphic.send(Graphic(GRAPHIC.random(), 0, 50), player.location)
            sendMessage(player,DARK_RED + "Well done! You've reached the total level ${player.skills.getTotalLevel()} milestone!")
        }

        if (player.skills.getTotalLevel() == 2376) {
            Graphic.send(Graphic(GRAPHIC.random(), 0, 50), player.location)
            // Send news message for achieving maximum total level.
            sendNews(player.username + " has just achieved level 99 in all skills!")
            // Send congratulations message to the player.
            sendMessage(player, DARK_RED + "Congratulations! Well done! You've reached the maximum Total level possible!")
        }

        // Set attribute to save level up state.
        setAttribute(player, "/save:levelup:$slot", true)

        // Send flashing icons to the player.
        sendFlashingIcons(player, slot)
    }

    /**
     * This function handles the milestones for combat and total level.
     * It checks the player's current combat level and total level,
     * and updates the milestones accordingly.
     *
     * @param player The player who leveled up.
     * @param slot The skill slot that was leveled up.
     * @param amount The amount of levels gained.
     */
    private fun handleMilestones(player: Player, slot: Int, amount: Int) {
        var value = ADVANCE_CONFIGS[slot]

        // Check combat milestones.
        for (i in COMBAT_MILESTONES.indices) {
            if (player.properties.getCurrentCombatLevel() < COMBAT_MILESTONES[i]) {
                if (i > player.getSkills().combatMilestone) {
                    value = value or 0x2
                    player.getSkills().combatMilestone = i
                }
                break
            }

            // Check if combat level is 126 on free worlds.
            if (i == 126 && !GameWorld.settings!!.isMembers) {
                Graphic.send(Graphic(GRAPHIC.random(), 0, 50), player.location)
                sendMessage(player, "Congratulations! Your Combat level is now 126 - the highest possible Combat level on free worlds!")
                break
            }

            // Check if combat level is 138.
            if (i == 138) {
                Graphic.send(Graphic(GRAPHIC.random(), 0, 50), player.location)
                sendMessage(player, "Congratulations! Your Combat level is now 138! You've achieved the highest Combat level possible!")
                break
            }
        }

        val totalLevel = player.getSkills().getTotalLevel()

        // Check skill milestones.
        for (i in SKILL_MILESTONES.indices) {
            if (totalLevel < SKILL_MILESTONES[i]) {
                if (i > player.getSkills().skillMilestone) {
                    value = value or 0x4
                    player.getSkills().skillMilestone = i
                }
                break
            }
        }

        // Set combat level and total level milestones.
        value = value or (player.getSkills().combatMilestone shl 23) //Combat level milestone index.
        value = value or (player.getSkills().skillMilestone shl 27) //Total level milestone index.
        setVarp(player, 1230, value)
    }

    /**
     * This function sends flashing icons to the player.
     * It checks if a specific skill slot is provided and sends the corresponding flashing icon.
     *
     * @param player The player who leveled up.
     * @param slot The skill slot that was leveled up.
     */
    @JvmStatic
    fun sendFlashingIcons(player: Player, slot: Int) {
        var value = 0

        // Check if any skill has leveled up.
        for (i in Skills.SKILL_NAME.indices) {
            if (player.getAttribute("levelup:$i", false)) {
                value = value or FLASH_ICONS[i]
            }
        }

        // Check if a specific skill slot is provided.
        if (slot > -1) {
            value = value or SKILL_ICON[slot]
            openChatbox(player, Components.GAME_INTERFACE_740)
        }

        // Set flashing icons varp.
        setVarp(player, Vars.VARP_IFACE_SKILL_FLASH_ICONS, value)
    }
}
