package utility;
@SuppressWarnings("serial")
public class ScriptExecutionException extends RuntimeException{
	public ScriptExecutionException(String message) {
        super(message);
        Log.error(message);
    }
	public ScriptExecutionException(String message, Exception cause) {
        super(message, cause);
        Log.error(message);
        
    }
}
