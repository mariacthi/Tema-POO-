package org.example;

public class UserFactory {
    public static <T extends Comparable<T>> User<T> factory(AccountType type) {
        if(type == AccountType.Admin)
            return new Admin<>();
        if (type == AccountType.Contributor)
            return new Contributor<>();
        if (type == AccountType.Regular)
            return new Regular<>();

        return null;
    }
}
