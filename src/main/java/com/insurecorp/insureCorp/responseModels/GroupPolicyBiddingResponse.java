package com.insurecorp.insureCorp.responseModels;

import com.insurecorp.insureCorp.entities.Offer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupPolicyBiddingResponse {
boolean accepted;
String title;
String severity;
List<BiddingOfferResponse> offers;

}
