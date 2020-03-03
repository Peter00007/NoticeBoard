<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change password</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<%@ include file="/header/mainHeader.jsp" %>
<div class="card w-25 mx-auto mt-5">
    <div class="card-body">
        <form method="post">
            <div class="form-group">
                <label for="exampleInputEmail1">Your email:</label>
                <input type="email" minlength="10" maxlength="50" class="form-control" id="exampleInputEmail1"
                       name="confirmEmail">
                <c:if test="${not empty requestScope.error}">
                    <p class="error" style="color: #d61527">
                            ${requestScope.error}
                    </p>
                </c:if>
            </div>
            <button type="submit" name="button" value="confirm" class="btn btn-primary">Confirm</button>
            <div>
            </div>
        </form>
    </div>
</div>
</body>
</html>