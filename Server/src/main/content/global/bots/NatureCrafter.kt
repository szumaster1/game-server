package content.global.bots

import cfg.consts.Items
import core.api.teleport
import core.game.bots.*
import core.game.node.entity.player.link.TeleportManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders

/**
 * Nature crafter.
 */
@PlayerCompatible
@ScriptName("Nature Rune Crafter")
@ScriptDescription("Crafts nat runes. Start in Zanaris w/ dramen staff + Nat tiara.")
@ScriptIdentifier("nature_crafter")
class NatureCrafter : Script() {
    var state = State.INIT
    var runeCounter = 0
    var overlay: ScriptAPI.BottingOverlay? = null
    var startLocation = Location(0, 0, 0)
    var timer = 0
    var teleWaitTime = 16 // 18 ticks needed to rotate the clocks, 2 to teleport.
    var bank = Location(2384, 4457)
    var ruinsZone = ZoneBorders(2866, 3017, 2873, 3022)

    // You don't walk to the fae rings.
    var fairyRingDestination = Location(2412, 4435, 0)

    // you can only walk next to them. so path to the nearest point that isn't in the ring.
    var fairyRingCenter = Location(2412, 4434, 0)
    var karamjaRingDestination = Location(2801, 3002, 0)
    var karamjaRingCenter = Location(2801, 3003, 0)
    var natureZone = ZoneBorders(2387, 4828, 2416, 4856)

    override fun tick() {
        if (timer-- > 0) {
            return
        }
        if (bot!!.settings.runEnergy > 10.0) {
            bot!!.settings.isRunToggled = true
        }

        when (state) {
            State.INIT -> {
                if (checkValid()) {
                    overlay = scriptAPI!!.getOverlay()
                    overlay!!.init()
                    overlay!!.setTitle("Nature Runes")
                    overlay!!.setTaskLabel("Runes Crafted:")
                    overlay!!.setAmount(0)
                    startLocation = bot!!.location
                    state = State.BANKING
                }
            }

            State.BANKING -> {
                if (bot!!.location != bank) {
                    scriptAPI!!.walkTo(bank)
                    return
                }
                val runes = bot!!.inventory.getAmount(Item(Items.NATURE_RUNE_561))
                if (runes > 0) {
                    runeCounter += runes
                    overlay!!.setAmount(runeCounter)
                    bot!!.sendMessage("You have crafted a total of: $runeCounter runes.")
                    scriptAPI!!.bankItem(Items.NATURE_RUNE_561)
                } else {
                    scriptAPI!!.withdraw(Items.PURE_ESSENCE_7936, 28)
                    state = State.RUNNING_TO_TELE
                }
            }

            State.RUNNING_TO_TELE -> {
                if (bot!!.location != fairyRingDestination) {
                    scriptAPI!!.walkTo(fairyRingDestination)
                } else {
                    if (checkValid()) {
                        timer = teleWaitTime
                        state = State.TELE_WAIT
                        bot!!.sendMessage("Entering Fairy Ring Codes. Please wait...")
                    }
                }
            }

            State.TELE_WAIT -> {
                teleport(bot!!, karamjaRingCenter, TeleportManager.TeleportType.FAIRY_RING)
                state = State.RUNNING_TO_ALTER
                timer = 4
            }

            State.RETURN_WAIT -> {
                teleport(bot!!, fairyRingCenter, TeleportManager.TeleportType.FAIRY_RING)
                state = State.BANKING
            }

            State.RUNNING_TO_ALTER -> {
                val ruins = scriptAPI!!.getNearestNode(2460, true)
                if (natureZone.insideBorder(bot)) state = State.CRAFTING

                if (!ruinsZone.insideBorder(bot)) {
                    scriptAPI!!.walkTo(ruinsZone.randomLoc)
                } else if (ruins != null && ruins.location.withinDistance(bot!!.location, 20) && checkValid()) {
                    val ruinsChild = (ruins as Scenery).getChild(bot)
                    scriptAPI!!.interact(bot!!, ruinsChild, "enter")
                    timer = 4
                }
            }

            State.CRAFTING -> {
                val alter = scriptAPI!!.getNearestNode(2486, true)
                scriptAPI!!.interact(bot!!, alter, "craft-rune")
                if (bot!!.inventory.containsAtLeastOneItem(Item(Items.NATURE_RUNE_561))) state = State.LEAVING_ALTER
            }

            State.LEAVING_ALTER -> {
                var portalOut = scriptAPI!!.getNearestNode(2473, true)
                scriptAPI!!.interact(bot!!, portalOut, "use")
                if (ruinsZone.insideBorder(bot)) state = State.RETURNING_TO_TELE
            }

            State.RETURNING_TO_TELE -> {
                if (bot!!.location != karamjaRingDestination) {
                    scriptAPI!!.walkTo(karamjaRingDestination)
                } else {
                    if (checkValid()) {
                        var portalOut = scriptAPI!!.getNearestNode(14130, true)
                        portalOut?.interaction?.handle(bot!!, portalOut.interaction[0])
                        timer = 4
                        state = State.RETURN_WAIT
                    }
                }
            }

            State.INVALID -> {
                timer = 25
                state = State.INIT
            }
        }
    }

    // Terminates if you have invalid inventory to avoid cheating.
    private fun checkValid(): Boolean {
        if (!bot!!.equipment.containsAtLeastOneItem(Items.DRAMEN_STAFF_772) or !bot!!.equipment.containsAtLeastOneItem(
                Items.NATURE_TIARA_5541
            )
        ) {
            bot!!.sendMessage("Please equip a dramen staff and nature tiara first.")
            state = State.INVALID
            return false
        }
        return true
    }

    override fun newInstance(): Script {
        return this
    }

    /**
     * State
     *
     * @constructor State
     */
    enum class State {
        /**
         * Init
         *
         * @constructor Init
         */
        INIT,

        /**
         * Banking
         *
         * @constructor Banking
         */
        BANKING,

        /**
         * Running To Tele
         *
         * @constructor Running To Tele
         */
        RUNNING_TO_TELE,

        /**
         * Tele Wait
         *
         * @constructor Tele Wait
         */
        TELE_WAIT,

        /**
         * Running To Alter
         *
         * @constructor Running To Alter
         */
        RUNNING_TO_ALTER,

        /**
         * Crafting
         *
         * @constructor Crafting
         */
        CRAFTING,

        /**
         * Leaving Alter
         *
         * @constructor Leaving Alter
         */
        LEAVING_ALTER,

        /**
         * Returning To Tele
         *
         * @constructor Returning To Tele
         */
        RETURNING_TO_TELE,

        /**
         * Return Wait
         *
         * @constructor Return Wait
         */
        RETURN_WAIT,

        /**
         * Invalid
         *
         * @constructor Invalid
         */
        INVALID
    }
}
