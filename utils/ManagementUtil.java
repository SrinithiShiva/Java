package utils;

import java.util.function.Predicate;

public class ManagementUtil {
    public static final Predicate<String> isNullOrEmpty = (str) -> str == null || str.trim().isEmpty();
    public static final Predicate<String> isMailChecked = (email) -> isNullOrEmpty.and((str-> email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))).test(email);
    public static final Predicate<String> isValidPhoneNumber = (phoneNumber) -> isNullOrEmpty.and(phone -> phone.matches("^\\+?[0-9]{10,13}$")).test(phoneNumber);
}
