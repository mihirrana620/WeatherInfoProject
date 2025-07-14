package weather.info.WeatherInfoProject.Controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import weather.info.WeatherInfoProject.entity.WeatherInfo;
import weather.info.WeatherInfoProject.service.WeatherService;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherInfoController {
  
	 private final WeatherService weatherService;
	 
	 @GetMapping
	    public ResponseEntity<WeatherInfo> getWeather(
	            @RequestParam String pincode,
	            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate for_date) {

	        WeatherInfo weather = weatherService.getWeather(pincode, for_date);
	        return ResponseEntity.ok(weather);
	    }
}
