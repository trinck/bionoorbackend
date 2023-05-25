/**
 * 
 */
 
 
var input = document.querySelector('input[type=file]');
var img = document.querySelector('#preview');
var label = document.querySelector("#labelfile")

input.addEventListener('change', updateImageDisplay);



function updateImageDisplay()
{
	img.src = "";
	
	var curFiles = input.files;
	if(curFiles.length === 0) {
    label.textContent = '0 file selected for upload';
  } else{
	  img.src =  window.URL.createObjectURL(curFiles[0]);
	  label.textContent = curFiles.length + ' file selected'
  }
}




 /**
 * 
 */