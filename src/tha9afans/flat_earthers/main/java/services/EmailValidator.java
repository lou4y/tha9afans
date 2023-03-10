package services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private final Pattern pattern;
    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_REGEX);
    }
    public boolean validate(final String hex) {
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
    }
}
