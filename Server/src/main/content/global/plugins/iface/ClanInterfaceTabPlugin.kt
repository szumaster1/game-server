package content.global.plugins.iface

import core.api.consts.Components
import core.api.sendInputDialogue
import core.api.setInterfaceText
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.game.system.communication.ClanRank
import core.game.system.communication.ClanRepository
import core.network.amsc.MSPacketRepository
import core.network.amsc.WorldCommunicator
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.StringUtils

@Initializable
class ClanInterfaceTabPlugin : ComponentPlugin() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(Components.CLANSETUP_590, this)
        ComponentDefinition.put(Components.CLANJOIN_589, this)
        return this
    }

    override fun handle(
        player: Player,
        component: Component,
        opcode: Int,
        button: Int,
        slot: Int,
        itemId: Int
    ): Boolean {
        when (component.id) {
            589 -> when (button) {
                9 -> {
                    if (player.interfaceManager.getComponent(590) != null) {
                        player.packetDispatch.sendMessage("Please close the interface you have open before using 'Clan Setup'")
                        return true
                    }
                    ClanRepository.openSettings(player)
                    return true
                }

                14 -> {
                    player.details.communication.toggleLootshare(player)
                    return true
                }
            }

            590 -> {
                val clan = ClanRepository.get(player.name, true)
                when (button) {
                    23 -> {
                        if (opcode == 155) {
                            clan.joinRequirement = ClanRank.NONE
                        } else {
                            clan.joinRequirement = getRank(opcode)
                        }
                        player.details.communication.joinRequirement = clan.joinRequirement
                        MSPacketRepository.setClanSetting(player, 0, clan.joinRequirement)
                        player.packetDispatch.sendString(clan.joinRequirement.info, 590, 23)
                    }

                    24 -> {
                        clan.messageRequirement = getRank(opcode)
                        player.details.communication.messageRequirement = clan.messageRequirement
                        MSPacketRepository.setClanSetting(player, 1, clan.messageRequirement)
                        player.packetDispatch.sendString(clan.messageRequirement.info, 590, 24)
                    }

                    25 -> {
                        clan.kickRequirement = getRank(opcode)
                        player.details.communication.kickRequirement = clan.kickRequirement
                        MSPacketRepository.setClanSetting(player, 2, clan.kickRequirement)
                        player.packetDispatch.sendString(clan.kickRequirement.info, 590, 25)
                        clan.update()
                    }

                    26 -> {
                        if (opcode == 230) {
                            clan.lootRequirement = ClanRank.ADMINISTRATOR
                        } else {
                            clan.lootRequirement = getRank(opcode)
                        }
                        player.details.communication.lootRequirement = clan.lootRequirement
                        MSPacketRepository.setClanSetting(player, 3, clan.lootRequirement)
                        player.packetDispatch.sendString(clan.lootRequirement.info, 590, 26)
                    }

                    22 -> when (opcode) {
                        196 -> {
                            clan.name = "Chat disabled"
                            player.communication.clanName = ""
                            player.packetDispatch.sendString(clan.name, 590, 22)
                            if (WorldCommunicator.isEnabled()) {
                                MSPacketRepository.sendClanRename(player, "")
                            }
                            clan.clean(true)
                        }

                        else -> sendInputDialogue(player, false, "Enter clan prefix:") { value: Any? ->
                            val name = StringUtils.formatDisplayName(value as String?)
                            setInterfaceText(player, name, 590, 22)
                            if (WorldCommunicator.isEnabled()) {
                                MSPacketRepository.sendClanRename(player, name)
                                clan.name = name
                                return@sendInputDialogue
                            }
                            if (clan.name == "Chat disabled") {
                                player.packetDispatch.sendMessage("Your clan channel has now been enabled!")
                                player.packetDispatch.sendMessage("Join your channel by clicking 'Join Chat' and typing: " + player.username)
                            }
                            clan.name = name
                            player.communication.clanName = name
                            clan.update()
                        }
                    }
                }
            }
        }
        return true
    }

    companion object {
        fun getRank(opcode: Int): ClanRank {
            when (opcode) {
                155 -> return ClanRank.NONE
                196 -> return ClanRank.FRIEND
                124 -> return ClanRank.RECRUIT
                199 -> return ClanRank.CORPORAL
                234 -> return ClanRank.SERGEANT
                168 -> return ClanRank.LIEUTENANT
                166 -> return ClanRank.CAPTAIN
                64 -> return ClanRank.GENERAL
                53 -> return ClanRank.OWNER
            }
            return ClanRank.NONE
        }
    }

}
