package com.crud.mywebapp.web;
import com.crud.mywebapp.model.Roles;
import com.crud.mywebapp.model.User;
import com.crud.mywebapp.service.RoleService;
import com.crud.mywebapp.service.UserNotFoundException;
import com.crud.mywebapp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private UserService service;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;


    @SuppressWarnings("unchecked")
    @RequestMapping("")
    public String showHomePage( Model model) {
        String authorities="";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal()!=null){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
             authorities = userDetails.getAuthorities().toString();
            User currentUser = service.findUserByName(authentication.getName());
            model.addAttribute("role",authorities);
            List<User> userList= service.listAll();
            model.addAttribute("userList" ,userList);
            model.addAttribute("currentUser" ,currentUser);
        }


        model.addAttribute("newUser", new User());

        if(authorities.equals("[ADMIN]"))
            return "admin";
        else
            return "users";
        }

    @GetMapping(value = "/allusers" )
    @ResponseBody
    public List<User> listAll(){
        return service.listAll();
    }
    @GetMapping(value = "/currentUser" )
    @ResponseBody
    public Map<String, Object> currentUser(){
        Map<String, Object> user = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = service.findUserByName(authentication.getName());
        user.put("id", currentUser.getId());
        user.put("name", currentUser.getFirstName());
        user.put("email", currentUser.getEmail());
        user.put("lastName", currentUser.getLastName());
        user.put("age", currentUser.getAge());
        user.put("role", currentUser.getRoleUser());
        return user;
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "Redirect:/";
    }





    @PostMapping(value = "/saveuser",  consumes = {"application/json", "application/xml", "text/plain"})
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody String httpEntity) throws IOException {
       try{
           ObjectMapper objectMapper = new ObjectMapper();
           JsonNode user = objectMapper.readTree(httpEntity);
           User _user = new User( user.get("firstName").asText(),
                   user.get("lastName").asText(),
                   user.get("age").asInt(),
                   user.get("email").asText(), passwordEncoder.encode(user.get("password").asText()));
           Roles role =roleService.getRoleById(user.get("role").asInt());
           if((user.has("id"))){
             _user.setId(user.get("id").asInt());
           }
           Set<Roles> userRole = new HashSet<>();
           userRole.add(role);
           _user.setRoles(userRole);
           service.save(_user);
       } catch (Exception e){
           System.out.println(e.toString());
       }
        return ResponseEntity.ok().body("success");
    }







@PostMapping(value = "/delete", consumes = {"application/json", "application/xml", "text/plain"})
@ResponseBody
    public ResponseEntity<String> deleteUser(@RequestBody String httpEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = service.findUserByName(authentication.getName());
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode user = objectMapper.readTree(httpEntity);
            System.out.println(user.asText());
            if(user.has("id")){
                Integer id = user.get("id").asInt();
                if (!Objects.equals(id, currentUser.getId())) {
                    service.getUser(id);
                    service.removeUserById(id);
                    return ResponseEntity.ok().body("za");
                }
            }


        } catch (JsonProcessingException | UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.badRequest() .body("failure");
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(Model model) {
        System.out.println("No handler found exception");
        String errorMessage = "OOops! Something went wrong - value passed via exception handler.";
        model.addAttribute("errorMessage", errorMessage);
        return "error"; // This will display the "error.html" Thymeleaf template
    }
}
