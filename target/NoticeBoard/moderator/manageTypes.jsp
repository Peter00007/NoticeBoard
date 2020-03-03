<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Types</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
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
<%@ include file="/header/moderatorHeader.jsp" %>
<c:choose>
    <c:when test="${not empty sessionScope.updated}">
        <div class="alert alert-primary w-50 mx-auto mt-3" role="alert">
                ${sessionScope.updated}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${not empty sessionScope.created}">
        <div class="alert alert-primary  w-50 mx-auto mt-3" role="alert">
                ${sessionScope.created}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${not empty requestScope.deleted}">
        <div class="alert alert-danger w-50 mx-auto mt-3" role="alert">
                ${requestScope.deleted}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${not empty requestScope.error}">
        <div class="alert alert-danger w-50 mx-auto mt-3" role="alert">
                ${requestScope.error}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>
<div class="card w-50 mt-3  mx-auto">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5>List of all Types</h5>
        <form method="post" class="mb-0">
            <button class="btn btn-primary" type="submit" name="button" value="addTypeForm">Add
            </button>
        </form>
    </div>
    <div class="card-body">
    <table class="table table-hover">
        <tr>
            <th scope="col">type</th>
            <th scope="col">actions</th>
        </tr>
        <c:forEach items="${requestScope.types}" var="type">
        <tr>
            <td>${type.type}
            </td>
            <td>
                <form method="post">
                    <input type="hidden" name="typeId" value="${type.id}"/>
                    <button class="btn btn-link edit" type="submit" name="button"
                            value="updateTypeForm"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                    </button>
                    <button class="btn btn-link delete" type="submit" name="button"
                            value="deleteType"><i class="fa fa-trash" aria-hidden="true"></i>
                    </button>
                </form>
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