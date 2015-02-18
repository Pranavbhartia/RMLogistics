<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>customer general info


<springForm:form method="POST" commandName="user"
        action="save.do">
        <table>
         <tr>
                <td>ID:</td>
                <td><springForm:input path="idUser" /></td>
                <td><springForm:errors path="idUser" cssClass="error" /></td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td><springForm:input path="fname" /></td>
                <td><springForm:errors path="fname" cssClass="error" /></td>
            </tr>
            <tr>
                <td>last Name:</td>
                <td><springForm:input path="lname" /></td>
                <td><springForm:errors path="lname" cssClass="error" /></td>
            </tr>
            <tr>
                <td>StreetAddress:</td>
                <td><springForm:input path="streetAddress" /></td>
                <td><springForm:errors path="streetAddress" cssClass="error" /></td>
            </tr>
            <tr>
                <td>City:</td>
                <td><springForm:input path="city" /></td>
                <td><springForm:errors path="city" cssClass="error" /></td>
            </tr>
             <tr>
                <td>State:</td>
                <td><springForm:input path="state" /></td>
                <td><springForm:errors path="state" cssClass="error" /></td>
            </tr>
             <tr>
                <td>Zipcode:</td>
                <td><springForm:input path="zipcode" /></td>
                <td><springForm:errors path="zipcode" cssClass="error" /></td>
            </tr>
            <tr>
                <td colspan="3"><input type="submit" value="Save Customer"></td>
            </tr>
        </table>
 
    </springForm:form>


</body>
</html>