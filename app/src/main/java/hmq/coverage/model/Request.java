package hmq.coverage.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AlexanderHammond on 2/27/19.
 */

public class Request {
    String rid;
    User requester;
    User finalCover;
    Date date;
    Location location;
    ArrayList<User> potentialCoverage;

    public String getRid() {
        return rid;
    }

    @Override
    public String toString() {
        return requester.toString();
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
            if (obj instanceof Location) {
                if (((Request) obj).getRid().equals(this.getRid())) {
                    return true;
                }
            }
        }
        return false;
    }
}
