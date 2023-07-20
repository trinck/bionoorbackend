/**
 * 
 */

 

 function deleteDiscountElm(event){
	 
	 event.target.remove()
 }
 
 
 function emailAction(event){
	 
	  var select = document.querySelector("#emailActionSelect")
	 fetchFunctionJson(url=select.value, method="GET", body= null ,caller= (response)=>{
		
		alert(response.message)
	}) 
	 
 }
 
 
 function addProduct(event){
	 
	 var input = document.querySelector("#input-product")
	 
	 if(input.value == ""){
		 return false
	 }
	 
	 var productList = document.querySelector("#products")
	 var discountable = document.createElement("span")
	 discountable.classList.add("discountable")
	 discountable.addEventListener("click",deleteDiscountElm )
	 
	 
	 discountable.innerHTML = input.value
	 
	 input.value = ""
	 
	 productList.appendChild(discountable)
	 
	 
 }
 
 
 function addCategory(event){
	 var input = document.querySelector("#input-category")
	if(input.value == ""){
		 return false
	 }
	 
	  var categories = document.querySelector("#categories")
	  var discountable = document.createElement("span")
	  discountable.classList.add("discountable")
	 discountable.addEventListener("click",deleteDiscountElm )
	  discountable.innerHTML = input.value
	  
	  input.value = ""
	  
	  categories.appendChild(discountable)
 }
 
 
 function createDiscount(event){
	 
	 
	 url = "/api/discounts/customers/add"
	 event.preventDefault()
	 
	 var form = document.querySelector("#form-modal")
	 
	 var formData = new FormData(form)

	 var data = {	endDate: formData.get("endDate"),
	 				code: formData.get("code"),
	 				discount: formData.get("discount"),
	 				type: formData.get("type"),
	 				unique: formData.get("unique") === "true"?true: false,
	 				actif: formData.get("actif") === "true"?true: false,
	 				customerId: formData.get("customerId")
	 			}
	 			
		
	 			
	  var categories = document.querySelector("#categories")
	 var products = document.querySelector("#products")
	 
	 var listP = []
	 var listC = []
	for(let p of products.children){
		
		listP.push(p.innerHTML)
	}
	
	for(let c of categories.children){
		
		listC.push(c.innerHTML)
	}
	 
	 
	 data.categories = listC
	 data.products = listP
	 
	fetchFunctionJson(url=url, method="POST", body= JSON.stringify(data) ,caller= (response)=>{
		
		console.log(response)
	})

 }
 
 
 
 
 
 
 
 