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
 
 
   function dateCorrectDigit(date, targe= "input"){
	 
	 var month = date.getMonth()+1//add 1 because month start to 0
	 var day = date.getDate()
	 var year = date.getFullYear()
	 month = month >= 10? month: '0'+month
	 day  = day >= 10? day: '0'+day
	 var correctDate = ""
	 switch(targe){
		 case "input":  correctDate = year+'-'+month+'-'+day; break;
		 case "string": correctDate = day+"-"+month+"-"+year; break;
		 default: correctDate = date.toString(); break;
	 }
	
	 
	 return correctDate
 }
 
 
  function discountSubmit(id){
	 
	  addDiscount(id)
	 
	 	
 }
 
 //******************************************** */
 function showToast(message, nature=1){
	 switch(nature){
		 case 1: alert(message);  break;
		 case 2: break;
		 case 3: break;
		 default : break;
	 }
 }
 
 
 
 //************************************************ */
 
 async function addDiscount(id){
	 
	discountUrl = "/api/discounts/add"
	var f = new FormData(formdiscount)
	f.set("categoryId",id)
	
	
	 try{
				 fetch(discountUrl,{
					method: "POST",
					body: f,
					
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					else {
						console.log(response.json())
						throw new Error('error occured '+response.status);
					}
				})
				.then(discountToDom)
				.catch(error => showToast(error,1))
				
			
	 }catch(e){
		
		 showToast("origin adding discount :"+e,1)
	 }
 }
 
 
 
 
 
async function toggleDiscountActif(event){
	
	event.preventDefault()
	var url = "/api/discounts/toggleActif"
	
	var form = new FormData()
	
	form.set("toggle",event.target.checked )
	form.set("discountId",event.target.value)
	
	
	 try{
				 fetch(url,{
					method: "POST",
					body: form
					
					
					
				}).then(response => response.json)
				.catch(error => showToast(error,1))
					
				
			
	 }catch(e){
		 showToast("origin adding discount :"+e,1)
	 }
}




//******************change category name */

async function changeName(event, id){
	
	var url = "/api/categories/put/name"
	var f = new FormData()
	f.set("id",id)
	f.set("name", event.target.value)


	try{
				 fetch(url,{
					method: "POST",
					body: f
					
				}).then(response => {
					if(response.status == 200){return response.text()}
					else {
						console.log(response.json())
						throw new Error('error occured '+response.status)
					}}).then(e => showToast("name mis à jour to :"+e)
						
					).catch(error => showToast(error))
					
				
			
	 }catch(e){
		 showToast("origin adding discount :"+e)
	 }
	
}



//****************function to update category fields */

//********function to add new discountcode in dom */

function discountToDom(discount){
	
	const template = document.querySelector('#template');
	const discountList = document.querySelector(".discountsList")
	 const clone = template.content.cloneNode(true);
	 let li = clone.querySelectorAll("span");
	 let check =clone.querySelector(".discount-check")
	 
	 li[0].textContent = discount.code
	 li[1].textContent = dateCorrectDigit(new Date(discount.endDate), "string")
	check.checked = discount.actif
	check.value = discount.id
	discountList.appendChild(clone)
}

function updateName(name){
	
	
}
 
 