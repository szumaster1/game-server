package content.region.asgarnia.entrana.dialogue

import cfg.consts.NPCs
import core.api.getStatLevel
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Cave Monk dialogue.
 */
@Initializable
class CaveMonkDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        quest = player.getQuestRepository().getQuest("Lost City")
        when (quest!!.getStage(player)) {
            0, 10 -> {
                player("Hello, what are you doing here?")
                stage = 100
            }

            else -> {
                npc(FacialExpression.HALF_GUILTY, "Be careful going in there! You are unarmed, and there", "is much evilness lurking down there! The evilness seems", "to block off our contact with our gods,")
                stage = 0
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            100 -> npc("None of your business.").also { stage = END_DIALOGUE }
            0 -> npc(FacialExpression.HALF_GUILTY, "so our prayers seem to have less effect down there. Oh,", "also, you won't be able to come back this way - This", "ladder only goes one way!").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "The only exit from the caves below is a portal which", "leads only to the deepest wilderness!").also { stage++ }
            2 -> options("I don't think I'm strong enough to enter then.", "Well that is a risk I will have to take.").also { stage++ }
            3 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "I don't think I'm strong enough to enter then.").also { stage = END_DIALOGUE }
                2 -> player(FacialExpression.HALF_GUILTY, "Well that is a risk I will have to take.").also { stage = 20 }
            }
            20 -> {
                if (getStatLevel(player, Skills.PRAYER) > 2 && player.getSkills().prayerPoints > 2) {
                    player.getSkills().decrementPrayerPoints((player.getSkills().getLevel(Skills.PRAYER) - 2).toDouble())
                }
                player.properties.teleportLocation = DUNGEON
                end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CAVE_MONK_656)
    }

    companion object {
        private val DUNGEON: Location = Location.create(2822, 9774, 0)
    }
}
