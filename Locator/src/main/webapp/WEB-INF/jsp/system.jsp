<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
    String context = request.getContextPath();
    request.setAttribute("context",context);
    request.setAttribute("page", "system");
%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>系统</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <link type="image/x-icon" href="/favicon.ico" rel="shortcut icon" />
    <link href="/favicon.ico" rel="bookmark icon" />
    
    <script src="js/jquery-2.2.3.js"></script>
    <script src="js/jquery-form.js"></script>
    <script src="js/pintuer.js"></script>
    <script src="js/respond.js"></script>
    <script src="js/admin.js"></script>
    <script src="js/layer.js"></script> 
</head>

<script>
	function setDefault(){
		$.ajax({
	  	   	  url:"sysDefault/getSysDefault",
	  	   	  type:"get",
	  	   	  dataType:"json",
	  	   	  success:function(data){	  	   	  	  
				  $("#locationInterval").val(data.locationInterval);	
				  $("#locationUpload").val(data.locationUpload);
				  $("#locateInterval").val(data.locateInterval);
				  $("#locateTimes").val(data.locateTimes); 	
	  	   	  },
	  	   	  error:function(){
	  	   	  }	   
	  	});	
	}
    
   $(document).ready(function(){
  	    setDefault();
		$(".form-reset").mousedown(function(){
			setTimeout(function(){$(".form-reset").click();},0);
			setDefault();
		});
		
		$("#reset").click(function(){
			setDefault();
		});
    });
    
    
    function onSubmit(){
    	$.ajax({
	  	   	  url:"sysDefault/updateSysDefault",
	  	   	  type:"post",
	  	   	  data:{	  	   	  	 
				  "locationInterval":$("#locationInterval").val(),	
				  "locationUpload":$("#locationUpload").val(),
				  "locateInterval":$("#locateInterval").val(),
				  "locateTimes":$("#locateTimes").val()
	  	   	  },
	  	   	  success:function(data){
	  	   	  	  layer.alert('提交成功',function(){
	  	   	  	  	   location.href="system.html";
	  	   	  	  });
	  	   	  },
	  	   	  error:function(){
	  	   	  }	   
	  	});	
	  	
	  	return false;
    }

</script>

<body>
<%@include file="naviBar.jsp"%>
<div class="admin" style="font-size:15px;">
   <div class="panel">
      <div class="panel-head" style="font-size:18px"><strong>系统默认配置</strong></div>
   <form class="form-x" onsubmit="return onSubmit();"> 
       <br/><br/>    
       <div class="form-group">
           <div class="label" style="width:20%;"><label>采集间隔(位置):&nbsp;&nbsp;&nbsp;&nbsp;</label></div>
           <div class="field" style="width:15%;">
               <input type="text" class="input" id="locationInterval" name="locationInterval" size="50" data-validate="required:请填写数据采集间隔,number:格式错误(数字)" />
           </div>
           <div class="label" style="width:5%;text-align:left;"><label>&nbsp;分钟</label></div> 
       </div>
       <div class="form-group">
           <div class="label" style="width:20%;"><label>上传条数(位置):&nbsp;&nbsp;&nbsp;&nbsp;</label></div>
           <div class="field" style="width:15%;">
               <input type="text" class="input" id="locationUpload" name="locationUpload" size="50" data-validate="required:请填写上传数据条数,number:格式错误(数字)" />
           </div>
           <div class="label" style="width:5%;text-align:left;"><label>&nbsp;条</label></div> 
       </div>
       <br/>
       <div class="form-group">
           <div class="label" style="width:20%;"><label>紧急定位间隔:&nbsp;&nbsp;&nbsp;&nbsp;</label></div>
           <div class="field" style="width:15%;">
               <input type="text" class="input" id="locateInterval" name="locateInterval" size="50" data-validate="required:请填写紧急定位间隔,number:格式错误(数字)" />
           </div>
           <div class="label" style="width:5%;text-align:left;"><label>&nbsp;秒</label></div> 
       </div>     
       <div class="form-group">
           <div class="label" style="width:20%;"><label>紧急定位次数:&nbsp;&nbsp;&nbsp;&nbsp;</label></div>
           <div class="field" style="width:15%;">
               <input type="text" class="input" id="locateTimes" name="locateTimes" size="50" data-validate="required:请填写紧急定位次数,number:格式错误(数字)" />
           </div>
           <div class="label" style="width:5%;text-align:left;"><label>&nbsp;次</label></div> 
       </div>
       <br/><br/><br/>
       <div class="form-button" style="margin-left:20%;">
       	   <button class="button bg-main form-submit text-medium icon-check" type="submit" id="submit"> 提交</button>
       	   &nbsp;&nbsp;
   	   	   <button class="button bg-main form-reset text-medium icon-undo" type="reset" id="reset"> 重置</button>
   	   </div>
   	   <br/><br/><br/>
   </form>
</div>
</div>
</body>
</html>