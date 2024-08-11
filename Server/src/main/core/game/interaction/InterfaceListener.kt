package core.game.interaction

import core.api.ContentInterface
import core.game.component.Component
import core.game.node.entity.player.Player

/**
 * An interface for writing content that allows the class to handle game interface interactions.
 *
 * Interactions should be defined in the required [defineInterfaceListeners] method.
 */
interface InterfaceListener : ContentInterface {
    /**
     * Define interface listeners.
     *
     */
    fun defineInterfaceListeners()

    /**
     * On method to handle button clicks.
     *
     * @param componentID the ID of the component.
     * @param buttonID    the ID of the button.
     * @param handler     the lambda function to handle the interaction.
     * @receiver the receiver of the function.
     */
    fun on(componentID: Int, buttonID: Int, handler: (player: Player, component: Component, opcode: Int, buttonID: Int, slot: Int, itemID: Int) -> Boolean) {
        InterfaceListeners.add(componentID, buttonID, handler)
    }

    /**
     * On method to handle component interactions.
     *
     * @param componentID the ID of the component.
     * @param handler     the lambda function to handle the interaction.
     * @receiver the receiver of the function.
     */
    fun on(componentID: Int, handler: (player: Player, component: Component, opcode: Int, buttonID: Int, slot: Int, itemID: Int) -> Boolean) {
        InterfaceListeners.add(componentID, handler)
    }

    /**
     * On open method to handle component opening.
     *
     * @param componentID the ID of the component.
     * @param handler     the lambda function to handle the opening.
     * @receiver the receiver of the function.
     */
    fun onOpen(componentID: Int, handler: (player: Player, component: Component) -> Boolean) {
        InterfaceListeners.addOpenListener(componentID, handler)
    }

    /**
     * On close method to handle component closing.
     *
     * @param componentID the ID of the component.
     * @param handler     the lambda function to handle the closing.
     * @receiver the receiver of the function.
     */
    fun onClose(componentID: Int, handler: (player: Player, component: Component) -> Boolean) {
        InterfaceListeners.addCloseListener(componentID, handler)
    }
}
