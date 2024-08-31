package core.game.bots.impl

import core.Configuration
import core.game.bots.AIRepository
import core.game.bots.CombatBotAssembler
import core.game.bots.Script
import core.game.interaction.DestinationFlag
import core.game.interaction.IntType
import core.game.interaction.InteractionListeners
import core.game.interaction.MovementPulse
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.*
import core.tools.RandomFunction
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.io.File
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

/**
 * A bot script for Adventurers who explore the world.
 *
 * @param counter used in the bots random idling function.
 * @param random is used to generate random number.
 * @param city determines the home city of the bot.
 * @param freshspawn determines if the bot has just been spawned.
 * @param random_city is the list of cities that can be randomly chosen as the home city.
 * @param tree_list is the list of trees that a bot can start cutting randomly.
 * @author Sir Kermit, Ceikry
 */
class Adventurer(val style: CombatStyle) : Script() {

    var city: Location = core.game.bots.impl.Adventurer.Companion.lumbridge
    var ticks = 0
    var freshspawn = true
    var sold = false
    private val geloc: Location = if (Random.nextBoolean()) {
        Location.create(3165, 3487, 0)
    } else {
        Location.create(3164, 3492, 0)
    }

    var counter: Int = 0
    var random: Int = (5..30).random()

    val type = when (style) {
        CombatStyle.MELEE -> CombatBotAssembler.Type.MELEE
        CombatStyle.MAGIC -> CombatBotAssembler.Type.MAGE
        CombatStyle.RANGE -> CombatBotAssembler.Type.RANGE
    }

    init {
        skills[Skills.AGILITY] == 99
        inventory.add(Item(1359))
        skills[Skills.WOODCUTTING] == 95
        inventory.add(Item(590))
        skills[Skills.FISHING] == 90
        inventory.add(Item(1271))
        skills[Skills.MINING] == 90
        skills[Skills.SLAYER] == 90
    }

    override fun toString(): String {
        return "${bot!!.name} is an Adventurer bot at ${bot!!.location}! State: $state - City: $city"
    }

    var state = core.game.bots.impl.Adventurer.State.START

    fun getRandomCity(): Location {
        return core.game.bots.impl.Adventurer.Companion.cities.random()
    }

    fun getRandomPoi(): Location {
        return core.game.bots.impl.Adventurer.Companion.pois.random()
    }

    //TODO: Optimise and adjust how bots handle picking up ground items further.
    fun immerse() {
        if (counter++ == 180) {
            state = core.game.bots.impl.Adventurer.State.TELEPORTING
        }
        val items = AIRepository.groundItems[bot]
        if (Random.nextBoolean()) {
            if (items.isNullOrEmpty()) {
                scriptAPI!!.attackNpcsInRadius(bot!!, 8)
                state = core.game.bots.impl.Adventurer.State.LOOT_DELAY
            }
            if (bot!!.inventory.isFull) {
                if (core.game.bots.impl.Adventurer.Companion.bankMap[city] == null) {
                    scriptAPI!!.teleport(getRandomCity().also { city = it })
                } else {
                    if (core.game.bots.impl.Adventurer.Companion.bankMap[city]?.insideBorder(bot) == true) {
                        state = core.game.bots.impl.Adventurer.State.FIND_BANK
                    } else {
                        scriptAPI!!.walkTo(core.game.bots.impl.Adventurer.Companion.bankMap[city]?.randomLoc ?: Location(0, 0, 0))
                    }
                }
            }

        } else {
            if (bot!!.inventory.isFull) {
                if (core.game.bots.impl.Adventurer.Companion.bankMap[city] == null) {
                    scriptAPI!!.teleport(getRandomCity().also { city = it })
                } else {
                    if (core.game.bots.impl.Adventurer.Companion.bankMap[city]?.insideBorder(bot) == true) {
                        state = core.game.bots.impl.Adventurer.State.FIND_BANK
                    } else {
                        scriptAPI!!.walkTo(core.game.bots.impl.Adventurer.Companion.bankMap[city]?.randomLoc ?: Location(0, 0, 0))
                    }
                }
            } else {
                val resources = listOf(
                    "Rocks", "Tree", "Oak", "Willow", "Maple tree", "Yew", "Magic tree", "Teak", "Mahogany"
                )
                val resource = scriptAPI!!.getNearestNodeFromList(resources, true)
                if (resource != null) {
                    if (resource.name.contains("ocks")) InteractionListeners.run(
                        resource.id, IntType.SCENERY, "mine", bot!!, resource
                    )
                    else InteractionListeners.run(resource.id, IntType.SCENERY, "chop down", bot!!, resource)
                }
            }
        }
        return
    }

    fun refresh() {
        scriptAPI!!.teleport(core.game.bots.impl.Adventurer.Companion.lumbridge)
        freshspawn = true
        state = core.game.bots.impl.Adventurer.State.START
    }

    var poi = false
    var poiloc = core.game.bots.impl.Adventurer.Companion.karamja


    override fun tick() {
        ticks++
        if (ticks++ >= 800) {
            ticks = 0
            refresh()
            return
        }


        if (ticks % 30 == 0) {
            for ((zone, resolution) in core.game.bots.impl.Adventurer.Companion.common_stuck_locations) {
                if (zone.insideBorder(bot)) {
                    resolution(this)
                    return
                }
            }
        }

        when (state) {

            core.game.bots.impl.Adventurer.State.LOOT_DELAY -> {
                bot!!.pulseManager.run(object : Pulse() {
                    var counter1 = 0
                    override fun pulse(): Boolean {
                        when (counter1++) {
                            7 -> return true.also { state = core.game.bots.impl.Adventurer.State.LOOT }
                        }
                        return false
                    }
                })
            }

            core.game.bots.impl.Adventurer.State.LOOT -> {
                val items = AIRepository.groundItems[bot]
                if (items?.isNotEmpty() == true && !bot!!.inventory.isFull) {
                    items.toTypedArray().forEach {
                        scriptAPI!!.takeNearestGroundItem(it.id)
                    }
                    return
                } else {
                    state = core.game.bots.impl.Adventurer.State.EXPLORE
                }
            }

            core.game.bots.impl.Adventurer.State.START -> {
                if (freshspawn) {
                    freshspawn = false
                    scriptAPI!!.randomWalkTo(core.game.bots.impl.Adventurer.Companion.lumbridge, 20)
                } else {
                    city = getRandomCity()
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }
            }

            core.game.bots.impl.Adventurer.State.TELEPORTING -> {
                ticks = 0
                counter = 0
                if (bot!!.location != city) {
                    poi = false
                    scriptAPI!!.teleport(city)
                } else {
                    poi = false
                    state = core.game.bots.impl.Adventurer.State.EXPLORE
                }
            }

            core.game.bots.impl.Adventurer.State.EXPLORE -> {
                if (counter++ == 350) {
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }

                val chance = if (city == core.game.bots.impl.Adventurer.Companion.ge || city == core.game.bots.impl.Adventurer.Companion.ge2) 5000 else 2500
                if (RandomFunction.random(chance) <= 10) {
                    val nearbyPlayers = RegionManager.getLocalPlayers(bot!!)
                    if (nearbyPlayers.isNotEmpty()) {
                        ticks = 0
                        dialogue()
                    }
                }

                if (RandomFunction.random(1000) <= 150 && !poi) {
                    val roamDistance = if (city != core.game.bots.impl.Adventurer.Companion.ge && city != core.game.bots.impl.Adventurer.Companion.ge2) 225 else 7
                    if ((city == core.game.bots.impl.Adventurer.Companion.ge || city == core.game.bots.impl.Adventurer.Companion.ge2) && RandomFunction.random(100) < 90) {
                        if (!bot!!.bank.isEmpty) {
                            state = core.game.bots.impl.Adventurer.State.FIND_GE
                        }
                        return
                    }
                    scriptAPI!!.randomWalkTo(city, roamDistance)
                    return
                }

                if (RandomFunction.random(1000) <= 50 && poi) {
                    val roamDistancePoi = when (poiloc) {
                        core.game.bots.impl.Adventurer.Companion.teakfarm, core.game.bots.impl.Adventurer.Companion.crawlinghands -> 5
                        core.game.bots.impl.Adventurer.Companion.treegnome -> 50
                        core.game.bots.impl.Adventurer.Companion.isafdar -> 40
                        core.game.bots.impl.Adventurer.Companion.eaglespeek -> 40
                        core.game.bots.impl.Adventurer.Companion.keldagrimout -> 30
                        core.game.bots.impl.Adventurer.Companion.teak1 -> 30
                        core.game.bots.impl.Adventurer.Companion.miningguild -> 5
                        core.game.bots.impl.Adventurer.Companion.magics, core.game.bots.impl.Adventurer.Companion.coal -> 7
                        core.game.bots.impl.Adventurer.Companion.gemrocks, core.game.bots.impl.Adventurer.Companion.chaosnpc, core.game.bots.impl.Adventurer.Companion.chaosnpc2 -> 1
                        else -> 60
                    }
                    scriptAPI!!.randomWalkTo(poiloc, roamDistancePoi)
                    return
                }

                if (RandomFunction.random(1000) <= 75) {
                    if (city != core.game.bots.impl.Adventurer.Companion.ge && city != core.game.bots.impl.Adventurer.Companion.ge2) {
                        immerse()
                        return
                    } else {
                        return
                    }
                }

                if (RandomFunction.random(20000) <= 60 && !poi) {
                    poiloc = getRandomPoi()
                    city = core.game.bots.impl.Adventurer.Companion.teak1
                    poi = true
                    scriptAPI!!.teleport(poiloc)
                    return
                }

                if ((city == core.game.bots.impl.Adventurer.Companion.ge || city == core.game.bots.impl.Adventurer.Companion.ge2) && RandomFunction.random(1000) >= 999) {
                    ticks = 0
                    city = getRandomCity()
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }

                if (city == core.game.bots.impl.Adventurer.Companion.ge || city == core.game.bots.impl.Adventurer.Companion.ge2) {
                    return
                }

                if (city == core.game.bots.impl.Adventurer.Companion.teak1 && counter++ >= 240) {
                    city = getRandomCity()
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }

                if (counter++ >= 240 && RandomFunction.random(100) >= 10) {
                    city = getRandomCity()
                    if (RandomFunction.random(100) % 2 == 0) {
                        counter = 0
                        ticks = 0
                        state = core.game.bots.impl.Adventurer.State.TELEPORTING
                    } else {
                        if (core.game.bots.impl.Adventurer.Companion.citygroupA.contains(city)) {
                            city = core.game.bots.impl.Adventurer.Companion.citygroupA.random()
                        } else {
                            city = core.game.bots.impl.Adventurer.Companion.citygroupB.random()
                        }
                        counter = 0
                        ticks = 0
                        state = core.game.bots.impl.Adventurer.State.FIND_CITY
                    }
                    counter = 0
                    return
                }
                return
            }

            core.game.bots.impl.Adventurer.State.GE -> {
                var ge = false
                if (counter++ == 180) {
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }
                if (!sold) {
                    if (counter++ >= 15) {
                        sold = true
                        ge = true
                        counter = 0
                        ticks = 0
                        scriptAPI!!.sellAllOnGeAdv()
                        return
                    }
                } else if (ge && sold) {
                    ge = false
                    city = getRandomCity()
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }
                return
            }

            core.game.bots.impl.Adventurer.State.FIND_GE -> {
                if (counter++ == 180) {
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }
                sold = false
                val ge: Scenery? = scriptAPI!!.getNearestNode("Desk", true) as Scenery?
                if (ge == null || bot!!.bank.isEmpty) state = core.game.bots.impl.Adventurer.State.EXPLORE
                class GEPulse : MovementPulse(bot!!, ge, DestinationFlag.OBJECT) {
                    override fun pulse(): Boolean {
                        bot!!.faceLocation(ge?.location)
                        state = core.game.bots.impl.Adventurer.State.GE
                        return true
                    }
                }
                if (ge != null && !bot!!.bank.isEmpty) {
                    counter = 0
                    scriptAPI!!.randomWalkTo(geloc, 3)
                    GameWorld.Pulser.submit(GEPulse())
                }
                return
            }

            core.game.bots.impl.Adventurer.State.FIND_BANK -> {
                if (counter++ == 300) {
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }
                val bank: Scenery? = scriptAPI!!.getNearestNode("Bank booth", true) as Scenery?
                if (core.game.bots.impl.Adventurer.Companion.badedge.insideBorder(bot) || bot!!.location == core.game.bots.impl.Adventurer.Companion.badedge2 || bot!!.location == core.game.bots.impl.Adventurer.Companion.badedge3 || bot!!.location == core.game.bots.impl.Adventurer.Companion.badedge4) {
                    bot!!.randomWalk(5, 5)
                }
                if (bank == null) state = core.game.bots.impl.Adventurer.State.EXPLORE
                class BankingPulse : MovementPulse(bot!!, bank, DestinationFlag.OBJECT) {
                    override fun pulse(): Boolean {
                        bot!!.faceLocation(bank?.location)
                        state = core.game.bots.impl.Adventurer.State.IDLE_BANKS
                        return true
                    }
                }
                if (bank != null) {
                    bot!!.pulseManager.run(BankingPulse())
                }
                return
            }

            core.game.bots.impl.Adventurer.State.IDLE_BANKS -> {
                if (counter++ == 300) {
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }
                if (RandomFunction.random(1000) < 100) {
                    for (item in bot!!.inventory.toArray()) {
                        item ?: continue
                        when (item.id) {
                            1359, 590, 1271, 995 -> continue
                        }
                        bot!!.bank.add(item)
                        bot!!.inventory.remove(item)
                    }
                    counter = 0
                    state = core.game.bots.impl.Adventurer.State.EXPLORE
                }
                return
            }

            core.game.bots.impl.Adventurer.State.FIND_CITY -> {
                if (counter++ >= 600 || (city == core.game.bots.impl.Adventurer.Companion.ge || city == core.game.bots.impl.Adventurer.Companion.ge2)) {
                    counter = 0
                    scriptAPI!!.teleport(getRandomCity().also { city = it })
                    state = core.game.bots.impl.Adventurer.State.EXPLORE
                }
                if (bot!!.location.equals(city)) {
                    state = core.game.bots.impl.Adventurer.State.EXPLORE
                } else {
                    scriptAPI!!.randomWalkTo(city, 5)
                }
                return
            }

            core.game.bots.impl.Adventurer.State.IDLE_CITY -> {
                if (counter++ == 300) {
                    state = core.game.bots.impl.Adventurer.State.TELEPORTING
                }
                var random = (120..300).random()
                if (counter++ == random && RandomFunction.random(1000) % 33 == 0) {
                    counter = 0
                    state = core.game.bots.impl.Adventurer.State.EXPLORE
                }
                return
            }
        }

    }

    fun dialogue() {
        val localPlayer = RegionManager.getLocalPlayers(bot!!).random()
        val until = 1225 - core.game.bots.impl.Adventurer.Companion.dateCode
        val lineStd = core.game.bots.impl.Adventurer.Companion.dialogue.getLines("standard").rand()
        var lineAlt = ""

        when {
            //Celebrates Halloween!
            core.game.bots.impl.Adventurer.Companion.dateCode == 1031 -> lineAlt = core.game.bots.impl.Adventurer.Companion.dialogue.getLines("halloween").rand()

            //Celebrates lead up to Christmas!
            until in 2..23 -> lineAlt = core.game.bots.impl.Adventurer.Companion.dialogue.getLines("approaching_christmas").rand()

            //Celebrates Christmas Day!
            core.game.bots.impl.Adventurer.Companion.dateCode == 1225 -> lineAlt = core.game.bots.impl.Adventurer.Companion.dialogue.getLines("christmas_day").rand()

            //Celebrates Christmas Eve
            core.game.bots.impl.Adventurer.Companion.dateCode == 1224 -> lineAlt = core.game.bots.impl.Adventurer.Companion.dialogue.getLines("christmas_eve").rand()

            //New years eve
            core.game.bots.impl.Adventurer.Companion.dateCode == 1231 -> lineAlt = core.game.bots.impl.Adventurer.Companion.dialogue.getLines("new_years_eve").rand()

            //New years
            core.game.bots.impl.Adventurer.Companion.dateCode == 101 -> lineAlt = core.game.bots.impl.Adventurer.Companion.dialogue.getLines("new_years").rand()

            //Valentines
            core.game.bots.impl.Adventurer.Companion.dateCode == 214 -> lineAlt = core.game.bots.impl.Adventurer.Companion.dialogue.getLines("valentines").rand()

            //Easter
            core.game.bots.impl.Adventurer.Companion.dateCode == 404 -> lineAlt = core.game.bots.impl.Adventurer.Companion.dialogue.getLines("easter").rand()
        }

        val chat = if (lineAlt.isNotEmpty() && Random.nextBoolean()) {
            lineAlt
        } else {
            lineStd
        }.replace("@name", localPlayer.username).replace("@timer", until.toString())

        scriptAPI!!.sendChat(chat)
    }

    enum class State {
        START, EXPLORE, FIND_BANK, IDLE_BANKS, FIND_CITY, IDLE_CITY, GE, TELEPORTING, LOOT, LOOT_DELAY, FIND_GE
    }


    override fun newInstance(): Script {
        val script = core.game.bots.impl.Adventurer(style)
        script.state = core.game.bots.impl.Adventurer.State.START
        val tier = CombatBotAssembler.Tier.MED
        if (type == CombatBotAssembler.Type.RANGE) script.bot =
            CombatBotAssembler().RangeAdventurer(tier, bot!!.startLocation)
        else script.bot = CombatBotAssembler().MeleeAdventurer(tier, bot!!.startLocation)
        return script
    }

    companion object {
        val badedge = ZoneBorders(3094, 3494, 3096, 3497)
        val badedge2 = Location.create(3094, 3492, 0)
        val badedge3 = Location.create(3094, 3490, 0)
        val badedge4 = Location.create(3094, 3494, 0)

        val yanille: Location = Location.create(2615, 3104, 0)
        val ardougne: Location = Location.create(2662, 3304, 0)
        val seers: Location = Location.create(2726, 3485, 0)
        val edgeville: Location = Location.create(3088, 3486, 0)
        val ge: Location = Location.create(3168, 3487, 0)
        val ge2: Location = Location.create(3161, 3493, 0)
        val catherby: Location = Location.create(2809, 3435, 0)
        val falador: Location = Location.create(2965, 3380, 0)
        val varrock: Location = Location.create(3213, 3428, 0)
        val draynor: Location = Location.create(3080, 3250, 0)
        val rimmington: Location = Location.create(2977, 3239, 0)
        val lumbridge: Location = Location.create(3222, 3219, 0)
        val karamja = Location.create(2849, 3033, 0)
        val alkharid = Location.create(3297, 3219, 0)
        val feldiphills = Location.create(2535, 2919, 0)
        val isafdar = Location.create(2241, 3217, 0)
        val eaglespeek = Location.create(2333, 3579, 0)
        val canafis = Location.create(3492, 3485, 0)
        val treegnome = Location.create(2437, 3441, 0)
        val teak1 = Location.create(2334, 3048, 0)
        val teakfarm = Location.create(2825, 3085, 0)
        val keldagrimout = Location.create(2724, 3692, 0)
        val miningguild = Location.create(3046, 9740, 0)
        val magics = Location.create(2285, 3146, 0)
        val coal = Location.create(2581, 3481, 0)
        val crawlinghands = Location.create(3422, 3548, 0)
        val gemrocks = Location.create(2825, 2997, 0)
        val chaosnpc = Location.create(2612, 9484, 0)
        val chaosnpc2 = Location.create(2580, 9501, 0)
        val taverly = Location.create(2909, 3436, 0)
        var citygroupA = listOf(
            core.game.bots.impl.Adventurer.Companion.falador,
            core.game.bots.impl.Adventurer.Companion.varrock,
            core.game.bots.impl.Adventurer.Companion.draynor,
            core.game.bots.impl.Adventurer.Companion.rimmington,
            core.game.bots.impl.Adventurer.Companion.lumbridge,
            core.game.bots.impl.Adventurer.Companion.edgeville
        )
        var citygroupB = listOf(
            core.game.bots.impl.Adventurer.Companion.yanille,
            core.game.bots.impl.Adventurer.Companion.ardougne,
            core.game.bots.impl.Adventurer.Companion.seers,
            core.game.bots.impl.Adventurer.Companion.catherby
        )

        var bankMap = mapOf<Location, ZoneBorders>(
            core.game.bots.impl.Adventurer.Companion.falador to ZoneBorders(2950, 3374, 2943, 3368),
            core.game.bots.impl.Adventurer.Companion.varrock to ZoneBorders(3182, 3435, 3189, 3446),
            core.game.bots.impl.Adventurer.Companion.draynor to ZoneBorders(3092, 3240, 3095, 3246),
            core.game.bots.impl.Adventurer.Companion.edgeville to ZoneBorders(3093, 3498, 3092, 3489),
            core.game.bots.impl.Adventurer.Companion.yanille to ZoneBorders(2610, 3089, 2613, 3095),
            core.game.bots.impl.Adventurer.Companion.ardougne to ZoneBorders(2649, 3281, 2655, 3286),
            core.game.bots.impl.Adventurer.Companion.seers to ZoneBorders(2729, 3493, 2722, 3490),
            core.game.bots.impl.Adventurer.Companion.catherby to ZoneBorders(2807, 3438, 2811, 3441)
        )

        val cities = listOf(
            core.game.bots.impl.Adventurer.Companion.yanille,
            core.game.bots.impl.Adventurer.Companion.ardougne,
            core.game.bots.impl.Adventurer.Companion.seers,
            core.game.bots.impl.Adventurer.Companion.catherby,
            core.game.bots.impl.Adventurer.Companion.falador,
            core.game.bots.impl.Adventurer.Companion.varrock,
            core.game.bots.impl.Adventurer.Companion.draynor,
            core.game.bots.impl.Adventurer.Companion.rimmington,
            core.game.bots.impl.Adventurer.Companion.lumbridge,
            core.game.bots.impl.Adventurer.Companion.ge,
            core.game.bots.impl.Adventurer.Companion.ge2,
            core.game.bots.impl.Adventurer.Companion.edgeville
        )

        val pois = listOf(
            core.game.bots.impl.Adventurer.Companion.karamja,
            core.game.bots.impl.Adventurer.Companion.karamja,
            core.game.bots.impl.Adventurer.Companion.alkharid,
            core.game.bots.impl.Adventurer.Companion.alkharid,
            core.game.bots.impl.Adventurer.Companion.feldiphills,
            core.game.bots.impl.Adventurer.Companion.feldiphills,
            core.game.bots.impl.Adventurer.Companion.isafdar,
            core.game.bots.impl.Adventurer.Companion.eaglespeek,
            core.game.bots.impl.Adventurer.Companion.eaglespeek,
            core.game.bots.impl.Adventurer.Companion.canafis,
            core.game.bots.impl.Adventurer.Companion.treegnome,
            core.game.bots.impl.Adventurer.Companion.treegnome,
            core.game.bots.impl.Adventurer.Companion.teak1,
            core.game.bots.impl.Adventurer.Companion.teakfarm,
            core.game.bots.impl.Adventurer.Companion.keldagrimout,
            core.game.bots.impl.Adventurer.Companion.miningguild,
            core.game.bots.impl.Adventurer.Companion.coal,
            core.game.bots.impl.Adventurer.Companion.crawlinghands,
            core.game.bots.impl.Adventurer.Companion.magics,
            core.game.bots.impl.Adventurer.Companion.gemrocks,
            core.game.bots.impl.Adventurer.Companion.chaosnpc,
            core.game.bots.impl.Adventurer.Companion.chaosnpc,
            core.game.bots.impl.Adventurer.Companion.chaosnpc2,
            core.game.bots.impl.Adventurer.Companion.taverly
        )

        private val whiteWolfMountainTop = Location(2850, 3496, 0)
        private val catherbyToTopOfWhiteWolf =
            arrayOf(Location(2856, 3442, 0), Location(2848, 3455, 0), Location(2848, 3471, 0), Location(2848, 3487, 0))
        private val tavleryToTopOfWhiteWolf = arrayOf(
            Location(2872, 3425, 0),
            Location(2863, 3440, 0),
            Location(2863, 3459, 0),
            Location(2854, 3475, 0),
            Location(2859, 3488, 0)
        )
        val common_stuck_locations = mapOf(
            // South of Tavlery dungeon
            ZoneBorders(2878, 3386, 2884, 3395) to { it: core.game.bots.impl.Adventurer ->
                it.scriptAPI!!.walkArray(core.game.bots.impl.Adventurer.Companion.tavleryToTopOfWhiteWolf + core.game.bots.impl.Adventurer.Companion.whiteWolfMountainTop + core.game.bots.impl.Adventurer.Companion.catherbyToTopOfWhiteWolf.reversedArray())
            },
            // West of Tavlery dungeon
            ZoneBorders(2874, 3390, 2880, 3401) to { it: core.game.bots.impl.Adventurer ->
                it.scriptAPI!!.walkArray(core.game.bots.impl.Adventurer.Companion.tavleryToTopOfWhiteWolf + core.game.bots.impl.Adventurer.Companion.whiteWolfMountainTop + core.game.bots.impl.Adventurer.Companion.catherbyToTopOfWhiteWolf.reversedArray())
            },
            // South of White Wolf Mountain in Tavlery
            ZoneBorders(2865, 3408, 2874, 3423) to { it: core.game.bots.impl.Adventurer ->
                it.scriptAPI!!.walkArray(core.game.bots.impl.Adventurer.Companion.tavleryToTopOfWhiteWolf + core.game.bots.impl.Adventurer.Companion.whiteWolfMountainTop + core.game.bots.impl.Adventurer.Companion.catherbyToTopOfWhiteWolf.reversedArray())
            },
            // On beginning of White Wolf Mountain in Tavlery
            ZoneBorders(2855, 3454, 2852, 3450) to { it: core.game.bots.impl.Adventurer ->
                it.scriptAPI!!.walkArray(core.game.bots.impl.Adventurer.Companion.tavleryToTopOfWhiteWolf + core.game.bots.impl.Adventurer.Companion.whiteWolfMountainTop + core.game.bots.impl.Adventurer.Companion.catherbyToTopOfWhiteWolf.reversedArray())
            },
            // South of White Wolf Mountain in Catherby
            ZoneBorders(2861, 3425, 2867, 3432) to { it: core.game.bots.impl.Adventurer ->
                it.scriptAPI!!.walkArray(core.game.bots.impl.Adventurer.Companion.catherbyToTopOfWhiteWolf + core.game.bots.impl.Adventurer.Companion.whiteWolfMountainTop + core.game.bots.impl.Adventurer.Companion.tavleryToTopOfWhiteWolf.reversedArray())
            },
            // On beginning of White Wolf Mountain in Catherby
            ZoneBorders(2863, 3441, 2859, 3438) to { it: core.game.bots.impl.Adventurer ->
                it.scriptAPI!!.walkArray(core.game.bots.impl.Adventurer.Companion.catherbyToTopOfWhiteWolf + core.game.bots.impl.Adventurer.Companion.whiteWolfMountainTop + core.game.bots.impl.Adventurer.Companion.tavleryToTopOfWhiteWolf.reversedArray())
            },
            // At the Crumbling Wall in Falador
            ZoneBorders(2937, 3356, 2936, 3353) to { it: core.game.bots.impl.Adventurer ->
                // Interact with the Crumbling Wall
                val wall = it.scriptAPI!!.getNearestNode("Crumbling wall", true)
                if (wall == null) {
                    it.refresh()
                    it.ticks = 0
                    return@to
                }
                it.scriptAPI!!.interact(it.bot!!, wall, "Climb-over")
            },
            // Northwest corner of Draynor Bank
            ZoneBorders(3092, 3246, 3091, 3247) to { it: core.game.bots.impl.Adventurer ->
                // Walk into Draynor Bank
                it.scriptAPI!!.walkTo(Location(3093, 3243, 0))
            },
            // West of GE, stuck in the corner south of the outlaw place
            ZoneBorders(3140, 3468, 3140, 3468) to { it: core.game.bots.impl.Adventurer ->
                // Walk to Barbarian village
                it.scriptAPI!!.walkArray(
                    arrayOf(
                        Location.create(3135, 3516, 0),
                        Location.create(3103, 3489, 0),
                        Location.create(3082, 3423, 0)
                    )
                )
            },
        )

        val dialogue: JSONObject
        val dateCode: Int

        init {
            val reader = FileReader(Configuration.BOT_DATA_PATH + File.separator + "bot_dialogue.json")
            val parser = org.json.simple.parser.JSONParser()
            val data = parser.parse(reader) as JSONObject

            core.game.bots.impl.Adventurer.Companion.dialogue = data

            val formatter = DateTimeFormatter.ofPattern("MMdd")
            val current = LocalDateTime.now()
            val formatted: String = current.format(formatter)
            core.game.bots.impl.Adventurer.Companion.dateCode = formatted.toInt()
        }

        private fun JSONObject.getLines(category: String): JSONArray {
            return this[category] as JSONArray
        }

        private fun JSONArray.rand(): String {
            return this.random() as String
        }
    }
}
