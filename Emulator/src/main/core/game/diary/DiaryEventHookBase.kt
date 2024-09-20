package core.game.diary

import core.api.*
import core.api.Event
import core.game.event.*
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.map.zone.ZoneBorders

/**
 * Base class for diary event hooks.
 *
 * @property diaryType The type of diary associated with this event hook.
 * @constructor Creates a [DiaryEventHookBase] with the specified [diaryType].
 */
abstract class DiaryEventHookBase(private val diaryType: DiaryType) : MapArea, LoginListener {
    protected companion object {
        /**
         * Executes a handler function if the entity is an eligible player.
         *
         * @param entity The entity to check.
         * @param event The event to pass to the handler.
         * @param handler The function to execute if the entity is an eligible player.
         */
        private fun <T> forEligibleEntityDo(entity: Entity, event: T, handler: (Player, T) -> Unit) {
            if (entity !is Player) return
            if (entity.isArtificial) return

            handler(entity, event)
        }
    }

    /**
     * Handles specific events for the diary.
     *
     * @param [T] The type of event.
     * @param owner The owning [DiaryEventHookBase] instance.
     * @param handler The function to handle the event.
     * @constructor Creates an [EventHandler] with the specified owner and handler.
     */
    class EventHandler<T : core.game.event.Event>(
        private val owner: DiaryEventHookBase,
        private val handler: (Player, T) -> Unit
    ) : EventHook<T> {
        override fun process(entity: Entity, event: T) {
            forEligibleEntityDo(entity, event, handler)
        }
    }
    /**
     * The tasks associated with the area.
     *
     * @return An array of [AreaDiaryTask].
     */
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
     * Completes a task requirement for a player.
     *
     * @param player The player whose task requirement is being fulfilled.
     * @param level The diary level associated with the task.
     * @param task The specific task to be fulfilled.
     * @param attribute The attribute indicating the task's status.
     */
    protected fun fulfillTaskRequirement(player: Player, level: DiaryLevel, task: Int, attribute: String) {
        if (getAttribute(player, attribute, false)) return
        player.achievementDiaryManager.updateTask(player, diaryType, findIndexFor(level), task, false)
        setAttribute(player, "/save:$attribute", true)
    }

    /**
     * Executes an action when a task requirement is fulfilled.
     *
     * @param player The player whose task requirement has been fulfilled.
     * @param attribute The attribute indicating the task's status.
     * @param then The action to perform after the requirement is fulfilled.
     */
    protected fun whenTaskRequirementFulfilled(player: Player, attribute: String, then: () -> Unit) {
        if (getAttribute(player, attribute, false)) {
            then()
            removeAttribute(player, attribute)
        }
    }

    /**
     * Increments the progress of a task for a player.
     *
     * @param player The player whose task progress is being updated.
     * @param level The diary level associated with the task.
     * @param task The specific task to be progressed.
     * @param attribute The attribute tracking the task's progress.
     * @param maxProgress The maximum progress value for the task.
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
     * Progresses a flagged task for a player.
     *
     * @param player The player whose task progress is being updated.
     * @param level The diary level associated with the task.
     * @param task The specific task identifier.
     * @param attribute The attribute being modified.
     * @param bit The bitmask used for task progress.
     * @param targetValue The target value for the task progress.
     */
    protected fun progressFlaggedTask(
        player: Player,
        level: DiaryLevel,
        task: Int,
        attribute: String,
        bit: Int,
        targetValue: Int
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
     * Completes a task for a player.
     *
     * @param player The player completing the task.
     * @param level The diary level associated with the task.
     * @param task The specific task number to be completed.
     */
    protected fun finishTask(player: Player, level: DiaryLevel, task: Int) {
        player.achievementDiaryManager.finishTask(
            player, diaryType, findIndexFor(level), task
        )
    }

    /**
     * Checks if a task is completed for a player.
     *
     * @param player The player to check.
     * @param level The diary level associated with the task.
     * @param task The specific task identifier.
     * @return `true` if the task is completed, `false` otherwise.
     */
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
     * Called when a player enters the area.
     *
     * @param player The player entering the area.
     */
    protected open fun onAreaVisited(player: Player) {
        areaTasks.forEach {
            it.whenSatisfied(player) {
                finishTask(
                    player, it.diaryLevel, it.taskId
                )
            }
        }
    }

    /**
     * Called when a player leaves the area.
     *
     * @param player The player leaving the area.
     */
    protected open fun onAreaLeft(player: Player) {
    }

    /**
     * Handles the event where a player produces a resource.
     *
     * @param player The player producing the resource.
     * @param event The resource production event.
     */
    protected open fun onResourceProduced(player: Player, event: ResourceProducedEvent) {
    }

    /**
     * Handles the event where a player kills an NPC.
     *
     * @param player The player killing the NPC.
     * @param event The NPC kill event.
     */
    protected open fun onNpcKilled(player: Player, event: NPCKillEvent) {
    }

    /**
     * Handles the event where a player teleports.
     *
     * @param player The player teleporting.
     * @param event The teleportation event.
     */
    protected open fun onTeleported(player: Player, event: TeleportEvent) {
    }

    /**
     * Handles the event where a player lights a fire.
     *
     * @param player The player lighting the fire.
     * @param event The fire lighting event.
     */
    protected open fun onFireLit(player: Player, event: LitFireEvent) {
    }

    /**
     * Handles the event where a player lights a light source.
     *
     * @param player The player lighting the light source.
     * @param event The light source lighting event.
     */
    protected open fun onLightSourceLit(player: Player, event: LitLightSourceEvent) {
    }

    /**
     * Handles the event where a player interacts with an object.
     *
     * @param player The player interacting.
     * @param event The interaction event.
     */
    protected open fun onInteracted(player: Player, event: InteractionEvent) {
        // This function is currently empty and can be implemented later
    }

    /**
     * Handles the event where a player clicks a button.
     *
     * @param player The player clicking the button.
     * @param event The button click event.
     */
    protected open fun onButtonClicked(player: Player, event: ButtonClickEvent) {
    }

    /**
     * Handles the event where a player opens a dialogue.
     *
     * @param player The player opening the dialogue.
     * @param event The dialogue open event.
     */
    protected open fun onDialogueOpened(player: Player, event: DialogueOpenEvent) {
    }

    /**
     * Handles the event where a player closes a dialogue.
     *
     * @param player The player closing the dialogue.
     * @param event The dialogue close event.
     */
    protected open fun onDialogueClosed(player: Player, event: DialogueCloseEvent) {}

    /**
     * Handles the event where a player selects a dialogue option.
     *
     * @param player The player selecting the option.
     * @param event The dialogue option selection event.
     */
    protected open fun onDialogueOptionSelected(player: Player, event: DialogueOptionSelectionEvent) {}

    /**
     * Handles the event where a player uses an item with an object or another item.
     *
     * @param player The player using the item.
     * @param event The item use event.
     */
    protected open fun onUsedWith(player: Player, event: UseWithEvent) {}

    /**
     * Handles the event where a player picks up an item.
     *
     * @param player The player picking up the item.
     * @param event The item pickup event.
     */
    protected open fun onPickedUp(player: Player, event: PickUpEvent) {}

    /**
     * Handles the event where a player opens an interface.
     *
     * @param player The player opening the interface.
     * @param event The interface open event.
     */
    protected open fun onInterfaceOpened(player: Player, event: InterfaceOpenEvent) {}

    /**
     * Handles the event where a player opens an interface.
     *
     * @param player The player opening the interface.
     * @param event The interface open event.
     */
    protected open fun onInterfaceClosed(player: Player, event: InterfaceCloseEvent) {}

    /**
     * Handles the event where an attribute is set for a player.
     *
     * @param player The player setting the attribute.
     * @param event The attribute set event.
     */
    protected open fun onAttributeSet(player: Player, event: AttributeSetEvent) {}

    /**
     * Handles the event where an attribute is removed for a player.
     *
     * @param player The player removing the attribute.
     * @param event The attribute removal event.
     */
    protected open fun onAttributeRemoved(player: Player, event: AttributeRemoveEvent) {}

    /**
     * Handles the event where a player casts a spell.
     *
     * @param player The player casting the spell.
     * @param event The spell cast event.
     */
    protected open fun onSpellCast(player: Player, event: SpellCastEvent) {}

    /**
     * Handles the event where a player alchemizes an item.
     *
     * @param player The player alchemizing the item.
     * @param event The item alchemy event.
     */
    protected open fun onItemAlchemized(player: Player, event: ItemAlchemizationEvent) {}

    /**
     * Handles the event where a player equips an item.
     *
     * @param player The player equipping the item.
     * @param event The item equip event.
     */
    protected open fun onItemEquipped(player: Player, event: ItemEquipEvent) {}

    /**
     * Handles the event where a player unequips an item.
     *
     * @param player The player unequipping the item.
     * @param event The item unequip event.
     */
    protected open fun onItemUnequipped(player: Player, event: ItemUnequipEvent) {}

    /**
     * Handles the event where a player purchases an item from a shop.
     *
     * @param player The player purchasing the item.
     * @param event The item purchase event.
     */
    protected open fun onItemPurchasedFromShop(player: Player, event: ItemShopPurchaseEvent) {}

    /**
     * Handles the event where a player sells an item to a shop.
     *
     * @param player The player selling the item.
     * @param event The item sale event.
     */
    protected open fun onItemSoldToShop(player: Player, event: ItemShopSellEvent) {}

    /**
     * Handles the event where a player is assigned a job.
     *
     * @param player The player being assigned the job.
     * @param event The job assignment event.
     */
    protected open fun onJobAssigned(player: Player, event: JobAssignmentEvent) {}

    /**
     * Handles the event where a player dials a fairy ring.
     *
     * @param player The player dialing the fairy ring.
     * @param event The fairy ring dialing event.
     */
    protected open fun onFairyRingDialed(player: Player, event: FairyRingDialEvent) {}

    /**
     * Handles the event where a player recharges their summoning points.
     *
     * @param player The player recharging their summoning points.
     * @param event The summoning points recharge event.
     */
    protected open fun onSummoningPointsRecharged(player: Player, event: SummoningPointsRechargeEvent) {}

    /**
     * Handles the event where a player recharges their prayer points.
     *
     * @param player The player recharging their prayer points.
     * @param event The prayer points recharge event.
     */
    protected open fun onPrayerPointsRecharged(player: Player, event: PrayerPointsRechargeEvent) {}
}