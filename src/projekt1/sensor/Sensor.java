package projekt1.sensor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import projekt1.medianwrapper.MedianWrapper;
import projekt1.readout.ReadOut;

public class Sensor {

    private final String name;
    private final ArrayList<ReadOut> data;

    public Sensor(String name) {
        this.data = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLengthOfData() {
        return data.size();
    }

    public void addReadout(ReadOut readout) {
        data.add(readout);
    }

    public Optional<ReadOut> getMax() {
        return data.stream().max(ReadOut::compareTo);
    }

    public Optional<ReadOut> getMin() {
        return data.stream().min(ReadOut::compareTo);
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
        if (data.size() % 2 == 0) {
            ReadOut elem1 = data.get(data.size() / 2);
            ReadOut elem2 = data.get(data.size() / 2 - 1);
            return new MedianWrapper(elem1, elem2);
        } else {
            ReadOut elem = data.get(data.size() / 2);
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
