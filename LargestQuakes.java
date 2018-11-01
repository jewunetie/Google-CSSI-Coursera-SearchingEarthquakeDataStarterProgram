import java.util.*;

/**
 * Write a description of LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LargestQuakes {
    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "data/http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        //String source ="data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        /*
        for(QuakeEntry qe : list){
            System.out.println(qe);
        }
        System.out.println("read data for "+list.size());
        */
        int howMany = 50;
        ArrayList<QuakeEntry> listMag = getLargest(list, howMany);
        for (QuakeEntry qe : listMag){
            System.out.println(qe);
        }
    }
    
    public int indexOfLargest(ArrayList<QuakeEntry> data){
        int index = 0;
        for(int i = 0; i < data.size(); i++){
            if(data.get(index).getMagnitude() < data.get(i).getMagnitude()){
                index = i;
            }
        }
        return index;
    }
    
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        
        if (howMany > quakeData.size()){
            howMany = quakeData.size();
        }
        
        ArrayList<QuakeEntry> copyQD = new ArrayList<QuakeEntry>(quakeData);
        QuakeEntry bigQuake = copyQD.get(0);
        double maxMag = 0;
        for(int i = 0; i < howMany; i++){
            int index = indexOfLargest(copyQD);
            answer.add(copyQD.get(index));
            copyQD.remove(index);
        }
        
        return answer;
    }

}
