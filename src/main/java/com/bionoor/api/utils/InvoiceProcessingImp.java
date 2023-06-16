package com.bionoor.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.DiscountDCC;
import com.bionoor.api.models.DiscountDCC.Type;
import com.bionoor.api.models.DiscountDCP;
import com.bionoor.api.models.Discountable;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.Product;
import com.bionoor.api.services.DiscountCodeService;


@Service
public class InvoiceProcessingImp implements InvoiceProcessingIn {

	
	@Autowired
	private DiscountCodeService discountCodeService;
	
	@Override
	public Order TotalAmountOrder(Order order, Boolean definitely) {
		
		
		Double amountInvoice = 0d;
		Double amountHT= 0d;
		
		
		DiscountCode code = order.getDiscountCode();
		Invoice invoice = order.getInvoice();
		
		
		//compute the total  HT amount
		if(code != null && code.getActif()) {
			
			List<OrderItem> toReduceOrderItems = new ArrayList<>();
			List<OrderItem> otherOrderItems = new ArrayList<>();
			for(OrderItem item:order.getOrderItems() ) {
				
					Product product = item.getProduct();
					for(Discountable discountable: code.getDiscountables()){
						if(discountable instanceof Product && product.getId() == discountable.getId()) { toReduceOrderItems.add(item); }
						else if(discountable instanceof Category && product.getCategory().getId() == discountable.getId()){ toReduceOrderItems.add(item); }
						else { otherOrderItems.add(item); }
					}
		
			}
			
			//the nature of discount coe (DCC or DCP
			if(code.getDiscriminatorValue().equalsIgnoreCase("DCC") && toReduceOrderItems.size()>0) {
				
				try {
					DiscountDCC dcc = (DiscountDCC) Utils.getTargetObject(code, DiscountDCC.class);
					amountHT+= reduceDCC(dcc, toReduceOrderItems, definitely);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				
				
			}
			else if(code.getDiscriminatorValue().equalsIgnoreCase("DCP") && toReduceOrderItems.size()>0){
				
				 try {
					DiscountCode dcp = Utils.getTargetObject(code, DiscountDCP.class);
					amountHT += reduceDCP(toReduceOrderItems, (DiscountDCP) dcp , definitely);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			//add the other's price, for undiscountables items
			amountHT += AmountOrderWithoutReduce(otherOrderItems);
		}else { 
			
			//add the  price, for undiscountables items if the discount doesn't exists
			amountHT += AmountOrderWithoutReduce(order.getOrderItems());
			
			
		}
		
		
		
		
		
	
		order.setTotalAmount(amountHT);
		//return order if not invoiceed // yet //
		if(invoice == null) {
			
			
			return order;
		}
		
		
		
		
		
		Double remise = invoice.getRemise() != null?invoice.getRemise(): 0 ;
		int vat = invoice.getVat();
	    Double transport = invoice.getTransport();
	    
	    amountInvoice = amountHT- remise;
	    
	    amountInvoice += (0.01 * amountInvoice * vat);
	    
	    amountInvoice += transport;
	    
	    invoice.setTotalAmount(amountInvoice);
	    
	    
	    
		
		return order;
	}


	
	
	private Double reduceDCC(DiscountDCC dcc, List<OrderItem> items, Boolean definitely) {
		
		Double toPay = 0d;
		
		if (dcc.getType()== Type.AMOUNT) {
			 
			for(OrderItem item: items) {
				
				toPay += item.getProduct().getPrice() * item.getQuantity() ;
			}
			
		      if(toPay - dcc.getDiscount()>=0){
		    	  
		    	  toPay = toPay - dcc.getDiscount();
		    	  dcc.setDiscount(0d);
		      }else {
		    	  dcc.setDiscount(dcc.getDiscount()- toPay);
		    	  toPay = 0d;
		    	  
		      }
		      
		      //delete if unique useful
	    	  if(dcc.getIsUnique() && definitely){ this.discountCodeService.delete(dcc.getId()); }
			
		}else {
			
				for(OrderItem item: items) {
					
					toPay += item.getProduct().getPrice() * item.getQuantity() ;
				}
				
				//computing according to percentage discount
				toPay = toPay - (0.01* toPay* dcc.getDiscount());
				
		}
		
		return toPay;
		
	}
	
	
	private Double reduceDCP(List<OrderItem> items, DiscountDCP dcp, Boolean definitely) {
		
		Double toPay = 0d;
		
		for(OrderItem item: items) {
			
			toPay += item.getProduct().getPrice() * item.getQuantity() ;
		}
		
		//computing according to percentage discount
		toPay = toPay - (0.01* toPay* dcp.getDiscount());
		
		 //delete if not reusable useful
  	    if(!dcp.getReusable() && definitely){ this.discountCodeService.delete(dcp.getId()); }
		
		return toPay;
	}
	
	public Double AmountOrderWithoutReduce(List<OrderItem> items) {
		
		
			Double toPay = 0d;
		
			for(OrderItem item: items) {
				
			
				toPay += (item.getProduct().getPrice() * item.getQuantity()) ;
			}
		
		return toPay;
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
}
