package hmq.coverage;

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
}
