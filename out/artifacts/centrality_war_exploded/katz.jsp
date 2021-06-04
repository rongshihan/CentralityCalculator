<%--
  Created by IntelliJ IDEA.
  User: rsh
  Date: 2020/2/15
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>分析结果</title>
    <link rel="stylesheet" href="style/css/bootstrap.min.css">
    <link rel="stylesheet" href="style/css/dashboard.css">

    <script src="style/js/jquery.min.js"></script>
    <script src="style/js/bootstrap.min.js"></script>
    <script src="style/js/echarts.min.js" type="text/javascript"></script>
    <script src="style/js/echarts-wordcloud.min.js" type="text/javascript"></script>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Centrality</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="index.jsp">首页</a></li>
                <li><a href="#">设置</a></li>
                <li><a href="#">简介</a></li>
                <li><a href="#">帮助</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="搜索...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="degreeServlet">Degree</a></li>
                <li><a href="betweennessServlet">Betweenness</a></li>
                <li><a href="closenessServlet">Closeness<span class="sr-only">(current)</span></a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="eigenvectorServlet">Eigenvector</a></li>
                <li class="active"><a href="katzServlet">Katz</a></li>
                <li><a href="pageRankServlet">PageRank</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="#">Total</a></li>
            </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ul id="myTab" class="nav nav-tabs">
                <li class="active">
                    <a href="#table" data-toggle="tab">表格</a>
                </li>

                <li class="dropdown">
                    <a href="#" id="myTabDrop1" class="dropdown-toggle" data-toggle="dropdown">
                        关系图
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
                        <li><a href="#graph" tabindex="-1" data-toggle="tab">中心度</a></li>
                        <li><a href="#setting" tabindex="-1" data-toggle="tab">设置</a></li>
                    </ul>
                </li>

                <li>
                    <a href="#wordcloud" data-toggle="tab">词云</a>
                </li>

                <li>
                    <a href="#value" data-toggle="tab">参数值设置</a>
                </li>
            </ul>
            <form action="katzServlet" method="post">
                <div id="myTabContent" class="tab-content">

                    <div id="table" class="tab-pane fade in active">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>
                                        <a href="katzServlet?sort=0&alpha=${alpha}&beta=${beta}&enlarge=${enlarge}">节点</a>
                                    </th>
                                    <th>
                                        <a href="katzServlet?sort=1&alpha=${alpha}&beta=${beta}&enlarge=${enlarge}">Katz中心度</a>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="U" items="${total}">
                                    <tr>
                                        <td>${U.name}</td>
                                        <td>${U.kc}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div id="graph" class="tab-pane fade" style="width: 1200px;height: 600px;"></div>
                    <div id="setting" class="tab-pane fade">
                        <nav class="navbar navbar-default">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                            data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                                        <span class="sr-only">Toggle navigation</span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                    </button>
                                    <h3>关系图相关系数设置</h3>
                                </div>
                                <ul class="nav navbar-btn navbar-right">
                                    <li><input class="btn btn-lg btn-primary" type="submit" value="提交设置"></li>
                                </ul>
                            </div><!--/.container-fluid -->
                        </nav>
                        <div class="input-group input-group-lg">
                            <span class="input-group-addon">放大值:</span>
                            <input type="text" class="form-control" placeholder="当前为${enlarge}" name="enlarge"
                                   value="${enlarge}">
                        </div>
                    </div>

                    <div id="wordcloud" class="tab-pane fade" style="width: 1200px;height: 620px"></div>
                    <div id="value" class="tab-pane fade">

                        <nav class="navbar navbar-default">
                            <div class="container-fluid">
                                <div class="navbar-header">
                                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                            data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                                        <span class="sr-only">Toggle navigation</span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                    </button>
                                    <h3>矩阵特征值为${lamta}，特征值倒数值为${range}</h3>
                                </div>
                                <ul class="nav navbar-btn navbar-right">
                                    <li><input class="btn btn-lg btn-primary" type="submit" value="提交设置"></li>
                                </ul>
                            </div><!--/.container-fluid -->
                        </nav>
                        <div class="input-group input-group-lg">
                            <span class="input-group-addon">α值:</span>
                            <input type="text" class="form-control" placeholder="当前为${alpha}" name="alpha"
                                   value="${alpha}">
                        </div>
                        <div class="alert alert-info">建议设置0到${range}</div>
                        <div class="input-group input-group-lg">
                            <span class="input-group-addon">β值:</span>
                            <input type="text" class="form-control" placeholder="当前为${beta}" name="beta"
                                   value="${beta}">
                        </div>
                        <div class="alert alert-info">建议设置0到1.0</div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    var chart1 = echarts.init(document.getElementById("graph"));

    function get_color() {
        return 'rgb(' + [
            Math.round(Math.random() * 160),
            Math.round(Math.random() * 160),
            Math.round(Math.random() * 160)
        ].join(',') + ')';
    }

    var option = {
        title: {
            text: "分析结果",
            left: '3%',
            top: '3%',
            textStyle: {
                color: '#000',
                fontSize: '30',
            }
        },

        tooltip: {
            formatter: function (param) {
                if (param.dataType === 'edge') {
                    return param.data.target;
                }
                return "节点名称：" + param.data.name + "<br/>" +
                    "Katz中心度：" + param.data.symbolSize / ${enlarge};
            }
        },

        legend: {
            type: 'scroll',
            orient: 'vertical',
            right: 10
        },

        series: [{
            type: "graph",
            top: '10%',
            roam: true,
            focusNodeAdjacency: true,
            force: {
                repulsion: 10000,
                edgeLength: [10, 50]
            },

            edgeSymbol: ['none', 'arrow'],
            layout: "force",
            symbol: 'circle',

            lineStyle: {
                normal: {
                    color: '#000',
                    width: 1,
                    type: 'solid',
                    opacity: 0.5,
                    curveness: 0
                }
            },

            label: {
                normal: {
                    show: false,
                    position: "inside",
                    textStyle: {
                        fontSize: 16
                    }
                }
            },

            edgeLabel: {
                normal: {
                    show: false,
                    textStyle: {
                        fontSize: 14
                    },
                    formatter: function (param) {
                        return param.data.category;
                    }
                }
            },

            data: [
                <c:forEach var="U" items="${KC}">
                {
                    name: "${U.key}",
                    draggable: true,
                    symbolSize: ${U.value * enlarge},
                    itemStyle: {
                        color: get_color()
                    }
                },
                </c:forEach>
            ],

            categories: [
                <c:forEach var="U" items="${KC}">
                {name: "${U.key}"},
                </c:forEach>
            ],

            links: [
                <c:forEach var="i" begin="1" end="${fn:length(data) - 1}">
                <c:forEach var="j" begin="1" end="${fn:length(data[i]) - 1}">
                <c:if test="${requestScope.data[i][j]!=0.0}">
                {
                    target: "${requestScope.data[0][j]}",
                    source: "${requestScope.data[i][0]}",
                    category: "${requestScope.data[i][j]}"
                },
                </c:if>
                </c:forEach>
                </c:forEach>
            ]
        }],

        animationEasingUpdate: "quinticInOut",
        animationDurationUpdate: 100
    };

    chart1.setOption(option)
</script>

<script type="text/javascript">
    var chart = echarts.init(document.getElementById('wordcloud'));

    chart.setOption({
        series: [{
            type: 'wordCloud',

            shape: 'circle',

            //maskImage: maskImage,

            left: 'center',
            top: 'center',
            width: '70%',
            height: '80%',
            right: null,
            bottom: null,

            sizeRange: [12, 60],

            rotationRange: [-90, 90],
            rotationStep: 45,

            gridSize: 8,

            drawOutOfBound: false,

            tooltip: {
                show: true
            },

            textStyle: {
                normal: {
                    fontFamily: 'sans-serif',
                    fontWeight: 'bold',
                    color: function () {
                        return 'rgb(' + [
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },

            data: [
                <c:forEach var="U" items="${KC}">
                {
                    name: "${U.key}",
                    value: ${U.value}
                },
                </c:forEach>
            ]
        }]
    });
</script>

