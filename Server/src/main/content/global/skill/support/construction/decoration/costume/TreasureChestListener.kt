package content.global.skill.support.construction.decoration.costume

import core.api.animate
import core.api.consts.Animations
import core.api.consts.Scenery
import core.api.openInterface
import core.api.queueScript
import core.api.stopExecuting
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.world.update.flag.context.Animation

/**
 * Treasure chest listener.
 */
class TreasureChestListener : InterfaceListener, InteractionListener {

    private val treasureChestInterface = 467
    private val lowLevelChest = Scenery.TREASURE_CHEST_18804
    private val mediumLevelChest = Scenery.TREASURE_CHEST_18806
    private val highLevelChest = Scenery.TREASURE_CHEST_18808
    override fun defineListeners() {
        on(intArrayOf(lowLevelChest, mediumLevelChest, highLevelChest), IntType.SCENERY, "open") { player, node ->
            animate(player, Animation(Animations.HUMAN_OPEN_CHEST_536))
            queueScript(player, Animation(Animations.HUMAN_OPEN_CHEST_536).duration) { stage ->
                openInterface(player, treasureChestInterface)
                return@queueScript stopExecuting(player)
            }
            return@on true
        }
    }

    override fun defineInterfaceListeners() {
       onOpen(treasureChestInterface) { player, component ->
           return@onOpen true
       }

        on(treasureChestInterface) { player, component, opcode, buttonID, slot, Items ->
            // sendMessage(player, "You place a costume in the treasure chest")
            /*
            |--------------------------------------------------------------------------------------------------------------|
            | Low-level Treasure Trail rewards | Medium-level Treasure Trail rewards | High-level treasure trail rewards   |
            |----------------------------------|-------------------------------------|-------------------------------------|
            |                                  |                                     |                                     |
            | Black heraldic kiteshield        | Red straw boater                    | Rune heraldic kiteshield            |
            | Black heraldic kiteshield        | Orange straw boater                 | Rune heraldic kiteshield            |
            | Black heraldic kiteshield        | Green straw boater                  | Rune heraldic kiteshield            |
            | Black heraldic kiteshield        | Blue straw boater                   | Rune heraldic kiteshield            |
            | Black heraldic kiteshield        | black straw boater                  | Rune heraldic kiteshield            |
            | Gold-Trimmed studded leather     | Adamant heraldic kiteshield         | Gold-trimmed blue dragonhide armour |
            | Fur-trimmed studded leather      | Adamant heraldic kiteshield         | Trimed blue dragonhide armour       |
            | Gold trimmed wizard's robes      | Adamant heraldic kiteshield         | Enchanted robes                     |
            | Trimed wizard's robes            | Adamant heraldic kiteshield         | Robin hood hat                      |
            | Wizard boots                     | Adamant heraldic kiteshield         | Gold-trimmed rune armour            |
            | Trimmed black armour             | Gold-trimmed dragonhide armour      | Trimmed rune armour                 |
            | Gold-trimmed black armour        | Trimed dragonhide armour            | Brown cavalier                      |
            | Highwayman mask                  | Ranger boots                        | Dark brown cavalier                 |
            | Blue beret                       | Trimmed adamantite armour           | Black cavalier                      |
            | Deez nuts                        | Gold-trimmed adamantite armour      | Pirate hat                          |
            | Black beret                      | Red headband                        | Zamorak rune armour                 |
            | White beter                      | Black headband                      | Saradomin rune armour               |
            | Black heraldic helm              | Brown headband                      | Guthix rune armour                  |
            | Black heraldic helm              | Adamant heraldic helm               | Gold-plated rune armour             |
            | Black heraldic helm              | Adamant heraldic helm               | Rune heraldic helm 8464             |
            | Black heraldic helm              | Adamant heraldic helm               | Rune heraldic helm 8466             |
            | Black heraldic helm              | Adamant heraldic helm               | Rune heraldic helm 8468             |
            | Trimmed amulet of magic          | Adamant heraldic helm               | Rune heraldic helm 8470             |
            | Pantaloons                       | Trimmed amulet of strength          | Rune heraldic helm 8472             |
            | Wig                              | Elegant clothes (black)             | Trimmed amulet of glory             |
            | Flaredpants                      | Elegant clothes (purple)            | More (9839)                         |
            | Sleeping cap                     |                                     | Saradomin vestments                 |
            | More... (9839)                   |                                     | Guthix vestments                    |
            | Bob the Cat shirt (red)          |                                     | Zamorak vestments                   |
            | Bob the Cat shirt (blue)         |                                     | Saradomin blessed dragonhide        |
            | Bob the Cat shirt (green)        |                                     | Guthix blessed dragonhide           |
            | Bob the Cat shirt (black)        |                                     | Zamorak blessed dragonhide          |
            | Bob the Cat shirt (purple)       |                                     | Back...  (9839)                     |
            | Elegant clothes (red)            |                                     |                                     |
            | Elegant clothes (green)          |                                     |                                     |
            | Elegant clothes (blue)           |                                     |                                     |
            | Back... (9839)                   |                                     |                                     |
            |------------------------------------------------------------------------|-------------------------------------|
             */
            return@on true
        }
    }
}