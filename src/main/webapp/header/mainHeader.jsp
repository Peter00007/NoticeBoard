<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Advertisements</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <c:choose>
            <c:when test="${empty sessionScope.userId}">
                <div class="navbar-nav">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/authorization">LogIn</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/registration">Registration</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="navbar-nav w-100 justify-content-between">
                    <div class="form-inline">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/choiceRole">DashBoard</a>
                    </div>
                    <a class="nav-item nav-link">Hello, ${sessionScope.email}</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
