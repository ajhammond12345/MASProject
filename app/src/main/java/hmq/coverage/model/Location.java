package hmq.coverage.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AlexanderHammond on 2/27/19.
 */

public class Location {
    String lid;
    String name;
    Map<String, User> checkedIn;
    Map<String, Request> requests;

    public Location(String id, String n, Map<String, User> u, Map<String, Request> r) {
        lid = id;
        name = n;
        checkedIn = u;
        requests = r;
    }

    public Location(String n) {
        this("", n, new HashMap<String, User>(), new HashMap<String, Request>());
    }

    public Location() {
        this("", "", new HashMap<String, User>(), new HashMap<String, Request>());
    }

    public String getLid() {
        return lid;
    }
    //Eventually add more as project progresses, initially can just be a name.

    public List<Request> retrieveRequestsList() {
        return new ArrayList<Request>(requests.values());
    }

    public List<User> retrieveCheckedIn() {
        return new ArrayList<>(checkedIn.values());
    }
    public void addRequest(Request request) {
        requests.put(request.rid, request);
    }

    public void removeRequest(Request request) {
        requests.remove(request);
    }


    public void checkIn(User user) {
        checkedIn.put(user.uid, user);
    }

    public void checkOut(User user) {
        checkedIn.remove(user.uid);
    }

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
