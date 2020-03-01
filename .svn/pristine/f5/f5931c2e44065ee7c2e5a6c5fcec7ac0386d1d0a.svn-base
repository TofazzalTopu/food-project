<%--
  Created by IntelliJ IDEA.
  User: Samia
  Date: 6/7/2015
  Time: 11:12 PM
--%>
<%@ page import="java.text.SimpleDateFormat; org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<head>
    <meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
    <title>My Dashboard (${applicationUser?.username})</title>
</head>
<script type="text/javascript">
    var migrationDate = "${new SimpleDateFormat("dd-MM-yyyy").format(new Date())}"
    var previousDate = "";
    var isDisplayed;
    var count = 0;

    $(document).ready(function () {
        loadHighchart();
        %{--portletAjax.addPortlets(${userPortletFeed})--}%
        %{--var dashBoardBusinessDate = $("#dashBoardBusinessDate").val()--}%
        %{--portletAjax.init(dashBoardBusinessDate)--}%
        %{--$("#dashBoardBusinessDate").datepicker({--}%
        %{--showOn: "button",--}%
        %{--buttonImage: "${request.contextPath}/images/cal.gif",--}%
        %{--buttonImageOnly: true,--}%
        %{--dateFormat: 'dd-mm-yy',--}%
        %{--onSelect:function (dateText, inst) {--}%
        %{--var selectedDate = JsUtil.getDate(dateText)--}%
        %{--var mDate = JsUtil.getDate(migrationDate)--}%
        %{--if (selectedDate < mDate) {--}%
        %{--$("#dashBoardBusinessDate").val(previousDate)--}%
        %{--MessageRenderer.renderErrorText("Date should be later or equal to migration date (" + migrationDate + ")");--}%
        %{--return false;--}%
        %{--}--}%
        %{--loadDashboard()--}%
        %{--},--}%
        %{--beforeShow:function (input, inst) {--}%
        %{--previousDate = $("#dashBoardBusinessDate").val()--}%
        %{--}--}%
        %{--});--}%

        %{--isDisplayed = '${params?.flag}';--}%
        $('#div1').highcharts({

                    chart: {
                        type: 'gauge',
                        plotBackgroundColor: null,
                        plotBackgroundImage: null,
                        plotBorderWidth: 0,
                        plotShadow: false
                    },

                    title: {
                        text: '<b>Projected Sales Performance</b>'
                    },

                    subtitle: {
                        text: 'In Millions per Year'
                    },
                    pane: {
                        startAngle: -150,
                        endAngle: 150,
                        background: [{
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#FFF'],
                                    [1, '#333']
                                ]
                            },
                            borderWidth: 0,
                            outerRadius: '109%'
                        }, {
                            backgroundColor: {
                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                stops: [
                                    [0, '#333'],
                                    [1, '#FFF']
                                ]
                            },
                            borderWidth: 1,
                            outerRadius: '107%'
                        }, {
                            // default background
                        }, {
                            backgroundColor: '#DDD',
                            borderWidth: 0,
                            outerRadius: '105%',
                            innerRadius: '103%'
                        }]
                    },

                    // the value axis
                    yAxis: {
                        min: 0,
                        max: 200,

                        minorTickInterval: 'auto',
                        minorTickWidth: 1,
                        minorTickLength: 10,
                        minorTickPosition: 'inside',
                        minorTickColor: '#666',

                        tickPixelInterval: 30,
                        tickWidth: 2,
                        tickPosition: 'inside',
                        tickLength: 10,
                        tickColor: '#666',
                        labels: {
                            step: 2,
                            rotation: 'auto'
                        },
                        title: {
                            text: 'Sales Amount'
                        },
                        plotBands: [{
                            from: 0,
                            to: 60,
                            color: '#DF5353' // green
                        }, {
                            from: 60,
                            to: 120,
                            color: '#DDDF0D' // yellow
                        }, {
                            from: 120,
                            to: 200,
                            color: '#55BF3B' // red
                        }]
                    },

                    series: [{
                        name: 'Projected Sale',
                        data: [80],
                        tooltip: {
                            valueSuffix: ' Million/Year'
                        }
                    }]

                },
                // Add some life
                function (chart) {
                    if (!chart.renderer.forExport) {
                        setInterval(function () {
                            var point = chart.series[0].points[0],
                                    newVal,
                                    inc = Math.round((Math.random() - 0.5) * 20);

                            newVal = point.y + inc;
                            if (newVal < 0 || newVal > 200) {
                                newVal = point.y - inc;
                            }

                            point.update(newVal);

                        }, 3000);
                    }
                }
        );

        $('#div6').highcharts({
            chart: {
                type: 'bar'
            },
            title: {
                text: '<b>Target vs Achievement(Current Month)</b>'
            },
            subtitle: {
                text: 'By Channel'
            },
            xAxis: {
                categories: ['Modern Trade', 'Institutional Sales', 'Retail Sales', 'Others'],
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: '',
                    align: 'high'
                },
                labels: {
                    overflow: 'justify'
                }
            },
            tooltip: {
                valueSuffix: ' millions'
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -40,
                y: 80,
                floating: true,
                borderWidth: 1,
                backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
                shadow: true
            },
            credits: {
                enabled: false
            },
            series: [{
                name: 'Target',
                data: [107, 31, 635, 203]
            }, {
                name: 'Achievement',
                data: [133, 156, 947, 408]
            }]
        });

        $('#div3').highcharts({
            title: {
                text: '<b>Sale vs Collection</b>'
            },
            xAxis: {
                title: {
                    text: "Sales Channel"
                },
                categories: ['Modern Trade', 'Institutional Sales', 'Retail Sales', 'Other']
            },
            yAxis: {
                title: {
                    text: "Amount"
                },
                gridLineWidth: 0
            },
//            labels: {
//                items: [{
//                    html: 'Total Sales',
//                    style: {
//                        left: '50px',
//                        top: '18px',
//                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
//                    }
//                }]
//            },
            series: [{
                type: 'column',
                name: 'Sales',
                data: [600, 300, 280, 450]
            },
                {
                type: 'spline',
                name: 'Collection',
                data: [316.67, 270, 280, 388.33],
                marker: {
                    lineWidth: 2,
                    lineColor: Highcharts.getOptions().colors[3],
                    fillColor: 'white'
                }
            }
//                {
//                type: 'pie',
//                name: 'Total Sales',
//                data: [{
//                    name: 'Banani',
//                    y: 2400,
//                    color: Highcharts.getOptions().colors[0] // Jane's color
//                }, {
//                    name: 'Gulshan',
//                    y: 2541,
//                    color: Highcharts.getOptions().colors[1] // John's color
//                }, {
//                    name: 'Mohammadpur',
//                    y: 2760,
//                    color: Highcharts.getOptions().colors[2] // Joe's color
//                }],
//                center: [150, 30],
//                size: 100,
//                showInLegend: false,
//                dataLabels: {
//                    enabled: false
//                }
//            }
            ]
        });

        $('#div5').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: '<b>Year-to-Date (YTD) Sale</b>'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: "Brands",
                colorByPoint: true,
                data: [{
                    name: "Modern Trade",
                    y: 29.33
                }, {
                    name: "Institutional Sales",
                    y: 24.03
                }, {
                    name: "Retail Sales",
                    y: 30.38
                }, {
                    name: "Other",
                    y: 16.26
                }]
            }]
        });

        $('#div4').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '<b>Increase and Decrease of Salesman</b>'
            },
            subtitle: {
                text: 'Monthly'
            },
            xAxis: {
                categories: [
                    'Jan',
                    'Feb',
                    'Mar',
                    'Apr',
                    'May',
                    'Jun',
                    'Jul',
                    'Aug',
                    'Sep',
                    'Oct',
                    'Nov',
                    'Dec'
                ],
                crosshair: true
            },
            yAxis: {
                min: 0,
                gridLineWidth: 0,
                title: {
                    text: ''
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: [{
                name: 'Modern Trade',
                data: [3.1, 3.3, 2.1, 1.6, 5.0, 4.2, 1.2, 1.9, 2.8, 3.3, 3.4, 2.0]

            }, {
                name: 'Institutional Sales',
                data: [3.6, 8.8, 8.5, 3.4, 6.0, 4.5, 5.0, 4.3, 1.2, 3.5, 6.6, 2.3]

            }, {
                name: 'Retail Sales',
                data: [8.9, 8.8, 9.3, 1.4, 7.0, 8.3, 9.0, 9.6, 2.4, 5.2, 9.3, 1.2]

            }, {
                name: 'Other',
                data: [2.4, 3.2, 4.5, 9.7, 2.6, 5.5, 7.4, 0.4, 7.6, 9.1, 6.8, 1.1]

            }]
        });

        $('#div2').highcharts({
            chart: {
                type: 'spline'
            },
            title: {
                text: '<b>Market Claim</b>'
            },
            subtitle: {
                text: 'Current Month vs Last Month'
            },
            xAxis: {
//                type: 'datetime',
                categories: ['Modern Trade','Institutional Sales','Retail Sales','Other'],
                labels: {
                    overflow: 'justify'
                }
            },
            yAxis: {
                title: {
                    text: 'BDT'
                },
                minorGridLineWidth: 0,
                gridLineWidth: 0,
                alternateGridColor: null
//                plotBands: [{ // Light air
//                    from: 0.3,
//                    to: 1.5,
//                    color: 'rgba(68, 170, 213, 0.1)'
//                }, { // Light breeze
//                    from: 1.5,
//                    to: 3.3,
//                    color: 'rgba(0, 0, 0, 0)'
//                }, { // Gentle breeze
//                    from: 3.3,
//                    to: 5.5,
//                    color: 'rgba(68, 170, 213, 0.1)'
//                }, { // Moderate breeze
//                    from: 5.5,
//                    to: 8,
//                    color: 'rgba(0, 0, 0, 0)'
//                }, { // Fresh breeze
//                    from: 8,
//                    to: 11,
//                    color: 'rgba(68, 170, 213, 0.1)'
//                }, { // Strong breeze
//                    from: 11,
//                    to: 14,
//                    color: 'rgba(0, 0, 0, 0)'
//                }, { // High wind
//                    from: 14,
//                    to: 15,
//                    color: 'rgba(68, 170, 213, 0.1)'
//                }]
            },
            tooltip: {
                shared: true
            },
            plotOptions: {
                spline: {
                    lineWidth: 4,
                    states: {
                        hover: {
                            lineWidth: 5
                        }
                    },
                    marker: {
                        enabled: false
                    }
//                    pointInterval: 3600000 * 24 * 30, // one hour
//                    pointStart: Date.UTC(2010, 12, 10, 0, 0, 0)
                }
            },
            series: [{
                name: 'Claim Last Month',
                data: [3000, 2080, 4800, 6000]

            }, {
                name: 'Claim This Month',
                data: [6000, 5200, 5500, 3900]
            }],
            navigation: {
                menuItemStyle: {
                    fontSize: '10px'
                }
            }
        });

//        $('#div5').highcharts({
//            chart: {
//                polar: true
//            },
//
//            title: {
//                text: '<b>Target vs Achievement(Current Month)</b>'
//            },
//            subtitle: {
//                text: 'By Channel'
//            },
//            pane: {
//                startAngle: 0,
//                endAngle: 360
//            },
//
//            xAxis: {
//                tickInterval: 45,
//                min: 0,
//                max: 360,
//                labels: {
//                    formatter: function () {
//                        if (this.value == 0) {
//                            return 'Raw Milk';
//                        } else if (this.value == 45) {
//                            return 'Pasturized Milk';
//                        } else if (this.value == 90) {
//                            return 'UHT Milk';
//                        } else if (this.value == 135) {
//                            return 'Flavoured Milk';
//                        } else if (this.value == 180) {
//                            return 'Juice';
//                        } else if (this.value == 225) {
//                            return 'Curd';
//                        } else if (this.value == 270) {
//                            return 'Ghee';
//                        } else {
//                            return 'Butter';
//                        }
////                        this.value + '°';
//                    }
//                }
//            },
//
//            yAxis: {
//                min: 0,
//                gridLineWidth: 0
//            },
//            tooltip: {
//                formatter: function () {
//                    var head = '';
//                    if (this.x == '0') {
//                        head = 'Raw Milk';
//                    } else if (this.x == '45') {
//                        head = 'Pasturized Milk';
//                    } else if (this.x == '90') {
//                        head = 'UHT Milk';
//                    } else if (this.x == '135') {
//                        head = 'Flavoured Milk';
//                    } else if (this.x == '180') {
//                        head = 'Juice';
//                    } else if (this.x == '225') {
//                        head = 'Curd';
//                    } else if (this.x == '270') {
//                        head = 'Ghee';
//                    } else {
//                        head = 'Butter';
//                    }
//                    var s = head;
//                    $.each(this.points, function (i, point) {
//                        s += '<br/><span style="color:' + point.series.color + '">\u25CF</span> ' + point.series.name + ': <b>' + point.y + '%</b>';
//                    });
//
//                    return s;
//                },
//                shared: true,
//                useHTML: true
//            },
//            plotOptions: {
//                series: {
//                    pointStart: 0,
//                    pointInterval: 45
//                },
//                column: {
//                    pointPadding: 0,
//                    groupPadding: 0
//                }
//            },
//
//            series: [{
//                type: 'column',
//                name: 'Total Leftover',
//                data: [10, 17, 13, 8, 16, 12, 15, 9]
////                pointPlacement: 'between'
//            }, {
//                type: 'line',
//                name: 'Total Profit',
//                data: [12, 21, 17, 10, 11, 16, 6, 7]
//            }, {
//                type: 'area',
//                name: 'Total Return',
//                data: [13, 9, 11, 3, 6, 9, 10, 13]
//            }]
//        });
    });

    function loadDashboard() {
        var dashBoardBusinessDate = $("#dashBoardBusinessDate").val();
        portletAjax.init(dashBoardBusinessDate)
    }

    function displayNotification() {
        $("#dialog:ui-dialog").dialog("destroy");

        var messageVal1 = 1;
        var messageVal2 = 2;
        var messageVal3 = 3;

        $("#addMessage1").html("Add a message here: " + messageVal1);
        $("#addMessage2").html("Add a message here: " + messageVal2);
        $("#addMessage3").html("Add a message here: " + messageVal3);

        if (isDisplayed == 'false') {
            $("#dialog-user-notification").dialog({
                resizable: false,
                height: 200,
                modal: true,
                title: 'User Notification',
                buttons: {
                    'Ok': function () {
                        $(this).dialog('close');
                    }
                }
            });
        }
    }

    function loadHighchart(){
        jQuery.ajax({
            url: "${resource(dir:'home', file:'highchartdata')}",
            success: function (data, textStatus) {
                var d1 = data.list1;
                var d2 = data.list2;
                var d3 = data.list3;
                var d4 = data.list4;
                var d5 = data.list5;
                var dataArrayFinal = Array();
                var dataArraySales = Array();
                var dataArrayCollection = Array();
                var dataArrayClaimp = Array();
                var dataArrayClaimc = Array();
                var dataArrayMordernTrade = Array();
                var dataArrayRetail = Array();
                var dataArraySalesPerformance = Array();
                var dataArrayTargetRT = Array();
                var dataArrayAchievementRT = Array();
                for (i = 0; i < d1.length; i++) {
                    dataArrayFinal.push([d1[i].name,d1[i].sales]);
                }
                for (i = 0; i < d1.length; i++) {
                    dataArraySales.push([d1[i].sales]);
                    dataArrayCollection.push([d1[i].collection]);
                }
                for (i = 0; i < d2.length; i++) {
                    dataArrayClaimp.push([d2[i].c]);
                    dataArrayClaimc.push([d2[i].p]);
                }
//                debugger
                for (i = 0; i < d3.length; i++) {
                    if(d3[i].name == "Modern Trade")
                    { dataArrayMordernTrade.push([d3[i].sales]);}
                    if(d3[i].name == "Retail Sales")
                    {dataArrayRetail.push([d3[i].sales]);}

                }
                for (i = 0; i < d4.length; i++) {
//debugger
                    if(d4[i].name == "Retail Sales")
                    {
                        dataArrayTargetRT.push([d4[i].t]);
                        dataArrayAchievementRT.push([d4[i].a]);
                    }

                }
                dataArraySalesPerformance.push([d5[0].target])

                $('#ddd').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type: 'pie'
                    },
                    title: {
                        text: '<b>Year-to-Date(YTD) Sale  (R & D)</b>'
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true
                        }
                    },
                    series: [{
                        name: "Percentage",
                        colorByPoint: true,
                        data: dataArrayFinal
                    }]
                });


                $('#div31').highcharts({
                    title: {
                        text: '<b>Sale vs Collection</b>'
                    },
                    xAxis: {
                        title: {
                            text: "Sales Channel"
                        },
                        categories: ['Modern Trade', 'Institutional Sales', 'Retail Sales', 'Other']
                    },
                    yAxis: {
                        title: {
                            text: "Amount"
                        },
                        gridLineWidth: 0
                    },
                    series: [{
                        type: 'column',
                        name: 'Sales',
                        data: dataArraySales
                    },
                        {
                            type: 'spline',
                            name: 'Collection',
                            data: dataArrayCollection,
                            marker: {
                                lineWidth: 2,
                                lineColor: Highcharts.getOptions().colors[3],
                                fillColor: 'white'
                            }
                        }
                    ]
                });

                $('#div21').highcharts({
                    chart: {
                        type: 'spline'
                    },
                    title: {
                        text: '<b>Market Claim</b>'
                    },
                    subtitle: {
                        text: 'Current Month vs Last Month'
                    },
                    xAxis: {
//                type: 'datetime',
                        categories: ['Modern Trade','Institutional Sales','Retail Sales','Other'],
                        labels: {
                            overflow: 'justify'
                        }
                    },
                    yAxis: {
                        title: {
                            text: 'BDT'
                        },
                        minorGridLineWidth: 0,
                        gridLineWidth: 0,
                        alternateGridColor: null
                    },
                    tooltip: {
                        shared: true
                    },
                    plotOptions: {
                        spline: {
                            lineWidth: 4,
                            states: {
                                hover: {
                                    lineWidth: 5
                                }
                            },
                            marker: {
                                enabled: false
                            }
                        }
                    },
                    series: [{
                        name: 'Claim Last Month',
                        data: dataArrayClaimp

                    }, {
                        name: 'Claim This Month',
                        data: dataArrayClaimc
                    }],
                    navigation: {
                        menuItemStyle: {
                            fontSize: '10px'
                        }
                    }
                });

                $('#div41').highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: '<b>Increase and Decrease of Salesman</b>'
                    },
                    subtitle: {
                        text: 'Monthly'
                    },
                    xAxis: {
                        categories: [
                            'Jan',
                            'Feb',
                            'Mar',
                            'Apr',
                            'May',
                            'Jun',
                            'Jul',
                            'Aug',
                            'Sep',
                            'Oct',
                            'Nov',
                            'Dec'
                        ],
                        crosshair: true
                    },
                    yAxis: {
                        min: 0,
                        gridLineWidth: 0,
                        title: {
                            text: ''
                        }
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0
                        }
                    },
                    series: [{
                        name: 'Modern Trade',
                        data: dataArrayMordernTrade

                    }, {
                        name: 'Institutional Sales',
                        data: [3.6, 8.8, 8.5, 3.4, 6.0, 4.5, 5.0, 4.3, 1.2, 3.5, 6.6, 2.3]

                    }, {
                        name: 'Retail Sales',
                        data: dataArrayRetail

                    }, {
                        name: 'Other',
                        data: [2.4, 3.2, 4.5, 9.7, 2.6, 5.5, 7.4, 0.4, 7.6, 9.1, 6.8, 1.1]

                    }]
                });

                $('#div11').highcharts({
                            chart: {
                                type: 'gauge',
                                plotBackgroundColor: null,
                                plotBackgroundImage: null,
                                plotBorderWidth: 0,
                                plotShadow: false
                            },

                            title: {
                                text: '<b>Projected Sales Performance</b>'
                            },

                            subtitle: {
                                text: 'In Millions per Year'
                            },
                            pane: {
                                startAngle: -150,
                                endAngle: 150,
                                background: [{
                                    backgroundColor: {
                                        linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                        stops: [
                                            [0, '#FFF'],
                                            [1, '#333']
                                        ]
                                    },
                                    borderWidth: 0,
                                    outerRadius: '109%'
                                }, {
                                    backgroundColor: {
                                        linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                        stops: [
                                            [0, '#333'],
                                            [1, '#FFF']
                                        ]
                                    },
                                    borderWidth: 1,
                                    outerRadius: '107%'
                                }, {
                                    // default background
                                }, {
                                    backgroundColor: '#DDD',
                                    borderWidth: 0,
                                    outerRadius: '105%',
                                    innerRadius: '103%'
                                }]
                            },

                            // the value axis
                            yAxis: {
                                min: 0,
                                max: 200,

                                minorTickInterval: 'auto',
                                minorTickWidth: 1,
                                minorTickLength: 10,
                                minorTickPosition: 'inside',
                                minorTickColor: '#666',

                                tickPixelInterval: 30,
                                tickWidth: 2,
                                tickPosition: 'inside',
                                tickLength: 10,
                                tickColor: '#666',
                                labels: {
                                    step: 2,
                                    rotation: 'auto'
                                },
                                title: {
                                    text: 'Sales Amount'
                                },
                                plotBands: [{
                                    from: 0,
                                    to: 60,
                                    color: '#DF5353' // green
                                }, {
                                    from: 60,
                                    to: 120,
                                    color: '#DDDF0D' // yellow
                                }, {
                                    from: 120,
                                    to: 200,
                                    color: '#55BF3B' // red
                                }]
                            },

                            series: [{
                                name: 'Projected Sale',
                                data: dataArraySalesPerformance,
                                tooltip: {
                                    valueSuffix: ' Million/Year'
                                }
                            }]

                        }
                );

                $('#div61').highcharts({
                    chart: {
                        type: 'bar'
                    },
                    title: {
                        text: '<b>Target vs Achievement(Current Month)</b>'
                    },
                    subtitle: {
                        text: 'By Channel'
                    },
                    xAxis: {
                        categories: ['Retail Sales','Modern Trade', 'Institutional Sales', 'Others'],
                        title: {
                            text: null
                        }
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '',
                            align: 'high'
                        },
                        labels: {
                            overflow: 'justify'
                        }
                    },
                    tooltip: {
                        valueSuffix: ' taka'
                    },
                    plotOptions: {
                        bar: {
                            dataLabels: {
                                enabled: true
                            }
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'top',
                        x: -40,
                        y: 80,
                        floating: true,
                        borderWidth: 1,
                        backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
                        shadow: true
                    },
                    credits: {
                        enabled: false
                    },
                    series: [{
                        name: 'Target',
                        data: dataArrayTargetRT
                    }, {
                        name: 'Achievement',
                        data: dataArrayAchievementRT
                    }]
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }
</script>

<div id="dialog-user-notification" style="display:none">
    <h2>Please check the followings:</h2>
    <br/>

    <div id="addMessage1"></div>

    <div id="addMessage2"></div>

    <div id="addMessage3"></div>
    <br/>

    <h2>Do you want to continue?</h2>
</div>


%{--<h1>--}%
%{--<span class="headerMain">My Dashboard (${applicationUser?.username}) on</span>--}%
%{--<span class="mar_top8 pad_left5">  &nbsp;<g:textField readonly="readonly" name="dashBoardBusinessDate" id="dashBoardBusinessDate" value="${new java.util.Date().format('dd-MM-yyyy hh:mm aa')}" class="inputBgBlank" style="width:122px;"/>&nbsp;--}%
%{--</span>--}%
%{--</h1>--}%

<div id="portlet_parent">

</div>

%{--<div id="div1" style="float: left; width: 50%;">--}%
%{--</div>--}%

%{--<div id="div6" style="margin-left: 50%;">--}%
%{--</div>--}%

%{--<div id="div2" style="float: left; width: 50%;">--}%
%{--</div>--}%

%{--<div id="div3" style="margin-left: 50%;">--}%
%{--</div>--}%

%{--<div id="div4" style="float: left; width: 50%;">--}%
%{--</div>--}%

%{--<div id="div5" style="margin-left: 50%;">--}%
%{--</div>--}%

<div id="div11" style="float: left; width: 50%;">

</div>

<div id="div61" style="margin-left: 50%;">

</div>
<div id="ddd" style="float: left; width: 50%;">

</div>

<div id="div31" style="margin-left: 50%;">

</div>

<div id="div21" style="float: left; width: 50%;">

</div>

<div id="div41" style="margin-left: 50%;">

</div>
