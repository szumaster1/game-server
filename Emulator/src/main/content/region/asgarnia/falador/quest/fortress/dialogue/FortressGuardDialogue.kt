package content.region.asgarnia.falador.quest.fortress.dialogue

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.sendChat
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.RegionManager.getLocalNpcs
import core.game.world.map.RegionManager.getObject
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Fortress guard dialogue.
 */
@Initializable
class FortressGuardDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (args.size == 2) {
            npc(FacialExpression.ANGRY, "Hey! You can't come in here! This is a high security", "military installation!").also { stage = 40 }
            return true
        }
        if (args.size == 3) {
            npc(FacialExpression.NEUTRAL, "I wouldn't go in there if I were you. Those Black", "Knights are in an important meeting. They said they'd", "kill anyone who went in there!").also { stage = 50 }
            return true
        }
        if (!inUniform(player)) {
            npc(FacialExpression.ANGRY, "Get lost. This is private property.").also { stage = 0 }
        } else {
            npc(FacialExpression.FURIOUS, "Hey! Get back on duty!").also { stage = 30 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "Yes, but I work here!").also { stage++ }
            1 -> npc(FacialExpression.NEUTRAL, "Well, this is the guards' entrance. I might be new here", "but I can tell you're not a guard.").also { stage++ }
            2 -> player(FacialExpression.ASKING, "How can you tell?").also { stage++ }
            3 -> npc(FacialExpression.ANGRY, "You're not even wearing proper guards uniform!").also { stage++ }
            4 -> options("Oh pleeeaase let me in!", "So what is this uniform?").also { stage++ }
            5 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Oh pleeeaaase let me in!").also { stage = 10 }
                2 -> player(FacialExpression.ASKING, "So what is this uniform?").also { stage = 20 }
            }
            10 -> npc(FacialExpression.ANNOYED, "Go away. You're getting annoying.").also { stage = END_DIALOGUE }
            20 -> npc(FacialExpression.NEUTRAL, "Well you can see me wearing it. It's an iron chainbody", "and a medium bronze helm.").also { stage++ }
            21 -> player(FacialExpression.HALF_ASKING, "Hmmm... I wonder if I can make that or get some in", "the local towns...").also { stage++ }
            22 -> npc(FacialExpression.HALF_ASKING, "What was that you muttered?").also { stage++ }
            23 -> player(FacialExpression.SUSPICIOUS, "Oh, nothing important!").also { stage = END_DIALOGUE }
            30 -> player(FacialExpression.SUSPICIOUS, "Uh...").also { stage = END_DIALOGUE }
            40 -> options("Yes, but I work here!", "Oh, sorry.", "So who does it belong to?").also { stage++ }
            41 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Yes, but I work here!").also { stage = 1 }
                2 -> player(FacialExpression.HALF_GUILTY, "Oh, sorry.").also { stage++ }
                3 -> player(FacialExpression.ASKING, "So who does it belong to?").also { stage = 44 }
            }
            42 -> npc(FacialExpression.NEUTRAL, "Don't let it happen again.").also { stage = END_DIALOGUE }
            44 -> npc(FacialExpression.NEUTRAL, "This fortress belongs to the order of Black Knights", "known as the Kinshra.").also { stage++ }
            45 -> player(FacialExpression.FRIENDLY, "Oh. Okay, thanks.").also { stage = END_DIALOGUE }
            50 -> options( "Okay, I won't.", "I don't care. I'm going in anyway.").also { stage++ }
            51 -> when (buttonId) {
                1 -> player(FacialExpression.NEUTRAL, "Okay, I won't.").also { stage++ }
                2 -> player(FacialExpression.NEUTRAL, "I don't care. I'm going in anyway.").also { stage = 54 }
            }
            52 -> npc(FacialExpression.NEUTRAL, "Wise move.").also { stage = END_DIALOGUE }
            54 -> {
                end()
                DoorActionHandler.handleAutowalkDoor(player, getObject(0, 3020, 3515)!!)
                val npcs = getLocalNpcs(player)
                for (npc in npcs) {
                    if (npc.id == NPCs.BLACK_KNIGHT_179) {
                        npc.attack(player)
                        sendChat(player, "Die, intruder!")
                        return true
                    }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FORTRESS_GUARD_609, NPCs.FORTRESS_GUARD_4603, NPCs.FORTRESS_GUARD_4604, NPCs.FORTRESS_GUARD_4605, NPCs.FORTRESS_GUARD_4606)
    }

    companion object {
        private val UNIFORM = arrayOf(Item(Items.BRONZE_MED_HELM_1139), Item(Items.IRON_CHAINBODY_1101))

        fun inUniform(player: Player): Boolean {
            for (i in UNIFORM) {
                if (!player.equipment.containsItem(i)) {
                    return false
                }
            }
            return true
        }
    }
}
