function checkIfDisplayed(element) {
    var style = window.getComputedStyle(element);
    return (style.display !== 'none')
}