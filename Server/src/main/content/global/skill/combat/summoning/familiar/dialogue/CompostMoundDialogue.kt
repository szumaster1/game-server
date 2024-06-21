package content.global.skill.combat.summoning.familiar.dialogue

import content.global.skill.combat.summoning.familiar.Forager
import core.api.consts.NPCs
import core.api.getDynLevel
import core.api.getStatLevel
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class CompostMoundDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        options("Chat", "Withdraw", "Farming boost").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> when ((0..5).random()) {
                    0 -> npc(FacialExpression.CHILD_NORMAL, "Schlorp, splort, splort, splutter shclorp?", "(What we be doin' 'ere, zur?)").also { stage = 124 }
                    1 -> npcl(FacialExpression.CHILD_NORMAL, "Oi've gotta braand new comboine 'aarvester!").also { stage = 100 }
                    2 -> npcl(FacialExpression.CHILD_NORMAL, "What we be doin' 'ere, zur?").also { stage = 104 }
                    3 -> npcl(FacialExpression.CHILD_NORMAL, "Errr...are ye gonna eat that?").also { stage = 106 }
                    4 -> npcl(FacialExpression.CHILD_NORMAL, "Sigh...").also { stage = 113 }
                    5 -> npcl(FacialExpression.CHILD_NORMAL, "Oi wus just a-wonderin'...").also { stage = 117 }
                }

                2 -> {
                    end()
                    val forager = player.familiarManager.familiar as Forager
                    forager.openInterface()
                }

                3 -> player("Can you boost my Farming stat, please?").also { stage = 30 }

            }

            30 -> player("Can you boost my Farming stat, please?").also { stage++ }
            31 -> npc("Schlup glorp sputter!", "(Oi do believe oi can!)").also { stage++ }
            32 -> {
                if (getDynLevel(player, Skills.FARMING) > getStatLevel(player, Skills.FARMING)) {
                    end()
                    sendMessage(player, "Your stat cannot be boosted this way right now.")
                    return true
                }
                player.getSkills().updateLevel(Skills.FARMING, (1 + (getStatLevel(player, Skills.FARMING) * 0.02)).toInt())
                sendMessage(player, "The Compost mound has boosted your Farming stat.")
                end()
            }

            100 -> playerl(FacialExpression.HALF_ASKING, "A what?").also { stage++ }
            101 -> npcl(FacialExpression.CHILD_NORMAL, "Well, it's a flat bit a metal wi' a 'andle that I can use ta 'aarvest all combintions o' plaants.").also { stage++ }
            102 -> playerl(FacialExpression.HALF_ASKING, "You mean a spade?").also { stage++ }
            103 -> npcl(FacialExpression.CHILD_NORMAL, "Aye, 'aat'll be it.").also { stage = END_DIALOGUE }

            104 -> playerl(FacialExpression.FRIENDLY, "Oh, I have a few things to take care of here, is all.").also { stage++ }
            105 -> npcl(FacialExpression.CHILD_NORMAL, "Aye, right ye are, zur. Oi'll be roight there.").also { stage = END_DIALOGUE }

            106 -> playerl(FacialExpression.HALF_ASKING, "Eat what?").also { stage++ }
            107 -> npcl(FacialExpression.CHILD_NORMAL, "Y've got summat on yer, goin' wastin'.").also { stage++ }
            108 -> playerl(FacialExpression.DISGUSTED, "Ewwww!").also { stage++ }
            109 -> npcl(FacialExpression.CHILD_NORMAL, "So ye don' want it then?").also { stage++ }
            110 -> playerl(FacialExpression.FRIENDLY, "No I do not want it! Nor do I want to put my boot in your mouth for you to clean it off.").also { stage++ }
            111 -> npcl(FacialExpression.CHILD_NORMAL, "An' why not?").also { stage++ }
            112 -> playerl(FacialExpression.FRIENDLY, "It'll likely come out dirtier than when I put it in!").also { stage = END_DIALOGUE }

            113 -> playerl(FacialExpression.HALF_ASKING, "What's the matter?").also { stage++ }
            114 -> npcl(FacialExpression.CHILD_NORMAL, "Oi'm not 'appy carryin' round these young'uns where we're going.").also { stage++ }
            115 -> playerl(FacialExpression.HALF_ASKING, "Young'uns? Oh, the buckets of compost! Well, those wooden containers will keep them safe.").also { stage++ }
            116 -> npcl(FacialExpression.CHILD_NORMAL, "'Aah, that be a mighty good point, zur.").also { stage = END_DIALOGUE }

            117 -> playerl(FacialExpression.HALF_ASKING, "Oh! What have you been eating! Your breath is making my eyes water!").also { stage++ }
            118 -> npcl(FacialExpression.CHILD_NORMAL, "Oi! Oi'm 'urt by thaat.").also { stage++ }
            119 -> playerl(FacialExpression.SAD, "Sorry.").also { stage++ }
            120 -> npcl(FacialExpression.CHILD_NORMAL, "Oi mean, oi even et some mints earlier.").also { stage++ }
            121 -> playerl(FacialExpression.HALF_ASKING, "You did?").also { stage++ }
            122 -> npcl(FacialExpression.CHILD_NORMAL, "'At's roight. Oi found some mint plaants in a big pile o' muck, and oi 'ad 'em fer me breakfast.").also { stage++ }
            123 -> playerl(FacialExpression.FRIENDLY, "The mystery resolves itself.").also { stage = END_DIALOGUE }

            124 -> player("Oh, I have a few things to take care of here, is all.").also { stage++ }
            125 -> npc(FacialExpression.CHILD_NORMAL, "Schorp, splutter, splutter. Schlup schorp.", "(Aye, right ye are, zur. Oi'll be roight there.)").also { stage = END_DIALOGUE }

        }

        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.COMPOST_MOUND_6871 /* NPCs.COMPOST_MOUND_6872 */)
    }

}
