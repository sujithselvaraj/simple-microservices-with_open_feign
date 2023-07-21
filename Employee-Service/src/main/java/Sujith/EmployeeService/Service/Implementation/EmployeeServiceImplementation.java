package Sujith.EmployeeService.Service.Implementation;

import Sujith.EmployeeService.DTO.APIResponseDto;
import Sujith.EmployeeService.DTO.DepartmentDto;
import Sujith.EmployeeService.DTO.EmployeeDto;
import Sujith.EmployeeService.Entity.Employee;
import Sujith.EmployeeService.Repository.EmployeeRepository;
import Sujith.EmployeeService.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService
{
    private EmployeeRepository employeeRepository;
    private RestTemplate restTemplate;

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
//
//        ResponseEntity<DepartmentDto> responseEntity= restTemplate.getForEntity("http://localhost:8082/"+employee.getDepartmentCode(), DepartmentDto.class);
//        DepartmentDto departmentDto=responseEntity.getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4OTg3ODUxNywiaWF0IjoxNjg5ODYwNTE3fQ.CIzUZytlli1zgOUt-KfIej3FXum0DKcvKki1OoNFmEzjeg_XWLwR1_zkMXh87bR042yH8DCYfVPkZHDvf-zDaw");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<DepartmentDto> responseEntity= restTemplate.exchange("http://localhost:8081/"+employee.getDepartmentCode(), HttpMethod.GET,entity, DepartmentDto.class);
        DepartmentDto departmentDto=responseEntity.getBody();

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