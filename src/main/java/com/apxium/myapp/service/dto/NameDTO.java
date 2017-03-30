package com.apxium.myapp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Name entity.
 */
public class NameDTO implements Serializable {

    private Long id;

    @NotNull
    private String surname;

    @NotNull
    private String firstName;

    @NotNull
    private LocalDate startDate;

    private Long departmentId;

    private String departmentDepartment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentDepartment() {
        return departmentDepartment;
    }

    public void setDepartmentDepartment(String departmentDepartment) {
        this.departmentDepartment = departmentDepartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NameDTO nameDTO = (NameDTO) o;

        if ( ! Objects.equals(id, nameDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NameDTO{" +
            "id=" + id +
            ", surname='" + surname + "'" +
            ", firstName='" + firstName + "'" +
            ", startDate='" + startDate + "'" +
            '}';
    }
}
