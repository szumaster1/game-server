package content.global.skill.support.construction.decoration.combatroom

import content.global.skill.support.construction.BuildHotspot
import core.api.*
import cfg.consts.Items
import core.game.dialogue.DialogueFile
import core.game.dialogue.IfTopic
import core.game.dialogue.Topic
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

/**
 * Weapon rack listener.
 */
class WeaponRackListener : InteractionListener{

    private val WEAPON_RACK = intArrayOf(13381,13382,13383)
    private val BOXING_GLOVES = intArrayOf(Items.BOXING_GLOVES_7671, Items.BOXING_GLOVES_7673)

    override fun defineListeners() {
        on(WEAPON_RACK, IntType.SCENERY, "search") { player, r ->
            if (freeSlots(player) < 1) {
                sendMessage(player, "You don't have enough inventory space for that.")
                return@on true
            }
            lock(player, 1)
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    when (stage) {
                        0 -> sendDialogueOptions(
                            player, "What do you want to take?" + showTopics(
                                Topic("Red boxing gloves", 1, true),
                                Topic("Blue boxing gloves", 2, true),
                                IfTopic("Wooden sword", 3, r.id != 13381, true),
                                IfTopic("Wooden shield", 4, r.id != 13381, true),
                                IfTopic("Pugel", 5, r.id == 13383, true)
                            )
                        )

                        1 -> {
                            end()
                            addItem(player, Items.BOXING_GLOVES_7671)
                            sendMessage(player, "You take boxing gloves from the rack.")
                        }

                        2 -> {
                            end()
                            addItem(player, Items.BOXING_GLOVES_7673)
                            sendMessage(player, "You take boxing gloves from the rack.")

                        }

                        3 -> {
                            end()
                            addItem(player, Items.WOODEN_SWORD_7675)
                            sendMessage(player, "You take wooden sword from the rack.")
                        }

                        4 -> {
                            end()
                            addItem(player, Items.WOODEN_SHIELD_7676)
                            sendMessage(player, "You take wooden shield from the rack.")
                        }

                        5 -> {
                            end()
                            val room = player.houseManager.getRoom(player.location)!!
                            val hotspots = player.houseManager.getRoom(player.location)!!.hotspots
                            if (getItemFromEquipment(player, EquipmentSlot.WEAPON) != null && getItemFromEquipment(player, EquipmentSlot.SHIELD) != null) {
                                end()
                                sendMessage(player, "You cannot wield items in your both hands.")
                                return
                            }
                            for (i in hotspots.indices) {
                                val h = hotspots[i]!!
                                if (!room.isBuilt(BuildHotspot.CR_RING4)) {
                                    if (h.decorationIndex == -1) {
                                        end()
                                        sendMessage(player, "You must build a balance beam first!")
                                        return
                                    }
                                }
                                player.equipment.replace(Item(Items.PUGEL_7679), 3)
                            }
                        }
                    }
                }
            })
            return@on true
        }

        onEquip(BOXING_GLOVES) { player, _ ->
            val gloves = getItemFromEquipment(player, EquipmentSlot.HANDS)
            if (gloves != null) {
                sendMessage(player, "You cannot wear the boxing gloves over other gloves.")
                return@onEquip false
            }
            return@onEquip true
        }

    }
}