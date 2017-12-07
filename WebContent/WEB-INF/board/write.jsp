<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>�� �� ����</title>
<link href="<%=request.getContextPath() %>/css/board.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.7.1.js"></script>
<script type="text/javascript">
	function writeFormCheck(){
		if($("#subject").val() == null || $("#subject").val() == ""){
			alert("������ �Է��� �ּ���!");
			$("#subject").focus();
			return false;
		}
		if($("#content").val() == null || $("#content").val() == ""){
			alert("������ �Է��� �ּ���!");
			$("#content").focus();
			return false;
		}
	}
</script>
</head>
<body>

<div class="wrapper">
	<h3>�� �� ����</h3>
	<form action="write.do" method="post" onsubmit="return writeFormCheck()" enctype="multipart/form-data">
		<table class="boardWrite">
			<tr>
				<th><label for="subject">����</label></th>
				<td>
					<input type="text" id="subject" name="subject" class="boardSubject" />
					<input type="hidden" id="writer" name="writer" value="${userName }" />
					<input type="hidden" id="writerId" name="writerId" value="${userId }"/>
				</td>
			</tr>
			<tr>
				<th><label for="content">����</label></th>
				<td><textarea id="content" name="content" class="boardContent"></textarea></td>
			</tr>
			<tr>
				<th><label for="file">����</label></th>
				<td>
					<input type="file" id="file" name="file" />
					<span class="date">&nbsp;&nbsp;*&nbsp;���Ƿ� ���ϸ��� ����� �� �ֽ��ϴ�</span>
				</td>
			</tr>
		</table>
		<br />
		<input type="reset" value="���ۼ�" class="writeBt" />
		<input type="submit" value="Ȯ��" class="writeBt" />
	</form>
</div>

</body>
</html>