/**
 * 
 */

 
 
 
document.addEventListener("DOMContentLoaded", event=>{
	
	var AllBioDropdownToggle = document.querySelectorAll(".bio-dropdown-toggle")
	
	AllBioDropdownToggle.forEach(dropdown =>{
		dropdown.addEventListener("click",(event) =>{
		
		
		
		var brother = dropdown.nextElementSibling
		if(brother.classList.contains("bio-dropdown-menu")){
			brother.classList.toggle("show")
		}
		//var bioDropdownToggle = document.querySelector(".bio-dropdown-toggle + .dropdown-menu")
		
		})
	})
	
	var dropdownMenus = document.querySelectorAll(".dropdown-menu")
	
	dropdownMenus.forEach(menu =>{
		
		menu.addEventListener("click", ()=>{
			
			menu.classList.toggle("show")
		})
		
	})
	
	//bioDropdownToggle.
})




//*****************************spinner grow */

function addSpinnerTable(type="border", color= "success"){
	
	var tr = document.createElement("tr")
	tr.className = "border-none"
	
	var td = document.createElement("td")
	td.className = "text-center"
	td.colSpan = 5
	
	var spinner = document.createElement("div")
	var span = document.createElement("span")
	span.className = "visually-hidden"
	span.innerHTML = "Loading..."
	
	spinner.className = `spinner-${type} text-${color}`
	spinner.role = "status"
	
	spinner.appendChild(span)
	td.appendChild(spinner)
	tr.appendChild(td)
	return tr;
	
	
}


