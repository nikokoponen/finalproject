$(document).ready(function(){
	//jQuery for employees html page
	$("#empdetails").hide();
	$("#empcreate").click(function(){
		$("#empdetails").show();
	});
	$("#cancel").click(function(){
		$("#empdetails").hide();
	});
	//jQuery for companies html page
	$("#compdetails").hide();
	$("#compcreate").click(function(){
		$("#compdetails").show();
	});
	$("#cancel").click(function(){
		$("#compdetails").hide();
	});
	//jQuery for workhours html page
	$("#pwhdetails").hide();
	$("#pwhcreate").click(function(){
		$("#pwhdetails").show();
	});
	$("#cancel").click(function(){
		$("#pwhdetails").hide();
	});
	//jQuery for departments html page
	$("#deptdetails").hide();
	$("#deptcreate").click(function(){
		$("#deptdetails").show();
	});
	$("#cancel").click(function(){
		$("#deptdetails").hide();
	});
	//jQuery for projectmembers html page
	$("#pmemberdetails").hide();
	$("#pmembercreate").click(function(){
		$("#pmemberdetails").show();
	});
	$("#cancel").click(function(){
		$("#pmemberdetails").hide();
	});
	//jQuery for projects html page
	$("#projectdetails").hide();
	$("#projectcreate").click(function(){
		$("#projectdetails").show();
	});
	$("#cancel").click(function(){
		$("#projectdetails").hide();
	});
});