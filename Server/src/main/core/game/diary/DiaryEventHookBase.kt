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
     * @constructor Initializes the Event handler with the specified owner and handler.
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
     * @param player
     * @param level
     * @param task
     * @param attribute
     */
    protected fun fulfillTaskRequirement(player: Player, level: DiaryLevel, task: Int, attribute: String) {
        if (getAttribute(player, attribute, false)) return

        player.achievementDiaryManager.updateTask(
            player, diaryType, findIndexFor(level), task, false
        )

        setAttribute(player, "/save:$attribute", true)
    }

    /**
     * When task requirement fulfilled
     *
     * @param player
     * @param attribute
     * @param then
     * @receiver
     */
    protected fun whenTaskRequirementFulfilled(player: Player, attribute: String, then: () -> Unit) {
        if (getAttribute(player, attribute, false)) {
            then()
            removeAttribute(player, attribute)
        }
    }

    /**
     * Progress incremental task
     *
     * @param player
     * @param level
     * @param task
     * @param attribute
     * @param maxProgress
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
     * @param player
     * @param level
     * @param task
     * @param attribute
     * @param bit
     * @param targetValue
     */
    protected fun progressFlaggedTask(
        player: Player, level: DiaryLevel, task: Int, attribute: String, bit: Int, targetValue: Int
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
     * @param player
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
     * On area left
     *
     * @param player
     */
    protected open fun onAreaLeft(player: Player) {}

    /**
     * On resource produced
     *
     * @param player
     * @param event
     */
    protected open fun onResourceProduced(player: Player, event: ResourceProducedEvent) {}

    /**
     * On npc killed
     *
     * @param player
     * @param event
     */
    protected open fun onNpcKilled(player: Player, event: NPCKillEvent) {}

    /**
     * On teleported
     *
     * @param player
     * @param event
     */
    protected open fun onTeleported(player: Player, event: TeleportEvent) {}

    /**
     * On fire lit
     *
     * @param player
     * @param event
     */
    protected open fun onFireLit(player: Player, event: LitFireEvent) {}

    /**
     * On light source lit
     *
     * @param player
     * @param event
     */
    protected open fun onLightSourceLit(player: Player, event: LitLightSourceEvent) {}

    /**
     * On interacted
     *
     * @param player
     * @param event
     */
    protected open fun onInteracted(player: Player, event: InteractionEvent) {}

    /**
     * On button clicked
     *
     * @param player
     * @param event
     */
    protected open fun onButtonClicked(player: Player, event: ButtonClickEvent) {}

    /**
     * On dialogue opened
     *
     * @param player
     * @param event
     */
    protected open fun onDialogueOpened(player: Player, event: DialogueOpenEvent) {}

    /**
     * On dialogue closed
     *
     * @param player
     * @param event
     */
    protected open fun onDialogueClosed(player: Player, event: DialogueCloseEvent) {}

    /**
     * On dialogue option selected
     *
     * @param player
     * @param event
     */
    protected open fun onDialogueOptionSelected(player: Player, event: DialogueOptionSelectionEvent) {}

    /**
     * On used with
     *
     * @param player
     * @param event
     */
    protected open fun onUsedWith(player: Player, event: UseWithEvent) {}

    /**
     * On picked up
     *
     * @param player
     * @param event
     */
    protected open fun onPickedUp(player: Player, event: PickUpEvent) {}

    /**
     * On interface opened
     *
     * @param player
     * @param event
     */
    protected open fun onInterfaceOpened(player: Player, event: InterfaceOpenEvent) {}

    /**
     * On interface closed
     *
     * @param player
     * @param event
     */
    protected open fun onInterfaceClosed(player: Player, event: InterfaceCloseEvent) {}

    /**
     * On attribute set
     *
     * @param player
     * @param event
     */
    protected open fun onAttributeSet(player: Player, event: AttributeSetEvent) {}

    /**
     * On attribute removed
     *
     * @param player
     * @param event
     */
    protected open fun onAttributeRemoved(player: Player, event: AttributeRemoveEvent) {}

    /**
     * On spell cast
     *
     * @param player
     * @param event
     */
    protected open fun onSpellCast(player: Player, event: SpellCastEvent) {}

    /**
     * On item alchemized
     *
     * @param player
     * @param event
     */
    protected open fun onItemAlchemized(player: Player, event: ItemAlchemizationEvent) {}

    /**
     * On item equipped
     *
     * @param player
     * @param event
     */
    protected open fun onItemEquipped(player: Player, event: ItemEquipEvent) {}

    /**
     * On item unequipped
     *
     * @param player
     * @param event
     */
    protected open fun onItemUnequipped(player: Player, event: ItemUnequipEvent) {}

    /**
     * On item purchased from shop
     *
     * @param player
     * @param event
     */
    protected open fun onItemPurchasedFromShop(player: Player, event: ItemShopPurchaseEvent) {}

    /**
     * On item sold to shop
     *
     * @param player
     * @param event
     */
    protected open fun onItemSoldToShop(player: Player, event: ItemShopSellEvent) {}

    /**
     * On job assigned
     *
     * @param player
     * @param event
     */
    protected open fun onJobAssigned(player: Player, event: JobAssignmentEvent) {}

    /**
     * On fairy ring dialed
     *
     * @param player
     * @param event
     */
    protected open fun onFairyRingDialed(player: Player, event: FairyRingDialEvent) {}

    /**
     * On summoning points recharged
     *
     * @param player
     * @param event
     */
    protected open fun onSummoningPointsRecharged(player: Player, event: SummoningPointsRechargeEvent) {}

    /**
     * On prayer points recharged
     *
     * @param player
     * @param event
     */
    protected open fun onPrayerPointsRecharged(player: Player, event: PrayerPointsRechargeEvent) {}
}