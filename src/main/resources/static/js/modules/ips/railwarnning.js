$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ips/railwarnning/list',
        datatype: "json",
        colModel: [			
			{ label: '编号', name: 'id', index: 'id', width: 50, key: true },
			{ label: '围栏名称', name: 'railName', index: 'rail_name', width: 80 },
			{ label: '人员名称', name: 'name', index: 'target_name', width: 80 },
			{ label: '触发时间', name: 'triggerDate', index: 'trigger_date', width: 80 }, 			
			{ label: '是否进入', name: 'triggerType', index: 'trigger_type', width: 80,
			   formatter:function (value,index,row) {
                   return value === 1 ?
                       '<span class="label label-danger">进入</span>' :
                       '<span class="label label-success">离开</span>';
               }},
			{ label: '信息描述', name: 'info', index: 'info', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		showList1:false,
		showEcharts:false,
		title: null,
		railWarnning: {},
		times:{
			start:null,
			end:null
		},
		warn:{
			railname:null,
			name:null,
            stime:null,
            etime:null
		},
		//url:'http://www.baidu.com'
		url:'/ips/railwarnning/test'
	},
	methods: {
		query: function () {
			vm.reload();
		},

		saveOrUpdate: function (event) {
			var url = vm.railWarnning.id == null ? "ips/railwarnning/save" : "ips/railwarnning/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.railWarnning),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		export_excel:function (event) {
            var url = baseURL+"api/export_excel?timestart="+ vm.warn.stime+"&timeend="+vm.warn.etime+"&railname="+vm.warn.railname+"&name="+vm.warn.name;
            // window.open(url,"_blank");
            window.location.href = url;
        },
		show_echarts:function (event) {
            vm.showEcharts = true,
			vm.showList = false,
			vm.showList1 = false
        },
		getInfo: function(id){
			$.get(baseURL + "ips/railwarnning/info/"+id, function(r){
                vm.railWarnning = r.railWarnning;
            });
		},
		reload: function (event) {
			vm.showList = true;
            vm.showList1 = false;
            vm.showEcharts = false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{'timestart':vm.warn.stime,'timeend':vm.warn.etime,'railname':vm.warn.railname,'name':vm.warn.name},
                page:page
            }).trigger("reloadGrid");
		},
        reset:function () {
            vm.warn.stime=null;
            vm.warn.etime=null;
            vm.warn.railname=null;
            vm.warn.name=null;
        }
	}
});

//获取初始时间和结束时间
$(function () {
    $("#start").datetimepicker({
        format: "yyyy-MM-dd",
		minView: 2,
        autoclose:true,
		language:"zh-CN"
    }).on('changeDate', function(ev) {
        vm.warn.stime = ev.date.format("yyyy-MM-dd");
    });
    $("#end").datetimepicker({
        format: "yyyy-MM-dd",
        minView: 2,
        autoclose:true,
        language:"zh-CN"
    }).on('changeDate', function(ev) {
        vm.warn.etime = ev.date.format("yyyy-MM-dd");
    });
})
//获取柱状图
$(function () {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echarts_railwarnning'));

    //指定图表的配置项和数据
    option = {
        color: ['#003366', '#e5323e'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
		title:{
            text:"每个围栏下的进出次数"
		},
        legend: {
            data: ['进入次数', '出去次数'],
			right:50
        },
        /*toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },*/
        calculable: true,
        xAxis: [
            {
                type: 'category',
                axisTick: {show: false},
                data:[]
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '进入次数',
                type: 'bar',
                barGap: 0,
                label: {
                    normal: {
                        show: true,
                        //formatter:'{c}\n',
                        position: 'top'
                    }
                },
                data: []
            },
            {
                name: '出去次数',
                type: 'bar',
                label: {
                    normal: {
                        show: true,
                        //formatter:'{c}\n',
                        position: 'top'
                    }
                },
                data: []
            }
        ]
    };
    //ajax从后台获取数据
    var url = "ips/railwarnning/showecharts";
    $.ajax({
        type: "POST",
        url: baseURL + url,
        contentType: "application/json",
        data: JSON.stringify({
			id:'1'
		}),
        success: function(r){
        	console.log(r.list);
            if(r.list){
                //myChart.hideLoading();
                myChart.setOption({
                    xAxis:{
                        data:r.list[0].railName
                        //data:['经营部自提','江苏零号','吉安川吉']
                    },
                    series:
                    //for(var i=0;i<data.messageAndData.data[0].COUNT.length;i++){
                        [{
                            name:"进入次数",
                            data:r.list[0].inNumbers

                        },
                            {
                                name:"出去次数",
                                data:r.list[0].outNumbers
                            }]
                });
            }
            else{
                alert(r.msg);
            }
        }
    });
    myChart.setOption(option);
})