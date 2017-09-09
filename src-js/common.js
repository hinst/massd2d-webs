function massd2d_webs_checkIfDisplayed(element) {
    var style = window.getComputedStyle(element);
    return (style.display !== 'none')
}