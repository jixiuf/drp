
//str 为xml 格式的字符串,开头可以有<?xml version="1.0" encoding="UTF-8"?>的声明 ，也可以没有F
function createXMLDOM(str) {
	var oXmlDom;
	var arrSignatures = [ "MSXML2.DOMDocument.6.0", "MSXML2.DOMDocument.5.0",
			"MSXML2.DOMDocument.4.0", "MSXML2.DOMDocument.3.0",
			"MSXML2.DOMDocument", "MSXML.DOMDocument", "Microsoft.XmlDom" ];
	if (window.ActiveXObject) {
		for ( var i = 0; i < arrSignatures.length; i++) {
			try {
				var oXmlDom = new ActiveXObject(arrSignatures[i]);
				oXmlDom.loadXML(str);
				// return oXmlDom;
				break;
			} catch (oError) {
				// ignore
			}
		}
	} else {
		try {
			var Parser = new DOMParser();
			oXmlDom = Parser.parseFromString(str, "text/xml");
		} catch (e) {
		}
	}

	if (oXmlDom == null) {
		// throw new Error("MSXML没有安装");
	}

	return oXmlDom;
}

// clone attributes,and the node self ,no child is cloned
// 进行dom Element节点 的浅层复制
function cloneSimpleEle(root) {
	if (root.nodeType == 1) {
		var root_clone_;
		// alert(document.all);
		var tagName_ = root.tagName.toLowerCase();

		// if (CheckBrowser() == "IE") {
		//
		// root_clone_ = document.createElement(tagName_);
		// } else if (CheckBrowser() == "Firefox") {
		//			
		// }
		root_clone_ = document.createElement(tagName_);
		var root_clone_ = document.createElement(root.tagName);
		if (root.attributes && root.attributes.length > 0) {
			var attr_len = root.attributes.length;
			for ( var j = 0; j < attr_len; j++) {
				var attr = root.attributes[j];
				root_clone_.setAttribute(attr.nodeName, attr.nodeValue);
			}
		}
		return root_clone_;

	}

}
/*
 * 此函数，用于DOM Element 的复制,返回一个几乎与参数一致的Dom Element
 * （有一些差别，如忽略了注释等无关紧要的东西，只复制了其中的文本节点和标签，） 另外，传进去的参数不仅可以是html中的DOM 对象，也可以是xml
 * 中的DOM 对像， 如果您想用此函数进行html 中dom 节点的复制，建议用 系统自带的函数dom.cloneNode(deep_boolean);
 * 进行深层或浅层的复制，此函数存在 的理由，主要是适用于xml dom对像向html dom对像转化的情形， 例如处理ajax
 * 返回的xml数据，（直接返回的dom对像没法在html 的document 中进行appendChild() ,insertBefore()
 * 等操作，经过此函数处理之后的对像则可以）
 */
function cloneElement(root) {
	var root_clone = cloneSimpleEle(root);
	if (root.hasChildNodes()) {
		var child_len = root.childNodes.length;
		for ( var i = 0; i < child_len; i++) {
			var child = root.childNodes[i];
			var clone;
			if (child.nodeType == 3) {
				clone = document.createTextNode(child.nodeValue);
			} else if (child.nodeType == 1) {
				clone = cloneElement(child);

				// ie 里如果<table ></table>人无<tbody>
				// ,而直接是<tr>则显示有问题,此部分代码在其中加入<tbody>
				if (child.tagName.toLowerCase() == "table") {

					var children = child.childNodes;
					var children_has_tbody = false;
					for ( var i = 0; i < children.length; i++) {
						if (children[i].nodeType == 1
								&& children[i].tagName.toLowerCase() == "tbody") {
							children_has_tbody = true;
							break;
						}
					}
					// 如果table 下无<tbody> 则table 下添加 tbody 然后将table 下的标签加到tbody
					// 下，即插入一个tbody
					if (!children_has_tbody) {
						var tbody = document.createElement("tbody");
						var len_ = clone.childNodes.length;
						for ( var i = 0; i < len_; i++) {
							var child = clone.childNodes[0];
							tbody.appendChild(child);
						}
						clearChildren(clone);
						clone.appendChild(tbody);
					}
				}
			}
			root_clone.appendChild(clone);
		}
	}
	return root_clone;
}
// 专门针对 xml_http_request 对象封装的一个方法,返回的对象，如果是结构正确的html 则可以用此方法直接appendChild

//将xml 对象转化成html 对象
// 用法 :
function cloneXmlHttpRequest(xml_http_request) {
	if (CheckBrowser() == "IE") {
	 
		for(var i=0;i<xml_http_request.childNodes.length;i++){
		 
			if(xml_http_request.childNodes[i].nodeType==1){// 如果是元素节点
				alert(xml_http_request.childNodes[i].tagName);
				var cl= cloneElement(xml_http_request.childNodes[i]);
			 
				return cl;
			 break;
			}
		}
		
	}   if (CheckBrowser() == "Firefox") {
		return cloneElement(xml_http_request.firstChild);
	}z
}
function CheckBrowser() {
	var cb = "Unknown";
	if (window.ActiveXObject) {
		cb = "IE";
	} else if (navigator.userAgent.toLowerCase().indexOf("firefox") != -1) {
		cb = "Firefox";
	} else if ((typeof document.implementation != "undefined")
			&& (typeof document.implementation.createDocument != "undefined") &&

			(typeof HTMLDocument != "undefined")) {
		cb = "Mozilla";
	} else if (navigator.userAgent.toLowerCase().indexOf("opera") != -1) {
		cb = "Opera";
	}
	return cb;
}
function clearChildren(element) {
	var children = element.childNodes;
	for ( var i = children.length - 1; i >= 0; i--) {
		var child = children[i];
		// while (child.hasChildNodes()) {
		// child.clearChildren();
		// }
		element.removeChild(child);
	}
	return element;

}



// ============================= start of js lib for
// ajax==============================================================================================================================
var http_request = false;
var callback_func;

// 使用说明
// 二,在页面使用如下代码将js文件包含进来:
// <script language=javascript src="ajax.js"></script>

// 三,在页面调用sendRequest(...)方法:
// 如:<a href="javascript:sendRequest(param,function,'GET')" >调用AJAX</a>
// 或:<input type="button" value="提交"
// onclick="sendRequest('post.do',param,function,'POST')" />
// 注释(以第一个为例):
// 暂时就不用了 default.do: 这个例子采用ajax通过一个链接请求default.do页面,
// param:
// 为参数,可以为空,也可以不为空,比如name=value&password=123456,也可以通过把一个表单(form)的字段组合起来作为一个字符串传递
// 过去,
// function: 是你自己写的一个函数,用于处理返回的内容,一般的处理是
// 将返回的内容显示在页面,一个典型的例子:
// function search(str){
// alert(str); //用于调试.
// myId.innerHTML = str;
// }
// GET: 是请求的方法,简单的说,get代表请求一个资源,post代表提交参数并请求资源.
// 发送ajax请求 get或post 完全兼容 IE FireFox各个版本,程序在 IE5.0,6.0,7.0,8.0
// FireeFox2.0,30下均做了全面测试
function sendRequest(url, argstr, func, method) {
	http_request = false;
	callback_func = func;
	if (window.XMLHttpRequest) { // Mozilla, Safari,...
		http_request = new XMLHttpRequest();
		// if (http_request.overrideMimeType) {
		// http_request.overrideMimeType('text/xml');
		// }
	} else if (window.ActiveXObject) { // IE
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	}
	if (!http_request) {
  // alert("不能建立 XMLHTTP 对象");
		 return false;
	}
	if (method == 'GET') {
		// http_request.onreadystatechange = alertContents;
		http_request.open('GET', url + '?' + argstr, false); // 目前是同步 true:异步
		http_request.send(null);
		alertContents();
	} else {
		// http_request.onreadystatechange = alertContents;
		http_request.open('POST', url + '', false);
		http_request.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=utf-8");
		http_request.send(argstr);
		alertContents();
	}
}
// 延迟处理,超时后的处理 private ,
function alertContents() {
	if (http_request.readyState == 4) {
		if (http_request.status == 200 || http_request.status == 0) {
			callback_func(http_request.responseText);
		} else {
			//alert('服务端返回状态: ' + http_request.statusText);
		}
	} else {
		// alert('数据加载中...');
	}

 
}



//取出url中的各个参数对应的值 url参数解析
//如 果url 中并无strParamName ,则返回空字符中“”
function getURLParam(strParamName, url) {
	var strReturn = "";
	var strHref = url.toLowerCase();
	if (strHref.indexOf("?") > -1) {
		var strQueryString = strHref.substr(strHref.indexOf("?") + 1)
				.toLowerCase();
		var aQueryString = strQueryString.split("&");
		for ( var iParam = 0; iParam < aQueryString.length; iParam++) {
			if (aQueryString[iParam].indexOf(strParamName.toLowerCase() + "=") > -1) {
				var aParam = aQueryString[iParam].split("=");
				strReturn = aParam[1];
				break;
			}
		}
	}
	return strReturn;
}
function appendParam(url,paramStr){
	var index=url.indexOf("?");
	if(index==-1){
		return url+"?"+paramStr ;
	}else{
		return url+"&" +paramStr;

	}
}
