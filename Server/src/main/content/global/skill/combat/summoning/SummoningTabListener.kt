package content.global.skill.combat.summoning

import content.global.skill.combat.summoning.familiar.BurdenBeast
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.combat.summoning.pet.Pet
import core.api.consts.Components
import core.api.sendMessage
import core.game.interaction.InterfaceListener

class SummoningTabListener : InterfaceListener {

    override fun defineInterfaceListeners() {

        on(Components.LORE_STATS_SIDE_662) { player, _, opcode, buttonID, _, _ ->
            when (buttonID) {
                51 -> {
                    if (player.familiarManager.hasFamiliar()) {
                        player.familiarManager.familiar.call()
                    } else {
                        sendMessage(player, "You don't have a follower.")
                    }
                }

                67 -> {
                    if (player.familiarManager.hasFamiliar()) {
                        if (player.familiarManager.familiar.isInvisible || !player.familiarManager.familiar.location.withinDistance(
                                player.location
                            )
                        ) {
                            sendMessage(player, "Your familiar is too far away!")
                            return@on true
                        }
                        if (!player.familiarManager.familiar.isBurdenBeast) {
                            sendMessage(player, "Your familiar is not a beast of burden.")
                            return@on true
                        }
                        val beast = player.familiarManager.familiar as BurdenBeast
                        if (beast.container.isEmpty()) {
                            sendMessage(player, "Your familiar is not carrying any items.")
                            return@on true
                        }
                        beast.withdrawAll()
                        return@on true
                    }
                    sendMessage(player, "You don't have a follower.")
                }

                53 -> {
                    if (player.familiarManager.hasFamiliar()) {
                        if (opcode == 155) {
                            /*
                             * Dismiss familiar.
                             */
                            player.dialogueInterpreter.open("dismiss_dial")
                        } else if (opcode == 196) {
                            /*
                             * Dismiss now.
                             */
                            if (player.familiarManager.familiar is Pet) {
                                val pet = player.familiarManager.familiar as Pet
                                player.familiarManager.removeDetails(pet.getItemIdHash())
                            }
                            player.familiarManager.dismiss()
                        }
                    } else {
                        sendMessage(player, "You don't have a follower.")
                    }
                }

                else -> {
                    if (player.familiarManager.hasFamiliar()) {
                        player.familiarManager.familiar.executeSpecialMove(FamiliarSpecial(player))
                    } else {
                        sendMessage(player, "You don't have a follower.")
                    }
                }
            }
            return@on true
        }
    }
}