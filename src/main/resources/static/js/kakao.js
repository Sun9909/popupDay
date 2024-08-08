//카카오 로그인 호출
//REST API
//카카오 로그인 호출
//REST API
function fn_kakao() {
    location.href="https://kauth.kakao.com/oauth/authorize?client_id=c9237e3260b74eea6991180ceca971ca&redirect_uri=http://172.16.104.16:8090/login/popupday/kakao&response_type=code";
}
//
/*function kakaoLogout() {
    Kakao.init('c9237e3260b74eea6991180ceca971ca');
    if(Kakao.isInitialized()) {
        if (Kakao.Auth.getAccessToken()) {
            Kakao.API.request({
                url: '/v1/user/unlink',
                success: function (response) {
                    console.log(response)
                },
                fail: function (error) {
                    console.log(error)
                },
            })
            Kakao.Auth.setAccessToken(undefined)
        }
    }
}*/