// 去掉字符左端的的空白字符
String.prototype.leftTrim = function() {
	return this.replace(/^\s*/g, "");
}
// 去掉字符右端的空白字符
String.prototype.rightTrim = function() {
	return this.replace(/\s*$/g, "");
}
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

// 判断字符串能不能以指定的字符串结束
String.prototype.endsWith = function(str) {
	return this.substr(this.length - str.length) == str;
}


// 判断字符串能不能以指定的字符串开始
String.prototype.startsWith = function(str) {
	return this.substr(0, str.length) == str;
}