      
function  hours2()
{

var dListTimeVar = document.getElementById("dListTime").value; 
 dListTimeVar = dListTimeVar.replaceAll(':','');

 var dListTimeVarArray = JSON.parse(dListTimeVar); 


var dListError336FVar = document.getElementById("dListError336F").value; 
 var dListError336FVarArray = JSON.parse(dListError336FVar); 

var dListError337FVar = document.getElementById("dListError337F").value;
 var dListError337FVarArray = JSON.parse(dListError337FVar); 

var dListError939FVar = document.getElementById("dListError939F").value;
 var dListError939FVarArray = JSON.parse(dListError939FVar);
 
              var chart=  $('#container').highcharts({
                chart: {
                    zoomType: 'xy'
                },
                colors: ['#66b3ff', '#ff9966', '#ff5050', '#884EA0', '#ADD8E6', 
           '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1',"#FF00FF","#154360","#27AE60"],
                
                title: {
                    text: 'Hours Wise Error Response Report'
                },
               
                
                xAxis: [{
                    categories: dListTimeVarArray  <!--Minutes-->
                }],

                yAxis: [{ // Primary yAxis
                    title: {
                text: 'Average Response Time'
            },
                    labels: {

                        format: '{value}',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    },
                    
                }, { // Secondary yAxis
                    title: {
                        text: '',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                  
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },
               
                plotOptions: {
                    column: {
                        stacking: 'normal'
                    }
                },
                series: [
                    {
                    name: '336',
                    type: 'column',
                    
                    yAxis: 1,
                    data: dListError336FVarArray, <!--336 error count-->
                   

                }, 
                  {
                      name: '337',
                    type: 'column',
                      
                    yAxis: 1,
                   
                     data: dListError337FVarArray, <!-- 337 error count -->
                },{
                    name: '939',
                    yAxis: 1,
                    type: 'column',
                   
                    data: dListError939FVarArray
                },
                  {
                    name: 'Average Response Time',
                    type: 'spline',
                     
                    data: [0.2,0.3,0.1,0.2,0.1, 0.2,0.7,0.3,0.2,0.1,0.2,0.1,0.2,0.3,0.1,0.2,0.1, 0.2,0.7,0.3,0.2,0.1], <!-- Average Time -->
                    
                }]
            });
}

$( document ).ready(function() {
          var chart=  $('#container').highcharts({
                chart: {
                    zoomType: 'xy'
                },
                colors: ['#66b3ff', '#ff9966', '#ff5050', '#884EA0', '#ADD8E6', 
           '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1',"#FF00FF","#154360","#27AE60"],
                
                title: {
                    text: 'Hours Wise Error Response Report'
                },
               
                xAxis: [{
                    categories: [5,10,15,20,25,30,35,40,45,55,60]
                }],

                yAxis: [{ // Primary yAxis
                    title: {
                text: 'Average Response Time'
            },
                    labels: {

                        format: '{value}',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    },
                    
                }, { // Secondary yAxis
                    title: {
                        text: '',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                  
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },
               
                plotOptions: {
                    column: {
                        stacking: 'normal'
                    }
                },
                series: [
                    {
                    name: '336',
                    type: 'column',
                    
                    yAxis: 1,
                    data: [2, 10, 3,9,14, 3, 10, 6,5,20, 20],
                   

                }, 
                  {
                      name: '337',
                    type: 'column',
                      
                    yAxis: 1,
                   
                     data: [12, 5, 10,6,15, 2, 36,9,15,10, 3],
                },{
                    name: '939',
                    yAxis: 1,
                    type: 'column',
                   
                    data: [20,5, 0, 5, 2, 3,7, 30, 9, 10,15],
                },
                  {
                    name: 'Average Response Time',
                    type: 'spline',
                     
                    data: [0.2,0.3,0.1,0.2,0.1, 0.2,0.7,0.3,0.2,0.1,0.2],
                    
                }]
            });
        });



$('#select').change(function(){
var slstestedVal = $(this).children("option:selected").val();
  $.ajax('/', {
    type: 'POST',  // http method
    data: { myData: slstestedVal },  // data to submit
    success: function () {
        hours2();
    },
    error: function (jqXhr, textStatus, errorMessage) {
            $('p').append('Error' + errorMessage);
    }
});
});



