<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirm Initialize</title>
<link rel="stylesheet" type="text/css" href="confirm.css">
</head>
<body>
	<div>
		<p>初期化処理を実行しますか?</p>
	</div>
	<div>
		<form action="InitializeServlet" method="post">
			<button name="btn" value="yes">はい</button>
			<button name="btn" value="no">いいえ</button>
			<input type="hidden" name="nowTurn" value="${nowTurn}">
			<input type="hidden" name="com" value="${com}">
		</form>
	</div>
</body>
</html>