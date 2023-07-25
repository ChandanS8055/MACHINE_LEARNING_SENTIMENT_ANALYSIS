	<!-- Mobile Menu start -->
	<div class="mobile-menu-area">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="mobile-menu">
						<nav id="dropdown">
							<ul class="mobile-menu-nav">
								<li><a data-toggle="collapse" data-target="#Charts"
									href="#">Welcome</a>
									<ul class="collapse dropdown-header-top">
										<li><a href="welcome.jsp">Welcome</a></li>
										<li><a href="welcome.jsp">About</a></li>
										
										
										
									</ul></li>
								<li><a data-toggle="collapse" data-target="#handles"
									href="#">Twitter Handles</a>
									<ul class="collapse dropdown-header-top">
										<li><a href="twitter_handle_add.jsp">Add New Handle</a></li>
										<li><a href="twitterhandle?requestType=get">View List
												of Handles</a></li>
									</ul></li>
								<li><a data-toggle="collapse" data-target="#keywords"
									href="#">Twitter Keywords</a>
									<ul class="collapse dropdown-header-top">
										<li><a href="twitter_keyword_add.jsp">Add New Keyword</a></li>
										<li><a href="twitterkeyword?requestType=get">View List
												of Keywords</a></li>
									</ul></li>

								<li><a data-toggle="collapse" data-target="#keywords"
									href="#">Analysis</a>
									<ul class="collapse dropdown-header-top">
										<li><a href="adhoc_run.jsp">Ad-hoc Run</a></li>
										<li><a href="run.jsp">Run Twitter Sentiment Analysis</a></li>
										<li><a href="result.jsp">View Twitter Sentiment Results</a></li>
									</ul></li>
									
								<li><a data-toggle="collapse" data-target="#demoevent"
									href="#"><%=u1.getFname() %> <%=u1.getLname() %></a>
									<ul id="demoevent" class="collapse dropdown-header-top">
										<li><a href="updateprofile.jsp">Update Profile</a></li>
										<li><a href="changepassword.jsp">Change Password</a></li>
										<li><a href="account?request_type=deleteprofile">Delete Profile</a></li>
										<li><a href="account?request_type=logout">Logout</a></li>										
									</ul></li>
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Mobile Menu end -->
