/**
 * 
 */

//****set the default value parameters************ */
initPram();
initLinksDropdown();



//******************************************************************** */





function initPram(){
	
		if(localStorage.getItem("defaultBestSellerMonth") == null){
		localStorage.setItem("defaultBestSellerMonth",1)
	}
	
	if(localStorage.getItem("fufilledYear")==null){
		localStorage.setItem("fufilledYear",1)
	}
	
	if(localStorage.getItem("salesYears")==null){
		localStorage.setItem("salesYears",1)
	}
	
	if(localStorage.getItem("regionYear")==null){
		localStorage.setItem("regionYear",1)
	}

}





function initLinksDropdown(){
	updateBestSellerMonth(localStorage.getItem("defaultBestSellerMonth"), null)
	updateYearsFilter(localStorage.getItem("fufilledYear"),null, "dropdown-fulfilled-action")
	updateYearsFilter(localStorage.getItem("salesYears"),null, "dropdown-sales-action")
	updateYearsFilter(localStorage.getItem("regionYear"),null, "dropdown-region-action")
}
/*******************defaults values graphs***************************** */




//***************update link***************************** */


function updateBestSellerMonth(month, event=null){
	
	//localStorage.setItem("defaultBestSellerMonth",month)
	var text= document.querySelector("#dropdown-best-seller-action span")
	
	if(event != null){
		text.textContent = event.target.textContent
	}else{
		
		text.textContent = month == 0? "Last month":"This month"
	}
	
	
}




function updateYearsFilter(year, event=null, originId=null){
	
	if(event != null){
		var text= event.target.parentElement.parentElement.querySelector("button span")
		text.textContent = event.target.textContent
	}else{
		
		if(originId== null)throw new Error("originId must be indicated without event")
		var text = document.querySelector(`#${originId} span`)
		text.textContent = year == 0? "Last year":"This years"
	}
	
	
}


//********************************************************************** */
 
function filter(event, graphId){
 
	 switch(graphId){
		 
		 case 'top-sales' :  salesFilter(event.target.value)
							 updateBestSellerMonth(event.target.value, event)
							 break
							 
		case 'fulfilled':  ordersFulfilledGraph(event.target.value)
							 updateYearsFilter(event.target.value,event)
							 break
							 
							 
		case 'sales':  salesGraph2(event.target.value)
						updateYearsFilter(event.target.value,event)
						break
						
						
		case 'orders-region':  geoGraph(event.target.value)
								updateYearsFilter(event.target.value,event)
								break
		 default: break
	 }
 	
}
 
 
 //*****functions filters */
 
 
 
 
 
 function salesFilter(month){
	  
	 
	 url = month == 1? "api/dashboard/productsOfMonth" : "api/dashboard/productsOfMonth?month="+(new Date().getMonth()-1)
	 
	 
	
	 fetchFunction(url=url,method= "GET", null, result =>{
		
		const bestSellerBody = document.querySelector("#best-seller-body")
		bestSellerBody.innerHTML = ""
		//add spinner for waiting  load data 
		bestSellerBody.appendChild( addSpinnerTable())
		//pushdata to graph dom
		productsSalesToDom(result)
		
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
		  
		  //prespare the link
		 var linkToProduct = tds[4].querySelector(".best-seller-action")
		 linkToProduct.href = `product?id=${product.id}`
		 
		// bestSellerBody.replaceChildren(clone)
		 bestSellerBody.appendChild(clone)
	}
	
}
 
 
 
 
 
 
//*********************initialization************************************************************ */ 
 
document.addEventListener("DOMContentLoaded", event =>{
	
	var printerAllGraphs = document.getElementById("printerAllGraphsPdf")
	printerAllGraphs.addEventListener("click", event=>{
		print(event, 'allGraphs',"pdf","dashboard")
	})
	
	
	var printerAllGraphs = document.getElementById("printerAllGraphsImg")
	printerAllGraphs.addEventListener("click", event=>{
		print(event, 'allGraphs', "img","dashboard")
	})
	
	//add event listener on printer single graph
	var downloadGraphBtn = document.querySelectorAll(".download-graph-btn")
	downloadGraphBtn.forEach(btn =>{
		btn.addEventListener("click", event =>{
			
			var graphContainer = event.target.parentElement.parentElement.querySelector(".graph")
			print(event, graphContainer.id, "img",graphContainer.id)
			
		})
	})
	
	
	//init graphs*********
	ordersFulfilledGraph(localStorage.getItem("fufilledYear"))
	ordersByStausGraph()
	stockGraph()
	geoGraph(localStorage.getItem("regionYear"))
	//salesGraph()
	salesGraph2(localStorage.getItem("salesYears"))
	salesFilter(localStorage.getItem("defaultBestSellerMonth"))
	
})


 
 
 
 //**************charts************************************************************* */

async function salesGraph(){
	
	
	google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawSalesChart);
	
}




async function salesGraph2(year){
	
	const labels = ["January", "February", "March", "April","May", "June", "July","August", "September", "October","November", "December"]

	 url = year == 1? "api/dashboard/sales" : "api/dashboard/sales?year="+(new Date().getFullYear()-1)

fetchFunction(url=url,method= "GET", null, result =>{
		
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





async function geoGraph(year){
	
	
	 google.charts.load('current', {
        'packages':['geochart'],
      });
      
      	 url = year == 1? "api/dashboard/ordersByRegion" : "api/dashboard/ordersByRegion?year="+(new Date().getFullYear()-1)

      fetchFunction(url=url,method= "GET", null,(result)=>{
		  
		  google.charts.setOnLoadCallback(()=>{
			  drawRegionsMap(result)
		  });
	  })
      
      
	
}




 function drawRegionsMap(dataResult) {
	 
	 var array = [['Country', 'Orders']]
	 //push the top data lablels
	
	 
	 
	 for(let [country, cities] of Object.entries(dataResult)){
		 
		  var line = []
		 line.push(country)
		 let orders = 0
		 for(let [city, ordersRegion] of Object.entries(cities)){
			 orders+= ordersRegion.length
		 }
		
		 line.push(orders)
		 array.push(line)
		 
	 }
	 
	
	
        var data = google.visualization.arrayToDataTable(array);

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
	
	
	var Labels = []
	var array = []
	
	fetchFunction(url=`api/dashboard/productsStock`,method= "GET", null,(result)=>{
		  
		for(let [label, size] of Object.entries(result)){
			
			Labels.push(label)
			array.push(Number.parseInt(size))
		}
		
		
					
			var data = {
				 labels:Labels ,
			  datasets: [{
			    
			    data: array,
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

	
		
	  })
	
	
}







async function ordersFulfilledGraph(year){
	
	
const labels = ["January", "February", "March", "April","May", "June", "July","August", "September", "October","November", "December"]

 url = year == 1? "api/dashboard/fulfilled?fulfilled=true" : "api/dashboard/fulfilled?fulfilled=true&year="+(new Date().getFullYear()-1)

fetchFunction(url=url,method= "GET", null, result =>{
		
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
	
	
	
 

/***********init chart************************** */
 function initGrapghs(canvas,data, type="doughnut", options=null){
	


		 var  chart = Chart.getChart(canvas.id)
		if(chart == undefined){
			
			   chart = new Chart(canvas, {
	
				type: type,
			    data:data,
			    options: options
			});
			
		}else{
			
			chart.data = data
			chart.update()
		}
			
	
}