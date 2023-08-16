/**
 * 
 */



document.addEventListener("DOMContentLoaded", event =>{
	
	var inputId = document.querySelector("#id")
	
	inputId.addEventListener("change", event2 =>{
		
		if(inputId.value == ''){
			return
		}
		
		
		fetchFunction(url= `api/invoices/notPaid?id=${inputId.value}`, method="GET", null,  result =>{
			
			
			var total = document.getElementById("invoice-amount")
			var toPay = document.getElementById("invoice-to-pay")
			var duedate = document.getElementById("invoice-due-date")
			var orderId = document.getElementById("order-id")
			var cardInvoiceId = document.getElementById("card-invoice-id")
			
			total.textContent = result.totalAmount.toFixed(2)
			toPay.textContent = result.dueToPay.toFixed(2)
			duedate.textContent = new Date(result.dueDate).toDateString()
			orderId.textContent = result.order
			cardInvoiceId.textContent = `#${result.id}`
			
		})
	})
})