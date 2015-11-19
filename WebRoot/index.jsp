<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${ queryWords}_搜索引擎</title>
    <link rel="stylesheet" href="style/css/bootstrap.min.css">
    <script src="style/js/bootstrap.min.js"></script>
    <script src="style/js/jquery.min.js"></script>
    <link rel="shortcut icon" href="style/images/logo.ico" />
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
	
    <!--[endif]-->
   <style>
	.indexlong{width: 700px;margin-top:20px;display:block;margin-left: 40px;}
      /*  .indexlong img{ width: 100%; height: 100%; display: block;}
       /* .indexlong{ background: url("loginwp.jpg") center center repeat; }*/
     /*   .indexlongfrom{ width: 426px; height: 375px;left:50%;top:50%;margin-left:-213px;margin-top:-187px;background: url("houligon.png") repeat;  position: absolute}
        .form-horizontal{width: 310px; margin: auto; margin-top: 127px;}*/
	.resu_ana{width:320px;margin-left: 40px;margin-top:20px;display:block;height: 20px;color: #808080;font-size:10px}
	.container{}
	.getlist{width:650px;margin-left: 50px;}
	.results{width: 600px;height:90px;}
	/* .pagination li{width:100%;height:38px;line-height:38px;}*/
	.fenye ul li.cut{color:red;} 
	.pagination>li.cut>a{background:#337ab7;color:#fff}
	</style>
</head>
<body style=" position: relative">
<div class="indexlong">
  <form class="form-inline" action="query!queDetail.action">
  <div class="form-group">
    <p class="form-control-static">搜索</p>
  </div>
  <div class="form-group">
    <input type="text" class="form-control" id="inputPassword2" placeholder="关键词" name="query.queryWords" size="70" value=${ queryWords}>
  </div>
  <button type="submit" class="btn btn-default">查找</button>
</form>
</div>


<s:if test="totalHits == 0">
</s:if>
<s:else>
	<div class = "resu_ana">
	找到约  ${totalHits} 条结果
	<nobr> （用时 0.27 秒）&nbsp;</nobr>
	</div>
</s:else>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->


<div class="getlist">
	<div class= "container"> 		
  	<s:iterator value="queryDocs" status="stat" >
  	<div class = "results" id = "">
	 	<h3 class = "">
	 		<a href="${url}" >
	 		<%-- <s:if test="title.length() >20">
	 			<s:property value = "#title.replaceAll('<[^>]+>','').substring(0,20)" escape="false" />... 
	 			<s:property value = "title.substring(0,20)"/>...
	 		</s:if> --%>
	 		${title}
	 		<s:else>
	 		</s:else>
	 		</a>
	 	</h3>	 	
	 	<div class="resu_">
	 		<%-- <s:if test="body.replaceAll('<[^>]+>','').length() >100 ">
	 			<s:property value="#body.replaceAll('<[^>]+>','').substring(0,100)" escape="false" />...
	 			<s:property value = "body.substring(0,100)"/>...
	 		</s:if> --%>
	 		${body}
	 		<s:else>
	 		</s:else>
	 	</div>  	
  	</div>
	</s:iterator> 	
</div>
</div>
<nav style="  text-align: center;width: 700px;margin-top:50px">
<div class="fenye">
	<s:if test="totalPage>1">
	<ul class="pagination">
		<li class="pre">
      		<a  href="query!queDetail.action?query.queryWords=${query.queryWords}&pageNo=${pageNo-1}" aria-label="Previous">
        	<span aria-hidden="true">&laquo;</span>
      		</a>
    	</li>
    	<s:if test="totalPage>10 && pageNo >= 10">
    		<c:forEach  var="i" begin="${pageNo-5}" end="${pageNo+4}">
         	<li id="a_${i}">
         		<a href="query!queDetail.action?query.queryWords=${query.queryWords}&pageNo=${i}">${i}</a>
         	</li>
        </c:forEach>
    	</s:if>
    	<s:elseif test="totalPage>10 && pageNo < 10">
    		<c:forEach  var="i" begin="1" end="10">
         	<li id="a_${i}">
         		<a href="query!queDetail.action?query.queryWords=${query.queryWords}&pageNo=${i}">${i}</a>
         	</li>
        </c:forEach>
    	</s:elseif>
    	<s:else>
    	<c:forEach  var="i" begin="1" end="${totalPage}">
         	<li id="a_${i}">
         		<a href="query!queDetail.action?query.queryWords=${query.queryWords}&pageNo=${i}">${i}</a>
         	</li>
        </c:forEach>
        </s:else>
	    <li class = "next">
	      	<a  href="query!queDetail.action?query.queryWords=${query.queryWords}&pageNo=${pageNo+1}" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      	</a>
	    </li>
	</ul>
	  <script  type="text/javascript">
	    $(function(){
	         if(${pageNo-1}==0){
	         	$(".pre").hide();
	         }
	         if(${pageNo}==${totalPage}){
	            $(".next").hide();
	         }
	         
	         var cPage=document.getElementById("a_${pageNo}");
			 $(cPage).addClass("cut");
	         
	    })
	  </script>
  </s:if>
 </div>
</nav>
</body>
</html>