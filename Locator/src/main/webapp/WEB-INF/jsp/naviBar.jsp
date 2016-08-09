<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.framework.domain.UserInfo"%>
<%  
	String loginState = "notLogin";
  	if(session.getAttribute("loginState")!=null){
  		loginState = "login";
  	}
  	
  	UserInfo user = new UserInfo();
  	if(session.getAttribute("user")!=null){
   		 user = (UserInfo)session.getAttribute("user");
   	}
%>

<script type="text/javascript" src="js/jquery.md5.js"></script> 

<script type='text/javascript'>
   //登录状态是否过期
   if('<%=loginState%>' == 'notLogin'){
   	    location.href = "login.html";
   }
   
   $(document).ready(function(){
   		$("#<%=request.getAttribute("page")%>").addClass("active");
   		$("#logOut").click(function(){
   			location.href="login.html";
   		});
   			
 		$("#change").click(function(){
 			layer.open({
  				type: 1,
 				title: false,
  				closeBtn: 0,
 				shadeClose: true,
 				area: '30%',
  				content: $("#dialog")
			});
			$("#password").focus();
			$(document).keydown(function (e) {
            	if (e.keyCode == 13) {
                	$("#confirm").click();
            	}
        	});
 		});	
 		
 		$("#confirm").click(function(){
 			$(document).keydown(function (e) {
            	if (e.keyCode == 13) {
                	return false;
            	}
        	});
 		
 			var psw = $("#password").val();
 			var newPsw1 = $("#newPassword1").val();
 			var newPsw2 = $("#newPassword2").val();
 			if(psw=='' || newPsw1=='' || newPsw2==''){
 				layer.msg("请填写完整信息");
 			}
 			else if($.md5(psw)!='<%=user.getPassword()%>'){
 				layer.msg("输入的当前密码有误");
 			}
 			else if(newPsw1!=newPsw2){
 				layer.msg("输入的新密码不一致");
 			}
 			else if(newPsw1==psw){
 				layer.msg("新密码与当前密码相同");
 			}
 			else{
 				$.ajax({
 					url:"user/changePsw",
 					type:"post",
 					data:{'userName':'<%=user.getUserName()%>','password':$("#newPassword1").val()},
 					success:function(){				
						location.href="";
						layer.msg("修改成功");
 					},
 					error:function(){
 					}
 				});							
 			}				
 		});
 		
   });
 
</script>

<div class="righter nav-navicon" id="admin-nav">
    <div class="mainer">
        <div class="admin-navbar">
            <span class="float-right">
            	<a class="button button-small bg-main" href="index.html">&nbsp;&nbsp;首&nbsp;&nbsp;页&nbsp;&nbsp;</a>
                <a class="button button-small bg-red" id="logOut" href="#">&nbsp;&nbsp;注&nbsp;&nbsp;销&nbsp;&nbsp;</a>
                <button class="button button-small bg-yellow" id="change">修改密码</button>
            </span>
			<div class="dialog" id="dialog">
				<div class="dialog-head">
					<strong>修改密码</strong>
				</div>
				<div class="dialog-body">
					<form method="post" class="form-x">
						<div class="form-group">
							<div class="label" style="width:30%">
								<label for="password">当前密码：</label>
							</div>
							<div class="field" style="width:60%">
								<input type="password" class="input" id="password" name="password" size="20" data-validate="length#>=6:密码长度不符合要求" placeholder="请输入当前密码" />
							</div>
						</div>
						<div class="form-group">
							<div class="label" style="width:30%">
								<label for="newPassword1">新密码：</label>
							</div>
							<div class="field" style="width:60%">
								<input type="password" class="input" id="newPassword1" name="newPassword2" size="20" data-validate="length#>=6:密码长度不符合要求" placeholder="请输入新密码" />
							</div>
						</div>
						<div class="form-group">
							<div class="label" style="width:30%">
								<label for="newPassword2">确认新密码：</label>
							</div>
							<div class="field" style="width:60%">
								<input type="password" class="input" id="newPassword2" name="newPassword2" size="20" data-validate="length#>=6:密码长度不符合要求" placeholder="请再次输入新密码" />
							</div>
						</div>
						<div class="dialog-foot">
							<a class="button bg-main" id="cancel" href="" >取消</a>
							<a class="button bg-main" id="confirm" href="#">确认</a>
						</div>
					</form>	
				</div>
			</div>
			
            <ul class="nav nav-inline admin-nav">
                <li id="index"><a href="index.html" class="icon-home"> 首页</a></li>	
                <li id="user"><a href="device.html" class="icon-rocket"> 设备</a></li>    
                <li id="position"><a href="position.html" class="icon-map-marker"> 位置</a></li>
                <li id="config"><a href="config.html" class="icon-cog"> 设置</a></li>
                <li id="system"><a href="system.html" class="icon-desktop"> 系统</a></li>
            </ul>
        </div>
    </div>
</div>