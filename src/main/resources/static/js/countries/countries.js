/**
 * 
 */


document.addEventListener("DOMContentLoaded", event => {

getCountries()
	init()
})


















function getCountries() {
	
	fetch("https://restcountries.com/v3.1/all")
		.then(response => response.json())
		.then(data => {
			// Utiliser les données pour obtenir les noms et codes des pays
			var countrySelect = document.querySelector("#select-country")
			
			const countries = data.map(country => ({
				name: country.name.common,
				alpha2Code: country.cca2,
				alpha3Code: country.cca3
			}));
			
			countries.forEach(country=>{
				var option = document.createElement("option")
				option.textContent = country.name
				option.setAttribute("code",country.alpha3Code)
				countrySelect.appendChild(option)
			})
			
		})
		.catch(error => {
			console.error("Une erreur s'est produite : ", error);
		});

}




function init(){
	
	var addCountry = document.querySelector("#add-country")
	addCountry.addEventListener("click", btnEvent => {
		var countrySelect = document.querySelector("#select-country")


		if (countrySelect.value != "") {
			var selectedIndex = countrySelect.selectedIndex;

			try {

				var name = countrySelect.value;
				var code = countrySelect.options[selectedIndex].attributes.code.value;

				var form = {
					name: name,
					code: code
				}

				fetch('api/countries/save', {
					method: "POST",
					body: JSON.stringify(form),
					headers: {
						"Content-Type": "application/json" // Exemple d'en-tête de type de contenu
					}

				}).then(result => {
					if (result.status == 200) { return result.json() }

					result.json().then(error => {

						console.log(error.message, "\n at " + error.dateTime)
						showToast(error.message)

					})

					throw new Error("error occured: " + result.status);

				}).then(result => {


					window.location.href = "/countries"

				}).catch(err => {

					console.log(err)
				})

			} catch (e) {

				console.log("origin add country  :" + e)
			}
		}



	})
	
}