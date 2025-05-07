package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Rollback(value = false)
    void testDeleteCustomer(){
        Customer customer = customerRepository.findById(10L)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        customerRepository.delete(customer);
    }


    @Test
    @Rollback(value = false)
    @Disabled
    void testUpdateCustomer(){
        Customer customer = customerRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        customer.setCustomerName("SpringBoot1");
//        customerRepository.save(customer);
        assertThat(customer.getCustomerName()).isEqualTo("SpringBoot1");
    }

    @Test
    @Disabled
    void testByNotFoundException(){
        Customer customer = customerRepository.findByCustomerId("A004")
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
//        assertThat(customer.getCustomerId()).isEqualTo("A001");
    }

    @Test
        @Disabled
    void testFindBy() {
        Optional<Customer> optionalCustomer = customerRepository.findById(1L);
        //assertThat(optionalCustomer).isNotEmpty();
        if(optionalCustomer.isPresent()) {
            Customer existCustomer = optionalCustomer.get();
            assertThat(existCustomer.getId()).isEqualTo(1L);
        }
        Optional<Customer> optionalCustomer2 = customerRepository.findByCustomerId("A001");
//        assertThat(optionalCustomer2).isEmpty();
        Customer a001customer = optionalCustomer2.orElseGet(() -> new Customer());
        assertThat(a001customer.getCustomerName()).isEqualTo("스프링");


        Customer notFoundCustomer = customerRepository.findByCustomerId("A004")
                .orElseGet(()->new Customer());
        assertThat(notFoundCustomer.getCustomerName()).isNull();
    }
    @Test
    @Rollback(value = false)
    @Disabled
    public void testCustomer() throws Exception{
        Customer customer = new Customer();
        customer.setCustomerId("A002");
        customer.setCustomerName("스프링2");
        Customer addCustomer = customerRepository.save(customer);
        System.out.println(addCustomer.getCustomerId()+" "+addCustomer.getCustomerName());
        assertThat(addCustomer).isNotNull();
        assertThat(addCustomer.getCustomerName()).isEqualTo("스프링2");

        Optional<Customer> existCustomer = customerRepository.findByCustomerId(addCustomer.getCustomerId());
        assertThat(existCustomer).isNotEmpty();

        Optional<Customer> notExistOptional = customerRepository.findByCustomerId("B001");
        assertThat(notExistOptional).isEmpty();
    }
}