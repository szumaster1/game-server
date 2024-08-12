package core.game.node.entity.player.link

import content.data.GodBook
import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 * Global data.
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

    private var enableBoneCrusher: Boolean = false
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
     * @param data
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
     * @param x
     * @param y
     * @param z
     */
    fun setSavedLocation(x: Int, y: Int, z: Int) {
        savedX = x
        savedY = y
        savedH = z
    }

    /**
     * Get travel logs
     *
     * @return
     */
    fun getTravelLogs(): BooleanArray {
        return travelLogs
    }

    /**
     * Remove travel log
     *
     * @param index
     */
    fun removeTravelLog(index: Int) {
        travelLogs[index] = false
    }

    /**
     * Has travel log
     *
     * @param index
     * @return
     */
    fun hasTravelLog(index: Int): Boolean {
        return travelLogs[index]
    }

    /**
     * Set travel log
     *
     * @param index
     */
    fun setTravelLog(index: Int) {
        travelLogs[index] = true
    }

    /**
     * Set charming delay
     *
     * @param delay
     */
    fun setCharmingDelay(delay: Long) {
        charmingDelay = delay
    }

    /**
     * Get charming delay
     *
     * @return
     */
    fun getCharmingDelay(): Long {
        return charmingDelay
    }

    /**
     * Get test stage
     *
     * @return
     */
    fun getTestStage(): Int {
        return playerTestStage
    }

    /**
     * Set test stage
     *
     * @param stage
     */
    fun setTestStage(stage: Int) {
        playerTestStage = stage
    }

    /**
     * Get tutorial stage
     *
     * @return
     */
    fun getTutorialStage(): Int {
        return tutorialStage
    }

    /**
     * Set tutorial stage
     *
     * @param tutorialStage
     */
    fun setTutorialStage(tutorialStage: Int) {
        this.tutorialStage = tutorialStage
    }

    /**
     * Get home teleport delay
     *
     * @return
     */
    fun getHomeTeleportDelay(): Long {
        return homeTeleportDelay
    }

    /**
     * Set home teleport delay
     *
     * @param homeTeleportDelay
     */
    fun setHomeTeleportDelay(homeTeleportDelay: Long) {
        this.homeTeleportDelay = homeTeleportDelay
    }

    /**
     * Has tied lumbridge rope
     *
     * @return
     */
    fun hasTiedLumbridgeRope(): Boolean {
        return lumbridgeRope
    }

    /**
     * Set lumbridge rope
     *
     * @param lumbridgeRope
     */
    fun setLumbridgeRope(lumbridgeRope: Boolean) {
        this.lumbridgeRope = lumbridgeRope
    }

    /**
     * Has spoken to apprentice
     *
     * @return
     */
    fun hasSpokenToApprentice(): Boolean {
        return apprentice
    }

    /**
     * Set apprentice
     *
     * @param apprentice
     */
    fun setApprentice(apprentice: Boolean) {
        this.apprentice = apprentice
    }

    /**
     * Get assist time
     *
     * @return
     */
    fun getAssistTime(): Long {
        return assistTime
    }

    /**
     * Set assist time
     *
     * @param assistTime
     */
    fun setAssistTime(assistTime: Long) {
        this.assistTime = assistTime
    }

    /**
     * Get assist experience
     *
     * @return
     */
    fun getAssistExperience(): DoubleArray {
        return assistExperience
    }

    /**
     * Set assist experience
     *
     * @param assistExperience
     */
    fun setAssistExperience(assistExperience: DoubleArray) {
        this.assistExperience = assistExperience
    }

    /**
     * Get strong hold rewards
     *
     * @return
     */
    fun getStrongHoldRewards(): BooleanArray {
        return strongHoldRewards
    }

    /**
     * Has stronghold reward
     *
     * @param reward
     * @return
     */
    fun hasStrongholdReward(reward: Int): Boolean {
        return strongHoldRewards[reward - 1]
    }

    /**
     * Get chat ping
     *
     * @return
     */
    fun getChatPing(): Long {
        return chatPing
    }

    /**
     * Set chat ping
     *
     * @param chatPing
     */
    fun setChatPing(chatPing: Long) {
        this.chatPing = chatPing
    }

    /**
     * Get tutor claim
     *
     * @return
     */
    fun getTutorClaim(): Long {
        return tutorClaim
    }

    /**
     * Set tutor claim
     *
     * @param tutorClaim
     */
    fun setTutorClaim(tutorClaim: Long) {
        this.tutorClaim = tutorClaim
    }

    /**
     * Is luthas task
     *
     * @return
     */
    fun isLuthasTask(): Boolean {
        return luthasTask
    }

    /**
     * Set luthas task
     *
     * @param luthasTask
     */
    fun setLuthasTask(luthasTask: Boolean) {
        this.luthasTask = luthasTask
    }

    /**
     * Get karamja bananas
     *
     * @return
     */
    fun getKaramjaBananas(): Int {
        return karamjaBananas
    }

    /**
     * Set karamja bannanas
     *
     * @param karamjaBannanas
     */
    fun setKaramjaBannanas(karamjaBannanas: Int) {
        this.karamjaBananas = karamjaBannanas
    }

    /**
     * Get silk steal
     *
     * @return
     */
    fun getSilkSteal(): Long {
        return silkSteal
    }

    /**
     * Set silk steal
     *
     * @param silkSteal
     */
    fun setSilkSteal(silkSteal: Long) {
        this.silkSteal = silkSteal
    }

    /**
     * Get zaff amount
     *
     * @return
     */
    fun getZaffAmount(): Int {
        return zafAmount
    }

    /**
     * Set zaff amount
     *
     * @param zaffAmount
     */
    fun setZaffAmount(zaffAmount: Int) {
        this.zafAmount = zaffAmount
    }

    /**
     * Get zaf time
     *
     * @return
     */
    fun getZafTime(): Long {
        return zafTime
    }

    /**
     * Is draynor recording
     *
     * @return
     */
    fun isDraynorRecording(): Boolean {
        return draynorRecording
    }

    /**
     * Set draynor recording
     *
     * @param draynorRecording
     */
    fun setDraynorRecording(draynorRecording: Boolean) {
        this.draynorRecording = draynorRecording
    }

    /**
     * Is wydin employee
     *
     * @return
     */
    fun isWydinEmployee(): Boolean {
        return wydinEmployee
    }

    /**
     * Set wydin employee
     *
     * @param wydinEmployee
     */
    fun setWydinEmployee(wydinEmployee: Boolean) {
        this.wydinEmployee = wydinEmployee
    }

    /**
     * Set zaf time
     *
     * @param zaffTime
     */
    fun setZafTime(zaffTime: Long) {
        this.zafTime = zaffTime
    }

    /**
     * Is fritz glass
     *
     * @return
     */
    fun isFritzGlass(): Boolean {
        return fritzGlass
    }

    /**
     * Set fritz glass
     *
     * @param frizGlass
     */
    fun setFritzGlass(frizGlass: Boolean) {
        this.fritzGlass = frizGlass
    }

    /**
     * Is ge tutorial
     *
     * @return
     */
    fun isGeTutorial(): Boolean {
        return geTutorial
    }

    /**
     * Set ge tutorial
     *
     * @param geTutorial
     */
    fun setGeTutorial(geTutorial: Boolean) {
        this.geTutorial = geTutorial
    }

    /**
     * Get essence teleporter
     *
     * @return
     */
    fun getEssenceTeleporter(): Int {
        return essenceTeleporter
    }

    /**
     * Set essence teleporter
     *
     * @param essenceTeleporter
     */
    fun setEssenceTeleporter(essenceTeleporter: Int) {
        this.essenceTeleporter = essenceTeleporter
    }

    /**
     * Get recoil damage
     *
     * @return
     */
    fun getRecoilDamage(): Int {
        return recoilDamage
    }

    /**
     * Set recoil damage
     *
     * @param recoilDamage
     */
    fun setRecoilDamage(recoilDamage: Int) {
        this.recoilDamage = recoilDamage
    }

    /**
     * Get double exp delay
     *
     * @return
     */
    fun getDoubleExpDelay(): Long {
        return doubleExpDelay
    }

    /**
     * Set double exp delay
     *
     * @param doubleExpDelay
     */
    fun setDoubleExpDelay(doubleExpDelay: Long) {
        this.doubleExpDelay = doubleExpDelay
    }

    /**
     * Is joined monastery
     *
     * @return
     */
    fun isJoinedMonastery(): Boolean {
        return joinedMonastery
    }

    /**
     * Set joined monastery
     *
     * @param joinedMonastery
     */
    fun setJoinedMonastery(joinedMonastery: Boolean) {
        this.joinedMonastery = joinedMonastery
    }


    /**
     * Has read plaques
     *
     * @return
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
     * Set god book
     *
     * @param book
     */
    fun setGodBook(book: GodBook) {
        godBooks[book.ordinal] = true
    }

    /**
     * Has completed god book
     *
     * @param book
     * @return
     */
    fun hasCompletedGodBook(book: GodBook): Boolean {
        return godBooks[book.ordinal]
    }

    /**
     * Get forging uses
     *
     * @return
     */
    fun getForgingUses(): Int {
        return forgingUses
    }

    /**
     * Set forging uses
     *
     * @param forgingUses
     */
    fun setForgingUses(forgingUses: Int) {
        this.forgingUses = forgingUses
    }

    /**
     * Get ecto charges
     *
     * @return
     */
    fun getEctoCharges(): Int {
        return ectoCharges
    }

    /**
     * Set ecto charges
     *
     * @param ectoCharges
     */
    fun setEctoCharges(ectoCharges: Int) {
        this.ectoCharges = ectoCharges
    }

    /**
     * Reset abyss
     *
     */
    fun resetAbyss() {
        for (i in abyssData.indices) {
            abyssData[i] = false
        }
    }

    /**
     * Set abyss charge
     *
     * @param ordinal
     */
    fun setAbyssCharge(ordinal: Int) {
        abyssData[ordinal] = true
    }

    /**
     * Has abyss charge
     *
     * @param ordinal
     * @return
     */
    fun hasAbyssCharge(ordinal: Int): Boolean {
        return abyssData[ordinal]
    }

    /**
     * Get drop delay
     *
     * @return
     */
    fun getDropDelay(): Long {
        return dropDelay
    }

    /**
     * Set drop delay
     *
     * @param dropDelay
     */
    fun setDropDelay(dropDelay: Long) {
        this.dropDelay = dropDelay
    }

    /**
     * Get rc decay
     *
     * @param ordinal
     * @return
     */
    fun getRcDecay(ordinal: Int): Int {
        if (ordinal < 0) {
            return 0
        }
        return rcDecays[ordinal]
    }

    /**
     * Get rc decays
     *
     * @return
     */
    fun getRcDecays(): IntArray {
        return rcDecays
    }

    /**
     * Is death screen disabled
     *
     * @return
     */
    fun isDeathScreenDisabled(): Boolean {
        return disableDeathScreen
    }

    /**
     * Set disable death screen
     *
     * @param b
     */
    fun setDisableDeathScreen(b: Boolean) {
        this.disableDeathScreen = b
    }

    /**
     * Get god books
     *
     * @return
     */
    fun getGodBooks(): BooleanArray {
        return godBooks
    }

    /**
     * Set god books
     *
     * @param godBooks
     */
    fun setGodBooks(godBooks: BooleanArray) {
        this.godBooks = godBooks
    }

    /**
     * Get god book
     *
     * @return
     */
    fun getGodBook(): Int {
        return godBook
    }

    /**
     * Set god book
     *
     * @param godBook
     */
    fun setGodBook(godBook: Int) {
        this.godBook = godBook
    }

    /**
     * Is disable news
     *
     * @return
     */
    fun isDisableNews(): Boolean {
        return disableNews
    }

    /**
     * Set disable news
     *
     * @param disableNews
     */
    fun setDisableNews(disableNews: Boolean) {
        this.disableNews = disableNews
    }

    /**
     * Get god pages
     *
     * @return
     */
    fun getGodPages(): BooleanArray {
        return godPages
    }

    /**
     * Set god pages
     *
     * @param godPages
     */
    fun setGodPages(godPages: BooleanArray) {
        this.godPages = godPages
    }

    /**
     * Get over charge delay
     *
     * @return
     */
    fun getOverChargeDelay(): Long {
        return overChargeDelay
    }

    /**
     * Set over charge delay
     *
     * @param overChargeDelay
     */
    fun setOverChargeDelay(overChargeDelay: Long) {
        this.overChargeDelay = overChargeDelay
    }

    /**
     * Get boss counters
     *
     * @return
     */
    fun getBossCounters(): IntArray {
        return bossCounters
    }

    /**
     * Set boss counters
     *
     * @param bossCounters
     */
    fun setBossCounters(bossCounters: IntArray) {
        this.bossCounters = bossCounters
    }

    /**
     * Get barrows loots
     *
     * @return
     */
    fun getBarrowsLoots(): Int {
        return barrowsLoots
    }

    /**
     * Set barrows loots
     *
     * @param barrowsLoots
     */
    fun setBarrowsLoots(barrowsLoots: Int) {
        this.barrowsLoots = barrowsLoots
    }

    /**
     * Get loot share points
     *
     * @return
     */
    fun getLootSharePoints(): Int {
        return lootSharePoints
    }

    /**
     * Set loot share points
     *
     * @param lootSharePoints
     */
    fun setLootSharePoints(lootSharePoints: Int) {
        this.lootSharePoints = lootSharePoints
    }

    /**
     * Get loot share delay
     *
     * @return
     */
    fun getLootShareDelay(): Long {
        return lootShareDelay
    }

    /**
     * Set loot share delay
     *
     * @param lootShareDelay
     */
    fun setLootShareDelay(lootShareDelay: Long) {
        this.lootShareDelay = lootShareDelay
    }

    /**
     * Get double exp
     *
     * @return
     */
    fun getDoubleExp(): Long {
        return doubleExp
    }

    /**
     * Set double exp
     *
     * @param doubleExp
     */
    fun setDoubleExp(doubleExp: Long) {
        this.doubleExp = doubleExp
    }

    /**
     * Has double exp
     *
     * @return
     */
    fun hasDoubleExp(): Boolean {
        return doubleExp > System.currentTimeMillis()
    }

    /**
     * Get global teleporter delay
     *
     * @return
     */
    fun getGlobalTeleporterDelay(): Long {
        return globalTeleporterDelay
    }

    /**
     * Set global teleporter delay
     *
     * @param globalTeleporterDelay
     */
    fun setGlobalTeleporterDelay(globalTeleporterDelay: Long) {
        this.globalTeleporterDelay = globalTeleporterDelay
    }

    /**
     * Get run replenish delay
     *
     * @return
     */
    fun getRunReplenishDelay(): Long {
        return runReplenishDelay
    }

    /**
     * Set run replenish delay
     *
     * @param runReplenishDelay
     */
    fun setRunReplenishDelay(runReplenishDelay: Long) {
        this.runReplenishDelay = runReplenishDelay
    }

    /**
     * Get run replenish charges
     *
     * @return
     */
    fun getRunReplenishCharges(): Int {
        return runReplenishCharges
    }

    /**
     * Set run replenish charges
     *
     * @param runReplenishCharges
     */
    fun setRunReplenishCharges(runReplenishCharges: Int) {
        this.runReplenishCharges = runReplenishCharges
    }

    /**
     * Get low alchemy charges
     *
     * @return
     */
    fun getLowAlchemyCharges(): Int {
        return lowAlchemyCharges
    }

    /**
     * Set low alchemy charges
     *
     * @param lowAlchemyCharges
     */
    fun setLowAlchemyCharges(lowAlchemyCharges: Int) {
        this.lowAlchemyCharges = lowAlchemyCharges
    }

    /**
     * Get low alchemy delay
     *
     * @return
     */
    fun getLowAlchemyDelay(): Long {
        return lowAlchemyDelay
    }

    /**
     * Set low alchemy delay
     *
     * @param lowAlchemyDelay
     */
    fun setLowAlchemyDelay(lowAlchemyDelay: Long) {
        this.lowAlchemyDelay = lowAlchemyDelay
    }

    /**
     * Is enable bone crusher
     *
     * @return
     */
    fun isEnableBoneCrusher(): Boolean {
        return enableBoneCrusher
    }

    /**
     * Set enable bone crusher
     *
     * @param enableBoneCrusher
     */
    fun setEnableBoneCrusher(enableBoneCrusher: Boolean) {
        this.enableBoneCrusher = enableBoneCrusher
    }

    /**
     * Is enable coin machine
     *
     * @return
     */
    fun isEnableCoinMachine(): Boolean {
        return enableCoinMachine
    }

    /**
     * Set enable coin machine
     *
     * @param enableCoinMachine
     */
    fun setEnableCoinMachine(enableCoinMachine: Boolean) {
        this.enableCoinMachine = enableCoinMachine
    }

    /**
     * Get magic skill cape delay
     *
     * @return
     */
    fun getMagicSkillCapeDelay(): Long {
        return magicSkillCapeDelay
    }

    /**
     * Set magic skill cape delay
     *
     * @param magicSkillCapeDelay
     */
    fun setMagicSkillCapeDelay(magicSkillCapeDelay: Long) {
        this.magicSkillCapeDelay = magicSkillCapeDelay
    }

    /**
     * Get hunter cape delay
     *
     * @return
     */
    fun getHunterCapeDelay(): Long {
        return hunterCapeDelay
    }

    /**
     * Set hunter cape delay
     *
     * @param hunterCapeDelay
     */
    fun setHunterCapeDelay(hunterCapeDelay: Long) {
        this.hunterCapeDelay = hunterCapeDelay
    }

    /**
     * Get hunter cape charges
     *
     * @return
     */
    fun getHunterCapeCharges(): Int {
        return hunterCapeCharges
    }

    /**
     * Set hunter cape charges
     *
     * @param hunterCapeCharges
     */
    fun setHunterCapeCharges(hunterCapeCharges: Int) {
        this.hunterCapeCharges = hunterCapeCharges
    }

    /**
     * Is enable charm collector
     *
     * @return
     */
    fun isEnableCharmCollector(): Boolean {
        return enableCharmCollector
    }

    /**
     * Set enable charm collector
     *
     * @param enableCharmCollector
     */
    fun setEnableCharmCollector(enableCharmCollector: Boolean) {
        this.enableCharmCollector = enableCharmCollector
    }

    /**
     * Get minigame teleport delay
     *
     * @return
     */
    fun getMinigameTeleportDelay(): Long {
        return minigameTeleportDelay
    }

    /**
     * Set minigame teleport delay
     *
     * @param delay
     */
    fun setMinigameTeleportDelay(delay: Long) {
        this.minigameTeleportDelay = delay
    }

    /**
     * Get saved h
     *
     * @return
     */
    fun getSavedH(): Int {
        return savedH
    }

    /**
     * Set saved h
     *
     * @param savedH
     */
    fun setSavedH(savedH: Int) {
        this.savedH = savedH
    }

    /**
     * Get saved y
     *
     * @return
     */
    fun getSavedY(): Int {
        return savedY
    }

    /**
     * Set saved y
     *
     * @param savedY
     */
    fun setSavedY(savedY: Int) {
        this.savedY = savedY
    }

    /**
     * Get saved x
     *
     * @return
     */
    fun getSavedX(): Int {
        return savedX
    }

    /**
     * Set saved x
     *
     * @param savedX
     */
    fun setSavedX(savedX: Int) {
        this.savedX = savedX
    }

    /**
     * Get task amount
     *
     * @return
     */
    fun getTaskAmount(): Int {
        return taskAmount
    }

    /**
     * Set task amount
     *
     * @param taskAmount
     */
    fun setTaskAmount(taskAmount: Int) {
        this.taskAmount = taskAmount
    }

    /**
     * Get task points
     *
     * @return
     */
    fun getTaskPoints(): Int {
        return taskPoints
    }

    /**
     * Set task points
     *
     * @param taskPoints
     */
    fun setTaskPoints(taskPoints: Int) {
        this.taskPoints = taskPoints
    }

    /**
     * Set macro disabled
     *
     * @param disabled
     */
    fun setMacroDisabled(disabled: Boolean) {
        this.macroDisabled = disabled
    }

    /**
     * Get macro disabled
     *
     * @return
     */
    fun getMacroDisabled(): Boolean {
        return this.macroDisabled
    }

    /**
     * Get abyss data
     *
     * @return
     */
    fun getAbyssData(): BooleanArray {
        return abyssData
    }

    /**
     * Get player test stage
     *
     * @return
     */
    fun getPlayerTestStage(): Int {
        return playerTestStage
    }
}
