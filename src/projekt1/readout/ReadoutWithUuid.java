package projekt1.readout;

public class ReadoutWithUuid extends ReadOut{
    private String uuid;
    
    public ReadoutWithUuid(double value, String uuid){
        super(value);
        this.uuid = uuid;
    }
    
    @Override
    public String toString() {
    return String.format("%.3f", getValue())+" ["+uuid+"]";
    }

    
}
