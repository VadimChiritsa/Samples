package tours.params;

/**
 * Created by Aliaksandr_Sheliutsi on 1/18/2017.
 */
public class NoSuchTransportTypeException extends Exception {
    public NoSuchTransportTypeException(String message){
        super(message);
    }
}
