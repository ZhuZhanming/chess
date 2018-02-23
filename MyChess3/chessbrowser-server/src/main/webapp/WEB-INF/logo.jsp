<%@page pageEncoding="utf-8"%>
<img src="images/logo.png" alt="logo" class="left" />
<!-- 
EL默认取值范围 page,request,session,application
EL也可以从cookie中获取数据,语法:cookie.参数名.value	
 -->
<%--<span>${cookie.adminCode.value } </span>--%>
<span>${player.name }</span>
<a href="toLogin.do">[退出]</a>
