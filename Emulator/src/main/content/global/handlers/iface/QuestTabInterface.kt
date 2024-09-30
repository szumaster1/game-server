package content.global.handlers.iface

import core.api.*
import core.game.component.Component
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.tools.colorize
import org.rs.consts.Components
import org.rs.consts.QuestName
import kotlin.math.max
import kotlin.math.min

class QuestTabInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.QUESTJOURNAL_V2_274) { player, _, _, buttonID, _, _ ->
            if (buttonID == 3) {
                player.achievementDiaryManager.openTab()
            } else {
                val quest = player.questRepository.forButtonId(buttonID)
                if (quest != null) {
                    openInterface(player, Components.QUESTJOURNAL_SCROLL_275)
                    quest.drawJournal(player, quest.getStage(player))
                } else {
                    showRequirementsInterface(player, buttonID)
                }
            }
            return@on true
        }

        on(Components.AREA_TASK_259) { player, _, _, buttonID, _, _ ->
            if (buttonID == 8) {
                player.interfaceManager.openTab(2, Component(Components.QUESTJOURNAL_V2_274))
            } else {
                player.achievementDiaryManager.getDiary(DiaryType.forChild(buttonID))?.open(player)
            }
            return@on true
        }
    }

    companion object {
        @JvmStatic
        fun showRequirementsInterface(player: Player, button: Int) {
            val questName = getNameForButton(button)
            val questReq = QuestRequirements.values().filter { it.questName.equals(questName, true) }.firstOrNull() ?: return
            var (isMet, unmetReqs) = QuestReq(questReq).evaluate(player)

            var messageList = ArrayList<String>()

            val statMap = HashMap<Int, Int>()
            val questList = HashSet<String>()
            var maxQpReq = 0
            var qpPenalty = 0
            closeInterface(player)
            for (req in unmetReqs) {
                if (req is QuestReq) questList.add(req.questReq.questName)
                else if (req is SkillReq) {
                    if (statMap[req.skillId] == null || (statMap[req.skillId] != null && statMap[req.skillId]!! < req.level)) statMap[req.skillId] =
                        req.level
                } else if (req is QPReq && req.amount > maxQpReq) maxQpReq = req.amount
                else if (req is QPCumulative) qpPenalty += req.amount
            }

            messageList.add(colorize("Quests Needed"))
            messageList.addAll(questList.map { "Completion of $it." })

            messageList.add(" ")
            messageList.add(colorize("Skills Needed"))

            for ((skillId, level) in statMap) {
                val name = Skills.SKILL_NAME[skillId]
                messageList.add("$level $name")
            }

            messageList.add(" ")
            messageList.add(colorize("Other Reqs"))

            val totalQpRequirement = QPReq(min(max(maxQpReq, qpPenalty), player.questRepository.availablePoints))
            val (meetsQp, _) = totalQpRequirement.evaluate(player)
            isMet = isMet && meetsQp

            if (isMet) messageList.add(colorize("Congratulations! You've earned this one."))

            if (!meetsQp) messageList.add("A total of ${totalQpRequirement.amount} Quest Points.")

            messageList.add("")

            sendString(player, questName, Components.QUESTJOURNAL_SCROLL_275, 2)
            var lineId = 11
            for (i in 0..299) {
                val entry = messageList.elementAtOrNull(i)
                if (entry != null) sendString(player, entry, Components.QUESTJOURNAL_SCROLL_275, lineId++)
                else sendString(player, "", Components.QUESTJOURNAL_SCROLL_275, lineId++)
            }
            openInterface(player, Components.QUESTJOURNAL_SCROLL_275)
        }

        fun getNameForButton(button: Int): String {
            val name = when (button) {
                10 -> QuestName.MYTHS_OF_THE_WHITE_LANDS
                11 -> QuestName.MYTHS_OF_THE_WHITE_LANDS
                //"Free Quests"
                13 -> QuestName.BLACK_KNIGHTS_FORTRESS
                14 -> QuestName.COOKS_ASSISTANT
                15 -> QuestName.DEMON_SLAYER
                16 -> QuestName.DORICS_QUEST
                17 -> QuestName.DRAGON_SLAYER
                18 -> QuestName.ERNEST_THE_CHICKEN
                19 -> QuestName.GOBLIN_DIPLOMACY
                20 -> QuestName.IMP_CATCHER
                21 -> QuestName.THE_KNIGHTS_SWORD
                22 -> QuestName.PIRATES_TREASURE
                23 -> QuestName.PRINCE_ALI_RESCUE
                24 -> QuestName.THE_RESTLESS_GHOST
                25 -> QuestName.ROMEO_JULIET
                26 -> QuestName.RUNE_MYSTERIES
                27 -> QuestName.SHEEP_SHEARER
                28 -> QuestName.SHIELD_OF_ARRAV
                29 -> QuestName.VAMPIRE_SLAYER
                30 -> QuestName.WITCHS_POTION
                //"Members' Quests"
                32 -> QuestName.ANIMAL_MAGNETISM
                33 -> QuestName.BETWEEN_A_ROCK
                34 -> QuestName.BIG_CHOMPY_BIRD_HUNTING
                35 -> QuestName.BIOHAZARD
                36 -> QuestName.CABIN_FEVER
                37 -> QuestName.CLOCK_TOWER
                38 -> QuestName.CONTACT
                39 -> QuestName.ZOGRE_FLESH_EATERS
                40 -> QuestName.CREATURE_OF_FENKENSTRAIN
                41 -> QuestName.DARKNESS_OF_HALLOWVALE
                42 -> QuestName.DEATH_TO_THE_DORGESHUUN
                43 -> QuestName.DEATH_PLATEAU
                44 -> QuestName.DESERT_TREASURE
                45 -> QuestName.DEVIOUS_MINDS
                46 -> QuestName.THE_DIG_SITE
                47 -> QuestName.DRUIDIC_RITUAL
                48 -> QuestName.DWARF_CANNON
                49 -> QuestName.EADGARS_RUSE
                50 -> QuestName.EAGLES_PEAK
                51 -> QuestName.ELEMENTAL_WORKSHOP_I
                52 -> QuestName.ELEMENTAL_WORKSHOP_II
                53 -> QuestName.ENAKHRAS_LAMENT
                54 -> QuestName.ENLIGHTENED_JOURNEY
                55 -> QuestName.THE_EYES_OF_GLOUPHRIE
                56 -> QuestName.FAIRYTALE_I_GROWING_PAINS
                57 -> QuestName.FAIRYTALE_II_CURE_A_QUEEN
                58 -> QuestName.FAMILY_CREST
                59 -> QuestName.THE_FEUD
                60 -> QuestName.FIGHT_ARENA
                61 -> QuestName.FISHING_CONTEST
                62 -> QuestName.FORGETTABLE_TALE
                63 -> QuestName.THE_FREMENNIK_TRIALS
                64 -> QuestName.WATERFALL_QUEST
                65 -> QuestName.GARDEN_OF_TRANQUILLITY
                66 -> QuestName.GERTRUDES_CAT
                67 -> QuestName.GHOSTS_AHOY
                68 -> QuestName.THE_GIANT_DWARF
                69 -> QuestName.THE_GOLEM
                70 -> QuestName.THE_GRAND_TREE
                71 -> QuestName.THE_HAND_IN_THE_SAND
                72 -> QuestName.HAUNTED_MINE
                73 -> QuestName.HAZEEL_CULT
                74 -> QuestName.HEROES_QUEST
                75 -> QuestName.HOLY_GRAIL
                76 -> QuestName.HORROR_FROM_THE_DEEP
                77 -> QuestName.ICTHLARINS_LITTLE_HELPER
                78 -> QuestName.IN_AID_OF_THE_MYREQUE
                79 -> QuestName.IN_SEARCH_OF_THE_MYREQUE
                80 -> QuestName.JUNGLE_POTION
                81 -> QuestName.LEGENDS_QUEST
                82 -> QuestName.LOST_CITY
                83 -> QuestName.THE_LOST_TRIBE
                84 -> QuestName.LUNAR_DIPLOMACY
                85 -> QuestName.MAKING_HISTORY
                86 -> QuestName.MERLINS_CRYSTAL
                87 -> QuestName.MONKEY_MADNESS
                88 -> QuestName.MONKS_FRIEND
                89 -> QuestName.MOUNTAIN_DAUGHTER
                90 -> QuestName.MOURNINGS_END_PART_I
                91 -> QuestName.MOURNINGS_END_PART_II
                92 -> QuestName.MURDER_MYSTERY
                93 -> QuestName.MY_ARMS_BIG_ADVENTURE
                94 -> QuestName.NATURE_SPIRIT
                95 -> QuestName.OBSERVATORY_QUEST
                96 -> QuestName.ONE_SMALL_FAVOUR
                97 -> QuestName.PLAGUE_CITY
                98 -> QuestName.PRIEST_IN_PERIL
                99 -> QuestName.RAG_AND_BONE_MAN
                100 -> QuestName.RATCATCHERS
                101 -> QuestName.RECIPE_FOR_DISASTER
                102 -> QuestName.RECRUITMENT_DRIVE
                103 -> QuestName.REGICIDE
                104 -> QuestName.ROVING_ELVES
                105 -> QuestName.ROYAL_TROUBLE
                106 -> QuestName.RUM_DEAL
                107 -> QuestName.SCORPION_CATCHER
                108 -> QuestName.SEA_SLUG
                109 -> QuestName.THE_SLUG_MENACE
                110 -> QuestName.SHADES_OF_MORTTON
                111 -> QuestName.SHADOW_OF_THE_STORM
                112 -> QuestName.SHEEP_HERDER
                113 -> QuestName.SHILO_VILLAGE
                114 -> QuestName.A_SOULS_BANE
                115 -> QuestName.SPIRITS_OF_THE_ELID
                116 -> QuestName.SWAN_SONG
                117 -> QuestName.TAI_BWO_WANNAI_TRIO
                118 -> QuestName.A_TAIL_OF_TWO_CATS
                119 -> QuestName.TEARS_OF_GUTHIX
                120 -> QuestName.TEMPLE_OF_IKOV
                121 -> QuestName.THRONE_OF_MISCELLANIA
                122 -> QuestName.THE_TOURIST_TRAP
                123 -> QuestName.WITCHS_HOUSE
                124 -> QuestName.TREE_GNOME_VILLAGE
                125 -> QuestName.TRIBAL_TOTEM
                126 -> QuestName.TROLL_ROMANCE
                127 -> QuestName.TROLL_STRONGHOLD
                128 -> QuestName.UNDERGROUND_PASS
                129 -> QuestName.WANTED
                130 -> QuestName.WATCHTOWER
                131 -> QuestName.COLD_WAR
                132 -> QuestName.THE_FREMENNIK_ISLES
                133 -> QuestName.TOWER_OF_LIFE
                134 -> QuestName.THE_GREAT_BRAIN_ROBBERY
                135 -> QuestName.WHAT_LIES_BELOW
                136 -> QuestName.OLAFS_QUEST
                137 -> QuestName.ANOTHER_SLICE_OF_HAM
                138 -> QuestName.DREAM_MENTOR
                139 -> QuestName.GRIM_TALES
                140 -> QuestName.KINGS_RANSOM
                141 -> QuestName.THE_PATH_OF_GLOUPHRIE
                142 -> QuestName.BACK_TO_MY_ROOTS
                143 -> QuestName.LAND_OF_THE_GOBLINS
                144 -> QuestName.DEALING_WITH_SCABARAS
                145 -> QuestName.WOLF_WHISTLE
                146 -> QuestName.AS_A_FIRST_RESORT
                147 -> QuestName.CATAPULT_CONSTRUCTION
                148 -> QuestName.KENNITHS_CONCERNS
                149 -> QuestName.LEGACY_OF_SEERGAZE
                150 -> QuestName.PERILS_OF_ICE_MOUNTAIN
                151 -> QuestName.TOKTZ_KET_DILL
                152 -> QuestName.SMOKING_KILLS
                153 -> QuestName.ROCKING_OUT
                154 -> QuestName.SPIRIT_OF_SUMMER
                155 -> QuestName.MEETING_HISTORY
                156 -> QuestName.ALL_FIRED_UP
                157 -> QuestName.SUMMERS_END
                158 -> QuestName.DEFENDER_OF_VARROCK
                159 -> QuestName.SWEPT_AWAY
                160 -> QuestName.WHILE_GUTHIX_SLEEPS
                161 -> QuestName.IN_PYRE_NEED
                162 -> QuestName.MYTHS_OF_THE_WHITE_LANDS
                else -> ""
            }
            return name
        }
    }

}