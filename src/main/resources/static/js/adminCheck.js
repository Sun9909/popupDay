// 아이디 중복 확인 함수
function id_overlap_check() {
    var user_id_input = document.querySelector('input[name="user_id"]'); // 'user_id' input 요소를 선택
    var original_user_id = user_id_input.getAttribute('data-original-value');

    // 아이디 입력 값이 비어있는지 확인
    if (user_id_input.value === '') {
        alert('아이디를 입력해주세요.');
        return Promise.resolve(false);
    }

    // 기존 아이디와 입력 값 비교
    if (user_id_input.value === original_user_id) {
        alert("기존 아이디와 동일합니다");
        return Promise.resolve(false);
    }

    // AJAX 요청을 통해 서버에 아이디 중복 확인 요청
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "/admin/check-id", // 아이디 중복 확인을 위한 서버 URL
            data: {
                'user_id': user_id_input.value // 입력된 아이디 값
            },
            datatype: 'json', // 응답 데이터 타입
            success: function (data) {
                // 서버 응답이 중복 아이디 존재 여부를 나타냄
                if (data) {
                    alert("이미 존재하는 아이디 입니다."); // 중복 아이디인 경우 경고 메시지
                    user_id_input.focus(); // 아이디 입력란에 포커스
                    resolve(false);
                } else {
                    alert("사용가능한 아이디 입니다."); // 중복되지 않은 경우 메시지
                    resolve(true);
                }
            },
            error: function (error) {
                console.error("Error:", error); // 오류 발생 시 콘솔에 오류 출력
                alert("아이디 중복 확인 중 오류가 발생했습니다."); // 사용자에게 오류 메시지 표시
                reject(false);
            }
        });
    });
}

// 닉네임 중복 확인 함수
function user_nikname_overlap_check() {
    var user_nikname_input = document.querySelector('input[name="user_nikname"]'); // 'user_nikname' input 요소를 선택
    var original_user_nikname = user_nikname_input.getAttribute('data-original-value');

    // 닉네임 입력 값이 비어있는지 확인
    if (user_nikname_input.value === '') {
        alert('닉네임을 입력해주세요.');
        return Promise.resolve(false);
    }

    //기존 닉네임과 동일한지 확인
    if(user_nikname_input.value === original_user_nikname) {
        alert('기존 닉네임과 동일합니다');
        return Promise.resolve(false);
    }

    // AJAX 요청을 통해 서버에 닉네임 중복 확인 요청
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "/admin/check-nikname", // 닉네임 중복 확인을 위한 서버 URL
            data: {
                'user_nikname': user_nikname_input.value // 입력된 닉네임 값
            },
            datatype: 'json', // 응답 데이터 타입
            success: function (data) {
                // 서버 응답이 중복 닉네임 존재 여부를 나타냄
                if (data) {
                    alert("이미 존재하는 닉네임 입니다."); // 중복 닉네임인 경우 경고 메시지
                    user_nikname_input.focus(); // 닉네임 입력란에 포커스
                    resolve(false);
                } else {
                    alert("사용가능한 닉네임 입니다."); // 중복되지 않은 경우 메시지
                    resolve(true);
                }
            },
            error: function (error) {
                console.error("Error:", error); // 오류 발생 시 콘솔에 오류 출력
                alert("닉네임 중복 확인 중 오류가 발생했습니다."); // 사용자에게 오류 메시지 표시
                reject(false);
            }
        });
    });
}

function delete_check() {
    if (confirm("회원을 삭제하시겠습니까?") == true) {
        alert("삭제되었습니다");
        return true;
    }
    else {
        return false;
    }
}

function modify_check() {
    if (confirm("회원 정보를 수정하시겠습니까?")) {
        return Promise.all([id_overlap_check(), user_nikname_overlap_check()])
            .then(results => {
                if (results.every(result => result)) {
                    alert("수정되었습니다");
                    return true;
                } else {
                    alert("중복된 정보가 있습니다. 다시 수정해주세요.");
                    return false;
                }
            })
            .catch(error => {
                console.error("Error during validation:", error);
                alert("검증 중 오류가 발생했습니다. 다시 시도해주세요.");
                return false;
            });
    } else {
        alert("다시 수정해주세요");
        return false;
    }
}