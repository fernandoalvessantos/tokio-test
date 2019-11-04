package com.example.api;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import com.example.api.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CustomerServiceIntegrationTest {

    @TestConfiguration
    static class CustomerServiceTestContextConfiguration {

        @Bean
        public CustomerService customerService() {
            return new CustomerService();
        }
    }

    @Autowired
    private CustomerService customerService;
    @MockBean
    private CustomerRepository repository;

    @Before
    public void setUp() {
        Customer fernando = new Customer();
        fernando.setId(1L);
        fernando.setName("Fernando");
        fernando.setEmail("fernando@gmail.com");

        Mockito.when(customerService.findByName(fernando.getName()))
                .thenReturn(fernando);
    }

    @Test
    public void getByName(){
        String fernando = "Fernando";
        Customer achado = customerService.findByName(fernando);

        Assert.assertTrue(fernando.equals(achado.getName()));
    }
}
