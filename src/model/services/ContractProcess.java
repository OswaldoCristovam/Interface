package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;

public class ContractProcess {

	private OnlinePaymentService paymentService;
	
	public ContractProcess() {
	}
	
	public ContractProcess(OnlinePaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public OnlinePaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(OnlinePaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void processContract(Contract contract, Integer months) {
		double installment = contract.getTotalValue() / months;
		for (int i=1; i<=months; i++) {
			Date date = contract.getDate();
			Double interest = paymentService.interest(installment, i);
			Double installmentValue = paymentService.paymentFee(interest);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, i);
			date = calendar.getTime();
			
			contract.addInstallment(date, installmentValue);
		}
	}
}
