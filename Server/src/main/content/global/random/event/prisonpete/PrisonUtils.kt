package content.global.random.event.prisonpete

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.system.timer.impl.AntiMacro
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.context.Graphic
import core.utilities.RandomFunction

object PrisonUtils {

    val PRISON_LOCATION: Location = Location.create(2086, 4462, 0)

    const val GET_REWARD = "/save:prisonpete:reward"
    const val RE_LOGOUT = "/save:prisonpete:logout"
    const val POP_KEY = "/save:prisonpete:pop-animals"
    const val POP_KEY_FALSE = "/save:prisonpete:pop-incorrect"
    const val POP_KEY_VALUE = "/save:prisonpete:value"
    const val PREVIOUS_LOCATION = "prisonpete:location"

    val ANIMAL_ID = intArrayOf(3119, 3120, 3121, 3122)
    val PRISON_ZONE = ZoneBorders(2075, 4458, 2096, 4474)

    fun cleanup(player: Player) {
        player.properties.teleportLocation = getAttribute(player, PREVIOUS_LOCATION, null)
        clearLogoutListener(player, RE_LOGOUT)
        removeAttributes(player, POP_KEY, GET_REWARD, PREVIOUS_LOCATION, RE_LOGOUT, POP_KEY_FALSE, POP_KEY_VALUE)
        sendMessage(player, "Welcome back to ${GameWorld.settings!!.name}.")
        if (anyInInventory(player, Items.PRISON_KEY_6966)) {
            removeAll(player, Items.PRISON_KEY_6966)
        }
    }

    fun bringKey(player: Player) {
        queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    forceWalk(player, Location.create(2084, 4461, 0), "smart")
                    return@queueScript keepRunning(player)
                }

                1 -> {
                    face(player, findNPC(NPCs.PRISON_PETE_3118)!!.location)
                    openDialogue(player, PrisonPeteDialogue(dialOpt = 2))
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

    fun checkKey(player: Player) {
        queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    findNPC(NPCs.PRISON_PETE_3118)!!.faceLocation(Location.create(2086, 4458, 0))
                    animate(findNPC(NPCs.PRISON_PETE_3118)!!, 881)
                    return@queueScript delayScript(player, 2)
                }

                1 -> {
                    face(findNPC(NPCs.PRISON_PETE_3118)!!, player.location)
                    openDialogue(player, PrisonPeteDialogue(dialOpt = 3))
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

    fun teleport(player: Player) {
        queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    setAttribute(
                        player,
                        PREVIOUS_LOCATION,
                        player.location
                    )
                    visualize(player, 714, Graphic(308, 85, 50))
                    playAudio(player, Sounds.TELEPORT_ALL_200)
                    return@queueScript delayScript(player, 3)
                }

                1 -> {
                    registerLogoutListener(player, RE_LOGOUT) { p ->
                        p.location = getAttribute(p, PREVIOUS_LOCATION, player.location)
                    }
                    teleport(player, PRISON_LOCATION)
                    return@queueScript delayScript(player, 1)
                }

                2 -> {
                    setAttribute(player, POP_KEY, 0)
                    AntiMacro.terminateEventNpc(player)
                    resetAnimator(player)
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

    // https://runescape.wiki/w/Prison_Pete?oldid=2031476
    fun reward(player: Player) {
        val chaosRunes = (19..23).random()
        val mithrilArrowTips = (47..50).random()
        val randomReward = getAttribute(
            player,
            GET_REWARD, RandomFunction.random(0, 9)
        )
        when (randomReward) {
            0 -> addItemOrDrop(player, Items.LAW_RUNE_563, 10)
            1 -> addItemOrDrop(player, Items.SAPPHIRE_1608, 5)
            2 -> addItemOrDrop(player, Items.RUBY_1604, 4)
            3 -> addItemOrDrop(player, Items.DIAMOND_1602, 2)
            4 -> addItemOrDrop(player, Items.GRIMY_SNAPDRAGON_3052, 4)
            5 -> addItemOrDrop(player, Items.UNCUT_DIAMOND_1618, 3)
            6 -> addItemOrDrop(player, Items.COINS_995, 527)
            7 -> addItemOrDrop(player, Items.UGTHANKI_KEBAB_1884, 2)
            8 -> addItemOrDrop(player, Items.MITHRIL_ARROWTIPS_42, mithrilArrowTips)
            9 -> addItemOrDrop(player, Items.CHAOS_RUNE_562, chaosRunes)
        }
    }
}
