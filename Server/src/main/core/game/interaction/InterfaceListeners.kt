package core.game.interaction

import core.game.component.Component
import core.game.node.entity.player.Player

object InterfaceListeners {
    val buttonListeners = HashMap<String, (Player, Component, Int, Int, Int, Int) -> Boolean>(1000)
    val openListeners = HashMap<String, (Player, Component) -> Boolean>(100)

    /**
     * Add
     *
     * @param componentID
     * @param buttonID
     * @param handler
     * @receiver
     */
    @JvmStatic
    fun add(componentID: Int, buttonID: Int, handler: (Player, Component, Int, Int, Int, Int) -> Boolean) {
        buttonListeners["$componentID:$buttonID"] = handler
    }

    /**
     * Add
     *
     * @param componentID
     * @param handler
     * @receiver
     */
    @JvmStatic
    fun add(componentID: Int, handler: (Player, Component, Int, Int, Int, Int) -> Boolean) {
        buttonListeners["$componentID"] = handler
    }

    /**
     * Add open listener
     *
     * @param componentID
     * @param handler
     * @receiver
     */
    @JvmStatic
    fun addOpenListener(componentID: Int, handler: (Player, Component) -> Boolean) {
        openListeners["$componentID"] = handler
    }

    /**
     * Add close listener
     *
     * @param componentID
     * @param handler
     * @receiver
     */
    @JvmStatic
    fun addCloseListener(componentID: Int, handler: (Player, Component) -> Boolean) {
        openListeners["close:$componentID"] = handler
    }

    /**
     * Get
     *
     * @param componentID
     * @param buttonID
     * @return
     */
    @JvmStatic
    fun get(componentID: Int, buttonID: Int): ((Player, Component, Int, Int, Int, Int) -> Boolean)? {
        return buttonListeners["$componentID:$buttonID"]
    }

    /**
     * Get
     *
     * @param componentID
     * @return
     */
    @JvmStatic
    fun get(componentID: Int): ((Player, Component, Int, Int, Int, Int) -> Boolean)? {
        return buttonListeners["$componentID"]
    }

    /**
     * Get open listener
     *
     * @param componentID
     * @return
     */
    @JvmStatic
    fun getOpenListener(componentID: Int): ((Player, Component) -> Boolean)? {
        return openListeners["$componentID"]
    }

    /**
     * Get close listener
     *
     * @param componentID
     * @return
     */
    @JvmStatic
    fun getCloseListener(componentID: Int): ((Player, Component) -> Boolean)? {
        return openListeners["close:$componentID"]
    }

    /**
     * Run open
     *
     * @param player
     * @param component
     * @return
     */
    @JvmStatic
    fun runOpen(player: Player, component: Component): Boolean {
        val method = getOpenListener(component.id) ?: return false
        return method.invoke(player, component)
    }

    /**
     * Run close
     *
     * @param player
     * @param component
     * @return
     */
    @JvmStatic
    fun runClose(player: Player, component: Component): Boolean {
        val method = getCloseListener(component.id) ?: return true
        return method.invoke(player, component)
    }

    /**
     * Run
     *
     * @param player
     * @param component
     * @param opcode
     * @param buttonID
     * @param slot
     * @param itemID
     * @return
     */
    @JvmStatic
    fun run(player: Player, component: Component, opcode: Int, buttonID: Int, slot: Int, itemID: Int): Boolean {
        val method = get(component.id, buttonID) ?: get(component.id) ?: return false
        return method.invoke(player, component, opcode, buttonID, slot, itemID)
    }
}