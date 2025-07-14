package weather.info.WeatherInfoProject.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import weather.info.WeatherInfoProject.entity.PincodeLocation;




public interface PincodeLocationDao extends JpaRepository<PincodeLocation, String> {}


