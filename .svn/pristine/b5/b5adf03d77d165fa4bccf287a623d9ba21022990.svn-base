var MessageType = {
    MessageTypeSuccess: 'success',
    AlertTypePass: 'pass',
    AlertTypeError: 'error',
    AlertTypeLoad: 'load'
};

var MessageRenderer = {
    themes: {'0':"error",'1':"success",'2':"warn",'3':"confirm",'4':"info"},

    render: function(json, callback) {
        if (typeof json == 'undefined') {
            return false;
        }
        if(!callback){
            callback = function(){};
        }
        $.jGrowl.defaults.position = 'bottom-right';
        $.jGrowl(json.messageBody, {
            header:json.messageTitle,
            theme: MessageRenderer.themes[json.type],
            sticky: false,
            closer: true,
            corners: '10px',
            easing: 'swing',
            life: 15000,
            beforeClose: callback
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

    renderMessageList: function(json) {
        if (typeof json == 'undefined') {
            return false;
        }

        var messageBody = new Array();
        if ($.isArray(json.messageBody)) {
            for (key in json.messageBody) {
                messageBody.push(json.messageBody[key]['defaultMessage']);
            }
        } else {
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
            life: 15000
        });
    },

    alert: function(selectorId, messageText, messageType){
        $('#'+selectorId).validationEngine('showPrompt', messageText, messageType ? messageType : MessageType.AlertTypeError);
    },

    showHeaderMessage: function(messageText, messageType){
        var panel = $('#docu-message-header');
        if(panel.length == 0){
            $('.sbi-layout-body-container h1').after('<div id="docu-message-header" class="docu-message-header ui-corner-all error pg_error">'+messageText+'</div>')
            $('#docu-message-header').slideDown();
        }
        else{
            $('#docu-message-header').slideUp('fast', function(){
                $('#docu-message-header').html(messageText).slideDown();
            });
        }
    },

    hideHeaderMessage: function(){
        $('#docu-message-header').slideUp();
    }
};

// $('body').append('<div id="message-renderer" class="message-renderer"></div>');
// $('#message-renderer').jGrowl(json.message, {
//     theme: 'flora',
//     header: "Flora makes Fauna",
//     sticky: true,
//     closer: false
// });