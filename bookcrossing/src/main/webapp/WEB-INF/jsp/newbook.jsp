<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.UUID"%>
<!DOCTYPE html>
<html>
<head>
<head>
<meta charset="UTF-8">
<title>书享校园</title>

<%-- 引入JQ和Bootstrap --%>
<script src="js/jquery/2.0.0/jquery.min.js"></script>
<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
<link href="css/style.css" rel="stylesheet">

<script>
	$(function() {
		$("ul.pagination li.disabled a").click(function() {
			return false;
		});

		$("#addForm").submit(function() {
			if (!checkEmpty("studentid", "学号"))
				return false;
			if (!checkEmpty("bookname", "书名"))
				return false;
			if (!checkEmpty("author", "作者"))
				return false;
			if (!checkEmpty("press", "出版社"))
				return false;
			if (!checkEmpty("email", "邮箱"))
				return false;
			if (!checkEmpty("description", "描述"))
				return false;
			return true;
		});
	});

	function checkEmpty(id, name) {
		var value = $("#" + id).val();
		if (value.length == 0) {
			alert(name + "不能为空");
			$("#" + id).focus();
			return false;
		}
		return true;
	}
</script>
</head>

<body>
	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">书享校园</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<li><a href="booklist">书籍列表</a></li>
					<li class="active"><a href="#">新增图书</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<%
		/*生成图书编号*/
		String bookId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	%>
	
	<div class="addDIV">

		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">新增图书</h3>
			</div>
			<div class="panel-body">
				<form role="form" method="post" id="addForm" action="addBook" enctype="multipart/form-data">
					<div class="form-group">
						<label class="control-label">书号:</label> <input
							class="form-control" type="text" name="bookid" id="bookid"
								readonly="true" value="<%=bookId%>" />
					</div>
					<div class="form-group">
						<label class="control-label">学号:</label> <input
							class="form-control" type="text" name="studentid" id="studentid"
								placeholder="请在这里输入学号" />
					</div>
					<div class="form-group">
						<label class="control-label">书名:</label> <input
							class="form-control" type="text" name="bookname" id="bookname"
								placeholder="请在这里输入书名" />
					</div>
					<div class="form-group">
						<label class="control-label">作者:</label> <input
							class="form-control" type="text" name="author" id="author"
								placeholder="请在这里输入作者" />
					</div>
					<div class="form-group">
						<label class="control-label">出版社:</label> <input
							class="form-control" type="text" name="press" id="press"
								placeholder="请在这里输入出版社" />
					</div>
					<div class="form-group">
						<label class="control-label">电子邮箱:</label> <input
							class="form-control" type="text" name="email" id="email"
								placeholder="请在这里输入电子邮箱" />
					</div>
					<div class="form-group">
						<label for="name">书籍类别:</label> <select class="form-control" name="category">
							<option value="教育">教育</option>
							<option value="小说">小说</option>
							<option value="文艺">文艺</option>
							<option value="青春文学">青春文学</option>
							<option value="童书">童书</option>
							<option value="人文社科">人文社科</option>
							<option value="经管">经管</option>
							<option value="生活">生活</option>
							<option value="科技">科技</option>
						</select>
					</div>
					<div class="form-group">
						<label for="inputfile">上传图片:</label> <input type="file" name="picture">
					</div>
					<div class="form-group">
						<label for="name">书籍描述：</label>
						<textarea class="form-control" rows="3"
									name="description" id="description" placeholder="请在这里输入描述"></textarea>
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