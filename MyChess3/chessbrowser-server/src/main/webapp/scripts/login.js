function checkCode(){
	var code=$("#regist_username").val().trim();
	$("#warning_1_span").load(path+"/checkCode.do?code="+code);
}
function loginAction() {
	$("#code_span").html("");
	$("#pwd_span").html("");
	// 获取请求参数
	var code = $("#code").val().trim();
	var pwd = $("#pwd").val().trim();
	// console.log(code);
	var ok = true;
	if (code == "") {
		$("#code_span").html("账号不能为空!");
		ok = false;
	}
	if (pwd == "") {
		$("#pwd_span").html("密码不能为空!");
		ok = false;
	}
	if (ok) {
		$.ajax({
			"url" : path + "/login.do",
			"type" : "post",
			"data" : {
				"code" : code,
				"pwd" : pwd
			},
			"dataType" : "json",
			"success" : function(json) {
				switch (json.state) {
				case 0:
					location.href = path + "/index.do";
					break;
				case 2:
					$("#code_span").html(json.message);
					break;
				case 3:
					$("#pwd_span").html(json.message);
					break;
				default:
					alert(json.message)
					break;
				}
			},
			"error" : function() {
				alert("登录失败");
			}
		});
	}
}
function registAction(){
	var code = $("#regist_username").val().trim();
	var name = $("#nickname").val().trim();
	var pwd = $("#regist_pwd").val().trim();
	var final_pwd = $("#final_pwd").val().trim();
	$("#warning_1_span").html("");
	$("#warning_2_span").html("");
	$("#warning_3_span").html("");
	//校验数据
	//检查用户名:空
	var ok = true;
	if(!code){
		$("#warning_1_span").html("用户名不能为空");
		ok = false;
	}
	//检查密码:空/长度(大于等于6)
	if(!pwd){
		$("#warning_2_span").html("密码不能为空");
		ok = false;
	}
	if(pwd.length<3){
		$("#warning_2_span").html("密码长度不能小于3");
		ok = false;
	}
	//检测确认密码
	if(pwd != final_pwd){
		$("#warning_3_span").html("密码输入不一致");
		ok = false;
	}
	if(ok){
		$.ajax({
			"url":path+"/regist.do",
			"type":"post",
			"data":{"code":code,"name":name,"pwd":pwd},
			"dataType":"json",
			"success":function(json){
				switch (json.state) {
				case 0:
					var message = json.data;
					alert(message);
					$("#back").click();
					$("#code").val(code);
					$("#pwd").focus();
					break;
				case 2:
					$("#warning_1_span").html(json.message);
					break;
				case 3:
					$("#warning_2_span").html(json.message);
					break;
				default:
					alert(json.message);
					break;
				}
			},
			"error":function(){
				alert("注册失败");
			}
		});
	}
}