package firebase_demo.fg.com.android_firebase_demo;

/**
 * Created by floriangabach on 07/12/2016.
 */

public class User {
    private String id;
    private String name;

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getId().toString() + " : " +  this.getName().toString();
    }
}
