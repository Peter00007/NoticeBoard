<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Type</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<%@ include file="/header/moderatorHeader.jsp" %>
<c:set var="type" value="${requestScope.type}"/>
<div class="card w-25 mx-auto mt-5">
    <div class="card-body">
        <form method="post">
            <caption><h3>Edit Type</h3></caption>
            <input type="hidden" name="typeId" size="30" value="${type.id}"/>
            <div class="form-group">
                <label>Type</label>
                <input type="text" minlength="3" maxlength="40" class="form-control" aria-describedby="emailHelp"
                       name="type" value="${type.type}">
                <c:if test="${not empty requestScope.error}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.error}
                    </p>
                </c:if>
            </div>
            <button type="submit" name="button" value="updateType" class="btn btn-primary">Update</button>
        </form>
    </div>
</div>
</body>
</html>