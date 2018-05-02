function deparam(query) {
	var pairs, i, keyValuePair, key, value, map = new Array();
	// remove leading question mark if its there
	if (query.slice(0, 1) === '?') {
		query = query.slice(1);
	}
	if (query !== '') {
		pairs = query.split('&');
		for (i = 0; i < pairs.length; i += 1) {
			keyValuePair = pairs[i].split('=');
			key = decodeURIComponent(keyValuePair[0]);
			value = (keyValuePair.length > 1) ? decodeURIComponent(keyValuePair[1])
					: undefined;
			map.push(value);
		}
	}
	return map.join(",");
}


function set_checkbox_value(domId,value){
	var v=value.split(",");
	$("input[name='"+domId+"']").each(function (i, n) {
		for(var i=0;i<v.length;i++){
			$(n).attr("checked",false);
			if($(n).val()==v[i]){
				$(n).attr("checked","checked");
			}
		}
	});
}