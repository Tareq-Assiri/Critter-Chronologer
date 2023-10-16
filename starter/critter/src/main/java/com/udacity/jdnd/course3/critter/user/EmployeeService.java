package com.udacity.jdnd.course3.critter.user;


import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {


    private final EmployeeRepository employeeRepository;



    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepository = employeeRepo;
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO){
        Employee employee = getEmployee(employeeDTO);
        employee = employeeRepository.save(employee);
        return getEmployeeDTO(employee);
    }

    public EmployeeDTO getEmployeeById(Long employeeId){
        return getEmployeeDTO(employeeRepository.findById(employeeId).get());
    }
    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        employeeRepository.findById(employeeId).get().setDaysAvailable(daysAvailable);
    }


    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        LocalDate date = employeeDTO.getDate();
        List<Employee> available = employeeRepository.getAllByDaysAvailableContains(date.getDayOfWeek());
        List<EmployeeDTO> availableAndSkilled = available.stream().filter(employee -> employee.getSkills().containsAll(skills)).map(employee -> getEmployeeDTO(employee))
                .collect(Collectors.toList());
        return availableAndSkilled;
    }
    private Employee getEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }
    private EmployeeDTO getEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }
}
