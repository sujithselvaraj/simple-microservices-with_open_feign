package Sujith.EmployeeService.Service.Implementation;

import Sujith.EmployeeService.DTO.APIResponseDto;
import Sujith.EmployeeService.DTO.DepartmentDto;
import Sujith.EmployeeService.DTO.EmployeeDto;
import Sujith.EmployeeService.Entity.Employee;
import Sujith.EmployeeService.Repository.EmployeeRepository;
import Sujith.EmployeeService.Service.APIClient;
import Sujith.EmployeeService.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService
{
    private EmployeeRepository employeeRepository;
//    private RestTemplate restTemplate;

    private APIClient apiClient;
    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee=new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getDepartmentCode()
        );

        Employee saveEmployee=employeeRepository.save(employee);

        EmployeeDto saveEmployeeDto=new EmployeeDto(
                saveEmployee.getId(),
                saveEmployee.getFirstName(),
                saveEmployee.getLastName(),
                saveEmployee.getEmail(),
                saveEmployee.getDepartmentCode()
        );



        return saveEmployeeDto;
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        Employee employee=employeeRepository.findById(employeeId).get();

//        ResponseEntity<DepartmentDto> responseEntity= restTemplate.getForEntity("http://localhost:8080/api/departments/"+employee.getDepartmentCode(), DepartmentDto.class);
//        DepartmentDto departmentDto=responseEntity.getBody();

        DepartmentDto departmentDto=apiClient.getDepartment(employee.getDepartmentCode());

        EmployeeDto employeeDto=new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentCode()
        );

        APIResponseDto apiResponseDto=new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }


}
