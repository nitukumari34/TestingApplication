package com.TestingApp.services;

import com.TestingApp.dto.EmployeeDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long id,EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
}
