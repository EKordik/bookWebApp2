<%-- 
    Document   : listAuthors
    Created on : Sep 23, 2015, 8:44:05 PM
    Author     : ekordik
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="bookWebApp.css">
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
                    <ul>
                        <li>Delete Author</li>
                        <li>Update Author</li>
                        <li>Insert Author</li>
                    </ul>
                </div>
            </nav>
            <div class="col-xs-9">
                <table width="500" border="1" cellspacing="0" cellpadding="4">
                    <tr style="background-color: black;color:white;">
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
                    <td align="center">
                        <form name="deleteForm" method="POST" action="AuthorController?action=delete">
                        <button href="AuthorController?action=delete" name="deleteAuthor" value="${a.authorId}">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                            <span class="sr-only">Delete</span>
                        </button>
                        </form>
                    </td>
                    <td align="center"><a href=""><span class="glyphicon glyphicon-pencil" aria-hidden="true" name="${a.authorId}"></span>
                            <span class="sr-only">Edit</span></a>
                    </td>
                </tr>
                </c:forEach>
                </table>
                <c:if test="${errMsg != null}">
                    <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                        ${errMsg}</p>
                </c:if>
            </div>
        </div>
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>    
    </body>
</html>
