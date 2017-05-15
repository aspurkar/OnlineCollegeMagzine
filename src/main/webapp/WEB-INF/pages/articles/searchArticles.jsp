
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<link rel="icon" type="image/png" href="assets/img/favicon.ico">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>College online magazine: Admin</title>

<meta
	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'
	name='viewport' />
<meta name="viewport" content="width=device-width" />


<!-- Bootstrap core CSS     -->
<link rel="stylesheet"
	href="<c:url value="/external/assets/css/bootstrap.min.css" />">

<!-- Animation library for notifications   -->
<link rel="stylesheet"
	href="<c:url value="/external/assets/css/animate.min.css" />">

<!--  Light Bootstrap Table core CSS    -->
<link rel="stylesheet"
	href="<c:url value="/external/assets/css/light-bootstrap-dashboard.css"/>">

<!--  CSS for Demo Purpose, don't include it in your project     -->
<link rel="stylesheet"
	href="<c:url value="/external/assets/css/demo.css"/>">


<!--     Fonts and icons     -->
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="<c:url value="/external/assets/css/pe-icon-7-stroke.css" />">
<link rel="stylesheet"
	href="<c:url value="/external/assets/fonts/font-awesome-min.css" />">
<script type="text/javascript">
function adminDashboard(){
	$('#searchArticleform').attr('action', "/ocm/getDashboard").submit();
}

function manageUsers(){
	$('#searchArticleform').attr('action', "/ocm/admin/getUsers").submit();
}
function manageArticles(){
	$('#searchArticleform').attr('action', "/ocm/article/manageArticles").submit();
}
function myProfile(){
	$('#searchArticleform').attr('action', "/ocm/admin/myProfile").submit();
}
function viewArticle(artId){
	$('#artId').val(artId);
	$('#searchArticleform').attr('action', "/ocm/article/viewArticle").submit();
}
function approveArticle(artId){
	$('#artId').val(artId);
	$('#searchArticleform').attr('action', "/ocm/article/approveArticle").submit();
}
function editArticle(artId){
	$('#artId').val(artId);
	$('#searchArticleform').attr('action', "/ocm/article/editArticle").submit();
}
function removeArticle(artId){
	$('#artId').val(artId);
	$('#searchArticleform').attr('action', "/ocm/article/removeArticle").submit();
}
function displayNextPage(index){
	$('#pageId').val(index);
	$('#searchArticleform').attr('action', "/ocm/article/postSearch").submit();
}
</script>
</head>
<body>
	<form id="searchArticleform" class="form-horizontal" role="form"
		action="/ocm/article/performSearch">
		<div class="wrapper">
<div class="sidebar" data-color="black" data-image="<c:url value="/external/assets/img/sidebar-5.jpg" />">

				<div class="sidebar-wrapper">
					<div class="logo">
						<a href="http://localhost:8080/ocm/" class="simple-text"> OCM
						</a>
					</div>

					<ul class="nav">
						<li><a href="#" onclick="adminDashboard();"> <i
								class="pe-7s-graph"></i>
								<p>Dashboard</p>
						</a></li>
						<li><a id="mngUsersLink" href="#" onclick="manageUsers();">
								<i class="pe-7s-news-paper"></i>
								<p>Manage Users</p>
						</a></li>
						<li class="active"><a href="#" onclick="manageArticles();">
								<i class="pe-7s-note2"></i>
								<p>Manage Articles</p>
						</a></li>
						<li><a href="#" onclick="myProfile();"> <i class="pe-7s-user"></i>
								<p>My Profile</p>
						</a></li>
					</ul>
				</div>
			</div>

			<div class="main-panel">
				<nav class="navbar navbar-default navbar-fixed">
					<div class="container-fluid">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target="#navigation-example-2">
								<span class="sr-only">Toggle navigation</span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
<!-- 							<a class="navbar-brand" href="#">Dashboard</a> -->
						</div>
						<div class="collapse navbar-collapse">


							<ul class="nav navbar-nav navbar-right">
								<li><a href="/ocm/logout"> Welcome, ${user.fname } &nbsp;
										${user.lname }</a></li>

								<li><a href="/ocm/logout"> Log out </a></li>
							</ul>
						</div>
					</div>
				</nav>


				<div class="content">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-10 col-md-offset-1">

								<div class="panel panel-default panel-table">
									<div class="panel-heading">
										<div class="row">
											<div class="col-md-10">
												<h3 class="panel-title"> Articles</h3>
											</div>
										</div>
									</div>
									<div class="panel-body" id="search_table">
<!-- 									<table id="ad_search_table" class="table table-bordered table-hover"></table> -->
										<table class="table table-striped table-list">
											<thead>
												<tr>
													<th>Article Id</th>
													<th>Heading</th>
													<th>Status</th>
													<th>Approve</th>
													<th>View</th>
													<th>Edit</th>
													<th>Delete</th>
												</tr>
											</thead>
											<tbody>
											<c:forEach items="${result}" var="art" varStatus="loop">
												<tr>
													<td>${art.articleId}</td>
													<td>${art.heading}</td>
													<td>${art.postStatus}</td>
													<td>
														<c:if test="${art.postStatus != 1}">
															<button type="button" rel="tooltip" title="Approve Article" class="btn btn-success btn-simple btn-xs" 
				                                                onclick="approveArticle('${art.articleId}');">
				                                                    <i class="fa fa-check" aria-hidden="true"></i>
				                                            </button>
				                                        </c:if>
				                                        <c:if test="${art.postStatus == 1}">
															<button type="button" rel="tooltip" title="Approved" class="btn btn-success btn-simple btn-xs">
				                                                    <i class="fa fa-thumbs-o-up" aria-hidden="true"></i>
				                                            </button>
				                                        </c:if>
		                                            </td>
		                                            <td>
		                                            	<button type="button" rel="tooltip" title="View Article" class="btn btn-info btn-simple btn-xs" 
			                                                onclick="viewArticle('${art.articleId}');">
			                                                    <i class="fa fa-edit"></i>
		                                                </button>
		                                            </td>
													<td>
<!-- 												<a class="btn btn-default"><em class="fa fa-pencil"></em></a> -->
													<button type="button" rel="tooltip" title="Edit Article" class="btn btn-primary btn-simple btn-xs" 
		                                                onclick="editArticle('${art.articleId}');">
		                                                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
		                                            </button>
													</td>
													<td><button type="button" rel="tooltip" title="Remove Article" class="btn btn-danger btn-simple btn-xs" 
		                                                onclick="removeArticle('${art.articleId}');">
		                                                    <i class="fa fa-trash" aria-hidden="true"></i>
		                                            </button></td>
												</tr>
												
												</c:forEach>
											</tbody>
										</table>

									</div>
									<div class="panel-footer">
										<div class="row">
											<%-- <div class="col col-xs-4">
												<c:if test="${currentIndex != 1}">
											       <a href="javascript:void(0)" onclick="javascript:displayNextPage(${currentIndex - 1});">Previous</a>
											    </c:if>
											</div> --%>
<%-- 											<div class="col col-xs-4">Page 1 of ${endIndex}</div> --%>
											<div class="col col-xs-8">
												<ul class="pagination hidden-xs pull-right">
												<c:if test="${currentIndex != 1}">
											       <li> <a href="javascript:void(0)" onclick="javascript:displayNextPage(${currentIndex - 1});">Previous</a></li>
											    </c:if>
												<c:forEach begin="1" end="${endIndex}" var="i">
												<c:choose>
								                    <c:when test="${currentIndex eq i}">
<%-- 								                        <li>${i}</li> --%>
								                         <li class="active">  <a href="javascript:void(0)" onclick="javascript:displayNextPage(${i});">${i}</a> </li>
								                    </c:when>
								                    <c:otherwise>
								                      
								                         <li>  <a href="javascript:void(0)" onclick="javascript:displayNextPage(${i});">${i}</a> </li>
								                     
								                    </c:otherwise>
								                </c:choose>
												</c:forEach>
												<c:if test="${currentIndex lt endIndex}">
											            <li>  <a href="javascript:void(0)" onclick="javascript:displayNextPage(${currentIndex + 1});">Next</a> </li>
											    </c:if>
												</ul>
												<ul class="pagination visible-xs pull-right">
													<li><a href="#">«</a></li>
													<li><a href="#">»</a></li>
												</ul>
											</div>
										</div>
									</div>
								</div>

							</div>


						</div>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="mesg" name="mesg" value="${mesg}" /> <input
			type="hidden" id="errmesg" name="errmesg" value="${errmesg}" /> <input
			type="hidden" id="artId" name="artId" value="" />
			<input type="hidden" id="pageId" name="pageId" value="" />
	</form>
</body>

<!--   Core JS Files   -->
<script src="https://code.jquery.com/jquery-1.11.2.js" integrity="sha256-WMJwNbei5YnfOX5dfgVCS5C4waqvc+/0fV7W2uy3DyU=" crossorigin="anonymous"></script>
<script src="<c:url value="/external/assets/js/bootstrap.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/external/assets/js/jquery.jqGrid.min.js"/>" type="text/javascript"></script>

<!--  Checkbox, Radio & Switch Plugins -->
<script
	src="<c:url value="/external/assets/js/bootstrap-checkbox-radio-switch.js"/>"></script>

<!--  Charts Plugin -->
<script src="<c:url value="/external/assets/js/chartist.min.js"/>"></script>

<!--  Notifications Plugin    -->
<script src="<c:url value="/external/assets/js/bootstrap-notify.js"/>"></script>

<!--  Google Maps Plugin    -->
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>

<!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
<script
	src="<c:url value="/external/assets/js/light-bootstrap-dashboard.js"/>"></script>

<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
<script src="<c:url value="/external/assets/js/demo.js"/>"></script>

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
<script type="text/javascript">
// function displayMsg(){
// 	var msg = $('#mesg').val();
// 	var ermsg = $('#errmesg').val();
// 	demo.initChartist();
// 	if(msg != null && msg !=''){
// 		$.notify({
//         	icon: 'pe-7s-info',
//         	message: msg

//         },{
//             type: 'info',
//             timer: 4000
//         });
// 	}else if(ermsg != null && ermsg !=''){
// 		$.notify({
//         	icon: 'pe-7s-info',
//         	message: ermsg

//         },{
//             type: 'danger',
//             timer: 4000
//         });
// 	}
// }
</script>
<script type="text/javascript">
//     	$(document).ready(function(){
//     		$.ajax({
//     			type : "POST",
//     			url : "/ocm/article/performSearch",
//     			async : false,
//     			cache : false,
//     			data : $('#searchArticleform').serialize(),
//     			success : function(result) {
//     				if(result == ''){
//     					$('#mesg').val("Oops!! No Articles found.");
//     					$('#errmesg').val("");
//     					displayMsg();
    					
//     					return ;
//     				}
//     	    		var items = [];
//     	    		$.each(result, function(key, val) {
//     					obj1 = {

//     						articleId : val.articleId,
//     						heading : val.heading,
//     						postStatus : val.postStatus,
//     					};
//     					items.push(obj1);
    				
//     				});
//     				$("#searchResult").show();
//     				SearchViewGrid(items);
// //     				$("#search_jqGrid").show(); 

//     			},
//     			error : function(jqXHR, textStatus, errorThrown) {
//     				alert("errorThrown:" + errorThrown);
//     			}
//     		});
			
//     	}); 
	</script>
<script type="text/javascript">
// function SearchViewGrid(jsonArrayGrid) {
// 	$('#ad_search_table').jqGrid('GridUnload');
// 	 $("#ad_search_table").jqGrid({
// 		 data : jsonArrayGrid,
// 		 styleUI : 'Bootstrap',
//          datatype: "local",
//          colModel: [
//              { label: 'Article ID', name: 'articleId', key: true, width: 75, align:"left"},
//              { label: 'Title', name: 'heading', width: 400, align:"left"},
//              { label: 'Status', name: 'postStatus', width: 60, align:"center"},
//              { label: 'Approve', name: 'articleId', formatter: approveArticle, width: 80, align:"center"},
//              { label: 'View', name: 'articleId', formatter: viewArticle, width: 50, align:"center"},
//              { label: 'Edit', name: 'articleId', formatter: editArticle, width: 50, align:"center"},
//              { label: 'Delete', name: 'articleId', formatter: removeArticle, width: 60, align:"center"}
//          ],
//          page: 1,
//          height: 'auto',
//          //rowNum: 5,
//         // rowList : [ 5, 10, 15, 20, 25 ],
//          shrinkToFit : false,
//          pager: "#pa_ad_search_table",
//          viewrecords : true,
// 		sortorder : "desc",
// 		rownumbers : true,
// 		autowidth : true,
//      });
// 	 jQuery("#ad_search_table").jqGrid('navGrid', '#pa_ad_search_table', {
// 			edit : false,
// 			add : false,
// 			del : false,
// 			search : true
// 		}, {}, {}, {}, {});
// }
</script>
<script type="text/javascript">
// function approveArticle(cellvalue, options, rowObject) {

// 	var y = rowObject.postStatus;
// 	if(y !=1){
// 		var x = "<button type=\"button\" rel=\"tooltip\" title=\"Approve Article\"  class=\"btn btn-success btn-simple btn-xs\"  onClick=\"approveArtcle("
// 			+ cellvalue + ")\" > <i class=\"fa fa-edit\" aria-hidden=\"true\"></i> </button>";

			
// 	}else if(y ==1){
// 		var x = "<button type=\"button\" rel=\"tooltip\" title=\"Approved\"  class=\"btn btn-success btn-simple btn-xs\"  > <i class=\"fa fa-thumbs-o-up\" aria-hidden=\"true\"></i> </button>";

// 	}

// 	return x;
// }
// function viewArticle(cellvalue, options, rowObject) {
// 	var x = "<button type=\"button\" rel=\"tooltip\" title=\"View Article\"  class=\"btn btn-info btn-simple btn-xs\"  onClick=\"viewArtcle("
// 		+ cellvalue + ")\" > <i class=\"fa fa-edit\" aria-hidden=\"true\"></i> </button>";

// 	return x;
// }
// function editArticle(cellvalue, options, rowObject) {

// 	var x = "<button type=\"button\" rel=\"tooltip\" title=\"Edit Article\"  class=\"btn btn-primary btn-simple btn-xs\"  onClick=\"editArtcle("
// 			+ cellvalue + ")\" > <i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i> </button>";
// 	return x;
// }
// function removeArticle(cellvalue, options, rowObject) {

// 	var x = "<button type=\"button\" rel=\"tooltip\" title=\"Remove Article\"  class=\"btn btn-danger btn-simple btn-xs\"  onClick=\"removeArtcle("
// 			+ cellvalue + ")\" > <i class=\"fa fa-trash\" aria-hidden=\"true\"></i> </button>";
// 	return x;
// }
</script>
</html>
