package com.nitp.club.management.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
public class CustomRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Pattern(regexp = "[A-Z_]{3,20}", message = "Role name can be 3 to 20 characters and should contain only uppercase letters")
	@Column(unique = true)
	private String roleName;
	@ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
	@JsonIgnore
	@ToString.Exclude
	@Default
	@JsonBackReference
    @EqualsAndHashCode.Exclude
	private Set<CustomUser> users = new HashSet<>();
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "custom_role_authorities", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	@Default
	@JsonManagedReference
    @EqualsAndHashCode.Exclude
	private Set<CustomAuthority> authorities = new HashSet<>();
}
