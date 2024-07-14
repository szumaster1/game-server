package content.minigame.mta.impl

import content.minigame.mta.MTAType
import content.minigame.mta.MTAZone
import core.api.consts.Music
import core.api.setAttribute
import core.api.setInterfaceText
import core.game.interaction.Option
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction
import core.tools.StringUtils

class GraveyardZone :
    MTAZone("Creature Graveyard", arrayOf(Item(6904), Item(6905), Item(6906), Item(6907), Item(1963), Item(6883))) {

    override fun update(player: Player?) {
        setInterfaceText(
            player!!,
            player.getSavedData().activityData.getPizazzPoints(type!!.ordinal).toString(),
            type!!.overlay.id,
            12
        )
    }

    override fun leave(entity: Entity, logout: Boolean): Boolean {
        if (entity is Player && PLAYERS.contains(entity)) {
            PLAYERS.remove(entity)
        }
        return super.leave(entity, logout)
    }

    override fun enter(entity: Entity): Boolean {
        if (entity is Player && !PLAYERS.contains(entity)) {
            PLAYERS.add(entity.asPlayer())
            if (!PULSE.isRunning) {
                PULSE.restart()
                PULSE.start()
                Pulser.submit(PULSE)
            }
            if(!entity.asPlayer().musicPlayer.hasUnlocked(Music.ROLL_THE_BONES_533)){
                entity.asPlayer().musicPlayer.unlock(Music.ROLL_THE_BONES_533)
            }
        }
        return super.enter(entity)
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        if (e is Player) {
            val player = e.asPlayer()
            if (target.id == 10735) {
                deposit(player)
                return true
            } else if (target.id in 10725..10728) {
                BoneType.forObject(target.id)!!.grab(player, target.asScenery())
                return true
            }
        }
        return super.interact(e, target, option)
    }

    override fun death(e: Entity, killer: Entity): Boolean {
        if (e is Player) {
            val p = e.asPlayer()
            val points = p.getSavedData().activityData.getPizazzPoints(MTAType.GRAVEYARD.ordinal)
            if (points >= 10) {
                p.getSavedData().activityData.decrementPizazz(MTAType.GRAVEYARD.ordinal, 10)
            } else {
                p.getSavedData().activityData.decrementPizazz(MTAType.GRAVEYARD.ordinal, points)
            }
        }
        return super.death(e, killer)
    }

    private fun deposit(player: Player) {
        if (!player.inventory.containsItem(BANANA) && !player.inventory.containsItem(PEACH)) {
            player.dialogueInterpreter.sendDialogue("You don't have any bananas or peaches to deposit.")
            return
        }
        var totalAmount = player.getAttribute("grave-amt", 0)
        var amount = 0
        val items = arrayOf(BANANA, PEACH)
        for (item in items) {
            if (player.inventory.containsItem(item)) {
                amount = player.inventory.getAmount(item)
                totalAmount += amount
                player.inventory.remove(Item(item.id, amount))
            }
        }
        if (totalAmount >= 16) {
            totalAmount = if (totalAmount > 16) {
                totalAmount - 16
            } else {
                0
            }
            val rune = RandomFunction.getRandomElement(RUNES)
            val name = rune.name.lowercase()
            player.inventory.add(rune, player)
            incrementPoints(player, MTAType.GRAVEYARD.ordinal, 1)
            player.getSkills().addExperience(Skills.MAGIC, 50.0, true)
            player.dialogueInterpreter.sendDialogue(
                "Congratulations - you've been awarded a" + (if (StringUtils.isPlusN(
                        name
                    )
                ) "n" else "") + " " + name + " and extra", "magic XP."
            )
        }
        setAttribute(player, "grave-amt", totalAmount)
    }

    override fun configure() {
        PULSE.stop()
        register(ZoneBorders(3333, 9610, 3390, 9663, 1, true))
    }


    enum class BoneType(val objectId: Int, val item: Item) {

        FIRST(10725, Item(6904)),
        SECOND(10726, Item(6905)),
        THIRD(10727, Item(6906)),
        FOURTH(10728, Item(6907));

        fun grab(player: Player, `object`: Scenery) {
            if (player.inventory.freeSlots() < 1) {
                player.sendMessage("You have no free space!")
                return
            }
            var life = `object`.attributes.getAttribute("life", 4)
            player.lock(1)
            player.inventory.add(item)
            player.animate(Animation.create(827))
            life -= 1
            if (life < 1) {
                life = 4
                val type = if (ordinal + 1 > values().size - 1) FIRST else values()[ordinal + 1]
                SceneryBuilder.replace(`object`, `object`.transform(type.objectId))
            }
            `object`.attributes.setAttribute("life", life)
        }

        companion object {

            fun forItem(item: Item): BoneType? {
                for (type in values()) {
                    if (type.item.id == item.id) {
                        return type
                    }
                }
                return null
            }


            fun forObject(objectId: Int): BoneType? {
                for (type in values()) {
                    if (type.objectId == objectId) {
                        return type
                    }
                }
                return null
            }
        }
    }

    companion object {

        val ZONE = GraveyardZone()
        private val PLAYERS: MutableList<Player> = ArrayList(20)
        private val RUNES = arrayOf(Item(561), Item(555), Item(557))
        private val BANANA = Item(1963)
        private val PEACH = Item(6883)
        private val GFX_POS = arrayOf(
            Location.create(3370, 9634, 1),
            Location.create(3359, 9634, 1),
            Location.create(3360, 9643, 1),
            Location.create(3363, 9643, 1),
            Location.create(3363, 9633, 1),
            Location.create(3367, 9637, 1),
            Location.create(3366, 9642, 1),
            Location.create(3380, 9647, 1),
            Location.create(3375, 9648, 1),
            Location.create(3370, 9649, 1),
            Location.create(3372, 9644, 1),
            Location.create(3379, 9646, 1),
            Location.create(3381, 9651, 1),
            Location.create(3379, 9656, 1),
            Location.create(3373, 9658, 1),
            Location.create(3353, 9648, 1),
            Location.create(3356, 9653, 1),
            Location.create(3346, 9657, 1),
            Location.create(3347, 9655, 1),
            Location.create(3354, 9634, 1),
            Location.create(3352, 9631, 1),
            Location.create(3355, 9627, 1),
            Location.create(3345, 9631, 1),
            Location.create(3345, 9627, 1),
            Location.create(3347, 9623, 1),
            Location.create(3351, 9621, 1),
            Location.create(3368, 9624, 1),
            Location.create(3368, 9631, 1),
            Location.create(3375, 9630, 1),
            Location.create(3375, 9635, 1),
            Location.create(3371, 9621, 1),
            Location.create(3377, 9621, 1),
            Location.create(3381, 9624, 1),
            Location.create(3382, 9629, 1),
            Location.create(3371, 9635, 1),
            Location.create(3365, 9634, 1),
            Location.create(3361, 9641, 1),
            Location.create(3363, 9644, 1)
        )
        private val PULSE: Pulse = object : Pulse(6) {
            override fun pulse(): Boolean {
                if (PLAYERS.isEmpty()) {
                    return true
                }
                var locs: MutableList<Location?>? = ArrayList(20)
                for (l in GFX_POS) {
                    if (RandomFunction.random(12) >= 8) {
                        continue
                    }
                    locs!!.add(l)
                }
                for (player in PLAYERS) {
                    if (!player.isActive) {
                        continue
                    }
                    for (l in locs!!) {
                        player.packetDispatch.sendPositionedGraphics(Graphic.create(520), l)
                    }
                    if (player.dialogueInterpreter.dialogue == null) {
                        player.impactHandler.manualHit(player, 2, ImpactHandler.HitsplatType.NORMAL)
                    }
                }
                locs = null
                delay = RandomFunction.random(6, 12)
                return false
            }
        }
    }
}
