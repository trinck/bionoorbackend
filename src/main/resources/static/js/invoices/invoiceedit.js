/**
 * 
 */
 /***variables globale*********
 ************************** */
 var discountCode;
 var orderId = document.querySelector(".order-info").id

/******************************************** */
/**initialization */
init(orderId)

/****************** */



function init(id){
	url = "/api/orders/discountCode/get?id="+id
	
	try{
				
				 fetch(url,{
					method: "GET",
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					else {
						
					  	discountCode = null
						throw new Error(response.json().message+"; status "+response.status);
					}
				})
				.then(result =>{ 
					
					discountCode = {...result}
					
				})
				.catch(error => console.log(error))
				
	 }catch(e){
		
		 alert("origin get order discountCode :"+e)
	 }	
	
	
	
}
















async function addItemTr(event){
	
	
	const template = document.querySelector('#template');
	const discountList = document.querySelector("#items-table")
	 const clone = template.content.cloneNode(true);
	 
	discountList.appendChild(clone)
} 


async function onChangeItemName(event){
	
	  Url = "/api/products/search?name="+event.target.value
	
	if (event.target.value === null || event.target.value.trim() === ""){
					
						return alert("Product name cann't be empty")
		}
	
	
	try{
				
				 fetch(Url,{
					method: "GET",
					
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					else {
						
						throw new Error(response.json()+"; status "+response.status);
					}
				})
				.then(result =>{ 
					
					if(result.length == 0){
						alert("no product with this name")
					}else{
						
						alert("product code "+ result[0].code+" found")
						
						addOrderItem(result[0], event.target)
					}
					
				})
				.catch(error => alert(error))
				
			
	 }catch(e){
		
		 alert("origin search product :"+e)
	 }	
	

}



async function findDiscountCode(event){
	
	url = "/api/discounts/find/code?code="
	
	var code = event.target.value
	/**assure that discount code already present or no for delete before adding *********
	****new discount******************************************************************* */
	if ( code.trim() === "" ){
					
				if(discountCode === undefined || discountCode === null){
					
					return alert("dicount code field is empty")
				}	
			
			return deleteDiscountCode()
		}
	/***************************************************************************************** */
	url = url+code
	
	try{
				
				 fetch(url,{
					method: "GET",
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					else {
						
						//delete other if present
						deleteDiscountCode()
						throw new Error(response.json()+"; status "+response.status);
						
					}
				})
				.then(result =>{ 
						
						alert("discount code "+ result.code+" found")
						//add to order right now
						addDiscountCode(result)
						
						
				})
				.catch(error => alert(error))
				
			
	 }catch(e){
		
		 alert("origin search discount code :"+e)
	 }	
	 
}




//add discount code to order
function addDiscountCode(findResult){
	url = "/api/orders/discountCode/add"
	
	var f =new FormData()
	f.set("discountCodeId",findResult.id )
	f.set("id", orderId)
	var discountCodeValue = document.querySelector(".discountCode-value")
	
	try{
				
				 fetch(url,{
					method: "POST",
					body:f
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					else {
					
						throw new Error(response.json()+"; status "+response.status);
					}
				})
				.then(result =>{ 
						
						alert("discount code "+ result.code+" added")
						discountCodeValue.innerHTML = result.discount+" MAD"
						discountCode = {...result}	
						
				})
				.catch(error => alert(error))
				
			
	 }catch(e){
		
		 alert("origin add discount code :"+e)
	 }	
	
	
	
}

//delete discount code from order
function deleteDiscountCode(){
	
	url = "/api/orders/discountCode/delete?id="
	url = url+orderId
	
	if(discountCode=== null || discountCode === undefined){
		
			return false
	}
	
	try{
				
				 fetch(url,{
					method: "GET",
					
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					else {
						
						throw new Error(response.json()+"; status "+response.status);
					}
				})
				.then(result =>{ 
						
						var discountCodeValue = document.querySelector(".discountCode-value")
						discountCodeValue.innerHTML = "N/A"
						discountCode = null
						
				})
				.catch(error => alert(error))
				
			
	 }catch(e){
		
		 alert("origin delete discount code :"+e)
	 }	
	
}




async function updateQauntity(event){
	url = "/api/orders/orderItems/put/quantity"
	
	var target = event.target
	var quantity = target.value
	
	var id = target.parentElement.parentElement.id
	
	var f = new FormData()
	f.set("id", id)
	f.set("quantity", quantity)
	
	try{
		
		if (quantity === "") {
			throw Error("value undefined")
		}
		
		//update quantity of item
		
		fetch(url, {
			
			method: "POST",
			body: f
			
		}).then(response =>{
			
			if(response.status == 200){return response.text()}
					else {
						
						throw new Error(response.json().message+"; status "+response.status);
					}
			
		} ).then(resultat=>{
			
			//update totalAmount with the new quantity item
			updateTotaAmount(event)
			
		}).catch(error => alert(error))
		
	}catch(e){
		alert("origin quantity changing: "+e)
	}
	
}






function updateTotaAmount(event){
	
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
		
		fetch(url, {
			
			method: "POST",
			body: f
			
		}).then(response =>{
			
			if(response.status == 200){return response.json()}
					else {
						
						throw new Error(response.json().message+"; status "+response.status);
					}
			
		} ).then(resultat=>{
			
				Tr.remove()
			
		}).catch(error => alert(error))
		
	}catch(e){
		alert("origin deleting item: "+e)
	}

	
}








 function addOrderItem(product, node){
	url = "/api/orders/orderItems/save"
	
	//retrieve eleme
	var Tr = node.parentElement.parentElement
	var code = Tr.querySelector(".item-product-code")
	var price = Tr.querySelector(".item-product-price")
	var quantity = Tr.querySelector(".item-quantity")
	var totaAmount = Tr.querySelector(".item-totalAmount")					
	
	
	var orderInfo = document.querySelector(".order-info")
	var f = new FormData()
	
	f.set("order",Number(orderInfo.id) )
	f.set("quantity", quantity.value)
	f.set("product",product.id)
	f.set("id", Tr.id)
	
	
		
	try{
				 fetch(url,{
					method: "POST",
					body: f
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					else {
						
						throw new Error(response.json().message+"; status "+response.status);
					}
				})
				.then(result =>{ 
					
						alert("item added")
						code.innerHTML = product.code
						price.innerHTML = product.price
						quantity.value = result.quantity
						totaAmount.innerHTML = (product.price * result.quantity).toFixed(2)
						Tr.id = result.id
						quantity.onchange = updateQauntity;
						
				})
				.catch(error => alert(error))
				
	 }catch(e){
		
		 alert("origin added item :"+e)
	 }	
	
}






 
 
async function updateOrderStatus(element, id){
	 
	 url = "/api/orders/put/status"
	var f = new FormData()
	f.set("status",element.value)
	f.set("id", id)
	
	
	
	try{
				 fetch(url,{
					method: "POST",
					body: f,
					
					
				}).then(response => {
					if(response.status == 200){return response.text()}
					else {
						alert(response.text())
						throw new Error('error occured '+response.status);
					}
				})
				.then(result => alert("status updated"))
				.catch(error => alert(error))
				
			
	 }catch(e){
		
		 alert("origin update order status  :"+e)
	 }
	 
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 