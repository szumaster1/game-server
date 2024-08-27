package content.location.varrock.museum

import cfg.consts.Animations
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.RegionManager.getObject
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Museum guard dialogue.
 */
@Initializable
class MuseumGuardDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Guard the Varrock Museum and the path leading to the Varrock Dig Site east of Varrock.
     * Location: 3263,3441
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello there. Come to see the new museum?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "Yes, how do I get in?").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "Well, the main entrance is 'round the front. Just head", "west then north slightly, you can't miss it!").also { stage++ }
            2 -> player(FacialExpression.HALF_GUILTY, "What about these doors?").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "They're primarily for the workmen bringing finds from the", "Dig Site, but you can go through if you want.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MUSEUM_GUARD_5943)
    }

}

/**
 * Represents the Museum guard entrance dialogue.
 */
class MuseumGuardEntranceDialogue : DialogueFile() {

    /*
     * Info: Museum guard at entrance to the archaeological cleaning centre.
     * Location: 3260,3447
     */

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MUSEUM_GUARD_5941)
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "Welcome! Would you like to go into the Dig Site", "archaeology cleaning area?").also { stage++ }
            1 -> options("Yes, I'll go in!", "No thanks, I'll take a look around out there.").also { stage++ }
            2 -> when (buttonID) {
                1 -> playerl(FacialExpression.HALF_GUILTY, "Yes, I'll go in!").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "No thanks, I'll take a look around out there.").also { stage = END_DIALOGUE }
            }
            3 -> {
                end()
                val door = getObject(Location(3261, 3446, 0))
                if (DoorActionHandler.getEndLocation(player!!, door!!).y > player!!.location.y) Animation(Animations.HANDS_BEHIND_BACK_SIDEWAYS_DO_A_MOTION_6391) else Animation(Animations.HANDS_BEHIND_BACK_SIDEWAYS_DO_A_MOTION_6392)
                DoorActionHandler.handleAutowalkDoor(player!!, door)
            }
        }
    }
}