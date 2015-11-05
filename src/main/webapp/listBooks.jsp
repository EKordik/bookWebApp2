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
        <title>Book List</title>

    </head>
    <body>
        <header id="header">
            <h1>Book List</h1>
        </header>
        <div class="container">
            <div class="row">
                <nav class="col-xs-3">
                    <div class="fixed">
                        <form method="POST" action="BookController?action=home">
                            <sec:csrfInput />
                            <button type="submit" class="btn ${btnClass} btnSpacing">Home</button>
                        </form>
                        <form method="POST" action="BookController?action=updateAdd">
                            <sec:csrfInput />
                            <button type="submit" class="btn ${btnClass} btnSpacing" data-toggle="modal">Add Book</button>
                        </form>
                    </div>
                </nav>
                <div class="col-xs-9">
                    <form method="POST" action="BookController?action=showByAuthor" class="form-inline bookForm">
                        <sec:csrfInput />
                        <input type="text" name="searchAuthor" id="searchAuthor" placeholder="Search Term" class="form-control">
                        <input type="submit" name="searchSubmit" id="searchSubmit" value="Search" class="btn btn-info">
                    </form>
        
                    <table width="700" border="1" cellspacing="0" cellpadding="4">
                        <tr style="background-color: black; color:white;">
                            <th align="left" class="tableHead">ID</th>
                            <th align="left" class="tableHead">Title</th>
                            <th align="right" class="tableHead">ISBN</th>
                            <th align="right" class="tableHead">Author</th>
                            <th align="left" class="tableHead"></th>
                            <th align="left" class="tableHead"></th>
                        </tr>
                        <c:forEach var="b" items="${books}" varStatus="rowCount">
                            <c:choose>
                                <c:when test="${rowCount.count % 2 == 0}">
                                    <tr style="background-color: white;">
                                    </c:when>
                                    <c:otherwise>
                                    <tr style="background-color: #ccffff;">
                                    </c:otherwise>
                                </c:choose>
                                <td align="left">${b.bookId}</td>
                                <td align="left">${b.title}</td>
                                <td align="left">
                                    ${b.isbn}
                                </td>
                                <td align="left">${b.authorId.authorName}</td>
                                 <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                <td align="center">
                                    <form name="deleteForm" method="POST" action="BookController?action=delete">
                                        <sec:csrfInput />
                                        <button name="deleteBook" value="${b.bookId}">
                                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                            <span class="sr-only">Delete</span>
                                        </button>
                                    </form>
                                </td>
                                <td align="center">
                                    <form name="updateForm" method="POST" action="BookController?action=updateAdd">    
                                        <sec:csrfInput />
                                        <button id="updateBook" name="updateBook" value="${b.bookId}">
                                            <c:set var="updateId" value="${b.bookId}"/>
                                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                            <span class="sr-only">Edit</span>
                                        </button>
                                    </form>
                                </td>
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </table>
                    <c:if test="${search != null}">
                    <form method="POST" action="BookController?action=list">
                        <input type="submit" name="exitSearch" id="exitSearch" value="Exit" class="btn btn-primary">
                    </form>
                    </c:if>
                    <c:if test="${errMsg != null}">
                        <div class="alert alert-danger" role="alert">
                        <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                            ${errMsg}</p>
                        </div>
                        </c:if>
                </div>
            </div>
             <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
                Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
                <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
            </sec:authorize>   
            
        </div>

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.0/jquery.validate.min.js"></script>
        <script src="bookWepApp.js"></script>
       
    </body>
</html>
