package core.game.diary

import core.api.*
import core.api.Event
import core.game.event.*
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.map.zone.ZoneBorders

/**
 * Diary event hook base.
 *
 * @property diaryType diary type.
 * @constructor Diary event hook base.
 */
abstract class DiaryEventHookBase(private val diaryType: DiaryType) : MapArea, LoginListener {
    protected companion object {
        private fun <T> forEligibleEntityDo(entity: Entity, event: T, handler: (Player, T) -> Unit) {
            if (entity !is Player) return
            if (entity.isArtificial) return

            handler(entity, event)
        }
    }

    /**
     * Event handler.
     *
     * @param T Represents the type of event.
     * @property owner   The owner of the event handler.
     * @property handler The function to handle the event.
     * @constructor Represents the Event handler with the specified owner and handler.
     */
    class EventHandler<T : core.game.event.Event>(
        private val owner: DiaryEventHookBase,
        private val handler: (Player, T) -> Unit
    ) : EventHook<T> {
        override fun process(entity: Entity, event: T) {
            forEligibleEntityDo(entity, event, handler)
        }
    }

    open val areaTasks get() = arrayOf<AreaDiaryTask>()

    final override fun defineAreaBorders(): Array<ZoneBorders> =
        areaTasks.map { task -> task.zoneBorders }.toTypedArray()

    final override fun areaEnter(entity: Entity) {
        if (entity !is Player) return
        if (entity.isArtificial) return

        onAreaVisited(entity)
    }

    final override fun areaLeave(entity: Entity, logout: Boolean) {
        if (entity !is Player) return
        if (entity.isArtificial) return

        onAreaLeft(entity)
    }

    final override fun login(player: Player) {
        player.hook(Event.ResourceProduced, EventHandler(this, ::onResourceProduced))
        player.hook(Event.NPCKilled, EventHandler(this, ::onNpcKilled))
        player.hook(Event.Teleported, EventHandler(this, ::onTeleported))
        player.hook(Event.FireLit, EventHandler(this, ::onFireLit))
        player.hook(Event.LightSourceLit, EventHandler(this, ::onLightSourceLit))
        player.hook(Event.Interacted, EventHandler(this, ::onInteracted))
        player.hook(Event.ButtonClicked, EventHandler(this, ::onButtonClicked))
        player.hook(Event.DialogueOpened, EventHandler(this, ::onDialogueOpened))
        player.hook(Event.DialogueOptionSelected, EventHandler(this, ::onDialogueOptionSelected))
        player.hook(Event.DialogueClosed, EventHandler(this, ::onDialogueClosed))
        player.hook(Event.UsedWith, EventHandler(this, ::onUsedWith))
        player.hook(Event.PickedUp, EventHandler(this, ::onPickedUp))
        player.hook(Event.InterfaceOpened, EventHandler(this, ::onInterfaceOpened))
        player.hook(Event.AttributeSet, EventHandler(this, ::onAttributeSet))
        player.hook(Event.AttributeRemoved, EventHandler(this, ::onAttributeRemoved))
        player.hook(Event.SpellCast, EventHandler(this, ::onSpellCast))
        player.hook(Event.ItemAlchemized, EventHandler(this, ::onItemAlchemized))
        player.hook(Event.ItemEquipped, EventHandler(this, ::onItemEquipped))
        player.hook(Event.ItemUnequipped, EventHandler(this, ::onItemUnequipped))
        player.hook(Event.ItemPurchased, EventHandler(this, ::onItemPurchasedFromShop))
        player.hook(Event.ItemSold, EventHandler(this, ::onItemSoldToShop))
        player.hook(Event.JobAssigned, EventHandler(this, ::onJobAssigned))
        player.hook(Event.FairyRingDialed, EventHandler(this, ::onFairyRingDialed))
        player.hook(Event.SummoningPointsRecharged, EventHandler(this, ::onSummoningPointsRecharged))
        player.hook(Event.PrayerPointsRecharged, EventHandler(this, ::onPrayerPointsRecharged))
    }

    /**
     * Fulfill task requirement
     *
     * @param player The player whose task requirement is being fulfilled
     * @param level The level of the diary associated with the task
     * @param task The specific task to be fulfilled
     * @param attribute The attribute that indicates the task's status
     */
    protected fun fulfillTaskRequirement(player: Player, level: DiaryLevel, task: Int, attribute: String) {
        // Check if the player already has the required attribute
        if (getAttribute(player, attribute, false)) return

        // Update the player's achievement diary with the new task status
        player.achievementDiaryManager.updateTask(
            player, diaryType, findIndexFor(level), task, false
        )

        // Set the attribute to indicate that the task requirement has been fulfilled
        setAttribute(player, "/save:$attribute", true)
    }

    /**
     * When task requirement fulfilled
     *
     * @param player The player whose task requirement has been fulfilled
     * @param attribute The attribute that indicates the task's status
     * @param then The action to perform after the requirement is fulfilled
     * @receiver
     */
    protected fun whenTaskRequirementFulfilled(player: Player, attribute: String, then: () -> Unit) {
        // Check if the player has the required attribute
        if (getAttribute(player, attribute, false)) {
            // Execute the provided action if the requirement is fulfilled
            then()
            // Remove the attribute after the action has been executed
            removeAttribute(player, attribute)
        }
    }

    /**
     * Progress incremental task
     *
     * @param player The player whose task progress is being updated
     * @param level The level of the diary associated with the task
     * @param task The specific task to be progressed
     * @param attribute The attribute that tracks the task's progress
     * @param maxProgress The maximum progress value for the task
     */
    protected fun progressIncrementalTask(
        player: Player, level: DiaryLevel, task: Int, attribute: String, maxProgress: Int
    ) {
        if (isTaskCompleted(player, level, task)) return

        val newValue = getAttribute(player, attribute, 0) + 1

        setAttribute(
            player, "/save:${attribute}", newValue
        )

        if (newValue < maxProgress) {
            player.achievementDiaryManager.updateTask(
                player, diaryType, findIndexFor(level), task, false
            )
        } else {
            finishTask(player, level, task)
            removeAttribute(player, attribute)
        }
    }

    /**
     * Progress flagged task
     *
     * @param player The player whose task progress is being updated
     * @param level The diary level associated with the task
     * @param task The specific task identifier
     * @param attribute The attribute that is being modified
     * @param bit The bitmask used for task progress
     * @param targetValue The value to which the task progress should be set
     */
    protected fun progressFlaggedTask(
        player: Player, // The player whose task progress is being updated
        level: DiaryLevel, // The diary level associated with the task
        task: Int, // The specific task identifier
        attribute: String, // The attribute that is being modified
        bit: Int, // The bitmask used for task progress
        targetValue: Int // The value to which the task progress should be set
    ) {
        if (isTaskCompleted(player, level, task)) {
            return
        }

        val oldValue = getAttribute(player, attribute, 0)
        val newValue = oldValue or bit

        if (newValue != targetValue) {
            if ((oldValue and bit) != 0) return

            setAttribute(
                player, "/save:${attribute}", newValue
            )

            player.achievementDiaryManager.updateTask(
                player, diaryType, findIndexFor(level), task, false
            )
        } else {
            finishTask(player, level, task)
            removeAttribute(player, attribute)
        }
    }

    /**
     * Finish task
     *
     * @param player
     * @param level
     * @param task
     */
    protected fun finishTask(player: Player, level: DiaryLevel, task: Int) {
        player.achievementDiaryManager.finishTask(
            player, diaryType, findIndexFor(level), task
        )
    }

    private fun isTaskCompleted(player: Player, level: DiaryLevel, task: Int): Boolean {
        return player.achievementDiaryManager.hasCompletedTask(
            diaryType, findIndexFor(level), task
        )
    }

    private fun findIndexFor(level: DiaryLevel): Int {
        val levelName = level.name.lowercase().replaceFirstChar { c -> c.uppercase() }
        val levelIndex = diaryType.levelNames.indexOf(levelName)

        if (levelIndex < 0) {
            throw IllegalArgumentException("'$levelName' was not found in diary '$diaryType'.")
        }

        return levelIndex
    }

    /**
     * On area visited
     *
     * @param player The player who visited the area
     */
    protected open fun onAreaVisited(player: Player) {
        // Iterate through all area tasks
        areaTasks.forEach {
            // Check if the task conditions are satisfied for the player
            it.whenSatisfied(player) {
                // Finish the task for the player, providing diary level and task ID
                finishTask(
                    player, it.diaryLevel, it.taskId
                )
            }
        }
    }

    /**
     * On area left
     *
     * @param player The player who left the area
     */
    protected open fun onAreaLeft(player: Player) {
        // This function is currently empty and can be implemented later
    }

    /**
     * On resource produced
     *
     * @param player The player who produced the resource
     * @param event The event related to resource production
     */
    protected open fun onResourceProduced(player: Player, event: ResourceProducedEvent) {
        // This function is currently empty and can be implemented later
    }

    /**
     * On npc killed
     *
     * @param player The player who killed the NPC
     * @param event The event related to NPC killing
     */
    protected open fun onNpcKilled(player: Player, event: NPCKillEvent) {
        // This function is currently empty and can be implemented later
    }

    /**
     * On teleported
     *
     * @param player The player who was teleported
     * @param event The event related to teleportation
     */
    protected open fun onTeleported(player: Player, event: TeleportEvent) {
        // This function is currently empty and can be implemented later
    }

    /**
     * On fire lit
     *
     * @param player The player who lit the fire
     * @param event The event related to fire lighting
     */
    protected open fun onFireLit(player: Player, event: LitFireEvent) {
        // This function is currently empty and can be implemented later
    }

    /**
     * On light source lit
     *
     * @param player The player who lit the light source
     * @param event The event related to light source lighting
     */
    protected open fun onLightSourceLit(player: Player, event: LitLightSourceEvent) {
        // This function is currently empty and can be implemented later
    }

    /**
     * On interacted
     *
     * @param player The player who interacted with an object
     * @param event The event related to the interaction
     */
    protected open fun onInteracted(player: Player, event: InteractionEvent) {
        // This function is currently empty and can be implemented later
    }

    /**
     * On button clicked
     *
     * @param player The player who clicked the button
     * @param event The event related to the button click
     */
    protected open fun onButtonClicked(player: Player, event: ButtonClickEvent) {
        // This function is currently empty and can be implemented later
    }

    /**
     * On dialogue opened
     *
     * @param player The player who opened the dialogue
     * @param event The event related to the dialogue opening
     */
    protected open fun onDialogueOpened(player: Player, event: DialogueOpenEvent) {
        // This function is currently empty and can be implemented later
    }

    /**
     * On dialogue closed
     *
     * @param player The player who closed the dialogue
     * @param event The event triggered when the dialogue is closed
     */
    protected open fun onDialogueClosed(player: Player, event: DialogueCloseEvent) {}

    /**
     * On dialogue option selected
     *
     * @param player The player who selected the dialogue option
     * @param event The event triggered when a dialogue option is selected
     */
    protected open fun onDialogueOptionSelected(player: Player, event: DialogueOptionSelectionEvent) {}

    /**
     * On used with
     *
     * @param player The player who used an item with another
     * @param event The event triggered when an item is used with another
     */
    protected open fun onUsedWith(player: Player, event: UseWithEvent) {}

    /**
     * On picked up
     *
     * @param player The player who picked up an item
     * @param event The event triggered when an item is picked up
     */
    protected open fun onPickedUp(player: Player, event: PickUpEvent) {}

    /**
     * On interface opened
     *
     * @param player The player who opened the interface
     * @param event The event triggered when an interface is opened
     */
    protected open fun onInterfaceOpened(player: Player, event: InterfaceOpenEvent) {}

    /**
     * On interface closed
     *
     * @param player The player who closed the interface
     * @param event The event triggered when an interface is closed
     */
    protected open fun onInterfaceClosed(player: Player, event: InterfaceCloseEvent) {}

    /**
     * On attribute set
     *
     * @param player The player to whom the attribute is set
     * @param event The event triggered when an attribute is set
     */
    protected open fun onAttributeSet(player: Player, event: AttributeSetEvent) {}

    /**
     * On attribute removed
     *
     * @param player The player from whom the attribute is removed
     * @param event The event triggered when an attribute is removed
     */
    protected open fun onAttributeRemoved(player: Player, event: AttributeRemoveEvent) {}

    /**
     * On spell cast
     *
     * @param player The player who cast the spell
     * @param event The event triggered when a spell is cast
     */
    protected open fun onSpellCast(player: Player, event: SpellCastEvent) {}

    /**
     * On item alchemized
     *
     * @param player The player who alchemized the item
     * @param event The event triggered when an item is alchemized
     */
    protected open fun onItemAlchemized(player: Player, event: ItemAlchemizationEvent) {}

    /**
     * On item equipped
     *
     * @param player The player who equipped the item
     * @param event The event triggered when an item is equipped
     */
    protected open fun onItemEquipped(player: Player, event: ItemEquipEvent) {}

    /**
     * On item unequipped
     *
     * @param player The player who unequipped the item
     * @param event The event triggered when an item is unequipped
     */
    protected open fun onItemUnequipped(player: Player, event: ItemUnequipEvent) {}

    /**
     * On item purchased from shop
     *
     * @param player The player who purchased the item from the shop
     * @param event The event triggered when an item is purchased from the shop
     */
    protected open fun onItemPurchasedFromShop(player: Player, event: ItemShopPurchaseEvent) {}

    /**
     * On item sold to shop
     *
     * @param player The player who sold the item to the shop
     * @param event The event triggered when an item is sold to the shop
     */
    protected open fun onItemSoldToShop(player: Player, event: ItemShopSellEvent) {}

    /**
     * On job assigned
     *
     * @param player The player to whom the job is assigned
     * @param event The event triggered when a job is assigned
     */
    protected open fun onJobAssigned(player: Player, event: JobAssignmentEvent) {}

    /**
     * On fairy ring dialed
     *
     * @param player The player who dialed the fairy ring
     * @param event The event triggered when a fairy ring is dialed
     */
    protected open fun onFairyRingDialed(player: Player, event: FairyRingDialEvent) {}

    /**
     * On summoning points recharged
     *
     * @param player The player whose summoning points are recharged
     * @param event The event triggered when summoning points are recharged
     */
    protected open fun onSummoningPointsRecharged(player: Player, event: SummoningPointsRechargeEvent) {}

    /**
     * On prayer points recharged
     *
     * @param player The player whose prayer points are recharged
     * @param event The event triggered when prayer points are recharged
     */
    protected open fun onPrayerPointsRecharged(player: Player, event: PrayerPointsRechargeEvent) {}
}