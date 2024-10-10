package org.example;

import java.util.List;
import java.util.SortedSet;

public class Regular<T extends Comparable<T>> extends User<T> implements RequestsManager {

    public Regular() {
    }

    public Regular(String username, int experience, Information information,
                   AccountType userType, SortedSet<String> favoriteProductions,
                   SortedSet<String> favoriteActors, List<String> notifications) {
        super(username, experience, information, userType, favoriteProductions, favoriteActors, notifications);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());

        if (getNotifications() != null) {
            stringBuilder.append("Notifications: ").append(getNotifications()).append("\n");
        }

        return stringBuilder.toString();
    }

    public void createRequest(Request r) {
        String resolverUsername = r.getResolverUsername();
        if(resolverUsername.equals("ADMIN"))
            IMDB.RequestsHolder.addRequest(r);
        else {
            List<User> users = IMDB.getInstance().getUsers();
            for (User<?> u : users) {
                if (u instanceof Staff) {
                    if (u.getUsername().equals(resolverUsername)) {
                        Contributor<?> staffUser = (Contributor<?>) u;
                        staffUser.getRequests().add(r);
                    }
                }
            }
        }
    }

    public void removeRequest(Request r) {
        String resolverUsername = r.getResolverUsername();
        if(resolverUsername.equals("ADMIN"))
            IMDB.RequestsHolder.removeRequest(r);
        else {
            List<User> users = IMDB.getInstance().getUsers();
            for (User<?> u : users) {
                if (u instanceof Staff) {
                    if (u.getUsername().equals(resolverUsername)) {
                        Contributor<?> staffUser = (Contributor<?>) u;
                        staffUser.getRequests().remove(r);
                    }
                }
            }
        }
    }

    public void addRating(Production production, int rating, String comment) {
        if (rating < 1 || rating > 10) {
            System.out.println("Invalid rating. Rating should be between 1 and 10.");
            return;
        }

        Rating userRating = new Rating(getUsername(), rating, comment);

        /* find out if user rated this production before */
        List<Rating> ratings = production.getRatings();
        String username = getUsername();
        for (Rating r : ratings) {
            if (username.equals(r.getUsername())) {
                production.deleteRating(r);
            }
        }

        production.addRating(userRating);
        updateExperience(new StrategyForRating());
    }
}
