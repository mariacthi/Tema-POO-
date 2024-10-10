package org.example;

import java.util.List;

public class Movie extends Production {
    private String duration;
    private int releaseYear;

    public Movie(String title, ProductionType type, List<String> directors, List<String> actors, List<Genre> genres,
                 List<Rating> ratings, String duration, String plot, int releaseYear, double averageRating) {
        super(title, ProductionType.Movie, directors, actors, genres, ratings, plot, averageRating);
        this.releaseYear = releaseYear;
        this.duration = duration;
    }

    public Movie(Movie m) {
        super(m);
        this.releaseYear = m.releaseYear;
        this.duration = m.duration;
    }


    public String getDuration() {
        return duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public void displayInfo() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        if (duration != null) {
            result.append("Duration: ").append(duration).append("\n");
        }

        if (releaseYear != 0) {
            result.append("Release Year: ").append(releaseYear).append("\n");
        }
        return result.toString();
    }
}
