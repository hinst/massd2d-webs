function massd2d_webs_makeDownloadGameFilePanel(fileName) {
    var panel = document.createElement("div");
    var link = document.createElement("a");
    link.setAttribute("href", massd2d_webs_AppPath + "/game-files?name=" + encodeURIComponent(fileName));
    link.innerText = fileName;
    panel.appendChild(link);
    return panel;
};
document.getElementById("massd2d_webs_gameFiles").appendChild(
    massd2d_webs_makeDownloadGameFilePanel("massd2d-2017.09.16.zip")
);