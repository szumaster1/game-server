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
     * @property player
     * @property optIndex
     * @property itemId
     * @property slot
     * @property iface
     * @property child
     * @constructor Item action
     */
    data class ItemAction(val player: Player, val optIndex: Int, val itemId: Int, val slot: Int, val iface: Int, val child: Int) : Packet()

    /**
     * Npc action
     *
     * @property player
     * @property optIndex
     * @property npcIndex
     * @constructor Npc action
     */
    data class NpcAction(val player: Player, val optIndex: Int, val npcIndex: Int) : Packet()

    /**
     * Scenery action
     *
     * @property player
     * @property optIndex
     * @property id
     * @property x
     * @property y
     * @constructor Scenery action
     */
    data class SceneryAction(val player: Player, val optIndex: Int, val id: Int, val x: Int, val y: Int) : Packet()

    /**
     * Player action
     *
     * @property player
     * @property optIndex
     * @property otherIndex
     * @constructor Player action
     */
    data class PlayerAction(val player: Player, val optIndex: Int, val otherIndex: Int) : Packet()

    /**
     * Ground item action
     *
     * @property player
     * @property optIndex
     * @property id
     * @property x
     * @property y
     * @constructor Ground item action
     */
    data class GroundItemAction(val player: Player, val optIndex: Int, val id: Int, val x: Int, val y: Int) : Packet()

    /**
     * Item examine
     *
     * @property player
     * @property id
     * @constructor Item examine
     */
    data class ItemExamine(val player: Player, val id: Int) : Packet()

    /**
     * Scenery examine
     *
     * @property player
     * @property id
     * @constructor Scenery examine
     */
    data class SceneryExamine(val player: Player, val id: Int) : Packet()

    /**
     * Npc examine
     *
     * @property player
     * @property id
     * @constructor Npc examine
     */
    data class NpcExamine(val player: Player, val id: Int) : Packet()

    /**
     * Use with npc
     *
     * @property player
     * @property itemId
     * @property npcIndex
     * @property iface
     * @property slot
     * @constructor Use with npc
     */
    data class UseWithNpc(val player: Player, val itemId: Int, val npcIndex: Int, val iface: Int, val slot: Int) : Packet()

    /**
     * Use with player
     *
     * @property player
     * @property itemId
     * @property otherIndex
     * @property iface
     * @property slot
     * @constructor Use with player
     */
    data class UseWithPlayer(val player: Player, val itemId: Int, val otherIndex: Int, val iface: Int, val slot: Int) : Packet()

    /**
     * Use with item
     *
     * @property player
     * @property usedId
     * @property usedWithId
     * @property usedSlot
     * @property usedWithSlot
     * @property usedIface
     * @property usedWithIface
     * @property usedChild
     * @property usedWithChild
     * @constructor Use with item
     */
    data class UseWithItem(val player: Player, val usedId: Int, val usedWithId: Int, val usedSlot: Int, val usedWithSlot: Int, val usedIface: Int, val usedWithIface: Int, val usedChild: Int, val usedWithChild: Int) : Packet()

    /**
     * Use with scenery
     *
     * @property player
     * @property itemId
     * @property slot
     * @property sceneryId
     * @property x
     * @property y
     * @constructor Use with scenery
     */
    data class UseWithScenery(val player: Player, val itemId: Int, val slot: Int, val sceneryId: Int, val x: Int, val y: Int) : Packet()

    /**
     * Use with ground item
     *
     * @property player
     * @property usedId
     * @property withId
     * @property iface
     * @property child
     * @property slot
     * @property x
     * @property y
     * @constructor Use with ground item
     */
    data class UseWithGroundItem(val player: Player, val usedId: Int, val withId: Int, val iface: Int, val child: Int, val slot: Int, val x: Int, val y: Int) : Packet()

    /**
     * If action
     *
     * @property player
     * @property opcode
     * @property optIndex
     * @property iface
     * @property child
     * @property slot
     * @property itemId
     * @constructor If action
     */
    data class IfAction(val player: Player, val opcode: Int, val optIndex: Int, val iface: Int, val child: Int, val slot: Int, val itemId: Int = -1) : Packet()

    /**
     * Continue option
     *
     * @property player
     * @property iface
     * @property child
     * @property slot
     * @property opcode
     * @constructor Continue option
     */
    data class ContinueOption(val player: Player, val iface: Int, val child: Int, val slot: Int, val opcode: Int) : Packet()

    /**
     * Close iface
     *
     * @property player
     * @constructor Close iface
     */
    data class CloseIface(val player: Player) : Packet()

    /**
     * Join clan
     *
     * @property player
     * @property clanName
     * @constructor Join clan
     */
    data class JoinClan(val player: Player, val clanName: String) : Packet()

    /**
     * Set clan rank
     *
     * @property player
     * @property username
     * @property rank
     * @constructor Set clan rank
     */
    data class SetClanRank(val player: Player, val username: String, val rank: Int) : Packet()

    /**
     * Kick from clan
     *
     * @property player
     * @property username
     * @constructor Kick from clan
     */
    data class KickFromClan(val player: Player, val username: String) : Packet()

    /**
     * Add friend
     *
     * @property player
     * @property username
     * @constructor Add friend
     */
    data class AddFriend(val player: Player, val username: String) : Packet()

    /**
     * Remove friend
     *
     * @property player
     * @property username
     * @constructor Remove friend
     */
    data class RemoveFriend(val player: Player, val username: String) : Packet()

    /**
     * Add ignore
     *
     * @property player
     * @property username
     * @constructor Add ignore
     */
    data class AddIgnore(val player: Player, val username: String) : Packet()

    /**
     * Remove ignore
     *
     * @property player
     * @property username
     * @constructor Remove ignore
     */
    data class RemoveIgnore(val player: Player, val username: String) : Packet()

    /**
     * Tracking focus
     *
     * @property player
     * @property isFocused
     * @constructor Tracking focus
     */
    data class TrackingFocus(val player: Player, val isFocused: Boolean) : Packet()

    /**
     * Tracking camera pos
     *
     * @property player
     * @property x
     * @property y
     * @constructor Tracking camera pos
     */
    data class TrackingCameraPos(val player: Player, val x: Int, val y: Int) : Packet()

    /**
     * Tracking display update
     *
     * @property player
     * @property windowMode
     * @property screenWidth
     * @property screenHeight
     * @property displayMode
     * @constructor Tracking display update
     */
    data class TrackingDisplayUpdate(val player: Player, val windowMode: Int, val screenWidth: Int, val screenHeight: Int, val displayMode: Int) : Packet()

    /**
     * Tracking afk timeout
     *
     * @property player
     * @constructor Tracking afk timeout
     */
    data class TrackingAfkTimeout(val player: Player) : Packet()

    /**
     * Tracking mouse click
     *
     * @property player
     * @property x
     * @property y
     * @property isRightClick
     * @property delay
     * @constructor Tracking mouse click
     */
    data class TrackingMouseClick(val player: Player, val x: Int, val y: Int, val isRightClick: Boolean, val delay: Int) : Packet()

    /**
     * Component player action
     *
     * @property player
     * @property otherIndex
     * @property iface
     * @property child
     * @constructor Component player action
     */
    data class ComponentPlayerAction(val player: Player, val otherIndex: Int, val iface: Int, val child: Int) : Packet()

    /**
     * Component item action
     *
     * @property player
     * @property iface
     * @property child
     * @property itemId
     * @property slot
     * @constructor Component item action
     */
    data class ComponentItemAction(val player: Player, val iface: Int, val child: Int, val itemId: Int, val slot: Int) : Packet()

    /**
     * Component npc action
     *
     * @property player
     * @property iface
     * @property child
     * @property npcIndex
     * @constructor Component npc action
     */
    data class ComponentNpcAction(val player: Player, val iface: Int, val child: Int, val npcIndex: Int) : Packet()

    /**
     * Component scenery action
     *
     * @property player
     * @property iface
     * @property child
     * @property sceneryId
     * @property x
     * @property y
     * @constructor Component scenery action
     */
    data class ComponentSceneryAction(val player: Player, val iface: Int, val child: Int, val sceneryId: Int, val x: Int, val y: Int) : Packet()

    /**
     * Component ground item action
     *
     * @property player
     * @property iface
     * @property child
     * @property slot
     * @property itemId
     * @property x
     * @property y
     * @constructor Component ground item action
     */
    data class ComponentGroundItemAction(val player: Player, val iface: Int, val child: Int, val slot: Int, val itemId: Int, val x: Int, val y: Int) : Packet()

    /**
     * Slot switch multi component
     *
     * @property player
     * @property sourceIface
     * @property sourceChild
     * @property sourceSlot
     * @property destIface
     * @property destChild
     * @property destSlot
     * @constructor Slot switch multi component
     */
    data class SlotSwitchMultiComponent(val player: Player, val sourceIface: Int, val sourceChild: Int, val sourceSlot: Int, val destIface: Int, val destChild: Int, val destSlot: Int) : Packet()

    /**
     * Slot switch single component
     *
     * @property player
     * @property iface
     * @property child
     * @property sourceSlot
     * @property destSlot
     * @property isInsert
     * @constructor Slot switch single component
     */
    data class SlotSwitchSingleComponent(val player: Player, val iface: Int, val child: Int, val sourceSlot: Int, val destSlot: Int, val isInsert: Boolean) : Packet()

    /**
     * Packet count update
     *
     * @property player
     * @property count
     * @constructor Packet count update
     */
    data class PacketCountUpdate(val player: Player, val count: Int) : Packet()

    /**
     * Private message
     *
     * @property player
     * @property username
     * @property message
     * @constructor Private message
     */
    data class PrivateMessage(val player: Player, val username: String, val message: String) : Packet()

    /**
     * Chat message
     *
     * @property player
     * @property effects
     * @property message
     * @constructor Chat message
     */
    data class ChatMessage(val player: Player, val effects: Int, val message: String) : Packet()

    /**
     * Chat setting
     *
     * @property player
     * @property public
     * @property private
     * @property trade
     * @constructor Chat setting
     */
    data class ChatSetting(val player: Player, val public: Int, val private: Int, val trade: Int) : Packet()

    /**
     * Command
     *
     * @property player
     * @property commandLine
     * @constructor Command
     */
    data class Command(val player: Player, val commandLine: String) : Packet()

    /**
     * G e set offer item
     *
     * @property player
     * @property itemId
     * @constructor G e set offer item
     */
    data class GESetOfferItem(val player: Player, val itemId: Int) : Packet()

    /**
     * Track finished
     *
     * @property player
     * @property trackId
     * @constructor Track finished
     */
    data class TrackFinished(val player: Player, val trackId: Int) : Packet()

    /**
     * Report abuse
     *
     * @property player
     * @property target
     * @property rule
     * @property modMute
     * @constructor Report abuse
     */
    data class ReportAbuse(val player: Player, val target: String, val rule: Int, val modMute: Boolean) : Packet()

    /**
     * Worldspace walk
     *
     * @property player
     * @property destX
     * @property destY
     * @property isRun
     * @constructor Worldspace walk
     */
    data class WorldspaceWalk(val player: Player, val destX: Int, val destY: Int, val isRun: Boolean) : Packet()

    /**
     * Minimap walk
     *
     * @property player
     * @property destX
     * @property destY
     * @property clickedX
     * @property clickedY
     * @property rotation
     * @property isRun
     * @constructor Minimap walk
     */
    data class MinimapWalk(val player: Player, val destX: Int, val destY: Int, val clickedX: Int, val clickedY: Int, val rotation: Int, val isRun: Boolean) : Packet()

    /**
     * Interact walk
     *
     * @property player
     * @property destX
     * @property destY
     * @property isRun
     * @constructor Interact walk
     */
    data class InteractWalk(val player: Player, val destX: Int, val destY: Int, val isRun: Boolean) : Packet()

    /**
     * Ping
     *
     * @property player
     * @constructor Ping
     */
    data class Ping(val player: Player) : Packet()

    /**
     * Quick chat
     *
     * @property player
     * @property indexA
     * @property indexB
     * @property forClan
     * @property multiplier
     * @property offset
     * @property type
     * @constructor Quick chat
     */
    data class QuickChat(val player: Player, val indexA: Int, val indexB: Int, val forClan: Boolean, val multiplier: Int, val offset: Int, val type: QCPacketType) : Packet()

    /**
     * Input prompt response
     *
     * @property player
     * @property response
     * @constructor Input prompt response
     */
    data class InputPromptResponse(val player: Player, val response: Any) : Packet()

    /**
     * Player prefs update
     *
     * @property player
     * @property prefs
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
     * @property message
     * @constructor Decoding error
     */
    data class DecodingError(val message: String) : Packet()
}
