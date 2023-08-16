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
	
	
	
	
	//******************************controll formulair */
	
	 
	 var sortInputs = document.querySelectorAll(".pageNumber")
	 
	  var searchBtns = document.querySelectorAll(".search-btn")
	  
	
	  
	  searchBtns.forEach(btn =>{
		  
				  btn.addEventListener("click", event2 =>{
				 
					 var form = btn.closest("form")
					 form.submit()
					 
				 })
		  
	  })
	  
	 
	  sortInputs.forEach(sortItem =>{
		  
			  sortItem.addEventListener("change", event3 =>{
			 
				 var form = sortItem.closest("form")
				 form.submit()
				 
			 })
	  })
	  
//************************************************************************************************** */	
	 
	
	
})




//*****************************spinner grow ***********************/

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


//************************Printer********************************** */

 async function print(event, id, nature="pdf",name="facture"){

			  var doc = new jspdf.jsPDF;
			 // doc.setMargins(20, 20, 20, 20);
			  
            // Options de configuration pour l'impression
            var options = {
                background: 'white', // Couleur d'arrière-plan
                scale: 3 // Échelle de l'impression (plus la valeur est élevée, plus l'image sera grande)
            };

            // Sélection de la partie de la page à imprimer en PDF
            var content = document.getElementById(id);

	 // Génération du PDF
		 html2canvas(content).then((canvas) => {
			 // Obtenez l'image du canvas en tant que données de l'image au format PNG
			 const imageData = canvas.toDataURL('image/png');
	
	        if(nature == "pdf"){
				 // Ajoutez l'image au document PDF
			 doc.addImage(imageData, 'PNG', 10, 10, 190, 0);
	
			 // Sauvegardez le document PDF
			 doc.save(`${name}.pdf`);
			 
			 return
			}
			
			//print image instead
			 const downloadLink = document.createElement('a');
			 downloadLink.href = imageData;
			 downloadLink.download = `${name}.png`;
			 downloadLink.click();
			
			
		 });
		 
		
		 
 }
 
 
 
 
 
 
 
 
 
//*****************************controll form search********************************************* */




