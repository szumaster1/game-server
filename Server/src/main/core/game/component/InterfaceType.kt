package core.game.component

import core.api.consts.Components

/**
 * Interface type
 *
 * @property fixedPaneId
 * @property resizablePaneId
 * @property fixedChildId
 * @property resizableChildId
 * @constructor Interface type
 */
enum class InterfaceType(
    @JvmField val fixedPaneId: Int,
    @JvmField val resizablePaneId: Int,
    @JvmField val fixedChildId: Int,
    @JvmField val resizableChildId: Int
) {
    /**
     * Default
     *
     * @constructor Default
     */
    DEFAULT(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 11, 6),

    /**
     * Overlay
     *
     * @constructor Overlay
     */
    OVERLAY(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 4, 5),

    /**
     * Tab
     *
     * @constructor Tab
     */
    TAB(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 83, 93),

    /**
     * Single Tab
     *
     * @constructor Single Tab
     */
    SINGLE_TAB(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 80, 76),

    /**
     * Dialogue
     *
     * @constructor Dialogue
     */
    DIALOGUE(Components.CHATTOP_752, Components.CHATTOP_752, 12, 12),

    /**
     * Window Pane
     *
     * @constructor Window Pane
     */
    WINDOW_PANE(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 0, 0),

    /**
     * Cs Chatbox
     *
     * @constructor Cs Chatbox
     */
    CS_CHATBOX(Components.CHATTOP_752, Components.CHATTOP_752, 6, 6),

    /**
     * Chatbox
     *
     * @constructor Chatbox
     */
    CHATBOX(Components.CHATTOP_752, Components.CHATTOP_752, 8, 8),

    /**
     * Overlay B
     *
     * @constructor Overlay B
     */
    OVERLAY_B(Components.TOPLEVEL_548, Components.TOPLEVEL_FULLSCREEN_746, 11, 3)
}