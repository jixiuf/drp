// ȥ���ַ���˵ĵĿհ��ַ�
String.prototype.leftTrim = function() {
	return this.replace(/^\s*/g, "");
}
// ȥ���ַ��Ҷ˵Ŀհ��ַ�
String.prototype.rightTrim = function() {
	return this.replace(/\s*$/g, "");
}
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

// �ж��ַ����ܲ�����ָ�����ַ�������
String.prototype.endsWith = function(str) {
	return this.substr(this.length - str.length) == str;
}


// �ж��ַ����ܲ�����ָ�����ַ�����ʼ
String.prototype.startsWith = function(str) {
	return this.substr(0, str.length) == str;
}