package content.region.kandarin.quest.arthur.dialogue

import content.region.kandarin.quest.arthur.handlers.MerlinUtils
import core.api.*
import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.Location
import core.tools.END_DIALOGUE

/**
 * Represents the Merlin dialogue file.
 *
 * Related to [Merlin Crystal][content.region.kandarin.quest.arthur.MerlinCrystal] quest.
 * @author lostmyphat
 */
class MerlinDialogueFile(val forced: Boolean) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MERLIN_249)

        when (stage) {
            0 -> {
                if (forced) {
                    setQuestStage(player!!, "Merlin's Crystal", 60)
                    removeCrystal()
                    spawnMerlin()
                    npcl(FacialExpression.HAPPY, "Thank you! Thank you! Thank you!")
                    stage++
                } else {
                    npcl(FacialExpression.NEUTRAL, "Excuse me for rushing off like this, but I must get back to my workroom.").also { stage = END_DIALOGUE }
                }
            }
            1 -> {
                npcl(FacialExpression.HAPPY, "It's not fun being trapped in a giant crystal!")
                stage++
            }
            2 -> {
                npcl(FacialExpression.HAPPY, "Go speak to King Arthur, I'm sure he'll reward you!")
                stage++
            }
            3 -> {
                sendDialogue(player!!,"You have set Merlin free. Now talk to King Arthur.")
                stage++
            }
            4 -> {
                val merlin = player!!.getAttribute<NPC>(MerlinUtils.TEMP_ATTR_MERLIN, null)
                merlin?.clear()

                end()
                stage = END_DIALOGUE
            }

        }
    }

    private fun removeCrystal() {
        val crystal = getScenery(Location.create(2767, 3493, 2))

        if (crystal != null) {
            SceneryBuilder.remove(crystal)
        }
    }

    private fun spawnMerlin() {
        val merlin = NPC.create(NPCs.MERLIN_249, Location.create(2768, 3493, 2))
        merlin.moveStep()
        merlin.init()
        setAttribute(player!!, MerlinUtils.TEMP_ATTR_MERLIN, merlin)

        queueScript(merlin, 100, QueueStrength.SOFT) { _ ->

            if (merlin != null && merlin.isActive) {
                merlin.clear()
            }

            spawnCrystal()
            removeAttribute(player!!, MerlinUtils.TEMP_ATTR_MERLIN)
            return@queueScript stopExecuting(merlin)
        }
    }

    private fun spawnCrystal() {
        val crystal = getScenery(Location.create(2767, 3493, 2))

        if (crystal == null) {
            SceneryBuilder.add(Scenery(cfg.consts.Scenery.GIANT_CRYSTAL_62, Location.create(2767, 3493, 2)))
        }
    }

}
