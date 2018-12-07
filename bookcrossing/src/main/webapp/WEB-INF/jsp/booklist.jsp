<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.UUID"%>

<!DOCTYPE html>
<html>
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
	});

	function del() {
		var msg = "您真的确定要删除吗？\n\n请确认！";
		if (confirm(msg) == true) {
			return true;
		} else {
			return false;
		}
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
					<li class="active"><a href="#">书籍列表</a></li>
					<li><a href="newbook">新增图书</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
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

</body>
</html>