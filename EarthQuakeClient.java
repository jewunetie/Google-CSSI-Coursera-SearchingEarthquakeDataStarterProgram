import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData){
            if(qe.getMagnitude() > magMin){
                answer.add(qe);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData){
            Location loc = qe.getLocation();
            if(loc.distanceTo(from) < distMax){
                answer.add(qe);
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
    double minDepth,
    double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData){
            double depth = qe.getDepth();
            if((minDepth < depth) && (depth < maxDepth)){
                answer.add(qe);
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,
    String where,
    String phrase) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData){
            String title = qe.getInfo();
            if(where.equals("start")){
                if(title.startsWith(phrase)){
                    answer.add(qe);
                }
            } else if (where.equals("end")){
                if(title.endsWith(phrase)){
                    answer.add(qe);
                }
            } else if (where.equals("any")){
                if((title.indexOf(phrase)) != -1){
                    answer.add(qe);
                }
            }
        }
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        
        double magMin = 5.0;
        ArrayList<QuakeEntry> listBig = filterByMagnitude(list, magMin);
        for(QuakeEntry qe : listBig){
            System.out.println(qe);
        }
        System.out.println("Found " + listBig.size() + " quakes that match that criteria");

    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+ list.size() +" quakes");

        // This location is Durham, NC
        Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
        // Location city =  new Location(38.17, -118.82);
        
        double distMax = 1000;
        ArrayList<QuakeEntry> listClose = filterByDistanceFrom(list, distMax, city);
        for(QuakeEntry qe : listClose){
            System.out.println(qe);
        }
        System.out.println("Found " + listClose.size() + " quakes that match that criteria");
    }
    
    public void quakeOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+ list.size() +" quakes");
        
        double minDepth = -4000.0;
        double maxDepth = -2000.0;
        ArrayList<QuakeEntry> listDepth = filterByDepth(list, minDepth, maxDepth);
        for(QuakeEntry qe : listDepth){
            System.out.println(qe);
        }
        System.out.println("read data for "+ list.size() +" quakes");
        System.out.println("Found " + listDepth.size() + " quakes that match that criteria");

    }
    
    public void quakeByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        
        String where = "any";
        String phrase = "Can";
        ArrayList<QuakeEntry> listPhrase = filterByPhrase(list, where, phrase);
        for(QuakeEntry qe : listPhrase){
            System.out.println(qe);
        }
        System.out.println("read data for "+ list.size() +" quakes");
        System.out.println("Found " + listPhrase.size() + " quakes that match that criteria");

    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
