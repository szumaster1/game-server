package content.global.random.event.prisonpete

import core.api.*
import core.api.consts.*
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction
import core.game.world.update.flag.context.Graphic
import core.utilities.RandomFunction

class PrisonPeteListeners : InteractionListener, MapArea {

    override fun defineListeners() {
        on(NPCs.PRISON_PETE_3118, IntType.NPC, "talk-to") { player, _ ->
            face(player, findNPC(NPCs.PRISON_PETE_3118)!!.location)
            openDialogue(player, PrisonPeteDialogue(0))
            return@on true
        }

        on(Items.PRISON_KEY_6966, IntType.ITEM, "return") { player, _ ->
            openDialogue(player, PrisonPeteDialogue(1))
            return@on true
        }

        on(Scenery.LEVER_26191, IntType.SCENERY, "pull") { player, _ ->
            animate(player, 798)
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    when (RandomFunction.random(0, 3)) {
                        0 -> {
                            openInterface(player, Components.DOUBLEOBJBOX_131).also {
                                player.packetDispatch.sendModelOnInterface(10734, Components.DOUBLEOBJBOX_131, 2, 100)
                                player.packetDispatch.sendAngleOnInterface(Components.DOUBLEOBJBOX_131, 2, 500, 200, 500)
                                setInterfaceText(player, "<br>" + "Pop the correct balloon animal to find a key.", Components.DOUBLEOBJBOX_131, 1)
                            }
                            setAttribute(player, PrisonUtils.POP_KEY_VALUE, 0)
                        }

                        1 -> {
                            openInterface(player, Components.DOUBLEOBJBOX_131).also {
                                player.packetDispatch.sendModelOnInterface(10735, Components.DOUBLEOBJBOX_131, 2, 100)
                                player.packetDispatch.sendAngleOnInterface(Components.DOUBLEOBJBOX_131, 2, 500, 200, 500)
                                setInterfaceText(player, "<br>" + "Pop the correct balloon animal to find a key.", Components.DOUBLEOBJBOX_131, 1)
                            }
                            setAttribute(player, PrisonUtils.POP_KEY_VALUE, 1)
                        }

                        2 -> {
                            openInterface(player, Components.DOUBLEOBJBOX_131).also {
                                player.packetDispatch.sendModelOnInterface(10736, Components.DOUBLEOBJBOX_131, 2, 100)
                                player.packetDispatch.sendAngleOnInterface(Components.DOUBLEOBJBOX_131, 2, 500, 200, 500)
                                setInterfaceText(player, "<br>" + "Pop the correct balloon animal to find a key.", Components.DOUBLEOBJBOX_131, 1)
                            }
                            setAttribute(player, PrisonUtils.POP_KEY_VALUE, 2)
                        }

                        3 -> {
                            openInterface(player, Components.DOUBLEOBJBOX_131).also {
                                player.packetDispatch.sendModelOnInterface(10737, Components.DOUBLEOBJBOX_131, 2, 100)
                                player.packetDispatch.sendAngleOnInterface(Components.DOUBLEOBJBOX_131, 2, 500, 200, 500)
                                setInterfaceText(player, "<br>" + "Pop the correct balloon animal to find a key.", Components.DOUBLEOBJBOX_131, 1)
                            }
                            setAttribute(player, PrisonUtils.POP_KEY_VALUE, 3)
                        }
                    }
                }
            })
            return@on true
        }

        on(PrisonUtils.ANIMAL_ID, IntType.NPC, "pop") { player, node ->
            if (getUsedOption(player) == "pop") {
                if (node.id == 3119 && getAttribute(player, PrisonUtils.POP_KEY_VALUE, -1) == 0) {
                    player.incrementAttribute(PrisonUtils.POP_KEY)
                } else if (node.id == 3120 && getAttribute(player, PrisonUtils.POP_KEY_VALUE, -1) == 1) {
                    player.incrementAttribute(PrisonUtils.POP_KEY)
                } else if (node.id == 3121 && getAttribute(player, PrisonUtils.POP_KEY_VALUE, -1) == 2) {
                    player.incrementAttribute(PrisonUtils.POP_KEY)
                } else if (node.id == 3122 && getAttribute(player, PrisonUtils.POP_KEY_VALUE, -1) == 3) {
                    player.incrementAttribute(PrisonUtils.POP_KEY)
                } else {
                    setAttribute(player, PrisonUtils.POP_KEY_FALSE, true)
                }
            }
            queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
                when (stage) {
                    0 -> {
                        lock(player, 3)
                        visualize(player, 794,
                            Graphic(524, 0, 10)
                        )
                        playAudio(player, Sounds.POP3_3252, 5)
                        return@queueScript delayScript(player, 4)
                    }

                    1 -> {
                        animate(player, 827)
                        addItem(player, Items.PRISON_KEY_6966)
                        node.asNpc().clear()
                        face(player, findNPC(NPCs.PRISON_PETE_3118)!!.location)
                        openDialogue(player, PrisonPeteDialogue(dialOpt = 1))
                        return@queueScript stopExecuting(player)
                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }
    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(PrisonUtils.PRISON_ZONE)
    }

    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(ZoneRestriction.RANDOM_EVENTS, ZoneRestriction.CANNON, ZoneRestriction.FOLLOWERS)
    }

    override fun defineDestinationOverrides() {
        setDest(IntType.SCENERY, intArrayOf(Scenery.LEVER_10817), "pull") { _, _ ->
            return@setDest Location.create(2083, 4461, 0)
        }
        setDest(IntType.NPC, intArrayOf(NPCs.PRISON_PETE_3118), "talk-to") { _, _ ->
            return@setDest Location.create(2084, 4461, 0)
        }
    }

}
