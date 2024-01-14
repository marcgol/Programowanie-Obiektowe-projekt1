package projekt1.iohelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import projekt1.filecontent.FileContent;
import projekt1.logger.Logger;
import projekt1.readout.ReadOut;
import projekt1.readout.ReadoutWithUuid;
import projekt1.sensor.Sensor;

public class IOHelper {
            
    public static FileContent readFile(String filePath, Logger logger) throws IOException {
        Sensor dummySensor=new Sensor("<N/A>");
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add(dummySensor);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int noOfInvalidRecords = 0;
        String line;
        String uuid;
        while ((line = reader.readLine()) != null) {
            try {
                if (line.contains("id:")) {
                    String delimiter1 = " ";
                    String delimiter2 = " id:";
                    line = line.replaceAll(delimiter2, delimiter1);
                    String[] parts = line.split(delimiter1, 3);
                    uuid = parts[1].trim();
                    double value = Double.parseDouble(parts[0].trim());
                    ReadoutWithUuid readoutuuid = new ReadoutWithUuid(value, uuid);
                    if (parts.length > 2) {
                        addReadOutToSensor(sensors, parts[2], readoutuuid);
                    } else {
                        dummySensor.addReadout(readoutuuid);
                    }
                } else {
                    ReadOut rdata = new ReadOut(Double.parseDouble(line));
                    dummySensor.addReadout(rdata);
                }
            } catch (NumberFormatException ex) {
                logger.log("Faulty record in [" + filePath + "]: " + line);
                noOfInvalidRecords++;
            }
        }
        reader.close();
        return new FileContent(sensors, noOfInvalidRecords);
    }
    
    public static void addReadOutToSensor(ArrayList<Sensor> sensorList, String sensorName, ReadOut readout) {
        for (Sensor sensor : sensorList) {
            if (sensor.getName().equals(sensorName)) {
                sensor.addReadout(readout);
            } else {
                Sensor newSensor = new Sensor(sensorName);
                newSensor.addReadout(readout);
                sensorList.add(newSensor);                
            }
        }
    }
        
    public static String getOutputInfo(FileContent fContent, String title) {
        ArrayList<Sensor> sensors = fContent.getSensors();
        String output = "";
        for (Sensor sensor: fContent.getSensors()){
            double mean = sensor.getMean();
            ReadOut max = sensor.getMax();
            ReadOut min = sensor.getMin();
            output += title + "\n";
            output += "--------------------------------\n";
            output += "Sensor name: " + sensor.getName();
            output += "\n--------------------------------\n";            
            output += "Marcel Golab, 285300\n";
            output += "Length of the series: " + String.format("%d", sensor.getLengthOfData()) + "\n";
            output += "Max value: " + max.toString() + "\n";
            output += "Min value: " + min.toString() + "\n";
            output += "Mean value: " + String.format("%.3f", mean) + "\n";
            output += "Median: " + sensor.getMedian().toString() + "\n";
            output += "Number of central elements: " + String.format("%d", sensor.noOfCentralElements(mean, (max.getValue() - min.getValue()) / 100)) + "\n";
        }       
        output += "Number of invalid records: " + String.format("%d", fContent.getNoOfInvalidRecords()) + "\n";
        return output;
    }
}
