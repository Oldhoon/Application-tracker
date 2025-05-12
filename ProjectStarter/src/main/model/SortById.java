//Attribution : w3 schools java advanced sorting

package model;

import java.util.Comparator;

// used to sort the applications by ID; order they were added to the manager 
public class SortById implements Comparator<Application> {
    @Override
    public int compare(Application o1, Application o2) {

        if (o1.getId() < o2.getId()) {
            return -1; 
        } 
        if (o1.getId() > o2.getId()) {
            return 1;
        } 
        return 0; // both have the same id
    }


}
