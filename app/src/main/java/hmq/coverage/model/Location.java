package hmq.coverage.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexanderHammond on 2/27/19.
 */

public class Location {
    String lid;
    String name;
    List<User> checkedIn;
    List<Request> requests;

    public Location(String id, String n, List<User> u, List<Request> r) {
        lid = id;
        name = n;
        checkedIn = u;
        requests = r;
    }

    public Location(String n) {
        this("", n, new ArrayList<User>(), new ArrayList<Request>());
    }

    public Location() {
        this("", "", new ArrayList<User>(), new ArrayList<Request>());
    }

    public String getLid() {
        return lid;
    }
    //Eventually add more as project progresses, initially can just be a name.

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return lid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Location) {
                if (((Location) obj).getLid().equals(this.getLid())) {
                    return true;
                }
            }
        }
        return false;
    }
}
