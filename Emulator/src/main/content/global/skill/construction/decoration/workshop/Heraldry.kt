package content.global.skill.construction.decoration.workshop

import core.api.*
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items
import org.rs.consts.Scenery

/**
 * Handles interaction for heraldry in the workshop.
 */
class Heraldry : InteractionListener {

    private val heraldryStands = intArrayOf(
        Scenery.HELMET_PLUMING_STAND_13716,
        Scenery.PAINTING_STAND_13717,
        Scenery.BANNER_MAKING_STAND_13718
    )

    override fun defineListeners() {

        /*
         * Handles the heraldry stands interactions in the workshop.
         */

        on(heraldryStands, IntType.SCENERY, "make-helmet", "use") { player, node ->
            openHeraldryDialogue(player, node.asScenery().id)
            true
        }
    }

    /*
     * Open the Heraldry dialogue.
     */
    private fun openHeraldryDialogue(player: Player, sceneryId: Int) {
        val crestType = getAttribute(player, "con:crest-type", 0)
        openDialogue(player, object : DialogueFile() {
            override fun handle(componentID: Int, buttonID: Int) {
                when (stage) {
                    0 -> handleInitialDialogue(sceneryId, player)
                    1 -> handleCraftingOptions(buttonID, player, crestType, sceneryId)
                }
            }
        })
    }

    /*
     * Handles initial interaction with scenery.
     */
    private fun handleInitialDialogue(sceneryId: Int, player: Player) {
        when (sceneryId) {
            Scenery.HELMET_PLUMING_STAND_13716 -> {
                setTitle(player, 2)
                sendDialogueOptions(player, "Plume which type of helmet?", "Steel helmet", "Runite helmet")
            }

            Scenery.PAINTING_STAND_13717 -> {
                setTitle(player, 4)
                sendDialogueOptions(player, "What do you want to make?", "Steel helmet", "Runite helmet", "Steel heraldic shield", "Runite heraldic shield")
            }

            else -> {
                setTitle(player, 5)
                sendDialogueOptions(player, "What do you want to make?", "Steel helmet", "Runite helmet", "Steel heraldic shield", "Runite heraldic shield", "Heraldic banner")
            }
        }
    }

    /*
     * Handles the crafting options.
     */
    private fun handleCraftingOptions(buttonID: Int, player: Player, crestType: Int, sceneryId: Int) {
        when (buttonID) {
            1, 2 -> handleHelmetCrafting(buttonID, player, crestType)
            3, 4 -> handleShieldCrafting(buttonID, player, crestType)
            5 -> handleBannerCrafting(player, crestType)
        }
    }

    /*
     * Handles the helmet crafting based on crest type.
     */
    private fun handleHelmetCrafting(buttonID: Int, player: Player, crestType: Int) {
        val (itemToRemove, itemToAdd) = if (buttonID == 2) {
            Pair(Item(Items.RUNE_FULL_HELM_1163, 1), getRuniteHeraldicHelm(crestType))
        } else {
            Pair(Item(Items.STEEL_FULL_HELM_1157, 1), getSteelHeraldicHelm(crestType))
        }

        if (inInventory(player, itemToRemove.id, 1)) {
            craftItem(player, itemToRemove, itemToAdd, 37.0, 3656, "helmet")
        } else {
            sendDialogue(player, "You need ${itemToRemove.name} to do this.")
        }
    }

    /*
     * Handles the shield crafting based on crest type.
     */
    private fun handleShieldCrafting(buttonID: Int, player: Player, crestType: Int) {
        val (itemToRemove, itemToAdd) = if (buttonID == 4) {
            Pair(Item(Items.RUNE_KITESHIELD_1201, 1), getRuniteHeraldicShield(crestType))
        } else {
            Pair(Item(Items.STEEL_KITESHIELD_1193, 1), getSteelHeraldicShield(crestType))
        }

        if (inInventory(player, itemToRemove.id, 1)) {
            craftItem(player, itemToRemove, itemToAdd, 40.0, 3654, "shield")
        } else {
            sendDialogue(player, "You need ${itemToRemove.name} to do this.")
        }
    }

    /*
     * Handles the banner crafting based on crest type.
     */
    private fun handleBannerCrafting(player: Player, crestType: Int) {
        if (inInventory(player, Items.BOLT_OF_CLOTH_8790, 1) && inInventory(player, Items.PLANK_960, 1)) {
            craftItem(player, Item(Items.BOLT_OF_CLOTH_8790), getHeraldicBanner(crestType), 42.5, 3655, "banner")
            removeItem(player, Items.PLANK_960, Container.INVENTORY)
        } else {
            sendDialogue(player, "You need 1 bolt of cloth and 1 plank to do this.")
        }
    }

    /*
     * Handles crafting the crest item based on crest type.
     */
    private fun craftItem(player: Player, itemToRemove: Item, itemToAdd: Int, xp: Double, animationId: Int, itemType: String) {
        lock(player, 100)
        removeItem(player, itemToRemove, Container.INVENTORY)
        animate(player, animationId)
        runTask(player, 3) {
            addItem(player, itemToAdd, 1)
            rewardXP(player, Skills.CRAFTING, xp)
            sendMessage(player, "You make a $itemType with your symbol on.")
            unlock(player)
        }
    }

    /*
     * Steel heraldry items.
     */
    private fun getSteelHeraldicHelm(crest: Int) = steelHelmCrestMap.getOrElse(crest) { Items.STEEL_FULL_HELM_1157 }
    private fun getRuniteHeraldicHelm(crest: Int) = runiteHelmCrestMap.getOrElse(crest) { Items.RUNE_FULL_HELM_1163 }

    /*
     * Runite heraldry items.
     */
    private fun getSteelHeraldicShield(crest: Int) =
        steelShieldCrestMap.getOrElse(crest) { Items.STEEL_KITESHIELD_1193 }

    private fun getRuniteHeraldicShield(crest: Int) =
        runiteShieldCrestMap.getOrElse(crest) { Items.RUNE_KITESHIELD_1201 }

    /*
     * Heraldry Banners.
     */
    private fun getHeraldicBanner(crest: Int) = bannerCrestMap.getOrElse(crest) { Items.PLANK_960 }


    companion object {
        /*
         * Map of crest type steel helmets.
         */
        private val steelHelmCrestMap = mapOf(
            1 to Items.STEEL_HERALDIC_HELM_8682,
            2 to Items.STEEL_HERALDIC_HELM_8684,
            3 to Items.STEEL_HERALDIC_HELM_8686,
            4 to Items.STEEL_HERALDIC_HELM_8688,
            5 to Items.STEEL_HERALDIC_HELM_8690,
            6 to Items.STEEL_HERALDIC_HELM_8692,
            7 to Items.STEEL_HERALDIC_HELM_8694,
            8 to Items.STEEL_HERALDIC_HELM_8696,
            9 to Items.STEEL_HERALDIC_HELM_8698,
            10 to Items.STEEL_HERALDIC_HELM_8700,
            11 to Items.STEEL_HERALDIC_HELM_8702,
            12 to Items.STEEL_HERALDIC_HELM_8704,
            13 to Items.STEEL_HERALDIC_HELM_8706,
            14 to Items.STEEL_HERALDIC_HELM_8708,
            15 to Items.STEEL_HERALDIC_HELM_8710,
            16 to Items.STEEL_HERALDIC_HELM_8712,
        )

        /*
         * Map of crest type runite helmets.
         */
        private val runiteHelmCrestMap = mapOf(
            1 to Items.RUNE_HERALDIC_HELM_8464,
            2 to Items.RUNE_HERALDIC_HELM_8466,
            3 to Items.RUNE_HERALDIC_HELM_8468,
            4 to Items.RUNE_HERALDIC_HELM_8470,
            5 to Items.RUNE_HERALDIC_HELM_8472,
            6 to Items.RUNE_HERALDIC_HELM_8474,
            7 to Items.RUNE_HERALDIC_HELM_8476,
            8 to Items.RUNE_HERALDIC_HELM_8478,
            9 to Items.RUNE_HERALDIC_HELM_8480,
            10 to Items.RUNE_HERALDIC_HELM_8482,
            11 to Items.RUNE_HERALDIC_HELM_8484,
            12 to Items.RUNE_HERALDIC_HELM_8486,
            13 to Items.RUNE_HERALDIC_HELM_8488,
            14 to Items.RUNE_HERALDIC_HELM_8490,
            15 to Items.RUNE_HERALDIC_HELM_8492,
            16 to Items.RUNE_HERALDIC_HELM_8494,
        )

        /*
         * Map of crest type steel shields.
         */
        private val steelShieldCrestMap = mapOf(
            1 to Items.STEEL_KITESHIELD_8746,
            2 to Items.STEEL_KITESHIELD_8748,
            3 to Items.STEEL_KITESHIELD_8750,
            4 to Items.STEEL_KITESHIELD_8752,
            5 to Items.STEEL_KITESHIELD_8754,
            6 to Items.STEEL_KITESHIELD_8756,
            7 to Items.STEEL_KITESHIELD_8758,
            8 to Items.STEEL_KITESHIELD_8760,
            9 to Items.STEEL_KITESHIELD_8762,
            10 to Items.STEEL_KITESHIELD_8764,
            11 to Items.STEEL_KITESHIELD_8766,
            12 to Items.STEEL_KITESHIELD_8768,
            13 to Items.STEEL_KITESHIELD_8770,
            14 to Items.STEEL_KITESHIELD_8772,
            15 to Items.STEEL_KITESHIELD_8774,
            16 to Items.STEEL_KITESHIELD_8776,
        )

        /*
         * Map of crest type runite shields.
         */
        private val runiteShieldCrestMap = mapOf(
            1 to Items.RUNE_KITESHIELD_8714,
            2 to Items.RUNE_KITESHIELD_8716,
            3 to Items.RUNE_KITESHIELD_8718,
            4 to Items.RUNE_KITESHIELD_8720,
            5 to Items.RUNE_KITESHIELD_8722,
            6 to Items.RUNE_KITESHIELD_8724,
            7 to Items.RUNE_KITESHIELD_8726,
            8 to Items.RUNE_KITESHIELD_8728,
            9 to Items.RUNE_KITESHIELD_8730,
            10 to Items.RUNE_KITESHIELD_8732,
            11 to Items.RUNE_KITESHIELD_8734,
            12 to Items.RUNE_KITESHIELD_8736,
            13 to Items.RUNE_KITESHIELD_8738,
            14 to Items.RUNE_KITESHIELD_8740,
            15 to Items.RUNE_KITESHIELD_8742,
            16 to Items.RUNE_KITESHIELD_8744,
        )

        /*
         * Map of banner crest items.
         */
        private val bannerCrestMap = mapOf(
            1 to Items.BANNER_8650,
            2 to Items.BANNER_8652,
            3 to Items.BANNER_8654,
            4 to Items.BANNER_8656,
            5 to Items.BANNER_8658,
            6 to Items.BANNER_8660,
            7 to Items.BANNER_8662,
            8 to Items.BANNER_8664,
            9 to Items.BANNER_8666,
            10 to Items.BANNER_8668,
            11 to Items.BANNER_8670,
            12 to Items.BANNER_8672,
            13 to Items.BANNER_8674,
            14 to Items.BANNER_8676,
            15 to Items.BANNER_8678,
            16 to Items.BANNER_8680,
        )
    }
}
