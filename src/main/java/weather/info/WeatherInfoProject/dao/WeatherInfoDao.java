package weather.info.WeatherInfoProject.dao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import weather.info.WeatherInfoProject.entity.WeatherInfo;

public interface WeatherInfoDao extends JpaRepository<WeatherInfo, Long> {
	Optional<WeatherInfo> findByPincodeAndDate(String pincode, LocalDate date);
}
