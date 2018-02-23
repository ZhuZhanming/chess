<%@page pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">
<title>登录吧!少年</title>
<link rel="stylesheet" href="styles/login.css" />
<script src="scripts/basevalue.js"></script>
<script src="scripts/jquery-1.4.3.js"></script>
<script src="scripts/login.js" charset="utf-8" ></script>
<script>
	$(function() { //加载完bady调用该函数
		$("#login").click(loginAction);
		$("#regist_username").blur(checkCode);
		$("#regist_button").click(registAction);
		$("#pwd").keydown(function(event){
			var code = event.keyCode;
			if(code==13){
				$("#login").trigger("click");
			}
		});
	});
</script>
</head>
<body>
	<div class="global">
		<div class="log log_in" tabindex='-1' id='dl'>
			<dl>
				<dt>
					<div class='header'>
						<h3>登&nbsp;录</h3>
					</div>
				</dt>
				<dt></dt>
				<dt>
					<div class='letter'>
						用户名:&nbsp;<input type="text" name="" id="code" tabindex='1' /> <span
							id="code_span" style="color: red; font-size: 10px"></span>
					</div>
				</dt>
				<dt>
					<div class='letter'>
						密&nbsp;码:&nbsp;<input type="password" name=""
							id="pwd" tabindex='2' /> <span id="pwd_span"
							style="color: red; font-size: 10px"></span>
					</div>
				</dt>
				<dt>
					<div>
						<input type="button" name="" id="login" value='&nbsp登&nbsp录&nbsp'
							tabindex='3' /> <input type="button" name="" id="sig_in"
							value='&nbsp注&nbsp册&nbsp' tabindex='4' />
					</div>
				</dt>
			</dl>
		</div>
		<div class="sig sig_out" tabindex='-1' id='zc'
			style='visibility: hidden;'>
			<dl>
				<dt>
					<div class='header'>
						<h3>注&nbsp;册</h3>
					</div>
				</dt>
				<dt></dt>
				<dt>
					<div class='letter'>
						用户名:&nbsp;<input type="text" name="" id="regist_username" tabindex='5' />
						<div class='warning' id='warning_1'>
							<span id="warning_1_span"></span>
						</div>
					</div>
				</dt>
				<dt>
					<div class='letter'>
						昵&nbsp;称:&nbsp;<input type="text" name="" id="nickname" tabindex='6' />
					</div>
				</dt>
				<dt>
					<div class='letter'>
						密&nbsp;码:&nbsp;<input type="password" name="" id="regist_pwd" tabindex='7' />
						<div class='warning' id='warning_2'>
							<span id="warning_2_span"></span>
						</div>
					</div>
				</dt>
				<dt>
					<div class='letter'>
						确认密码:<input type="password" name="" id="final_pwd" tabindex='8' />
						<div class='warning' id='warning_3'>
							<span id="warning_3_span"></span>
						</div>
					</div>
				</dt>
				<dt>
					<div>
						<input type="button" name="" id="regist_button"
							value='&nbsp注&nbsp册&nbsp' tabindex='9' /> <input type="button"
							name="" id="back" value='&nbsp返&nbsp回&nbsp' tabindex='10' />
						<script type="text/javascript">
							function get(e) {
								return document.getElementById(e);
							}
							get('sig_in').onclick = function() {
								get('dl').className = 'log log_out';
								get('zc').className = 'sig sig_in';
							}
							get('back').onclick = function() {
								get('zc').className = 'sig sig_out';
								get('dl').className = 'log log_in';
							}
							window.onload = function() {
								var t = setTimeout(
										"get('zc').style.visibility='visible'",
										800);
								get('final_pwd').onblur = function() {
									var npwd = get('regist_pwd').value;
									var fpwd = get('final_pwd').value;
									if (npwd != fpwd) {
										get('warning_3').style.display = 'block';
									}
								}
								get('regist_pwd').onblur = function() {
									var npwd = get('regist_pwd').value.length;
									if (npwd<6&&npwd>0) {
										get('warning_2').style.display = 'block';
									}
								}
								get('regist_pwd').onfocus = function() {
									get('warning_2').style.display = 'none';
								}
								get('final_pwd').onfocus = function() {
									get('warning_3').style.display = 'none';
								}
							}
						</script>
					</div>
				</dt>
			</dl>
		</div>
	</div>
</body>
</html>