/**
 * 
 */
 /***variables globale*********
 ************************** */

 
 var order;
 
/******************************************** */
/**initialization */

document.addEventListener('DOMContentLoaded', function() {
    getOrder()
});



/****************** */


 
function getOrder(){
	
	
	 var id = document.querySelector(".order-info").id
	 url = "/api/orders/"+id
	try{
			 fetchFunction(url,"GET", null, resultOrder => order = {...resultOrder})
					
	 }catch(e){
		
		 console.log("origin get order  :"+e)
	 }	
	
}















async function addItemTr(event){
	
	
	const template = document.querySelector('#template');
	const discountList = document.querySelector("#items-table")
	 const clone = template.content.cloneNode(true);
	 
	discountList.appendChild(clone)
} 




async function onChangeItemName(event){
	
	  url = "/api/orders/orderItems/save"
	  
	  var value = event.target.value.trim()
	
	if (value === null || value === ""){
					
			return alert("Product name cann't be empty")
		}
	
	
	var Tr = event.target.parentElement.parentElement
	var code = Tr.querySelector(".item-product-code")
	var price = Tr.querySelector(".item-product-price")
	var quantity = Tr.querySelector(".item-quantity")
	var totaAmount = Tr.querySelector(".item-totalAmount")					
	
	
	var orderInfo = document.querySelector(".order-info")
	var f = new FormData()
	
	f.set("order",Number(orderInfo.id) )
	f.set("quantity", quantity.value)
	f.set("productName",value)
	f.set("id", Tr.id)
	
	
	
	
	try{
		
		 fetchFunction(url,"POST", f, result =>{ 
					
						
						order = {...result}
						
						let item = order.orderItems.find( item => item.product.name === value)
						code.innerHTML = item.product.code
						price.innerHTML = item.product.price
						quantity.value = item.quantity
						totaAmount.innerHTML = (item.product.price * item.quantity).toFixed(2)
						Tr.id = item.id
						quantity.onchange = updateQauntity;
						
						//update order's values dome
						updateOrderValues()
						
				})
		
				
				
	 }catch(e){
		
		 console.log("origin added item :"+e)
	 }	
	

}













async function addDiscountCode(event){
	
	var code = event.target.value.trim()
	/**assure that discount code already present or no for delete before adding *********
	****new discount******************************************************************* */
	if ( code === "" ){
					
	
				if(order.discountCode === undefined || order.discountCode === null){
					
					console.log("dicount code field is empty")
					return false
				}	
			
			
			return  deleteDiscountCode()
		}
	/***************************************************************************************** */
	var discountCodeValue = document.querySelector(".discountCode-value")
	var orderId = document.querySelector(".order-info").id
	url = "/api/orders/discountCode/add"
	
	var f =new FormData()
	f.set("code",code)
	f.set("id", orderId)
	
	
	try{
				
				 fetchFunction(url,"POST", f, result =>{ 
						
						discountCodeValue.innerHTML = result.discountCode.discount
						order = {...result}	
						//update order's values dome
						updateOrderValues()
						
				})
				 
			
	 }catch(e){
		
		 console.log("origin add discount code :"+e)
	 }	
	
	
}




//delete discount code from order
 async function deleteDiscountCode(){
	var orderId = document.querySelector(".order-info").id
	url = "/api/orders/discountCode/delete?id="
	url = url+orderId
	
	if(order.discountCode=== null || order.discountCode === undefined){
		
			return false
	}
	
	try{
				 fetchFunction(url,"GET", null,result =>{ 
						
						var discountCodeValue = document.querySelector(".discountCode-value")
						discountCodeValue.innerHTML = "N/A"
						order = {...result}
						//update order's values dom
						updateOrderValues()
						
				})
				
				
	 }catch(e){
		
		 console.log("origin delete discount code :"+e)
	 }	
	
}




async function updateQauntity(event){
	var orderId = document.querySelector(".order-info").id
	url = "/api/orders/orderItems/put/quantity"
	
	var target = event.target
	var quantity = target.value
	
	var id = target.parentElement.parentElement.id
	
	var f = new FormData()
	f.set("id", orderId)
	f.set("quantity", quantity)
	f.set("orderItemId",id )
	
	try{
		
		if (quantity === "") {
			throw Error("value undefined")
		}
		
		//update quantity of item
		fetchFunction(url,"POST", f, resultat=>{
			
			order = {...resultat}
			//update item amout
			updateTotalAmount(event)
			//update order
			updateOrderValues()
			
		} )
		
	}catch(e){
		console.log("origin quantity changing: "+e)
	}
	
}






function updateTotalAmount(event){
	
	var Tr = event.target.parentElement.parentElement
	
	var price = Tr.querySelector(".item-product-price")
	var totalAmount = Tr.querySelector(".item-totalAmount")
	
	totalAmount.innerHTML = (Number(price.innerHTML) * event.target.value).toFixed(2)
}




async function deleteOrderItem(event){
	url = "/api/orders/orderItems/delete"
	
	var orderInfo = document.querySelector(".order-info")
	var Tr = event.target.parentElement.parentElement
	
	
	//delete element even there is no item for tr
	if(Tr.id === ""){
		
		return Tr.remove()
	}
	
	
	
	var f = new FormData()
	f.set("id", orderInfo.id)
	f.set("orderItemId", Tr.id)
	
	try{
		
		fetchFunction(url,"POST", f,resultat=>{
			
				Tr.remove()
				order = {...resultat}
				//update dom order values
				updateOrderValues()
			
		})
		
		
		
	}catch(e){
		console.log("origin deleting item: "+e)
	}

	
}










 
 
async function updateOrderStatus(element, id){
	 
	 url = "/api/orders/put/status"
	var f = new FormData()
	f.set("status",element.value)
	f.set("id", id)
	
	try{
			fetchFunction(url,"POST", f,resultat => console.log("status updated to "+resultat.status))
			
	 }catch(e){
		
		 console.log("origin update order status  :"+e)
	 }
	 
 }
 
 
 
 
 
 
 
 
 //create order invoice**************
async function createOrderInvoice(event){
	 
	 event.preventDefault()
	 url = "/api/orders/invoice/save"
	 var orderInfo = document.querySelector(".order-info")
	 var invoiceForm = document.querySelector("#invoice-form")
	var f = new FormData(invoiceForm)
	
	
	f.set("order",Number( orderInfo.id))
	//f.delete("status")
	console.log(...f.entries())
	
	try{
			fetchFunction(url,"POST", f,resultat => window.location.href = "/invoice?id="+resultat.invoice.id)
			
	 }catch(e){
		
		 console.log("origin create order invoice  :"+e)
	 }
	 
 }
 
 
 
 
 
 
 
 function updateOrderValues(){
	 
	 var tht = document.querySelector("#THT")
	 
	 var ttc = document.querySelector("#TTC")
	 var dtp = document.querySelector("#DTP")
	 tht.innerHTML = order.totalAmount.toFixed(2)
	 
	 if(order.invoice!= undefined){
		 ttc.innerHTML = order.invoice.totalAmount.toFixed(2)
		  dtp.innerHTML = order.invoice.dueToPay.toFixed(2)
	 }
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 