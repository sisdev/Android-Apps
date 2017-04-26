package hcl.in.mylistapp;

/**
 * Created by Dell on 07-09-2016.
 */
public class Emp {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String location ;

    public Emp(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
