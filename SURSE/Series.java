package org.example;

import java.util.List;
import java.util.Map;

public class Series extends Production {
    private int releaseYear;
    private int numSeasons;
    private Map<String, List<Episode>> seasons;

    public Series( String title, ProductionType type, List<String> directors, List<String> actors, List<Genre> genres,
            List<Rating> ratings, String plot, Double averageRating, int releaseYear, int numSeasons, Map<String, List<Episode>> seasons) {
        super(title, type, directors, actors, genres, ratings, plot, averageRating);
        this.releaseYear = releaseYear;
        this.numSeasons = numSeasons;
        this.seasons = seasons;
    }

    public Series(Series s) {
        super(s);
        this.releaseYear = s.releaseYear;
        this.numSeasons = s.numSeasons;
        this.seasons = s.seasons;
    }

    public void displayInfo() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());

        if (releaseYear != 0) {
            result.append("Release Year: ").append(releaseYear).append("\n");
        }

        if (numSeasons != 0) {
            result.append("Number of Seasons: ").append(numSeasons).append("\n");
        }

        if (seasons != null) {
            for (Map.Entry<String, List<Episode>> entry : seasons.entrySet()) {
                result.append(entry.getKey()).append(":\n");
                for (Episode episode : entry.getValue()) {
                    result.append("\t").append(episode.toString()).append("\n");
                }
            }
        }

        return result.toString();
    }
}
