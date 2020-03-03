<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Confirm Registration</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<%@ include file="/header/mainHeader.jsp" %>
<div class="card w-25 mx-auto mt-5">
    <div class="card-body">
        <form method="post">
            <div class="form-group">
                <label for="exampleInputEmail1">Input 6 number, that you receive to your email, please:</label>
                <input type="text" pattern="[0-9]{6}" class="form-control" id="exampleInputEmail1"
                       name="activationCode">
                <c:if test="${not empty sessionScope.error}">
                    <p class="error" style="color: #d61527">
                            ${sessionScope.error}
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
