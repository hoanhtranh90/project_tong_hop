package com.task2.controller;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
public class EmployeeController {
//    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @GetMapping("/greeting")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Data Response Retrieved."),
//            @ApiResponse(code = 500, message = "Internal Server Error."),
//            @ApiResponse(code = 400, message = "Bad Request cause data input."),
//            @ApiResponse(code = 404, message = "Not found."),
//            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
//            @ApiResponse(code = 401, message = "Unauthorized122.")})
    public List<GrantedAuthority> getEmployees() {
        //get authorities
        return ((List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities());


    }
}