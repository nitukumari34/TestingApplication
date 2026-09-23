package com.TestingApp.services;

import com.TestingApp.dto.EmployeeDTO;
import com.TestingApp.entities.Employee;
import com.TestingApp.exceptions.ResourceNotFoundException;
import com.TestingApp.repositories.EmployeeRepository;
import com.TestingApp.services.Impl.EmployeeServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

                mockEmployeeDTO = modelMapper.map(mockEmployee, EmployeeDTO.class);
        }

        @Test
        void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto() {

                // Arrange
                Long id = mockEmployee.getId();

                when(employeeRepository.findById(id))
                                .thenReturn(Optional.of(mockEmployee));

                // Act
                EmployeeDTO employeeDto = employeeService.getEmployeeById(id);

                // Assert
                assertThat(employeeDto).isNotNull();

                assertThat(employeeDto.getId())
                                .isEqualTo(mockEmployee.getId());

                assertThat(employeeDto.getName())
                                .isEqualTo(mockEmployee.getName());

                assertThat(employeeDto.getEmail())
                                .isEqualTo(mockEmployee.getEmail());

                assertThat(employeeDto.getSalary())
                                .isEqualTo(mockEmployee.getSalary());

                // Verify
                verify(employeeRepository, times(1))
                                .findById(id);
        }

        @Test
        void testGetEmployeeById_whenEmployeeIsNotPresent_thenThrowException() {
                // Arrange
                when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

                // Act & Assert
                assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                                .isInstanceOf(ResourceNotFoundException.class)
                                .hasMessage("Employee not found with id : 1");

                // Verify
                verify(employeeRepository, times(1)).findById(1L);
        }

        @Test
        void testCreateNewEmployee_WhenEmployeeDoesNotExist_ThenCreateNewEmployee() {

                // Arrange
                when(employeeRepository.findByEmail(mockEmployeeDTO.getEmail()))
                                .thenReturn(List.of());

                when(employeeRepository.save(any(Employee.class)))
                                .thenReturn(mockEmployee);

                // Act
                EmployeeDTO employeeDTO = employeeService.createNewEmployee(mockEmployeeDTO);

                // Assert
                assertThat(employeeDTO).isNotNull();

                assertThat(employeeDTO.getId())
                                .isEqualTo(mockEmployee.getId());

                assertThat(employeeDTO.getName())
                                .isEqualTo(mockEmployee.getName());

                assertThat(employeeDTO.getEmail())
                                .isEqualTo(mockEmployee.getEmail());

                assertThat(employeeDTO.getSalary())
                                .isEqualTo(mockEmployee.getSalary());

                // Verify findByEmail()
                verify(employeeRepository, times(1))
                                .findByEmail(mockEmployeeDTO.getEmail());

                // ArgumentCaptor
                ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

                // Capture Employee passed to save()
                verify(employeeRepository, times(1))
                                .save(employeeCaptor.capture());

                // Get captured Employee
                Employee capturedEmployee = employeeCaptor.getValue();

                // Assert captured Employee
                assertThat(capturedEmployee).isNotNull();

                assertThat(capturedEmployee.getName())
                                .isEqualTo(mockEmployeeDTO.getName());

                assertThat(capturedEmployee.getEmail())
                                .isEqualTo(mockEmployeeDTO.getEmail());

                assertThat(capturedEmployee.getSalary())
                                .isEqualTo(mockEmployeeDTO.getSalary());
        }

        @Test
        void testCreateNewEmployee_whenAttemptingToCreateNewEmployeeWithExistingEmail_thenThrowException() {
                // Arrange
                when(employeeRepository.findByEmail(mockEmployeeDTO.getEmail()))
                                .thenReturn(List.of(mockEmployee));

                // Act & Assert
                assertThatThrownBy(() -> employeeService.createNewEmployee(mockEmployeeDTO))
                                .isInstanceOf(RuntimeException.class)
                                .hasMessage("Employee already exist with email: " + mockEmployeeDTO.getEmail());

                // Verify
                verify(employeeRepository, times(1)).findByEmail(mockEmployeeDTO.getEmail());
                verify(employeeRepository, never()).save(any());
        }

        @Test
        void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException() {
                // arrange
                assertThatThrownBy(() -> employeeService.updateEmployee(1L, mockEmployeeDTO))
                                .isInstanceOf(ResourceNotFoundException.class)
                                .hasMessage("Employee not found with id : 1");
                // verify
                verify(employeeRepository).findById(1L);
                verify(employeeRepository, never()).save(any());
        }

        @Test
        void testUpdateEmployee_whenAttemptingToUpdateEmail_thenThrowException() {
                // Arrange
                when(employeeRepository.findById(mockEmployeeDTO.getId())).thenReturn(Optional.of(mockEmployee));
                mockEmployeeDTO.setName("Random");
                mockEmployeeDTO.setEmail("random@gmail.com");

                // Act & Assert
                assertThatThrownBy(() -> employeeService.updateEmployee(mockEmployeeDTO.getId(), mockEmployeeDTO))
                                .isInstanceOf(RuntimeException.class)
                                .hasMessage("The email of the employee cannot be updated");

                // Verify
                verify(employeeRepository).findById(mockEmployeeDTO.getId());
                verify(employeeRepository, never()).save(any());
        }
    @Test
    void testUpdateEmployee_whenValidEmployee_thenUpdateEmployee() {
        // Arrange
        when(employeeRepository.findById(mockEmployeeDTO.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDTO.setName("Random name");
        mockEmployeeDTO.setSalary(199.0);

        Employee newEmployee = modelMapper.map(mockEmployeeDTO, Employee.class);
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);

        // Act
        EmployeeDTO updatedEmployeeDto = employeeService.updateEmployee(mockEmployeeDTO.getId(), mockEmployeeDTO);

        // Assert
        assertThat(updatedEmployeeDto).isEqualTo(mockEmployeeDTO);

        // Verify
        verify(employeeRepository).findById(mockEmployeeDTO.getId());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> employeeService.deleteEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id : 1");

        // Verify
        verify(employeeRepository).existsById(1L);
        verify(employeeRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteEmployee_whenEmployeeIsValid_thenDeleteEmployee() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> employeeService.deleteEmployee(1L))
                .doesNotThrowAnyException();

        // Verify
        verify(employeeRepository).existsById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}