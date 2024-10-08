package content.global.travel

import core.api.*
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.tools.END_DIALOGUE
import org.rs.consts.Animations
import org.rs.consts.Graphics
import org.rs.consts.NPCs
import org.rs.consts.Scenery

class GnomeSpiritListener : InteractionListener {

    val spiritTrees = intArrayOf(Scenery.SPIRIT_TREE_1317, Scenery.SPIRIT_TREE_1293, Scenery.SPIRIT_TREE_1294)

    override fun defineListeners() {
        on(spiritTrees, IntType.SCENERY, "talk-to", "teleport") { player, node ->
            val option = getUsedOption(player)
            if (!GnomeSpiritTreeTeleportDialogue().hasQuestCompleted(player)) {
                return@on true
            }
            when (node.asNpc()) {
                NPC(NPCs.SPIRIT_TREE_3636) -> if (option == "talk-to") {
                    sendNPCDialogue(player, NPCs.SPIRIT_TREE_3636, "If you are a friend of the gnome people, you are a friend of mine, Do you wish to travel?")
                    addDialogueAction(player) { _, button ->
                        if (button >= 1) openDialogue(player, GnomeSpiritTreeTeleportDialogue())
                        return@addDialogueAction
                    }
                } else {
                    openDialogue(player, GnomeSpiritTreeTeleportDialogue(), NPC(NPCs.SPIRIT_TREE_3636))
                }
            }
            return@on true
        }
    }

    /**
     * Gnome spirit tree teleport dialogue.
     */
    inner class GnomeSpiritTreeTeleportDialogue : DialogueFile() {
        private val locationArray = arrayOf(
            Location(2542, 3170, 0),
            Location(2461, 3444, 0),
            Location(2556, 3259, 0),
            Location(3184, 3508, 0)
        )
        private val animationIds = arrayOf(Animation(Animations.HUMAN_SPIRIT_TREE_TELE_TO_7082), Animation(Animations.HUMAN_SPIRIT_TREE_TELE_FROM_7084))
        private val graphicIds = arrayOf(Graphic(Graphics.TELEPORTING_WITH_THE_GRAND_TREE_GFX_START_1228), Graphic(Graphics.TELEPORTING_WITH_THE_GRAND_TREE_GFX_END_1229))

        /**
         * Has quest completed.
         */
        fun hasQuestCompleted(player: Player): Boolean {
            if (!isQuestComplete(player, "Tree Gnome Village")) {
                sendDialogue(player, "The tree doesn't feel like talking.")
                stage = END_DIALOGUE
                return false
            }
            return true
        }

        private fun sendTeleport(player: Player, location: Location) {
            end()
            Pulser.submit(object : Pulse(1, player) {
                var count = 0
                override fun pulse(): Boolean {
                    when (count) {
                        0 -> visualize(player, animationIds[0], graphicIds[0])
                        3 -> teleport(player, location)
                        5 -> {
                            visualize(player, animationIds[1], graphicIds[1])
                            player.face(null)
                            if (withinDistance(player, Location.create(3184, 3508, 0))) {
                                finishDiaryTask(player, DiaryType.VARROCK, 1, 5)
                            }
                            return true
                        }
                    }
                    count++
                    return false
                }
            })
        }

        override fun handle(componentID: Int, buttonID: Int) {
            if (!GnomeSpiritTreeTeleportDialogue().hasQuestCompleted(player!!)) {
                stage = END_DIALOGUE
                return
            }
            when (stage) {
                0 -> {
                    setTitle(player!!, 4)
                    sendDialogueOptions(player!!, "Where would you like to go?", "Tree Gnome Village", "Tree Gnome Stronghold", "Battlefield of Khazard", "Grand Exchange").also { stage++ }
                }

                1 -> when (buttonID) {
                    1 -> sendTeleport(player!!, locationArray[0])
                    2 -> sendTeleport(player!!, locationArray[1])
                    3 -> sendTeleport(player!!, locationArray[2])
                    4 -> sendTeleport(player!!, locationArray[3])
                }
            }
        }
    }
}