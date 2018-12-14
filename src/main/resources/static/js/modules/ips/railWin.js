/**
 * 描述：【电子围栏选择】公共弹框，依赖：JQuery、jqgrid、layer弹框
 * 作者：adams
 * 日期：2018年9月28日
 */
;
(function($) {
	$.railWin = function(params) {
		var that = this;		
		params = $.extend({
			multicheck: true,//是否允许多选 默认false
			callback: function() {} //确定后的回掉函数
		}, params);


		/**
		 * 检索参数
		 */
		that.queryParams={
            railName:""
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
            serverUrl = baseURL + 'ips/positionrail/list'; //数据接口url

		
			gridHtml.push('<div style="padding:10px">');
			gridHtml.push('	<div class="form-inline" style="padding-bottom: 10px;">');
			gridHtml.push('		<div class="form-group form-group-sm">');
            gridHtml.push('			<label>围栏名称：</label>');
			gridHtml.push('			<input type="text" class="form-control" id="winSearchVal">');
			gridHtml.push('		</div>');
			gridHtml.push('		<a class="btn btn-default btn-sm" id="winQuery">查询</a>');
			gridHtml.push('		<div style="float:right;"><a class="btn btn-default btn-sm" id="winSubmit">确定</a>');
			gridHtml.push('		&nbsp;<a class="btn btn-default btn-sm"  id="winCancel">取消</a></div>');
			gridHtml.push('	</div>');
			gridHtml.push('	<table id="winGrid"></table>');
			gridHtml.push('	<div id="winGridPager"></div>');
			gridHtml.push('</div>');
		
		

			T.Dialog.openHtml("围栏列表", gridHtml.join(''), '850px', '500px', function() {
				// parent.$("#winSelType").on("change", function() {
				// 	that.queryParams.targetType=$(this).val();
				// });
				parent.$("#winQuery").on("click", function() {
					that.queryParams.railName=parent.$("#winSearchVal").val();
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
								/*console.log(JSON.stringify( parent.$("#winGrid").getGridParam()));*/

								var enter=parent.$("#enter_"+n)[0].checked?1:0;
                                var leave=parent.$("#leave_"+n)[0].checked?1:0;
                                var name=  parent.$("#winGrid").jqGrid("getRowData",n).railName;
                                resultArr.push({id:n,enter:enter,leave:leave,name:name});
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
					colModel: [
						{ label: 'id', name: 'id', index: 'id', width: 50, hidden: true,key: true },
                        { label: '围栏编号', name: 'railId', index: 'rail_id', width: 80 },
                        { label: '围栏名称', name: 'railName', index: 'rail_name', width: 80 },
                        { label: '围栏报警', name: 'id', index: 'id', align:"center", width: 50,
                            formatter: function(value, options, row){
                                return '<label class="checkbox-inline"><input  type="checkbox" id="enter_'+value+'" checked="checked"></input>进入</label>'+
                                    '<label class="checkbox-inline"><input type="checkbox" id="leave_'+value+'"  checked="checked" ></input>离开</label>';
                            }
                        }
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