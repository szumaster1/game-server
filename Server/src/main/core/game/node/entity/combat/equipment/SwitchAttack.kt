package core.game.node.entity.combat.equipment

import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.impl.Projectile
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

open class SwitchAttack @JvmOverloads constructor(handler: CombatSwingHandler?, animation: Animation?, startGraphic: Graphic? = null, endGraphic: Graphic? = null, projectile: Projectile? = null) {

    var handler: CombatSwingHandler? = null
    val startGraphic: Graphic?
    val animation: Animation?
    val endGraphic: Graphic?
    val projectile: Projectile?
    @JvmField var isUseHandler: Boolean = false
    @JvmField var maximumHit: Int = -1

    constructor(handler: CombatSwingHandler?, animation: Animation?, projectile: Projectile?) : this(
        handler,
        animation,
        null,
        null,
        projectile
    )

    constructor(style: CombatStyle) : this(style.swingHandler, null, null, null)

    init {
        this.handler = handler
        this.animation = animation
        this.startGraphic = startGraphic
        this.endGraphic = endGraphic
        this.projectile = projectile
    }

    open fun canSelect(entity: Entity?, victim: Entity?, state: BattleState?): Boolean {
        return true
    }

    val style: CombatStyle? get() = handler!!.type

    fun setUseHandler(useHandler: Boolean): SwitchAttack {
        this.isUseHandler = useHandler
        return this
    }

    fun setMaximumHit(maximumHit: Int): SwitchAttack {
        this.maximumHit = maximumHit
        return this
    }

}