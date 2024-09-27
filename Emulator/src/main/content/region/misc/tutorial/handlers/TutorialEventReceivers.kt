package content.region.misc.tutorial.handlers

import content.global.skill.gather.fishing.FishingSpot
import content.global.skill.gather.mining.MiningNode
import content.global.skill.gather.woodcutting.WoodcuttingNode
import core.api.getAttribute
import core.api.setAttribute
import core.game.event.*
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery

object TutorialButtonReceiver : EventHook<ButtonClickEvent> {

    override fun process(entity: Entity, event: ButtonClickEvent) {
        if (entity !is Player) return
        val player = entity.asPlayer()
        when (getAttribute(player, "tutorial:stage", 0)) {

            /*
             * Character design interface, confirm button.
             */
            0 -> if (event.iface == 771 && event.buttonId == 362) {
                setAttribute(player, "/save:tutorial:stage", 39)
                TutorialStage.load(player, 39)
            }

            /*
             * Click settings tab.
             * SD: 548,24
             * HD: 746,52
             */
            1 -> if ((event.iface == 548 && event.buttonId == 24) || (event.iface == 746 && event.buttonId == 52)) {
                setAttribute(player, "/save:tutorial:stage", 2)
                TutorialStage.load(player, 2)
            }

            /*
             * Click inventory tab.
             * SD: 548,41
             * HD: 746,44
             */
            5 -> if ((event.iface == 548 && event.buttonId == 41) || (event.iface == 746 && event.buttonId == 44)) {
                setAttribute(player, "tutorial:stage", 6)
                TutorialStage.load(player, 6)
            }

            /*
             * Click skills tab.
             * SD: 548,39
             * HD: 746,42
             */
            10 -> if ((event.iface == 548 && event.buttonId == 39) || (event.iface == 746 && event.buttonId == 42)) {
                setAttribute(player, "tutorial:stage", 11)
                TutorialStage.load(player, 11)
            }

            /*
             * Click music tab.
             * SD: 548,26
             * HD: 746,54
             */
            21 -> if ((event.iface == 548 && event.buttonId == 26) || (event.iface == 746 && event.buttonId == 54)) {
                setAttribute(player, "tutorial:stage", 22)
                TutorialStage.load(player, 22)
            }

            /*
             * Click emote tab.
             * SD: 548,25
             * HD: 746,53
             */
            23 -> if ((event.iface == 548 && event.buttonId == 25) || (event.iface == 746 && event.buttonId == 53)) {
                setAttribute(player, "tutorial:stage", 24)
                TutorialStage.load(player, 24)
            }

            /*
             * Click any emote.
             */
            24 -> if (event.iface == 464) {
                setAttribute(player, "tutorial:stage", 25)
                TutorialStage.load(player, 25)
            }

            /*
             * click run button.
             */
            25 -> if (event.iface == 261 && event.buttonId == 3 || event.iface == 750 && event.buttonId == 1) {
                setAttribute(player, "tutorial:stage", 26)
                TutorialStage.load(player, 26)
            }

            /*
             * Open quest journal tab.
             * SD: 548,40
             * HD: 746,43
             */
            27 -> if ((event.iface == 548 && event.buttonId == 40) || (event.iface == 746 && event.buttonId == 43)) {
                setAttribute(player, "tutorial:stage", 28)
                TutorialStage.load(player, 28)
            }

            /*
             * Open equipment tab.
             * SD: 548,42
             * HD: 746,45
             */
            45 -> if ((event.iface == 548 && event.buttonId == 42) || (event.iface == 746 && event.buttonId == 45)) {
                setAttribute(player, "tutorial:stage", 46)
                TutorialStage.load(player, 46)
            }

            /*
             * Open weapon interface.
             * SD: 548,38
             * HD: 746,41
             */
            49 -> if ((event.iface == 548 && event.buttonId == 38) || (event.iface == 746 && event.buttonId == 41)) {
                setAttribute(player, "tutorial:stage", 50)
                TutorialStage.load(player, 50)
            }

            /*
             * Open prayer interface.
             * SD: 548,43
             * HD: 746,46
             */
            61 -> if ((event.iface == 548 && event.buttonId == 43) || (event.iface == 746 && event.buttonId == 46)) {
                setAttribute(player, "tutorial:stage", 62)
                TutorialStage.load(player, 62)
            }

            /*
             * Open friends tab.
             * SD: 548,21
             * HD: 746,49
             */
            63 -> if ((event.iface == 548 && event.buttonId == 21) || (event.iface == 746 && event.buttonId == 49)) {
                setAttribute(player, "tutorial:stage", 64)
                TutorialStage.load(player, 64)
            }

            /*
             * Open ignore list tab.
             * SD: 548,22
             * HD: 746,50
             */
            64 -> if ((event.iface == 548 && event.buttonId == 22) || (event.iface == 746 && event.buttonId == 50)) {
                setAttribute(player, "tutorial:stage", 65)
                TutorialStage.load(player, 65)
            }

            /*
             * Open magic tab.
             * SD: 548,44
             * HD: 746,47
             */
            68 -> if ((event.iface == 548 && event.buttonId == 44) || (event.iface == 746 && event.buttonId == 47)) {
                setAttribute(player, "tutorial:stage", 69)
                TutorialStage.load(player, 69)
            }
        }
    }
}

object TutorialInteractionReceiver : EventHook<InteractionEvent> {

    override fun process(entity: Entity, event: InteractionEvent) {
        if (entity !is Player) return
        val player = entity.asPlayer()
        when (getAttribute(player, "tutorial:stage", 0)) {
            /*
             * Click on tree and start chopping.
             */
            6 -> if ((WoodcuttingNode.forId(event.target.id)?.identifier ?: -1) == 1.toByte()) {
                setAttribute(player, "tutorial:stage", 7)
                TutorialStage.load(player, 7)
            }

            /*
             * Click on fishing spot to start fishing.
             */
            12 -> if (FishingSpot.forId(event.target.id) != null) {
                setAttribute(player, "tutorial:stage", 13)
                TutorialStage.load(player, 13)
            }

            /*
             * Prospect rock - Tin.
             */
            31 -> if (MiningNode.forId(event.target.id)?.identifier?.equals(2.toByte()) == true && event.option == "prospect") {
                setAttribute(player, "tutorial:stage", 32)
                TutorialStage.load(player, 32)
            }

            /*
             * Prospect rock - Copper.
             */
            33 -> if (MiningNode.forId(event.target.id)?.identifier?.equals(1.toByte()) == true && event.option == "prospect") {
                setAttribute(player, "tutorial:stage", 34)
                TutorialStage.load(player, 34)
            }

            /*
             * Mine rock - Tin.
             */
            35 -> if (MiningNode.forId(event.target.id)?.identifier?.equals(2.toByte()) == true && event.option == "mine") {
                setAttribute(player, "tutorial:stage", 36)
                TutorialStage.load(player, 36)
            }

            /*
             * Equip bronze dagger.
             */
            46 -> if (event.target.id == Items.BRONZE_DAGGER_1205 && event.option == "equip") {
                setAttribute(player, "tutorial:stage", 47)
                TutorialStage.load(player, 47)
            }

            /*
             * Equip wooden sword and wooden shield.
             */
            48 -> {
                if (event.target.id == Items.BRONZE_SWORD_1277 && event.option == "equip") {
                    setAttribute(player, "/save:tutorial:sword", true)
                } else if (event.target.id == Items.WOODEN_SHIELD_1171 && event.option == "equip") {
                    setAttribute(player, "/save:tutorial:shield", true)
                }
                if (getAttribute(player, "tutorial:shield", false) && getAttribute(player, "tutorial:sword", false)) {
                    setAttribute(player, "tutorial:stage", 49)
                    TutorialStage.load(player, 49)
                }
            }

            /*
             * Attack Rat NPC.
             */
            51 -> if (event.target.id == NPCs.GIANT_RAT_86 && event.option == "attack") {
                setAttribute(player, "tutorial:stage", 52)
                TutorialStage.load(player, 52)
            }

            /*
             * Open bank account.
             */
            56 -> if (event.target.name.contains("booth", true) && event.option == "use") {
                setAttribute(player, "tutorial:stage", 57)
                TutorialStage.load(player, 57)
            }
        }
    }
}

object TutorialResourceReceiver : EventHook<ResourceProducedEvent> {

    override fun process(entity: Entity, event: ResourceProducedEvent) {
        if (entity !is Player) return
        val player = entity.asPlayer()
        when (getAttribute(player, "tutorial:stage", 0)) {
            /*
             * Gather some logs.
             */
            7 -> if (event.itemId == Items.LOGS_1511) {
                setAttribute(player, "tutorial:stage", 8)
                TutorialStage.load(player, 8)
            }

            /*
             * Catch some raw shrimp.
             */
            13 -> if (event.itemId == Items.RAW_SHRIMPS_317) {
                setAttribute(player, "tutorial:stage", 14)
                TutorialStage.load(player, 14)
            }

            /*
             * Cook a shrimp.
             */
            14, 15 -> if (event.itemId == Items.BURNT_SHRIMP_7954) {
                setAttribute(player, "tutorial:stage", 15)
                TutorialStage.load(player, 15)
            } else if (event.itemId == Items.SHRIMPS_315) {
                setAttribute(player, "tutorial:stage", 16)
                TutorialStage.load(player, 16)
            }

            /*
             * Make some bread dough.
             */
            19 -> if (event.itemId == Items.BREAD_DOUGH_2307) {
                setAttribute(player, "tutorial:stage", 20)
                TutorialStage.load(player, 20)
            }

            /*
             * Bake some bread
             */
            20 -> if (event.itemId == Items.BREAD_2309 || event.itemId == Items.BURNT_BREAD_2311) {
                setAttribute(player, "tutorial:stage", 21)
                TutorialStage.load(player, 21)
            }

            /*
             * Mine some tin ore.
             */
            36 -> if (event.itemId == Items.TIN_ORE_438) {
                setAttribute(player, "tutorial:stage", 37)
                TutorialStage.load(player, 37)
            }

            /*
             * Mine some copper ore.
             */
            37 -> if (event.itemId == Items.COPPER_ORE_436) {
                setAttribute(player, "tutorial:stage", 38)
                TutorialStage.load(player, 38)
            }

            /*
             * Make a bronze bar.
             */
            38 -> if (event.itemId == Items.BRONZE_BAR_2349) {
                setAttribute(player, "tutorial:stage", 40)
                TutorialStage.load(player, 40)
            }

            /*
             * Make a bronze dagger.
             */
            42 -> if (event.itemId == Items.BRONZE_DAGGER_1205) {
                setAttribute(player, "tutorial:stage", 43)
                TutorialStage.load(player, 43)
            }
        }
    }
}

object TutorialFireReceiver : EventHook<LitFireEvent> {

    override fun process(entity: Entity, event: LitFireEvent) {
        if (entity !is Player) return
        val player = entity.asPlayer()
        when (getAttribute(player, "tutorial:stage", 0)) {
            9 -> {
                setAttribute(player, "tutorial:stage", 10)
                TutorialStage.load(player, 10)
            }
        }
    }
}

object TutorialUseWithReceiver : EventHook<UseWithEvent> {

    override fun process(entity: Entity, event: UseWithEvent) {
        if (entity !is Player) return
        val player = entity.asPlayer()
        when (getAttribute(player, "tutorial:stage", 0)) {
            /*
             * Start lighting a fire.
             */
            8 -> if (event.used == Items.TINDERBOX_590 && event.with == Items.LOGS_1511) {
                setAttribute(player, "tutorial:stage", 9)
                TutorialStage.load(player, 9)
            }

            /*
             * Use bar on anvil.
             */
            41 -> if (event.used == Items.BRONZE_BAR_2349 && event.with == Scenery.ANVIL_2783) {
                setAttribute(player, "tutorial:stage", 42)
                TutorialStage.load(player, 42)
            }
        }
    }
}

object TutorialKillReceiver : EventHook<NPCKillEvent> {

    override fun process(entity: Entity, event: NPCKillEvent) {
        if (entity !is Player) return
        val player = entity.asPlayer()
        when (getAttribute(player, "tutorial:stage", 0)) {

            /*
             * Kill giant rat with sword.
             */
            52 -> if (event.npc.id == NPCs.GIANT_RAT_86) {
                setAttribute(player, "tutorial:stage", 53)
                TutorialStage.load(player, 53)
            }

            /*
             * Kill giant rat using bow with arrows.
             */
            54 -> if (event.npc.id == NPCs.GIANT_RAT_86) {
                setAttribute(player, "tutorial:stage", 55)
                TutorialStage.load(player, 55)
            }

            /*
             * Use spell on chicken.
             */
            70 -> if (event.npc.id == NPCs.CHICKEN_41) {
                setAttribute(player, "tutorial:stage", 71)
                TutorialStage.load(player, 71)
            }
        }
    }
}
