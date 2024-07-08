
    // 체크박스 전체 선택
    $(".checkboxGroup").on(
        "click",
        "#check_all",
        function () {
    $(this).parents(
        ".checkbox_group").find('input').prop(
            "checked",
        $(this).is(
            ":checked"
    ));
});

    // 체크박스 개별 선택
    $(".checkboxGroup").on(
        "click",
        ".normal",
        function() {
            let is_checked = true;

            $(".checkbox_group .normal").each(function(){
    is_checked = is_checked && $(this).is(":checked");
});

    $("#check_all").prop(
        "checked",
        is_checked);
});
