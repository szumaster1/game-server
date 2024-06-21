package content.region.desert.quest.thegolemquest

import content.region.desert.quest.thegolemquest.dialogue.ClayGolemProgramDialogueFile
import content.region.desert.quest.thegolemquest.dialogue.DISPLAY_CASE_TEXT
import content.region.desert.quest.thegolemquest.dialogue.LETTER_LINES
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.api.consts.Vars
import core.game.global.action.ClimbActionHandler
import core.game.global.action.SpecialLadders
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.node.Node
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.utilities.RandomFunction

@Initializable
class TheGolemQuest : Quest("The Golem", 70, 69, 1, Vars.VARBIT_QUEST_THE_GOLEM_PROGRESS, 0, 1, 10) {

    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        var ln = 11
        if (stage == 0) {
            line(player, "I can start this quest by talking to !!the golem?? who is in the:", ln++, false)
            line(player, "Ruined city of !!Uzer??, which is in the desert to the east of", ln++, false)
            line(player, "the !!Shantay Pass.", ln++, false)
            line(player, "I will need to have !!level 20 crafting?? and !!level 25 thieving", ln++, false)
        }
        if (stage >= 1) {
            line(player, "I've found !!the golem??, and offered to !!repair?? it.", ln++, stage > 1)
        }
        if (stage >= 2) {
            line(player, "I've !!repaired?? the golem with some !!soft clay??.", ln++, stage > 2)
        }
        if (stage >= 3) {
            line(player, "The golem wants me to open a portal to help it defeat", ln++, stage > 3)
            line(player, "the demon that attacked its city.", ln++, stage > 3)
        }
        val readLetter = getAttribute(player, "the-golem:read-elissa-letter", false)
        val readBook = getAttribute(player, "the-golem:varmen-notes-read", false)
        if (readLetter) {
            line(player, "I've found a letter that mentions !!The Digsite", ln++, readBook)
        }
        if (readBook) {
            line(player, "I've found a !!book?? that mentions that golems are !!programmed by??", ln++, stage > 7)
            line(player, "!!writing instructions?? on !!papyrus?? with a !!phoenix quill pen??.", ln++, stage > 7)
        }
        val hasStatuette = TheGolemListeners.hasStatuette(player)
        val doorOpen = getAttribute(player, "the-golem:door-open", false)
        if (hasStatuette) {
            line(player, "I've acquired a statuette that fits a !!mechanism?? in the !!ruins??", ln++, doorOpen)
            line(player, " of !!Uzer?? from the !!Varrock museum??.", ln++, doorOpen)
        }
        val seenDemon = getAttribute(player, "the-golem:seen-demon", false)
        if (doorOpen) {
            line(player, "I've opened the portal in the !!ruins of Uzer??.", ln++, seenDemon)
        }
        if (seenDemon) {
            line(player, "It turns out that !!the demon?? is !!already dead??!", ln++, stage > 4)
            line(player, "I should tell the golem the good news.", ln++, stage > 4)
        }
        if (stage > 4) {
            line(player, "The demon doesn't think its task is complete.", ln++, stage > 7)
        }
        if (stage >= 100) {
            ln++
            line(player, "<col=FF0000>QUEST COMPLETE!</col>", ln, false)
        }
    }

    override fun hasRequirements(player: Player?): Boolean {
        return getStatLevel(player!!, Skills.CRAFTING) >= 20 && getStatLevel(player, Skills.THIEVING) >= 25
    }

    override fun finish(player: Player?) {
        super.finish(player)
        player ?: return
        var ln = 10
        player.packetDispatch.sendItemZoomOnInterface(Items.STATUETTE_4618, 230, 277, 5)
        drawReward(player, "1 quest point", ln++)
        drawReward(player, "1,000 Crafting XP", ln++)
        drawReward(player, "1,000 Theiving XP", ln)
        rewardXP(player, Skills.CRAFTING, 3000.0)
        rewardXP(player, Skills.THIEVING, 2000.0)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

    override fun updateVarps(player: Player) {
        TheGolemListeners.updateVarps(player)
    }
}

@Initializable
class ClayGolemNPC : AbstractNPC {
    constructor() : super(NPCs.BROKEN_CLAY_GOLEM_1908, null, true)
    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any?): AbstractNPC {
        return ClayGolemNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(1907, NPCs.BROKEN_CLAY_GOLEM_1908, NPCs.DAMAGED_CLAY_GOLEM_1909, NPCs.CLAY_GOLEM_1910)
    }
}

class LetterListener : InterfaceListener {
    override fun defineInterfaceListeners() {
        onOpen(220) { player, _ ->
            val lines: Array<String> = player.getAttribute("ifaces:220:lines", arrayOf())
            for (i in 0 until Math.min(lines.size, 15)) {
                setInterfaceText(player, lines[i], 220, i + 1)
                //setInterfaceText(player, "${i}", 220, i+1)
            }
            return@onOpen true
        }
    }
}

class DisplayCaseListener : InterfaceListener {
    override fun defineInterfaceListeners() {
        onOpen(534) { player, _ ->
            //player.packetDispatch.sendModelOnInterface(25576, 534, 5, 650)
            val model = player.getAttribute("ifaces:534:model", 0)
            player.packetDispatch.sendModelOnInterface(model, 534, 4, 461)
            //player.packetDispatch.sendModelOnInterface(25576, 534, 12, 973)
            setInterfaceText(player, DISPLAY_CASE_TEXT.joinToString("<br>"), 534, 2)
            setInterfaceText(player, "30", 534, 85)
            return@onOpen true
        }
    }
}

class TheGolemListeners : InteractionListener {
    fun repairGolem(player: Player): Boolean {
        if (player.questRepository.getStage("The Golem") == 1) {
            var clayUsed = player.getAttribute("the-golem:clay-used", 0)
            val msg = when (clayUsed) {
                0 -> "You apply some clay to the golem's wounds. The clay begins to harden in the hot sun."
                1 -> "You fix the golem's legs."
                2 -> "The golem is nearly whole."
                3 -> "You repair the golem with a final piece of clay."
                else -> null
            }
            if (removeItem(player, Item(Items.SOFT_CLAY_1761, 1))) {
                playGlobalAudio(player.location, Sounds.GOLEM_REPAIRCLAY_1850)
                if (msg != null) {
                    sendItemDialogue(player, Items.SOFT_CLAY_1761, msg)
                }
                clayUsed = Math.min(clayUsed + 1, 4)
                setAttribute(player, "/save:the-golem:clay-used", clayUsed)
                updateVarps(player)
                if (clayUsed == 4) {
                    setQuestStage(player, "The Golem", 2)
                }
            }
        }
        return true
    }

    fun takeThroneGems(player: Player) {
        if (player.getAttribute("the-golem:gems-taken", false)) {
            return
        }
        if (!inInventory(player, Items.HAMMER_2347) || !inInventory(player, Items.CHISEL_1755)) {
            sendMessage(player, "You need a hammer and chisel to remove the gems.")
            return
        }
        if (freeSlots(player) < 6) {
            sendMessage(player, "You don't have enough free space to remove all six gems.")
            return
        }
        setAttribute(player, "/save:the-golem:gems-taken", true)
        updateVarps(player)
        for (item in arrayOf(Items.SAPPHIRE_1607, Items.EMERALD_1605, Items.RUBY_1603)) {
            player.inventory.add(Item(item, 2))
        }
        sendItemDialogue(player, Items.RUBY_1603, "You prize the gems from the demon's throne.")
    }

    companion object {
        @JvmStatic
        fun hasStatuette(player: Player): Boolean {
            return player.inventory.containsAtLeastOneItem(4618) || player.bank.containsAtLeastOneItem(4618) || player.getAttribute(
                "the-golem:placed-statuette",
                false
            )
        }

        @JvmStatic
        fun initializeStatuettes(player: Player) {
            if (!player.getAttribute("the-golem:statuette-rotation:initialized", false)) {
                for (i in 0 until 4) {
                    setAttribute(player, "/save:the-golem:statuette-rotation:${i}", RandomFunction.random(2))
                }
                setAttribute(player, "/save:the-golem:statuette-rotation:initialized", true)
            }
        }

        @JvmStatic
        fun updateVarps(player: Player) {
            val clayUsed = player.getAttribute("the-golem:clay-used", 0)
            val gemsTaken = if (player.getAttribute("the-golem:gems-taken", false)) {
                1
            } else {
                0
            }
            val statuetteTaken = if (hasStatuette(player)) {
                1
            } else {
                0
            }
            val statuettePlaced = if (player.getAttribute("the-golem:placed-statuette", false)) {
                1
            } else {
                0
            }
            initializeStatuettes(player)
            val rotation0 = player.getAttribute("the-golem:statuette-rotation:0", 0)
            val rotation1 = player.getAttribute("the-golem:statuette-rotation:1", 0)
            val rotation2 = player.getAttribute("the-golem:statuette-rotation:2", 0)
            val rotation3 = player.getAttribute("the-golem:statuette-rotation:3", 0)
            val doorOpen = player.getAttribute("the-golem:door-open", false)
            var clientStage = 0
            if (player.questRepository.getStage("The Golem") > 0) {
                clientStage = Math.max(clientStage, 1)
            }
            if (doorOpen) {
                clientStage = Math.max(clientStage, 5)
            }
            if (player.questRepository.getStage("The Golem") >= 100) {
                clientStage = Math.max(clientStage, 10)
            }
            setVarbit(player, 346, clientStage)
            setVarbit(player, 348, clayUsed)
            setVarbit(player, 354, gemsTaken)
            setVarbit(player, 355, statuetteTaken)
            setVarbit(player, 349, rotation0)
            setVarbit(player, 350, rotation1)
            setVarbit(player, 351, rotation2)
            setVarbit(player, 352, statuettePlaced * (rotation3 + 1))
        }
    }

    fun pickpocketCurator(player: Player, node: Node): Boolean {
        if (player.inventory.containsAtLeastOneItem(4617) || player.bank.containsAtLeastOneItem(4617)) {
            sendMessage(player, "You have no reason to do that.")
            return true
        }
        sendItemDialogue(player, 4617, "You steal a tiny key.")
        addItemOrDrop(player, 4617, 1)
        return true
    }

    fun displayCase(player: Player, node: Scenery): Boolean {
        val model = node.definition.modelIds[0]
        setAttribute(player, "ifaces:534:model", model)
        openInterface(player, 534)
        return true
    }

    fun openDisplayCase(player: Player, node: Node): Boolean {
        if (!player.inventory.containsAtLeastOneItem(4617)) {
            sendMessage(player, "You can't open the display case without the key.")
            return true
        }
        if (hasStatuette(player)) {
            return true
        }
        addItemOrDrop(player, 4618, 1)
        updateVarps(player)
        return true
    }

    fun placeStatuette(player: Player): Boolean {

        if (player.inventory.remove(Item(4618))) {
            sendMessage(player, "You insert the statuette into the alcove.")
            setAttribute(player, "/save:the-golem:placed-statuette", true)
            updateVarps(player)
        }
        return true
    }

    fun turnStatuette(player: Player, node: Scenery): Boolean {
        playGlobalAudio(player.location, Sounds.TURN_STATUE_1852)
        if (player.getAttribute("the-golem:door-open", false)) {
            sendMessage(player, "You've already opened the door.")
            return true
        }
        val index = when (node.wrapper.id) {
            6303 -> 0
            6304 -> 1
            6305 -> 2
            6306 -> 3
            else -> return true
        }
        initializeStatuettes(player)
        val rotation = 1 - player.getAttribute("the-golem:statuette-rotation:${index}", 0)
        val dir = if (rotation == 0) {
            "right"
        } else {
            "left"
        }
        sendMessage(player, "You turn the statuette to the ${dir}.")
        setAttribute(player, "the-golem:statuette-rotation:${index}", rotation)
        checkDoor(player)
        updateVarps(player)
        return true
    }

    fun checkDoor(player: Player) {
        if (!player.getAttribute("the-golem:door-open", false)) {
            val rotation0 = player.getAttribute("the-golem:statuette-rotation:0", 0)
            val rotation1 = player.getAttribute("the-golem:statuette-rotation:1", 0)
            val rotation2 = player.getAttribute("the-golem:statuette-rotation:2", 0)
            val rotation3 = player.getAttribute("the-golem:statuette-rotation:3", 0)
            val placed = player.getAttribute("the-golem:placed-statuette", false)
            if (rotation0 == 1 && rotation1 == 1 && rotation2 == 0 && rotation3 == 0 && placed) {
                sendMessage(player, "The door grinds open.")
                setAttribute(player, "/save:the-golem:door-open", true)
            }
        }
    }

    fun mortarOnMushroom(player: Player): Boolean {
        if (!player.inventory.containsAtLeastOneItem(Items.VIAL_229)) {
            sendMessage(player, "You need a vial to do that.")
            return true
        }
        if (player.inventory.remove(Item(Items.BLACK_MUSHROOM_4620, 1)) && player.inventory.remove(Item(Items.VIAL_229, 1))) {
            sendItemDialogue(
                player,
                Items.BLACK_MUSHROOM_INK_4622,
                "You crush the mushroom and pour the juice into a vial."
            )
            player.inventory.add(Item(Items.BLACK_MUSHROOM_INK_4622, 1))
        }
        return true
    }

    fun featherOnInk(player: Player): Boolean {
        if (player.inventory.remove(Item(Items.PHOENIX_FEATHER_4621, 1))) {
            sendItemDialogue(player, Items.PHOENIX_QUILL_PEN_4623, "You dip the phoenix feather into the ink.")
            player.inventory.add(Item(Items.PHOENIX_QUILL_PEN_4623, 1))
        }
        return true
    }

    fun penOnPapyrus(player: Player): Boolean {
        if (!player.getAttribute("the-golem:varmen-notes-read", false)) {
            sendMessage(player, "You don't know what to write.")
            return true
        }
        if (player.inventory.remove(Item(Items.PAPYRUS_970, 1))) {
            sendItemDialogue(player, Items.GOLEM_PROGRAM_4624, "You write on the papyrus:<br>YOUR TASK IS DONE")
            player.inventory.add(Item(Items.GOLEM_PROGRAM_4624, 1))
        }
        return true
    }

    fun implementOnGolem(player: Player): Boolean {
        if (!player.getAttribute("the-golem:varmen-notes-read", false)) {
            sendMessage(player, "You don't know what that would do.")
            return true
        }
        if (player.questRepository.getStage("The Golem") == 7) {
            sendMessage(player, "You insert the key and the golem's skull hinges open.")
            setQuestStage(player, "The Golem", 8)
        }
        return true
    }

    fun programOnGolem(player: Player, used: Node, with: Node): Boolean {
        playGlobalAudio(player.location, Sounds.GOLEM_PROGRAM_1849)
        player.dialogueInterpreter.open(ClayGolemProgramDialogueFile(), with)
        return true
    }


    override fun defineDestinationOverrides() {
        addClimbDest(Location.create(3492, 3089, 0), Location.create(2722, 4886, 0))
        addClimbDest(Location.create(2721, 4884, 0), Location.create(3491, 3090, 0))
        setDest(IntType.SCENERY, intArrayOf(34978), "climb-down") { _, _ ->
            return@setDest Location.create(3491, 3090, 0)
        }
        setDest(IntType.SCENERY, intArrayOf(6372), "climb-up") { _, _ -> return@setDest Location.create(2722, 4886, 0) }
    }

    override fun defineListeners() {
        onUseWith(IntType.NPC, Items.SOFT_CLAY_1761, 1907) { player, _, _ -> return@onUseWith repairGolem(player) }
        on(core.api.consts.Scenery.STAIRCASE_34978, IntType.SCENERY, "climb-down") { player, node ->
            ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_DOWN, SpecialLadders.getDestination(node.location)); return@on true
        }
        on(core.api.consts.Scenery.STAIRCASE_6372, IntType.SCENERY, "climb-up") { player, node ->
           ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, SpecialLadders.getDestination(node.location)); return@on true
        }
        on(Items.LETTER_4615, IntType.ITEM, "read") { player, _ ->
            setAttribute(player, "ifaces:220:lines", LETTER_LINES)
            setAttribute(player, "/save:the-golem:read-elissa-letter", true)
            openInterface(player, 220)
            return@on true
        }
        on(core.api.consts.Scenery.BOOKCASE_35226, IntType.SCENERY, "search") { player, _ ->
            player.packetDispatch.sendMessage("You search the bookcase.")
            val readLetter = player.getAttribute("the-golem:read-elissa-letter", false)
            if (!player.inventory.containsAtLeastOneItem(4616) && !player.bank.containsAtLeastOneItem(4616) && readLetter) {
                sendItemDialogue(player, 4616, "You find Varmen's expedition notes.")
                addItemOrDrop(player, 4616, 1)
            } else {
                player.packetDispatch.sendMessage("You find nothing of interest.")
            }
            return@on true
        }
        on(core.api.consts.Scenery.DOOR_6363, IntType.SCENERY, "open") { player, _ ->
            sendMessage(
                player,
                "The door doesn't open."
            ); return@on true
        }
        on(core.api.consts.Scenery.DOOR_6364, IntType.SCENERY, "enter") { player, _ ->
            sendMessage(player, "You step into the portal.")
            if (!player.getAttribute("the-golem:seen-demon", false)) {
                sendMessage(player, "The room is dominated by a colossal horned skeleton!")
                setAttribute(player, "/save:the-golem:seen-demon", true)
                setQuestStage(player, "The Golem", 4)
            }
            playGlobalAudio(player.location, Sounds.GOLEM_DEMONDOOR_1848)
            teleport(player, Location.create(3552, 4948, 0))
            return@on true
        }
        on(core.api.consts.Scenery.PORTAL_6282, IntType.SCENERY, "enter") { player, _ ->
            sendMessage(player, "You step into the portal.")
            playGlobalAudio(player.location, Sounds.GOLEM_TELEPORT_1851)
            teleport(player, Location.create(2722, 4911, 0))
            return@on true
        }

        onUseWith(IntType.SCENERY, Items.HAMMER_2347, 6301) { player, _, _ -> takeThroneGems(player); return@onUseWith true }
        onUseWith(IntType.SCENERY, Items.CHISEL_1755, 6301) { player, _, _ -> takeThroneGems(player); return@onUseWith true }
        on(646, IntType.NPC, "pickpocket") { player, node -> return@on pickpocketCurator(player, node) }
        on(intArrayOf(core.api.consts.Scenery.DISPLAY_CASE_24627, core.api.consts.Scenery.DISPLAY_CASE_24550), IntType.SCENERY, "study") { player, node ->
            return@on displayCase(player, node as Scenery)
        }
        on(24627, IntType.SCENERY, "open") { player, node -> return@on openDisplayCase(player, node) }
        onUseWith(IntType.SCENERY, 4618, 6309) { player, _, _ -> return@onUseWith placeStatuette(player) }
        on(intArrayOf(6307, 6308), IntType.SCENERY, "turn") { player, node -> turnStatuette(player, node as Scenery) }
        onUseWith(IntType.ITEM, 233, 4620) { player, _, _ -> return@onUseWith mortarOnMushroom(player) }
        onUseWith(IntType.ITEM, 4621, 4622) { player, _, _ -> return@onUseWith featherOnInk(player) }
        onUseWith(IntType.ITEM, 970, 4623) { player, _, _ -> return@onUseWith penOnPapyrus(player) }
        onUseWith(IntType.NPC, 4619, 1907) { player, _, _ -> return@onUseWith implementOnGolem(player) }
        onUseWith(IntType.NPC, 4624, 1907) { player, used, with -> return@onUseWith programOnGolem(player, used, with) }
        on(NPCs.DESERT_PHOENIX_1911, IntType.NPC, "grab-feather") { player, _ ->
            if (getAttribute(player,"the-golem:varmen-notes-read", false)) {
                lock(player, 1000)
                GameWorld.Pulser.submit(object : Pulse() {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            0 -> {
                                sendMessage(player, "You attempt to grab the pheonix's tail-feather.")
                                animate(player, Animation(881))
                            }

                            3 -> {
                                sendMessage(player, "You grab a tail-feather.")
                                player.inventory.add(Item(Items.PHOENIX_FEATHER_4621))
                                unlock(player)
                                return true
                            }
                        }
                        return false
                    }
                })
            } else {
                sendMessage(player, "You have no reason to take the phoenix's feathers.")
            }
            return@on true
        }
    }

}
