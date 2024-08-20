package content.global.random.event.eviltwin

import content.global.random.RandomEventNPC
import core.api.*
import core.api.utils.WeightBasedTable
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.scenery.Scenery
import core.game.system.timer.impl.AntiMacro
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Represents the Molly NPC.
 */
class MollyNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(EvilTwinUtils.mollyNPC!!.id) {

    override fun init() {
        super.init()
        sendChat(EvilTwinUtils.mollyNPC!!, "I need your help, ${player.username}.")
    }

    override fun tick() {
        val color: EvilTwinColors = RandomFunction.getRandomElement(EvilTwinColors.values())
        val model = RandomFunction.random(5)
        val hash = color.ordinal or (model shl 16)
        val npcId = EvilTwinUtils.getMollyId(hash)
        EvilTwinUtils.mollyNPC = create(npcId, Location.getRandomLocation(player.location, 1, true))
        EvilTwinUtils.mollyNPC!!.init()
        EvilTwinUtils.mollyNPC!!.faceTemporary(player, 3)
        EvilTwinUtils.region.add(player)
        EvilTwinUtils.region.setMusicId(612)
        setAttribute(player, EvilTwinUtils.randomEvent, hash)
        setAttribute(player, EvilTwinUtils.originalLocation, player.location)
        EvilTwinUtils.currentCrane = Scenery(14976, EvilTwinUtils.region.baseLocation.transform(14, 12, 0), 10, 0)
        queueScript(player, 4, QueueStrength.SOFT) {
            EvilTwinUtils.teleport(player, EvilTwinUtils.mollyNPC!!, hash)
            EvilTwinUtils.mollyNPC!!.locks.lockMovement(3000)
            openDialogue(player, MollyDialogue(3))
            AntiMacro.terminateEventNpc(player)
            return@queueScript stopExecuting(player)
        }
        super.tick()
    }

    override fun talkTo(npc: NPC) {
        /*
         * Empty
         */
    }

}
