<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Notice edit form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>
<%@ include file="/header/userHeader.jsp" %>
<c:set var="notice" value="${requestScope.notice}"/>
<div class="card w-25 mx-auto mt-5">
    <div class="card-body">
        <form method="post">
            <caption><h3>Edit Notice</h3></caption>
            <input type="hidden" name="noticeId" value="${notice.id}"/>
            <div class="form-group">
                <label>Description</label>
                <input type="text" maxlength="200" class="form-control" aria-describedby="emailHelp" name="description"
                       value="${notice.description}">
                <c:if test="${not empty requestScope.description}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.description}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label>Notice type</label>
                <table>
                <c:forEach items="${requestScope.types}" var="types">
                    <tr>
                        <td>
                            <input type="checkbox" name="typeId" value="${types.id}">${types.type}
                        </td>
                    </tr>
                </c:forEach>
                </table>
                <c:if test="${not empty requestScope.typesForEdit}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.typesForEdit}
                    </p>
                </c:if>
            </div>
            <div>
            <button type="submit" name="button" value="updateNotice" class="btn btn-primary">Update</button>
            </div>
            <div>
            </div>
        </form>
    </div>
</div>
</body>
</html>