package content.region.asgarnia.dialogue.portsarim

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.item.Item
import core.plugin.Initializable

/**
 * Represents the Klarense dialogue.
 */
@Initializable
class KlarenseDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Klarense is a sailor located on the second south-east
     * dock of Port Sarim. He is the owner of the Lady Lumbridge,
     * a ship which is in a state of disrepair.
     */

    private var quest: Quest? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        quest = player.getQuestRepository().getQuest("Dragon Slayer")
        if (args.size > 1) {
            npc(FacialExpression.ANGRY, "Hey, stay off my ship! That's private property!")
            stage = 0
            return true
        }
        when (quest!!.getStage(player)) {
            100 -> {
                player("Hey, that's my ship!")
                stage = -1
            }

            else -> {
                if (player.getSavedData().questData.getDragonSlayerAttribute("ship")) {
                    npc(FacialExpression.NEUTRAL, "Hello, captain! Here to inspect your new ship? Just a", "little work and she'll be seaworthy again.")
                    stage = 400
                    return true
                }
                npc(FacialExpression.NEUTRAL, "So, are you interested in buying the Lady Lumbridge?", "Now, I'll be straight with you: she's not quite seaworthy", "right now, but give her a bit of work and she'll be the", "nippiest ship this side of Port Khazard.")
                stage = 1
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (stage == 400) {
            end()
            return true
        }
        when (quest!!.getStage(player)) {
            100 -> when (stage) {
                0 -> end()
                -1 -> {
                    npc(FacialExpression.SUSPICIOUS, "No, it's not. It may, to the untrained eye, at first", "appear to be the Lady Lumbridge, but it is definitely", "not. It's my new ship. It just happens to look slightly", "similar, is all.")
                    stage = 1
                }

                1 -> {
                    player(FacialExpression.ANNOYED, "It has Lady Lumbridge pained out and 'Klarense's", "Cruiser' painted over it!")
                    stage = 2
                }

                2 -> {
                    npc(FacialExpression.SUSPICIOUS, "Nope, you're mistaken. It's my new boat.")
                    stage = 3
                }

                3 -> end()
            }

            20 -> when (stage) {
                0 -> end()
                1 -> {
                    player(FacialExpression.ASKING, "Would you take me to Crandor when she's ready?")
                    stage = 2
                }

                2 -> {
                    npc(FacialExpression.EXTREMELY_SHOCKED, "Crandor? You're joking, right?")
                    stage = 3
                }

                3 -> {
                    player(FacialExpression.NEUTRAL, "No. I want to go to Crandor.")
                    stage = 4
                }

                4 -> {
                    npc(FacialExpression.WORRIED, "Then you must be crazy.")
                    stage = 5
                }

                5 -> {
                    npc(FacialExpression.NEUTRAL, "That island is surrounded by reefs that would rip this", "ship to shreds. Even if you found a map, you'd need", "an experienced captain to stand a chance of getting", "through")
                    stage = 6
                }

                6 -> {
                    npc(FacialExpression.NEUTRAL, "even if you gould get to it, there's no way I'm going", "any closer to that dragon than I have to. They say it", "can destroy whole ships with one bite.")
                    stage = 7
                }

                7 -> {
                    player(FacialExpression.NEUTRAL, "I'd like to buy her.")
                    stage = 8
                }

                8 -> {
                    npc(FacialExpression.HAPPY, "Of course! I'm sure the work needed to do on it", "wouldn't be too expensive.")
                    stage = 9
                }

                9 -> {
                    npc(FacialExpression.HAPPY, "How does, 2,000 gold sound? I'll even throw in my", "cabin boy, Jenkins, for free! He'll swab the decks and", "splice the mainsails for you!")
                    stage = 10
                }

                10 -> {
                    player(FacialExpression.HAPPY, "Yep, sounds good.")
                    stage = 11
                }

                11 -> if (!player.inventory.containsItem(COINS)) {
                    player(FacialExpression.HALF_GUILTY, "...except I don't have that kind of money on me...")
                    stage = 12
                } else {
                    if (!player.inventory.containsItem(COINS)) {
                        end()
                        return false
                    }
                    if (player.inventory.remove(COINS)) {
                        npc(FacialExpression.HAPPY, "Okey dokey, she's all yours!")
                        player.getSavedData().questData.setDragonSlayerAttribute("ship", true)
                        stage = 12
                    }
                }

                12 -> end()
            }

            else -> when (stage) {
                0 -> end()
                1 -> {
                    options("Do you know when she will be seaworthy?", "Why is she damaged?", "Ah, well, never mind.")
                    stage = 2
                }

                2 -> when (buttonId) {
                    1 -> {
                        player(FacialExpression.ASKING, "Do you know when she will be seaworthy?")
                        stage = 10
                    }

                    2 -> {
                        player(FacialExpression.ASKING, "Why is she damaged?")
                        stage = 20
                    }

                    3 -> {
                        player(FacialExpression.HALF_GUILTY, "Ah, well, never mind.")
                        stage = 30
                    }
                }

                10 -> {
                    npc(FacialExpression.HALF_GUILTY, "No, not really. Port Sarim's shipbuilders aren't very", "efficient so it could be quite a while.")
                    stage = 11
                }

                11 -> end()
                20 -> {
                    npc(FacialExpression.HALF_GUILTY, "Oh, there was no particular accident. It's just years of", "wear and tear.")
                    stage = 21
                }

                21 -> {
                    npc(FacialExpression.NEUTRAL, "The Lady Lumbridge is an old Crandorian fishing ship -", "the last one of her kind, as far as I know. That kind of", "ship was always mightily manoeuvrable, but not too", "tough.")
                    stage = 22
                }

                22 -> {
                    npc(FacialExpression.NEUTRAL, "She happened to be somewhere else when Crandor was", "destroyed, and she's had several owners since then. Not", "all of them have looked after her too well,")
                    stage = 23
                }

                23 -> {
                    npc(FacialExpression.NEUTRAL, "but once she's patched up, she'll be good as new!")
                    stage = 24
                }

                24 -> end()
                30 -> end()
                400 -> end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KLARENSE_744)
    }

    companion object {
        private val COINS = Item(995, 2000)
    }
}
