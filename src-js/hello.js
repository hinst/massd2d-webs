function massd2d_webs_hello_setupScreenshots() {
    var container = document.getElementById("massd2d_webs_hello_screenshots");
    function add(file) {
        var html = massd2d_webs_makeHtmlImagePanel(massd2d_webs_AppPath + "/src-img/" + file, 300);
        container.innerHTML += html;
    }
    add("game-screenshot-01.jpg");
    add("game-screenshot-02.jpg");
    add("game-screenshot-03.jpg");
    add("game-screenshot-04.jpg");
}
massd2d_webs_hello_setupScreenshots();
