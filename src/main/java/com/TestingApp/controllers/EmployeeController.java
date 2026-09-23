package com.TestingApp.controllers;

import com.TestingApp.dto.EmployeeDTO;
import com.TestingApp.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    //@RequestMapping("/api")
@RequestMapping("/api/employees")
public class EmployeeController {
private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable  Long id){
        EmployeeDTO employeeDTO=employeeService.getEmployeeById(id);
        return  ResponseEntity.ok(employeeDTO);
    }
    @PostMapping
    public ResponseEntity<EmployeeDTO>createEmployee(@RequestBody EmployeeDTO employeeDTO){
         EmployeeDTO createEmployeeDto=employeeService.createNewEmployee(employeeDTO);
         return  new ResponseEntity<>(createEmployeeDto, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO>updateEmployee(@PathVariable Long id,@RequestBody EmployeeDTO employeeDTO){
        EmployeeDTO uodateEmployeeDto=employeeService.updateEmployee(id,employeeDTO);
        return  ResponseEntity.ok(uodateEmployeeDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
