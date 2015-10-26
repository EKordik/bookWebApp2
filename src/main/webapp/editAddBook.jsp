<%-- 
    Document   : editAddBook
    Created on : Oct 19, 2015, 9:36:24 PM
    Author     : emmakordik
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
        <title>Edit Books</title>

    </head>
    <body>
        <header id="header">
            <h1>Edit Book</h1>
        </header>
        <div class="container">
            <div class="row">
                <nav class="col-xs-3">
                    <div class="fixed">
                       <form method="POST" action="BookController?action=home">
                            <button type="submit" class="btn ${btnClass} btnSpacing">Home</button>
                        </form>
                        <form method="POST" action="BookController?action=list" class="indexForm">
                            <button type="submit" class="btn ${btnClass} btnSpacing">View All Books</button>
                        </form>
                    </div>
                </nav>
                <div class="col-xs-9">
                    <h3>Edit/Add Book</h3>
                    <form method="POST" action="BookController?action=addEdit" id="updateForm" name="updateForm">
                        <c:choose>
                            <c:when test="${not empty book}">
                                <input type="hidden" name="updateId" value="${book.bookId}">
                                <label for="title">Title</label>
                                <input type="text" name="title" id="title" value="${book.title}">
                                <label for="isbn">ISBN</label>
                                <input type="text" name="isbn" id="isbn" value="${book.isbn}">
                                <label for="author">Author</label>
                                <select name="author" id="author">
                                    <c:forEach var="a" items="${authors}">
                                        <c:choose>
                                            <c:when test="${book.authorId == a}">
                                                <option value="${a.authorId}" selected>${a.authorName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${a.authorId}">${a.authorName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                        
                                    </c:forEach>
                                </select>
                            </c:when>
                                <c:otherwise>
                                <label for="title">Title</label>
                                <input type="text" name="title" id="title">
                                <label for="isbn">ISBN</label>
                                <input type="text" name="isbn" id="isbn">
                                <label for="author">Author</label>
                                <select name="author" id="author">
                                    <c:forEach var="a" items="${authors}">
                                        <option value="${a.authorId}">${a.authorName}</option>
                                    </c:forEach>
                                </select>
                                </c:otherwise>
                        </c:choose>         
                        <input type="submit" name="submitUpdate" id="submitUpdate" value="Submit" class="btn ${btnClass}">
                    </form>
                    <c:if test="${errMsg != null}">
                        <div class="alert alert-danger" role="alert">
                        <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                            ${errMsg}</p>
                        </div>
                        </c:if>    
                </div>
            </div>
        </div>

                        <!-- Insert Modal -->
<div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Add Author</h4>
      </div>
      <div class="modal-body">
          <form method="POST" action="BookController?action=addAuthor" id="insertForm" name="insertForm">
              <label for="addName">Author Name:</label>
              <input type="text" name="addName" id="addName">
              <input type="submit" id="addAuthor" name="addAuthor" value="Save Author" class="btn ${btnClass}">
        </form>
      </div>
      <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div><!--End Modal-->

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.0/jquery.validate.min.js"></script>
        <script src="bookWepApp.js"></script>
      
    </body>
</html>
