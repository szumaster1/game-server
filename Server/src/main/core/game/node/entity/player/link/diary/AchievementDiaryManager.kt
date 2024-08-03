package core.game.node.entity.player.link.diary

import content.global.skill.production.smithing.data.Bar
import core.game.component.Component
import core.game.container.impl.EquipmentContainer
import core.game.node.entity.player.Player
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class AchievementDiaryManager(val player: Player) {

    val diaries: Array<AchievementDiary> = arrayOf(
        AchievementDiary(DiaryType.KARAMJA),
        AchievementDiary(DiaryType.VARROCK),
        AchievementDiary(DiaryType.LUMBRIDGE),
        AchievementDiary(DiaryType.FALADOR),
        AchievementDiary(DiaryType.FREMENNIK),
        AchievementDiary(DiaryType.SEERS_VILLAGE)
    )

    fun parse(data: JSONArray) {
        for (i in data.indices) {
            val diary = data[i] as JSONObject
            var name = diary.keys.toTypedArray()[0] as String
            name = name.replace("_", "' ")
            for (ii in diaries.indices) {
                if (diaries[ii].type.diaryName == name) {
                    (diary[name.replace("' ", "_")] as JSONObject?)?.let { diaries[ii].parse(it) }
                }
            }
        }
    }

    fun openTab() {
        player.interfaceManager.openTab(2, Component(259))
        for (diary in diaries) {
            diary.drawStatus(player)
        }
    }

    fun updateTask(player: Player, type: DiaryType, level: Int, index: Int, complete: Boolean) {
        getDiary(type)!!.updateTask(player, level, index, complete)
    }

    fun finishTask(player: Player, type: DiaryType, level: Int, index: Int) {
        if (!player.isArtificial) {
            getDiary(type)!!.finishTask(player, level, index)
        }
    }

    fun hasCompletedTask(type: DiaryType, level: Int, index: Int): Boolean {
        return getDiary(type)!!.isComplete(level, index)
    }

    fun setStarted(type: DiaryType, level: Int) {
        getDiary(type)!!.setLevelStarted(level)
    }

    fun setCompleted(type: DiaryType, level: Int, index: Int) {
        getDiary(type)!!.setCompleted(level, index)
    }

    fun getDiary(type: DiaryType): AchievementDiary? {
        for (diary in diaries) {
            if (diary.type == type) {
                return diary
            }
        }
        return null
    }

    val karamjaGlove: Int
        get() {
            if (!hasGlove()) {
                return -1
            }
            for (i in 0..2) {
                if (player.equipment.containsItem(DiaryType.KARAMJA.rewards[i][0])) {
                    return i
                }
            }
            return -1
        }

    val armour: Int
        get() {
            if (!hasArmour()) {
                return -1
            }
            for (i in 0..2) {
                if (player.equipment.containsItem(DiaryType.VARROCK.rewards[i][0])) {
                    return i
                }
            }
            return -1
        }

    fun checkMiningReward(reward: Int): Boolean {
        val level = player.achievementDiaryManager.armour
        if (level == -1) {
            return false
        }
        if (reward == 453) {
            return true
        }
        return level == 0 && reward <= 442 || level == 1 && reward <= 447 || level == 2 && reward <= 449
    }

    fun checkSmithReward(type: Bar): Boolean {
        val level = player.achievementDiaryManager.armour
        if (level == -1) {
            return false
        }
        return level == 0 && type.ordinal <= Bar.STEEL.ordinal || level == 1 && type.ordinal <= Bar.MITHRIL.ordinal || level == 2 && type.ordinal <= Bar.ADAMANT.ordinal
    }

    fun hasGlove(): Boolean {
        val glove = player.equipment[EquipmentContainer.SLOT_HANDS]
        return glove != null && (glove.id == DiaryType.KARAMJA.rewards[0][0].id || glove.id == DiaryType.KARAMJA.rewards[1][0].id || glove.id == DiaryType.KARAMJA.rewards[2][0].id)
    }

    fun hasArmour(): Boolean {
        val plate = player.equipment[EquipmentContainer.SLOT_CHEST]
        return plate != null && (plate.id == DiaryType.VARROCK.rewards[0][0].id || plate.id == DiaryType.VARROCK.rewards[1][0].id || plate.id == DiaryType.VARROCK.rewards[2][0].id)
    }

    fun isComplete(type: DiaryType): Boolean {
        return diaries[type.ordinal].isComplete
    }

    fun resetRewards() {
        for (diary in diaries) {
            for (axis in diary.type.rewards) {
                for (item in axis) {
                    if (player.inventory.containsItem(item)) {
                        player.inventory.remove(item)
                    }
                    if (player.bank.containsItem(item)) {
                        player.bank.remove(item)
                    }
                    if (player.equipment.containsItem(item)) {
                        player.equipment.remove(item)
                    }
                }
            }
        }
    }
}
