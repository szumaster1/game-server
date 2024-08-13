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
 * @param handler The combat swing handler associated with the attack.
 * @param animation The animation to be played during the attack.
 * @param startGraphic The graphic to be displayed at the start of the attack (optional).
 * @param endGraphic The graphic to be displayed at the end of the attack (optional).
 * @param projectile The projectile associated with the attack (optional).
 */
open class SwitchAttack @JvmOverloads constructor(
    handler: CombatSwingHandler?, // Combat swing handler for managing attack actions
    animation: Animation?, // Animation to be executed during the attack
    startGraphic: Graphic? = null, // Optional graphic at the start of the attack
    endGraphic: Graphic? = null, // Optional graphic at the end of the attack
    projectile: Projectile? = null // Optional projectile for ranged attacks
) {

    var handler: CombatSwingHandler? = null // Variable to hold the combat swing handler
    val startGraphic: Graphic? // Variable to hold the start graphic
    val animation: Animation? // Variable to hold the animation
    val endGraphic: Graphic? // Variable to hold the end graphic
    val projectile: Projectile? // Variable to hold the projectile

    @JvmField
    var isUseHandler: Boolean = false // Flag to indicate if the handler should be used

    @JvmField
    var maximumHit: Int = -1 // Variable to store the maximum hit value

    // Secondary constructor to initialize with handler, animation, and projectile
    constructor(handler: CombatSwingHandler?, animation: Animation?, projectile: Projectile?) : this(
        handler = handler,
        animation = animation,
        startGraphic = null, // Default start graphic is null
        endGraphic = null, // Default end graphic is null
        projectile = projectile // Assign the provided projectile
    )

    // Constructor to initialize with a combat style
    constructor(style: CombatStyle) : this(
        handler = style.swingHandler, // Use the swing handler from the combat style
        animation = null, // Default animation is null
        startGraphic = null, // Default start graphic is null
        endGraphic = null // Default end graphic is null
    )

    init {
        this.handler = handler // Assign the handler to the class variable
        this.animation = animation // Assign the animation to the class variable
        this.startGraphic = startGraphic // Assign the start graphic to the class variable
        this.endGraphic = endGraphic // Assign the end graphic to the class variable
        this.projectile = projectile // Assign the projectile to the class variable
    }

    /**
     * Can select
     *
     * @param entity The entity attempting to perform the attack.
     * @param victim The target entity of the attack.
     * @param state The current battle state.
     * @return Returns true if the attack can be selected, false otherwise.
     */
    open fun canSelect(entity: Entity?, victim: Entity?, state: BattleState?): Boolean {
        return true // Default implementation allows selection
    }

    val style: CombatStyle? get() = handler!!.type // Getter for the combat style from the handler

    /**
     * Set use handler
     *
     * @param useHandler Indicates whether to use the handler.
     * @return Returns the current instance of SwitchAttack for method chaining.
     */
    fun setUseHandler(useHandler: Boolean): SwitchAttack {
        this.isUseHandler = useHandler // Set the use handler flag
        return this // Return the current instance for chaining
    }

    /**
     * Set maximum hit
     *
     * @param maximumHit The maximum hit value to be set.
     * @return Returns the current instance of SwitchAttack for method chaining.
     */
    fun setMaximumHit(maximumHit: Int): SwitchAttack {
        this.maximumHit = maximumHit // Set the maximum hit value
        return this // Return the current instance for chaining
    }

}
