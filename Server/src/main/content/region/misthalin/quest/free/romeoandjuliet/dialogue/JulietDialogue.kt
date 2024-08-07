package content.region.misthalin.quest.free.romeoandjuliet.dialogue

import content.region.misthalin.quest.free.romeoandjuliet.cutscene.JulietCutscene
import core.api.consts.Items
import core.api.sendItemDialogue
import core.game.activity.ActivityManager
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.path.Pathfinder
import core.game.world.repository.Repository.findNPC
import core.game.world.update.flag.context.Animation
import core.tools.END_DIALOGUE

/**
 * Juliet dialogue.
 */
class JulietDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null
    private var cutscene: JulietCutscene? = null

    override fun open(vararg args: Any): Boolean {
        quest = player.getQuestRepository().getQuest("Romeo & Juliet")
        npc = args[0] as NPC
        if (args.size > 1) {
            cutscene = args[1] as JulietCutscene
            npc("Oh, here's Phillipa, my cousin...she's in on the plot too!")
            stage = 2000
            return true
        }
        stage = when (quest!!.getStage(player)) {
            0 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "Romeo, Romeo, wherefore art thou Romeo? Bold",
                    "adventurer, have you seen Romeo on your travels?",
                    "Skinny guy, a bit wishy washy, head full of poetry."
                )
                0
            }

            10 -> {
                player(
                    FacialExpression.HALF_GUILTY,
                    "Juliet, I come from Romeo.",
                    "He begs me to tell you that he cares still."
                )
                700
            }

            20 -> {
                player(FacialExpression.HALF_GUILTY, "Hello Juliet!")
                100
            }

            30 -> {
                player(
                    FacialExpression.HALF_GUILTY,
                    "Hi Juliet, I have passed your message on to",
                    "Romeo...he's scared half out of his wits at the news that",
                    "your father wants to kill him."
                )
                900
            }

            40 -> {
                player(
                    "Hi Juliet, I've found Father Lawrence and he has a",
                    "cunning plan. But for it to work, I need to seek the",
                    "Apothecary!"
                )
                236
            }

            50 -> {
                player("Hi Juliet!")
                656
            }

            60 -> if (!player.inventory.contains(756, 1)) {
                player(
                    "Hi Juliet! I have an interesting proposition for",
                    "you...suggested by Father Lawrence. It may be the",
                    "only way you'll be able to escape from this house and",
                    "be with Romeo."
                )
                950
            } else {
                player(
                    "Hi Juliet! I have an interesting proposition for",
                    "you...suggested by Father Lawrence. It may be the",
                    "only way you'll be able to escape from this house and",
                    "be with Romeo."
                )
                950
            }

            70 -> {
                npc("Quickly! Go and tell Romeo the plan!")
                1002
            }

            else -> {
                npc(FacialExpression.ANGRY, "Oh, Romeo, that no good scoundrel!")
                22
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest("Romeo & Juliet")
        val phil = if (cutscene != null) cutscene!!.phillipia else findNPC(3325)!!
        val dad = if (cutscene != null) cutscene!!.npcs[2] else findNPC(3324)!!
        when (stage) {
            2000 -> {
                npc("She's going to make it seem even more convincing!")
                stage = 2001
            }

            2001 -> {
                interpreter.sendDialogues(phil,FacialExpression.HALF_GUILTY, "Yes, I'm quite the actress! Good luck dear cousin!")
                stage = 2002
            }

            2002 -> {
                npc("Right...bottoms up!")
                stage = 2003
            }

            2003 -> {
                close()
                Pulser.submit(object : Pulse(1) {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            0 -> npc.animate(DRINK_ANIM)
                            2 -> {
                                npc(FacialExpression.THINKING, "Urk!")
                                stage = 2004
                                return true
                            }
                        }
                        return false
                    }
                })
            }

            2004 -> {
                close()
                npc.animate(Animation(836))
                Pulser.submit(object : Pulse(1) {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            2 -> {
                                interpreter.sendDialogues(phil, FacialExpression.THINKING, "Oh no...Juliet has...died!")
                                stage = 2005
                                return true
                            }
                        }
                        return false
                    }
                })
            }

            2005 -> {
                player(
                    FacialExpression.HALF_GUILTY,
                    "You might be more believable if you're not smiling when",
                    "you say it..."
                )
                stage = 2006
            }

            2006 -> {
                interpreter.sendDialogues(
                    phil,
                    FacialExpression.HALF_GUILTY,
                    "Oh yeah...you might be right...ok, let's try again."
                )
                stage = 2007
            }

            2007 -> {
                interpreter.sendDialogues(phil, FacialExpression.HALF_GUILTY, "Oh no...Juliet has...died?")
                stage = 2008
            }

            2008 -> {
                player(
                    FacialExpression.HALF_GUILTY,
                    "Perhaps a bit louder, like you're upset...that your cousin",
                    "has died!"
                )
                stage = 2009
            }

            2009 -> {
                interpreter.sendDialogues(
                    phil,
                    FacialExpression.HALF_GUILTY,
                    "Right...yes...Ok, ok, I think I'm getting my motivation",
                    "now. Ok, let's try this again!"
                )
                stage = 2010
            }

            2010 -> {
                interpreter.sendDialogues(
                    phil,
                    FacialExpression.FURIOUS,
                    "OH NO...JULIET HAS...DIED?....",
                    "Oooooohhhhhh....(sob), (sob).Juliet...my poor dead cousin!"
                )
                stage = 2011
            }

            2011 -> {
                interpreter.sendDialogues(dad,FacialExpression.HALF_GUILTY, "What's all that screaming?")
                stage = 2012
            }

            2012 -> {
                val path = Pathfinder.find(dad, player.location.transform(0, 1, 0))
                path.walk(dad)
                interpreter.sendDialogues(dad,FacialExpression.HALF_GUILTY, "Oh no! My poor daughter...what has become of you?")
                stage = 2013
            }

            2013 -> {
                interpreter.sendDialogues(
                    phil,
                    FacialExpression.FURIOUS,
                    "Poor Juliet...make preparations for her body to be",
                    "placed in the Crypt..."
                )
                stage = 2014
            }

            2014 -> if (player.inventory.remove(POTION)) {
                quest.setStage(player, 70)
                cutscene!!.stop(true)
                end()
            }

            0 -> {
                options(
                    "Yes I've met him.",
                    "No, I think I'd have remembered if I'd met him.",
                    "I guess I could look for him for you."
                )
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Yes I've met him.")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "No, I think I'd have remembered if I'd met him."
                    )
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "I guess I could look for him for you."
                    )
                    stage = 30
                }
            }

            20 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "Oh, well that's a shame, I was hopping that you might",
                    "deliver a message to him for me."
                )
                stage = 21
            }

            21 -> {
                player(
                    FacialExpression.HALF_GUILTY,
                    "Sorry, but I don't even know what he looks like."
                )
                stage = END_DIALOGUE
            }

            30 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "Oh, that would be so wonderful of you!",
                    "I'd be most grateful if you could please deliver a",
                    "message to him?"
                )
                stage = 31
            }

            31 -> {
                player(FacialExpression.HALF_GUILTY, "Certainly, I'll do it straight away.")
                stage = 32
            }

            32 -> {
                npc(FacialExpression.HALF_GUILTY, "Many thanks! Oh, i'm so very grateful. You may be", "our only hope.")
                stage = 33
            }

            10 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "I'd be most grateful if you could please deliver a",
                    "message to him?"
                )
                stage = 31
            }

            33 -> {
                if (!player.inventory.add(Item(755))) {
                    GroundItemManager.create(GroundItem(Item(755), npc.location, player))
                }
                quest.setStage(player, 20)
                player.getQuestRepository().syncronizeTab(player)
                interpreter.sendItemMessage(755, "Juliet gives you a message.")
                stage = 34
            }

            34 -> end()
            100 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "Hello there...have you delivered the message to Romeo",
                    "yet? What news do you have from my loved one?"
                )
                stage = 101
            }

            101 -> if (!player.inventory.contains(755, 1) && !player.bank.contains(755, 1)) {
                player(
                    FacialExpression.HALF_GUILTY,
                    "Hmmm, that's the thing about messages....they're so easy",
                    "to misplace..."
                )
                stage = 105
            } else {
                player(FacialExpression.HALF_GUILTY, "Oh, sorry, I've not had a chance to deliver it yet!")
                stage = 102
            }

            102 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "Oh, that's a shame. I've been waiting so patiently to",
                    "hear some word from him."
                )
                stage = 103
            }

            103 -> end()
            105 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "How could you lose that message?",
                    "It was incredibly important...and it took me an age to",
                    "write! I used joined up writing and everything!"
                )
                stage = 106
            }

            106 -> {
                npc(FacialExpression.HALF_GUILTY, "Please, take this new message to him,", "and please don't loose it.")
                stage = 107
            }

            107 -> {
                if (!player.inventory.add(Item(755))) {
                    GroundItemManager.create(GroundItem(Item(755), npc.location, player))
                }
                quest.setStage(player, 20)
                player.getQuestRepository().syncronizeTab(player)
                interpreter.sendItemMessage(755, "Juliet gives you another message.")
                stage = END_DIALOGUE
            }

            900 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "yes, unfortunately my father is quite the hunter, you",
                    "may have seen some the animal head trophies on the",
                    "wall. And it would be so awful to see Romeo's head up",
                    "there with them!"
                )
                stage = 901
            }

            901 -> {
                player(FacialExpression.HALF_GUILTY, "I know what you mean...")
                stage = 902
            }

            902 -> {
                player(
                    FacialExpression.HALF_GUILTY,
                    "...this hair colour will clash terribly with the rest of the",
                    "decoration."
                )
                stage = 903
            }

            903 -> {
                npc(FacialExpression.HALF_GUILTY, "That's not what I was suggesting at all...")
                stage = 904
            }

            904 -> {
                player(FacialExpression.HALF_GUILTY, "I know, I know...I was just kidding.")
                stage = 905
            }

            905 -> {
                player(
                    "Anyway, don't worry because I'm on the case. I'm",
                    "going to get some help from Father Lawrence."
                )
                stage = 906
            }

            906 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "Oh yes, I'm sure that Father Lawrence will come up",
                    "with a solution. I hope you find him soon."
                )
                stage = END_DIALOGUE
            }

            700 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "Oh how my heart soars to hear this news! Please take",
                    "this message to him with great haste."
                )
                stage = 701
            }

            701 -> {
                player(
                    FacialExpression.HALF_GUILTY,
                    "Well, I hope it's good news...he was quite upset when I",
                    "left him."
                )
                stage = 702
            }

            702 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "He's quite often upset...the poor sensitive soul. But I",
                    "don't think he's going to take this news very well,",
                    "however, all is not lost."
                )
                stage = 703
            }

            703 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "Everything is explained in the letter, would you be so",
                    "kind and deliver it to him please?"
                )
                stage = 704
            }

            704 -> {
                player(FacialExpression.HALF_GUILTY, "Certainly, I'll do so straight away.")
                stage = 705
            }

            705 -> {
                npc(
                    FacialExpression.HALF_GUILTY,
                    "Many thanks! Oh, I'm so very grateful. You may be",
                    "our only hope."
                )
                stage = 706
            }

            706 -> {
                if (!player.inventory.add(Item(755))) {
                    GroundItemManager.create(GroundItem(Item(755), npc.location, player))
                }
                quest.setStage(player, 20)
                player.getQuestRepository().syncronizeTab(player)
                interpreter.sendItemMessage(755, "Juliet gives you a message.")
                stage = 707
            }

            707 -> end()
            236 -> {
                npc(
                    "Oh good! I knew Father Lawrence would come up with",
                    "something. However, I don't know where the apothecary",
                    "is...I hope you find him soon. My father's temper gets",
                    "no better."
                )
                stage = END_DIALOGUE
            }

            656 -> {
                npc("Hi " + player.username + ", how close am I to being with my true", "love Romeo?")
                stage = 657
            }

            657 -> {
                player("Sorry, I still have to get a speical potion for you.")
                stage = 658
            }

            658 -> {
                npc(
                    "Oh, I hope it isn't a love potion because you would be",
                    "wasting your time. My love for Romeo grows stronger",
                    "every minute..."
                )
                stage = 659
            }

            659 -> {
                player("That must be because you're not with him...")
                stage = 660
            }

            660 -> {
                npc("Oh no! I long to be close to my true love Romeo!")
                stage = 661
            }

            661 -> {
                player(
                    "Well, ok then...I'll set about getting this potion as quickly",
                    "as I can!"
                )
                stage = 662
            }

            662 -> {
                npc("Fair luck to you, the end is close.")
                stage = 663
            }

            663 -> end()
            950 -> {
                npc("Go on....")
                stage = 951
            }

            951 -> if (!player.inventory.containsItem(POTION)) {
                player("Let me go get the potion..")
                stage = 663
            } else {
                player(
                    "I have a Cadava potion here, suggested by Father",
                    "Lawrence. If you drink it, it will make you appear dead!"
                )
                stage = 952
            }

            952 -> {
                npc("Yes...")
                stage = 953
            }

            953 -> {
                player("And when you appear dead...your still and lifeless", "corpse will be removed to the crypt!")
                stage = 954
            }

            954 -> {
                npc("Oooooh, a cold dark creepy crypt...")
                stage = 955
            }

            955 -> {
                npc("...sounds just peachy!")
                stage = 956
            }

            956 -> {
                player(
                    "Then...Romeo can steal into the crypt and rescure you",
                    "just as you wake up!"
                )
                stage = 957
            }

            957 -> {
                npc("...and this is the great idea for getting me out of here?")
                stage = 958
            }

            958 -> {
                player(
                    "To be fair, I can't take all the credit...in fact...it was all",
                    "Father Lawrence's suggestion..."
                )
                stage = 959
            }

            959 -> {
                npc("Ok...if this is the best we can do...hand over the potion!")
                stage = 960
            }

            960 -> {
                sendItemDialogue(player, Items.CADAVA_POTION_756, "You pass the suspicious potion to Juliet.")
                stage = 961
            }

            961 -> {
                npc(
                    "Wonderful! I just hope Romeo can remember to get",
                    "me from the crypt."
                )
                stage = 962
            }

            962 -> {
                npc(
                    "Please go to Romeo and make sure he understands.",
                    "Although I love his gormless, lovelorn soppy ways, he",
                    "can be a bit dense sometimes and I don't want to wake",
                    "up in that crypt on my own."
                )
                stage = 963
            }

            963 -> {
                npc(
                    "Before I swig this potion down, let me stand on the",
                    "balcony so that I might see the sun one last time before",
                    "I am commited to the crypt."
                )
                stage = 964
            }

            964 -> {
                end()
                ActivityManager.start(player, "Juliet Cutscene", false)
            }

            965 -> {
                npc("She's going to make it seem even more convincing!")
                stage = 966
            }

            966 -> {
                interpreter.sendDialogues(phil,FacialExpression.HALF_GUILTY, "Yes, I'm quite the actress! Good luck dear cousin!")
                stage = 967
            }

            967 -> {
                npc("Right...buttoms up!")
                stage = 968
            }

            968 -> {
                npc.animate(npc.properties.deathAnimation)
                npc("Urk!")
                stage = 969
            }

            969 -> {
                interpreter.sendDialogues(phil,FacialExpression.HALF_GUILTY, "Oh no...Juliet has...died!")
                stage = 970
            }

            970 -> {
                player("You might be more believable if you're not smiling when", "you say it...")
                stage = 971
            }

            971 -> {
                interpreter.sendDialogues(phil,FacialExpression.HALF_GUILTY, "Oh yeah...you might be right...ok, let's try again.")
                stage = 972
            }

            972 -> {
                interpreter.sendDialogues(phil,FacialExpression.HALF_GUILTY, "Oh no...Juliet has...died?")
                stage = 973
            }

            973 -> {
                player("Perhaps a bit louder, like you're upset...that your cousin", "has died!")
                stage = 974
            }

            974 -> {
                interpreter.sendDialogues(phil,FacialExpression.HALF_GUILTY,"Right...yes...Ok, ok, I think I'm getting my motivation",
                    "now. Ok, let's try this again!"
                )
                stage = 975
            }

            975 -> {
                interpreter.sendDialogues(phil,FacialExpression.SAD,"OH NO...JULIET HAS...DIED?...",
                    "Ooooooohhhhhh...(sob), (sob).Juliet...my poor dead cousin!"
                )
                stage = 976
            }

            976 -> {
                interpreter.sendDialogues(dad,FacialExpression.HALF_GUILTY,"What's all that screaming?")
                stage = 977
            }

            977 -> {
                interpreter.sendDialogues(dad,FacialExpression.SAD, "Oh no! My poor daughter...what has become of you?")
                stage = 978
            }

            978 -> interpreter.sendDialogues(phil,FacialExpression.HALF_GUILTY,"Poor Juliet...make preparations for her body to be",
                "placed in the Crypt..."
            ).also { stage = END_DIALOGUE }

            1002 -> player("I'm on my way!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(637)
    }

    companion object {
        private val POTION = Item(756)
        private val DRINK_ANIM = Animation(1327)
    }
}
