/**
 Upload data to central block
 */

function loadCenterBlock(fileName) {
    $('#central_block').load('./views/' + fileName, function (response, status, xhr) {
        if (status == "error") {
            var msg = "File uploading error:<br />";
            $("#central_block").html(msg + xhr.status + " " + xhr.statusText);
        }
    });
}

/**
 Action after uploading
 */

$(document).ready(function () {
    var fileName = location.href.replace(/.*?!/, '');
    if (fileName != location.href) {
        loadCenterBlock(fileName)
    }

});

/**
 uploading data from html file
 */

function loadHtmlFile(obj) {
    var fileName = $(obj).attr('href').replace(/.*?!/, '');
    if (fileName) {
        loadCenterBlock(fileName);
    }
}