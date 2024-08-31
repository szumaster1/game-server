package content.global.skill.support.slayer.data

/**
 * Slayer flags.
 */
class SlayerFlags {
    // Variable to hold task-related flags
    var taskFlags = 0
    // Variable to hold reward-related flags
    var rewardFlags = 0
    // Variable to hold equipment-related flags
    var equipmentFlags = 0
    // Variable to count completed tasks
    var completedTasks = 0
    // Variable to track the current task streak
    var taskStreak = 0
    // List to hold removed tasks, initialized with a capacity of 4
    val removed: ArrayList<Tasks> = ArrayList(4)

    /**
     * Get master
     *
     * @return SlayerMaster - the current Slayer master based on taskFlags
     */
    fun getMaster(): SlayerMaster {
        // Extract the master ordinal from taskFlags using bitwise AND
        val ordinal = taskFlags and 0xF
        // Return the corresponding SlayerMaster from the enum values
        return SlayerMaster.values()[ordinal]
    }

    /**
     * Set master
     *
     * @param master the SlayerMaster to set
     */
    fun setMaster(master: SlayerMaster) {
        // Update taskFlags to set the new master while preserving other flags
        taskFlags = (taskFlags - (taskFlags and 0xF)) or master.ordinal
    }

    /**
     * Get task
     *
     * @return Tasks the current task based on taskFlags
     */
    fun getTask(): Tasks {
        // Extract the task ordinal from taskFlags using bitwise operations
        val ordinal = (taskFlags shr 4) and 0x7F
        // Return the corresponding task from the enum values
        return Tasks.values()[ordinal]
    }

    /**
     * Set task
     *
     * @param tasks the Tasks to set
     */
    fun setTask(tasks: Tasks) {
        // Update taskFlags to set the new task while preserving other flags
        taskFlags = (taskFlags - (getTask().ordinal shl 4)) or (tasks.ordinal shl 4)
    }

    /**
     * Get task amount
     *
     * @return the amount of the current task
     */
    fun getTaskAmount(): Int {
        // Extract the task amount from taskFlags using bitwise operations
        return (taskFlags shr 11) and 0xFF
    }

    /**
     * Set task amount
     *
     * @param amount the amount of the task to set
     */
    fun setTaskAmount(amount: Int) {
        // Update taskFlags to set the new task amount while preserving other flags
        taskFlags = (taskFlags - (getTaskAmount() shl 11)) or (amount shl 11)
    }

    /**
     * Decrement task amount
     *
     * @param amount the amount to decrement from the current task amount
     */
    fun decrementTaskAmount(amount: Int) {
        // Decrease the current task amount by the specified amount
        setTaskAmount(getTaskAmount() - amount)
    }

    /**
     * Can earn points
     *
     * @return Boolean - true if points can be earned, false otherwise
     */
    fun canEarnPoints(): Boolean {
        // Check if the points earning flag is set in taskFlags
        return (taskFlags shr 20) and 1 == 1
    }

    /**
     * Flag can earn points
     *
     */
    fun flagCanEarnPoints() {
        // Set the points earning flag in taskFlags
        taskFlags = taskFlags or (1 shl 20)
    }

    /**
     * Is broads unlocked
     *
     * @return Boolean - true if broads are unlocked, false otherwise
     */
    fun isBroadsUnlocked(): Boolean {
        // Check if the broads unlock flag is set in rewardFlags
        return rewardFlags and 1 == 1
    }

    /**
     * Unlock broads
     *
     */
    fun unlockBroads() {
        // Set the broads unlock flag in rewardFlags
        rewardFlags = rewardFlags or 1
    }

    /**
     * Is ring unlocked
     *
     * @return Boolean - true if the ring is unlocked, false otherwise
     */
    fun isRingUnlocked(): Boolean {
        // Check if the ring unlock flag is set in rewardFlags
        return (rewardFlags shr 1) and 1 == 1
    }

    /**
     * Unlock ring
     *
     */
    fun unlockRing() {
        // Set the ring unlock flag in rewardFlags
        rewardFlags = rewardFlags or (1 shl 1)
    }

    /**
     * Is helm unlocked
     *
     * @return Boolean - true if the helm is unlocked, false otherwise
     */
    fun isHelmUnlocked(): Boolean {
        // Check if the helm unlock flag is set in rewardFlags
        return (rewardFlags shr 2) and 1 == 1
    }

    /**
     * Unlock helm
     *
     */
    fun unlockHelm() {
        // Set the helm unlock flag in rewardFlags
        rewardFlags = rewardFlags or (1 shl 2)
    }

    /**
     * Set points
     *
     * @param amount the amount of points to set
     */
    fun setPoints(amount: Int) {
        // Update rewardFlags to set the new points while preserving other flags
        rewardFlags = (rewardFlags - (getPoints() shl 15)) or (amount shl 15)
    }

    /**
     * Get points
     *
     * @return Int - the current points
     */
    fun getPoints(): Int {
        // Extract the points from rewardFlags using bitwise operations
        return (rewardFlags shr 15) and 0xFFFF
    }

    /**
     * Increment points
     *
     * @param amount the amount to increment the current points
     */
    fun incrementPoints(amount: Int) {
        // Increase the current points by the specified amount
        setPoints(getPoints() + amount)
    }

    /**
     * Clear task
     *
     */
    fun clearTask() {
        // Reset the current task and its amount to default values
        setTask(Tasks.values()[0])
        setTaskAmount(0)
    }

    /**
     * Has task
     *
     * @return Boolean - true if there is a current task, false otherwise
     */
    fun hasTask(): Boolean {
        // Check if the current task amount is not zero
        return getTaskAmount() != 0
    }

    /**
     * Full clear
     *
     */
    fun fullClear() {
        // Reset all flags and counters to their initial state
        taskFlags = 0
        rewardFlags = 0
        equipmentFlags = 0
        completedTasks = 0
        taskStreak = 0
    }
}