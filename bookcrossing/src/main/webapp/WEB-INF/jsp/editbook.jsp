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

				<form role="form" method="post" action="updateBook" enctype="multipart/form-data">
					<div class="form-group">
						<label class="control-label">书号:</label> <input
							class="form-control" type="text" name="bookid" id="bookid"
								readonly="true" value="${book.bookId}" />
					</div>
					<div class="form-group">
						<label class="control-label">书名:</label> <input
							class="form-control" type="text" name="bookName" id="bookName" value="${book.bookName}"
								placeholder="请在这里输入书名" />
					</div>
					<div class="form-group">
						<label class="control-label">作者:</label> <input
							class="form-control" type="text" name="author" id="author" value="${book.author}"
								placeholder="请在这里输入作者" />
					</div>
					<div class="form-group">
						<label class="control-label">出版社:</label> <input
							class="form-control" type="text" name="press" id="press" value="${book.press}"
								placeholder="请在这里输入出版社" />
					</div>
					<div class="form-group">
						<label for="inputfile">上传图片:</label>
						<img alt="暂无图片" src="${book.picture}"> 
						<input type="file" name="picture">
					</div>
					<div class="form-group">
						<label for="name">书籍描述：</label>
						<textarea class="form-control" rows="3"
									name="description" id="description" placeholder="请在这里输入描述">${book.bookDescription}</textarea>
					</div>
					<div class="form-group">
						<center><button type="submit" class="btn btn-default">提交</button></center>
					</div>
				</form>
			</div>
		</div>

	</div>

</body>
</html>