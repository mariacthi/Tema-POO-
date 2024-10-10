package org.example;
public class Credentials {
    private String email;
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Email: ").append(email).append(", ")
                .append("Password: ").append(password).append("]");

        return stringBuilder.toString();
    }

    // Getter methods for Credentials
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}