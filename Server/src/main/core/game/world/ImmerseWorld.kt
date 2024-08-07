package core.game.world

import content.global.bots.*
import core.api.StartupListener
import core.game.bots.CombatBotAssembler
import core.game.bots.GeneralBotCreator
import core.game.bots.SkillingBotAssembler
import core.game.node.entity.combat.CombatStyle
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import java.util.*
import java.util.concurrent.Executors
import kotlin.concurrent.schedule

/**
 * Immerse world
 *
 * @constructor Immerse world
 */
class ImmerseWorld : StartupListener {

    override fun startup() {
        if (GameWorld.settings?.max_adv_bots!! > 0) {
            spawnBots()
        }
    }

    companion object {
        var assembler = CombatBotAssembler()
        var skillingBotAssembler = SkillingBotAssembler()

        fun spawnBots() {
            if (GameWorld.settings!!.enable_bots) {
                Executors.newSingleThreadExecutor().execute {
                    Thread.currentThread().name = "BotSpawner"
                    immerseSeersAndCatherby()
                    immerseLumbridgeDraynor()
                    immerseVarrock()
                    immerseWilderness()
                    immerseFishingGuild()
                    immerseAdventurer()
                    immerseFalador()
                    immerseSlayer()
                    immerseGE()
                }
            }
        }

        fun immerseAdventurer() {
            for (i in 0..(GameWorld.settings?.max_adv_bots ?: 50)) {
                var random: Long = (10000..300000).random().toLong()
                Timer().schedule(random) {
                    spawn_adventurers()
                }
            }
        }

        fun spawn_adventurers() {
            val lumbridge = Location.create(3221, 3219, 0)
            val tiers = listOf(CombatBotAssembler.Tier.LOW, CombatBotAssembler.Tier.MED)
            GeneralBotCreator(
                botScript = Adventurer(CombatStyle.MELEE),
                bot = assembler.MeleeAdventurer(tier = tiers.random(), location = lumbridge)
            )
            GeneralBotCreator(
                botScript = Adventurer(CombatStyle.RANGE),
                bot = assembler.RangeAdventurer(tier = tiers.random(), location = lumbridge)
            )
        }

        fun immerseFishingGuild() {
            val fishingGuild = Location.create(2604, 3421, 0)
            for (i in (0..4)) {
                GeneralBotCreator(SharkCatcher(), fishingGuild)
            }
        }

        fun immerseSeersAndCatherby() {
            GeneralBotCreator(
                botScript = SeersMagicTrees(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.AVERAGE,
                    loc = Location.create(2702, 3397, 0)
                )
            )
            GeneralBotCreator(
                botScript = SeersFlax(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(2738, 3444, 0)
                )
            )
            GeneralBotCreator(
                botScript = FletchingBankstander(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.AVERAGE,
                    loc = Location.create(2722, 3493, 0)
                )
            )
            GeneralBotCreator(
                botScript = GlassBlowingBankstander(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.AVERAGE,
                    loc = Location.create(2807, 3441, 0)
                )
            )
            GeneralBotCreator(
                botScript = LobsterCatcher(),
                loc = Location.create(2805, 3435, 0)
            )
        }

        fun immerseLumbridgeDraynor() {
            GeneralBotCreator(
                botScript = CowKiller(),
                bot = assembler.produce(
                    type = CombatBotAssembler.Type.RANGE,
                    tier = CombatBotAssembler.Tier.MED,
                    location = Location.create(3261, 3269, 0)
                )
            )
            GeneralBotCreator(
                botScript = CowKiller(),
                bot = assembler.produce(
                    type = CombatBotAssembler.Type.MELEE,
                    tier = CombatBotAssembler.Tier.LOW,
                    location = Location.create(3261, 3269, 0)
                )
            )
            GeneralBotCreator(
                botScript = CowKiller(),
                bot = assembler.produce(
                    type = CombatBotAssembler.Type.MELEE,
                    tier = CombatBotAssembler.Tier.MED,
                    location = Location.create(3257, 3267, 0)
                )
            )
            GeneralBotCreator(
                botScript = ManThiever(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3235, 3213, 0)
                )
            )
            GeneralBotCreator(
                botScript = FarmerThiever(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3094, 3243, 0)
                )
            )
            GeneralBotCreator(
                botScript = FarmerThiever(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3094, 3243, 0)
                )
            )
            GeneralBotCreator(
                botScript = DraynorWillows(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.values().random(),
                    loc = Location.create(3094, 3245, 0)
                )
            )
            GeneralBotCreator(
                botScript = DraynorWillows(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.values().random(),
                    loc = Location.create(3094, 3245, 0)
                )
            )
            GeneralBotCreator(
                botScript = DraynorWillows(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.values().random(),
                    loc = Location.create(3094, 3245, 0)
                )
            )
            GeneralBotCreator(
                botScript = DraynorFisher(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3095, 3246, 0)
                )
            )
            GeneralBotCreator(
                botScript = DraynorFisher(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3095, 3246, 0)
                )
            )
            GeneralBotCreator(
                botScript = DraynorFisher(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3095, 3246, 0)
                )
            )
        }

        fun immerseVarrock() {
            val WestBankIdlerBorders = ZoneBorders(3184, 3435, 3187, 3444)
            GeneralBotCreator(
                botScript = GlassBlowingBankstander(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.RICH,
                    loc = Location.create(3189, 3435, 0)
                )
            )
            GeneralBotCreator(
                botScript = FletchingBankstander(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.AVERAGE,
                    loc = Location.create(3189, 3439, 0)
                )
            )
            GeneralBotCreator(
                botScript = Idler(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.RICH,
                    loc = WestBankIdlerBorders.randomLoc
                )
            )
            GeneralBotCreator(
                botScript = GlassBlowingBankstander(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3256, 3420, 0)
                )
            )
            GeneralBotCreator(
                botScript = VarrockEssenceMiner(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3253, 3420, 0)
                )
            )
            GeneralBotCreator(
                botScript = VarrockSmither(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.RICH,
                    loc = Location.create(3189, 3436, 0)
                )
            )
            GeneralBotCreator(
                botScript = NonBankingMiner(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3182, 3374, 0)
                )
            )
        }

        fun immerseWilderness() {
            val wilderness = Location.create(3092, 3493, 0)

            repeat(6) {
                GeneralBotCreator(
                    botScript = GreenDragonKiller(CombatStyle.MELEE),
                    bot = assembler.assembleMeleeDragonBot(
                        tier = CombatBotAssembler.Tier.MED,
                        location = wilderness
                    )
                )
            }
        }

        fun immerseFalador() {
            GeneralBotCreator(
                botScript = CoalMiner(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.POOR,
                    loc = Location.create(3037, 9737, 0)
                )
            )
            GeneralBotCreator(
                botScript = CannonballSmelter(),
                bot = skillingBotAssembler.produce(
                    type = SkillingBotAssembler.Wealth.AVERAGE,
                    loc = Location.create(3013, 3356, 0)
                )
            )
        }

        fun immerseSlayer() {
            GeneralBotCreator(
                botScript = GenericSlayerBot(),
                bot = assembler.produce(
                    type = CombatBotAssembler.Type.MELEE,
                    tier = CombatBotAssembler.Tier.HIGH,
                    location = Location.create(2673, 3635, 0)
                )
            )
        }

        private fun immerseGE() {
            spawnDoubleMoneyBot(false)
        }

        fun spawnDoubleMoneyBot(delay: Boolean) {
            if (GameWorld.settings?.enable_doubling_money_scammers != true) return
            val random: Long = (10_000..7_200_000).random().toLong()
            Timer().schedule(if (delay) random else 0) {
                GeneralBotCreator(
                    botScript = DoublingMoney(),
                    bot = skillingBotAssembler.produce(
                        type = SkillingBotAssembler.Wealth.POOR,
                        loc = DoublingMoney.startingLocs.random()
                    )
                )
            }
        }
    }
}
