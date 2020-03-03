<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<%@ include file="/header/mainHeader.jsp" %>
<div class="card w-25 mx-auto mt-5">
    <div class="card-body">
        <form action="registration" method="post">
            <div class="form-group">
                <label for="exampleInputEmail1">Name</label>
                <input type="text" minlength="2" maxlength="20" pattern="[A-Z]{1}[a-z]{1,19}" class="form-control"
                       aria-describedby="emailHelp"
                       name="name">
                <c:if test="${not empty requestScope.firstName}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.firstName}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Last Name</label>
                <input type="text" minlength="2" maxlength="20" class="form-control"
                       aria-describedby="emailHelp" pattern="[A-Z]{1}[a-z]{1,19}" name="lastName">
                <c:if test="${not empty requestScope.lastName}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.lastName}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Email Address</label>
                <input type="email" minlength="20" maxlength="50" class="form-control" id="exampleInputEmail1"
                       aria-describedby="emailHelp" name="email">
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                    else.</small>
                <c:if test="${not empty requestScope.email}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.email}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                <input type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,25}" minlength="8" maxlength="25"
                       name="password" class="form-control"
                       id="exampleInputPassword1">
                <c:if test="${not empty requestScope.password}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.password}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword2">Confirm Password</label>
                <input type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,25}" minlength="8" maxlength="25"
                       name="confirmPassword" class="form-control"
                       id="exampleInputPassword2">
                <c:if test="${not empty requestScope.confirmPassword}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.confirmPassword}
                    </p>
                </c:if>
            </div>
            <button type="submit" name="button" value="registration" class="btn btn-primary">Registration</button>
            <div>
            </div>
            <c:if test="${not empty requestScope.error}">
                <p class="error mt-3" style="color: #d61527">
                        ${requestScope.error}
                </p>
            </c:if>
        </form>
    </div>
</div>
</body>
</html>
