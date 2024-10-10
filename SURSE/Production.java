package org.example;

import java.util.List;

public abstract class Production implements Comparable<Production> {
    private String title;
    private List<String> directors;
    private List<String> actors;
    private List<Genre> genres;
    private List<Rating> ratings;
    private String plot;
    private Double averageRating;
    private ProductionType type;
    public abstract void displayInfo();

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Title: " + title + "\n");

        if (type != null) {
            result.append("Type: ").append(type).append("\n");
        }

        if (directors != null) {
            result.append("Directors: ").append(directors).append("\n");
        }

        if (actors != null) {
            result.append("Actors: ").append(actors).append("\n");
        }

        if (genres != null) {
            result.append("Genres: ").append(genres).append("\n");
        }

        if (ratings != null) {
            result.append("Ratings: ").append(ratings).append("\n");
        }

        if (plot != null) {
            result.append("Plot: ").append('\'').append(plot).append('\'').append("\n");
        }

        if (averageRating != null) {
            result.append("Average Rating: ").append(averageRating).append("\n");
        }

        return result.toString();
    }

    public Production(String title, ProductionType type, List<String> directors,
                      List<String> actors, List<Genre> genres, List<Rating> ratings,
                       String plot, Double averageRating) {
        this.title = title;
        this.type = type;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.plot = plot;
        this.averageRating = averageRating;
    }

    public Production(Production p) {
        this.title = p.title;
        this.type = p.type;
        this.directors = p.directors;
        this.actors = p.actors;
        this.genres = p.genres;
        this.ratings = p.ratings;
        this.plot = p.plot;
        this.averageRating = p.averageRating;
    }

    @Override
    public int compareTo(Production otherProduction) {
        // Implement comparison based on the title
        return this.title.compareTo(otherProduction.title);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        updateAverageRating();
    }

    public void deleteRating(Rating rating) {
        ratings.remove(rating);
        updateAverageRating();
    }

    public void updateAverageRating() {
        Double sum = 0.0, contor = 0.0;
        for (Rating r : ratings) {
            sum+= r.getRating();
            contor++;
        }

        averageRating = sum / contor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public ProductionType getType() {
        return type;
    }

    public void setType(ProductionType type) {
        this.type = type;
    }

    public int getNumberOfRatings() {
        return ratings.size();
    }


}