<%@page import="JavaBean.MyFriBean" %>
<%@page import="JavaBean.MyDayBean" %>
<%@page import="DBJavaBean.DB" %>
<%@page import="java.sql.*" %>
<%@page import= "java.util.ArrayList" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title> Book Management System</title>
		<meta name="description" content="">
		<meta name="author" content="">

		<!-- Use the correct meta names below for your web application
			 Ref: http://davidbcalhoun.com/2010/viewport-metatag 
			 
		<meta name="HandheldFriendly" content="True">
		<meta name="MobileOptimized" content="320">-->
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<!-- Basic Styles -->
		<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="css/font-awesome.min.css">

		<!-- SmartAdmin Styles : Please note (smartadmin-production.css) was created using LESS variables -->
		<link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-production.css">
		<link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-skins.css">

		<!-- SmartAdmin RTL Support is under construction
		<link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-rtl.css"> -->

		<!-- We recommend you use "your_style.css" to override SmartAdmin
		     specific styles this will also ensure you retrain your customization with each SmartAdmin update.
		<link rel="stylesheet" type="text/css" media="screen" href="css/your_style.css"> -->

		<!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
		<link rel="stylesheet" type="text/css" media="screen" href="css/demo.css">

		<!-- FAVICONS -->
		<link rel="shortcut icon" href="img/favicon/favicon.ico" type="image/x-icon">
		<link rel="icon" href="img/favicon/favicon.ico" type="image/x-icon">

		<!-- GOOGLE FONT -->
		<!--  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">-->
	</head>
<body class="">
		<!-- possible classes: minified, fixed-ribbon, fixed-header, fixed-width-->

		<!-- HEADER -->
		<header id="header">
			<div id="logo-group">

				<!-- PLACE YOUR LOGO HERE -->
				<span id="logo"> <img src="img/avatars/timg.jpg" alt="SmartAdmin"> </span>
				<!-- END LOGO PLACEHOLDER -->
				<!-- span id="logo"> <img src="img/logo.png" alt="SmartAdmin"> </span> -->
				<!-- END LOGO PLACEHOLDER -->

				<!-- Note: The activity badge color changes when clicked and resets the number to 0
				Suggestion: You may want to set a flag when this happens to tick off all checked messages / notifications -->


				<!-- AJAX-DROPDOWN : control this dropdown height, look and feel from the LESS variable file -->
				<div class="ajax-dropdown">

					<!-- the ID links are fetched via AJAX to the ajax container "ajax-notifications" -->
					

					<!-- notification content -->
					<div class="ajax-notifications custom-scroll">

						<div class="alert alert-transparent">
							<h4>Click a button to show messages here</h4>
							This blank page message helps protect your privacy, or you can show the first message here automatically.
						</div>

						<i class="fa fa-lock fa-4x fa-border"></i>

					</div>
					<!-- end notification content -->

					
					<!-- end footer -->

				</div>
				<!-- END AJAX-DROPDOWN -->
			</div>


			<!-- pulled right: nav area -->
			<div class="pull-right">

				
				

			</div>
			<!-- end pulled right: nav area -->

		</header>
		<!-- END HEADER -->

		<!-- Left panel : Navigation area -->
		<!-- Note: This width of the aside area can be adjusted through LESS variables -->
		<aside id="left-panel">

			<!-- User info -->
			<div class="login-info">
				<span> <!-- User image size is adjusted inside CSS, it should stay as it --> 
					
					<a href="javascript:void(0);" id="show-shortcut">
						<img src="img/avatars/timg.jpg" alt="me" class="online" /> 
						<span>
							UserName 
						</span>
						<i class="fa fa-angle-down"></i>
					</a> 
					
				</span>
			</div>
			<!-- end user info -->

			<!-- NAVIGATION : This navigation is also responsive

			To make this navigation dynamic please make sure to link the node
			(the reference to the nav > ul) after page load. Or the navigation
			will not initialize.
			-->
			<nav>
				<!-- NOTE: Notice the gaps after each icon usage <i></i>..
				Please note that these links work a bit different than
				traditional hre="" links. See documentation for details.
				-->

				<ul>
					<li>
						<a href="main.jsp" title="Dashboard"><i class="fa fa-lg fa-fw fa-home"></i> <span class="menu-item-parent">Home</span></a>
					</li>
						
					<li>
						<a href="lookFriends.jsp"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent">Books Information</span></a>
					</li>
								
					<li>
						<a href="Addnewbook.jsp"><i class="fa fa-lg fa-fw fa-calendar"></i> <span class="menu-item-parent">Add New Book</span></a>
					</li>
									
					<li>
						<a href="searchwithname.jsp"><i class="fa fa-lg fa-fw fa-desktop"></i> <span class="menu-item-parent">Search By Author Name</span></a>
					</li>
					<li>
						<a href="login/login.jsp"><i class="fa fa-lg fa-fw fa-pencil-square-o"></i> <span class="menu-item-parent">Logout</span></a>
					</li>
					
				</ul>
			</nav>
			<span class="minifyme"> <i class="fa fa-arrow-circle-left hit"></i> </span>

		</aside>
		<!-- END NAVIGATION -->


		<!-- MAIN PANEL -->
		<div id="main" role="main">

			<!-- RIBBON -->
			<div id="ribbon">

				<span class="ribbon-button-alignment"> <span id="refresh" class="btn btn-ribbon" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> Warning! This will reset all your widget settings." data-html="true"><i class="fa fa-refresh"></i></span> </span>

				<!-- breadcrumb -->
				<ol class="breadcrumb">
					<li>Home</li><li>search with name</li><li>search result</li>
				</ol>
				<!-- end breadcrumb -->

				<!-- You can also add more buttons to the
				ribbon for further usability

				Example below:

				<span class="ribbon-button-alignment pull-right">
				<span id="search" class="btn btn-ribbon hidden-xs" data-title="search"><i class="fa-grid"></i> Change Grid</span>
				<span id="add" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa-plus"></i> Add</span>
				<span id="search" class="btn btn-ribbon" data-title="search"><i class="fa-search"></i> <span class="hidden-mobile">Search</span></span>
				</span> -->

			</div>
			<!-- END RIBBON -->
			<!-- MAIN CONTENT -->
			<div id="content">
			
			<!-- NEW WIDGET START -->
						<article class="col-sm-12 col-md-12 col-lg-10">

							<!-- Widget ID (each widget will need unique ID)-->
							<div class="jarviswidget jarviswidget-color-greenLight" id="wid-id-3" data-widget-editbutton="false">
								
								<header>
									<span class="widget-icon"> <i class="fa fa-table"></i> </span>
									<h2>Search Result</h2>

								</header>		
				
				<!-- widget div-->
								<div>

									<!-- widget edit box -->
									<div class="jarviswidget-editbox">
										<!-- This area used as dropdown edit box -->

									</div>
									<!-- end widget edit box -->

									
											<!--  
											<form action="lookFriends.jsp" method ="post">
											<input type="text" name="friendname"/>
											<input type="submit" value="查找"/>
											</form>
											-->
									<!-- widget content -->
									<div class="widget-body no-padding">

										<table class="table table-bordered">
											<thead>
												<tr>
													<!--  <th>Book Number</th>-->
													<th> ISBN(PK)</th>
													<th> Title</th>
													<th> Author(ID)</th>
													<th> Publisher</th>
													<th> PublishDate</th>
													<th> Price</th>
												</tr>
											</thead>
											<tbody>
											<%
											
											
											DB mysql = new DB();
											String userName1 = mysql.returnLogin(request);
											String author =request.getParameter("authorid");
											String authorname = request.getParameter("authorname");
											//if(authorname!= null)
											//	authorname =  new String(request.getParameter("authorname").getBytes("ISO-8859-1"),"utf-8");
											if(author == null || author.length() <= 0)
											{
												ResultSet rs1 = mysql.selectauthorID(request, userName1,authorname);
												String fri11 = mysql.myFriendssearchID(request,userName1,authorname);
												ArrayList IDS = (ArrayList)session.getAttribute("friendsearchID");
												System.out.println(IDS.size());
												if(IDS == null|| IDS.size() == 0){
													%>
													<h1>书籍库中未有书籍</h1>
													<%
												}
												else{										
													for(int i=IDS.size()-1;i>=0;i--)										
													{
														MyDayBean fa = (MyDayBean)IDS.get(i);
														
														ResultSet rs2 = mysql.selectauthor444(request, userName1,fa.getid());
														String fri12 = mysql.myFriendssearch(request,userName1,fa.getid());
														ArrayList friends= (ArrayList)session.getAttribute("friendsearch");
														System.out.println(friends.size());
														if(friends == null|| friends.size() == 0){
														%>
														<h1>书籍库中未有书籍</h1>
														<%
														}else{
															for(int j=friends.size()-1;j>=0;j--)
															{
																MyFriBean ff =(MyFriBean)friends.get(j);
																//System.out.println(ff.getName());
														%>
														
																<tr>
																<!--  <th><%=ff.getName()%></th>-->
																<th><%=ff.getISBN()%></th>
																<th><%=ff.getName()%></th>
																<th><%=ff.getauthor()%></th>
																<th><%=ff.getpublisher()%></th>
																<th><%=ff.getdate()%></th>
																<th><%=ff.getprice()%></th>
															</tr>
														<%
															}
														}
													}
												}
											}	
												else
												{
													ResultSet rs2 = mysql.selectauthor444(request, userName1,author);
													String fri12 = mysql.myFriendssearch(request,userName1,author);
													ArrayList friends= (ArrayList)session.getAttribute("friendsearch");
													
													if(friends == null|| friends.size() == 0){
													%>
													<h1>书籍库中未有书籍</h1>
													<%
													}else{
														for(int j=friends.size()-1;j>=0;j--)
														{
															MyFriBean ff =(MyFriBean)friends.get(j);
															//System.out.println(ff.getName());
													%>
													
															<tr>
															<!--  <th><%=ff.getName()%></th>-->
															<th><%=ff.getISBN()%></th>
															<th><%=ff.getName()%></th>
															<th><%=ff.getauthor()%></th>
															<th><%=ff.getpublisher()%></th>
															<th><%=ff.getdate()%></th>
															<th><%=ff.getprice()%></th>
														</tr>
													<%
														}
													}
												}
											
											%>
											
											</tbody>
										</table>
										
										
									</div>
									<!-- end widget content -->

								</div>
								<!-- end widget div -->

							</div>
							<!-- end widget -->
				</article>
			<!-- END MAIN CONTENT -->
</body>
</html>