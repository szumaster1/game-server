package content.global.skill.construction.decoration

import content.global.skill.construction.Decoration
import core.api.*
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import org.rs.consts.Animations

/**
 * Handles chair and bench options.
 * @author Emperor
 */
@Initializable
class ChairBenchPlugin : OptionHandler() {

    /**
     * The decorations that can be seated on.
     */
    private val chairIDs = listOf(
        Decoration.CRUDE_CHAIR to arrayOf(Animations.SITTING_IN_CRUDE_CHAIR_4073, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.WOODEN_CHAIR to arrayOf(Animations.SITTING_IN_CHAIR_4075, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.ROCKING_CHAIR to arrayOf(Animations.ROCKING_CHAIR_4079, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.OAK_CHAIR to arrayOf(Animations.OAK_CHAIR_4081, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.OAK_ARMCHAIR to arrayOf(Animations.OAK_CHAIR_4083, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.TEAK_ARMCHAIR to arrayOf(Animations.TEAK_CHAIR_4085, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.MAHOGANY_ARMCHAIR to arrayOf(Animations.MAHOGANY_CHAIR_4087, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.BENCH_WOODEN to arrayOf(Animations.BENCH_4089, Animations.SITTING_DOWN_4104),
        Decoration.BENCH_OAK to arrayOf(Animations.OAK_BENCH_4091, Animations.SITTING_DOWN_4104),
        Decoration.BENCH_CARVED_OAK to arrayOf(Animations.SIT_ON_CARVED_OAK_BENCH_4093, Animations.SITTING_DOWN_4104),
        Decoration.BENCH_TEAK to arrayOf(Animations.SITTING_ON_TEAK_BENCH_4095, Animations.SITTING_DOWN_4104),
        Decoration.BENCH_CARVED_TEAK to arrayOf(Animations.SITTING_ON_CARVED_TEAK_BENCH_4097, Animations.SITTING_DOWN_4104),
        Decoration.BENCH_MAHOGANY to arrayOf(Animations.SITTING_ON_MAHOGANY_BENCH_4099, Animations.SITTING_DOWN_4104),
        Decoration.BENCH_GILDED to arrayOf(Animations.SITTING_ON_GILDED_MAHOGANY_BENCH_4101, Animations.SITTING_DOWN_4104),
        Decoration.CARVED_TEAK_BENCH to arrayOf(Animations.SITTING_ON_CARVED_TEAK_BENCH_4097, Animations.SITTING_DOWN_4104),
        Decoration.MAHOGANY_BENCH to arrayOf(Animations.SITTING_ON_MAHOGANY_BENCH_4099, Animations.SITTING_DOWN_4104),
        Decoration.GILDED_BENCH to arrayOf(Animations.SITTING_ON_GILDED_MAHOGANY_BENCH_4101, Animations.SITTING_DOWN_4104),
        Decoration.OAK_THRONE to arrayOf(Animations.SITTING_IN_THRONE_4111, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.TEAK_THRONE to arrayOf(Animations.SITTING_IN_THRONE_B_4112, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.MAHOGANY_THRONE to arrayOf(Animations.SITTING_IN_THRONE_C_4113, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.GILDED_THRONE to arrayOf(Animations.SITTING_IN_THRONE_D_4114, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.SKELETON_THRONE to arrayOf(Animations.SITTING_IN_SKELETON_THRONE_4115, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.CRYSTAL_THRONE to arrayOf(Animations.SITTING_IN_CRYSTAL_THRONE_4116, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103),
        Decoration.DEMONIC_THRONE to arrayOf(Animations.SITTING_IN_DEMONIC_THRONE_4117, Animations.SITTING_DOWN_ON_POH_WORKBENCH_4103)
    )

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        chairIDs.forEach { (decoration, ids) ->
            SceneryDefinition.forId(decoration.objectId).handlers["option:sit-on"] = this
        }
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val scenery = node as Scenery
        val (animId, sitAnimId) = chairIDs.firstOrNull { (it.first as Decoration).objectId == scenery.id }
            ?.let { it.second[0] to it.second[1] } ?: return false

        val newAnimation = if (scenery.type == 11) animId + 1 else animId
        forceMove(
            player,
            player.location,
            node.location,
            ForceMovement.WALK_ANIMATION.id,
            Animation.create(sitAnimId).id,
            node.direction,
            ForceMovement.WALKING_SPEED
        )
        lockInteractions(player, 600000)
        submitIndividualPulse(player, object : Pulse(2) {
            override fun pulse(): Boolean {
                animate(player, newAnimation)
                return false
            }

            override fun stop() {
                super.stop()
                unlock(player)
                animate(player, sitAnimId + 2)
            }
        })
        return true
    }
}
