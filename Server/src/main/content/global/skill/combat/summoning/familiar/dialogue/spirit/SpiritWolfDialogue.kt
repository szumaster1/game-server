package content.global.skill.combat.summoning.familiar.dialogue.spirit

import content.global.skill.combat.prayer.Bones
import core.api.anyInInventory
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class SpiritWolfDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if(anyInInventory(player, *bones)){
            npc(FacialExpression.CHILD_NORMAL, "Whuff-Whuff! Arf!","(Throw the bone!I want to chase it!)").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npc(FacialExpression.CHILD_NORMAL, "Whurf?","(What are you doing?)").also { stage = 1 }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Bark Bark!","(Danger!)").also { stage = 2 }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Whuff whuff. Pantpant awff!","(I smell something good! Hunting time!)").also { stage = 4 }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Pant pant whine?","(When am I going to get to chase something?)").also { stage = 5 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "I can't just throw bones away-I need them to train my Prayer!").also { stage = END_DIALOGUE }

            1 -> playerl(FacialExpression.FRIENDLY, "Oh, just some... biped things. I'm sure it would bore you.").also { stage = END_DIALOGUE }

            2 -> playerl(FacialExpression.FRIENDLY, "Where?!").also { stage++ }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Whiiiine...","(False alarm...)").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "We can go hunting in a moment. I just have to take care of something first.").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "Oh I'm sure we'll fine something for you in a bit.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_WOLF_6829, NPCs.SPIRIT_WOLF_6830)
    }

    companion object {
        private val bones = Bones.values().map { it.itemId }.toIntArray()
    }

}
