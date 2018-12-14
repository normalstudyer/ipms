/*
Bootstable
 @description  标准的基于jquery、bootstrap、bootstrap-datetimepicker的可编辑表格插件
 @version 1.1
 @autor adams
*/
(function($) {
	$.fn.editable = function(params) {
		var colsEdi = null;
		var newColHtml = '<div class="btn-group pull-right">' +
			'<button id="bImport" type="button" class="btn btn-sm btn-default" data-type="import"  title="添加">' +
			'<span class="glyphicon glyphicon-plus" > </span>' +
			'</button>' +
			'<button id="bEdit" type="button" class="btn btn-sm btn-default" data-type="edit"  title="编辑">' +
			'<span class="glyphicon glyphicon-pencil" > </span>' +
			'</button>' +
			'<button id="bElim" type="button" class="btn btn-sm btn-default" data-type="delete"  title="删除">' +
			'<span class="glyphicon glyphicon-trash" > </span>' +
			'</button>' +
			'<button id="bAcep" type="button" class="btn btn-sm btn-default" data-type="save" style="display:none;"  title="保存结果">' +
			'<span class="glyphicon glyphicon-ok" > </span>' +
			'</button>' +
			'<button id="bCanc" type="button" class="btn btn-sm btn-default" data-type="cancel" style="display:none;"  title="取消编辑">' +
			'<span class="glyphicon glyphicon-remove" > </span>' +
			'</button>' +
			'</div>';
		var colEdicHtml = '<td name="buttons">' + newColHtml + '</td>';
		var that = this;
		params = $.extend({
			showOpColumn: true, //是否显示操作列
			columnsEd: null, //Index to editable columns. If null all td editables. Ex.: "1,2,3,4,5"
			$addButton: null, //Jquery object of "Add" button
			onEdit: function() {}, //Called after edition
			onBeforeDelete: function() {}, //Called before deletion
			onDelete: function() {}, //Called after deletion
			onAdd: function() {}, //Called when added a new row
			onImport: function() {} //导入数据
		}, params);

		this.find('thead tr').prepend('<th width="50px">序号</th>'); //添加序号
		if(params.showOpColumn) {
			this.find('thead tr').append('<th name="buttons" width="160px">操作</th>');
			this.find('tbody tr').append(colEdicHtml);
		}

		//  var index=this.find('tbody tr').length;
		this.find('tbody tr').prepend('<td name="index">1</td>');
		//设置每一行tr的索引
		setIndex();
		var $tabedi = this;

		if(params.$addButton != null) {
			//Se proporcionó parámetro
			params.$addButton.click(function() {
				rowAddNew($tabedi.attr("id"));
			});
		}

		if(params.columnsEd != null) {
			//Extract felds
			colsEdi = params.columnsEd.split(',');
		}

		//绑定按钮事件
		rebindEvt();

		function IterarCamposEdit($cols, tarea) {

			var n = 0;
			$cols.each(function() {
				n++;
				if($(this).attr('name') == 'buttons') return;
				if(!EsEditable(n - 1)) return;
				tarea($(this));
			});

			function EsEditable(idx) {

				if(colsEdi == null) {
					return true;
				} else {
					//alert('verificando: ' + idx);
					for(var i = 0; i < colsEdi.length; i++) {
						if(idx == colsEdi[i]) return true;
					}
					return false;
				}
			}
		}

		function setIndex() {
			that.find('tbody tr').each(function(i) {
				$(this).attr("data-index", i);
			});
		}

		function rebindEvt() {
			that.find("button").unbind();
			that.find("button").on("click", function() {
				var type = $(this).attr("data-type");
				switch(type) {
					case "import":
						rowImport(this);
						break;
					case "edit":
						rowEdit(this);
						break;
					case "delete":
						rowElim(this);
						break;
					case "save":
						rowAcep(this);
						break;
					case "cancel":
						rowCancel(this);
						break;
					default:
						break;
				}
			});
		}

		function FijModoNormal(but) {
			$(but).parent().find('#bAcep').hide();
			$(but).parent().find('#bCanc').hide();
			$(but).parent().find('#bEdit').show();
			$(but).parent().find('#bElim').show();
			var $row = $(but).parents('tr');
			$row.attr('id', '');
		}

		function FijModoEdit(but) {
			$(but).parent().find('#bAcep').show();
			$(but).parent().find('#bCanc').show();
			$(but).parent().find('#bEdit').hide();
			$(but).parent().find('#bElim').hide();
			var $row = $(but).parents('tr');
			$row.attr('id', 'editing');
		}

		function ModoEdicion($row) {
			if($row.attr('id') == 'editing') {
				return true;
			} else {
				return false;
			}
		}

		function rowAcep(but) {

			var $row = $(but).parents('tr');
			var $cols = $row.find('td');
			if(!ModoEdicion($row)) return;

			IterarCamposEdit($cols, function($td) {
				var cont = $td.find('input').val();
				$td.html(cont);
			});
			FijModoNormal(but);
			params.onEdit($row);
		}

		function rowCancel(but) {

			var $row = $(but).parents('tr');
			var $cols = $row.find('td');
			if(!ModoEdicion($row)) return;

			IterarCamposEdit($cols, function($td) {
				var cont = $td.find('div').html();
				$td.html(cont);
			});
			FijModoNormal(but);
		}

		function rowEdit(but) {
			var $row = $(but).parents('tr');
			var $cols = $row.find('td');
			if(ModoEdicion($row)) return;

			IterarCamposEdit($cols, function($td) {
				var cont = $td.html();
				//				var div = '<div style="display: none;">' + cont + '</div>'; 
				var inputType = $td.attr("data-type")
				var div = '<div style="display: none;">' + cont + '</div>'; 
				var input = '<input class="form-control input-sm" maxlength="150" value="' + cont + '">';
				if(inputType == "number") {
					input = '<input type="number" step="1" min="0"  class="form-control input-sm"  value="' + cont + '">';
				}
				$td.html(div+input);
				if(inputType == "date") {
					$td.find("input").datetimepicker({
						format: 'yyyy-mm-dd 00:00:00', //时间格式
						autoclose: true, //选择后自动关闭
						todayBtn: true,
						minView: 2,
						maxView: 4,
						language: 'zh-CN',
						startDate:new Date()
					});
				}

			});
			FijModoEdit(but);
		}
		//删除当前行
		function rowElim(but) {
			var $row = $(but).parents('tr');
			var $tbody = $(but).parents('tr').parent('tbody');
			console.log($tbody.find('tr').length);
			if($tbody.find('tr').length == 1) {
				$tbody.find('tr').eq(0).find("td").each(function(i) {
					if($(this).attr('name') != 'buttons' && $(this).attr('name') != 'index') {
						$(this).html('');
					}
				});
				return;
			}

			params.onBeforeDelete($row);
			$row.remove();
			console.log($tbody.find('tr').length);
			//更新序号
			$tbody.find('tr').each(function(i) {
				//Es columna de botones
				$(this).find('td').eq(0).text(i + 1);
			});
			setIndex();
			params.onDelete();
		}

		function rowAddNew(tabId) {
			var $tab_en_edic = $("#" + tabId); //Table to edit
			var $filas = $tab_en_edic.find('tbody tr');
			if($filas.length == 0) {

				var $row = $tab_en_edic.find('thead tr');
				var $cols = $row.find('th');
				//construye html
				var htmlDat = '';
				$cols.each(function() {
					if($(this).attr('name') == 'buttons') {

						htmlDat = htmlDat + colEdicHtml;
					} else {
						htmlDat = htmlDat + '<td></td>';
					}
				});
				$tab_en_edic.find('tbody').append('<tr>' + htmlDat + '</tr>');
			} else {

				var $ultFila = $tab_en_edic.find('tr:last');
				$ultFila.clone().appendTo($ultFila.parent());
				$ultFila = $tab_en_edic.find('tr:last');
				var $cols = $ultFila.find('td');
				$cols.each(function() {
					if($(this).attr('name') == 'buttons') {
						//Es columna de botones
					} else if($(this).attr('name') == 'index') {
						$(this).html(parseInt($(this).text()) + 1);
					} else {
						$(this).html('');
					}
				});
			}
			setIndex();
			rebindEvt();
			params.onAdd();
		}

		function TableToCSV(tabId, separator) { //Convierte tabla a CSV
			var datFil = '';
			var tmp = '';
			var $tab_en_edic = $("#" + tabId); //Table source
			$tab_en_edic.find('tbody tr').each(function() {
				if(ModoEdicion($(this))) {
					$(this).find('#bAcep').click(); //
				}
				var $cols = $(this).find('td');
				datFil = '';
				$cols.each(function() {
					if($(this).attr('name') == 'buttons') {

					} else {
						datFil = datFil + $(this).html() + separator;
					}
				});
				if(datFil != '') {
					datFil = datFil.substr(0, datFil.length - separator.length);
				}
				tmp = tmp + datFil + '\n';
			});
			return tmp;
		}

		function rowImport(but) {
			params.onImport($(but).parents("tr").attr("data-index"));
		}

		return this;
	};

})(jQuery);