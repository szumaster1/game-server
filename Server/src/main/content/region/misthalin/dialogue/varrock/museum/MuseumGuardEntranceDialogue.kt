package content.region.misthalin.dialogue.varrock.museum

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.RegionManager.getObject
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class MuseumGuardEntranceDialogue(player: Player? = null) : Dialogue(player) {

    /*
     *  Info: Museum guard at entrance to the archaeological cleaning centre.
     *  Location: 3260,3447
     */

    override fun open(vararg args: Any): Boolean {
        npc(FacialExpression.HALF_GUILTY, "Welcome! Would you like to go into the Dig Site", "archaeology cleaning area?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, I'll go in!", "No thanks, I'll take a look around out there.").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.HALF_GUILTY, "Yes, I'll go in!").also { stage++ }
                2 -> playerl(FacialExpression.HALF_GUILTY, "No thanks, I'll take a look around out there.").also { stage = END_DIALOGUE }
            }
            2 -> {
                end()
                DoorActionHandler.handleAutowalkDoor(player, getObject(Location(3261, 3446, 0)))
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MUSEUM_GUARD_5941)
    }

}
