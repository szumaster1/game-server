package content.global.skill.support.construction.servants

import content.global.handlers.iface.plugin.Plank
import cfg.consts.NPCs
import core.api.freeSlots
import core.api.getStatLevel
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.interaction.MovementPulse
import core.game.node.entity.impl.PulseType
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.IronmanMode
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.path.Pathfinder
import core.tools.BLACK
import java.util.*

/**
 * House servant dialogue.
 */
class HouseServantDialogue(player: Player? = null) : Dialogue(player) {

    private var sawmill = false
    private var logs: Item? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        val manager = player.houseManager
        val expression = if (npc.id != 4243) FacialExpression.HALF_GUILTY else FacialExpression.OLD_DEFAULT
        val fontColor = if (npc.id != 4243) BLACK else BLUE
        val inHouse = manager.isInHouse(player)
        /*
         * Parse options from our "use-with" handler.
         */
        if (args.size > 1) {
            sawmill = args[1] as Boolean
            logs = args[2] as Item
        }
        if (player.ironmanManager.checkRestriction(IronmanMode.ULTIMATE)) {
            player.sendMessage("Ultimate Ironman cannot hire butlers.")
            return true
        }
        if (!manager.hasHouse() && inHouse) {
            interpreter.sendDialogues(npc, expression, "You don't have a house that I can work in.", "I'll be waiting here if you decide to buy a house.")
            stage = 100
            return true
        }
        if (!manager.hasServant()) {
            val type = ServantType.forId(npc.id)
            if (getStatLevel(player, Skills.CONSTRUCTION) >= type.level) {
                interpreter.sendDialogues(npc, expression, fontColor + "You're not aristocracy, but I suppose you'd do. Do you", fontColor + "want a good cook for " + type.cost + fontColor + " coins?")
                stage = 0
                return true
            }
            interpreter.sendDialogues(npc, expression, "You need a Construction level of " + type.level + " and you must not", "currently have another person working for you", "in order to hire me.")
            stage = 100
            return true
        } else {
            val servant = manager.servant
            if (servant.item == null) {
                servant.item = Item(0, 0)
            }
            if (inHouse) {
                follow(player, servant)
                if (sawmill) {
                    interpreter.sendDialogues(servant, expression, fontColor + "Very well, I will take these logs to the mill and", fontColor + "have them converted into planks.")
                    stage = 110
                    return true
                }
                if (servant.item.amount > 0) {
                    if (freeSlots(player) < 1) {
                        interpreter.sendDialogues(servant, expression, fontColor + "I have returned with what you asked me to", fontColor + "retrieve. As I see your inventory is full, I shall wait", fontColor + "with these " + fontColor + servant.item.amount + fontColor + " items until you are ready.")
                        stage = 100
                        return true
                    }
                    interpreter.sendDialogues(servant, expression, fontColor + "I have returned with what you asked me to", fontColor + "retrieve.")
                    stage = 150
                    return true
                }
                interpreter.sendDialogues(servant, expression, fontColor + "Yes, " + fontColor + (if (player.appearance.isMale) "sir" else "ma'am") + "?", fontColor + "You have " + fontColor + (8 - servant.uses) + fontColor + " uses of my services remaining.")
                stage = 50
            } else if (npc.id != servant.id) {
                interpreter.sendDialogues(npc, expression, "You already have someone working for you.", "Fire them first before hiring me.")
                stage = 100
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val manager = player.houseManager
        val servant = manager.servant
        var type = ServantType.forId(npc.id)
        val expression = if (npc.id != 4243) FacialExpression.HALF_GUILTY else FacialExpression.OLD_DEFAULT
        val fontColor = if (npc.id != 4243) BLACK else BLUE
        when (stage) {
            0 -> {
                options("What can you do?", "Tell me about your previous jobs.", "You're hired!")
                stage++
            }

            1 -> when (buttonId) {
                1 -> when (npc.id) {
                    4235 -> interpreter.open(ServantRickDialogue())
                    4237 -> interpreter.open(ServantMaidDialogue())
                    4239 -> interpreter.open(ServantCookDialogue())
                    4241 -> interpreter.open(ServantButlerDialogue())
                    4243 -> interpreter.open(ServantDemonButlerDialogue())
                }

                2 -> when (npc.id) {
                    4235 -> interpreter.open(ServantRickDialogueExtension())
                    4237 -> interpreter.open(ServantMaidDialogueExtension())
                    4239 -> interpreter.open(ServantCookDialogueExtension())
                    4241 -> interpreter.open(ServantButlerDialogueExtension())
                    4243 -> interpreter.open(ServantDemonButlerDialogueExtension())
                }

                3 -> {
                    if (!manager.hasHouse()) {
                        interpreter.sendDialogues(npc, expression, "You don't have a house that I can work in.", "I'll be waiting here if you decide to buy a house.")
                        stage = 100
                        return true
                    }
                    player("You're hired!")
                    stage = 2
                }
            }

            2 -> {
                interpreter.sendDialogues(npc, expression, "Alright, " + (if (player.appearance.isMale) "sir" else "ma'am") + ". I can start work immediately.")
                stage++
            }

            3 -> {
                if (type != null && player.inventory.getAmount(995) >= type.cost && player.inventory.remove(Item(995, type.cost))) {
                    manager.servant = Servant(type)
                    interpreter.sendDialogue("The servant heads to your house.")
                    stage = 100
                } else {
                    interpreter.sendDialogue("You don't have enough money to pay the servant's hiring fee.")
                    stage = 100
                }
            }

            50 -> {
                options("Go to the bank/sawmill...", "Misc...", "Stop following me.", "You're fired!")
                stage++
            }

            51 -> {
                type = servant.type
                when (buttonId) {
                    1 -> {
                        options(if (!servant.attributes.containsKey("con:lastfetch")) "Repeat last fetch task" else "Fetch another " + (type.capacity.toString() + " x " + (servant.getAttribute<Any>("con:lastfetch") as Item).name.lowercase(Locale.getDefault())) + " (" + (servant.getAttribute("con:lastfetchtype")) + ")", "Go to the bank", "Go to the sawmill", "Pay wages (" + servant.uses + "/8 uses)")
                        stage++
                    }

                    2 -> {
                        options("Greet guests", "Cook me something")
                        stage = 56
                    }

                    3 -> {
                        player("Stop following me.")
                        if (servant.pulseManager.isMovingPulse) {
                            servant.pulseManager.clear(PulseType.STANDARD)
                        }
                        stage = 100
                    }

                    4 -> {
                        player("You're fired!")
                        stage = 75
                    }
                }
            }

            52 -> when (buttonId) {
                1 -> if (!servant.attributes.containsKey("con:lastfetch")) {
                    interpreter.sendDialogues(servant, expression, fontColor + "I haven't recently fetched anything from the bank or", fontColor + "sawmill for you.")
                    stage = 50
                    return true
                } else {
                    if (servant.getAttribute<Any>("con:lastfetchtype") === "bank") {
                        bankFetch(player, servant.getAttribute("con:lastfetch"))
                        return true
                    }
                    end()
                    sawmillRun(player, servant.getAttribute("con:lastfetch"))
                }

                2 -> {
                    options("Planks", "Oak planks", "Teak planks", "Mahogany planks", "More options")
                    stage = 60
                }

                3 -> {
                    if (type == ServantType.MAID || type == ServantType.RICK) {
                        interpreter.sendDialogues(servant, expression, fontColor + "I am unable to travel to the sawmill for you.")
                        stage = 100
                        return true
                    }
                    interpreter.sendDialogues(servant, expression, fontColor + "Hand the logs to me and I will take them to the", fontColor + "sawmill for you.")
                    stage = 100
                }

                4 -> {
                    stage = 100
                    if (servant.uses < 1) {
                        interpreter.sendDialogues(servant, expression, fontColor + "You have no need to pay me yet, I haven't performed", fontColor + "any of my services for you.")
                        return true
                    }
                    if (!player.inventory.containsItem(Item(995, type!!.cost))) {
                        interpreter.sendDialogues(servant, expression, fontColor + "Thanks for the kind gesture, but you don't have enough", fontColor + "money to pay me. I require " + fontColor + type.cost + fontColor + " coins every eight uses", fontColor + "of my services.")
                        return true
                    }
                    if (player.inventory.remove(Item(995, type.cost))) {
                        interpreter.sendDialogues(servant, expression, fontColor + "Thank you very much.")
                        servant.uses = 0
                        return true
                    }
                }
            }

            56 -> when (buttonId) {
                1 -> {
                    servant.isGreet = !servant.isGreet
                    player("Please " + (if (servant.isGreet) "greet" else "do not greet") + " all new guests upon arrival.")
                    stage++
                }

                2 -> {
                    if (type!!.food == null) {
                        interpreter.sendDialogues(servant, expression, fontColor + "I don't know any recipes.")
                        stage = 100
                        return true
                    }
                    if (type.food.size > 1) {
                        options(type.food[0].name, type.food[1].name, "Nevermind.")
                        stage = 58
                    } else {
                        options(type.food[0].name, "Nevermind.")
                        stage = 59
                    }
                }
            }

            57 -> {
                interpreter.sendDialogues(servant, expression, fontColor + "Whatever you command.")
                stage = 50
            }

            58 -> {
                if (freeSlots(player) < 1) {
                    interpreter.sendDialogues(servant, expression, fontColor + "I would love to share my fine cooking with you,", fontColor + "but your hands are currently full.")
                    stage = 100
                    return true
                }
                if (!requirements(player, null, false)) {
                    end()
                    return true
                }
                when (buttonId) {
                    1 -> {
                        player.inventory.add(type!!.food[0])
                        stage = 50
                    }

                    2 -> {
                        player.inventory.add(type!!.food[1])
                        stage = 50
                    }

                    3 -> {
                        player("Nevermind.")
                        stage = 100
                        return true
                    }
                }
                servant.uses += 1
                interpreter.sendDialogues(servant, expression, fontColor + "Luckily for you, I already have some made. Here you", fontColor + "go.")
                stage = 50
            }

            59 -> {
                if (freeSlots(player) < 1) {
                    interpreter.sendDialogues(servant, expression, fontColor + "I would love to share my fine cooking with", fontColor + "you, but you have no space to take anything.")
                    stage = 50
                    return true
                }
                if (!requirements(player, null, false)) {
                    end()
                    return true
                }
                when (buttonId) {
                    1 -> {
                        player.inventory.add(type!!.food[0])
                        stage = 100
                    }

                    2 -> {
                        player("Nevermind.")
                        stage = 100
                        return true
                    }
                }
                servant.uses += 1
                interpreter.sendDialogues(servant, expression, fontColor + "Luckily for you, I already have some made. Here you", fontColor + "go.")
                stage = 100
            }

            60 -> when (buttonId) {
                1 -> {
                    bankFetch(player, Item(960))
                    stage = 100
                }

                2 -> {
                    bankFetch(player, Item(8778))
                    stage = 100
                }

                3 -> bankFetch(player, Item(8780))
                4 -> bankFetch(player, Item(8782))
                5 -> {
                    options("Soft clay", "Limestone bricks", "Steel bars", "Cloth", "More options")
                    stage++
                }
            }

            61 -> when (buttonId) {
                1 -> bankFetch(player, Item(1761))
                2 -> bankFetch(player, Item(3420))
                3 -> bankFetch(player, Item(2353))
                4 -> bankFetch(player, Item(8790))
                5 -> {
                    options("Gold leaves", "Marble blocks", "Magic stones")
                    stage++
                }
            }

            62 -> when (buttonId) {
                1 -> bankFetch(player, Item(4692))
                2 -> bankFetch(player, Item(8786))
                3 -> bankFetch(player, Item(4703))
            }

            75 -> {
                interpreter.sendDialogues(servant, expression, fontColor + "Very well. I will return to the Guild of the Servants", fontColor + "in Ardougne if you wish to re-hire me.")
                stage++
            }

            76 -> {
                end()
                servant.item.amount = 0
                servant.uses = 0
                servant.clear()
                servant.location = Location(0, 0)
                manager.servant = null
            }

            100 -> end()
            110 -> {
                end()
                sawmillRun(player, logs)
            }

            150 -> {
                if (servant.item == null) {
                    end()
                    return true
                }
                var amtLeft = servant.item.amount
                var flag = false
                if (amtLeft < 1 || servant.item == null) {
                    interpreter.sendDialogues(servant, expression, fontColor + "I don't have any items left.")
                    stage = 100
                    return true
                }
                if (amtLeft > freeSlots(player)) {
                    amtLeft = freeSlots(player)
                    flag = true
                }
                servant.item.amount -= amtLeft
                player.inventory.add(Item(servant.item.id, amtLeft))
                if (flag) {
                    interpreter.sendDialogues(servant, expression, fontColor + "I still have " + fontColor + servant.item.amount + fontColor + " left for you to take from me.")
                    stage = 100
                } else {
                    end()
                }
            }
        }
        return true
    }

    private fun requirements(player: Player?, item: Item?, sawmill: Boolean): Boolean {
        val manager = player!!.houseManager
        val servant = manager.servant
        val expression = if (npc.id != 4243) FacialExpression.HALF_GUILTY else FacialExpression.OLD_DEFAULT
        val fontColor = if (npc.id != 4243) BLACK else BLUE
        if (!sawmill && freeSlots(player) < 1) {
            interpreter.sendDialogues(servant, expression, fontColor + "You don't have any space in your inventory.")
            stage = 100
            return false
        }
        if (servant.uses >= 8) {
            player.sendMessage("<col=CC0000>The servant has left your service due to a lack of payment.</col>")
            servant.item.amount = 0
            servant.uses = 0
            servant.clear()
            servant.location = Location(0, 0)
            manager.servant = null
            end()
            return false
        }
        if (servant.item.amount > 0) {
            interpreter.sendDialogues(servant, expression, fontColor + "You can't send me off again, I'm still holding some of", fontColor + "your previous items.")
            return false
        }
        return true
    }

    private fun sawmillRun(player: Player, item: Item?) {
        val manager = player.houseManager
        val servant = manager.servant
        val type = manager.servant.type
        val expression = if (npc.id != 4243) FacialExpression.HALF_GUILTY else FacialExpression.OLD_DEFAULT
        val fontColor = if (npc.id == 4243) BLUE else BLACK
        if (item == null || !requirements(player, item, true)) {
            return
        }
        if (type == ServantType.MAID || type == ServantType.RICK) {
            interpreter.sendDialogues(servant, expression, fontColor + "I am unable to take planks to the sawmill.")
            return
        }
        var amt = player.inventory.getAmount(item)
        if (amt < 1) {
            interpreter.sendDialogues(servant, expression, fontColor + "You don't have any more of that type of log.")
            return
        }
        for (plank in Plank.values()) {
            if (plank.log.id == item.id) {
                if (amt > type.capacity) {
                    amt = type.capacity
                }
                if (!player.inventory.contains(995, plank.price * amt)) {
                    interpreter.sendDialogues(servant, expression, fontColor + "You don't have enough coins for me to do that.", fontColor + "I can hold " + fontColor + type.capacity + fontColor + " logs and each of this type of log", fontColor + "costs " + fontColor + plank.price + fontColor + " coins each to convert into plank form.")
                    return
                }
                end()
                if (player.inventory.remove(Item(item.id, amt)) && player.inventory.remove(Item(995, amt * plank.price))) {
                    manager.servant.item = Item(plank.plank.id, amt)
                    servant.isInvisible = true
                    servant.locks.lockMovement(100)
                    Pulser.submit(object : Pulse((type.timer / 0.6).toInt()) {
                        override fun pulse(): Boolean {
                            servant.isInvisible = false
                            servant.locks.unlockMovement()
                            servant.setAttribute("con:lastfetch", Item(item.id, 1))
                            servant.setAttribute("con:lastfetchtype", "sawmill")
                            interpreter.open(servant.id, servant)
                            return true
                        }
                    })
                }
                break
            }
        }
    }

    private fun bankFetch(player: Player?, item: Item?) {
        val manager = player!!.houseManager
        val servant = manager.servant
        val type = manager.servant.type
        val expression = if (npc.id != 4243) FacialExpression.HALF_GUILTY else FacialExpression.OLD_DEFAULT
        val fontColor = if (npc.id != 4243) BLACK else BLUE
        if (item == null || !requirements(player, item, false)) {
            return
        }
        if (!player.bank.containsItem(item)) {
            interpreter.sendDialogues(servant, expression, fontColor + "You don't seem to have any of those in the bank.")
            stage = 100
            return
        }
        end()
        servant.isInvisible = true
        servant.locks.lockMovement(100)
        Pulser.submit(object : Pulse((type.timer / 0.6).toInt()) {
            override fun pulse(): Boolean {
                /*
                 * TODO: Check if in dungeon?
                 */
                if (player.houseManager.houseRegion != player.viewport.region) {
                    return true
                }
                var amt = player.bank.getAmount(item.id)
                if (amt < 1) {
                    return true
                }
                if (amt > type.capacity) {
                    amt = type.capacity
                }
                servant.isInvisible = false
                servant.locks.unlockMovement()
                val fetch = Item(item.id, amt)
                if (player.bank.remove(fetch)) {
                    manager.servant.item = fetch
                    interpreter.open(servant.id, servant)
                }
                servant.setAttribute("con:lastfetch", Item(fetch.id, 1))
                servant.setAttribute("con:lastfetchtype", "bank")
                manager.servant.uses += 1
                follow(player, servant)
                return true
            }
        })
    }

    private fun follow(player: Player, npc: NPC) {
        npc.pulseManager.run(object : MovementPulse(npc, player, Pathfinder.SMART) {
            override fun pulse(): Boolean {
                return false
            }
        }, PulseType.STANDARD)
    }

    override fun newInstance(player: Player): Dialogue {
        return HouseServantDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RICK_4235, NPCs.MAID_4237, NPCs.COOK_4239, NPCs.BUTLER_4241, NPCs.DEMON_BUTLER_4243)
    }

}
