<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
	<%@ page import="com.hust.pojo.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<%-- 引入JQ和Bootstrap --%>
<script src="js/jquery/2.0.0/jquery.min.js"></script>
<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
<link href="css/style.css" rel="stylesheet">

<title>图书管理页面 - 编辑页面</title>

</head>

<body>

	<div class="editDIV">

		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">编辑图书</h3>
			</div>
			<div class="panel-body">

				<form method="post" action="updateBook" role="form" enctype="multipart/form-data">
					<table class="editTable">
						<tr>
							<td>书号：</td>
							<td><input type="text" name="bookid" id="bookid"
								value="${book.bookId}" readonly="true"></td>
						</tr>
						<tr>
							<td>书名：</td>
							<td><input type="text" name="bookName" id="bookName"
								value="${book.bookName}" placeholder="请在这里输入名字"></td>
						</tr>
						<tr>
							<td>作者：</td>
							<td><input type="text" name="author" id="author"
								value="${book.author}" placeholder="请在这里输入作者"></td>
						</tr>
						<tr>
							<td>出版社：</td>
							<td><input type="text" name="press" id="press"
								value="${book.press}" placeholder="请在这里输入出版社"></td>
						</tr>
						<tr>
							<td>上传图片:</td>
							<td><img alt="暂无图片" src="${book.picture}"></td>
							<td><input type="file" name="picture"></td>
						</tr>
						<tr>
							<td>书籍描述：</td>
							<td><textarea style="width: 300px; height: 100px;"
									name="description" id="description">${book.bookDescription}</textarea></td>
						</tr>
						<tr class="submitTR">
							<td colspan="2" align="center"><input type="hidden"
								name="id" value="${book.bookId}">
								<button type="submit" class="btn btn-success">提 交</button></td>

						</tr>

					</table>
				</form>
			</div>
		</div>

	</div>

</body>
</html>