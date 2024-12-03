function getChartColorsArray(t) {
    if (null !== document.getElementById(t)) {
        t = document.getElementById(t).getAttribute("data-colors");
        if (t)
            return (t = JSON.parse(t)).map(function (t) {
                var e = t.replace(" ", "");
                if (-1 === e.indexOf(",")) {
                    var a = getComputedStyle(document.documentElement).getPropertyValue(e);
                    return a || e;
                }
                t = t.split(",");
                return 2 != t.length ? e : "rgba(" + getComputedStyle(document.documentElement).getPropertyValue(t[0]) + "," + t[1] + ")";
            });
    }
}
// DEPARTMENT WISE TRANSACTION(BAR CHART)//
//var jsonBom = $('#dListORGHidden').val();
var dListORGHiddenVar = document.getElementById("dListORGHidden").value; 
//alert(dListORGHiddenVar);
var dListORGHiddenVarArray = JSON.parse(dListORGHiddenVar);

var dListCountHiddenVal = document.getElementById("dListCountHidden").value;  
//alert(dListCountHiddenVal);
var dListCountHiddenValArry = JSON.parse(dListCountHiddenVal);

var chart,
    chartColumnDistributedColors = getChartColorsArray("column_distributed");
chartColumnDistributedColors &&
    ((options = {
        series: [{ name: "Department",data: dListCountHiddenValArry }],
        chart: { height: 250, type: "bar", events: { click: function (t, e, a) {} } },
        colors: chartColumnDistributedColors,
        plotOptions: { bar: { columnWidth: "40%", distributed: !0 } },
        dataLabels: { enabled: !1 },
        legend: { show: !1 },
        xaxis: {
            categories: dListORGHiddenVarArray,
            labels: { style: { colors: ["#038edc", "#51d28c", "#f7cc53", "#f34e4e", "#564ab1", "#5fd0f3","#B39C4D","#34623F","#1E2F23"], fontSize: "12px" } },
        },
    }),
    (chart = new ApexCharts(document.querySelector("#column_distributed"), options)).render());
var optionsGroup,
    chartGroup,
    chartColumnGroupLabelsColors = getChartColorsArray("column_group_labels");
chartColumnGroupLabelsColors &&
    (dayjs.extend(window.dayjs_plugin_quarterOfYear),
      (chartGroup = new ApexCharts(document.querySelector("#column_group_labels"), optionsGroup)).render());

/* line chart(HOURS WISE COUNT) */

var dListHoursHiddenval = document.getElementById("dListHoursHidden").value; 
//alert(dListHoursHiddenval);
var dListHoursHiddenArray = JSON.parse(dListHoursHiddenval);

var dListHourCountHiddenval = document.getElementById("dListHourCountHidden").value; 
//alert(dListHourCountHiddenval);
var dListHourCountHiddenArray = JSON.parse(dListHourCountHiddenval);



    var steplineChartColors = getChartColorsArray("line_chart_stepline");
steplineChartColors &&
    ((options = {
        series: [{ data: dListHourCountHiddenArray }],
        chart: { type: "line", height: 350, toolbar: { show: !1 } },
        xaxis: {
            type: "chart",
            categories: dListHoursHiddenArray,
        },
        stroke: { curve: "straight" },
        dataLabels: { enabled: !1 },
        title: { text: "", align: "left", style: { fontWeight: 500 } },
        markers: { hover: { sizeOffset: 4 } },
        colors: steplineChartColors,
    }),
    (chart = new ApexCharts(document.querySelector("#line_chart_stepline"), options)).render());

/* compirsion chart(SUCCESS FAIL GRAPH) */

//var dListSForgHiddenval = document.getElementById("dListSForgHidden").value; 
//alert(dListSForgHiddenVal);
//var dListSForgHiddenArray = JSON.parse(dListSForgHiddenVal);

var dListtSuccessHiddenVal = document.getElementById("dListtSuccessHidden").value;  
//alert(dListtSuccessHiddenVal);
var dListtSuccessHiddenArray = JSON.parse(dListtSuccessHiddenVal);

var dListFailedHiddenVal = document.getElementById("dListFailedHidden").value;  
//alert(dListFailedHiddenVal);
var dListFailedHiddenArray = JSON.parse(dListFailedHiddenVal);

var areachartSplineColors = getChartColorsArray("area_chart_spline");
areachartSplineColors &&
    ((options = {
        series: [
            { name: "Success", data: dListtSuccessHiddenArray},//[31, 40, 28, 51, 42, 109, 100,50,60,80,90,58,10,60] },
            { name: "Fail", data: dListFailedHiddenArray},//[11, 32, 45, 32, 34, 52, 41, 40, 28, 51, 42, 109, 100,60] },
        ],
        chart: { height: 350, type: "area", toolbar: { show: !1 } },
        dataLabels: { enabled: !1 },
        stroke: { curve: "smooth" },
        colors: areachartSplineColors,
        xaxis: {
            type: "chart",
            categories: [["SSO"], ["ePDS"], ["RISL"],["RAJ", "e-VAULT"], ["Secondary", "Education"], ["ECO and", "Statistics"], ["Medical", "and Health"], ["e-Mitra"], ["Social", "Justice Dept"], ["Electrical", "Inspectorate"],["Drug", "Controller"],["Settlement", "Dept"],["SCRB"],["RKCL"]],
        },
      //  tooltip: { x: { format: "dd/MM/yy HH:mm" } },
    }),
    (chart = new ApexCharts(document.querySelector("#area_chart_spline"), options)).render());