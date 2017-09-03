function massd2d_webs_fetchTemplate(templateName) {
    console.log("x");
    fetch(massd2d_webs_AppPath + "/src-js/" + templateName + ".html").then(
        function(response) {
            response.text().then(
                function(text) {
                    window[templateName] = text;
                }
            );
        }
    );
};