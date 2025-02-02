package com.example.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CUSTOMER_ID_GEN")
	private Long id;

	@Column(nullable = false)
	@NotEmpty(message = "Campo name é obrigatório")
	private String name;

	@Column(nullable = false)
	@NotEmpty(message = "Campo email é obrigatório")
	@Email(message = "Campo email é inválido")
	private String email;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonManagedReference
	private List<Endereco> enderecos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
}
