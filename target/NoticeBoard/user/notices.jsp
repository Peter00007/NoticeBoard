<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Manage Notices</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .add-user {
            position: fixed;
            bottom: 0;
            right: 20px;
        }

        .btn-link.edit {
            color: #00983d;
        }

        .btn-link.delete {
            color: #de1d1d;
        }
    </style>
</head>
<body>
<%@ include file="/header/userHeader.jsp" %>
<c:choose>
    <c:when test="${not empty sessionScope.message}">
        <div class="alert alert-primary &lt;%&ndash;w-75&ndash;%&gt; mx-auto mt-3" style="width: 90%" role="alert">
                ${sessionScope.message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${not empty requestScope.delete}">
        <div class="alert alert-danger &lt;%&ndash;w-75&ndash;%&gt; mx-auto mt-3" style="width: 90%" role="alert">
                ${requestScope.delete}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${not empty requestScope.notices}">
<div class="card mt-3  mx-auto" style="width: 90%">
    <h5 class="card-header">List of Notices</h5>
    <div class="card-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Description</th>
                <th scope="col">Status</th>
                <th scope="col">Created</th>
                <th scope="col">Type</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <c:forEach items="${requestScope.notices}" var="notice">
                <c:set var="date" value="${notice.created}"/>
                <td>${notice.description}
                </td>
                <td>${notice.status}
                </td>
                <td>${f:formatLocalDateTime(date, 'dd.MM.yyyy HH:mm')}
                </td>
                <td>${notice.types}
                </td>
                <td>
                    <form method="post">
                        <input type="hidden" name="noticeId" value="${notice.id}"/>
                        <button class="btn btn-link edit" type="submit" name="button" value="updateNoticeForm">
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                        </button>
                        <button class="btn btn-link delete" type="submit" name="button" value="deleteNotice">
                            <i class="fa fa-trash" aria-hidden="true"></i>
                        </button>
                    </form>
                </td>
                </tr>
            </c:forEach>
        </table>
        <form method="post" class="add-user">
            <button class="btn btn-primary" type="submit" name="button" value="addNoticeForm">Add
            </button>
        </form>
    </div>
</div>
</c:when>
<c:otherwise>
<div class="card mt-3  mx-auto" style="width: 90%">
    <h5 class="card-header">You don't have any Notice</h5>
    <form method="post" class="add-user">
        <button class="btn btn-primary" type="submit" name="button" value="addNoticeForm">Add
        </button>
    </form>
</div>
</c:otherwise>
</c:choose>
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