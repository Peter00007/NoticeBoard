<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>Add Notice</title>
</head>
<body>
<%@ include file="/header/userHeader.jsp" %>
<div class="card w-25 mx-auto mt-5">
    <div class="card-body">
        <form method="post">
            <caption><h3>Add Notice</h3></caption>
            <div class="form-group">
                <label>Description</label>
                <input type="text" minlength="10" max="200" class="form-control" aria-describedby="emailHelp"
                       name="description">
                <c:if test="${not empty requestScope.description}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.description}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label>Type notice</label>
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
            <button type="submit" name="button" value="update" class="btn btn-primary">Add</button>
        </form>
        <div>
        </div>
    </div>
</div>
</body>
</html>
