package tours.params;

/**
 * Created by Aliaksandr_Sheliutsi on 1/18/2017.
 */
public class NoSuchTourTypeException extends Exception {
    public NoSuchTourTypeException(String message){
        super(message);
    }
}
