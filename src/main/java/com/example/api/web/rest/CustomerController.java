package com.example.api.web.rest;

import java.util.ArrayList;

import com.example.api.domain.Endereco;
import com.example.api.service.EnderecoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

import javax.validation.Valid;

@Api(value = "/customers",
		authorizations = {@Authorization(value = "Basic")})
@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;
	private EnderecoService enderecoService;

	@Autowired
	public CustomerController(CustomerService service, EnderecoService enderecoService) {
		this.service = service; this.enderecoService = enderecoService;
	}

	@ApiOperation(value = "Lista os Customers",
			response = Customer.class,
			responseContainer = "Page",
			notes = "Este operação lista os Customers cadastrados")
	@ApiResponses(value= {
			@ApiResponse(
					code=200,
					message="Retorna um Page<Customer> com status 200",
					response=Customer.class,
					responseContainer = "Page"
			)
	})
	@GetMapping
	public Page<Customer> findAll(@RequestParam( value = "page", required = false, defaultValue = "0") int page) {
		return service.findAll(page);
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@PostMapping
	public ResponseEntity insertCustomer(@Valid @RequestBody Customer customer){
		Customer customerSaved = service.save(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerSaved);
	}

	@PostMapping("/{id}/cep/{cep}")
	public ResponseEntity<Customer> insertEndereco(@PathVariable("id") Long id, @PathVariable("cep") String cep){
		Customer customer = service.findById(id).get();
		if(customer == null){
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
		}
		Endereco endereco = enderecoService.buscaEnderecoPorCEP(cep);
		endereco.setCustomer(customer);
		endereco = enderecoService.salvar(endereco);
		if(customer.getEnderecos() == null){
			customer.setEnderecos(new ArrayList<>());
		}
		customer.getEnderecos().add(endereco);
		customer = service.save(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}

	@PutMapping("/{id}")
	public ResponseEntity alterCustomer(@PathVariable("id") Long id, @Valid @RequestBody Customer customer){
		Customer customerSaved = service.save(customer);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id){
		service.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}

}
