package content.minigame.stealingcreation

import core.api.*
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction
import kotlin.math.ceil

/**
 * Creation skill pulse.
 *
 * @property player The player performing the creation skill.
 * @property node The node where the creation skill is being performed.
 * @property animation The animation used for the creation skill.
 * @property itemUsed The item used for the creation skill.
 * @property baseId The base ID for the creation skill.
 * @property sceneryIndex The index of the scenery for the creation skill.
 * @constructor Creates a CreationSkillPulse instance.
 */
class CreationSkillPulse(
    val player: Player,
    val node: Node,
    val animation: Animation,
    var itemUsed: Item,
    val baseId: Int,
    val sceneryIndex: Int
) : Pulse(1, player, node) {

    private lateinit var definitions: CreationScenery
    private val skillId = Skills.HUNTER

    /**
     * Creation scenery.
     *
     * @property baseTime The base time for the creation scenery.
     * @property randomLife The random life for the creation scenery.
     * @property randomTime The random time for the creation scenery.
     * @property level The level required for the creation scenery.
     * @constructor Creates a CreationScenery instance.
     */
    enum class CreationScenery(val baseTime: Int, val randomLife: Int, val randomTime: Int, val level: Int) {
        /**
         * Class 1.
         */
        CLASS_1(10, -1, 1, 1),

        /**
         * Class 2.
         */
        CLASS_2(20, 200, 6, 20),

        /**
         * Class 3.
         */
        CLASS_3(25, 300, 12, 40),

        /**
         * Class 4.
         */
        CLASS_4(30, 400, 16, 60),

        /**
         * Class 5.
         */
        CLASS_5(35, 500, 20, 80);
    }


    /**
     * Animate the player
     */
    fun animate() {
        animate(player, animation)
    }

    override fun setDelay(delay: Int) {
        if (freeSlots(player) == 0) {
            sendMessage(player, "Not enough space in your inventory!")
            return
        } else if (RandomFunction.getRandom(definitions.randomLife) == 0) {

        }
        addItem(player, StealingCreation.sacredClayItem[sceneryIndex], 1)
        return
    }

    /**
     * Check the reward for the player.
     *
     * @param player The player to check the reward for.
     * @param skillId The skill ID to check the reward for.
     * @return The delay for the reward.
     */
    fun checkReward(player: Player, skillId: Int): Int {
        val playerLevel = player.getSkills().getLevel(Skills.FISHING)
        val fishLevel = definitions.level
        val modifier: Int = skillId
        val randomAmt: Int = RandomFunction.random(4)
        var cycleCount = 1.0
        val otherBonus = 0.0
        cycleCount = ceil(((fishLevel + otherBonus) * 50 - playerLevel * 10) / modifier * 0.25 - randomAmt * 4)
        if (cycleCount < 1) cycleCount = 1.0
        var delay = cycleCount.toInt() + 1
        delay /= delay
        return delay
    }

    override fun pulse(): Boolean {
        definitions = CreationScenery.CLASS_5
        return !(getStatLevel(player, StealingCreation.getScenery()) < definitions.level || itemUsed == null)
    }


    override fun stop() {
        super.stop()
        resetAnimator(player)
    }
}
