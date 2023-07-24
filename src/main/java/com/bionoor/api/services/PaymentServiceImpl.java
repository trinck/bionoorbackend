package com.bionoor.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.exceptions.EntityAlreadyExists;
import com.bionoor.api.exceptions.IllegalOperationException;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Payment;
import com.bionoor.api.models.PaymentMethod;
import com.bionoor.api.repositories.PaymentMethodRepository;
import com.bionoor.api.repositories.PaymentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentServiceIn{

	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private PaymentMethodRepository methodRepository;
	private PaymentProcessing paymentProcessing;
	@Autowired
	private InvoiceService invoiceService;
	
	
	

	public Payment addPayment(Payment payment) {
		
		boolean Validation = false;
		
		try {
			Class<PaymentProcessing> class1 = (Class<PaymentProcessing>) Class.forName("com.bionoor.api.services."+payment.getInvoice().getOrder().getPaymentMethod().getClassProcessing());
			if(payment.getAmount()>payment.getInvoice().getDueToPay()) {
				throw new IllegalOperationException(String.format("The amount is more than required for this invoice. invoice required: %f ", payment.getInvoice().getDueToPay()));
			}
			
			
			this.paymentProcessing = class1.getDeclaredConstructor().newInstance();
			Validation = this.paymentProcessing.process(payment);
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			throw new IllegalOperationException(e.getMessage());
		} catch  (Exception e) {
			
			e.printStackTrace();
			throw new IllegalOperationException(e.getMessage());
		}
		
		
			if(Validation) {
				//reduce th due to pay 
				Invoice invoice = payment.getInvoice();
				invoice.setDueToPay(invoice.getDueToPay()-payment.getAmount());
				return this.paymentRepository.save(payment);
			}
			
		throw new IllegalOperationException("the processing payment failed");
	
	}
	
	
	
	
	public PaymentMethod addPaymentMethod(String name, String description) {
		
		PaymentMethod paymentMethod = this.methodRepository.findByName(name).orElse(null);
		
		if(paymentMethod != null) {
			throw  new EntityAlreadyExists(String.format("Payment method with name: %s already exist", name));
		}
		
		paymentMethod = PaymentMethod.builder()
				.name(name)
				.description(description)
				.build();
		
		return this.methodRepository.save(paymentMethod);
	}
	
	
	
	public PaymentMethod addPaymentMethod(PaymentMethod paymentMethod) {
			
			
			return this.methodRepository.save(paymentMethod);
		}
		
	
	
	
	public PaymentMethod getPaymentMethodById(Long id) {
			
			PaymentMethod paymentMethod = this.methodRepository.findById(id).orElse(null);
			
			if(paymentMethod == null) {
				throw  new EntityNotFoundException(String.format("Payment method with id: %d not found", id));
			}
			
			
			return paymentMethod;
		}
	
	
	public List<PaymentMethod> getPaymentMethods() {
			
		return  this.methodRepository.findAll();
	}
	
	public List<Payment> getPayments() {
		
		return  this.paymentRepository.findAll();
	}
	
}
