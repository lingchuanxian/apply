var onlyOpenTitle = "主页";//不允许关闭的标签的标题
$(function () {
	tabClose();
	tabCloseEven();
});

function getRootPath()   
{   
	var pathName = window.location.pathname.substring(1);   
	var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));   
	return window.location.protocol + '//' + window.location.host + '/'+ webName + '/';   
}   

function changeTheme(themeName) {/* 更换主题 */
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#easyuiTheme').attr('href', href);
		}
	}

	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});
};
if ($.cookie('easyuiThemeName')) {
	changeTheme($.cookie('easyuiThemeName'));
}

function jsonTimeStamp(milliseconds) {
	if (milliseconds != "" && milliseconds != null
			&& milliseconds != "null") {
		var datetime = new Date();
		datetime.setTime(milliseconds);
		var year = datetime.getFullYear();
		var month = datetime.getMonth() + 1 < 10 ? "0"
				+ (datetime.getMonth() + 1) : datetime.getMonth() + 1;
				var date = datetime.getDate() < 10 ? "0" + datetime.getDate()
						: datetime.getDate();
				var hour = datetime.getHours() < 10 ? "0" + datetime.getHours()
						: datetime.getHours();
				var minute = datetime.getMinutes() < 10 ? "0"
						+ datetime.getMinutes() : datetime.getMinutes();
						var second = datetime.getSeconds() < 10 ? "0"
								+ datetime.getSeconds() : datetime.getSeconds();
								return year + "-" + month + "-" + date + " " + hour + ":" + minute
								+ ":" + second;
	} else {
		return "";
	}
}

function jsonYearMonthDay(milliseconds) {
	var datetime = new Date();
	datetime.setTime(milliseconds);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0"
			+ (datetime.getMonth() + 1) : datetime.getMonth() + 1;
			var date = datetime.getDate() < 10 ? "0" + datetime.getDate()
					: datetime.getDate();
			return year + "-" + month + "-" + date;
}

function tabClose() {
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function () {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu', function (e) {
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
		var subtitle = $(this).children(".tabs-closable").text();
		$('#mm').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven() {
	$('#mm').menu({
		onClick: function (item) {
			closeTab(item.id);
		}
	});
	return false;
}

//创建/移除tab
var index = 0;
function addTab(subtitle, url, icon) {
	index++;
	if (!$('#tabs').tabs('exists', subtitle)) {
		$('#tabs').tabs('add', {
			title: subtitle,
			content: createFrame(url),
			closable: true,
			icon: 'icon'
		});
	} else {
		$('#tabs').tabs('select', subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}
function removeTab() {
	var tab = $('#tabs').tabs('getSelected');
	if (tab) {
		var index = $('#tabs').tabs('getTabIndex', tab);
		$('#tabs').tabs('close', index);
	}
}
//创建Frame
function createFrame(url) {
	var s = '<iframe scrolling="no" frameborder="0"  src="' + url + '" style="width:100%;height:99%;"></iframe>';
	return s;
}

//关闭tab
function closeTab(action) {
	var alltabs = $('#tabs').tabs('tabs');
	var currentTab = $('#tabs').tabs('getSelected');
	var allTabtitle = [];
	$.each(alltabs, function (i, n) {
		allTabtitle.push($(n).panel('options').title);
	})
	switch (action) {
	case "refresh":
		var iframe = $(currentTab.panel('options').content);
		var src = iframe.attr('src');
		$('#tabs').tabs('update', {
			tab: currentTab,
			options: {
				content: createFrame(src)
			}
		})
		break;
	case "close":
		var currtab_title = currentTab.panel('options').title;
		if(currtab_title != onlyOpenTitle){
			$('#tabs').tabs('close', currtab_title);
		}
		break;
	case "closeall":
		$.each(allTabtitle, function (i, n) {
			if (n != onlyOpenTitle) {
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "closeother":
		var currtab_title = currentTab.panel('options').title;
		$.each(allTabtitle, function (i, n) {
			if (n != currtab_title && n != onlyOpenTitle) {
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "closeright":
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

		if (tabIndex == alltabs.length - 1) {
			alert('亲，后边没有啦 ^@^!!');
			return false;
		}
		$.each(allTabtitle, function (i, n) {
			if (i > tabIndex) {
				if (n != onlyOpenTitle) {
					$('#tabs').tabs('close', n);
				}
			}
		});
		break;
	case "closeleft":
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
		if (tabIndex == 1) {
			alert('亲，前边那个上头有人，咱惹不起哦。 ^@^!!');
			return false;
		}
		$.each(allTabtitle, function (i, n) {
			if (i < tabIndex) {
				if (n != onlyOpenTitle) {
					$('#tabs').tabs('close', n);
				}
			}
		});
		break;
	}
}