package content.global.handlers.player

import core.api.*
import org.rs.consts.Items
import org.rs.consts.Music
import core.game.activity.ActivityManager
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.SystemManager
import core.game.world.GameWorld
import core.plugin.Initializable
import core.plugin.Plugin
import core.plugin.PluginManifest
import core.plugin.PluginType
import java.util.concurrent.TimeUnit

/**
 * Represents the Login validation plugin.
 */
@Initializable
@PluginManifest(type = PluginType.LOGIN)
class LoginValidationPlugin : Plugin<Player> {

    private val QUEST_ITEMS = arrayOf(Item(Items.QUEST_POINT_CAPE_9813), Item(Items.QUEST_POINT_HOOD_9814))

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    override fun newInstance(player: Player): Plugin<Player> {
        player.unlock()
        if (player.isArtificial) {
            return this
        }
        if (!SystemManager.systemConfig.validLogin(player)) {
            return this
        }
        if (GameWorld.settings!!.isDevMode) {
            player.toggleDebug()
        }
        if (player.getAttribute("fc_wave", -1) > -1) {
            ActivityManager.start(player, "fight caves", true)
        }
        if (player.getAttribute("falconry", false)) {
            ActivityManager.start(player, "falconry", true)
        }
        if (player.getSavedData().questData.getDragonSlayerAttribute("repaired")) {
            setVarp(player, 177, 1967876)
        }
        if (player.getSavedData().globalData.getLootShareDelay() < System.currentTimeMillis() && player.getSavedData().globalData.getLootShareDelay() != 0L) {
            player.globalData.setLootSharePoints((player.globalData.getLootSharePoints() - player.globalData.getLootSharePoints() * 0.10).toInt())
        } else {
            player.getSavedData().globalData.setLootShareDelay(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1))
        }
        if (!player.musicPlayer.hasUnlocked(Music.SCAPE_SUMMON_457)) {
            player.musicPlayer.unlock(Music.SCAPE_SUMMON_457)
        }
        if (!player.musicPlayer.hasUnlocked(Music.SCAPE_HUNTER_207)) {
            player.musicPlayer.unlock(Music.SCAPE_HUNTER_207)
        }
        if (!player.musicPlayer.hasUnlocked(Music.GROUND_SCAPE_466)) {
            player.musicPlayer.unlock(Music.GROUND_SCAPE_466)
        }
        checkQuestPointsItems(player)
        return this
    }

    /*
     * Method used to check for the quest point cape items.
     * @param player the player.
     */

    private fun checkQuestPointsItems(player: Player) {
        if (!player.getQuestRepository().hasCompletedAll() && anyInEquipment(player, Items.QUEST_POINT_CAPE_9813, Items.QUEST_POINT_HOOD_9814)) {
            var location1: String? = null
            var location2: String? = null
            var item1 = 0
            var item2 = 0
            var amt = 0
            for (i in QUEST_ITEMS) {
                if (removeItem(player, i, Container.EQUIPMENT)) {
                    amt++
                    var location: String
                    if (addItem(player, i.id, i.amount, Container.INVENTORY)) {
                        location = "your inventory"
                    } else if (addItem(player, i.id, i.amount, Container.BANK)) {
                        location = "your bank"
                    } else {
                        location = "the Wise Old Man"
                        if (i.id == Items.QUEST_POINT_CAPE_9813) {
                            setAttribute(player, "/save:reclaim-qp-cape", true)
                        } else {
                            setAttribute(player, "/save:reclaim-qp-hood", true)
                        }
                    }
                    if (amt == 1) {
                        item1 = i.id
                        location1 = location
                    }
                    if (amt == 2) {
                        item2 = i.id
                        location2 = location
                    }
                }
            }
            if (amt == 2) {
                sendDoubleItemDialogue(player, item1, item2, "As you no longer have completed all the quests, your " + getItemName(item1) + " unequips itself to " + location1 + " and your " + getItemName(item2) + " unequips itself to " + location2 + "!")
            } else {
                sendItemDialogue(player, item1, "As you no longer have completed all the quests, your " + getItemName(item1) + " unequips itself to " + location1 + "!")
            }
        }
    }
}