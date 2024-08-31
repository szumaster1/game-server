/*package core.game.world.map.zone.impl

import cfg.consts.Components
import core.api.openInterface
import core.api.setInterfaceText
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.Option
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.plugin.Initializable

/**
 * Represents the Tutorial zone area.
 */
@Initializable
class TutorialZone : MapZone("tutorial", false) {

    val ZONES: List<ZoneBorders> = listOf(
        ZoneBorders.forRegion(12336), // START_ZONE
        ZoneBorders.forRegion(12335), // START_ZONE_2
        ZoneBorders.forRegion(12592), // MAGIC_ZONE
        ZoneBorders.forRegion(12436), // SMITHING_ZONE
        ZoneBorders.forRegion(12366), // SURVIVAL_ZONE
        ZoneBorders.forRegion(12080)  // QUEST_ZONE
    )

    /**
     * Configures the zones within the Tutorial area.
     */
    override fun configure() {
        ZONES.forEach(this::register)
    }

    override fun enter(e: Entity?): Boolean {
        return super.enter(e)
    }
    /**
     * Handles examine option interaction with entities inside the zone.
     */
    override fun interact(entity: Entity?, target: Node?, option: Option?): Boolean {
        super.interact(entity, target, option)
        if (entity !is Player) return false
        val player = entity.asPlayer()
        if (option!!.name == "examine") {
            openInterface(player, Components.TUTORIAL2_MESBOX_674)
            if (player.getAttribute("tutorial:stage", 0) in 1..99) {
                if (target!!.interaction.options.equals("Examine") && entity.interaction.isInitialized) {
                    when (target.asNpc()) {
                        is Node -> NPCDefinition.getDefinitions()[target.id]?.examine
                        else -> {
                            val sceneryId = target?.id
                            SceneryDefinition.getDefinitions()[sceneryId]?.examine
                        }
                    }
                    let {
                        setInterfaceText(player, "<b>$it", Components.TUTORIAL2_MESBOX_674, 1)

                    }
                }
                return true
            }
        }
        return true
    }

    companion object {
        val instance = TutorialZone()
    }
}
*/