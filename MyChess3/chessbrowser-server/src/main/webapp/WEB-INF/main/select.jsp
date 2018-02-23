<%@page pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">
<title>你的战绩</title>
	<script src="scripts/jquery-1.4.3.js"></script>
	<script src="scripts/basevalue.js"></script>
	<script type="text/javascript">
	//主处理函数
	$(function(){
		$("#search_btn").click(function(){
				var status=$("#status").val();
				var color=$("#color").val();
				var begin=$("#begin").val().trim();
				var end=$("#end").val().trim();
				var ok=true;
				var reg=/^\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2]\d|3[0-1])$/;
				//非空，不满足
				if(begin && !reg.test(begin)){
					alert("开始时间输入错误");
					ok=false;
				}
				if(end && !reg.test(end)){
					alert("结束时间输入错误");
					ok=false;
				}
		if (ok) {
				$("#main").html("");
				$.ajax({
					"url" : path + "/find.do",
					"type" : "get",
					"data" : {
						"color":color,
						"status" : status,
						"begin" : begin,
						"end" : end
					},
					"dataType" : "json",
					"success" : function(result) {
						switch (result.state) {
						case 0:
							var data=result.data;
							console.log(data.length);
							for(i=0;i<data.length;i++){
								var $tr=$("<tr>"
								+"<td>"+data[i].ID+"</td>"
								+"<td>"+data[i].RNAME+"</td>"
								+"<td>"+data[i].BNAME+"</td>"
								+"<td>"+data[i].STARTTIME+"</td>"
								+"<td>"+data[i].TIME+"</td>"
								+"<td>"+data[i].WINNAME+"</td>"
								+"</tr>");
								console.log(data[i].ID);
								$("#main").append($tr);
							}
							break;
						case 4:
							alert(result.message);
						default:
							break;
						}
					},
					"error" : function() {
						alert("傻逼,请检查你的网络,一定是你的问题!!!!")
					}
				});
				}
			});
		});
	</script>
</head>
<body>
	 状态:<select id="status">
		<option value="0">全部</option>
		<option value="1">胜利</option>
		<option value="2">失败</option>
	</select>
	颜色：<select id="color">
		<option value="0">全部</option>
		<option value="2">红色</option>
		<option value="1">黑色</option>
		</select>
	<br /> 
	开始时间:<input type="text" id="begin" placeholder="时间格式：年-月-日" /> 
	结束时间:<input type="text" id="end" placeholder="时间格式：年-月-日" /><br>
	<input type="button" id="search_btn" value="搜索按钮">
	<hr />
	<table border="1" style="width: 500;">
		<thead>
			<tr>
				<td>ID</td>
				<td>红方</td>
				<td>黑方</td>
				<td>开始时间</td>
				<td>比赛时间</td>
				<td>胜利方</td>
			</tr>
		</thead>
		<tbody id="main">
		</tbody>
	</table>
</body>
</html>