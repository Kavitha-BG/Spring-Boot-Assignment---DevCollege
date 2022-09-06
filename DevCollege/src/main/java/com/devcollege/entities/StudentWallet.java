package com.devcollege.entities;

import javax.validation.constraints.*;

public class StudentWallet {

	@Positive(message= "Amount must be numeric or decimal positive value")
	@Min(value=1, message = "WalletAmount should be Rs.1")
	@Max(value = 50000, message = "Wallet Amount should be lesser than Rs.50000")
	private Float amount;

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

}
