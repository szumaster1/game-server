package content.region.asgarnia.handlers.guild.crafting

import core.api.Container
import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.hasLevelStat
import core.api.removeItem
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Master crafter dialogue.
 */
@Initializable
class MasterCrafterDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.FRIENDLY, "Hello, and welcome to the Crafting Guild. We're running a master crafting event currently, we're inviting crafters from all over the land to come here and use our top notch workshops!")
        stage = if (npc.id != NPCs.MASTER_CRAFTER_805) END_DIALOGUE else 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Skillcape of Crafting", "Nevermind.").also { stage++ }
            1 -> when (buttonId) {
                1 -> if (hasLevelStat(player, Skills.CRAFTING, 99)) {
                    player(FacialExpression.ASKING, "Hey, could I buy a Skillcape of Crafting?").also { stage = 10 }
                } else {
                    player(FacialExpression.ASKING, "Hey, what is that cape you're wearing? I don't", "recognize it.").also { stage++ }
                }
                2 -> player("Nevermind.").also { stage = END_DIALOGUE }
            }
            2 -> npcl(FacialExpression.FRIENDLY, "This? This is a Skillcape of Crafting. It is a symbol of my ability and standing here in the Crafting Guild.").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "If you should ever achieve level 99 Crafting come and talk to me and we'll see if we can sort you out with one.").also { stage = END_DIALOGUE }
            10 -> npcl(FacialExpression.HAPPY, "Certainly! Right after you pay me 99000 coins.").also { stage++ }
            11 -> options("Okay, here you go.", "No thanks.").also { stage++ }
            12 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Okay, here you go.").also { stage++ }
                2 -> player(FacialExpression.HALF_THINKING, "No, thanks.").also { stage = END_DIALOGUE }
            }
            13 -> {
                if (!removeItem(player, Item(coins, 99000), Container.INVENTORY)) {
                    npcl(FacialExpression.NEUTRAL, "You don't have enough coins for a cape.").also { stage = END_DIALOGUE }
                } else {
                    addItemOrDrop(player, if (player.getSkills().masteredSkills >= 1) craftingSkillcapeTrimmed else craftingSkillcape, 1)
                    addItemOrDrop(player, skillcapeHood, 1)
                    npcl(FacialExpression.HAPPY, "There you go! Enjoy.").also { stage = END_DIALOGUE }
                }
            }
            20 -> npcl(FacialExpression.NEUTRAL, "Where's your brown apron? You can't come in here unless you're wearing one.").also { stage++ }
            21 -> player(FacialExpression.HALF_GUILTY, "Err... I haven't got one.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.MASTER_CRAFTER_805,
            NPCs.MASTER_CRAFTER_2732,
            NPCs.MASTER_CRAFTER_2733
        )
    }

    companion object {
        private var craftingSkillcape = Items.CRAFTING_CAPE_9780
        private var craftingSkillcapeTrimmed = Items.CRAFTING_CAPET_9781
        private var skillcapeHood = Items.CRAFTING_HOOD_9782
        private var coins = Items.COINS_995
    }

}
