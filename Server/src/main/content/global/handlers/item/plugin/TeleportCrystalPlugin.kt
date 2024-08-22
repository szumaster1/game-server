package content.global.handlers.item.plugin

import core.api.addItem
import cfg.consts.Items
import core.api.hasRequirement
import core.api.removeItem
import core.api.sendMessage
import core.cache.def.impl.ItemDefinition
import core.game.dialogue.Dialogue
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager.TeleportType
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.zone.impl.WildernessZone
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Teleport crystal plugin.
 */
@Initializable
class TeleportCrystalPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(Items.TELEPORT_CRYSTAL_4_6099).handlers["option:activate"] = this
        ItemDefinition.forId(Items.TELEPORT_CRYSTAL_3_6100).handlers["option:activate"] = this
        ItemDefinition.forId(Items.TELEPORT_CRYSTAL_2_6101).handlers["option:activate"] = this
        ItemDefinition.forId(Items.TELEPORT_CRYSTAL_1_6102).handlers["option:activate"] = this
        TeleportCrystalDialogue().init()
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (!hasRequirement(player, "Mourning's End Part I")) return true
        if (!WildernessZone.checkTeleport(player, 20)) {
            sendMessage(player, "The crystal is unresponsive.")
            return true
        }
        player.dialogueInterpreter.open(TeleportCrystalDialogue.ID, 1, node.asItem().id)
        //degrade(player, node.asItem());
        return true
    }


    override fun isWalk(): Boolean {
        return false
    }

    /**
     * Teleport crystal dialogue.
     */
    class TeleportCrystalDialogue(player: Player? = null) : Dialogue(player) {
        private var itemId: Int? = null

        override fun newInstance(player: Player): Dialogue {
            return TeleportCrystalDialogue(player)
        }

        override fun open(vararg args: Any): Boolean {
            itemId = args[1] as Int
            when (args[0] as Int) {
                1 -> options("Teleport to Lletya", "Cancel").also { stage = 100 }
            }
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            val npc = NPC(2376)
            val billTeach = NPC(3155)
            when (stage) {
                100 -> when (buttonId) {
                    1 -> {
                        player.teleporter.send(Location(2329, 3172), TeleportType.NORMAL)
                        degrade(player, Item(itemId!!))
                    }

                    2 -> end()
                }
            }
            return true
        }


        override fun getIds(): IntArray {
            return intArrayOf(ID)
        }

        companion object {
            const val ID: Int = 3999111
            private fun degrade(player: Player, item: Item) {
                val id = item.id
                val newItem = item.id + 1
                if (id < 6102) {
                    removeItem(player, Item(id, 1))
                    addItem(player, newItem, 1)
                    sendMessage(player, "Your teleportation crystal has degraded from use.")
                } else {
                    removeItem(player, Item(id, 1))
                    addItem(player, newItem, 1)
                    sendMessage(player, "Your teleportation crystal has degraded to a tiny elf crystal,")
                    sendMessage(player, "Eluned can re-enchant it.")
                }
            }
        }
    }
}
