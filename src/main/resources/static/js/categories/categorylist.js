/**
 *js for category'list 
 */
 
 
 async function deleteCategory(id){
	 var url = "/api/categories/delete?id="+id
	
	try{
				 fetch(url,{
					method: "GET"
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					else {
						console.log(response.json())
						throw new Error('error occured on deleting category:'+id)
					}}).then(e => {
						alert('deleting success: '+e.name)
						//remove row from table list
						removeRowCategory(e.name)
						
					}).catch(error => alert(error))
					
				
			
	 }catch(e){
		 alert(e)
	 }
 }
 
 
 function removeRowCategory(name){
	 const tr = document.querySelector("tr[id = '"+name+"']")
	
    animateDeletting(tr)
	setTimeout(()=> tr.remove(), 500)
	
 }
 
 
 async function animateDeletting(tr){
	 
	  var keyframDeletting = [
		 {transform: "translateX(0px)"},
		  {transform: "translateX(-115%)"}
	 ]
	 
	 var optionAnimation ={
		 duration: 500,
		 
	 }
	  
	 tr.animate(keyframDeletting, optionAnimation)
 }
 
 