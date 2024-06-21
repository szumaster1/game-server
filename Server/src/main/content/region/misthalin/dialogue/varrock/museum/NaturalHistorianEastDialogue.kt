package content.region.misthalin.dialogue.varrock.museum

import core.api.consts.NPCs
import core.api.*
import core.api.utils.PlayerCamera
import core.game.dialogue.DialogueFile
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class NaturalHistorianEastDialogue(player: Player? = null) : Dialogue(player) {

    /*
     *  Info: Biologist that have been hired by the Varrock Museum to educate visitors
     *  and gather further information regarding Gielinor's diverse animal kingdom.
     *  Location: 1774,4960
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.ASKING, "Hello again, " + if (player.isMale) "sir" else "madam" + ", how can I help you on this fine day?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.ASKING, "I was hoping you could tell me about something.").also { stage++ }
            1 -> options("Tell me about snails.", "Tell me about monkeys.", "Tell me about sea slugs.", "Tell me about snakes.", "That's enough education for one day.").also { stage++ }

            2 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_ASKING, "Tell me about snails.").also { stage++ }
                2 -> player(FacialExpression.HALF_ASKING, "Tell me about monkeys.").also { stage = 6 }
                3 -> player(FacialExpression.HALF_ASKING, "Tell me about sea slugs.").also { stage = 9 }
                4 -> player(FacialExpression.HALF_ASKING, "Tell me about snakes.").also { stage = 12 }
                5 -> player(FacialExpression.HALF_ASKING, "That's enough education for one day.").also { stage = 15 }
            }

            3 -> npcl(FacialExpression.HALF_GUILTY, "Ahh snails, the gelatinous gastropods.").also { stage++ }
            4 -> npcl(FacialExpression.HALF_GUILTY, "If you just follow me to the display case I shall explain all about them.").also { stage++ }

            5 -> {
                end()
                MuseumUtils.initLearning(player)
                teleport(player, location(1776, 4962, 0))
                runTask(player, 1) {
                    face(player, location(1775, 4965, 0))
                    PlayerCamera(player).panTo(1771, 4961, 400, 300)
                    PlayerCamera(player).rotateTo(1777, 4968, 400, 300)
                    openDialogue(player, TalkAboutSnails())
                }
            }

            6 -> npcl(FacialExpression.HALF_GUILTY, "Ahh monkeys, the simian collective.").also { stage++ }
            7 -> npcl(FacialExpression.HALF_GUILTY, "If you just follow me to the display case I shall explain all about them.").also { stage++ }

            8 -> {
                end()
                MuseumUtils.initLearning(player)
                teleport(player, location(1774, 4958, 0))
                runTask(player, 1) {
                    face(player, location(1775, 4955, 0))
                    PlayerCamera(player).panTo(1771, 4959, 400, 300)
                    PlayerCamera(player).rotateTo(1778, 4951, 400, 300)
                    openDialogue(player, TalkAboutMonkeys())
                }
            }

            9 -> npcl(FacialExpression.HALF_GUILTY, "Ahh sea slugs, the cute crustaceans.").also { stage++ }
            10 -> npcl(FacialExpression.HALF_GUILTY, "If you just follow me to the display case I shall explain all about them.").also { stage++ }

            11 -> {
                end()
                MuseumUtils.initLearning(player)
                teleport(player, location(1781, 4958, 0))
                runTask(player, 1) {
                    face(player, location(1782, 4955, 0))
                    PlayerCamera(player).panTo(1778, 4959, 400, 300)
                    PlayerCamera(player).rotateTo(1785, 4951, 400, 300)
                    openDialogue(player, TalkAboutSeaSlugs())
                }
            }


            12 -> npcl(FacialExpression.HALF_GUILTY, "Ahh snakes, the slithering squamata.").also { stage++ }
            13 -> npcl(FacialExpression.HALF_GUILTY, "If you just follow me to the display case I shall explain all about them.").also { stage++ }

            14 -> {
                end()
                MuseumUtils.initLearning(player)
                teleport(player, location(1783, 4962, 0))
                runTask(player, 1) {
                    face(player, location(1782, 4965, 0))
                    PlayerCamera(player).panTo(1786, 4961, 400, 300)
                    PlayerCamera(player).rotateTo(1779, 4969, 400, 300)
                    openDialogue(player, TalkAboutSnakes())
                }
            }

            15 -> npc("Nonsense! There's always room for more.", " And remember, science isn't dull!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.NATURAL_HISTORIAN_5967)
    }

}

class TalkAboutSnails : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.NATURAL_HISTORIAN_5967)
        when (stage) {
            0 -> npcl(
                FacialExpression.HALF_GUILTY,
                "Snails move like worms by squishing up and stretching out, very, very slowly. They also make a lot of slime, in order to aid moving by reducing friction."
            ).also { stage++ }

            1 -> npcl(
                FacialExpression.HALF_GUILTY,
                "They also use slime for protection. For instance, snails can use their slime to crawl over razor-blades without being hurt. It also helps keep away dangerous insects like ants."
            ).also { stage++ }

            2 -> npcl(
                FacialExpression.HALF_GUILTY,
                "When they hide in their shells, snails secrete a special type of slime, which dries to cover the entrance of their shells like a 'trapdoor'."
            ).also { stage++ }

            3 -> npcl(
                FacialExpression.HALF_GUILTY,
                "This is called an operculum. The snails of Morytania are the most malignant molluscs ever to have been studied."
            ).also { stage++ }

            4 -> npcl(
                FacialExpression.HALF_GUILTY,
                "They are broken down into two distinct species: achatina acidia and achatina acidia giganteus or, as they are more commonly known, the acid-spitting snail and the giant"
            ).also { stage++ }

            5 -> npcl(
                FacialExpression.HALF_GUILTY,
                "acid-spitting snail. Both of these varieties are voracious carnivores, using their mutated mouthpieces to spit a glob of powerful acid to kill their foe."
            ).also { stage++ }

            6 -> npcl(
                FacialExpression.HALF_GUILTY,
                "They then simply have to wait, whilst the digestive juices make short work of the poor creature. Then, they simply slurp up what remains."
            ).also { stage++ }

            7 -> npcl(
                FacialExpression.HALF_GUILTY,
                "How these strange creatures came to be is still something of a mystery. The most prevalent theory suggests that they mutated,"
            ).also { stage++ }

            8 -> npcl(
                FacialExpression.HALF_GUILTY,
                "as a reaction to an 'as yet unknown' pollutant that has appeared in the swamps. The local populace has capitalised on the appearance of these strange species,"
            ).also { stage++ }

            9 -> npcl(
                FacialExpression.HALF_GUILTY,
                "using their shells to fashion a rudimentary helm that is fairly resistant to the snails acid. Other known uses of snail by-products include"
            ).also { stage++ }

            10 -> npcl(FacialExpression.HALF_GUILTY, "a tasty local delicacy and a fireproof oil.").also { stage++ }
            11 -> npcl(
                FacialExpression.HALF_GUILTY,
                "And this concludes my short lecture on snails. I hope you've enjoyed yourselves."
            ).also { stage++ }

            12 -> {
                end()
                unlock(player!!)
                PlayerCamera(player).reset()
            }
        }
    }

}

class TalkAboutMonkeys : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.NATURAL_HISTORIAN_5967)
        when (stage) {
            0 -> npcl(
                FacialExpression.HALF_GUILTY,
                "A monkey is a member of either of two known groupings of simian primates. These two known groupings are the Karamja monkeys and the 'harmless' monkeys."
            ).also { stage++ }

            1 -> npcl(
                FacialExpression.HALF_GUILTY,
                "Because of their similarity to monkeys, apes such as chimpanzees and gibbons are often called monkeys by accident."
            ).also { stage++ }

            2 -> npcl(
                FacialExpression.HALF_GUILTY,
                "Though some natural historians don't consider them to be monkeys. Also, a few monkey species have the word 'ape' in their common name."
            ).also { stage++ }

            3 -> npcl(
                FacialExpression.HALF_GUILTY,
                "The Karamja monkeys are rumoured to be fairly cunning and intelligent creatures, although rumours that they have learned human speech is anecdotal at best."
            ).also { stage++ }

            4 -> npcl(
                FacialExpression.HALF_GUILTY,
                "In appearance, they stand much shorter than a human and tend to move in a hunched fashion. Karamja monkeys also sport a red mohawk,"
            ).also { stage++ }

            5 -> npcl(
                FacialExpression.HALF_GUILTY,
                "though it is unknown whether this is an affectation or not. They are very fond of bananas and bitternuts, eating them in huge quantities"
            ).also { stage++ }

            6 -> npcl(
                FacialExpression.HALF_GUILTY,
                "whenever they can get their paws on them. The harmless monkeys of Mos Le'Harmless are a very similar, but in some ways entirely different, breed."
            ).also { stage++ }

            7 -> npcl(
                FacialExpression.HALF_GUILTY,
                "They stand roughly the same size but are a lighter colour. Interestingly, Karamaja monkeys have a deep dislike of seaweed,"
            ).also { stage++ }

            8 -> npcl(
                FacialExpression.HALF_GUILTY,
                "though this may stem from the actions of a number of irresponsible people."
            ).also { stage++ }

            9 -> npcl(
                FacialExpression.HALF_GUILTY,
                "And this concludes my short lecture on monkeys. I hope you've enjoyed yourselves."
            ).also { stage++ }

            10 -> {
                end()
                unlock(player!!)
                PlayerCamera(player).reset()
            }
        }
    }

}

class TalkAboutSeaSlugs : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.NATURAL_HISTORIAN_5967)
        when (stage) {
            0 -> npcl(
                FacialExpression.HALF_GUILTY,
                "The term 'sea slug' is something of a misnomer. Whilst these small creatures have a soft body like that of a slug, they also possess a very hard shell like a snail."
            ).also { stage++ }

            1 -> npcl(
                FacialExpression.HALF_GUILTY,
                "Very little is actually known about the sea slug, as we have, as yet, been unable to procure a sample for observation and study."
            ).also { stage++ }

            2 -> npcl(
                FacialExpression.HALF_GUILTY,
                "For some reason all expeditions we have sent have either vanished mysteriously, or those on the expedition have sent letters"
            ).also { stage++ }

            3 -> npcl(
                FacialExpression.HALF_GUILTY,
                "back announcing their desire to leave the Museum and go on to other things. It is presumed that the species is native to the very deep waters around the eastern Ardougne coastline."
            ).also { stage++ }

            4 -> npcl(
                FacialExpression.HALF_GUILTY,
                "There must be some natural consts in the area that the sea slugs are using, as the underwater habitat there is much the same around many coastal areas on Gielinor."
            ).also { stage++ }

            5 -> npcl(
                FacialExpression.HALF_GUILTY,
                "Through looking at similar species we have determined that the sea slug is a harmless little creature. It spends much of its life grazing on seaweed and other plant life."
            ).also { stage++ }

            6 -> npcl(
                FacialExpression.HALF_GUILTY,
                "There are reports that these reclusive animals have two large fangs at their front, though this is assumed to be either for decorative or defensive purposes."
            ).also { stage++ }

            7 -> npcl(
                FacialExpression.HALF_GUILTY,
                "If they do follow the same pattern as other similar creatures, the shell will be nigh on impervious to most attacks. The exposed soft skin may have a number of nematocysts,"
            ).also { stage++ }

            8 -> npcl(
                FacialExpression.HALF_GUILTY,
                "or stinging organs, similar to jellyfish. It is typical of prey animals such as these to develop some kind of unique defence mechanism that allows them to survive."
            ).also { stage++ }

            9 -> npcl(
                FacialExpression.HALF_GUILTY,
                "If only we could acquire one for study. I'm sure we would find this mechanism to be truly unique."
            ).also { stage++ }

            10 -> npcl(
                FacialExpression.HALF_GUILTY,
                "And this concludes my short lecture on sea slugs. I hope you've enjoyed yourselves."
            ).also { stage++ }

            11 -> {
                end()
                unlock(player!!)
                PlayerCamera(player).reset()
            }
        }
    }

}

class TalkAboutSnakes : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.NATURAL_HISTORIAN_5967)
        when (stage) {
            0 -> npcl(
                FacialExpression.HALF_GUILTY,
                "Serpentes, or ophidia, is the suborder under squamata that snakes belong to, like a big family."
            ).also { stage++ }

            1 -> npcl(
                FacialExpression.HALF_GUILTY,
                "Unlike lizards, snakes have no limbs whatsoever; not that this limits them. Snakes live in or near every habitat in the world."
            ).also { stage++ }

            2 -> npcl(
                FacialExpression.HALF_GUILTY,
                "Found in nice tropical forests, temperate latitudes and even in the ocean - some have adapted specialised stomach juices that they inject into their prey as venom,"
            ).also { stage++ }

            3 -> npcl(
                FacialExpression.HALF_GUILTY,
                "while others prefer to grab and crush their food. While others still are fast hunters who use speed and strength to overcome their prey."
            ).also { stage++ }

            4 -> npcl(
                FacialExpression.HALF_GUILTY,
                "The sense of smell in snakes has been enhanced in an amazing way. With most animals, like you and me, tiny particles are filtered through the nose."
            ).also { stage++ }

            5 -> npcl(
                FacialExpression.HALF_GUILTY,
                "However, instead of using just their nose, these animals use their tongues as well. When a lizard or a snake wants to smell it's surroundings,"
            ).also { stage++ }

            6 -> npcl(
                FacialExpression.HALF_GUILTY,
                "it will wave its tongue around and pick up the particles in the air. The tongue then returns to the mouth and the tips of the tongue"
            ).also { stage++ }

            7 -> npcl(
                FacialExpression.HALF_GUILTY,
                "are pushed up against two tiny pits in the roof of the snake's mouth. Since these pits are split apart from each other, the tongue itself also has to split."
            ).also { stage++ }

            8 -> npcl(
                FacialExpression.HALF_GUILTY,
                "This is why snakes have forked tongues. So the next time you see a snake sticking it's tongue out at you,"
            ).also { stage++ }

            9 -> npcl(
                FacialExpression.HALF_GUILTY,
                "remember, it's sniffing the air, not trying to bite you. And this concludes my short lecture on snakes. I hope you've enjoyed yourselves."
            ).also { stage++ }

            10 -> {
                end()
                unlock(player!!)
                PlayerCamera(player).reset()
            }
        }
    }

}
