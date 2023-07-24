/**
 * 
 */

 
 
 //**************charts********************************** */


document.addEventListener("DOMContentLoaded", event =>{
	
	const DATA_COUNT = 7;
const NUMBER_CFG = {count: DATA_COUNT, min: -100, max: 100};

const labels = ["Janvier", "Fevrier", "Mars", "Avril","Mais", "Juin", "Juillet"]
const data = {
  labels: labels,
  datasets: [
    {
      label: 'Order received',
      data: [25,8,36,25,41,19,60],
      borderColor: "rgba(255,0,0)",
      backgroundColor: "rgba(255,0,0,0.2)",
    },
    {
      label: 'Order fulfilled',
      data:[45,58,36,125,69,32,69],
      borderColor: "rgb(25, 118, 210)",
      backgroundColor: "rgba(25, 118, 210, 0.18)"
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
    }
  }


	
	initGrapghs(data, "bar", options)
	
	
	//fetchFunction("api/categories/graphs/"+id, "GET", null, initGrapghs)
	
	
 
	
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