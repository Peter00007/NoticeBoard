<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Moderator</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
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
    <c:when test="${not empty requestScope.approved}">
        <div class="alert alert-primary &lt;%&ndash;w-75&ndash;%&gt; mx-auto mt-3" style="width: 90%" role="alert">
                ${requestScope.approved}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${not empty requestScope.rejected}">
        <div class="alert alert-danger &lt;%&ndash;w-75&ndash;%&gt; mx-auto mt-3" style="width: 90%" role="alert">
                ${requestScope.rejected}
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
                    <c:forEach items="${requestScope.notices}" var="notices">
                        <c:set var="date" value="${notices.created}"/>
                        <tr>
                            <td>${notices.description}
                            </td>
                            <td>${notices.status}
                            </td>
                            <td>${f:formatLocalDateTime(date, 'dd.MM.yyyy HH:mm')}
                            </td>
                            <td>${notices.types}
                            </td>
                            <td>
                                <form method="post">
                                    <input type="hidden" name="noticeId" value="${notices.id}"/>
                                    <button class="btn btn-link edit" type="submit" name="button" value="approveNotice">
                                        <i class="fa fa-check-circle-o" aria-hidden="true"></i>
                                    </button>
                                    <button class="btn btn-link delete" type="submit" name="button"
                                            value="rejectNotice">
                                        <i class="fa fa-ban" aria-hidden="true"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </c:when>
    <c:otherwise>
<div class="card mt-3  mx-auto" style="width: 90%">
    <h5 class="card-header">Any Notice for approve/reject</h5>
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