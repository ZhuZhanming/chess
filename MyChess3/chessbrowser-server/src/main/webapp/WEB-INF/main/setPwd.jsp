<%@page pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8" />
        <title>修改密码</title>
        <link type="text/css" rel="stylesheet" media="all" href="styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css" />  
        <script src="scripts/basevalue.js"></script>
		<script src="scripts/jquery-1.4.3.js"></script>
        <script type="text/javascript">
        	$(function(){
        		$("#save").click(function(){
        			var oldPwd=$(":password:first").val().trim();
        			var newPwd1=$(":password:eq(2)").val().trim();
        			var newPwd2=$(":password:last").val().trim();
        			console.log(oldPwd+newPwd1+newPwd2);
        			var ok=true;
        			if(!oldPwd || !newPwd1 || !newPwd2){
        				alert("请勿输入非空值");
        				ok=false;
        			}
        			if(newPwd1!=newPwd2){
        				alert("密码不一致");
        			}
        			if(ok){
        				$.ajax({
        					url:path+"/setPwd.do",
        					type:"post",
        					data:{"oldPwd":oldPwd,"newPwd":newPwd1},
        					dataType:"json",
        					success:function(result){
        						switch (result.state) {
								case 0:
									alert("修改成功,请重新登录");
									location.href=path+"/log_in.html";
									break;
								case 2:
									alert("session过期，请重新登录");
									location.href=path+"/log_in.html";
									break;
								case 3:
									alert(result.message)
									break;
								default:
									alert("修改失败");
									break;
								}
        					},
        					error:function(){
        						alert("修改失败");
        					}
        				});
        			}
        		});
        	});
        </script>      
    </head>
    <body>
        <!--Logo区域开始-->
        <div id="header">
            <%@include file="../logo.jsp" %>
        </div>
        <!--Logo区域结束-->
        <!--导航区域开始-->
        <div id="navi">                
            <c:import url="../menu.jsp"></c:import>          
        </div>
        <!--导航区域结束-->
        <div id="main">      
            <!--保存操作后的提示信息：成功或者失败-->      
            <div id="save_result_info" class="save_success">保存成功！</div><!--保存失败，旧密码错误！-->
                <div class="text_info clearfix"><span>旧密码：</span></div>
                <div class="input_info">
                    <input type="password" class="width200"  /><span class="required">*</span>
                    <div class="validate_msg_medium">30长度以内的字母、数字和下划线的组合</div>
                </div>
                <div class="text_info clearfix"><span>新密码：</span></div>
                <div class="input_info">
                    <input type="password"  class="width200" /><span class="required">*</span>
                    <div class="validate_msg_medium">30长度以内的字母、数字和下划线的组合</div>
                </div>
                <div class="text_info clearfix"><span>重复新密码：</span></div>
                <div class="input_info">
                    <input type="password" class="width200"  /><span class="required">*</span>
                    <div class="validate_msg_medium">两次新密码必须相同</div>
                </div>
                <div class="button_info clearfix">
                    <input id="save" type="button" value="保存" class="btn_save" />
                    <input id="cancel" type="button" value="取消" class="btn_save" onclick="history.back();" />
                </div>
        </div>
        <!--主要区域结束-->
        <div id="footer">
            <p>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</p>
            <p>版权所有(C)加拿大达内IT培训集团公司 </p>
        </div>
    </body>
</html>
