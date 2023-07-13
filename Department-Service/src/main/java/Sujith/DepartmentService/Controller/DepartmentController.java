package Sujith.DepartmentService.Controller;

import Sujith.DepartmentService.DTO.DepartmentDto;
import Sujith.DepartmentService.Entity.Department;
import Sujith.DepartmentService.Service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentController
{
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto)
    {
        DepartmentDto savedDepartment=departmentService.saveDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @GetMapping("{department-code}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable("department-code") String departmentCode)
    {
       DepartmentDto departmentDto= departmentService.getDepartmentByCode(departmentCode);
       return new ResponseEntity<>(departmentDto,HttpStatus.OK);
    }

}
