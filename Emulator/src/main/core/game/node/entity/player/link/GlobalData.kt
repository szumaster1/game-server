package core.game.node.entity.player.link

import content.data.GodBook
import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 * Represents the quest data to save.
 * @author Vexia
 */
class GlobalData {
    private var runReplenishCharges: Int = 0
    private var lowAlchemyCharges: Int = 0
    private var forgingUses: Int = 0
    private var ectoCharges: Int = 0
    private var playerTestStage: Int = 0
    private var barrowsLoots: Int = 0
    private var lootSharePoints: Int = 0
    private var tutorialStage: Int = 0

    @JvmField
    var karamjaBananas: Int = 0
    private var savedX: Int = 0
    private var savedY: Int = 0
    private var savedH: Int = 0
    private var taskAmount: Int = 0
    private var taskPoints: Int = 0
    private var godBook: Int = -1
    private var zafAmount: Int = 8
    private var essenceTeleporter: Int = 0
    private var recoilDamage: Int = 40
    private var hunterCapeCharges: Int = 0
    private var bossCounters: IntArray = IntArray(25)
    private val rcDecays: IntArray = IntArray(3)
    private var assistExperience: DoubleArray = DoubleArray(9)
    private var homeTeleportDelay: Long = 0
    private var assistTime: Long = 0
    private var chatPing: Long = 0
    private var tutorClaim: Long = 0
    private var magicSkillCapeDelay: Long = 0
    private var hunterCapeDelay: Long = 0
    private var charmingDelay: Long = 0
    private var overChargeDelay: Long = 0
    private var lootShareDelay: Long = 0
    private var doubleExp: Long = 0
    private var globalTeleporterDelay: Long = 0
    var starSpriteDelay: Long = 0
    private var runReplenishDelay: Long = 0
    private var lowAlchemyDelay: Long = 0
    private var minigameTeleportDelay: Long = 0
    private var silkSteal: Long = 0
    private var zafTime: Long = 0
    private var doubleExpDelay: Long = 0
    private var dropDelay: Long = 0
    private var luthasTask: Boolean = false
    private var lumbridgeRope: Boolean = false
    private var apprentice: Boolean = false
    private var fritzGlass: Boolean = false
    private var wydinEmployee: Boolean = false
    private var draynorRecording: Boolean = false
    private var geTutorial: Boolean = false
    private var joinedMonastery: Boolean = false
    private var disableDeathScreen: Boolean = false
    private var disableNews: Boolean = false
    private var enableCoinMachine: Boolean = false
    private var enableCharmCollector: Boolean = false
    private var macroDisabled: Boolean = false
    private var strongHoldRewards: BooleanArray = BooleanArray(4)
    var readPlaques: BooleanArray = BooleanArray(7)
    private var abyssData: BooleanArray = BooleanArray(4)
    private var travelLogs: BooleanArray = BooleanArray(45)
    private var godBooks: BooleanArray = BooleanArray(3)
    private var godPages: BooleanArray = BooleanArray(4)


    /**
     * Parse
     *
     * @param data JSON object containing data to parse.
     */
    fun parse(data: JSONObject) {
        tutorialStage = data["tutorialStage"].toString().toInt()
        homeTeleportDelay = data["homeTeleportDelay"].toString().toLong()
        lumbridgeRope = data["lumbridgeRope"] as Boolean
        apprentice = data["apprentice"] as Boolean
        assistTime = data["assistTime"].toString().toLong()
        val ae = data["assistExperience"] as JSONArray
        for (i in 0 until ae.size) {
            assistExperience[i] = ae[i].toString().toDouble()
        }
        val sr = data["strongHoldRewards"] as JSONArray
        for (i in 0 until sr.size) {
            strongHoldRewards[i] = sr[i] as Boolean
        }
        chatPing = data["chatPing"].toString().toLong()
        tutorClaim = data["tutorClaim"].toString().toLong()
        luthasTask = data["luthasTask"] as Boolean
        karamjaBananas = data["karamjaBananas"].toString().toInt()
        silkSteal = data["silkSteal"].toString().toLong()
        zafAmount = data["zafAmount"].toString().toInt()
        zafTime = data["zafTime"].toString().toLong()
        fritzGlass = data["fritzGlass"] as Boolean
        wydinEmployee = data["wydinEmployee"] as Boolean
        draynorRecording = data["draynorRecording"] as Boolean
        geTutorial = data["geTutorial"] as Boolean
        essenceTeleporter = data["essenceTeleporter"].toString().toInt()
        recoilDamage = data["recoilDamage"].toString().toInt()
        doubleExpDelay = data["doubleExpDelay"].toString().toLong()
        joinedMonastery = data["joinedMonastery"] as Boolean
        val rp = data["readPlaques"] as JSONArray
        for (i in 0 until rp.size) {
            readPlaques[i] = rp[i] as Boolean
        }
        forgingUses = data["forgingUses"].toString().toInt()
        ectoCharges = data["ectoCharges"].toString().toInt()
        dropDelay = data["dropDelay"].toString().toLong()
        val ad = data["abyssData"] as JSONArray
        for (i in 0 until ad.size) {
            abyssData[i] = ad[i] as Boolean
        }
        val rd = data["rcDecays"] as JSONArray
        for (i in 0 until rd.size) {
            rcDecays[i] = rd[i].toString().toInt()
        }
        disableDeathScreen = data["disableDeathScreen"] as Boolean
        playerTestStage = data["playerTestStage"].toString().toInt()
        charmingDelay = data["charmingDelay"].toString().toLong()
        val tl = data["travelLogs"] as JSONArray
        for (i in 0 until tl.size) {
            travelLogs[i] = tl[i] as Boolean
        }
        val gb = data["godBooks"] as JSONArray
        for (i in 0 until gb.size) {
            godBooks[i] = gb[i] as Boolean
        }
        disableNews = data["disableNews"] as Boolean
        val gp = data["godPages"] as JSONArray
        for (i in 0 until gp.size) {
            godPages[i] = gp[i] as Boolean
        }
        overChargeDelay = data["overChargeDelay"].toString().toLong()
        val bc = data["bossCounters"] as JSONArray
        for (i in 0 until bc.size) {
            bossCounters[i] = bc[i].toString().toInt()
        }
        barrowsLoots = data["barrowsLoots"].toString().toInt()
        lootShareDelay = data["lootShareDelay"].toString().toLong()
        lootSharePoints = data["lootSharePoints"].toString().toInt()
        doubleExp = data["doubleExp"].toString().toLong()
        globalTeleporterDelay = data["globalTeleporterDelay"].toString().toLong()
        starSpriteDelay = data["starSpriteDelay"].toString().toLong()
        runReplenishDelay = data["runReplenishDelay"].toString().toLong()
        runReplenishCharges = data["runReplenishCharges"].toString().toInt()
        lowAlchemyCharges = data["lowAlchemyCharges"].toString().toInt()
        lowAlchemyDelay = data["lowAlchemyDelay"].toString().toLong()
        magicSkillCapeDelay = data["magicSkillCapeDelay"].toString().toLong()
        hunterCapeDelay = data["hunterCapeDelay"].toString().toLong()
        hunterCapeCharges = data["hunterCapeCharges"].toString().toInt()
        taskAmount = data["taskAmount"].toString().toInt()
        taskPoints = data["taskPoints"].toString().toInt()
        macroDisabled = data["macroDisabled"] as Boolean
    }

    /**
     * Set saved location
     *
     * @param x X coordinate to save
     * @param y Y coordinate to save
     * @param z Height coordinate to save
     */
    fun setSavedLocation(x: Int, y: Int, z: Int) {
        savedX = x
        savedY = y
        savedH = z
    }

    /**
     * Get travel logs
     *
     * @return Array of travel logs
     */
    fun getTravelLogs(): BooleanArray {
        return travelLogs
    }

    /**
     * Remove travel log
     *
     * @param index Index of the travel log to remove
     */
    fun removeTravelLog(index: Int) {
        travelLogs[index] = false
    }

    /**
     * Has travel log
     *
     * @param index Index of the travel log to check
     * @return True if the travel log exists, otherwise false
     */
    fun hasTravelLog(index: Int): Boolean {
        return travelLogs[index]
    }

    /**
     * Set travel log
     *
     * @param index Index of the travel log to set
     */
    fun setTravelLog(index: Int) {
        travelLogs[index] = true
    }

    /**
     * Set charming delay
     *
     * @param delay Delay time for charming
     */
    fun setCharmingDelay(delay: Long) {
        charmingDelay = delay
    }

    /**
     * Get charming delay
     *
     * @return Current charming delay
     */
    fun getCharmingDelay(): Long {
        return charmingDelay
    }

    /**
     * Get test stage
     *
     * @return Current player test stage
     */
    fun getTestStage(): Int {
        return playerTestStage
    }

    /**
     * Set test stage
     *
     * @param stage New test stage to set
     */
    fun setTestStage(stage: Int) {
        playerTestStage = stage
    }

    /**
     * Get tutorial stage
     *
     * @return Current tutorial stage
     */
    fun getTutorialStage(): Int {
        return tutorialStage
    }

    /**
     * Set tutorial stage
     *
     * @param tutorialStage New tutorial stage to set
     */
    fun setTutorialStage(tutorialStage: Int) {
        this.tutorialStage = tutorialStage
    }

    /**
     * Get home teleport delay
     *
     * @return Current home teleport delay
     */
    fun getHomeTeleportDelay(): Long {
        return homeTeleportDelay
    }

    /**
     * Set home teleport delay
     *
     * @param homeTeleportDelay New home teleport delay to set
     */
    fun setHomeTeleportDelay(homeTeleportDelay: Long) {
        this.homeTeleportDelay = homeTeleportDelay
    }

    /**
     * Has tied lumbridge rope
     *
     * @return True if the lumbridge rope is tied, otherwise false
     */
    fun hasTiedLumbridgeRope(): Boolean {
        return lumbridgeRope
    }

    /**
     * Set lumbridge rope
     *
     * @param lumbridgeRope New status for the lumbridge rope
     */
    fun setLumbridgeRope(lumbridgeRope: Boolean) {
        this.lumbridgeRope = lumbridgeRope
    }

    /**
     * Has spoken to apprentice
     *
     * @return True if the apprentice has been spoken to, otherwise false
     */
    fun hasSpokenToApprentice(): Boolean {
        return apprentice
    }

    /**
     * Set apprentice
     *
     * @param apprentice Indicates whether the user is an apprentice
     */
    fun setApprentice(apprentice: Boolean) {
        this.apprentice = apprentice
    }

    /**
     * Get assist time
     *
     * @return The amount of time spent assisting
     */
    fun getAssistTime(): Long {
        return assistTime
    }

    /**
     * Set assist time
     *
     * @param assistTime The time to be set for assisting
     */
    fun setAssistTime(assistTime: Long) {
        this.assistTime = assistTime
    }

    /**
     * Get assist experience
     *
     * @return An array of assist experience values
     */
    fun getAssistExperience(): DoubleArray {
        return assistExperience
    }

    /**
     * Set assist experience
     *
     * @param assistExperience An array of experience values to be set
     */
    fun setAssistExperience(assistExperience: DoubleArray) {
        this.assistExperience = assistExperience
    }

    /**
     * Get strong hold rewards
     *
     * @return An array indicating the stronghold rewards
     */
    fun getStrongHoldRewards(): BooleanArray {
        return strongHoldRewards
    }

    /**
     * Has stronghold reward
     *
     * @param reward The index of the reward to check
     * @return True if the reward exists, otherwise false
     */
    fun hasStrongholdReward(reward: Int): Boolean {
        return strongHoldRewards[reward - 1]
    }

    /**
     * Get chat ping
     *
     * @return The current chat ping value
     */
    fun getChatPing(): Long {
        return chatPing
    }

    /**
     * Set chat ping
     *
     * @param chatPing The ping value to be set for chat
     */
    fun setChatPing(chatPing: Long) {
        this.chatPing = chatPing
    }

    /**
     * Get tutor claim
     *
     * @return The current tutor claim value
     */
    fun getTutorClaim(): Long {
        return tutorClaim
    }

    /**
     * Set tutor claim
     *
     * @param tutorClaim The claim value to be set for the tutor
     */
    fun setTutorClaim(tutorClaim: Long) {
        this.tutorClaim = tutorClaim
    }

    /**
     * Is luthas task
     *
     * @return True if it is a Luthas task, otherwise false
     */
    fun isLuthasTask(): Boolean {
        return luthasTask
    }

    /**
     * Set luthas task
     *
     * @param luthasTask Indicates whether it is a Luthas task
     */
    fun setLuthasTask(luthasTask: Boolean) {
        this.luthasTask = luthasTask
    }

    /**
     * Get karamja bananas
     *
     * @return The number of Karamja bananas
     */
    fun getKaramjaBananas(): Int {
        return karamjaBananas
    }

    /**
     * Set karamja bananas
     *
     * @param karamjaBannanas The number of Karamja bananas to be set
     */
    fun setKaramjaBannanas(karamjaBannanas: Int) {
        this.karamjaBananas = karamjaBannanas
    }

    /**
     * Get silk steal
     *
     * @return The amount of silk stolen
     */
    fun getSilkSteal(): Long {
        return silkSteal
    }

    /**
     * Set silk steal
     *
     * @param silkSteal The amount of silk to be set as stolen
     */
    fun setSilkSteal(silkSteal: Long) {
        this.silkSteal = silkSteal
    }

    /**
     * Get zaff amount
     *
     * @return The current amount of Zaff
     */
    fun getZaffAmount(): Int {
        return zafAmount
    }

    /**
     * Set zaff amount
     *
     * @param zaffAmount The amount of Zaff to be set
     */
    fun setZaffAmount(zaffAmount: Int) {
        this.zafAmount = zaffAmount
    }

    /**
     * Get zaf time
     *
     * @return The current Zaf time
     */
    fun getZafTime(): Long {
        return zafTime
    }

    /**
     * Is draynor recording
     *
     * @return True if Draynor is being recorded, otherwise false
     */
    fun isDraynorRecording(): Boolean {
        return draynorRecording
    }

    /**
     * Set draynor recording
     *
     * @param draynorRecording Indicates whether Draynor is being recorded
     */
    fun setDraynorRecording(draynorRecording: Boolean) {
        this.draynorRecording = draynorRecording
    }

    /**
     * Is wydin employee
     *
     * @return True if the user is a Wydin employee, otherwise false
     */
    fun isWydinEmployee(): Boolean {
        return wydinEmployee
    }

    /**
     * Set wydin employee
     *
     * @param wydinEmployee Indicates whether the user is a Wydin employee
     */
    fun setWydinEmployee(wydinEmployee: Boolean) {
        this.wydinEmployee = wydinEmployee
    }

    /**
     * Set zaf time
     *
     * @param zaffTime The time to be set for Zaf
     */
    fun setZafTime(zaffTime: Long) {
        this.zafTime = zaffTime
    }

    /**
     * Is fritz glass
     *
     * @return True if Fritz glass is present, otherwise false
     */
    fun isFritzGlass(): Boolean {
        return fritzGlass
    }

    /**
     * Set fritz glass
     *
     * @param frizGlass Indicates whether Fritz glass is present
     */
    fun setFritzGlass(frizGlass: Boolean) {
        this.fritzGlass = frizGlass
    }

    /**
     * Is ge tutorial
     *
     * @return True if the GE tutorial is active, otherwise false
     */
    fun isGeTutorial(): Boolean {
        return geTutorial
    }

    /**
     * Set ge tutorial
     *
     * @param geTutorial Indicates whether the GE tutorial is active
     */
    fun setGeTutorial(geTutorial: Boolean) {
        this.geTutorial = geTutorial
    }

    /**
     * Get essence teleporter
     *
     * @return The current essence teleporter value
     */
    fun getEssenceTeleporter(): Int {
        return essenceTeleporter
    }

    /**
     * Set essence teleporter
     *
     * @param essenceTeleporter The value to be set for the essence teleporter
     */
    fun setEssenceTeleporter(essenceTeleporter: Int) {
        this.essenceTeleporter = essenceTeleporter
    }

    /**
     * Get recoil damage
     *
     * @return The current recoil damage value
     */
    fun getRecoilDamage(): Int {
        return recoilDamage
    }

    /**
     * Set recoil damage
     *
     * @param recoilDamage The damage value to be set for recoil
     */
    fun setRecoilDamage(recoilDamage: Int) {
        this.recoilDamage = recoilDamage
    }

    /**
     * Get double exp delay
     *
     * @return The current delay for double experience
     */
    fun getDoubleExpDelay(): Long {
        return doubleExpDelay
    }

    /**
     * Set double exp delay
     *
     * @param doubleExpDelay The delay value to be set for double experience
     */
    fun setDoubleExpDelay(doubleExpDelay: Long) {
        this.doubleExpDelay = doubleExpDelay
    }

    /**
     * Checks if the user has joined the monastery.
     *
     * @return true if the user has joined the monastery, false otherwise.
     */
    fun isJoinedMonastery(): Boolean {
        return joinedMonastery
    }

    /**
     * Sets the user's joined monastery status.
     *
     * @param joinedMonastery true if the user has joined the monastery, false otherwise.
     */
    fun setJoinedMonastery(joinedMonastery: Boolean) {
        this.joinedMonastery = joinedMonastery
    }

    /**
     * Checks if the user has read all plaques.
     *
     * @return true if all plaques have been read, false otherwise.
     */
    fun hasReadPlaques(): Boolean {
        for (i in readPlaques.indices) {
            if (!readPlaques[i]) {
                return false
            }
        }
        return true
    }

    /**
     * Sets the status of a specific god book.
     *
     * @param book the GodBook to be set as read.
     */
    fun setGodBook(book: GodBook) {
        godBooks[book.ordinal] = true
    }

    /**
     * Checks if a specific god book has been completed.
     *
     * @param book the GodBook to check.
     * @return true if the god book has been completed, false otherwise.
     */
    fun hasCompletedGodBook(book: GodBook): Boolean {
        return godBooks[book.ordinal]
    }

    /**
     * Gets the number of forging uses available.
     *
     * @return the number of forging uses.
     */
    fun getForgingUses(): Int {
        return forgingUses
    }

    /**
     * Sets the number of forging uses available.
     *
     * @param forgingUses the new number of forging uses.
     */
    fun setForgingUses(forgingUses: Int) {
        this.forgingUses = forgingUses
    }

    /**
     * Gets the number of ecto charges available.
     *
     * @return the number of ecto charges.
     */
    fun getEctoCharges(): Int {
        return ectoCharges
    }

    /**
     * Sets the number of ecto charges available.
     *
     * @param ectoCharges the new number of ecto charges.
     */
    fun setEctoCharges(ectoCharges: Int) {
        this.ectoCharges = ectoCharges
    }

    /**
     * Resets the abyss data to its initial state.
     */
    fun resetAbyss() {
        for (i in abyssData.indices) {
            abyssData[i] = false
        }
    }

    /**
     * Sets a specific abyss charge to true.
     *
     * @param ordinal the index of the abyss charge to set.
     */
    fun setAbyssCharge(ordinal: Int) {
        abyssData[ordinal] = true
    }

    /**
     * Checks if a specific abyss charge is active.
     *
     * @param ordinal the index of the abyss charge to check.
     * @return true if the abyss charge is active, false otherwise.
     */
    fun hasAbyssCharge(ordinal: Int): Boolean {
        return abyssData[ordinal]
    }

    /**
     * Gets the current drop delay.
     *
     * @return the current drop delay in milliseconds.
     */
    fun getDropDelay(): Long {
        return dropDelay
    }

    /**
     * Sets the drop delay.
     *
     * @param dropDelay the new drop delay in milliseconds.
     */
    fun setDropDelay(dropDelay: Long) {
        this.dropDelay = dropDelay
    }

    /**
     * Gets the decay value for a specific ordinal.
     *
     * @param ordinal the index of the decay to retrieve.
     * @return the decay value, or 0 if the ordinal is invalid.
     */
    fun getRcDecay(ordinal: Int): Int {
        if (ordinal < 0) {
            return 0
        }
        return rcDecays[ordinal]
    }

    /**
     * Gets all rc decays.
     *
     * @return an array of rc decay values.
     */
    fun getRcDecays(): IntArray {
        return rcDecays
    }

    /**
     * Checks if the death screen is disabled.
     *
     * @return true if the death screen is disabled, false otherwise.
     */
    fun isDeathScreenDisabled(): Boolean {
        return disableDeathScreen
    }

    /**
     * Sets the status of the death screen.
     *
     * @param b true to disable the death screen, false to enable it.
     */
    fun setDisableDeathScreen(b: Boolean) {
        this.disableDeathScreen = b
    }

    /**
     * Gets the status of god books.
     *
     * @return an array indicating the status of each god book.
     */
    fun getGodBooks(): BooleanArray {
        return godBooks
    }

    /**
     * Sets the status of god books.
     *
     * @param godBooks the new statuses for the god books.
     */
    fun setGodBooks(godBooks: BooleanArray) {
        this.godBooks = godBooks
    }

    /**
     * Gets the current god book index.
     *
     * @return the index of the current god book.
     */
    fun getGodBook(): Int {
        return godBook
    }

    /**
     * Sets the current god book index.
     *
     * @param godBook the new index for the god book.
     */
    fun setGodBook(godBook: Int) {
        this.godBook = godBook
    }

    /**
     * Checks if news notifications are disabled.
     *
     * @return true if news notifications are disabled, false otherwise.
     */
    fun isDisableNews(): Boolean {
        return disableNews
    }

    /**
     * Sets the status of news notifications.
     *
     * @param disableNews true to disable news notifications, false to enable them.
     */
    fun setDisableNews(disableNews: Boolean) {
        this.disableNews = disableNews
    }

    /**
     * Gets the status of god pages.
     *
     * @return an array indicating the status of each god page.
     */
    fun getGodPages(): BooleanArray {
        return godPages
    }

    /**
     * Sets the status of god pages.
     *
     * @param godPages the new statuses for the god pages.
     */
    fun setGodPages(godPages: BooleanArray) {
        this.godPages = godPages
    }

    /**
     * Gets the current over charge delay.
     *
     * @return the current over charge delay in milliseconds.
     */
    fun getOverChargeDelay(): Long {
        return overChargeDelay
    }

    /**
     * Sets the over charge delay.
     *
     * @param overChargeDelay the new over charge delay in milliseconds.
     */
    fun setOverChargeDelay(overChargeDelay: Long) {
        this.overChargeDelay = overChargeDelay
    }

    /**
     * Gets the current boss counters.
     *
     * @return an array of boss counters.
     */
    fun getBossCounters(): IntArray {
        return bossCounters
    }

    /**
     * Sets the boss counters.
     *
     * @param bossCounters the new values for the boss counters.
     */
    fun setBossCounters(bossCounters: IntArray) {
        this.bossCounters = bossCounters
    }

    /**
     * Gets the current barrows loots count.
     *
     * @return the number of barrows loots.
     */
    fun getBarrowsLoots(): Int {
        return barrowsLoots
    }

    /**
     * Sets the barrows loots count.
     *
     * @param barrowsLoots the new number of barrows loots.
     */
    fun setBarrowsLoots(barrowsLoots: Int) {
        this.barrowsLoots = barrowsLoots
    }

    /**
     * Gets the current loot share points.
     *
     * @return the number of loot share points.
     */
    fun getLootSharePoints(): Int {
        return lootSharePoints
    }

    /**
     * Sets the loot share points.
     *
     * @param lootSharePoints the new number of loot share points.
     */
    fun setLootSharePoints(lootSharePoints: Int) {
        this.lootSharePoints = lootSharePoints
    }

    /**
     * Gets the current loot share delay.
     *
     * @return the current loot share delay in milliseconds.
     */
    fun getLootShareDelay(): Long {
        return lootShareDelay
    }

    /**
     * Set loot share delay
     *
     * @param lootShareDelay The delay time for loot sharing in milliseconds.
     */
    fun setLootShareDelay(lootShareDelay: Long) {
        this.lootShareDelay = lootShareDelay
    }

    /**
     * Get double exp
     *
     * @return The current double experience value.
     */
    fun getDoubleExp(): Long {
        return doubleExp
    }

    /**
     * Set double exp
     *
     * @param doubleExp The new double experience value to be set.
     */
    fun setDoubleExp(doubleExp: Long) {
        this.doubleExp = doubleExp
    }

    /**
     * Has double exp
     *
     * @return True if double experience is active, otherwise false.
     */
    fun hasDoubleExp(): Boolean {
        return doubleExp > System.currentTimeMillis()
    }

    /**
     * Get global teleporter delay
     *
     * @return The current global teleporter delay in milliseconds.
     */
    fun getGlobalTeleporterDelay(): Long {
        return globalTeleporterDelay
    }

    /**
     * Set global teleporter delay
     *
     * @param globalTeleporterDelay The new delay time for the global teleporter.
     */
    fun setGlobalTeleporterDelay(globalTeleporterDelay: Long) {
        this.globalTeleporterDelay = globalTeleporterDelay
    }

    /**
     * Get run replenish delay
     *
     * @return The current run replenish delay in milliseconds.
     */
    fun getRunReplenishDelay(): Long {
        return runReplenishDelay
    }

    /**
     * Set run replenish delay
     *
     * @param runReplenishDelay The new delay time for run replenishment.
     */
    fun setRunReplenishDelay(runReplenishDelay: Long) {
        this.runReplenishDelay = runReplenishDelay
    }

    /**
     * Get run replenish charges
     *
     * @return The current number of run replenish charges.
     */
    fun getRunReplenishCharges(): Int {
        return runReplenishCharges
    }

    /**
     * Set run replenish charges
     *
     * @param runReplenishCharges The new number of run replenish charges to be set.
     */
    fun setRunReplenishCharges(runReplenishCharges: Int) {
        this.runReplenishCharges = runReplenishCharges
    }

    /**
     * Get low alchemy charges
     *
     * @return The current number of low alchemy charges.
     */
    fun getLowAlchemyCharges(): Int {
        return lowAlchemyCharges
    }

    /**
     * Set low alchemy charges
     *
     * @param lowAlchemyCharges The new number of low alchemy charges to be set.
     */
    fun setLowAlchemyCharges(lowAlchemyCharges: Int) {
        this.lowAlchemyCharges = lowAlchemyCharges
    }

    /**
     * Get low alchemy delay
     *
     * @return The current low alchemy delay in milliseconds.
     */
    fun getLowAlchemyDelay(): Long {
        return lowAlchemyDelay
    }

    /**
     * Set low alchemy delay
     *
     * @param lowAlchemyDelay The new delay time for low alchemy.
     */
    fun setLowAlchemyDelay(lowAlchemyDelay: Long) {
        this.lowAlchemyDelay = lowAlchemyDelay
    }

    /**
     * Is enable coin machine
     *
     * @return True if the coin machine is enabled, otherwise false.
     */
    fun isEnableCoinMachine(): Boolean {
        return enableCoinMachine
    }

    /**
     * Set enable coin machine
     *
     * @param enableCoinMachine The new status for enabling the coin machine.
     */
    fun setEnableCoinMachine(enableCoinMachine: Boolean) {
        this.enableCoinMachine = enableCoinMachine
    }

    /**
     * Get magic skill cape delay
     *
     * @return The current magic skill cape delay in milliseconds.
     */
    fun getMagicSkillCapeDelay(): Long {
        return magicSkillCapeDelay
    }

    /**
     * Set magic skill cape delay
     *
     * @param magicSkillCapeDelay The new delay time for the magic skill cape.
     */
    fun setMagicSkillCapeDelay(magicSkillCapeDelay: Long) {
        this.magicSkillCapeDelay = magicSkillCapeDelay
    }

    /**
     * Get hunter cape delay
     *
     * @return The current hunter cape delay in milliseconds.
     */
    fun getHunterCapeDelay(): Long {
        return hunterCapeDelay
    }

    /**
     * Set hunter cape delay
     *
     * @param hunterCapeDelay The new delay time for the hunter cape.
     */
    fun setHunterCapeDelay(hunterCapeDelay: Long) {
        this.hunterCapeDelay = hunterCapeDelay
    }

    /**
     * Get hunter cape charges
     *
     * @return The current number of hunter cape charges.
     */
    fun getHunterCapeCharges(): Int {
        return hunterCapeCharges
    }

    /**
     * Set hunter cape charges
     *
     * @param hunterCapeCharges The new number of hunter cape charges to be set.
     */
    fun setHunterCapeCharges(hunterCapeCharges: Int) {
        this.hunterCapeCharges = hunterCapeCharges
    }

    /**
     * Is enable charm collector
     *
     * @return True if the charm collector is enabled, otherwise false.
     */
    fun isEnableCharmCollector(): Boolean {
        return enableCharmCollector
    }

    /**
     * Set enable charm collector
     *
     * @param enableCharmCollector The new status for enabling the charm collector.
     */
    fun setEnableCharmCollector(enableCharmCollector: Boolean) {
        this.enableCharmCollector = enableCharmCollector
    }

    /**
     * Get minigame teleport delay
     *
     * @return The current minigame teleport delay in milliseconds.
     */
    fun getMinigameTeleportDelay(): Long {
        return minigameTeleportDelay
    }

    /**
     * Set minigame teleport delay
     *
     * @param delay The new delay time for minigame teleportation.
     */
    fun setMinigameTeleportDelay(delay: Long) {
        this.minigameTeleportDelay = delay
    }

    /**
     * Get saved h
     *
     * @return The current saved horizontal position.
     */
    fun getSavedH(): Int {
        return savedH
    }

    /**
     * Set saved h
     *
     * @param savedH The new horizontal position to be saved.
     */
    fun setSavedH(savedH: Int) {
        this.savedH = savedH
    }

    /**
     * Get saved y
     *
     * @return The current saved vertical position.
     */
    fun getSavedY(): Int {
        return savedY
    }

    /**
     * Set saved y
     *
     * @param savedY The new vertical position to be saved.
     */
    fun setSavedY(savedY: Int) {
        this.savedY = savedY
    }

    /**
     * Get saved x
     *
     * @return The current saved horizontal position.
     */
    fun getSavedX(): Int {
        return savedX
    }

    /**
     * Set saved x
     *
     * @param savedX The new horizontal position to be saved.
     */
    fun setSavedX(savedX: Int) {
        this.savedX = savedX
    }

    /**
     * Get task amount
     *
     * @return The current number of tasks.
     */
    fun getTaskAmount(): Int {
        return taskAmount
    }

    /**
     * Set task amount
     *
     * @param taskAmount The new number of tasks to be set.
     */
    fun setTaskAmount(taskAmount: Int) {
        this.taskAmount = taskAmount
    }

    /**
     * Get task points
     *
     * @return The current number of task points.
     */
    fun getTaskPoints(): Int {
        return taskPoints
    }

    /**
     * Set task points
     *
     * @param taskPoints The new number of task points to be set.
     */
    fun setTaskPoints(taskPoints: Int) {
        this.taskPoints = taskPoints
    }

    /**
     * Set macro disabled
     *
     * @param disabled The new status for disabling the macro.
     */
    fun setMacroDisabled(disabled: Boolean) {
        this.macroDisabled = disabled
    }

    /**
     * Get macro disabled
     *
     * @return True if the macro is disabled, otherwise false.
     */
    fun getMacroDisabled(): Boolean {
        return this.macroDisabled
    }

    /**
     * Get abyss data
     *
     * @return The current abyss data as a boolean array.
     */
    fun getAbyssData(): BooleanArray {
        return abyssData
    }

    /**
     * Get player test stage
     *
     * @return The current player test stage.
     */
    fun getPlayerTestStage(): Int {
        return playerTestStage
    }
}