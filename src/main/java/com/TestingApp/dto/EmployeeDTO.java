package com.TestingApp.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class EmployeeDTO {

    private  Long id;
    private String email;
    private String name;
    private  double salary;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return Double.compare(salary, that.salary) == 0 && Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, salary);
    }


}
