package com.example.proj.controller;

import com.example.proj.entity.Employee;
import com.example.proj.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private EmployeeService employeeService;




    // HOME
    @GetMapping("/admin")
    public String home() {
        // Add logic to retrieve products from the database and pass them to the template
        return "index";
    }


    // GET ALL EMPLOYEES
    @GetMapping("/admin/employees")
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployeesSortedById();
        model.addAttribute("employees", employees);
        return "employees";
    }

    // GET EMPLOYEE DETAILS BY ID
    @GetMapping("/admin/employees/{id}")
    public String getEmployeeById(@PathVariable("id") String idString, Model model) {
        try {
            Long id = Long.parseLong(idString);
            Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                model.addAttribute("employee", employee);
                return "employee"; // Assuming "employee.html" is your Thymeleaf template
            } else {
                return "error"; // Assuming "error.html" is your error template
            }
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Invalid employee ID");
            return "error"; // Assuming "error.html" is your error template
        }
    }


    // DELETE EMPLOYEE
    @GetMapping("/admin/employees/delete/{id}")
    public String deleteEmployeeById(@PathVariable("id") String idString){
        System.out.println("ADMIN DELETE CONTROLLER HIT");
        Long id = Long.parseLong(idString);
        employeeService.deleteEmployee(id);
        return "redirect:/admin/employees";
    }

    // ADD NEW EMPLOYEE
    @GetMapping("/admin/employees/add")
    public String getEmployeeAdd(Model model){
        System.out.println("ADMIN ADD GET CONTROLLER HIT");
        model.addAttribute("employee", new Employee());
        return "employeeAdd";
    }

    @PostMapping("/admin/employees/add")
    public String postEmployeeAdd(@ModelAttribute("employee") Employee employee){
        System.out.println("ADMIN ADD POST CONTROLLER HIT");
        employeeService.saveEmployee(employee);
        return "index";
    }

    // UPDATE EMPLOYEE
    @GetMapping("/admin/employees/update/{id}")
    public String updateEmployee(@PathVariable("id") String idString, Model model){
        System.out.println("ADMIN UPDATE EMPLOYEE GET CONTROLLER HIT");
        Long id = Long.parseLong(idString);
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if(employee.isPresent()){
            model.addAttribute("employee",employee.get());
            return "employeeupdate";
        }
        else return "error";
    }
    @PostMapping("/admin/employees/update/{id}")
    public String processUpdateEmployee(@PathVariable("id") Long id, @ModelAttribute Employee updatedEmployee) {
        // Update the employee with the provided ID using the data from updatedEmployee
        System.out.println("ADMIN UPDATE EMPLOYEE POST CONTROLLER HIT");

        employeeService.updateEmployee(id, updatedEmployee);

        // Redirect to the admin employees page
        return "redirect:/admin/employees";
    }


}
