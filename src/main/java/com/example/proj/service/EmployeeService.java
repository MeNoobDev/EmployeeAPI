package com.example.proj.service;

import com.example.proj.entity.Employee;
import com.example.proj.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService (EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    // add employee
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    // get all employees
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // get employee by id
    public Optional<Employee> getEmployeeById(long id){
        return employeeRepository.findById(id);
    }

    // delete employee by id
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    // update employee by id
    public Employee updateEmployee(Long id, Employee updatedEmployee){
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if(existingEmployee.isPresent()){
            Employee employee = existingEmployee.get();
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setGender(updatedEmployee.getGender());
            employee.setStreetAddress(updatedEmployee.getStreetAddress());
            employee.setCity(updatedEmployee.getCity());
            employee.setState(updatedEmployee.getState());
            employee.setCountry(updatedEmployee.getCountry());
            employee.setPhone(updatedEmployee.getPhone());
            employee.setCountryCode(updatedEmployee.getCountryCode());
            employee.setEmail(updatedEmployee.getEmail());
            return employeeRepository.save(employee);
        }
        else{
            throw new RuntimeException("Employee not found");
        }
    }
    public List<Employee> getAllEmployeesSortedById() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return employeeRepository.findAll(sort);
    }
}
