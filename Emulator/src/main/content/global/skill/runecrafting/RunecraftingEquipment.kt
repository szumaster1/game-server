package content.global.skill.runecrafting

import core.game.node.Node
import core.game.node.entity.player.Player
import content.global.skill.runecrafting.items.Staves
import content.global.skill.runecrafting.items.Tiara
import core.api.setVarp
import core.game.interaction.InteractionListener
import org.rs.consts.Vars

class RunecraftingEquipment : InteractionListener {

    private val tiara = IntArray(Tiara.values().size) { Tiara.values()[it].item.id }
    private val staves = IntArray(Staves.values().size) { Staves.values()[it].item }
    private val stavesMap = HashMap<Int, Int>()
    private val tiaraMap = HashMap<Int, Int>()

    init {
        for (i in 13630..13641) {
            stavesMap[i] = 1 shl (i - 13630)
        }
        for (index in tiara.indices) {
            tiaraMap[tiara[index]] = 1 shl index
        }
    }

    override fun defineListeners() {
        flagInstant()

        onEquip(tiara + staves) { player, n ->
            setVarbit(player, n)
            return@onEquip true
        }

        onUnequip(tiara + staves) { player, _ ->
            resetVarp(player)
            return@onUnequip true
        }
    }

    private fun setVarbit(player: Player, node: Node) {
        val num = tiaraMap[node.id] ?: stavesMap[node.id] ?: 0
        setVarp(player, Vars.VARP_SCENERY_ABYSS, num)
    }

    private fun resetVarp(player: Player) {
        setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
    }
}
