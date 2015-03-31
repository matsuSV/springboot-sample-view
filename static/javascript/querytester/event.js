/*
 * データベースへの接続を確認する処理
 */
function verifyConnection(clazz, url, user, password, db2v10driver) {
	var _clazz        = document.getElementById(clazz);
	var _url          = document.getElementById(url);
	var _user         = document.getElementById(user);
	var _password     = document.getElementById(password);
	var _db2v10driver = document.getElementById(db2v10driver).checked;

	$.ajax(
			{
				type: "GET",
				url : "/connect",
				data: {
					"clazz"        : $(_clazz).val(),
					"url"          : $(_url).val(),
					"user"         : $(_user).val(),
					"password"     : $(_password).val(),
					"db2v10driver" : _db2v10driver
				},
				success: function(data, status, xhr) {
					if( eval(xhr.responseText) ) {
						alert("Success ! ＼(＾o＾)／");
					} else {
						alert("Error ! (´・ω・｀)");
					}
				},
				error: function() {
					alert("Error ! (/。＼)");
				}
			}
		);
}
