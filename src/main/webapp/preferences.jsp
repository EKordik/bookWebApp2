<%-- 
    Document   : preferences
    Created on : Oct 5, 2015, 7:34:15 PM
    Author     : emmakordik
    Purpose     : Sets the color theme for the user for the session
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="bookWebApp.css">
        <title>Book Web App Preferences</title>
    </head>
    <body>
        <header id="header">
            <h1>Preferences</h1>
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
                    <h3>Set Color</h3>
                    <form method="POST" action="AuthorController?action=setPref" id="prefForm" name="prefForm">
                        <input type="radio" name="themeColor" id="blue" value="btn-primary" checked>
                        <label for="blue">Blue</label><br>
                        <input type="radio" name="themeColor" id="lightBlue" value="btn-info">
                        <label for="lightBlue">Light Blue</label><br>
                        <input type="radio" name="themeColor" id="red" value="btn-warning">
                        <label for="red">Orange</label><br>
                        <input type="radio" name="themeColor" id="green" value="btn-success">
                        <label for="green">Green</label><br>
                        <input type="submit" name="colorBtn" id="colorBtn" value="Set Color" class="btn ${btnClass}">
                    </form>
                    <br>
                    <br>
                    
                    <form method="POST" action="AuthorController?action=home" name="exitForm">
                        <input type="submit" class="btn ${btnClass}" value="Exit">
                    </form>
                    
                   

                </div>
            </div>
        </div>

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
    </body>
</html>
