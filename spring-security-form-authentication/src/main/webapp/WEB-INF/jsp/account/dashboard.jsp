<%@ include file="../common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Dashboard</title>
</head>
<body>
	<sec:authentication property="principal.username" var="username" />

	Hello,
	<c:out value="${username}" />

	<spring:url value="/api/account/@{name}" var="accountUrl">
		<spring:param name="name" value="${username}" />
	</spring:url>
	<p><a href="${fn:escapeXml(accountUrl)}">RestApi Link</a></p>
	
	<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post">
		<sec:csrfInput />
		<button>Log Out</button>
	</form>
</body>
</html>