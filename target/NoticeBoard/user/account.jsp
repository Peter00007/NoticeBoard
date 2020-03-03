<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<c:choose>
    <c:when test="${requestScope.url eq '/noticeBoard/user/account'}">
        <%@ include file="/header/userHeader.jsp" %>
    </c:when>
    <c:otherwise>
        <%@ include file="/header/moderatorHeader.jsp" %>
    </c:otherwise>
</c:choose>
<c:set var="user" value="${requestScope.userForEdit}"/>
<div class="card w-25 mx-auto mt-5">
    <div class="card-body">
        <form method="post">
            <caption><h3>Edit Profile</h3></caption>
            <input type="hidden" name="idOfUpdatedUser" value="${user.id}"/>
            <div class="form-group">
                <label for="exampleInputEmail1">Name</label>
                <input type="text" minlength="2" maxlength="20" pattern="[A-Z][a-z]{1,19}" class="form-control"
                       aria-describedby="emailHelp" name="firstName" value="${user.name}">
                <c:if test="${not empty requestScope.firstName}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.firstName}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Last Name</label>
                <input type="text" minlength="2" maxlength="20" pattern="[A-Z][a-z]{1,19}" class="form-control"
                       aria-describedby="emailHelp" name="lastName" value="${user.lastName}">
                <c:if test="${not empty requestScope.lastName}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.lastName}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Email Address</label>
                <input type="email" minlength="20" maxlength="50" class="form-control" id="exampleInputEmail1"
                       aria-describedby="emailHelp" name="email" value="${user.email}">
                <c:if test="${not empty requestScope.email}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.email}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                <input type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,25}" minlength="8" maxlength="25"
                       name="password" class="form-control" id="exampleInputPassword1">
                <c:if test="${not empty requestScope.password}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.password}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Status</label>
                <input type="text" maxlength="200" class="form-control" name="status" value="${user.userStatus}"
                       readonly>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Role</label>
                <input type="text" maxlength="200" class="form-control" type="text" name="roles" value="${user.roles}"
                       readonly>
            </div>
            <button type="submit" name="button" value="update" class="btn btn-primary">Update</button>
            <div>
            </div>
        </form>
    </div>
</div>
</body>
</html>