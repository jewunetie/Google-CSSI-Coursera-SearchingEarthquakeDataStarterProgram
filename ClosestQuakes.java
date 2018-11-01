
/**
 * Find N-closest quakes
 * 
 * @author Duke Software/Learn to Program
 * @version 1.0, November 2015
 */

import java.util.*;

public class ClosestQuakes {
    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, int howMany) {
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        
        
        if (howMany > quakeData.size()){
            howMany = quakeData.size();
        }
        
        ArrayList<QuakeEntry> copyQD = new ArrayList<QuakeEntry>(quakeData);
        QuakeEntry leastDist = copyQD.get(0);
        double distMin = current.distanceTo(leastDist.getLocation());
        for(int i = 0; i < howMany; i++){
            for(QuakeEntry qe : copyQD){
                if(current.distanceTo(qe.getLocation()) < distMin){
                    distMin = current.distanceTo(qe.getLocation());
                    leastDist = qe;
                }
            }
            ret.add(leastDist);
            copyQD.remove(leastDist);
        }
        

        return ret;
    }

    public void findClosestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());

        Location jakarta  = new Location(-6.211,106.845);

        ArrayList<QuakeEntry> close = getClosest(list,jakarta,10);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }
    
}
