package hmq.coverage;

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
}
