function personalPage() {
    let user = JSON.parse(localStorage.getItem("user"));
    if (!user) {
        alert('请登录');
    }
	if (user && user.level && user.level === 1) {
		window.location = '/front/accountAdminInfo.html';
	}
	if (user && user.level && user.level === 2) {
		window.location = '/front/accountBusinessInfo.html';
	}
	if (user && user.level && user.level === 3) {
		window.location = '/front/accountUserInfo.html';
	}

}