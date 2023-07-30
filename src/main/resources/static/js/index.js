/**
 * 
 */
 
 
 
  //******************************************** */
 function showToast(message, nature=1){
	
	const template = document.querySelector('#toast-template');
	var toastContainer = document.querySelector(".toast-container")
	var clone = template.content.cloneNode(true)
	var taost = clone.querySelector(".toast")
	var toastMessage = taost.querySelector(".toast-message")
	toastMessage.innerHTML = message
	toastContainer.appendChild(taost)
	
	const toastBootstrap = bootstrap.Toast.getOrCreateInstance(taost)
		
	 switch(nature){
		 case 1: toastBootstrap.show();  break;
		 case 2: break;
		 case 3: break;
		 default : break;
	 }
 }
 
 
 
 
 
 
 //the generic method to consume api
 
 async function fetchFunction(url, method="POST", body=null,  caller){
	 
	
	 fetch(url,{
					method: method,
					body: body
					
					
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					
					
					response.json().then(error =>{
						
						console.log(error.message, "\n at "+ error.dateTime)
						 showToast(error.message)
						
					})
					
					throw new Error("error occured: "+response.status);	
					
					
				})
				.then(caller)
				.catch(error => {
					console.log(error)
				})
	 
 }
 
 
 
 
 
 async function fetchFunctionJson(url, method="POST", body=null,  caller){
	 
	
	 fetch(url,{
					method: method,
					body: body,
					headers:{
						"Content-Type": "application/json"
					}
					
					
					
				}).then(response => {
					if(response.status == 200){return response.json()}
					
					
					response.json().then(error =>{
						
						console.log(error.message, "\n at "+ error.dateTime)
						 showToast(error.message)
						
					})
					
					throw new Error("error occured: "+response.status);	
					
					
				})
				.then(caller)
				.catch(error => {
					console.log(error)
				})
	 
 }
 
 
 
 
 