package content.region.morytania.quest.deal

import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Captain brain death dialogue file.
 */
class CaptainBrainDeathDialogueFile(var qs: Int) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        val check = if (!player!!.isMale) "lass" else "lad"
        qs = getQuestStage(player!!, "Rum Deal")
        npc = NPC(NPCs.CAPTAIN_BRAINDEATH_2827)
        when (qs) {
            1 -> when (stage) {
                0 -> npc(FacialExpression.HALF_ASKING, "Are ye alright, $check?").also { stage++ }
                1 -> player(FacialExpression.DISGUSTED_HEAD_SHAKE, "Ohhhh... My head...").also { stage++ }
                2 -> player(FacialExpression.DISGUSTED_HEAD_SHAKE, "It feels like someone has smacked me one with a bottle...").also { stage++ }
                3 -> npc(FacialExpression.SAD, "Arr... Those devils gave ye a nasty knock when ye", "came to aid us.").also { stage++ }
                4 -> npc(FacialExpression.HAPPY, "But now yer here we'll run those evil brain-eatin' dogs", "off the island fer good!").also { stage++ }
                5 -> player(FacialExpression.HALF_ASKING, "What? What is going on here?").also { stage++ }
                6 -> player(FacialExpression.SAD, "I can't seem to remember anything beyond chatting to", "a man at the docks.").also { stage++ }
                7 -> npc(FacialExpression.SUSPICIOUS, "Arr. Well, $check, that would be Pete, one of my men.").also { stage++ }
                8 -> npc("He's been out lookin' fer heroes like yerself to aid us in", "our peril.").also { stage++ }
                9 -> npc(FacialExpression.SUSPICIOUS, "When ye arrived ye took a nasty knock to the head, so", "ye probably don't remember agreein' to help us out.", "But I swear to ye that ye did.").also { stage++ }
                10 -> player("Okay... I'll buy that. It sounds like something I would", "do.").also { stage++ }
                11 -> player(FacialExpression.CALM_TALK, "So where am I, and what is going on?").also { stage++ }
                12 -> npc(FacialExpression.NEUTRAL, "Yer on Braindeath Island!").also { stage++ }
                13 -> npc(FacialExpression.NEUTRAL, "Where it lies is a secret, because ye are standin' in the", "brewery of Cap'n Braindeath, purveyor of the most", "vitriolic alcoholic beverages in the world!").also { stage++ }
                14 -> player(FacialExpression.CALM, "Wow!").also { stage++ }
                15 -> npc(FacialExpression.HAPPY, "I am the notorious alchemist Cap'n Braindeath, and this", "whole operation be my idea!").also { stage++ }
                16 -> npc(FacialExpression.HAPPY, "With my crew of sturdy, upright pirate brewers, we sail", "the seven seas, distributing cheap 'alcohol' to all and", "sundry.").also { stage++ }
                17 -> npc(FacialExpression.HAPPY, "Well, fer a price, at any rate.").also { stage++ }
                18 -> player(FacialExpression.CALM, "Oooh!").also { stage++ }
                19 -> npc(FacialExpression.HALF_CRYING, "These be dark times, though, $check. See, a week ago we", "awoke to find ourselves beseiged.").also { stage++ }
                20 -> npc(FacialExpression.HALF_CRYING, "The lads and I have held them off so far, but 'tis only", "a matter of time before they sweep through the buildin'", "and put us all to the sword.").also { stage++ }
                21 -> player(FacialExpression.HALF_ASKING, "Who?").also { stage++ }
                22 -> npc(FacialExpression.ANGRY, "Them!").also { stage++ }
                23 -> sendDialogue(player!!, "The Captain points out of the window...").also { stage++ }
                24 -> {
                    lock(player!!, 3)
                    lockInteractions(player!!, 3)
                    ZombiePirateProtestingCutscene(player!!).start()
                }
            }

            2 -> when (stage) {
                0 -> player(FacialExpression.HALF_THINKING, "Are they...").also { stage++ }
                1 -> player(FacialExpression.HALF_THINKING, "...protesting?").also { stage++ }
                2 -> npc(FacialExpression.ANGRY, "Arr, $check! That they are!").also { stage++ }
                3 -> npc(FacialExpression.ANGRY, "Day and night they seek to break our will with their", "chantin', and their singin' and their passive resistance!").also { stage++ }
                4 -> npc(FacialExpression.NEUTRAL, "Seems they lost their fightin' spirit after the first few", "days. Now most of them just protest all the time.").also { stage++ }
                5 -> player(FacialExpression.HALF_ASKING, "So, what do you want me to do?").also { stage++ }
                6 -> npc(FacialExpression.NEUTRAL, "Well, me and the lads got our heads together and", "decided that if we can get their Cap'n drunk enough,", "perhaps they'll stop protestin'.").also { stage++ }
                7 -> npc(FacialExpression.NEUTRAL, "If that happens, we'll slip out the back and set up shop", "somewhere else.").also { stage++ }
                8 -> player(FacialExpression.NEUTRAL, "Well, how can I help?").also { stage++ }
                9 -> npc(FacialExpression.NEUTRAL, "Well, first of all, we need someone to go out the front", "and grow us some Blindweed.").also { stage++ }
                10 -> npc(FacialExpression.NEUTRAL, "'Tis one of the ingredients of our 'rum'.").also { stage++ }
                11 -> npc(FacialExpression.NEUTRAL, "The only problem is that those rottin' fiends have torn", "up and destroyed all but one of the Blindweed Patches.").also { stage++ }
                12 -> if (freeSlots(player!!) == 0) {
                    npc("Well, I have some Blindweed seeds fer ye. When ye have", "some free space fer them, come and talk to me.").also { stage = END_DIALOGUE }
                } else {
                    npc(FacialExpression.NEUTRAL, "Here, $check. I'll give ye the seed you'll need fer growin'", "the herb. Help yerself to the gardenin' equipment in the", "basement.").also { stage++ }
                    addItem(player!!, Items.BLINDWEED_SEED_6710)
                    stage++
                }
                13 -> npc(FacialExpression.NEUTRAL, "I'll warn ye again that those devils are sat right on top", "of the patch.").also { stage++ }
                14 -> npc("Try hecklin' 'em from a distance. Those Swabs may", "talk a good fight, but if ye can put a scare in 'em", "they'll keep out of yer way!").also { stage = END_DIALOGUE }
            }
        }
    }
}
