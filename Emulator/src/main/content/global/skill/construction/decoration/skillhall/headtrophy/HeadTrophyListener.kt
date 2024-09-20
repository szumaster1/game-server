package content.global.skill.construction.decoration.skillhall.headtrophy

import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Scenery

/**
 * Head trophy listener.
 */
class HeadTrophyListener : InteractionListener {
    private val crawlingTrophy = Scenery.CRAWLING_HAND_13481
    private val cockatriceTrophy = Scenery.COCKATRICE_HEAD_13482
    private val basiliskTrophy = Scenery.BASILISK_HEAD_13483
    private val kuraskTrophy = Scenery.KURASK_HEAD_13484
    private val abyssalTrophy = Scenery.ABYSSAL_DEMON_HEAD_13485
    private val kdbTrophy = Scenery.KBD_HEADS_13486
    private val kqTrophy = Scenery.KQ_HEAD_13487

    override fun defineListeners() {
        on(crawlingTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, CrawlingHeadDialogue(), node)
            return@on true
        }

        on(cockatriceTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, CockatriceHeadDialogue(), node)
            return@on true
        }

        on(basiliskTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, BasiliskHeadDialogue(), node)
            return@on true
        }

        on(kuraskTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, KuraskHeadDialogue(), node)
            return@on true
        }

        on(abyssalTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, AbyssalHeadDialogue(), node)
            return@on true
        }

        on(kdbTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, KBDHeadDialogue(), node)
            return@on true
        }

        on(kqTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, KalphiteHeadDialogue(), node)
            return@on true
        }
    }
}