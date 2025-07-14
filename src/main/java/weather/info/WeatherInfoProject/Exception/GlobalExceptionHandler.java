package weather.info.WeatherInfoProject.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
	 @ExceptionHandler(InvalidPincodeException.class)
	    public ResponseEntity<String> handleInvalidPincode(InvalidPincodeException ex) {
	        return ResponseEntity.badRequest().body("Invalid Pincode: " + ex.getMessage());
	    }

	    @ExceptionHandler(InvalidDateException.class)
	    public ResponseEntity<String> handleInvalidDate(InvalidDateException ex) {
	        return ResponseEntity.badRequest().body("Invalid Date: " + ex.getMessage());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGeneric(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
	    }
}
