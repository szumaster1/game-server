package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.production.crafting.data.Jewellery.open
import content.global.skill.support.firemaking.FireMakingPulse.getAsh
import content.global.skill.support.firemaking.data.Log.Companion.forId
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
import core.plugin.PluginManager.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Pyre lord familiar.
 */
@Initializable
class PyreLordNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 7377) : Familiar(owner, id, 3200, 12816, 6, WeaponInterface.STYLE_AGGRESSIVE) {

    init {
        boosts.add(SkillBonus(Skills.FIREMAKING, 3.0))
    }

    override fun construct(owner: Player, id: Int): Familiar {
        return PyreLordNPC(owner, id)
    }

    public override fun configureFamiliar() {
        definePlugin(PyreLordFiremake())
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val item = special.node as Item
        if (item.id != 2357) {
            owner.packetDispatch.sendMessage("You can only use this special on gold bars.")
            return false
        }
        owner.lock(1)
        animate(Animation.create(8081))
        owner.graphics(Graphic.create(1463))
        open(owner)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(7377, 7378)
    }

    /**
     * Pyre lord firemake
     *
     * @constructor Pyre lord firemake
     */
    inner class PyreLordFiremake : UseWithHandler(1511, 2862, 1521, 1519, 6333, 10810, 1517, 6332, 12581, 1515, 1513, 13567, 10329, 10328, 7406, 7405, 7404) {

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
            val ticks = FIREMAKE_ANIMATION.definition!!.getDurationTicks()
            if (!player.familiarManager.isOwner(familiar)) {
                return true
            }
            if (getObject(familiar.location) != null || familiar.zoneMonitor.isInZone("bank")) {
                player.packetDispatch.sendMessage("You can't light a fire here.")
                return false
            }
            familiar.lock(ticks)
            familiar.animate(FIREMAKE_ANIMATION)
            if (player.inventory.remove(event.usedItem)) {
                val ground = GroundItemManager.create(event.usedItem, familiar.location, player)
                Pulser.submit(object : Pulse(ticks, player, familiar) {
                    override fun pulse(): Boolean {
                        if (!ground.isActive) {
                            return true
                        }
                        val `object` = Scenery(log!!.fireId, familiar.location)
                        familiar.moveStep()
                        GroundItemManager.destroy(ground)
                        player.getSkills().addExperience(Skills.FIREMAKING, log.xp + 10)
                        familiar.faceLocation(`object`.getFaceLocation(familiar.location))
                        SceneryBuilder.add(`object`, log.life, getAsh(player, log, `object`))
                        if (player.viewport.region.id == 10806) {
                            player.achievementDiaryManager.finishTask(player, DiaryType.SEERS_VILLAGE, 1, 9)
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
    }
}
