package content.global.skill.agility.courses

import content.region.kandarin.barcrawl.handlers.BarcrawlManager
import core.api.*
import core.cache.def.impl.ItemDefinition
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.dialogue.Dialogue
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.node.Node
import core.game.node.entity.combat.ImpactHandler.HitsplatType
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.PluginManager
import org.rs.consts.NPCs
import org.rs.consts.Sounds

/**
 * Barbarian outpost course.
 */
@Initializable
class BarbarianOutpostCourse @JvmOverloads constructor(player: Player? = null) : content.global.skill.agility.AgilityCourse(player, 6, 46.2) {

    override fun createInstance(player: Player): content.global.skill.agility.AgilityCourse {
        return BarbarianOutpostCourse(player)
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val id = node.id
        getCourse(player)
        when (id) {
            2115, 2116 -> if (!BarcrawlManager.getInstance(player).isFinished || BarcrawlManager.getInstance(player).isStarted()) {
                player.dialogueInterpreter.open(384)
            } else {
                DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
            }
            2282 -> handleRopeSwing(player, node as Scenery)
            2294 -> handleLogBalance(player, node as Scenery)
            2302 -> handleLedgeBalance(player, node as Scenery)
            20211 -> {
                if (player.location.x < node.location.x) {
                    return true
                }
                content.global.skill.agility.AgilityHandler.climb(player, 2, ClimbActionHandler.CLIMB_UP, player.location.transform(-1, 0, 1), 8.0, "You climb the netting...")
            }
            1948 -> {
                if (player.location.x > node.location.x) {
                    player.packetDispatch.sendMessage("You cannot climb from this side.")
                    return true
                }
                val flag = if (node.location == Location(2536, 3553, 0)) 4 else if (node.location == Location(2539, 3553, 0)) 5 else 6
                sendMessage(player, "You climb the low wall...")
                content.global.skill.agility.AgilityHandler.forceWalk(player, flag, node.location.transform(-1, 0, 0), node.location.transform(1, 0, 0), Animation.create(839), 10, 13.5, null)
            }
            455 -> BarcrawlManager.getInstance(player).read()
            385 -> {
                sendMessage(player, "The scorpion stings you!")
                impact(player, 3, HitsplatType.NORMAL)
            }
            386 -> {
                sendMessage(player, "The scorpion stings you!")
                impact(player, 3, HitsplatType.NORMAL)
            }
            387 -> {
                sendMessage(player, "The scorpion stings you!")
                impact(player, 3, HitsplatType.NORMAL)
            }
        }
        return true
    }

    private fun handleRopeSwing(player: Player, `object`: Scenery) {
        if (player.location.y < 3554) {
            sendMessage(player, "You cannot do that from here.")
            return
        }
        if (ropeDelay > GameWorld.ticks) {
            sendMessage(player, "The rope is being used.")
            return
        }
        if (content.global.skill.agility.AgilityHandler.hasFailed(player, 1, 0.1)) {
            content.global.skill.agility.AgilityHandler.fail(player, 0, Location.create(2549, 9951, 0), null, getHitAmount(player), "You slip and fall to the pit below.")
            return
        }
        ropeDelay = GameWorld.ticks + 2
        content.global.skill.agility.AgilityHandler.forceWalk(player, 0, player.location, Location.create(2551, 3549, 0), Animation.create(751), 50, 22.0, "You skillfully swing across.", 1)
        playAudio(player, Sounds.SWING_ACROSS_2494, 1)
        animateScenery(player, `object`, 497, true)
    }

    private fun handleLogBalance(player: Player, `object`: Scenery) {
        val failed = content.global.skill.agility.AgilityHandler.hasFailed(player, 1, 0.5)
        val end = if (failed) Location.create(2545, 3546, 0) else Location.create(2541, 3546, 0)
        sendMessage(player, "You walk carefully across the slippery log...")
        content.global.skill.agility.AgilityHandler.walk(player, if (failed) -1 else 1, Location.create(2551, 3546, 0), end, Animation.create(155), if (failed) 0.0 else 13.5, if (failed) null else "...You make it safely to the other side.")
        if (failed) {
            content.global.skill.agility.AgilityHandler.walk(player, -1, player.location, Location.create(2545, 3546, 0), Animation.create(155), 0.0, null)
            GameWorld.Pulser.submit(getSwimPulse(player))
            return
        }
    }

    private fun getSwimPulse(player: Player): Pulse {
        return object : Pulse(1, player) {
            var counter = 0
            override fun pulse(): Boolean {
                when (++counter) {
                    6 -> {
                        player.animate(Animation.create(771))
                        sendMessage(player, "...You loose your footing and fall into the water. Something in the water bites you.")
                    }

                    7 -> {
                        player.graphics(Graphic.create(68))
                        player.properties.teleportLocation = Location.create(2545, 3545, 0)
                        player.impactHandler.manualHit(player, getHitAmount(player), HitsplatType.NORMAL)
                        content.global.skill.agility.AgilityHandler.walk(player, -1, Location.create(2545, 3545, 0), Location.create(2545, 3543, 0), Animation.create(152), 0.0, null)
                    }
                    11 -> {
                        player.properties.teleportLocation = Location.create(2545, 3542, 0)
                        return true
                    }
                }
                return false
            }
        }
    }

    private fun handleLedgeBalance(player: Player, `object`: Scenery) {
        val failed = content.global.skill.agility.AgilityHandler.hasFailed(player, 1, 0.3)
        val end = if (failed) Location.create(2534, 3547, 1) else Location.create(2532, 3547, 1)
        content.global.skill.agility.AgilityHandler.walk(player, if (failed) -1 else 3, Location.create(2536, 3547, 1), end, Animation.create(157), if (failed) 0.0 else 22.0, if (failed) null else "You skillfully edge across the gap.")
        sendMessage(player, "You put your foot on the ledge and try to edge across..")
        if (failed) {
            content.global.skill.agility.AgilityHandler.fail(player, 3, Location.create(2534, 3545, 0), Animation(760), getHitAmount(player), "You slip and fall to the pit below.")
            return
        }
    }

    override fun configure() {
        SceneryDefinition.forId(2115).handlers["option:open"] = this
        SceneryDefinition.forId(2116).handlers["option:open"] = this
        SceneryDefinition.forId(2282).handlers["option:swing-on"] = this
        SceneryDefinition.forId(2294).handlers["option:walk-across"] = this
        SceneryDefinition.forId(20211).handlers["option:climb-over"] = this
        SceneryDefinition.forId(2302).handlers["option:walk-across"] = this
        SceneryDefinition.forId(1948).handlers["option:climb-over"] = this
        ItemDefinition.forId(455).handlers["option:read"] = this
        NPCDefinition.forId(385).handlers["option:pick-up"] = this
        NPCDefinition.forId(386).handlers["option:pick-up"] = this
        NPCDefinition.forId(387).handlers["option:pick-up"] = this
        PluginManager.definePlugin(BarbarianGuardDialogue())
    }

    override fun getDestination(node: Node, n: Node): Location? {
        if (n is Scenery) {
            when (n.getId()) {
                2282 -> return Location.create(2551, 3554, 0)
            }
        }
        return null
    }


    /**
     * Barbarian guard dialogue.
     */
    class BarbarianGuardDialogue(player: Player? = null) : Dialogue(player) {

        override fun newInstance(player: Player): Dialogue {
            return BarbarianGuardDialogue(player)
        }

        override fun open(vararg args: Any): Boolean {
            if (!BarcrawlManager.getInstance(player).isStarted()) {
                npc("O, waddya want?")
            } else if (BarcrawlManager.getInstance(player).isFinished && !BarcrawlManager.getInstance(player).isStarted()) {
                npc("'Ello friend.")
                stage = 50
            } else {
                npc("So, how's the Barcrawl coming along?")
                stage = 20
            }
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            when (stage) {
                0 -> {
                    options("I want to come through this gate.", "I want some money.")
                    stage++
                }
                1 -> when (buttonId) {
                    1 -> {
                        player("I want to come through this gate.")
                        stage = 5
                    }
                    2 -> {
                        player("I want some money.")
                        stage = 3
                    }
                }
                3 -> {
                    npc("Do I look like a bank to you?")
                    stage++
                }
                4 -> end()
                5 -> if (BarcrawlManager.getInstance(player).isFinished) {
                    npc("You may pass if you like. You are a true", "barbarian now.")
                    stage = 4
                } else {
                    npc("Barbarians only. Are you a barbarian? You don't look", "like one.")
                    stage++
                }
                6 -> {
                    options("Hmm, yep you've got me there.", "Looks can be deceiving, I am in fact a barbarian.")
                    stage++
                }
                7 -> when (buttonId) {
                    1 -> {
                        player("Hmm, yep you've got me there.")
                        stage = 4
                    }
                    2 -> {
                        player("Looks can be deceiving, I am in fact a barbarian.")
                        stage++
                    }
                }
                8 -> {
                    npc("If you're a barbarian you need to be able to drink like", "one. We barbarians like a good drink.")
                    stage++
                }
                9 -> {
                    npc("I have the perfect challenge for you... the Alfred", "Grimhand Barcrawl! First completed by Alfred", "Grimhand.")
                    stage++
                }
                10 -> {
                    BarcrawlManager.getInstance(player).reset()
                    BarcrawlManager.getInstance(player).setStarted(true)
                    player.inventory.add(BarcrawlManager.BARCRAWL_CARD, player)
                    sendDialogue("The guard hands you a Barcrawl card.")
                    stage++
                }
                11 -> {
                    npc("Take that card to each of the bards named on it. The", "bartenders will know what it means. We're kinda well", "known.")
                    stage++
                }
                12 -> {
                    npc("They'll give you their strongest drink and sign your", "card. When you've done all that, we'll be happy to let", "you in.")
                    stage++
                }

                13 -> end()
                20 -> if (!BarcrawlManager.getInstance(player).hasCard()) {
                    player("I've lost my barcrawl card...")
                    stage = 23
                } else if (BarcrawlManager.getInstance(player).isFinished) {
                    player("I tink I jusht 'bout done dem all... but I losht count...")
                    stage = 24
                } else {
                    player("I haven't finished it yet.")
                    stage++
                }

                21 -> {
                    npc("Well come back when you have, you lightweight.")
                    stage++
                }

                22 -> end()
                23 -> {
                    npc("What are you like? You're gonna have to start all over", "now.")
                    stage = 10
                }

                24 -> {
                    if (!player.inventory.containsItem(BarcrawlManager.BARCRAWL_CARD)) {
                        end()
                    }
                    BarcrawlManager.getInstance(player).setStarted(false)
                    player.bank.remove(BarcrawlManager.BARCRAWL_CARD)
                    player.inventory.remove(BarcrawlManager.BARCRAWL_CARD)
                    sendDialogue(player!!,"You give the card to the barbarian.")
                    stage++
                }

                25 -> {
                    npc("Yep that seems fine, you can come in now. I never", "learned to read, but you look like you've drunk plenty.")
                    stage = 22
                }

                50 -> end()
            }
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(NPCs.BARBARIAN_GUARD_384)
        }
    }

    companion object {
        private var ropeDelay = 0
    }
}
