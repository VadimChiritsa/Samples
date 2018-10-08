package tours.params;

/**
 * Created by Aliaksandr_Sheliutsi on 1/18/2017.
 */
public class NoSuchMealTypeException extends Exception {
    public NoSuchMealTypeException(String message){
        super(message);
    }
}
