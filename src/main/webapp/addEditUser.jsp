<%-- 
    Document   : addEditUser
    Created on : Nov 5, 2015, 12:12:23 PM
    Author     : emmakordik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/bookWebApp.css">
        <title>BookWebApp - Manage Users</title>
    </head>
    <body>
        <header id="header">
            <h1>Manage Users</h1>
        </header>
        
        <div class="container">
            <div class="row">
                <nav class="col-xs-3">
                    <div class="fixed">
                       <form method="POST" action="PreferencesController?action=home">
                            <sec:csrfInput />
                            <button type="submit" class="btn ${btnClass} btnSpacing">Home</button>
                        </form>
                        
                        <form method="POST" action="PreferencesController?action=preference" class="indexForm">
                            <sec:csrfInput />
                            <button type="submit" class="btn ${btnClass} btnSpacing">Preferences</button>
                        </form>
                    </div>
                </nav>
                <div class="col-xs-9">
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                        <c:choose>
                            <c:when test="${not empty user}">
                        
                    <h3>Edit User</h3>
                    <form method="POST" action="PreferencesController?action=insert" id="userForm" name="userForm">
                        <sec:csrfInput />
                        <label for="username">Username:</label>
                        <input type="text" name="username" id="username" value="${user.username}"><br>
                        <label for="password">Password:</label>
                        <!--<input type="text" name="password" id="password" value="${user.password}"></br>-->
                        <label for="role">Role: </label>
                        <select name="role" id="role">
                            <c:forEach var="r" items="${roles}">
                                <c:choose>
                                    <c:when test="${r.username == user}">
                                        <option value="${r.authority}" selected>${r.authority}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${r.authority}">${r.authority}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <br>
                        <c:choose>
                            <c:when test="${user.enabled}">
                                <label for="enableTrue">Enabled</label>
                                <input type="radio" name="enable" id="enableTrue" value="true" checked>
                                <label for="enableFalse">Not Enabled</label>
                                <input type="radio" name="enable" id="enableFalse" value="false">
                            </c:when>
                            <c:otherwise>
                                <label for="enableTrue">Enabled</label>
                                <input type="radio" name="enable" id="enableTrue" value="true">
                                <label for="enableFalse">Not Enabled</label>
                                <input type="radio" name="enable" id="enableFalse" value="false" checked>
                            </c:otherwise>
                        </c:choose>
                        <br>
                        <input type="submit" name="submitUpdate" id="submitUpdate" value="Update User" class="btn ${btnClass}">
                            </c:when>
                    <c:otherwise>
                        <h3>Add New User</h3>
                        <label for="username">Username:</label>
                        <input type="text" name="username" id="username" value="${user.username}"><br>
                        <!--<input type="text" name="password" id="password" value="${user.password}"></br>-->
                        <label for="role">Role: </label>
                        <select name="role" id="role">
                            <c:forEach var="r" items="${roles}">
                                <option value="${r.authority}">${r.authority}</option>
                            </c:forEach>
                        </select>
                        <br>
                        <label for="enableTrue">Enabled</label>
                        <input type="radio" name="enable" id="enableTrue" value="true" checked>
                        <label for="enableFalse">Not Enabled</label>
                        <input type="radio" name="enable" id="enableFalse" value="false">
                          
                        <br>
                        <input type="submit" name="submitUpdate" id="submitUpdate" value="Update User" class="btn ${btnClass}">
                    </form>
                        
                    </c:otherwise>
                    </c:choose>
                    </sec:authorize>
                </div>
            </div>
                        
       
        </div>

        <jsp:include page="footer.jsp"/> 
  
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.0/jquery.validate.min.js"></script>
        <script src="resources/js/bookWepApp.js"></script>
      
    </body>
</html>
