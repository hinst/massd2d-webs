function massd2d_webs_displayCommitHistory(data) {
    var table = document.getElementById("massd2d_webs_commitHistoryTable");
    var rowTemplate = document.getElementById("massd2d_webs_commitHistoryRowTemplate").innerHTML;
    table.innerHTML = "";
    console.log(data.length);
    console.log(rowTemplate);
    for (var i = 0; i < data.length; ++i) {
        table.innerHTML += rowTemplate
            .replace(/\$moment/, data[i].moment)
            .replace(/\$message/, data[i].message);
    }
    document.getElementById("massd2d_webs_commitHistoryTableLoading").style.display = "none";
    document.getElementById("massd2d_webs_commitHistoryTable").style.display = "block";
};
function massd2d_webs_loadCommitHistory() {
    document.getElementById("massd2d_webs_commitHistoryTableLoading").style.display = "block";
    document.getElementById("massd2d_webs_commitHistoryTable").style.display = "none";
    fetch(
        massd2d_webs_AppPath + "/commitHistory"
    ).then(function(response) {
        response.json().then(function(data) {
            console.log(data);
            massd2d_webs_displayCommitHistory(data);
        });
    });
};
function massd2d_webs_receiveCommitHistoryPageLoaded() {
    massd2d_webs_loadCommitHistory();
};
massd2d_webs_receiveCommitHistoryPageLoaded();
document.getElementById("massd2d_webs_commitHistoryRefreshButton").addEventListener("click", function() {
    massd2d_webs_loadCommitHistory();
});
