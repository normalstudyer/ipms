$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ips/positionlabel/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50,hidden: true, key: true },
			{ label: '标签编号', name: 'tagId', index: 'tag_id', width: 60 },
			// { label: '标签名称', name: 'targetName', index: 'target_name', width: 80 },
			{ label: '标签类型', name: 'targetType', index: 'target_type',align:"center", width: 80,
                formatter: function(value, options, row){
                    if (value==0)
                    	return "作业人员";
                    else if (value==1)
                        return "作业车辆";
                    else if (value==2)
                        return "货物";
                    else if (value==3)
                        return "其它";
                } },
            { label: '最后位置x', name: 'x', index: 'x', width: 80 },
            { label: '最后位置y', name: 'y', index: 'y', width: 80 },
            { label: '电量（%）', name: 'battery', index: 'battery', width: 50 },
            { label: '更新时间', name: 'updateDate', index: 'update_date', width: 100 },
            { label: '状态', name: 'enable', index: 'enable',align:"center", width: 50,
                formatter: function(value, options, row){
                    return value == 0 ?
                        '<span class="label label-warning">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                } },
            { label: '操作', width: 80 ,
                formatter: function(value, options, row){
            	//console.log(row);
                    return  '<a class="btn btn-link btn-xs"  onclick="vm.update('+row.id+')"></i>编辑</a>';
						// +'<a class="btn btn-link btn-xs"  onclick="vm.record('+row.tagId+')"></i>历史轨迹</a>';
                }}
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
		positionLabel: {},
        searchParams:{
            tagId:"",
            targetName:"",
            targetType:""
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.positionLabel = {targetType:"",enable:1};
		},
		update: function (id) {
			// var id = $(this).attr("data-id");
			console.log(id);
            // console.log(event);
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
        record: function (tag_id) {
            //这里是导出的业务代码
            // console.log(tag_id);

        },
		saveOrUpdate: function (event) {
			var url = vm.positionLabel.id == null ? "ips/positionlabel/save" : "ips/positionlabel/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.positionLabel),
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
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ips/positionlabel/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "ips/positionlabel/info/"+id, function(r){
                vm.positionLabel = r.positionLabel;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
				postData:vm.searchParams
            }).trigger("reloadGrid");
		},
        derive: function (event) {
            //这里是导出的业务代码
            // vm.labelWin.show();
        }
	}
});