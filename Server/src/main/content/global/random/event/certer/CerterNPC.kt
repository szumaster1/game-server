package content.global.random.event.certer

import content.global.random.RandomEventNPC
import core.api.animate
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.api.playAudio
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.emote.Emotes
import core.tools.RandomFunction

/**
 * Certer NPC.
 */
class CerterNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.GILES_2538) {
    lateinit var pName: String
    lateinit var phrases: Array<String>

    override fun tick() {
        // Don't speak if we have the interface opened
        if (!timerPaused) {
            // Over allotted time phrase
            if (ticksLeft <= 2) {
                player.lock(2)
                sendChat(phrases[4])

                // Say a phrase every 20 ticks starting at 280 ticks
                // as to not interfere with the init chat phrase
            } else if (ticksLeft <= 280 && ticksLeft % 20 == 0) {
                sendChat(phrases[RandomFunction.random(1, 3)])
            }
        }
        super.tick()
    }

    override fun talkTo(npc: NPC) {
        player.setAttribute("random:pause", true)
        player.dialogueInterpreter.open(CerterDialogue(true), npc)
    }

    override fun init() {
        super.init()
        pName = player.username.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        phrases = arrayOf(
            "Greetings $pName, I need your help.",
            "ehem... Hello $pName, please talk to me!",
            "Hello, are you there $pName?",
            "It's really rude to ignore someone, $pName!",
            "No-one ignores me!"
        )
        player.setAttribute("random:pause", false)
        player.setAttribute("certer:reward", false)
        sendChat(phrases[0])
        animate(this, Emotes.BOW.animation, true)
        playAudio(player, Sounds.HAND_WAVE_2302)
    }
}
