function massd2d_webs_getGameFileDownloadCount(fileName, targetElement) {
    var url = massd2d_webs_AppPath + "/accessHistorySum?key=" + encodeURIComponent(fileName);
    fetch(url).then(
        function(response) {
            response.text().then(function(data) {
                targetElement.innerText = "" + data;
                console.log(data);
            })
        }
    );
};
function massd2d_webs_makeDownloadGameFilePanel(fileName) {
    var panel = document.createElement("div");
    var link = document.createElement("a");
    link.setAttribute("href", massd2d_webs_AppPath + "/game-files?name=" + encodeURIComponent(fileName));
    link.innerText = fileName;
    panel.appendChild(link);
    var downloadCountText = document.createElement("span");
    downloadCountText.className = "w3-grey";
    downloadCountText.style.marginLeft = "10px";
    downloadCountText.style.paddingLeft = "4px";
    downloadCountText.style.paddingRight = "4px";
    downloadCountText.title = "Количество скачиваний";
    massd2d_webs_getGameFileDownloadCount(fileName, downloadCountText);
    panel.appendChild(downloadCountText);
    return panel;
};
document.getElementById("massd2d_webs_gameFiles").appendChild(
    massd2d_webs_makeDownloadGameFilePanel("massd2d-2017.09.20.zip")
);