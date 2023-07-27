/**
 * 
 */

 
 
 //**************charts********************************** */


document.addEventListener("DOMContentLoaded", event =>{
	
	const DATA_COUNT = 7;
const NUMBER_CFG = {count: DATA_COUNT, min: -100, max: 100};

const labels = ["Janvier", "Fevrier", "Mars", "Avril","Mais", "Juin", "Juillet","Aout", "Septembre", "Octobre","Novembre", "Decembre"]

fetchFunction(url="api/dashboard/fulfilled?fulfilled=true&annee=2023",method= "GET", null, result =>{
	
	
	
		
		var DataFulfilled =  toGraphData(labels, result.fulfilled)
		var DataReceived = toGraphData(labels, result.ready)
	
		var toMAtchFulfilled =toMatch(labels, DataFulfilled)
		var toMatchReceived = toMatch(labels, DataReceived)
		
		console.log(...toMAtchFulfilled.entries())
		
	const data = {
	  labels: labels,
	  datasets: [
	    {
	      label: 'Order fulfilled',
	      data: [...toMAtchFulfilled.values()],
	      borderColor: "rgba(255,0,0)",
	      backgroundColor: "rgba(255,0,0,0.2)",
	    },
	    
	    
	    {
	      label: 'Order received',
	      data: [...toMatchReceived.values()],
	      borderColor: "rgb(22, 102, 182)",
	      backgroundColor: "rgba(22, 102, 182,0.2)",
	    }
	  ]
	};
	
	
	
	
options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Report on Orders fulfilled'
      }
    },
    
   
  }


	initGrapghs(data, "bar", options)
	
})




function toGraphData(labels, inputDto){
	
	var data = new Map()
	for(const [key, value] of Object.entries(inputDto) ){
		
		data.set(labels[parseInt(key)],value.length )
	}
	
	return data;
}
	
	
	
function toMatch(labels, data){
	
	
	var toMatchData = new Map()
	for(let value of labels){
			toMatchData.set(value, 0)
		}
	
	data.forEach((value, key) => {
		if (toMatchData.has(key)) {
			toMatchData.set(key, value);
		}
		
		
	});
	
	return toMatchData;
}
	
	
	
 
	
})

/***********init graphs************************** */
 function initGrapghs(data, type="doughnut", options=null){
	
	
	const canvas = document.getElementById("graph-orders-fulfilled");

	const chart = new Chart(canvas, {
	
				type: type,
			    data:data,
			    options: options
			});
	
}