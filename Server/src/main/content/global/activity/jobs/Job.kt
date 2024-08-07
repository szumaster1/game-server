package content.global.activity.jobs

import content.global.activity.jobs.impl.Employers
import core.tools.RandomFunction

/**
 * Job interface defining job properties and behavior.
 */
interface Job {

    val type: JobType // Declares a property to store the type of job.
    val lower: Int // Declares a property to store the lower bound of the job amount.
    val upper: Int // Declares a property to store the upper bound of the job amount.

    val employer: Employers // Declares a property to store the employer of the job.

    /**
     * Generates a random amount within the specified range.
     *
     * @return The randomly generated job amount.
     */
    fun getAmount(): Int {
        return RandomFunction.random(lower, upper + 1)
    }
}
