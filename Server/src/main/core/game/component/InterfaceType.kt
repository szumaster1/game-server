package core.game.component

import core.api.consts.Components

/**
 * InterfaceType Enum Class.
 *
 * @param fixedPaneId      The ID of the fixed pane.
 * @param resizablePaneId  The ID of the resizable pane.
 * @param fixedChildId     The ID of the fixed child.
 * @param resizableChildId The ID of the resizable child.
 * @constructor Represents InterfaceType with the provided IDs.
 */
enum class InterfaceType(
    @JvmField val fixedPaneId: Int,
    @JvmField val resizablePaneId: Int,
    @JvmField val fixedChildId: Int,
    @JvmField val resizableChildId: Int
) {
    /**
     * Default interface type.
     */
    DEFAULT(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 11, 6),

    /**
     * Overlay interface type.
     */
    OVERLAY(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 4, 5),

    /**
     * Tab interface type.
     */
    TAB(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 83, 93),

    /**
     * Single Tab interface type.
     */
    SINGLE_TAB(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 80, 76),

    /**
     * Dialogue interface type.
     */
    DIALOGUE(Components.CHATTOP_752, Components.CHATTOP_752, 12, 12),

    /**
     * Window Pane interface type.
     */
    WINDOW_PANE(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 0, 0),

    /**
     * Cs Chatbox interface type.
     */
    CS_CHATBOX(Components.CHATTOP_752, Components.CHATTOP_752, 6, 6),

    /**
     * Chatbox interface type.
     */
    CHATBOX(Components.CHATTOP_752, Components.CHATTOP_752, 8, 8),

    /**
     * Overlay B interface type.
     */
    OVERLAY_B(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 11, 3)
}
