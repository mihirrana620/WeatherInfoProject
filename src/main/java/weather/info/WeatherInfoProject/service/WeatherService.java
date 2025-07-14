package weather.info.WeatherInfoProject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import weather.info.WeatherInfoProject.Exception.InvalidDateException;
import weather.info.WeatherInfoProject.Exception.InvalidPincodeException;
import weather.info.WeatherInfoProject.dao.PincodeLocationDao;
import weather.info.WeatherInfoProject.dao.WeatherInfoDao;
import weather.info.WeatherInfoProject.entity.PincodeLocation;
import weather.info.WeatherInfoProject.entity.WeatherInfo;

@Service
@RequiredArgsConstructor
public class WeatherService {

	private final PincodeLocationDao locationRepo;
	private final WeatherInfoDao weatherRepo;
	private final RestTemplate restTemplate;
	private final Environment env;

	public WeatherInfo getWeather(String pincode, LocalDate date) {
		validateInputs(pincode, date);
		return weatherRepo.findByPincodeAndDate(pincode, date).orElseGet(() -> {

			PincodeLocation location = locationRepo.findById(pincode).orElseGet(() -> {
				String geoUrl = String.format("%s?zip=%s,IN&appid=%s", env.getProperty("openweather.geo.url"), pincode,
						env.getProperty("openweather.api.key"));

				ResponseEntity<Map> response = restTemplate.getForEntity(geoUrl, Map.class);
				Map body = response.getBody();

				double lat = ((Number) body.get("lat")).doubleValue();
				double lon = ((Number) body.get("lon")).doubleValue();

				PincodeLocation loc = new PincodeLocation(pincode, lat, lon);
				return locationRepo.save(loc);
			});

			String weatherUrl = String.format("%s?lat=%s&lon=%s&appid=%s&units=metric",
					env.getProperty("openweather.weather.url"), location.getLatitude(), location.getLongitude(),
					env.getProperty("openweather.api.key"));

			ResponseEntity<Map> response = restTemplate.getForEntity(weatherUrl, Map.class);
			Map body = response.getBody();

			String description = ((Map) ((List) body.get("weather")).get(0)).get("description").toString();
			double temp = ((Map<String, Number>) body.get("main")).get("temp").doubleValue();
			double wind = ((Map<String, Number>) body.get("wind")).get("speed").doubleValue();

			WeatherInfo info = new WeatherInfo(null, pincode, date, description, temp, wind);
			return weatherRepo.save(info);
		});
	}

	private void validateInputs(String pincode, LocalDate date) {

		if (!pincode.matches("^[1-9][0-9]{5}$")) {
			throw new InvalidPincodeException(pincode + " is not a valid Indian pincode.");
		}

		if (date.isAfter(LocalDate.now())) {
			throw new InvalidDateException("Date " + date + " is in the future. Only historical data allowed.");
		}
	}
}
