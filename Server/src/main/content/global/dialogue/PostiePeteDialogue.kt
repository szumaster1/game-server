package content.global.dialogue

import core.api.consts.NPCs
import core.api.findLocalNPC
import core.api.sendChat
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Postie pete dialogue.
 */
@Initializable
class PostiePeteDialogue(player: Player? = null) : Dialogue(player) {

    // TODO: Spawn at random location.
    // TODO: Add player dialogues.
    // DONE: Fix opening dialogue.
    // TODO: Check all npc spawn locations.

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "Anyone got Post?").also { sendChat(npc, "Anyone got Post?") }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (player!!.viewport.region.id) {

            /*
             * At the Laughing Miner pub.
             */
            11679 -> {
                when (stage) {
                    0 -> npcl(FacialExpression.OLD_NORMAL, "4 pints of your finest please.").also {
                        sendChat(npc, "4 pints of your finest please.")
                        stage++
                    }
                    1 -> sendNPCDialogue(player, NPCs.BARMAID_2178, "4 pints coming right up. Are you expecting guests?", FacialExpression.OLD_NORMAL).also {
                        findLocalNPC(player, NPCs.BARMAID_2178)?.let { sendChat(it, "4 pints coming right up. Are you expecting guests?") }
                        stage++
                    }
                    2 -> npcl(FacialExpression.OLD_NORMAL, "Just a few old friends.").also {
                        sendChat(npc, "Just a few old friends.")
                        stage++
                    }
                    3 -> sendNPCDialogue(player, NPCs.BARMAID_2178, "You want me to put it on your tab?", FacialExpression.OLD_NORMAL).also {
                        findLocalNPC(player, NPCs.BARMAID_2178)?.let { sendChat(it, "You want me to put it on your tab?") }
                        stage++
                    }
                    4 -> npcl(FacialExpression.OLD_NORMAL, "That would be great, thanks. Oh, cripes! I've left a fire burning! I've got to go!").also {
                        sendChat(npc, "That would be great, thanks. Oh, cripes! I've left a fire burning! I've got to go!")
                        stage = END_DIALOGUE
                    }
                }
            }

            /*
             * At Port Phasmatys.
             */
            14647 -> {
                when (stage) {
                    0 -> npcl(FacialExpression.OLD_NORMAL, "Hey Pete. Any news from the fleet?").also {
                        sendChat(npc, "Hey Pete. Any news from the fleet?")
                        stage++
                    }
                    1 -> sendNPCDialogue(player, NPCs.PIRATE_PETE_2825, "No yo ho bruv.").also {
                        findLocalNPC(player, NPCs.PIRATE_PETE_2825)?.let { sendChat(it, "No yo ho bruv.") }
                        stage++
                    }
                    2 -> sendNPCDialogue(player, NPCs.PIRATE_PETE_2825, "Tis all quiet on the eastern front.").also {
                        findLocalNPC(player, NPCs.PIRATE_PETE_2825)?.let { sendChat(it, "Tis all quiet on the eastern front.") }
                        stage++
                    }
                    3 -> npcl(FacialExpression.OLD_NORMAL, "Good good.").also {
                        sendChat(npc, "Good good.")
                        stage++
                    }
                    4 -> npcl(FacialExpression.OLD_NORMAL, "Well you know what to do if something goes wrong.").also {
                        sendChat(npc, "Well you know what to do if something goes wrong.")
                        stage++
                    }
                    5 -> sendNPCDialogue(player, NPCs.PIRATE_PETE_2825, "Aye, that I do, bruv.").also {
                        findLocalNPC(player, NPCs.PIRATE_PETE_2825)?.let { sendChat(it, "Aye, that I do, bruv.") }
                        stage++
                    }
                    6 -> sendNPCDialogue(player, NPCs.PIRATE_PETE_2825, "Holler like a bosun without a bottle.").also {
                        findLocalNPC(player, NPCs.PIRATE_PETE_2825)?.let { sendChat(it, "Holler like a bosun without a bottle.") }
                        stage++
                    }
                    7 -> npcl(FacialExpression.OLD_NORMAL, "That's it brother, and we'll come a running!").also {
                        sendChat(npc, "That's it brother, and we'll come a running!")
                        stage = END_DIALOGUE
                    }
                }
            }

            /*
             * At the Varrock Library.
             */
            12854 -> {
                when (stage) {
                    0 -> npcl(FacialExpression.OLD_NORMAL, "Reldo, have you found that book I was looking for yet?").also {
                        sendChat(npc, "Reldo, have you found that book I was looking for yet?")
                        stage++
                    }
                    1 -> sendNPCDialogue(player, NPCs.RELDO_2660, "No, not yet... but I know I've seen it somewhere...").also {
                        findLocalNPC(player, NPCs.RELDO_2660)?.let { sendChat(it, "No, not yet... but I know I've seen it somewhere...") }
                        stage++
                    }
                    2 -> sendNPCDialogue(player, NPCs.RELDO_2660, "...come back later.").also {
                        findLocalNPC(player, NPCs.RELDO_2660)?.let { sendChat(it, "...come back later.") }
                        stage++
                    }
                    3 -> npcl(FacialExpression.OLD_NORMAL, "Sure, no problems. See you later.").also {
                        sendChat(npc, "Sure, no problems. See you later.")
                        stage++
                    }
                    4 -> sendNPCDialogue(player, NPCs.RELDO_2660, "Bye! Say hi to the other Petes for me.").also {
                        findLocalNPC(player, NPCs.RELDO_2660)?.let { sendChat(it, "Bye! Say hi to the other Petes for me.") }
                        stage++
                    }
                    5 -> npcl(FacialExpression.OLD_NORMAL, "Will do. Farewell!").also {
                        sendChat(npc, "Will do. Farewell!")
                        stage = END_DIALOGUE
                    }
                }
            }

            /*
             * At the Falador Party Room.
             */
            12084 -> {
                when (stage) {
                    0 -> npcl(FacialExpression.OLD_NORMAL, "Hey Bro, how's the party business?").also {
                        sendChat(npc, "Hey Bro, how's the party business?")
                        stage++
                    }
                    1 -> sendNPCDialogue(player, NPCs.PARTY_PETE_659, "Great! Thanks!").also {
                        findLocalNPC(player, NPCs.PARTY_PETE_659)?.let { sendChat(it, "Great! Thanks!") }
                        stage++
                    }
                    2 -> sendNPCDialogue(player, NPCs.PARTY_PETE_659, "Celebrating weddings and drop parties...").also {
                        findLocalNPC(player, NPCs.PARTY_PETE_659)?.let { sendChat(it, "Celebrating weddings and drop parties...") }
                        stage++
                    }
                    3 -> sendNPCDialogue(player, NPCs.PARTY_PETE_659, "...and getting paid for it!").also {
                        findLocalNPC(player, NPCs.PARTY_PETE_659)?.let { sendChat(it, "...and getting paid for it!") }
                        stage++
                    }
                    4 -> npcl(FacialExpression.OLD_NORMAL, "Nice. Well I'm here to talk to some white knights.").also {
                        sendChat(npc, "Nice. Well I'm here to talk to some white knights.")
                        stage++
                    }
                    5 -> sendNPCDialogue(player, NPCs.PARTY_PETE_659, "Want to take some cake with you?").also {
                        findLocalNPC(player, NPCs.PARTY_PETE_659)?.let { sendChat(it, "Want to take some cake with you?") }
                        stage++
                    }
                    6 -> npcl(FacialExpression.OLD_NORMAL, "No thanks, I'm still dieting.").also {
                        sendChat(npc, "No thanks, I'm still dieting.")
                        stage++
                    }
                    7 -> npcl(FacialExpression.OLD_NORMAL, "Just seafood for me.").also {
                        sendChat(npc, "Just seafood for me.")
                        stage = END_DIALOGUE
                    }
                }
            }

            /*
             * At Ice Mountain.
             */
            12086 -> {
                when (stage) {
                    0 -> npcl(FacialExpression.OLD_NORMAL, "Well I'm back. And he said no. Again.").also {
                        sendChat(npc, "Well I'm back. And he said no. Again.")
                        stage++
                    }
                    1 -> sendNPCDialogue(player, NPCs.ORACLE_746, "Lemons? Both of them?").also {
                        findLocalNPC(player, NPCs.ORACLE_746)?.let { sendChat(it, "Lemons? Both of them?") }
                        stage++
                    }
                    2 -> npcl(FacialExpression.OLD_NORMAL, "Lemons? What are you talking about?").also {
                        sendChat(npc, "Lemons? What are you talking about?")
                        stage++
                    }
                    3 -> sendNPCDialogue(player, NPCs.ORACLE_746, "Fragile! Do not bend!").also {
                        findLocalNPC(player, NPCs.ORACLE_746)?.let { sendChat(it, "Fragile! Do not bend!") }
                        stage++
                    }
                    4 -> npcl(FacialExpression.OLD_NORMAL, "I swear you get stranger every time.").also {
                        sendChat(npc, "I swear you get stranger every time.")
                        stage++
                    }
                    5 -> npcl(FacialExpression.OLD_NORMAL, "Ok, I'll ask again.").also {
                        sendChat(npc, "Ok, I'll ask again.")
                        stage = END_DIALOGUE
                    }
                }
            }

            /*
             * At the Wilderness Bandit Camp
             */
            12089 -> {
                when (stage) {
                    0 -> npcl(FacialExpression.OLD_NORMAL, "Psssst! How is the plan going?").also {
                        sendChat(npc, "Psssst! How is the plan going?")
                        stage++
                    }
                    1 -> sendNPCDialogue(player, NPCs.NOTERAZZO_597, "Perfectly. Everyone is prepared.").also {
                        findLocalNPC(player, NPCs.NOTERAZZO_597)?.let { sendChat(it, "Perfectly. Everyone is prepared.") }
                        stage++
                    }
                    2 -> npcl(FacialExpression.FRIENDLY, "Great. Now don't forget- nobody does anything until I give the signal.").also {
                        sendChat(npc, "Great. Now don't forget- nobody does anything until I give the signal.")
                        stage++
                    }
                    3 -> sendNPCDialogue(player, NPCs.NOTERAZZO_597, "Sure no problem. We'll be waiting.").also {
                        findLocalNPC(player, NPCs.NOTERAZZO_597)?.let { sendChat(it, "Sure no problem. We'll be waiting.") }
                        stage++
                    }
                    4 -> npcl(FacialExpression.OLD_NORMAL, "Till next time. Farewell.").also {
                        sendChat(npc, "Till next time. Farewell.")
                        stage = END_DIALOGUE
                    }
                }
            }

            /*
             * At the Ardougne Zoo.
             */
            10291 -> {
                when (stage) {
                    0 -> npcl(FacialExpression.OLD_NORMAL, "Hi Pete!").also {
                        sendChat(npc, "Hi Pete!")
                        stage++
                    }
                    1 -> npcl(FacialExpression.OLD_NORMAL, "Mum wants to know if you're coming round for tea.").also {
                        sendChat(npc, "Mum wants to know if you're coming round for tea.")
                        stage++
                    }
                    2 -> sendNPCDialogue(player, NPCs.PARROTY_PETE_1216, "No. I've got another late night here.").also {
                        findLocalNPC(player, NPCs.PARROTY_PETE_1216)?.let { sendChat(it, "No. I've got another late night here.") }
                        stage++
                    }
                    3 -> sendNPCDialogue(player, NPCs.PARROTY_PETE_1216, "Ol' blue beak's got the flu again!").also {
                        findLocalNPC(player, NPCs.PARROTY_PETE_1216)?.let { sendChat(it, "Ol' blue beak's got the flu again!") }
                        stage++
                    }
                    4 -> sendNPCDialogue(player, NPCs.PARROTY_PETE_1216, "Oh dear. Nothing too serious I hope.").also {
                        findLocalNPC(player, NPCs.PARROTY_PETE_1216)?.let { sendChat(it, "Oh dear. Nothing too serious I hope.") }
                        stage++
                    }
                    5 -> sendNPCDialogue(player, NPCs.PARROTY_PETE_1216, "Well as long as he hasn't caught anything from that evil chicken.").also {
                        findLocalNPC(player, NPCs.PARROTY_PETE_1216)?.let { sendChat(it, "Well as long as he hasn't caught anything from that evil chicken.") }
                        stage++
                    }
                    6 -> npcl(FacialExpression.OLD_NORMAL, "Got a taste for parrots now too does he?").also {
                        sendChat(npc, "Got a taste for parrots now too does he?")
                        stage++
                    }
                    7 -> npcl(FacialExpression.OLD_NORMAL, "*Sigh* Will nothing sacred be safe?").also {
                        sendChat(npc, "*Sigh* Will nothing sacred be safe?")
                        stage = END_DIALOGUE
                    }
                }
            }

            /*
             * At Castle Wars.
             */
            9776 -> {
                when (stage) {
                    0 -> npcl(FacialExpression.OLD_NORMAL, "Fight! Fight! ...Fight!").also {
                        sendChat(npc, "Fight! Fight! ...Fight!")
                        stage++
                    }
                    1 -> sendNPCDialogue(player, NPCs.LANTHUS_1526, "Easy Pete, calm down. Don't lose your head!", FacialExpression.FRIENDLY).also {
                        findLocalNPC(player, NPCs.LANTHUS_1526)?.let { sendChat(it, "Easy Pete, calm down. Don't lose your head!") }
                        stage++
                    }
                    2 -> npcl(FacialExpression.OLD_NORMAL, "Cheeky!! If you're not careful I'll get you cursed too.").also {
                        sendChat(npc, "Cheeky!! If you're not careful I'll get you cursed too.")
                        stage++
                    }
                    3 -> sendNPCDialogue(player, NPCs.LANTHUS_1526, "You wouldn't catch me double-crossing a witch!", FacialExpression.LAUGH).also {
                        findLocalNPC(player, NPCs.LANTHUS_1526)?.let { sendChat(it, "You wouldn't catch me double-crossing a witch!") }
                        stage++
                    }
                    4 -> npcl(FacialExpression.OLD_NORMAL, "But which witch is which! Anyway, I'm late for an appointment with the oracle").also {
                        sendChat(npc, "But which witch is which! Anyway, I'm late for an appointment with the oracle")
                        stage++
                    }
                    5 -> sendNPCDialogue(player, NPCs.LANTHUS_1526, "Take care and say hi to the kids for me.").also {
                        findLocalNPC(player, NPCs.LANTHUS_1526)?.let { sendChat(it, "Take care and say hi to the kids for me.") }
                        stage = END_DIALOGUE
                    }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.POSTIE_PETE_3805)
    }

}