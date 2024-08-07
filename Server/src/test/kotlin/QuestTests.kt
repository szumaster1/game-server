import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.player.link.quest.QuestRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Quest tests
 *
 * @constructor Quest tests
 */
class QuestTests {
    lateinit var testPlayer: MockPlayer

    init {
        TestUtils.preTestSetup()
        testPlayer = TestUtils.getMockPlayer("test")
    }

    /**
     * Test quest
     *
     * @constructor Test quest
     */
    class TestQuest : Quest("Test Quest", 0, 0, 1, 1, 0, 1, 2) {
        override fun newInstance(`object`: Any?): Quest {
            return this
        }
    }

    val testQuest = TestQuest()

    /**
     * Get index should not throw exception
     *
     */
    @Test
    fun getIndexShouldNotThrowException() {
        Assertions.assertDoesNotThrow {
            testQuest.index
        }
    }

    /**
     * Register should make quest immediately available
     *
     */
    @Test
    fun registerShouldMakeQuestImmediatelyAvailable() {
        QuestRepository.register(testQuest)
        Assertions.assertNotNull(QuestRepository.getQuests()[testQuest.name])
    }

    /**
     * Register should make quest immediately available to instances
     *
     */
    @Test
    fun registerShouldMakeQuestImmediatelyAvailableToInstances() {
        QuestRepository.register(testQuest)
        val instance = QuestRepository(testPlayer)
        Assertions.assertNotNull(instance.getQuest(testQuest.name))
    }

    /**
     * Get stage on unstarted quest should not throw exception
     *
     */
    @Test
    fun getStageOnUnstartedQuestShouldNotThrowException() {
        QuestRepository.register(testQuest)
        val instance = QuestRepository(testPlayer)
        Assertions.assertDoesNotThrow {
            instance.getStage(testQuest)
        }
    }

    /**
     * Set stage on unstarted quest should not throw exception
     *
     */
    @Test
    fun setStageOnUnstartedQuestShouldNotThrowException() {
        QuestRepository.register(testQuest)
        val instance = QuestRepository(testPlayer)
        Assertions.assertDoesNotThrow {
            instance.setStage(testQuest, 10)
        }
    }

    /**
     * Complete quest should throw exception if already complete
     *
     */
    @Test
    fun completeQuestShouldThrowExceptionIfAlreadyComplete() {
        Assertions.assertThrows(IllegalStateException::class.java, {
            QuestRepository.register(testQuest)
            val repo = QuestRepository(testPlayer)
            repo.getQuest("Test Quest").finish(testPlayer)
            repo.getQuest("Test Quest").finish(testPlayer)
        }, "Quest completed twice without throwing an exception or threw wrong exception!")
    }
}