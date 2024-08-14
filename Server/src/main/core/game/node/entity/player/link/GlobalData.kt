package core.game.node.entity.player.link

import content.data.GodBook
import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 * Global data
 */
class GlobalData {
    // Number of charges for replenishing run energy
    private var runReplenishCharges: Int = 0
    // Number of charges for low alchemy
    private var lowAlchemyCharges: Int = 0
    // Number of uses for forging
    private var forgingUses: Int = 0
    // Number of ecto charges
    private var ectoCharges: Int = 0
    // Current stage of the player in the test
    private var playerTestStage: Int = 0
    // Loots obtained from Barrows
    private var barrowsLoots: Int = 0
    // Points for loot sharing
    private var lootSharePoints: Int = 0
    // Current stage of the tutorial
    private var tutorialStage: Int = 0

    @JvmField
    // Number of Karamja bananas
    var karamjaBananas: Int = 0
    // Saved X coordinate
    private var savedX: Int = 0
    // Saved Y coordinate
    private var savedY: Int = 0
    // Saved height coordinate
    private var savedH: Int = 0
    // Amount of tasks
    private var taskAmount: Int = 0
    // Points earned from tasks
    private var taskPoints: Int = 0
    // ID of the god book
    private var godBook: Int = -1
    // Amount of Zaf's items
    private var zafAmount: Int = 8
    // Essence teleporter status
    private var essenceTeleporter: Int = 0
    // Damage from recoil
    private var recoilDamage: Int = 40
    // Charges for the hunter cape
    private var hunterCapeCharges: Int = 0
    // Array to count bosses
    private var bossCounters: IntArray = IntArray(25)

    // Array to hold decay values for runecrafting
    private val rcDecays: IntArray = IntArray(3)

    // Array to hold experience values for assistance
    private var assistExperience: DoubleArray = DoubleArray(9)

    // Delay for home teleportation
    private var homeTeleportDelay: Long = 0
    // Time for assistance
    private var assistTime: Long = 0
    // Ping time for chat
    private var chatPing: Long = 0
    // Time for claiming tutor rewards
    private var tutorClaim: Long = 0
    // Delay for magic skill cape
    private var magicSkillCapeDelay: Long = 0
    // Delay for hunter cape
    private var hunterCapeDelay: Long = 0
    // Delay for charming
    private var charmingDelay: Long = 0
    // Delay for overcharging
    private var overChargeDelay: Long = 0
    // Delay for loot sharing
    private var lootShareDelay: Long = 0
    // Duration for double experience
    private var doubleExp: Long = 0
    // Delay for global teleportation
    private var globalTeleporterDelay: Long = 0
    // Delay for star sprite
    var starSpriteDelay: Long = 0
    // Delay for replenishing run energy
    private var runReplenishDelay: Long = 0
    // Delay for low alchemy
    private var lowAlchemyDelay: Long = 0
    // Delay for minigame teleportation
    private var minigameTeleportDelay: Long = 0
    // Delay for stealing silk
    private var silkSteal: Long = 0
    // Time for Zaf's items
    private var zafTime: Long = 0
    // Delay for double experience
    private var doubleExpDelay: Long = 0
    // Delay for dropping items
    private var dropDelay: Long = 0

    // Status of Luthas' task
    private var luthasTask: Boolean = false
    // Status of the tied Lumbridge rope
    private var lumbridgeRope: Boolean = false
    // Status of the apprentice interaction
    private var apprentice: Boolean = false
    // Status of Fritz's glass
    private var fritzGlass: Boolean = false
    // Status of Wydin's employee
    private var wydinEmployee: Boolean = false
    // Status of Draynor recording
    private var draynorRecording: Boolean = false
    // Status of Grand Exchange tutorial
    private var geTutorial: Boolean = false
    // Status of joining the monastery
    private var joinedMonastery: Boolean = false
    // Status of disabling the death screen
    private var disableDeathScreen: Boolean = false
    // Status of disabling news notifications
    private var disableNews: Boolean = false

    // Status of enabling the bone crusher
    private var enableBoneCrusher: Boolean = false
    // Status of enabling the coin machine
    private var enableCoinMachine: Boolean = false
    // Status of enabling the charm collector
    private var enableCharmCollector: Boolean = false
    // Status of disabling macros
    private var macroDisabled: Boolean = false

    // Array to hold rewards for stronghold
    private var strongHoldRewards: BooleanArray = BooleanArray(4)
    // Array to track read plaques
    var readPlaques: BooleanArray = BooleanArray(7)
    // Array to hold abyss data
    private var abyssData: BooleanArray = BooleanArray(4)
    // Array to hold travel logs
    private var travelLogs: BooleanArray = BooleanArray(45)
    // Array to hold god books
    private var godBooks: BooleanArray = BooleanArray(3)
    // Array to hold god pages
    private var godPages: BooleanArray = BooleanArray(4)


    /**
     * Parse
     *
     * @param data JSON object containing data to parse.
     */
    fun parse(data: JSONObject) {
        // Parse tutorial stage from JSON data
        tutorialStage = data["tutorialStage"].toString().toInt()
        // Parse home teleport delay from JSON data
        homeTeleportDelay = data["homeTeleportDelay"].toString().toLong()
        // Parse status of lumbridge rope from JSON data
        lumbridgeRope = data["lumbridgeRope"] as Boolean
        // Parse status of apprentice interaction from JSON data
        apprentice = data["apprentice"] as Boolean
        // Parse assist time from JSON data
        assistTime = data["assistTime"].toString().toLong()
        // Parse assist experience from JSON data
        val ae = data["assistExperience"] as JSONArray
        for (i in 0 until ae.size) {
            assistExperience[i] = ae[i].toString().toDouble()
        }
        // Parse stronghold rewards from JSON data
        val sr = data["strongHoldRewards"] as JSONArray
        for (i in 0 until sr.size) {
            strongHoldRewards[i] = sr[i] as Boolean
        }
        // Parse chat ping from JSON data
        chatPing = data["chatPing"].toString().toLong()
        // Parse tutor claim time from JSON data
        tutorClaim = data["tutorClaim"].toString().toLong()
        // Parse status of Luthas' task from JSON data
        luthasTask = data["luthasTask"] as Boolean
        // Parse number of Karamja bananas from JSON data
        karamjaBananas = data["karamjaBananas"].toString().toInt()
        // Parse silk steal delay from JSON data
        silkSteal = data["silkSteal"].toString().toLong()
        // Parse Zaf's amount from JSON data
        zafAmount = data["zafAmount"].toString().toInt()
        // Parse Zaf's time from JSON data
        zafTime = data["zafTime"].toString().toLong()
        // Parse Fritz's glass status from JSON data
        fritzGlass = data["fritzGlass"] as Boolean
        // Parse Wydin's employee status from JSON data
        wydinEmployee = data["wydinEmployee"] as Boolean
        // Parse Draynor recording status from JSON data
        draynorRecording = data["draynorRecording"] as Boolean
        // Parse Grand Exchange tutorial status from JSON data
        geTutorial = data["geTutorial"] as Boolean
        // Parse essence teleporter status from JSON data
        essenceTeleporter = data["essenceTeleporter"].toString().toInt()
        // Parse recoil damage from JSON data
        recoilDamage = data["recoilDamage"].toString().toInt()
        // Parse double experience delay from JSON data
        doubleExpDelay = data["doubleExpDelay"].toString().toLong()
        // Parse status of joining the monastery from JSON data
        joinedMonastery = data["joinedMonastery"] as Boolean
        // Parse read plaques from JSON data
        val rp = data["readPlaques"] as JSONArray
        for (i in 0 until rp.size) {
            readPlaques[i] = rp[i] as Boolean
        }
        // Parse number of forging uses from JSON data
        forgingUses = data["forgingUses"].toString().toInt()
        // Parse number of ecto charges from JSON data
        ectoCharges = data["ectoCharges"].toString().toInt()
        // Parse drop delay from JSON data
        dropDelay = data["dropDelay"].toString().toLong()
        // Parse abyss data from JSON data
        val ad = data["abyssData"] as JSONArray
        for (i in 0 until ad.size) {
            abyssData[i] = ad[i] as Boolean
        }
        // Parse runecrafting decay values from JSON data
        val rd = data["rcDecays"] as JSONArray
        for (i in 0 until rd.size) {
            rcDecays[i] = rd[i].toString().toInt()
        }
        // Parse status of disabling the death screen from JSON data
        disableDeathScreen = data["disableDeathScreen"] as Boolean
        // Parse player test stage from JSON data
        playerTestStage = data["playerTestStage"].toString().toInt()
        // Parse charming delay from JSON data
        charmingDelay = data["charmingDelay"].toString().toLong()
        // Parse travel logs from JSON data
        val tl = data["travelLogs"] as JSONArray
        for (i in 0 until tl.size) {
            travelLogs[i] = tl[i] as Boolean
        }
        // Parse god books from JSON data
        val gb = data["godBooks"] as JSONArray
        for (i in 0 until gb.size) {
            godBooks[i] = gb[i] as Boolean
        }
        // Parse status of disabling news notifications from JSON data
        disableNews = data["disableNews"] as Boolean
        // Parse god pages from JSON data
        val gp = data["godPages"] as JSONArray
        for (i in 0 until gp.size) {
            godPages[i] = gp[i] as Boolean
        }
        // Parse overcharge delay from JSON data
        overChargeDelay = data["overChargeDelay"].toString().toLong()
        // Parse boss counters from JSON data
        val bc = data["bossCounters"] as JSONArray
        for (i in 0 until bc.size) {
            bossCounters[i] = bc[i].toString().toInt()
        }
        // Parse number of Barrows loots from JSON data
        barrowsLoots = data["barrowsLoots"].toString().toInt()
        // Parse loot share delay from JSON data
        lootShareDelay = data["lootShareDelay"].toString().toLong()
        // Parse loot share points from JSON data
        lootSharePoints = data["lootSharePoints"].toString().toInt()
        // Parse double experience duration from JSON data
        doubleExp = data["doubleExp"].toString().toLong()
        // Parse global teleporter delay from JSON data
        globalTeleporterDelay = data["globalTeleporterDelay"].toString().toLong()
        // Parse star sprite delay from JSON data
        starSpriteDelay = data["starSpriteDelay"].toString().toLong()
        // Parse run replenish delay from JSON data
        runReplenishDelay = data["runReplenishDelay"].toString().toLong()
        // Parse run replenish charges from JSON data
        runReplenishCharges = data["runReplenishCharges"].toString().toInt()
        // Parse low alchemy charges from JSON data
        lowAlchemyCharges = data["lowAlchemyCharges"].toString().toInt()
        // Parse low alchemy delay from JSON data
        lowAlchemyDelay = data["lowAlchemyDelay"].toString().toLong()
        // Parse magic skill cape delay from JSON data
        magicSkillCapeDelay = data["magicSkillCapeDelay"].toString().toLong()
        // Parse hunter cape delay from JSON data
        hunterCapeDelay = data["hunterCapeDelay"].toString().toLong()
        // Parse hunter cape charges from JSON data
        hunterCapeCharges = data["hunterCapeCharges"].toString().toInt()
        // Parse task amount from JSON data
        taskAmount = data["taskAmount"].toString().toInt()
        // Parse task points from JSON data
        taskPoints = data["taskPoints"].toString().toInt()
        // Parse macro disabled status from JSON data
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
        // Save the X coordinate
        savedX = x
        // Save the Y coordinate
        savedY = y
        // Save the height coordinate
        savedH = z
    }

    /**
     * Get travel logs
     *
     * @return Array of travel logs
     */
    fun getTravelLogs(): BooleanArray {
        // Return the array of travel logs
        return travelLogs
    }

    /**
     * Remove travel log
     *
     * @param index Index of the travel log to remove
     */
    fun removeTravelLog(index: Int) {
        // Set the specified travel log to false (removed)
        travelLogs[index] = false
    }

    /**
     * Has travel log
     *
     * @param index Index of the travel log to check
     * @return True if the travel log exists, otherwise false
     */
    fun hasTravelLog(index: Int): Boolean {
        // Check if the specified travel log exists
        return travelLogs[index]
    }

    /**
     * Set travel log
     *
     * @param index Index of the travel log to set
     */
    fun setTravelLog(index: Int) {
        // Set the specified travel log to true (exists)
        travelLogs[index] = true
    }

    /**
     * Set charming delay
     *
     * @param delay Delay time for charming
     */
    fun setCharmingDelay(delay: Long) {
        // Set the charming delay
        charmingDelay = delay
    }

    /**
     * Get charming delay
     *
     * @return Current charming delay
     */
    fun getCharmingDelay(): Long {
        // Return the current charming delay
        return charmingDelay
    }

    /**
     * Get test stage
     *
     * @return Current player test stage
     */
    fun getTestStage(): Int {
        // Return the current player test stage
        return playerTestStage
    }

    /**
     * Set test stage
     *
     * @param stage New test stage to set
     */
    fun setTestStage(stage: Int) {
        // Set the player test stage to the new value
        playerTestStage = stage
    }

    /**
     * Get tutorial stage
     *
     * @return Current tutorial stage
     */
    fun getTutorialStage(): Int {
        // Return the current tutorial stage
        return tutorialStage
    }

    /**
     * Set tutorial stage
     *
     * @param tutorialStage New tutorial stage to set
     */
    fun setTutorialStage(tutorialStage: Int) {
        // Set the tutorial stage to the new value
        this.tutorialStage = tutorialStage
    }

    /**
     * Get home teleport delay
     *
     * @return Current home teleport delay
     */
    fun getHomeTeleportDelay(): Long {
        // Return the current home teleport delay
        return homeTeleportDelay
    }

    /**
     * Set home teleport delay
     *
     * @param homeTeleportDelay New home teleport delay to set
     */
    fun setHomeTeleportDelay(homeTeleportDelay: Long) {
        // Set the home teleport delay to the new value
        this.homeTeleportDelay = homeTeleportDelay
    }

    /**
     * Has tied lumbridge rope
     *
     * @return True if the lumbridge rope is tied, otherwise false
     */
    fun hasTiedLumbridgeRope(): Boolean {
        // Check if the lumbridge rope is tied
        return lumbridgeRope
    }

    /**
     * Set lumbridge rope
     *
     * @param lumbridgeRope New status for the lumbridge rope
     */
    fun setLumbridgeRope(lumbridgeRope: Boolean) {
        // Set the status of the lumbridge rope
        this.lumbridgeRope = lumbridgeRope
    }

    /**
     * Has spoken to apprentice
     *
     * @return True if the apprentice has been spoken to, otherwise false
     */
    fun hasSpokenToApprentice(): Boolean {
        // Check if the apprentice has been spoken to
        return apprentice
    }

    /**
     * Set apprentice
     *
     * @param apprentice Indicates whether the user is an apprentice
     */
    fun setApprentice(apprentice: Boolean) {
        this.apprentice = apprentice // Assigns the apprentice status to the current instance
    }

    /**
     * Get assist time
     *
     * @return The amount of time spent assisting
     */
    fun getAssistTime(): Long {
        return assistTime // Returns the assist time value
    }

    /**
     * Set assist time
     *
     * @param assistTime The time to be set for assisting
     */
    fun setAssistTime(assistTime: Long) {
        this.assistTime = assistTime // Updates the assist time for the current instance
    }

    /**
     * Get assist experience
     *
     * @return An array of assist experience values
     */
    fun getAssistExperience(): DoubleArray {
        return assistExperience // Returns the array of assist experience
    }

    /**
     * Set assist experience
     *
     * @param assistExperience An array of experience values to be set
     */
    fun setAssistExperience(assistExperience: DoubleArray) {
        this.assistExperience = assistExperience // Updates the assist experience for the current instance
    }

    /**
     * Get strong hold rewards
     *
     * @return An array indicating the stronghold rewards
     */
    fun getStrongHoldRewards(): BooleanArray {
        return strongHoldRewards // Returns the array of stronghold rewards
    }

    /**
     * Has stronghold reward
     *
     * @param reward The index of the reward to check
     * @return True if the reward exists, otherwise false
     */
    fun hasStrongholdReward(reward: Int): Boolean {
        return strongHoldRewards[reward - 1] // Checks if the specified reward is available
    }

    /**
     * Get chat ping
     *
     * @return The current chat ping value
     */
    fun getChatPing(): Long {
        return chatPing // Returns the chat ping value
    }

    /**
     * Set chat ping
     *
     * @param chatPing The ping value to be set for chat
     */
    fun setChatPing(chatPing: Long) {
        this.chatPing = chatPing // Updates the chat ping for the current instance
    }

    /**
     * Get tutor claim
     *
     * @return The current tutor claim value
     */
    fun getTutorClaim(): Long {
        return tutorClaim // Returns the tutor claim value
    }

    /**
     * Set tutor claim
     *
     * @param tutorClaim The claim value to be set for the tutor
     */
    fun setTutorClaim(tutorClaim: Long) {
        this.tutorClaim = tutorClaim // Updates the tutor claim for the current instance
    }

    /**
     * Is luthas task
     *
     * @return True if it is a Luthas task, otherwise false
     */
    fun isLuthasTask(): Boolean {
        return luthasTask // Returns the status of the Luthas task
    }

    /**
     * Set luthas task
     *
     * @param luthasTask Indicates whether it is a Luthas task
     */
    fun setLuthasTask(luthasTask: Boolean) {
        this.luthasTask = luthasTask // Updates the Luthas task status for the current instance
    }

    /**
     * Get karamja bananas
     *
     * @return The number of Karamja bananas
     */
    fun getKaramjaBananas(): Int {
        return karamjaBananas // Returns the count of Karamja bananas
    }

    /**
     * Set karamja bananas
     *
     * @param karamjaBannanas The number of Karamja bananas to be set
     */
    fun setKaramjaBannanas(karamjaBannanas: Int) {
        this.karamjaBananas = karamjaBannanas // Updates the count of Karamja bananas for the current instance
    }

    /**
     * Get silk steal
     *
     * @return The amount of silk stolen
     */
    fun getSilkSteal(): Long {
        return silkSteal // Returns the amount of silk stolen
    }

    /**
     * Set silk steal
     *
     * @param silkSteal The amount of silk to be set as stolen
     */
    fun setSilkSteal(silkSteal: Long) {
        this.silkSteal = silkSteal // Updates the silk steal amount for the current instance
    }

    /**
     * Get zaff amount
     *
     * @return The current amount of Zaff
     */
    fun getZaffAmount(): Int {
        return zafAmount // Returns the amount of Zaff
    }

    /**
     * Set zaff amount
     *
     * @param zaffAmount The amount of Zaff to be set
     */
    fun setZaffAmount(zaffAmount: Int) {
        this.zafAmount = zaffAmount // Updates the Zaff amount for the current instance
    }

    /**
     * Get zaf time
     *
     * @return The current Zaf time
     */
    fun getZafTime(): Long {
        return zafTime // Returns the Zaf time value
    }

    /**
     * Is draynor recording
     *
     * @return True if Draynor is being recorded, otherwise false
     */
    fun isDraynorRecording(): Boolean {
        return draynorRecording // Returns the recording status for Draynor
    }

    /**
     * Set draynor recording
     *
     * @param draynorRecording Indicates whether Draynor is being recorded
     */
    fun setDraynorRecording(draynorRecording: Boolean) {
        this.draynorRecording = draynorRecording // Updates the recording status for Draynor
    }

    /**
     * Is wydin employee
     *
     * @return True if the user is a Wydin employee, otherwise false
     */
    fun isWydinEmployee(): Boolean {
        return wydinEmployee // Returns the employment status for Wydin
    }

    /**
     * Set wydin employee
     *
     * @param wydinEmployee Indicates whether the user is a Wydin employee
     */
    fun setWydinEmployee(wydinEmployee: Boolean) {
        this.wydinEmployee = wydinEmployee // Updates the Wydin employee status for the current instance
    }

    /**
     * Set zaf time
     *
     * @param zaffTime The time to be set for Zaf
     */
    fun setZafTime(zaffTime: Long) {
        this.zafTime = zaffTime // Updates the Zaf time for the current instance
    }

    /**
     * Is fritz glass
     *
     * @return True if Fritz glass is present, otherwise false
     */
    fun isFritzGlass(): Boolean {
        return fritzGlass // Returns the status of Fritz glass
    }

    /**
     * Set fritz glass
     *
     * @param frizGlass Indicates whether Fritz glass is present
     */
    fun setFritzGlass(frizGlass: Boolean) {
        this.fritzGlass = frizGlass // Updates the Fritz glass status for the current instance
    }

    /**
     * Is ge tutorial
     *
     * @return True if the GE tutorial is active, otherwise false
     */
    fun isGeTutorial(): Boolean {
        return geTutorial // Returns the status of the GE tutorial
    }

    /**
     * Set ge tutorial
     *
     * @param geTutorial Indicates whether the GE tutorial is active
     */
    fun setGeTutorial(geTutorial: Boolean) {
        this.geTutorial = geTutorial // Updates the GE tutorial status for the current instance
    }

    /**
     * Get essence teleporter
     *
     * @return The current essence teleporter value
     */
    fun getEssenceTeleporter(): Int {
        return essenceTeleporter // Returns the essence teleporter value
    }

    /**
     * Set essence teleporter
     *
     * @param essenceTeleporter The value to be set for the essence teleporter
     */
    fun setEssenceTeleporter(essenceTeleporter: Int) {
        this.essenceTeleporter = essenceTeleporter // Updates the essence teleporter value for the current instance
    }

    /**
     * Get recoil damage
     *
     * @return The current recoil damage value
     */
    fun getRecoilDamage(): Int {
        return recoilDamage // Returns the recoil damage value
    }

    /**
     * Set recoil damage
     *
     * @param recoilDamage The damage value to be set for recoil
     */
    fun setRecoilDamage(recoilDamage: Int) {
        this.recoilDamage = recoilDamage // Updates the recoil damage for the current instance
    }

    /**
     * Get double exp delay
     *
     * @return The current delay for double experience
     */
    fun getDoubleExpDelay(): Long {
        return doubleExpDelay // Returns the double experience delay value
    }

    /**
     * Set double exp delay
     *
     * @param doubleExpDelay The delay value to be set for double experience
     */
    fun setDoubleExpDelay(doubleExpDelay: Long) {
        this.doubleExpDelay = doubleExpDelay // Updates the double experience delay for the current instance
    }

    /**
     * Checks if the user has joined the monastery.
     *
     * @return true if the user has joined the monastery, false otherwise.
     */
    fun isJoinedMonastery(): Boolean {
        return joinedMonastery // Returns the current status of joinedMonastery
    }

    /**
     * Sets the user's joined monastery status.
     *
     * @param joinedMonastery true if the user has joined the monastery, false otherwise.
     */
    fun setJoinedMonastery(joinedMonastery: Boolean) {
        this.joinedMonastery = joinedMonastery // Updates the joinedMonastery variable with the provided value
    }

    /**
     * Checks if the user has read all plaques.
     *
     * @return true if all plaques have been read, false otherwise.
     */
    fun hasReadPlaques(): Boolean {
        for (i in readPlaques.indices) { // Iterates through the indices of readPlaques
            if (!readPlaques[i]) { // Checks if any plaque has not been read
                return false // Returns false if any plaque is unread
            }
        }
        return true // Returns true if all plaques have been read
    }

    /**
     * Sets the status of a specific god book.
     *
     * @param book the GodBook to be set as read.
     */
    fun setGodBook(book: GodBook) {
        godBooks[book.ordinal] = true // Marks the specified god book as read
    }

    /**
     * Checks if a specific god book has been completed.
     *
     * @param book the GodBook to check.
     * @return true if the god book has been completed, false otherwise.
     */
    fun hasCompletedGodBook(book: GodBook): Boolean {
        return godBooks[book.ordinal] // Returns the completion status of the specified god book
    }

    /**
     * Gets the number of forging uses available.
     *
     * @return the number of forging uses.
     */
    fun getForgingUses(): Int {
        return forgingUses // Returns the current number of forging uses
    }

    /**
     * Sets the number of forging uses available.
     *
     * @param forgingUses the new number of forging uses.
     */
    fun setForgingUses(forgingUses: Int) {
        this.forgingUses = forgingUses // Updates the forgingUses variable with the provided value
    }

    /**
     * Gets the number of ecto charges available.
     *
     * @return the number of ecto charges.
     */
    fun getEctoCharges(): Int {
        return ectoCharges // Returns the current number of ecto charges
    }

    /**
     * Sets the number of ecto charges available.
     *
     * @param ectoCharges the new number of ecto charges.
     */
    fun setEctoCharges(ectoCharges: Int) {
        this.ectoCharges = ectoCharges // Updates the ectoCharges variable with the provided value
    }

    /**
     * Resets the abyss data to its initial state.
     */
    fun resetAbyss() {
        for (i in abyssData.indices) { // Iterates through the indices of abyssData
            abyssData[i] = false // Resets each entry in abyssData to false
        }
    }

    /**
     * Sets a specific abyss charge to true.
     *
     * @param ordinal the index of the abyss charge to set.
     */
    fun setAbyssCharge(ordinal: Int) {
        abyssData[ordinal] = true // Marks the specified abyss charge as true
    }

    /**
     * Checks if a specific abyss charge is active.
     *
     * @param ordinal the index of the abyss charge to check.
     * @return true if the abyss charge is active, false otherwise.
     */
    fun hasAbyssCharge(ordinal: Int): Boolean {
        return abyssData[ordinal] // Returns the status of the specified abyss charge
    }

    /**
     * Gets the current drop delay.
     *
     * @return the current drop delay in milliseconds.
     */
    fun getDropDelay(): Long {
        return dropDelay // Returns the current drop delay
    }

    /**
     * Sets the drop delay.
     *
     * @param dropDelay the new drop delay in milliseconds.
     */
    fun setDropDelay(dropDelay: Long) {
        this.dropDelay = dropDelay // Updates the dropDelay variable with the provided value
    }

    /**
     * Gets the decay value for a specific ordinal.
     *
     * @param ordinal the index of the decay to retrieve.
     * @return the decay value, or 0 if the ordinal is invalid.
     */
    fun getRcDecay(ordinal: Int): Int {
        if (ordinal < 0) { // Checks if the ordinal is negative
            return 0 // Returns 0 for invalid ordinals
        }
        return rcDecays[ordinal] // Returns the decay value for the specified ordinal
    }

    /**
     * Gets all rc decays.
     *
     * @return an array of rc decay values.
     */
    fun getRcDecays(): IntArray {
        return rcDecays // Returns the array of rc decay values
    }

    /**
     * Checks if the death screen is disabled.
     *
     * @return true if the death screen is disabled, false otherwise.
     */
    fun isDeathScreenDisabled(): Boolean {
        return disableDeathScreen // Returns the status of the death screen
    }

    /**
     * Sets the status of the death screen.
     *
     * @param b true to disable the death screen, false to enable it.
     */
    fun setDisableDeathScreen(b: Boolean) {
        this.disableDeathScreen = b // Updates the disableDeathScreen variable with the provided value
    }

    /**
     * Gets the status of god books.
     *
     * @return an array indicating the status of each god book.
     */
    fun getGodBooks(): BooleanArray {
        return godBooks // Returns the array of god book statuses
    }

    /**
     * Sets the status of god books.
     *
     * @param godBooks the new statuses for the god books.
     */
    fun setGodBooks(godBooks: BooleanArray) {
        this.godBooks = godBooks // Updates the godBooks variable with the provided array
    }

    /**
     * Gets the current god book index.
     *
     * @return the index of the current god book.
     */
    fun getGodBook(): Int {
        return godBook // Returns the current god book index
    }

    /**
     * Sets the current god book index.
     *
     * @param godBook the new index for the god book.
     */
    fun setGodBook(godBook: Int) {
        this.godBook = godBook // Updates the godBook variable with the provided value
    }

    /**
     * Checks if news notifications are disabled.
     *
     * @return true if news notifications are disabled, false otherwise.
     */
    fun isDisableNews(): Boolean {
        return disableNews // Returns the status of news notifications
    }

    /**
     * Sets the status of news notifications.
     *
     * @param disableNews true to disable news notifications, false to enable them.
     */
    fun setDisableNews(disableNews: Boolean) {
        this.disableNews = disableNews // Updates the disableNews variable with the provided value
    }

    /**
     * Gets the status of god pages.
     *
     * @return an array indicating the status of each god page.
     */
    fun getGodPages(): BooleanArray {
        return godPages // Returns the array of god page statuses
    }

    /**
     * Sets the status of god pages.
     *
     * @param godPages the new statuses for the god pages.
     */
    fun setGodPages(godPages: BooleanArray) {
        this.godPages = godPages // Updates the godPages variable with the provided array
    }

    /**
     * Gets the current over charge delay.
     *
     * @return the current over charge delay in milliseconds.
     */
    fun getOverChargeDelay(): Long {
        return overChargeDelay // Returns the current over charge delay
    }

    /**
     * Sets the over charge delay.
     *
     * @param overChargeDelay the new over charge delay in milliseconds.
     */
    fun setOverChargeDelay(overChargeDelay: Long) {
        this.overChargeDelay = overChargeDelay // Updates the overChargeDelay variable with the provided value
    }

    /**
     * Gets the current boss counters.
     *
     * @return an array of boss counters.
     */
    fun getBossCounters(): IntArray {
        return bossCounters // Returns the array of boss counters
    }

    /**
     * Sets the boss counters.
     *
     * @param bossCounters the new values for the boss counters.
     */
    fun setBossCounters(bossCounters: IntArray) {
        this.bossCounters = bossCounters // Updates the bossCounters variable with the provided array
    }

    /**
     * Gets the current barrows loots count.
     *
     * @return the number of barrows loots.
     */
    fun getBarrowsLoots(): Int {
        return barrowsLoots // Returns the current number of barrows loots
    }

    /**
     * Sets the barrows loots count.
     *
     * @param barrowsLoots the new number of barrows loots.
     */
    fun setBarrowsLoots(barrowsLoots: Int) {
        this.barrowsLoots = barrowsLoots // Updates the barrowsLoots variable with the provided value
    }

    /**
     * Gets the current loot share points.
     *
     * @return the number of loot share points.
     */
    fun getLootSharePoints(): Int {
        return lootSharePoints // Returns the current number of loot share points
    }

    /**
     * Sets the loot share points.
     *
     * @param lootSharePoints the new number of loot share points.
     */
    fun setLootSharePoints(lootSharePoints: Int) {
        this.lootSharePoints = lootSharePoints // Updates the lootSharePoints variable with the provided value
    }

    /**
     * Gets the current loot share delay.
     *
     * @return the current loot share delay in milliseconds.
     */
    fun getLootShareDelay(): Long {
        return lootShareDelay // Returns the current loot share delay
    }

    /**
     * Set loot share delay
     *
     * @param lootShareDelay The delay time for loot sharing in milliseconds.
     */
    fun setLootShareDelay(lootShareDelay: Long) {
        this.lootShareDelay = lootShareDelay // Assign the provided delay to the class variable.
    }

    /**
     * Get double exp
     *
     * @return The current double experience value.
     */
    fun getDoubleExp(): Long {
        return doubleExp // Return the value of double experience.
    }

    /**
     * Set double exp
     *
     * @param doubleExp The new double experience value to be set.
     */
    fun setDoubleExp(doubleExp: Long) {
        this.doubleExp = doubleExp // Update the class variable with the new double experience value.
    }

    /**
     * Has double exp
     *
     * @return True if double experience is active, otherwise false.
     */
    fun hasDoubleExp(): Boolean {
        return doubleExp > System.currentTimeMillis() // Check if the current time is less than the double experience time.
    }

    /**
     * Get global teleporter delay
     *
     * @return The current global teleporter delay in milliseconds.
     */
    fun getGlobalTeleporterDelay(): Long {
        return globalTeleporterDelay // Return the global teleporter delay value.
    }

    /**
     * Set global teleporter delay
     *
     * @param globalTeleporterDelay The new delay time for the global teleporter.
     */
    fun setGlobalTeleporterDelay(globalTeleporterDelay: Long) {
        this.globalTeleporterDelay = globalTeleporterDelay // Update the class variable with the new delay.
    }

    /**
     * Get run replenish delay
     *
     * @return The current run replenish delay in milliseconds.
     */
    fun getRunReplenishDelay(): Long {
        return runReplenishDelay // Return the run replenish delay value.
    }

    /**
     * Set run replenish delay
     *
     * @param runReplenishDelay The new delay time for run replenishment.
     */
    fun setRunReplenishDelay(runReplenishDelay: Long) {
        this.runReplenishDelay = runReplenishDelay // Update the class variable with the new delay.
    }

    /**
     * Get run replenish charges
     *
     * @return The current number of run replenish charges.
     */
    fun getRunReplenishCharges(): Int {
        return runReplenishCharges // Return the number of run replenish charges.
    }

    /**
     * Set run replenish charges
     *
     * @param runReplenishCharges The new number of run replenish charges to be set.
     */
    fun setRunReplenishCharges(runReplenishCharges: Int) {
        this.runReplenishCharges = runReplenishCharges // Update the class variable with the new charges.
    }

    /**
     * Get low alchemy charges
     *
     * @return The current number of low alchemy charges.
     */
    fun getLowAlchemyCharges(): Int {
        return lowAlchemyCharges // Return the number of low alchemy charges.
    }

    /**
     * Set low alchemy charges
     *
     * @param lowAlchemyCharges The new number of low alchemy charges to be set.
     */
    fun setLowAlchemyCharges(lowAlchemyCharges: Int) {
        this.lowAlchemyCharges = lowAlchemyCharges // Update the class variable with the new charges.
    }

    /**
     * Get low alchemy delay
     *
     * @return The current low alchemy delay in milliseconds.
     */
    fun getLowAlchemyDelay(): Long {
        return lowAlchemyDelay // Return the low alchemy delay value.
    }

    /**
     * Set low alchemy delay
     *
     * @param lowAlchemyDelay The new delay time for low alchemy.
     */
    fun setLowAlchemyDelay(lowAlchemyDelay: Long) {
        this.lowAlchemyDelay = lowAlchemyDelay // Update the class variable with the new delay.
    }

    /**
     * Is enable bone crusher
     *
     * @return True if the bone crusher is enabled, otherwise false.
     */
    fun isEnableBoneCrusher(): Boolean {
        return enableBoneCrusher // Return the status of the bone crusher.
    }

    /**
     * Set enable bone crusher
     *
     * @param enableBoneCrusher The new status for enabling the bone crusher.
     */
    fun setEnableBoneCrusher(enableBoneCrusher: Boolean) {
        this.enableBoneCrusher = enableBoneCrusher // Update the class variable with the new status.
    }

    /**
     * Is enable coin machine
     *
     * @return True if the coin machine is enabled, otherwise false.
     */
    fun isEnableCoinMachine(): Boolean {
        return enableCoinMachine // Return the status of the coin machine.
    }

    /**
     * Set enable coin machine
     *
     * @param enableCoinMachine The new status for enabling the coin machine.
     */
    fun setEnableCoinMachine(enableCoinMachine: Boolean) {
        this.enableCoinMachine = enableCoinMachine // Update the class variable with the new status.
    }

    /**
     * Get magic skill cape delay
     *
     * @return The current magic skill cape delay in milliseconds.
     */
    fun getMagicSkillCapeDelay(): Long {
        return magicSkillCapeDelay // Return the magic skill cape delay value.
    }

    /**
     * Set magic skill cape delay
     *
     * @param magicSkillCapeDelay The new delay time for the magic skill cape.
     */
    fun setMagicSkillCapeDelay(magicSkillCapeDelay: Long) {
        this.magicSkillCapeDelay = magicSkillCapeDelay // Update the class variable with the new delay.
    }

    /**
     * Get hunter cape delay
     *
     * @return The current hunter cape delay in milliseconds.
     */
    fun getHunterCapeDelay(): Long {
        return hunterCapeDelay // Return the hunter cape delay value.
    }

    /**
     * Set hunter cape delay
     *
     * @param hunterCapeDelay The new delay time for the hunter cape.
     */
    fun setHunterCapeDelay(hunterCapeDelay: Long) {
        this.hunterCapeDelay = hunterCapeDelay // Update the class variable with the new delay.
    }

    /**
     * Get hunter cape charges
     *
     * @return The current number of hunter cape charges.
     */
    fun getHunterCapeCharges(): Int {
        return hunterCapeCharges // Return the number of hunter cape charges.
    }

    /**
     * Set hunter cape charges
     *
     * @param hunterCapeCharges The new number of hunter cape charges to be set.
     */
    fun setHunterCapeCharges(hunterCapeCharges: Int) {
        this.hunterCapeCharges = hunterCapeCharges // Update the class variable with the new charges.
    }

    /**
     * Is enable charm collector
     *
     * @return True if the charm collector is enabled, otherwise false.
     */
    fun isEnableCharmCollector(): Boolean {
        return enableCharmCollector // Return the status of the charm collector.
    }

    /**
     * Set enable charm collector
     *
     * @param enableCharmCollector The new status for enabling the charm collector.
     */
    fun setEnableCharmCollector(enableCharmCollector: Boolean) {
        this.enableCharmCollector = enableCharmCollector // Update the class variable with the new status.
    }

    /**
     * Get minigame teleport delay
     *
     * @return The current minigame teleport delay in milliseconds.
     */
    fun getMinigameTeleportDelay(): Long {
        return minigameTeleportDelay // Return the minigame teleport delay value.
    }

    /**
     * Set minigame teleport delay
     *
     * @param delay The new delay time for minigame teleportation.
     */
    fun setMinigameTeleportDelay(delay: Long) {
        this.minigameTeleportDelay = delay // Update the class variable with the new delay.
    }

    /**
     * Get saved h
     *
     * @return The current saved horizontal position.
     */
    fun getSavedH(): Int {
        return savedH // Return the saved horizontal position.
    }

    /**
     * Set saved h
     *
     * @param savedH The new horizontal position to be saved.
     */
    fun setSavedH(savedH: Int) {
        this.savedH = savedH // Update the class variable with the new horizontal position.
    }

    /**
     * Get saved y
     *
     * @return The current saved vertical position.
     */
    fun getSavedY(): Int {
        return savedY // Return the saved vertical position.
    }

    /**
     * Set saved y
     *
     * @param savedY The new vertical position to be saved.
     */
    fun setSavedY(savedY: Int) {
        this.savedY = savedY // Update the class variable with the new vertical position.
    }

    /**
     * Get saved x
     *
     * @return The current saved horizontal position.
     */
    fun getSavedX(): Int {
        return savedX // Return the saved horizontal position.
    }

    /**
     * Set saved x
     *
     * @param savedX The new horizontal position to be saved.
     */
    fun setSavedX(savedX: Int) {
        this.savedX = savedX // Update the class variable with the new horizontal position.
    }

    /**
     * Get task amount
     *
     * @return The current number of tasks.
     */
    fun getTaskAmount(): Int {
        return taskAmount // Return the number of tasks.
    }

    /**
     * Set task amount
     *
     * @param taskAmount The new number of tasks to be set.
     */
    fun setTaskAmount(taskAmount: Int) {
        this.taskAmount = taskAmount // Update the class variable with the new task amount.
    }

    /**
     * Get task points
     *
     * @return The current number of task points.
     */
    fun getTaskPoints(): Int {
        return taskPoints // Return the number of task points.
    }

    /**
     * Set task points
     *
     * @param taskPoints The new number of task points to be set.
     */
    fun setTaskPoints(taskPoints: Int) {
        this.taskPoints = taskPoints // Update the class variable with the new task points.
    }

    /**
     * Set macro disabled
     *
     * @param disabled The new status for disabling the macro.
     */
    fun setMacroDisabled(disabled: Boolean) {
        this.macroDisabled = disabled // Update the class variable with the new macro status.
    }

    /**
     * Get macro disabled
     *
     * @return True if the macro is disabled, otherwise false.
     */
    fun getMacroDisabled(): Boolean {
        return this.macroDisabled // Return the status of the macro.
    }

    /**
     * Get abyss data
     *
     * @return The current abyss data as a boolean array.
     */
    fun getAbyssData(): BooleanArray {
        return abyssData // Return the abyss data.
    }

    /**
     * Get player test stage
     *
     * @return The current player test stage.
     */
    fun getPlayerTestStage(): Int {
        return playerTestStage // Return the current player test stage.
    }
}