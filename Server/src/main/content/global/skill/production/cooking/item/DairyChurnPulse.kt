package content.global.skill.production.cooking.item

import content.global.skill.production.cooking.dairy.DairyProduct
import core.api.*
import core.api.consts.Animations
import core.api.consts.Graphics
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.tools.StringUtils

class DairyChurnPulse(player: Player?, item: Item?, product: DairyProduct, amount: Int) :
    SkillPulse<Item?>(player, item) {

    private val dairy: DairyProduct
    private var amount: Int

    init {
        super.setDelay(8)
        this.amount = amount
        this.dairy = product
    }

    override fun checkRequirements(): Boolean {
        player.interfaceManager.closeChatbox()
        var hasAnyInput = false
        for (input in dairy.inputs) {
            if (inInventory(player, input!!.id)) {
                hasAnyInput = true
                node = input
                break
            }
        }
        if (!hasAnyInput) {
            sendMessage(player, "You need a bucket of milk.")
            return false
        }
        if (getStatLevel(player, Skills.COOKING) < dairy.level) {
            sendMessage(player, "You need a cooking level of " + dairy.level + " to cook this.")
            return false
        }
        if (amount > player.inventory.getAmount(node)) {
            amount = player.inventory.getAmount(node)
        }
        if (amount < 1) {
            return false
        }
        animate()
        return true
    }

    override fun animate() {
        animate(player, Animations.CHURN_BUTTER_2793)
        /*
         *  sendGraphics(if(node!!.id == Scenery.DAIRY_CHURN_10094) Graphics.DAIRY_CHURN_458 else Graphics.DAIRY_CHURN_459, node!!.location)
         */
    }

    override fun reward(): Boolean {
        amount--
        for (input in dairy.inputs) {
            if (removeItem(player, input)) {
                addItem(player, dairy.product.id)
                if (input?.id == Items.BUCKET_OF_MILK_1927) {
                    if (!addItem(player, Items.BUCKET_OF_MILK_1927, 1)) {
                        GroundItemManager.create(Item(Items.BUCKET_1925, 1), player)
                    }
                }
                player.dispatch(ResourceProducedEvent(dairy.product.id, amount, node!!, Items.BUCKET_OF_MILK_1927))
                sendMessage(player, "You make " + (if (StringUtils.isPlusN(dairy.product.name.lowercase())) "an" else "a") + " " + dairy.product.name.lowercase() + ".")
                rewardXP(player, Skills.COOKING, dairy.experience)
                break
            }
        }

        return amount < 1
    }

}
