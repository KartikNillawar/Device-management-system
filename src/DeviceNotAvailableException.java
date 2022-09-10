import java.util.concurrent.ExecutionException;

public class DeviceNotAvailableException extends Exception {
	DeviceNotAvailableException(String err) {
		super(err);
	}
}