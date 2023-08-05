/**
 * 
 */

//****set the default value parameter************ */
if(localStorage.getItem("defaultBestSellerMonth") == null){
	localStorage.setItem("defaultBestSellerMonth",0)
}

/*******************defaults values graphs***************************** */
updateBestSellerMonth(localStorage.getItem("defaultBestSellerMonth"), null)



//******************************************** */


function updateBestSellerMonth(month, event=null){
	
	localStorage.setItem("defaultBestSellerMonth",month)
	var text= document.querySelector("#dropdown-best-seller-action span")
	
	if(event != null){
		text.textContent = event.target.textContent
	}else{
		
		text.textContent = month == 0? "Last month":"This month"
	}
	
	
}



 
function filter(event, graphId){
 
 if(graphId === 'top-sales'){
	 
	 salesFilter(event.target.value)
	 updateBestSellerMonth(event.target.value, event)
	 
 }
 	
}
 
 
 //*****functions filters */
 
 
 
 
 
 function salesFilter(month){
	  
	 
	 url = month == 1? "api/dashboard/productsOfMonth" : "api/dashboard/productsOfMonth?month="+(new Date().getMonth()-1)
	 
	 
	
	 fetchFunction(url=url,method= "GET", null, result =>{
		
		const bestSellerBody = document.querySelector("#best-seller-body")
		bestSellerBody.innerHTML = ""
		bestSellerBody.appendChild( addSpinnerTable())
		setTimeout(()=>{
			productsSalesToDom(result)
		}, 500)
		
	 })
 }
 
 
 function productsSalesToDom(bestSeller){
	
	const template = document.querySelector('#best-seller-template');
	const bestSellerBody = document.querySelector("#best-seller-body")
	bestSellerBody.innerHTML= ""
	for(let product of bestSeller.products){
		
		const clone = template.content.cloneNode(true);
		 let tds = clone.querySelectorAll("td");
		
		 let img = tds[0].querySelector("#best-seller-img")
		
		 
		 
		 img.src = product.images[0].url
		tds[1].innerHTML = product.name
		tds[2].innerHTML = (product.earned).toFixed(2)
		
		 var stockSpan = document.createElement('span')
		 stockSpan.className= "badge shadow"
		  var stockstatusClass= product.quantity>= bestSeller.stockAverage? 'bg-success': product.quantity == 0? 'bg-danger': 'bg-warning'
		  var stockmessage =  product.quantity == 0? 'Out of stock': 'Available'
		  
		  stockSpan.classList.add(stockstatusClass)
		  
		  stockSpan.innerHTML =  `${product.quantity >0 ?product.quantity: '' } ${stockmessage}` 
		  tds[3].appendChild(stockSpan)
		 
		// bestSellerBody.replaceChildren(clone)
		 bestSellerBody.appendChild(clone)
	}
	
	
	
}
 
 
 
 
 
 
//*************************** */ 
 
document.addEventListener("DOMContentLoaded", event =>{
	
	ordersFulfilledGraph()
	ordersByStausGraph()
	stockGraph()
	geoGraph()
	//salesGraph()
	salesGraph2()
	salesFilter(localStorage.getItem("defaultBestSellerMonth"))
	
})


 
 
 
 //**************charts********************************** */

async function salesGraph(){
	
	
	google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawSalesChart);
	
}


async function salesGraph2(){
	
	const labels = ["January", "February", "March", "April","May", "June", "July","August", "September", "October","November", "December"]

fetchFunction(url="api/dashboard/sales",method= "GET", null, result =>{
		
		var dataSales =  toGraphData(labels, result)
	
		var toMAtchSales =toMatch(labels, dataSales)
	
		
	
		
	const data = {
	  labels: labels,
	  datasets: [
	    
	     {
		      label: 'Sales',
		      data: [...toMAtchSales.values()],
		      borderColor: "rgba(0, 128, 0, 0.9)",
		      backgroundColor: "rgba(0, 128, 0, 0.9)",
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
        text: 'Report on Sales'
      }
    },
    
   
  }


 const canvas = document.getElementById("graph-sales");
	initGrapghs(canvas,data, "line", options)
	
})

}




async function geoGraph(){
	
	
	 google.charts.load('current', {
        'packages':['geochart'],
      });
      google.charts.setOnLoadCallback(drawRegionsMap);
	
}


 function drawRegionsMap() {
        var data = google.visualization.arrayToDataTable([
          ['Country', 'Customers'],
          ['Morocco', 900],
          ['United States', 700],
          ['Gabon', 500],
          ['France', 600],
          ['Senegal', 200],
          ['Algeria', 700]
        ]);

        var options = {};

        var chart = new google.visualization.GeoChart(document.getElementById('graph-geo'));

        chart.draw(data, options);
      }






function drawSalesChart() {
        var data = google.visualization.arrayToDataTable([
          ['Year', 'Sales', 'Expenses'],
          ['2004',  1000,      400],
          ['2005',  1170,      460],
          ['2006',  660,       1120],
          ['2007',  1030,      540]
        ]);

        var options = {
          title: 'Company Performance',
          curveType: 'function',
          legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('graph-sales'));

        chart.draw(data, options);
      }


//********************************************************************* */

async function stockGraph(){
	
	
var data = {
	 labels: [
    
  ],
  datasets: [{
    
    data: [230,1000,500],
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
      }
    },
    
   
  }

	 const canvas = document.getElementById("graph-stock");
	initGrapghs(canvas,data, "doughnut", options)

	
}







async function ordersFulfilledGraph(){
	
	
const labels = ["January", "February", "March", "April","May", "June", "July","August", "September", "October","November", "December"]

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