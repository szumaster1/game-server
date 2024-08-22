package content.region.kandarin.quest.zogreflesheaters

import content.region.kandarin.quest.zogreflesheaters.dialogue.*
import content.region.kandarin.quest.zogreflesheaters.npc.ZavisticRarveNPC.Companion.spawnWizard
import core.api.*
import cfg.consts.*
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.map.Direction
import core.game.world.map.Location
import core.tools.BLUE

/**
 * Zogre flesh eaters listeners.
 */
class ZogreFleshEatersListeners : InteractionListener {

    override fun defineListeners() {

        /*
         * Interaction after start quest and talk to Ogre guard.
         */

        on(intArrayOf(Scenery.CRUSHED_BARRICADE_6881,Scenery.CRUSHED_BARRICADE_6882), IntType.SCENERY, "climb-over") { player, _ ->
            submitIndividualPulse(player, object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> forceMove(player, player.location, player.location.transform(if (player.location.x < 2457) Direction.EAST else Direction.WEST, 2), 20, 60, null, 10980)
                        1 -> {
                            resetAnimator(player)
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }

        /*
         * Quest items interaction with Grish NPC.
         */

        onUseWith(IntType.NPC, ZogreQuestUtils.QUEST_ITEMS, NPCs.GRISH_2038) { player, used, _ ->
            when (used.id) {
                Items.DRAGON_INN_TANKARD_4811 -> openDialogue(player, GrishDialogueFiles())
                Items.BLACK_PRISM_4808 -> openDialogue(player, GrishBlackPrismDialogueFile())
                Items.TORN_PAGE_4809 -> openDialogue(player, GrishTornPageDialogueFile())
            }
            return@onUseWith true
        }

        /*
         * Quest items interaction with Bartender NPC.
         */

        onUseWith(IntType.NPC, ZogreQuestUtils.QUEST_ITEMS, NPCs.BARTENDER_739) { player, used, _ ->
            if (getVarbit(player, Vars.VARBIT_QUEST_ZORGE_FLESH_EATERS_PROGRESS) >= 3) when (used.id) {
                Items.DRAGON_INN_TANKARD_4811 -> openDialogue(player, BartenderDialogueFiles())
                Items.BLACK_PRISM_4808 -> openDialogue(player, BartenderBlackPrismDialogueFile())
                Items.TORN_PAGE_4809 -> openDialogue(player, BartenderTornPageDialogueFile())
                ZogreQuestUtils.UNREALIST_PORTRAIT -> openDialogue(player, BartenderWrongPortraitDialogueFile())
                ZogreQuestUtils.REALIST_PORTRAIT -> openDialogue(player, BartenderCorrectPortraitDialogueFile())
                else -> sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, ZogreQuestUtils.QUEST_ITEMS, NPCs.ZAVISTIC_RARVE_2059) { player, used, _ ->
            when (used.id) {
                Items.BLACK_PRISM_4808 -> {
                    if (getVarbit(player, Vars.VARBIT_QUEST_ZORGE_FLESH_EATERS_PROGRESS) == 13) {
                        openDialogue(player, ZavisticRarveSellBlackPrismDialogue())
                    } else {
                        openDialogue(player, ZavisticRarveBlackPrismDialogue())
                    }
                }

                else -> sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /*
         * Ring the bell for spawn Zavistic Rarve.
         */

        on(Scenery.BELL_6847, IntType.SCENERY, "ring") { player, _ ->
            if (getAttribute(player, ZogreQuestUtils.NPC_ACTIVE, false)) {
                sendMessage(player, "You can't do that right now.")
            } else if (getVarbit(player, Vars.VARBIT_QUEST_ZORGE_FLESH_EATERS_PROGRESS) in 4..12) {
                playGlobalAudio(player.location, Sounds.ZOGRE_BELL_1959)
                spawnWizard(player)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@on true
        }

        /*
         * Look-at signed portrait of Sithik.
         */

        on(Items.SIGNED_PORTRAIT_4816, IntType.ITEM, "look-at") { player, _ ->
            sendItemDialogue(player, Items.SIGNED_PORTRAIT_4816, "You see an image of Sithik with a message underneath$BLUE'I, the bartender of the Dragon Inn, do swear that this is the true likeness of the wizzy who was talking to Brentle Vahn, my customer the other day.'")
            return@on true
        }

        on(NPCs.UGLUG_NAR_2039, IntType.NPC, "trade") { player, _ ->
            if (getVarbit(player, Vars.VARBIT_QUEST_ZORGE_FLESH_EATERS_PROGRESS) < 7) {
                sendNPCDialogue(player, NPCs.UGLUG_NAR_2039, "Me's not got no glug-glugs to sell, yous bring me da sickies glug-glug den me's open da stufsies for ya.", FacialExpression.OLD_DEFAULT)
            } else {
                openNpcShop(player, NPCs.UGLUG_NAR_2039)
            }
            return@on true
        }

        /*
         * Selling Relicym balm to Uglug Nar.
         */
        onUseWith(IntType.NPC, Items.RELICYMS_BALM3_4844, NPCs.UGLUG_NAR_2039) { player, _, _ ->
            if ((getVarbit(player, Vars.VARBIT_QUEST_ZORGE_FLESH_EATERS_PROGRESS) < 7)) {
                sendNPCDialogue(player, NPCs.UGLUG_NAR_2039, "Me's not got no glug-glugs to sell, yous bring me da sickies glug-glug den me's open da stufsies for ya.", FacialExpression.OLD_DEFAULT)
            } else {
                openDialogue(player, UglugNarDialogueFile())
            }
            return@onUseWith true
        }
    }

    override fun defineDestinationOverrides() {
        setDest(IntType.SCENERY, intArrayOf(Scenery.BELL_6847), "ring") { _, _ ->
            return@setDest Location(2598, 3086, 0)
        }
    }

}
