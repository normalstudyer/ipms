var setting;
$(function () {
    setting = {
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

        },
        callback:{ //回调函数,给每个节点绑定事件  
            onClick : function(event, treeId, treeNode) {
                vm.zTreeOnClick();
            }

        }
    };
    vm.getMap();
    vm.mapCatalog = {parentName:null,pid:0,orderNum:0};
    vm.mapTree();
    $("#jqGrid").jqGrid({
        url: baseURL + 'ips/mapinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50,hidden: true, key: true },
			// { label: '地图目录编号', name: 'catalogId', index: 'catalog_id', width: 80 },
			{ label: '地图名称', name: 'mapName', index: 'map_name', width: 80 },
			// { label: '地图图片地址', name: 'mapUrl', index: 'map_url', width: 80 },
			{ label: '原点坐标x', name: 'originX', index: 'origin_x', width: 50 },
			{ label: '原点坐标y', name: 'originY', index: 'origin_y', width: 50 },
			{ label: '区域长度(mm)', name: 'aeraLength', index: 'aera_length', width: 50 },
			{ label: '区域宽度(mm)', name: 'aeraWidth', index: 'aera_width', width: 50 },
			{ label: '图片高度(px)', name: 'imageHeight', index: 'image_height', width: 50 },
			{ label: '图片宽度(px)', name: 'imageWidth', index: 'image_width', width: 50 },
			{ label: '最大级别', name: 'zoomMax', index: 'zoom_max', width: 50 },
			{ label: '最小级别', name: 'zoomMin', index: 'zoom_min', width: 50 }
        ],
		viewrecords: true,
        //float:"right",

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
            /*$("#jqGrid").closest("#gbox_jqGrid").css({ "margin-left" : "200px" })
            $("#mapTree").closest("#layui-layer1").css({ "margin-top": "-465px"});
            $("#mapTree").closest("#layui-layer1").css({ "border": "1px solid #ddd"});
            $("#mapTree").closest("#layui-layer1").css({ "border-radius": "3px"});*/
			//$("#jqGrid").jqGrid("setGridWidth", 900)
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		mapInfo: {},
        q:{
            mapName: null,
            mapUrl:null
        },
        labelWin: new $.labelWin({callback:function (data) {
            //弹框选择数据后，点击确认后的回调函数
            vm.persons.labelId=data.tagId;
            $("#labelIdinput").val(data.targetType);

        }}),
        searchParams:{
            tagId:"",
            targetName:"",
            targetType:""
        }
	},
	methods: {
        getMap: function(){
            //加载地图树
            $.get(baseURL + "ips/mapcatalog/select", function(r){

                ztree = $.fn.zTree.init($("#mapTree"), setting, r.mapList);
                var node = ztree.getNodeByParam("id", vm.mapCatalog.pid);

                ztree.selectNode(node);

                vm.mapCatalog.pid = node.id;
            })
        },
        mapTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                float: "left",
                skin: 'layui-layer-molv',
                title: false,
                area: ['190px', '465px'],
                shade: 0,
                shadeClose: true,
                content: jQuery("#mapLayer"),
				/*  btn: ['确定', '取消'],*/
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级地图
                    vm.mapCatalog.pid = node[0].id;
                    vm.mapCatalog.parentName = node[0].name;

                    layer.close(index);
                },

            });
            $("#mapLayer").closest("#layui-layer1").css({"left": "0px"});
            $("#mapLayer").closest("#layui-layer1").removeClass("layui-layer");
            $("#mapLayer").closest("#layui-layer1").find(".layui-layer-setwin").remove();

        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
            $("#layui-layer1").hide();
			vm.mapInfo = {};
            $("#test-upload-normal-img").attr("src","");
            vm.mapInfo.catalogId=ztree.getSelectedNodes()[0].id;
		},
		update: function (event) {
			var id = getSelectedRow();
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
			}else {
                var url = vm.mapInfo.id == null ? "ips/mapinfo/save" : "ips/mapinfo/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    /*contentType: "application/json",
                    data: JSON.stringify(vm.mapInfo),*/
                    data:vm.mapInfo,
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            };
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "ips/mapinfo/delete",
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
			$.get(baseURL + "ips/mapinfo/info/"+id, function(r){
                vm.mapInfo = r.mapInfo;
                console.log(r.mapInfo.mapUrl);
                var path = r.mapInfo.mapUrl;
                var i = path.lastIndexOf("/");
                var relpath = path.substring(i);
                $("#test-upload-normal-img").attr("src",baseURL+relpath);
            });
		},
		reload: function (event) {
            $("#layui-layer1").show();
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'mapName': vm.q.mapName},
                page:page
            }).trigger("reloadGrid");
           
		},
        reloadTree: function (event) {
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'id': ztree.getSelectedNodes()[0].id},
                page:page
            }).trigger("reloadGrid");
        },
        select: function (event) {
            //这里是导出的业务代码
            alert("选择")
        },
        zTreeOnClick: function () {
            vm.reloadTree();
        }
	}
});
//当input框没有内容时会有提示
$(function(){
	var a = document.getElementsByClassName('form-control');
	var size = a.length;
    
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

//上传图片
layui.use('upload',function() {
    var $ = layui.jquery
        ,upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#selectPic'
        ,url:baseURL+"ips/mapinfo/preview"
        ,data:{id:'1'}
        ,type:'POST'
        ,contentType:"application/json"
        /*,auto:false
        ,bindAction: '#commit'*/
        ,before:function(obj){

                //预读本地文件实例
                obj.preview(function (index,file,result) {
                    $("#test-upload-normal-img").attr("src",result);
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        var data = e.target.result;
                        console.log("data::"+data);
                        //加载图片获取图片真实宽度和高度
                        var image = new Image();
                        image.onload=function(){
                            var width = image.width;
                            var height = image.height;
                            vm.mapInfo.imageWidth=width;
                            vm.mapInfo.imageHeight=height;
                        };
                        image.src = data;
                    };
                    reader.readAsDataURL(file);
                    /*vm.mapInfo.mapUrl = file.name;
                    $('#picaddress').val(file.name);*/
                });
            }
        ,done:function(res){
            console.log(res);
            console.log("身份证:"+res.ret.result_list[0].data.id);
            $("#test-upload-demoText").html(res.ret.result_list[0].data.id);
            $('#picaddress').val(res.url);
            vm.mapInfo.mapUrl = res.url;
        }

    })
})
/*
function getObjectURL(file) {
    var url = null;
    if(window.createObjectURL!=undefined){
        url = window.createObjectURL(file);
    }else if(window.URL!=undefined){
        url = window.URL.createObjectURL(file);
    }else if(window.webkitURL!=undefined){
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}*/
$(function () {
    layer.photos({
        photos:".layui-upload-list",
        anim:0
    })
})