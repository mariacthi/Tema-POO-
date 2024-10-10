package org.example;

public class Rating {
    private String username;
    private int rating;
    private String comment;

    public Rating(String username, int rating, String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String toString() {
        return  "\n" + "Rating{" + "username='" + username + '\'' +  ", rating=" + rating +
                ", comment='" + comment + '\'' + '}' ;
    }
}
