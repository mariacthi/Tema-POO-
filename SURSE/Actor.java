package org.example;


import kotlin.Pair;

import java.util.List;

public class Actor {
    private String actorName;
    private List<Pair<String, ProductionType>> filmography;
    private String biography;

    public Actor(String actorName, List<Pair<String, ProductionType>> filmography, String biography) {
        this.actorName = actorName;
        this.filmography = filmography;
        this.biography = biography;
    }

    public String getActorName() {
        return actorName;
    }

    public List<Pair<String, ProductionType>> getFilmography() {
        return filmography;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Actor Name: ").append(actorName).append("\n");
        sb.append("Biography: ").append(biography).append("\n");
        sb.append("Filmography: \n");
        for (Pair<String, ProductionType> performance : filmography) {
            sb.append("- Title: ").append(performance.getFirst())
                    .append(", Type: ").append(performance.getSecond()).append("\n");
        }
        return sb.toString();
    }
}

