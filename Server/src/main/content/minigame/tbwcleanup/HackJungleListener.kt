package content.minigame.tbwcleanup

import content.data.skill.SkillingTool
import content.global.skill.gathering.woodcutting.WoodcuttingNode
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.cache.def.impl.ItemDefinition
import core.game.event.ResourceProducedEvent
import core.game.interaction.Clocks
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.RegionManager
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction
import core.tools.RandomFunction.random
import kotlin.streams.toList

/**
 * Hack jungle.
 */
class HackJungleListener : InteractionListener {

    private var eventTriggered = false
    private val woodcuttingSounds = intArrayOf(
        Sounds.WOODCUTTING_HIT_3038,
        Sounds.WOODCUTTING_HIT_3039,
        Sounds.WOODCUTTING_HIT_3040,
        Sounds.WOODCUTTING_HIT_3041,
        Sounds.WOODCUTTING_HIT_3042
    )
    private val junglePlants = intArrayOf(
        9010, 9011, 9012, 9013, //Light Jungle Plants
        9015, 9016, 9017, 9018, //Medium Jungle Plants
        9020, 9021, 9022, 9023 //Dense Jungle Plants
    )
    private val jungleRegions = arrayOf(11056, 11055)

    override fun defineListeners() {
        defineInteraction(
            IntType.SCENERY,
            ids = WoodcuttingNode.values().map { it.id }.toIntArray(),
            "hack",
            persistent = true,
            allowedDistance = 1,
            handler = ::handleJungleHacking
        )
    }

    /**
     * Handle jungle hacking
     *
     * @param player The player who is attempting to hack the jungle.
     * @param node The specific node in the jungle being hacked.
     * @param state The current state of the hacking process.
     * @return Returns true if the hacking was successful, false otherwise.
     */
    private fun handleJungleHacking(player: Player, node: Node, state: Int): Boolean {
        val resource = WoodcuttingNode.forId(node.id)
        val tool = SkillingTool.getMachete(player)

        if (!finishedMoving(player))
            return restartScript(player)

        if (state == 0) {
            if (!resource!!.let { checkRequirements(player, it, node) })
                return clearScripts(player)
            sendMessage(player, "You swing your Machete at the plant.")
        }

        if (clockReady(player, Clocks.SKILLING)) {
            animateWoodcutting(player)

            eventTriggered = (random(0, 1000) < ChanceOfEventPerRewardTick)
            if (eventTriggered) {
                queueScript(player, -1, QueueStrength.SOFT) {
                    rollJungleEvent(player, node, resource!!.emptyId)
                    return@queueScript stopExecuting(player)
                }
            }

            if (!checkReward(player, resource!!, tool!!))
                return delayClock(player, Clocks.SKILLING, 1)

            //if reward rolls the rest should stop
            if (!eventTriggered) {
                queueScript(player, -1, QueueStrength.SOFT) {
                    depletionEvent(player, node.asScenery(), resource, resource.reward)
                }
            }
            if (!checkRequirements(player, resource, node)) {
                return clearScripts(player)
            }
        }
        return keepRunning(player)
    }

    fun checkRequirements(player: Player, resource: WoodcuttingNode, node: Node): Boolean {
        var regionId = player.location.regionId
        if (regionId !in jungleRegions) {
            return false
        }
        if (!player.getAttribute("/save:startedTBWCleanup", false)) {
            player.sendMessage("I should probably talk to someone like Murcaily before I start hacking away at this.")
            return false
        }
        if (player.getSkills().getLevel(Skills.WOODCUTTING) < resource.level) {
            player.getPacketDispatch()
                .sendMessage("You need a woodcutting level of " + resource.level + " to hack this jungle plant.")
            return false
        }
        if (SkillingTool.getMachete(player) == null && node.id in junglePlants) {
            player.packetDispatch.sendMessage("You do not have a machete to use.")
            return false
        }
        if (player.inventory.freeSlots() < 1 && node.isActive) {
            player.sendMessage("Your inventory is too full to hold any more " + ItemDefinition.forId(resource.reward).name.lowercase() + ".")
            return false
        }
        return node.isActive
    }

    fun animateWoodcutting(player: Player) {
        player.locks.lockMovement(1)
        animate(player, SkillingTool.getMachete(player)!!.animation, true)

        val playersAroundMe: List<Player> = RegionManager.getLocalPlayers(player, 2)
            .stream()
            .toList()
        val soundIndex = random(1, woodcuttingSounds.size)

        for (p in playersAroundMe) {
            playAudio(p, woodcuttingSounds[soundIndex])
        }
    }

    /**
     * Check reward (woodcutting).
     */
    private fun checkReward(player: Player, resource: WoodcuttingNode, tool: SkillingTool): Boolean {
        val skill = Skills.WOODCUTTING
        val level: Int = player.getSkills().getLevel(skill) + player.familiarManager.getBoost(skill)
        val hostRatio = RandomFunction.randomDouble(100.0)
        val lowMod: Double = resource.tierModLow
        val low: Double = resource.baseLow + tool.ordinal * lowMod
        val highMod: Double = resource.tierModHigh
        val high: Double = resource.baseHigh + tool.ordinal * highMod
        val clientRatio = RandomFunction.getSkillSuccessChance(low, high, level)
        return hostRatio < clientRatio
    }

    /**
     * This function contains actions that happen when a player successfully hacks a thatch spar from a jungle plant
     *
     * @param player The player who is performing the action
     * @param node The scenery node being interacted with
     * @param resource The type of resource being harvested
     * @param reward The reward given to the player for the action
     * @return Returns true if the action was successful, false otherwise
     */
    private fun depletionEvent(player: Player, node: Scenery, resource: WoodcuttingNode, reward: Int): Boolean {
        if (resource.respawnRate > 0) {
            lock(player, 1)
            player.animate(Animation(2387), 0) //Animate succesfully cut dense jungle plant
            playAudio(player, 1288)

            queueScript(player, 1, QueueStrength.SOFT) {
                SceneryBuilder.replaceWithTempBeforeNew(
                    node,
                    node.transform(resource.id + 1),
                    node.transform(resource.emptyId),
                    140,
                    true
                )
                when (reward) {
                    Items.THATCH_SPAR_LIGHT_6281 -> awardTBWCleanupPoints(player, 1)
                    Items.THATCH_SPAR_MED_6283 -> awardTBWCleanupPoints(player, 5)
                    Items.THATCH_SPAR_DENSE_6285 -> awardTBWCleanupPoints(player, 10)
                }

                player.dispatch(ResourceProducedEvent(reward, 1, node, -1))

                //add experience
                player.getSkills().addExperience(Skills.WOODCUTTING, resource.experience, true)

                //give the reward
                player.inventory.add(Item(reward, 1))

                //send the message for the resource reward
                player.packetDispatch.sendMessage("You get some " + ItemDefinition.forId(reward).name.lowercase() + ".")
                return@queueScript stopExecuting(player)
            }
            node.setActive(false)
            return true
        }
        return false
    }

    /**
     * This function is called when an event is triggered while hacking a jungle plant
     */
    private fun rollJungleEvent(player: Player, node: Node, resource: Int) {
        val roll = random(1, 101)
        if (roll <= 22) {
            spawnNPC(player, NPCs.JUNGLE_SPIDER_2491, node, random(10, 20))
            return
        }
        if (roll <= 43) {
            spawnNPC(player, NPCs.TRIBESMAN_2497, node, random(10, 20))
            return
        }
        if (roll <= 64) {
            spawnNPC(player, NPCs.BUSH_SNAKE_2489, node, 10)
            return
        }
        if (roll <= 72) {
            spawnNPC(player, NPCs.LARGE_MOSQUITO_2493, node, 10)
            return
        }
        if (roll <= 79) {
            spawnNPC(player, NPCs.MOSQUITO_SWARM_2494, node, 10)
            return
        }
        if (roll <= 85) {
            spawnNPC(player, NPCs.MOSQUITO_SWARM_2495, node, 10)
            return
        }
        if (roll <= 88) {
            spawnNPC(player, NPCs.BROODOO_VICTIM_2499, node, 30)
            return
        }
        if (roll <= 92) {
            spawnNPC(player, NPCs.BROODOO_VICTIM_2503, node, 30)
            return
        }
        if (roll <= 94) {
            spawnNPC(player, NPCs.BROODOO_VICTIM_2501, node, 30)
            return
        }
        if (roll <= 99) {
            playAudio(player, 1270)

            val GEMROCK_ID = when (node.id) {
                in 9020..9024 -> 9032
                in 9015..9019 -> 9031
                in 9010..9014 -> 9030
                else -> 0
            }

            SceneryBuilder.replaceWithTempBeforeNew(
                node.asScenery(),
                node.asScenery().transform(GEMROCK_ID),
                node.asScenery().transform(resource),
                200,
                true
            )
            return
        }
        if (roll <= 100) {
            playAudio(player, 1282)
            SceneryBuilder.replaceWithTempBeforeNew(
                node.asScenery(),
                node.asScenery().transform(9033),
                node.asScenery().transform(resource),
                200,
                true
            )
            animateScenery(node.asScenery(), 2383)
            player.sendMessage("You find a Gout Tuber.")
            return
        }
        return
    }
}
