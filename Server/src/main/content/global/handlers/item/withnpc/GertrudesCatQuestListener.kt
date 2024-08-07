package content.global.handlers.item.withnpc

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.path.Path
import core.game.world.map.path.Pathfinder

/**
 * Gertrudes cat quest listener
 *
 * @constructor Gertrudes cat quest listener
 */
class GertrudesCatQuestListener : InteractionListener {

    private val gertrudeCat = "Gertrude's Cat"
    private val bendDownAnim = 827

    override fun defineListeners() {

        onUseWith(IntType.NPC, Items.BUCKET_OF_MILK_1927, NPCs.GERTRUDES_CAT_2997) { player, used, with ->
            if (getQuestStage(player, gertrudeCat) == 20 && removeItem(player, used.asItem())) {
                addItem(player, Items.BUCKET_1925)
                animate(player, bendDownAnim) //bend down
                sendChat(with.asNpc(), "Mew!")
                setQuestStage(player, gertrudeCat, 30)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.DOOGLE_SARDINE_1552, NPCs.GERTRUDES_CAT_2997) { player, used, with ->
            if (getQuestStage(player, gertrudeCat) == 30 && removeItem(player, used.asItem())) {
                animate(player, bendDownAnim)
                sendChat(with.asNpc(), "Mew!")
                playAudio(player, Sounds.KITTENS_MEW_339)
                setQuestStage(player, gertrudeCat, 40)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.RAW_SARDINE_327, NPCs.GERTRUDES_CAT_2997) { player, _, _ ->
            sendMessage(player, "The cat doesn't seem interested in that.")
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.THREE_LITTLE_KITTENS_13236, NPCs.GERTRUDES_CAT_2997) { player, used, with ->
            if (removeItem(player, used.asItem())) {
                setQuestStage(player, gertrudeCat, 60)
                //below copied verbatim from original, I don't like it.
                Pulser.submit(object : Pulse(1) {
                    var count = 0
                    val kitten = core.game.node.entity.npc.NPC.create(761, player.location)
                    override fun pulse(): Boolean {
                        when (count) {
                            0 -> {
                                kitten.init()
                                kitten.face(with.asNpc())
                                with.asNpc().face(kitten)
                                with.asNpc().sendChat("Pur...")
                                kitten.sendChat("Pur...")
                                playAudio(player, Sounds.PURR_340)
                                val path: Path = Pathfinder.find(with.asNpc(), Location(3310, 3510, 1))
                                path.walk(with.asNpc())
                                val pathh = Pathfinder.find(kitten, Location(3310, 3510, 1))
                                pathh.walk(kitten)
                            }

                            5 -> {
                                kitten.clear()
                                setAttribute(player, "hidefluff", System.currentTimeMillis() + 60000)
                            }
                        }
                        count++
                        return count == 6
                    }
                })
            }

            return@onUseWith true
        }
    }
}
