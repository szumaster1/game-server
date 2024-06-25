package core.api.consts

// GregHib, Denis.
interface InterfaceVars {

    companion object {
        /*
         * Used for the Level up interfaces & Skill icon flashing.
         */
        const val LEVEL_UP_AND_FLASH: Int = 1179

        /*
         * Used to enable the poisoned orb effect on the hp orb.
         */
        const val POISON_HP_ORB: Int = 102

        /*
         * Though not confirmed due no existing Shop system created,
         * it is assumed to be in relations to the Shop container itself.
         */
        const val SHOP_KEY: Int = 1496

        /*
         * Though not confirmed due inauthentic Shop system created,
         * it is assumed to be in relations to the Shop container itself.
         */
        const val SHOP_CURRENCY: Int = 532

        /*
         * Though not confirmed due no existing Shop system created,
         * it is assumed to be in relations to the Shop container itself.
         */
        const val SHOP_TRANSACTION: Int = 2564

        /*
         * Though not confirmed due no existing Shop system created,
         * it is assumed to be in relations to the Shop container itself.
         */
        const val SHOP_BUYING_STATE: Int = 2565

        /*
         * Though not confirmed due no existing Shop system created,
         * it is assumed to be in relations to the Shop container itself.
         */
        const val SHOP_RESET_SELECTED: Int = 2563

        /*
         * Though not confirmed due no existing Shop system created,
         * it is assumed to be in relations to the Shop container itself.
         */
        const val SHOP_ITEM_ID: Int = 2562

        /*
         * Updates the Prayer bonuses % interface (optionally can hide this popup).
         */
        const val PRAYER_STAT_PERCENTAGE_BASE_VALUE: Int = 6857

        /*
         * Unknown function due to lack of functionalities.
         */
        const val CURSES_PRAYER_DISABLE: Int = 1582

        /*
         * Unknown function due to lack of functionalities.
         */
        const val MODERN_PRAYER_DISABLE: Int = 1395

        /*
         * Updates the Slot id for a Curses Prayer being used.
         */
        const val UPDATE_CURSES_SLOT_PRAYER: Int = 1582

        /*
         * Updates the Slot id for a Curses Quick Prayer being used.
         */
        const val UPDATE_QUICK_CURSES_SLOT_PRAYER: Int = 1587

        /*
         * Updates the Slot id for a Curses Prayer being used.
         */
        const val UPDATE_MODERN_SLOT_PRAYER: Int = 1395

        /*
         * Updates the Slot id for a Curses Quick Prayer being used.
         */
        const val UPDATE_QUICK_MODERN_SLOT_PRAYER: Int = 1397

        /*
         * Refreshes the current Prayer book.
         */
        const val REFRESH_PRAYER_BOOK: Int = 1584

        /*
         * Refreshes the current Prayer points.
         */
        const val REFRESH_PRAYER_POINTS: Int = 2382

        /*
         * Sets the Primary color text of the Note being written.
         */
        const val PRIMARY_NOTE_COLOR: Int = 1440

        /*
         * Believed to set the background color of the text being written.
         */
        const val SECONDARY_NOTE_COLOR: Int = 1441

        /*
         * Believed to unlock the notes management buttons
         */
        const val UNLOCK_MANAGE_NOTES: Int = 1437

        /*
         * An unknown Notes var/config Believed to set the note index.
         */
        const val SET_NOTE_INDEX: Int = 1439

        /*
         * Sets the target destination waypoint flag in the World Map interface.
         */
        const val WORLD_MAP_MARKER: Int = 1159

        /*
         * Sets the Chat effects (wave1: text) to visible.
         */
        const val SETTINGS_CHAT_EFFECTS: Int = 171

        /*
         * Sets the force right click always function or not.
         */
        const val SETTINGS_MOUSE_BUTTONS: Int = 170

        /*
         * Sets the acceptance of other Players.
         */
        const val SETTINGS_ACCEPT_AID: Int = 427

        /*
         * Sets the state of the run button in Settings interface.
         */
        const val SETTINGS_RUN: Int = 173

        /*
         * Refreshes the Auto attack style of the magic spell
         */
        const val COMBAT_REFRESH_AUTO_CAST_SPELL: Int = 108

        /*
         * Refreshes the scrollbar for the auto casting
         * Note: haven't tested this proof of concept but assuming it is.
         */
        const val COMBAT_REFRESH_DEFENCE_SPELL_CAST_SCROLLBAR: Int = 439

        /*
         * Refreshes the current combat spellbook.
         */
        const val COMBAT_REFRESH_SPELLBOOK: Int = 1376

        /*
         * Refreshes the current combat style state.
         */
        const val COMBAT_REFRESH_COMBAT_STYLE: Int = 43

        /*
         * Refreshes the current state of the special attack bar.
         */
        const val COMBAT_REFRESH_USING_SPECIAL_ATTACK: Int = 301

        /*
         * Refreshes the current state of the special attack bar value.
         */
        const val COMBAT_REFRESH_SPECIAL_ATTACK_VALUE: Int = 301

        /*
         * Refreshes the auto retaliation state.
         */
        const val COMBAT_REFRESH_AUTO_RETALIATION: Int = 172

        /*
         * Refreshes the last X value.
         */
        const val BANK_LAST_X: Int = 1249

        /*
         * Refreshes the last viewing tab of the bank.
         */
        const val BANK_REFRESH_LAST_VIEWING_TAB: Int = 4893

        /*
         * Refreshes the specified bank tab
         */
        const val BANK_REFRESH_SPECIFIED_TAB: Int = 4885

        /*
         * Updates the state of insert mode for the bank
         */
        const val BANK_SWITCH_INSERT_MODES: Int = 762

        /*
         * Refreshes the special energy used for familiars specials
         */
        const val SUMMONING_REFRESH_SPECIAL_ENGERY: Int = 1177

        /*
         * Represents the time left of a familiars life
         */
        const val SUMMONING_TIME_REMAINING: Int = 1176

        /*
         * Believed to be the item id for name display
         */
        const val SUMMONING_POUCH_ID: Int = 448

        /*
         * Represents the familiars head animation
         */
        const val SUMMONING_HEAD_ANIMATION: Int = 1160

        /*
         * Represents the special amount of a familiar that it can produce.
         */
        const val SUMMONING_SPECIAL_AMOUNT: Int = 1175

        /*
         * Summoning is not updated yet, unknown if functionalities are correct.
         */
        const val SUMMONING_SWITCH_ORB: Int = 1174

        /*
         * Unable to verify the nature of these configs till summoning is updated.
         * These are group together in the meantime as their usages are quite similar.
         */
        const val SUMMONING_LEFT_CLICK_OPTION: Int = 1493
        const val SUMMONING_EXTRA_LEFT_CLICK_OPTION: Int = 1494

        /*
         * Refreshes the XP counter value.
         */
        const val SKILLS_REFRESH_XP_COUNTER: Int = 1801

        /*
         * Refreshes the state of the clan status.
         */
        const val GAME_BAR_STATUS_CLAN: Int = 1054

        /*
         * Refreshes the state of the assist status.
         */
        const val GAME_BAR_STATUS_ASSIST: Int = 1055

        /*
         * Refreshes the state of the friends/ignore status.
         */
        const val GAME_BAR_STATUS_FRIENDS_IGNORE: Int = 2159

        /*
         * Believed to be the item id for name display.
         */
        const val PET_ITEM_ID: Int = 448

        /*
         * Represents the pet head animation.
         */
        const val PET_HEAD_ANIMATION: Int = 1160

        /*
         * Represents the run button config
         */
        const val RUN_BUTTON: Int = 173

        /*
         * Represents the data being sent for the level up interface and
         * what you can now do on after unlocking the level.
         */
        const val SKILL_CONGRATULATIONS_LEVEL_UP_INFORMATION: Int = 1230

        /*
         * Represents the global skill-based information.)
         */
        const val SKILL_SKILL_GUIDE_DATA: Int = 965

        /*
         * Represents an item being modified in a trade screen.
         */
        const val TRADE_ITEM_MODIFIED: Int = 1042

        /*
         * Represents the targets item being modified in a trade screen.
         */
        const val TRADE_TARGET_ITEM_MODIFIED: Int = 1043

        /*
         * This allows you to close your chatbox interface entirely as well as your
         * toolbelt slots (skill tab, such). This feature is disabled by default
         * cause of the tutorial islands.
         */
        const val CLOSE_CHAT_TOOLBELT: Int = 281

        /*
         * Enables Skill target Levels or XP tracking.
         */
        const val SKILL_TARGETS: Int = 1966

        /*
         * Represents the state of using the Level mode specifically.
         */
        const val SKILL_TARGET_LEVEL_MODE: Int = 1968

        /*
         * Represents the tracking target values.
         */
        const val SKILL_TARGET_VALUES: Int = 1969

        /*
         * Represents global runecrafting options for scenery.
         */
        const val RUNECRAFTING_ALTARS_OPTIONS: Int = 492

        /*
         * Shop Item slot components to display
         */
        const val SHOP_ITEM_COMPONENTS: Int = 118

        /*
         * An immutable number that is to show only the total quest points to earn.
         */
        const val QUEST_POINTS: Int = 101

        /*
         * An immutable number that is to show only the total quest points to earn.
         */
        const val TOTAL_QUEST_POINTS: Int = 904

        /*
         *  Item lent amount.
         */
        const val LENT_ITEM_AMOUNT: Int = 1269

        /*
         *  Fist of guthix mini-game rating.
         */
        const val FOG_RATING: Int = 1405

        /*
         * Slayer assignment complete.
         */
        const val SLAYER_TASK_COMPLETE: Int = 394
    }
}
