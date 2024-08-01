<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connect Four</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<div id="header">
		<div id="title">
			<h1>四目並べ</h1>
		</div>
		<div id="init">
		<form action="InitializeServlet" method="get">
			<button name="btn" value="0">初期化</button>
			<input type="hidden" name="nowTurn" value="${nowTurn}">
		</form>
		</div>
	</div>
	<div id="space"></div>
	<div id="tbl">
		<c:forEach var="el" items="${board}">
			<c:forEach var="item" items="${el}">
				<div class="col" id="piece">
					<c:choose>
						<c:when test="${item == '1'}"><div id="white">●</div></c:when>
						<c:when test="${item == '2'}"><div id="black">●</div></c:when>
						<c:otherwise><p id="blank">X</p></c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
		</c:forEach>
		<c:forEach begin="1" end="7" varStatus="status">
			<div class="col" id="col_btn">
				<form action="SetPieceServlet" method="get">
					<button name="btn" value="${status.index}">設置</button>
					<input type="hidden" name="nowTurn" value="${nowTurn}">
					<input type="hidden" name="isOver" value="${isOver}">
				</form>
			</div>
		</c:forEach>
	</div>
	<div id="msg">
		<div id="msg_bar">
			<c:if test="${isOver == null}">
				<c:if test="${nowTurn != null}">
					<c:if test="${nowTurn == '〇'}"><div id="white">●</div></c:if>
					<c:if test="${nowTurn == '●'}"><div id="black">●</div></c:if>
					のターンです
				</c:if>
				<c:if test="${message != null}">${message}</c:if>
			</c:if>
			<c:if test="${isOver != null}">
				<c:if test="${isOver == 'end'}">
					<c:if test="${nowTurn == '〇'}"><div id="white">●</div></c:if>
					<c:if test="${nowTurn == '●'}"><div id="black">●</div></c:if>
					の勝利です
				</c:if>
				<c:if test="${isOver == 'tie'}">引き分けです</c:if>
			</c:if>
		</div>
	</div>
</body>
</html>