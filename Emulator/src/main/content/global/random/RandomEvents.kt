package content.global.random

import content.global.random.event.certer.CerterNPC
import content.global.random.event.drilldemon.SergeantDamienNPC
import content.global.random.event.drunkdwarf.DrunkenDwarfNPC
import content.global.random.event.evilbob.EvilBobNPC
import content.global.random.event.evilchicken.EvilChickenNPC
import content.global.random.event.eviltwin.MollyNPC
import content.global.random.event.freakyforest.FreakyForesterNPC
import content.global.random.event.genie.GenieNPC
import content.global.random.event.kissthefrog.FrogHeraldNPC
import content.global.random.event.lostandfound.LostAndFoundNPC
import content.global.random.event.mime.MimeNPC
import content.global.random.event.pinball.PinballNPC
import content.global.random.event.rickturpentine.RickTurpentineNPC
import content.global.random.event.rivertroll.RiverTrollNPC
import content.global.random.event.rockgolem.RockGolemNPC
import content.global.random.event.sandwichlady.SandwichLadyNPC
import content.global.random.event.securityguard.SecurityGuardNPC
import content.global.random.event.shade.ShadeNPC
import content.global.random.event.strangeplant.StrangePlantNPC
import content.global.random.event.surpriseexam.SurpriseExamNPC
import content.global.random.event.swarm.SwarmNPC
import content.global.random.event.treespirit.TreeSpiritNPC
import content.global.random.event.zombie.ZombieNPC
import cfg.consts.Items
import content.global.random.event.prisonpete.PrisonPeteNPC
import core.api.utils.WeightBasedTable
import core.api.utils.WeightedItem
import core.game.node.entity.skill.Skills

/**
 * Random events.
 *
 * @param npc The NPC associated with the random event.
 * @param loot The loot table for the random event.
 * @param skillIds The skill IDs associated with the random event.
 * @param type The type of the random event.
 * @author Ceikry
 */
enum class RandomEvents(
    val npc: RandomEventNPC,
    val loot: WeightBasedTable? = null,
    val skillIds: IntArray = intArrayOf(),
    val type: String = ""
) {
    /**
     * Sandwich Lady.
     */
    SANDWICH_LADY(npc = SandwichLadyNPC()),

    /**
     * Genie.
     */
    GENIE(npc = GenieNPC()),

    /**
     * Certer.
     */
    CERTER(npc = CerterNPC(), loot = WeightBasedTable.create(WeightedItem(Items.UNCUT_SAPPHIRE_1623, 1, 1, 3.4), WeightedItem(Items.KEBAB_1971, 1, 1, 1.7), WeightedItem(Items.UNCUT_EMERALD_1621, 1, 1, 1.7), WeightedItem(Items.SPINACH_ROLL_1969, 1, 1, 1.5), WeightedItem(Items.COINS_995, 80, 80, 1.1), WeightedItem(Items.COINS_995, 160, 160, 1.1), WeightedItem(Items.COINS_995, 320, 320, 1.1), WeightedItem(Items.COINS_995, 480, 480, 1.1), WeightedItem(Items.COINS_995, 640, 640, 1.1), WeightedItem(Items.UNCUT_RUBY_1619, 1, 1, 0.86), WeightedItem(Items.COINS_995, 240, 240, 0.65), WeightedItem(Items.COSMIC_TALISMAN_1454, 1, 1, 0.43), WeightedItem(Items.UNCUT_DIAMOND_1617, 1, 1, 0.22), WeightedItem(Items.TOOTH_HALF_OF_A_KEY_985, 1, 1, 0.1), WeightedItem(Items.LOOP_HALF_OF_A_KEY_987, 1, 1, 0.1))),

    /**
     * Drill Demon.
     */
    DRILL_DEMON(npc = SergeantDamienNPC()),

    /**
     * Evil Chicken.
     */
    EVIL_CHICKEN(npc = EvilChickenNPC()),

    /**
     * Kiss The Frog.
     */
    KISS_THE_FROG(npc = FrogHeraldNPC()),

    /**
     * Lost And Found.
     */
    LOST_AND_FOUND(npc = LostAndFoundNPC(), skillIds = intArrayOf(Skills.MAGIC)),

    /**
     * Evil Bob.
     */
    EVIL_BOB(npc = EvilBobNPC(), skillIds = intArrayOf(Skills.FISHING, Skills.MAGIC)),

    /**
     * Evil Twin.
     */
    EVIL_TWIN(npc = MollyNPC(), skillIds = intArrayOf(Skills.MINING, Skills.MAGIC), loot = CERTER.loot),

    /**
     * Surprise Exam.
     */
    SURPRISE_EXAM(npc = SurpriseExamNPC(), type = "sexam"),

    /**
     * Prison pete.
     */
    // PRISON_PETE(npc = PrisonPeteNPC(), skillIds = intArrayOf(Skills.SUMMONING, Skills.DEFENCE, Skills.FISHING), loot = WeightBasedTable.create(WeightedItem(Items.LAW_RUNE_563, 10, 10, 1.1), WeightedItem(Items.SAPPHIRE_1608, 5, 5, 1.1), WeightedItem(Items.RUBY_1604, 4, 4, 1.1), WeightedItem(Items.DIAMOND_1602, 2, 2, 1.1), WeightedItem(Items.GRIMY_SNAPDRAGON_3052, 1, 4, 0.86), WeightedItem(Items.COINS_995, 527, 527, 0.65), WeightedItem(Items.UGTHANKI_KEBAB_1884, 1, 2, 0.43), WeightedItem(Items.UNCUT_DIAMOND_1618, 1, 3, 0.22), WeightedItem(Items.MITHRIL_ARROWTIPS_42, 1, 1, 0.1), WeightedItem(Items.CHAOS_RUNE_562, 19, 23, 0.1))),

    /**
     * Swarm.
     */
    SWARM(npc = SwarmNPC()),

    /**
     * Security Guard.
     */
    SECURITY_GUARD(npc = SecurityGuardNPC(), skillIds = intArrayOf(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE, Skills.RANGE, Skills.PRAYER, Skills.HITPOINTS, Skills.SUMMONING), loot = CERTER.loot),

    /**
     * Drunken Dwarf.
     */
    DRUNKEN_DWARF(npc = DrunkenDwarfNPC(), skillIds = intArrayOf(Skills.STRENGTH, Skills.DEFENCE, Skills.ATTACK, Skills.RANGE, Skills.HITPOINTS)),

    /**
     * Mime Event.
     */
    MIME_EVENT(npc = MimeNPC(), skillIds = intArrayOf(Skills.PRAYER)),

    /**
     * Pinball Event.
     */
    PINBALL_EVENT(npc = PinballNPC(), skillIds = intArrayOf(Skills.FISHING, Skills.WOODCUTTING), loot = CERTER.loot),

    /**
     * Freaky Forester.
     */
    FREAKY_FORESTER(npc = FreakyForesterNPC(), skillIds = intArrayOf(Skills.WOODCUTTING)),

    /**
     * Tree Spirit.
     */
    TREE_SPIRIT(npc = TreeSpiritNPC(), skillIds = intArrayOf(Skills.WOODCUTTING)),

    /**
     * River Troll.
     */
    RIVER_TROLL(RiverTrollNPC(), skillIds = intArrayOf(Skills.FISHING)),

    /**
     * Strange Plant.
     */
    STRANGE_PLANT(npc = StrangePlantNPC(), skillIds = intArrayOf(Skills.WOODCUTTING)),

    /**
     * Rick Turpentine.
     */
    RICK_TURPENTINE(npc = RickTurpentineNPC(), skillIds = intArrayOf(Skills.STRENGTH, Skills.DEFENCE, Skills.ATTACK, Skills.RANGE, Skills.HITPOINTS, Skills.FISHING, Skills.WOODCUTTING), loot = CERTER.loot),

    /**
     * Rock Golem.
     */
    ROCK_GOLEM(RockGolemNPC(), skillIds = intArrayOf(Skills.MINING)),

    /**
     * Shade.
     */
    SHADE(ShadeNPC(), skillIds = intArrayOf(Skills.PRAYER)),

    /**
     * Zombie.
     */
    ZOMBIE(ZombieNPC(), skillIds = intArrayOf(Skills.PRAYER));

    companion object {
        @JvmField
        val randomIDs = values().map { it.npc.id }.toList()
        val skillMap = HashMap<Int, ArrayList<RandomEvents>>()
        val nonSkillList = ArrayList<RandomEvents>()

        init {
            populateMappings()
        }

        /**
         * Get a skill-based random event based on the given skill.
         *
         * @param skill The skill ID.
         * @return The random event associated with the skill.
         */
        fun getSkillBasedRandomEvent(skill: Int): RandomEvents? {
            return skillMap[skill]?.random()
        }

        /**
         * Get a non-skill random event.
         *
         * @return The random event.
         */
        fun getNonSkillRandom(): RandomEvents {
            return nonSkillList.random()
        }

        private fun populateMappings() {
            for (event in values()) {
                for (id in event.skillIds) {
                    val list = skillMap[id] ?: ArrayList<RandomEvents>().also { skillMap[id] = it }
                    list.add(event)
                }
                if (event.skillIds.isEmpty()) nonSkillList.add(event)
            }
        }
    }
}
