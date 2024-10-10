package org.example;

import kotlin.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Parser {

    public static List<Actor> parseActors(String filePath) {
        List<Actor> actors = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(filePath);
            Object obj = parser.parse(reader);
            JSONArray actorsArray = (JSONArray) obj;

            for (Object actorObj : actorsArray) {
                JSONObject actorJson = (JSONObject) actorObj;
                String actorName = (String) actorJson.get("name");
                String biography = (String) actorJson.get("biography");

                List<Pair<String, ProductionType>> filmography = new ArrayList<>();
                JSONArray performancesArray = (JSONArray) actorJson.get("performances");
                for (Object performanceObj : performancesArray) {
                    JSONObject performanceJson = (JSONObject) performanceObj;
                    String title = (String) performanceJson.get("title");
                    String type = (String) performanceJson.get("type");
                    ProductionType productionType = ProductionType.valueOf(type);

                    Pair<String, ProductionType> performancePair = new Pair<>(title, productionType);
                    filmography.add(performancePair);
                }

                Actor actor = new Actor(actorName, filmography, biography);
                actors.add(actor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return actors;
    }

    public static List<Production> parseProductions(String filePath) {
        List<Production> productions = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(filePath);
            Object obj = parser.parse(reader);
            JSONArray productionsArray = (JSONArray) obj;

            for (Object productionObj : productionsArray) {
                JSONObject productionJson = (JSONObject) productionObj;
                String title = (String) productionJson.get("title");
                ProductionType type = ProductionType.valueOf((String)productionJson.get("type"));

                JSONArray jsonArray = (JSONArray) productionJson.get("directors");
                List<String> directors = new ArrayList<>();
                for (Object item : jsonArray) {
                    directors.add((String) item);
                }

                jsonArray = (JSONArray) productionJson.get("actors");
                List<String> actors = new ArrayList<>();
                for (Object item : jsonArray) {
                    actors.add((String) item);
                }

                jsonArray = (JSONArray) productionJson.get("genres");
                List<Genre> genres = new ArrayList<>();
                for (Object item : jsonArray) {
                    Genre genre = Genre.valueOf((String) item);
                    genres.add(genre);
                }

                jsonArray = (JSONArray) productionJson.get("ratings");
                List<Rating> ratings = new ArrayList<>();
                for (Object ratingObj : jsonArray) {
                    JSONObject ratingJson = (JSONObject) ratingObj;
                    String username = (String) ratingJson.get("username");
                    int ratingValue = ((Long) ratingJson.get("rating")).intValue(); // Adjust for JSON Long to int
                    String comment = (String) ratingJson.get("comment");

                    Rating rating = new Rating(username, ratingValue, comment);
                    ratings.add(rating);
                }

                String plot = (String) productionJson.get("plot");

                Double averageRating = (Double) productionJson.get("averageRating");

                if (type == ProductionType.Movie) {
                    String duration = (String) productionJson.get("duration");

                    Object releaseYearObj = productionJson.get("releaseYear");
                    int releaseYear = (releaseYearObj != null) ? ((Long) releaseYearObj).intValue() : 0;

                    Movie movie = new Movie(title, type, directors, actors, genres, ratings, duration,
                            plot, releaseYear, averageRating);
                    productions.add(movie);
                }

                if (type == ProductionType.Series) {
                    Object releaseYearObj = productionJson.get("releaseYear");
                    int releaseYear = (releaseYearObj != null) ? ((Long) releaseYearObj).intValue() : 0;

                    Object numSeasonsObj = productionJson.get("numSeasons");
                    int numSeasons = (numSeasonsObj != null) ? ((Long) numSeasonsObj).intValue() : 0;

                    Map<String, List<Episode>> seasons = new TreeMap<>();

                    for (int seasonNum = 1; seasonNum <= numSeasons; seasonNum++) {
                        String seasonKey = "Season " + seasonNum;
                        JSONArray seasonEpisodesJson = (JSONArray) ((JSONObject)productionJson.get("seasons")).get(seasonKey);

                        List<Episode> episodes = new ArrayList<>();

                        // Parse each episode in the season
                        for (Object episodeObj : seasonEpisodesJson) {
                            JSONObject episodeJson = (JSONObject) episodeObj;

                            String episodeName = (String) episodeJson.get("episodeName");
                            String duration = (String) episodeJson.get("duration");

                            Episode episode = new Episode(episodeName, duration);
                            episodes.add(episode);
                        }

                        seasons.put(seasonKey, episodes);
                    }

                    Series series = new Series(title, type, directors, actors, genres, ratings, plot, averageRating, releaseYear, numSeasons, seasons);
                    productions.add(series);

                }
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productions;

    }

    public static List<User> parseUsers(String filePath) {
        List<User> users = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(filePath);
            Object obj = parser.parse(reader);
            JSONArray usersArray = (JSONArray) obj;

            for (Object userObj : usersArray) {
                JSONObject userJson = (JSONObject) userObj;

                String username = (String) userJson.get("username");
                Object experienceObj = userJson.get("experience");
                int experience = 0;  // Default value in case of any issues

                if (experienceObj != null) {
                    if (experienceObj instanceof Number) {
                        experience = ((Number) experienceObj).intValue();
                    } else if (experienceObj instanceof String) {
                        try {
                            experience = Integer.parseInt((String) experienceObj);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // INFORMATION
                JSONObject informationJson = (JSONObject) userJson.get("information");
                User.Information.Builder builder = new User.Information.Builder((String) informationJson.get("name"));

                if (informationJson.containsKey("credentials")) {
                    JSONObject credentialsJson = (JSONObject) informationJson.get("credentials");
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");
                    builder.setCredentials(email, password);
                }

                if (informationJson.containsKey("country")) {
                    builder.setCountry((String) informationJson.get("country"));
                }

                if (informationJson.containsKey("age")) {
                    builder.setAge(((Long) informationJson.get("age")).intValue());
                }

                if (informationJson.containsKey("gender")) {
                    builder.setGender(((String) informationJson.get("gender")).charAt(0));
                }

                if (informationJson.containsKey("birthDate")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDateTime birthDate = LocalDate.parse((String) informationJson.get("birthDate"), formatter).atStartOfDay();
                    builder.setBirthDate(birthDate);
                }
                User.Information information = builder.build();
                AccountType userType = AccountType.valueOf((String) userJson.get("userType"));

                User<?> user = UserFactory.factory(userType);
                assert user != null;
                user.setUsername(username);
                user.setExperience(experience);
                user.setInformation(information);
                user.setUserType(userType);

                if(userJson.containsKey("favoriteProductions")) {
                    SortedSet<String> favoriteProductions = new TreeSet<>();
                    JSONArray jsonArray = (JSONArray) userJson.get("favoriteProductions");
                    for (Object productionObj : jsonArray) {
                        String production = (String) productionObj;
                        favoriteProductions.add(production);
                    }

                    user.setFavoriteProductions(favoriteProductions);
                }

                if(userJson.containsKey("favoriteProductions")) {
                    SortedSet<String> favoriteActors = new TreeSet<>();
                    JSONArray jsonArray = (JSONArray) userJson.get("favoriteActors");
                    for (Object actorObj : jsonArray) {
                        String actor = (String) actorObj;
                        favoriteActors.add(actor);
                    }

                    user.setFavoriteActors(favoriteActors);
                }

                if(userJson.containsKey("notifications")) {
                    JSONArray jsonArray = (JSONArray) userJson.get("notifications");
                    List<String> notifications = new ArrayList<>();
                    for (Object item : jsonArray) {
                        notifications.add((String) item);
                    }
                    user.setNotifications(notifications);
                }

                if(userType != AccountType.Regular) {
                    Staff<?> staffUser = (Staff<?>) user;
                    staffUser.setRequests(new ArrayList<>());
                    if(userJson.containsKey("productionsContribution")) {
                        SortedSet<String> productionsContribution = new TreeSet<>();
                        JSONArray jsonArray = (JSONArray) userJson.get("productionsContribution");
                        for (Object productionObj : jsonArray) {
                            String production = (String) productionObj;
                            productionsContribution.add(production);
                        }
                        staffUser.setProductionsContribution(productionsContribution);

                    }

                    if(userJson.containsKey("actorsContribution")) {
                        SortedSet<String> actorsContribution = new TreeSet<>();
                        JSONArray jsonArray = (JSONArray) userJson.get("actorsContribution");
                        for (Object actorObj : jsonArray) {
                            String actor = (String) actorObj;
                            actorsContribution.add(actor);
                        }
                        staffUser.setActorsContribution(actorsContribution);
                    }
                }

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public static List<Request> parseRequests(String filePath) {
        List<Request> requests = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(filePath);
            Object obj = parser.parse(reader);
            JSONArray requestsArray = (JSONArray) obj;

            for (Object requestObj : requestsArray) {
                JSONObject requestJson = (JSONObject) requestObj;

                RequestType type = RequestType.valueOf((String) requestJson.get("type"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime createdDate = LocalDate.parse((String) requestJson.get("createdDate"), formatter).atStartOfDay();

                String requesterUsername = (String) requestJson.get("username");

                String actorName = null;
                if (requestJson.containsKey("actorName"))
                    actorName = (String) requestJson.get("actorName");

                String movieTitle = null;
                if (requestJson.containsKey("movieTitle"))
                    movieTitle = (String) requestJson.get("movieTitle");

                String resolverUsername = (String) requestJson.get("to");

                String description = (String) requestJson.get("description");

                Request request = new Request(type, createdDate, requesterUsername,
                        actorName, movieTitle, resolverUsername, description);

                requests.add(request);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }
}
