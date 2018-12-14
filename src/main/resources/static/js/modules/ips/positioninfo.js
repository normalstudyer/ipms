var axisIndoor;
var ws = null;
var lockReconnect = false;//避免重复连接
var IP_CONFIG = (window.location.protocol ? (window.location.protocol + "//") : "") + window.location.host;
IP_CONFIG=IP_CONFIG.replace("http","ws");
var wsUrl=IP_CONFIG+"/mifu-ipms/websocket";//websocket地址
$(function() {

	$(".row").css("height", $(window).height());
    $(".rail_log_div ul").eq(0).height($(".rail_log_div").eq(0).height()-44);
    $("#placeTree").height($(".treediv").eq(0).height()-44);

    //加载树
    loadtree();


	var Settings = {
		$container: $("#axisContent"),
		$containerParent: $("#mapContainer"),
		scale: "auto", //比例尺 ，mm/像素，一个像素多少毫米 auto:自动，室内平面图会自适应大小,16.2
        indoor2DSize: [20000,10000 ], //2D平面图的尺寸,长和宽，(单位：mm)
        imageSize: [2752, 1203], //图片的像素尺寸
        axisGrid: false, //是否显示网格
        origin: {//原点坐标
            x: 0,
            y: 0
        }
	};

	axisIndoor = new $.axis4Indoor(Settings);

	$(window).resize(function() {
		$(".row").height($(window).height());
		axisIndoor.resize();
		$(".rail_log_div ul").eq(0).height($(".rail_log_div").eq(0).height()-44);
        $("#placeTree").height($(".treediv").eq(0).height()-44);
	});

	//websocket通信定位
    if('WebSocket' in window){
        createWebSocket(wsUrl);
    }
    else{
        alert('Not support websocket');
    }

    $("#clearlog").on("click",function () {
        $(".rail_log_div ul").eq(0).html('<li class="nolog">暂无告警消息</li>');
    });
});



//websocket心跳检测
var heartCheck = {
    timeout: 30000,//10秒
    timeoutObj: null,
    reset: function(){
        clearTimeout(this.timeoutObj);
        return this;
    },
    start: function(){
        this.timeoutObj = setTimeout(function(){
            //这里发送一个心跳，后端收到后，返回一个心跳消息，
            //onmessage拿到返回的心跳就说明连接正常
            ws.send("HeartBeat");
        }, this.timeout)
    }
}

function createWebSocket(url) {
    try {
        ws = new WebSocket(url);
        initEventHandle();
    } catch (e) {
        reconnect(url);
    }
}

function initEventHandle() {
    ws.onclose = function () {
        reconnect(wsUrl);
    };
    ws.onerror = function () {
        reconnect(wsUrl);
    };
    ws.onopen = function () {
        //心跳检测重置
        heartCheck.reset().start();
    };
    ws.onmessage = function (event) {
        //如果获取到消息，心跳检测重置
        //拿到任何消息都说明当前连接是正常的
        heartCheck.reset().start();
        if(event.data&&event.data!="HeartBeat"){
            // console.log("aaaa："+event.data);
            var position=$.parseJSON(event.data);

            axisIndoor.doLocation(position.location);
            displayRailWarnning(position.railwarnning);
        }


    };
    window.onbeforeunload = function(){
        ws.close();
    }
}

function reconnect(url) {
    if(lockReconnect) return;
    lockReconnect = true;
    //没连接上会一直重连，设置延迟避免请求过多
    setTimeout(function () {
        createWebSocket(url);
        lockReconnect = false;
    }, 2000);
}
//显示告警日志
function displayRailWarnning(data) {
    if(data){
        var $container=$(".rail_log_div ul").eq(0);
        if($container.find(".nolog").length>0){
            $container.empty();
        }
        var date=new Date(parseInt(data.triggerDate));
        $container.prepend('<li><span class="log-time">'+date.format("yyyy/MM/dd HH:mm:ss")+'</span>：'+data.info+'</li>');
    }
}

//节点点击
function nodeclick(event, treeId, treeNode) {
    console.log(treeNode);
    if(treeNode.nodetype==1&&treeNode.checked){
        axisIndoor.openInfoWindow(treeNode.label_id);
    }
}

//节点选中
function nodecheck(e, treeId, treeNode) {
    // console.log(treeNode);
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    var checkedNodes=zTree.getCheckedNodes(true);

    var tagIds=[];

    $.each(checkedNodes,function (i,n) {
        tagIds.push(n.label_id);
    });
    axisIndoor.displayPoints(tagIds);

}

function loadtree() {
    //物品类别树
    var treeSetting = {
        view: {
            // addHoverDom: addHoverDom,
            // removeHoverDom: removeHoverDom,
            // addDiyDom: addDiyDom
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: -1
            },
            key: {
                url: "nourl"
            }
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        callback: {
            onClick: nodeclick,
            onCheck: nodecheck
        }
    };



    $.get(baseURL + "/ips/positioninfo/treelist", function(r){
        var data=[];
        $.each(r.treelist,function (i,n) {
            if(n.nodetype==1){
                n["iconSkin"]="iconuser";
            }
            //checked:true
            n["checked"]=true;
            data.push(n);
        });

        $.fn.zTree.init($("#placeTree"), treeSetting, r.treelist).expandAll(true);
        // console.log(r.treelist);
    });

    function addDiyDom(treeId, treeNode) {
        // console.log(treeNode);
        if (treeNode.nodetype==0) return;
        var aObj = $("#" + treeNode.tId + "_a");
        var editStr = "<span class='demoIcon' id='diyBtn_" +treeNode.id+ "' title='"+treeNode.name+"' onfocus='this.blur();'><span class='button icontRailOff'></span></span>";
        aObj.append(editStr);
        var btn = $("#diyBtn_"+treeNode.id);
        if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
    }
}



