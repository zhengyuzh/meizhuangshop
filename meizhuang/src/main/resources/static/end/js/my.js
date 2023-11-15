function checkValid(entity) {
    if (!entity.name || entity.name === "") {
        alert("name不能为空");
        return false;
    }
    if (entity.phone && entity.phone !== "") {
        let reg = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (!reg.test(entity.phone)) {
            alert("请输入正确的手机号");
            return false;
        }
    }
    if (entity.email && entity.email !== "") {
        let reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        if (!reg.test(entity.email)) {
            alert("请输入正确的邮箱");
            return false;
        }
    }
    if (entity.cardId && entity.cardId !== "") {
        let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (!reg.test(entity.cardId)) {
            alert("请输入正确的身份证号");
            return false;
        }
    }
    return true;
}

function personalPage() {
    let user = JSON.parse(localStorage.getItem("user"));
	if (user && user.level && user.level === 1) {
		window.location = '/end/page/accountAdminInfo.html';
	}
	if (user && user.level && user.level === 2) {
		window.location = '/end/page/accountBusinessInfo.html';
	}
	if (user && user.level && user.level === 3) {
		window.location = '/end/page/accountUserInfo.html';
	}

}

function logout() {
    axios.get('/logout').then(res => {
        if (res.data.code === '0') {
            window.location = '/end/page/login.html';
        } else {
            alert(res.data.msg);
        }
    })
}

/**
* 弹出式提示框，默认1.2秒自动消失
* @param message 提示信息
* @param style 提示样式，有alert-success、alert-danger、alert-warning、alert-info
* @param time 消失时间
*/
let prompt = function (message, style, time)
{
    style = (style === undefined) ? 'alert-success' : style;
    time = (time === undefined) ? 1200 : time;
    $('<div>')
        .appendTo('body')
        .addClass('alert ' + style)
        .html(message)
        .show()
        .delay(time)
        .fadeOut();
};

// 成功提示
let success_prompt = function(message, time)
{
    prompt(message, 'alert-success', time);
};

// 失败提示
let fail_prompt = function(message, time)
{
    prompt(message, 'alert-danger', time);
};

// 提醒
let warning_prompt = function(message, time)
{
    prompt(message, 'alert-warning', time);
};

// 信息提示
let info_prompt = function(message, time)
{
    prompt(message, 'alert-info', time);
};