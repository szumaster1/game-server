package content.region.kandarin.quest.tol.dialogue

import org.rs.consts.NPCs
import core.api.getQuestStage
import core.api.setQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Homunculus dialogue.
 */
@Initializable
class HomunculusDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (getQuestStage(player!!, "Tower of Life")) {
            7 -> when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hello?").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Leeet mwwe free. Argyyghh.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "It's okay. I'm here to help you.").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Lieees. Grahhh").also { stage++ }
                4 -> playerl(
                    FacialExpression.FRIENDLY,
                    "No, honest. I helped the alchemists, but I had no idea what their plans involved."
                ).also { stage++ }

                5 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Arggh, so alchemist create me now I trapped for experiements. But still I confused."
                ).also { stage++ }

                6 -> playerl(FacialExpression.FRIENDLY, "About what?").also { stage++ }
                7 -> npcl(FacialExpression.FRIENDLY, "Me see logic, feeeel magic. A mix of the two.").also { stage++ }
                8 -> playerl(FacialExpression.FRIENDLY, "Hmmm. A creature created of logic and magic.").also { stage++ }
                9 -> playerl(FacialExpression.FRIENDLY, "Don't fear. I shall fix your mind!").also { stage++ }
                10 -> npcl(
                    FacialExpression.FRIENDLY,
                    "No so sure it is easy to do. No one canna 'elp. Arghhh."
                ).also { stage++ }

                11 -> playerl(FacialExpression.FRIENDLY, "Watch me.").also { stage++ }
                12 -> npcl(
                    FacialExpression.FRIENDLY,
                    "You must now make sense of the Homunculus's mind. Force him to either follow the line of logic or the line of magic."
                ).also { stage++ }

                13 -> playerl(
                    FacialExpression.FRIENDLY,
                    "You tell me that you're confused, so ask me some questions!"
                ).also { stage++ }

                14 -> npcl(FacialExpression.FRIENDLY, "Ah, please...").also { stage++ }
                15 -> options(
                    "Get some logs and a tinderbox.",
                    "With the aid of 5 fire runes.",
                    "That's impossible! No one can do that!"
                ).also { stage++ }

                16 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Get some logs and a tinderbox.").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "With the aid of 5 fire runes.").also { stage++ }
                    3 -> playerl(FacialExpression.FRIENDLY, "That's impossible! No one can do that!").also { stage++ }
                }

                17 -> options(
                    "Not too sure, I've never seen it happen.",
                    "With the help of the magical dragonstones!",
                    "By ignition of gas in their belly as they exhale."
                ).also { stage++ }

                18 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Not too sure, I've never seen it happen.").also { stage++ }
                    2 -> playerl(
                        FacialExpression.FRIENDLY,
                        "With the help of the magical dragonstones!"
                    ).also { stage++ }

                    3 -> playerl(
                        FacialExpression.FRIENDLY,
                        "By ignition of gas in their belly as they exhale."
                    ).also { stage++ }
                }

                19 -> options(
                    "Runecraft, enchant jewellery, perform alchemy.",
                    "Eat, sleep, nothing that exciting.",
                    "Fletching, Crafting, Smithing."
                ).also { stage++ }

                20 -> when (buttonID) {
                    1 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Runecraft, enchant jewellery, perform alchemy."
                    ).also { stage++ }

                    2 -> playerl(FacialExpression.FRIENDLY, "Eat, sleep, nothing that exciting.").also { stage++ }
                    3 -> playerl(FacialExpression.FRIENDLY, "Fletching, Crafting, Smithing.").also { stage++ }
                }

                21 -> options(
                    "Bury them.",
                    "I'd like to think you wouldn't be carrying bones around.",
                    "Turn them into bananas or peaches!"
                ).also { stage++ }

                22 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Bury them.").also { stage++ }
                    2 -> playerl(
                        FacialExpression.FRIENDLY,
                        "I'd like to think you wouldn't be carrying bones around."
                    ).also { stage++ }

                    3 -> playerl(FacialExpression.FRIENDLY, "Turn them into bananas or peaches!").also { stage++ }
                }

                23 -> options(
                    "I'm not really much of a traveller, sorry.",
                    "Run, run as fast as you can.",
                    "Depends where you are headed, but teleport spells are a safe bet."
                ).also { stage++ }

                24 -> when (buttonID) {
                    1 -> playerl(
                        FacialExpression.FRIENDLY,
                        "I'm not really much of a traveller, sorry."
                    ).also { stage++ }

                    2 -> playerl(FacialExpression.FRIENDLY, "Run, run as fast as you can.").also { stage++ }
                    3 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Depends where you are headed, but teleport spells are a safe bet."
                    ).also { stage++ }
                }

                25 -> options(
                    "Yes, you can make magic potions to boost your skills.",
                    "People mix together ingredients in vials. The nutrients will help you.",
                    "Yes, liquid-filled vials. Big deal."
                ).also { stage++ }

                26 -> when (buttonID) {
                    1 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Yes, you can make magic potions to boost your skills."
                    ).also { stage++ }

                    2 -> playerl(
                        FacialExpression.FRIENDLY,
                        "People mix together ingredients in vials. The nutrients will help you."
                    ).also { stage++ }

                    3 -> playerl(FacialExpression.FRIENDLY, "Yes, liquid-filled vials. Big deal.").also { stage++ }
                }

                27 -> options(
                    "By harnessing the power of the gods!",
                    "Never seen one personally.",
                    "Take a rune stone to an altar and use a talisman."
                ).also { stage++ }

                28 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "By harnessing the power of the gods!").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "Never seen one personally.").also { stage++ }
                    3 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Take a rune stone to an altar and use a talisman."
                    ).also { stage++ }
                }

                29 -> options(
                    "Perhaps. I've never seen it myself, though.",
                    "Yep, you can use the Telekinetic Grab spell.",
                    "Sure. Use your brain to tell someone to move it!"
                ).also { stage++ }

                30 -> when (buttonID) {
                    1 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Perhaps. I've never seen it myself, though."
                    ).also { stage++ }

                    2 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Yep, you can use the Telekinetic Grab spell."
                    ).also { stage++ }

                    3 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Sure. Use your brain to tell someone to move it!"
                    ).also { stage++ }
                }

                31 -> options(
                    "Through the power of alchemy.",
                    "It's beyond me!",
                    "It's a simple case of combining materials."
                ).also { stage++ }

                32 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Through the power of alchemy.").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "It's beyond me!").also { stage++ }
                    3 -> playerl(
                        FacialExpression.FRIENDLY,
                        "It's a simple case of combining materials."
                    ).also { stage++ }
                }

                33 -> options(
                    "You have special powers - no surprise seeing how you were created.",
                    "Coincidence - there is a lot of loose metal around.",
                    "Yeah, they were cool! Nice one."
                ).also { stage++ }

                34 -> when (buttonID) {
                    1 -> playerl(
                        FacialExpression.FRIENDLY,
                        "You have special powers - no surprise seeing how you were created."
                    ).also { stage++ }

                    2 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Coincidence - there is a lot of loose metal around."
                    ).also { stage++ }

                    3 -> playerl(FacialExpression.FRIENDLY, "Yeah, they were cool! Nice one.").also { stage++ }
                }

                35 -> options(
                    "Try some Mining followed by Smithing.",
                    "How about Magic and Runecrafting?",
                    "That's up to you; depends on what you find interesting."
                ).also { stage++ }

                36 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Try some Mining followed by Smithing.").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "How about Magic and Runecrafting?").also { stage++ }
                    3 -> playerl(
                        FacialExpression.FRIENDLY,
                        "That's up to you; depends on what you find interesting."
                    ).also { stage++ }
                }

                37 -> options(
                    "Don't be silly! You'd get burnt!",
                    "Can't see why not, anything is possible.",
                    "Well the sun is not actually there, it's where it used to be!"
                ).also { stage++ }

                38 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Don't be silly! You'd get burnt!").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "Can't see why not, anything is possible.").also { stage++ }
                    3 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Well the sun is not actually there, it's where it used to be!"
                    ).also { stage++ }
                }

                39 -> options(
                    "Everything has a reason, even if you don't know what it is.",
                    "Probably a bit of both.",
                    "Your very existence speaks of mystical forces."
                ).also { stage++ }

                40 -> when (buttonID) {
                    1 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Everything has a reason, even if you don't know what it is."
                    ).also { stage++ }

                    2 -> playerl(FacialExpression.FRIENDLY, "Probably a bit of both.").also { stage++ }
                    3 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Your very existence speaks of mystical forces."
                    ).also { stage++ }
                }

                41 -> options("Magic.", "I'm too laid back to really care, mate.", "Logic.").also { stage++ }
                42 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Magic.").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "I'm too laid back to really care, mate.").also { stage++ }
                    3 -> playerl(FacialExpression.FRIENDLY, "Logic.").also { stage++ }
                }

                43 -> npcl(FacialExpression.FRIENDLY, "Thats it! Make sense now, thank you!").also { stage++ }
                44 -> playerl(FacialExpression.FRIENDLY, "I decided to root for MAGIC/LOGIC").also { stage++ }
                45 -> npcl(
                    FacialExpression.FRIENDLY,
                    "No does matter which you chose. Me needed organise my thoughts. I see there is truth in both."
                ).also { stage++ }

                46 -> playerl(FacialExpression.FRIENDLY, "Okay.").also { stage++ }
                47 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Now we go scare alchemies - they have been wrong to do this. Although I have life, they mustn't go about this again."
                ).also { stage++ }

                48 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Sounds like a good plan. Can you get out of that cage?"
                ).also { stage++ }

                49 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Easy. You run down and I watching from here. I surprise right moment."
                ).also { stage++ }

                50 -> playerl(FacialExpression.FRIENDLY, "Okay.").also { stage = END_DIALOGUE }
            }

            8 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "How are you?").also { stage++ }
                1 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Better. You been mine angel. I now have the understanding I need."
                ).also { stage++ }

                2 -> playerl(FacialExpression.FRIENDLY, "My pleasure.").also { stage++ }
                3 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Please, alchemists you speak, me appear to surprise."
                ).also { stage++ }

                4 -> playerl(
                    FacialExpression.FRIENDLY,
                    "I'm not sure I understand, but okay, I will go have a word with them."
                ).also { stage++ }

                5 -> npcl(FacialExpression.FRIENDLY, "Thank you.").also { stage = END_DIALOGUE }
            }

            9 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "This place is bizarre!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Me do know. Pleased you rescue.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "My pleasure. So what is this place?").also { stage++ }
                3 -> npcl(
                    FacialExpression.FRIENDLY,
                    "They use essence of Guthix power. Create tower pump this from ground. Make me with godly power. Dangerous."
                ).also { stage++ }

                4 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Oh dear, but you're okay now. You have this place to reside in."
                ).also { stage++ }

                5 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Thrank you. For reward you speak me, I makes monsters."
                ).also { stage++ }

                6 -> {
                    end()
                    setQuestStage(player, "Tower of Life", 100)
                }
            }

            100 -> when (stage) {
                0 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Hi there, you mentioned something about creating monsters...?"
                ).also { stage++ }

                1 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Good! I gain know from alchemists and builders. Me make beings."
                ).also { stage++ }

                2 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Interesting. Tell me if I'm right. By the alchemists and builders creating you, you have inherited their combined knowledge in much the same way that a child might inherit the looks of their parents."
                ).also { stage++ }

                3 -> npcl(FacialExpression.FRIENDLY, "Yes, you right!").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "So what do you need me to do?").also { stage++ }
                5 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Inspect symbol of life altars around dungeon. You see item give. Use item on altar. Activate altar to create, you fight."
                ).also { stage++ }

                6 -> playerl(FacialExpression.FRIENDLY, "Okay. Sounds like a challenge.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HOMUNCULUS_5581)
    }
}
