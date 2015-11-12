<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>搜索引擎</title>
    <link rel="stylesheet" href="style/css/bootstrap.min.css">
    <script src="style/js/jquery.min.js"></script>
    <script src="style/js/bootstrap.min.js"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>

    <!--[endif]-->
    <style>
	.indexlong{width:320px;margin:auto;margin-top:20px;}
    .getlist{width:1000px;height:500px;margin:auto;}
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
    <input type="text" class="query-words" id="inputPassword2" placeholder="关键词" name="query.queryWords">
  </div>
  <button type="submit" class="btn btn-default">查找</button>
</form>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<div class="getlist">
	<ul>
		<li>1</li>
		<li>2</li>
		<li>3</li>
		<li>4</li>
		<li>5</li>
		<li>6</li>
		<li>7</li>
		<li>8</li>
	</ul>
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
    <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>

  <script>
  .class("query-words"),val()
  
    
  </script>
</body>
</html>