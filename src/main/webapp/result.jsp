<!doctype html>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sentimentanalysis.vader.VScore"%>
<%@page import="java.util.Map"%>
<%@page import="com.sentimentanalysis.util.Constants"%>
<%@page import="com.sentimentanalysis.pojo.User"%>
<%
	User u1 = (User) session.getAttribute("user");
	if (u1 == null) {
		response.sendRedirect("login.jsp?msg=Session expired. Login again");
	} else {
%>

<html class="no-js" lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">

<%
	String check2 = (String) session.getAttribute("inprogress");
	if (check2 != null && check2.equals("yes"))
	{
%>
 <meta http-equiv="refresh" content="5">
 
 <% } %>

<title>Sentiment Analysis</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- favicon
		============================================ -->
<link rel="shortcut icon" type="image/x-icon" href="img/favicon.ico">
<!-- Google Fonts
		============================================ -->
<link
	href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,700,900"
	rel="stylesheet">
<!-- Bootstrap CSS
		============================================ -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<!-- Bootstrap CSS
		============================================ -->
<link rel="stylesheet" href="css/font-awesome.min.css">
<!-- owl.carousel CSS
		============================================ -->
<link rel="stylesheet" href="css/owl.carousel.css">
<link rel="stylesheet" href="css/owl.theme.css">
<link rel="stylesheet" href="css/owl.transitions.css">
<!-- meanmenu CSS
		============================================ -->
<link rel="stylesheet" href="css/meanmenu/meanmenu.min.css">
<!-- animate CSS
		============================================ -->
<link rel="stylesheet" href="css/animate.css">
<!-- normalize CSS
		============================================ -->
<link rel="stylesheet" href="css/normalize.css">
<!-- mCustomScrollbar CSS
		============================================ -->
<link rel="stylesheet"
	href="css/scrollbar/jquery.mCustomScrollbar.min.css">
<!-- jvectormap CSS
		============================================ -->
<link rel="stylesheet" href="css/jvectormap/jquery-jvectormap-2.0.3.css">
<!-- notika icon CSS
		============================================ -->
<link rel="stylesheet" href="css/notika-custom-icon.css">
<!-- wave CSS
		============================================ -->
<link rel="stylesheet" href="css/wave/waves.min.css">
<!-- main CSS
		============================================ -->
<link rel="stylesheet" href="css/main.css">
<!-- style CSS
		============================================ -->
<link rel="stylesheet" href="style.css">
<!-- responsive CSS
		============================================ -->
<link rel="stylesheet" href="css/responsive.css">
<!-- modernizr JS
		============================================ -->
<script src="js/vendor/modernizr-2.8.3.min.js"></script>
</head>

<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	<!-- Start Header Top Area -->
	<div class="header-top-area">
		<div class="container">
			<div class="row">
				<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
					<div class="logo-area">
						<h3 style='color: white;'><%=Constants.PROJECT_NAME %></h3>
					</div>
				</div>
				<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
					<div class="header-top-menu">
						<h4 style='float: right; color: white; padding-top: 20px;'>SIMS Institutions</h4>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End Header Top Area -->
	<%@include file='mobile_menu.jsp' %>
	<!-- Main Menu area start-->
	<div class="main-menu-area mg-tb-40">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<ul class="nav nav-tabs notika-menu-wrap menu-it-icon-pro">
						<li ><a data-toggle="tab" href="#Home"> Welcome</a></li>
						<li ><a data-toggle="tab" href="#handles"> Twitter Handles</a></li>
						<li ><a data-toggle="tab" href="#keywords"> Twitter HashTags</a></li>
						<li class='active'><a data-toggle="tab" href="#analysis"> Analysis</a></li>
						<li ><a data-toggle="tab" href="#mailbox">
								<%=u1.getFname() %> <%=u1.getLname() %></a></li>
					</ul>
					<div class="tab-content custom-menu-content">
						<div id="Home"
							class="tab-pane notika-tab-menu-bg animated flipInX">
							<ul class="notika-main-menu-dropdown">
								<li><a href="welcome.jsp">Welcome</a></li>
										<li><a href="welcome.jsp">About</a></li>
										
										
										
							</ul>
						</div>
						<div id="mailbox"
							class="tab-pane notika-tab-menu-bg animated flipInX">
							<ul class="notika-main-menu-dropdown">
										<li><a href="updateprofile.jsp">Update Profile</a></li>
										<li><a href="changepassword.jsp">Change Password</a></li>
										<li><a href="account?request_type=deleteprofile">Delete Profile</a></li>
										<li><a href="account?request_type=logout">Logout</a></li>										
							</ul>
						</div>
						<div id="handles"
							class="tab-pane notika-tab-menu-bg animated flipInX">
							<ul class="notika-main-menu-dropdown">
										<li><a href="twitter_handle_add.jsp">Add New Handle</a></li>
										<li><a href="twitterhandle?requestType=get">View List of Handles</a></li>
							</ul>
						</div>
						<div id="keywords"
							class="tab-pane notika-tab-menu-bg animated flipInX">
							<ul class="notika-main-menu-dropdown">
										<li><a href="twitter_keyword_add.jsp">Add New Keyword</a></li>
										<li><a href="twitterkeyword?requestType=get">View List of Keywords</a></li>
							</ul>
						</div>
						<div id="analysis"
							class="tab-pane in active notika-tab-menu-bg animated flipInX">
							<ul class="notika-main-menu-dropdown">
										<li><a href="adhoc_run.jsp">Adhoc Run</a></li>
										<li><a href="run.jsp">Run Twitter Sentiment Analysis</a></li>
										<li><a href="result.jsp">View Twitter Sentiment Results</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Main Menu area End-->
	<!-- Start Status area -->
	<div class="notika-status-area">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="breadcomb-list">
						<div class="row">
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<div class="breadcomb-wp">
									<div class="breadcomb-ctn">
										<h2>Results</h2>
										<p>Here, you can view the results of the Twitter Sentiment Analysis Algorithm.</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br/><br/>

	<div class="form-element-area" >
		<div class="container">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="form-element-list">
						<div class="row" style='min-height:400px;'>

							<%
							   String msg = request.getParameter("msg");
							%>
							<%
							   if (msg != null)
							   {
							%>
							<div class="alert alert-success alert-dismissable">
								<a href="#" class="close" data-dismiss="alert"
									aria-label="close">&times;</a> <strong>Message!</strong>
								<%=msg%>.
							</div>
							<%
							   }
							%>


							<%
								String check = (String) session.getAttribute("inprogress");
								if (check != null && check.equals("yes"))
								{
								   %>
								   		<div class='row' style='padding:40px;'>
								   		<h4>The Algorithm is RUNNING. Please come back later. </h4>
								   		<br/>
								   		<br/>
								   		
								   		<img src='images/loading.gif' />
								   		</div>
								   <%
								}
								else
								{
								   %>
										
										<%
											Map<String, Map<String, Map<String, VScore>>> twitterhandleresults = (Map<String, Map<String, Map<String, VScore>>>) session.getAttribute("twitterhandleresults");
											Map<String, Map<String, Map<String, VScore>>> twitterkeywordresults = (Map<String, Map<String, Map<String, VScore>>>) session.getAttribute("twitterkeywordresults");
											
											%>
												
												<h3 style='padding:20px;'>Twitter Handler Analysis Report</h3>
												<hr/>
												<div class='row' style='padding:40px;'>
											<%
											
											if (twitterhandleresults != null && twitterhandleresults.size() > 0)
											{
											   %>
											   		
											   		<%
											   			Iterator<String> it = twitterhandleresults.keySet().iterator();
											   			int i=0;
											   			while (it.hasNext())
											   			{
											   			   i++;
											   			   String handler = it.next();
											   			   Map<String, Map<String, VScore>> handler_result = twitterhandleresults.get(handler);
											   			   		
											   			   %>
											   			   		
															   		<div class='col-md-3'>
													   			   		<div class="panel panel-default">
																		  <div class="panel-body">
																		   	<img src='images/handle.gif' width=40 />
																		   	
																		    <%=handler %>
																		    <br/>
																		    <br/>
																		  </div>
																		  <div class="panel-footer"><a href='#' data-toggle="modal" data-target="#result<%=i%>">View Results</a></div>
																		</div>
												   			   		</div>
												   			   		<div class="modal animated flash" id="result<%=i%>" role="dialog">
									                                    <div class="modal-dialog modal-large">
									                                        <div class="modal-content">
									                                            <div class="modal-header">
									                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
									                                            </div>
									                                            <div class="modal-body">
									                                                <h1>Sentiment Analysis Report for <%=handler %></h1>
									                                                <hr/>
									                                                <ul class="list-group">
									                                                
									                                                <%
									                                                	Iterator<String> it2 =  handler_result.keySet().iterator();
									                                                    int j=0;
									                                                    while (it2.hasNext())
									                                                    {
									                                                       j++;
									                                                       String tweet = it2.next();
									                                                       Map<String, VScore> result = handler_result.get(tweet);
									                                                       %>
									                                                       			<li class="list-group-item"> 
									                                                       				<%=tweet %>			
									                                                       				<br/><br/>
									                                                       						
									                                                       						<button class='btn btn-default btn-xs' onclick='showreport(<%=i%>,<%=j%>);' >Show Report</button>
									                                                       						
									                                                       						
									                                                       						
									                                                       						
									                                                       						<div id='report<%=i%><%=j%>' style='display:none;' class='row'>	
									                                                       						<br/><br/>			
									                                                       						<div style='padding-left:60px; padding-right:20px;'>		                                                       			
									                                                       											   <button class='btn btn-default btn-xs' onclick='hidereport();' style='float:right;'>Hide Report</button>
									                                                       											   <h5>Individual Tokens Analysis</h5>
																																		<hr/>
																																		<div class="accordion-stn">
																																	   		<div class="panel-group" data-collapse-color="nk-indigo"  id="accordion<%=i%>" role="tablist" aria-multiselectable="true">
																																	   
																																	   			<%
																																	   				Iterator<String> it3 = result.keySet().iterator();
																																		   			DecimalFormat df = new DecimalFormat();
																																		   			df.setMaximumFractionDigits(2);
																																	   				int k = 0;
																																	   				double positives = 0;
																																	   				double negatives = 0;
																																	   				double neutral = 0;
																																	   				while (it3.hasNext())
																																	   				{
																																	   				   k++;
																																	   				   String token = it3.next();
																																	   				   VScore vscore = result.get(token);
																																	   				   positives += vscore.getPositive();
																																	   				   negatives += vscore.getNegative();
																																	   				   neutral += vscore.getNeutral();
																																	   				   %>
																																						  <div class="panel panel-collapse notika-accrodion-cus">
																																						    <div class="panel-heading" role="tab" id="heading<%=i%><%=j%><%=k%>">
																																						      <h4 class="panel-title">
																																						        <a role="button" data-toggle="collapse" data-parent="#accordion<%=i%>" href="#collapse<%=i%><%=j%><%=k%>" aria-controls="collapse<%=i%><%=j%><%=k%>">
																																						          <%=token %>
																																						        </a>
																																						      </h4>
																																						    </div>
																																						    <div id="collapse<%=i%><%=j%><%=k%>" class="panel-collapse collapse animated zoomInLeft" role="tabpanel" aria-labelledby="heading<%=i%><%=j%><%=k%>">
																																						      <div class="panel-body">
																																						        
																																						        <ul class="list-group col-md-4">
																																								  <li class="list-group-item" >
																																								    <span class="badge"><%=df.format(vscore.getPositive()) %></span>
																																								    Positive Score
																																								  </li>
																																								  <li class="list-group-item">
																																								    <span class="badge"><%=df.format(vscore.getNeutral()) %></span>
																																								    Neutral Score
																																								  </li>
																																								  <li class="list-group-item" >
																																								    <span class="badge"><%=df.format(vscore.getNegative()) %></span>
																																								    Negative Score
																																								  </li>
																																								</ul>
																																						        
																																						        
																																						        
																																						      </div>
																																						    </div>
																																						  </div>	
																																		  		<% } %>
																																		  								 									
																																			</div>
																																		</div>
																																		<br/><br/>
																																		<h5>Overall Result</h5>
																																		<hr/>
																																		
																																		<%
																																			double total = positives + negatives + neutral;
																																			
																																		%>
																																								<ul class="list-group col-md-6">
																																								  <li class="list-group-item" >
																																								    <span class="badge"><%=df.format(positives/total * 100) %> %</span>
																																								    <span style='color: lightgreen;'><b>Positive Score</b></span>
																																								  </li>
																																								  <li class="list-group-item">
																																								    <span class="badge"><%=df.format(neutral/total * 100) %> %</span>
																																								    <b>Neutral Score</b>
																																								  </li>
																																								  <li class="list-group-item" >
																																								    <span class="badge"><%=df.format(negatives/total * 100) %> %</span>
																																								    <span style='color: red;'><b>Negative Score</b></span>
																																								  </li>
																																								</ul>
								   
									                                                       			
									                                                       			
									                                                       			
									                                                       			
									                                                       						</div>
									                                                       						</div>
									                                                       			</li>
									                                                       <%					                                                       
									                                                    }
									                                                %>
									                                                 
						                                                       		</ul>
									                                                
									                                                
									                                                
									                                            </div>
									                                            <div class="modal-footer">
									                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									                                            </div>
									                                        </div>
									                                    </div>
									                                </div>
												   			   		
												   			   		
											   			   		
											   			   
											   			   <%
											   			   
											   			}
											   		%>
											   		
											   <%	
											}
											else
											{
											 %>
											 	<h5>Twitter handler analysis report unavailable</h5>
											 <%  
											}									
												
											%>
												</div>
												<br/><br/>
												<h3 style='padding:20px;'>Twitter Keyword Analysis Report</h3>
												<hr/>
												<div class='row'>
													
											<div class='row' style='padding:40px;'>
											<%
											
											if (twitterkeywordresults != null && twitterkeywordresults.size() > 0)
											{
											   %>
											   		
											   		<%
											   			Iterator<String> it = twitterkeywordresults.keySet().iterator();
											   			int i=0;
											   			while (it.hasNext())
											   			{
											   			   i++;
											   			   String keyword = it.next();
											   			   Map<String, Map<String, VScore>> keyword_results = twitterkeywordresults.get(keyword);
											   			   		
											   			   %>
											   			   		
															   		<div class='col-md-3'>
													   			   		<div class="panel panel-default">
																		  <div class="panel-body">
																		   	<img src='images/keyword.png' width=40 />
																		   	
																		    <%=keyword %>
																		    <br/>
																		    <br/>
																		  </div>
																		  <div class="panel-footer"><a href='#' data-toggle="modal" data-target="#result2<%=i%>">View Results</a></div>
																		</div>
												   			   		</div>
												   			   		<div class="modal animated flash" id="result2<%=i%>" role="dialog">
									                                    <div class="modal-dialog modal-large">
									                                        <div class="modal-content">
									                                            <div class="modal-header">
									                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
									                                            </div>
									                                            <div class="modal-body">
									                                                <h1>Sentiment Analysis Report for <i>'<%=keyword %>'</i></h1>
									                                                <hr/>
									                                                <ul class="list-group">
									                                                
									                                                <%
									                                                	Iterator<String> it2 =  keyword_results.keySet().iterator();
									                                                    int j=0;
									                                                    while (it2.hasNext())
									                                                    {
									                                                       j++;
									                                                       String tweet = it2.next();
									                                                       Map<String, VScore> result = keyword_results.get(tweet);
									                                                       %>
									                                                       			<li class="list-group-item"> 
									                                                       				<%=tweet %>			
									                                                       				<br/><br/>
									                                                       						
									                                                       						<button class='btn btn-default btn-xs' onclick='showreport2(<%=i%>,<%=j%>);' >Show Report</button>
									                                                       						
									                                                       						
									                                                       						
									                                                       						
									                                                       						<div id='reportt<%=i%><%=j%>' style='display: none;' class='row'>	
									                                                       						<br/><br/>			
									                                                       						<div style='padding-left:60px; padding-right:20px;'>		                                                       			
									                                                       											   <button class='btn btn-default btn-xs' onclick='hidereport2();' style='float:right;'>Hide Report</button>
									                                                       											   <h5>Individual Tokens Analysis</h5>
																																		<hr/>
																																		<div class="accordion-stn">
																																	   		<div class="panel-group" data-collapse-color="nk-indigo"  id="accordionn<%=i%>" role="tablist" aria-multiselectable="true">
																																	   
																																	   			<%
																																	   				Iterator<String> it3 = result.keySet().iterator();
																																		   			DecimalFormat df = new DecimalFormat();
																																		   			df.setMaximumFractionDigits(2);
																																	   				int k = 0;
																																	   				double positives = 0;
																																	   				double negatives = 0;
																																	   				double neutral = 0;
																																	   				while (it3.hasNext())
																																	   				{
																																	   				   k++;
																																	   				   String token = it3.next();
																																	   				   VScore vscore = result.get(token);
																																	   				   positives += vscore.getPositive();
																																	   				   negatives += vscore.getNegative();
																																	   				   neutral += vscore.getNeutral();
																																	   				   %>
																																						  <div class="panel panel-collapse notika-accrodion-cus">
																																						    <div class="panel-heading" role="tab" id="headingg<%=i%><%=j%><%=k%>">
																																						      <h4 class="panel-title">
																																						        <a role="button" data-toggle="collapse" data-parent="#accordionn<%=i%>" href="#collapsee<%=i%><%=j%><%=k%>" aria-controls="collapsee<%=i%><%=j%><%=k%>">
																																						          <%=token %>
																																						        </a>
																																						      </h4>
																																						    </div>
																																						    <div id="collapsee<%=i%><%=j%><%=k%>" class="panel-collapse collapse animated zoomInLeft" role="tabpanel" aria-labelledby="headingg<%=i%><%=j%><%=k%>">
																																						      <div class="panel-body">
																																						        
																																						        <ul class="list-group col-md-4">
																																								  <li class="list-group-item" >
																																								    <span class="badge"><%=df.format(vscore.getPositive()) %></span>
																																								    Positive Score
																																								  </li>
																																								  <li class="list-group-item">
																																								    <span class="badge"><%=df.format(vscore.getNeutral()) %></span>
																																								    Neutral Score
																																								  </li>
																																								  <li class="list-group-item" >
																																								    <span class="badge"><%=df.format(vscore.getNegative()) %></span>
																																								    Negative Score
																																								  </li>
																																								</ul>
																																						        
																																						        
																																						        
																																						      </div>
																																						    </div>
																																						  </div>	
																																		  		<% } %>
																																		  								 									
																																			</div>
																																		</div>
																																		<br/><br/>
																																		<h5>Overall Result</h5>
																																		<hr/>
																																		
																																		<%
																																			double total = positives + negatives + neutral;
																																			
																																		%>
																																								<ul class="list-group col-md-6">
																																								  <li class="list-group-item" >
																																								    <span class="badge"><%=df.format(positives/total * 100) %> %</span>
																																								    <span style='color: lightgreen;'><b>Positive Score</b></span>
																																								  </li>
																																								  <li class="list-group-item">
																																								    <span class="badge"><%=df.format(neutral/total * 100) %> %</span>
																																								    <b>Neutral Score</b>
																																								  </li>
																																								  <li class="list-group-item" >
																																								    <span class="badge"><%=df.format(negatives/total * 100) %> %</span>
																																								    <span style='color: red;'><b>Negative Score</b></span>
																																								  </li>
																																								</ul>
								   
									                                                       			
									                                                       			
									                                                       			
									                                                       			
									                                                       						</div>
									                                                       						</div>
									                                                       			</li>
									                                                       <%					                                                       
									                                                    }
									                                                %>
									                                                 
						                                                       		</ul>
									                                                
									                                                
									                                                
									                                            </div>
									                                            <div class="modal-footer">
									                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									                                            </div>
									                                        </div>
									                                    </div>
									                                </div>
												   			   		
												   			   		
											   			   		
											   			   
											   			   <%
											   			   
											   			}
											   		%>
											   		
											   <%	
											}
											else
											{
											 %>
											 	<h5>Twitter Keyword analysis report unavailable</h5>
											 <%  
											}									
												
											%>
												</div>														   
								   			</div>
								   	
								   <%
								}
							%>
							

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>




	<!-- End Realtime sts area-->
	<!-- Start Footer area-->
	<div class="footer-copyright-area">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="footer-copy-right">
						<p>Copyright © 2021 <%=Constants.PROJECT_NAME%> by <%=Constants.COMPANY_NAME %></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End Footer area-->
	<!-- jquery
		============================================ -->
	<script src="js/vendor/jquery-1.12.4.min.js"></script>
	<!-- bootstrap JS
		============================================ -->
 	<script src="js/bootstrap.min.js"></script>
	<!-- wow JS
		============================================ -->
	<script src="js/wow.min.js"></script>
	<!-- price-slider JS
		============================================ -->
	<script src="js/jquery-price-slider.js"></script>
	<!-- owl.carousel JS
		============================================ -->
	<script src="js/owl.carousel.min.js"></script>
	<!-- scrollUp JS
		============================================ -->
	<script src="js/jquery.scrollUp.min.js"></script>
	<!-- meanmenu JS
		============================================ -->
	<script src="js/meanmenu/jquery.meanmenu.js"></script>
	<!-- counterup JS
		============================================ -->
	<script src="js/counterup/jquery.counterup.min.js"></script>
	<script src="js/counterup/waypoints.min.js"></script>
	<script src="js/counterup/counterup-active.js"></script>
	<!-- mCustomScrollbar JS
		============================================ -->
	<script src="js/scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- jvectormap JS
		============================================ -->
	<script src="js/jvectormap/jquery-jvectormap-2.0.2.min.js"></script>
	<script src="js/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
	<script src="js/jvectormap/jvectormap-active.js"></script>
	<!-- sparkline JS
		============================================ -->
	<script src="js/sparkline/jquery.sparkline.min.js"></script>
	<script src="js/sparkline/sparkline-active.js"></script>
	<!-- sparkline JS
		============================================ -->
	<script src="js/flot/jquery.flot.js"></script>
	<script src="js/flot/jquery.flot.resize.js"></script>
	<script src="js/flot/curvedLines.js"></script>
	<script src="js/flot/flot-active.js"></script>
	<!-- knob JS
		============================================ -->
	<script src="js/knob/jquery.knob.js"></script>
	<script src="js/knob/jquery.appear.js"></script>
	<script src="js/knob/knob-active.js"></script>
	<!--  wave JS
		============================================ -->
	<script src="js/wave/waves.min.js"></script>
	<script src="js/wave/wave-active.js"></script>
	<!--  todo JS
		============================================ -->
	<script src="js/todo/jquery.todo.js"></script>
	<!-- plugins JS
		============================================ -->
	<script src="js/plugins.js"></script>
	<!--  Chat JS
		============================================ -->
	<script src="js/chat/moment.min.js"></script>
	<script src="js/chat/jquery.chat.js"></script>
	<!-- main JS
		============================================ -->
	<script src="js/main.js"></script>
	<!-- tawk chat JS
		============================================ -->
</body>

<script>

function showreport(i, j)
{
	for (k=0;k<20;k++)
		{
			for (p=0;p<100;p++)
				{
					$('#report'+k+''+p).slideUp();
				}
		}
	
	$('#report'+i+''+j).slideDown();
}


function hidereport()
{
	for (k=0;k<20;k++)
		{
			for (p=0;p<100;p++)
				{
					$('#report'+k+''+p).slideUp();
				}
		}
	
}

function showreport2(i, j)
{
	for (k=0;k<20;k++)
		{
			for (p=0;p<100;p++)
				{
					$('#reportt'+k+''+p).slideUp();
				}
		}
	
	$('#reportt'+i+''+j).slideDown();
}


function hidereport2()
{
	for (k=0;k<20;k++)
		{
			for (p=0;p<100;p++)
				{
					$('#reportt'+k+''+p).slideUp();
				}
		}
	
}

</script>

</html>

<% } %>