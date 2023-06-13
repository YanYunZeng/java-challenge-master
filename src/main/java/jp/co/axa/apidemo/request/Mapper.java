package jp.co.axa.apidemo.request;

import org.springframework.stereotype.Component;

import jp.co.axa.apidemo.entities.Employee;

@Component
public class Mapper {
    public EmployeeDTO toDto(Employee employee) {
    	
        return new EmployeeDTO(
        		employee.getId(),
        		employee.getName(),
        		employee.getSalary(),
        		employee.getDepartment());
    }
    
    public Employee toEmployee(EmployeeDTO dto) {
    	
        return new Employee(
        		dto.getId(),
        		dto.getName(),
        		dto.getSalary(),
        		dto.getDepartment());
    }

}

