package com.crud.employee.springboot.testing.repository;

import com.crud.employee.springboot.testing.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    //We have used @BeforeEach annotation to signal that the annotated method should be executed before each @Test method in the current test class:
    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Khosi")
                .lastName("Nkosi")
                .email("khosi@gmail.com")
                .build();
    }

    /**
     * test for saving a complete employee object
     */
    //use @test to make this method as a junit test
    @Test
    public void save_employee_test() {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Khosi")
                .lastName("Nkosi")
                .email("khosi@gmail.com")
                .build();

        //use employee repository as the repository provide the crud methods
        //save the employee object to the database
        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        //validate wheater the employee object contains id which is greater than zero
        assertThat(savedEmployee).isNotNull();
        assertThat(employee.getId()).isGreaterThan(0);

    }

    /**
     * test for getting an employee object by id
     */
    //use @test to make this method as a junit test
    @Test
    public void get_employee_test() {

        employeeRepository.save(employee);
        // when -  action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        assertThat(employeeDB.getId()).isNotNull();

    }

    /**
     * test for getting the list of employee object
     */
    //use @test to make this method as a junit test
    @Test
    public void get_list_of_employee_test() {

        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("Khosi")
                .lastName("Nkosi")
                .email("khosi@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

     // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    /**
     * test for updating an employee object
     */
    //use @test to make this method as a junit test
    @Test
    public void update_employee_test() {

        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        //update the email and firstname
        savedEmployee.setEmail("lomakhosi@gmail.com");
        savedEmployee.setFirstName("khosi");

        //save the email and firstname to db
        Employee updatedEmployee =  employeeRepository.save(savedEmployee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("lomakhosi@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("khosi");

    }

    /**
     * This is a failure test for saving an incomplete employee object without  employee id
     */
    @Test
    public void test_for_saving_employee_with_missing_employee_id(){
        Employee employees = employeeRepository.save(incompleteEmployee());
        assertThat(employees).isNotNull();
    }

    /**
     * This is a dummy employee object used for testing purposes
     * @return  Employee object missing employee Id
     */

    private Employee incompleteEmployee() {
        return Employee.builder()
                .firstName("Khosi")
                .lastName("Nkosi")
                .email("khosi@gmail")
                .build();
    }
}