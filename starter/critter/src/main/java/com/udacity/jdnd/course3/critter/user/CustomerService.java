package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;



    public CustomerService(CustomerRepository customerRepo, PetRepository petRepository) {
        this.customerRepository = customerRepo;
        this.petRepository = petRepository;
    }

    public CustomerDTO save(CustomerDTO customerDTO){
        Customer customer = getCustomer(customerDTO);
        customer = customerRepository.save(customer);
        return getCustomerDTO(customer);
    }

    public CustomerDTO getCustomerByPetId(Long petId){
        return getCustomerDTO(petRepository.findById(petId).get().getCustomer());
    }



    public List<CustomerDTO> getAll() {
        return customerRepository.findAll().stream().map(customer -> getCustomerDTO(customer)).collect(Collectors.toList());
    }
    private Customer getCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null && !petIds.isEmpty()) {
            customer.setPets(petIds.stream().map(id -> petRepository.findById(id).get()).collect(Collectors.toList()));
        }else{
            customer.setPets(new ArrayList<>());
        }
        customer.setNotes(customer.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        return customer;
    }
    private CustomerDTO getCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPetIds(customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customerDTO.getPhoneNumber());
        return customerDTO;
    }
}
