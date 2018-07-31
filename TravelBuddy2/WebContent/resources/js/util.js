/**
 * 
 */
function handleSaveRequest(xhr, status, args) {
		if(args.saved) {
			getWeather();
			PF('forecast').show();
		}
	}
function getWeather() {
		var req = new XMLHttpRequest();
		var error=document.getElementById("error");
		var date=document.getElementById("weather-table");
		req.onreadystatechange = function () {
			if(this.readyState == 4 && this.status == 200) {
				var jsonObj = JSON.parse(this.responseText);
				document.getElementById("icon").src = "http://openweathermap.org/img/w/" + jsonObj.weather[0].icon + ".png";
				document.getElementById("countryIcon").src = "https://www.countryflags.io/" + jsonObj.sys.country.toLowerCase() + "/flat/64.png";
				document.getElementById("city").innerHTML = jsonObj.name + ", " + jsonObj.sys.country;
				document.getElementById("weather").innerHTML = jsonObj.weather[0].description;
				document.getElementById("temperature").innerHTML = jsonObj.main.temp + " °С"; 
				document.getElementById("minMaxTemperature").innerHTML = "Min/max temperature:  [" + jsonObj.main.temp_min+ " / " + jsonObj.main.temp_max + "] °С";
				document.getElementById("humidity").innerHTML = "Humidity: " + jsonObj.main.humidity + "%";
				document.getElementById("pressure").innerHTML = "Pressure: " + jsonObj.main.pressure + "mb";
			}
		};
		var city = document.getElementsByClassName("citySelector")[0].value;
		var url="http://api.openweathermap.org/data/2.5/weather?q=Belgrade&amp;appid=c0da9ddb7d9cd1a5b9c619a22766fe4b&amp;units=metric";
		var url2="http://api.openweathermap.org/data/2.5/weather?q=" + city +"&amp;units=metric&amp;lang=en&amp;APPID=c0da9ddb7d9cd1a5b9c619a22766fe4b"
		req.open("GET",url2 , false);
		req.send();
		clear();
		if(req.status !=200){
			date.style.display="none";
			error.style.display="block";
		}else{
			error.style.display="none";
			date.style.display="block";
		}
		alert("city:"+city+"status:"+req.status+"response:"+req.responseText);
	}