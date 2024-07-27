package content.region.asgarnia.dialogue.burthope

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.END_DIALOGUE

class MartinThwaitDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MARTIN_THWAIT_2270)
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "You know it's sometimes funny how things work out, I", "lose some gold but find an item, or I lose an item and", "find some gold... no-one ever knows what's gone where", "ya know.").also { stage++ }

            1 -> if (getStatLevel(player!!, Skills.THIEVING) < 99) {
                options("Yeah I know what you mean, found anything recently?", "Okay... I'll be going now.").also { stage++ }
            } else {
                options("Yeah I know what you mean, found anything recently?", "Okay... I'll be going now.", "Can you tell me about your skillcape?").also { stage++ }
            }
            2 -> when (buttonID) {
                1 -> player(FacialExpression.HALF_GUILTY, "Yeah I know what you mean, found anything recently?").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "Okay... I'll be going now.").also { stage = END_DIALOGUE }
                3 -> if (getStatLevel(player!!, Skills.THIEVING) < 99) {
                    end()
                } else {
                    player("Can you tell me about your skillcape?").also { stage = 30 }
                }
            }
            10 -> {
                end()
                if (getStatLevel(player!!, Skills.AGILITY) >= 50 && getStatLevel(player!!, Skills.THIEVING) >= 50 || getStatLevel(player!!, Skills.THIEVING) == 99) {
                    openNpcShop(player!!, NPCs.MARTIN_THWAIT_2270)
                } else {
                    if (getStatLevel(player!!, Skills.THIEVING) < 50) {
                        npc(FacialExpression.HALF_GUILTY, "Sorry, mate. Train up your Thieving skill to at least", "50 and I might be able to help you out.")
                    }
                    if (getStatLevel(player!!, Skills.AGILITY) < 50) {
                        npc(FacialExpression.HALF_GUILTY, "Sorry, mate. Train up your Agility skill to at least", "50 and I might be able to help you out.")
                    }
                }
            }
            30 -> npc(FacialExpression.HALF_GUILTY, "Sure, this is a Skillcape of Thieving. It shows my stature as", "a master thief! It has all sorts of uses , if you", "have a level of 99 Thieving I'll sell you one.").also { stage++ }
            31 -> if (getStatLevel(player!!, Skills.THIEVING) < 99) {
                end()
            } else {
                options("Yes, please.", "No, thanks.").also { stage++ }
            }
            32 -> when (buttonID) {
                1 -> player("Yes, please.").also { stage++ }
                2 -> end()
            }
            33 -> {
                end()
                if (freeSlots(player!!) < 2) {
                    player("Sorry, I don't seem to have enough inventory space.")
                }
                if (!inInventory(player!!, COINS.id, COINS.amount)) {
                    player("Sorry, I don't seem to have enough coins", "with me at this time.")
                }
                if (removeItem(player!!, COINS, Container.INVENTORY)) {
                    addItemOrDrop(player!!, ITEMS[if (player!!.getSkills().masteredSkills > 1) 1 else 0].id, 1)
                    addItemOrDrop(player!!, ITEMS[2].id, 1)
                    npc("There you go! Enjoy.")
                } else {
                    player("Sorry, I don't seem to have enough coins", "with me at this time.")
                }
            }
        }
    }

    companion object {
        private val ITEMS = arrayOf(Item(Items.THIEVING_CAPE_9777), Item(Items.THIEVING_CAPET_9778), Item(Items.THIEVING_HOOD_9779))
        private val COINS = Item(Items.COINS_995, 99000)
    }

    override fun defineListeners() {
        on(NPCs.MARTIN_THWAIT_2270, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, MartinThwaitDialogue())
            return@on true
        }
    }
}
