package core.game.component

import org.rs.consts.Components

/**
 * Represents an interface type.
 * @author Emperor
 */
enum class InterfaceType(
    @JvmField val fixedPaneId: Int,
    @JvmField val resizablePaneId: Int,
    @JvmField val fixedChildId: Int,
    @JvmField val resizableChildId: Int
) {
    /**
     * Default interface.
     */
    DEFAULT(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 11, 6),

    /**
     * Walkable interface.
     */
    OVERLAY(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 4, 5),

    /**
     * A tab interface.
     */
    TAB(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 83, 93),

    /**
     * The only tab to be shown (when this type is opened).
     */
    SINGLE_TAB(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 80, 76),

    /**
     * Chatbox dialogue interface.
     */
    DIALOGUE(Components.CHATTOP_752, Components.CHATTOP_752, 12, 12),

    /**
     * A window pane.
     */
    WINDOW_PANE(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 0, 0),

    /**
     * Client script chatbox interface.
     */
    CS_CHATBOX(Components.CHATTOP_752, Components.CHATTOP_752, 6, 6),

    /**
     * Chatbox interface.
     */
    CHATBOX(Components.CHATTOP_752, Components.CHATTOP_752, 8, 8),

    /**
     * Wilderness overlay.
     */
    OVERLAY_B(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 11, 3)
}
