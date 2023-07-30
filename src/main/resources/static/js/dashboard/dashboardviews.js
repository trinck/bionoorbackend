/**
 * 
 */

 
 
 
document.addEventListener("DOMContentLoaded", event =>{
	
	ordersFulfilledGraph()
	ordersByStausGraph()
	stockGraph()
})


 
 
 
 //**************charts********************************** */

async function stockGraph(){
	
	
var data = {
	 labels: [
    
  ],
  datasets: [{
    label: 'Orders By Status',
    data: [45,85,34],
    backgroundColor: [
      'rgba(255, 99, 132,0.9)',
      'rgba(54, 162, 235,0.9)',
      'rgba(255, 205, 86,0.9)'
      
    ],
    hoverOffset: 4
  }]
}


	
options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Report on Stock status'
      }
    },
    
   
  }

	 const canvas = document.getElementById("graph-stock");
	initGrapghs(canvas,data, "doughnut", options)

	
}







async function ordersFulfilledGraph(){
	
	
const labels = ["Janvier", "Fevrier", "Mars", "Avril","Mais", "Juin", "Juillet","Aout", "Septembre", "Octobre","Novembre", "Decembre"]

fetchFunction(url="api/dashboard/fulfilled?fulfilled=true&annee=2023",method= "GET", null, result =>{
		
		var DataFulfilled =  toGraphData(labels, result.fulfilled)
		var DataReceived = toGraphData(labels, result.ready)
	
		var toMAtchFulfilled =toMatch(labels, DataFulfilled)
		var toMatchReceived = toMatch(labels, DataReceived)
		
	
		
	const data = {
	  labels: labels,
	  datasets: [
	    {
		      label: 'Order fulfilled',
		      data: [...toMAtchFulfilled.values()],
		      borderColor: "rgb(255,0,0)",
		      backgroundColor: "rgba(255,0,0,0.4)",
	    },
	    
	     {
			 type:"line",
		      label: 'Sales',
		      data: [...toMAtchFulfilled.values()],
		      borderColor: "rgba(0, 128, 0, 0.9)",
		      backgroundColor: "rgba(0, 128, 0, 0.9)",
	    }
	    
	    ,
	    
	    
	    {
		      label: 'Order received',
		      data: [...toMatchReceived.values()],
		      borderColor: "rgb(22, 102, 182)",
		      backgroundColor: "rgba(22, 102, 182,0.4)",
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


 const canvas = document.getElementById("graph-orders-fulfilled");
	initGrapghs(canvas,data, "bar", options)
	
})

}




async function ordersByStausGraph(){
	
fetchFunction(url="api/dashboard/ordersByStatus",method= "GET", null, result =>{

var data = {
	 labels: [
    
  ],
  datasets: [{
    label: 'Orders By Status',
    data: [],
    backgroundColor: [
      'rgba(255, 99, 132,0.9)',
      'rgba(54, 162, 235,0.9)',
      'rgba(255, 205, 86,0.9)',
      'rgba(220, 109, 56,0.9)',
      'rgba(0, 128, 0, 0.9)'
    ],
    hoverOffset: 4
  }]
}


 for(const [key, value] of Object.entries(result) ){
	 
	 data.labels.push(key)
	 data.datasets[0].data.push(value.length)
 }

	
options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Report on Orders by status'
      }
    },
    
   
  }

	 const canvas = document.getElementById("graph-orders-status");
	initGrapghs(canvas,data, "pie", options)
	
})

}


























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
	
	
	
 

/***********init graphs************************** */
 function initGrapghs(canvas,data, type="doughnut", options=null){
	

	const chart = new Chart(canvas, {
	
				type: type,
			    data:data,
			    options: options
			});
	
}