package org.example;

import javax.print.DocFlavor;
import java.util.*;

public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface {
    private List<Request> requests;
    private SortedSet<String> productionsContribution;
    private SortedSet<String> actorsContribution;
    public void setProductionsContribution(SortedSet<String> productionsContribution) {
        this.productionsContribution = productionsContribution;
    }

    public void setActorsContribution(SortedSet<String> actorsContribution) {
        this.actorsContribution = actorsContribution;
    }
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
    public List<Request> getRequests() {
        return requests;
    }

    public SortedSet<String> getProductionsContribution() {
        return productionsContribution;
    }

    public SortedSet<String> getActorsContribution() {
        return actorsContribution;
    }

    public Staff(String username, int experience, Information information,
                 AccountType userType, SortedSet<String> favoriteProductions,
                 SortedSet<String> favoriteActors, SortedSet<String> productionsContribution,
                 SortedSet<String> actorsContribution, List<String> notifications) {
        super(username, experience, information, userType, favoriteProductions,
                favoriteActors, notifications);
        this.actorsContribution = actorsContribution;
        this.productionsContribution = productionsContribution;
        this.requests = new ArrayList<>();
    }
    public Staff() {
    // for User factory
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());

        if (productionsContribution != null) {
            stringBuilder.append("Productions Contribution: ").append(productionsContribution).append("\n");
        }

        if (actorsContribution != null) {
            stringBuilder.append("Actors Contribution: ").append(actorsContribution).append("\n");
        }

        if (getNotifications() != null) {
            stringBuilder.append("Notifications: ").append(getNotifications()).append("\n");
        }

        return stringBuilder.toString();
    }

    public void addProductionSystem(Production p) {
        IMDB.getInstance().getProductions().add(p);
        productionsContribution.add(p.getTitle());
    }

    public void addActorSystem(Actor a) {
        IMDB.getInstance().getActors().add(a);
        actorsContribution.add(a.getActorName());
    }

   public void removeProductionSystem(String name) {
        Production p = findProductionByName(name);
        IMDB.getInstance().getProductions().remove(p);
        productionsContribution.remove(name);
    }

    public void removeActorSystem(String name) {
        Actor a = findActorByName(name);
        IMDB.getInstance().getActors().remove(a);
        actorsContribution.remove(name);
    }

    public void updateProduction(Production p) {
        removeProductionSystem(p.getTitle());
        addProductionSystem(p);
    }

    public void updateActor(Actor a) {
        removeActorSystem(a.getActorName());
        addActorSystem(a);
    }

    public void resolveUserRequests() {
        if(requests.isEmpty()) {
            System.out.println("No requests to solve");
            return;
        }
        for(Request r : requests) {
            System.out.println(r);
            System.out.println("Would you like to solve this request?");
            System.out.println("\t1. Yes");
            System.out.println("\t2. No");
            Scanner scanner = new Scanner(System.in);
            int nr = scanner.nextInt();

            if (nr == 1) {
                if (r.getType().equals(RequestType.ACTOR_ISSUE) ||
                        r.getType().equals(RequestType.MOVIE_ISSUE)) {
                    updateExperience(new StrategyForIssue(), r.getRequesterUsername());
                } else {
                    System.out.println("Do you want to give experience?");
                    System.out.println("\t1. Yes");
                    System.out.println("\t2. No");
                    Scanner experienceScanner = new Scanner(System.in);
                    int choice = experienceScanner.nextInt();
                    if (choice == 1) {
                        updateExperience(new StrategyForNewMovieActor(), r.getRequesterUsername());
                    }
                }

            }
            requests.remove(r);

        }
        if (IMDB.getInstance().getLoggedUser() instanceof Admin<?>) {
            System.out.println("Do you want to solve the requests for all admins?");
            System.out.println("\t1. Yes");
            System.out.println("\t2. No");
            Scanner scanner = new Scanner(System.in);
            int nr = scanner.nextInt();
            if (nr == 1) {
                for (Request r : IMDB.RequestsHolder.requests) {
                    System.out.println(r);
                    System.out.println("Would you like to solve this request?");
                    System.out.println("\t1. Yes");
                    System.out.println("\t2. No");
                    Scanner adminScanner = new Scanner(System.in);
                    int n = adminScanner.nextInt();

                    if (n == 1) {
                        if (r.getType().equals(RequestType.ACTOR_ISSUE) ||
                                r.getType().equals(RequestType.MOVIE_ISSUE)) {
                            updateExperience(new StrategyForIssue(), r.getRequesterUsername());
                        } else {
                            System.out.println("Do you want to give experience?");
                            System.out.println("\t1. Yes");
                            System.out.println("\t2. No");
                            Scanner experienceScanner = new Scanner(System.in);
                            int choice = experienceScanner.nextInt();
                            if (choice == 1) {
                                updateExperience(new StrategyForNewMovieActor(), r.getRequesterUsername());
                            }
                        }

                    }
                    requests.remove(r);
                }

            }
        }
    }


    private Actor findActorByName(String name) {
        List<Actor> actors = IMDB.getInstance().getActors();
        for (Actor actor : actors) {
            if (actor.getActorName().equals(name)) {
                return actor;
            }
        }
        return null;
    }

    private Production findProductionByName(String name) {
        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                return production;
            }
        }
        return null;
    }
}
