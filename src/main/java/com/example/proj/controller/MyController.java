package com.example.proj.controller;

import com.example.proj.entity.Employee;
import com.example.proj.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class MyController {

    @Autowired
    private EmployeeService employeeService;



    // HOME
    @GetMapping({"/", "/home"})
    public String home() {
        // Add logic to retrieve products from the database and pass them to the template
        return "userIndex";
    }

    // GET ALL EMPLOYEES
    @GetMapping("/userEmployees")
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployeesSortedById();
        model.addAttribute("employees", employees);
        return "userEmployees";
    }

    // GET EMPLOYEE BY ID
    @GetMapping("/userEmployees/{id}")
    public String getEmployeeById(@PathVariable("id") String idString, Model model) {
        try {
            Long id = Long.parseLong(idString);
            Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                model.addAttribute("employee", employee);
                return "userEmployee"; // Assuming "employee.html" is your Thymeleaf template
            } else {
                return "error"; // Assuming "error.html" is your error template
            }
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Invalid employee ID");
            return "error"; // Assuming "error.html" is your error template
        }
    }

}
