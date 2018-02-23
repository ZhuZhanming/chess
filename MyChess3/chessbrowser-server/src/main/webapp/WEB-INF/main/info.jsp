<%@page pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>我的象棋</title>
        <link type="text/css" rel="stylesheet" media="all" href="styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css" />
        <script src="scripts/jquery-1.4.3.js"></script>
        <script src="scripts/basevalue.js"></script>
        <script type="text/javascript">
            //保存成功的提示信息
            $(function(){
            	$("#save").click(function(){
            		$("#name_span").html("");
            		$("#telephone_span").html("");
            		$("#email_span").html("");
            		var name= $("#name").val().trim();
            		var telephone = $("#telephone").val().trim();
            		var email = $("#email").val().trim();
            		var code = $("#code").val().trim();
            		var ok = true;
            		if(!name){
            			$("#name_span").html("昵称不能为空！");
            			ok= false;
            		}
            		if(!telephone.match(/^1\d{10}$/)){
            			$("#telephone_span").html("请输入正确的11位手机号");
            			ok=false;
            		}
            		if(!email.match(/^\w+\@.+(\..+)+$/)){
            			$("#email_span").html("邮箱格式不对");
            			ok=false;
            		}
            		if(ok){
            			$.ajax({
            				url:path+"/modify.do",
            				type:"post",
            				data:{"name":name,"telephone":telephone,"email":email,"code":code},
            				dataType:"json",
            				success:function(result){
								switch (result.state) {
								case 0:
									showResultDiv(true);
									window.setTimeout("showResultDiv(false);", 3000);
									break;
								default:
									alert(result.message);
									break;
								}
            					var data=result.data;
            				},
            				error:function(){
            					alert("保存失败")
            				}
            			});
            		}
            	});
            });
            function showResultDiv(flag) {
                var divResult = document.getElementById("save_result_info");
                if (flag){
                	divResult.innerHTML="保存成功";
                    divResult.style.display = "block";
                }
                else
                    divResult.style.display = "none";
            }
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
        <!--主要区域开始-->
        <div id="main">            
            <!--保存操作后的提示信息：成功或者失败-->
            <div id="save_result_info" class="save_success"></div><!--保存失败，数据并发错误！-->
            <form action="" method="" class="main_form">
                <div class="text_info clearfix"><span>账号：</span></div>
                <div class="input_info"><input type="text" readonly="readonly" id="code" class="readonly" value="${player.code }" /></div>
                <div class="text_info clearfix"><span>胜/败：</span></div>
                <div class="input_info">
                    <input type="text" readonly="readonly" class="readonly width200" value="${player.win}/${player.lose}" />
                </div>
                <div class="text_info clearfix"><span>昵称：</span></div>
                <div class="input_info">
                    <input id="name" type="text" value="${player.name}" />
                    <div class="validate_msg_medium"><span id="name_span"></span></div>
                </div>
                <div class="text_info clearfix"><span>手机：</span></div>
                <div class="input_info">
                    <input id="telephone" type="text" class="width200" value="${player.telephone}" />
                    <div class="validate_msg_medium"><span id="telephone_span"></span></div>
                </div>
                <div class="text_info clearfix"><span>Email：</span></div>
                <div class="input_info">
                    <input id="email" type="text" class="width200" value="${player.email }" />
                    <div class="validate_msg_medium"><span id="email_span"></span></div>
                </div>
                <div class="text_info clearfix"><span>注册时间：</span></div>
                <div class="input_info"><input type="text" readonly="readonly" class="readonly" value="${player.enrolldate}"/></div>
                <div class="button_info clearfix">
                    <input type="button" value="保存" class="btn_save" id="save" />
                </div>
            </form>  
        </div>
        <!--主要区域结束-->
        <div id="footer">
            <p>[明日复明日，明日何其多]</p>            
            <p>--@朱占明</p>
        </div>
    </body>
</html>
