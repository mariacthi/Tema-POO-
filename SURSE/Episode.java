package org.example;

public class Episode {
    private String episodeName;
    private String duration;

    public String getEpisodeName() {
        return episodeName;
    }

    public String getDuration() {
        return duration;
    }

    public Episode(String episodeName, String duration) {
        this.episodeName = episodeName;
        this.duration = duration;
    }

    public String toString() {
        return "Episode Name: " + episodeName +
                ", Duration: " + duration;
    }

}
