package com.nashtech.rookie.assetmanagement.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nashtech.rookie.assetmanagement.entity.Enum.StateAssignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="assignments")
public class Assignment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="assignment_id")
	private Long assignmentId;
	
	@ManyToOne
	@JoinColumn(name="asset_code",nullable = false)
	private Asset asset;
	
	@ManyToOne
	@JoinColumn(name="assigned_to")
	private User assignedTo;
	
	@ManyToOne
	@JoinColumn(name="assigned_by")
	private User assignedBy;
	
	@Column(name="assigned_date",nullable = false)
	private LocalDate assignedDate;
	
	@Column(name = "state",nullable = false)
	@Enumerated(EnumType.STRING)
	private StateAssignment state;
	
	@Column(name="note")
	private String note;
	
	

}
