package com.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enum representing HTTP versions and their associated properties.
 */
public enum HttpVersion {
    /** HTTP version 1.1 */
    HTTP_1_1("HTTP/1.1", 1, 1); // Represents HTTP version 1.1 with major and minor version numbers

    /** The literal representation of the HTTP version. */
    public final String LITERAL;

    /** The major version number. */
    public final int MAJOR;

    /** The minor version number. */
    public final int MINOR;

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpVersion.class); // Logger for this class

    /**
     * Constructor to create an instance of HttpVersion with a literal, major, and minor version.
     *
     * @param LITERAL the literal representation of the HTTP version.
     * @param MAJOR the major version number.
     * @param MINOR the minor version number.
     */
    HttpVersion(String LITERAL, int MAJOR, int MINOR) {
        this.LITERAL = LITERAL; // Initialize the literal representation
        this.MAJOR = MAJOR;     // Initialize the major version number
        this.MINOR = MINOR;     // Initialize the minor version number
    }

    /** Regular expression pattern to match HTTP version literals. */
    private static final Pattern httpVersionRegexPattern = Pattern.compile("^HTTP/(?<major>\\d+).(?<minor>\\d+)");

    /**
     * Retrieves the best compatible HttpVersion based on the provided literal version.
     *
     * @param literalVersion the literal representation of the HTTP version to check.
     * @return the best compatible HttpVersion if found.
     * @throws BadHttpVersionException if the provided version is invalid or unsupported.
     */
    public static HttpVersion getBestCompatibleVersion(String literalVersion) throws BadHttpVersionException {
        LOGGER.info("Checking best compatible HTTP version for: {}", literalVersion);

        Matcher matcher = httpVersionRegexPattern.matcher(literalVersion); // Match the provided version against the regex pattern
        if (!matcher.find() || matcher.groupCount() != 2) {
            LOGGER.error("Invalid HTTP version format: {}", literalVersion);
            throw new BadHttpVersionException(); // Throw exception if format is invalid
        }

        // Extract the major and minor version numbers from the matched groups
        int major = Integer.parseInt(matcher.group("major"));
        int minor = Integer.parseInt(matcher.group("minor"));
        LOGGER.debug("Parsed version - Major: {}, Minor: {}", major, minor);

        HttpVersion tempBestCompatible = null; // Variable to hold the best compatible version
        for (HttpVersion version : HttpVersion.values()) {
            if (version.LITERAL.equals(literalVersion)) {
                LOGGER.info("Exact match found for version: {}", version.LITERAL);
                return version; // Return the exact match if found
            } else if (version.MAJOR == major) {
                if (version.MINOR < minor) {
                    tempBestCompatible = version; // Update compatible version if minor is less
                }
            }
        }

        // Log and return the best compatible version found, or warn if none found
        if (tempBestCompatible != null) {
            LOGGER.info("Best compatible version found: {}", tempBestCompatible.LITERAL);
        } else {
            LOGGER.warn("No compatible HTTP version found for: {}", literalVersion);
        }

        return tempBestCompatible; // Return the best compatible version or null if none found
    }
}
