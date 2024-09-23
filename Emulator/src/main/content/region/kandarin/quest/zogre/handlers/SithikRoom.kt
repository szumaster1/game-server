package content.region.kandarin.quest.zogre.handlers

import content.region.kandarin.quest.zogre.dialogue.*
import core.api.*
import org.rs.consts.*
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders

/**
 * Sithik room listeners.
 */
class SithikRoom : InteractionListener, MapArea {

    companion object {
        const val SITHIK = Scenery.SITHIK_INTS_6888
        const val SITHIK_OGRE = Scenery.SITHIK_INTS_6889
        const val CUPBOARD = Scenery.CUPBOARD_6876
        const val WARDROBE = Scenery.WARDROBE_6877
        const val DRAWERS = Scenery.DRAWERS_6875
    }

    override fun defineListeners() {

        /*
            Talk-to interaction with Sithik (NPC).
         */

        on(SITHIK, IntType.SCENERY, "talk-to") { player, _ ->
            when {
                getAttribute(player, ZUtils.SITHIK_DIALOGUE_UNLOCK, false) -> openDialogue(player, SithikQuestDialogueFile())
                inInventory(player, ZUtils.STRANGE_POTION) -> openDialogue(player, SithikIntsStrangePotionDialogueFile())
                getAttribute(player, ZUtils.TALK_WITH_ZAVISTIC_DONE, false) -> openDialogue(player, SithikDialogueFiles())
                else -> openDialogue(player, if (getVarbit(player, Vars.VARBIT_QUEST_ZORGE_FLESH_EATERS_PROGRESS) == 0) SithikPermissionDialogueFile() else SithikDialogueFiles())
            }
            return@on true
        }

        /*
            Talk-to interaction with Sithik after he drink strange potion.
         */

        on(SITHIK_OGRE, IntType.SCENERY, "talk-to") { player, _ ->
            when (getVarbit(player, Vars.VARBIT_QUEST_SITHIK_OGRE_TRANSFORMATION) == 1) {
                getAttribute(player, ZUtils.TALK_WITH_SITHIK_OGRE_DONE, false) -> openDialogue(player, SithikTalkAgainAfterTransformDialogueFile())
                else -> openDialogue(player, SithikIntsAfterTransformDialogueFile())
            }
            return@on true
        }

        /*
            Searching cupboard.
         */

        on(CUPBOARD, IntType.SCENERY, "search") { player, _ ->
            if(getAttribute(player, ZUtils.ASK_SITHIK_AGAIN, false)) {
                if (!inInventory(player, ZUtils.NECROMANCY_BOOK) && freeSlots(player) < 1) {
                    sendDialogue(player, "You see an item in the cupboard, but you don't have space to put it in your inventory.")
                } else {
                    sendItemDialogue(player, ZUtils.NECROMANCY_BOOK, "You find a book on Necromancy.")
                    addItemOrDrop(player, ZUtils.NECROMANCY_BOOK, 1)
                }
            } else {
                sendMessage(player, "You search the cupboard but find nothing.")
            }
            return@on true
        }

        /*
            Searching wardrobe.
         */

        on(WARDROBE, IntType.SCENERY, "search") { player, _ ->
            if (getAttribute(player, ZUtils.ASK_SITHIK_AGAIN, false)) {
                if (!inInventory(player, ZUtils.HAM_BOOK) && freeSlots(player) < 1) {
                    sendDialogue(player, "You see an item in the wardrobe, but you don't have space to put it in your inventory.")
                } else {
                    sendItemDialogue(player, ZUtils.HAM_BOOK, "You find a book on Philosophy written by the 'Humans Against Monsters' leader, Johanhus Ulsbrecht.")
                    addItemOrDrop(player, ZUtils.HAM_BOOK, 1)
                }
            } else {
                sendMessage(player, "You search the wardrobe but find nothing.")
            }
            return@on true
        }

        /*
            Search drawers.
         */

        on(DRAWERS, IntType.SCENERY, "search") { player, _ ->
            var hasBoth = inInventory(player, Items.CHARCOAL_973) && inInventory(player, Items.PAPYRUS_970)
            var hasCharcoal = inInventory(player, Items.CHARCOAL_973) && !inInventory(player, Items.PAPYRUS_970)
            var hasPapyrus = inInventory(player, Items.PAPYRUS_970) && !inInventory(player, Items.CHARCOAL_973)
            if(getAttribute(player, ZUtils.ASK_SITHIK_AGAIN, false)) {
                when {
                    hasBoth -> {
                        if (freeSlots(player) < 1) {
                            sendDialogue(player, "You see an item in the drawer, but you need a free inventory space to take it.")
                        } else if (!inInventory(player, ZUtils.PORTRAI_BOOK)) {
                            sendItemDialogue(player, ZUtils.PORTRAI_BOOK, "You also find a book on portraiture.")
                            addItemOrDrop(player, ZUtils.PORTRAI_BOOK, 1)
                        } else {
                            sendDialogue(player, "You search the drawers but find nothing.")
                        }
                    }

                    hasCharcoal -> {
                        if (freeSlots(player) < 1) {
                            sendDialogue(player, "You see an item in the drawer, but you need a free inventory space to take it.")
                        } else if (!inInventory(player, Items.PAPYRUS_970)) {
                            sendItemDialogue(player, Items.PAPYRUS_970, "You find some papyrus.")
                            addItemOrDrop(player, Items.PAPYRUS_970, 1)
                        } else {
                            sendDialogue(player, "You search the drawers but find nothing.")
                        }
                    }

                    hasPapyrus -> {
                        if (freeSlots(player) < 1 && !inInventory(player, Items.CHARCOAL_973)) {
                            sendDialogue(player, "You see an item in the drawer, but you need a free inventory space to take it.")
                        } else if (!inInventory(player, Items.CHARCOAL_973)) {
                            sendItemDialogue(player, Items.CHARCOAL_973, "You find some charcoal.")
                            addItemOrDrop(player, Items.CHARCOAL_973, 1)
                        } else {
                            sendDialogue(player, "You search the drawers but find nothing.")
                        }
                    }

                    else -> {
                        if (freeSlots(player) < 3 && !inInventory(player, ZUtils.PORTRAI_BOOK)) {
                            sendDialogue(player, "You see some items in the drawer, but you need 3 free inventory spaces to take them.")
                        } else if (freeSlots(player) < 2) {
                            sendDialogue(player, "You see some items in the drawer, but you need 2 free inventory spaces to take them.")
                        } else {
                            sendDoubleItemDialogue(player, Items.CHARCOAL_973, Items.PAPYRUS_970, "You find some charcoal and papyrus.")
                            addItemOrDrop(player, Items.CHARCOAL_973, 1)
                            addItemOrDrop(player, Items.PAPYRUS_970, 1)
                        }
                    }
                }
            } else {
                sendDialogue(player, "You search the drawers but find nothing.")
            }
            return@on true
        }

        /*
            Repair necromancy book.
         */

        onUseWith(IntType.ITEM, Items.TORN_PAGE_4809, ZUtils.NECROMANCY_BOOK) { player, used, _ ->
            if (removeItem(player, used.asItem())) {
                sendDoubleItemDialogue(player, ZUtils.NECROMANCY_BOOK, Items.TORN_PAGE_4809, "The torn page matches exactly the part where a torn out page is missing from the book. You feel sure that this page came from this book.")
                setAttribute(player, "/save:${ZUtils.TORN_PAGE_ON_NECRO_BOOK}", true)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /*
            Use books on Sithik (start dialogue).
         */

        onUseWith(IntType.SCENERY, ZUtils.QUEST_ITEMS, SITHIK) { player, used, _ ->
            var item = used.id
            when (item) {
                ZUtils.PORTRAI_BOOK -> openDialogue(player, SithikIntsPortraitureBookDialogueFile())
                ZUtils.HAM_BOOK -> openDialogue(player, SithikIntsHamBookDialogueFile())
                ZUtils.NECROMANCY_BOOK -> {
                    if (getAttribute(player, ZUtils.TORN_PAGE_ON_NECRO_BOOK, false)) {
                        openDialogue(player, SithikIntsNecromancyBookDialogueFile())
                    } else {
                        sendMessage(player, "Nothing interesting happens.")
                    }
                }

                Items.TORN_PAGE_4809 -> openDialogue(player, SithikIntsTornPageDialogueFile())
                Items.BLACK_PRISM_4808 -> openDialogue(player, SithikIntsBlackPrismDialogueFile())
                Items.DRAGON_INN_TANKARD_4811 -> openDialogue(player, SithikIntsDragonTankardDialogueFile())
                Items.PAPYRUS_970 -> {
                    if (!inInventory(player, Items.CHARCOAL_973)) {
                        sendDialogue(player, "You have no charcoal with which to sketch this subject.")
                    } else {
                        animate(player, Animations.HUMAN_PAINT_ON_ITEM_909)
                        openDialogue(player, SithikIntsPortraitDialogueFile())
                    }
                }
                ZUtils.REALIST_PORTRAIT,
                ZUtils.UNREALIST_PORTRAIT -> openDialogue(player, SithikIntsUsedPortraitDialogueFile())
                ZUtils.SIGNED_PORTRAIT -> openDialogue(player, SithikIntsSignedPortraitDialogueFile())
                ZUtils.STRANGE_POTION -> openDialogue(player, SithikIntsStrangePotionDialogueFile())
            }
            return@onUseWith true
        }

        /*
            Trying to take cup of tea from Sithik drawer.
         */

        on(Items.CUP_OF_TEA_4838, IntType.GROUNDITEM, "take") { player, node ->
            val cup = node as GroundItem
            when (cup.location) {
                Location(2593, 3103, 1) -> sendNPCDialogue(
                    player,
                    NPCs.SITHIK_INTS_2061,
                    "Hey! What do you think you're doing? Leave my tea alone!",
                    FacialExpression.ANNOYED
                )

                else -> {
                    if (freeSlots(player) < 1) {
                        sendMessage(player, "You don't have enough inventory space.")
                    } else {
                        removeGroundItem(cup)
                        addItem(player, Items.CUP_OF_TEA_4838)
                    }
                }
            }
            return@on true
        }

        onUseWith(IntType.GROUNDITEM, ZUtils.STRANGE_POTION, Items.CUP_OF_TEA_4838) { player, used, _ ->
            lock(player, 2)
            animate(player, 537)
            replaceSlot(player, used.asItem().index, Item(Items.SAMPLE_BOTTLE_3377))
            sendItemDialogue(player, ZUtils.STRANGE_POTION, "You pour some of the potion into the cup. Zavistic said it may take some time to have an effect.")
            setAttribute(player, "/save:${ZUtils.SITHIK_TURN_INTO_OGRE}", true)
            return@onUseWith true
        }

    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(2598, 3103, 2590, 3108, 1, true))
    }

    override fun areaEnter(entity: Entity) {
        if (entity !is Player) {
            return
        } else {
            if (getAttribute(entity.asPlayer(), ZUtils.SITHIK_TURN_INTO_OGRE, false)) {
                setVarbit(entity.asPlayer(), Vars.VARBIT_QUEST_SITHIK_OGRE_TRANSFORMATION, 1, true)
            } else return
        }
    }
}
