package content.region.kandarin.quest.zogreflesheaters.npc

import content.region.kandarin.quest.zogreflesheaters.ZogreQuestUtils
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Vars
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.config.ItemConfigParser
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.utilities.RandomFunction

@Initializable
class SlashBashNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    var despawnTime = 0
    private val player: Player? = null

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return SlashBashNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SLASH_BASH_2060)
    }

    override fun handleTickActions() {
        super.handleTickActions()
        if (player != null) {
            if (player.location.getDistance(getLocation()) > 10 || !player.isActive || despawnTime++ > 1030) {
                removeAttribute(player, ZogreQuestUtils.SLASH_BASH_ACTIVE)
                poofClear(this)
            }
        }
    }

    companion object {
        val brutalArrows = intArrayOf(4773,4778,4783,4788,4793,4798,4803)
        val questItem = Items.OGRE_ARTEFACT_4818

        fun spawnZogreBoss(player: Player) {
            val boss = SlashBashNPC(NPCs.SLASH_BASH_2060)
            val spawnGraphic = Graphic(86)
            boss.location = Location.getRandomLocation(Location(2480, 9445, 0), 2, true)
            boss.isWalks = true
            boss.isAggressive = true
            boss.isActive = false

            if (boss.asNpc() != null && boss.isActive) {
                boss.properties.teleportLocation = boss.properties.spawnLocation
            }

            boss.isActive = true
            GameWorld.Pulser.submit(object : Pulse(2, boss) {
                override fun pulse(): Boolean {
                    setAttribute(player, ZogreQuestUtils.SLASH_BASH_ACTIVE, true)
                    sendMessage(player, "Something stirs behind you!")
                    visualize(boss, -1, spawnGraphic)
                    boss.init()
                    registerHintIcon(player, boss)
                    boss.attack(player)
                    return true
                }
            })
        }
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        val player = state.attacker
        if (player is Player) {
            val checkWeapon: Item = getItemFromEquipment(player, EquipmentSlot.WEAPON)!!
            val checkAmmunition = getItemFromEquipment(player, EquipmentSlot.AMMO)!!
            val crumbleUndead = 22
            if (checkWeapon.definition.getConfiguration(ItemConfigParser.TWO_HANDED, false) == true && state.style == CombatStyle.RANGE) {
                if (state.estimatedHit > -1) {
                    state.estimatedHit = 0
                    return
                }
                if(checkWeapon.id == Items.COMP_OGRE_BOW_4827 && checkAmmunition.id in brutalArrows) {
                    state.neutralizeHits()
                    state.estimatedHit = state.maximumHit
                }
            }
            if (player.properties.spell.spellId == crumbleUndead) {
                state.neutralizeHits()
                state.estimatedHit = state.maximumHit
            }
        }
    }

    override fun finalizeDeath(killer: Entity?) {
        if (killer is Player) {
            val player = killer.asPlayer()
            produceGroundItem(player, questItem, 1, this.location)
            produceGroundItem(player, Items.OURG_BONES_4834, RandomFunction.random(1,3), this.location)
            produceGroundItem(player, Items.ZOGRE_BONES_4812, RandomFunction.random(1,2), this.location)
            setVarbit(player!!, Vars.VARBIT_QUEST_ZORGE_FLESH_EATERS_PROGRESS, 12, true)
            removeAttribute(player, ZogreQuestUtils.SLASH_BASH_ACTIVE)
        }
        clearHintIcon(killer!!.asPlayer())
        clear()
        super.finalizeDeath(killer)
    }
}
