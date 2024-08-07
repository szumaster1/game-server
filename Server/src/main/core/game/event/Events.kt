package core.game.event

import content.global.activity.jobs.JobType
import content.global.skill.combat.magic.TeleportMethod
import content.region.misc.handlers.zanaris.FairyRing
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.node.entity.player.link.TeleportManager.TeleportType
import core.game.node.entity.player.link.prayer.PrayerType
import core.game.node.item.Item
import core.game.world.map.Location

/**
 * Resource produced event.
 *
 * @property itemId   the item id.
 * @property amount   the amount.
 * @property source   the source.
 * @property original the original id.
 * @return Resource produced event.
 */
data class ResourceProducedEvent(val itemId: Int, val amount: Int, val source: Node, val original: Int = -1) : Event

/**
 * NPC kill event.
 *
 * @property npc the npc id.
 * @constructor NPC kill event.
 */
data class NPCKillEvent(val npc: NPC) : Event

/**
 * Bone bury event.
 *
 * @property boneId the bone id.
 * @constructor Bone bury event
 */
data class BoneBuryEvent(val boneId: Int) : Event

/**
 * Teleport event.
 *
 * @property type     the type.
 * @property method   the method.
 * @property source   the source.
 * @property location the location.
 * @constructor Teleport event
 */
data class TeleportEvent(val type: TeleportType, val method: TeleportMethod, val source: Any, val location: Location) :
    Event

/**
 * Lit fire event.
 *
 * @property logId the log.
 * @constructor Lit fire event
 */
data class LitFireEvent(val logId: Int) : Event

/**
 * Lit light source event.
 *
 * @property litLightSourceId the lit light source id.
 * @constructor Lit light source event
 */
data class LitLightSourceEvent(val litLightSourceId: Int) : Event

/**
 * Interaction event.
 *
 * @property target the target.
 * @property option the option.
 * @constructor Interaction event
 */
data class InteractionEvent(val target: Node, val option: String) : Event

/**
 * Button click event.
 *
 * @property iface    interface id.
 * @property buttonId button id.
 * @constructor Button click event
 */
data class ButtonClickEvent(val iface: Int, val buttonId: Int) : Event

/**
 * Dialogue open event.
 *
 * @property dialogue the dialogue.
 * @constructor Dialogue open event
 */
data class DialogueOpenEvent(val dialogue: Dialogue) : Event

/**
 * Dialogue option selection event.
 *
 * @property dialogue     the dialogue.
 * @property currentStage the stage id.
 * @property optionId     the option id.
 * @constructor Dialogue option selection event
 */
data class DialogueOptionSelectionEvent(val dialogue: Any, val currentStage: Int, val optionId: Int) : Event

/**
 * Dialogue close event.
 *
 * @property dialogue the dialogue.
 * @constructor Dialogue close event
 */
data class DialogueCloseEvent(val dialogue: Dialogue) : Event

/**
 * Use with event.
 *
 * @property used used id.
 * @property with with id.
 * @constructor Use with event
 */
data class UseWithEvent(val used: Int, val with: Int) : Event

/**
 * Self death event.
 *
 * @property killer the killer (player).
 * @constructor Self death event
 */
data class SelfDeathEvent(val killer: Entity) : Event

/**
 * Tick event.
 *
 * @property worldTicks the world ticks.
 * @constructor Tick event
 */
data class TickEvent(val worldTicks: Int) : Event

/**
 * Pick up event.
 *
 * @property itemId the item id.
 * @constructor Pick up event
 */
data class PickUpEvent(val itemId: Int) : Event

/**
 * Interface open event.
 *
 * @property component the component id.
 * @constructor Interface open event
 */
data class InterfaceOpenEvent(val component: Component) : Event

/**
 * Interface close event.
 *
 * @property component the component id.
 * @constructor Interface close event
 */
data class InterfaceCloseEvent(val component: Component) : Event

/**
 * Attribute set event.
 *
 * @property entity    the entity.
 * @property attribute the attribute.
 * @property value     the value.
 * @constructor Attribute set event
 */
data class AttributeSetEvent(val entity: Entity, val attribute: String, val value: Any) : Event

/**
 * Attribute remove event.
 *
 * @property entity    the entity.
 * @property attribute the attribute.
 * @constructor Attribute remove event
 */
data class AttributeRemoveEvent(val entity: Entity, val attribute: String) : Event

/**
 * Spell cast event.
 *
 * @property spellBook the spellbook id.
 * @property spellId   the spell id.
 * @property target    the target.
 * @constructor Spell cast event
 */
data class SpellCastEvent(val spellBook: SpellBook, val spellId: Int, val target: Node? = null) : Event

/**
 * Item alchemization event.
 *
 * @property itemId the item id.
 * @property isHigh is high boolean.
 * @constructor Item alchemization event
 */
data class ItemAlchemizationEvent(val itemId: Int, val isHigh: Boolean) : Event

/**
 * Item equip event.
 *
 * @property itemId the item id.
 * @property slotId the slot id.
 * @constructor Item equip event
 */
data class ItemEquipEvent(val itemId: Int, val slotId: Int) : Event

/**
 * Item unequip event.
 *
 * @property itemId the item id.
 * @property slotId the slot id.
 * @constructor Item unequip event
 */
data class ItemUnequipEvent(val itemId: Int, val slotId: Int) : Event

/**
 * Item shop purchase event.
 *
 * @property itemId   the item id.
 * @property amount   the amount.
 * @property currency the currency.
 * @constructor Item shop purchase event
 */
data class ItemShopPurchaseEvent(val itemId: Int, val amount: Int, val currency: Item) : Event

/**
 * Item shop sell event.
 *
 * @property itemId   the item id.
 * @property amount   the amount.
 * @property currency the currency.
 * @constructor Item shop sell event
 */
data class ItemShopSellEvent(val itemId: Int, val amount: Int, val currency: Item) : Event

/**
 * Job assignment event.
 *
 * @property jobType     the job type.
 * @property employerNpc the npc id.
 * @constructor Job assignment event
 */
data class JobAssignmentEvent(val jobType: JobType, val employerNpc: NPC) : Event

/**
 * Fairy ring dial event.
 *
 * @property fairyRing the fairy ring id.
 * @constructor Fairy ring dial event
 */
data class FairyRingDialEvent(val fairyRing: FairyRing) : Event

/**
 * Varbit update event.
 *
 * @property offset the offset.
 * @property value  the value.
 * @constructor Varbit update event
 */
data class VarbitUpdateEvent(val offset: Int, val value: Int) : Event

/**
 * Dynamic skill level change event.
 *
 * @property skillId  the skill id.
 * @property oldValue the old value.
 * @property newValue the new value.
 * @constructor Dynamic skill level change event
 */
data class DynamicSkillLevelChangeEvent(val skillId: Int, val oldValue: Int, val newValue: Int) : Event

/**
 * Summoning points recharge event.
 *
 * @property obelisk the obelisk id.
 * @constructor Summoning points recharge event
 */
data class SummoningPointsRechargeEvent(val obelisk: Node) : Event

/**
 * Prayer points recharge event.
 *
 * @property altar the altar id.
 * @constructor Prayer points recharge event
 */
data class PrayerPointsRechargeEvent(val altar: Node) : Event

/**
 * XP gain event.
 *
 * @property skillId the skill id.
 * @property amount  amount.
 * @constructor XP gain event
 */
data class XPGainEvent(val skillId: Int, val amount: Double) : Event

/**
 * Prayer activated event.
 *
 * @property type the type.
 * @constructor Prayer activated event
 */
data class PrayerActivatedEvent(val type: PrayerType) : Event

/**
 * Prayer deactivated event.
 *
 * @property type the type.
 * @constructor Prayer deactivated event
 */
data class PrayerDeactivatedEvent(val type: PrayerType) : Event
