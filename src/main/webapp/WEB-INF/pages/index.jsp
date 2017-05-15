<%-- 
    Document   : index
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

<!--  Light Bootstrap Table core CSS    -->
<!-- <link rel="stylesheet" -->
<%-- 	href="<c:url value="/external/assets/css/light-bootstrap-dashboard.css"/>"> --%>

<!--     Fonts and icons     -->
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="<c:url value="/external/assets/css/pe-icon-7-stroke.css" />">
	
<!--  CSS for Demo Purpose, don't include it in your project     -->
<link rel="stylesheet"
	href="<c:url value="/external/assets/css/demo.css"/>">		
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<script src="<c:url value="/external/bootstrap/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/external/js/index.js" />"></script>
<%--        <script src="<c:url value="/resources/js/register.js" />"></script> --%>
</head>
<body>
	<div class="middlePage">
		<div class="page-header">
			<h1 class="logo">
				CSU East Bay <small>Welcome to our magazine!</small>
			</h1>
		</div>

		<div class="panel panel-info">
			<div class="panel-heading">
				<h3 class="panel-title">Sign In</h3>
			</div>
			<div class="panel-body">
			<c:if test="${fn:length(errmsgs) gt 0}">
						<div role="main">
							<div class="section-body">
								<p class="col-xs-9 col-xs-offset-3"
									style="display: block !important">
									<ol style="list-style-type: square; color: rgba(255, 0, 0, 0.8);">
									<c:forEach items="${errmsgs}" var="errmsg" varStatus="loop">
									<li >${errmsg}</li>
									</c:forEach>
									</ol>
								</p>
							</div>
						</div>
				
					</c:if>
					<c:if test="${fn:length(msgs) gt 0}">
						<div role="main">
							<div class="section-body">
								<p class="col-xs-9 col-xs-offset-3"
									style="display: block !important">
									<ol style="list-style-type: square; color:hsl(120,100%,25%);">
									<c:forEach items="${msgs}" var="msg" varStatus="loop">
									<li >${msg}</li>
									</c:forEach>
									</ol>
								</p>
							</div>
						</div>
				
					</c:if>
				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form id="loginform" class="form-horizontal" role="form" action="login" method="get">

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="uName"
								type="text" class="form-control" name="uName" value=""
								placeholder="username or email">
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="pwd"
								type="password" class="form-control" name="pwd"
								placeholder="password">
						</div>



						<div class="input-group">
							<div class="checkbox">
								<label> <input id="login-remember" type="checkbox"
									name="remember" value="1"> Remember me
								</label>
							</div>
						</div>


						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->

							<div class="col-sm-12 controls">
<!-- 							onclick="validateIndexForm();" -->
								<button type="submit" id="btn-login" class="btn btn-success">Login </button>
								<button type="reset"
									id="btn-reset" class="btn btn-warning"
									onclick="reset();">Reset</button>

							</div>
						</div>
					</form>
					<form  id="signUpform" class="form-horizontal" role="form"
						action="signUp" method="get">
						<div class="form-group">
							<div class="col-md-12 control">
								<div
									style="border-top: 1px solid #888; padding-top: 15px; font-size: 85%">
									Don't have an account! 
									
										<button type="submit" class="btn btn-link">Sign Up Here</button>
								</div>
							</div>
						</div>
		<input type="hidden" id="mesg" name="mesg" value="${mesg}"/>
<input type="hidden" id="errmesg" name="errmesg" value="${errmesg}"/>
					</form>


				</div>
			</div>
		</div>

	</div>
</body>
<script type="text/javascript">
    	$(document).ready(function(){
			
    		var msg = $('#mesg').val();
    		var ermsg = $('#errmesg').val();
        	demo.initChartist();
			if(msg != null && msg !=''){
				$.notify({
	            	icon: 'pe-7s-info',
	            	message: msg

	            },{
	                type: 'info',
	                timer: 4000
	            });
			}else if(ermsg != null && ermsg !=''){
				$.notify({
	            	icon: 'pe-7s-info',
	            	message: ermsg

	            },{
	                type: 'danger',
	                timer: 4000
	            });
			}
        	

    	});
	</script>
</html>