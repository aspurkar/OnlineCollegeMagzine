function validateIndexForm(){
	
	var uName = $('#uName').val();
	var pwd = $('#pwd').val();
	alert("pwd >>>> " + pwd);
//	var passw=  /^[A-Za-z]\w{7,14}$/;  
	if(pwd != '')   
	{   
		$('#loginform').attr('action', "/ocm/login ").submit();
	} else  
	{   
		document.getElementById("errFname").style.display = "block";
	       document.getElementById("errFname").style.color = "#a94442";
	return false;  
	} 
}