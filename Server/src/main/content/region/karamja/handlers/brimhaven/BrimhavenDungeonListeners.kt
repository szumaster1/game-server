package content.region.karamja.handlers.brimhaven

import content.global.skill.support.agility.AgilityHandler
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.api.getAttribute
import core.api.location
import core.api.removeAttribute
import core.api.sendNPCDialogue
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Brimhaven dungeon listeners.
 */
class BrimhavenDungeonListeners : InteractionListener {

    companion object {
        private const val SANIBOCH_NPC = NPCs.SANIBOCH_1595
        private const val ENTRANCE = Scenery.DUNGEON_ENTRANCE_5083
        private const val EXIT = Scenery.EXIT_5084
        private val VINES = intArrayOf(5103, 5104, 5105, 5106, 5107)
        private val STEPPING_STONES = intArrayOf(5110, 5111)
        private val STAIRS = intArrayOf(5094, 5096, 5097, 5098)
        private val LOGS = intArrayOf(5088, 5090)
    }

    override fun defineListeners() {

        on(ENTRANCE, IntType.SCENERY, "enter"){ player, _ ->
            if (getAttribute(player, "saniboch:paid", false) || player.achievementDiaryManager.getDiary(DiaryType.KARAMJA)!!.isComplete) {
                ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, location(2713, 9564, 0))
                removeAttribute(player, "saniboch:paid")
            } else {
                sendNPCDialogue(player, NPCs.SANIBOCH_1595, "You can't go in there without paying!", FacialExpression.NEUTRAL)
            }
            return@on true
        }

        on(EXIT, IntType.SCENERY, "leave"){ player, _ ->
            player.properties.teleportLocation = Location.create(2745, 3152, 0)
            return@on true
        }

        on(STAIRS, IntType.SCENERY, "walk-up","walk-down"){ player, node ->
            BrimhavenUtils.handleStairs(node.asScenery(), player)
            return@on true
        }

        on(STEPPING_STONES, IntType.SCENERY, "jump-from"){ player, node ->
            BrimhavenUtils.handleSteppingStones(player, node.asScenery())
            return@on true
        }

        on(VINES, IntType.SCENERY, "chop-down"){ player, node ->
            BrimhavenUtils.handleVines(player, node.asScenery())
            return@on true
        }

        on(SANIBOCH_NPC, IntType.NPC, "pay"){ player, node ->
            player.dialogueInterpreter.open(NPCs.SANIBOCH_1595,node.asNpc(),10)
            return@on true
        }

        on(LOGS, IntType.SCENERY, "walk-across"){ player, node ->

            if (player.skills.getLevel(Skills.AGILITY) < 30) {
                player.packetDispatch.sendMessage("You need an agility level of 30 to cross this.")
                return@on true
            }
            if(node.id == 5088){
                AgilityHandler.walk(player, -1, player.location, Location.create(2687, 9506, 0), Animation.create(155), 0.0, null)
            } else {
                AgilityHandler.walk(player, -1, player.location, Location.create(2682, 9506, 0), Animation.create(155), 0.0, null)
            }
            return@on true
        }
    }
}