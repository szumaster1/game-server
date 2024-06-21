package content.global.random.event.supriseexam

import content.global.random.RandomEventNPC
import core.api.consts.NPCs
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.utilities.RandomFunction

class MOMsupriseexamNPC(var type: String = "", override var loot: WeightBasedTable? = null) :
    RandomEventNPC(NPCs.MYSTERIOUS_OLD_MAN_410) {
    override fun init() {
        super.init()
        sayLine()
    }

    override fun tick() {
        super.tick()
        if (RandomFunction.random(1, 10) == 5) sayLine()
    }

    fun sayLine() {
        when (type) {
            "sexam" -> sendChat("Surprise exam, ${player.username.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}!")
        }
    }

    override fun talkTo(npc: NPC) {
        when (type) {
            "sexam" -> player.dialogueInterpreter.open(MOMsupriseexamDialogue("sexam"), this.asNpc())
        }
    }
}
