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
                        <form method="POST" action="AuthorController?action=home">
                            <button type="submit" class="btn ${btnClass} btnSpacing">Home</button>
                        </form>
                      <!--  <button type="button" class="btn ${btnClass} btnSpacing" data-toggle="modal" href="#updateModal2">Update Author</button>-->
                        <button type="button" class="btn ${btnClass} btnSpacing" data-toggle="modal" href="#insertModal">Add Author</button>
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
                                <td align="center">
                                    <form name="deleteForm" method="POST" action="AuthorController?action=delete">
                                        <button href="AuthorController?action=delete" name="deleteAuthor" value="${a.authorId}">
                                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                            <span class="sr-only">Delete</span>
                                        </button>
                                    </form>
                                </td>
                                <td align="center">
                                    <form name="updateForm" method="POST" action="AuthorController?action=findUpdate">                                            
                                        <button id="updateAuthor" name="updateAuthor" value="${a.authorId}">
                                            <c:set var="updateId" value="${a.authorId}"/>
                                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                            <span class="sr-only">Edit</span>
                                        </button>
                                    </form>
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
 
         <!-- Insert Modal -->
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
              <input type="text" name="addName" id="addName"><br>
              <label for="addDate">Date Added:</label>
              <input type="date" name="addDate" id="addDate" placeholder="e.g. 2015-09-02"><br>
              <input type="submit" id="addAuthor" name="addAuthor" value="Save Author" class="btn ${btnClass}">
        </form>
      </div>
      <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div><!--End Modal-->
  <!-- Update Link Modal -->
<!--<div class="modal fade" id="updateModal2" tabindex="-1" role="dialog" aria-labelledby="Update">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Update Author</h4>
      </div>
      <div class="modal-body">
          <form method="POST" action="AuthorController?action=update" id="updateForm2" name="updateForm2">
              <select name="updateId">
                  <c:forEach var="a" items="${authors}">
                      <option value="${a.authorId}">${a.authorId}</option>
                  </c:forEach>
              </select>
              <label for="updateName">Author Name:</label>
              <input type="text" name="updateName" id="updateName"><br>
              <label for="updateDate">Date Added:</label>
              <input type="date" name="updateDate" id="updateDate"><p>e.g.YYYY-MM-DD</p><br>
              <input type="submit" value="Update Author" id="updateAuthor" name="updateAuthor">
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
