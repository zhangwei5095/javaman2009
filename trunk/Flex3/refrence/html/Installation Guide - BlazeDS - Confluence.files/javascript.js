var alertText = "Please enter a keyword to continue.";

var php = true;
var asp = true;
var java = true;

function validateSearchForm (formID, fieldID) { 

	var text = document[formID][fieldID].value;
	
	if (text == null || text == "" || text == " ") {
	
		alert(alertText);
		
		return false;

	}

	else {
	
	  /*if (document[formID][fieldID].value.indexOf("site:") == -1) {
	
	    var site = document.location.href.substring(0,document.location.href.lastIndexOf("/")); 
	
	    document[formID][fieldID].value = "site:" + site + " " + document[formID][fieldID].value;
	
	  }*/

	}

	return true;
}