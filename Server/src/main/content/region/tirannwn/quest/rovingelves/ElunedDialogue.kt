package content.region.tirannwn.quest.rovingelves

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.sendItemDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents the Eluned dialogue.
 */
class ElunedDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        val quest = player.getQuestRepository().getQuest("Roving Elves")
        stage = if (quest.getStage(player) == 10) {
            player(FacialExpression.HALF_GUILTY, "Hey there... Islwyn said you may be able to help me.", "He told me you know how to consecrate ground for an", "elven burial. I need to reconsecrate Glarial's resting", "place.")
            1
        } else {
            player(FacialExpression.HALF_GUILTY, "Good day.")
            1000
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest("Roving Elves")
        when (stage) {
            500 -> end()
            1000 -> {
                if (quest.getStage(player) == 15 && !player.inventory.contains(RovingElves.CONSECRATION_SEED_CHARGED.id, 1)) {
                    interpreter.sendDialogues(
                        1679,
                        FacialExpression.HALF_GUILTY,
                        "Hello, any luck finding the consecration seed?"
                    )
                    stage = 1002
                }
                if (quest.getStage(player) == 15 && player.inventory.contains(RovingElves.CONSECRATION_SEED_CHARGED.id, 1)) {
                    interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "You still have the charged seed with you, I can feel it.", "Hurry adventurer, go plant the seed and free", "my grandmother's spirit.")
                    stage = 500
                }
                if (quest.getStage(player) == 15 && player.inventory.contains(RovingElves.CONSECRATION_SEED.id, 1)) {
                    interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "Hello, any luck finding the consecration seed?")
                    stage = 1002
                }
                if (quest.getStage(player) == 20) {
                    interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "Hey... how's it going? Have you managed to", "reconsecrate Glarial's resting place?")
                    stage = 12
                } else if (quest.getStage(player) != 15) {
                    interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "Hello there, it's a lovely day for a walk in the woods.", "So what can I help you with?")
                    stage = 1001
                }
            }

            1001 -> stage = if (quest.getStage(player) >= 100 && player.inventory.contains(Items.TINY_ELF_CRYSTAL_6103, 1)) {
                player(FacialExpression.HALF_GUILTY, "I am looking to recharge teleportation crystals.")
                1200
            } else {
                player(FacialExpression.HALF_GUILTY, "I'm just looking around.")
                500
            }

            1002 -> stage = if (player.inventory.contains(RovingElves.CONSECRATION_SEED.id, 1)) {
                player(FacialExpression.HALF_GUILTY, "Yes, I have it here.")
                6
            } else {
                player(FacialExpression.HALF_GUILTY, "I forgot what you told me to do.")
                1003
            }

            1003 -> {
                interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "Re-enter Glarial's tomb and defeat the tomb's guardian.", "Take the consecration seed it is guarding and", "bring it back to me.")
                stage = 500
            }

            1200 -> {
                val timesRecharged = player.getAttribute("rovingelves:crystal-teleport-recharges", 0)
                val price = crystalTeleportPrice(timesRecharged)
                interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "Very well. I'll recharge your teleportation", String.format("crystal for %d gold. What do you say?", price))
                stage = 1202
            }

            1202 -> {
                options( "Recharge a crystal", "Nevermind")
                stage = 1203
            }

            1203 -> when (buttonId) {
                1 -> {
                    stage = 1204
                    val timesRecharged = player.getAttribute<Int>("rovingelves:crystal-teleport-recharges", 0)
                    var price = crystalTeleportPrice(timesRecharged)
                    if (!player.inventory.contains(995, price)) {
                        player(FacialExpression.HALF_GUILTY, "Actually, I don't have enough coins.")
                    } else {
                        interpreter.sendDialogue(
                            String.format("Eluned recharges your elven teleportation crystal for %d gold.", price)
                        )
                        if (player.inventory.remove(Item(Items.TINY_ELF_CRYSTAL_6103)) && player.inventory.remove(Item(995, price))) {
                            player.inventory.add(Item(Items.TELEPORT_CRYSTAL_4_6099, 1))
                            player.incrementAttribute("/save:rovingelves:crystal-teleport-recharges", 1)
                        }
                    }
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Nevermind, I really must be going.")
                    stage = 1204
                }
            }

            1204 -> end()
            1 -> {
                interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "That I do... It is elvish tradition to plant a specially", "enchanted crystal seed at the graves of our ancestors.", "The seed will create guardians to protect the area.")
                stage = 2
            }

            2 -> {
                interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "Unfortunately the crystal seed must be tuned to the", "person it's protecting... a new seed won't do. But you", "should be able to recover the seed from her old tomb.")
                stage = 3
            }

            3 -> {
                interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "The tomb's guardian will be protecting the seed, you'll", "need to defeat him to get it. Once you have it, return", "here and I will re-enchant it.")
                stage = 4
            }

            4 -> {
                player(FacialExpression.HALF_GUILTY, "OK... I'll be back as soon as I have it.")
                stage = 5
            }

            5 -> {
                quest.setStage(player, 15)
                end()
            }

            6 -> {
                sendItemDialogue(player, RovingElves.CONSECRATION_SEED.id, "You hand the crystal seed to Eluned.")
                stage = 7
            }

            7 -> {
                interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "")
                stage = 8
            }

            8 -> {
                player(FacialExpression.HALF_GUILTY, "How odd. I can see her lips moving... But there's no", "sound.")
                stage = 9
            }

            9 -> {
                if (player.inventory.remove(RovingElves.CONSECRATION_SEED)) {
                    player.inventory.add(RovingElves.CONSECRATION_SEED_CHARGED)
                }
                sendItemDialogue(player, RovingElves.CONSECRATION_SEED_CHARGED.id, "Eluned hands you an enchanted crystal seed.")
                stage = 10
            }

            10 -> {
                interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "Plant this seed in Glarial's new tomb, close to her", "remains and she will rest in peace.")
                stage = 11
            }

            11 -> {
                player(FacialExpression.HALF_GUILTY, "OK.")
                stage = 500
            }

            12 -> {
                player(FacialExpression.HALF_GUILTY, "Yes, it's done.")
                stage = 13
            }

            13 -> {
                interpreter.sendDialogues(1679, FacialExpression.HALF_GUILTY, "Well done... You should go see Islwyn, but I'd guess he", "already knows.")
                stage = 500
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ELUNED_1679)
    }

    /**
     * Crystal teleport price
     *
     * @param timesRecharged
     * @return
     */
    fun crystalTeleportPrice(timesRecharged: Int): Int {
        return Math.max(750 - 150 * timesRecharged, 150)
    }
}