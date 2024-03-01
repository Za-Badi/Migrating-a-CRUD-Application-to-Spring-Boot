package com.crud.mywebapp.web;
import com.crud.mywebapp.model.Roles;
import com.crud.mywebapp.model.User;
import com.crud.mywebapp.service.RoleService;
import com.crud.mywebapp.service.UserNotFoundException;
import com.crud.mywebapp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String authorities = userDetails.getAuthorities().toString();
        User currentUser = service.findUserByName(authentication.getName());
        model.addAttribute("role",authorities);
        List<User> userList= service.listAll();
        model.addAttribute("userList" ,userList);
        model.addAttribute("currentUser" ,currentUser);
        model.addAttribute("newUser", new User());

        if(authorities.equals("[ADMIN]"))
            return "admin";
        else
            return "users";
        }

    @GetMapping("/logout")

    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "Redirect:/";
    }



    @GetMapping(value = "/add" )
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "add";
    }
    @PostMapping(value = "/save" )
    public String saveUser( User user, @RequestParam(name = "roles", required = false) Integer roles){
        Roles role =roleService.getRoleById(roles);
        Set<Roles> userRole = new HashSet<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRole.add(role);
        user.setRoles(userRole);
        service.save(user);
        return "redirect:/";
    }


    @PostMapping(value = "/editUser" )
    public String saveUser(@RequestParam(name = "roles") Integer roles,
                           @RequestParam(name ="editId") Integer editId,
                           @RequestParam(name ="editName") String editName,
                           @RequestParam(name ="editLastName") String editLastName,
                           @RequestParam(name ="editAge") Integer editAge,
                           @RequestParam(name ="editEmail") String editEmail,
                           @RequestParam(name ="editPassword") String editPassword
    ){
        User editedUser = new User( editName, editLastName, editAge, editEmail, passwordEncoder.encode(editPassword));
        editedUser.setId(editId);
        Roles role =roleService.getRoleById(roles);
        Set<Roles> userRole = new HashSet<>();
        userRole.add(role);
        editedUser.setRoles(userRole);
        service.save(editedUser);
        return "redirect:/";
    }


    @GetMapping(value = "edit_user/{id}")
    @ResponseBody
    public List editUser( @PathVariable(name = "id") Integer id, Model model) {
        try   {
            List userInfo=new ArrayList<>();
            User user = service.getUser(id);
            System.out.println("zaha hhh "+user.getRoleUser());
            userInfo.add(user);
            String role = user.getRoleUser().toString();
            userInfo.add(role);
            return userInfo;
        }
        catch (UserNotFoundException e){
            return null;
        }
    }

    @GetMapping(value = "deleteform/{id}")
    @ResponseBody
    public List deleteForm(RedirectAttributes redirectMsg , @PathVariable(name = "id") Integer id, Model model) {


        try {
            List userInfo = new ArrayList<>();
            User user = service.getUser(id);
            System.out.println("zaha hhh " + user.getRoleUser());
            userInfo.add(user);
            String role = user.getRoleUser().toString();
            userInfo.add(role);
            return userInfo;
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "delete" , method = RequestMethod.POST)
    public String deleteUser(@RequestParam(name ="dId") Integer id){
        System.out.print("zaha delete id "+id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = service.findUserByName(authentication.getName());
        if(!Objects.equals(id, currentUser.getId())){
            service.removeUserById(id);
        }
        return "redirect:/";
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(Model model) {
        System.out.println("No handler found exception");
        String errorMessage = "OOops! Something went wrong - value passed via exception handler.";
        model.addAttribute("errorMessage", errorMessage);
        return "error"; // This will display the "error.html" Thymeleaf template
    }
}
