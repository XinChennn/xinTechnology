<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 31/08/2022
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript"
            src="https://api.map.baidu.com/api?v=1.0&&type=webgl&ak=8IpvlsPrNzx8qLXhbFj3bGvgio1KWA9E"></script>
    <title>$Title$</title>
</head>
<body>
$END$
<div id="container"></div>

<script>
    var map = new BMapGL.Map("container")
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
    // var map = new BMapGL.Map("container");
    // var point = new BMapGL.Point(121.500033,31.40116);

</script>

</body>
</html>
