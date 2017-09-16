/// <reference path="common.js"/>
function massd2d_webs_displayCommitHistory(data) {
    var table = document.getElementById("massd2d_webs_commitHistoryTable");
    var rowTemplate = document.getElementById("massd2d_webs_commitHistoryRowTemplate").innerHTML;
    var rowHtmls = [];
    for (var i = 0; i < data.length; ++i) {
        var rowHtml = rowTemplate
            .replace(/\$moment/, data[i].moment)
            .replace(/\$message/, data[i].message);
        rowHtmls.push(rowHtml);
    }
    table.innerHTML = rowHtmls.join("");
};
function massd2d_webs_receiveCommitHistory(data) {
    if (data) {
        massd2d_webs_displayCommitHistory(data.history);
        console.log(data.latestUpdateMoment);
        var moment = new Date(data.latestUpdateMoment);
        document.getElementById("massd2d_commitHistory_updateTime").innerText = moment.toLocaleString();
        document.getElementById("massd2d_commitHistory_readTime").innerText = "" + Math.round(data.readingTime / 1000 / 1000) / 1000;
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
            massd2d_webs_receiveCommitHistory(data);
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
massd2d_webs_makeToggler(
    document.getElementById("massd2d_webs_commitHistory_showSubInfoButton"),
    document.getElementById("massd2d_webs_commitHistory_subInfoPanel"), 
    "block"
);
massd2d_webs_makeToggler(
    document.getElementById("massd2d_webs_commitHistory_showDescriptionButton"),
    document.getElementById("massd2d_webs_commitHistory_descriptionPanel"), 
    "block"
);
