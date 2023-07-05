/**
 * 
 */

 
 
async function onMediaHover(event){
	 
	
	var parent = event.target.parentElement
	var mediaOpt = parent.querySelector(".media-opt") 
	
	mediaOpt.classList.add("media-opt-scal")
	
 }
 
 
 
async function onMediaLeave(event){
	var parent = event.target
	
	var mediaOpt = parent.querySelector(".media-opt") 
	
	mediaOpt.classList.remove("media-opt-scal")
	
 }