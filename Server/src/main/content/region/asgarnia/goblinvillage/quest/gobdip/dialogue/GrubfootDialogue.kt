package content.region.asgarnia.goblinvillage.quest.gobdip.dialogue

import content.region.asgarnia.goblinvillage.quest.gobdip.GoblinDiplomacy
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest

/**
 * Represents the Grubfoot dialogue.
 */
class GrubfootDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        quest = player.getQuestRepository().getQuest(GoblinDiplomacy.NAME)
        when (quest!!.getStage(player)) {
            100 -> {
                npc(FacialExpression.OLD_NORMAL, "Me lonely.")
                stage = 0
            }

            30 -> {
                npc(FacialExpression.OLD_NORMAL, "Me not like this blue colour.")
                stage = 0
            }

            20 -> {
                npc(FacialExpression.OLD_NORMAL, "Me not like this orange armour. Make me look like that", "thing.")
                stage = 0
            }

            else -> {
                npc(FacialExpression.OLD_NORMAL, "Grubfoot wear red armour! Grubfoot wear green", "armour!")
                stage = 0
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (quest!!.getStage(player)) {
            100 -> when (stage) {
                0 -> {
                    player("Why?")
                    stage = 1
                }

                1 -> {
                    npc(
                        FacialExpression.OLD_NORMAL,
                        "Other goblins in village follow either General Wartface",
                        "or General Bentnoze. Me try to follow both but then",
                        "me get left out of both groups."
                    )
                    stage = 2
                }

                2 -> {
                    interpreter.sendDialogues(4493, FacialExpression.OLD_NORMAL, "Shut up Grubfoot!")
                    stage = 3
                }

                3 -> end()
            }

            30 -> when (stage) {
                0 -> {
                    player("Why not?")
                    stage = 1
                }

                1 -> {
                    npc(FacialExpression.OLD_NORMAL, "Me not know. It just make me feel...")
                    stage = 2
                }

                2 -> {
                    player("Makes you feel blue?")
                    stage = 3
                }

                3 -> {
                    npc(FacialExpression.OLD_NORMAL, "Makes me feel kinda of sad.")
                    stage = 4
                }

                4 -> {
                    interpreter.sendDialogues(4493, FacialExpression.OLD_NORMAL, "Shut up Grubfoot!")
                    stage = 5
                }

                5 -> end()
            }

            20 -> when (stage) {
                0 -> {
                    player("Look like what thing?")
                    stage = 1
                }

                1 -> {
                    npc(FacialExpression.OLD_NORMAL, "That fruit thing. The one that orange. What it called?")
                    stage = 2
                }

                2 -> {
                    player("An orange?")
                    stage = 3
                }

                3 -> {
                    npc(
                        FacialExpression.OLD_NORMAL,
                        "That right. This armour make me look same colour as",
                        "orange-fruit."
                    )
                    stage = 4
                }

                4 -> {
                    interpreter.sendDialogues(4493, FacialExpression.OLD_NORMAL, "Shut up Grubfoot!")
                    stage = 5
                }

                5 -> end()
            }

            else -> when (stage) {
                0 -> {
                    npc(FacialExpression.OLD_NORMAL, "Why they not make up their minds?")
                    stage = 1
                }

                1 -> {
                    interpreter.sendDialogues(4493, FacialExpression.OLD_NORMAL, "Shut up Grubfoot!")
                    stage = 2
                }

                2 -> end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GENERAL_WARTFACE_4495, NPCs.GRUBFOOT_4497, NPCs.GRUBFOOT_4498, NPCs.GRUBFOOT_4496)
    }
}