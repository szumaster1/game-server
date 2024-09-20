package core.game.interaction

import core.game.component.Component
import core.game.node.entity.player.Player

/**
 * Interface listeners.
 */
object InterfaceListeners {
    val buttonListeners = HashMap<String, (Player, Component, Int, Int, Int, Int) -> Boolean>(1000)
    val openListeners = HashMap<String, (Player, Component) -> Boolean>(100)

    /**
     * Add button listener.
     *
     * @param [componentID]   the component id.
     * @param [buttonID]      the button id.
     * @param [handler]       the handler function to be executed.
     * @receiver the receiver object.
     */
    @JvmStatic
    fun add(componentID: Int, buttonID: Int, handler: (Player, Component, Int, Int, Int, Int) -> Boolean) {
        buttonListeners["$componentID:$buttonID"] = handler
    }

    /**
     * Add button listener.
     *
     * @param [componentID]   the component id.
     * @param [handler]       the handler function to be executed.
     * @receiver the receiver object.
     */
    @JvmStatic
    fun add(componentID: Int, handler: (Player, Component, Int, Int, Int, Int) -> Boolean) {
        buttonListeners["$componentID"] = handler
    }

    /**
     * Add open listener
     *
     * @param [componentID] the component id.
     * @param [handler]     the handler function to be executed.
     * @receiver the receiver object.
     */
    @JvmStatic
    fun addOpenListener(componentID: Int, handler: (Player, Component) -> Boolean) {
        openListeners["$componentID"] = handler
    }

    /**
     * Add close listener.
     *
     * @param [componentID]   the ID of the component.
     * @param [handler]       the handler function to be executed.
     * @receiver the receiver object.
     */
    @JvmStatic
    fun addCloseListener(componentID: Int, handler: (Player, Component) -> Boolean) {
        openListeners["close:$componentID"] = handler
    }

    /**
     * Get button listener.
     *
     * @param [componentID] the ID of the component.
     * @param [buttonID]    the ID of the button.
     * @return the button listener function.
     */
    @JvmStatic
    fun get(componentID: Int, buttonID: Int): ((Player, Component, Int, Int, Int, Int) -> Boolean)? {
        return buttonListeners["$componentID:$buttonID"]
    }

    /**
     * Get button listener.
     *
     * @param [componentID] the component id.
     * @return the button listener function.
     */
    @JvmStatic
    fun get(componentID: Int): ((Player, Component, Int, Int, Int, Int) -> Boolean)? {
        return buttonListeners["$componentID"]
    }

    /**
     * Get open listener.
     *
     * @param [componentID] the component id.
     * @return the open listener function.
     */
    @JvmStatic
    fun getOpenListener(componentID: Int): ((Player, Component) -> Boolean)? {
        return openListeners["$componentID"]
    }

    /**
     * Get close listener.
     *
     * @param [componentID] the component id.
     * @return the close listener function
     */
    @JvmStatic
    fun getCloseListener(componentID: Int): ((Player, Component) -> Boolean)? {
        return openListeners["close:$componentID"]
    }

    /**
     * Run open listener.
     *
     * @param [player]        the player object.
     * @param [component]     the component object.
     * @return `true` if the listener was executed successfully, `false` otherwise
     */
    @JvmStatic
    fun runOpen(player: Player, component: Component): Boolean {
        val method = getOpenListener(component.id) ?: return false
        return method.invoke(player, component)
    }

    /**
     * Run close listener.
     *
     * @param [player]      the player object.
     * @param [component]   the component object.
     * @return `true` if the listener was executed successfully, `false` otherwise
     */
    @JvmStatic
    fun runClose(player: Player, component: Component): Boolean {
        val method = getCloseListener(component.id) ?: return true
        return method.invoke(player, component)
    }

    /**
     * Run button listener.
     *
     * @param [player]      the player object.
     * @param [component]   the component object.
     * @param [opcode]      the opcode.
     * @param [buttonID]    the button id.
     * @param [slot]        the slot.
     * @param [itemID]      the item id.
     * @return `true` if the listener was executed successfully, `false` otherwise
     */
    @JvmStatic
    fun run(player: Player, component: Component, opcode: Int, buttonID: Int, slot: Int, itemID: Int): Boolean {
        val method = get(component.id, buttonID) ?: get(component.id) ?: return false
        return method.invoke(player, component, opcode, buttonID, slot, itemID)
    }
}
