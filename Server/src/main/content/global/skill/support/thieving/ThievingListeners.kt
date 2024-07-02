package content.global.skill.support.thieving

import content.global.skill.skillcape.SkillcapePerks
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Sounds
import core.api.utils.WeightBasedTable
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.Entity
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.impl.Animator
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.appearance.Gender
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.utilities.RandomFunction

class ThievingListeners : InteractionListener {

    companion object {
        val PICKPOCKET_ANIM = Animation(881, Animator.Priority.HIGH)
        val NPC_ANIM = Animation(422)
        fun updateGender(player: Entity, male: Boolean): Boolean {
            setAttribute(player, "mm-gender", if (male) Gender.MALE.ordinal else Gender.FEMALE.ordinal)
            return true;
        }
        /*
         * Standalone pickpocketing function. For thieving other stuff outside of normal pickpocketing tables.
         */
        fun pickpocketRoll(player: Player, low: Double, high: Double, table: WeightBasedTable): ArrayList<Item>? {
            // Able to pickpocket
            var successMod = 0.0
            if (SkillcapePerks.isActive(SkillcapePerks.SMOOTH_HANDS, player)) {
                successMod += 25
            }
            if (player.equipment.contains(Items.GLOVES_OF_SILENCE_10075, 1)) {
                successMod += 3
            }

            val chance = RandomFunction.randomDouble(1.0, 100.0)
            val failThreshold =
                RandomFunction.getSkillSuccessChance(low, high, player.skills.getLevel(Skills.THIEVING)) + successMod

            if (chance > failThreshold) {
                // Fail Pickpocket
                return null // Returns a null, different from an empty table.
            } else {
                // Success Pickpocket
                return table.roll() // You could also successfully pickpocket nothing when the table returns a blank array.
            }
        }
    }

    override fun defineListeners() {

        on(IntType.NPC, "pickpocket", "pick-pocket") { player, node ->
            val pickpocketData = Pickpockets.forID(node.id) ?: return@on false

            if (player.inCombat()) {
                sendMessage(player, "You can't pickpocket while in combat.")
                return@on true
            }

            if (getStatLevel(player, Skills.THIEVING) < pickpocketData.requiredLevel) {
                sendMessage(player, "You need a Thieving level of ${pickpocketData.requiredLevel} to do that.")
                return@on true
            }

            if (!pickpocketData.table.canRoll(player)) {
                sendMessage(player, "You don't have enough inventory space to do that.")
                return@on true
            }
            sendMessage(player, "You attempt to pick the ${ if(updateGender(node.asNpc(), false)) "woman's" else "man's" } pocket.")
            animate(player, PICKPOCKET_ANIM, true)
            val lootTable = pickpocketRoll(player, pickpocketData.low, pickpocketData.high, pickpocketData.table)
            if (lootTable == null) {
                sendMessage(player, "You fail to pick the ${ if(updateGender(node.asNpc(), false)) "woman's" else "man's" } pocket.")
                sendMessage(player, "${node.name}: What do you think you're doing?")
                sendChat(node.asNpc(), "What do you think you're doing?")
                face(node.asNpc(), player)
                animate(node.asNpc(), NPC_ANIM, true)
                queueScript(player, 2, QueueStrength.SOFT) {
                    animate(player, Animations.DEFEND_378)
                    playHurtAudio(player, 20)
                    stun(player, pickpocketData.stunTime)
                    impact(
                        node.asNpc(),
                        RandomFunction.random(pickpocketData.stunDamageMin, pickpocketData.stunDamageMax),
                        ImpactHandler.HitsplatType.NORMAL
                    )
                    sendMessage(player, "You feel slightly concussed from the blow.")
                    return@queueScript stopExecuting(player)
                }
                node.asNpc().face(null)
            } else {
                lock(player, 2)
                queueScript(player, 2, QueueStrength.SOFT) {
                    playAudio(player, Sounds.PICK_2581)
                    sendMessage(player, "You pick the ${if(updateGender(node.asNpc(), false)) "woman's" else "man's" } pocket.")
                    lootTable.forEach {
                        player.inventory.add(it)
                        sendMessage(player, "You steal some ${getItemName(it.id).lowercase()}.")
                    }
                    player.skills.addExperience(Skills.THIEVING, pickpocketData.experience)
                    return@queueScript stopExecuting(player)
                }
            }

            return@on true
        }

    }
}
