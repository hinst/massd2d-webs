function massd2d_webs_checkIfDisplayed(element) {
    var style = window.getComputedStyle(element);
    return (style.display !== 'none')
};
function massd2d_webs_makeHtmlImagePanel(url, width) {
    var template = document.getElementById("massd2d_webs_imagePanelTemplate").innerHTML;
    return template.replace(/\$url/, url).replace(/\$url/, url).replace(/\$template_width\$/, "" + width);
};