package content.region.kandarin.baxtorian.barbariantraining.herblore

import content.region.kandarin.baxtorian.barbariantraining.BarbarianTraining
import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

class BarbHerbloreListener : InteractionListener {

    override fun defineListeners() {
        for (potion in BarbarianMix.values()) {
            if (potion.isBoth()) {
                onUseWith(IntType.ITEM, potion.getItem(), Items.ROE_11324) { player, used, with ->
                    if (getAttribute(player, BarbarianTraining.HERBLORE_BASE, false) || getAttribute(player, BarbarianTraining.HERBLORE_FULL, false)) {
                        handle(player, used, with)
                    } else {
                        sendMessage(player, "In order to be able to make Barbarian mixes, Otto Godblessed must be talked to.")
                    }
                    return@onUseWith true
                }
            }

            onUseWith(IntType.ITEM, potion.getItem(), Items.CAVIAR_11326) { player, used, with ->
                if (getAttribute(player, BarbarianTraining.HERBLORE_BASE, false) || getAttribute(player, BarbarianTraining.HERBLORE_FULL, false)) {
                    handle(player, used, with)
                } else {
                    sendMessage(player, "In order to be able to make Barbarian mixes, Otto Godblessed must be talked to.")
                }
                return@onUseWith true
            }
        }

    }

    fun handle(player: Player, inputPotion: Node, egg: Node): Boolean {
        val potion: BarbarianMix = BarbarianMix.forId(inputPotion.id) ?: return false

        if (!hasLevelStat(player, Skills.HERBLORE, potion.getLevel())) {
            sendMessage(player, "You need a herblore level of " + potion.getLevel().toString() + " to make this mix.")
            return true
        }

        if (!removeItem(player, potion.getItem())) {
            return false
        }

        if (!removeItem(player, egg.id)) {
            addItem(player, potion.getItem())
            return false
        }

        animate(player, Animations.PESTLE_MORTAR_364)
        addItem(player, potion.getProduct())
        rewardXP(player, Skills.HERBLORE, potion.getExp())
        sendMessage(player, "you combine your potion with the ${getItemName(egg.id).lowercase()}.")

        if (getAttribute(player, BarbarianTraining.HERBLORE_BASE, false)) {
            removeAttribute(player, BarbarianTraining.HERBLORE_BASE)
            setAttribute(player, "/save:${BarbarianTraining.HERBLORE_FULL}", true)
            sendDialogueLines(player, "You feel you have learned more of barbarian ways. Otto might wish","to talk to you more.")
        }

        return true
    }

}
