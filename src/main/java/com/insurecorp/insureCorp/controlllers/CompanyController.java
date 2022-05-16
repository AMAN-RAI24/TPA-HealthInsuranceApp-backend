package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.Company;
import com.insurecorp.insureCorp.exceptions.CompanyNotFoundException;
import com.insurecorp.insureCorp.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class CompanyController {
    @Autowired
    CompanyRepository companyrepository;
    @RequestMapping(path="/getcompanybyname/{companyname}",method= RequestMethod.GET)
    public List<Company> getCompanyDetailsByName(@PathVariable(value="companyname") String companyname){
        List<Company> companyList=companyrepository.findByCompanyName(companyname);
        if(companyList.isEmpty()){
            throw new CompanyNotFoundException(companyname);
        }
        return companyList;
//        Company company =companyrepository.findByCompanyName(companyname);
//        if(Objects.isNull(company)){
//            throw new CompanyNotFoundException(companyname);
//        }
//            return company;
    }
    @RequestMapping(path="/getcompanylist",method= RequestMethod.GET)
    public List<Company> listcompanies(){
        return companyrepository.findAll();
    }
}
