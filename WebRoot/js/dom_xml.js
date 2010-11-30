
//str Ϊxml ��ʽ���ַ���,��ͷ������<?xml version="1.0" encoding="UTF-8"?>������ ��Ҳ����û��F
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
		// throw new Error("MSXMLû�а�װ");
	}

	return oXmlDom;
}

// clone attributes,and the node self ,no child is cloned
// ����dom Element�ڵ� ��ǳ�㸴��
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
 * �˺���������DOM Element �ĸ���,����һ�����������һ�µ�Dom Element
 * ����һЩ����������ע�͵��޹ؽ�Ҫ�Ķ�����ֻ���������е��ı��ڵ�ͱ�ǩ���� ���⣬����ȥ�Ĳ�������������html�е�DOM ����Ҳ������xml
 * �е�DOM ���� ��������ô˺�������html ��dom �ڵ�ĸ��ƣ������� ϵͳ�Դ��ĺ���dom.cloneNode(deep_boolean);
 * ��������ǳ��ĸ��ƣ��˺������� �����ɣ���Ҫ��������xml dom������html dom����ת�������Σ� ���紦��ajax
 * ���ص�xml���ݣ���ֱ�ӷ��ص�dom����û����html ��document �н���appendChild() ,insertBefore()
 * �Ȳ����������˺�������֮��Ķ�������ԣ�
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

				// ie �����<table ></table>����<tbody>
				// ,��ֱ����<tr>����ʾ������,�˲��ִ��������м���<tbody>
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
					// ���table ����<tbody> ��table ����� tbody Ȼ��table �µı�ǩ�ӵ�tbody
					// �£�������һ��tbody
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
// ר����� xml_http_request �����װ��һ������,���صĶ�������ǽṹ��ȷ��html ������ô˷���ֱ��appendChild

//��xml ����ת����html ����
// �÷� :
function cloneXmlHttpRequest(xml_http_request) {
	if (CheckBrowser() == "IE") {
	 
		for(var i=0;i<xml_http_request.childNodes.length;i++){
		 
			if(xml_http_request.childNodes[i].nodeType==1){// �����Ԫ�ؽڵ�
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

// ʹ��˵��
// ��,��ҳ��ʹ�����´��뽫js�ļ���������:
// <script language=javascript src="ajax.js"></script>

// ��,��ҳ�����sendRequest(...)����:
// ��:<a href="javascript:sendRequest(param,function,'GET')" >����AJAX</a>
// ��:<input type="button" value="�ύ"
// onclick="sendRequest('post.do',param,function,'POST')" />
// ע��(�Ե�һ��Ϊ��):
// ��ʱ�Ͳ����� default.do: ������Ӳ���ajaxͨ��һ����������default.doҳ��,
// param:
// Ϊ����,����Ϊ��,Ҳ���Բ�Ϊ��,����name=value&password=123456,Ҳ����ͨ����һ����(form)���ֶ����������Ϊһ���ַ�������
// ��ȥ,
// function: �����Լ�д��һ������,���ڴ����ص�����,һ��Ĵ�����
// �����ص�������ʾ��ҳ��,һ�����͵�����:
// function search(str){
// alert(str); //���ڵ���.
// myId.innerHTML = str;
// }
// GET: ������ķ���,�򵥵�˵,get��������һ����Դ,post�����ύ������������Դ.
// ����ajax���� get��post ��ȫ���� IE FireFox�����汾,������ IE5.0,6.0,7.0,8.0
// FireeFox2.0,30�¾�����ȫ�����
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
  // alert("���ܽ��� XMLHTTP ����");
		 return false;
	}
	if (method == 'GET') {
		// http_request.onreadystatechange = alertContents;
		http_request.open('GET', url + '?' + argstr, false); // Ŀǰ��ͬ�� true:�첽
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
// �ӳٴ���,��ʱ��Ĵ��� private ,
function alertContents() {
	if (http_request.readyState == 4) {
		if (http_request.status == 200 || http_request.status == 0) {
			callback_func(http_request.responseText);
		} else {
			//alert('����˷���״̬: ' + http_request.statusText);
		}
	} else {
		// alert('���ݼ�����...');
	}

 
}



//ȡ��url�еĸ���������Ӧ��ֵ url��������
//�� ��url �в���strParamName ,�򷵻ؿ��ַ��С���
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
