package content.region.asgarnia.dialogue.portsarim

import content.global.travel.ship.Ships
import core.api.consts.NPCs
import core.api.sendDialogue
import core.cache.def.impl.ItemDefinition
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.plugin.Initializable

@Initializable
class MonkOfEntranaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (npc.id == 2730 || npc.id == 658 || npc.id == 2731) {
            npc("Do you wish to leave holy Entrana?")
            stage = 500
            return true
        }
        npc(FacialExpression.HALF_GUILTY, "Do you seek passage to holy Entrana? If so, you must", "leave your weaponry and armour behind. This is", "Saradomin's will.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options( "No, not right now.", "Yes, okay, I'm ready to go.")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player("No, not right now.")
                    stage = 10
                }

                2 -> {
                    player("Yes, okay, I'm ready to go.")
                    stage = 20
                }
            }

            10 -> {
                npc("Very well.")
                stage = 11
            }

            11 -> end()
            20 -> {
                npc("Very well. One moment please.")
                stage = 21
            }

            21 -> {
                sendDialogue(player!!, "The monk quickly searches you.")
                stage = 22
            }

            22 -> {
                if (!ItemDefinition.canEnterEntrana(player)) {
                    npc("NO WEAPONS OR ARMOUR are permitted on holy", "Entrana AT ALL. We will not allow you to travel there", "in breach of mighty Saradomin's edict.")
                    stage = 23
                    return true
                }
                npc("All is satisfactory. You may board the boat now.")
                stage = 25
            }

            23 -> {
                npc("Do not try and decieve us again. Come back when you",
                    "have liad down your Zamorakian instruments of death."
                )
                stage = 24
            }

            24 -> end()
            25 -> {
                end()
                Ships.PORT_SARIM_TO_ENTRANA.sail(player)
                if (!player.achievementDiaryManager.getDiary(DiaryType.FALADOR).isComplete(0, 14)) {
                    player.achievementDiaryManager.getDiary(DiaryType.FALADOR).updateTask(player, 0, 14, true)
                }
            }

            500 -> {
                options( "Yes, I'm ready to go.", "Not just yet.")
                stage = 501
            }

            501 -> when (buttonId) {
                1 -> {
                    player("Yes, I'm ready to go.")
                    stage = 510
                }

                2 -> {
                    player("Not just yet.")
                    stage = 520
                }
            }

            510 -> {
                npc("Okay, let's board...")
                stage = 511
            }

            511 -> {
                end()
                Ships.ENTRANA_TO_PORT_SARIM.sail(player)
            }

            520 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.MONK_OF_ENTRANA_657,
            NPCs.MONK_OF_ENTRANA_658,
            NPCs.MONK_OF_ENTRANA_2728,
            NPCs.MONK_OF_ENTRANA_2729,
            NPCs.MONK_OF_ENTRANA_2730,
            NPCs.MONK_OF_ENTRANA_2731,
        )
    }
}
