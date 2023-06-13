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
 

 
 
 //************************************************ */
 
 async function addDiscount(id){
	 
	url = "/api/discounts/add"
	var f = new FormData(formdiscount)
	f.set("categoryId",id)
	
	
	 try{
				 
				 fetchFunction(url, "POST", f,discountToDom)
				 
				 
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
		 
		 fetchFunction(url, "POST", form, resultat =>{} )
		 		
	 }catch(e){
		 showToast("origin toggle discountActif :"+e,1)
	 }
}




//******************change category name */

async function changeName(event, id){
	
	var url = "/api/categories/put/name"
	var f = new FormData()
	f.set("id",id)
	f.set("name", event.target.value)

//fetchFunction
	try{
				
				fetchFunction(url, "POST", f, resultat =>{
					
					
				})
				
			
	 }catch(e){
		 showToast("origin changing category name :"+e)
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

 