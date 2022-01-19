package com.nashtech.rookie.assetmanagement.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.nashtech.rookie.assetmanagement.entity.Enum.State;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="assets")
@AllArgsConstructor
@NoArgsConstructor
public class Asset implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "assetCodeGenerator")
	@GenericGenerator(name = "assetCodeGenerator",
						
						strategy="com.nashtech.rookie.assetmanagement.entity.generator.AssetCodeGenerator")
	@Column(name = "asset_code")
	private String assetCode;
	
	@Column(name = "asset_name",nullable = false)
	private String assetName;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Column(name = "specification")
	private String specification;
	
	@Column(name = "installed_date",nullable = false)
	private LocalDate installed;
	
	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private State state;	
	
	@Column(name="location")
	private String location;
	

	@OneToMany(mappedBy = "asset",fetch = FetchType.LAZY)
	private List<Assignment> assignment;
}
