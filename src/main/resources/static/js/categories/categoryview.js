/**
 * js for category view to manage form name, discount crud
 */

addDiscount = document.querySelector(".addDiscount");



 
 function discountEditing(event)
 {
	 if(!event.target.value=="" & addDiscount.hasAttribute("disabled")){
		
		 addDiscount.removeAttribute("disabled")
	 }else if(event.target.value == "")
	 {
		addDiscount.setAttribute("disabled", "")
	 }
	 
 }