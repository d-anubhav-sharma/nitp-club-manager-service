package com.nitp.club.management.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomAuthority{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Pattern(regexp = "[A-Z_]{3,20}", message = "Authority name can be 3 to 20 characters and should contain only uppercase letters")
	@Column(unique = true)
	private String authorityName;
	@ManyToMany(mappedBy = "authorities",fetch = FetchType.EAGER)
	@JsonIgnore
	@ToString.Exclude
    @Default
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<CustomRole> roles = new HashSet<>();
}
