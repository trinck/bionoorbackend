/**
 * js for category view to manage form name, discount crud
 */


addDiscountSubmitter = document.querySelector(".addDiscount")

var formdiscount = document.querySelector("#formDiscount")
var inputDate = document.querySelector("input[type='date']")
 
inputDate.min = dateCorrectDigit(new Date())



 
 function discountEditing(event)
 {
	 if(!event.target.value=="" & addDiscountSubmitter.hasAttribute("disabled")){
		
		 addDiscountSubmitter.removeAttribute("disabled")
	 }else if(event.target.value == "")
	 {
		addDiscountSubmitter.setAttribute("disabled", "")
	 }
	 
 }
 
 
   function dateCorrectDigit(date){
	 
	 var month = date.getMonth()+1//add 1 because month start to 0
	 var day = date.getDate()
	 var year = date.getFullYear()
	 month = month >= 10? month: '0'+month
	 day  = day >= 10? day: '0'+day
	 
	 correctDate = year+'-'+month+'-'+day
	 
	 return correctDate
 }
 
 
  function discountSubmit(id){
	 
	  addDiscount(id)
	 
	 	
 }
 
 
 function showToast(message){
	 alert(message)
 }
 
 
 
 async function addDiscount(id){
	 
	discountUrl = "http://localhost:8083/api/discounts/add"
	var f = new FormData(formdiscount)
	f.set("categoryId",id)
	
	console.log(...f.entries())
	
	 try{
				 fetch(discountUrl,{
					method: "POST",
					body: f,
					
					
				}).then(response => response.json)
				.then(json => console.log(json))
				.catch(showToast)
					
				
				//let result = await response.json()
				
				//if(response.status == 200){showToast("new discount added")}
				//else{showToast("error adding discount : "+response.status)}
			
	 }catch(e){
		 console.log(e)
	 }
 }
 
 