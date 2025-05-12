package model;

import java.util.Comparator;

// used to sort the applications by status in alphabetical order
public class SortByStatus implements Comparator<Application> {

    @Override
    public int compare(Application o1, Application o2) {
        return o1.getStatus().name().compareToIgnoreCase(o2.getStatus().name());
    }
    
}
