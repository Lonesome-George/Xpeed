<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>搜索引擎</title>
    <link rel="stylesheet" href="style/css/bootstrap.min.css">
    <script src="style/js/bootstrap.min.js"></script>
    <script src="style/js/jquery.min.js"></script>
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

	/* .pagination li{width:100%;height:38px;line-height:38px;}
	.pagination li.cut{background:red;} */
	</style>
</head>
<body style=" position: relative">
<div class="indexlong">
  <form class="form-inline" action="query!queDetail.action">
  <div class="form-group">
    <p class="form-control-static">搜索</p>
  </div>
  <div class="form-group">
    <input type="text" class="form-control" id="inputPassword2" placeholder="关键词" name="query.queryWords" size="70" value="${queryWords }">
  </div>
  <button type="submit" class="btn btn-default">查找</button>
</form>
</div>

<div class = "resu_ana">
找到约  ${totalHits} 条结果
<nobr> （用时 ${querySeconds} 秒）&nbsp;</nobr>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->


<div class="getlist">
	<div class= "container"> 		
  	<s:iterator value="queryDocs" status="stat" >
  	<div class = "results" id = "">
	 	<h3 class = "">
	 		<a href="${url}" >${title}</a>
	 	</h3>	 	
	 	<div class="resu_">
	 		<s:if test="body.length() > 100">
	 			<s:property value = "#body.substring(0,100)" />...
	 		</s:if>
	 		<esle>
	 			${body}
	 		</esle>
	 	</div>  	
  	</div>
	</s:iterator> 	
</div>
</div>
<nav style="  text-align: center;width: 700px;margin-top:100px">
	<s:if test="totalPage>1">
	<ul class="pagination">
		<li class="pre">
      		<a  href="query!queDetail.action?query.queryWords=${query.queryWords}&pageNo=${pageNo-1}" aria-label="Previous">
        	<span aria-hidden="true">&laquo;</span>
      		</a>
    	</li>
    	<c:forEach  var="i" begin="1" end="${totalPage}">
         	<li id="a_${i}" class="cut">
         		<a href="query!queDetail.action?query.queryWords=${query.queryWords}&pageNo=${i}">${i}</a>
         	</li>
        </c:forEach>
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
</nav>
</body>
</html>