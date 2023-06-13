package jp.co.axa.apidemo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;
	
	Logger logger = LoggerFactory.getLogger(EmployeeControllerTest.class);

	@SneakyThrows
	@Test
	@WithMockUser(value = "viewer", password = "viewer123", roles = { "VIEWER" })
	public void testGetEmployee() throws Exception {
		List<Employee> employeeList = employeeService.retrieveEmployees();
		logger.info("employeeList:{}", employeeList);
		MockHttpServletResponse mockHttpServletResponse = this.mockMvc.perform(
				get("/api/v1/employees/{employeeId}", employeeList.get(0).getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		        .andExpect(status().isCreated())
				.andReturn()
				.getResponse();
		Employee e = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), Employee.class);
		assertThat(e).isNotNull();
		assertThat(e).isEqualTo(employeeList.get(0));
	}

	@SneakyThrows
	@Test
	@WithMockUser(value = "admin", password = "admin123", roles = { "ADMIN" })
	public void testDeleteEmployee() throws Exception {
		List<Employee> employeeList = employeeService.retrieveEmployees();
		logger.info("employeeList:{}", employeeList);
		MockHttpServletResponse mockHttpServletResponse = this.mockMvc
				.perform(delete("/api/v1/employees/{employeeId}",  employeeList.get(0).getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
		        .andExpect(status().isCreated())
		        .andReturn()
				.getResponse();
		assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

}
