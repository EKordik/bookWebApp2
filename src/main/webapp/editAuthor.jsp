<%-- 
    Document   : listAuthors
    Created on : Sep 23, 2015, 8:44:05 PM
    Author     : ekordik
    Purpose    : Allows an author to be edited
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
        <title>Edit Author</title>

    </head>
    <body>
        <header id="header">
            <h1>Edit Author</h1>
        </header>
        <div class="container">
            <div class="row">
                <nav class="col-xs-3">
                    <div class="fixed">
                       <form method="POST" action="AuthorController?action=home">
                            <button type="submit" class="btn ${btnClass} btnSpacing">Home</button>
                        </form>
                        <form method="POST" action="AuthorController?action=list" class="indexForm">
                            <button type="submit" class="btn ${btnClass} btnSpacing">View All Authors</button>
                        </form>
                    </div>
                </nav>
                <div class="col-xs-9">
                    <h3>Edit Author with ID ${author.authorId}</h3>
                    <form method="POST" action="AuthorController?action=update" id="updateForm" name="updateForm">
                        <input type="hidden" name="updateId" value="${author.authorId}">
                        <label for="updateName">Author Name:</label>
                        <input type="text" name="updateName" id="updateName" value="${author.authorName}"><br>
                        <label for="updateDate">Date Added:</label>
                        <input type="date" name="updateDate" id="updateDate" value="${author.dateCreated}"><br>
                        <input type="submit" name="submitUpdate" id="submitUpdate" value="Update Author" class="btn ${btnClass}">
                    </form>

                </div>
            </div>
        </div>

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.0/jquery.validate.min.js"></script>
        <script src="bookWepApp.js"></script>
      
    </body>
</html>
