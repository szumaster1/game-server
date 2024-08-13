package core.network.packet.incoming

import core.game.node.entity.player.Player

/**
 * Packet
 *
 * @constructor Packet
 */
sealed class Packet {
    /**
     * Item action
     *
     * @param player
     * @param optIndex
     * @param itemId
     * @param slot
     * @param iface
     * @param child
     * @constructor Item action
     */
    data class ItemAction(val player: Player, val optIndex: Int, val itemId: Int, val slot: Int, val iface: Int, val child: Int) : Packet()

    /**
     * Npc action
     *
     * @param player
     * @param optIndex
     * @param npcIndex
     * @constructor Npc action
     */
    data class NpcAction(val player: Player, val optIndex: Int, val npcIndex: Int) : Packet()

    /**
     * Scenery action
     *
     * @param player
     * @param optIndex
     * @param id
     * @param x
     * @param y
     * @constructor Scenery action
     */
    data class SceneryAction(val player: Player, val optIndex: Int, val id: Int, val x: Int, val y: Int) : Packet()

    /**
     * Player action
     *
     * @param player
     * @param optIndex
     * @param otherIndex
     * @constructor Player action
     */
    data class PlayerAction(val player: Player, val optIndex: Int, val otherIndex: Int) : Packet()

    /**
     * Ground item action
     *
     * @param player
     * @param optIndex
     * @param id
     * @param x
     * @param y
     * @constructor Ground item action
     */
    data class GroundItemAction(val player: Player, val optIndex: Int, val id: Int, val x: Int, val y: Int) : Packet()

    /**
     * Item examine
     *
     * @param player
     * @param id
     * @constructor Item examine
     */
    data class ItemExamine(val player: Player, val id: Int) : Packet()

    /**
     * Scenery examine
     *
     * @param player
     * @param id
     * @constructor Scenery examine
     */
    data class SceneryExamine(val player: Player, val id: Int) : Packet()

    /**
     * Npc examine
     *
     * @param player
     * @param id
     * @constructor Npc examine
     */
    data class NpcExamine(val player: Player, val id: Int) : Packet()

    /**
     * Use with npc
     *
     * @param player
     * @param itemId
     * @param npcIndex
     * @param iface
     * @param slot
     * @constructor Use with npc
     */
    data class UseWithNpc(val player: Player, val itemId: Int, val npcIndex: Int, val iface: Int, val slot: Int) : Packet()

    /**
     * Use with player
     *
     * @param player
     * @param itemId
     * @param otherIndex
     * @param iface
     * @param slot
     * @constructor Use with player
     */
    data class UseWithPlayer(val player: Player, val itemId: Int, val otherIndex: Int, val iface: Int, val slot: Int) : Packet()

    /**
     * Use with item
     *
     * @param player
     * @param usedId
     * @param usedWithId
     * @param usedSlot
     * @param usedWithSlot
     * @param usedIface
     * @param usedWithIface
     * @param usedChild
     * @param usedWithChild
     * @constructor Use with item
     */
    data class UseWithItem(val player: Player, val usedId: Int, val usedWithId: Int, val usedSlot: Int, val usedWithSlot: Int, val usedIface: Int, val usedWithIface: Int, val usedChild: Int, val usedWithChild: Int) : Packet()

    /**
     * Use with scenery
     *
     * @param player
     * @param itemId
     * @param slot
     * @param sceneryId
     * @param x
     * @param y
     * @constructor Use with scenery
     */
    data class UseWithScenery(val player: Player, val itemId: Int, val slot: Int, val sceneryId: Int, val x: Int, val y: Int) : Packet()

    /**
     * Use with ground item
     *
     * @param player
     * @param usedId
     * @param withId
     * @param iface
     * @param child
     * @param slot
     * @param x
     * @param y
     * @constructor Use with ground item
     */
    data class UseWithGroundItem(val player: Player, val usedId: Int, val withId: Int, val iface: Int, val child: Int, val slot: Int, val x: Int, val y: Int) : Packet()

    /**
     * If action
     *
     * @param player
     * @param opcode
     * @param optIndex
     * @param iface
     * @param child
     * @param slot
     * @param itemId
     * @constructor If action
     */
    data class IfAction(val player: Player, val opcode: Int, val optIndex: Int, val iface: Int, val child: Int, val slot: Int, val itemId: Int = -1) : Packet()

    /**
     * Continue option
     *
     * @param player
     * @param iface
     * @param child
     * @param slot
     * @param opcode
     * @constructor Continue option
     */
    data class ContinueOption(val player: Player, val iface: Int, val child: Int, val slot: Int, val opcode: Int) : Packet()

    /**
     * Close iface
     *
     * @param player
     * @constructor Close iface
     */
    data class CloseIface(val player: Player) : Packet()

    /**
     * Join clan
     *
     * @param player
     * @param clanName
     * @constructor Join clan
     */
    data class JoinClan(val player: Player, val clanName: String) : Packet()

    /**
     * Set clan rank
     *
     * @param player
     * @param username
     * @param rank
     * @constructor Set clan rank
     */
    data class SetClanRank(val player: Player, val username: String, val rank: Int) : Packet()

    /**
     * Kick from clan
     *
     * @param player
     * @param username
     * @constructor Kick from clan
     */
    data class KickFromClan(val player: Player, val username: String) : Packet()

    /**
     * Add friend
     *
     * @param player
     * @param username
     * @constructor Add friend
     */
    data class AddFriend(val player: Player, val username: String) : Packet()

    /**
     * Remove friend
     *
     * @param player
     * @param username
     * @constructor Remove friend
     */
    data class RemoveFriend(val player: Player, val username: String) : Packet()

    /**
     * Add ignore
     *
     * @param player
     * @param username
     * @constructor Add ignore
     */
    data class AddIgnore(val player: Player, val username: String) : Packet()

    /**
     * Remove ignore
     *
     * @param player
     * @param username
     * @constructor Remove ignore
     */
    data class RemoveIgnore(val player: Player, val username: String) : Packet()

    /**
     * Tracking focus
     *
     * @param player
     * @param isFocused
     * @constructor Tracking focus
     */
    data class TrackingFocus(val player: Player, val isFocused: Boolean) : Packet()

    /**
     * Tracking camera pos
     *
     * @param player
     * @param x
     * @param y
     * @constructor Tracking camera pos
     */
    data class TrackingCameraPos(val player: Player, val x: Int, val y: Int) : Packet()

    /**
     * Tracking display update
     *
     * @param player
     * @param windowMode
     * @param screenWidth
     * @param screenHeight
     * @param displayMode
     * @constructor Tracking display update
     */
    data class TrackingDisplayUpdate(val player: Player, val windowMode: Int, val screenWidth: Int, val screenHeight: Int, val displayMode: Int) : Packet()

    /**
     * Tracking afk timeout
     *
     * @param player
     * @constructor Tracking afk timeout
     */
    data class TrackingAfkTimeout(val player: Player) : Packet()

    /**
     * Tracking mouse click
     *
     * @param player
     * @param x
     * @param y
     * @param isRightClick
     * @param delay
     * @constructor Tracking mouse click
     */
    data class TrackingMouseClick(val player: Player, val x: Int, val y: Int, val isRightClick: Boolean, val delay: Int) : Packet()

    /**
     * Component player action
     *
     * @param player
     * @param otherIndex
     * @param iface
     * @param child
     * @constructor Component player action
     */
    data class ComponentPlayerAction(val player: Player, val otherIndex: Int, val iface: Int, val child: Int) : Packet()

    /**
     * Component item action
     *
     * @param player
     * @param iface
     * @param child
     * @param itemId
     * @param slot
     * @constructor Component item action
     */
    data class ComponentItemAction(val player: Player, val iface: Int, val child: Int, val itemId: Int, val slot: Int) : Packet()

    /**
     * Component npc action
     *
     * @param player
     * @param iface
     * @param child
     * @param npcIndex
     * @constructor Component npc action
     */
    data class ComponentNpcAction(val player: Player, val iface: Int, val child: Int, val npcIndex: Int) : Packet()

    /**
     * Component scenery action
     *
     * @param player
     * @param iface
     * @param child
     * @param sceneryId
     * @param x
     * @param y
     * @constructor Component scenery action
     */
    data class ComponentSceneryAction(val player: Player, val iface: Int, val child: Int, val sceneryId: Int, val x: Int, val y: Int) : Packet()

    /**
     * Component ground item action
     *
     * @param player
     * @param iface
     * @param child
     * @param slot
     * @param itemId
     * @param x
     * @param y
     * @constructor Component ground item action
     */
    data class ComponentGroundItemAction(val player: Player, val iface: Int, val child: Int, val slot: Int, val itemId: Int, val x: Int, val y: Int) : Packet()

    /**
     * Slot switch multi component
     *
     * @param player
     * @param sourceIface
     * @param sourceChild
     * @param sourceSlot
     * @param destIface
     * @param destChild
     * @param destSlot
     * @constructor Slot switch multi component
     */
    data class SlotSwitchMultiComponent(val player: Player, val sourceIface: Int, val sourceChild: Int, val sourceSlot: Int, val destIface: Int, val destChild: Int, val destSlot: Int) : Packet()

    /**
     * Slot switch single component
     *
     * @param player
     * @param iface
     * @param child
     * @param sourceSlot
     * @param destSlot
     * @param isInsert
     * @constructor Slot switch single component
     */
    data class SlotSwitchSingleComponent(val player: Player, val iface: Int, val child: Int, val sourceSlot: Int, val destSlot: Int, val isInsert: Boolean) : Packet()

    /**
     * Packet count update
     *
     * @param player
     * @param count
     * @constructor Packet count update
     */
    data class PacketCountUpdate(val player: Player, val count: Int) : Packet()

    /**
     * Private message
     *
     * @param player
     * @param username
     * @param message
     * @constructor Private message
     */
    data class PrivateMessage(val player: Player, val username: String, val message: String) : Packet()

    /**
     * Chat message
     *
     * @param player
     * @param effects
     * @param message
     * @constructor Chat message
     */
    data class ChatMessage(val player: Player, val effects: Int, val message: String) : Packet()

    /**
     * Chat setting
     *
     * @param player
     * @param public
     * @param private
     * @param trade
     * @constructor Chat setting
     */
    data class ChatSetting(val player: Player, val public: Int, val private: Int, val trade: Int) : Packet()

    /**
     * Command
     *
     * @param player
     * @param commandLine
     * @constructor Command
     */
    data class Command(val player: Player, val commandLine: String) : Packet()

    /**
     * G e set offer item
     *
     * @param player
     * @param itemId
     * @constructor G e set offer item
     */
    data class GESetOfferItem(val player: Player, val itemId: Int) : Packet()

    /**
     * Track finished
     *
     * @param player
     * @param trackId
     * @constructor Track finished
     */
    data class TrackFinished(val player: Player, val trackId: Int) : Packet()

    /**
     * Report abuse
     *
     * @param player
     * @param target
     * @param rule
     * @param modMute
     * @constructor Report abuse
     */
    data class ReportAbuse(val player: Player, val target: String, val rule: Int, val modMute: Boolean) : Packet()

    /**
     * Worldspace walk
     *
     * @param player
     * @param destX
     * @param destY
     * @param isRun
     * @constructor Worldspace walk
     */
    data class WorldspaceWalk(val player: Player, val destX: Int, val destY: Int, val isRun: Boolean) : Packet()

    /**
     * Minimap walk
     *
     * @param player
     * @param destX
     * @param destY
     * @param clickedX
     * @param clickedY
     * @param rotation
     * @param isRun
     * @constructor Minimap walk
     */
    data class MinimapWalk(val player: Player, val destX: Int, val destY: Int, val clickedX: Int, val clickedY: Int, val rotation: Int, val isRun: Boolean) : Packet()

    /**
     * Interact walk
     *
     * @param player
     * @param destX
     * @param destY
     * @param isRun
     * @constructor Interact walk
     */
    data class InteractWalk(val player: Player, val destX: Int, val destY: Int, val isRun: Boolean) : Packet()

    /**
     * Ping
     *
     * @param player
     * @constructor Ping
     */
    data class Ping(val player: Player) : Packet()

    /**
     * Quick chat
     *
     * @param player
     * @param indexA
     * @param indexB
     * @param forClan
     * @param multiplier
     * @param offset
     * @param type
     * @constructor Quick chat
     */
    data class QuickChat(val player: Player, val indexA: Int, val indexB: Int, val forClan: Boolean, val multiplier: Int, val offset: Int, val type: QCPacketType) : Packet()

    /**
     * Input prompt response
     *
     * @param player
     * @param response
     * @constructor Input prompt response
     */
    data class InputPromptResponse(val player: Player, val response: Any) : Packet()

    /**
     * Player prefs update
     *
     * @param player
     * @param prefs
     * @constructor Player prefs update
     */
    data class PlayerPrefsUpdate(val player: Player, val prefs: Int) : Packet()

    /**
     * No process
     *
     * @constructor No process
     */
    class NoProcess : Packet()

    /**
     * Unhandled op
     *
     * @constructor Unhandled op
     */
    class UnhandledOp : Packet()

    /**
     * Decoding error
     *
     * @param message
     * @constructor Decoding error
     */
    data class DecodingError(val message: String) : Packet()
}
