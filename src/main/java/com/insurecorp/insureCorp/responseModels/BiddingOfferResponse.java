package com.insurecorp.insureCorp.responseModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BiddingOfferResponse {
String company;
double amount;
String message;
int id;
}
