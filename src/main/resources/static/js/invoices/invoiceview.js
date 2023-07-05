/**
 * 
 */






 
 function printInvoice(event){
	 
			  var doc = new jspdf.jsPDF;
			 // doc.setMargins(20, 20, 20, 20);
			  
            // Options de configuration pour l'impression
            var options = {
                background: 'white', // Couleur d'arrière-plan
                scale: 3 // Échelle de l'impression (plus la valeur est élevée, plus l'image sera grande)
            };

            // Sélection de la partie de la page à imprimer en PDF
            var content = document.getElementById('invoice');

	 // Génération du PDF
		 html2canvas(content).then((canvas) => {
			 // Obtenez l'image du canvas en tant que données de l'image au format PNG
			 const imageData = canvas.toDataURL('image/png');
	
			 // Ajoutez l'image au document PDF
			 doc.addImage(imageData, 'PNG', 10, 10, 190, 0);
	
			 // Sauvegardez le document PDF
			 doc.save('facture.pdf');
		 });
		 
		
		 
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 