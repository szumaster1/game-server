package core.api.consts

import core.api.setVarp
import core.game.node.entity.player.Player

interface QuestVars {

    companion object {
        var p: Player? = null

        fun rune_mysteries_map(value: Int) {
            for (value in (0..6))
                return setVarp(p!!, 63, value, true)

        }

        fun dorics_map(value: Int) {
            for (value in intArrayOf(0, 10, 100))
                return setVarp(p!!, 31, value, true)
        }

        /*
        fun champions_challenge_map(value: Int) {
            for (value in (1452..1461))
                return setVarbit(p!!, value, 1, true)
        }
         */

    }

}
