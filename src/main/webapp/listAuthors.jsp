<%-- 
    Document   : listAuthors
    Created on : Sep 23, 2015, 8:44:05 PM
    Author     : ekordik
    Purpose    : display list of author records and deletes records (in the future) provide
                 a way to add/edit records
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
        <title>Author List</title>

    </head>
    <body>
        <header id="header">
            <h1>Author List</h1>
        </header>
        <div class="container">
            <div class="row">
                <nav class="col-xs-3">
                    <div class="fixed">
                        <form method="POST" action="AuthorController?action=home">
                            <sec:csrfInput />
                            <button type="submit" class="btn ${btnClass} btnSpacing">Home</button>
                        </form>
                        <sec:authorize access="hasAnyRole('ROLE_MGR', 'ROLE_ADMIN')">
                            <form method="POST" action="AuthorController?action=insert">
                                <sec:csrfInput />
                                <button type="submit" class="btn ${btnClass} btnSpacing" data-toggle="modal" href="#insertModal">Add Author</button>
                            </form>
                        </sec:authorize>
                    </div>
                </nav>
                <div class="col-xs-9">
                    <table width="500" border="1" cellspacing="0" cellpadding="4">
                        <tr style="background-color: black; color:white;">
                            <th align="left" class="tableHead">ID</th>
                            <th align="left" class="tableHead">Author Name</th>
                            <th align="right" class="tableHead">Date Added</th>
                            <th align="left" class="tableHead"></th>
                            <th align="left" class="tableHead"></th>
                        </tr>
                        <c:forEach var="a" items="${authors}" varStatus="rowCount">
                            <c:choose>
                                <c:when test="${rowCount.count % 2 == 0}">
                                    <tr style="background-color: white;">
                                    </c:when>
                                    <c:otherwise>
                                    <tr style="background-color: #ccffff;">
                                    </c:otherwise>
                                </c:choose>
                                <td align="left">${a.authorId}</td>
                                <td align="left">${a.authorName}</td>
                                <td align="right">
                                    <fmt:formatDate pattern="M/d/yyyy" value="${a.dateCreated}"></fmt:formatDate>
                                </td>
                                 <sec:authorize access="hasAnyRole('ROLE_MGR', 'ROLE_ADMIN')">
                                <td align="center">
                                    <form name="deleteForm" method="POST" action="AuthorController?action=delete">
                                        <sec:csrfInput />
                                        <button href="AuthorController?action=delete" name="deleteAuthor" value="${a.authorId}">
                                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                            <span class="sr-only">Delete</span>
                                        </button>
                                    </form>
                                </td>
                                <td align="center">
                                    <form name="updateForm" method="POST" action="AuthorController?action=findUpdate">  
                                        <sec:csrfInput />
                                        <button id="updateAuthor" name="updateAuthor" value="${a.authorId}">
                                            <c:set var="updateId" value="${a.authorId}"/>
                                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                            <span class="sr-only">Edit</span>
                                        </button>
                                    </form>
                                </td>
                                 </sec:authorize>
                            </tr>
                        </c:forEach>
                    </table>
                    <c:if test="${errMsg != null}">
                        <div class="alert alert-danger" role="alert">
                        <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                            ${errMsg}</p>
                        </div>
                        </c:if>
                </div>
            </div>
   
        </div>
                        
        <jsp:include page="footer.jsp"/> 
 
         

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
        <script src="/resources/js/bookWepApp.js"></script>
       
    </body>
</html>
