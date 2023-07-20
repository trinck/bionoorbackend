/**
 * 
 */



 function toogleEnabled(event){
	 
	const url = "api/customers/toogle/enabled?id="+event.target.id+"&enabled="+event.target.checked
	  fetchFunction(url,"POST", null, result => console.log(result))
 }