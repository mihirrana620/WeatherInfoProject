package weather.info.WeatherInfoProject.Exception;

public class InvalidPincodeException extends RuntimeException {
    public InvalidPincodeException(String message) {
        super(message);
    }
}