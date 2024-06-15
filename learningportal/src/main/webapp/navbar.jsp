<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	 <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="home.jsp">Learning Portal</a>
        <div class="navbar-nav mx-auto">
            <div class="nav-item">
                <span class="navbar-text">Welcome, <%= session.getAttribute("username") %></span>
            </div>
        </div>
        <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-user"></i> My Profile
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="#"><i class="fas fa-user-circle"></i> Profile</a>
                        <a class="dropdown-item" href="#"><i class="fas fa-book-open"></i> My Courses</a>
                        <a class="dropdown-item" href="performance.jsp"><i class="fas fa-chart-line"></i> My Performance</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="logoutController"><i class="fas fa-sign-out-alt"></i> Logout</a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
    </nav>
</body>
</html>