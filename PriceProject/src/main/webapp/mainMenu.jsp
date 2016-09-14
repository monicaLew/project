<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.pctrade.price.servlet.ScanCarPage"%>
<%@page import="com.pctrade.price.validation.FormValidator"%>
<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="validation" uri="http://belhard.com/validation"%>
<html>
<head>
<title>File Uploading Form</title>
</head>
<body>
	<h3>File Upload:</h3>
	Select a file to upload:
	<br />
	<br />
	<form action="UploadServlet" method="post"
		enctype="multipart/form-data">
		<input type="file" name="file" size="50" /> <br /> <br /> <input
			type="submit" value="Upload File" />
	</form>
	<br />
	<br />
	<form action="ScanCarPage" method="post">
		<p>
			<b>ID from:</b><br> <input type="text" name="idFrom" size="40" />

			<validation:fieldError errorCode="idFrom.negative.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idFrom.less.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idFrom.text.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>
		</p>
		<br />
		<p>
			<b>ID till:</b><br> <input type="text" name="idTill" size="40" />
			<validation:fieldError errorCode="idTill.negative.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idTill.bigger.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idTill.text.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>
		</p>
		<br /> <input type="submit" value="ScanningPages" />
	</form>
	
	<form action="Myseeeervlet?param1=value1" method="POST"> 
   <input type="text" name="param1" value="value2" /> 
   <input type="submit" value="value3" /> 
</form> 
</body>
</html>