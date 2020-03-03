<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/admin">Advertisements</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav w-100 justify-content-between">
            <div class="form-inline">
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/admin/account">Account</a>
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/admin/manageUsers">Manage Users</a>
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/admin/allNoticesByFilter">Notices By Filter</a>
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/choiceRole">DashBoard</a>
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/admin/logOut">LogOut</a>
        </div>
        <a class="nav-item nav-link">Hello, ${sessionScope.email}</a>
        </div>
    </div>
</nav>