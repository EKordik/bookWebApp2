<%-- 
    Document   : listAuthors
    Created on : Sep 23, 2015, 8:44:05 PM
    Author     : ekordik
    Purpose    :
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
                        <ul>
                            <li>View Author</li>
                        </ul>
                    </div>
                </nav>
                <div class="col-xs-9">
                    <h3>Author ID</h3>
                    <form method="POST" action="AuthorController?action=update" id="updateForm" name="updateForm">
                        <label for="updateName">Author Name:</label>
                        <input type="text" name="updateName" id="updateName"><br>
                        <label for="updateDate">Date Added:</label>
                        <input type="date" name="updateDate" id="updateDate"><p>e.g.YYYY-MM-DD</p><br>
                        <input type="submit" value="Update Author">
                    </form>

                </div>
            </div>
        </div>

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
      
    </body>
</html>
