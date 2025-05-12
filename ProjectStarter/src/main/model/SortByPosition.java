//Attribution : w3 schools java advanced sorting

package model;

import java.util.Comparator;

// used to sort the application by the positions in alphabetical order 
public class SortByPosition implements Comparator<Application> {

    @Override
    public int compare(Application o1, Application o2) {
        return o1.getPosition().compareToIgnoreCase(o2.getPosition());
    }
    

}
