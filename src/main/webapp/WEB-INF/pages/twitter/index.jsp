<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Log-in page</title>
    </head>
    <body>
        <h3>Please click on the link below to allow our application to get your information!</h3>     
        <a href="<c:url value="/twitter/signin" />">Yes, it's here!</a>
    </body>
</html>