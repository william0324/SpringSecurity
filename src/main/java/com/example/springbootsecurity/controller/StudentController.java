package com.example.springbootsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/query")
    @PreAuthorize("hasAuthority('student:query')")
    public String queryInfo(){
        return "Student/query";
    }
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('student:add')")
    public String addInfo(){
        return "Student/add";
    }
    @GetMapping("/update")
    @PreAuthorize("hasAuthority('student:update')")
    public String updateInfo(){
        return "Student/update";
    }
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('student:delete')")
    public String deleteInfo(){
        return "Student/delete";
    }
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('student:export')")
    public String exportInfo(){
        return "/Student/export";
    }

}
