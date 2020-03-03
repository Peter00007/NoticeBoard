<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choose role</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%@ include file="/header/mainHeader.jsp" %>
<div class="jumbotron h-100 mb-0">
    <p class="lead">Please choose your role and continue working! Have a nice day!</p>
    <hr class="my-4">
    <p class="lead text-center mt-5">
        <c:forEach items="${sessionScope.roles}" var="roles">
            <c:choose>
                <c:when test="${roles eq 'User'}">
                        <a class="btn btn-primary btn-lg" role="button" href="${pageContext.request.contextPath}/user">
                            <i class="fa fa-user"></i> User
                        </a>
                </c:when>
                <c:when test="${roles eq 'Moderator'}">
                        <a class="btn btn-primary btn-lg" role="button" href="${pageContext.request.contextPath}/moderator">
                            <i class="fa fa-cogs" aria-hidden="true"></i> Moderator
                        </a>
                </c:when>
                <c:when test="${roles eq 'Admin'}">
                        <a class="btn btn-primary btn-lg" role="button" href="${pageContext.request.contextPath}/admin">
                            <i class="fa fa-user-secret" aria-hidden="true"></i> Admin
                        </a>
                </c:when>
            </c:choose>
        </c:forEach>
    </p>
</div>

</body>
</html>
