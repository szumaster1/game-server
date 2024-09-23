package content.global.handlers.iface

import content.global.handlers.iface.bank.BankInterface
import content.region.misc.tutorial.handlers.CharacterDesign
import core.api.*
import core.game.component.Component
import core.game.container.Container
import core.game.container.ContainerEvent
import core.game.container.ContainerListener
import core.game.container.access.InterfaceContainer
import core.game.ge.GEItemSet
import core.game.interaction.InterfaceListener
import core.game.interaction.QueueStrength
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.combat.equipment.WeaponInterface.WeaponInterfaces
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.Rights
import core.game.node.entity.player.info.login.LoginConfiguration
import core.game.node.entity.player.link.IronmanMode
import core.game.node.entity.player.link.emote.Emotes
import core.game.node.entity.player.link.request.assist.AssistSession
import core.game.system.communication.ClanRank
import core.game.system.communication.ClanRepository
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.net.amsc.MSPacketRepository
import core.net.amsc.WorldCommunicator
import core.tools.Log
import core.tools.StringUtils
import org.rs.consts.Animations
import org.rs.consts.Components
import org.rs.consts.Vars

/**
 * Handles the main interface buttons.
 */
class GameInterface : InterfaceListener {

    override fun defineInterfaceListeners() {

        /*
         * Item select.
         */

        onOpen(Components.ITEM_SELECT_12) { player, _ ->
            submitIndividualPulse(player, object : Pulse() {
                override fun pulse(): Boolean {
                    return false
                }

                override fun stop() {
                    super.stop()
                    closeTabInterface(player)
                }
            })
            return@onOpen true
        }
        on(Components.ITEM_SELECT_12) { player, _, opcode, _, slot, _ ->
            processResponse(player, opcode, slot)
            return@on true
        }
        onClose(Components.ITEM_SELECT_12) { player, _ ->
            removeAttribute(player, "itemselect-callback")
            removeAttribute(player, "itemselect-keepalive")
            return@onClose true
        }

        /*
         * Double object box.
         */

        onOpen(Components.DOUBLEOBJBOX_131) { player, _ ->
            setInterfaceSprite(player, Components.DOUBLEOBJBOX_131, 1, 96, 25)// String.
            setInterfaceSprite(player, Components.DOUBLEOBJBOX_131, 3, 96, 98)// Continue button.
            return@onOpen true
        }

        /*
         * Chat.
         */

        on(Components.CHATDEFAULT_137) { player, _, _, buttonID, _, _ ->
            if (buttonID == 5) {
                openInterface(player, Components.QUICKCHAT_TUTORIAL_157)
            }
            return@on true
        }

        /*
         * Select an option.
         */

        onOpen(Components.SELECT_AN_OPTION_140) { player, _ ->
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 0, 23, 5)// Left sword sprite.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 2, 31, 32)// Left text box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 3, 234, 32)// Right text box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 4, 24, 3)// Title.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 5, 123, 36)// Left model box.
            setInterfaceSprite(player, Components.SELECT_AN_OPTION_140, 6, 334, 36)// Right model box.
            return@onOpen true
        }

        /*
         * Handles the death interface.
         */

        on(Components.AIDE_DEATH_153) { player, _, _, buttonID, _, _ ->
            if (buttonID == 1) {
                player.getSavedData().globalData.setDisableDeathScreen(true)
                player.interfaceManager.close()
            }
            return@on true
        }

        /*
         * Quick-chat tutorial.
         */

        onOpen(Components.QUICKCHAT_TUTORIAL_157) { player, _ ->
            setVarbit(player, Vars.VARBIT_IFACE_QUICKCHAT_TUTORIAL_4762, 1)
            return@onOpen true
        }
        onClose(Components.QUICKCHAT_TUTORIAL_157) { player, _ ->
            setVarbit(player, Vars.VARBIT_IFACE_QUICKCHAT_TUTORIAL_4762, 0)
            return@onClose true
        }

        /*
         * Options.
         */

        on(Components.OPTIONS_261) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                RUN -> player.settings.toggleRun()
                CHAT_EFFECTS -> player.settings.toggleChatEffects()
                SPLIT_PM -> player.settings.toggleSplitPrivateChat()
                MOUSE -> player.settings.toggleMouseButton()
                AID -> restrictForIronman(player, IronmanMode.STANDARD) { player.settings.toggleAcceptAid() }
                HOUSE -> openSingleTab(player, Components.POH_HOUSE_OPTIONS_398)
                GRAPHICS -> openInterface(player, Components.GRAPHICS_OPTIONS_742)
                AUDIO -> openInterface(player, Components.SOUND_OPTIONS_743)
            }
            return@on true
        }

        /*
         * Tutorial chatbox.
         */

        onOpen(Components.TUTORIAL_TEXT_372) { player, _ ->
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 0, 25, 20)// Title.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 1, 10, 34)// String 0.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 2, 10, 49)// String 1.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 3, 10, 64)// String 2.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 4, 10, 79)// String 3.
            return@onOpen true
        }

        /*
         * Request assist.
         */

        on(Components.REQ_ASSIST_301) { player, _, _, buttonID, _, _ ->
            val session = AssistSession.getExtension(player) ?: return@on true
            if (player !== session.player) {
                return@on true
            }
            when (buttonID) {
                15 -> session.toggleButton(0.toByte())
                20 -> session.toggleButton(1.toByte())
                25 -> session.toggleButton(2.toByte())
                30 -> session.toggleButton(3.toByte())
                35 -> session.toggleButton(4.toByte())
                40 -> session.toggleButton(5.toByte())
                45 -> session.toggleButton(6.toByte())
                50 -> session.toggleButton(7.toByte())
                55 -> session.toggleButton(8.toByte())
            }
            session.refresh()
            return@on true
        }

        /*
         * Represents the plugin used for the login interface.
         */

        on(Components.WELCOME_SCREEN_378) { player, _, _, buttonID, _, _ ->
            val playButton = 140
            val creditButton = 145
            val discordButton = 204

            when (buttonID) {
                playButton -> {
                    player.locks.lock("login", 2)
                    closeInterface(player)
                    runTask(player, 1, 0) {
                        LoginConfiguration.configureGameWorld(player)
                    }
                }

                creditButton -> return@on true
                discordButton -> return@on true
            }
            return@on true
        }
        onClose(Components.WELCOME_SCREEN_378) { player, _ ->
            return@onClose player.locks.isLocked("login")
        }

        /*
         * Handles the emote tab interface.
         */

        on(Components.EMOTES_464) { player, _, _, buttonID, _, _ ->
            Emotes.handle(player, buttonID)
            return@on true
        }

        /*
         * Represents the listener used for the skilling interface.
         */

        on(Components.SKILL_GUIDE_V2_499) { player, _, _, buttonID, _, _ ->
            setVarbit(player, 3288, getAttribute(player, "skillMenu", -1))
            setVarbit(player, 3289, buttonID - 10)
            return@on true
        }

        /*
         * Top level.
         */

        on(Components.TOPLEVEL_548) { player, _, _, buttonID, _, _ ->
            if (buttonID in 38..44 || buttonID in 20..26) {
                player.interfaceManager.currentTabIndex = getTabIndex(buttonID)
            }
            when (buttonID) {
                21 -> sendString(player, "Friends List - " + GameWorld.settings!!.name + " " + GameWorld.settings!!.worldId, Components.FRIENDS2_550, 3)
                22 -> {}
                24 -> {}
                25 -> {}
                26 -> {}
                38 -> {
                    if (player.getExtension<Any>(WeaponInterface::class.java) == WeaponInterfaces.STAFF) {
                        val c = Component(WeaponInterfaces.STAFF.interfaceId)
                        player.interfaceManager.openTab(0, c)
                        val inter = player.getExtension<WeaponInterface>(WeaponInterface::class.java)
                        inter.updateInterface()
                    }
                }
                39 -> {}
                40 -> player.questRepository.syncronizeTab(player)
                41 -> player.inventory.refresh()
                42 -> {}
                43 -> {}
                44 -> {}
                66, 110 -> configureWorldMap(player)
                69 -> sendString(player, "When you have finished playing ${GameWorld.settings!!.name}" + ", always use the button below to logout safely.", 182, 0)
                else -> throw IllegalStateException("Unexpected value: $buttonID")

            }
            return@on true
        }

        /*
         * Clan join.
         */

        on(Components.CLANJOIN_589) { player, _, _, buttonID, _, _ ->
            if (buttonID == 9) {
                if (player.interfaceManager.opened != null) {
                    sendMessage(player, "Please close the interface you have open before using 'Clan Setup'")
                } else {
                    ClanRepository.openSettings(player)
                }
            }

            if (buttonID == 14) {
                player.communication.toggleLootshare(player)
            }
            return@on true
        }

        /*
         * Clan setup.
         */

        on(Components.CLANSETUP_590) { player, _, opcode, buttonID, _, _ ->
            val clan = ClanRepository.get(player.name, true)

            when (buttonID) {
                22 -> {
                    if (opcode == 155) {
                        sendInputDialogue(player, false, "Enter clan prefix:") { value ->
                            val clanName = StringUtils.formatDisplayName(value.toString())

                            if (WorldCommunicator.isEnabled()) {
                                MSPacketRepository.sendClanRename(player, clanName)
                            }

                            if (clan.name == "Chat disabled") {
                                sendMessage(player, "Your clan channel has now been enabled!")
                                sendMessage(
                                    player,
                                    "Join your channel by clicking 'Join Chat' and typing: ${player.username}"
                                )
                            }

                            clan.name = clanName
                            player.communication.clanName = clanName
                            clan.updateSettings(player)
                            clan.update()
                        }
                    }

                    if (opcode == 196) {
                        clan.name = "Chat disabled"
                        player.communication.clanName = ""
                        if (WorldCommunicator.isEnabled()) {
                            MSPacketRepository.sendClanRename(player, player.communication.clanName)
                        }
                        clan.updateSettings(player)
                        clan.delete()
                    }
                }

                23 -> {
                    clan.joinRequirement = getRank(opcode)
                    player.communication.joinRequirement = clan.joinRequirement
                    MSPacketRepository.setClanSetting(player, 0, clan.joinRequirement)
                }

                24 -> {
                    clan.messageRequirement = getRank(opcode)
                    player.communication.messageRequirement = clan.messageRequirement
                    MSPacketRepository.setClanSetting(player, 1, clan.messageRequirement)
                }

                25 -> {
                    clan.kickRequirement = getRank(opcode)
                    player.communication.kickRequirement = clan.kickRequirement
                    MSPacketRepository.setClanSetting(player, 2, clan.kickRequirement)
                }

                26 -> {
                    clan.lootRequirement = if (opcode == 155) ClanRank.ADMINISTRATOR else getRank(opcode)
                    player.communication.lootRequirement = clan.lootRequirement
                    MSPacketRepository.setClanSetting(player, 3, clan.lootRequirement)
                }

                33 -> sendMessage(player, "CoinShare is not available.")
            }

            clan.updateSettings(player)
            clan.update()
            return@on true
        }

        /*
         * Exchange item sets.
         */

        onClose(Components.EXCHANGE_ITEMSETS_645) { player, _ ->
            val listener = getAttribute<InventoryListener?>(player, "ge-listener", null)
            player.inventory.listeners.remove(listener)
            player.interfaceManager.closeSingleTab()
            removeAttribute(player, "container-key")
            removeAttribute(player, "ge-listener")
            return@onClose true
        }

        /*
         * Represents the component plugin used for the game interface.
         */

        on(Components.GAME_INTERFACE_740) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                3 -> closeChatBox(player)
            }
            return@on true
        }

        /*
         * Top level full screen.
         */

        on(Components.TOPLEVEL_FULLSCREEN_746) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                3 -> closeInterface(player)
                12 -> player.packetDispatch.sendString("When you have finished playing " + GameWorld.settings!!.name + ", always use the button below to logout safely.", 182, 0)
                49 -> sendString(player, "Friends List - " + GameWorld.settings!!.name + " " + GameWorld.settings!!.worldId, 550, 3)
                110 -> configureWorldMap(player)
            }
            return@on true
        }

        /*
         * Run.
         */

        on(Components.TOPSTAT_RUN_750) { player, _, opcode, buttonID, _, _ ->
            when (opcode) {
                155 -> when (buttonID) {
                    1 -> player.settings.toggleRun()
                }
            }
            return@on true
        }

        /*
         * Filter.
         */

        on(Components.FILTERBUTTONS_751) { player, _, opcode, buttonID, _, _ ->
            when (opcode) {
                155 -> when (buttonID) {
                    27 -> openReport(player)
                }
            }
            return@on true
        }

        /*
         * Handles the world map interface.
         */

        on(Components.WORLDMAP_755, 3) { player, _, _, _, _, _ ->
            player.interfaceManager.openWindowsPane(Component(if (player.interfaceManager.isResizable) 746 else 548), 2)
            player.packetDispatch.sendRunScript(1187, "ii", 0, 0)
            player.updateSceneGraph(true)
            return@on true
        }

        /*
         * Represents a listener to handle the character design interface.
         */

        on(Components.APPEARANCE_771) { player, _, _, buttonID, _, _ ->
            CharacterDesign.handleButtons(player, buttonID)
            return@on true
        }
    }

    companion object {
        const val RUN = 3
        const val CHAT_EFFECTS = 4
        const val SPLIT_PM = 5
        const val MOUSE = 6
        const val AID = 7
        const val HOUSE = 8
        const val GRAPHICS = 16
        const val AUDIO = 18

        fun getTabIndex(button: Int): Int {
            var tabIndex = button - 38
            if (button < 27) {
                tabIndex = (button - 20) + 7
            }
            return tabIndex
        }

        fun getRank(opcode: Int): ClanRank {
            return when (opcode) {
                155 -> ClanRank.NONE
                196 -> ClanRank.FRIEND
                124 -> ClanRank.RECRUIT
                199 -> ClanRank.CORPORAL
                234 -> ClanRank.SERGEANT
                168 -> ClanRank.LIEUTENANT
                166 -> ClanRank.CAPTAIN
                64 -> ClanRank.GENERAL
                53 -> ClanRank.OWNER
                else -> ClanRank.NONE
            }
        }

        @JvmStatic
        fun openReport(player: Player) {
            player.interfaceManager.open(Component(553)).setCloseEvent { player1: Player, c: Component? ->
                player1.packetDispatch.sendRunScript(80, "")
                player1.packetDispatch.sendRunScript(137, "")
                true
            }
            player.packetDispatch.sendRunScript(508, "")
            if (player.details.rights !== Rights.REGULAR_PLAYER) {
                for (i in 0..17) {
                    player.packetDispatch.sendInterfaceConfig(553, i, false)
                }
            }
        }

        @JvmStatic
        fun openFor(player: Player) {
            openInterface(player, Components.EXCHANGE_ITEMSETS_645)
            player.interfaceManager.openSingleTab(Component(Components.EXCHANGE_SETS_SIDE_644))
            val listener: InventoryListener
            setAttribute(player, "ge-listener", InventoryListener(player).also { listener = it })
            player.inventory.listeners.add(listener)
        }

        private fun configureWorldMap(player: Player) {
            if (player.inCombat()) {
                player.packetDispatch.sendMessage("It wouldn't be very wise opening the world map during combat.")
                return
            }
            if (player.locks.isInteractionLocked || player.locks.isMovementLocked) {
                player.packetDispatch.sendMessage("You can't do this right now.")
                return
            }
            player.interfaceManager.close()
            player.interfaceManager.openWindowsPane(Component(755))
            val posHash = player.location.z shl 28 or (player.location.x shl 14) or player.location.y
            player.packetDispatch.sendScriptConfigs(622, posHash, "", 0)
            player.packetDispatch.sendScriptConfigs(674, posHash, "", 0)
        }

        private fun processResponse(player: Player, opcode: Int, slot: Int) {
            val callback = getAttribute<((Int, Int) -> Unit)?>(player, "itemselect-callback", null)
            if (callback == null) {
                log(this::class.java, Log.WARN, "${player.name} is trying to use an item select prompt with no callback!")
                return
            }

            val optionIndex = when (opcode) {
                155 -> 0
                196 -> 1
                124 -> 2
                199 -> 3
                234 -> 4
                9 -> 10
                else -> -1
            }

            if (optionIndex == -1) {
                log(this::class.java, Log.WARN, "${player.name} is clicking a right-click index that we don't know the opcode for yet, lol. Here's the opcode: $opcode")
                return
            }

            callback.invoke(slot, optionIndex)
            if (!getAttribute(player, "itemselect-keepalive", false)) {
                removeAttribute(player, "itemselect-callback")
                removeAttribute(player, "itemselect-keepalive")
                closeTabInterface(player)
            }
        }

        private class InventoryListener(val player: Player) : ContainerListener {
            init {
                createContainers(player)
            }

            override fun update(c: Container?, event: ContainerEvent?) {
                createContainers(player)
            }

            override fun refresh(c: Container?) {
                createContainers(player)
            }

            private fun createContainers(player: Player) {
                setAttribute(entity = player, attribute = "container-key", value = InterfaceContainer.generateItems(player, player.inventory.toArray(), arrayOf("Examine", "Exchange", "Components"), Components.EXCHANGE_SETS_SIDE_644, 0, 7, 4))
                InterfaceContainer.generateItems(player, GEItemSet.getItemArray(), arrayOf("Examine", "Exchange", "Components"), Components.EXCHANGE_ITEMSETS_645, 16, 15, 10)
            }
        }
    }

}