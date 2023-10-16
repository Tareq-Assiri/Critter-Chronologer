package com.udacity.jdnd.course3.critter.pet;


import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

private final CustomerRepository customerRepository;
    private final PetRepository petRepository;



    public PetService(CustomerRepository customerRepository, PetRepository petRepo) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepo;
    }

    public PetDTO save(PetDTO petDTO){
        Pet pet = getPet(petDTO);
        Customer customer = customerRepository.getOne(petDTO.getOwnerId());
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.insertPet(pet);
        customerRepository.save(customer);
        return getPetDTO(pet);
    }
    public PetDTO get(Long petId){
        Pet pet = petRepository.findById(petId).get();
        return getPetDTO(pet);
    }
    public List<PetDTO> getAll() {
        return petRepository.findAll().stream().map(pet -> getPetDTO(pet)).collect(Collectors.toList());
    }
    public List<PetDTO> getPetsByOwner(long ownerId) {
        List<PetDTO> petDTOList = petRepository.getAllByCustomerId(ownerId).stream().map(pet -> getPetDTO(pet)).collect(Collectors.toList());
        return petDTOList;
    }
    private PetDTO getPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }
    private Pet getPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setType(petDTO.getType());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setCustomer(customerRepository.getOne(petDTO.getOwnerId()));
        return pet;
    }
}