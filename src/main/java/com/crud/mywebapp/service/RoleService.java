package com.crud.mywebapp.service;

import com.crud.mywebapp.repositories.RoleRepository;
import com.crud.mywebapp.repositories.UserRepository;
import com.crud.mywebapp.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository repository;

    public List<Roles> listAll(){
        return (List<Roles>) repository.findAll();
    }
    public Roles getRoleById(Integer id){
        System.out.println("zaha inspect "+ id);
        System.out.println("zaha inspect "+ repository.findById( id).get());
        return  repository.findById( id).get();
    }

    public void save(Roles role) {
        repository.save(role);
    }
//    public Roles getUser(Integer id) throws UserNotFoundException {
//        Optional<Roles> roleById =  repository.findById(id);
//        if (roleById.isPresent()){
//            return roleById.get();
//        }
//        throw new UserNotFoundException("Could not found user with this ID");
//
//    }

    public void removeUserById(Integer id) {
        repository.deleteById(id);
    }
    public Roles findUserById(Integer id) {
       return repository.findById(id).orElse(null);
    }
    public  Roles getRoleByName(String role){
        System.out.println("zaha role res " +role);
        return repository.getRoleByName(role);

    }



//    public void assignUserRole(Integer userId,Integer roleId){
//        User user = userRepository.findById(userId).orElse(null);
//        Roles role = repository.findById(roleId).orElse(null);
//        Set<Roles> userRole = user.getRoles();
//        userRole.add(role);
//        user.setRoles(userRole);
//        userRepository.save(user);
//     }
//    public void unAssignUserRole(Integer userId,Integer roleId){
//        User user = userRepository.findById(userId).orElse(null);
//        Set<Roles> userRole = user.getRoles();
//        userRole.removeIf(x -> x.getId() == (long) roleId);
//        userRepository.save(user);
//    }
//    public Set<Roles> getUserRoles(User user){
//      return user.getRoles();
//    }
//    public Set<Roles> getUserNotRoles(User user){
//        return repository.getUserNotRoles(( (user.getId()).intValue()));
//
//
//
//    }
}
