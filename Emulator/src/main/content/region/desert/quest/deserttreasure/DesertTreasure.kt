package content.region.desert.quest.deserttreasure

import core.api.*
import org.rs.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Desert Treasure Quest
 * @author Ovenbreado
 */
@Initializable
class DesertTreasure : Quest(QuestName.DESERT_TREASURE, 45, 44, 3, 440, 0, 1, 15) {

    companion object {
        const val questName = QuestName.DESERT_TREASURE

        /**
         * This is an important varbit as it is literally controlling the
         * Quest varbit (440 Varp 0-14 bits -> 358 Varbit )
         */
        // 10-15-Eblis 1924 shows up other values Eblis disappears.
        // This is tied to the quest.
        const val varbitDesertTreasure = 358

        // Desert Treasure Start
        const val attributeBoughtBeer = "/save:quest:deserttreasure-boughtbeer"
        const val attributeCountMagicLogs = "/save:quest:deserttreasure-countmagiclogs"
        const val attributeCountSteelBars = "/save:quest:deserttreasure-countsteelbars"
        const val attributeCountMoltenGlass = "/save:quest:deserttreasure-countmoltenglass"
        const val attributeCountBones = "/save:quest:deserttreasure-countbones"
        const val attributeCountAshes = "/save:quest:deserttreasure-countashes"
        const val attributeCountCharcoal = "/save:quest:deserttreasure-countcharocal"
        const val attributeCountBloodRune = "/save:quest:deserttreasure-countbloodrune"
        const val varbitMirrors = 392 // Set to 1 to show mirrors, they are cactuses before desert treasure.

        // Blood Diamond
        const val attributeBloodStage = "/save:quest:deserttreasure-bloodstage"
        const val attributeDessousInstance = "quest:deserttreasure-dessousinstance"

        // Smoke Diamond
        const val attributeSmokeStage = "/save:quest:deserttreasure-smokestage"
        const val attributeFareedInstance = "quest:deserttreasure-fareedinstance"
        const val varbitStandingTorchNorthEast = 360 // North East standing torch 6406
        const val varbitStandingTorchSouthEast = 361 // South East standing torch 6408
        const val varbitStandingTorchSouthWest = 362 // South West standing torch 6410
        const val varbitStandingTorchNorthWest = 363 // North West standing torch 6412

        // Ice Diamond
        const val attributeIceStage = "/save:quest:deserttreasure-icestage"
        const val attributeTrollKillCount = "/save:quest:deserttreasure-iciclecount"
        const val attributeKamilInstance = "quest:deserttreasure-kamilinstance"
        const val varbitFrozenFather = 380 // 0-frozen 1-defrosted
        const val varbitFrozenMother = 381 // 0-frozen 1-defrosted
        const val varbitChildReunite =
            382 // 0-frozen 4-reunited 5-reunited 6-varbitfails/ends (This varbit seems to manage ice stages...)

        // 0 - start,  1 - feed the crying child, 2 - fought kamil, 3 - unfreeze dad/mum, 4 - reunite, 5 - all chat complete.
        const val varbitCaveEntrance = 378 //  6446 0 - 6 6441

        // Shadow Diamond
        const val attributeShadowStage = "/save:quest:deserttreasure-shadowstage"
        const val attributeDamisInstance = "quest:deserttreasure-damisinstance"
        const val varbitSmokeDungeonLadder = 393 // Set to 1 to show ladder when wearing ring of visibility.

        // Desert Treasure End
        const val attributeBloodDiamondInserted = "/save:quest:deserttreasure-blooddiamondinserted"
        const val attributeSmokeDiamondInserted = "/save:quest:deserttreasure-smokediamondinserted"
        const val attributeIceDiamondInserted = "/save:quest:deserttreasure-icediamondinserted"
        const val attributeShadowDiamondInserted = "/save:quest:deserttreasure-shadowdiamondinserted"
        const val varbitBloodObelisk = 390
        const val varbitSmokeObelisk = 387
        const val varbitIceObelisk = 389
        const val varbitShadowObelisk = 388

        fun completedAllSubstages(player: Player): Boolean {
            return getSubStage(player, attributeBloodStage) == 100 &&
                    getSubStage(player, attributeSmokeStage) == 100 &&
                    getSubStage(player, attributeIceStage) == 100 &&
                    getSubStage(player, attributeShadowStage) == 100
        }

        fun getSubStage(player: Player, attributeName: String): Int {
            return getAttribute(player, attributeName, 0)
        }

        fun setSubStage(player: Player, attributeName: String, value: Int) {
            return setAttribute(player, attributeName, value)
        }

        fun hasRequirements(player: Player): Boolean {
            return arrayOf(
                hasLevelStat(player, Skills.SLAYER, 10),
                hasLevelStat(player, Skills.FIREMAKING, 50),
                hasLevelStat(player, Skills.MAGIC, 50),
                hasLevelStat(player, Skills.THIEVING, 53),
                isQuestComplete(player, QuestName.THE_DIG_SITE),
                isQuestComplete(player, QuestName.THE_TOURIST_TRAP),
                isQuestComplete(player, QuestName.TEMPLE_OF_IKOV),
                isQuestComplete(player, QuestName.PRIEST_IN_PERIL),
                isQuestComplete(player, QuestName.WATERFALL_QUEST),
                isQuestComplete(player, QuestName.TROLL_STRONGHOLD),
            ).all { it }
        }
    }

    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        var line = 12
        var stage = getStage(player)

        var started = getQuestStage(player, questName) > 0

        if (!started) {
            line(player, "I can start this quest by speaking to !!The Archaeologist??", line++)
            line(player, "who is exploring the !!Bedabin Camp?? South West of the", line++)
            line(player, "!!Shantay Pass??.", line++)
            line(player, "To complete this quest I will need:", line++)
            line(player, "Level 10 Slayer", line++, hasLevelStat(player, Skills.SLAYER, 10))
            line(player, "Level 50 Firemaking", line++, hasLevelStat(player, Skills.FIREMAKING, 50))
            line(player, "Level 50 Magic", line++, hasLevelStat(player, Skills.MAGIC, 50))
            line(player, "Level 53 Thieving", line++, hasLevelStat(player, Skills.THIEVING, 53))
            line(
                player,
                "I must have completed the following quests:",
                line++
            ) // After - I have completed all of the required quests:
            line(player, QuestName.THE_DIG_SITE, line++, isQuestComplete(player,QuestName.THE_DIG_SITE))
            line(player, QuestName.THE_TOURIST_TRAP, line++, isQuestComplete(player, QuestName.THE_TOURIST_TRAP))
            line(player, QuestName.TEMPLE_OF_IKOV, line++, isQuestComplete(player, QuestName.TEMPLE_OF_IKOV))
            line(player, QuestName.PRIEST_IN_PERIL, line++, isQuestComplete(player, QuestName.PRIEST_IN_PERIL))
            line(player, QuestName.WATERFALL_QUEST, line++, isQuestComplete(player, QuestName.WATERFALL_QUEST))
            line(player, QuestName.TROLL_STRONGHOLD, line++, isQuestComplete(player, QuestName.TROLL_STRONGHOLD))
        } else {

            if (stage >= 2) {
                line(player, "I took some etchings of a stone tablet discovered by the", line++, true)
                line(player, "archaeologist in the desert to Terry Balando at the", line++, true)
                line(player, "Varrock digsite.", line++, true)
            } else if (stage >= 1) {
                line(player, "The !!archaeologist?? has given me some !!etchings from a??", line++, false)
                line(player, "!!stone tablet?? that he discovered in the desert somewhere,", line++, false)
                line(player, "and asked me to take it to an archaeological expert called", line++, false)
                line(player, "!!Terry Balando?? at the Varrock !!digsite??.", line++, false)
            }

            if (stage >= 4) {
                line(player, "He made a rough translation, which I returned to the", line++, true)
                line(player, "archaeologist.", line++, true)
            } else if (stage == 3) {
                line(player, "Terry Balando made some quick !!translation notes?? of the", line++, false)
                line(player, "tablet and asked me to return them to the !!archaeologist?? in", line++, false)
                line(player, "the !!Bedabin Camp??.", line++, false)
            } else if (stage == 2) {
                line(player, "I should get the !!translation notes?? from !!Terry Balando??", line++, false)
            }

            if (stage >= 5) {
                line(player, "The archaeologist I met in the desert seemed to think that", line++, true)
                line(player, "there was some hidden treasure in the desert somewhere,", line++, true)
                line(player, "I agreed to help him find it in return for a fifty percent", line++, true)
                line(player, "share of whatever we found.", line++, true)
            } else if (stage == 4) {
                line(player, "I should find out what the !!archaeologist?? has to say about", line++, false)
                line(player, "the !!translation notes??.", line++, false)
            }

            if (stage >= 6) {
                line(player, "I headed South and found a Bandit Camp.", line++, true)
            } else if (stage >= 5) {
                line(player, "I should head South to the !!Bandit Camp?? and try to find out", line++, false)
                line(player, "more about this !!treasure??.", line++, false)
            }

            if (stage >= 7) {
                line(player, "I asked around the Bandit Camp and discovered someone", line++, true)
                line(player, "who was willing to help me find the diamonds, by using a", line++, true)
                line(player, "magic spell and some scrying glasses.", line++, true)
            } else if (stage >= 6) {
                line(player, "After some searching, the barman let slip some", line++, true)
                line(player, "information about the 'Four Diamonds of Azzanadra'.", line++, true)
                line(player, "I should ask around the !!Bandit Camp?? and see if anyone", line++, false)
                line(player, "else has any information about the !!Four Diamonds of??", line++, false)
                line(player, "!!Azzanadra??", line++, false)
            }

            if (stage >= 10 || (stage >= 9 && completedAllSubstages(player))) {
                // This disappears after you find all the diamonds.
            } else if (stage >= 8) {
                line(player, "I brought Eblis the ingredients so that he could cast the", line++, true)
                line(player, "spell to see the places touched by the magic of the", line++, true)
                line(player, "diamonds.", line++, true)
            } else if (stage >= 7) {
                // Crosses out gradually as you SUBMIT them.
                line(player, "To make the scrying glasses I need to bring the following", line++, false)
                // Crosses out with "I have brought him all xxx(6 Steel bars)"
                line(player, "items to Eblis:", line++, false)
                line(player, "!!12 Magic logs??", line++, false)
                line(player, "!!6 Steel bars??", line++, false)
                line(player, "!!6 Molten glass??", line++, false)
                line(player, "To cast the spell I will need to bring Eblis:", line++, false)
                // Crosses out with "I have brought him xxx(some bones)"
                line(player, "!!Some bones??", line++, false)
                line(player, "!!Some ash??", line++, false)
                line(player, "!!Some charcoal??", line++, false)
                line(player, "!!A blood rune??", line++, false)
            }

            if (stage >= 10 || (stage >= 9 && completedAllSubstages(player))) {
                // This disappears after you find all the diamonds.
            } else if (stage >= 9 && !completedAllSubstages(player)) {
                // This was supposed to disappear...  youtu.be/J4cQMY66MI4 is old but disagrees
                line(player, "I headed East into the desert and used the scrying", line++, true)
                line(player, "glasses set up for me there by Eblis to try and find the", line++, true)
                line(player, "Four Diamonds of Azzanadra.", line++, true)
                line++
            } else if (stage >= 8) {
                line(player, "I should head !!East into the desert?? and meet Eblis where he", line++, false)
                line(player, "has set up the scrying glasses, and use them to try and", line++, false)
                line(player, "track down the !!Four Diamonds of Azzanadra??.", line++, false)
                line++
            }


            // The huge one with sub quests.
            if (stage >= 9 && completedAllSubstages(player)) {
                line(player, "I found all four diamonds using this spell.", line++, true)
            } else if (stage == 9) {
                line++
                /**
                 * DIAMOND OF BLOOD (DESSOUS)
                 * @author Ovenbreado
                 */
                // Dessous returns to his grave, bored of toying with you. // https://youtu.be/7CvfS7pypso
                if (getSubStage(player, attributeBloodStage) == 100) {
                    line(player, "I defeated a vampire named Dessous to claim the Diamond", line++, true)
                    line(player, "of Blood.", line++, true)
                } else if (getSubStage(player, attributeBloodStage) >= 1) {
                    // ZSOCwWEwimM 4:25 helped the first line.
                    line(player, "I discovered that the location of the Diamond of Blood was", line++, true)
                    line(player, "somewhere in Morytania, and in the possession of a", line++, true)
                    line(player, "vampire warrior named Dessous.", line++, true)

                    if (getSubStage(player, attributeBloodStage) >= 3) {
                        line(player, "I don't fully trust Malek, but he has agreed to help me kill", line++, true)
                        line(player, "Dessous.", line++, true)

                        line(player, "I made a special blessed pot, filled with blood, garlic and", line++, true)
                        line(player, "spices, which I used to lure Dessous from his tomb.", line++, true)
                        line(player, "I managed to defeat the vampire warrior Dessous, but", line++, true)
                        line(player, "there was no sign of the Diamond of Blood anywhere.", line++, true)
                        line(player, "I should find out what game !!Malek?? has been playing with", line++, false)
                        line(player, "me, and where I can actually find the !!Diamond of Blood??.", line++, false)
                    } else if (getSubStage(player, attributeBloodStage) >= 2) {
                        line(player, "I don't fully trust Malek, but he has agreed to help me kill", line++, true)
                        line(player, "Dessous.", line++, true)

                        // None of these lines cross out when you do it. So it remains like this.
                        line(player, "Apparently I can find an old !!assistant of Count Draynor?? in", line++, false)
                        line(player, "the !!sewers of Draynor Village??, who will be able to help me", line++, false)
                        line(player, "make a !!sacrificial pot??", line++, false)
                        line(
                            player,
                            "I then need to take that !!sacrificial pot?? to !!Entrana?? and get",
                            line++,
                            false
                        )
                        line(player, "it blessed by the !!head priest??", line++, false)
                        line(player, "When I have done that, I should return to !!Malak??, and he will", line++, false)
                        line(player, "provide me with some !!fresh blood??", line++, false)
                        line(
                            player,
                            "I then need to add !!garlic?? and !!spices?? to the pot in order to",
                            line++,
                            false
                        )
                        line(player, "lure Dessous from his tomb.", line++, false)
                        line(player, "When I have done all of this, I must !!kill Dessous!??", line++, false)
                    } else if (getSubStage(player, attributeBloodStage) >= 1) {
                        line(player, "I should speak to !!Malek?? again and find out how exactly I", line++, false)
                        line(player, "can kill !!Dessous??.", line++, false) // This doesn't stay
                    }
                } else if (getSubStage(player, attributeBloodStage) == 0) {
                    line(player, "I can use the !!scrying glasses?? to help find the", line++, false)
                    line(player, "!!Diamond of Blood??.", line++, false)
                }

                line++
                /**
                 * DIAMOND OF SMOKE (FAREED)
                 * @author Ovenbreado
                 */
                // Fareed has lost interest in you, and returned to his flames.
                if (getSubStage(player, attributeSmokeStage) == 100) {
                    line(player, "I defeated a fire warrior, and now have the Diamond of", line++, true)
                    line(player, "Smoke.", line++, true)
                } else if (getSubStage(player, attributeSmokeStage) >= 1) {
                    line(player, "I entered a smokey well and lit up the path. I found a", line++, true) // Derived.
                    line(player, "key in a chest.", line++, true) // Derived.
                    line(player, "I should find out what the !!key?? unlocks.", line++, false) // Derived.
                } else if (getSubStage(player, attributeSmokeStage) == 0) {
                    // This doesn't change to stage 1 even when you are lighting the fires...
                    line(player, "I can use the !!scrying glasses?? to help find the", line++, false)
                    line(player, "!!Diamond of Smoke??.", line++, false)
                }

                line++
                /**
                 * DIAMOND OF ICE (KAMIL)
                 */
                // Kamil vanishes on an icy wind...
                if (getSubStage(player, attributeIceStage) == 100) {
                    line(player, "I defeated a warrior named Kamil, and now have the", line++, true)
                    line(player, "Diamond of Ice.", line++, true)
                } else if (getSubStage(player, attributeIceStage) >= 1) {
                    // https://www.youtube.com/watch?v=F5F6Ds-T1P8 28:52
                    line(player, "I met a crying ice troll child to the North of Trollheim.", line++, true)
                    if (getSubStage(player, attributeIceStage) >= 3) {
                        line(player, "I managed to cheer him up slightly with a sweet treat.", line++, true)
                        line(player, "After speaking with him, I discovered that his parents had", line++, true)
                        line(player, "been hurt by a 'bad man' who had the Diamond of Ice, and I", line++, true)
                        line(player, "agreed to help him rescue them.", line++, true)
                        line(player, "While heading through the icy area, I was attacked by an", line++, true)
                        line(player, "ice warrior named Kamil, and managed to defeat him.", line++, true)
                        line(player, "Was this the 'bad man' the troll child has spoken of?", line++, true)
                        line(player, "I should head further into the icy area to try and find", line++, false)
                        line(player, "them.", line++, false)
                    } else if (getSubStage(player, attributeIceStage) >= 2) {
                        line(player, "I managed to cheer him up slightly with a sweet treat.", line++, true)
                        line(player, "After speaking with him, I discovered that his parents had", line++, true)
                        line(player, "been hurt by a 'bad man' who had the Diamond of Ice, and I", line++, true)
                        line(player, "agreed to help him rescue them.", line++, true)
                        line(player, "I should head further into the icy area to try and find", line++, false)
                        line(player, "them.", line++, false)
                    } else if (getSubStage(player, attributeIceStage) >= 1) {
                        line(player, "I should cheer him up with something sweet.", line++, false) // Derived
                    }
                } else if (getSubStage(player, attributeIceStage) == 0) {
                    line(player, "I can use the !!scrying glasses?? to help find the", line++, false)
                    line(player, "!!Diamond of Ice??.", line++, false)
                }

                line++
                /**
                 * DIAMOND OF SHADOW (DAMIS)
                 */
                // Damis has vanished once more into the shadows...
                if (getSubStage(player, attributeShadowStage) == 100) {
                    line(player, "I defeated a warrior named Damis, and now have the", line++, true)
                    line(player, "Diamond of Shadow.", line++, true)
                } else if (getSubStage(player, attributeShadowStage) >= 1) {
                    line(player, "A travelling merchant named Rasolo had some information", line++, true)
                    line(player, "about the Diamond of Shadow.", line++, true)
                    line(player, "Apparently it was owned by an invisible warrior, who I", line++, true)
                    line(player, "needed a special ring to see.", line++, true)
                    line(player, "Rasolo owned such a ring, but would only trade it in return", line++, true)
                    line(player, "for a gilded cross stolen from him by a bandit named.", line++, true)
                    line(player, "Laheeb.", line++, true)
                    if (getSubStage(player, attributeShadowStage) >= 3) {
                        line(player, "I found Laheeb's treasure chest, and managed to bypass", line++, true)
                        line(player, "the traps on it to take the gilded cross, which I returned to", line++, true)
                        line(player, "Rasolo.", line++, true)
                        line(player, "In return, he gave me the Ring of Visibility.", line++, true)
                        line(player, "I should put the !!Ring of Visibility?? on and try and find the", line++, false)
                        line(player, "hidden home of !!Damis?? - Rasolo suggested it was very", line++, false)
                        line(player, "close by to where he is...", line++, false)
                    } else if (getSubStage(player, attributeShadowStage) >= 2) {
                        line(player, "I found Laheeb's treasure chest, and managed to bypass", line++, true)
                        line(player, "the traps on it to take the gilded cross.", line++, true)
                        line(player, "I need to return the !!gilded cross?? to !!Rasolo??.", line++, false) // Derived
                    } else if (getSubStage(player, attributeShadowStage) >= 1) {
                        line(player, "I need to find !!Laheeb's loot?? and retrieve the stolen !!gilded??", line++, false)
                        line(player, "!!cross??.", line++, false)
                    }
                } else if (getSubStage(player, attributeShadowStage) == 0) {
                    line(player, "I can use the !!scrying glasses?? to help find the", line++, false)
                    line(player, "!!Diamond of Shadow??.", line++, false)
                }
            }

            // If you hold a diamond too long a stranger will try to kill you.
            // https://oldschool.runescape.wiki/w/Transcript:Stranger
            // After defeating the stranger
            // player(THINKING, "I wonder what that was all about?")

            // After all 4 diamonds
            // Player dialogue("I should make sure I have all four diamonds with me", "before speaking to Eblis again.")

            if (stage >= 10) {
                // This disappears after you place all the diamonds.
            } else if (stage >= 9 && completedAllSubstages(player)) {
                line(player, "Now that I have recovered all of the !!Diamonds of??", line++, false)
                line(player, "!!Azzanadra?? I should take them all to !!Eblis?? and find out what.", line++, false)
                line(player, "is so special about them.", line++, false)
                // This is the current message even AFTER you speak to Eblis... baQ6oJOk7Gc
            }

            if (stage >= 11) {
                // This disappears at the end of this quest.
            } else if (stage >= 10) {
                line(player, "I should explore the !!pyramid?? and see what !!treasure?? awaits", line++, false)
                line(player, "me!", line++, false)
            }

            if (stage >= 100) {
                line(player, "At the heart of the pyramid I found a strange being, who", line++, true)
                line(player, "gave me some powerful new magic spells.", line++, true)
                line(player, "I can switch between my old spells and my new spells any", line++, true)
                line(player, "time by using the altar there, and can avoid the traps by", line++, true)
                line(player, "using the secret passage.", line++, true)
                line++
                line++
                line(player, "<col=FF0000>QUEST COMPLETE!</col>", line)
            }
        }
    }

    override fun reset(player: Player) {
        setVarbit(player, varbitChildReunite, 0, true)

        removeAttribute(player, attributeBoughtBeer)
        removeAttribute(player, attributeCountMagicLogs)
        removeAttribute(player, attributeCountSteelBars)
        removeAttribute(player, attributeCountMoltenGlass)
        removeAttribute(player, attributeCountBones)
        removeAttribute(player, attributeCountAshes)
        removeAttribute(player, attributeCountCharcoal)
        removeAttribute(player, attributeCountBloodRune)
        setVarbit(player, varbitMirrors, 0, true)

        removeAttribute(player, attributeBloodStage)
        removeAttribute(player, attributeDessousInstance)

        removeAttribute(player, attributeSmokeStage)
        removeAttribute(player, attributeFareedInstance)
        setVarbit(player, varbitStandingTorchNorthEast, 0, true)
        setVarbit(player, varbitStandingTorchSouthEast, 0, true)
        setVarbit(player, varbitStandingTorchSouthWest, 0, true)
        setVarbit(player, varbitStandingTorchNorthWest, 0, true)

        removeAttribute(player, attributeIceStage)
        removeAttribute(player, attributeKamilInstance)
        removeAttribute(player, attributeTrollKillCount)
        setVarbit(player, varbitFrozenFather, 0, true) // 0-frozen 1-defrosted
        setVarbit(player, varbitFrozenMother, 0, true) // 0-frozen 1-defrosted
        setVarbit(player, varbitChildReunite, 0, true) // 0-frozen 4-reunited 5-reunited 6-varbitfails/ends (This varbit seems to manage ice stages...)
        setVarbit(player, varbitCaveEntrance, 0, true) //  6446 0 - 6 6441

        removeAttribute(player, attributeShadowStage)
        removeAttribute(player, attributeDamisInstance)
        setVarbit(player, varbitSmokeDungeonLadder, 0, true) // Set to 1 to show ladder when wearing ring of visibility.

        removeAttribute(player, attributeBloodDiamondInserted)
        removeAttribute(player, attributeSmokeDiamondInserted)
        removeAttribute(player, attributeIceDiamondInserted)
        removeAttribute(player, attributeShadowDiamondInserted)
        setVarbit(player, varbitBloodObelisk, 0, true)
        setVarbit(player, varbitSmokeObelisk, 0, true)
        setVarbit(player, varbitIceObelisk, 0, true)
        setVarbit(player, varbitShadowObelisk, 0, true)
    }


    override fun finish(player: Player) {
        var ln = 10
        super.finish(player)
        player.packetDispatch.sendString("You have completed the Desert Treasure Quest!", 277, 4)
        player.packetDispatch.sendItemZoomOnInterface(Items.ANCIENT_STAFF_4675, 240, 277, 5)

        drawReward(player, "3 Quest Points", ln++)
        drawReward(player, "20,000 Magic XP", ln++)
        drawReward(player, "Ancient Magicks", ln)

        player.skills.addExperience(Skills.MAGIC, 20000.0)
    }

    override fun setStage(player: Player, stage: Int) {
        super.setStage(player, stage)
        this.updateVarps(player)
    }

    override fun updateVarps(player: Player) {

        // Ice: Cave Entrance Varbit
        setVarbit(player, varbitCaveEntrance, getAttribute(player, attributeTrollKillCount, 0))

        // Shadow: Ring of Visibility Ladder Varbit
        if (inEquipment(player, Items.RING_OF_VISIBILITY_4657)) {
            setVarbit(player, varbitSmokeDungeonLadder, 1)
        } else {
            setVarbit(player, varbitSmokeDungeonLadder, 0)
        }

        // Obelisks Varbits
        if (getAttribute(player, attributeBloodDiamondInserted, 0) == 1) {
            setVarbit(player, varbitBloodObelisk, 1)
        } else {
            setVarbit(player, varbitBloodObelisk, 0)
        }

        if (getAttribute(player, attributeSmokeDiamondInserted, 0) == 1) {
            setVarbit(player, varbitSmokeObelisk, 1)
        } else {
            setVarbit(player, varbitSmokeObelisk, 0)
        }

        if (getAttribute(player, attributeIceDiamondInserted, 0) == 1) {
            setVarbit(player, varbitIceObelisk, 1)
        } else {
            setVarbit(player, varbitIceObelisk, 0)
        }

        if (getAttribute(player, attributeShadowDiamondInserted, 0) == 1) {
            setVarbit(player, varbitShadowObelisk, 1)
        } else {
            setVarbit(player, varbitShadowObelisk, 0)
        }

        // Stage Varbits
        if (getQuestStage(player, questName) == 0) {
            setVarbit(player, varbitDesertTreasure, 0, true)
            setVarbit(player, varbitMirrors, 0, true)
        }
        if (getQuestStage(player, questName) in 1..7) {
            setVarbit(player, varbitDesertTreasure, 1, true)
            setVarbit(player, varbitMirrors, 0, true)
        }
        if (getQuestStage(player, questName) in 8..9) {
            setVarbit(player, varbitDesertTreasure, 10, true)
            setVarbit(player, varbitMirrors, 1, true)
        }
        if (getQuestStage(player, questName) == 10) {
            setVarbit(player, varbitDesertTreasure, 13, true)
            setVarbit(player, varbitMirrors, 1, true)
        }
        if (getQuestStage(player, questName) >= 100) {
            setVarbit(player, varbitDesertTreasure, 15, true)
            setVarbit(player, varbitMirrors, 1, true)
        }
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }
}