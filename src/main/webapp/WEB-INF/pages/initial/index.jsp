<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
    <head>
        <title>What API do you want to test?</title>
    </head>
    <body>
        <h3>If you want to test the Twitter API, click on the link below!</h3>     
        <a href="<c:url value="/twitter/" />">Twitter!</a>
        <hr />
        <h3>If you want to test the Facebook API, click on the link below!</h3>     
        <a href="<c:url value="/facebook/" />">Facebook!</a>
        <hr />        
    </body>
</html>