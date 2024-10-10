package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Admin<T extends Comparable<T>> extends Staff<T> {
    public Admin(String username, int experience, Information information,
                 AccountType userType, SortedSet<String> favoriteProductions,
                 SortedSet<String> favoriteActors, SortedSet<String> addedProductions,
                 SortedSet<String> addedActors, List<String> notifications) {
        super(username, experience, information, userType, favoriteProductions,
                favoriteActors, addedProductions, addedActors, notifications);
    }

    public Admin() {
    // for User factory
    }

    public void addUser() {
        Information information;
        AccountType userType = AccountType.Regular;
        String username;
        int experience = 0;
        List<String> notifications = new ArrayList<>();
        SortedSet<String> favoriteProductions = new TreeSet<>();
        SortedSet<String> favoriteActors = new TreeSet<>();

        System.out.println("Choose Account type:");
        System.out.println("\t1. Regular");
        System.out.println("\t2. Admin");
        System.out.println("\t3. Contributor");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice == 2) {
            setUserType(AccountType.Admin);
        } else if (choice == 3) {
            setUserType(AccountType.Contributor);
        }
        scanner.nextLine();
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        username = generateUniqueUsername(name);

        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        String password = generateStrongPassword();
        System.out.println("This is your password " + password);
        User.Information.Builder builder = new User.Information.Builder(name);
        builder.setCredentials(email, password);

        System.out.println("Enter country: ");
        String country = scanner.nextLine();
        builder.setCountry(country);

        System.out.println("Enter your age: ");
        String age = scanner.nextLine();
        builder.setAge(Integer.parseInt(age));

        System.out.println("Enter your gender: ");
        String gender = scanner.nextLine();
        builder.setGender(gender.charAt(0));

        System.out.println("Enter your birth date: ");
        String birthdate = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime birthDate = LocalDate.parse(birthdate, formatter).atStartOfDay();
        builder.setBirthDate(birthDate);

        information = builder.build();

        User<?> user = UserFactory.factory(userType);
        assert user != null;
        user.setUsername(username);
        user.setExperience(experience);
        user.setInformation(information);
        user.setUserType(userType);
        user.setNotifications(notifications);
        user.setFavoriteActors(favoriteActors);
        user.setFavoriteProductions(favoriteProductions);

        if (user instanceof Staff) {
            ((Staff<?>) user).setActorsContribution(new TreeSet<>());
            ((Staff<?>) user).setProductionsContribution(new TreeSet<>());
            ((Staff<?>) user).setRequests(new ArrayList<>());
        }

        IMDB.getInstance().getUsers().add(user);
    }

    public String generateUniqueUsername(String name) {
        String username = name.toLowerCase().replaceAll("\\s", "_");

        int number = generateUniqueNumber();

        return username + "_" + number;
    }
    public int generateUniqueNumber() {
        Random random = new Random();
        return random.nextInt(1000);
    }

    public String generateStrongPassword() {
        String caracterePermise = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        StringBuilder parolaGenerata = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(caracterePermise.length());
            parolaGenerata.append(caracterePermise.charAt(index));
        }

        return parolaGenerata.toString();
    }

    public void removeUser(User<T> user) {

        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production p : productions) {
            List<Rating> ratings = p.getRatings();
            if(ratings.isEmpty())
                continue;
            for (Rating r : ratings) {
                if (r.getUsername().equals(user.getUsername())) {
                    p.deleteRating(r);
                }
            }
        }
        List<Request> requests = IMDB.getInstance().getRequests();
        for (Request r : requests) {
            if(r.getRequesterUsername().equals(user.getUsername())) {
                requests.remove(r);
            }

        }
        IMDB.getInstance().getUsers().remove(user);

        if (user.getUserType() == AccountType.Contributor) {
            Contributor<T> contributorUser = (Contributor<T>) user;
            SortedSet<String> actorsContribution = contributorUser.getActorsContribution();
            SortedSet<String> productionsContribution = contributorUser.getProductionsContribution();
            List<User> users = IMDB.getInstance().getUsers();
            for (User u : users) {
                if(u.getUserType() == AccountType.Admin) {
                    Admin<?> adminUser = (Admin<?>) u;
                    adminUser.getActorsContribution().addAll(actorsContribution);
                    adminUser.getProductionsContribution().addAll(productionsContribution);
                }
            }
        }
    }
}