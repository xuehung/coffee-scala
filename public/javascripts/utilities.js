function urlToPage(n) {
	var queryString = "page=" + n +
		"&size=" + document.size;
	if(typeof document.query != "undefined")
		queryString += "&query=" + JSON.stringify(document.query);
	return (getFileName() + "?" + escape(queryString));
}

function getFileName() {
	return location.pathname.substring(location.pathname.lastIndexOf("/") + 1);
}


function notAllow(){
	alert("您的權限不夠無法操作此動作");
}

function ajaxErrorMsg(){
	alert("網路錯誤");
}

function isUndefined(obj) {
	return (typeof obj == "undefined")? true : false;
}

function ajaxRequest(method, url, data, callbackIfData) {
	$.ajax({
		type: method,
		url: url,
		dataType: "json",
		data: data,
		success: function(data) {
			if(data) {
				callbackIfData(data);
			} else {
				ajaxErrorMsg();
			}
		},
	    error:function() {
	    	ajaxErrorMsg();
	    },
	    complete:function(){
		}
	});
}

/*
 * $div should be the parent element which contains
 * <div class='pagination-centered' id='pagination-div'>
 *     <ul class='pagination'></ul>
 * <div>
 */

function genPageTable($div, headerHtml, url, postData, drawRow, page, size) {
	var doc = $div;
	doc.html("");
	var paginationHtml = "<div class='pagination-centered' id='pagination-div'> \
			<ul class='pagination'></ul> \
		</div>";
	headerHtml = "<div id='table-header'><div class='row'>" + headerHtml + "</div></div>";
	bodyHtml = "<div id='table-body'></div>"
	doc.append(paginationHtml);
	doc.append(headerHtml);
	doc.append(bodyHtml);

	ajaxRequest(
			"POST",
			url,
			JSON.stringify(postData),
			function(data) {
				if (data.result == "success") {
					var totalN = parseInt(data.outcome.total_num);
					tbodyHtml = "";
					// clear table
					for (var i = 0; i < data.outcome.query_result.length; i++) {
						var row = data.outcome.query_result[i];
						var rowHtml = drawRow(row);
						doc.find("#table-body").append(rowHtml);
					}

					// render pagination
					// render pagination from startPage to endPage
					var lastPage = Math.ceil(totalN / size);
					var startPage = (page % 10 == 0) ? page - 9 : page - page
							% 10 + 1;
					// endPage cannot be bigger than last page
					var endPage = (startPage + 9 < lastPage) ? startPage + 9
							: lastPage;
					var paginationUl = doc.find("#pagination-div ul");
					paginationUl.html("");
					for (var i = startPage; i <= endPage; i++) {
						liHtml = "";
						if (i == page) {
							liHtml = "<li class=\"current\"><a href=\"javascript:void(0)\">"
									+ i + "</a></li>";
						} else {
							liHtml = "<li><a href=\"javascript:void(0)\">" + i
									+ "</a></li>";
						}
						paginationUl.append(liHtml);
					}

					var smallerPage = (startPage - 10 > 1) ? startPage - 10 : 1;
					var biggerPage = (endPage + 1 < lastPage) ? endPage + 1
							: lastPage;
					var skipHtml = "<li class=\"unavailable\">&hellip;</li>";
					if (smallerPage < startPage) {
						paginationUl.prepend(skipHtml);
						paginationUl
								.prepend("<li><a href=\"javascript:void(0)\">"
										+ smallerPage + "</a></li>");
					}
					if (biggerPage < lastPage) {
						paginationUl.append(skipHtml);
						paginationUl
								.append("<li><a href=\"javascript:void(0)\">"
										+ biggerPage + "</a></li>");
					}

					$("#pagination-div").on("click", "a", function(){
		  	  			var page = parseInt($(this).html());
		  	  			genPageTable($div, headerHtml, url, postData, drawRow, page, size);
		  	  		});
				} else {
					alert(data.error_msg);
				}
			});
}

function genLogTable(divName, category, page, size) {
	page = page ||  1;
	size = size || 15;
	var doc = $("#"+divName);
	var tableHtml =
		"<div class='pagination-centered' id='pagination-div'> \
			<ul class='pagination'></ul> \
		</div> \
		<table>	\
			<thead>	\
				<tr> \
					<th></th> \
					<th width='140px'>時間</th> \
					<th width='110px'>帳號</th> \
					<th width='60px'>動作</th> \
					<th width='400px'>內容</th> \
				</tr> \
			</thead> \
			<tbody id='log-tbody'> \
			</tbody> \
		</table> ";

	doc.html(tableHtml);

	//send request
	var postData = {
		category : category,
		page : page,
		size : size
	};
	ajaxRequest(
			"POST",
			CRM_CONSTANT.ACTIONLOG_API_URL,
			JSON.stringify(postData),
			function(data) {
				if (data.result == "success") {
					var totalN = parseInt(data.outcome.total_num);
					tbodyHtml = "";
					// clear table
					doc.find("#log-tbody").html(tbodyHtml);
					for (var i = 0; i < data.outcome.query_result.length; i++) {
						var log = data.outcome.query_result[i];
						var index = (page - 1) * size + i + 1;
						var detail = "";
						try {
						   detail = JSON.stringify(JSON.parse(log.detail), undefined, 2);
						   detail = "<pre>" + detail + "</pre>";
						} catch(e) {
						   detail = log.detail;
						}
						var trHtml = "<td>" + index + "</td>" + "<td>"
								+ new Date(log.time).toLocaleString() + "</td>"
								+ "<td>" + log.account + "</td>"
								+ "<td>" + log.action+ "</td>"
								+ "<td>" + detail + "</td>";

						tbodyHtml += ("<tr>" + trHtml + "</tr>");
					}
					doc.find("#log-tbody").html(tbodyHtml);

					// render pagination
					// render pagination from startPage to endPage
					var lastPage = Math.ceil(totalN / size);
					var startPage = (page % 10 == 0) ? page - 9 : page - page
							% 10 + 1;
					// endPage cannot be bigger than last page
					var endPage = (startPage + 9 < lastPage) ? startPage + 9
							: lastPage;
					var paginationUl = doc.find("#pagination-div ul");
					paginationUl.html("");
					for (var i = startPage; i <= endPage; i++) {
						liHtml = "";
						if (i == page) {
							liHtml = "<li class=\"current\"><a href=\"javascript:void(0)\">"
									+ i + "</a></li>";
						} else {
							liHtml = "<li><a href=\"javascript:void(0)\">" + i
									+ "</a></li>";
						}
						paginationUl.append(liHtml);
					}

					var smallerPage = (startPage - 10 > 1) ? startPage - 10 : 1;
					var biggerPage = (endPage + 1 < lastPage) ? endPage + 1
							: lastPage;
					var skipHtml = "<li class=\"unavailable\">&hellip;</li>";
					if (smallerPage < startPage) {
						paginationUl.prepend(skipHtml);
						paginationUl
								.prepend("<li><a href=\"javascript:void(0)\">"
										+ smallerPage + "</a></li>");
					}
					if (biggerPage < lastPage) {
						paginationUl.append(skipHtml);
						paginationUl
								.append("<li><a href=\"javascript:void(0)\">"
										+ biggerPage + "</a></li>");
					}

					$("#pagination-div").on("click", "a", function(){
		  	  			var page = parseInt($(this).html());
		  	  			genLogTable(divName, category, page, size);
		  	  		});
				} else {
					alert(data.error_msg);
				}
			});

}

function clock(){
	var now = new Date().toLocaleString();
	$('#now-time').html(now);
	setTimeout("clock()",1000);
}
