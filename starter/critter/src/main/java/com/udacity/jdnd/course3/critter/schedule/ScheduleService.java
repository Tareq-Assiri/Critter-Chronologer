package com.udacity.jdnd.course3.critter.schedule;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {


    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;



    public ScheduleService(ScheduleRepository scheduleRepo,  PetRepository petRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepo;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    public ScheduleDTO save(ScheduleDTO scheduleDTO){
        Schedule schedule =  getSchedule(scheduleDTO);
        schedule = scheduleRepository.save(schedule);
        return getScheduleDTO(schedule);
    }

    public List<ScheduleDTO> getScheduleForPet(Long petId){
        Pet pet = petRepository.findById(petId).get();
        return scheduleRepository.getAllByPetsContains(pet).stream().map(schedule -> getScheduleDTO(schedule)).collect(Collectors.toList());
    }
    public List<ScheduleDTO> getScheduleForEmployee( long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        return scheduleRepository.getAllByEmployeesContains(employee).stream().map(schedule -> getScheduleDTO(schedule)).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForCustomer( long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        return scheduleRepository.getAllByPetsIn(customer.getPets()).stream().map(schedule -> getScheduleDTO(schedule)).collect(Collectors.toList());
    }


    public List<ScheduleDTO> getAll() {
        return scheduleRepository.findAll().stream().map(schedule -> getScheduleDTO(schedule)).collect(Collectors.toList());
    }
    private Schedule getSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDTO.getId());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setPets(scheduleDTO.getPetIds().stream().map(id -> petRepository.findById(id).get()).collect(Collectors.toList()));
        schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().map(id -> employeeRepository.findById(id).get()).collect(Collectors.toList()));
        schedule.setActivities(scheduleDTO.getActivities());
        return schedule;
    }
    private ScheduleDTO getScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setPetIds(schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
        return scheduleDTO;
    }
}
