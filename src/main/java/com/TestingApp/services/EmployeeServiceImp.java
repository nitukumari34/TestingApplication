package com.TestingApp.services;

import com.TestingApp.dto.EmployeeDTO;
import com.TestingApp.entities.Employee;
import com.TestingApp.exceptions.ResourceNotFoundException;
import com.TestingApp.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImp implements  EmployeeService{
    private  final EmployeeRepository employeeRepository;
    private  final ModelMapper modelMapper;

    public EmployeeServiceImp(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee with id : " + id);
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->{
                        log.info("Employee not found with id : {}",id);
                       return new ResourceNotFoundException("Employee not found with id : " + id);
                });
        log.info("Successfully fetched employee with id : " +id);
        return  modelMapper.map(employee,EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
         log.info("Creating employee with email : {}", employeeDTO.getEmail());
        List<Employee> existingEmployees = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (!existingEmployees.isEmpty()) {
            log.info("Employee already exist with email : " + employeeDTO.getEmail());
            throw new RuntimeException("Employee already exist with email: " + employeeDTO.getEmail());
        }

        Employee newEmployee=modelMapper.map(employeeDTO,Employee.class);
        Employee savedEmployee=employeeRepository.save(newEmployee);
        log.info("Successfully created employee with id : {}",savedEmployee.getEmail());
        return modelMapper.map(savedEmployee,EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating the employee with id :{}",id);
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->{
                    log.info("Employee not found with id :{}",id);
                    return  new ResourceNotFoundException("Employee not found with id : " + id);
                });

        if(!employee.getEmail().equals(employeeDTO.getEmail())){
            log.error("Attempted to update email with id : {}",id);
            throw  new RuntimeException("The email of the employee cannot be updated");
        }
        employeeDTO.setId(id);
        modelMapper.map(employeeDTO, employee);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee updated successfully with this id : {}",id);
        return modelMapper.map(savedEmployee,EmployeeDTO.class);

    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting  the employee with id :{}",id);
        boolean exists=employeeRepository.existsById(id);
        if(!exists){
            log.info("Employee not found with id: {}",id);
            throw  new ResourceNotFoundException("Employee not found with id : " +id);
        }
        employeeRepository.deleteById(id);
        log.info("Successfully deleted employee with id :{}",id);

    }

}
