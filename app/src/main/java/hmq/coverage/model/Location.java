package hmq.coverage.model;

import java.util.ArrayList;

/**
 * Created by AlexanderHammond on 2/27/19.
 */

public class Location {
    String lid;
    String name;
    ArrayList<User> checkedIn;
    ArrayList<Request> requests;

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
