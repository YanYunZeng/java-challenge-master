package jp.co.axa.apidemo.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.request.EmployeeDTO;
import jp.co.axa.apidemo.request.EmployeeUpdateDTO;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.services.UserInfoServiceImpl;
import jp.co.axa.apidemo.request.Mapper;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private Mapper mapper;
    
    Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    
    @GetMapping("/employees")
    @PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public List<EmployeeDTO> getEmployees() {
        List<EmployeeDTO> employees = employeeService.retrieveEmployees()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());;
        return employees;
    }

    @GetMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public EmployeeDTO getEmployee(@PathVariable(name="employeeId")Long employeeId) {
    	try {
    		return mapper.toDto(employeeService.getEmployee(employeeId));
    	} catch (NoSuchElementException e) {
    		throw new ResponseStatusException(
    		           HttpStatus.NOT_FOUND, "Employee Not Found", e);
    	}
    }

    @PostMapping("/employees")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public void saveEmployee(EmployeeDTO dto){
        employeeService.saveEmployee(mapper.toEmployee(dto));
        logger.debug("Employee Saved Successfully");
    }

    @DeleteMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
    	try {
    		employeeService.deleteEmployee(employeeId);
    		logger.debug("Employee Deleted Successfully");
    	} catch (NoSuchElementException | EmptyResultDataAccessException e) {
    		throw new ResponseStatusException(
    		           HttpStatus.NOT_FOUND, "Employee Not Found", e);
    	}
    }

    @PutMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateEmployee(@RequestBody EmployeeUpdateDTO dto,
                               @PathVariable(name="employeeId")Long employeeId){
    	
    	try {
            Employee emp = employeeService.getEmployee(employeeId);
        	if (dto.getName() != "" ) emp.setName(dto.getName());
        	if (dto.getSalary() > 0) emp.setSalary(dto.getSalary());
        	if (dto.getDepartment() != "") emp.setDepartment(dto.getDepartment());
            employeeService.updateEmployee(emp);
    	} catch (NoSuchElementException e) {
    		throw new ResponseStatusException(
    		           HttpStatus.NOT_FOUND, "Employee Not Found", e);
    	}
    }
}
