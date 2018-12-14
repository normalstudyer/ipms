/**
 * 描述：室内坐标轴生成类, 依赖：jquery、jquery.webui-popover.min.js
 * 作者：adams
 * 日期：2018年7月26日
 */
;
(function($) {
	$.axis4Indoor = function(params) {
		var that = this;

		params = $.extend({
			callback: function() {},
			//			scale: 20, //比例尺 ，mm/像素，一个像素多少毫米， auto:自动，室内平面图会自适应大小
			// indoor2DSize: [22150, 9710], //2D平面图的尺寸，(单位：mm)
			// imageSize: [2752, 1203], //图片的像素尺寸
			$container: null, //容器的jq对象
			axisGrid: false, //是否显示网格
			origin: {
				x: 0,
				y: 0
			}, //坐标原点：以左下角为原点的坐标位置(单位：mm)
			xAxis: "east", //x轴正方向,默认east
			yAxis: "north", //y轴正方向，默认north
			clickCallback: function(data) {
				console.log(data.x + "," + data.y);
			},
			maxZoom: 10 //最大级别

		}, params);
		//popover默认配置
		var popSettings = {
			placement: 'top',
			//			style: "inverse",
			width: 260,
			//			height: 120,
			padding: true,
			//			animation: 'fade',
			closeable: true,
			trigger: 'manual',
			$container: params.$container //容器
		};
		var iconOffset = 8; //定位图标的偏移尺寸
		var zoomIndex = 1;
		var currentScale;
		var containerWidth = params.$container.width();
		var containerHeight = params.$container.height();
		var zoomWidth = (params.imageSize[0] - containerWidth) / params.maxZoom; //放大一级，地图容器需要增加的宽度
		var whRatio = containerWidth / containerHeight;
		that.currentData = null;
        // that.stopPlay=false;//历史轨迹播放停止状态
		var playInterval=null;//播放周期对象
        var playIndex=0;//
		// that.playObjects=null;//当前播放的历史轨迹数据对象；
		/**
		 * 定位
		 * @param {Object} data
		 */
		that.doLocation = function(data) {
			//			params.$container.find(".cursor").remove();
			if(data && data.length > 0) {
				that.currentData = data;
				$.each(data, function(i, n) {
					locating(n);
				});
			}
		};

		/**
		 * 定位标签
		 * @param {Object} n
		 */
		function locating(n) {

			var $coursor=$("#cursor_" + n.tagId);
			if($coursor.length == 0) {
				params.$container.append('<span id="cursor_' + n.tagId + '" class="cursor"></span>');
				$coursor=$("#cursor_" + n.tagId);
				var zIndex = params.$container.find(".cursor").length;
				$coursor.css("z-index", zIndex + 1);
				
				var html=[];
				html.push('<p>编号：' + n.tagId + '</p>');
				html.push('<p>电量：' + n.battery + '</p>');
                html.push('<p>x：' + n.x + '</p>');
                html.push('<p>y：' + n.y + '</p>');
				html.push('<p>更新时间：' + n.updateDate + '</p>');
				
				$coursor.webuiPopover($.extend(popSettings, {
					id: n.tagId,
					title: "编号：" + n.tagId,
					content: html.join(""),
					onShow: function() {
						$coursor.attr("data-popShow", "true");
					},
					onHide: function() {
						$coursor.attr("data-popShow", "false");
					}
				}));
				$coursor.on("click", function(event) {
					event.stopPropagation();
					$coursor.webuiPopover('show');
				});
			}
			//计算xy坐标的偏移量
			var left = n.x * 1000 / currentScale + params.origin.x / currentScale;
			var bottom = n.y * 1000 / currentScale + params.origin.y / currentScale;
			$coursor.css("bottom", bottom - iconOffset); //加上定位游标的高度zoomIndex
			$coursor.css("left", left - iconOffset);

		};
		/**
		 * 播放历史轨迹
		 * @param {Object} data
		 */
		that.playRecord = function(data,callback) {
			if(data && data.length > 0) {
				that.closeAllInfoWin();
				params.$container.find(".cursor").remove();
				that.currentData = data;

                playInterval = setInterval(function () {
                    var pos=that.currentData[playIndex];
                    locating(pos);//定位
					if (typeof (callback)=="function"){
					   callback(pos);//回掉
					}
                    playIndex++;
                    if(that.currentData.length==playIndex){
                        that.stop();
                    }
				}, 500);
			}
		};

        /**
		 * 停止播放
         */
		that.stop=function(){
            playIndex=0;
            clearInterval(playInterval);
		};
        /**
		 * 暂停播放
         */
        that.pause=function(){
            clearInterval(playInterval);
        };


        that.init = function() {
			that.resize();
			params.$container.on("click", function(event) {
				//鼠标点击的绝对位置
				Ev = event || window.event;
				var x = event.clientX + document.body.scrollLeft - document.body.clientLeft;
				var y = event.clientY + document.body.scrollTop - document.body.clientTop;
				//				alert("鼠标点击的绝对位置坐标："+x+","+y);

				//获取div在body中的绝对位置
				var x1 = params.$container.offset().left;
				var y1 = params.$container.offset().top;
				//	            console.log(params.$container.offset().bottom);
				//				console.log("div在body中的绝对位置坐标："+x1+","+y1);

				//鼠标点击位置相对于div的坐标
				var x2 = x - x1;
				var y2 = y - y1;
				//				console.log("鼠标点击位置相对于div的坐标："+x2+","+y2);
				y2 = params.$container.height() - y2; //top变成bottom
				if(typeof(params.clickCallback) == "function") {
					params.clickCallback({
						x: x2 * currentScale,
						y: y2 * currentScale
					});
				}
				//地图点击隐藏pop
				//				$("span[data-popShow]").webuiPopover('hide');
			});

			params.$container.on("mousewheel DOMMouseScroll", function(e) {
				var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) || // chrome & ie
					(e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1)); // firefox
				if(delta > 0) {
					// 向上滚
					that.zoomOut();
				} else if(delta < 0) {
					// 向下滚
					that.zoomIn();
				}
			});
			//地图可拖动，依赖Tdag.js
			params.$container.Tdrag();

		}
		//自适应
		that.resize = function() {
            // console.log("resize");
			//比例尺:一个像素多少毫米
			currentScale = params.indoor2DSize[0] / params.$container.width();

			params.areaLength = params.$container.width() * currentScale;
			params.areaWidth = params.areaLength*(params.indoor2DSize[1]/params.indoor2DSize[0]);

			//			params.$container.empty();
			if(params.axisGrid) {
			    $("#axisTable").remove();
				params.$container.append('<table id="axisTable" border="1"></table>');
				that.axisTable = params.$container.find("table").eq(0);
				var tableHtml = [];
				//默认每1²米一个格子
				var num1=Math.ceil(params.areaWidth / 500) ;
				var num2=Math.ceil(params.areaLength / 500);
				for(var i = 0; i < num1; i++) {
					tableHtml.push('<tr>');
					for(j = 0; j < num2; j++) {
						if(j==0){

                            if(i==0&&j==0){
                                tableHtml.push('<td><span class="unit-col">'+(num1-i-1)/2+'</span><span class="unit-col-top">'+num1/2+'</span></td>');
							}else{
                                tableHtml.push('<td><span class="unit-col">'+(num1-i-1)/2+'</span></td>');
							}
						}else if((i+1)==num1){
                            if(i==num1-1&&j==num2-1) {
                                tableHtml.push('<td><span class="unit-row">'+j/2+'</span><span class="unit-row-top">'+num2/2+'</span><i class="unit-memo">单位：米</i></td>');
                            }else{
                                tableHtml.push('<td><span class="unit-row">'+j/2+'</span></td>');
							}

						}else{
                            tableHtml.push('<td></td>');
                        }

					}
					tableHtml.push('</tr>');
				}
				that.axisTable.html(tableHtml.join(''));

                that.axisTable.find("span").each(function(i){
					var num=parseFloat($(this).text());
                	if( Math.round(num) === num){
					}else{
						$(this).addClass("hidden");
					}
				});
			}

			that.doLocation(that.currentData);

		};

		function reContainerSize() {
			var currentWidth = containerWidth + zoomWidth * zoomIndex;
			var currentHeight = currentWidth / whRatio;
			var offset = params.$container.offset();
			params.$container.offset({
				top: offset.top - ((currentHeight - params.$container.height()) / 2),
				left: offset.left - ((currentWidth - params.$container.width()) / 2)
			});
			params.$container.height(currentHeight);
			params.$container.width(currentWidth);
		}
		//放大
		that.zoomOut = function() {
			if(zoomIndex < params.maxZoom) {
				zoomIndex++;
				reContainerSize();
				//刷新坐标轴及重新加载坐标点
				that.resize();
				//刷新已打开的气泡位置
				$("span[data-popShow='true']").webuiPopover('show');
			}
		}
		//缩小
		that.zoomIn = function() {
			if(zoomIndex > 0) {
				zoomIndex--;
				reContainerSize();
				//刷新坐标轴及重新加载坐标点
				that.resize();
				//刷新已打开的气泡位置
				$("span[data-popShow='true']").webuiPopover('show');
			}

		}
		/**
		 * 打开信息框
		 * @param {Object} id
		 */
		that.openInfoWindow = function(tagId) {
			$("#cursor_" + tagId).webuiPopover('show');
			that.centerTo(tagId);
		}
		/**
		 * 居中坐标点
		 * @param {Object} tagId
		 */
		that.centerTo = function(tagId) {
			var pOffset=params.$containerParent.offset();
			var l = params.$containerParent.width() / 2 + pOffset.left;
			var t = params.$containerParent.height() / 2;
			var tagOffset = $("#cursor_" + tagId).offset();
			var los = tagOffset.left - l;
			var tos = tagOffset.top - t;
			var offset = params.$container.offset();
			params.$container.animate({
				top:offset.top - tos,
				left:offset.left - los-pOffset.left
			});

		}
		/**
		 * 关掉所有的infowindow
		 */
		that.closeAllInfoWin=function(){
			$("span[data-popShow='true']").webuiPopover('hide');
		}

		//加载
		if(params.$container && params.indoor2DSize) {
			that.init();
		} else {
			throw("缺少必要参数");
		}

	}

})(jQuery);