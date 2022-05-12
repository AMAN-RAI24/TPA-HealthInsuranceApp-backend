package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.Role;
import com.insurecorp.insureCorp.exceptions.UnidentifiedRoleException;
import com.insurecorp.insureCorp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleManagingController {
    @Autowired
    RoleRepository rolerepository;
    @RequestMapping(path="/getrolebyname/{role}",method = RequestMethod.GET)
    public List<Role> getRoleByName(@PathVariable(value = "role") String role){
        List<Role> roleList=rolerepository.findByRole(role);
        if(roleList.isEmpty()){
            throw new UnidentifiedRoleException(role);
        }
        return roleList;
    }
}
