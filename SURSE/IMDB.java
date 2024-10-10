package org.example;

import java.util.*;

public class IMDB {

    private List<User> users;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;
    public static IMDB instance = null;
    private User loggedUser = null;

    public Scanner scanner = new Scanner(System.in);

    private Flow flowapp = new Flow();

    private IMDB() {
        users = new ArrayList<>();
        actors = new ArrayList<>();
        requests = new ArrayList<>();
        productions = new ArrayList<>();
    }

    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    public void run() {
        loadDataFromJSON();

        System.out.println("Welcome! Please choose how you want to use this app:");
        System.out.println("\t1. From terminal");
        System.out.println("\t2. Graphic interface");
        int nr = scanner.nextInt();
        if (nr == 2) {
            openGraphicInterface();
        } else {
            while (loggedUser == null)
                authentificateUser();
        }

        startFlowApplication();
    }

    private void loadDataFromJSON() {
        actors = Parser.parseActors("src/main/resources/input/actors.json");
        users = Parser.parseUsers("src/main/resources/input/accounts.json");
        requests = Parser.parseRequests("src/main/resources/input/requests.json");
        productions = Parser.parseProductions("src/main/resources/input/production.json");
    }

    public void authentificateUser() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your credentials!");
        System.out.println("\tEmail: ");
        String email = s.nextLine();

        System.out.println("\tPassword: ");
        String password = s.nextLine();

        for (User user : users) {
            String emailUser = user.getInformation().getCredentials().getEmail();
            String passwordUser = user.getInformation().getCredentials().getPassword();
            if (emailUser.equals(email) && passwordUser.equals(password)) {
                System.out.println("Welcome back user " + user.getUsername());
                if(user instanceof Regular<?> || user instanceof Contributor<?>) {
                    System.out.println("Here's your experience score: " + user.getExperience());
                    System.out.println("Make requests and add reviews for a bigger score!");
                }
                loggedUser = user;
                return;
            }
        }
    }

    public void openGraphicInterface() {

    }

    public void startFlowApplication() {
        if(loggedUser instanceof Regular<?>) {
            flowapp.showRegularMenu();
        } else if(loggedUser instanceof Admin<?>) {
            flowapp.showAdminMenu();
        } else if (loggedUser instanceof Contributor<?>) {
            flowapp.showContributorMenu();
        }
    }

    public static void main(String[] args) {
        IMDB imdb = IMDB.getInstance();
        imdb.run();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void logOutUser() {
        loggedUser = null;
    }
    static class RequestsHolder {
        public static List<Request> requests = new ArrayList<>();

        public static void addRequest(Request request) {
            requests.add(request);
        }

        public static void removeRequest(Request request) {
            requests.remove(request);
        }

    }
}
