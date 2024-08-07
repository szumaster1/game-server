package core.network.registry;

import java.sql.Date;

/**
 * Represents details of an account to register.
 * @author Vexia
 */
public class RegistryDetails {

    /**
     * The username.
     */
    private final String username;

    /**
     * The password.
     */
    private final String password;

    /**
     * The date of birth.
     */
    private final Date birth;

    /**
     * The country.
     */
    private final int country;

    /**
     * Constructs a new {@Code RegistryDetails} {@Code Object}
     *
     * @param username The username to register.
     * @param password The password to register.
     * @param birth The birth year.
     * @param country The country code.
     */
    public RegistryDetails(String username, String password, Date birth, int country) {
        this.username = username;   // Initialize the username field with the provided username.
        this.password = password;   // Initialize the password field with the provided password.
        this.birth = birth;         // Initialize the birth field with the provided birthdate.
        this.country = country;     // Initialize the country field with the provided country code.
    }

    /**
     * Gets the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username; // Return the stored username.
    }

    /**
     * Gets the password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password; // Return the stored password.
    }

    /**
     * Gets the birth.
     *
     * @return the birth.
     */
    public Date getBirth() {
        return birth; // Return the stored birthdate.
    }

    /**
     * Gets the country.
     *
     * @return the country.
     */
    public int getCountry() {
        return country; // Return the stored country code.
    }

}
