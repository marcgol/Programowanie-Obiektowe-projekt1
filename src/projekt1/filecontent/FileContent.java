package projekt1.filecontent;

import java.util.ArrayList;
import projekt1.sensor.Sensor;

public class FileContent {
    private ArrayList<Sensor> sensors;
    private int noOfInvalidRecords;
    
    public FileContent(ArrayList<Sensor> sensors, int noOfInvalidRecords){
        this.sensors=sensors;
        this.noOfInvalidRecords=noOfInvalidRecords;
    }
    public ArrayList<Sensor> getSensors() {
        return sensors;
    }
    public int getNoOfInvalidRecords() {
        return noOfInvalidRecords;
    }

}
