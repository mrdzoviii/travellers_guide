/**
 * 
 */
var markerFrom = null;
		var markerTo=null;

		 function geocodeFrom() {
			  
			 	if (markerFrom != null){
					markerFrom.setMap(null);
			 	}
			 	PF('mapFrom').geocode(document.getElementById('starting_point_input').value);
		    }
		function handlePointClickFrom(event) {
			
			if (markerFrom != null)
				markerFrom.setMap(null);
		
			markerFrom = new google.maps.Marker({
				position : new google.maps.LatLng(event.latLng.lat(), event.latLng.lng())
			});
			PF('mapFrom').addOverlay(markerFrom);
	
	
		}
		function finishSearchFrom(){
			 

			markerFrom = new google.maps.Marker({
				position : new google.maps.LatLng(document.getElementById('fromX').value, document.getElementById('fromY').value)
			});
			PF('mapFrom').getMap().setCenter(new google.maps.LatLng(document.getElementById('fromX').value, document.getElementById('fromY').value));
			PF('mapFrom').addOverlay(markerFrom);
		}	
		function geocodeTo() {
			 
		 	if (markerTo != null){
				markerTo.setMap(null);
		 	}
		 	PF('mapTo').geocode(document.getElementById('destination_input').value);
	    }
	function handlePointClickTo(event) {
		
		if (markerTo != null)
			markerTo.setMap(null);
	
		markerTo = new google.maps.Marker({
			position : new google.maps.LatLng(event.latLng.lat(), event.latLng.lng())
		});
		PF('mapTo').addOverlay(markerTo);


	}
	
	function initMe(){
		if (document.getElementById('count').value=='0'){
			document.getElementById('count').value='1';
		var isFrom=document.getElementById('isFrom').value;
		var isTo=document.getElementById('isTo').value;	
		var fromX=document.getElementById('fromX').value;
		var fromY=document.getElementById('fromY').value;
		var toX=document.getElementById('toX').value;
		var toY=document.getElementById('toY').value;
		alert(fromX+","+fromY+"/"+toX+","+toY+"/");
		if (isFrom){
			markerFrom = new google.maps.Marker({
				position : new google.maps.LatLng(fromX, fromY)
			});
			PF('mapFrom').addOverlay(markerFrom);

		}
		if (isTo){
			
			markerTo = new google.maps.Marker({
				position : new google.maps.LatLng(toX, toY)
			});
			PF('mapTo').addOverlay(markerTo);
		}
		}
	}
	function finishSearchTo(){
		markerTo = new google.maps.Marker({
			position : new google.maps.LatLng(document.getElementById('toX').value, document.getElementById('toY').value)
		});
		PF('mapTo').getMap().setCenter(new google.maps.LatLng(document.getElementById('toX').value, document.getElementById('toY').value));
		PF('mapTo').addOverlay(markerTo);
	}	