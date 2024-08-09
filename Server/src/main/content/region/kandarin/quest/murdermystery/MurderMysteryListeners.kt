package content.region.kandarin.quest.murdermystery

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.tools.RandomFunction

/**
 * Murder mystery listeners.
 */
class MurderMysteryListeners : InteractionListener {

    override fun defineListeners() {

        class PaperInSackDialogue : DialogueFile() {
            override fun handle(componentID: Int, buttonID: Int) {
                when (stage) {
                    0 -> sendDialogue(player!!, "There's some flypaper in there. Should I take it?").also { stage = 1 }
                    1 -> options("Yes. it might be useful.", "No, I don't see any need for it.").also { stage = 2 }
                    2 -> when (buttonID) {
                        1 -> addItem(player!!, Items.FLYPAPER_1811, 1).also { stage = 3 }
                        2 -> end()
                    }
                    3 -> sendDialogue(player!!, "You take a piece of flypaper. There is still plenty of fly paper left. Should I take it?").also { stage = 1 }
                }
            }
        }

        // Roll guilty person.
        on(Scenery.SMASHED_WINDOW_26110, IntType.SCENERY, "investigate") { player, _ ->
            if (getQuestStage(player, "Murder Mystery") == 1) {
                val caseNumber = if (RandomFunction.getRandom(3) == 1) {
                    sendDialogue(player, "Some thread seems to have been caught on a loose nail on the window.")
                    1
                } else if (RandomFunction.getRandom(3) == 2) {
                    sendDialogue(player, "Some thread seems to have been caught on a loose nail on the window.")
                    2
                } else {
                    sendDialogue(player, "Some thread seems to have been caught on a loose nail on the window.")
                    3
                }
                when {
                    (caseNumber) == 1 -> {
                        if (!inInventory(player, Items.CRIMINALS_THREAD_1808, 1)) {
                            setAttribute(player, "/save:${MysteryUtils.ATTRIBUTE_ELIZABETH}", true)
                            addItem(player, Items.CRIMINALS_THREAD_1808)
                            MysteryUtils.initialSuspects(player)
                        }
                    }

                    (caseNumber) == 2 -> {
                        if (!inInventory(player, Items.CRIMINALS_THREAD_1809, 1)) {
                            setAttribute(player, "/save:${MysteryUtils.ATTRIBUTE_ANNA}", true)
                            addItem(player, Items.CRIMINALS_THREAD_1809)
                            MysteryUtils.initialSuspects(player)
                        }
                    }

                    else -> {
                        if (!inInventory(player, Items.CRIMINALS_THREAD_1810, 1)) {
                            setAttribute(player, "/save:${MysteryUtils.ATTRIBUTE_DAVID}", true)
                            addItem(player, Items.CRIMINALS_THREAD_1810)
                            MysteryUtils.initialSuspects(player)
                        }
                    }
                }
            }
            return@on true
        }

        on(MysteryUtils.MANSION_OBJECTS, IntType.SCENERY, "search") { player, node ->
            when (node.id) {
                Scenery.ANNA_S_BARREL_2656 -> {
                    if (!inInventory(player, Items.SILVER_NECKLACE_1796, 1)) {
                        sendDialogue(player, "There's something shiny hidden at the bottom. You take Anna's silver necklace.")
                        addItem(player, Items.SILVER_NECKLACE_1796)
                    } else {
                        sendMessage(player, "You search the Anna's barrel but find nothing.")
                    }
                }

                Scenery.BOB_S_BARREL_2657 -> {
                    if (!inInventory(player, Items.SILVER_CUP_1798, 1)) {
                        sendDialogue(player, "There's something shiny hidden at the bottom. You take Bob's silver cup.")
                        addItem(player, Items.SILVER_CUP_1798)
                    } else {
                        sendMessage(player, "You search the Bob's barrel but find nothing.")
                    }
                }

                Scenery.CAROL_S_BARREL_2658 -> {
                    if (!inInventory(player, Items.SILVER_BOTTLE_1800, 1)) {
                        sendDialogue(player, "There's something shiny hidden at the bottom. You take Carol's silver bottle.")
                        addItem(player, Items.SILVER_BOTTLE_1800)
                    } else {
                        sendMessage(player, "You search the Carol's barrel but find nothing.")
                    }
                }

                Scenery.DAVID_S_BARREL_2659 -> {
                    if (!inInventory(player, Items.SILVER_BOOK_1802, 1)) {
                        sendDialogue(
                            player,
                            "There's something shiny hidden at the bottom. You take David's silver book."
                        )
                        addItem(player, Items.SILVER_BOOK_1802)
                    } else {
                        sendMessage(player, "You search the David's barrel but find nothing.")
                    }
                }

                Scenery.ELIZABETH_S_BARREL_2660 -> {
                    if (!inInventory(player, Items.SILVER_NEEDLE_1804, 1)) {
                        sendDialogue(player, "There's something shiny hidden at the bottom. You take Elizabeth's silver needle.")
                        addItem(player, Items.SILVER_NEEDLE_1804)
                    } else {
                        sendMessage(player, "You search the Elizabeth's barrel but find nothing.")
                    }
                }

                Scenery.FRANK_S_BARREL_2661 -> {
                    if (!inInventory(player, Items.SILVER_POT_1806, 1)) {
                        sendDialogue(player, "There's something shiny hidden at the bottom. You take Frank's silver pot.")
                        addItem(player, Items.SILVER_POT_1806)
                    } else {
                        sendMessage(player, "You search the Frank's barrel but find nothing.")
                    }
                }
            }
            return@on true
        }

        on(MysteryUtils.CRIME_SCENE_OBJECTS, IntType.SCENERY, "investigate") { player, node ->
            when (node.id) {
                Scenery.SINCLAIR_FAMILY_CREST_2655 -> {
                    if (getAttribute(player, MysteryUtils.ATTRIBUTE_DAVID, false)) {
                        sendMessage(player, "The Sinclair Family Crest is hung up here.")
                    } else if (getQuestStage(player, "Murder Mystery") == 1) {
                        sendDialogue(player, "That it is still dirty, and there is no evidence of cleaning here.")
                    } else {
                        sendDialogue(player, "The Sinclair family crest. It's shiny and freshly polished and has a slight smell of poison.")
                    }
                }

                Scenery.SPIDERS__NEST_26109 -> {
                    if (getAttribute(player, MysteryUtils.ATTRIBUTE_DAVID, false)) {
                        sendDialogue(player, "There is a spider's nest here. There must be at least a few hundred spiders ready to hatch. It's certainly clear nobody's used poison here.")
                    } else if (getQuestStage(player, "Murder Mystery") == 1) {
                        sendDialogue(player, "There is a faint smell of poison behind the smell of the compost.")
                    } else {
                        sendMessage(player, "It looks like a spiders' nest of some kind...")
                    }
                }

                Scenery.SINCLAIR_FAMILY_BEEHIVE_26121 -> {
                    if (getAttribute(player, MysteryUtils.ATTRIBUTE_ANNA, false)) {
                        sendMessage(player, "It's a very old beehive.")
                    } else {
                        sendDialogue(player, "The hive is empty. There are a few dead bees and a faint smell of poison.")
                    }
                }

                Scenery.SINCLAIR_FAMILY_COMPOST_HEAP_26120 -> {
                    if (getAttribute(player, MysteryUtils.ATTRIBUTE_ANNA, false)) {
                        sendDialogue(player, "The compost is teeming with maggots. Somebody should really do something about it. It's certainly clear nobody's used poison here.")
                    } else if (getQuestStage(player, "Murder Mystery") == 1) {
                        sendDialogue(player, "There is a faint smell of poison behind the smell of the compost.")
                    } else {
                        sendMessage(player, "It's a heap of compost.")
                    }
                }

                Scenery.SINCLAIR_MANSION_DRAIN_2843 -> {
                    if (getAttribute(player, MysteryUtils.ATTRIBUTE_ELIZABETH, false)) {
                        sendMessage(player, "It's the drains from the kitchen.")
                    } else if (getQuestStage(player, "Murder Mystery") == 1) {
                        sendDialogue(player, "The drain seems to have been recently cleaned. You can still smell the faint aroma of poison.")
                    }
                }

                Scenery.SINCLAIR_FAMILY_FOUNTAIN_2654 -> {
                    if (getAttribute(player, MysteryUtils.ATTRIBUTE_ELIZABETH, false)) {
                        sendDialogue(player, "The fountain is swarming with mosquito's. There's a nest of them underneath the fountain.")
                    } else if (getQuestStage(player, "Murder Mystery") == 1) {
                        sendDialogue(player, "There are a lot of dead mosquito's around the base of the fountain. A faint smell of poison is in the air, but the water seems clean.")
                    } else {
                        sendMessage(player, "A fountain with large numbers of insects around the base.")
                    }
                }

            }
            return@on true
        }

        onUseWith(IntType.ITEM, Items.POT_OF_FLOUR_1933, *MysteryUtils.EVIDENCE_ITEMS) { player, used, with ->
            if (removeItem(player, used.id) && removeItem(player, with.id)) {
                when (with.id) {
                    Items.PUNGENT_POT_1812 -> {
                        sendMessage(player, "You sprinkle a small amount of flour on the strange smelling pot.")
                        sendMessageWithDelay(player, "The surface isn't shiny enough to take a fingerprint from.", 1)
                    }

                    Items.CRIMINALS_DAGGER_1813 -> {
                        sendMessage(player, "You sprinkle a small amount of flour on the murder weapon.")
                        sendMessage(player, "The murder weapon is now coated with a thin layer of flour.")
                        addItem(player, Items.CRIMINALS_DAGGER_1814)
                    }

                    Items.SILVER_NECKLACE_1796 -> {
                        sendMessage(player, "You sprinkle the flour on Anna's necklace.")
                        sendMessage(player, "The Necklace is now coated with a thin layer of flour.")
                        addItem(player, Items.SILVER_NECKLACE_1797)

                    }

                    Items.SILVER_CUP_1798, -> {
                        sendMessage(player, "You sprinkle the flour on Bob's cup.")
                        sendMessage(player, "The cup is now coated with a thin layer of flour.")
                        addItem(player, Items.SILVER_CUP_1799)
                    }

                    Items.SILVER_BOTTLE_1800, -> {
                        sendMessage(player, "You sprinkle the flour on Carol's bottle.")
                        sendMessage(player, "The Bottle is now coated with a thin layer of flour.")
                        addItem(player, Items.SILVER_BOTTLE_1801)
                    }

                    Items.SILVER_BOOK_1802, -> {
                        sendMessage(player, "You sprinkle the flour on David's book.")
                        sendMessage(player, "The Book is now coated with a thin layer of flour.")
                        addItem(player, Items.SILVER_BOOK_1803)
                    }

                    Items.SILVER_NEEDLE_1804 -> {
                        sendMessage(player, "You sprinkle the flour on Elizabeth's needle.")
                        sendMessage(player, "The Needle is now coated with a thin layer of flour.")
                        addItem(player, Items.SILVER_NEEDLE_1805)
                    }

                    Items.SILVER_POT_1806 -> {
                        sendMessage(player, "You sprinkle the flour on Frank's pot.")
                        sendMessage(player, "The Pot is now coated with a thin layer of flour.")
                        addItem(player, Items.SILVER_POT_1807)
                    }
                }
                addItem(player, Items.EMPTY_POT_1931)
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, Items.FLYPAPER_1811, *MysteryUtils.EVIDENCE_ITEMS_2) { player, used, with ->
            if (removeItem(player, used.id)) {
                when (with.id) {
                    Items.SILVER_NECKLACE_1797 -> {
                        sendMessage(player, "You use the flypaper on the flour covered necklace.")
                        sendMessage(player, "You have a clean impression of the Anna's finger prints.")
                        addItem(player, Items.ANNAS_PRINT_1816)
                    }

                    Items.SILVER_CUP_1799 -> {
                        sendMessage(player, "You use the flypaper on the flour covered cup.")
                        sendMessage(player, "You have a clean impression of the Bob's finger prints.")
                        addItem(player, Items.BOBS_PRINT_1817)
                    }

                    Items.SILVER_BOTTLE_1801 -> {
                        sendMessage(player, "You use the flypaper on the flour covered bottle.")
                        sendMessage(player, "You have a clean impression of the Carol's finger prints.")
                        addItem(player, Items.CAROLS_PRINT_1818)
                    }

                    Items.SILVER_BOOK_1803 -> {
                        sendMessage(player, "You use the flypaper on the flour covered book.")
                        sendMessage(player, "You have a clean impression of the David's finger prints.")
                        addItem(player, Items.DAVIDS_PRINT_1819)
                    }

                    Items.SILVER_NEEDLE_1805 -> {
                        sendMessage(player, "You use the flypaper on the flour covered needle.")
                        sendMessage(player, "You have a clean impression of the Elizabeth's finger prints.")
                        addItem(player, Items.ELIZABETHS_PRINT_1820)
                    }

                    Items.SILVER_POT_1807 -> {
                        sendMessage(player, "You use the flypaper on the flour covered pot.")
                        sendMessage(player, "You have a clean impression of the Frank's finger prints.")
                        addItem(player, Items.FRANKS_PRINT_1821)
                    }

                    Items.CRIMINALS_DAGGER_1814 -> {
                        sendMessage(player, "You use the flypaper on the floury dagger.")
                        sendMessage(player, "You have a clean impression of the murderer's finger prints.")
                        addItem(player, Items.UNKNOWN_PRINT_1822)
                    }
                }
            }

            return@onUseWith true
        }


        onUseWith(IntType.ITEM, MysteryUtils.GUILTY_NPC_PRINT_ITEMS, Items.UNKNOWN_PRINT_1822) { player, used, with ->
            for (i in MysteryUtils.GUILTY_NPC_PRINT_ITEMS)
            if(removeItem(player, used.id == i) && removeItem(player, with.id)) {
                when(i) {
                    Items.ANNAS_PRINT_1816 -> {
                        if (inInventory(player, Items.CRIMINALS_THREAD_1809)) {
                            sendMessage(player, "The finger print's are an exact match to Anna's.")
                            addItem(player, Items.KILLERS_PRINT_1815)
                        } else {
                            sendDialogue(player, "They don't seem to be the same. I guess that clears Bob of the crime. You destroy useless fingerprint.")
                        }
                    }

                    Items.BOBS_PRINT_1817 -> {
                        sendDialogue(player, "They don't seem to be the same. I guess that clears Bob of the crime. You destroy useless fingerprint.")
                    }

                    Items.CAROLS_PRINT_1818 -> {
                        sendDialogue(player, "They don't seem to be the same. I guess that clears Carol of the crime. You destroy useless fingerprint.")
                    }

                    Items.DAVIDS_PRINT_1819 -> {
                        if (inInventory(player, Items.CRIMINALS_THREAD_1810)) {
                            sendMessage(player, "The finger print's are an exact match to David.")
                            addItem(player, Items.KILLERS_PRINT_1815)
                        } else {
                            sendDialogue(player, "They don't seem to be the same. I guess that clears Frank of the crime. You destroy useless fingerprint.")
                        }
                    }

                    Items.ELIZABETHS_PRINT_1820 -> {
                        if (inInventory(player, Items.CRIMINALS_THREAD_1808)) {
                            sendMessage(player, "The finger print's are an exact match to Elizabeth.")
                            addItem(player, Items.KILLERS_PRINT_1815)
                        } else {
                            sendDialogue(player, "They don't seem to be the same. I guess that clears Carol of the crime. You destroy useless fingerprint.")
                        }
                    }

                    Items.FRANKS_PRINT_1821 -> {
                        sendDialogue(player, "They don't seem to be the same. I guess that clears Frank of the crime. You destroy useless fingerprint.")
                    }
                }
            }
            return@onUseWith true
        }


        on(intArrayOf(Scenery.STURDY_WOODEN_GATE_2664, Scenery.STURDY_WOODEN_GATE_2665), IntType.SCENERY, "investigate") { player, _ ->
            sendDialogue(player, "As you approach the gate the Guard Dog starts barking loudly at you. There is no way an intruder could have committed the murder. It must have been someone the dog knew to get past it quietly.")
            sendChat(findNPC(NPCs.SINCLAIR_GUARD_DOG_821)!!, "BARK")
            return@on true
        }

        on(Scenery.SMASHED_WINDOW_26110, IntType.SCENERY, "break") { player, _ ->
            sendMessage(player, "You don't want to damage evidence!")
            return@on true
        }

        onUseWith(IntType.SCENERY, Items.SILVER_POT_1806, Scenery.BARREL_OF_FLOUR_26122) { player, used, _ ->
            if (used.id in intArrayOf(Items.SILVER_POT_1806, Items.SILVER_POT_1807, Items.PUNGENT_POT_1812)) sendMessage(player, "You probably shouldn't use evidence from a crime scene to keep flour in...")
            return@onUseWith false
        }

        on(Scenery.SACKS_2663, IntType.SCENERY, "investigate") { player, _ ->
            openDialogue(player, PaperInSackDialogue())
            return@on true
        }
    }
}
