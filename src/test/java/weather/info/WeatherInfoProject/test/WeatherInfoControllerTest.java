package weather.info.WeatherInfoProject.test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherInfoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetWeather() throws Exception {
		mockMvc.perform(get("/weather").param("pincode", "395005").param("for_date", "2021-10-15"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.pincode").value("395005"));
	}

	@Test
	void testInvalidDate() throws Exception {
		String futureDate = LocalDate.now().plusDays(1).toString();

		mockMvc.perform(get("/weather").param("pincode", "395005").param("for_date", futureDate))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("Invalid Date")));
	}

	@Test
	void tesrInvalidPincode() throws Exception {
		mockMvc.perform(get("/weather").param("pincode", "4343G03").param("for_date", "2021-10-15"))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("Invalid Pincode")));
	}

}
