var massd2d_webs_togglerClosedClass = "w3-black";
var massd2d_webs_togglerOpenedClass = "w3-grey";
function massd2d_webs_checkIfDisplayed(element) {
    var style = window.getComputedStyle(element);
    return (style.display !== 'none')
};
function massd2d_webs_updateTogglerClass(toggler, displayed) {
    if (displayed) {
        toggler.classList.remove(massd2d_webs_togglerClosedClass);
        toggler.classList.add(massd2d_webs_togglerOpenedClass);
    } else {
        toggler.classList.remove(massd2d_webs_togglerOpenedClass);
        toggler.classList.add(massd2d_webs_togglerClosedClass);        
    }
};
function massd2d_webs_makeToggler(toggler, panel, displayStyle) {
    massd2d_webs_updateTogglerClass(toggler, massd2d_webs_checkIfDisplayed(panel));
    toggler.addEventListener("click", function() {
        panel.style.display = massd2d_webs_checkIfDisplayed(panel) ? "none" : displayStyle;
        massd2d_webs_updateTogglerClass(toggler, massd2d_webs_checkIfDisplayed(panel));
    });
};
function massd2d_webs_makeHtmlImagePanel(url, width) {
    var template = document.getElementById("massd2d_webs_imagePanelTemplate").innerHTML;
    return template.replace(/\$url/, url).replace(/\$url/, url).replace(/\$template_width\$/, "" + width);
};