package org.example;

import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;

public abstract class User<T extends Comparable<T>> {
    private Information information;
    private AccountType userType;
    private String username;
    private int experience;
    private List<String> notifications;
    private SortedSet<String> favoriteProductions;
    private SortedSet<String> favoriteActors;

    public User() {
    // constructor for User factory
    }

    public User(String username, int experience, Information information,
                AccountType userType, SortedSet<String> favoriteProductions,
                SortedSet<String> favoriteActors, List<String> notifications) {
        this.username = username;
        this.experience = experience;
        this.information = information;
        this.userType = userType;
        this.favoriteProductions = favoriteProductions;
        this.favoriteActors = favoriteActors;
        this.notifications = notifications;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Username: '").append(username).append("'\n");

        if (experience == 0)
            stringBuilder.append("Experience: null").append("\n");
        else
            stringBuilder.append("Experience: ").append(experience).append("\n");

        if (information != null) {
            stringBuilder.append("Information: ").append(information).append("\n");
        }

        if (userType != null) {
            stringBuilder.append("User Type: ").append(userType).append("\n");
        }

        if (favoriteProductions != null) {
            stringBuilder.append("Favorite Productions: ").append(favoriteProductions).append("\n");
        }

        if (favoriteActors != null) {
            stringBuilder.append("Favorite Actors: ").append(favoriteActors).append("\n");
        }

        return stringBuilder.toString();
    }

    public void addFavoriteProduction(Production production) {
        if (favoriteProductions == null)
            favoriteProductions = new TreeSet<>();
        favoriteProductions.add(production.getTitle());
    }

    public void addFavoriteProduction(String production) {
        if (favoriteProductions == null)
            favoriteProductions = new TreeSet<>();
        favoriteProductions.add(production);
    }

    public void addFavoriteActor(Actor actor) {

        if (favoriteActors == null)
            favoriteActors = new TreeSet<>();
        favoriteActors.add(actor.getActorName());
    }

    public void addFavoriteActor(String actor) {

        if (favoriteActors == null)
            favoriteActors = new TreeSet<>();
        favoriteActors.add(actor);
    }

    public void deleteFavoriteProduction(Production production) {
        favoriteProductions.remove(production.getTitle());
    }

    public void deleteFavoriteProduction(String production) {
        favoriteProductions.remove(production);
    }

    public void deleteFavoriteActor(Actor actor) {
        favoriteActors.remove(actor.getActorName());
    }

    public void deleteFavoriteActor(String actor) {
        favoriteActors.remove(actor);
    }

    public void updateExperience(ExperienceStrategy strategy) {
        experience += strategy.calculateExperience();
    }

    public void updateExperience(ExperienceStrategy strategy, String userName) {
        List<User> users = IMDB.getInstance().getUsers();
        for (User<?> u : users) {
            if (u.getUsername().equals(userName)) {
               int experience = u.getExperience();
               experience += strategy.calculateExperience();
               u.setExperience(experience);
               return;
            }
        }
    }

    public void logout() {
        IMDB.getInstance().logOutUser();
    }

    public String getUsername() {
        return username;
    }

    public int getExperience() {
        return experience;
    }

    public Information getInformation() {
        return information;
    }
    public AccountType getUserType() {
        return userType;
    }
    public List<String> getNotifications() {
        return notifications;
    }
    public SortedSet<String> getFavoriteProductions() {
        return favoriteProductions;
    }
    public SortedSet<String> getFavoriteActors() {
        return favoriteActors;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void setUserType(AccountType userType) {
        this.userType = userType;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public void setFavoriteProductions(SortedSet<String> favoriteProductions) {
        this.favoriteProductions = favoriteProductions;
    }

    public void setFavoriteActors(SortedSet<String> favoriteActors) {
        this.favoriteActors = favoriteActors;
    }

    public static class Information {
        private Credentials credentials;
        private String name;
        private String country;
        private int age;
        private char gender;
        private LocalDateTime birthDate;

        public Information(Builder b) {
            this.credentials = b.credentials;
            this.name = b.name;
            this.country = b.country;
            this.age = b.age;
            this.gender = b.gender;
            this.birthDate = b.birthDate;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{\nCredentials:").append(credentials).append("\n")
                    .append("Name: '").append(name).append("\n")
                    .append("Country: ").append(country).append("\n")
                    .append("Age: ").append(age).append("\n")
                    .append("Gender: ").append(gender).append("\n")
                    .append("BirthDate: ").append(birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .append("\n}");

            return stringBuilder.toString();
        }
        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public int getAge() {
            return age;
        }

        public char getGender() {
            return gender;
        }

        public LocalDateTime getBirthDate() {
            return birthDate;
        }

        public static class Builder {
            private Credentials credentials;
            private String name;
            private String country;
            private int age;
            private char gender;
            private LocalDateTime birthDate;

            public Builder(String name) {
                this.name = name;
            }

            public Builder setCredentials(String email, String password) {
                this.credentials = new Credentials(email, password);
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Builder setAge(int age) {
                this.age = age;
                return this;
            }

            public Builder setGender(char gender) {
                this.gender = gender;
                return this;
            }

            public Builder setBirthDate(LocalDateTime birthDate) {
                this.birthDate = birthDate;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }
}
