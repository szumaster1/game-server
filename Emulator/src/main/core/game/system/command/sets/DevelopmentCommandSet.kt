package core.game.system.command.sets

import org.rs.consts.Items
import content.global.activity.jobs.JobManager
import content.global.skill.support.construction.HouseLocation
import content.minigame.tbwcleanup.changeSpawnChance
import core.api.*
import core.cache.Cache
import core.cache.def.impl.DataMap
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.Struct
import core.cache.def.impl.VarbitDefinition
import core.game.activity.ActivityManager
import core.game.node.entity.combat.ImpactHandler.HitsplatType
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item
import core.game.system.command.Privilege
import core.game.world.map.Location
import core.net.packet.PacketWriteQueue
import core.net.packet.context.PlayerContext
import core.net.packet.outgoing.ResetInterface
import core.plugin.Initializable
import core.tools.Log
import core.tools.RED
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 * Development command set.
 */
@Initializable
class DevelopmentCommandSet : CommandSet(Privilege.ADMIN) {

    private val farmKitItems = arrayListOf(Items.RAKE_5341, Items.SPADE_952, Items.SEED_DIBBER_5343, Items.WATERING_CAN8_5340, Items.SECATEURS_5329, Items.GARDENING_TROWEL_5325)
    private val runeKitItems = arrayListOf(Items.AIR_RUNE_556, Items.EARTH_RUNE_557, Items.FIRE_RUNE_554, Items.WATER_RUNE_555, Items.MIND_RUNE_558, Items.BODY_RUNE_559, Items.DEATH_RUNE_560, Items.NATURE_RUNE_561, Items.CHAOS_RUNE_562, Items.LAW_RUNE_563, Items.COSMIC_RUNE_564, Items.BLOOD_RUNE_565, Items.SOUL_RUNE_566, Items.ASTRAL_RUNE_9075)
    private val talismanKitItems = arrayListOf(Items.AIR_TALISMAN_1438, Items.EARTH_TALISMAN_1440, Items.FIRE_TALISMAN_1442, Items.WATER_TALISMAN_1444, Items.MIND_TALISMAN_1448, Items.BODY_TALISMAN_1446, Items.DEATH_TALISMAN_1456, Items.NATURE_TALISMAN_1462, Items.CHAOS_TALISMAN_1452, Items.LAW_TALISMAN_1458, Items.COSMIC_TALISMAN_1454, Items.BLOOD_TALISMAN_1450, Items.SOUL_TALISMAN_1460)
    override fun defineCommands() {
        /*
         * Provides a command that allows you to buy a house.
         */

        define(
            name = "buyhouse",
            privilege = Privilege.ADMIN,
            usage = "::buyhouse",
            description = "Allows you to buy house."
        ) { player, _ ->
            player.houseManager.createNewHouseAt(HouseLocation.RIMMINGTON)
            sendMessage(player, RED + "The house has been bought.")
        }

        /*
         * Provides a set of talisman items.
         */
        define(
            name = "talismans",
            privilege = Privilege.ADMIN,
            usage = "::talismans",
            description = "Provides a set of talisman items"
        ) { player, _ ->
            for (item in talismanKitItems) {
                player.inventory.add(Item(item))
            }
        }

        /*
         * Provides a farming equipment.
         */

        define(
            name = "farmkit",
            privilege = Privilege.ADMIN,
            usage = "::farmkit",
            description = "Provides a kit of various farming equipment."
        ) { player, _ ->
            for (item in farmKitItems) {
                player.inventory.add(Item(item))
            }
        }

        /*
         * Allows you to call arbitrary cs2 scripts during runtime.
         */

        define(
            name = "cs2",
            privilege = Privilege.ADMIN,
            usage = "::cs2 id args",
            description = "Allows you to call arbitrary cs2 scripts during runtime"
        ) { player, args ->
            var scriptArgs = ArrayList<Any>()
            if (args.size == 2) {
                runcs2(player, args[1].toIntOrNull() ?: return@define)
                return@define
            } else if (args.size > 2) {
                for (i in 2 until args.size) {
                    scriptArgs.add(args[i].toIntOrNull() ?: args[i])
                }
                runcs2(
                    player,
                    args[1].toIntOrNull() ?: return@define,
                    *(scriptArgs.toTypedArray().also { player.debug(it.contentToString()) })
                )
            }
        }

        /*
         * Clear all finished achievements diary.
         */

        define(
            name = "cleardiary",
            privilege = Privilege.ADMIN,
            usage = "::cleardiary",
            description = "Clear all the achievements."
        ) { player, _ ->
            for (type in DiaryType.values()) {
                val diary = player.achievementDiaryManager.getDiary(type)
                if (diary != null) {
                    for (level in 0 until diary.levelStarted.size) {
                        for (task in 0 until diary.taskCompleted[level].size) {
                            diary.resetTask(player, level, task)
                        }
                    }
                }
            }
            sendMessage(player, "All achievement diaries cleared successfully.")
        }

        /*
         * Clear work-for task.
         */

        define(
            name = "clearjob",
            privilege = Privilege.ADMIN,
            usage = "::clearjob",
            description = "Clear the actually job."
        ) { player, _ ->
            val playerJobManager = JobManager.getInstance(player)
            playerJobManager.job = null
            playerJobManager.jobAmount = -1
            playerJobManager.jobOriginalAmount = -1

            sendMessage(player, "Job cleared successfully.")
        }

        /*
         * Prints current Region ID.
         */

        define(
            name = "region",
            privilege = Privilege.STANDARD,
            usage = "::region",
            description = "Prints your current Region ID."
        ) { player, _ ->
            sendMessage(player, "Region ID: ${player.viewport.region.regionId}")
        }

        /*
         * Starts the Dragon slayer cutscene.
         */

        define(
            name = "dscut",
            privilege = Privilege.ADMIN,
            usage = "::dscut",
            description = "Starts the Dragon slayer cutscene."
        ) { player, _ ->
            setQuestStage(player, "Dragon Slayer", 30)
            ActivityManager.start(player, "Dragon Slayer", false, player.location);
        }

        /*
         * Swaps your spellbook to the given book ID.
         */

        define(
            name = "spellbook",
            privilege = Privilege.ADMIN,
            usage = "::spellbook <lt>book ID<gt> (0 = MODERN, 1 = ANCIENTS, 2 = LUNARS)",
            description = "Swaps your spellbook to the given book ID."
        ) { player, args ->
            if (args.size < 2) {
                reject(player, "Usage: ::spellbook [int]. 0 = MODERN, 1 = ANCIENTS, 2 = LUNARS")
            }
            val spellBook = SpellBookManager.SpellBook.values()[args[1].toInt()]
            player.spellBookManager.setSpellBook(spellBook)
            player.spellBookManager.update(player)
        }

        /*
         * Kill character.
         */

        define(
            name = "killme",
            privilege = Privilege.ADMIN,
            usage = "::killme",
            description = "Does exactly what it says on the tin."
        ) { player, _ ->
            player.impactHandler.manualHit(player, player.skills.lifepoints, HitsplatType.NORMAL)
        }

        define(name = "struct") { _, args ->
            val mapId = args[1].toIntOrNull() ?: return@define

            val def = Struct.get(mapId)
            log(this::class.java, Log.FINE, def.toString())
        }

        define(name = "datamap") { _, args ->
            val mapId = args[1].toIntOrNull() ?: return@define

            val def = DataMap.get(mapId)
            log(this::class.java, Log.FINE, def.toString())
        }

        /*
         * Dumps all the cache structs to structs.txt
         */

        define(
            name = "dumpstructs",
            privilege = Privilege.ADMIN,
            usage = "::dumpstructs",
            description = "Dumps all the cache structs to structs.txt"
        ) { _, _ ->
            val dump = File("structs.txt")
            val writer = BufferedWriter(FileWriter(dump))
            val index = Cache.getIndexes()[2]
            val containers = index.information.containers[26].filesIndexes
            for (fID in containers) {
                val file = index.getFileData(26, fID)
                if (file != null) {
                    val def = Struct.parse(fID, file)
                    if (def.dataStore.isEmpty()) continue //no data in struct.
                    writer.write(def.toString())
                    writer.newLine()
                }
            }
            writer.flush()
            writer.close()
        }

        /*
         * Dumps all the cache data maps to datamaps.txt
         */

        define(
            name = "dumpdatamaps",
            privilege = Privilege.ADMIN,
            usage = "::dumpdatamaps",
            description = "Dumps all the cache data maps to datamaps.txt"
        ) { _, _ ->
            val index = Cache.getIndexes()[17]
            val containers = index.information.containersIndexes

            val dump = File("datamaps.txt")
            val writer = BufferedWriter(FileWriter(dump))

            for (cID in containers) {
                val fileIndexes = index.information.containers[cID].filesIndexes
                for (fID in fileIndexes) {
                    val file = index.getFileData(cID, fID)
                    if (file != null) {
                        val def = DataMap.parse((cID shl 8) or fID, file)
                        if (def.keyType == '?') continue //Empty definition - only a 0 present in the cachefile data.
                        writer.write(def.toString())
                        writer.newLine()
                    }
                }
            }
            writer.flush()
            writer.close()
        }

        /*
         * Rolls the given NPC drop table AMOUNT times.
         */

        define(
            name = "rolldrops",
            privilege = Privilege.ADMIN,
            usage = "::rolldrops <lt>NPC ID<gt> <lt>AMOUNT<gt>",
            description = "Rolls the given NPC drop table AMOUNT times."
        ) { player: Player, args: Array<String> ->
            if (args.size < 2) {
                reject(player, "Usage: ::rolldrops npcid amount")
            }

            val container = player.dropLog
            val npcId = args[1].toInt()
            val amount = args[2].toInt()

            container.clear()
            val drops = NPCDefinition.forId(npcId).dropTables.table.roll(player, amount)
            for (drop in drops) container.add(drop, false)
            container.open(player)
        }

        /*
         * Lists all the varbits assigned to the given varp.
         */

        define(
            name = "varbits",
            privilege = Privilege.ADMIN,
            usage = "::varbits <lt>Varp ID<gt>",
            description = "Lists all the varbits assigned to the given varp."
        ) { player, args ->
            if (args.size < 2)
                reject(player, "Usage: ::varbits varpIndex")

            val varp = args[1].toIntOrNull() ?: reject(player, "Please use a valid int for the varpIndex.")
            GlobalScope.launch {
                sendMessage(player, "========== Found Varbits for Varp $varp ==========")
                for (id in 0 until 10000) {
                    val def = VarbitDefinition.forId(id)
                    if (def.varpId == varp) {
                        sendMessage(player, "${def.id} -> [offset: ${def.startBit}, upperBound: ${def.endBit}]")
                    }
                }
                sendMessage(player, "=========================================")
            }
        }

        /*
         * Provides test packet.
         */

        define(name = "testpacket") { player, _ ->
            PacketWriteQueue.write(ResetInterface(), PlayerContext(player))
        }

        /*
         * Provides a command to search the npc.
         */

        define(
            name = "npcsearch",
            privilege = Privilege.STANDARD,
            usage = "npcsearch name",
            description = "Searches for NPCs that match the name either in main or children."
        ) { player, strings ->
            val name = strings.slice(1 until strings.size).joinToString(" ").lowercase()
            for (id in 0 until 9000) {
                val def = NPCDefinition.forId(id)
                if (def.name.isNotBlank() && (def.name.lowercase()
                        .contains(name) || name.contains(def.name.lowercase()))
                ) {
                    notify(player, "$id - ${def.name}")
                } else {
                    for ((childId, index) in def.childNPCIds?.withIndex() ?: continue) {
                        val childDef = NPCDefinition.forId(childId)
                        if (childDef.name.lowercase().contains(name) || name.contains(childDef.name.lowercase())) {
                            notify(player, "$childId child($id) index $index - ${childDef.name}")
                        }
                    }
                }
            }
        }

        /*
         * Command to search for items by name.
         */

        define(name = "itemsearch") { player, args ->
            val itemName = args.copyOfRange(1, args.size).joinToString(" ").lowercase()
            for (i in 0 until 15000) {
                val name = getItemName(i).lowercase()
                if (name.contains(itemName) || itemName.contains(name))
                    notify(player, "$i: $name")
            }
        }

        /*
         * Command to give the player 1000 of each Rune type.
         */

        define(
            name = "runekit",
            privilege = Privilege.ADMIN,
            usage = "",
            description = "Gives 1k of each Rune type"
        ) { player, _ ->
            for (item in runeKitItems) {
                addItem(player, item, 1000)
            }
        }

        /*
         * Command to toggle the drawing of the chunk border.
         */

        define(
            name = "drawchunks",
            privilege = Privilege.ADMIN,
            usage = "",
            description = "Draws the border of the chunk you're standing in"
        ) { player, _ ->
            setAttribute(player, "chunkdraw", !getAttribute(player, "chunkdraw", false))
        }

        /*
         * Command to toggle the drawing of clipping flags.
         */

        define(
            name = "drawclipping",
            privilege = Privilege.ADMIN,
            usage = "",
            description = "Draws the clipping flags of the region you're standing in"
        ) { player, _ ->
            setAttribute(player, "clippingdraw", !getAttribute(player, "clippingdraw", false))
        }

        /*
         * Command to toggle the drawing of the region border.
         */

        define(
            name = "drawregions",
            privilege = Privilege.ADMIN,
            usage = "",
            description = "Draws the border of the region you're standing in"
        ) { player, _ ->
            setAttribute(player, "regiondraw", !getAttribute(player, "regiondraw", false))
        }

        /*
         * Command to visualize the player's path.
         */

        define(
            name = "drawroute",
            privilege = Privilege.ADMIN,
            usage = "",
            description = "Visualizes the path your player is taking"
        ) { player, _ ->
            setAttribute(player, "routedraw", !getAttribute(player, "routedraw", false))
        }

        /*
         * Command to set the starting location for a movement feature.
         */

        define(
            name = "fmstart",
            privilege = Privilege.ADMIN,
            usage = "",
            description = ""
        ) { player, _ ->
            setAttribute(player, "fmstart", Location.create(player.location))
        }

        /*
         * Command to set the ending location for a movement feature.
         */

        define(
            name = "fmend",
            privilege = Privilege.ADMIN,
            usage = "",
            description = ""
        ) { player, _ ->
            setAttribute(player, "fmend", Location.create(player.location))
        }

        /*
         * Command to set the speed for a movement feature.
         */

        define(
            name = "fmspeed",
            privilege = Privilege.ADMIN,
            usage = "",
            description = ""
        ) { player, args ->
            setAttribute(player, "fmspeed", args[1].toIntOrNull() ?: 10)
        }

        /*
         * Command to set the ending speed for a movement feature.
         */

        define(
            name = "fmspeedend",
            privilege = Privilege.ADMIN,
            usage = "",
            description = ""
        ) { player, args ->
            setAttribute(player, "fmspeedend", args[1].toIntOrNull() ?: 10)
        }

        /*
         * Command to test the movement feature with specified parameters.
         */

        define(
            name = "testfm",
            privilege = Privilege.ADMIN,
            usage = "",
            description = ""
        ) { player, _ ->
            val start = getAttribute(player, "fmstart", Location.create(player.location))
            val end = getAttribute(player, "fmend", Location.create(player.location))
            val speed = getAttribute(player, "fmspeed", 10)
            val speedEnd = getAttribute(player, "fmspeedend", 10)
            val ani = getAttribute(player, "fmanim", -1)
            forceMove(player, start, end, speed, speedEnd, anim = ani)
        }

        /*
         * Command to set the animation for the movement feature.
         */

        define(
            name = "fmanim",
            privilege = Privilege.ADMIN,
            usage = "",
            description = ""
        ) { player, args ->
            setAttribute(player, "fmanim", args[1].toIntOrNull() ?: -1)
        }

        /*
         * Command to visualize the predicted intersection point with an NPC.
         */

        define(
            name = "drawintersect",
            privilege = Privilege.ADMIN,
            usage = "",
            description = "Visualizes the predicted intersection point with an NPC"
        ) { player, _ ->
            setAttribute(player, "draw-intersect", !getAttribute(player, "draw-intersect", false))
        }

        /*
         * Command to visualize chathead animations from a specified ID.
         */

        define(
            name = "expression",
            privilege = Privilege.ADMIN,
            usage = "::expression id",
            description = "Visualizes chathead animations from ID."
        ) { player, args ->
            if (args.size != 2)
                reject(player, "Usage: ::expression id")
            val id = args[1].toIntOrNull() ?: 9804
            player.dialogueInterpreter.sendDialogues(player, id, "Expression ID: $id")
        }

        /*
         * Command to print out active timers for the player.
         */

        define(
            name = "timers",
            privilege = Privilege.ADMIN,
            usage = "::timers",
            description = "Print out timers"
        ) { player, _ ->
            player.sendMessage("Active timers:")
            for (timer in player.timers.activeTimers) {
                player.sendMessage("  ${timer.identifier} ${timer.nextExecution}")
            }
            player.sendMessage("New timers:")
            for (timer in player.timers.newTimers) {
                player.sendMessage("  ${timer.identifier}")
            }
        }

        /*
         * Command to open a specified overlay for the player.
         */

        define(
            name = "overlay",
            privilege = Privilege.ADMIN,
            usage = "::overlay <lt>Overlay ID<gt>"
        ) { player, args ->
            val overlayInt = args[1].toInt()
            openOverlay(player, overlayInt)
        }

        /*
         * Command to open a specified interface for the player.
         */

        define(
            name = "interface",
            privilege = Privilege.ADMIN,
            usage = "::interface <lt>Interface ID<gt>"
        ) { player, args ->
            val interfaceInt = args[1].toInt()
            openInterface(player, interfaceInt)
        }

        /*
         * Command to set the Tai Bwo Wannai cleanup points.
         */

        define(
            name = "set_tbwfp",
            privilege = Privilege.ADMIN,
            usage = "::set_tbwfp <lt>Points<gt>",
            description = "Changes your TBW cleanup points to the input number (1000=100%)."
        ) { player: Player, args: Array<String> ->
            if (args.size != 2) {
                reject(player, "Usage: ::set_tbwfp favourPercentage")
            }

            val favourPoints = args[1].toInt()
            player.setAttribute("/save:tbwcleanup", favourPoints)
            sendMessage(player, "You now have ${favourPoints.toDouble() / 10}% Tai Bwo Wannai Favour.")
        }

        /*
         * Command to get the current Tai Bwo Wannai cleanup points.
         */

        define(
            name = "get_tbwfp",
            privilege = Privilege.ADMIN,
            usage = "::get_tbwfp",
            description = "Prints your current TBW cleanup points."
        ) { player: Player, _: Array<String> ->
            sendMessage(
                player,
                "You have ${player.getAttribute("/save:tbwcleanup", 0).toDouble() / 10}% Tai Bwo Wannai Favour."
            )
        }

        /*
         * Command to change the chance of an event triggering when hacking a jungle plant.
         */

        define(
            name = "tbwceventodds",
            privilege = Privilege.ADMIN,
            usage = "::tbwceventodds <lt>Chance<gt>",
            description = "Changes the chance of an event triggering when hacking a jungle plant.<br>Chance per tick is input_value / 1000. (Realistic is around 15"
        ) { player: Player, args: Array<String> ->
            if (args.size != 2) {
                reject(player, "Usage: ::tbwceventodds chance")
            }

            val chance = args[1].toInt()
            changeSpawnChance(chance)
            sendMessage(player, "TBW cleanup events now spawn with odds ${chance}/1000 per tick for players on this server.")
        }
    }
}
