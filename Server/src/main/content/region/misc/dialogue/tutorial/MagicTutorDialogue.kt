package content.region.misc.dialogue.tutorial

import content.region.misc.handlers.tutorial.*
import core.ServerConstants
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.IronmanMode
import core.game.node.entity.player.link.TeleportManager
import core.game.node.item.Item
import core.game.world.GameWorld
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.worker.ManagementEvents
import proto.management.JoinClanRequest

/**
 * Magic tutor dialogue.
 */
@Initializable
class MagicTutorDialogue(player: Player? = null) : Dialogue(player) {

    private val STARTER_PACK = arrayOf(
        // Supplies
        Item(Items.BRONZE_AXE_1351, 1),
        Item(Items.TINDERBOX_590, 1),
        Item(Items.SMALL_FISHING_NET_303, 1),
        Item(Items.SHRIMPS_315, 1),
        Item(Items.BUCKET_1925, 1),
        Item(Items.EMPTY_POT_1931, 1),
        Item(Items.BREAD_2309, 1),
        // Weapons
        Item(Items.BRONZE_PICKAXE_1265, 1),
        Item(Items.BRONZE_DAGGER_1205, 1),
        Item(Items.BRONZE_SWORD_1277, 1),
        Item(Items.WOODEN_SHIELD_1171, 1),
        Item(Items.SHORTBOW_841, 1),
        Item(Items.BRONZE_ARROW_882, 25),
        // Runes
        Item(Items.AIR_RUNE_556, 25),
        Item(Items.MIND_RUNE_558, 15),
        Item(Items.WATER_RUNE_555, 6),
        Item(Items.EARTH_RUNE_557, 4),
        Item(Items.BODY_RUNE_559, 2)
    )
    private val STARTER_BANK = arrayOf(Item(Items.COINS_995, 25))

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when (getAttribute(player, "tutorial:stage", 0)) {
            67 -> playerl(FacialExpression.FRIENDLY, "Hello.")
            69 -> npc(FacialExpression.FRIENDLY, "Good. This is a list of your spells. Currently you can", "only cast one offensive spell called Wind Strike. Let's", "try it out on one of those chickens.")
            70 -> if (!inInventory(player, Items.AIR_RUNE_556) && !inInventory(player, Items.MIND_RUNE_558)) {
                sendDoubleItemDialogue(player, Items.AIR_RUNE_556, Items.MIND_RUNE_558, "You receive some spare runes.")
                addItem(player, Items.AIR_RUNE_556, 15)
                addItem(player, Items.MIND_RUNE_558, 15)
                return false
            }
            71 -> npc(FacialExpression.FRIENDLY, "Well you're all finished here now. I'll give you a", "reasonable number of runes when you leave.")

            else -> return false
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (getAttribute(player, "tutorial:stage", 0)) {
            67 -> when (stage++) {
                0 -> npc(FacialExpression.FRIENDLY, "Good day, newcomer. My name is Terrova. I'm here", "to tell you about <col=08088A>Magic</col>. Let's start by opening your", "spell list.")
                1 -> {
                    end()
                    setAttribute(player, "tutorial:stage", 68)
                    TutorialStage.load(player, 68)
                }
            }

            69 -> when (stage++) {
                0 -> {
                    sendDoubleItemDialogue(player, Items.AIR_RUNE_556, Items.MIND_RUNE_558, "Terrova gives you five <col=08088A>air runes</col> and five <col=08088A>mind runes</col>!")
                    addItemOrDrop(player, Items.AIR_RUNE_556, 5)
                    addItemOrDrop(player, Items.MIND_RUNE_558, 5)
                }
                1 -> {
                    end()
                    setAttribute(player, "tutorial:stage", 70)
                    TutorialStage.load(player, 70)
                }
            }

            71 -> when (stage) {
                0 -> {
                    setTitle(player, 4)
                    options("What is an Ironman account?", "Set Ironman Mode (current: ${player.ironmanManager.mode.name})", "Change XP Rate (current: ${player.skills.experienceMultiplier}x)", "I'm ready now.").also { stage++ }
                }

                1 -> when (buttonId) {
                    1 -> player(FacialExpression.HALF_THINKING, "What is an Ironman account?").also { stage++ }
                    2 -> {
                        setTitle(player, 5)
                        sendDialogueOptions(player!!, "What game mode do you want to choose?", "None", "Standard", "Hardcore", "Ultimate", "Nevermind.")
                        stage = 10
                    }
                    3 -> {
                        setTitle(player!!, 4)
                        sendDialogueOptions(player!!, "What experience multiplier do you choose?", "1.0x", "25.0x", "500.0x", "1000x")
                        stage = 20
                    }
                    4 -> npcl(FacialExpression.FRIENDLY, "Well, you're all finished here now. I'll give you a reasonable number of starting items when you leave.").also { stage = 30 }
                }

                2 -> npc("An Ironman account is a style of playing where players", "are completely self-sufficient.").also { stage++ }
                3 -> npc("A standard Ironman does not receieve items or", "assistance from other players. They cannot trade, stake,", "receieve Player killing loot, scavenge dropped items, nor player", "certain minigames.").also { stage++ }
                4 -> npc("In addition to the standard Ironman rules. An", "Ultimate Ironman cannot use banks, nor retain any", "items on death in dangerous areas.").also { stage = 0 }

                10 -> {
                    stage = 0
                    if (buttonId < 5) {
                        val mode = IronmanMode.values()[buttonId - 1]
                        sendDialogue(player,"You set your ironman mode to: ${mode.name}.")
                        player.ironmanManager.mode = mode
                        if (player.skills.experienceMultiplier == 1000.0 && mode != IronmanMode.HARDCORE) player.skills.experienceMultiplier = 500.0
                    } else {
                        handle(interfaceId, 0)
                    }
                }

                20 -> {
                    val rates = arrayOf(1.0, 25.0, 500.0, 1000.0)
                    val rate = rates[buttonId - 1]
                    if (rate == 1000.0 && player.ironmanManager.mode != IronmanMode.HARDCORE) {
                        sendDialogue(player,"1000.0x is only available to Hardcore Ironman!")
                        stage = 0
                        return true
                    }
                    sendDialogue(player, "You set your XP rate to: ${rate}x.")
                    player.skills.experienceMultiplier = rate
                    stage = 0
                }
                30 -> {
                    setTitle(player, 2)
                    sendDialogueOptions(player, "Do you want to go to the mainland?", "Yes.", "No.").also { stage++ }
                }
                31 -> when (buttonId) {
                    1 -> playerl(FacialExpression.NEUTRAL, "I'm ready to go now, thank you.").also { stage++ }
                    2 -> playerl(FacialExpression.NEUTRAL, "No.").also { stage = END_DIALOGUE }
                }
                32 -> npc("Good good. I've deactivated the protective spells around", "the island so now you can teleport yourself out of", "here.").also { stage++ }
                33 -> npc("When you get to the mainland you will find yourself in", "the town of Lumbridge. If you want some ideas on", "where to go next, talk to my friend Phileas, also known", "as the Lumbridge Guide. You can't miss him; he's").also { stage++ }
                34 -> npc("holding a big staff with a question mark on the end. He", "also has a white beard and carries a rucksack full of", "scrolls. There are also tutors willing to teach you about", "the many skills you could learn.").also { stage++ }
                35 -> {
                    openInterface(player, Components.DOUBLEOBJBOX_131).also {
                        sendModelOnInterface(player, Components.DOUBLEOBJBOX_131,2, 7369, -1)
                        sendAngleOnInterface(player, Components.DOUBLEOBJBOX_131, 2, 1200, 500, 0)
                        setInterfaceText(player, "When you get to Lumbridge, look for this icon on your minimap. The Lumbridge Guide and the other tutors will be standing near one of these. The Lumbridge Guide should be standing slightly to the north-east of", Components.DOUBLEOBJBOX_131, 1)
                    }
                    stage++
                }
                36 -> {
                    openInterface(player, Components.DOUBLEOBJBOX_131).also {
                        sendModelOnInterface(player, Components.DOUBLEOBJBOX_131,2, 7369, -1)
                        sendAngleOnInterface(player, Components.DOUBLEOBJBOX_131, 2, 1200, 500, 0)
                        setInterfaceText(player, "the castle's courtyard and the others you will find" + "scattered around lumbridge.", Components.DOUBLEOBJBOX_131, 1)
                    }
                    stage++
                }
                37 -> {
                    npc("If all else fails, visit the " + GameWorld.settings!!.name + " website for a whole", "chestload of information on quests skills and minigames", "as well as a very good starter's guide.")
                    stage++
                }
                38 -> {
                    /*
                    "You have almost completed the tutorial!"

                    "Just click on the first spell, Home Teleport, in your Magic Spellbook."
                    "This spell doesn't require any runes, but can only be cast once every"
                    "30 minutes."
                     */

                    setAttribute(player, "/save:tutorial:complete", true)
                    setVarbit(player, 3756, 0)
                    setVarp(player, 281, 1000, true)
                    teleport(player, Location.create(3233, 3230), TeleportManager.TeleportType.NORMAL)
                    closeOverlay(player)
                    player.inventory.clear()
                    player.bank.clear()
                    player.equipment.clear()
                    player.interfaceManager.restoreTabs()
                    player.interfaceManager.setViewedTab(3)
                    player.inventory.add(*STARTER_PACK)
                    player.bank.add(*STARTER_BANK)
                    interpreter.sendDialogue(
                        "Welcome to Lumbridge! To get more help click on the Lumbridge",
                        "Guide or one of the Tutors - these can be found by looking",
                        "for the question mark icon on your mini-map. If you are lost",
                        "at any time, look for a signpost or use the Lumbridge Home Teleport."
                    )
                    if (player.ironmanManager.mode == IronmanMode.HARDCORE) {
                        setAttribute(player, "/save:permadeath", true)
                    } else if (player.skills.experienceMultiplier == 1000.0) {
                        player.skills.experienceMultiplier = 500.0
                    }

                    stage = 39

                    TutorialStage.removeHintIcon(player)
                    player.unhook(TutorialKillReceiver)
                    player.unhook(TutorialFireReceiver)
                    player.unhook(TutorialResourceReceiver)
                    player.unhook(TutorialUseWithReceiver)
                    player.unhook(TutorialInteractionReceiver)
                    player.unhook(TutorialButtonReceiver)

                    if (GameWorld.settings!!.enable_default_clan) {
                        player.communication.currentClan = ServerConstants.SERVER_NAME
                        val clanJoin = JoinClanRequest.newBuilder()
                        clanJoin.clanName = ServerConstants.SERVER_NAME
                        clanJoin.username = player.name
                        ManagementEvents.publish(clanJoin.build())
                    }
                }

                39 -> {
                    setAttribute(player, "close_c_", true)
                    end()
                }
            }
        }
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAGIC_INSTRUCTOR_946)
    }

}
