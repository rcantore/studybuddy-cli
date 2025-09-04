package com.michead.studybuddy.localization;

import java.util.Arrays;
import java.util.Locale;

/**
 * Supported locales for StudyBuddy CLI
 */
public enum StudyBuddyLocale {
    SPANISH("es", "Spanish", "Español", new Locale("es")),
    ENGLISH("en", "English", "English", Locale.ENGLISH);

    private final String code;
    private final String englishName;
    private final String nativeName;
    private final Locale javaLocale;

    StudyBuddyLocale(String code, String englishName, String nativeName, Locale javaLocale) {
        this.code = code;
        this.englishName = englishName;
        this.nativeName = nativeName;
        this.javaLocale = javaLocale;
    }

    public String getCode() {
        return code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public Locale getJavaLocale() {
        return javaLocale;
    }

    public String getDisplayName() {
        return nativeName;
    }

    /**
     * Get StudyBuddyLocale from language code
     * @param code Language code (e.g., "es", "en")
     * @return StudyBuddyLocale matching the code
     * @throws IllegalArgumentException if code is not supported
     */
    public static StudyBuddyLocale fromCode(String code) {
        return Arrays.stream(values())
                .filter(locale -> locale.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unsupported language code: " + code + ". Supported codes: " + 
                        Arrays.toString(Arrays.stream(values()).map(StudyBuddyLocale::getCode).toArray())
                ));
    }

    /**
     * Get default locale for StudyBuddy (Spanish)
     */
    public static StudyBuddyLocale getDefault() {
        return SPANISH;
    }
}