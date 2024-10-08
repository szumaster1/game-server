package content.region.fremennik.rellekka.dialogue

import core.api.getAttribute
import core.api.hasRequirement
import core.api.isQuestComplete
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.net.packet.PacketRepository
import core.net.packet.context.MinimapStateContext
import core.net.packet.outgoing.MinimapState
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.Components
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Lokar searunner dialogue.
 */
@Initializable
class LokarSearunnerDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when (npc.id) {
            NPCs.LOKAR_SEARUNNER_4537 -> if (!isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS)) {
                player("Hello there.")
            } else {
                options("Hi Lokar, can you take me back to your ship?", "It seems you've been charming Pauline Polaris.")
            }
            NPCs.LOKAR_SEARUNNER_4536 -> if (!isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS)) {
                player("Hello there.")
            } else {
                player("Hello again Lokar.")
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (npc.id) {
            NPCs.LOKAR_SEARUNNER_4536 -> when (stage) {
                0 -> {
                    if (!isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS)) {
                        npcl(FacialExpression.ANNOYED, "Don't talk to me outerlander! I don't have time for the likes of you!")
                        stage = 40
                    } else {
                        npcl(FacialExpression.FRIENDLY, "Hi again ${getAttribute(player, "fremennikname", "fremmyname")}! What can I do for you?").also { stage++ }
                        stage = 2
                    }
                }

                2 -> options("Can you take me back to Rellekka?", "It seems you've been charming Pauline Polaris.", "Nothing, thanks.").also { stage++ }
                3 -> when (buttonId) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Can you take me back to Rellekka please?").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "It seems you've been charming Pauline Polaris.").also { stage = 8 }
                    3 -> playerl(FacialExpression.FRIENDLY, "Nothing thanks! I just saw you here and thought I'd say hello!").also { stage = 43 }
                }
                4 -> npcl(FacialExpression.FRIENDLY, "Hey, if you want to go back to Loserville with the Loser Gang, who am I to stop you?").also { stage++ }
                5 -> {
                    end()
                    travel(player, Location(2621, 3687, 0))
                    return true
                }
                6 -> npcl(FacialExpression.FRIENDLY, "You are possibly the most indecisive person I have ever met...").also { stage++ }
                7 -> playerl(FacialExpression.FRIENDLY, "Well, 'bye then.").also { stage = END_DIALOGUE }
                8 -> npcl(FacialExpression.FRIENDLY, "What did you say?").also { stage = 10 }
                10 -> playerl(FacialExpression.FRIENDLY, "Sorry. I mean, she was, erm, telling me a little about your business deal with her farm.").also { stage++ }
                11 -> npcl(FacialExpression.FRIENDLY, "Oh. Right.").also { stage++ }
                12 -> npcl(FacialExpression.FRIENDLY, "So...").also { stage++ }
                13 -> npcl(FacialExpression.FRIENDLY, "Did she say much about me?").also { stage++ }
                14 -> playerl(FacialExpression.FRIENDLY, "Not really.").also { stage++ }
                15 -> npcl(FacialExpression.FRIENDLY, "Oh.").also { stage++ }
                16 -> playerl(FacialExpression.FRIENDLY, "She said you'd got the seeds from the Jade Vine and that she was making it into livid.").also { stage++ }
                17 -> npcl(FacialExpression.FRIENDLY, "Yeah, it stands for Lokar's Infernal Vine of Incredible Death!").also { stage++ }
                18 -> npcl(FacialExpression.FRIENDLY, "Muahahaha!").also { stage++ }
                19 -> playerl(FacialExpression.FRIENDLY, "Why am I not surprised.").also { stage++ }
                20 -> npcl(FacialExpression.FRIENDLY, "I stole them from that nuts Papa Mambo. Wasn't sure what to do with the seeds at the time, they just seemed valuable.").also { stage++ }
                21 -> npcl(FacialExpression.FRIENDLY, "Then, when I was at Lunar Isle, I remembered Pauline and wondered if she might be able to turn it into something truly deadly. Not that I said that to her, obviously.").also { stage++ }
                22 -> playerl(FacialExpression.FRIENDLY, "Obviously.").also { stage++ }
                23 -> npcl(FacialExpression.FRIENDLY, "I told her they could be used to help protect the people of Rellekka.").also { stage++ }
                24 -> playerl(FacialExpression.FRIENDLY, "So, you lied?").also { stage++ }
                25 -> npcl(FacialExpression.FRIENDLY, "Well...at first. Afterwards, I was pleased to find that Brundt would actually pay a hefty fee to get hold of them. Gullible idiot.").also { stage++ }
                26 -> playerl(FacialExpression.FRIENDLY, "I think Pauline would be very disappointed to hear that.").also { stage++ }
                27 -> npcl(FacialExpression.FRIENDLY, "Hey, don't you go telling her!").also { stage++ }
                28 -> playerl(FacialExpression.FRIENDLY, "Okay, okay!").also { stage++ }
                29 -> playerl(FacialExpression.FRIENDLY, "It's probably for the best. She might shut the whole enterprise down.").also { stage++ }
                30 -> playerl(FacialExpression.FRIENDLY, "Exactly!").also { stage++ }
                31 -> npcl(FacialExpression.FRIENDLY, "And, erm, then I won't have my deal with Brundt.").also { stage++ }
                32 -> playerl(FacialExpression.FRIENDLY, "Of course that's the reason.").also { stage++ }
                33 -> options("Well, Pauline is probably itching to see you again.", "Maybe you should leave Pauline alone.").also { stage++ }
                34 -> when (buttonId) {
                    1 -> player("Well, Pauline is probably itching to see you again.").also { stage++ }
                    2 -> player("Maybe you should leave Pauline alone.").also { stage = 37 }
                }
                35 -> playerl(FacialExpression.FRIENDLY, "Go visit her soon.").also { stage++ }
                36 -> npcl(FacialExpression.FRIENDLY, "Okay. Maybe I will.").also { stage = END_DIALOGUE }
                37 -> npcl(FacialExpression.FRIENDLY, "Why?").also { stage++ }
                38 -> playerl(FacialExpression.FRIENDLY, "She's not used to your ways. It will only spell trouble.").also { stage++ }
                39 -> npcl(FacialExpression.FRIENDLY, "Don't worry yourself. Luckily for me, you can't decide what I do.").also { stage = END_DIALOGUE }
                40 -> player("What makes you say that?").also { stage++ }
                41 -> if (!isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS)) {
                    npcl(FacialExpression.FRIENDLY, "You've experience yet to gain. Especially in keeping with the Fremenniks and their trials!").also { stage++ }
                } else {
                    npcl(FacialExpression.NEUTRAL, "Only people who've travelled on quests far from here can talk to me!").also { stage++ }
                }
                42 -> player("Oh.").also { stage = END_DIALOGUE }
                43 -> npcl(FacialExpression.FRIENDLY, "Hey, I knew you seemed cool when I met you ${getAttribute(player, "fremennikname", "fremmyname")}!").also { stage++ }
                44 -> end()
            }

            NPCs.LOKAR_SEARUNNER_4537 -> when (stage) {
                0 -> {
                    if (!isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS)) {
                        npcl(FacialExpression.ANNOYED, "Don't talk to me outerlander! I don't have time for the likes of you!")
                        stage = 41
                    } else {
                        when (buttonId) {
                            1 -> {
                                player("Hi Lokar, can you take me back to your ship?")
                                stage = 3
                            }
                            2 -> {
                                player("It seems you've been charming Pauline Polaris.")
                                stage = 8
                            }
                        }
                    }
                }
                3 -> npcl(FacialExpression.FRIENDLY, "Sheesh! Make your mind up, lady, I'm not a taxi service! Do you want to go or not?").also { stage++ }
                4 -> options("Yes please.", "No thanks.").also { stage++ }
                5 -> when (buttonId) {
                    1 -> player("Yes please.").also { stage = 40 }
                    2 -> player("No thanks. I've changed my mind.").also { stage = 6 }
                }
                6 -> npcl(FacialExpression.FRIENDLY, "You are possibly the most indecisive person I have ever met...").also { stage++ }
                7 -> playerl(FacialExpression.FRIENDLY, "Well, 'bye then.").also { stage = END_DIALOGUE }
                8 -> npcl(FacialExpression.FRIENDLY, "What did you say?").also { stage = 10 }
                10 -> playerl(FacialExpression.FRIENDLY, "Sorry. I mean, she was, erm, telling me a little about your business deal with her farm.").also { stage++ }
                11 -> npcl(FacialExpression.FRIENDLY, "Oh. Right.").also { stage++ }
                12 -> npcl(FacialExpression.FRIENDLY, "So...").also { stage++ }
                13 -> npcl(FacialExpression.FRIENDLY, "Did she say much about me?").also { stage++ }
                14 -> playerl(FacialExpression.FRIENDLY, "Not really.").also { stage++ }
                15 -> npcl(FacialExpression.FRIENDLY, "Oh.").also { stage++ }
                16 -> playerl(FacialExpression.FRIENDLY, "She said you'd got the seeds from the Jade Vine and that she was making it into livid.").also { stage++ }
                17 -> npcl(FacialExpression.FRIENDLY, "Yeah, it stands for Lokar's Infernal Vine of Incredible Death!").also { stage++ }
                18 -> npcl(FacialExpression.FRIENDLY, "Muahahaha!").also { stage++ }
                19 -> playerl(FacialExpression.FRIENDLY, "Why am I not surprised.").also { stage++ }
                20 -> npcl(FacialExpression.FRIENDLY, "I stole them from that nuts Papa Mambo. Wasn't sure what to do with the seeds at the time, they just seemed valuable.").also { stage++ }
                21 -> npcl(FacialExpression.FRIENDLY, "Then, when I was at Lunar Isle, I remembered Pauline and wondered if she might be able to turn it into something truly deadly. Not that I said that to her, obviously.").also { stage++ }
                22 -> playerl(FacialExpression.FRIENDLY, "Obviously.").also { stage++ }
                23 -> npcl(FacialExpression.FRIENDLY, "I told her they could be used to help protect the people of Rellekka.").also { stage++ }
                24 -> playerl(FacialExpression.FRIENDLY, "So, you lied?").also { stage++ }
                25 -> npcl(FacialExpression.FRIENDLY, "Well...at first. Afterwards, I was pleased to find that Brundt would actually pay a hefty fee to get hold of them. Gullible idiot.").also { stage++ }
                26 -> playerl(FacialExpression.FRIENDLY, "I think Pauline would be very disappointed to hear that.").also { stage++ }
                27 -> npcl(FacialExpression.FRIENDLY, "Hey, don't you go telling her!").also { stage++ }
                28 -> playerl(FacialExpression.FRIENDLY, "Okay, okay!").also { stage++ }
                29 -> playerl(FacialExpression.FRIENDLY, "It's probably for the best. She might shut the whole enterprise down.").also { stage++ }
                30 -> playerl(FacialExpression.FRIENDLY, "Exactly!").also { stage++ }
                31 -> npcl(FacialExpression.FRIENDLY, "And, erm, then I won't have my deal with Brundt.").also { stage++ }
                32 -> playerl(FacialExpression.FRIENDLY, "Of course that's the reason.").also { stage++ }
                33 -> options("Well, Pauline is probably itching to see you again.", "Maybe you should leave Pauline alone.").also { stage++ }
                34 -> when (buttonId) {
                    1 -> player("Well, Pauline is probably itching to see you again.").also { stage++ }
                    2 -> player("Maybe you should leave Pauline alone.").also { stage = 37 }
                }
                35 -> playerl(FacialExpression.FRIENDLY, "Go visit her soon.").also { stage++ }
                36 -> npcl(FacialExpression.FRIENDLY, "Okay. Maybe I will.").also { stage = END_DIALOGUE }
                37 -> npcl(FacialExpression.FRIENDLY, "Why?").also { stage++ }
                38 -> playerl(FacialExpression.FRIENDLY, "She's not used to your ways. It will only spell trouble.").also { stage++ }
                39 -> npcl(FacialExpression.FRIENDLY, "Don't worry yourself. Luckily for me, you can't decide what I do.").also { stage = END_DIALOGUE }
                40 -> {
                    end()
                    travel(player, Location(2213, 3794, 0))
                    return true
                }
                41 -> player("What makes you say that?").also { stage++ }
                42 -> if (!isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS)) {
                    npcl(FacialExpression.FRIENDLY, "You've experience yet to gain. Especially in keeping with the Fremenniks and their trials!").also { stage++ }
                } else {
                    npcl(FacialExpression.NEUTRAL, "Only people who've travelled on quests far from here can talk to me!").also { stage++ }
                }
                43 -> player("Oh.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    private fun travel(player: Player, location: Location) {
        if (!hasRequirement(player, "Lunar Diplomacy")) return
        player.lock()
        Pulser.submit(object : Pulse(1, player) {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    1 -> {
                        player.interfaceManager.openOverlay(Component(Components.FADE_TO_BLACK_115))
                    }

                    3 -> PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                    4 -> player.properties.teleportLocation = location
                    5 -> {
                        PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 0))
                        player.interfaceManager.close()
                        player.interfaceManager.closeOverlay()
                        player.unlock()
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LOKAR_SEARUNNER_4536, NPCs.LOKAR_SEARUNNER_4537)
    }

}

// You're not going get very far on Lunar Isle without
// a Seal of Passage. Go and see Brundt if you need
// another one.
//
// I'm just going to Pirates' Cove.
// Oh right, I'll go and get one.
