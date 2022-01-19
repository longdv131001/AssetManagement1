package com.nashtech.rookie.assetmanagement.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nashtech.rookie.assetmanagement.entity.Enum.Gender;
import com.nashtech.rookie.assetmanagement.entity.Enum.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="staffcode")
	@GeneratedValue(generator = "staffcodeGenerator")
	@GenericGenerator(name = "staffcodeGenerator", 
						strategy="com.nashtech.rookie.assetmanagement.entity.generator.StaffCodeGenerator")	
	private String staffCode;
	
	@Column(name="fist_name",nullable = false)
	private String firstName;
	
	@Column(name="last_name",nullable = false)
	private String lastName;
	
	@Column(name="birthdate",nullable = false)
	private LocalDate birthDate;
	
	@Column(name="joined_date",nullable = false)
	private LocalDate joinedDate;
	
	
	@Column(name="gender",nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;
	

	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="location",nullable = false)
	private String location;
	
	@Column(name="disable",nullable = false)
	private boolean isDisable;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private Type type;

	@JsonIgnore
	@OneToMany(mappedBy = "assignedTo",fetch = FetchType.LAZY)
	private Set<Assignment> assignedAssignments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assignedBy",fetch = FetchType.LAZY)
	private Set<Assignment> assignedToOtherAssignments;
	
	@Column(name="isNew",nullable = false)
	private boolean isNewUser;
}