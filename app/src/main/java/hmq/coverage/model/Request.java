package hmq.coverage.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AlexanderHammond on 2/27/19.
 */

public class Request {
    String rid;

    User requester;
    String date;
    String time;
    String location_id;
    String amount;

    User finalCover;
    ArrayList<User> potentialCoverage;

    public Request(String id, User r, String d, String t, String l, String a, User f, List<User> p) {
        rid = id;
        requester = r;
        date = d;
        time = t;
        location_id = l;
        amount = a;
        finalCover = f;
        potentialCoverage = new ArrayList<>(p);
    }

    public Request(User r, String d, String t, String l, String a) {
        this("", r, d, t, l, a, null, new ArrayList<User>());
    }

    public Request() {
        this("", null, "", "", "", "", null, new ArrayList<User>());
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String newID) {
        rid = newID;
    }

    public String getLocationID() {
        return location_id;
    }

    public void setLocationID(String loc_id) {
        location_id = loc_id;
    }

    @Override
    public String toString() {
        String name = requester != null ? requester.fname + " " + requester.lname: "Unnamed";
        return "Request By: " + name + " on " + date + " at " + time + " for $" + amount + ".";
    }

    @Override
    public int hashCode() {
        return rid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Request) {
                if (((Request) obj).getRid().equals(this.getRid())) {
                    return true;
                }
            }
        }
        return false;
    }
}
