package core.game.node.entity.player.link

import core.api.removeAttribute
import core.api.setAttribute
import core.game.node.entity.Entity
import core.game.system.task.Pulse

/**
 * Teleport pulse
 *
 * @property entity The entity that this teleport pulse is associated with.
 * @constructor Teleport pulse Represents a new instance of TeleportPulse with the specified entity.
 */
internal abstract class TeleportPulse(private val entity: Entity) : Pulse() {
    // Abstract method that must be implemented by subclasses to define the pulse behavior.
    abstract override fun pulse(): Boolean

    // Method to start the teleport pulse.
    override fun start() {
        // Set the teleport pulse attribute for the entity, linking it to this instance.
        setAttribute(entity, "tele-pulse", this)
        // Call the superclass's start method to ensure any additional startup logic is executed.
        super.start()
    }

    // Method to stop the teleport pulse.
    override fun stop() {
        // Remove the teleport pulse attribute from the entity, unlinking it from this instance.
        removeAttribute(entity, "tele-pulse")
        // Call the superclass's stop method to ensure any additional shutdown logic is executed.
        super.stop()
    }
}