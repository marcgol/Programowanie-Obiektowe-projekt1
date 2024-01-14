package projekt1.medianwrapper;

import java.util.ArrayList;
import projekt1.readout.ReadOut;

public class MedianWrapper {
    private final ArrayList<ReadOut> medianElems;
    private final double medianVal;
    
    //the case of the odd length
    public MedianWrapper(ReadOut elem) {
        this.medianElems = new ArrayList<>();
        medianElems.add(elem);
        medianVal=elem.getValue();
    }
    
    //the case of the even length
    public MedianWrapper(ReadOut elem1, ReadOut elem2) {
        this.medianElems = new ArrayList<>();
        if (elem1.getValue() < elem2.getValue()) { //sort the elements
        medianElems.add(elem1);
        medianElems.add(elem2);
        } else {
        medianElems.add(elem2);
        medianElems.add(elem1);
        }
        medianVal=(elem1.getValue()+elem2.getValue())/2;
    }
    @Override
    public String toString(){
        String medianStr =String.format("%.3f source: ", medianVal);
        medianStr+=(medianElems.size()>1)?
        medianElems.get(0)+"::"+medianElems.get(1):
        medianElems.get(0).toString();
        return medianStr;
    }
}