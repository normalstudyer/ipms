(function($){
    $.recordWin=function(){
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div id="winGrid" style="width:100%;height:100%;padding: 10px;">');
        contentHtml.push('  <div class="form-inline search-blocks">');
        contentHtml.push('      <div class="form-group form-group-sm">');
        contentHtml.push('          <label>日期：</label>');
        contentHtml.push('          <input type="text" class="form-control" id="search_stime" value=""   placeholder="开始日期" >');
        contentHtml.push('          <input type="text" class="form-control" id="search_etime" value=""  placeholder="结束日期" >');
        contentHtml.push('      </div>');
        contentHtml.push('      <a class="btn btn-primary btn-sm" id="btn_start"><span class="glyphicon glyphicon-play"></span> 开始</a>');
        // contentHtml.push('      <a class="btn btn-primary btn-sm" id="btn_export"><span class="glyphicon glyphicon-export"></span> 导出</a>');
        contentHtml.push('      <div class="form-group form-group-sm" style="margin-left: 20px">');
        contentHtml.push('          <label>日期：</label>');
        contentHtml.push('          <span id="sp_date">--</span>');
        contentHtml.push('      </div>');
        contentHtml.push('      <div class="form-group form-group-sm">');
        contentHtml.push('          <label>电量：</label>');
        contentHtml.push('          <span class="span-normal" id="sp_battery">--</span>');
        contentHtml.push('      </div>');
        contentHtml.push('      <div class="form-group form-group-sm hidden">');
        contentHtml.push('          <label>电子围栏：</label>');
        contentHtml.push('          <span class="span-normal" id="sp_state">正常</span>');
        contentHtml.push('      </div>');
        contentHtml.push('      <div class="form-group form-group-sm">');
        contentHtml.push('          <label>位置：</label>');
        contentHtml.push('          <span id="sp_position">--</span>');
        contentHtml.push('      </div>');
        contentHtml.push('      <a class="btn btn-primary btn-sm pull-right" id="btn_export"><span class="glyphicon glyphicon-export"></span> 导出</a>');
        contentHtml.push('  </div> ');
        contentHtml.push('  <div style="height: 100%;width: 100%" id="mapContainer">');
        contentHtml.push('      <div id="axisContent">');
        contentHtml.push('          <img id="map" src="images/indoorImage.jpg" width="100%" height="auto" border="0" />');
        contentHtml.push('      </div>');
        contentHtml.push('  </div>');
        contentHtml.push('</div>');

        var axisIndoor,Settings = {
            $container: $("#axisContent"),
            $containerParent: $("#mapContainer"),
            scale: "auto", //比例尺 ，mm/像素，一个像素多少毫米 auto:自动，室内平面图会自适应大小,16.2
            indoor2DSize: [20000,10000 ], //2D平面图的尺寸,长和宽，(单位：mm)
            imageSize: [2752, 1203], //图片的像素尺寸
            axisGrid: false, //是否显示网格
            origin: {//原点坐标
                x: 1000,
                y: 1000
            }
        };

        /**
         * 弹框显示
         */
        that.show = function(tag_id) {
            // console.log(tag_id);
            T.Dialog.openHtml("历史轨迹", contentHtml.join('') , '80%', '80%', function() {
                parent.$("#mapContainer").height(parent.$("#mapContainer").height()-40);

                parent.$("#search_stime").datetimepicker({
                    format: 'yyyy/mm/dd hh:ii:00',//时间格式
                    autoclose: true,//选择后自动关闭
                    todayBtn: true,
                    // startDate:"2018/01/01 00:00:00",
                    forceParse:false,
                    minuteStep:10,
                    minView: 0,
                    maxView: 4,
                    language: 'zh-CN'
                });
                parent.$("#search_etime").datetimepicker({
                    format: 'yyyy/mm/dd hh:ii:00',//时间格式
                    autoclose: true,//选择后自动关闭
                    todayBtn: true,
                    forceParse:false,
                    minuteStep:10,
                    // startDate:"2018/01/01 00:00:00",
                    minView: 0,
                    maxView: 4,
                    language: 'zh-CN'
                });

                parent.$("#search_stime").on("change", function (e) {
                    verifyDate(1);
                });

                parent.$("#search_etime").on("change", function (e) {
                    verifyDate(2);
                });

                Settings.$container=parent.$("#axisContent");
                Settings.$containerParent=parent.$("#mapContainer");
                axisIndoor = new parent.$.axis4Indoor(Settings);


                //播放轨迹
                parent.$("#btn_start").on("click", function (e) {
                    if(parent.$("#search_stime").val()&&parent.$("#search_etime").val()){
                        playRecord(tag_id);
                    }
                });
                parent.$("#btn_export").on("click",function () {
                    exportRecord(tag_id);
                })

            },function () {
                // alert(2);
                axisIndoor.stop();
            });
        }
        //开始日期与结束日期验证,4个小时内
        function verifyDate(flag) {

            var startDate=Date.parse(parent.$("#search_stime").val());
            var endDate=Date.parse(parent.$("#search_etime").val());

            // console.log(date.format("yyyy/MM/dd hh:mm:00"));
            if(startDate>endDate){
                if(flag==1){
                    T.Dialog.tip("开始日期不能大于结束日期");
                    parent.$("#search_stime").val("");
                }else{
                    T.Dialog.tip("结束日期不能小于开始日期");
                    parent.$("#search_etime").val("");
                }
            }
            // debugger;
            var mm=endDate-startDate;
            var hours=mm/(60*60*1000);
            if(hours>4){
                T.Dialog.tip("间隔时间不能超过4个小时");
                startDate+=60*60*1000*4;
                var parseDate=new Date(startDate);
                parent.$("#search_etime").val(parseDate.format("yyyy/MM/dd HH:mm:00"));
            }
        }


        //播放轨迹
        function playRecord(tag_id) {
            var sTime=parent.$("#search_stime").val();
            var eTime=parent.$("#search_etime").val();
            var url=baseURL + "ips/positioninfohistory/recordlist?tagId="+tag_id+"&sTime="+sTime+"&eTime="+eTime;
            $.get(url, function(r){
                if(r.recordlist.length>0){
                    axisIndoor.playRecord(r.recordlist,function (data) {
                        parent.$("#sp_date").text(data.updateDate);
                        if(data.battery<30){
                            parent.$("#sp_battery").removeClass().addClass("span-danger")
                        }else{
                            parent.$("#sp_battery").removeClass().addClass("span-normal")
                        }
                        parent.$("#sp_battery").text(data.battery+"%");
                        parent.$("#sp_position").text(data.x.toFixed(6)+","+data.y.toFixed(6));
                        if(data.msg){
                            parent.$("#sp_state").removeClass().addClass("span-danger")
                            parent.$("#sp_state").text("警告");
                        }else{
                            parent.$("#sp_state").removeClass().addClass("span-normal")
                            parent.$("#sp_state").text("正常");
                        }

                    });
                }else{
                    T.Dialog.tip("此日期范围内无历史数据");
                }

            });
        }
        //导出历史轨迹
        function exportRecord(tag_id) {
            var sTime=parent.$("#search_stime").val();
            var eTime=parent.$("#search_etime").val();
            var url=baseURL + "api/exportrecord?tagId="+tag_id+"&sTime="+sTime+"&eTime="+eTime;
            window.location.href = url;
        }
    }
})(jQuery)