<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>Admin Account</title>
</head>
<body>
<%@ include file="/header/adminHeader.jsp" %>
<c:if test="${not empty sessionScope.message}">
    <div class="alert alert-primary &lt;%&ndash;w-75&ndash;%&gt; mx-auto mt-3" style="width: 90%" role="alert">
            ${sessionScope.message}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>
<div class="card mt-3  mx-auto" style="width: 90%">
    <h5 class="card-header">List of Notices</h5>
    <div class="card-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Description</th>
                <th scope="col">User Email</th>
                <th scope="col">created</th>
                <th scope="col">type</th>
            </tr>
            </thead>
            <c:forEach items="${requestScope.notices}" var="notice">
                <c:set var="date" value="${notice.created}"/>
                <tr>
                    <td>${notice.description}
                    </td>
                    <td>${notice.user.email}
                    </td>
                    <td>${f:formatLocalDateTime(date, 'dd.MM.yyyy HH:mm')}
                    </td>
                    <td>${notice.types}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>
