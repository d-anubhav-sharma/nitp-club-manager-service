package com.nitp.club.management.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Club {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String clubName;
	@OneToOne(optional = false)
	private CustomUser clubOwner; 
	
}
