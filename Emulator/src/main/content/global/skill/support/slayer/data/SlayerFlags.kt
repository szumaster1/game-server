package content.global.skill.support.slayer.data

/**
 * Slayer flags.
 */
class SlayerFlags {
    var taskFlags = 0
    var rewardFlags = 0
    var equipmentFlags = 0
    var completedTasks = 0
    var taskStreak = 0
    val removed: ArrayList<Tasks> = ArrayList(4)

    /**
     * Get master
     *
     * @return SlayerMaster - the current Slayer master based on taskFlags
     */
    fun getMaster(): SlayerMaster {
        val ordinal = taskFlags and 0xF
        return SlayerMaster.values()[ordinal]
    }

    /**
     * Set master
     *
     * @param master the SlayerMaster to set
     */
    fun setMaster(master: SlayerMaster) {
        taskFlags = (taskFlags - (taskFlags and 0xF)) or master.ordinal
    }

    /**
     * Get task
     *
     * @return Tasks the current task based on taskFlags
     */
    fun getTask(): Tasks {
        val ordinal = (taskFlags shr 4) and 0x7F
        return Tasks.values()[ordinal]
    }

    /**
     * Set task
     *
     * @param tasks the Tasks to set
     */
    fun setTask(tasks: Tasks) {
        taskFlags = (taskFlags - (getTask().ordinal shl 4)) or (tasks.ordinal shl 4)
    }

    /**
     * Get task amount
     *
     * @return the amount of the current task
     */
    fun getTaskAmount(): Int {
        return (taskFlags shr 11) and 0xFF
    }

    /**
     * Set task amount
     *
     * @param amount the amount of the task to set
     */
    fun setTaskAmount(amount: Int) {
        taskFlags = (taskFlags - (getTaskAmount() shl 11)) or (amount shl 11)
    }

    /**
     * Decrement task amount
     *
     * @param amount the amount to decrement from the current task amount
     */
    fun decrementTaskAmount(amount: Int) {
        setTaskAmount(getTaskAmount() - amount)
    }

    /**
     * Can earn points
     *
     * @return Boolean - true if points can be earned, false otherwise
     */
    fun canEarnPoints(): Boolean {
        return (taskFlags shr 20) and 1 == 1
    }

    /**
     * Flag can earn points
     *
     */
    fun flagCanEarnPoints() {
        taskFlags = taskFlags or (1 shl 20)
    }

    /**
     * Is broads unlocked
     *
     * @return Boolean - true if broads are unlocked, false otherwise
     */
    fun isBroadsUnlocked(): Boolean {
        return rewardFlags and 1 == 1
    }

    /**
     * Unlock broads
     *
     */
    fun unlockBroads() {
        rewardFlags = rewardFlags or 1
    }

    /**
     * Is ring unlocked
     *
     * @return Boolean - true if the ring is unlocked, false otherwise
     */
    fun isRingUnlocked(): Boolean {
        return (rewardFlags shr 1) and 1 == 1
    }

    /**
     * Unlock ring
     *
     */
    fun unlockRing() {
        rewardFlags = rewardFlags or (1 shl 1)
    }

    /**
     * Is helm unlocked
     *
     * @return Boolean - true if the helm is unlocked, false otherwise
     */
    fun isHelmUnlocked(): Boolean {
        return (rewardFlags shr 2) and 1 == 1
    }

    /**
     * Unlock helm
     *
     */
    fun unlockHelm() {
        rewardFlags = rewardFlags or (1 shl 2)
    }

    /**
     * Set points
     *
     * @param amount the amount of points to set
     */
    fun setPoints(amount: Int) {
        rewardFlags = (rewardFlags - (getPoints() shl 15)) or (amount shl 15)
    }

    /**
     * Get points
     *
     * @return Int - the current points
     */
    fun getPoints(): Int {
        return (rewardFlags shr 15) and 0xFFFF
    }

    /**
     * Increment points
     *
     * @param amount the amount to increment the current points
     */
    fun incrementPoints(amount: Int) {
        setPoints(getPoints() + amount)
    }

    /**
     * Clear task
     *
     */
    fun clearTask() {
        setTask(Tasks.values()[0])
        setTaskAmount(0)
    }

    /**
     * Has task
     *
     * @return Boolean - true if there is a current task, false otherwise
     */
    fun hasTask(): Boolean {
        return getTaskAmount() != 0
    }

    /**
     * Full clear
     *
     */
    fun fullClear() {
        taskFlags = 0
        rewardFlags = 0
        equipmentFlags = 0
        completedTasks = 0
        taskStreak = 0
    }
}