<%-- 
    Document   : listAuthors
    Created on : Sep 23, 2015, 8:44:05 PM
    Author     : ekordik
    Purpose    : Home page that has options to go to preferences or see a list
                of authors
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Book Web App</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/bookWebApp.css">
    </head>
    <body>
        <header id="header">
            <h1>Pick an Administrative Task</h1>
        </header>
        <div class="container">
            <div class="row">
                <div class="col-xs-4"></div>
                <div class="col-xs-8">
                        <c:choose>
                            <c:when test="${empty btnClass}">
                                <form method="POST" action="AuthorController?action=list" class="indexForm">
                                    <button type="submit" class="btn btn-primary btnSpacing">View All Authors</button>
                                </form>
                                <form method="POST" action="BookController?action=list" class="indexForm">
                                    <button type="submit" class="btn btn-primary btnSpacing">View All Books</button>
                                </form>
                                <form method="POST" action="PreferencesController?action=preference" class="indexForm">
                                    <button type="submit" class="btn btn-primary btnSpacing">Preferences</button>
                                </form>
                                
                                </c:when>
                                <c:otherwise>
                                <form method="POST" action="AuthorController?action=list" class="indexForm">
                                    <button type="submit" class="btn ${btnClass} btnSpacing">View All Authors</button>
                                </form>
                                <form method="POST" action="BookController?action=list" class="indexForm">
                                    <button type="submit" class="btn btn-primary btnSpacing">View All Books</button>
                                </form>
                                <form method="POST" action="PreferencesController?action=preference" class="indexForm">
                                    <button type="submit" class="btn ${btnClass} btnSpacing">Preferences</button>
                                </form>
                                </c:otherwise>
                            </c:choose>    
                </div>
            </div>
            
             
        </div>
        
        <jsp:include page="footer.jsp"/> 
        
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
    </body>
</html>
