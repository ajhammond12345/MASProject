package hmq.coverage.model;

/**
 * Created by AlexanderHammond on 2/27/19.
 */

public class User {
    String uid;
    String fname;
    String lname;
    String email;

    public String getUid() {
        return uid;
    }

    public String toString() {
        return fname + " " + lname;
    }
}