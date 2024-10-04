package com.httpserver.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enum representing HTTP versions and their associated properties.
 */
public enum HttpVersion {
    /** HTTP version 1.1 */
    HTTP_1_1("HTTP/1.1", 1, 1);

    /** The literal representation of the HTTP version. */
    public final String LITERAL;

    /** The major version number. */
    public final int MAJOR;

    /** The minor version number. */
    public final int MINOR;

    /**
     * Constructor to create an instance of HttpVersion with a literal, major, and minor version.
     *
     * @param LITERAL the literal representation of the HTTP version.
     * @param MAJOR the major version number.
     * @param MINOR the minor version number.
     */
    HttpVersion(String LITERAL, int MAJOR, int MINOR) {
        this.LITERAL = LITERAL;
        this.MAJOR = MAJOR;
        this.MINOR = MINOR;
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
        Matcher matcher = httpVersionRegexPattern.matcher(literalVersion);
        if (!matcher.find() || matcher.groupCount() != 2) {
            throw new BadHttpVersionException();
        }
        int major = Integer.parseInt(matcher.group("major"));
        int minor = Integer.parseInt(matcher.group("minor"));

        HttpVersion tempBestCompatible = null;
        for (HttpVersion version : HttpVersion.values()) {
            if (version.LITERAL.equals(literalVersion)) {
                return version;
            } else {
                if (version.MAJOR == major) {
                    if (version.MINOR < minor) {
                        tempBestCompatible = version;
                    }
                }
            }
        }
        return tempBestCompatible;
    }
}
