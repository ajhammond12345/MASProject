package hmq.coverage.model;

/**
 * Created by AlexanderHammond on 2/27/19.
 * @author Stuart Malone
 */

public class User {
    String uid;
    String fname;
    String lname;
    String phone;
    String email;
    //TODO Add list of requests the user has posted
    //TODO Add list of requests the user is covering

    public User(String id, String f, String l, String e, String p) {
        uid = id;
        fname = f;
        lname = l;
        email = e;
        phone = p;
    }

    public User(String id, String e) {
        this(id, "", "", e, "");
    }

    public User(String e) {
        this("", "", "", e, "");
    }

    public User() {
        this("", "", "", "", "");
    }




    public void updateUser(String id, String f, String l, String e) {
        uid = id;
        fname = f;
        lname = l;
        email = e;
    }

    public String getUid() {
        return uid;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() { return lname; }

    public String getEmail() {
        return email;
    }

    public String getPhone() { return phone; }

    public String getName() {
        String name = "";
        if (!fname.equals("")) {
            name = name + fname;
            if (!lname.equals("")) {
                name = name + " ";
            }
        }
        if (!lname.equals("")) {
            name = name + lname;
        }
        if (name.equals("")) {
            return "Unnamed";
        } else {
            return name;
        }
    }

    public String toString() {
        return getName();
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
