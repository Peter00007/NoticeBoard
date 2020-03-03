<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>Reject Notice</title>
</head>
<body>
<%@ include file="/header/moderatorHeader.jsp" %>
<div class="card w-25 mx-auto mt-5">
    <div class="card-body">
        <form method="post">
            <caption><h3>Letter</h3></caption>
            <input type="hidden" name="noticeId" value="${requestScope.noticeId}"/>
            <div class="form-group">
                <label for="exampleInputEmail1">Subject</label>
                <input type="text" minlength="5" maxlength="50" class="form-control" id="exampleInputEmail1"
                       aria-describedby="emailHelp" name="subject">
                <c:if test="${not empty requestScope.subject}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.subject}
                    </p>
                </c:if>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Message</label>
                <input type="text" minlength="10" maxlength="200" name="description" class="form-control"
                       id="exampleInputPassword1">
                <c:if test="${not empty requestScope.message}">
                    <p class="error mt-3" style="color: #d61527">
                            ${requestScope.message}
                    </p>
                </c:if>
            </div>
            <button type="submit" name="button" value="sendMessageWithCause" class="btn btn-primary">Send</button>
        </form>
    </div>
</div>
</body>
</html>