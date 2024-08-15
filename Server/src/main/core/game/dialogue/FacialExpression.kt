package core.game.dialogue

/**
 * Enum class representing different facial expressions.
 *
 * @param animationId Unique identifier for the animation associated with the facial expression.
 * @constructor Initializes a FacialExpression with a specific animation ID.
 */
enum class FacialExpression(val animationId: Int) {

    /**
     * Old Happy.
     */
    OLD_HAPPY(588),

    /**
     * Old Calm Talk 1.
     */
    OLD_CALM_TALK1(589),

    /**
     * Old Calm Talk 2.
     */
    OLD_CALM_TALK2(590),

    /**
     * Old Default.
     */
    OLD_DEFAULT(591),

    /**
     * Old Evil 1.
     */
    OLD_EVIL1(592),

    /**
     * Old Evil 2.
     */
    OLD_EVIL2(593),

    /**
     * Old Normal.
     */
    OLD_NORMAL(594),

    /**
     * Old Sneaky.
     */
    OLD_SNEAKY(595),

    /**
     * Old Distressed.
     */
    OLD_DISTRESSED(596),

    /**
     * Old Distressed 2.
     */
    OLD_DISTRESSED2(597),

    /**
     * Old Almost Crying.
     */
    OLD_ALMOST_CRYING(598),

    /**
     * Old Bows Head Sad.
     */
    OLD_BOWS_HEAD_SAD(599),

    /**
     * Old Drunk Left.
     */
    OLD_DRUNK_LEFT(600),

    /**
     * Old Drunk Right.
     */
    OLD_DRUNK_RIGHT(601),

    /**
     * Old Not Interested.
     */
    OLD_NOT_INTERESTED(602),

    /**
     * Old Sleepy.
     */
    OLD_SLEEPY(603),

    /**
     * Old Plain Evil.
     */
    OLD_PLAIN_EVIL(604),

    /**
     * Old Laugh 1,
     */
    OLD_LAUGH1(605),

    /**
     * Old Laugh 2.
     */
    OLD_LAUGH2(606),

    /**
     * Old Laugh 3.
     */
    OLD_LAUGH3(607),

    /**
     * Old Laugh 4.
     */
    OLD_LAUGH4(608),

    /**
     * Old Evil Laugh.
     */
    OLD_EVIL_LAUGH(609),

    /**
     * Old Sad.
     */
    OLD_SAD(610),

    /**
     * Old More Sad.
     */
    OLD_MORE_SAD(611),

    /**
     * Old On One Hand.
     */
    OLD_ON_ONE_HAND(612),

    /**
     * Old Nearly Crying.
     */
    OLD_NEARLY_CRYING(613),

    /**
     * Old Angry 1.
     */
    OLD_ANGRY1(614),

    /**
     * Old Angry 2.
     */
    OLD_ANGRY2(615),

    /**
     * Old Angry 3.
     */
    OLD_ANGRY3(616),

    /**
     * Old Angry 4.
     */
    OLD_ANGRY4(617),

    /**
     * Surely Not.
     */
    SURELY_NOT(9740),

    /**
     * Nod Yes.
     */
    NOD_YES(9741),

    /**
     * Nod No.
     */
    NOD_NO(9742),

    /**
     * Worried.
     */
    WORRIED(9743),

    /**
     * Half Worried.
     */
    HALF_WORRIED(9745),

    /**
     * Amazed.
     */
    AMAZED(9746),

    /**
     * Extremely Shocked.
     */
    EXTREMELY_SHOCKED(9750),

    /**
     * Guilty.
     */
    GUILTY(9758),

    /**
     * Half Guilty.
     */
    HALF_GUILTY(9760),

    /**
     * Sad.
     */
    SAD(9761),

    /**
     * Crying.
     */
    CRYING(9765),

    /**
     * Half Crying.
     */
    HALF_CRYING(9768),

    /**
     * Afraid.
     */
    AFRAID(9772),

    /**
     * Scared.
     */
    SCARED(9776),

    /**
     * Panicked.
     */
    PANICKED(9780),

    /**
     * Annoyed.
     */
    ANNOYED(9784),

    /**
     * Angry.
     */
    ANGRY(9785),

    /**
     * Furious.
     */
    FURIOUS(9792),

    /**
     * Angry With Smile.
     */
    ANGRY_WITH_SMILE(9796),

    /**
     * Angry With Smile And Evil Eye.
     */
    ANGRY_WITH_SMILE_AND_EVIL_EYE(9798),

    /**
     * Sleeping.
     */
    SLEEPING(9802),

    /**
     * Silent.
     */
    SILENT(9804),

    /**
     * Neutral.
     */
    NEUTRAL(9808),

    /**
     * Thinking.
     */
    THINKING(9812),

    /**
     * Half Thinking.
     */
    HALF_THINKING(9814),

    /**
     * Disgusted.
     */
    DISGUSTED(9816),

    /**
     * Disgusted Head Shake.
     */
    DISGUSTED_HEAD_SHAKE(9823),

    /**
     * Asking.
     */
    ASKING(9827),

    /**
     * Half Asking.
     */
    HALF_ASKING(9830),

    /**
     * Rolling Eyes.
     */
    ROLLING_EYES(9831),

    /**
     * Half Rolling Eyes.
     */
    HALF_ROLLING_EYES(9834),

    /**
     * Drunk.
     */
    DRUNK(9835),

    /**
     * Suspicious.
     */
    SUSPICIOUS(9836),

    /**
     * Laugh.
     */
    LAUGH(9840),

    /**
     * Loudly Laughing.
     */
    LOUDLY_LAUGHING(9841),

    /**
     * Evil Laugh.
     */
    EVIL_LAUGH(9842),

    /**
     * Cheer Talk.
     */
    CHEER_TALK(9843),

    /**
     * Friendly.
     */
    FRIENDLY(9844),

    /**
     * Happy.
     */
    HAPPY(9847),

    /**
     * Jolly.
     */
    JOLLY(9851),

    /**
     * Struggle.
     */
    STRUGGLE(9865),

    /**
     * Calm Talk.
     */
    CALM_TALK(9810),

    /**
     * Mad.
     */
    MAD(9789),

    /**
     * Calm.
     */
    CALM(9805),

    /**
     * Cheer Yes.
     */
    CHEER_YES(9864),

    /**
     * Child Evil Laugh.
     */
    CHILD_EVIL_LAUGH(7171),

    /**
     * Child Friendly.
     */
    CHILD_FRIENDLY(7172),

    /**
     * Child Normal.
     */
    CHILD_NORMAL(7173),

    /**
     * Child Neutral.
     */
    CHILD_NEUTRAL(7174),

    /**
     * Child Loudly Laughing.
     */
    CHILD_LOUDLY_LAUGHING(7175),

    /**
     * Child Thinking.
     */
    CHILD_THINKING(7176),

    /**
     * Child Sad.
     */
    CHILD_SAD(7177),

    /**
     * Child Guilty.
     */
    CHILD_GUILTY(7178),

    /**
     * Child Suspicious.
     */
    CHILD_SUSPICIOUS(7179)
}
