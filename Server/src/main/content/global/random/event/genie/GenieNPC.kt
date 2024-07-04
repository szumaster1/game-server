package content.global.random.event.genie

import content.global.random.RandomEventNPC
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.api.playAudio
import core.api.setAttribute
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.tools.RandomFunction

class GenieNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.GENIE_409) {

    val phrases =
        arrayOf("Greetings, @name!", "Ehem... Master @name?", "Are you there, Master @name?", "No one ignores me!")
    var assigned_item = 0
    val items = arrayOf(Items.LAMP_2528)

    override fun tick() {
        if (RandomFunction.random(1, 15) == 5) {
            sendChat(phrases.random().replace("@name", player.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }))
        }
        super.tick()
    }

    override fun init() {
        super.init()
        assignItem()
        playAudio(player, Sounds.GENIE_APPEAR_2301)
        sendChat(phrases.random().replace("@name", player.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }))
    }

    fun assignItem() {
        assigned_item = items.random()
        setAttribute(player, "genie:item", assigned_item)
    }

    override fun talkTo(npc: NPC) {
        player.dialogueInterpreter.open(GenieDialogue(), npc)
    }
}
