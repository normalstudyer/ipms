//jqGrid的配置信息
if($.jgrid){
    $.jgrid.defaults.width = 1000;
    $.jgrid.defaults.responsive = true;
    $.jgrid.defaults.styleUI = 'Bootstrap';
}


//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
};
T.p = url;

//请求前缀
var baseURL = "/mifu-ipms/";

//登录token
var token = localStorage.getItem("token");
if(token == 'null') {
	parent.location.href = baseURL + 'login.html';
}

//jquery全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false,
	headers: {
		"token": token
	},
	//  xhrFields: {
	//      withCredentials: true
	//  },
	complete: function(xhr) {
		//token过期，则跳转到登录页面
		if(xhr.responseJSON && xhr.responseJSON.code == 401) {
			parent.location.href = baseURL + 'login.html';
		}
	}
});
if($.jgrid) {
	//jqgrid全局配置
    $.extend($.jgrid.defaults, {
        ajaxGridOptions: {
            headers: {
                "token": token
            }
        }
    });
}
//日期格式化
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 返回系统当前时间
 * @returns {*}
 */
T.getNow=function () {
	return new Date().Format("yyyy-MM-dd hh:mm:ss");
}
/**
 * 获取用户信息
 * @returns {*|{deptName: null, deptId: null, status: number, roleIdList: Array}}
 */
T.getUser=function () {
	return parent.vm.user;
}

//权限判断
function hasPermission(permission) {
	 if (window.parent.permissions.indexOf(permission) > -1) {
	     return true;
	 } else {
	     return false;
	 }
}

//重写alert
window.alert = function(msg, callback) {
	parent.layer.alert(msg, function(index) {
		parent.layer.close(index);
		if(typeof(callback) === "function") {
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirm = function(msg, callback) {
	parent.layer.confirm(msg, {
			btn: ['确定', '取消']
		},
		function(index) { //确定事件;
			parent.layer.close(index);
			if(typeof(callback) === "function") {
				callback("ok");
			}
		});
}

//选择一条记录
function getSelectedRow() {
	var grid = $("#jqGrid");
	var rowKey = grid.getGridParam("selrow");
	if(!rowKey) {
		alert("请选择一条记录");
		return;
	}

	var selectedIDs = grid.getGridParam("selarrrow");
	if(selectedIDs.length > 1) {
		alert("只能选择一条记录");
		return;
	}

	return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
	var grid = $("#jqGrid");
	var rowKey = grid.getGridParam("selrow");
	if(!rowKey) {
		alert("请选择一条记录");
		return;
	}

	return grid.getGridParam("selarrrow");
}
//预览图片(测试)
function filePreview(evt) {
	alert($(evt.currentTarget).attr("data-url"));
}

function S4() { 
   return (((1+Math.random())*0x10000)|0).toString(16).substring(1); 
}; 

T.getUUID=function(){
   return (S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4()); 
}

/**
 * 弹框工具对象
 */
T.Dialog = {
	/**
	 * iframe窗
	 * @param {Object} title 标题
	 * @param {Object} url 页面地址/html内容
	 * @param {Object} width 窗体宽度
	 * @param {Object} height 窗体高度
	 */
	openUrl: function(title, url, width, height) {
		//默认
		parent.layer.open({
			type: 2,
			skin: 'layui-layer-molv',
			title: title,
			shadeClose: true,
			shade: false,
			fix: false,
			maxmin: true, //开启最大化最小化按钮
			area: [width, height],
			content: url
		});
	},

	/**
	 * 页面层
	 * @param {Object} title 标题
	 * @param {Object} content任意的html代码
	 * @param {Object} width 窗体宽度
	 * @param {Object} height 窗体高度
	 */
	openHtml: function(title, content, width, height,showCallback,cancelCallback) {
		parent.layer.open({
			type: 1,
			skin: 'layui-layer-molv',
			title: title,
			area: [width, height], //宽高
			content: content,
			success: function(layero, index){
//		    	console.log(layero, index);
		    	if(typeof (showCallback)=="function")
                    showCallback();
			},
			cancel:function (index,layer) {
                if(typeof (cancelCallback)=="function"){
                    cancelCallback();
				}

            }
		});
	},

	/**
	 * 提示层
	 * @param {Object} msg 提示信息
	 */
	tip: function(msg) {
		parent.layer.msg(msg);
	},
	/**
	 * 相册json 
	 * @param {Object} json
	 */
	photos: function(json) {
		//json 格式实例
		//			{
		//			  "title": "", //相册标题
		//			  "id": 123, //相册id
		//			  "start": 0, //初始显示的图片序号，默认0
		//			  "data": [   //相册包含的图片，数组格式
		//			    {
		//			      "alt": "图片名",
		//			      "pid": 666, //图片id
		//			      "src": "", //原图地址
		//			      "thumb": "" //缩略图地址
		//			    }
		//			  ]
		//			}

		parent.layer.photos({
			photos: json,
			closeBtn: 1,
			anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
		});
	},
	/**
	 * tab层
	 * @param {Object} width
	 * @param {Object} height
	 * @param {Object} content tab层内容 格式如下：[{title: 'TAB1',content: '内容1'}, {title: 'TAB2', content: '内容2'}]
	 */
	tab: function(content, width, height) {
		parent.layer.tab({
			area: [width, height],
			shadeClose: true,
			tab: content
		});
	}
};

/*
函数：格式化日期
参数：formatStr-格式化字符串
d：将日显示为不带前导零的数字，如1
dd：将日显示为带前导零的数字，如01
ddd：将日显示为缩写形式，如Sun
dddd：将日显示为全名，如Sunday
M：将月份显示为不带前导零的数字，如一月显示为1
MM：将月份显示为带前导零的数字，如01
MMM：将月份显示为缩写形式，如Jan
MMMM：将月份显示为完整月份名，如January
yy：以两位数字格式显示年份
yyyy：以四位数字格式显示年份
h：使用12小时制将小时显示为不带前导零的数字，注意||的用法
hh：使用12小时制将小时显示为带前导零的数字
H：使用24小时制将小时显示为不带前导零的数字
HH：使用24小时制将小时显示为带前导零的数字
m：将分钟显示为不带前导零的数字
mm：将分钟显示为带前导零的数字
s：将秒显示为不带前导零的数字
ss：将秒显示为带前导零的数字
l：将毫秒显示为不带前导零的数字
ll：将毫秒显示为带前导零的数字
tt：显示am/pm
TT：显示AM/PM
返回：格式化后的日期
*/
Date.prototype.format = function (formatStr) {
    var date = this;
    /*
    函数：填充0字符
    参数：value-需要填充的字符串, length-总长度
    返回：填充后的字符串
    */
    var zeroize = function (value, length) {
        if (!length) {
            length = 2;
        }
        value = new String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {
            zeros += '0';
        }
        return zeros + value;
    };
    return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {
        switch ($0) {
            case 'd': return date.getDate();
            case 'dd': return zeroize(date.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
            case 'M': return date.getMonth() + 1;
            case 'MM': return zeroize(date.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];
            case 'yy': return new String(date.getFullYear()).substr(2);
            case 'yyyy': return date.getFullYear();
            case 'h': return date.getHours() % 12 || 12;
            case 'hh': return zeroize(date.getHours() % 12 || 12);
            case 'H': return date.getHours();
            case 'HH': return zeroize(date.getHours());
            case 'm': return date.getMinutes();
            case 'mm': return zeroize(date.getMinutes());
            case 's': return date.getSeconds();
            case 'ss': return zeroize(date.getSeconds());
            case 'l': return date.getMilliseconds();
            case 'll': return zeroize(date.getMilliseconds());
            case 'tt': return date.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';
        }
    });
}