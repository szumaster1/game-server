package content.global.skill.support.thieving

import content.global.skill.skillcape.SkillcapePerks
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Sounds
import core.api.utils.WeightBasedTable
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.combat.DeathTask
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.impl.Animator
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

class ThievingListeners : InteractionListener {

    companion object {
        val PICKPOCKET_ANIM = Animation(Animations.HUMAN_PICKPOCKETING_881, Animator.Priority.HIGH)
        val NPC_ANIM = Animation(Animations.PUNCH_422)

        /*
         * Standalone pickpocketing function.
         * For thieving other stuff outside of normal
         * pickpocketing tables.
         */

        fun pickpocketRoll(player: Player, low: Double, high: Double, table: WeightBasedTable): ArrayList<Item>? {
            /*
             * Able to pickpocket
             */
            var successMod = 0.0
            if (SkillcapePerks.isActive(SkillcapePerks.SMOOTH_HANDS, player)) {
                successMod += 25
            }
            if (inInventory(player, Items.GLOVES_OF_SILENCE_10075, 1)) {
                successMod += 3
            }
            val chance = RandomFunction.randomDouble(1.0, 100.0)
            val failThreshold =
                RandomFunction.getSkillSuccessChance(low, high, getStatLevel(player, Skills.THIEVING)) + successMod
            if (chance > failThreshold) {
                /*
                 * Fail Pickpocket.
                 * Returns a null, different from an empty table.
                 */
                return null
            } else {
                /*
                 * Success Pickpocket.
                 * You could also successfully pickpocket nothing when the table returns a blank array.
                 */
                return table.roll()
            }
        }
    }

    override fun defineListeners() {

        on(IntType.NPC, "pickpocket", "pick-pocket") { player, node ->
            val pickpocketData = Pickpockets.forID(node.id) ?: return@on false
            val npc = node.asNpc()
            val npcName = npc.name.lowercase()

            if (player.inCombat()) {
                sendMessage(player, "You can't do this while in combat.")
                return@on true
            }

            if (pickpocketData == null) {
                sendMessage(player, "You cannot pickpocket that NPC.")
                return@on true
            }

            if (getStatLevel(player, Skills.THIEVING) < pickpocketData.requiredLevel) {
                sendMessage(player, "You need a Thieving level of ${pickpocketData.requiredLevel} to do that.")
                return@on true
            }

            if (DeathTask.isDead(npc)) {
                sendMessage(player, "Too late, $npcName is already dead.")
                return@on true
            }

            if (!pickpocketData.table.canRoll(player)) {
                sendMessage(player, "You don't have enough inventory space to do that.")
                return@on true
            }

            animate(player, PICKPOCKET_ANIM)
            sendMessage(player, "You attempt to pick the $npcName pocket.")
            val lootTable = pickpocketRoll(player, pickpocketData.low, pickpocketData.high, pickpocketData.table)

            if (lootTable == null) {
                npc.face(player)
                npc.animator.animate(NPC_ANIM)
                npc.sendChat("What do you think you're doing?")
                sendMessage(player, "You fail to pick the $npcName pocket.")
                sendMessage(player, "${npc.name}: What do you think you're doing?")
                playHurtAudio(player, 20)
                stun(player, pickpocketData.stunTime)
                impact(player, RandomFunction.random(pickpocketData.stunDamageMin, pickpocketData.stunDamageMax), ImpactHandler.HitsplatType.NORMAL)
                sendMessage(player, "You feel slightly concussed from the blow.")
                npc.face(null)
            } else {
                lock(player, 2)
                playAudio(player, Sounds.PICK_2581)
                sendMessage(player, "You pick the $npcName pocket.")
                lootTable.forEach {
                    player.inventory.add(it)
                    sendMessage(player, "You steal some ${getItemName(it.id).lowercase()}.")
                }
                rewardXP(player, Skills.THIEVING, pickpocketData.experience)
            }
            return@on true
        }

    }
}
