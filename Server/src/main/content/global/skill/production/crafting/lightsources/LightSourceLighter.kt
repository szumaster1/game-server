package content.global.skill.production.crafting.lightsources

import core.api.playAudio
import core.game.container.Container
import core.game.event.LitLightSourceEvent
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class LightSourceLighter : UseWithHandler(590, 36, 38) {
    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(590, ITEM_TYPE, this)
        addHandler(596, ITEM_TYPE, this)
        addHandler(4529, ITEM_TYPE, this)
        addHandler(4532, ITEM_TYPE, this)
        addHandler(4522, ITEM_TYPE, this)
        addHandler(4537, ITEM_TYPE, this)
        addHandler(4548, ITEM_TYPE, this)
        addHandler(4701, ITEM_TYPE, this)
        addHandler(9064, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent?): Boolean {
        event ?: return false
        val inventory = event.player.inventory
        val used =
            if (event.used.id == 590) event.usedWith.asItem() else event.used.asItem() //compensation for bad two-way use with handler shit

        val lightSource = LightSources.forId(used.id)

        lightSource ?: return false

        if (!light(event.player, used, lightSource)) {
            event.player.sendMessage("You need a Firemaking level of at least ${lightSource.levelRequired} to light this.")
        }

        return true
    }

    fun Container.replace(item: Item, with: Item) {
        if (remove(item)) {
            add(with)
        }
    }

    fun light(player: Player, item: Item, lightSource: LightSources): Boolean {
        val requiredLevel = lightSource.levelRequired
        val playerLevel = player.skills.getLevel(Skills.FIREMAKING)

        if (playerLevel < requiredLevel) return false

        playAudio(player, lightSource.lightSoundID)
        player.inventory.replace(item, Item(lightSource.litID))
        player.dispatch(LitLightSourceEvent(lightSource.litID))
        return true
    }
}
