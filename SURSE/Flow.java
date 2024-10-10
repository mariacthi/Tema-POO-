package org.example;

import kotlin.Pair;

import java.time.LocalDateTime;
import java.util.*;

public class Flow {
    Scanner s = new Scanner(System.in);
    int closeApp = 1;
    public Flow() {
        //
    }

    public void showRegularMenu() {
        try {
            int ok = 1;
            while (ok == 1) {
                System.out.println("Regular Menu:");
                System.out.println("Choose an option please:");
                System.out.println("\t1. View details of all productions");
                System.out.println("\t2. View details of all actors");
                System.out.println("\t3. View received notifications");
                System.out.println("\t4. Search for a specific movie/series/actor");
                System.out.println("\t5. Add/Delete a production/actor to/from favorites");
                System.out.println("\t6. Create/Delete a request");
                System.out.println("\t7. Add/Delete a review for a production");
                System.out.println("\t8. Logout");
                System.out.println("Enter your choice:");

                Regular<?> user = (Regular<?>) IMDB.getInstance().getLoggedUser();
                int nr = s.nextInt();
                int choice;
                String name;
                switch (nr) {
                    case 1:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Filter by genre");
                        System.out.println("\t2. Filter by number of ratings");
                        System.out.println("\t3. No filter");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1)
                            printProductionsByGenre();
                        else if (choice == 2)
                            printProductionsByRating();
                        else if (choice == 3)
                            printProductions();
                        break;
                    case 2:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Filter by name");
                        System.out.println("\t2. No filter");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1) {
                            printActorsByName();
                        } else if (choice == 2) {
                            printActors();
                        }
                        break;
                    case 3:
                        List<String> notifications = user.getNotifications();
                        System.out.println(notifications);
                        break;
                    case 4:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Movie/Series");
                        System.out.println("\t2. Actor");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        switch (choice) {
                            case 1:
                                searchProductions();
                                break;
                            case 2:
                                searchActors();
                                break;
                        }
                        break;
                    case 5:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Add a production to favorites");
                        System.out.println("\t2. Add an actor to favorites");
                        System.out.println("\t3. Delete a production from favorites");
                        System.out.println("\t4. Delete an actor from favorites");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();

                        switch (choice) {
                            case 1:
                                System.out.println("Enter production name: ");
                                Scanner favProd = new Scanner(System.in);
                                name = favProd.nextLine();
                                user.addFavoriteProduction(name);
                                System.out.println("Favorite productions list updated: ");
                                System.out.println(user.getFavoriteProductions());
                                break;
                            case 2:
                                System.out.println("Enter actor name: ");
                                Scanner favActor = new Scanner(System.in);
                                name = favActor.nextLine();
                                user.addFavoriteActor(name);
                                System.out.println("Favorite actors list updated: ");
                                System.out.println(user.getFavoriteActors());
                                break;
                            case 3:
                                System.out.println("Enter production name: ");
                                Scanner delProd = new Scanner(System.in);
                                name = delProd.nextLine();
                                user.deleteFavoriteProduction(name);
                                System.out.println("Favorite productions list updated: ");
                                System.out.println(user.getFavoriteProductions());
                                break;
                            case 4:
                                System.out.println("Enter actor name: ");
                                Scanner delActor = new Scanner(System.in);
                                name = delActor.nextLine();
                                user.deleteFavoriteActor(name);
                                System.out.println("Favorite actors list updated: ");
                                System.out.println(user.getFavoriteActors());
                                break;
                        }
                        break;
                    case 6:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Create a request");
                        System.out.println("\t2. Delete a request");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();

                        if (choice == 1)
                            createRequest();
                        else
                            deleteRequest();
                        break;
                    case 7:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Add a review");
                        System.out.println("\t2. Delete a review");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();

                        if (choice == 1)
                            addReview();
                        else
                            deleteReview();
                        break;
                    case 8:
                        System.out.println("Logging out user " + user.getUsername());
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Log in");
                        System.out.println("\t2. Close the app");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1) {
                            ok = 0;
                            break;
                        } else {
                            return;
                        }
                    default:
                        throw new InvalidCommandException("Invalid command");
                }
            }
            IMDB.getInstance().authentificateUser();
            IMDB.getInstance().startFlowApplication();
        } catch(InvalidCommandException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void printProductionsByGenre()  {
        List<Production> productions = IMDB.getInstance().getProductions();

        System.out.println("Choose a genre please:");
        System.out.println("\t1. Action");
        System.out.println("\t2. Adventure");
        System.out.println("\t3. Comedy");
        System.out.println("\t4. Drama");
        System.out.println("\t5. Horror");
        System.out.println("\t6. SF");
        System.out.println("\t7. Fantasy");
        System.out.println("\t8. Romance");
        System.out.println("\t9. Mystery");
        System.out.println("\t10. Thriller");
        System.out.println("\t11. Crime");
        System.out.println("\t12. Biography");
        System.out.println("\t13. War");
        System.out.println("\t14. Cooking");

        int nr = s.nextInt();
        switch(nr) {
            case 1:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Action))
                        System.out.println(p);
                }
                break;
            case 2:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Adventure))
                        System.out.println(p);
                }
                break;
            case 3:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Comedy))
                        System.out.println(p);
                }
                break;
            case 4:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Drama))
                        System.out.println(p);
                }
                break;
            case 5:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Horror))
                        System.out.println(p);
                }
                break;
            case 6:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.SF))
                        System.out.println(p);
                }
                break;
            case 7:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Fantasy))
                        System.out.println(p);
                }
                break;
            case 8:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Romance))
                        System.out.println(p);
                }
                break;
            case 9:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Mystery))
                        System.out.println(p);
                }
                break;
            case 10:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Thriller))
                        System.out.println(p);
                }
                break;
            case 11:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Crime))
                        System.out.println(p);
                }
                break;
            case 12:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Biography))
                        System.out.println(p);
                }
                break;
            case 13:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.War))
                        System.out.println(p);
                }
                break;
            case 14:
                for (Production p : productions) {
                    if(p.getGenres().contains(Genre.Cooking))
                        System.out.println(p);
                }
                break;
        }

    }

    public void printProductionsByRating() {
        Comparator<Production> byRating = Comparator.comparing(Production::getNumberOfRatings).reversed();
        List<Production> productions = IMDB.getInstance().getProductions();
        productions.sort(byRating);

        for (Production p : productions) {
            System.out.println(p);
        }
    }

    public void printProductions() {
        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production p : productions) {
            System.out.println(p);
        }
    }

    public void printActorsByName() {
        Comparator<Actor> byName = Comparator.comparing(Actor::getActorName);
        List<Actor> actors = IMDB.getInstance().getActors();
        actors.sort(byName);

        for (Actor actor : actors) {
            System.out.println(actor);
        }
    }

    public void printActors() {
        List<Actor> actors = IMDB.getInstance().getActors();
        for (Actor a : actors) {
            System.out.println(a);
        }
    }

    public void searchProductions() {
        int stop = 1;

        Scanner scanner = new Scanner(System.in);
        List<Production> productions = IMDB.getInstance().getProductions();

        while(stop == 1) {
            System.out.println("Choose an option please:");
            System.out.println("\t1. Enter director");
            System.out.println("\t2. Enter actor");
            System.out.println("\t3. Enter genre");
            System.out.println("\t4. Enter plot");
            System.out.println("\t5. Enter average rating");
            System.out.println("\t6. Enter letters to search");
            System.out.println("\t7. Exit search");
            int choice = scanner.nextInt();

           switch (choice) {
                case 1:
                    System.out.println("\tEnter director name: ");
                    Scanner directorScanner = new Scanner(System.in);
                    String directorName = directorScanner.nextLine();
                    System.out.println("Searching productions from " + directorName);
                    for (Production p : productions) {
                        List<String> directors = p.getDirectors();
                        if (directors.contains(directorName))
                            System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.println("\tEnter actor name: ");
                    Scanner actorScanner = new Scanner(System.in);
                    String actorName = actorScanner.nextLine();
                    System.out.println("Searching productions with " + actorName);
                    for (Production p : productions) {
                        List<String> actors = p.getActors();
                        if (actors.contains(actorName))
                            System.out.println(p);
                    }
                    break;
                case 3:
                    System.out.println("\tEnter genre name: ");
                    Scanner genreScanner = new Scanner(System.in);
                    String genreName = genreScanner.nextLine();
                    System.out.println("Searching productions with " + genreName);
                    for (Production p : productions) {
                        List<Genre> genres = p.getGenres();
                        if (genres.contains(Genre.valueOf(genreName)))
                            System.out.println(p);
                    }
                    break;
                case 4:
                    System.out.println("\tEnter plot: ");
                    Scanner plotScanner = new Scanner(System.in);
                    String plot = plotScanner.nextLine();
                    System.out.println("Searching productions with " + plot);
                    for (Production p : productions) {
                        String plotUser = p.getPlot();
                        if (plotUser.equals(plot))
                            System.out.println(p);
                    }
                    break;
                case 5:
                    System.out.println("\tEnter average rating: ");
                    Scanner ratingScanner = new Scanner(System.in);
                    int nr = ratingScanner.nextInt();
                    System.out.println("Searching productions with rating of " + nr);
                    for (Production p : productions) {
                        Double averageRating = p.getAverageRating();
                        if (averageRating == nr)
                            System.out.println(p);
                    }
                    break;
                case 6:
                    System.out.println("\tEnter letters: ");
                    Scanner letterScanner = new Scanner(System.in);
                    String name = letterScanner.nextLine();
                    System.out.println("Searching productions starting with " + name);
                    for (Production p : productions) {
                        String title = p.getTitle();
                        if(title.startsWith(name))
                            System.out.println(p);
                    }
                    break;
                case 7:
                    stop = 0;
                    break;
            }
        }
    }
    public void searchActors() {
        Scanner actorScanner = new Scanner(System.in);
        int stop = 1;
        String name;
        List<Actor> actors = IMDB.getInstance().getActors();
        while(stop == 1) {
            System.out.println("Choose an option please:");
            System.out.println("\t1. Enter production");
            System.out.println("\t2. Enter biography");
            System.out.println("\t3. Enter letters to search");
            System.out.println("\t4. Exit search");
            int choice = s.nextInt();
            switch(choice) {
                case 1:
                    System.out.println("\tEnter production name: ");

                    name = actorScanner.nextLine();
                    for (Actor a : actors) {
                        List<Pair<String, ProductionType>> filmography = a.getFilmography();
                        for(Pair<String, ProductionType> pair : filmography)
                            if(pair.getFirst().equals(name)){
                                System.out.println(a);
                            }
                    }
                    break;
                case 2:
                    System.out.println("\tEnter biography: ");
                    name = actorScanner.nextLine();
                    for (Actor a : actors) {
                        String biography = a.getBiography();
                        if (biography.equals(name))
                            System.out.println(a);
                    }
                    break;
                case 3:
                    System.out.println("\tEnter letters: ");
                    name = actorScanner.nextLine();
                    for (Actor a : actors) {
                        String actorName = a.getActorName();
                        if(actorName.startsWith(name))
                            System.out.println(a);
                    }
                    break;
                case 4:
                    stop = 0;
                    break;
            }
        }
    }

    public void createRequest() {
        System.out.println("Enter the type:");
        System.out.println("\t1. DELETE_ACCOUNT");
        System.out.println("\t2. ACTOR_ISSUE");
        System.out.println("\t3. MOVIE_ISSUE");
        System.out.println("\t4. OTHERS");
        Scanner scanner = new Scanner(System.in);
        int nr = scanner.nextInt();

        if (nr == 1) {
            String userName = IMDB.getInstance().getLoggedUser().getUsername();
            String resolver = "ADMIN";
            System.out.println("Enter a description");
            Scanner descriptionScanner = new Scanner(System.in);
            String description = descriptionScanner.nextLine();
            RequestType type = RequestType.DELETE_ACCOUNT;
            LocalDateTime createdDate = LocalDateTime.now();
            Request r = new Request(type, createdDate, userName, null, null, resolver, description);
            IMDB.RequestsHolder.addRequest(r);
        } else if (nr == 2) {
            String userName = IMDB.getInstance().getLoggedUser().getUsername();

            System.out.println("Enter name of the actor");
            Scanner actorScanner = new Scanner(System.in);
            String actor = actorScanner.nextLine();

            System.out.println("Enter a description");
            Scanner descriptionScanner = new Scanner(System.in);
            String description = descriptionScanner.nextLine();

            RequestType type = RequestType.ACTOR_ISSUE;
            LocalDateTime createdDate = LocalDateTime.now();

            List<User> users = IMDB.getInstance().getUsers();
            for (User<?> u : users) {
                if (u instanceof Contributor<?>) {
                    if(((Contributor<?>) u).getActorsContribution().isEmpty())
                        continue;
                    else {
                        if(((Contributor<?>) u).getActorsContribution().contains(actor)) {
                            String resolver = u.getUsername();
                            Request r = new Request(type, createdDate, userName, actor, null, resolver, description);
                            ((Contributor<?>) u).createRequest(r);
                        }
                    }
                }
            }
        } else if (nr == 3) {
            String userName = IMDB.getInstance().getLoggedUser().getUsername();

            System.out.println("Enter name of the movie");
            Scanner movieScanner = new Scanner(System.in);
            String movie= movieScanner.nextLine();

            System.out.println("Enter a description");
            Scanner descriptionScanner = new Scanner(System.in);
            String description = descriptionScanner.nextLine();

            RequestType type = RequestType.MOVIE_ISSUE;
            LocalDateTime createdDate = LocalDateTime.now();

            List<User> users = IMDB.getInstance().getUsers();
            for (User<?> u : users) {
                if (u instanceof Contributor<?>) {
                    if(((Contributor<?>) u).getProductionsContribution().isEmpty())
                        continue;
                    else {
                        if (((Contributor<?>) u).getProductionsContribution().contains(movie)) {
                            String resolver = u.getUsername();
                            Request r = new Request(type, createdDate, userName, null, movie, resolver, description);
                            ((Contributor<?>) u).createRequest(r);
                        }
                    }
                }
            }
        } else if (nr == 4) {
            String userName = IMDB.getInstance().getLoggedUser().getUsername();
            String resolver = "ADMIN";
            System.out.println("Enter a description");
            Scanner descriptionScanner = new Scanner(System.in);
            String description = descriptionScanner.nextLine();
            RequestType type = RequestType.OTHERS;
            LocalDateTime createdDate = LocalDateTime.now();
            Request r = new Request(type, createdDate, userName, null, null, resolver, description);
            IMDB.RequestsHolder.addRequest(r);
        }
    }

    public void deleteRequest() {
        String userName = IMDB.getInstance().getLoggedUser().getUsername();
        List<Request> adminRequests = IMDB.RequestsHolder.requests;
        for (Request r : adminRequests) {
            if (r.getRequesterUsername().equals(userName)) {
                System.out.println(r);
                System.out.println("Do you want to delete this request?");
                System.out.println("\t1. Yes");
                System.out.println("\t2. No");
                Scanner scanner = new Scanner(System.in);
                int nr = scanner.nextInt();
                if (nr == 1) {
                    IMDB.RequestsHolder.removeRequest(r);

                }
            }
        }

        List<User> users = IMDB.getInstance().getUsers();
        for (User<?> u : users) {
            if (u instanceof Contributor<?>) {
                if(((Contributor<?>) u).getRequests().isEmpty())
                    continue;
                else {
                    List<Request> requests = ((Contributor<?>) u).getRequests();
                    for (Request r : requests) {
                        if (r.getRequesterUsername().equals(userName)) {
                            System.out.println(r);
                            System.out.println("Do you want to delete this request?");
                            System.out.println("\t1. Yes");
                            System.out.println("\t2. No");
                            Scanner scanner = new Scanner(System.in);
                            int nr = scanner.nextInt();
                            if (nr == 1) {
                                ((Contributor<?>) u).getRequests().remove(r);
                            }
                        }
                    }
                }
            }
        }
    }

    public void addReview() {
        Scanner reviewScanner = new Scanner(System.in);
        System.out.println("Please enter the name of the production");
        String name = reviewScanner.nextLine();
        System.out.println("Please enter a comment");
        String comment = reviewScanner.nextLine();
        System.out.println("Please enter your rating");
        int rating = reviewScanner.nextInt();

        Regular<?> user = (Regular<?>) IMDB.getInstance().getLoggedUser();

        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production p : productions) {
            if(p.getTitle().equals(name)) {
                user.addRating(p, rating, comment);
                return;
            }
        }
        System.out.println("Rating invalid");
    }

    public void deleteReview() {
        Scanner reviewScanner = new Scanner(System.in);
        System.out.println("Please enter the name of the production");
        String name = reviewScanner.nextLine();

        Regular<?> user = (Regular<?>) IMDB.getInstance().getLoggedUser();

        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production p : productions) {
            if(p.getTitle().equals(name)) {
                List<Rating> ratings = p.getRatings();
                for(Rating r : ratings) {
                    if(r.getUsername().equals(user.getUsername())) {
                        p.deleteRating(r);
                        return;
                    }
                }
                break;
            }
        }
        System.out.println("You did not rate this production");
    }

    public void showAdminMenu() {
        try {
            int ok = 1;
            while (ok == 1) {
                System.out.println("Admin Menu:");
                System.out.println("Choose an option please:");
                System.out.println("\t1. View details of all productions");
                System.out.println("\t2. View details of all actors");
                System.out.println("\t3. View received notifications");
                System.out.println("\t4. Search for a specific movie/series/actor");
                System.out.println("\t5. Add/Delete a production/actor to/from favorites");
                System.out.println("\t6. Add/Delete a production/actor from the system");
                System.out.println("\t7. View and resolve received requests");
                System.out.println("\t8. Update information about productions/actors");
                System.out.println("\t9. Add/Delete a user from the system");
                System.out.println("\t10. Logout");
                System.out.println("Enter your choice:");

                Admin<?> user = (Admin<?>) IMDB.getInstance().getLoggedUser();
                int nr = s.nextInt();
                int choice;
                String name;
                switch (nr) {
                    case 1:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Filter by genre");
                        System.out.println("\t2. Filter by number of ratings");
                        System.out.println("\t3. No filter");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1)
                            printProductionsByGenre();
                        else if (choice == 2)
                            printProductionsByRating();
                        else if (choice == 3)
                            printProductions();
                        break;
                    case 2:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Filter by name");
                        System.out.println("\t2. No filter");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1) {
                            printActorsByName();
                        } else if (choice == 2) {
                            printActors();
                        }
                        break;
                    case 3:
                        List<String> notifications = user.getNotifications();
                        System.out.println(notifications);
                        break;
                    case 4:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Movie/Series");
                        System.out.println("\t2. Actor");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        switch (choice) {
                            case 1:
                                searchProductions();
                                break;
                            case 2:
                                searchActors();
                                break;
                        }
                        break;
                    case 5:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Add a production to favorites");
                        System.out.println("\t2. Add an actor to favorites");
                        System.out.println("\t3. Delete a production from favorites");
                        System.out.println("\t4. Delete an actor from favorites");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();

                        switch (choice) {
                            case 1:
                                System.out.println("Enter production name: ");
                                Scanner favProd = new Scanner(System.in);
                                name = favProd.nextLine();
                                user.addFavoriteProduction(name);
                                System.out.println("Favorite productions list updated: ");
                                System.out.println(user.getFavoriteProductions());
                                break;
                            case 2:
                                System.out.println("Enter actor name: ");
                                Scanner favActor = new Scanner(System.in);
                                name = favActor.nextLine();
                                user.addFavoriteActor(name);
                                System.out.println("Favorite actors list updated: ");
                                System.out.println(user.getFavoriteActors());
                                break;
                            case 3:
                                System.out.println("Enter production name: ");
                                Scanner delProd = new Scanner(System.in);
                                name = delProd.nextLine();
                                user.deleteFavoriteProduction(name);
                                System.out.println("Favorite productions list updated: ");
                                System.out.println(user.getFavoriteProductions());
                                break;
                            case 4:
                                System.out.println("Enter actor name: ");
                                Scanner delActor = new Scanner(System.in);
                                name = delActor.nextLine();
                                user.deleteFavoriteActor(name);
                                System.out.println("Favorite actors list updated: ");
                                System.out.println(user.getFavoriteActors());
                                break;
                        }
                        break;
                    case 6:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Add a production to system");
                        System.out.println("\t2. Add an actor to system");
                        System.out.println("\t3. Delete a production from the system");
                        System.out.println("\t4. Delete an actor from the system");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();

                        switch (choice) {
                            case 1:
                                user.addProductionSystem(addProductionInSystem());
                                break;
                            case 2:
                                System.out.println("Enter actor name: ");
                                Scanner actorScanner = new Scanner(System.in);
                                name = actorScanner.nextLine();

                                List<Pair<String, ProductionType>> filmography = enterProductionsForActors();

                                System.out.println("Enter biography");
                                String biography = actorScanner.nextLine();

                                Actor a = new Actor(name, filmography, biography);
                                user.addActorSystem(a);
                                break;
                            case 3:
                                System.out.println("Enter production name: ");
                                Scanner delProd = new Scanner(System.in);
                                name = delProd.nextLine();
                                user.removeProductionSystem(name);
                                break;
                            case 4:
                                System.out.println("Enter actor name: ");
                                Scanner delActor = new Scanner(System.in);
                                name = delActor.nextLine();
                                user.removeActorSystem(name);
                                break;
                        }
                        break;
                    case 7:
                        user.resolveUserRequests();
                        break;
                    case 8:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Update production");
                        System.out.println("\t2. Update actor");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1) {
                            if (user.getProductionsContribution().isEmpty())
                                System.out.println("You have not added any productions");
                            else {
                                System.out.println("You can update: ");
                                System.out.println(user.getProductionsContribution());
                                System.out.println("Enter name of production");
                                Scanner productionScanner = new Scanner(System.in);
                                String productionName = productionScanner.nextLine();
                                updateProduction(productionName);
                            }
                        } else if (choice == 2) {
                            if (user.getActorsContribution().isEmpty())
                                System.out.println("You have not added any actors");
                            else {
                                System.out.println("You can update: ");
                                System.out.println(user.getActorsContribution());
                                System.out.println("Enter name of actor");
                                Scanner actorScanner = new Scanner(System.in);
                                String actorName = actorScanner.nextLine();
                                updateActor(actorName);
                            }
                        }
                        break;
                    case 9:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Add user");
                        System.out.println("\t2. Delete user");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1)
                            user.addUser();
                        else {
                            System.out.println("Enter username");
                            Scanner usernameScanner = new Scanner(System.in);
                            String username = usernameScanner.nextLine();
                            List<User> users = IMDB.getInstance().getUsers();
                            for (User u : users) {
                                if (u.getUsername().equals(username)) {
                                    IMDB.getInstance().getUsers().remove(u);
                                    user.removeUser(u);
                                }
                            }
                        }
                        break;
                    case 10:
                        System.out.println("Logging out user " + user.getUsername());
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Log in");
                        System.out.println("\t2. Close the app");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1) {
                            ok = 0;
                            break;
                        } else {
                            return;
                        }
                    default:
                        throw new InvalidCommandException("invalid command");
                }
            }

            IMDB.getInstance().authentificateUser();
            IMDB.getInstance().startFlowApplication();
        } catch(InvalidCommandException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduction(String productionName) {
        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production p : productions) {
            if (p.getTitle().equals(productionName)) {
                System.out.println("Choose an option please:");
                System.out.println("\t1. Add director");
                System.out.println("\t2. Add actor");
                System.out.println("\t3. Add genre");
                System.out.println("\t4. Update plot");
                System.out.println("\t5. Delete director");
                System.out.println("\t6. Delete actor");
                System.out.println("\t7. Delete genre");
                Scanner scanner = new Scanner(System.in);
                int nr = scanner.nextInt();
                scanner.nextLine();
                String name;
                switch(nr) {
                    case 1:
                        System.out.println("Please enter director name: ");
                         name = scanner.nextLine();
                         List<String> directors = p.getDirectors();
                         directors.add(name);
                         p.setDirectors(directors);
                         break;
                    case 2:
                        System.out.println("Please enter actor name: ");
                        name = scanner.nextLine();
                        p.getActors().add(name);
                        break;
                    case 3:
                        System.out.println("Please enter genre: ");
                        name = scanner.nextLine();
                        p.getGenres().add(Genre.valueOf(name));
                        break;
                    case 4:
                        System.out.println("Please enter plot: ");
                        name = scanner.nextLine();
                        p.setPlot(name);
                        break;
                    case 5:
                        System.out.println("Please enter director name: ");
                        name = scanner.nextLine();
                        p.getDirectors().remove(name);
                        break;
                    case 6:
                        System.out.println("Please enter actor name: ");
                        name = scanner.nextLine();
                        p.getActors().remove(name);
                        break;
                    case 7:
                        System.out.println("Please enter genre: ");
                        name = scanner.nextLine();
                        p.getGenres().remove(Genre.valueOf(name));
                        break;
                }
            }
        }

    }

    public void updateActor(String actorName) {
        List<Actor> actors = IMDB.getInstance().getActors();
        for (Actor a : actors) {
            if (a.getActorName().equals(actorName)) {
                System.out.println("Choose an option please:");
                System.out.println("\t1. Add movie");
                System.out.println("\t2. Add series");
                System.out.println("\t3. Update biography");
                System.out.println("\t4. Delete movie");
                System.out.println("\t5. Delete series");
                Scanner scanner = new Scanner(System.in);
                int nr = scanner.nextInt();
                scanner.nextLine();
                String name;
                switch(nr) {
                    case 1:
                        System.out.println("Please enter movie name: ");
                        name = scanner.nextLine();
                        Pair<String, ProductionType> pair = new Pair<>(name,ProductionType.Movie);
                        a.getFilmography().add(pair);
                        break;
                    case 2:
                        System.out.println("Please enter series name: ");
                        name = scanner.nextLine();
                        Pair<String, ProductionType> pair2 = new Pair<>(name,ProductionType.Series);
                        a.getFilmography().add(pair2);
                        break;
                    case 3:
                        System.out.println("Please enter new biography: ");
                        name = scanner.nextLine();
                        a.setBiography(name);
                        break;
                    case 4:
                        System.out.println("Please enter movie name: ");
                        name = scanner.nextLine();
                        Pair<String, ProductionType> pair3 = new Pair<>(name,ProductionType.Movie);
                        a.getFilmography().remove(pair3);
                        break;
                    case 5:
                        System.out.println("Please enter series name: ");
                        name = scanner.nextLine();
                        Pair<String, ProductionType> pair4 = new Pair<>(name,ProductionType.Series);
                        a.getFilmography().remove(pair4);
                        break;
                }
            }
        }

    }

    public Production addProductionInSystem() {
        System.out.println("Enter production name: ");
        Scanner prodScanner = new Scanner(System.in);
        String name = prodScanner.nextLine();
        System.out.println("Choose an option please:");
        System.out.println("\t1. Movie");
        System.out.println("\t2. Series");
        int nr = prodScanner.nextInt();
        prodScanner.nextLine();
        ProductionType type = ProductionType.Movie;
        if (nr == 2) {
            type = ProductionType.Series;
        }
        int ok = 0;
        List<String> directors = new ArrayList<>();
        while (ok == 0) {
            System.out.println("Choose an option please:");
            System.out.println("\t1. Enter director name");
            System.out.println("\t2. I'm done with directors");
            Scanner director = new Scanner(System.in);
            int n = director.nextInt();
            director.nextLine();
            if (n == 1) {
                System.out.println("Enter director name");
                String str = director.nextLine();
                directors.add(str);
            } else
                ok = 1;
        }
        ok = 0;
        List<String> actors = new ArrayList<>();
        while (ok == 0) {
            System.out.println("Choose an option please:");
            System.out.println("\t1. Enter actor name");
            System.out.println("\t2. I'm done with actors");
            Scanner director = new Scanner(System.in);
            int n = director.nextInt();
            director.nextLine();
            if (n == 1) {
                System.out.println("Enter actor name");
                String str = director.nextLine();
                actors.add(str);
            } else
                ok = 1;
        }
        ok = 0;
        List<Genre> genres = new ArrayList<>();
        while (ok == 0) {
            System.out.println("Choose an option please:");
            System.out.println("\t1. Enter genre");
            System.out.println("\t2. I'm done with genres");
            Scanner director = new Scanner(System.in);
            int n = director.nextInt();
            director.nextLine();
            if (n == 1) {
                System.out.println("Enter genre");
                String str = director.nextLine();
                genres.add(Genre.valueOf(str));
            } else
                ok = 1;
        }

        System.out.println("Enter plot: ");
        String plot = prodScanner.nextLine();
        System.out.println("Enter release year: ");
        int releaseYear = prodScanner.nextInt();
        prodScanner.nextLine();


        if(type == ProductionType.Movie) {
            System.out.println("Enter duration: ");
            String duration = prodScanner.nextLine();
            Movie movie = new Movie(name, type, directors, actors, genres, new ArrayList<>(), duration, plot, releaseYear, 0.0);
            return movie;
        } else {
            System.out.print("Enter the number of seasons: ");
            int numberOfSeasons = prodScanner.nextInt();
            prodScanner.nextLine();
            Map<String, List<Episode>> seasons = enterSeasons(numberOfSeasons);
            Series series = new Series(name, type, directors, actors, genres, new ArrayList<>(), plot, 0.0, releaseYear, numberOfSeasons, seasons);
            return series;
        }
    }

    public Map<String, List<Episode>> enterSeasons(int numberOfSeasons) {
        Scanner scanner = new Scanner(System.in);

        Map<String, List<Episode>> seasons = new TreeMap<>();

        for (int seasonNumber = 1; seasonNumber <= numberOfSeasons; seasonNumber++) {
            System.out.println("Enter details for Season " + seasonNumber + ":");

            List<Episode> seasonEpisodes = new ArrayList<>();

            System.out.print("Enter the number of episodes in Season " + seasonNumber + ": ");
            int numberOfEpisodes = scanner.nextInt();
            scanner.nextLine();

            for (int episodeNumber = 1; episodeNumber <= numberOfEpisodes; episodeNumber++) {
                System.out.println("Enter details for Episode " + episodeNumber + ":");

                System.out.print("Enter the name of the episode: ");
                String episodeName = scanner.nextLine();

                System.out.print("Enter the duration of the episode: ");
                String duration = scanner.nextLine();

                Episode episode = new Episode(episodeName, duration);
                seasonEpisodes.add(episode);
            }

            seasons.put("Season " + seasonNumber, seasonEpisodes);
        }

        return seasons;
    }

    public List<Pair<String, ProductionType>> enterProductionsForActors() {
        Scanner scanner = new Scanner(System.in);
        List<Pair<String, ProductionType>> filmography = new ArrayList<>();
        List<Production> productions = IMDB.getInstance().getProductions();
        while (true) {
            System.out.println("Choose an option please:");
            System.out.println("\t1. Enter a movie the actor participated in");
            System.out.println("\t2. Enter a series the actor participated in");
            System.out.println("\t3. I'm done with actor details");
            int nr = scanner.nextInt();
            scanner.nextLine();
            ProductionType type = ProductionType.Movie;
            if (nr == 2) {
                type = ProductionType.Series;
            } else if (nr ==3)
                break;

            System.out.print("Enter the name of the production: ");
            String productionName = scanner.nextLine();

            filmography.add(new Pair<>(productionName, type));
        }

        return filmography;

    }

    public void showContributorMenu() {
        try {
            int ok = 1;
            while (ok == 1) {
                System.out.println("Contributor Menu:");
                System.out.println("Choose an option please:");
                System.out.println("\t1. View details of all productions");
                System.out.println("\t2. View details of all actors");
                System.out.println("\t3. View received notifications");
                System.out.println("\t4. Search for a specific movie/series/actor");
                System.out.println("\t5. Add/Delete a production/actor to/from favorites");
                System.out.println("\t6. Create/Delete a request");
                System.out.println("\t7. Add/Delete a production/actor from the system");
                System.out.println("\t8. View and resolve received requests");
                System.out.println("\t9. Update information about productions/actors");
                System.out.println("\t10. Logout");
                System.out.println("Enter your choice:");

                Contributor<?> user = (Contributor<?>) IMDB.getInstance().getLoggedUser();
                int nr = s.nextInt();
                int choice;
                String name;
                switch (nr) {
                    case 1:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Filter by genre");
                        System.out.println("\t2. Filter by number of ratings");
                        System.out.println("\t3. No filter");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1)
                            printProductionsByGenre();
                        else if (choice == 2)
                            printProductionsByRating();
                        else if (choice == 3)
                            printProductions();
                        break;
                    case 2:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Filter by name");
                        System.out.println("\t2. No filter");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1) {
                            printActorsByName();
                        } else if (choice == 2) {
                            printActors();
                        }
                        break;
                    case 3:
                        List<String> notifications = user.getNotifications();
                        System.out.println(notifications);
                        break;
                    case 4:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Movie/Series");
                        System.out.println("\t2. Actor");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        switch (choice) {
                            case 1:
                                searchProductions();
                                break;
                            case 2:
                                searchActors();
                                break;
                        }
                        break;
                    case 5:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Add a production to favorites");
                        System.out.println("\t2. Add an actor to favorites");
                        System.out.println("\t3. Delete a production from favorites");
                        System.out.println("\t4. Delete an actor from favorites");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();

                        switch (choice) {
                            case 1:
                                System.out.println("Enter production name: ");
                                Scanner favProd = new Scanner(System.in);
                                name = favProd.nextLine();
                                user.addFavoriteProduction(name);
                                System.out.println("Favorite productions list updated: ");
                                System.out.println(user.getFavoriteProductions());
                                break;
                            case 2:
                                System.out.println("Enter actor name: ");
                                Scanner favActor = new Scanner(System.in);
                                name = favActor.nextLine();
                                user.addFavoriteActor(name);
                                System.out.println("Favorite actors list updated: ");
                                System.out.println(user.getFavoriteActors());
                                break;
                            case 3:
                                System.out.println("Enter production name: ");
                                Scanner delProd = new Scanner(System.in);
                                name = delProd.nextLine();
                                user.deleteFavoriteProduction(name);
                                System.out.println("Favorite productions list updated: ");
                                System.out.println(user.getFavoriteProductions());
                                break;
                            case 4:
                                System.out.println("Enter actor name: ");
                                Scanner delActor = new Scanner(System.in);
                                name = delActor.nextLine();
                                user.deleteFavoriteActor(name);
                                System.out.println("Favorite actors list updated: ");
                                System.out.println(user.getFavoriteActors());
                                break;
                        }
                        break;
                    case 6:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Create a request");
                        System.out.println("\t2. Delete a request");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();

                        if (choice == 1)
                            createRequest();
                        else
                            deleteRequest();
                        break;
                    case 7:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Add a production to system");
                        System.out.println("\t2. Add an actor to system");
                        System.out.println("\t3. Delete a production from the system");
                        System.out.println("\t4. Delete an actor from the system");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();

                        switch (choice) {
                            case 1:
                                user.addProductionSystem(addProductionInSystem());
                                break;
                            case 2:
                                System.out.println("Enter actor name: ");
                                Scanner actorScanner = new Scanner(System.in);
                                name = actorScanner.nextLine();

                                List<Pair<String, ProductionType>> filmography = enterProductionsForActors();

                                System.out.println("Enter biography");
                                String biography = actorScanner.nextLine();

                                Actor a = new Actor(name, filmography, biography);
                                user.addActorSystem(a);
                                break;
                            case 3:
                                System.out.println("Enter production name: ");
                                Scanner delProd = new Scanner(System.in);
                                name = delProd.nextLine();
                                user.removeProductionSystem(name);
                                break;
                            case 4:
                                System.out.println("Enter actor name: ");
                                Scanner delActor = new Scanner(System.in);
                                name = delActor.nextLine();
                                user.removeActorSystem(name);
                                break;
                        }
                        break;
                    case 8:
                        user.resolveUserRequests();
                        break;
                    case 9:
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Update production");
                        System.out.println("\t2. Update actor");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1) {
                            if (user.getProductionsContribution().isEmpty())
                                System.out.println("You have not added any productions");
                            else {
                                System.out.println("You can update: ");
                                System.out.println(user.getProductionsContribution());
                                System.out.println("Enter name of production");
                                Scanner productionScanner = new Scanner(System.in);
                                String productionName = productionScanner.nextLine();
                                updateProduction(productionName);
                            }
                        } else if (choice == 2) {
                            if (user.getActorsContribution().isEmpty())
                                System.out.println("You have not added any actors");
                            else {
                                System.out.println("You can update: ");
                                System.out.println(user.getActorsContribution());
                                System.out.println("Enter name of actor");
                                Scanner actorScanner = new Scanner(System.in);
                                String actorName = actorScanner.nextLine();
                                updateActor(actorName);
                            }
                        }
                        break;
                    case 10:
                        System.out.println("Logging out user " + user.getUsername());
                        System.out.println("Choose an option please:");
                        System.out.println("\t1. Log in");
                        System.out.println("\t2. Close the app");
                        System.out.println("Enter your choice:");
                        choice = s.nextInt();
                        if (choice == 1) {
                            ok = 0;
                            break;
                        } else {
                            return;
                        }
                    default:
                        throw new InvalidCommandException("invalid command");
                }
            }

            IMDB.getInstance().authentificateUser();
            IMDB.getInstance().startFlowApplication();
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openGraphicInterface() {

    }

}
