/*$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ips/mapcatalog/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '父级编号', name: 'pid', index: 'pid', width: 80 }, 			
			{ label: '目录名称', name: 'catalogName', index: 'catalog_name', width: 80 }, 			
			{ label: '目录说明描述', name: 'catalogRemarks', index: 'catalog_remarks', width: 80 }, 			
			{ label: '1：正常，0：已删除', name: 'isdel', index: 'isdel', width: 80 }			
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
});*/

//修改后的地图目录列表
var Dept = {
    id: "deptTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},
        /*{title: '父级编号', field: 'pid', align: 'center', valign: 'middle', sortable: true, width: '100px'},*/
        {title: '目录名称', field: 'catalogName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '目录说明描述', field: 'catalogRemarks', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        /*{title: '是否正常', field: 'isdel', align: 'center', valign: 'middle', sortable: true, width: '100px',formatter:function(value,option,row){
            return value === 0 ?
                '<span class="label label-danger">禁用</span>' :
                '<span class="label label-success">正常</span>';
		}}*/
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}]

	return columns;
};


function getMapId () {
    var selected = $('#deptTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}


$(function () {
    $.get(baseURL + "ips/mapcatalog/info", function(r){
        var colunms = Dept.initColumn();
        var table = new TreeTable(Dept.id, baseURL + "ips/mapcatalog/list", colunms);
        table.setRootCodeValue(r.mapId);
        table.setExpandColumn(2);
        table.setIdField("id");
        table.setCodeField("id");
        table.setParentCodeField("pid");
        table.setExpandAll(false);
        table.init();
        Dept.table = table;
    });
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		//mapCatalog: {},
        mapCatalog:{
            pid:null,
            parentName:null,
            orderNum:0
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			//vm.mapCatalog = {};
            vm.mapCatalog = {pid:0,parentName:null,orderNum:0};
            vm.getMap();
		},
		update: function (event) {
			var id = getMapId ();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
            var a = 0;
            $('.form-control').each(function(){
                if($(this).val() == ""){
                    a += 1;
                }
            })
            if(a >= 1){
            	alert("您有必填项没有填写,无法提交");
            }else{
			  var url = vm.mapCatalog.id == null ? "ips/mapcatalog/save" : "ips/mapcatalog/update";
			  $.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mapCatalog),
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
            };
		},
		del: function (event) {
			var ids = getMapId ();
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ips/mapcatalog/delete",
					data:"ids="+ids,
				    /*data: JSON.stringify({
				    	"ids":ids
                    }),*/
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								/*$("#jqGrid").trigger("reloadGrid");*/
                                vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "ips/mapcatalog/info/"+id, function(r){

                vm.mapCatalog = r.mapCatalog;
                console.log("vm.mapCatalog"+vm.mapCatalog.pid);
                vm.getMap();
            });
		},
        getMap: function(){
            //加载地图树
            $.get(baseURL + "ips/mapcatalog/select", function(r){

                ztree = $.fn.zTree.init($("#mapTree"), setting, r.mapList);
                var node = ztree.getNodeByParam("id", vm.mapCatalog.pid);

                ztree.selectNode(node);

                vm.mapCatalog.pid = node.id;
            })
        },
        mapTree: function(){
            layer.open({
                type: 1,
                offset: '30px',
                skin: 'layui-layer-molv',
                title: "选择地图",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#mapLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级地图
                    vm.mapCatalog.pid = node[0].id;
                    vm.mapCatalog.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
		reload: function (event) {
			vm.showList = true;
			/*var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");*/
            Dept.table.refresh();
		}
	}
});
//当input框没有内容时会有提示
$(function(){
    $('.form-control').each(function (index,item) {
        $(this).blur(function () {

            if($(this).val() == ""){

                $(this).css("border-color","#CC120F");
                layer.tips("此为必填项",this);
            }else {
                $(this).css("border-color","#ccc");
            }
        })
    })

})