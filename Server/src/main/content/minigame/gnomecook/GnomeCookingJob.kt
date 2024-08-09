package content.minigame.gnomecook

/**
 * Gnome cooking job.
 *
 * @property level The level of difficulty for the cooking job.
 * @property npc_id The ID of the NPC associated with the cooking job.
 * @property tip A tip or hint related to the cooking job.
 * @constructor Creates a Gnome cooking job with the specified level, NPC ID, and tip.
 */
enum class GnomeCookingJob(val level: GnomeTipper.LEVEL, val npc_id: Int, val tip: String) {
    /**
     * Cpt Errdo
     *
     * @constructor Creates a Gnome cooking job with Cpt Errdo as the NPC
     */
    CPT_ERRDO(GnomeTipper.LEVEL.EASY, 3811, "at the top level of the Grand Tree."),

    /**
     * Dalilah
     *
     * @constructor Creates a Gnome cooking job with Dalilah as the NPC
     */
    DALILAH(GnomeTipper.LEVEL.EASY, 4588, "sitting in the Gnome Restaurant."),

    /**
     * Gulluck
     *
     * @constructor Creates a Gnome cooking job with Gulluck as the NPC
     */
    GULLUCK(GnomeTipper.LEVEL.EASY, 602, "on the third level of the Grand Tree."),

    /**
     * Rometti
     *
     * @constructor Creates a Gnome cooking job with Rometti as the NPC
     */
    ROMETTI(GnomeTipper.LEVEL.EASY, 601, "on the second level of the Grand Tree."),

    /**
     * Narnode
     *
     * @constructor Creates a Gnome cooking job with Narnode as the NPC
     */
    NARNODE(GnomeTipper.LEVEL.EASY, 670, "at the base of the Grand Tree."),

    /**
     * Meegle
     *
     * @constructor Creates a Gnome cooking job with Meegle as the NPC
     */
    MEEGLE(GnomeTipper.LEVEL.EASY, 4597, "in the terrorbird enclosure."),

    /**
     * Perrdur
     *
     * @constructor Creates a Gnome cooking job with Perrdur as the NPC
     */
    PERRDUR(GnomeTipper.LEVEL.EASY, 4587, "sitting in the Gnome Restaurant."),

    /**
     * Sarble
     *
     * @constructor Creates a Gnome cooking job with Sarble as the NPC
     */
    SARBLE(GnomeTipper.LEVEL.EASY, 4599, "in the swamp west of the Grand Tree."),

    /**
     * Gimlewap
     *
     * @constructor Creates a Gnome cooking job with Gimlewap as the NPC
     */
    GIMLEWAP(GnomeTipper.LEVEL.HARD, 4580, "upstairs in Ardougne castle."),

    /**
     * Bleemadge
     *
     * @constructor Creates a Gnome cooking job with Bleemadge as the NPC
     */
    BLEEMADGE(GnomeTipper.LEVEL.HARD, 3810, "at the top of White Wolf Mountain."),

    /**
     * Dalbur
     *
     * @constructor Creates a Gnome cooking job with Dalbur as the NPC
     */
    DALBUR(GnomeTipper.LEVEL.HARD, 3809, "by the gnome glider in Al Kharid"),

    /**
     * Bolren
     *
     * @constructor Creates a Gnome cooking job with Bolren as the NPC
     */
    BOLREN(GnomeTipper.LEVEL.HARD, 469, "next to the Spirit Tree in Tree Gnome Village"),

    /**
     * Schepbur
     *
     * @constructor Creates a Gnome cooking job with Schepbur as the NPC
     */
    SCHEPBUR(GnomeTipper.LEVEL.HARD, 3817, "in the battlefield of Khazar, south of the river."),

    /**
     * Imblewyn
     *
     * @constructor Creates a Gnome cooking job with Imblewyn as the NPC
     */
    IMBLEWYN(GnomeTipper.LEVEL.HARD, 4586, "on the ground floor of the Magic Guild."),

    /**
     * Onglewip
     *
     * @constructor Creates a Gnome cooking job with Onglewip as the NPC
     */
    ONGLEWIP(GnomeTipper.LEVEL.HARD, 4585, "in the Wizard's Tower south of Draynor.")
}
