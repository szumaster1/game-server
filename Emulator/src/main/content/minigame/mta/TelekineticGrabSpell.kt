package content.minigame.mta

import content.minigame.mta.impl.TelekineticZone
import org.rs.consts.Sounds
import core.api.findNPC
import core.api.playAudio
import core.api.sendMessage
import core.game.global.action.PickupHandler.canTake
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.combat.spell.MagicSpell
import core.game.node.entity.combat.spell.Runes
import core.game.node.entity.combat.spell.SpellBlocks.isBlocked
import core.game.node.entity.combat.spell.SpellType
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager
import core.game.node.entity.player.link.audio.Audio
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Plugin

/**
 * Represents the Telekinetic grab spell.
 */
class TelekineticGrabSpell : MagicSpell(SpellBookManager.SpellBook.MODERN, 33, 43.0, ANIMATION, START_GRAPHIC, SOUND, arrayOf(Item(Runes.AIR_RUNE.id), Item(Runes.LAW_RUNE.id, 1))) {

    override fun newInstance(arg: SpellType?): Plugin<SpellType?> {
        SpellBookManager.SpellBook.MODERN.register(SPELL_ID, this)
        return this
    }

    override fun cast(entity: Entity, target: Node): Boolean {
        if (target !is GroundItem) return false
        if (!canCast(entity, target)) {
            return false
        }
        entity.lock(2)
        visualize(entity, target)
        Pulser.submit(getGrabPulse(entity, target))
        return true
    }

    override fun visualize(entity: Entity, target: Node) {
        super.visualize(entity, target)
        entity.faceLocation(target.location)
        getProjectile(entity, target as GroundItem).send()
    }

    /**
     * Get grab pulse.
     *
     * @param [entity] the entity that is attempting to grab the item.
     * @param [ground] the ground item that is being targeted for grabbing.
     * @return A Pulse object that represents the grabbing action.
     */
    fun getGrabPulse(entity: Entity, ground: GroundItem): Pulse {
        return object : Pulse(getDelay(ground.location.getDistance(entity.location)), entity) {
            override fun pulse(): Boolean {
                val player = if (entity is Player) entity else null
                val g = GroundItemManager.get(ground.id, ground.location, player)
                if (g == null) {
                    player!!.packetDispatch.sendMessage("Too late!")
                    return true
                }
                if (ground.id == 6561) {
                    player!!.dialogueInterpreter.open(2692, findNPC(2692)!!, true)
                    return true
                }
                if (player == null) {
                    return true
                }
                val teleZone = player.zoneMonitor.isInZone("Telekinetic Theatre") && g.id == 6888
                if (player != null) {
                    if (g == null || !g.isActive) {
                        player.packetDispatch.sendMessage("Too late!")
                        return true
                    }
                    playAudio(player, Sounds.VULNERABILITY_IMPACT_3008)
                    if (!teleZone) {
                        player.inventory.add(Item(g.id, g.amount, g.charge))
                    } else {
                        val zone: TelekineticZone = TelekineticZone.getZone(player)!!
                        zone.moveStatue()
                        player.lock(delay)
                    }
                    player.packetDispatch.sendPositionedGraphics(END_GRAPHIC, ground.location)
                }
                if (!teleZone) {
                    GroundItemManager.destroy(g)
                }
                return true
            }
        }
    }

    /**
     * Can cast
     *
     * @param [entity] The entity attempting to cast
     * @param [item] The item to be cast
     * @return `true` if the entity can cast the item, `false` otherwise
     */
    fun canCast(entity: Entity, item: GroundItem?): Boolean {
        // Check if the entity's interaction is locked
        if (entity.locks.isInteractionLocked || entity.locks.isComponentLocked) {
            return false // Return false if locked
        }
        // Check if the entity is a Player
        if (entity is Player) {
            val player = entity // Cast entity to Player
            // Check if the player's inventory has space for the item
            if (!player.inventory.hasSpaceFor(item as Item?)) {
                sendMessage(player, "You don't have enough inventory space.") // Notify player
                return false // Return false if no space
            }
            // Check if the player can take the item
            if (!canTake(player, item!!, 1)) {
                return false // Return false if cannot take
            }
        }
        // Check if the action is blocked for the item
        if (isBlocked(SPELL_ID, (item as Node?)!!)) {
            (entity as? Player)?.asPlayer()?.dialogueInterpreter?.sendDialogue("You can't do that.") // Notify player
            return false // Return false if blocked
        }
        // Check if the entity meets the requirements to cast
        return super.meetsRequirements(entity, true, true) // Return true if requirements are met
    }

    /**
     * Get projectile.
     *
     * @param [entity] The entity that will launch the projectile.
     * @param [item] The item associated with the projectile.
     * @return The created projectile.
     */
    fun getProjectile(entity: Entity, item: GroundItem): Projectile {
        return Projectile.create(entity.location, item.location, PROJECTILE_ID, START_HEIGHT, END_HEIGHT, START_DELAY, Projectile.getSpeed(entity, item.location), ANGLE, 11)
    }

    /**
     * Get delay.
     *
     * @param [distance] The distance to calculate the delay for.
     * @return The calculated delay based on distance.
     */
    fun getDelay(distance: Double): Int {
        return (2 + distance * 0.5).toInt()
    }

    companion object {
        private val ANIMATION = Animation(2310)
        private val START_GRAPHIC = Graphic(142, 88, 15)
        private val END_GRAPHIC = Graphic(144)
        private val SOUND = Audio(3007, 10, 1)
        private const val PROJECTILE_ID = 143
        private const val START_HEIGHT = 40
        private const val END_HEIGHT = 0
        private const val START_DELAY = 41
        private const val ANGLE = 5
        const val SPELL_ID = 19
    }
}