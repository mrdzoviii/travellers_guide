function handleSaveRequest(xhr, status, args) {
	if(args.saved) {
		getWeather();
		PF('forecast').show();
	}
}

function getWeather() {
	var req = new XMLHttpRequest();
	req.onreadystatechange = function () {
		if(this.readyState == 4 &amp;&amp; this.status == 200) {
			var jsonObj = JSON.parse(this.responseText);
			document.getElementById("icon").src = "http://openweathermap.org/img/w/" + jsonObj.weather[0].icon + ".png";
			document.getElementById("countryIcon").src = "http://openweathermap.org/images/flags/" + jsonObj.sys.country.toLowerCase() + ".png";
			document.getElementById("city").innerHTML = jsonObj.name + ", " + jsonObj.sys.country;
			document.getElementById("weather").innerHTML = jsonObj.weather[0].description;
			document.getElementById("temperature").innerHTML = jsonObj.main.temp + " °С"; 
			document.getElementById("minMaxTemperature").innerHTML = "Temperature  [" + jsonObj.main.temp_min+ " / " + jsonObj.main.temp_max + "] °С";
			document.getElementById("humidity").innerHTML = "Humidity: " + jsonObj.main.humidity + "%";
			document.getElementById("pressure").innerHTML = "Pressure: " + jsonObj.main.pressure + "mb";
		}
	};
	
	var city = document.getElementsByClassName("destination")[0].value;
	req.open("GET", "http://api.openweathermap.org/data/2.5/weather?q=" + city +"&amp;units=metric&amp;lang=hr&amp;APPID=b88ef5c9ad183e68a5518e40dabdf821", true);
	req.send();
}