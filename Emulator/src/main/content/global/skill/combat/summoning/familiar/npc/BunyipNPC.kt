package content.global.skill.combat.summoning.familiar.npc

import content.data.consumable.Consumables.Companion.getConsumableById
import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.gathering.fishing.data.Fish.Companion.forItem
import content.global.skill.production.cooking.CookableItems.Companion.forId
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.GameWorld
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

/**
 * Bunyip npc.
 */
@Initializable
class BunyipNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6813) :
    Familiar(owner, id, 4400, 12029, 3, WeaponInterface.STYLE_ACCURATE) {
    private var lastHeal = 0

    /**
     * Instantiates a new Bunyip npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    /**
     * Instantiates a new Bunyip npc.
     */
    init {
        setLastHeal()
    }

    override fun construct(owner: Player, id: Int): Familiar {
        return BunyipNPC(owner, id)
    }

    override fun tick() {
        super.tick()
        if (lastHeal < GameWorld.ticks) {
            setLastHeal()
            owner.graphics(Graphic.create(1507), 1)
            // Since https://runescape.wiki/w/Bunyip?oldid=391088 (2008-04-02)
            // "The bunyip will automatically heal two hitpoints approximately every 15 seconds up to a player's maximum."
            // Since https://runescape.wiki/w/Bunyip?oldid=400848 (2008-04-06)
            // "The healing effect of the Bunyip can restore up to 352 hitpoints over its summoning duration of 44 minutes."
            // Numbers were multiplied by 10 with the constitution update on 2010-03-10 (https://runescape.wiki/w/Bunyip?oldid=2345672)
            owner.getSkills().heal(2)
        }
    }

    override fun isPoisonImmune(): Boolean {
        return true
    }

    /**
     * Sets last heal.
     */
    fun setLastHeal() {
        this.lastHeal = GameWorld.ticks + (15 / 0.6).toInt()
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val fish = forItem(special.item)
        val player = owner
        if (fish == null) {
            player.sendMessage("You can't use this special on an object like that.")
            return false
        }
        val consumable = getConsumableById(special.item.id + 2)!!.consumable
        if (consumable == null) {
            player.sendMessage("Error: Report to admin.")
            return false
        }
        if (player.getSkills().getLevel(Skills.COOKING) < forId(special.item.id)!!.level) {
            player.sendMessage("You need a Cooking level of at least " + forId(special.item.id)!!.level + " in order to do that.")
            return false
        }
        if (player.inventory.remove(special.item)) {
            animate(Animation.create(7747))
            graphics(Graphic.create(1481))
            val healthEffectValue = consumable.getHealthEffectValue(player)
            if (healthEffectValue > 0) {
                owner.getSkills().heal(consumable.getHealthEffectValue(player))
            } else {
                owner.impactHandler.manualHit(player, healthEffectValue, ImpactHandler.HitsplatType.NORMAL)
            }
        }
        return true
    }

    override fun visualizeSpecialMove() {
        owner.visualize(Animation.create(7660), Graphic.create(1316))
    }

    override fun handleFamiliarTick() {
    }

    override fun configureFamiliar() {
        UseWithHandler.addHandler(6813, UseWithHandler.NPC_TYPE, object : UseWithHandler(*FISH) {
            @Throws(Throwable::class)
            override fun newInstance(arg: Any?): Plugin<Any> {
                addHandler(6814, NPC_TYPE, this)
                return this
            }

            override fun handle(event: NodeUsageEvent): Boolean {
                val player = event.player
                val fish = forItem(event.usedItem)
                val consumable =
                    getConsumableById(fish!!.getItem().id + 2)!!.consumable ?: return true
                player.lock(1)
                val runes = Item(555, RandomFunction.random(1, consumable.getHealthEffectValue(player)))
                if (player.inventory.remove(event.usedItem)) {
                    player.animate(Animation.create(2779))
                    Projectile.create(player, event.usedWith.asNpc(), 1435).send()
                    player.inventory.add(runes)
                    player.sendMessage("The bunyip transmutes the fish into some water runes.")
                }
                return true
            }
        })
    }

    override fun getIds(): IntArray {
        return intArrayOf(6813, 6814)
    }

    companion object {
        private val FISH = intArrayOf(317, 327, 3150, 345, 321, 353, 335, 341, 349, 3379, 331, 5004, 359, 10138, 5001, 377, 363, 371, 2148, 7944, 3142, 383, 395, 389, 401, 405, 407)
    }
}
