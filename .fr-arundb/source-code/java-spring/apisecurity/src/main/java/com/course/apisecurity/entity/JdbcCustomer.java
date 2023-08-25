package com.course.apisecurity.entity;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;

public class JdbcCustomer {

	@Id
	private int customerId;

	private String fullName;

	@Email
	private String email;

	private LocalDate birthDate;

	@Pattern(regexp = "^[MF]$", message = "Invalid gender")
	private String gender;

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getEmail() {
		return email;
	}

	public String getFullName() {
		return fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
