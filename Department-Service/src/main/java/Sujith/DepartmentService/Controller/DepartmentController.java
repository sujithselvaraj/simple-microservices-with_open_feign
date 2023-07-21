package Sujith.DepartmentService.Controller;

import Sujith.DepartmentService.DTO.DepartmentDto;
import Sujith.DepartmentService.Entity.Department;
import Sujith.DepartmentService.Entity.JWTRequest;
import Sujith.DepartmentService.Entity.JWTResponse;
import Sujith.DepartmentService.Service.DepartmentService;
import Sujith.DepartmentService.Service.UserService;
import Sujith.DepartmentService.Utility.JWTUtility;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController

public class DepartmentController
{
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
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

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("INVALID_CREDENTIALS",e);
        }

        final UserDetails userDetails=userService.loadUserByUsername(jwtRequest.getUsername());
        final String token= jwtUtility.generateToken(userDetails);
        return new JWTResponse(token);
    }

}
