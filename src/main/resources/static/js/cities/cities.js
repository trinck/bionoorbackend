/**
 * 
 */

document.addEventListener("DOMContentLoaded", event=>{
	
	var countryCode = document.querySelector("#country-code")
	
	cargeCities(countryCode.textContent)
})



function cargeCities(countryCode) {

	// Remplacez "YOUR_USERNAME" par votre nom d'utilisateur Geonames
	const username = "bionoor1";
	const country = countryCode.trim(); // Remplacez par le code du pays (par exemple, "FR" pour la France)

	fetch(`http://api.geonames.org/searchJSON?country=${country}&maxRows=10&username=${username}`)
		.then(response => response.json())
		.then(data => {
			const cities = data.geonames.map(city => city.name);
			
			var countrySelect = document.querySelector("#select-cities")
			cities.forEach(city=>{
				var option = document.createElement("option")
				option.textContent = city.name
				countrySelect.appendChild(option)
			})
		})
		.catch(error => {
			console.error("error : ", error);
		});

}