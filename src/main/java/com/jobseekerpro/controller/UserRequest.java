package com.jobseekerpro.controller;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class UserRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String name;
    private String role;

    public UserRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameValue) {
        this.name = nameValue;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Custom annotation for signup validation
    @Constraint(validatedBy = SignupRequiredValidator.class)
    @Target({ ElementType.TYPE, ElementType.PARAMETER }) // Allow on parameters
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SignupRequired {
        String message() default "Name and Role are required for signup";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    // Validator to enforce name and role for signup
    public static class SignupRequiredValidator implements ConstraintValidator<SignupRequired, UserRequest> {
        @Override
        public boolean isValid(UserRequest request, ConstraintValidatorContext context) {
            boolean isValid = true;
            context.disableDefaultConstraintViolation();
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Name is required")
                       .addPropertyNode("name")
                       .addConstraintViolation();
            }
            if (request.getRole() == null || request.getRole().trim().isEmpty()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate("Role is required")
                       .addPropertyNode("role")
                       .addConstraintViolation();
            }
            return isValid;
        }
    }
}