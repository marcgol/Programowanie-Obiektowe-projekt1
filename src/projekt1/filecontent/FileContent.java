package projekt1.filecontent;

import java.util.ArrayList;
import java.util.List;

import projekt1.sensor.Sensor;

public class FileContent {
    private final List<Sensor> sensors;
    private final int noOfInvalidRecords;

    public FileContent(List<Sensor> sensors, int noOfInvalidRecords) {
        this.sensors = sensors;
        this.noOfInvalidRecords = noOfInvalidRecords;
    }

    public List<Sensor> getSensors() {
        return sensors.stream().filter(s -> s.getLengthOfData() != 0).toList();
    }

    public int getNoOfInvalidRecords() {
        return noOfInvalidRecords;
    }

}
