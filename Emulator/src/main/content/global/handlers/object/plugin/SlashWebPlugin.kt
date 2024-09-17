package content.global.handlers.`object`.plugin

import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.Sounds
import core.cache.def.impl.SceneryDefinition
import core.game.container.Container
import core.game.container.impl.EquipmentContainer
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

/**
 * Slash web plugin.
 */
@Initializable
class SlashWebPlugin : OptionHandler() {

    private val sceneryIDs = intArrayOf(733, 1810, 11400, 33237)
    private val baseAnimation = Animation(451)
    private val cutSpiderWebAnimation = Animation(Animations.CUT_SPIDER_WEB_911)
    private val cutSpiderWebSoundEffect = Sounds.STABSWORD_SLASH_2548
    private val knifeID = Items.KNIFE_946

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (objectId in sceneryIDs) {
            SceneryDefinition.forId(objectId).handlers["option:slash"] = this
        }
        SceneryDefinition.forId(27266).handlers["option:pass"] = this
        SceneryDefinition.forId(29354).handlers["option:pass"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val scenery = node as Scenery
        var weapon = getWeapon(player, player.equipment)
        if (weapon == null) {
            weapon = getWeapon(player, player.inventory)
            if (weapon == null) {
                if (!inInventory(player, knifeID)) {
                    sendMessage(player,"Only a sharp blade can cut through this sticky web.")
                    return true
                }
                weapon = Item(knifeID)
            }
        }
        val success = RandomFunction.random(2) == 1
        lock(player, 2)
        playAudio(player, cutSpiderWebSoundEffect)
        animate(player, if (weapon == Item(knifeID)) cutSpiderWebAnimation else baseAnimation)
        if (success) {
            sendMessage(player, "You slash the web apart.")
            SceneryBuilder.replace(scenery, if (scenery.id == 27266 || scenery.id == 29354) scenery.transform(734) else scenery.transform(scenery.id + 1), 100)

            if (scenery.id == 29354) {
                /*
                 * Venture through the cobwebbed corridor in Varrock Sewers.
                 */
                finishDiaryTask(player, DiaryType.VARROCK, 0, 17)
            }

            if (scenery.id == 29354 && player.inventory.containsAtLeastOneItem(Items.RED_SPIDERS_EGGS_223) && player.location.y <= 9897) {
                /*
                 * Escape from the spider lair in Varrock Sewers with some red spiders eggs.
                 */
                finishDiaryTask(player, DiaryType.VARROCK, 1, 4)
            }

        } else {
            sendMessage(player, "You fail to cut through it.")
        }
        return true
    }

    private fun getWeapon(player: Player, container: Container): Item? {
        var item: Item? = if (container is EquipmentContainer) container.get(EquipmentContainer.SLOT_WEAPON) else null
        if (container is EquipmentContainer) {
            return checkEquipmentWeapon(player, item)
        }
        for (i in container.toArray()) {
            if (i == null) {
                continue
            }
            if (i.definition.hasAction("wield") || i.definition.hasAction("equip")) {
                item = checkEquipmentWeapon(player, i)
                if (item != null) {
                    return item
                }
            } else {
                continue
            }
        }
        return item
    }

    private fun checkEquipmentWeapon(player: Player, item: Item?): Item? {
        if (item != null) {
            val inter = WeaponInterface.getWeaponInterface(item) ?: return null
            var success = false
            for (style in inter.attackStyles) {
                if (style.bonusType == WeaponInterface.BONUS_SLASH) {
                    return item
                }
            }
            if (!success) {
                return null
            }
        }
        return item
    }
}
