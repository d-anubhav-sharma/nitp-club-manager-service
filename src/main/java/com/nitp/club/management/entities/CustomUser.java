package com.nitp.club.management.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomUser{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Pattern(regexp = "[\\w.]{3,50}", message = "Username can be 3 to 50 characters and should contain alphanumeric character or underscore or dot")
	private String username;
	@Pattern(regexp = "[A-Za-z]{3,50}", message = "First name can be 3 to 50 characters and should contain english alphabets")
	private String firstName;
	@Pattern(regexp = "[A-Za-z]{3,50}", message = "Last name can be 3 to 50 characters and should contain english alphabets")
	private String lastName;
	@Email
	private String email;
	@JsonIgnore
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "custom_user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Default
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
	private Set<CustomRole> roles = new HashSet<>();
}
