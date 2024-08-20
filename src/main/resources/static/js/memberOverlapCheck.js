document.addEventListener('DOMContentLoaded', function() {
    let isUserIdValid = false;
    let isEmailValid = false;
    let isNicknameValid = false;
    let isPasswordMatch = false;

    function updateSubmitButtonState() {
        let submitButtons = document.querySelectorAll('input[type="submit"]');
        submitButtons.forEach(function(submitButton) {
            submitButton.disabled = !(isUserIdValid && isEmailValid && isNicknameValid && isPasswordMatch);
        });
        console.log('제출 버튼 상태 업데이트됨:', isUserIdValid, isEmailValid, isNicknameValid, isPasswordMatch);
    }

    function showAlertIfSubmitDisabled() {
        if (!isUserIdValid || !isEmailValid || !isNicknameValid || !isPasswordMatch) {
            Swal.fire({
                icon: 'warning',
                title: '중복 확인 및 비밀번호 확인을 해주세요.',
                showConfirmButton: true
            });
        }
    }

    function addEventListenerIfExists(selector, event, handler) {
        const element = document.querySelector(selector);
        if (element) {
            element.addEventListener(event, handler);
        }
    }

    function email_overlap_check() {
        let email = document.querySelector('input[name="email"]').value;
        if (email === '') {
            Swal.fire({
                icon: 'warning',
                title: '이메일을 입력해주세요.'
            });
            return;
        }
        $.ajax({
            url: "/login/check-email",
            data: { 'email': email },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    Swal.fire({
                        icon: 'error',
                        title: '이미 존재하는 이메일입니다.'
                    });
                    isEmailValid = false;
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '사용 가능한 이메일입니다.'
                    });
                    isEmailValid = true;
                }
                updateSubmitButtonState();
            },
            error: function() {
                Swal.fire({
                    icon: 'error',
                    title: '이메일 중복 확인 중 오류가 발생했습니다.'
                });
                isEmailValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function id_overlap_check() {
        let userId = document.querySelector('input[name="user_id"]').value;
        if (userId === '') {
            Swal.fire({
                icon: 'warning',
                title: '아이디를 입력해주세요.'
            });
            return;
        }
        $.ajax({
            url: "/login/check-id",
            data: { 'user_id': userId },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    Swal.fire({
                        icon: 'error',
                        title: '이미 존재하는 아이디입니다.'
                    });
                    isUserIdValid = false;
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '사용 가능한 아이디입니다.'
                    });
                    isUserIdValid = true;
                }
                updateSubmitButtonState();
            },
            error: function() {
                Swal.fire({
                    icon: 'error',
                    title: '아이디 중복 확인 중 오류가 발생했습니다.'
                });
                isUserIdValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function checkPasswordMatch() {
        let password = document.querySelector('input[name="pwd"]').value;
        let confirmPassword = document.querySelector('input[name="pwd_confirm"]').value;
        if (password === confirmPassword) {
            Swal.fire({
                icon: 'success',
                title: '비밀번호가 일치합니다.'
            });
            isPasswordMatch = true;
        } else {
            Swal.fire({
                icon: 'error',
                title: '비밀번호가 일치하지 않습니다.'
            });
            isPasswordMatch = false;
        }
        updateSubmitButtonState();
    }

    function user_nickname_overlap_check() {
        let nickname = document.querySelector('input[name="user_nickname"]').value;
        if (nickname === '') {
            Swal.fire({
                icon: 'warning',
                title: '닉네임을 입력해주세요.'
            });
            return;
        }
        $.ajax({
            url: "/login/check-nickname",
            data: { 'user_nickname': nickname },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    Swal.fire({
                        icon: 'error',
                        title: '이미 존재하는 닉네임입니다.'
                    });
                    isNicknameValid = false;
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '사용 가능한 닉네임입니다.'
                    });
                    isNicknameValid = true;
                }
                updateSubmitButtonState();
            },
            error: function() {
                Swal.fire({
                    icon: 'error',
                    title: '닉네임 중복 확인 중 오류가 발생했습니다.'
                });
                isNicknameValid = false;
                updateSubmitButtonState();
            }
        });
    }

    addEventListenerIfExists('.email_overlap_button', 'click', email_overlap_check);
    addEventListenerIfExists('.id_overlap_button', 'click', id_overlap_check);
    addEventListenerIfExists('.pwd_overlap_button', 'click', checkPasswordMatch);
    addEventListenerIfExists('.user_nickname_overlap_button', 'click', user_nickname_overlap_check);

    const frmMemberLogin = document.querySelector('form[name="frmMemberLogin"]');
    if (frmMemberLogin) {
        frmMemberLogin.addEventListener('submit', function(e) {
            if (!isUserIdValid || !isEmailValid || !isNicknameValid || !isPasswordMatch) {
                Swal.fire({
                    icon: 'warning',
                    title: '중복 확인 및 비밀번호 확인을 해주세요.'
                });
                e.preventDefault();
            }
        });
    }

    let submitButtons = document.querySelectorAll('input[type="submit"]');
    submitButtons.forEach(function(submitButton) {
        submitButton.addEventListener('click', function(e) {
            if (submitButton.disabled) {
                showAlertIfSubmitDisabled();
                e.preventDefault();
            }
        });
    });

    updateSubmitButtonState();
});


// YONI 해시태그 선택하기
document.addEventListener('DOMContentLoaded', function() {
    const showHashtagsBtn = document.getElementById('show-hashtags-btn');
    const submitBtn = document.getElementById('submit-btn');
    const hashtagSelectorContainer = document.getElementById('hashtag-selector-container');
    const displayHashtagsContainer = document.getElementById('display-hashtags-container');
    const hashtagInputs = document.querySelectorAll('#hashtag-form input[type="checkbox"]');

     // 페이지 로드 시 해시태그 선택 창을 숨김
    hashtagSelectorContainer.classList.add('hide');

    // 해시태그 선택 창 열기/닫기
    showHashtagsBtn.addEventListener('click', function() {
        hashtagSelectorContainer.classList.toggle('hide');
        hashtagSelectorContainer.classList.toggle('show');
    });

    // 체크박스 4개 이상 선택 방지
    hashtagInputs.forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            const checkedCount = document.querySelectorAll('#hashtag-form input[type="checkbox"]:checked').length;
            if (checkedCount > 4) {
                this.checked = false;
                   Swal.fire({
                       icon: 'warning',
                       title: '최대 4개까지 선택할 수 있습니다.',
                       confirmButtonText: '확인',
                       confirmButtonColor: '#3085d6'
                   });
               }
           });
       });

    // 해시태그 선택 완료 및 다시 선택하기 처리
    submitBtn.addEventListener('click', function() {
        const selectedHashtags = document.querySelectorAll('#hashtag-form input[type="checkbox"]:checked');

        if (submitBtn.textContent === '입력하기') {
            if (selectedHashtags.length !== 4) {
                 Swal.fire({
                    icon: 'warning',
                    title: '해시태그를 4개 선택해야 합니다.',
                    confirmButtonText: '확인',
                    confirmButtonColor: '#3085d6'
                });
                return;
            }

            // 선택된 해시태그를 표시
            displayHashtagsContainer.innerHTML = '';
            selectedHashtags.forEach(function(checkbox) {
                const hashtagText = checkbox.closest('.hashtag-item').querySelector('.hashtag-text').textContent;
                const hashtagItem = document.createElement('div');
                hashtagItem.className = 'selected-hashtag-item';
                hashtagItem.textContent = hashtagText; // 해시태그 텍스트를 화면에 표시
                displayHashtagsContainer.appendChild(hashtagItem);
            });

            // 해시태그 선택창 숨기기
            hashtagSelectorContainer.classList.add('hide');
            hashtagSelectorContainer.classList.remove('show');

            // 버튼 텍스트 변경
            submitBtn.textContent = '다시 선택하기';

        } else if (submitBtn.textContent === '다시 선택하기') {
            // 해시태그 선택창 다시 열기
            hashtagSelectorContainer.classList.remove('hide');
            hashtagSelectorContainer.classList.add('show');

            // 선택된 해시태그 초기화
            displayHashtagsContainer.innerHTML = '';
            hashtagInputs.forEach(function(checkbox) {
                checkbox.checked = false;
            });

            // 버튼 텍스트 변경
            submitBtn.textContent = '입력하기';
        }
    });
});
