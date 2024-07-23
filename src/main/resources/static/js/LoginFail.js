/*<![CDATA[*/
var errorMessage = [[${session.errorMessage}]];
if (errorMessage) {
    alert(errorMessage);
    // 메시지를 한 번 표시한 후 세션에서 제거
    [[${session.removeAttribute("errorMessage")}]];
}
/*]]>*/