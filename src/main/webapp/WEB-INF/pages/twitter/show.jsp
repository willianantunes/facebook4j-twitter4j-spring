<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Twitter test page</title>
    </head>
    <body>
        <h2>Welcome ${sessionObjects.twitter.screenName}!</h2>
        <ul>
        	<li>ID: ${sessionObjects.twitter.id}</li>
        </ul>       
            
		<form action="<c:url value="/twitter/post" />" method="post">
			<h3>Post a tweet!</h3>
			<textarea cols="80" rows="2" name="message"></textarea>
			<br />
			<input type="submit" value="Statuses" />
		</form>              
        
        <hr />
        
		<c:if test="${not empty messageSuccess}">
		
		<h3>Mensagem: <span style="color: red;"> ${messageSuccess}</span></h3>
		
		</c:if>         
        
        <hr />
            
        <a href="<c:url value="/twitter/logout" />">Click here to make logout!</a>
    </body>
</html>