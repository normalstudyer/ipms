$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ips/positioninfohistory/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50,hidden: true, key: true },
			// { label: '数据源名称', name: 'sourceName', index: 'source_name', width: 80 },
			// { label: 'TP:上传 Tag 位置信息; TD:上传 Tag 自定义数据', name: 'formatType', index: 'format_type', width: 80 },
			{ label: '标签编号', name: 'tagId', index: 'tag_id', width: 80 },
			// { label: '二进制', name: 'tagIdFormat', index: 'tag_id_format', width: 80 },
			{ label: '坐标x', name: 'x', index: 'x', width: 80 }, 			
			{ label: '坐标y', name: 'y', index: 'y', width: 80 }, 			
			// { label: '坐标z', name: 'z', index: 'z', width: 80 },
			{ label: '电量', name: 'battery', index: 'battery', width: 80 }, 			
			{ label: '更新日期', name: 'updateDate', index: 'update_date', width: 80 },
			// { label: '', name: 'blinkId', index: 'blink_id', width: 80 },
			// { label: '', name: 'qualityIndicator', index: 'quality_indicator', width: 80 },
			// { label: '主基站ID', name: 'payload', index: 'payload', width: 80 },
			{ label: '信息', name: 'msg', index: 'msg', width: 80 }			
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
		title: null,
	},
	methods: {
		query: function () {
			vm.reload();
		},

		getInfo: function(id){
			$.get(baseURL + "ips/positioninfohistory/info/"+id, function(r){
                vm.positionInfoHistory = r.positionInfoHistory;
            });
		},
		reload: function (event) {
			vm.showList = true;

			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});