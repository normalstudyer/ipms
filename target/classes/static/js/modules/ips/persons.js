var setting;
$(function () {
     setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "deptId",
                pIdKey: "parentId",
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

    vm.getDept();
    vm.dept = {parentName:null,parentId:0,orderNum:0};
    // vm.deptTree();
    $("#jqGrid").jqGrid({
        url: baseURL + 'ips/persons/list',
        datatype: "json",
        colModel: [
			{ label: '姓名', name: 'name', index: 'name', width: 50 },
            { label: '部门', name: 'deptName', index: 'deptName', width: 50 },
            {label: '性别',  name: 'sex', index: 'sex', width: 50,formatter:function(value,option,row){
                return value === '0' ?
                    '<span class="label label-danger">男</span>' :
                    '<span class="label label-success">女</span>';
            }},
			{ label: '民族', name: 'nation', index: 'nation', width: 50 },
			{ label: '出生日期', name: 'birth', index: 'birth', width: 50 },
			{ label: '职位', name: 'job', index: 'job', width: 50 },
			{ label: '工号', name: 'employeeNumber', index: 'employee_number', width: 50 },
			{ label: '联系方式', name: 'phone', index: 'phone', width: 50 },
			{ label: '家庭住址', name: 'address', index: 'address', width: 50 },
			{ label: '标签编号', name: 'labelId', index: 'label_id', width: 50 },
            { label: '历史轨迹', width: 50 ,name: 'id',
                formatter: function(value, options, row){
                    return  '<a class="btn btn-link btn-xs"  onclick="vm.record('+row.labelId+')"></i>查看</a>';
                }}

        ],
		viewrecords: true,
        height: 385,
		// float:"right",
        // width:1400,
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
            // $("#jqGrid").closest("#gbox_jqGrid").css({ "margin-left" : "200px" })

            // $("#jqGrid").jqGrid("setGridWidth", 1450)

        }


    });


});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            job: null,
            employeeNumber: null,
            name:null
        },
		showList: true,
		title: null,
		persons: {},
        labelWin: new $.labelWin({callback:function (data) {
            //弹框选择数据后，点击确认后的回调函数
            vm.persons.labelId=data.tagId;
            $("#labelIdinput").val(data.targetType);
            console.log(data);
        }}),
        recordWin: new $.recordWin(),
        searchParams:{
            tagId:"",
            targetName:"",
            targetType:""
        },
        railWin: new $.railWin({callback:function (data) {
            //弹框选择数据后，点击确认后的回调函数
            $.each(data ,function (index,value) {
                if(index!=(data.length)) {
                    data[index]["railId"] =data[index].id;
                    if(index==0){
                        vm.persons.rail=data[index].name;
                    }else {

                        vm.persons.rail =vm.persons.rail+ ","+ data[index].name;
                    }
                }
            })
            $("#railinput").val(vm.persons.rail);
            vm.persons.railcopy=data;
            console.log(data);
        }})

	},
	methods: {
        getDept: function () {
            //加载部门树
            $.get(baseURL + "sys/dept/select", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.deptList);
                var node = ztree.getNodeByParam("deptId", vm.dept.parentId);
                ztree.selectNode(node);
                ztree.expandAll(true);
               /* vm.dept.parentName = node.name;*/
            })
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            $("#layui-layer1").hide();

            vm.persons = {};

            vm.persons.deptId=ztree.getSelectedNodes()[0].deptId;
            vm.persons.sex=0;
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.persons.id == null ? "ips/persons/save" : "ips/persons/update";

            var b=true;
            $('.form-control-tiankong').each(function(){
                if($(this).val() == ""){
                   b=false;
                }
            })
            vm.persons.rail=vm.persons.railcopy;
            if(!b){
                alert("您有必填项没有填写,无法提交");
                return;k
             }
            vm.persons.labelId=vm.persons.id==null? vm.persons.labelId:vm.persons.labelId+","+vm.persons.labelIdcopy
           if(b) {
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.persons),
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
            }
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "ips/persons/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        exportpersion: function () {
            var deptId = ztree.getSelectedNodes()[0] == undefined ? "" : ztree.getSelectedNodes()[0].deptId;
            vm.q.employeeNumber=vm.q.employeeNumber==null?"":vm.q.employeeNumber;
            vm.q.job=vm.q.job==null?"":vm.q.job;
            vm.q.name=vm.q.name==null?"":vm.q.name;
            window.location.href=baseURL+ "ips/persons/export?deptId="+deptId+"&employeeNumber="+vm.q.employeeNumber+"&job="+vm.q.job+"&name="+vm.q.name;
        },
        getInfo: function (id) {
            $.get(baseURL + "ips/persons/info/" + id, function (r) {
                vm.persons = r.persons;
                vm.persons.railcopy=vm.persons.rail;//赋值保存
                vm.persons.labelIdcopy=vm.persons.labelId;//赋值保存
               var  value;
                $.each(vm.persons.rail ,function (index,val) {
                    if(index!=(vm.persons.rail.length)) {
                        if(index==0){
                            value=vm.persons.rail[index].railName;
                        }else {

                            value= value+ ","+ vm.persons.rail[index].railName;
                        }
                    }
                })

                vm.persons.rail=value;
            });
        },
        reload: function (event) {
            $("#layui-layer1").show();
            vm.showList = true;

            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData:{'employeeNumber':vm.q.employeeNumber,'job':vm.q.job,'name':vm.q.name},
                page: page
            }).trigger("reloadGrid");
        },
        reloadTree: function (event) {
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'deptId': ztree.getSelectedNodes()[0].deptId},
                page:page
            }).trigger("reloadGrid");
        },
        selelabel: function (event) {
            //这里是导出的业务代码
            vm.labelWin.show();
        },
        select: function (event) {
            //这里是导出的业务代码
            alert("选择")
        },
        zTreeOnClick: function () {
          vm.reloadTree();
        },
        selectrail: function (event) {
            //选择预警配置
            vm.railWin.show();
        },
        record: function (tag_id) {
            //这里是历史轨迹的业务代码
            vm.recordWin.show(tag_id);
        }
    }

});
$(function(){
    $('.form-control-tiankong').each(function (index,item) {
        $(this).blur(function () {

            if($(this).val() == ""){

                $(this).css("border-color","#CC120F");
                layer.tips("此为必填项",this);
            }else {
                $(this).css("border-color","#ccc");
            }
        })
    })
    layui.use('upload',function() {
        var $ = layui.jquery
            ,upload = layui.upload;
        //执行实例
        var uploadInst = upload.render({
            elem: '#selectPic'
            ,url:baseURL+"ips/mapinfo/preview"
            ,data:{id:'1'}

            /*,auto:false
             ,bindAction: '#commit'*/
            ,before:function(obj){

                //预读本地文件实例
                obj.preview(function (index,file,result) {
                    $("#test-upload-normal-img").attr("src",result);
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        var data = e.target.result;
                        //加载图片获取图片真实宽度和高度
                        var image = new Image();
                        image.onload=function(){
                            var width = image.width;
                            var height = image.height;
                            vm.persons.photoUrl=width;
                            vm.persons.photoUrl=height;
                        };
                        image.src= data;
                    };
                    reader.readAsDataURL(file);
                    /*vm.mapInfo.mapUrl = file.name;
                     $('#picaddress').val(file.name);*/
                });
            }
            ,done:function(res){
                console.log(res);
                $('#picaddress').val(res.url);
                vm.persons.photoUrl = res.url;
            }

        })
    })
})

$(function () {
    layer.photos({
        photos:".layui-upload-list",
        anim:0
    })
})