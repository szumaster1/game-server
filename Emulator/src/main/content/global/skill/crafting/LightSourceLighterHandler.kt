package content.global.skill.crafting

import org.rs.consts.Items
import core.api.getStatLevel
import core.api.playAudio
import core.api.sendMessage
import core.game.container.Container
import core.game.event.LitLightSourceEvent
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Light source lighter handler.
 */
@Initializable
class LightSourceLighterHandler : UseWithHandler(Items.TINDERBOX_590, Items.CANDLE_36, Items.BLACK_CANDLE_38) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Items.TINDERBOX_590, ITEM_TYPE, this)
        addHandler(Items.UNLIT_TORCH_596, ITEM_TYPE, this)
        addHandler(Items.CANDLE_LANTERN_4529, ITEM_TYPE, this)
        addHandler(Items.CANDLE_LANTERN_4532, ITEM_TYPE, this)
        addHandler(Items.OIL_LAMP_4522, ITEM_TYPE, this)
        addHandler(Items.OIL_LANTERN_4537, ITEM_TYPE, this)
        addHandler(Items.BULLSEYE_LANTERN_4548, ITEM_TYPE, this)
        addHandler(Items.SAPPHIRE_LANTERN_4701, ITEM_TYPE, this)
        addHandler(Items.EMERALD_LANTERN_9064, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent?): Boolean {
        event ?: return false

        val used = if (event.used.id == Items.TINDERBOX_590) event.usedWith.asItem() else event.used.asItem()
        val lightSource = LightSource.forId(used.id)

        lightSource ?: return false

        if (!light(event.player, used, lightSource)) {
            sendMessage(
                event.player,
                "You need a Firemaking level of at least ${lightSource.levelRequired} to light this."
            )
        }

        return true
    }

    fun Container.replace(item: Item, with: Item) {
        if (remove(item)) {
            add(with)
        }
    }

    /**
     * Light function to manage lighting effects in the game.
     *
     * @param player        the player object that is interacting with the light.
     * @param item          the item that is being used to create light.
     * @param lightSource   the data related to the light source being utilized.
     * @return Returns `true` if the light was successfully activated, `false` otherwise.
     */
    fun light(player: Player, item: Item, lightSource: LightSource): Boolean {
        val requiredLevel = lightSource.levelRequired
        val playerLevel = getStatLevel(player, Skills.FIREMAKING)

        if (playerLevel < requiredLevel) return false

        playAudio(player, lightSource.sfxId)
        player.inventory.replace(item, Item(lightSource.litId))
        player.dispatch(LitLightSourceEvent(lightSource.litId))
        return true
    }
}
