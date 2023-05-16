/**
 * 
 */
 
 
var input = document.querySelector('input[type=file]');
var label = document.querySelector('#labelfile');


input.addEventListener('change', updateImageDisplay);



function updateImageDisplay()
{
	var curFiles = input.files;
	if(curFiles.length === 0) {
    label.textContent = '0 files selected for upload';
  } else{
	  
	  label.textContent = curFiles.length + ' files selected'
  }
}