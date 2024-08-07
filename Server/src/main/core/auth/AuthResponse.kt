package core.auth

/**
 * Auth response
 *
 * @constructor Auth response
 */
enum class AuthResponse {
    /**
     * Unexpected error
     *
     * @constructor Unexpected error
     */
    UnexpectedError,

    /**
     * Could not ad
     *
     * @constructor Could not ad
     */
    CouldNotAd,

    /**
     * Success
     *
     * @constructor Success
     */
    Success,

    /**
     * Invalid credentials
     *
     * @constructor Invalid credentials
     */
    InvalidCredentials,

    /**
     * Account disabled
     *
     * @constructor Account disabled
     */
    AccountDisabled,

    /**
     * Already online
     *
     * @constructor Already online
     */
    AlreadyOnline,

    /**
     * Updated
     *
     * @constructor Updated
     */
    Updated,

    /**
     * Full world
     *
     * @constructor Full world
     */
    FullWorld,

    /**
     * Login server offline
     *
     * @constructor Login server offline
     */
    LoginServerOffline,

    /**
     * Login limit exceeded
     *
     * @constructor Login limit exceeded
     */
    LoginLimitExceeded,

    /**
     * Bad session i d
     *
     * @constructor Bad session i d
     */
    BadSessionID,

    /**
     * Weak password
     *
     * @constructor Weak password
     */
    WeakPassword,

    /**
     * Members world
     *
     * @constructor Members world
     */
    MembersWorld,

    /**
     * Could not login
     *
     * @constructor Could not login
     */
    CouldNotLogin,

    /**
     * Updating
     *
     * @constructor Updating
     */
    Updating,

    /**
     * Too many incorrect logins
     *
     * @constructor Too many incorrect logins
     */
    TooManyIncorrectLogins,

    /**
     * Standing in members area
     *
     * @constructor Standing in members area
     */
    StandingInMembersArea,

    /**
     * Account locked
     *
     * @constructor Account locked
     */
    AccountLocked,

    /**
     * Closed beta
     *
     * @constructor Closed beta
     */
    ClosedBeta,

    /**
     * Invalid login server
     *
     * @constructor Invalid login server
     */
    InvalidLoginServer,

    /**
     * Moving world
     *
     * @constructor Moving world
     */
    MovingWorld,

    /**
     * Error loading profile
     *
     * @constructor Error loading profile
     */
    ErrorLoadingProfile,

    /**
     * Banned user
     *
     * @constructor Banned user
     */
    BannedUser
}
