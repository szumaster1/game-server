package core.game.dialogue

/**
 * Facial expression
 *
 * @property animationId
 * @constructor Facial expression
 */
enum class FacialExpression(val animationId: Int) {

    /**
     * Old Happy
     *
     * @constructor Old Happy
     */
    OLD_HAPPY(588),

    /**
     * Old Calm Talk1
     *
     * @constructor Old Calm Talk1
     */
    OLD_CALM_TALK1(589),

    /**
     * Old Calm Talk2
     *
     * @constructor Old Calm Talk2
     */
    OLD_CALM_TALK2(590),

    /**
     * Old Default
     *
     * @constructor Old Default
     */
    OLD_DEFAULT(591),

    /**
     * Old Evil1
     *
     * @constructor Old Evil1
     */
    OLD_EVIL1(592),

    /**
     * Old Evil2
     *
     * @constructor Old Evil2
     */
    OLD_EVIL2(593),

    /**
     * Old Normal
     *
     * @constructor Old Normal
     */
    OLD_NORMAL(594),

    /**
     * Old Sneaky
     *
     * @constructor Old Sneaky
     */
    OLD_SNEAKY(595),

    /**
     * Old Distressed
     *
     * @constructor Old Distressed
     */
    OLD_DISTRESSED(596),

    /**
     * Old Distressed2
     *
     * @constructor Old Distressed2
     */
    OLD_DISTRESSED2(597),

    /**
     * Old Almost Crying
     *
     * @constructor Old Almost Crying
     */
    OLD_ALMOST_CRYING(598),

    /**
     * Old Bows Head Sad
     *
     * @constructor Old Bows Head Sad
     */
    OLD_BOWS_HEAD_SAD(599),

    /**
     * Old Drunk Left
     *
     * @constructor Old Drunk Left
     */
    OLD_DRUNK_LEFT(600),

    /**
     * Old Drunk Right
     *
     * @constructor Old Drunk Right
     */
    OLD_DRUNK_RIGHT(601),

    /**
     * Old Not Interested
     *
     * @constructor Old Not Interested
     */
    OLD_NOT_INTERESTED(602),

    /**
     * Old Sleepy
     *
     * @constructor Old Sleepy
     */
    OLD_SLEEPY(603),

    /**
     * Old Plain Evil
     *
     * @constructor Old Plain Evil
     */
    OLD_PLAIN_EVIL(604),

    /**
     * Old Laugh1
     *
     * @constructor Old Laugh1
     */
    OLD_LAUGH1(605),

    /**
     * Old Laugh2
     *
     * @constructor Old Laugh2
     */
    OLD_LAUGH2(606),

    /**
     * Old Laugh3
     *
     * @constructor Old Laugh3
     */
    OLD_LAUGH3(607),

    /**
     * Old Laugh4
     *
     * @constructor Old Laugh4
     */
    OLD_LAUGH4(608),

    /**
     * Old Evil Laugh
     *
     * @constructor Old Evil Laugh
     */
    OLD_EVIL_LAUGH(609),

    /**
     * Old Sad
     *
     * @constructor Old Sad
     */
    OLD_SAD(610),

    /**
     * Old More Sad
     *
     * @constructor Old More Sad
     */
    OLD_MORE_SAD(611),

    /**
     * Old On One Hand
     *
     * @constructor Old On One Hand
     */
    OLD_ON_ONE_HAND(612),

    /**
     * Old Nearly Crying
     *
     * @constructor Old Nearly Crying
     */
    OLD_NEARLY_CRYING(613),

    /**
     * Old Angry1
     *
     * @constructor Old Angry1
     */
    OLD_ANGRY1(614),

    /**
     * Old Angry2
     *
     * @constructor Old Angry2
     */
    OLD_ANGRY2(615),

    /**
     * Old Angry3
     *
     * @constructor Old Angry3
     */
    OLD_ANGRY3(616),

    /**
     * Old Angry4
     *
     * @constructor Old Angry4
     */
    OLD_ANGRY4(617),

    /**
     * Surely Not
     *
     * @constructor Surely Not
     */
    SURELY_NOT(9740),

    /**
     * Nod Yes
     *
     * @constructor Nod Yes
     */
    NOD_YES(9741),

    /**
     * Nod No
     *
     * @constructor Nod No
     */
    NOD_NO(9742),

    /**
     * Worried
     *
     * @constructor Worried
     */
    WORRIED(9743),

    /**
     * Half Worried
     *
     * @constructor Half Worried
     */
    HALF_WORRIED(9745),

    /**
     * Amazed
     *
     * @constructor Amazed
     */
    AMAZED(9746),

    /**
     * Extremely Shocked
     *
     * @constructor Extremely Shocked
     */
    EXTREMELY_SHOCKED(9750),

    /**
     * Guilty
     *
     * @constructor Guilty
     */
    GUILTY(9758),

    /**
     * Half Guilty
     *
     * @constructor Half Guilty
     */
    HALF_GUILTY(9760),

    /**
     * Sad
     *
     * @constructor Sad
     */
    SAD(9761),

    /**
     * Crying
     *
     * @constructor Crying
     */
    CRYING(9765),

    /**
     * Half Crying
     *
     * @constructor Half Crying
     */
    HALF_CRYING(9768),

    /**
     * Afraid
     *
     * @constructor Afraid
     */
    AFRAID(9772),

    /**
     * Scared
     *
     * @constructor Scared
     */
    SCARED(9776),

    /**
     * Panicked
     *
     * @constructor Panicked
     */
    PANICKED(9780),

    /**
     * Annoyed
     *
     * @constructor Annoyed
     */
    ANNOYED(9784),

    /**
     * Angry
     *
     * @constructor Angry
     */
    ANGRY(9785),

    /**
     * Furious
     *
     * @constructor Furious
     */
    FURIOUS(9792),

    /**
     * Angry With Smile
     *
     * @constructor Angry With Smile
     */
    ANGRY_WITH_SMILE(9796),

    /**
     * Angry With Smile And Evil Eye
     *
     * @constructor Angry With Smile And Evil Eye
     */
    ANGRY_WITH_SMILE_AND_EVIL_EYE(9798),

    /**
     * Sleeping
     *
     * @constructor Sleeping
     */
    SLEEPING(9802),

    /**
     * Silent
     *
     * @constructor Silent
     */
    SILENT(9804),

    /**
     * Neutral
     *
     * @constructor Neutral
     */
    NEUTRAL(9808),

    /**
     * Thinking
     *
     * @constructor Thinking
     */
    THINKING(9812),

    /**
     * Half Thinking
     *
     * @constructor Half Thinking
     */
    HALF_THINKING(9814),

    /**
     * Disgusted
     *
     * @constructor Disgusted
     */
    DISGUSTED(9816),

    /**
     * Disgusted Head Shake
     *
     * @constructor Disgusted Head Shake
     */
    DISGUSTED_HEAD_SHAKE(9823),

    /**
     * Asking
     *
     * @constructor Asking
     */
    ASKING(9827),

    /**
     * Half Asking
     *
     * @constructor Half Asking
     */
    HALF_ASKING(9830),

    /**
     * Rolling Eyes
     *
     * @constructor Rolling Eyes
     */
    ROLLING_EYES(9831),

    /**
     * Half Rolling Eyes
     *
     * @constructor Half Rolling Eyes
     */
    HALF_ROLLING_EYES(9834),

    /**
     * Drunk
     *
     * @constructor Drunk
     */
    DRUNK(9835),

    /**
     * Suspicious
     *
     * @constructor Suspicious
     */
    SUSPICIOUS(9836),

    /**
     * Laugh
     *
     * @constructor Laugh
     */
    LAUGH(9840),

    /**
     * Loudly Laughing
     *
     * @constructor Loudly Laughing
     */
    LOUDLY_LAUGHING(9841),

    /**
     * Evil Laugh
     *
     * @constructor Evil Laugh
     */
    EVIL_LAUGH(9842),

    /**
     * Cheer Talk
     *
     * @constructor Cheer Talk
     */
    CHEER_TALK(9843),

    /**
     * Friendly
     *
     * @constructor Friendly
     */
    FRIENDLY(9844),

    /**
     * Happy
     *
     * @constructor Happy
     */
    HAPPY(9847),

    /**
     * Jolly
     *
     * @constructor Jolly
     */
    JOLLY(9851),

    /**
     * Struggle
     *
     * @constructor Struggle
     */
    STRUGGLE(9865),

    /**
     * Calm Talk
     *
     * @constructor Calm Talk
     */
    CALM_TALK(9810),

    /**
     * Mad
     *
     * @constructor Mad
     */
    MAD(9789),

    /**
     * Calm
     *
     * @constructor Calm
     */
    CALM(9805),

    /**
     * Cheer Yes
     *
     * @constructor Cheer Yes
     */
    CHEER_YES(9864),

    /**
     * Child Evil Laugh
     *
     * @constructor Child Evil Laugh
     */
    CHILD_EVIL_LAUGH(7171),

    /**
     * Child Friendly
     *
     * @constructor Child Friendly
     */
    CHILD_FRIENDLY(7172),

    /**
     * Child Normal
     *
     * @constructor Child Normal
     */
    CHILD_NORMAL(7173),

    /**
     * Child Neutral
     *
     * @constructor Child Neutral
     */
    CHILD_NEUTRAL(7174),

    /**
     * Child Loudly Laughing
     *
     * @constructor Child Loudly Laughing
     */
    CHILD_LOUDLY_LAUGHING(7175),

    /**
     * Child Thinking
     *
     * @constructor Child Thinking
     */
    CHILD_THINKING(7176),

    /**
     * Child Sad
     *
     * @constructor Child Sad
     */
    CHILD_SAD(7177),

    /**
     * Child Guilty
     *
     * @constructor Child Guilty
     */
    CHILD_GUILTY(7178),

    /**
     * Child Suspicious
     *
     * @constructor Child Suspicious
     */
    CHILD_SUSPICIOUS(7179)
}
