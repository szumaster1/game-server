package content.global.activity.jobs

import content.activity.jobs.impl.Employers
import core.tools.RandomFunction

/**
 * The interface for a job that a player can be assigned.
 *
 * TODO: Once player data is stored in a database with support for migrations, implement a per-player field to store
 * the player's current employer, so that multiple employers can assign the same job, but the player will only be
 * able to turn the job into the employer that assigned it to them.
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
