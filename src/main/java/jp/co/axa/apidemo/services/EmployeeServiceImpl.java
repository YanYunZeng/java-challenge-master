package jp.co.axa.apidemo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	//Reduce the load on the database. Instead of executing the same query repeatedly,
    @Cacheable(cacheNames = "employees")
	public List<Employee> retrieveEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}
    
    //Using a specific cache key will retrieve the cached entity directly, saving unnecessary database queries.
    @Cacheable(cacheNames = "employees", key = "#employeeId")
	public Employee getEmployee(Long employeeId) {
		Optional<Employee> optEmp = employeeRepository.findById(employeeId);
		return optEmp.get();
	}
    
    //The cache reflect the latest state of the data to avoid serving stale or 
    //outdated information, also ensures that all cache entries stored in the specified cache are invalidated and removed.
    @CacheEvict(cacheNames = "employees", allEntries = true)
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}
    
    //Only the cache entry associated with the specific employeeId will be removed, leaving other 
    //cache entries intact, and reduces unnecessary cache invalidations.
    @CacheEvict(cacheNames = "employees", key = "#employeeId")
	public void deleteEmployee(Long employeeId) {
		employeeRepository.deleteById(employeeId);
	}
    
    //Ensures that subsequent requests for the same entity will fetch the updated data from the data source
	@CacheEvict(cacheNames = "employees", key = "#employee.id")
	public void updateEmployee(Employee employee) {
		employeeRepository.save(employee);
	}
}