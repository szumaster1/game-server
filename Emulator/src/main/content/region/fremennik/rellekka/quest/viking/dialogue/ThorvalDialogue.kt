package content.region.fremennik.rellekka.quest.viking.dialogue

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Thorval dialogue.
 */
@Initializable
class ThorvalDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (inInventory(player, Items.CHAMPIONS_TOKEN_3706, 1)) {
            playerl(FacialExpression.HAPPY, "I would like your contract to offer your services as a bodyguard.")
            stage = 215
            return true
        } else if (inInventory(player, Items.WARRIORS_CONTRACT_3710, 1)) {
            playerl(FacialExpression.ASKING, "You didn't take much persuading to 'lower' yourself to a bodyguard.")
            stage = 220
            return true
        } else if (getAttribute(player, "sigmundreturning", false)) {
            playerl(FacialExpression.ASKING, "Is this item for you?")
            stage = 214
            return true
        } else if (getAttribute(player, "sigmund-steps", 0) == 11) {
            playerl(FacialExpression.ASKING, "I don't suppose you have any idea where I could find the token to allow your seat at the champions table?")
            stage = 211
            return true
        } else if (getAttribute(player, "sigmund-steps", 0) == 10) {
            playerl(FacialExpression.ASKING, "I don't suppose you have any idea where I could find a brave and powerful warrior to act as a bodyguard?")
            stage = 200
            return true
        } else if (args.size > 1) {
            npcl(FacialExpression.FRIENDLY, "Hahaha! Well fought outerlander! Now come down from there, you have passed my trial with flying colours!")
            stage = 150
            return true
        } else if (isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS)) {
            playerl(FacialExpression.FRIENDLY, "Howdy Thorvald!")
            stage = 0
            return true
        } else if (getAttribute(player, "fremtrials:thorvald-vote", false)) {
            playerl(FacialExpression.FRIENDLY, "So can I count on your vote at the council of elders now Thorvald?")
            stage = 160
            return true
        } else if (isQuestComplete(player, QuestName.THE_FREMENNIK_TRIALS)) {
            playerl(FacialExpression.HAPPY, "Howdy Thorvald!")
            stage = 250
            return true
        } else if (!player.questRepository.hasStarted(QuestName.THE_FREMENNIK_TRIALS)) {
            npcl(FacialExpression.ANNOYED, "Leave me be, outerlander. I have nothing to say to the likes of you.").also { stage = END_DIALOGUE }
            return true
        } else if (!getAttribute(player, "fremtrials:thorvald-vote", false)) {
            if (getAttribute(player, "fremtrials:warrior-accepted", false)) {
                options("What do I have to do again?", "Who is my opponent?", "Can't I do something else?")
                stage = 100
                return true
            } else {
                playerl(FacialExpression.FRIENDLY, "Hello!")
                stage = 60
                return true
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            //After Fremennik Trials
            0 -> npcl(FacialExpression.FRIENDLY, "And greetings to you too. It is good to see new blood entering the Fremennik; we gain our strength by bringing new warriors into the tribe.").also { stage = END_DIALOGUE }
            //Warrior Trial
            60 -> npcl(FacialExpression.NEUTRAL, "Hello yourself, outerlander. What brings you to dare speak to a mighty Fremennik warrior such as myself?").also { stage++ }
            61 -> playerl(FacialExpression.HALF_ASKING, "Erm... are you a member of the council?").also { stage++ }
            62 -> npcl(FacialExpression.NEUTRAL, "The Fremennik council of elders? Why, of course I am. I am recognised as one the clans mightiest warriors.").also { stage++ }
            63 -> npcl(FacialExpression.HALF_ASKING, "What is it to you outerlander?").also { stage++ }
            64 -> playerl(FacialExpression.NEUTRAL, "Well... I was wondering if you could vote for me to become a Fremennik.").also { stage++ }
            65 -> npcl(FacialExpression.LAUGH, "An outerlander wishes to become a Fremennik!?!? Ha! That is priceless!").also { stage++ }
            66 -> npcl(FacialExpression.NEUTRAL, "Well, let us say that I am not totally against this concept. As a warrior, I appreciate the value of brave and powerful warriors to our clan, and even though you may be an outerlander,").also { stage++ }
            67 -> npcl(FacialExpression.NEUTRAL, "I will not hold this against you if you can prove yourself to be fierce of heart in a combat situation to me.").also { stage++ }
            68 -> playerl(FacialExpression.NEUTRAL, "So how can I prove that? You want to fight me? Come on then, bring it on! Right here, right now, buddy!").also { stage++ }
            69 -> npcl(FacialExpression.LOUDLY_LAUGHING, "Hahahahaha! You certainly show some spirit for an outerlander!").also { stage++ }
            70 -> npcl(FacialExpression.NEUTRAL, "But spirit does not always make a good warrior. It takes both skill and spirit to be so. I have a test that I give all Fremenniks on their path to be a member of the clan.").also { stage++ }
            71 -> npcl(FacialExpression.NEUTRAL, "My test will challenge both your combat prowess and your bravery equally. Should you pass it you will earn my vote at the council, and more importantly my respect for you as a warrior.").also { stage++ }
            72 -> npcl(FacialExpression.HALF_ASKING, "So what say you, outerlander? Are you prepared for the battle?").also { stage++ }
            73 -> options("Yes", "No").also { stage++ }
            74 -> when (buttonId) {
                1 -> playerl(FacialExpression.EVIL_LAUGH, "Am I prepared? I'll show you what combat's all about, you big sissy barbarian type guy!").also { setAttribute(player, "/save:fremtrials:warrior-accepted", true); stage = 80 }
                2 -> playerl(FacialExpression.NEUTRAL, "No thanks, I'm pretty sure that I can find someone else to vote for me.").also { stage = 90 }
            }
            //Yes
            80 -> npcl(FacialExpression.LAUGH, "Hahahahaha! I'm beginning to like you already, outerlander!").also { stage++ }
            81 -> npcl(FacialExpression.NEUTRAL, "Then allow me to present you with my challenge; This ladder here will take you to a place of combat. I have placed a special warrior down there to challenge you.").also { stage++ }
            82 -> npcl(FacialExpression.NEUTRAL, "Battle him to the death, and you will pass my challenge. If at any point you wish to leave combat, simply climb back up the ladder, to leave that place. If you leave you will of course fail the test.").also { stage++ }
            83 -> npcl(FacialExpression.NEUTRAL, "You may retry my test in the future if you fail, but you must stay down there until the death if you wish for my vote at the council. You must defeat him three times to prove that you are worthy.").also { stage++ }
            84 -> npcl(FacialExpression.NEUTRAL, "The fourth time that you fight him will be to the death, so do not show cowardice.").also { stage++ }
            85 -> playerl(FacialExpression.EVIL_LAUGH, "Is that all? It will be easy!").also { stage++ }
            86 -> npcl(FacialExpression.NEUTRAL, "No, there is one more important rule; You may not enter the battleground with any armour or weaponry of any kind.").also { stage++ }

            87 -> npcl(FacialExpression.NEUTRAL, "If you need to place your equipment into your bank account, I recommend that you speak to the seer, who knows a spell that will do that for you.").also { stage = END_DIALOGUE }

            //No thanks
            90 -> npcl(FacialExpression.NEUTRAL, "Hmm, not so brave after all, outerlander? Perhaps it is for the best. I doubt you have what it takes to pass my challenge.").also { stage = END_DIALOGUE }

            //Warrior Trial Accepted Not Completed
            100 -> when (buttonId) {
                1 -> playerl(FacialExpression.HALF_ASKING, "So what do I have to do to earn your vote at the council again?").also { stage = 110 }
                2 -> playerl(FacialExpression.HALF_ASKING, "So, who is my opponent?").also { stage = 120 }
                3 -> playerl(FacialExpression.HALF_ASKING, "I don't really like fighting that much... Isn't there something else I can do to earn your vote at the council of elders?").also { stage = 130 }
            }
            //What do I have to do again?
            110 -> npcl(FacialExpression.NEUTRAL, "I will not offer my vote to anybody whose bravery in combat I do not trust completely. You must go down that ladder and fight your foe to the death.").also { stage = END_DIALOGUE }

            //Who is my opponent?
            120 -> npcl(FacialExpression.NEUTRAL, "Ah, a wise question before entering combat. His name is Koschei the deathless. He is something of a mystery, even to us.").also { stage++ }
            121 -> npcl(FacialExpression.NEUTRAL, "On one of our regular raiding parties, our longship discovered a man in the frozen waters far north-east of here. We took him aboard our ship, thinking he must be dead.").also { stage++ }
            122 -> npcl(FacialExpression.NEUTRAL, "To our amazement he was perfectly healthy, even though he must have been in those deadly icey waters for many weeks.").also { stage++ }
            123 -> npcl(FacialExpression.NEUTRAL, "He has no memory of who he is, or how he came to be there, except for his own name: Koschei. We named him 'The Deathless' because he is seemingly unkillable!").also { stage++ }
            124 -> npcl(FacialExpression.NEUTRAL, "Any combat technique used against him, he learns instantly! He also apparently can heal himself from any wound at will!").also { stage++ }
            125 -> npcl(FacialExpression.NEUTRAL, "When he attacks, his weapon moves like a whirlwind! He can hide his combat level from his opponents at will as well!").also { stage++ }
            126 -> npcl(FacialExpression.NEUTRAL, "He is truly a horribly fierce opponent to face! I am only glad that he has chosen to stay here with us!").also { stage++ }
            127 -> npcl(FacialExpression.NEUTRAL, "The daylight makes him feel weak, so we have built him his own battleground beneath this building, where he can train his fiercesome skills without being disturbed.").also { stage++ }

            128 -> playerl(FacialExpression.LAUGH, "But he can't REALLY be unkillable...").also { stage++ }
            129 -> playerl(FacialExpression.SUSPICIOUS, "...can he?").also { stage++ }
            130 -> npcl(FacialExpression.NEUTRAL, "Some say he cannot die, for he has hidden his heart outside of his body to be kept forever safe in a duck egg.").also { stage++ }
            131 -> npcl(FacialExpression.NEUTRAL, "Others say he has been cursed by the gods to wander this land forever, never knowing any peace in his life, but only combat.").also { stage++ }
            132 -> npcl(FacialExpression.NEUTRAL, "Some claim that the sword he carries is the source of all his power, and if he should lose it, then exactly one minute later, he will turn back into his true form; A weakened, lame, old man.").also { stage++ }
            133 -> playerl(FacialExpression.HALF_ASKING, "And what do you believe?").also { stage++ }
            134 -> npcl(FacialExpression.NEUTRAL, "I believe you shouldn't look a gift horse in the mouth. He is a fearfully powerful warrior, but more importantly; He is on OUR side, not against us.").also { stage++ }
            135 -> npcl(FacialExpression.NEUTRAL, "He is content testing the battle skills of anyone taking their Fremennik trials of manhood, and I am content knowing that should an enemy ever invade").also { stage++ }
            136 -> npcl(FacialExpression.NEUTRAL, "our town, while our warriors are out on a raiding party, Koschei will be able to hold off ANY invader long enough for us to make our return.").also { stage = END_DIALOGUE }

            //Can't I do something else?
            140 -> npcl(FacialExpression.FRIENDLY, "Yes of course outerlander! If you bring me five raw sardines then I will vote for you instead!").also { stage++ }
            141 -> playerl(FacialExpression.EXTREMELY_SHOCKED, "REALLY?!?!?").also { stage++ }
            142 -> npcl(FacialExpression.LOUDLY_LAUGHING, "HAHAHAHAHAHA! No, of course not! You are stupid, even by outerlander standards!").also { stage++ }
            143 -> npcl(FacialExpression.NEUTRAL, "If you want my vote, you must pass my trial. It is as simple as that.").also { stage = END_DIALOGUE }

            //Warrior Trial Completed - after the fight
            150 -> playerl(FacialExpression.NEUTRAL, "But... I don't understand... I did not manage to beat Koschei...").also { stage++ }
            151 -> npcl(FacialExpression.NEUTRAL, "I did not say you had to, outerlander! All I asked was that you fought to the death! And you did! The death was your own!").also { stage++ }
            152 -> npcl(FacialExpression.NEUTRAL, "I was not interested in how strong you are! I was interested in how BRAVE you are!").also { stage++ }
            153 -> npcl(FacialExpression.NEUTRAL, "You fought a superior enemy to your very last breath - THAT is bravery.").also { stage++ }
            154 -> npcl(FacialExpression.NEUTRAL, "I would be honoured to represent you to the council as worthy of being a Fremennik after watching that superb battle!").also { stage = END_DIALOGUE }

            //Warrior Trial Completed
            160 -> npcl(FacialExpression.NEUTRAL, "Absolutely! I watched the entire battle, and was extremely impressed with your bravery in combat!").also { stage = END_DIALOGUE }

            //Sigmund stuff
            200 -> npcl(FacialExpression.ANNOYED, "Know you not who I am outerlander? There are none more brave or powerful than me amongst all the Fremennik!").also { stage++ }
            201 -> npcl(FacialExpression.ANGRY, "The role of bodyguard is below me, as a noble warrior. You might as well ask me to babysit the children!").also { stage++ }
            202 -> playerl(FacialExpression.THINKING, "Is there no way you would do this for me?").also { stage++ }
            203 -> npcl(FacialExpression.ANNOYED, "There is but one way outerlander. Since I was steeled in battle, I have dreamt of earning my place at the Champions Table in the Long Hall.").also { stage++ }
            204 -> npcl(FacialExpression.ANNOYED, "It is a tradition amongst us that the bravest and strongest are honoured with a table of champions to drink and feast all that they can in our Long Hall.").also { stage++ }
            205 -> npcl(FacialExpression.ANNOYED, "Unfortunately, there are only a fixed number of places available at the table, and these places were all filled many moons ago by others.").also { stage++ }
            206 -> npcl(FacialExpression.ANNOYED, "Although my worthiness is undeniable, the only way I may take my place is if one of those already there die, or give up their place to me voluntarily.").also { stage++ }
            207 -> playerl(FacialExpression.THINKING, "So you want me to go kill one of them off for you? Make it look like an accident?").also { stage++ }
            208 -> npcl(FacialExpression.EXTREMELY_SHOCKED, "WHAT? No, no, not at all! I am shocked you would suggest such a thing!").also { stage++ }
            209 -> npcl(FacialExpression.THINKING, "If you can persuade one of the Revellers to give up their Champions' Token to you so that I might take their place, you may have my contract as a bodyguard.").also { stage++ }
            210 -> playerl(FacialExpression.HAPPY, "Okay, I'll see what I can do.").also {
                player.incrementAttribute("sigmund-steps", 1)
                stage = END_DIALOGUE
            }
            211 -> npcl(FacialExpression.HAPPY, "You will need to persuade one of the revellers in the Long Hall to give up their token, and their place, in deference to my own worthiness somehow.").also { stage = END_DIALOGUE }
            214 -> npcl(FacialExpression.ANNOYED, "I'm afraid not, outerlander.").also { stage = END_DIALOGUE }
            215 -> npcl(FacialExpression.ANGRY, "Oh you would, would you outerlander? I have already told you, I will not demean myself with such a baby sitting job until I can sit in the Longhall with pride.").also { stage++ }
            216 -> playerl(FacialExpression.HAPPY, "It's a good thing I have the Champions' Token right here then, isn't it?").also { stage++ }
            217 -> npcl(FacialExpression.HAPPY, "Ah... well this is a different matter. With that token I can claim my rightful place as a champion in the Long hall!").also { stage++ }
            218 -> npcl(FacialExpression.HAPPY, "Here outerlander, I can suffer the indignity of playing babysitter if it means that I can then revel with my warrior equals in the Long Hall afterwards!").also {
                removeItem(player, Items.CHAMPIONS_TOKEN_3706)
                addItemOrDrop(player, Items.WARRIORS_CONTRACT_3710, 1)
                stage++
            }
            219 -> npcl(FacialExpression.HAPPY, "Here outerlander, take this contract; I will fulfill it to my utmost.").also { stage = END_DIALOGUE }
            220 -> npcl(FacialExpression.HAPPY, "You misunderstand, outerlander. Normally I will only battle for a noble cause, but have never been recognised as a true champion here.").also { stage++ }
            221 -> npcl(FacialExpression.HAPPY, "With this Champion's token, I can stand alongside my warrior brethren in the Long Hall, and revel in the glories of past victories together!").also { stage = END_DIALOGUE }
            250 -> npcl(FacialExpression.HAPPY, "And greetings to you too. It is good to see new blood entering the Fremennik; we gain our strength by bringing new warriors into the tribe.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.THORVALD_THE_WARRIOR_1289)
    }

}
