/**
 * 描述：【标签选择】公共弹框，依赖：JQuery、jqgrid、layer弹框
 * 作者：adams
 * 日期：2018年9月28日
 */
;
(function($) {
	$.labelWin = function(params) {
		var that = this;		
		params = $.extend({
			multicheck: false,//是否允许多选 默认false
			callback: function() {} //确定后的回掉函数
		}, params);


		/**
		 * 检索参数
		 */
		that.queryParams={
            targetType:"",
            tagId:""
		};
		
		/**
		 * 列表检索
		 * @param {Object} searchparams
		 */
		that.query=function (searchparams){
			searchparams = $.extend(that.queryParams, searchparams);
			console.log(searchparams);
			parent.$("#winGrid").jqGrid('setGridParam',{ 
                postData:searchparams,
                page:1
            }).trigger("reloadGrid");
		};

		that.show = function() {
			// var type = vm.formData.sourceListType;
			var gridHtml = [];
            serverUrl = baseURL + 'ips/positionlabel/list?isuse=0'; //数据接口url

		
			gridHtml.push('<div style="padding:10px">');
			gridHtml.push('	<div class="form-inline" style="padding-bottom: 10px;">');
			gridHtml.push('		<div class="form-group form-group-sm">');
			gridHtml.push('			<label>标签类型：</label>');
			gridHtml.push('			<select class="form-control" id="winSelType">');
			gridHtml.push('				<option value="">--请选择--</option>');
			gridHtml.push('				<option value="0">人员</option>');
			gridHtml.push('				<option value="1">车辆</option>');
            gridHtml.push('				<option value="2">固定资产</option>');
            gridHtml.push('				<option value="3">其它</option>');
			gridHtml.push('			</select>');
			gridHtml.push('		</div>');
			gridHtml.push('		<div class="form-group form-group-sm">');
            gridHtml.push('			<label>编号：</label>');
			gridHtml.push('			<input type="text" class="form-control" id="winSearchVal">');
			gridHtml.push('		</div>');
			gridHtml.push('		<a class="btn btn-default btn-sm" id="winQuery">查询</a>');
			gridHtml.push('		<div style="float:right;"><a class="btn btn-default btn-sm" id="winSubmit">确定</a>');
			gridHtml.push('		&nbsp;<a class="btn btn-default btn-sm"  id="winCancel">取消</a></div>');
			gridHtml.push('	</div>');
			gridHtml.push('	<table id="winGrid"></table>');
			gridHtml.push('	<div id="winGridPager"></div>');
			gridHtml.push('</div>');
		
		

			T.Dialog.openHtml("标签列表", gridHtml.join(''), '850px', '500px', function() {
				parent.$("#winSelType").on("change", function() {
					that.queryParams.targetType=$(this).val();
				});
				parent.$("#winQuery").on("click", function() {
					that.queryParams.tagId=parent.$("#winSearchVal").val();
					that.query();
				});
				parent.$("#winSubmit").on("click",function(){
					if(!params.multicheck){//如果只允许单选，那么返回的是选中的行的数据对象
						var rowId = parent.$("#winGrid").getGridParam("selrow");
						if(!rowId) {
							parent.layer.close(parent.layer.index);
							T.Dialog.tip("没有选择任何数据");
							return;
						}
						var rowData = parent.$("#winGrid").jqGrid("getRowData",rowId);
						if(typeof(params.callback)=="function"){
							params.callback(rowData);
						}
					}else{//如果允许多选，返回的将是选中数据组成的数组
						var rowIds = parent.$("#winGrid").getGridParam("selarrrow");
						var resultArr=[];
						if(!rowIds) {
							parent.layer.close(parent.layer.index);
							T.Dialog.tip("没有选择任何数据");
							return;
						}
						if(typeof(params.callback)=="function"){
							$.each(rowIds, function(i,n) {
								resultArr.push(parent.$("#winGrid").jqGrid("getRowData",n));
							});
							params.callback(resultArr);
						}
					}
					parent.layer.close(parent.layer.index);
				});
				parent.$("#winCancel").on("click",function(){

					parent.layer.close(parent.layer.index);
				});
				parent.$("#winGrid").jqGrid({
					url: serverUrl,
					datatype: "json",
					colModel: [{ label: 'id', name: 'id', index: 'id', width: 50,hidden: true, key: true },
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
                        { label: '电量（%）', name: 'battery', index: 'battery', width: 50 },
                        { label: '更新时间', name: 'updateDate', index: 'update_date', width: 100 }
					],
					viewrecords: true,
					height: 305,
					rownumbers: true,
					rownumWidth: 25,
					autowidth: true,
					multiselect: true,
					pager: "#winGridPager",
					jsonReader: {
						root: "page.list",
						page: "page.currPage",
						total: "page.totalPage",
						records: "page.totalCount"
					},
					prmNames: {
						page: "page",
						rows: "limit",
						order: "order"
					},
					gridComplete: function() {
						//隐藏grid底部滚动条
						parent.$("#winGrid").closest(".ui-jqgrid-bdiv").css({
							"overflow-x": "hidden"
						});
					},
					beforeSelectRow:function(rowid, e){
						if(!params.multicheck){
							parent.$("#winGrid").resetSelection();
						}

					},
					onSelectAll:function(rowids,statue){
						//函数里做自己的处理
						if(!params.multicheck){
							parent.$("#winGrid").resetSelection();
						}
					}
				});
			});

		}

	}

})(jQuery);