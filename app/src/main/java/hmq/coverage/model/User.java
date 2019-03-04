package hmq.coverage.model;

/**
 * Created by AlexanderHammond on 2/27/19.
 */

public class User {
    String uid;
    String fname;
    String lname;
    String email;

    public User(String id, String f, String l, String e) {
        uid = id;
        fname = f;
        lname = l;
        email = e;
    }

    public User(String id, String e) {
        this(id, "", "", e);
    }

    public User(String e) {
        this("", "", "", e);
    }

    public User() {
        this("", "", "", "");
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String toString() {
        return email;
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof User) {
                if (((User) obj).getEmail().equals(this.getEmail())) {
                    return true;
                }
            }
        }
        return false;
    }
}
