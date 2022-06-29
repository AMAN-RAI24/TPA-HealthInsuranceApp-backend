package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.*;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.repositories.InsuranceCompanyRepository;
import com.insurecorp.insureCorp.repositories.OfferRepository;
import com.insurecorp.insureCorp.requestModels.OfferRequest;
import com.insurecorp.insureCorp.responseModels.BiddingOfferResponse;
import com.insurecorp.insureCorp.responseModels.EntityAddedResponse;
import com.insurecorp.insureCorp.responseModels.GroupPolicyBiddingResponse;
import com.insurecorp.insureCorp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BiddingController {
    @Autowired
    GroupPolicyRepository groupPolicyRepository;
    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    LoginService loginService;
    @Autowired
    OfferRepository offerRepository;
    @PostMapping("/make-offer")
    EntityAddedResponse makeOffer(@RequestHeader("Authorization") String jwt, @RequestBody OfferRequest offerRequest)
    {
        Offer offer = new Offer();
        offer.setAmount(offerRequest.getAmount());
        offer.setMessage(offerRequest.getMessage());
        offer.setStatus("PENDING");
        offer.setGroupPolicy(groupPolicyRepository.getById(offerRequest.getGroupPolicyId()));
        User user = loginService.getUser(jwt);
        offer.setInsuranceCompany(insuranceCompanyRepository.findInsuranceCompanyByName(user.getName()).get(0));
        offerRepository.save(offer);
        EntityAddedResponse response = new EntityAddedResponse();
        response.setName("Offer from "+user.getName());
        response.setMessage("Offer Placed succesfully");
        return response;
    }
    @GetMapping("/all-offers")
    List<Offer> getAllOffers()
    {
        return offerRepository.findAll();
    }
    @GetMapping("/get-offer-data/{id}")
    GroupPolicyBiddingResponse getOfferData(@RequestHeader("Authorization") String jwt ,@PathVariable int id)
    {
            boolean accepted;
            List<Offer> offers = offerRepository.findOfferByGroupPolicy(groupPolicyRepository.getById(id), Sort.by("amount").ascending());
            List<Offer> acceptedOffer = offerRepository.findOfferByGroupPolicyAndStatus(groupPolicyRepository.getById(id),"ACCEPTED");
            accepted =(acceptedOffer.size()==1);
        GroupPolicyBiddingResponse response = new GroupPolicyBiddingResponse();
        List<BiddingOfferResponse> offerResponses = new ArrayList<>();
        for (Offer offer:
             offers) {
            BiddingOfferResponse biddingOfferResponse = new BiddingOfferResponse();
            biddingOfferResponse.setAmount(calculatePerEmployeeCost(offer.getGroupPolicy(),offer));
            biddingOfferResponse.setCompany(offer.getInsuranceCompany().getName());
            biddingOfferResponse.setMessage(offer.getMessage());
            biddingOfferResponse.setId(offer.getId());
            offerResponses.add(biddingOfferResponse);
        }


        response.setOffers(offerResponses);
        response.setAccepted(accepted);

        User user = loginService.getUser(jwt);
        if(user.getRole().getRole().equals("ROLE_MANAGER"))
        {
            if(!accepted)
            {
                response.setTitle("Choose an Offer to close the deal with the Insurance Company!");
                response.setSeverity("info");
            }
        }else{
            if(!accepted) {
                response.setTitle("Make an offer for the given requirements to bag the deal!!");
                response.setSeverity("info");
            }
        }
        if(accepted)
        {

            response.setTitle("The deal is close @ "+calculatePerEmployeeCost(acceptedOffer.get(0).getGroupPolicy(),acceptedOffer.get(0))+" , bagged by "+acceptedOffer.get(0).getInsuranceCompany().getName());
            response.setSeverity("success");
        }


        return response;
    }

    @PutMapping("/make-offer/{id}")
    String acceptOffer(@RequestHeader("Authorization") String jwt,@PathVariable int id)
    {
            Offer offer = offerRepository.getById(id);
            offer.setStatus("ACCEPTED");
            GroupPolicy groupPolicy = offer.getGroupPolicy();
            groupPolicy.setStatus("APPROVED");

            offerRepository.save(offer);
            groupPolicyRepository.save(groupPolicy);

            return "ACCEPTED !!!!";
    }
    double calculatePerEmployeeCost(GroupPolicy groupPolicy, Offer offer)
    {
        EmployeeDistribution employeeDistribution = groupPolicy.getEmployeeDistribution();
        int totalEmployee = employeeDistribution.getMales()+ employeeDistribution.getFemales()+employeeDistribution.getOthers();
        double amount = offer.getAmount()/totalEmployee;
        return Math.round(amount);
    }
}
