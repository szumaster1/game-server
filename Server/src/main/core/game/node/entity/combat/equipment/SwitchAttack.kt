package core.game.node.entity.combat.equipment

import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.impl.Projectile
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

/**
 * Switch attack
 *
 * @constructor
 *
 * @param handler
 * @param animation
 * @param startGraphic
 * @param endGraphic
 * @param projectile
 */
open class SwitchAttack @JvmOverloads constructor(
    handler: CombatSwingHandler?,
    animation: Animation?,
    startGraphic: Graphic? = null,
    endGraphic: Graphic? = null,
    projectile: Projectile? = null
) {

    var handler: CombatSwingHandler? = null
    val startGraphic: Graphic?
    val animation: Animation?
    val endGraphic: Graphic?
    val projectile: Projectile?

    @JvmField
    var isUseHandler: Boolean = false

    @JvmField
    var maximumHit: Int = -1

    constructor(handler: CombatSwingHandler?, animation: Animation?, projectile: Projectile?) : this(
        handler = handler,
        animation = animation,
        startGraphic = null,
        endGraphic = null,
        projectile = projectile
    )

    constructor(style: CombatStyle) : this(
        handler = style.swingHandler,
        animation = null,
        startGraphic = null,
        endGraphic = null
    )

    init {
        this.handler = handler
        this.animation = animation
        this.startGraphic = startGraphic
        this.endGraphic = endGraphic
        this.projectile = projectile
    }

    /**
     * Can select
     *
     * @param entity
     * @param victim
     * @param state
     * @return
     */
    open fun canSelect(entity: Entity?, victim: Entity?, state: BattleState?): Boolean {
        return true
    }

    val style: CombatStyle? get() = handler!!.type

    /**
     * Set use handler
     *
     * @param useHandler
     * @return
     */
    fun setUseHandler(useHandler: Boolean): SwitchAttack {
        this.isUseHandler = useHandler
        return this
    }

    /**
     * Set maximum hit
     *
     * @param maximumHit
     * @return
     */
    fun setMaximumHit(maximumHit: Int): SwitchAttack {
        this.maximumHit = maximumHit
        return this
    }

}