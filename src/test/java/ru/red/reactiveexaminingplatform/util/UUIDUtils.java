package ru.red.reactiveexaminingplatform.util;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDUtils {
    public final static String UUID_REGEX = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}?$";
    public final static Pattern UUID_PATTERN = Pattern.compile(UUID_REGEX);
    public final static UUIDMatcher UUID_MATCHER_HAMCREST = new UUIDMatcher();

    public static boolean isUUID(String uuid) {
        return UUID_PATTERN.matcher(uuid).matches();
    }

    public static UUID safeFromString(String uuid) {
        return UUID.fromString(Objects.requireNonNull(uuid));
    }

    private final static class UUIDMatcher extends TypeSafeMatcher<String> {
        @Override
        protected boolean matchesSafely(String item) {
            return isUUID(item);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Is UUID");
        }
    }
}
