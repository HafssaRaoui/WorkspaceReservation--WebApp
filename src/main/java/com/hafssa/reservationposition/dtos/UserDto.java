package com.hafssa.reservationposition.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.hafssa.reservationposition.entities.User}
 */
public class UserDto implements Serializable {
    private final Integer id;

    @Size(max = 45)
    private final String matricule;

    @Size(max = 45)
    private final String firstName;

    @Size(max = 45)
    private final String lastName;

    @Size(max = 45)
    private final String role;

    @Size(max = 45)
    @Email(message = "Incorrect email")
    private final String email;


    public UserDto(Integer id, String matricule, String firstName, String lastName, String role, String email) {
        this.id = id;
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;

    }

    public Integer getId() {
        return id;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.matricule, entity.matricule) &&
                Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.role, entity.role) &&
                Objects.equals(this.email, entity.email) ;

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matricule, firstName, lastName, role, email);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "matricule = " + matricule + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "role = " + role + ", " +
                "email = " + email ;
    }
}