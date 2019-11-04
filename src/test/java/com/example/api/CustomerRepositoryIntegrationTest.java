package com.example.api;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFind(){
        PageRequest pg = PageRequest.of(0, 2);
        Page<Customer> pager = customerRepository.findAllByOrderByNameAsc(pg);
        Assert.assertTrue(pager.getTotalElements() > 0);
    }

    @Test
    public void testFindId(){
        Customer customer = new Customer();
        customer.setName("FERNANDO");
        customer.setEmail("fernando@gmail.com");
        customer = entityManager.persist(customer);

        Optional<Customer> customerSaved = customerRepository.findById(customer.getId());
        if(customerSaved.isPresent()){
            Assert.assertTrue(customer.getName().equals(customerSaved.get().getName()));
        }else{
            Assert.fail();
        }

    }

}
