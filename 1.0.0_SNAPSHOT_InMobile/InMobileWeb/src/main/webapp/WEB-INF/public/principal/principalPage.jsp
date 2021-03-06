<%@ include file="/WEB-INF/common/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title><spring:message code="public.titulo.login" /></title>

<meta http-equiv="content-type" content="text/html; charset=UTF-8"></meta>
<meta charset="utf-8"></meta>
<meta name="generator" content="Bootply"></meta>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1"></meta>
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
	rel="stylesheet"></link>
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/css/styles.css"
	rel="stylesheet"></link>

</head>
<body>

	<div>
		<tiles:insertAttribute name="cabecera" />
	</div>
	<div>
		<tiles:insertAttribute name="contenidoIzq" />
		<tiles:insertAttribute name="contenidoCentro" />
		<tiles:insertAttribute name="contenidoDcho" />
	</div>
	<div>
		<tiles:insertAttribute name="pie" />
	</div>
	<!-- script references -->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>