package projekt1.iohelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import projekt1.filecontent.FileContent;
import projekt1.logger.Logger;
import projekt1.readout.ReadOut;
import projekt1.readout.ReadoutWithUuid;
import projekt1.sensor.Sensor;

public class IOHelper {

    static String delimiter1 = " ";
    static String delimiter2 = " id:";

    public static FileContent readFile(String filePath, Logger logger) throws IOException {
        Sensor dummySensor = new Sensor("<N/A>");
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add(dummySensor);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int noOfInvalidRecords = 0;
        String line;
        String uuid;
        while ((line = reader.readLine()) != null) {
            try {
                if (line.contains("id:")) {
                    line = line.replaceAll(delimiter2, delimiter1);
                    String[] parts = line.split(delimiter1, 3);
                    uuid = parts[1].trim();
                    double value = Double.parseDouble(parts[0].trim());
                    ReadoutWithUuid readoutWithUuid = new ReadoutWithUuid(value, uuid);
                    if (parts.length > 2) {
                        addReadOutToSensor(sensors, parts[2], readoutWithUuid);
                    } else {
                        dummySensor.addReadout(readoutWithUuid);
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
                return;
            }
        }

        Sensor newSensor = new Sensor(sensorName);
        newSensor.addReadout(readout);
        sensorList.add(newSensor);
    }

    public static String getOutputInfo(FileContent fContent, String title) {
        StringBuilder output = new StringBuilder();
        output.append("Marcel Golab, 285300\n\n");        
        output.append(title).append("\n");
        for (Sensor sensor : fContent.getSensors()) {
            double mean = sensor.getMean();
            Optional<ReadOut> max = sensor.getMax();
            Optional<ReadOut> min = sensor.getMin();
            output.append("--------------------------------\n");
            output.append("Sensor name: ").append(sensor.getName());
            output.append("\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");
            output.append("Length of the series: ").append(String.format("%d", sensor.getLengthOfData())).append("\n");
            max.ifPresent(readOut -> output.append("Max value: ").append(readOut).append("\n"));
            min.ifPresent(readOut -> output.append("Min value: ").append(readOut).append("\n"));
            output.append("Mean value: ").append(String.format("%.3f", mean)).append("\n");
            output.append("Median: ").append(sensor.getMedian().toString()).append("\n");
            if (max.isPresent() && min.isPresent()) {
                output.append("Number of central elements: ").append(String.format("%d", sensor.noOfCentralElements(mean, (max.get().getValue() - min.get().getValue()) / 100))).append("\n");
            }
        }
        output.append("--------------------------------\n");
        output.append("Number of invalid records: ").append(String.format("%d", fContent.getNoOfInvalidRecords())).append("\n");
        return output.toString();
    }
}
