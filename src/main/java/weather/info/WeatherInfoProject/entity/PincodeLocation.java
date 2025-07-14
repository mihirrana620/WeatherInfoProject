package weather.info.WeatherInfoProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PincodeLocation {

	@Id
	private String pincode;
	private double latitude;
	private double longitude;
}
