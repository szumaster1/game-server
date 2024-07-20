package content.region.fremennik.handlers.neitiznot

import content.global.skill.production.crafting.handlers.YakArmourCraftingHandler
import content.region.fremennik.dialogue.neitiznot.MawnisBurowgarDialogue
import content.region.fremennik.dialogue.neitiznot.ThakkradYakDialogue
import core.api.consts.Region
import core.api.consts.Regions
import core.game.interaction.Option
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBuilder
import core.plugin.ClassScanner.definePlugins
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class NeitiznotZone : MapZone("Neitiznot zone", true), Plugin<Any?> {

    override fun newInstance(arg: Any?): Plugin<Any?> {
        ZoneBuilder.configure(this)
        definePlugins(
            MawnisBurowgarDialogue(),
            ThakkradYakDialogue(),
            YakArmourCraftingHandler(),
            YakArmourCraftingHandler()
        )
        return this
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        if (e is Player) {
            val player = e.asPlayer()
            when (target.id) {
                21301 -> {
                    player.bank.open()
                    return true
                }

                5506 -> if (option.name == "Craft-goods") {
                    player.dialogueInterpreter.open("thakkrad-yak")
                    return true
                }
            }
        }
        return super.interact(e, target, option)
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    override fun configure() {
        registerRegion(Regions.NEITIZNOT_9275)
    }
}
