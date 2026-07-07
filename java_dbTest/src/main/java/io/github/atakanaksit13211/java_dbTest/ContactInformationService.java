package io.github.atakanaksit13211.java_dbTest;

public interface ContactInformationService {
    String sanitizePhoneNumber(String input);

    String sanitizeEMailAddress(String input);
}
