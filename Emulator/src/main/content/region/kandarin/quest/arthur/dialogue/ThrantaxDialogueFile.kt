package content.region.kandarin.quest.arthur.dialogue

import content.region.kandarin.quest.arthur.handlers.MerlinUtils
import content.region.kandarin.quest.arthur.handlers.ThrantaxNPC
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.sendDialogue
import core.api.sendDialogueOptions
import core.api.setAttribute
import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic
import core.tools.END_DIALOGUE

/**
 * Represents the Thrantax dialogue file.
 *
 * Related to [Merlin Crystal][content.region.kandarin.quest.arthur.MerlinCrystal] quest.
 * @author lostmyphat
 */
class ThrantaxDialogueFile : DialogueFile() {

    val options = arrayOf("Snarthanto Candon Termtrick", "Snarthtrick Candanto Termon", "Snarthon Candtrick Termanto")
    var correct = options[2]
    var shuffled = emptyArray<String>()
    var incantation = ""

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.THRANTAX_THE_MIGHTY_238)

        when (stage) {
            0 -> {
                initThrantax(player!!, false)
                sendDialogue(player!!, "Suddenly a mighty spirit appears!")
                stage++
            }

            1 -> playerl(FacialExpression.THINKING, "Now what were those magic words again?").also { stage++ }
            2 -> {
                shuffled = options.toMutableList().apply { shuffle() }.toTypedArray()

                sendDialogueOptions(
                    player!!, "Select an Option",
                    shuffled[0],
                    shuffled[1],
                    shuffled[2]
                )
                stage++
            }

            3 -> {
                if (buttonID == 1) {
                    incantation = shuffled[0]
                } else if (buttonID == 2) {
                    incantation = shuffled[1]
                } else if (buttonID == 3) {
                    incantation = shuffled[2]
                }

                playerl(FacialExpression.THINKING, incantation.split(" ")[0] + "...")
                stage++
            }

            4 -> {
                playerl(FacialExpression.THINKING, incantation.split(" ")[1] + "...")
                stage++
            }

            5 -> {
                playerl(FacialExpression.THINKING, incantation.split(" ")[2] + "!")
                stage++
            }

            6 -> {
                initThrantax(player!!, true)
                npcl(FacialExpression.OLD_HAPPY, "GRAAAAAARGH!").also { stage++ }
            }

            7 -> {
                if (incantation != correct) {
                    attackPlayer(player!!)
                    player!!.inventory.remove(Item(Items.LIT_BLACK_CANDLE_32, 1))
                    end()
                    stage = END_DIALOGUE
                } else {
                    npc(FacialExpression.OLD_HAPPY, "Thou hast me in thine control. So that I mayst", "return from whence I came, I must grant thee a boon.").also { stage++ }
                }
            }

            8 -> npc(FacialExpression.OLD_HAPPY, "What dost thou wish of me?").also { stage++ }
            9 -> playerl(FacialExpression.SCARED, "I wish to free Merlin from his giant crystal!").also { stage++ }
            10 -> npcl(FacialExpression.OLD_HAPPY, "GRAAAAAARGH!").also { stage++ }
            11 -> npcl(FacialExpression.OLD_HAPPY, "the deed is done.").also { stage++ }
            12 -> npc(FacialExpression.OLD_HAPPY, "Thou mayst now shatter Merlins' crystal with", "excalibur, ").also { stage++ }

            13 -> npc(FacialExpression.OLD_HAPPY, "and I can once more rest. Begone! And leave me once", "more in peace.").also { stage++ }

            14 -> {
                setQuestStage(player!!, "Merlin's Crystal", 50)
                disappear(player!!)
                end()
                stage = END_DIALOGUE
            }
        }

    }

    /**
     * Disappears the Thrantax.
     *
     * @param player The player whose Thrantax NPC is to be removed.
     */
    fun disappear(player: Player) {
        val thrantax = player.getAttribute<ThrantaxNPC>(MerlinUtils.TEMP_ATTR_THRANTAX, null)

        if (thrantax != null) {
            thrantax.player = null
        }
    }

    /**
     * Thrantax attacks the player.
     *
     * @param player The player to be attacked by the NPC.
     */
    fun attackPlayer(player: Player) {
        val thrantax = player.getAttribute<NPC>(MerlinUtils.TEMP_ATTR_THRANTAX, null)

        if (thrantax != null) {
            thrantax.attack(player)
        }
    }

    /**
     * Initializes the Thrantax NPC for the player, optionally checking for its existence.
     *
     * @param player The player for whom the Thrantax NPC is to be initialized.
     * @param checkMissing A flag indicating whether to check for an existing Thrantax NPC.
     */
    fun initThrantax(player: Player, checkMissing: Boolean) {
        if (checkMissing) {
            val thrantax = player.getAttribute<NPC>(MerlinUtils.TEMP_ATTR_THRANTAX, null)

            if (thrantax == null || thrantax != null && !thrantax.isActive) {
                spawn(player)
            }
        } else {
            spawn(player)
        }
    }

    /**
     * Spawns a Thrantax NPC for the player.
     *
     * @param player The player for whom the Thrantax NPC is to be spawned.
     */
    fun spawn(player: Player) {
        var thrantax = ThrantaxNPC(NPCs.THRANTAX_THE_MIGHTY_238, Location(2780, 3515, 0))
        thrantax.player = player
        thrantax.locks.lockMovement(10000)
        thrantax.graphics(Graphic.create(86))
        thrantax.init()
        thrantax.isRespawn = false
        setAttribute(player, MerlinUtils.TEMP_ATTR_THRANTAX, thrantax)
        setAttribute(thrantax, MerlinUtils.TEMP_ATTR_THRANTAX_OWNER, player.username)
    }

}
