package core.api.auth

/**
 * Auth response.
 */
enum class AuthResponse {

    UnexpectedError,
    CouldNotAd,
    Success,
    InvalidCredentials,
    AccountDisabled,
    AlreadyOnline,
    Updated,
    FullWorld,
    LoginServerOffline,
    LoginLimitExceeded,
    BadSessionID,
    WeakPassword,
    MembersWorld,
    CouldNotLogin,
    Updating,
    TooManyIncorrectLogins,
    StandingInMembersArea,
    AccountLocked,
    ClosedBeta,
    InvalidLoginServer,
    MovingWorld,
    ErrorLoadingProfile,
    BannedUser
}
