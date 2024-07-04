package content.global.activity.jobs

import content.global.activity.jobs.impl.Employers
import core.tools.RandomFunction

/**
 * @author Swizey
 * @date 14.02.2023
 */
interface Job {

    val type: JobType
    val lower: Int
    val upper: Int

    val employer: Employers

    fun getAmount(): Int {
        return RandomFunction.random(lower, upper + 1)
    }
}
