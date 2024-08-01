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
	<div id="tbl">
		<table border="1">
			<c:forEach var="el" items="${board}">
				<tr>
					<c:forEach var="item" items="${el}">
						<td align="center">
						<c:choose>
							<c:when test="${item == '1'}">〇</c:when>
							<c:when test="${item == '2'}">●</c:when>
							<c:otherwise>　</c:otherwise>
						</c:choose>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
			<tr>
				<c:forEach begin="1" end="7" varStatus="status">
				<td>
					<form action="SetPieceServlet" method="get">
						<button name="btn" value="${status.index}">設置</button>
						<input type="hidden" name="nowTurn" value="${nowTurn}">
						<input type="hidden" name="isOver" value="${isOver}">
					</form>
				</td>
				</c:forEach>
			</tr>
		</table>
	</div>
	<div id="msg">
		<c:if test="${isOver == null}">
			<c:if test="${nowTurn != null}">${nowTurn}のターンです</c:if>
			<c:if test="${message != null}">${message}</c:if>
		</c:if>
		<c:if test="${isOver != null}">
			<c:if test="${isOver == 'end'}">${nowTurn}の勝利です!</c:if>
			<c:if test="${isOver == 'tie'}">引き分けです</c:if>
		</c:if>
	</div>
	<div id="init">
		<form action="InitializeServlet" method="get">
			<button name="btn" value="0">初期化</button>
			<input type="hidden" name="nowTurn" value="${nowTurn}">
		</form>
	</div>
</body>
</html>