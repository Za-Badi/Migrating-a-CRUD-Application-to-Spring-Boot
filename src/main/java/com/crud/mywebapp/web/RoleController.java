package com.crud.mywebapp.web;

import com.crud.mywebapp.model.Roles;
import com.crud.mywebapp.service.RoleService;
import com.crud.mywebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoleController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @GetMapping("roles")
    public String findAll(Model model){
        model.addAttribute("roles", roleService.listAll());
        return "role";
    }
    @GetMapping("roles/findById")
    @ResponseBody
    public Roles findById(Integer id){
        return roleService.findUserById(id);
    }
    @PostMapping("roles/addNew")
    public String findAll(Roles role){
        roleService.save(role);
        return "redirect:/roles";
    }

    @RequestMapping(value = "roles/update", method = {RequestMethod.PUT })
    public String update(Roles role){
        roleService.save(role);
        return "redirect:/roles";
    }
    @RequestMapping(value = "roles/delete", method = {RequestMethod.DELETE })
    public String delete(Integer id){
        roleService.removeUserById(id);
        return "redirect:/roles";
    }


}
