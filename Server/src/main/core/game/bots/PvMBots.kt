package core.game.bots

import content.data.consumables.Consumables.Companion.getConsumableById
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.prayer.PrayerType
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalNpcs
import core.tools.RandomFunction

open class PvMBots : AIPlayer {

    private var tick = 0

    constructor(l: Location?) : super(l!!)

    constructor(copyFromFile: String?, l: Location?) : super(copyFromFile!!, l!!)

    fun FindTargets(entity: Entity?, radius: Int): List<Entity>? {
        val targets: MutableList<Entity> = ArrayList(20)
        val localNPCs: Array<Any> = getLocalNpcs(entity!!, radius).toTypedArray()
        var length = localNPCs.size
        if (length > 5) {
            length = 5
        }
        for (i in 0 until length) {
            val npc = localNPCs[i] as NPC
            run {
                if (checkValidTargets(npc)) targets.add(npc)
            }
        }
        if (targets.size == 0) return null
        return targets
    }

    fun checkValidTargets(target: NPC): Boolean {
        if (!target.isActive) {
            return false
        }
        if (!target.properties.isMultiZone && target.inCombat()) {
            return false
        }
        if (!target.definition.hasAction("attack")) {
            return false
        }
        return true
    }

    fun AttackNpcsInRadius(radius: Int): Boolean {
        return AttackNpcsInRadius(this, radius)
    }

    fun AttackNpcsInRadius(bot: Player, radius: Int): Boolean {
        if (bot.inCombat()) return true
        var creatures = FindTargets(bot, radius) ?: return false
        bot.attack(creatures[RandomFunction.getRandom((creatures.size - 1))])
        if (!creatures.isEmpty()) {
            return true
        } else {
            creatures = FindTargets(bot, radius)!!
            if (!creatures.isEmpty()) {
                bot.attack(creatures[RandomFunction.getRandom((creatures.size - 1))])
                return true
            }
            return false
        }
    }

    override fun tick() {
        super.tick()

        tick++

        //Despawn
        if (getSkills().lifepoints <= 5) {
            //TODO: Just respawn a new bot (not sure how you'd do that :L)
            // Maybe make all PvMBots know what to do if they aren't in right area? I.e. pest control bots teleport to PC
            //this.teleport(new Location(500, 500));
            //Despawning not being delayed causes 3 errors in the console
            getSkills().lifepoints = 20
        }

        //Npc Combat
        /*if (this.tick % 10 == 0) {
			if (!this.inCombat())
				AttackNpcsInRadius(this, 5);
		}*/
        if (this.tick == 100) this.tick = 0

        //this.eat();
        //this.getPrayer().toggle()
    }

    fun CheckPrayer(type: Array<PrayerType?>) {
        for (i in type.indices) prayer.toggle(type[i])
    }

    fun eat(foodId: Int) {
        val foodItem = Item(foodId)

        if ((getSkills().getStaticLevel(Skills.HITPOINTS) >= getSkills().lifepoints * 3) && inventory.containsItem(
                foodItem
            )
        ) {
            this.lock(3)
            //this.animate(new Animation(829));
            val food = inventory.getItem(foodItem)

            val consumable = getConsumableById(food.id)!!.consumable ?: return

            consumable.consume(food, this)
            properties.combatPulse.delayNextAttack(3)
        }
        if (this.checkVictimIsPlayer() == false) if (!(inventory.contains(foodId, 1))) inventory.add(
            Item(
                foodId,
                5
            )
        ) //Add Food to Inventory
    }
}
