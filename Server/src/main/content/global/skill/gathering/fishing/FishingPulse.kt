package content.global.skill.gathering.fishing


import content.global.skill.combat.summoning.familiar.Forager
import content.global.skill.skillcape.SkillcapePerks
import content.global.skill.skillcape.SkillcapePerks.Companion.isActive
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.event.ResourceProducedEvent
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.command.sets.STATS_BASE
import core.game.system.command.sets.STATS_FISH
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.path.Pathfinder
import core.game.world.update.flag.context.Animation
import core.utilities.RandomFunction
import core.utilities.colorize


/**
 * Handles a fishing pulse.
 */
class FishingPulse(player: Player?, npc: NPC, private val option: FishingOption?) : SkillPulse<NPC?>(player, npc) {
    /**
     * Represents the fish type.
     */
    private var fish: Fish? = null

    /**
     * Represents the base location the npc was at.
     */
    private val location: Location = npc.location

    override fun start() {
        if (player.familiarManager.hasFamiliar() && player.familiarManager.familiar is Forager) {
            val forager = player.familiarManager.familiar as Forager
            val dest = player.location.transform(player.direction)
            Pathfinder.find(forager.location, dest).walk(forager)
        }
        super.start()
    }

    override fun checkRequirements(): Boolean {
        if (option == null) {
            return false
        }
        player.debug(inInventory(player, option.tool).toString())
        if (!inInventory(player, option.tool) && !hasBarbTail()) {
            var msg = "You need a "
            msg += if (getItemName(option.tool).contains("net", true)) "net to " else "${getItemName(option.tool).lowercase()} to "
            msg += if (option.option in arrayOf("lure", "bait")) "${option.option} these fish." else "catch these fish."
            sendDialogue(player, msg)
            stop()
            return false
        }
        if (!option.hasBait(player)) {
            var msg = "You don't have any " + option.getBaitName().lowercase()
            msg += if (option.getBaitName() == getItemName(Items.FISHING_BAIT_313)) " left." else "s left."
            sendDialogue(player, msg)
            stop()
            return false
        }
        if (!hasLevelDyn(player, Skills.FISHING, option.level)) {
            sendDialogue(player, "You need a Fishing level of at least ${option.level} to ${option.option} these fish.")
            stop()
            return false
        }
        if (freeSlots(player) == 0) {
            if (option.fish.contains(Fish.LOBSTER)) {
                sendDialogue(player, "You can't carry any more lobsters.")
            } else {
                sendDialogue(player, "You can't carry any more fish.")
            }
            stop()
            return false
        }
        if (location !== node!!.location || !node!!.isActive || node!!.isInvisible) {
            stop()
            return false
        }
        return true
    }

    override fun animate() {
        if (isBareHanded(player)) {
            player.animate(Animation(6709))
            Pulser.submit(object : Pulse(1) {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        5 -> getCatchAnimationAndLoot(player)
                    }
                    return false
                }
            })
        } else {
            player.animate(option!!.animation)
        }
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            super.setDelay(5)
            return false
        }
        if (player.familiarManager.hasFamiliar() && player.familiarManager.familiar is Forager) {
            val forager = player.familiarManager.familiar as Forager
            forager.handlePassiveAction()
        }
        if (success()) {
            if (player.inventory.hasSpaceFor(Item(fish!!.id)) && option!!.removeBait(player)) {
                player.dispatch(ResourceProducedEvent(fish!!.id, 1, node!!))
                val item = fish!!
                if (isActive(SkillcapePerks.GREAT_AIM, player) && RandomFunction.random(100) <= 5) {
                    addItem(player, item.id)
                    player.sendMessage(colorize("%RYour expert aim catches you a second fish."))
                }
                addItem(player, item.id)
                var fishCaught = player.getAttribute(STATS_BASE + ":" + STATS_FISH, 0)
                player.setAttribute("/save:$STATS_BASE:$STATS_FISH", ++fishCaught)
                player.skills.addExperience(Skills.FISHING, fish!!.experience, true)
                message(2)
            }
        }
        return player.inventory.freeSlots() == 0
    }

    private fun isBareHanded(p: Player): Boolean {
        if (option == FishingOption.HARPOON || option == FishingOption.SHARK_HARPOON) {
            if (checkFish(p) > 0 && !(inInventory(player, option.tool) || inEquipment(player, option.tool) || hasBarbTail())) {
                return true
            }
            if (checkFish(p) > 2 && !(inInventory(player, option.tool) || inEquipment(player, option.tool) || hasBarbTail())) {
                return true
            }
        }
        return false
    }

    private fun getCatchAnimationAndLoot(p: Player): Int {
        val fishingFor = checkFish(p)
        when (node!!.id) {
            324 -> when (fishingFor) {
                1 -> {
                    animate(player, Animations.BAREHAND_TUNA_6710)
                    rewardXP(player, Skills.FISHING, 80.0)
                    rewardXP(player, Skills.STRENGTH, 8.0)
                    addItem(player, Items.RAW_TUNA_359)
                }

                2, 3 -> if (RandomFunction.random(1) == 1) {
                    animate(player, Animations.BAREHAND_TUNA_6710)
                    rewardXP(player, Skills.FISHING, 80.0)
                    rewardXP(player, Skills.STRENGTH, 8.0)
                    addItem(player, Items.RAW_TUNA_359)
                } else {
                    animate(player, Animations.BAREHAND_SWORDFISH_6707)
                    rewardXP(player, Skills.FISHING, 100.0)
                    rewardXP(player, Skills.STRENGTH, 10.0)
                    addItem(player, Items.RAW_SWORDFISH_371)
                }
            }

            313 -> {
                animate(player, Animations.BAREHAND_SHARK_6705)
                rewardXP(player, Skills.FISHING, 110.0)
                rewardXP(player, Skills.STRENGTH, 11.0)
                addItem(player, Items.RAW_SHARK_383)
            }
        }
        return 0
    }

    /**
     * Checks if they have the barb tail harpoon.
     */
    private fun hasBarbTail(): Boolean {
        val bh = FishingOption.BARB_HARPOON.tool
        if (option == FishingOption.HARPOON || option == FishingOption.SHARK_HARPOON) {
            if (inInventory(player, bh) || inEquipment(player, bh)) {
                return true
            }
        }
        return false
    }

    override fun message(type: Int) {
        when (type) {
            0 -> sendMessage(player, option!!.getStartMessage())
            2 -> {
                var msg = when (fish) {
                    in arrayOf(Fish.ANCHOVIE, Fish.SHRIMP, Fish.SEAWEED) -> "You catch some "
                    in arrayOf(Fish.OYSTER) -> "You catch an "
                    else -> "You catch a "
                }
                msg += getItemName(fish!!.id).lowercase().replace("raw ", "").replace("big ", "")
                msg += if (fish == Fish.SHARK) "!" else "."
                sendMessage(player, msg)

                if (player.inventory.freeSlots() == 0) {
                    if (fish == Fish.LOBSTER) {
                        sendDialogue(player, "You can't carry any more lobsters.")
                    } else {
                        sendDialogue(player, "You can't carry any more fish.")
                    }
                    stop()
                }
            }
        }
    }

    /**
     * Method used to check if the catch was a success.
     */
    private fun success(): Boolean {
        if (delay == 1) {
            return false
        }
        fish = option!!.rollFish(player)
        return fish != null
    }

    companion object {
        fun checkFish(p: Player): Int {
            return if (p.skills.getLevel(Skills.FISHING) >= 55 && p.skills.getLevel(Skills.STRENGTH) >= 35) {
                if (p.skills.getLevel(Skills.FISHING) >= 70 && p.skills.getLevel(Skills.STRENGTH) >= 50) {
                    if (p.skills.getLevel(Skills.FISHING) >= 96 && p.skills.getLevel(Skills.STRENGTH) >= 76) {
                        3
                    } else 2
                } else 1
            } else 0
        }
    }

    /**
     * Constructs a new `FishingPulse` `Object`.
     *
     * @param player the player.
     * @param npc    the fishing spot NPC.
     * @param option The fishing option.
     */
    init {
    }
}
