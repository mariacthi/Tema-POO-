package org.example;

import java.util.List;
import java.util.SortedSet;

public class Contributor<T extends Comparable<T>> extends Staff<T> implements RequestsManager {

    // Constructor for Contributor
    public Contributor(String username, int experience, Information information,
                       AccountType userType, SortedSet<String> favoriteProductions,
                       SortedSet<String> favoriteActors, SortedSet<String> addedProductions,
                       SortedSet<String> addedActors, List<String> notifications) {
        super(username, experience, information, userType, favoriteProductions,
                favoriteActors, addedProductions, addedActors, notifications);
    }

    public Contributor() {
    // for User factory
    }

    public void createRequest(Request r) {
        super.getRequests().add(r);
    }

    public void removeRequest(Request r) {
       super.getRequests().remove(r);
    }
}
