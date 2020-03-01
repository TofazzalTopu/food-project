var MessageRenderer = {
    themes: {'0':"flora",'1':"flora",'2':"flora",'3':"flora"},

    render: function(json){
        if(typeof json == 'undefined'){
            return false;
        }

        $.jGrowl.defaults.position = 'bottom-right';
        $.jGrowl(json.messageBody, {
            header:json.messageTitle,
            theme: MessageRenderer.themes[json.type],
            sticky: false,
            closer: true,
            corners: '10px',
            easing: 'swing',
            life: 5000
        });
    },
    renderErrorText: function(text, title) {
        if (title == "" || title == null) {
            MessageRenderer.render({"messageBody":text, "messageTitle":"BDFE", "type":"0"});
        } else {
            MessageRenderer.render({"messageBody":text, "messageTitle":title, "type":"0"});
        }
    },

    renderSuccessText: function(text) {
        MessageRenderer.render({"messageBody":text, "messageTitle":"BDFE", "type":"1"});
    },

    renderMessageList: function(json){
        if(typeof json == 'undefined'){
            return false;
        }

        var messageBody = new Array();
        if($.isArray(json.messageBody)){
            for(key in json.messageBody){
                messageBody.push(json.messageBody[key]['defaultMessage']);
            }
        }else{
            messageBody.push(json.messageBody);
        }

        $.jGrowl.defaults.position = 'bottom-right';
        $.jGrowl(messageBody.join('<br/>'), {
            header:json.messageTitle,
            theme: MessageRenderer.themes[json.type],
            sticky: false,
            closer: true,
            corners: '10px',
            easing: 'swing',
            life: 5000
        });
    }
}
