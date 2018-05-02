// 随机码生成
// length 随机码的长度
function makeValidtionCode(length) {
	var random = 0;
	var code = '';
	for (var i = 0; i < length; i++) {
		random = (74 * Math.random()) + 48;
		if (!((random > 57 && random < 65) || (random > 90 && random < 97))) {
			code += String.fromCharCode(random);
		} else {
			i--;
		}
	}
	return code;
}
// 随机颜色生成
// code 随机码
function makeHtmlCode(code) {
	var gValue = 0;
	var cValue = '';
	var htmlCode = '';
	for (var i = 0; i < code.length; i++) {
		cValue = '';
		for (var j = 0; j < 3; j++) {
			gValue = Math.floor(Math.random() * 255);
			cValue += gValue.toString(16).toUpperCase();
		}
		cValue = '#' + cValue;
		htmlCode += '<span style="color:' + cValue + ';padding-left:5px;width:82px;height:36px">' + code.charAt(i) + '</span>';
	}
	return htmlCode;
}