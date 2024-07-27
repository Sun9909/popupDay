$("#frames").on("load", function() {
    let head = $("#frames").contents().find("head");
    head.append("<link rel='stylesheet' href='/js/admin.css' type='text/css'>");
});


