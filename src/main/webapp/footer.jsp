<%-- 
    Document   : footer
    Created on : Nov 4, 2015, 8:00:06 PM
    Author     : emmakordik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<footer class="nav navbar">
    <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER', 'ROLE_ADMIN')">
                Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
                <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
            </sec:authorize>  
</footer>