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
};
function massd2d_webs_receiveCommitHistoryPageLoaded() {
    console.log("loaded");
    fetch(
        massd2d_webs_AppPath + "/commitHistory"
    ).then(function(response) {
        response.json().then(function(data) {
            console.log(data);
            massd2d_webs_displayCommitHistory(data);
        });
    });
};
massd2d_webs_receiveCommitHistoryPageLoaded();
