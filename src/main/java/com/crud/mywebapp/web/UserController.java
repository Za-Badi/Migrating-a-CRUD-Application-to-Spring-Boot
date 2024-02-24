package com.crud.mywebapp.web;
import com.crud.mywebapp.model.User;
import com.crud.mywebapp.service.UserNotFoundException;
import com.crud.mywebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping("/users")
    public String showUserList(Model model){
        List<User> userList= service.listAll();
        model.addAttribute("userList" ,userList);
        return "users";
    }

    @GetMapping(value = "/users/add" )
    public String addUser(Model model){
        model.addAttribute("user", new User());

        return "add";
    }
    @PostMapping(value = "/users/save" )
    public String saveUser( User user){
        service.save(user);
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/edit_user/{id}")
    public String editUser(RedirectAttributes redirectMsg , @PathVariable(name = "id") Integer id, Model model, User user) {
        try   {
             user = service.getUser(id);
            model.addAttribute("user", user);
            return "edit";

        }
        catch (UserNotFoundException e){
            return "redirect:/";
        }
    }

    @RequestMapping(value = "users/delete/{id}" , method = RequestMethod.GET)
    public String deleteUser(@PathVariable Integer id){
        service.removeUserById(id);
        return "redirect:/users";
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(Model model) {
        System.out.println("No handler found exception");
        String errorMessage = "OOops! Something went wrong - value passed via exception handler.";
        model.addAttribute("errorMessage", errorMessage);
        return "error"; // This will display the "error.html" Thymeleaf template
    }
}
