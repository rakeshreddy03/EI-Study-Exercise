// Subject (News Feed)
import java.util.*;

interface NewsFeed {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

// Concrete Subject
class NewsChannel implements NewsFeed {
    private List<Observer> observers = new ArrayList<>();
    private String news;

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);

    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(news);
        }
    }

    public void setNews(String news) {
        this.news = news;
        notifyObservers();
    }
}

// Observer
interface Observer {
    void update(String news);
}

// Concrete Observer
class User implements Observer {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String news) {
        System.out.println(name + " received news: " + news);
    }
}

public class ObserverPatternDemo {
    public static void main(String[] args) {
        NewsChannel channel = new NewsChannel();
        User user1 = new User("A");
        User user2 = new User("B");

        channel.registerObserver(user1);
        channel.registerObserver(user2);

        channel.setNews("Ei Study is hiring in Amrita University.");
    }
}
