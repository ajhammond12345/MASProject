package hmq.coverage.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Hammond
 */

public class Request {
    String rid;

    String uid;
    String date;
    String time;
    String locationID;
    String amount;

    String finalCover;
    ArrayList<String> potentialCoverage;

    public Request(String id, String r, String d, String t, String l, String a, String f, List<String> p) {
        rid = id;
        uid = r;
        date = d;
        time = t;
        locationID = l;
        amount = a;
        finalCover = f;
        potentialCoverage = new ArrayList<>(p);
    }

    public Request(String r, String d, String t, String l, String a) {
        this("", r, d, t, l, a, "", new ArrayList<String>());
    }

    public Request() {
        this("", null, "", "", "", "", "", new ArrayList<String>());
    }

    public String getUid() {
        return uid;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAmount() {
        return amount;
    }


    public String getRid() {
        return rid;
    }

    public void setRid(String newID) {
        rid = newID;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String loc_id) {
        locationID = loc_id;
    }

    public void setFinalCover(String id) {
        finalCover = id;
    }

    @Override
    public String toString() {
        return "Request on " + date + " at " + time + " for $" + amount + ".";
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
