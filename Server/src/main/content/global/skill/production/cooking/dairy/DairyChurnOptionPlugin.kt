package content.global.skill.production.cooking.dairy

import core.api.consts.Items
import core.api.consts.Scenery
import core.api.inInventory
import core.api.sendMessage
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class DairyChurnOptionPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(Scenery.DAIRY_CHURN_10093).handlers["option:churn"] = this
        SceneryDefinition.forId(Scenery.DAIRY_CHURN_10094).handlers["option:churn"] = this
        SceneryDefinition.forId(Scenery.DAIRY_CHURN_25720).handlers["option:churn"] = this
        SceneryDefinition.forId(Scenery.DAIRY_CHURN_34800).handlers["option:churn"] = this
        SceneryDefinition.forId(Scenery.DAIRY_CHURN_35931).handlers["option:churn"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (!inInventory(player, Items.BUCKET_OF_MILK_1927, 1) && !inInventory(player, Items.POT_OF_CREAM_2130, 1) && !inInventory(player, Items.PAT_OF_BUTTER_6697, 1)) {
            sendMessage(player, "You need some milk, cream or butter to use in the churn.")
            return true
        }
        player.dialogueInterpreter.open(984374)
        return true
    }

}
