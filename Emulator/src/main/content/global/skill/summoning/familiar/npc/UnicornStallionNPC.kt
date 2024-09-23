package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import core.api.curePoison
import core.api.isPoisoned
import core.api.playAudio
import core.cache.def.impl.NPCDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin
import core.plugin.PluginManager.definePlugin
import org.rs.consts.Sounds

/**
 * Unicorn stallion familiar.
 */
@Initializable
class UnicornStallionNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6822) :
    Familiar(owner, id, 5400, 12039, 20, WeaponInterface.STYLE_CONTROLLED) {
    override fun construct(owner: Player, id: Int): Familiar {
        return UnicornStallionNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val player = special.node as Player
        playAudio(player, Sounds.HEALING_AURA_4372)
        visualize(Animation.create(8267), Graphic.create(1356))
        player.getSkills().heal((player.getSkills().maximumLifepoints * 0.15).toInt())
        return true
    }

    override fun configureFamiliar() {
        definePlugin(object : OptionHandler() {
            override fun newInstance(arg: Any?): Plugin<Any> {
                NPCDefinition.forId(6822).handlers["option:cure"] = this
                NPCDefinition.forId(6823).handlers["option:cure"] = this
                return this
            }

            override fun handle(player: Player, node: Node, option: String): Boolean {
                val familiar = node as Familiar
                if (!player.familiarManager.isOwner(familiar)) {
                    return true
                }
                val owner = player
                if (owner.getSkills().getLevel(Skills.MAGIC) < 2) {
                    player.sendMessage("You don't have enough summoning points left")
                    return true
                }
                if (!isPoisoned(owner)) {
                    player.sendMessage("You are not poisoned.")
                    return true
                }
                playAudio(player, Sounds.HEALING_AURA_4372)
                familiar.visualize(Animation.create(8267), Graphic.create(1356))
                curePoison(player)
                player.getSkills().updateLevel(Skills.SUMMONING, -2, 0)
                return true
            }
        })
    }

    override fun visualizeSpecialMove() {
        owner.visualize(Animation.create(7660), Graphic.create(1298))
    }

    override fun getIds(): IntArray {
        return intArrayOf(6822, 6823)
    }
}
