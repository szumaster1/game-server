package content.minigame.gnomecook

/**
 * Gnome cooking job
 *
 * @property level
 * @property npc_id
 * @property tip
 * @constructor Gnome cooking job
 */
enum class GnomeCookingJob(val level: GnomeTipper.LEVEL, val npc_id: Int, val tip: String) {
    /**
     * Cpt Errdo
     *
     * @constructor Cpt Errdo
     */
    CPT_ERRDO(GnomeTipper.LEVEL.EASY, 3811, "at the top level of the Grand Tree."),

    /**
     * Dalilah
     *
     * @constructor Dalilah
     */
    DALILAH(GnomeTipper.LEVEL.EASY, 4588, "sitting in the Gnome Restaurant."),

    /**
     * Gulluck
     *
     * @constructor Gulluck
     */
    GULLUCK(GnomeTipper.LEVEL.EASY, 602, "on the third level of the Grand Tree."),

    /**
     * Rometti
     *
     * @constructor Rometti
     */
    ROMETTI(GnomeTipper.LEVEL.EASY, 601, "on the second level of the Grand Tree."),

    /**
     * Narnode
     *
     * @constructor Narnode
     */
    NARNODE(GnomeTipper.LEVEL.EASY, 670, "at the base of the Grand Tree."),

    /**
     * Meegle
     *
     * @constructor Meegle
     */
    MEEGLE(GnomeTipper.LEVEL.EASY, 4597, "in the terrorbird enclosure."),

    /**
     * Perrdur
     *
     * @constructor Perrdur
     */
    PERRDUR(GnomeTipper.LEVEL.EASY, 4587, "sitting in the Gnome Restaurant."),

    /**
     * Sarble
     *
     * @constructor Sarble
     */
    SARBLE(GnomeTipper.LEVEL.EASY, 4599, "in the swamp west of the Grand Tree."),

    /**
     * Gimlewap
     *
     * @constructor Gimlewap
     */
    GIMLEWAP(GnomeTipper.LEVEL.HARD, 4580, "upstairs in Ardougne castle."),

    /**
     * Bleemadge
     *
     * @constructor Bleemadge
     */
    BLEEMADGE(GnomeTipper.LEVEL.HARD, 3810, "at the top of White Wolf Mountain."),

    /**
     * Dalbur
     *
     * @constructor Dalbur
     */
    DALBUR(GnomeTipper.LEVEL.HARD, 3809, "by the gnome glider in Al Kharid"),

    /**
     * Bolren
     *
     * @constructor Bolren
     */
    BOLREN(GnomeTipper.LEVEL.HARD, 469, "next to the Spirit Tree in Tree Gnome Village"),

    /**
     * Schepbur
     *
     * @constructor Schepbur
     */
    SCHEPBUR(GnomeTipper.LEVEL.HARD, 3817, "in the battlefield of Khazar, south of the river."),

    /**
     * Imblewyn
     *
     * @constructor Imblewyn
     */
    IMBLEWYN(GnomeTipper.LEVEL.HARD, 4586, "on the ground floor of the Magic Guild."),

    /**
     * Onglewip
     *
     * @constructor Onglewip
     */
    ONGLEWIP(GnomeTipper.LEVEL.HARD, 4585, "in the Wizard's Tower south of Draynor.")
}