package content.region.fremennik.dialogue.piratescove

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE
import core.utilities.RandomFunction

@Initializable
class LadyZayDialogues(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (RandomFunction.random(0, 8) == 0) {
            when (stage) {
                0 -> playerl(FacialExpression.HALF_GUILTY, "Hello.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello to you too.").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "Yar! We be pirates, yar! Avast, ye scurvy land-lubbing lychee!").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Please don't talk like that, it is extremely irritating. Also, please don't call me a lychee, whatever that may be.").also { stage++ }
                4 -> playerl(FacialExpression.HALF_GUILTY, "Oh. Okay. Sorry.").also { stage = END_DIALOGUE }
            }
        }
        if (RandomFunction.random(0, 8) == 1) {
            when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Hello.").also { stage++ }
                1 -> playerl(FacialExpression.HALF_GUILTY, "You're a pirate, huh?").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "It's what it says on my pay-packet at the end of the month.").also { stage++ }
                3 -> playerl(FacialExpression.HALF_GUILTY, "How's that working out for you?").also { stage++ }
                4 -> npcl(FacialExpression.FRIENDLY, "Pretty good so far. All the grog and loot that we can plunder, plus full medical including dental.").also { stage++ }
                5 -> playerl(FacialExpression.HALF_GUILTY, "You mean you have insurance?").also { stage++ }
                6 -> npcl(FacialExpression.FRIENDLY, "Not as such. If any of us get sick we kidnap a doctor and don't let him go until we're better. You'd be surprised what an incentive for expert health care that is.").also { stage++ }
                7 -> playerl(FacialExpression.HALF_GUILTY, "I can imagine.").also { stage = END_DIALOGUE }
            }
        }
        if (RandomFunction.random(0, 8) == 2) {
            when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Sorry, can't stop, the Captain will have my guts for garters if he catches me slacking off talking to the stowaway.").also { stage++ }
                1 -> playerl(FacialExpression.HALF_GUILTY, "I'm not a stowaway! I was invited aboard!").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Yeah, whatever guy, it doesn't really matter who you are I'll get in trouble!").also { stage = END_DIALOGUE }
            }
        }
        if (RandomFunction.random(0, 8) == 3) {
            when (stage) {
                0 -> playerl(FacialExpression.HALF_GUILTY, "You know, I've always wondered what life as a pirate actually entails.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Well, at the moment it mostly involves being asked random questions by a stowaway.").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "I'm not a stowaway! I was invited aboard! By Lokar! Ask him!").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Hey, whatever pal. Just make sure the captain doesn't catch you, pirates don't like stowaways much.").also { stage = END_DIALOGUE }
            }
        }
        if (RandomFunction.random(0, 8) == 4) {
            when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Ah, good day to you sirrah! Your face is unfamiliar, did you perhaps join us aboard the ship at Lunar Isle?").also { stage++ }
                1 -> playerl(FacialExpression.HALF_GUILTY, "No, Lokar offered me a lift in Rellekka actually.").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Oh, really? You don't look like a Fremennik to me!").also { stage++ }
                3 -> playerl(FacialExpression.HALF_GUILTY, "I kind of am, and I kind of aren't. It's a long story.").also { stage++ }
                4 -> npcl(FacialExpression.FRIENDLY, "Sorry I don't have time to hear it then! See you around young fremennik-who-is-not-really-a-fremennik!").also { stage++ }
                5 -> playerl(FacialExpression.HALF_GUILTY, "'Bye..").also { stage = END_DIALOGUE }
            }
        }
        if (RandomFunction.random(0, 8) == 5) {
            when (stage) {
                0 -> playerl(FacialExpression.HALF_GUILTY, "Brrrr! Its cold up here!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "You think this is cold? Up by Acheron it gets so cold that when you talk you see the words freeze in the air in front of you!").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "REALLY?").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Nah, not really. I was exaggerating for humourous effect. It is very very cold though!").also { stage = END_DIALOGUE }
            }
        }
        if (RandomFunction.random(0, 8) == 6) {
            when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "ARGH! SOUND THE ALARM! STOWAWAY ON BOARD! STOWAWAY ON BOARD!").also { stage++ }
                1 -> playerl(FacialExpression.HALF_GUILTY, "I'm not a stowaway! Honest! I was invited here!").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Oh, sorry, my mistake then. You must admit you do look a lot like a stowaway though.").also { stage++ }
                3 -> playerl(FacialExpression.HALF_GUILTY, "Why, what do they usually look like?").also { stage++ }
                4 -> npcl(FacialExpression.FRIENDLY, "Errr.... I've never actually met one...").also { stage++ }
                5 -> playerl(FacialExpression.HALF_GUILTY, "Okay then...").also { stage = END_DIALOGUE }
            }
        }
        if (RandomFunction.random(0, 8) == 7) {
            when (stage) {
                0 -> playerl(FacialExpression.HALF_GUILTY, "Aren't you a little short for a pirate?").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "My mother was a gnome. Apparently it was a very painful birth.").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "More info than I wanted, thanks!").also { stage = END_DIALOGUE }
            }
        }
        if (RandomFunction.random(0, 8) == 8) {
            when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Hello there. So what brings you aboard the Lady Zay?").also { stage++ }
                1 -> playerl(FacialExpression.HALF_GUILTY, "Well, I was planning on visiting the Moon Clan, but I have to say your ship is very impressive.").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Aye, she's a beauty alright! The Lady Zay has been my home for many hard months, through storm and sun, and she always gets us to here we were headed!").also { stage++ }
                3 -> playerl(FacialExpression.HALF_GUILTY, "Yes, she's certainly one of the finest boats I've seen on my travels!").also { stage++ }
                4 -> npcl(FacialExpression.FRIENDLY, "That she is lad, that she is.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BIRDS_EYE_JACK_4544, NPCs.BEDREAD_THE_BOLD_4547, NPCs.TOMMY_2_TIMES_4549, NPCs.MURKY_PAT_4550, NPCs.JACK_SAILS_4551, NPCs.BEEDY_EYE_JONES_4554, NPCs.JENNY_BLADE_4555, NPCs.STICKY_SANDERS_4557)
    }
}
