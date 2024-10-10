package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private RequestType type;
    private LocalDateTime createdDate;
    private String requesterUsername;
    private String actorName;
    private String movieTitle;
    private String description;
    private String resolverUsername;

    public RequestType getType() {
        return type;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public String getActorName() {
        return actorName;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getResolverUsername() {
        return resolverUsername;
    }

    public Request(RequestType type, LocalDateTime createdDate, String requesterUsername,
                   String actorName, String movieTitle, String resolverUsername, String description) {
        this.type = type;
        this.createdDate = createdDate;
        this.actorName = actorName;
        this.movieTitle = movieTitle;
        this.description = description;
        this.requesterUsername = requesterUsername;
        this.resolverUsername = resolverUsername;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Type: ").append(type).append("\n")
                .append("Created Date: ").append(createdDate).append("\n")
                .append("Username: ").append(requesterUsername).append("\n");

        if (actorName != null) {
            stringBuilder.append("Actor Name: ").append(actorName).append("\n");
        }

        if (movieTitle != null) {
            stringBuilder.append("Movie Title: ").append(movieTitle).append("\n");
        }

        stringBuilder.append("To: ").append(resolverUsername).append("\n")
                .append("Description: ").append(description).append("\n");

        return stringBuilder.toString();
    }

    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdDate.format(formatter);
    }
}


