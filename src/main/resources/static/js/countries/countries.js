/**
 * 
 */


document.addEventListener("DOMContentLoaded", event => {

	var addCountry = document.querySelector("#add-country")
	addCountry.addEventListener("click", btnEvent => {
		var countrySelect = document.querySelector("#select-country")
		
			/*try {
				fetchFunction(url, "GET", null, resultOrder => order = { ...resultOrder })

			} catch (e) {

				console.log("origin get order  :" + e)
			}*/
			
			console.log(countrySelect.option)
		

	})
})