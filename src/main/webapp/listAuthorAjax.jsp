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
    <body class="authorDisplay">
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
                        <tbody id="authorList">
                            
                        </tbody>
                    </table>
                    
                    <div id="errorAlert" class="alert alert-danger" role="alert" style="display:none;">
                    <p style="font-weight: bold;color: red;width:500px;" >Sorry, data could not be retrieved:<br>
                        <span id="err"></span></p>
                    </div>
                </div>
            </div>
   
        </div>
                        
        <jsp:include page="footer.jsp"/> 
 
         

        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 
        <script src="resources/js/app.js"></script>
       
    </body>
</html>
