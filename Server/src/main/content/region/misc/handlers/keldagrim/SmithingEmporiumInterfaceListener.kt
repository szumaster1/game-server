package content.region.misc.handlers.keldagrim

import cfg.consts.Components
import core.api.getAttribute
import core.api.removeAttribute
import core.api.sendDialogue
import core.api.setAttribute
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.item.Item

const val REINALD_COMPONENT_ID = 593
private const val BRACELET_INTERFACE_CHILD_ID = 69

private const val SILVER_WRISTGUARDS = 27703
private const val SILVER_CLASPS = 27704
private const val SILVER_BANGLES = 27706
private const val SILVER_BANDING = 27707
private const val SILVER_BANDS = 27697
private const val SILVER_ARMGUARDS = 27699

private const val GOLD_WRISTGUARDS = 27698
private const val GOLD_CLASPS = 27708
private const val GOLD_BANGLES = 27702
private const val GOLD_BANDING = 27705
private const val GOLD_BANDS = 27700
private const val GOLD_ARMGUARDS = 27709

private const val NONE = 0

private val COINS = Item(995, 500)

/*
 * Appearance Indexes for Various Wrist guards.
 * 117S 118G 119S 120G 121S 122G 123S 124G 125S 126G M
 * 159S 160G 161S 162G 163S 164G 165S 166G 167S 168G F
 * 67SF 68NF 127GF 33SM 34NM 84GM
 */

/**
 * Smithing emporium interface listener.
 */
class SmithingEmporiumInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        onOpen(Components.REINALD_SMITHING_EMPORIUM_593) { player, component ->
            setAttribute(player, "wrists-look", player.appearance.wrists.look)
            player.toggleWardrobe(true)
            component.setCloseEvent { p, _ ->
                player.toggleWardrobe(false)
                val orindex = getAttribute(p, "wrists-look", if (p.isMale) 34 else 68)
                val paid = getAttribute(p, "bracelet-paid", false)
                if (!paid) {
                    p.appearance.wrists.changeLook(orindex)
                    p.appearance.sync()
                }
                removeAttribute(player, "bracelet-paid")
                return@setCloseEvent true
            }
            return@onOpen true
        }


        on(Components.REINALD_SMITHING_EMPORIUM_593) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                122, 129 -> sendModel(NONE, player)
                123 -> sendModel(SILVER_WRISTGUARDS, player)
                124 -> sendModel(SILVER_CLASPS, player)
                125 -> sendModel(SILVER_BANGLES, player)
                126 -> sendModel(SILVER_BANDING, player)
                127 -> sendModel(SILVER_BANDS, player)
                128 -> sendModel(SILVER_ARMGUARDS, player)
                130 -> sendModel(GOLD_WRISTGUARDS, player)
                131 -> sendModel(GOLD_CLASPS, player)
                132 -> sendModel(GOLD_BANGLES, player)
                133 -> sendModel(GOLD_BANDING, player)
                134 -> sendModel(GOLD_BANDS, player)
                135 -> sendModel(GOLD_ARMGUARDS, player)
                117 -> confirm(player)
            }
            return@on true
        }
    }


    /**
     * Confirm.
     *
     * @param player the player.
     */
    fun confirm(player: Player) {
        if (player.inventory.containsItem(COINS)) {
            player.inventory.remove(COINS)
            setAttribute(player, "bracelet-paid", true)
            player.interfaceManager.close()
        } else {
            sendDialogue(player, "You can not afford that.")
        }
    }

    /**
     * Send model.
     *
     * @param id the id.
     * @param player the player.
     */
    fun sendModel(id: Int, player: Player) {
        var appearanceIndex = when (id) {
            SILVER_CLASPS -> 117
            GOLD_CLASPS -> 118
            SILVER_BANDS -> 119
            GOLD_BANDS -> 120
            SILVER_ARMGUARDS -> 123
            GOLD_ARMGUARDS -> 124
            SILVER_BANDING -> 121
            GOLD_BANDING -> 122
            SILVER_BANGLES -> 125
            GOLD_BANGLES -> 126
            SILVER_WRISTGUARDS -> if (player.isMale) 33 else 67
            GOLD_WRISTGUARDS -> if (player.isMale) 84 else 127
            NONE -> if (player.isMale) 34 else 68
            else -> 0
        }
        if (!player.isMale && id != SILVER_WRISTGUARDS && id != GOLD_WRISTGUARDS && id != NONE) {
            appearanceIndex += 42
        }
        player.packetDispatch.sendModelOnInterface(id, REINALD_COMPONENT_ID, BRACELET_INTERFACE_CHILD_ID, 1)
        player.packetDispatch.sendInterfaceConfig(REINALD_COMPONENT_ID, BRACELET_INTERFACE_CHILD_ID, id == 0)
        player.appearance.wrists.changeLook(appearanceIndex)
        player.debug("USING WRIST APPEARANCE ID $appearanceIndex")
        player.appearance.sync()
        player.packetDispatch.sendPlayerOnInterface(REINALD_COMPONENT_ID, 60)
    }
}
