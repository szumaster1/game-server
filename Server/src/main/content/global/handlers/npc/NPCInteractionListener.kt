package content.global.handlers.npc

import content.global.random.RandomEvents
import content.minigame.gnomecook.*
import content.region.kandarin.miniquest.barcrawl.BarcrawlManager
import content.region.kandarin.miniquest.barcrawl.BarcrawlType
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.npc.IdleAbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.system.timer.impl.AntiMacro
import core.game.worldevents.holiday.HolidayRandomEventNPC
import core.game.worldevents.holiday.HolidayRandomEvents
import core.game.worldevents.holiday.HolidayRandoms
import core.tools.END_DIALOGUE

class NPCInteractionListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Milk cow interaction.
         */

        onUseWith(IntType.ITEM, Items.BUCKET_1925, *cowSceneryIds) { player, _, with ->
            milk(player, with.asScenery())
            return@onUseWith true
        }

        /*
         * Hair-cut interaction.
         */

        on(NPCs.HAIRDRESSER_598, IntType.NPC, "hair-cut") { player, node ->
            player.dialogueInterpreter.open(NPCs.HAIRDRESSER_598, (node as NPC?), true)
            return@on true
        }

        /*
         * Interaction with dummy scenery.
         */

        on(dummySceneryIds, IntType.SCENERY, "attack") { player, _ ->
            lock(player, 3)
            animate(player, player.properties.attackAnimation)
            if (player.properties.currentCombatLevel < 8) {
                var experience = 5.0
                when (player.properties.attackStyle.style) {
                    WeaponInterface.STYLE_ACCURATE -> rewardXP(player, Skills.ATTACK, experience)
                    WeaponInterface.STYLE_AGGRESSIVE -> rewardXP(player, Skills.STRENGTH, experience)
                    WeaponInterface.STYLE_DEFENSIVE -> rewardXP(player, Skills.DEFENCE, experience)
                    WeaponInterface.STYLE_CONTROLLED -> {
                        experience /= 3.0
                        rewardXP(player, Skills.ATTACK, experience)
                        rewardXP(player, Skills.STRENGTH, experience)
                        rewardXP(player, Skills.DEFENCE, experience)
                    }
                }
            } else {
                sendMessage(player, "You swing at the dummy.")
                sendMessage(player, "There is nothing more you can learn from hitting a dummy.")
            }
            return@on true
        }

        /*
         * Deposit interaction for Peer the Seer.
         */

        on(peerTheSeerNPC, IntType.NPC, "deposit") { player, _ ->
            if (anyInEquipment(player, Items.FREMENNIK_SEA_BOOTS_1_14571, Items.FREMENNIK_SEA_BOOTS_2_14572, Items.FREMENNIK_SEA_BOOTS_3_14573)) {
                openDepositBox(player)
                setInterfaceText(player, "Peer the Seer's Deposits", Components.BANK_DEPOSIT_BOX_11, 12)
            } else {
                sendNPCDialogue(player, NPCs.PEER_THE_SEER_1288, "Do not pester me, outerlander! I will only deposit items into the banks of those who have earned Fremennik sea boots!", FacialExpression.ANNOYED).also { END_DIALOGUE }
            }
            return@on true
        }

        /*
         * Barcrawl NPC interaction.
         */

        on(barCrawlNPCs, IntType.NPC, "talk-to", "talk") { player, node ->
            val type = BarcrawlType.forId(node.id)
            val instance = BarcrawlManager.getInstance(player)
            if (instance.isFinished || !instance.isStarted || instance.isCompleted(type!!.ordinal)) {
                player.dialogueInterpreter.open(node.id, node)
            } else {
                player.dialogueInterpreter.open("barcrawl dialogue", node.id, type)
            }
            return@on true
        }

        /*
         * Global Interactions.
         */

        on(IntType.NPC, "disturb") { player, node ->
            if (node is IdleAbstractNPC) {
                if (node.canDisturb(player)) {
                    node.disturb(player)
                }
            }
            return@on true
        }

        on(IntType.NPC, "talk-to", "talk", "talk to") { player, node ->
            val npc = node.asNpc()

            if (RandomEvents.randomIDs.contains(node.id)) {
                if (AntiMacro.getEventNpc(player) == null || AntiMacro.getEventNpc(player) != node.asNpc() || AntiMacro.getEventNpc(player)?.finalized == true) {
                    sendMessage(player, "They aren't interested in talking to you.")
                } else {
                    AntiMacro.getEventNpc(player)?.talkTo(node.asNpc())
                }
                return@on true
            }

            if (HolidayRandomEvents.holidayRandomIDs.contains(node.id) && node is HolidayRandomEventNPC) {
                if (HolidayRandoms.getEventNpc(player) == null || HolidayRandoms.getEventNpc(player) != node.asNpc() || HolidayRandoms.getEventNpc(player)?.finalized == true) {
                    sendMessage(player, "They aren't interested in talking to you.")
                } else {
                    HolidayRandoms.getEventNpc(player)?.talkTo(node.asNpc())
                }
                return@on true
            }

            if (getAttribute(npc, "holiday_random_extra_npc", false) && HolidayRandoms.getEventNpc(player) != null) {
                HolidayRandoms.getEventNpc(player)?.talkTo(node.asNpc())
                return@on true
            }

            if (!npc.getAttribute("facing_booth", false)) {
                npc.faceLocation(player.location)
            }

            if (player.properties.combatPulse.getVictim() == npc) {
                sendMessage(player, "I don't think they have any interest in talking to me right now!")
                return@on true
            }

            if (npc.inCombat()) {
                sendMessage(player, "They look a bit busy at the moment.")
                return@on true
            }

            if (player.getAttribute("$GC_BASE_ATTRIBUTE:$GC_JOB_ORDINAL", -1) != -1) {
                val job = GnomeCookingJob.values()[player.getAttribute("$GC_BASE_ATTRIBUTE:$GC_JOB_ORDINAL", -1)]
                if (node.id == job.npc_id && !player.getAttribute("$GC_BASE_ATTRIBUTE:$GC_JOB_COMPLETE", false)) {
                    player.dialogueInterpreter.open(GCCompletionDialogue(job))
                    return@on true
                }
            }
            return@on player.dialogueInterpreter.open(npc.id, npc)
        }
    }

    companion object {

        val barCrawlNPCs = intArrayOf(733, 848, 735, 739, 737, 738, 731, 568, 3217, 736, 734)
        val peerTheSeerNPC = 1288
        val dummySceneryIds = intArrayOf(2038, 15624, 15625, 15626, 15627, 15628, 15629, 15630, 18238, 25648, 28912, 823, 23921)
        val cowSceneryIds = intArrayOf(8689, 12111)
        val items: Array<Item> = arrayOf(Item(1925, 1), Item(3727, 1), Item(1927, 1))

        /*
         * Milking pulse.
         */

        fun milk(player: Player, node: Node): Boolean {
            if (!anyInInventory(player, Items.BUCKET_1925, Items.EMPTY_BUCKET_3727)) {
                player.dialogueInterpreter.open(3807, true, true)
                return true
            }

            lock(player, 8)
            animate(player, 2305)
            playAudio(player, Sounds.MILK_COW_372)
            player.pulseManager.run(object : Pulse(8, player) {
                override fun pulse(): Boolean {
                    if (player.inventory.remove(items[0]) || player.inventory.remove(items[1])) {
                        player.inventory.add(items[2])
                        sendMessage(player, "You milk the cow.")
                    }
                    if (inInventory(player, Items.BUCKET_1925, 1) || inInventory(player, Items.EMPTY_BUCKET_3727, 1)) {
                        animate(player, 2305)
                        return false
                    }
                    return true
                }

                override fun stop() {
                    super.stop()
                    resetAnimator(player)
                }
            })
            return true
        }
    }
}
