<%@page pageEncoding="utf-8"%>
<!-- 此jsp被其他所有网友所复用,因此无法确定当前用户正在访问的网页是谁,所以最好用绝对路径 -->

<ul id="menu">
	<li><a href="${pageContext.request.contextPath }/index.do" class="index_off"></a></li>
	<li><a href="" class="role_off"></a></li>
	<li><a href="${pageContext.request.contextPath }/toFind.do" class="admin_off"></a></li>
	<li><a href="${pageContext.request.contextPath }/findCost.do" class="fee_off"></a></li>
	<li><a href="" class="account_off"></a></li>
	<li><a href="" class="service_off"></a></li>
	<li><a href="" class="bill_off"></a></li>
	<li><a href="" class="report_off"></a></li>
	<li><a href="${pageContext.request.contextPath }/info.do" class="information_off"></a></li>
	<li><a href="${pageContext.request.contextPath }/toSetPwd.do" class="password_off"></a></li>
</ul>