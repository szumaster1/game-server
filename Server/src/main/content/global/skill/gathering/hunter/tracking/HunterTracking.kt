package content.global.skill.gathering.hunter.tracking

import core.api.*
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.tools.Log
import core.tools.RandomFunction

/**
 * Hunter tracking.
 */
abstract class HunterTracking : OptionHandler() {
    var KEBBIT_ANIM = Animation(0)
    val MISS_ANIM = Animation(5255)
    var trailLimit = 0
    var attribute = ""
    var indexAttribute = ""
    var rewards = Array(0) { Item(0) }
    var tunnelEntrances = Array(0) { Location(0, 0, 0) }
    var initialMap = HashMap<Int, ArrayList<TrailDefinition>>()
    var linkingTrails = ArrayList<TrailDefinition>()
    var experience = 0.0
    var varp = 0
    var requiredLevel = 1


    /**
     * Get initial trail
     *
     * @param obj
     * @return
     */
    fun getInitialTrail(obj: Scenery): TrailDefinition? {
        return initialMap[obj.id]?.random()
    }


    /**
     * Generate trail
     *
     * @param startobj
     * @param player
     */
    fun generateTrail(startobj: Scenery, player: Player) {
        val trail = player.getAttribute(attribute, ArrayList<TrailDefinition>())
        val initialTrail = getInitialTrail(startobj)
        if (initialTrail == null) {
            log(this::class.java, Log.WARN, "UNHANDLED STARTING OBJECT FOR HUNTER TRACKING $startobj")
            return
        }
        trail.add(initialTrail)
        player.setAttribute(attribute, trail)

        var numSpots = RandomFunction.random(2, trailLimit)
        var triesRemaining = numSpots * 3

        while (numSpots > 0) {
            if (triesRemaining-- <= 0) {
                clearTrail(player)
                return
            }
            val nextTrail = getLinkingTrail(player)
            var offsetUsed = false
            for (i in trail) {
                if (i.varbit == nextTrail.varbit) {
                    offsetUsed = true; break
                }
            }
            if (offsetUsed) continue
            if (nextTrail.type == TrailType.TUNNEL) {
                trail.add(nextTrail)
                continue
            }
            trail.add(nextTrail)
            player.setAttribute(attribute, trail)
            numSpots--
        }
    }


    /**
     * Get linking trail
     *
     * @param player
     * @return
     */
    fun getLinkingTrail(player: Player): TrailDefinition {
        val trail = player.getAttribute(attribute, ArrayList<TrailDefinition>())
        val previousTrail = trail[trail.lastIndex]
        if (previousTrail.type == TrailType.TUNNEL) {
            val possibleTrails = ArrayList<TrailDefinition>()
            for (trail in linkingTrails) {
                val invTrail = getTrailInverse(trail, false)
                if (invTrail.type == TrailType.TUNNEL && previousTrail.endLocation.withinDistance(invTrail.startLocation, 5) && previousTrail.endLocation != invTrail.startLocation && previousTrail.varbit != trail.varbit) {
                    possibleTrails.add(trail)
                }
            }
            return possibleTrails.random()
        }
        val possibleTrails = ArrayList<TrailDefinition>()
        for (trail in linkingTrails) {
            if (trail.startLocation == previousTrail.endLocation && previousTrail.varbit != trail.varbit) {
                possibleTrails.add(trail)
            }
        }
        return possibleTrails.random()
    }


    /**
     * Get trail inverse
     *
     * @param trail
     * @param swapLocations
     * @return
     */
    fun getTrailInverse(trail: TrailDefinition, swapLocations: Boolean): TrailDefinition {
        if (swapLocations) return TrailDefinition(
            trail.varbit,
            if (tunnelEntrances.contains(trail.startLocation)) TrailType.TUNNEL else TrailType.LINKING,
            !trail.inverted,
            trail.endLocation,
            trail.startLocation,
            trail.triggerObjectLocation
        )
        return TrailDefinition(
            trail.varbit,
            if (tunnelEntrances.contains(trail.startLocation)) TrailType.TUNNEL else TrailType.LINKING,
            !trail.inverted,
            trail.startLocation,
            trail.endLocation
        )
    }


    /**
     * Add extra trails
     *
     */
    fun addExtraTrails() {
        linkingTrails.toTypedArray().forEach { trail ->
            linkingTrails.add(getTrailInverse(trail, true))
        }
        if (this is PolarKebbitHunting) {
            initialMap.values.forEach {
                linkingTrails.addAll(it)
                it.forEach { trail ->
                    linkingTrails.add(getTrailInverse(trail, true))
                }
            }
        }
    }


    /**
     * Clear trail
     *
     * @param player
     */
    fun clearTrail(player: Player) {
        player.removeAttribute(attribute)
        player.removeAttribute(indexAttribute)
        setVarp(player, varp, 0)
    }


    /**
     * Has trail
     *
     * @param player
     * @return
     */
    fun hasTrail(player: Player): Boolean {
        return false
    }


    /**
     * Reward
     *
     * @param player
     * @param success
     */
    fun reward(player: Player, success: Boolean) {
        player.lock()
        player.animator.animate(if (success) KEBBIT_ANIM else MISS_ANIM)
        playAudio(player, Sounds.HUNTING_NOOSE_2637)
        GameWorld.Pulser.submit(object : Pulse(KEBBIT_ANIM.duration) {
            override fun pulse(): Boolean {
                if (hasTrail(player) && success) {
                    for (item in rewards) {
                        if (!player.inventory.add(item)) GroundItemManager.create(item, player)
                    }
                    player.skills.addExperience(Skills.HUNTER, experience)
                    clearTrail(player)
                }
                player.unlock()
                return true
            }
        })
    }


    /**
     * Update trail
     *
     * @param player
     */
    fun updateTrail(player: Player) {
        val trail = player.getAttribute(attribute, ArrayList<TrailDefinition>())
        val trailIndex = player.getAttribute(indexAttribute, 0)
        for (index in 0..trailIndex) {
            val trl = trail[index]
            var current = getVarp(player, varp)
            setVarbit(player, trl.varbit, (if (trl.inverted) 1 else 0) or (1 shl 2))
        }
    }


    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        node ?: return true
        player ?: return true
        val trail = player.getAttribute(attribute, ArrayList<TrailDefinition>())
        val currentIndex = player.getAttribute(indexAttribute, 0)
        if (!hasTrail(player) && !initialMap.containsKey(node.id)) {
            sendDialogue(player, "You search but find nothing.")
            return true
        }
        val currentTrail = if (hasTrail(player)) {
            if (currentIndex < trail.lastIndex) {
                trail[currentIndex + 1]
            } else {
                trail[currentIndex]
            }
        } else {
            TrailDefinition(0, TrailType.LINKING, false, Location(0, 0, 0), Location(0, 0, 0), Location(0, 0, 0))
        }
        when (option) {

            "attack" -> {
                if (!hasNooseWand(player)) {
                    sendDialogue(player, "You need a noose wand to catch the kebbit.")
                    return true
                }
                if (currentIndex == trail.lastIndex && currentTrail.endLocation.equals(node.location)) {
                    reward(player, true)
                } else {
                    reward(player, false)
                }
            }

            "inspect", "search" -> {
                if (!hasTrail(player)) {
                    if (player.skills.getLevel(Skills.HUNTER) < requiredLevel) {
                        sendDialogue(player, "You need a hunter level of $requiredLevel to track these.")
                        return true
                    }
                    generateTrail(node.asScenery(), player)
                    updateTrail(player)
                } else {
                    if (currentTrail.triggerObjectLocation == node.location || (currentIndex == trail.lastIndex && currentTrail.endLocation.equals(node.location))) {
                        if (currentIndex == trail.lastIndex) {
                            sendDialogue(player, "It looks like something is moving around in there.")
                        } else {
                            sendDialogue(player, "You discover some tracks nearby.")
                            player.incrementAttribute(indexAttribute)
                            updateTrail(player)
                        }
                    } else {
                        sendDialogue(player, "You search but find nothing of interest.")
                    }
                }
            }


        }
        return true
    }

    /**
     * Has noose wand
     *
     * @param player
     * @return
     */
    fun hasNooseWand(player: Player): Boolean {
        return inEquipment(player, Items.NOOSE_WAND_10150, 1) || inInventory(player, Items.NOOSE_WAND_10150, 1)
    }
}
