<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>搜索引擎</title>
    <link rel="stylesheet" href="style/css/bootstrap.min.css">
    <script src="style/js/bootstrap.min.js"></script>
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
	.getlist{width:650px;height:500px;margin-left: 50px;}
	.results{width: 600px;height: 120px;}
	.getlist li{width:100%;height:38px;line-height:38px;}
	</style>
</head>
<body style=" position: relative">
<div class="indexlong">
  <form class="form-inline" action="query!queDetail.action">
  <div class="form-group">
    <label class="sr-only">Email</label>
    <p class="form-control-static">搜索</p>
  </div>
  <div class="form-group">
    <label for="inputPassword2" class="sr-only">Password</label>
    <input type="text" class="form-control" id="inputPassword2" placeholder="关键词" name="query.queryWords" size="80">
  </div>
  <button type="submit" class="btn btn-default">查找</button>
</form>
</div>

<div class = "resu_ana">
找到约 0 条结果
<nobr> （用时 0.27 秒）&nbsp;</nobr>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->


<div class="getlist">


	<div class= "container">
  	
  	<s:iterator value="queryDocs"  id="queryDoc" >
  	<div class = "results" id = "">
	 	<h3 class = "">
	 		<a href="<s:property value="$queryDoc.url"/>"><s:property value="#queryDoc.title"/></a>
	 	</h3>
	 	
	 	<div class="resu_"><s:property value="#queryDoc.body"/></div>
  	
  	</div>
	</s:iterator>
  	

</div>
</div>
<nav style="  text-align: center;">
  <ul class="pagination">
    <li>
      <a href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <li><a href="#">1</a></li>
    <li><a href="#">2</a></li>
    <li><a href="#">3</a></li>
    <li><a href="#">4</a></li>
    <li><a href="#">5</a></li>
	<li><a href="#">6</a></li>
    <li><a href="#">7</a></li>
    <li><a href="#">8</a></li>
    <li><a href="#">9</a></li>
    <li><a href="#">10</a></li>
    <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>
 </div>	
</body>
</html>