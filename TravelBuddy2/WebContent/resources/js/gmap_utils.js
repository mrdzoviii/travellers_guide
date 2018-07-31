/**
 * 
 */
var markerFrom = null;
		var markerTo=null;

		 function geocodeFrom() {
			  
			 	if (markerFrom != null){
					markerFrom.setMap(null);
			 	}
			 	PF('mapFrom').geocode(document.getElementById('from_input').value);
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
		 	PF('mapTo').geocode(document.getElementById('to_input').value);
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
			document.getElementById('count').value='1'
		var fromX=document.getElementById('fromX').value;
		var fromY=document.getElementById('fromY').value;
		var toX=document.getElementById('toX').value;
		var toY=document.getElementById('toY').value;
		if (fromX>0&&fromY>0){
			markerFrom = new google.maps.Marker({
				position : new google.maps.LatLng(fromX, fromY)
			});
			PF('mapFrom').addOverlay(markerFrom);

		}
		if (toX>0&&toY>0){
			
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