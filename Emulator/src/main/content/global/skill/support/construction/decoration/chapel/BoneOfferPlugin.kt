package content.global.skill.support.construction.decoration.chapel

import org.rs.consts.Graphics
import content.global.skill.combat.prayer.Bones
import org.rs.consts.Sounds
import core.api.playAudio
import core.api.sendMessage
import core.api.submitIndividualPulse
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.map.RegionManager
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Handles the offering of bones on an altar.
 * @author Splinter
 */
@Initializable
class BoneOfferPlugin : UseWithHandler(526, 2859, 528, 3183, 3179, 530, 532, 3125, 4812, 3123, 534, 6812, 536, 4830, 4832, 6729, 4834, 14693) {
    /**
     * Represents the offer graphics.
     */
    private val GFX = Graphic(Graphics.USING_BONES_ON_AN_ALTAR_PRAYER_624)
    /**
     * The offer Animation.
     */
    private val ANIM = Animation(896)

    /**
     * Instantiates a new Bone offer plugin.
     */
    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(13185, OBJECT_TYPE, this)
        addHandler(13188, OBJECT_TYPE, this)
        addHandler(13191, OBJECT_TYPE, this)
        addHandler(13194, OBJECT_TYPE, this)
        addHandler(13197, OBJECT_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        var left: Scenery? = null
        var right: Scenery? = null
        if (event.usedWith.asScenery().rotation % 2 == 0) {
            left = RegionManager.getObject(
                event.usedWith.location.z,
                event.usedWith.location.x + 3,
                event.usedWith.location.y
            )
            right = RegionManager.getObject(
                event.usedWith.location.z,
                event.usedWith.location.x - 2,
                event.usedWith.location.y
            )
        } else {
            left = RegionManager.getObject(
                event.usedWith.location.z,
                event.usedWith.location.x,
                event.usedWith.location.y + 3
            )
            right = RegionManager.getObject(
                event.usedWith.location.z,
                event.usedWith.location.x,
                event.usedWith.location.y - 2
            )
        }
        val b = Bones.forId(event.usedItem.id)
        if (b != null) {
            worship(player, event.usedWith.asScenery(), left, right, b)
        }
        return true
    }

    /**
     * Worships the altar.
     *
     * @param [player] the player.
     * @param [altar] the altar object.
     * @param [left] the left brazier.
     * @param [right] the right brazier.
     * @param [bones] the bone used.
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
     * @param [scenery] the object.
     */
    private fun isLit(scenery: Scenery?): Boolean {
        return scenery != null && scenery.id != 15271 && SceneryDefinition.forId(scenery.id).options != null && !SceneryDefinition.forId(
            scenery.id
        ).hasAction("light")
    }

    /**
     * Gets the base modifier of the altar.
     *
     * @param [altar] the altar object
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
     * @param [isLeft] the left is lit.
     * @param [isRight] the right is lit.
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
}
