//Attribution : w3 schools java advanced sorting

package model;

import java.util.Comparator;

// used to sort the applications by company name in alphabetical order 
public class SortByCompanyName implements Comparator<Application> {
    
    @Override
    public int compare(Application o1, Application o2) {
        return o1.getCompanyName().compareToIgnoreCase(o2.getCompanyName());
    }

}