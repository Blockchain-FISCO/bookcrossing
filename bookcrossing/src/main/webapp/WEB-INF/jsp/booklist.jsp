<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.UUID"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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

	function del() {
		var msg = "您真的确定要删除吗？\n\n请确认！";
		if (confirm(msg) == true) {
			return true;
		} else {
			return false;
		}
	}

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
	<div class="listDIV">
		<table
			class="table table-striped table-bordered table-hover table-condensed">

			<caption>书籍列表 - 共${page.total}本</caption>
			<thead>
				<tr class="success">
					<th>书号</th>
					<th>书名</th>
					<th>作者</th>
					<!-- <th>简介</th> -->
					<th>出版社</th>
					<th>编辑</th>
					<th>删除</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${books}" var="b" varStatus="status">
					<tr>
						<td>${b.bookId}</td>
						<td>${b.bookName}</td>
						<td>${b.author}</td>
						<!-- <td>${b.bookDescription}</td> -->
						<td>${b.press}</td>
						<td><a href="editBook?id=${b.bookId}"><span
								class="glyphicon glyphicon-edit"></span> </a></td>
						<td><a href="deleteBook?id=${b.bookId}"
							onclick="javascript:return del();"><span
								class="glyphicon glyphicon-trash"></span> </a></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>

	<%
		/*生成图书编号*/
		String bookId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	%>

	<nav class="pageDIV">
		<ul class="pagination">
			<li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
				<a href="?page.start=0"> <span>«</span>
			</a>
			</li>

			<li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
				<a href="?page.start=${page.start-page.count}"> <span>‹</span>
			</a>
			</li>

			<c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">

				<c:if
					test="${status.count*page.count-page.start<=30 && status.count*page.count-page.start>=-10}">
					<li
						<c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
						<a href="?page.start=${status.index*page.count}"
						<c:if test="${status.index*page.count==page.start}">class="current"</c:if>>${status.count}</a>
					</li>
				</c:if>
			</c:forEach>

			<li <c:if test="${!page.hasNext}">class="disabled"</c:if>><a
				href="?page.start=${page.start+page.count}"> <span>›</span>
			</a></li>
			<li <c:if test="${!page.hasNext}">class="disabled"</c:if>><a
				href="?page.start=${page.last}"> <span>»</span>
			</a></li>
		</ul>
	</nav>

	<div class="addDIV">

		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">新增图书</h3>
			</div>
			<div class="panel-body">

				<form method="post" id="addForm" action="addBook" role="form"
					enctype="multipart/form-data">
					<table class="addTable">
						<tr>
							<td>书号：</td>
							<td><input type="text" name="bookid" id="bookid"
								readonly="true" value="<%=bookId%>"></td>
						</tr>
						<tr>
							<td>学号：</td>
							<td><input type="text" name="studentid" id="studentid"
								placeholder="请在这里输入学号"></td>
						</tr>
						<tr>
							<td>书名：</td>
							<td><input type="text" name="bookname" id="bookname"
								placeholder="请在这里输入书名"></td>
						</tr>
						<tr>
							<td>作者：</td>
							<td><input type="text" name="author" id="author"
								placeholder="请在这里输入作者"></td>
						</tr>
						<tr>
							<td>出版社：</td>
							<td><input type="text" name="press" id="press"
								placeholder="请在这里输入出版社"></td>
						</tr>
						<tr>
							<td>电子邮箱：</td>
							<td><input type="text" name="email" id="email"
								placeholder="请在这里输入电子邮箱"></td>
						</tr>
						<!--  <tr>
							<td>学院：</td>
							<td><select id="schoid" name="schoid">
									<c:forEach items="${schools}" var="s" varStatus="status">
										<option value="${s.schoId}">${s.schoName}</option>
									</c:forEach>
							</select></td>
						</tr> -->
						<tr>
							<td>书籍类别:</td>
							<td>
								<select name="category">
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
							</td>
						</tr>
						<tr>
							<td>上传图片:</td>
							<td><input type="file" name="picture"></td>
						</tr>
						<tr>
							<td>书籍描述：</td>
							<td><textarea style="width: 300px; height: 100px;"
									name="description" id="description" placeholder="请在这里输入描述"></textarea></td>
						</tr>
						<tr class="submitTR">
							<td colspan="2" align="center">
								<button type="submit" class="btn btn-success">提 交</button>
							</td>

						</tr>
					</table> 
				</form>
			</div>
		</div>

	</div>

</body>
</html>