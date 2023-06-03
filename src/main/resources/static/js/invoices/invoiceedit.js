/**
 * 
 */
 

async function addItemTr(event){
	
	
	const template = document.querySelector('#template');
	const discountList = document.querySelector("#items-table")
	 const clone = template.content.cloneNode(true);
	 
	discountList.appendChild(clone)
} 


async function onChangeItemName(event){
	
	  Url = "/api/products/search?name="+event.target.value
	
	
	
	try{
				
				if (event.target.value === null || event.target.value.trim() === ""){
					
						throw "Product name cann't be empty"
					}
				
				
				
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


function updateQauntity(event){
	
	
	var target = event.target
	var quantity = target.value
	
	var id = target.parentElement.parentElement.id
	
	alert("quantity "+quantity+" id = "+id)
	
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
						totaAmount.innerHTML = (product.price * result.quantity).toFixed(2)+" MAD"
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