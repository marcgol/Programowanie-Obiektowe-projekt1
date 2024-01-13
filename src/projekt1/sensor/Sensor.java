package projekt1.sensor;

import java.util.ArrayList;
import java.util.Collections;
import projekt1.medianwrapper.MedianWrapper;
import projekt1.readout.ReadOut;

public class Sensor {
    
    private String name;
    private ArrayList<ReadOut> data=new ArrayList<>();
    
    public Sensor(String name){
        this.name=name;
    }
    
    public String getName() {
        return name;
    }
    
    public int getLengthOfData(){
        return data.size();
    }
    
    public void addReadout(ReadOut readout){
        data.add(readout);  
    }
    public ReadOut getMax() {
        return Collections.max(data);
    }

    public ReadOut getMin() {
        return Collections.min(data);
    }

    public double getMean() {
        double sum = 0;
        for (ReadOut num : data) {
            sum += num.getValue();
        }
        return sum / data.size();
    }

    public MedianWrapper getMedian() {
        Collections.sort(data);
        int size = data.size();
        if (size % 2 == 0) {
            ReadOut elem1 = data.get(size / 2);
            ReadOut elem2 = data.get(size / 2 - 1);
            return new MedianWrapper(elem1, elem2);
        } else {
            ReadOut elem = data.get(size / 2);
            return new MedianWrapper(elem);
        }
    }

    public int noOfCentralElements(double mean, double eps) {
        int count = 0;
        for (ReadOut num : data) {
            if (Math.abs(num.getValue() - mean) < eps) {
                count++;
            }
        }
        return count;
    }


}
