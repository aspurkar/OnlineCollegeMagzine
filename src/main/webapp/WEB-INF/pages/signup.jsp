<%-- 
    Document   : signup
    Created on : 15 Nov, 2016, 8:55:28 PM
    Author     : ADITYA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>

<html>
<head>
<link rel="stylesheet"
	href="<c:url value="/external/bootstrap/css/bootstrap.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/external/css/bootstrapBase.css" />">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<script src="<c:url value="/external/bootstrap/js/bootstrap.min.js" />"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#regNo").hide();
	$("#almni").hide();
	$("#brnch").hide();
	$("#btch").hide();
	$("#pgrm").hide();
	
	$("#userType").change(function () {
        var userType = $('#userType').val();
        if(userType == "Student"){
        	$("#regNo").show();
        	$("#almni").show();
        	$("#brnch").show();
        	$("#btch").show();
        	$("#pgrm").show();
        }else{
        	$("#regNo").hide();
        	$("#almni").hide();
        	$("#brnch").hide();
        	$("#btch").hide();
        	$("#pgrm").hide();
        }
    });
});


</script>
</head>
<body>
	<div class="middlePage">
		<div class="page-header">
			<h1 class="logo">
				College <small>Welcome to our magazine!</small>
			</h1>
		</div>

		<div class="panel panel-info">
			<div class="panel-heading">
				<h3 class="panel-title">Sign up</h3>
			</div>
			<div class="panel-body">
<c:if test="${fn:length(errmsgs) gt 0}">
						<div role="main">
							<div class="section-body">
								<p class="col-xs-9 col-xs-offset-3"
									style="display: block !important">
									<ol style="list-style-type: square; color: rgba(255, 0, 0, 0.8);">
									<c:forEach items="${errmsgs}" var="msgs" varStatus="loop">
									<li >${msgs}</li>
									</c:forEach>
									</ol>
								</p>
							</div>
						</div>
				
					</c:if>
				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form id="registerform" class="form-horizontal" role="form"
						action="register" method="get">
						
						<div style="margin-bottom: 25px" class="input-group">
						<label for="userType">User Type (select one):</label>
							<select class="form-control" onchange="changeType(this)" title="Choose one of the following..." id="userType" name="userType">
								<c:forEach items="${register['userTypeList']}" var="type" varStatus="loop">
								 	<option value="${type}">${type}</option>
								 </c:forEach>
							</select>
						</div>
						
						<!-- Student Fields  -->	
						<div id="regNo" style="margin-bottom: 25px;display: none;" class="input-group">
							<span class="input-group-addon">
							<i class="glyphicon glyphicon-pencil"></i></span> 
							<input id="regNumber" type="text" class="form-control" name="studentDto.regNumber" value=""
								placeholder="Student Registration Number">
						</div>
						
						<div id="almni" style="margin-bottom: 25px;display: none;" class="input-group">
							<span class="input-group-addon">
							<i	class="glyphicon glyphicon-pencil"></i></span>
							 <input id="alumni" type="text" class="form-control" name="studentDto.alumni" value=""
								placeholder="Alumni">
						</div>
						
						<div id="brnch" style="margin-bottom: 25px;display: none;" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="branch"
								type="text" class="form-control" name="studentDto.branch" value=""
								placeholder="Student Branch">
						</div>
						
						<div id="btch" style="margin-bottom: 25px;display: none;" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="batch"
								type="text" class="form-control" name="studentDto.batch" value=""
								placeholder="Student Batch">
						</div>
						
						<div id="pgrm" style="margin-bottom: 25px;display: none;" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="program"
								type="text" class="form-control" name="studentDto.program" value=""
								placeholder="Student Program">
						</div>
						
						
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="fname"
								type="text" class="form-control" name="fname" value=""
								placeholder="First Name">
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="lname"
								type="text" class="form-control" name="lname" value=""
								placeholder="Last Name">
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="address1" type="text"
								class="form-control" name="address1" value=""
								placeholder="Address Line 1" />
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="address2" type="text"
								class="form-control" name="address2" value=""
								placeholder="Address Line 2" />
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="address3" type="text"
								class="form-control" name="address3" value=""
								placeholder="Address Line 3" />
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="city" type="text"
								class="form-control" name="city" value=""
								placeholder="City" />
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="state" type="text"
								class="form-control" name="state" value=""
								placeholder="State" />
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="country" type="text"
								class="form-control" name="country" value=""
								placeholder="Country" />
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="pincode" type="text"
								class="form-control" name="pincode" value=""
								placeholder="Pincode" />
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="email"
								type="text" class="form-control" name="email" value=""
								placeholder="Email">
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="uname"
								type="text" class="form-control" name="uname" value=""
								placeholder="User Name">
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="pwd"
								type="password" class="form-control" name="pwd"
								placeholder="Password">
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="repwd"
								type="password" class="form-control" name="repwd"
								placeholder="Re-confirm Password">
						</div>
						
						
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="squestion"
								type="text" class="form-control" name="squestion" value=""
								placeholder="Write your own security question..">
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> <input id="sanswer"
								type="text" class="form-control" name="sanswer" value=""
								placeholder="Write answer for you security question..">
						</div>
						
						
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-pencil"></i></span> 
								<textarea id="aboutme" class="form-control" name="aboutme" rows="5" value="" placeholder="write something about you.."> </textarea>
						</div>

						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->

							<div class="col-sm-12 controls">
								<button type="submit" id="btnRegister" class="btn btn-success">Register </button> 
								<button type="reset" id="btnReset"  class="btn btn-warning" >Reset</button>

							</div>
						</div>
					</form>

				</div>
			</div>
		</div>

	</div>
</body>
</html>