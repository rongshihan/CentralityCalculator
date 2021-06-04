<%--
  Created by IntelliJ IDEA.
  User: rsh
  Date: 2020/1/5
  Time: 11:57
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
                <li class="active"><a href="degreeServlet">Degree<span class="sr-only">(current)</span></a></li>
                <li><a href="betweennessServlet">Betweenness</a></li>
                <li><a href="closenessServlet">Closeness</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="eigenvectorServlet">Eigenvector</a></li>
                <li><a href="katzServlet">Katz</a></li>
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
                        <li><a href="#indegree1" tabindex="-1" data-toggle="tab">入度中心度</a></li>
                        <li><a href="#outdegree1" tabindex="-1" data-toggle="tab">出度中心度</a></li>
                        <li><a href="#setting" tabindex="-1" data-toggle="tab">设置</a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" id="myTabDrop2" class="dropdown-toggle" data-toggle="dropdown">
                        词云
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop2">
                        <li><a href="#indegree2" tabindex="-1" data-toggle="tab">入度中心度</a></li>
                        <li><a href="#outdegree2" tabindex="-1" data-toggle="tab">出度中心度</a></li>
                    </ul>
                </li>
            </ul>
            <div id="myTabContent" class="tab-content">

                <div id="table" class="tab-pane fade in active">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>
                                    <a href="degreeServlet?sort=0&enlarge=${enlarge}">节点</a>
                                </th>
                                <th>
                                    <a href="degreeServlet?sort=1&enlarge=${enlarge}">绝对入度中心度</a>
                                </th>
                                <th>
                                    <a href="degreeServlet?sort=2&enlarge=${enlarge}">相对入度中心度</a>
                                </th>
                                <th>
                                    <a href="degreeServlet?sort=3&enlarge=${enlarge}">绝对出度中心度</a>
                                </th>
                                <th>
                                    <a href="degreeServlet?sort=4&enlarge=${enlarge}">相对出度中心度</a>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="U" items="${total}">
                                <tr>
                                    <td>${U.name}</td>
                                    <td>${U.idc}</td>
                                    <td>${U.nidc}</td>
                                    <td>${U.odc}</td>
                                    <td>${U.nodc}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div id="indegree1" class="tab-pane fade" style="width: 1200px;height: 620px;"></div>
                <div id="outdegree1" class="tab-pane fade" style="width: 1200px;height: 620px"></div>
                <div id="setting" class="tab-pane fade">
                    <form action="degreeServlet" method="post">
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
                            <input type="text" class="form-control" placeholder="当前为${enlarge}" name="enlarge" value="${enlarge}">
                        </div>
                    </form>
                </div>

                <div id="indegree2" class="tab-pane fade" style="width: 1200px;height: 620px;"></div>
                <div id="outdegree2" class="tab-pane fade" style="width: 1200px;height: 620px"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    var chart1 = echarts.init(document.getElementById("indegree1"));

    /**
     * 获得随机颜色
     * @returns {string}
     */
    function get_color() {
        return 'rgb(' + [
            Math.round(Math.random() * 160),
            Math.round(Math.random() * 160),
            Math.round(Math.random() * 160)
        ].join(',') + ')';
    }

    var option = {
        //backgroundColor: '#ccc',	// 背景颜色
        title: {                    // 图表标题
            text: "分析结果",           // 标题文本
            left: '3%',                    // 标题距离左侧边距
            top: '3%',                     // 标题距顶部边距
            textStyle: {                       // 标题样式
                color: '#000',                     // 标题字体颜色
                fontSize: '30',                    // 标题字体大小
            }
        },

        tooltip: {                  // 提示框的配置
            formatter: function (param) {
                if (param.dataType === 'edge') {
                    //return param.data.category + ': ' + param.data.target;
                    return param.data.target;//鼠标停留在边时，显示边指向的节点名称
                }
                //return param.data.category + ': ' + param.data.name;
                return "节点名称：" + param.data.name + "<br/>" +
                    "入度中心度：" + param.data.symbolSize/${enlarge};//鼠标停留在节点时，显示节点名称
            }
        },

        legend: {
            type: 'scroll',
            orient: 'vertical',
            right: 10
        },

        series: [{
            type: "graph",          // 系列类型:关系图
            top: '10%',             // 图表距离容器顶部的距离
            roam: true,             // 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
            focusNodeAdjacency: true,   // 是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点。[ default: false ]
            force: {                // 力引导布局相关的配置项，力引导布局是模拟弹簧电荷模型在每两个节点之间添加一个斥力，每条边的两个节点之间添加一个引力，每次迭代节点会在各个斥力和引力的作用下移动位置，多次迭代后节点会静止在一个受力平衡的位置，达到整个模型的能量最小化。
                // 力引导布局的结果有良好的对称性和局部聚合性，也比较美观。
                repulsion: 10000,            // [ default: 50 ]节点之间的斥力因子(关系对象之间的距离)。支持设置成数组表达斥力的范围，此时不同大小的值会线性映射到不同的斥力。值越大则斥力越大
                //gravity : 0.00,           //节点受到的向中心的引力因子。该值越大节点越往中心点靠拢。
                edgeLength: [10, 50]        // [ default: 30 ]边的两个节点之间的距离(关系对象连接线两端对象的距离,会根据关系对象值得大小来判断距离的大小)，
                                            // 这个距离也会受 repulsion。支持设置成数组表达边长的范围，此时不同大小的值会线性映射到不同的长度。值越小则长度越长。如下示例:
                                            // 值最大的边长度会趋向于 10，值最小的边长度会趋向于 50      edgeLength: [10, 50]
                //layoutAnimation : true
                //因为力引导布局会在多次迭代后才会稳定，这个参数决定是否显示布局的迭代动画，在浏览器端节点数据较多（>100）的时候不建议关闭，布局过程会造成浏览器假死。
            },

            edgeSymbol: ['none', 'arrow'],//设置边的样式，circle为实心圆，arrow为箭头

            // 图的布局。[ default: 'none' ]
            // 'none' 不采用任何布局，使用节点中提供的 x， y 作为节点的位置。
            // 'circular' 采用环形布局;
            // 'force' 采用力引导布局.
            layout: "force",

            // 标记的图形
            //symbol: "path://M19.300,3.300 L253.300,3.300 C262.136,3.300 269.300,10.463 269.300,19.300 L269.300,21.300 C269.300,30.137 262.136,37.300 253.300,37.300 L19.300,37.300 C10.463,37.300 3.300,30.137 3.300,21.300 L3.300,19.300 C3.300,10.463 10.463,3.300 19.300,3.300 Z",
            symbol: 'circle',

            lineStyle: {            // 关系边的公用线条样式。其中 lineStyle.color 支持设置为'source'或者'target'特殊值，此时边会自动取源节点或目标节点的颜色作为自己的颜色。
                normal: {
                    color: '#000',          // 线的颜色[ default: '#aaa' ]
                    width: 1,               // 线宽[ default: 1 ]
                    type: 'solid',          // 线的类型[ default: solid ]   'dashed'    'dotted'
                    opacity: 0.5,           // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。[ default: 0.5 ]
                    curveness: 0            // 边的曲度，支持从 0 到 1 的值，值越大曲度越大。[ default: 0 ]
                }
            },

            label: {                // 关系对象上的标签
                normal: {
                    show: true,                 // 是否显示标签
                    //position: "inside",         // 标签位置:'top''left''right''bottom''inside''insideLeft''insideRight''insideTop''insideBottom''insideTopLeft''insideBottomLeft''insideTopRight''insideBottomRight'
                    textStyle: {                // 文本样式
                        //fontSize: 16
                    }
                }
            },

            edgeLabel: {                // 连接两个关系对象的线上的标签
                normal: {
                    show: false,
                    textStyle: {
                        //fontSize: 14
                    },
                    formatter: function (param) {        // 标签内容
                        return param.data.category;
                    }
                }
            },

            /**
             * name：节点是否可拖拽，只在使用力引导布局的时候有用。
             * draggable: 关系图节点标记的大小，可以设置成诸如 10 这样单一的数字，也可以用数组分开表示宽和高，例如 [20, 10] 表示标记宽为20，高为10。
             * category: 数据项所在类目的 index。
             */
            data: [
                <c:forEach var="U" items="${NIDC}">
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
                <c:forEach var="U" items="${NIDC}">
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


        animationEasingUpdate: "quinticInOut",          // 数据更新动画的缓动效果。[ default: cubicOut ]    "quinticInOut"
        animationDurationUpdate: 100                    // 数据更新动画的时长。[ default: 300 ]
    };

    // 使用刚指定的配置项和数据显示图表
    chart1.setOption(option)
</script>
<script type="text/javascript">
    var chart1 = echarts.init(document.getElementById("outdegree1"));

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
                    "出度中心度：" + param.data.symbolSize/${enlarge};
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
                <c:forEach var="U" items="${NODC}">
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
                <c:forEach var="U" items="${NODC}">
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
    var chart = echarts.init(document.getElementById('indegree2'));

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
                <c:forEach var="U" items="${NIDC}">
                {
                    name: "${U.key}",
                    value: ${U.value * 10}
                },
                </c:forEach>
            ]
        }]
    });
</script>
<script type="text/javascript">
    var chart = echarts.init(document.getElementById('outdegree2'));

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
                <c:forEach var="U" items="${NODC}">
                {
                    name: "${U.key}",
                    value: ${U.value * 10}
                },
                </c:forEach>
            ]
        }]
    });
</script>
