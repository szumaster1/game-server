package content.region.morytania.quest.naturespirit.dialogue

import cfg.consts.Items
import cfg.consts.NPCs
import content.region.morytania.quest.naturespirit.NSUtils
import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Nature spirit dialogue.
 */
@Initializable
class NatureSpiritDialogue(player: Player? = null) : Dialogue(player) {

    val questStage = player?.questRepository?.getStage("Nature Spirit") ?: 0

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when (questStage) {
            60 -> npcl(FacialExpression.NEUTRAL, "Hmm, good, the transformation is complete. Now, my friend, in return for your assistance, I will help you to kill the Ghasts. First bring to me a silver sickle so that I can bless it for you.").also { return true }
            65 -> npcl(FacialExpression.NEUTRAL, "Have you brought me a silver sickle?").also {
                stage = 100; return true
            }
            70 -> npcl(FacialExpression.NEUTRAL, "Now you can go forth and make the swamp bloom. Collect nature's bounty to fill a druids pouch. So armed will the Ghasts be bound to you until, you flee or they are defeated.").also { stage = 200 }
            75 -> npcl(FacialExpression.NEUTRAL, "Hello again, my friend. Have you defeated three ghasts as I asked you?").also { stage = 300 }
            else -> npcl(FacialExpression.FRIENDLY, "Welcome to my grotto, friend. Enjoy your visit.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.NEUTRAL, "A silver sickle? What's that?").also { stage++ }
            1 -> npcl(FacialExpression.NEUTRAL, "The sickle is the symbol and weapon of the Druid, you need to construct one of silver so that I can bless it, with its powers you will be able to defeat the Ghasts of Mort Myre.").also { stage++; setQuest(65) }
            2 -> options("Where would I get a silver sickle?", "What will you do to the silver sickle?", "How can a blessed sickle help me to defeat the Ghasts?", "Ok, thanks.").also { stage++ }
            3 -> when (buttonID) {
                1 -> playerl(FacialExpression.NEUTRAL, "Where would I get a silver sickle?").also { stage = 10 }
                2 -> playerl(FacialExpression.NEUTRAL, "What will you do to the silver sickle?").also { stage = 20 }
                3 -> playerl(FacialExpression.NEUTRAL, "How can a blessed sickle help me to defeat the Ghasts?").also { stage = 30 }
                4 -> playerl(FacialExpression.NEUTRAL, "Ok, thanks.").also { stage = END_DIALOGUE }
            }
            10 -> npcl(FacialExpression.NEUTRAL, "You could make one yourself if you're artisan enough. I've heard of a distant sandy place where you can buy the mould that you require, it's similar in many respects to the creating of a holy symbol.").also { stage = 2 }
            20 -> npcl(FacialExpression.NEUTRAL, "Why, I will give it my blessings so that the very swamp in which you stand will blossom and bloom!").also { stage = 2 } //pompous git
            30 -> npcl(FacialExpression.NEUTRAL, "My blessings will entice nature to bloom in Mort Myre! And then with nature's harvest you can fill a druids pouch and release the Ghasts from their torment.").also { stage = 2 } //this dude kinda weird
            100 -> {
                if (inInventory(player, Items.SILVER_SICKLE_2961)) {
                    playerl(FacialExpression.FRIENDLY, "Yes, here it is. What are you going to do with it?").also { stage = 110 }
                } else {
                    playerl(FacialExpression.NEUTRAL, "No sorry, not yet!").also { stage++ }
                }
            }
            101 -> npcl(FacialExpression.NEUTRAL, "Well, come to me when you have it.").also { stage = 2 }
            110 -> npcl(FacialExpression.NEUTRAL, "My friend, I will bless it for you and you will then be able to accomplish great things. Now then, I must cast the enchantment. You can bless a new sickle by dipping it in the holy water of the grotto.").also { stage++ }
            111 -> sendDialogue("- The Nature Spirit casts a spell on the player. -").also { stage++ }
            112 -> {
                end()
                lock(player, 10)
                submitWorldPulse(SickleBlessPulse(player, npc))
            }
            200 -> npcl(FacialExpression.NEUTRAL, "Go forth into Mort Myre and slay three Ghasts. You'll be releasing their souls from Mort Myre.").also { stage++ }
            201 -> sendItemDialogue(player, Items.DRUID_POUCH_2957, "The nature spirit gives you an empty pouch.").also { stage++; setQuest(75) }
            202 -> npcl(FacialExpression.NEUTRAL, "You'll need this in order to collect together nature's bounty. It will bind the Ghast to you until you flee or it is defeated.").also { stage = END_DIALOGUE }
            300 -> {
                if (NSUtils.getGhastKC(player) >= 3) {
                    playerl(FacialExpression.NEUTRAL, "Yes, I've killed all three and their spirits have been released!").also { stage = 350 }
                } else {
                    playerl(FacialExpression.NEUTRAL, "Not yet.").also { stage++ }
                }
            }
            301 -> npcl(FacialExpression.NEUTRAL, "Well, when you do, please come to me and I'll reward you!").also { stage++ }
            302 -> options("How do I get to attack the Ghasts?", "What's this pouch for?", "What can I do with this sickle?", "I've lost my sickle.", "Ok, thanks.").also { stage++ }
            303 -> when (buttonID) {
                1 -> playerl(FacialExpression.NEUTRAL, "How do I get to attack the Ghasts?").also { stage = 310 }
                2 -> playerl(FacialExpression.NEUTRAL, "What's this pouch for?").also { stage = 320 }
                3 -> playerl(FacialExpression.NEUTRAL, "What can I do with this sickle?").also { stage = 330 }
                4 -> playerl(FacialExpression.NEUTRAL, "I've lost my sickle.").also { stage = 340 }
                5 -> playerl(FacialExpression.NEUTRAL, "Ok, thanks.").also { stage = END_DIALOGUE }
            }
            310 -> npcl(FacialExpression.NEUTRAL, "Go forth and with the sickle make the swamp bloom. Collect natures bounty to fill a druids pouch. So armed will the Ghasts be bound to you until, you flee or they are defeated.").also { stage = 302 }
            320 -> npcl(FacialExpression.NEUTRAL, "It is for collecting natures bounty, once it contains the blossomed items of the swamp, it will make the Ghasts appear and you can then attack them.").also { stage = 302 }
            330 -> npcl(FacialExpression.NEUTRAL, "You may use it wisely within the area of Mort Myre to affect natures balance and bring forth a bounty of natures harvest. Once collected into the druid pouch will the Ghast be apparent.").also { stage = 302 }
            340 -> npcl(FacialExpression.NEUTRAL, "If you should lose the blessed sickle, simply bring another to my altar of nature and refresh it in the grotto waters.").also { stage = 302 }
            350 -> npcl(FacialExpression.NEUTRAL, "Many thanks my friend, you have completed your quest!").also { stage++ }
            351 -> {
                end()
                finishQuest(player, "Nature Spirit")
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.NATURE_SPIRIT_1051)
    }

    /**
     * Represents a pulse that blesses a player with a spirit.
     *
     * @param player the player to be blessed.
     * @param spirit the spirit NPC providing the blessing.
     * @constructor Creates a SickleBlessPulse instance with the given player and spirit.
     */
    internal class SickleBlessPulse(val player: Player, val spirit: NPC) : Pulse() {
        var ticks = 0
        val locs: MutableList<Location> = player.location.surroundingTiles

        override fun pulse(): Boolean {
            when (ticks++) {
                0 -> animate(spirit, 812)
                1 -> repeat(4) {
                    val loc = locs.random()
                    locs.remove(loc)

                    spawnProjectile(loc, player.location, 268, 0, 400, 0, 125, 180)
                    animate(player, 9021)
                }

                4 -> {
                    if (removeItem(player, Items.SILVER_SICKLE_2961, Container.INVENTORY)) {
                        addItem(player, Items.SILVER_SICKLEB_2963)
                        unlock(player)
                        player.questRepository.getQuest("Nature Spirit").setStage(player, 70)
                        openDialogue(player, NPCs.NATURE_SPIRIT_1051, findLocalNPC(player, NPCs.NATURE_SPIRIT_1051) as NPC)
                    }
                }

                6 -> {
                    player.dialogueInterpreter.sendItemMessage(Items.SILVER_SICKLEB_2963,"Your sickle has been blessed!","You can bless a new sickle by dipping it into the","grotto waters.")
                    sendMessage(player, "Your sickle has been blessed!")
                    sendMessage(player, "You can bless a new sickle by dipping it into the grotto waters.")
                    return true
                }
            }
            return false
        }
    }

    /**
     * Set quest.
     *
     * @param stage the stage.
     */
    fun setQuest(stage: Int) {
        player!!.questRepository.getQuest("Nature Spirit").setStage(player!!, stage)
    }
}
