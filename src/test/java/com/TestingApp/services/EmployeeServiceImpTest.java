package com.TestingApp.services;

import com.TestingApp.dto.EmployeeDTO;
import com.TestingApp.entities.Employee;
import com.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImpTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImp employeeService;

    private Employee mockEmployee;
    private EmployeeDTO mockEmployeeDTO;

    @BeforeEach
    void setUp() {

        mockEmployee = Employee.builder()
                .id(4L)
                .name("Aarav")
                .email("aarav123@gmail.com")
                .salary(20000)
                .build();

        mockEmployeeDTO =
                modelMapper.map(mockEmployee, EmployeeDTO.class);
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto() {

        // Arrange
        Long id = mockEmployee.getId();

        when(employeeRepository.findById(id))
                .thenReturn(Optional.of(mockEmployee));

        // Act
        EmployeeDTO employeeDto =
                employeeService.getEmployeeById(id);

        // Assert
        assertThat(employeeDto).isNotNull();

        assertThat(employeeDto.getId())
                .isEqualTo(mockEmployee.getId());

        assertThat(employeeDto.getEmail())
                .isEqualTo(mockEmployee.getEmail());

        // Verify
        verify(employeeRepository, times(1))
                .findById(id);
    }

    @Test
    void testCreateNewEmployee_WhenEmployeeDoesNotExist_ThenCreateNewEmployee() {

        // Arrange
        when(employeeRepository.findByEmail(mockEmployeeDTO.getEmail()))
                .thenReturn(List.of());

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(mockEmployee);

        // Act
        EmployeeDTO employeeDTO =
                employeeService.createNewEmployee(mockEmployeeDTO);

        // Assert
        assertThat(employeeDTO).isNotNull();

        assertThat(employeeDTO.getId())
                .isEqualTo(mockEmployee.getId());

        assertThat(employeeDTO.getName())
                .isEqualTo(mockEmployee.getName());

        assertThat(employeeDTO.getEmail())
                .isEqualTo(mockEmployee.getEmail());

        assertThat(employeeDTO.getSalary())
                .isEqualTo(mockEmployeeDTO.getSalary());

        // Verify
        verify(employeeRepository, times(1))
                .findByEmail(mockEmployeeDTO.getEmail());

        verify(employeeRepository, times(1))
                .save(any(Employee.class));
    }
}