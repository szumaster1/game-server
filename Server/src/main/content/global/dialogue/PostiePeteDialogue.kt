package content.global.dialogue

import core.api.consts.NPCs
import core.api.sendChat
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class PostiePeteDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc("Anyone got Post?").also { sendChat(npc, "Anyone got Post?") }
        stage = 49
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {

            /*
             * Barmaid dialogue.
             */

            0 -> npcl(FacialExpression.OLD_NORMAL, "4 pints of your finest please.").also { stage++ }
            1 -> sendNPCDialogue(player, NPCs.BARMAID_2178, "4 pints coming right up. Are you expecting guests?", FacialExpression.OLD_NORMAL).also { stage++ }
            2 -> npcl(FacialExpression.OLD_NORMAL, "Just a few old friends.").also { stage++ }
            3 -> sendNPCDialogue(player, NPCs.BARMAID_2178, "You want me to put it on your tab?", FacialExpression.OLD_NORMAL).also { stage++ }
            4 -> npcl(FacialExpression.OLD_NORMAL, "That would be great, thanks. Oh, cripes! I've left a fire burning! I've got to go!").also { stage = END_DIALOGUE }

            /*
             * Pirate Pete dialogue.
             */

            6 -> npcl(FacialExpression.OLD_NORMAL, "Hey Pete. Any news from the fleet?").also { stage++ }
            7 -> sendNPCDialogue(player, NPCs.PIRATE_PETE_2825, "No yo ho bruv.").also { stage++ }
            8 -> sendNPCDialogue(player, NPCs.PIRATE_PETE_2825, "Tis all quiet on the eastern front.").also { stage++ }
            9 -> npcl(FacialExpression.OLD_NORMAL, "Good good.").also { stage++ }
            10 -> npcl(FacialExpression.OLD_NORMAL, "Well you know what to do if something goes wrong.").also { stage++ }
            11 -> sendNPCDialogue(player, NPCs.PIRATE_PETE_2825, "Aye, that I do, bruv.").also { stage++ }
            12 -> sendNPCDialogue(player, NPCs.PIRATE_PETE_2825, "Holler like a bosun without a bottle.").also { stage++ }
            13 -> npcl(FacialExpression.OLD_NORMAL, "That's it brother, and we'll come a running!").also { stage = END_DIALOGUE }

            /*
             * Reldo dialogue.
             */

            15 -> npcl(FacialExpression.OLD_NORMAL, "Reldo, have you found that book I was looking for yet?").also { stage++ }
            16 -> sendNPCDialogue(player, NPCs.RELDO_2660, "No, not yet... but I know I've seen it somewhere...").also { stage++ }
            17 -> sendNPCDialogue(player, NPCs.RELDO_2660, "...come back later.").also { stage++ }
            18 -> npcl(FacialExpression.OLD_NORMAL, "Sure, no problems. See you later.").also { stage++ }
            19 -> sendNPCDialogue(player, NPCs.RELDO_2660, "Bye! Say hi to the other Petes for me.").also { stage++ }
            20 -> npcl(FacialExpression.OLD_NORMAL, "Will do. Farewell!").also { stage = END_DIALOGUE }

            /*
             * Party Pete dialogue.
             */

            22 -> npcl(FacialExpression.OLD_NORMAL, "Hey Bro, how's the party business?").also { stage++ }
            23 -> sendNPCDialogue(player, NPCs.PARTY_PETE_659, "Great! Thanks!").also { stage++ }
            24 -> sendNPCDialogue(player, NPCs.PARTY_PETE_659, "Celebrating weddings and drop parties...").also { stage++ }
            25 -> sendNPCDialogue(player, NPCs.PARTY_PETE_659, "...and getting paid for it!").also { stage++ }
            26 -> npcl(FacialExpression.OLD_NORMAL, "Nice. Well I'm here to talk to some white knights.").also { stage++ }
            27 -> sendNPCDialogue(player, NPCs.PARTY_PETE_659, "Want to take some cake with you?").also { stage++ }
            28 -> npcl(FacialExpression.OLD_NORMAL, "No thanks, I'm still dieting.").also { stage++ }
            29 -> npcl(FacialExpression.OLD_NORMAL, "Just seafood for me.").also { stage = END_DIALOGUE }

            /*
             * Oracle dialogue.
             */

            30 -> npcl(FacialExpression.OLD_NORMAL, "Well I'm back. And he said no. Again.").also { stage++ }
            31 -> sendNPCDialogue(player, NPCs.ORACLE_746, "Lemons? Both of them?").also { stage++ }
            32 -> npcl(FacialExpression.OLD_NORMAL, "Lemons? What are you talking about?").also { stage++ }
            33 -> sendNPCDialogue(player, NPCs.ORACLE_746, "Fragile! Do not bend!").also { stage++ }
            34 -> npcl(FacialExpression.OLD_NORMAL, "I swear you get stranger every time.").also { stage++ }
            35 -> npcl(FacialExpression.OLD_NORMAL, "Ok, I'll ask again.").also { stage = END_DIALOGUE }

            /*
             * Notterazzo dialogue.
             */

            36 -> npcl(FacialExpression.OLD_NORMAL, "Psssst! How is the plan going?").also { stage++ }
            37 -> sendNPCDialogue(player, NPCs.NOTERAZZO_597, "Perfectly. Everyone is prepared.").also { stage++ }
            38 -> npcl(FacialExpression.FRIENDLY, "Great. Now don't forget- nobody does anything until I give the signal.").also { stage++ }
            39 -> sendNPCDialogue(player, NPCs.NOTERAZZO_597, "Sure no problem. We'll be waiting.").also { stage++ }
            40 -> npcl(FacialExpression.OLD_NORMAL, "Till next time. Farewell.").also { stage = END_DIALOGUE }

            /*
             * Parroty Pete dialogue.
             */

            41 -> npcl(FacialExpression.OLD_NORMAL, "Hi Pete!").also { stage++ }
            42 -> npcl(FacialExpression.OLD_NORMAL, "Mum wants to know if you're coming round for tea.").also { stage++ }
            43 -> sendNPCDialogue(player, NPCs.PARROTY_PETE_1216, "No. I've got another late night here.").also { stage++ }
            44 -> sendNPCDialogue(player, NPCs.PARROTY_PETE_1216, "Ol' blue beak's got the flu again!").also { stage++ }
            45 -> sendNPCDialogue(player, NPCs.PARROTY_PETE_1216, "Oh dear. Nothing too serious I hope.").also { stage++ }
            46 -> sendNPCDialogue(player, NPCs.PARROTY_PETE_1216, "Well as long as he hasn't caught anything from that evil chicken.").also { stage++ }
            47 -> npcl(FacialExpression.OLD_NORMAL, "Got a taste for parrots now too does he?").also { stage++ }
            48 -> npcl(FacialExpression.OLD_NORMAL, "*Sigh* Will nothing sacred be safe?").also { stage = END_DIALOGUE }

            /*
             * Lanthus dialogue.
             */

            49 -> npcl(FacialExpression.OLD_NORMAL, "Fight! Fight! ...Fight!").also { stage++ }
            50 -> sendNPCDialogue(player, NPCs.LANTHUS_1526, "Easy Pete, calm down. Don't lose your head!", FacialExpression.FRIENDLY).also { stage++ }
            51 -> npcl(FacialExpression.OLD_NORMAL, "Cheeky!! If you're not careful I'll get you cursed too.").also { stage++ }
            52 -> sendNPCDialogue(player, NPCs.LANTHUS_1526, "You wouldn't catch me double-crossing a witch!", FacialExpression.LAUGH).also { stage++ }
            53 -> npcl(FacialExpression.OLD_NORMAL, "But which witch is which! Anyway, I'm late for an appointment with the oracle").also { stage++ }
            54 -> sendNPCDialogue(player, NPCs.LANTHUS_1526, "Take care and say hi to the kids for me.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.POSTIE_PETE_3805)
    }

}
