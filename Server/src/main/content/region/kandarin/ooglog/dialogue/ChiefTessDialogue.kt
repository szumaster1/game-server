package content.region.kandarin.ooglog.dialogue


import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.sendDialogue
import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Chief Tess dialogue.
 */
@Initializable
class ChiefTessDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Chief Tess is the leader of the spa town of Oo'glog.
     * Talking to her can start the As a First Resort quest.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "As a First Resort")) {
            npc(FacialExpression.CHILD_FRIENDLY, "You want discuss da resort or ar-key-olo-gy?")
        } else {
            sendDialogue(player, "Chief Tess seems too busy to talk.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogueOptions(
                player,
                "What would you like to talk about?",
                "As a First Resort quest.",
                "Where exactly am I?",
                "Who are you and what's that thing on your head?",
                "Do you know there's a larupia loose in this hut?",
                "Bye!"
            )
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Hello, there.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Where exactly am I?").also { stage = END_DIALOGUE }
                3 -> playerl(FacialExpression.FRIENDLY, "Who are you and what's that thing on your head?").also { stage = END_DIALOGUE }
                4 -> playerl(FacialExpression.FRIENDLY, "Do you know there's a larupia loose in this hut?").also { stage = END_DIALOGUE }
                5 -> playerl(FacialExpression.FRIENDLY, "Bye!").also { stage = END_DIALOGUE }
            }
            2 -> playerl(FacialExpression.FRIENDLY, "Hello, there.").also { stage++ }
            3 -> npcl(FacialExpression.HALF_ASKING, "What you want, skinny creature? We not open yet.").also { stage++ }
            4 -> playerl(FacialExpression.HALF_ASKING, "Not open?").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_FRIENDLY, "Nope, not open until dat other human say we ready.").also { stage++ }
            6 -> playerl(FacialExpression.HALF_ASKING, "Other human?").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_FRIENDLY, "Yeah, de bossy one in de bank. She say there is lot to do before we can open, so we not open.").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_FRIENDLY, "Which bring me back to question: what you want, human?").also { stage++ }
            9 -> options(
                "What exactly is going on around here?",
                "Where exactly am I?",
                "Who are you and what's that thing on your head?",
                "Do you know there's a larupia loose in this hut?",
                "Bye!"
            )
            10 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "What exactly is going on around here?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Where exactly am I?").also { stage = END_DIALOGUE }
                3 -> playerl(FacialExpression.FRIENDLY, "Who are you and what's that thing on your head?").also { stage = END_DIALOGUE }
                4 -> playerl(FacialExpression.FRIENDLY, "Do you know there's a larupia loose in this hut?").also { stage = END_DIALOGUE }
                5 -> playerl(FacialExpression.FRIENDLY, "Bye!").also { stage = END_DIALOGUE }
            }
            11 -> npcl(FacialExpression.CHILD_FRIENDLY, "Well, it long story. See, dis lady human come to Oo'glog. She look around village a little, got very excited, then start talking at me very fast. Started asking for many things.").also { stage++ }
            12 -> playerl(FacialExpression.HALF_ASKING, "What did she want?").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_FRIENDLY, "Dat woman, she very confusing, very bossy. Says lots but make no sense. Hard to understand. All I know is I agree to help her in thing she call 'business venture'.").also { stage++ }
            14 -> playerl(FacialExpression.HALF_ASKING, "You what?").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_FRIENDLY, "I know, I know. It such a mess - cause so much trouble, me not know what to do. I not even know what a 'business venture' looks like.").also { stage++ }
            16 -> options(
                "So, about this 'business venture'...",
                "Where exactly am I?",
                "Who are you and what's that thing on your head?",
                "Do you know there's a larupia loose in this hut?",
                "Bye!"
            )
            17 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "What exactly is going on around here?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Where exactly am I?").also { stage = END_DIALOGUE }
                3 -> playerl(FacialExpression.FRIENDLY, "Who are you and what's that thing on your head?").also { stage = END_DIALOGUE }
                4 -> playerl(FacialExpression.FRIENDLY, "Do you know there's a larupia loose in this hut?").also { stage = END_DIALOGUE }
                5 -> playerl(FacialExpression.FRIENDLY, "Bye!").also { stage = END_DIALOGUE }
            }
            18 -> playerl(FacialExpression.FRIENDLY, "So, about this 'business venture' - let me get this straight. Some woman shows up out of nowhere, talks to you for a while and you agree to help her with some random plan of hers, without having any idea what she's talking about?").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_FRIENDLY, "Uh, when you put it dat way, it sound less good. But de lady say dat many creatures give us lots of shiny pretties to sit in de pools and mud of Oo'glog! She go on about dis 'business plan' thing - she say it guarantee shiny pretties for everyone if we help her.").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "And you...believed this, did you?").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_FRIENDLY, "Well, it sound good at de time... She talk very fast and sound very sure about de shiny pretties. Besides, me had to agree - it was only way to make her stop talking.").also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "So, when are you going to get these shiny pretties, then?").also { stage++ }
            23 -> npcl(FacialExpression.CHILD_FRIENDLY, "That just the problem. Human lady say much stuffs need be done before everything is ready for customer. We try to help, but, thing is, we no can understand what she wants. We still have to listen to her nonsense talking, and there be no shiny pretties yet for anybody.").also { stage++ }
            24 -> options(
                "Can I help at all?",
                "Where exactly am I?",
                "Who are you and what's that thing on your head?",
                "Do you know there's a larupia loose in this hut?",
                "Bye!"
            )
            25 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Can I help at all?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Where exactly am I?").also { stage = END_DIALOGUE }
                3 -> playerl(FacialExpression.FRIENDLY, "Who are you and what's that thing on your head?").also { stage = END_DIALOGUE }
                4 -> playerl(FacialExpression.FRIENDLY, "Do you know there's a larupia loose in this hut?").also { stage = END_DIALOGUE }
                5 -> playerl(FacialExpression.FRIENDLY, "Bye!").also { stage = END_DIALOGUE }
            }
            26 -> npcl(FacialExpression.CHILD_FRIENDLY, "Dat so very nice of you, skinny creature. Can you go talk to bossy lady at de bank - see what she want? Maybe human have better chance than ogre of understanding her.").also { stage++ }
            27 -> playerl(FacialExpression.FRIENDLY, "I'd be happy to.").also { stage++ }
            28 -> options(
                "Where exactly is this bossy lady?",
                "Where exactly am I?",
                "Who are you and what's that thing on your head?",
                "Do you know there's a larupia loose in this hut?",
                "Bye!"
            )

            29 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Where exactly is this bossy lady?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Where exactly am I?").also { stage = END_DIALOGUE }
                3 -> playerl(FacialExpression.FRIENDLY, "Who are you and what's that thing on your head?").also { stage = END_DIALOGUE }
                4 -> playerl(FacialExpression.FRIENDLY, "Do you know there's a larupia loose in this hut?").also { stage = END_DIALOGUE }
                5 -> playerl(FacialExpression.FRIENDLY, "Bye!").also { stage = END_DIALOGUE }
            }

            30 -> npcl(FacialExpression.CHILD_FRIENDLY, "She at de new bank, just south of here.").also { stage++ }
            31 -> playerl(FacialExpression.FRIENDLY, "Thanks. I'll go see what I can do.").also {
                // setQuestStage(player, "As a First Resort", 1)
                stage++
            }

            32 -> npcl(FacialExpression.CHILD_FRIENDLY, "Anything else?").also { stage++ }
            33 -> options(
                "Where exactly am I?",
                "Who are you and what's that thing on your head?",
                "Do you know there's a larupia loose in this hut?",
                "Bye!"
            )
            34 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Where exactly am I?").also { stage = END_DIALOGUE }
                2 -> playerl(FacialExpression.FRIENDLY, "Who are you and what's that thing on your head?").also { stage = END_DIALOGUE }
                3 -> playerl(FacialExpression.FRIENDLY, "Do you know there's a larupia loose in this hut?").also { stage = END_DIALOGUE }
                4 -> playerl(FacialExpression.FRIENDLY, "Bye!").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
       return intArrayOf(NPCs.CHIEF_TESS_7051)
    }
}