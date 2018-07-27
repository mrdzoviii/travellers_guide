function free(){
	var freeDiv = document.getElementById("ad_free");
	var paidDiv = document.getElementById("ad_paid");
	paidDiv.style.display = 'none';
	freeDiv.style.display = 'block';
}

function paid(){
	var freeDiv = document.getElementById("ad_free");
	var paidDiv = document.getElementById("ad_paid");
	paidDiv.style.display = 'block';
	freeDiv.style.display = 'none';
}

function change(){
	/*var toogleButton = document.getElementById("ad_type");
	if(toogleButton.value="Free"){
		free();
	}else{
		paid();
	}*/
	alert("ALLOOO");
}