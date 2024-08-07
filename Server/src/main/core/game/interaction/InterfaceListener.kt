package core.game.interaction

import core.api.ContentInterface
import core.game.component.Component
import core.game.node.entity.player.Player

/**
 * An interface for writing content that allows the class to handle game interface interactions
 *
 * Interactions should be defined in the required [defineInterfaceListeners] method.
 */
interface InterfaceListener : ContentInterface {
    /**
     * Define interface listeners
     *
     */
    fun defineInterfaceListeners()

    /**
     * On
     *
     * @param componentID
     * @param buttonID
     * @param handler
     * @receiver
     */
    fun on(componentID: Int, buttonID: Int, handler: (player: Player, component: Component, opcode: Int, buttonID: Int, slot: Int, itemID: Int) -> Boolean) {
        InterfaceListeners.add(componentID, buttonID, handler)
    }

    /**
     * On
     *
     * @param componentID
     * @param handler
     * @receiver
     */
    fun on(componentID: Int, handler: (player: Player, component: Component, opcode: Int, buttonID: Int, slot: Int, itemID: Int) -> Boolean) {
        InterfaceListeners.add(componentID, handler)
    }

    /**
     * On open
     *
     * @param componentID
     * @param handler
     * @receiver
     */
    fun onOpen(componentID: Int, handler: (player: Player, component: Component) -> Boolean) {
        InterfaceListeners.addOpenListener(componentID, handler)
    }

    /**
     * On close
     *
     * @param componentID
     * @param handler
     * @receiver
     */
    fun onClose(componentID: Int, handler: (player: Player, component: Component) -> Boolean) {
        InterfaceListeners.addCloseListener(componentID, handler)
    }
}