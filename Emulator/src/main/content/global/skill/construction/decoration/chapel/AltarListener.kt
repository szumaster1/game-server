package content.global.skill.construction.decoration.chapel

import content.global.skill.prayer.Bones
import core.api.playAudio
import core.api.sendMessage
import core.api.submitIndividualPulse
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.map.RegionManager
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import org.rs.consts.Graphics
import org.rs.consts.Sounds

class AltarListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, BONES, *ALTAR) { player, used, with ->
            var left: Scenery? = null
            var right: Scenery? = null
            if (used.asScenery().rotation % 2 == 0) {
                left = RegionManager.getObject(with.location.z, used.location.x + 3, with.location.y)
                right = RegionManager.getObject(with.location.z, used.location.x - 2, with.location.y)
            } else {
                left = RegionManager.getObject(with.location.z, with.location.x, with.location.y + 3)
                right = RegionManager.getObject(with.location.z, with.location.x, with.location.y - 2)
            }
            val b = Bones.forId(used.id)
            if (b != null) {
                worship(player, with.asScenery(), left, right, b)
            }
            return@onUseWith true
        }
    }


    /**
     * Worships the altar.
     *
     * @param player    the player
     * @param altar     the altar object
     * @param left      the left brazier
     * @param right     the right brazier
     * @param bones     the bone used
     */
    private fun worship(player: Player, altar: Scenery, left: Scenery?, right: Scenery?, bones: Bones) {
        if (player.ironmanManager.isIronman && !player.houseManager.isInHouse(player)) {
            sendMessage(player, "You cannot do this on someone else's altar.")
            return
        }
        val start = player.location
        val gfxLoc = player.location.transform(player.direction, 1)

        submitIndividualPulse(player, object : Pulse(1) {
            var counter = 0

            override fun pulse(): Boolean {
                counter++
                if (counter == 1 || counter % 5 == 0) {
                    if (player.inventory.remove(Item(bones.itemId))) {
                        player.animate(ANIM)
                        playAudio(player, Sounds.POH_OFFER_BONES_958)
                        player.packetDispatch.sendPositionedGraphics(GFX, gfxLoc)
                        player.sendMessage(getMessage(isLit(left), isLit(right)))
                        player.skills.addExperience(
                            Skills.PRAYER,
                            bones.experience * getMod(altar, isLit(left), isLit(right))
                        )
                    }
                }
                return !(player.location == start || !player.inventory.containsItem(Item(bones.itemId)))
            }
        })
    }

    /**
     * Checks if the burner is lit.
     *
     * @param obj       the object.
     */
    private fun isLit(obj: Scenery?): Boolean {
        return obj != null && obj.id != 15271 && SceneryDefinition.forId(obj.id).options != null && !SceneryDefinition.forId(
            obj.id
        ).hasAction("light")
    }

    /**
     * Gets the base modifier of the altar.
     *
     * @param altar     the altar object
     * @return the base bonus.
     */
    private fun getBase(altar: Scenery?): Double {
        var base = 150.0
        if (altar == null) {
            return base
        }
        base = when (altar.id) {
            13182 -> 110.0
            13185 -> 125.0
            13188 -> 150.0
            13191 -> 175.0
            13194 -> 200.0
            13197 -> 250.0
            else -> base
        }
        return base
    }

    /**
     * Gets the total experience modifier.
     *
     * @param isLeft    if the left is lit.
     * @param isRight   if the right is lit.
     * @return the mod.
     */
    private fun getMod(altar: Scenery, isLeft: Boolean, isRight: Boolean): Double {
        var total = getBase(altar)
        if (isLeft) {
            total += 50.0
        }
        if (isRight) {
            total += 50.0
        }
        return total / 100
    }

    /**
     * Gets the proper message.
     *
     * @return the message.
     */
    private fun getMessage(isLeft: Boolean, isRight: Boolean): String {
        return when {
            isLeft && isRight -> "The gods are very pleased with your offering."
            isLeft || isRight -> "The gods are pleased with your offering."
            else -> "The gods accept your offering."
        }
    }

    companion object {
        /**
         * The offer graphics.
         */
        private val GFX = Graphic(Graphics.USING_BONES_ON_AN_ALTAR_PRAYER_624)

        /**
         * The offer animation.
         */
        private val ANIM = Animation(896)

        /**
         * The bones ids.
         */
        private val BONES = Bones.array

        /**
         * The scenery ids.
         */
        private val ALTAR = intArrayOf(13185, 13188, 13191, 13194, 13197)
    }
}
