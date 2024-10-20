package content.global.skill.summoning.familiar.npc

import content.global.skill.firemaking.FireMakingPulse.getAsh
import content.global.skill.firemaking.logs.Log.Companion.forId
import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import core.api.*
import core.game.container.impl.EquipmentContainer
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.RegionManager.getObject
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin
import core.plugin.PluginManager.definePlugin
import core.tools.Log
import core.tools.RandomFunction
import org.rs.consts.NPCs

/**
 * Forge regent familiar.
 */
@Initializable
class ForgeRegentNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 7335) :

    Familiar(owner, id, 4500, 12782, 6, WeaponInterface.STYLE_RANGE_ACCURATE) {

    init {
        boosts.add(SkillBonus(Skills.FIREMAKING, 4.0))
    }

    override fun construct(owner: Player, id: Int): Familiar {
        return ForgeRegentNPC(owner, id)
    }

    public override fun configureFamiliar() {
        definePlugin(ForgeRegentFiremake())
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        if (special.target !is Player) {
            owner.sendMessage("You can't use this special on an npc.")
            return false
        }
        val target = special.target.asPlayer()
        if (!canCombatSpecial(target)) {
            return false
        }
        if (target.inventory.freeSlots() < 1) {
            owner.sendMessage("The target doesn't have enough inventory space.")
            return false
        }
        val weapon = target.equipment[EquipmentContainer.SLOT_WEAPON]
        val shield = target.equipment[EquipmentContainer.SLOT_SHIELD]
        if (weapon == null && shield == null) {
            owner.sendMessage("The target doesn't have a weapon or shield.")
            return false
        }
        var remove: Item? = null
        while (remove == null) {
            remove = if (RandomFunction.random(2) == 1) {
                weapon
            } else {
                shield
            }
        }
        graphics(Graphic.create(1394))
        target.graphics(Graphic.create(1393))
        if (target.equipment.remove(remove)) {
            target.inventory.add(remove)
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FORGE_REGENT_7335, NPCs.FORGE_REGENT_7336)
    }

    inner class ForgeRegentFiremake : UseWithHandler(*logsIDs) {

        override fun newInstance(arg: Any?): Plugin<Any> {
            for (id in ids) {
                addHandler(id, NPC_TYPE, this)
            }
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            val player = event.player
            val log = forId(event.usedItem.id)
            val familiar = event.usedWith as Familiar
            val ticks = FIREMAKE_ANIMATION.definition.getDurationTicks()
            if (!player.familiarManager.isOwner(familiar)) {
                return true
            }
            if (getObject(familiar.location) != null || familiar.zoneMonitor.isInZone("bank")) {
                sendMessage(player, "You can't light a fire here.")
                return false
            }
            lock(familiar, ticks)
            animate(familiar, FIREMAKE_ANIMATION)
            if (removeItem(player, event.usedItem)) {
                val ground = GroundItemManager.create(event.usedItem, familiar.location, player)
                Pulser.submit(object : Pulse(ticks, player, familiar) {
                    override fun pulse(): Boolean {
                        if (!ground.isActive) {
                            return true
                        }
                        val `object` = Scenery(log!!.fireId, familiar.location)
                        familiar.moveStep()
                        GroundItemManager.destroy(ground)
                        rewardXP(player, Skills.FIREMAKING, log.xp + 10)
                        familiar.faceLocation(`object`.getFaceLocation(familiar.location))
                        SceneryBuilder.add(`object`, log.life, getAsh(player, log, `object`))
                        if (player.viewport.region.id == 10806) {
                            finishDiaryTask(player, DiaryType.SEERS_VILLAGE, 1, 9)
                        }
                        return true
                    }
                })
            }
            return true
        }
    }

    companion object {
        private val FIREMAKE_ANIMATION: Animation = Animation.create(8085)
        val logsIDs = content.global.skill.firemaking.logs.Log.values().map { it.logId }.toIntArray()
    }
}
