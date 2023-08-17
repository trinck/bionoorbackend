/**
 * 
 */


document.addEventListener("DOMContentLoaded", event => {

	var cancelbtns = document.querySelectorAll(".cancel")

	cancelbtns.forEach(btn => {
		btn.addEventListener("click", event => {

			var id = event.target.id
         
			fetchFunction(url = `api/orders/cancel?id=${id}`, method = "POST", null, result => {
				window.location.reload()
			})
		})
	})



})