<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Filter</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/css/bootstrap-multiselect.css">
    <style>
        .multiselect-container {
            width: 300px;
        }

        .card {
            width: 50%;
            margin: 30px auto;
        }
    </style>
</head>
<body>
<c:choose>
    <c:when test="${requestScope.url eq '/noticeBoard/moderator/allNoticesByFilter'}">
        <%@ include file="/header/moderatorHeader.jsp" %>
    </c:when>
    <c:otherwise>
        <%@ include file="/header/adminHeader.jsp" %>
    </c:otherwise>
</c:choose>


<div class="card">
    <div class="card-header">
        Filters
    </div>
    <div class="card-body">
        <form method="post">
            <select name="type" id="types" multiple="multiple">
                <c:forEach items="${requestScope.types}" var="type">
                    <option value="${type.id}">${type.type}</option>
                </c:forEach>
            </select>
            <select name="user" id="users" multiple="multiple">
                <c:forEach items="${requestScope.users}" var="user">
                    <option value="${user.id}">${user.email}</option>
                </c:forEach>
            </select>
            <button class="btn btn-primary" type="submit" name="button" value="search">
                Search
            </button>
            <c:if test="${not empty requestScope.error}">
                <p class="error mt-3" style="color: #d61527">
                        ${requestScope.error}
                </p>
            </c:if>
        </form>
    </div>
</div>

<c:choose>
<c:when test="${not empty requestScope.list}">
<div class="card mt-3  mx-auto" style="width: 90%">
    <h5 class="card-header">List of Notices</h5>
    <div class="card-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Description</th>
                <th scope="col">User Email</th>
                <th scope="col">Created</th>
                <th scope="col">Type</th>
            </tr>
            </thead>
            <c:forEach items="${requestScope.list}" var="notice">
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
</c:when>
<c:otherwise>
    <div class="card" align="center">
        No results by your request!
    </div>
</c:otherwise>
</c:choose>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js"></script>
<script type="text/javascript">
    $('#types').multiselect();
    $('#users').multiselect();
</script>
</body>
</html>