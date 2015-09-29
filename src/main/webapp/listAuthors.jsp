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
                            <li><a data-toggle="modal" href="#insertModal">Insert Author</a></li>
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
                                <td align="center">
                                    <button type="button" name="updateAuthor" data-toggle="modal" data-target="#updateModal">
                                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                        <span class="sr-only">Edit</span>
                                    </button>
                                    <!-- Modal -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Edit Author</h4>
      </div>
      <div class="modal-body">
          <form id="updateForm" name="updateForm" method=POST" action="AuthorController?action=update">
              <label for="authorId">Author ID</label>
              <select name="authorId" value="authorId" id="authorId">
                  <option>${a.authorId}</option>
              </select><br>
              <label for="authorName">Author Name:</label>
              <input type="text" name="authorName" id="authorName" value="${a.authorName}"><br>
              <label for="date">Date Added:</label>
              <input type="date" name="dateAdded" id="dateAdded" value="${a.dateCreated}"><br>
              <button type="submit" class="btn btn-primary" data-dismiss="modal">Save changes</button>
        </form>
      </div>
      <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div><!--End Modal-->
                                </td>
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
 
         <!-- Modal -->
<div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Add Author</h4>
      </div>
      <div class="modal-body">
          <form method="POST" action="AuthorController?action=insert" id="insertForm" name="insertForm">
              <label for="addName">Author Name:</label>
              <input type="text" name="authorName" id="authorName"><br>
              <label for="addDate">Date Added:</label>
              <input type="date" name="dateAdded" id="dateAdded" value="e.g. 2015-09-02"><br>
              <input type="submit" value="Save Author">
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
    </body>
</html>
