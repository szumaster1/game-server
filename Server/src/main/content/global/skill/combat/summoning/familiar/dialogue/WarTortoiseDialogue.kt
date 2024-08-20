package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the War tortoise dialogue.
 */
@Initializable
class WarTortoiseDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npc(FacialExpression.OLD_NORMAL, "*The tortoise waggles its head about.*","What are we doing in this dump?").also { stage = 0 }
            1 -> npcl(FacialExpression.OLD_NORMAL, "Hold up a minute, there.").also { stage = 4 }
            2 -> npc(FacialExpression.OLD_NORMAL, "*The tortoise bobs its head around energetically.*","Oh, so now you're paying attention to","me, are you?").also { stage = 10 }
            3 -> npc(FacialExpression.OLD_NORMAL, "*The tortoise exudes an air of reproach.*","Are you going to keep rushing","around all day?").also { stage = 16 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Well, I was just going to take care of a few things.").also { stage++ }
            1 -> npc(FacialExpression.OLD_NORMAL, "*The tortoise shakes its head.*","I don't believe it. Stuck here with this young whippersnapper","running around having fun.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "You know, I'm sure you would enjoy it if you gave it a chance.").also { stage++ }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Oh, you would say that, wouldn't you?").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "What do you want?").also { stage++ }
            5 -> npc(FacialExpression.OLD_NORMAL, "*The tortoise bobs its head sadly.*","For you to slow down!").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Well, I've stopped now.").also { stage++ }
            7 -> npcl(FacialExpression.OLD_NORMAL, "Yes, but you'll soon start up again, won't you?").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Probably.").also { stage++ }
            9 -> npc(FacialExpression.OLD_NORMAL, "*The tortoise waggles its head despondently.*","I don't believe it....").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "I pay you plenty of attention!").also { stage++ }
            11 -> npcl(FacialExpression.OLD_NORMAL, "Only when you want me to carry those heavy things of yours.").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "I don't ask you to carry anything heavy.").also { stage++ }
            13 -> npcl(FacialExpression.OLD_NORMAL, "What about those lead ingots?").also { stage++ }
            14 -> playerl(FacialExpression.HALF_ASKING, "What lead ingots?").also { stage++ }
            15 -> npc(FacialExpression.OLD_NORMAL, "*The tortoise droops its head.*","Well, that's what it felt like....","*grumble grumble*").also { stage = END_DIALOGUE }

            16 -> playerl(FacialExpression.FRIENDLY, "Only for as long as I have the energy to.").also { stage++ }
            17 -> npcl(FacialExpression.OLD_NORMAL, "Oh. I'm glad that my not being able to keep up with you brings you such great amusement.").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "I didn't mean it like that.").also { stage++ }
            19 -> npc(FacialExpression.OLD_NORMAL, "*The tortoise waggles its head disapprovingly.*","Well, when you are QUITE finished laughing at my expense,","how about you pick up a rock larger than your body","and go crawling about with it?").also { stage++ }
            20 -> npcl(FacialExpression.OLD_NORMAL, "We'll see how energetic you are after an hour or two of that.").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WAR_TORTOISE_6815, NPCs.WAR_TORTOISE_6816)
    }

}
