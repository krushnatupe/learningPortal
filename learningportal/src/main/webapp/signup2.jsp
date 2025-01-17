<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>

<body>
<input type="hidden" id = "registrationstatus" value=<%=session.getAttribute("registrationstatus")%>>
<script>
var status = document.getElementById("registrationstatus").value;
console.log(status);
if(status=="failed")
	{
	alert("Registration failed");
	}

</script>
<%
session.setAttribute("registrationstatus","Success");
%>
    <nav class="navbar navbar-expand-lg bg-transparent">
        <div class="container-fluid">
          <a class="navbar-brand" href="#">Learning Portal</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div
      class="collapse navbar-collapse justify-content-center"
      id="navbarCenteredExample"
    >
      <!-- Left links -->
      <ul class="navbar-nav mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Link</a>
        </li>
        <!-- Navbar dropdown -->
        <li class="nav-item dropdown">
          <a
            data-mdb-dropdown-init
            class="nav-link dropdown-toggle"
            href="#"
            id="navbarDropdown"
            role="button"
            aria-expanded="false"
          >
            Dropdown
          </a>
          <!-- Dropdown menu -->
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li>
              <a class="dropdown-item" href="#">Action</a>
            </li>
            <li>
              <a class="dropdown-item" href="#">Another action</a>
            </li>
            <li><hr class="dropdown-divider" /></li>
            <li>
              <a class="dropdown-item" href="#">Something else here</a>
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link disabled"
            >Disabled</a
          >
        </li>
      </ul>
      <!-- Left links -->
    </div>
        </div>
      </nav>

    <section class="vh-100 mt-5">
        <div class="container-fluid h-custom">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-md-9 col-lg-6 col-xl-5">
                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                        class="img-fluid" alt="Sample image">
                </div>
                <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                    <form action="registration">

                        <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example1cg">Your Name</label>
                            <input type="text" id="form3Example1cg" name="name" class="form-control form-control-lg" />
                        </div>

                        <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example3cg">Your Email</label>
                            <input type="email" id="form3Example3cg" name="email" class="form-control form-control-lg" />
                        </div>

                        <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example4cg">Password</label>
                            <input type="password" id="form3Example4cg" name="password" class="form-control form-control-lg" />
                        </div>

                        <div class="form-outline mb-4">
                            <label class="form-label" for="form3Example4cdg">Repeat your password</label>
                            <input type="password" id="form3Example4cdg" name="cpassword" class="form-control form-control-lg" />
                        </div>

                        <div class="form-check d-flex justify-content-center mb-5">
                            <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3cg" />
                            <label class="form-check-label" for="form2Example3g">
                                I agree all statements in <a href="#!" class="text-body"><u>Terms of service</u></a>
                            </label>
                        </div>

                        <div class="d-flex justify-content-center">
                            
                        </div>
						<button type="submit" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Register</button>
                        <p class="text-center text-muted mt-5 mb-0">Have already an account? <a href="login.jsp"
                                class="fw-bold text-body"><u>Login here</u></a></p>

                    </form>
                </div>
            </div>
        </div>

    </section>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>

</html>