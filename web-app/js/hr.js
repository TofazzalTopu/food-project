$(document).ready(function() {
//        $('input.only-number').format({precision: 2, allow_negative: false, autofix: true}).css('text-align','right');
    // ajax history


    $("#layout-body-ajax-loader").hide();
    function loadAjaxContents(url) {
        if (!checkABC(url)) {
            //alert(url);
            if (url != "")
                openUri(CONTEXT_PATH + url);
        }
    }

    $.history.init(loadAjaxContents);

    $('.ajax-history-menu a').live('click', function(e) {
        var url = $(this).attr('href');
        url = url.replace(/^.*#/, '');
        $.history.load(url);
        return false;
    });
    $("#hr-ajax-musk").hide();
    $("#layout-body-ajax-loader").hide();
});
function checkABC(str) {
    var pattern = /^tab-/
    if (str.match(pattern)) {
//      alert('matches');
        return true;
    } else {
//      alert('no match');
        return false;
    }
}
//-- Start HR
function openUri(uri) {

    if (REF_XHR != null) {
        REF_XHR.abort();
    }
    //alert(uri);
    //here the dashboard menu will be appeared
    loadDashBoardMenu(uri);
    ajaxHrShowSpinner();
    DocuAjaxHrHis.html(uri, null, function(data) {
        ajaxHrHideSpinner();
        jQuery('#layout-body-ajax').html(data);
        REF_XHR = null;
    });

}
function loadDashBoardMenu(uri) {
    var splturi = uri.split('/');
}
//-- End HR

var LoginAjaxHr = {
    showForm: function() {
        DocuAjax.html(CONTEXT_PATH + '/login/ajaxLoginForm.gsp', {}, function(response) {
            $.fancybox(response, {'hideOnOverlayClick':false, 'showCloseButton':false, 'enableEscapeButton':false});
        });
    },

    login: function() {
        $('#login-message-id').html("");
        var url = CONTEXT_PATH + '/j_spring_security_check';
        var data = $('#login-ajax-form').serialize();
        DocuAjax.json(url, data, function(response) {
            if (response.success) {
                $.fancybox.close();
                location.reload();
            }
            else {
                $('#login-message-id').html("Incorrect login username or password.");
            }
        });
    }
};


var DocuAjaxHrHis = {
    encodeUrl: function(url) {
        //if(url.substring(0, 1) == '/'){
        //    url = url.substring(1);
        //}
        //return encodeURIComponent(url);
        return url;
    },

    json: function(url, data, callback) {
        callback = typeof callback == "function" ? callback : function() {
        };
        REF_XHR = $.ajax({
                    url: DocuAjaxHrHis.encodeUrl(url),
                    type: 'post',
                    dataType: "json",
                    data: data,
                    success: function(response, textStatus, jqXHR) {
//                        alert(response.statusCode);
//                        var res = JSON.parseJSON(response);
                        if (response.statusCode == 1001) {
                            LoginAjaxHr.showForm();
                        }
                        else {
                            callback(response);
                        }
                        if(response.isBusinessDayOpen == 'false'){
                            alert("Business Day is Closed.")
                        }
//                        alert(res);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        ajaxHrHideSpinner();
                        MessageRenderer.renderErrorText("Unable to process request <br/>" + jqXHR.statusText + " (Response status: " + jqXHR.status + ")");
                    }
                });
    },

    html: function(url, data, callback) {
        callback = typeof callback == "function" ? callback : function() {
        };
        REF_XHR = $.ajax({
                    url: DocuAjaxHrHis.encodeUrl(url),
                    type: 'post',
                    dataType: "html",
                    data: data,
                    success: function(response, textStatus, jqXHR) {
                        var json = null;

                        try {
                            json = JSON.parse(response);
                            //alert(json.statusCode);
                            if (json.statusCode == 1001) {
                                LoginAjaxHr.showForm();
                            }


                        }
                        catch(e) {
                            json = null;
                        }
                        if (json == null) {
                            callback(response);
                        }
                        if(response == '["isBusinessDayOpen":"false"]'){
                            alert("Business Day is FN Closed.");
                            redirectTo(CONTEXT_PATH+'/businessDay/index');
                            //redirectTo('#/businessDay/index');
                        }
//                        if (response.statusCode == 1001) {
//                            LoginAjaxHr.showForm();
//                        }
//                        else {
//                            callback(response);
//                        }
//                        if(response.isBusinessDayOpen == 'false'){
//                            alert("Business Day is Closed.")
//                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        ajaxHrHideSpinner();
                        MessageRenderer.renderErrorText("Unable to process request <br/>" + jqXHR.statusText + " (Response status: " + jqXHR.status + ")");
                    }
                });
    }
};
var DocuAjaxHr = {
    encodeUrl: function(url) {
        return url;
    },

    json: function(url, data, callback, callbackerror) {
        callback = typeof callback == "function" ? callback : function() {
        };
        callbackerror = typeof callbackerror == "function" ? callbackerror : function() {
        };
        TMP_REF_XHR = $.ajax({
                    url: DocuAjaxHr.encodeUrl(url),
                    type: 'post',
                    dataType: "json",
                    data: data,
                    success: function(response, textStatus, jqXHR) {
//                        alert(response.statusCode);
                        if (response.statusCode == 1001) {
                            LoginAjaxHr.showForm();
                        }
                        else {
                            callback(response);
                        }
                        if(response.isBusinessDayOpen == 'false'){
                            alert("Business Day is Closed.")
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        callbackerror(textStatus);
                        MessageRenderer.renderErrorText("Unable to process request <br/>" + jqXHR.statusText + " (Response status: " + jqXHR.status + ")");
                    }
                });
    },

    html: function(url, data, callback, callbackerror) {
        callback = typeof callback == "function" ? callback : function() {
        };
        callbackerror = typeof callbackerror == "function" ? callbackerror : function() {
        };
        TMP_REF_XHR = $.ajax({
                    url: DocuAjaxHr.encodeUrl(url),
                    type: 'post',
                    dataType: "html",
                    data: data,
                    success: function(response, textStatus, jqXHR) {
//                        var json = null;
//                        try {
//                            json = JSON.parse(response);
//                            alert(json.statusCode);
//                            if (json.statusCode == 1001) {
//                                LoginAjaxHr.showForm();
//                            }
//                        }
//                        catch(e) {
//                            json = null;
//                        }
//                        if (json == null) {
//                            callback(response);
//                        }
                        if (response.statusCode == 1001) {
                            LoginAjaxHr.showForm();
                        }
                        else {
                            callback(response);
                        }
                        if(response.isBusinessDayOpen == 'false'){
                            alert("Business Day is Closed.")
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        callbackerror(textStatus);
                        MessageRenderer.renderErrorText("Unable to process request <br/>" + jqXHR.statusText + " (Response status: " + jqXHR.status + ")");
                    }
                });
    }
};

function ajaxHrHideSpinner() {

    $("#layout-body-ajax-loader").hide(1000);
    $("#hr-ajax-musk").hide();
}

function ajaxHrHideSpinnerErrorMsg(msg) {
    if (msg.trim() == '') {
        msg = 'Destination unreachable! Please try again later.'
        $('#layout-body-ajax').html('<div style="color: #ff0000; font-weight: bold; font-size: 1.2em;">' + msg + '</div>');
    } else {

        $('#layout-body-ajax').html('<div style="color: #ff0000; font-weight: bold; font-size: 1.2em;">' + msg + '</div>');
    }
}

function ajaxHrShowSpinner() {
    //$('#layout-body-ajax').html('<div id="mainspinner" class="spinner" align="left">' +
    //        '<img src="${request.contextPath}/images/spinner.gif" alt="Spinner"/></div>');
    $("#layout-body-ajax-loader").show(500);
//    $('div.ui-layout-center').html('');
    $("#hr-ajax-musk").show();
}

function showAjaxLoader(loadimage) {
    $(loadimage).html('<div id="mainspinner" class="spinner" align="left">' +
            '<img src="${request.contextPath}/images/spinner.gif" alt="Spinner"/> Please wait while system is loading your request...</div>');
}

function redirectTo(actionUrl) {
//    ajaxHrShowSpinner();
//        actionUrl = "#/hrPayrollPayStructure/edit/" + hrPayrollPayStructureId;
    window.location = actionUrl;
}


JSON.stringify = JSON.stringify || function (obj) {
    var t = typeof (obj);
    if (t != "object" || obj === null) {
        // simple data type
        if (t == "string") obj = '"' + obj + '"';
        return String(obj);
    }
    else {
        // recurse array or object
        var n, v, json = [], arr = (obj && obj.constructor == Array);
        for (n in obj) {
            v = obj[n];
            t = typeof(v);
            if (t == "string") v = '"' + v + '"';
            else if (t == "object" && v !== null) v = JSON.stringify(v);
            json.push((arr ? "" : '"' + n + '":') + String(v));
        }
        return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
    }
};

function reset_form(formName) {
    $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
        if (this.type == 'hidden') {
            this.value = "";
        } else {
            this.value = this.defaultValue;
        }
    });
    $(formName+' select').val('');
//    $('input[type=checkbox]').attr('checked', false);
    $(formName + ' input[name = create-button]').attr('value', 'Create');
    $(formName + ' input[name = delete-button]').hide();
}
function clear_form(formName) {
    $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
        if (this.type == 'hidden') {
            this.value = "";
        } else {
            this.value = this.defaultValue;
        }
    });
    $(formName).validationEngine('hideAll');
    $('input[type=checkbox]').attr('checked', false);
    $(formName + ' input[name = create-button]').attr('value', 'Create');
    $(formName + ' input[name = delete-button]').hide();
}


function trim_form() {
    $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
        this.value = $.trim(this.value);
    });
}

function loadallcommonfunctions(formname) {
    // for tab/Enter key
    CursorListener.setFocusOnFirstField();
    textboxes = $("input:visible:not([disabled],[readonly]),select,button,textarea");
    $(textboxes).keypress(function (e) {
        if ($(this).is(':input[type=button]')) {
            if (e.keyCode == 13 ) {
                $(this).click();
            }else if(e.keyCode == 9){
//                   alert(e.keyCode);
                e.preventDefault();
                var inputs = $(this).closest('form').find(':input:visible');
                inputs.eq( inputs.index(this)+ 1 ).focus();
            }
        }
        else {
            $(textboxes).keypress(checkForEnter);
        }

    });
//    textboxes = $("input:visible:not([disabled],[readonly]),select,button");
//    if ($.browser.mozilla) {
//        $(textboxes).keypress(checkForEnter);
//    } else {
//        $(textboxes).keydown(checkForEnter);
//    }
    $(textboxes).focusin(function() {
        $(this).css('background', '#EEF7FF');

    });
    $(textboxes).focusout(function() {
        $(this).css('background', '#fff');
    });
    $(formname).validationEngine({   //    client side validation
                isOverflown: true,
                overflownDIV: ".ui-layout-center"
            });

    $(formname).validationEngine('attach');

    $(".time").timepicker({
                showSecond: true,
                timeFormat: 'hh:mm:ss'
            });
    $(".datefld").datepicker({
                showOn: "both",
                buttonImage: "js/datepicker/images/calendar.png",
                buttonImageOnly: true,
                buttonText: 'Date-picker',
                dateFormat: 'dd-mm-yy'
            }).attr('readonly', 'true');


    $('input.only-number').format({precision: 2, allow_negative: false, autofix: true}).css('text-align', 'right');
}

function zeroPad(num, numZeros) {
    var n = Math.abs(num);
    var zeros = Math.max(0, numZeros - Math.floor(n).toString().length );
    var zeroString = Math.pow(10,zeros).toString().substr(1);

    return zeroString+n;
}

function DaysInMonth(yr,mn)
{
    console.debug(new Date(yr, mn-1, 32));
    console.debug(new Date(yr, mn-1, 32).getDate());
    return 32 - new Date(yr, mn-1, 32).getDate();
}


// TaskApprovalRequest common functions
function executeTaskApprovalRequestOnApprove(domainname, gridname, dmclz, id) {
    if(!confirm("Are you sure that you want to approve this request?")){
        return;
    }
    var actionUrl = null;
    actionUrl = CONTEXT_PATH+"/"+domainname+"/onapprovalJSON?dclz="+dmclz+"&onevent=approved&tid=" + id;
    jQuery.ajax({
        type:'post',
        url: actionUrl,
        success:function(data, textStatus) {
            // reload the grid
            jQuery(gridname).trigger('reloadGrid');
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
        },
        complete:function() {
        },
        dataType:'json'
    });
    return false;
}
function executeTaskApprovalRequestOnReject(domainname, gridname, dmclz, id) {
    if(!confirm("Are you sure that you want to reject this request?")){
        return;
    }
    var actionUrl = null;
    actionUrl = CONTEXT_PATH+"/"+domainname+"/onapprovalJSON?dclz="+dmclz+"&onevent=rejected&tid=" + id;
    jQuery.ajax({
        type:'post',
        url: actionUrl,
        success:function(data, textStatus) {
            // reload the grid
            jQuery(gridname).trigger('reloadGrid');
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
        },
        complete:function() {
        },
        dataType:'json'
    });
    return false;
}


function getemplstJsonFromCookie(){
    return  $.jStorage.get('empInfo');
}

function isValidEmpPin(emppin){
    var emplst = $.jStorage.get('empInfo');
    var flg = true;
    jQuery.map(emplst, function(obj) {
        if(obj.employee_pin == emppin)
            flg = false;
    });
    if(flg)
        return false;
    else
        return true;
}

function findEmployeeById(empid){
    var emplst = $.jStorage.get('empInfo');
    jQuery.grep(emplst, function(obj) {
        return obj.id === empid;
    });
}

function findEmployeeByPin(emppin){
    var emplst = $.jStorage.get('empInfo');
    jQuery.grep(emplst, function(obj) {
        return obj.employee_pin === emppin;
    });
}

function findAllEmployeeByOffice(officeId){
    var emplst = $.jStorage.get('empInfo');
    jQuery.grep(emplst, function(obj) {
        return obj.officeId === officeId;
    });
}

function findAllEmployeeByDesignation(){

}

function getEmployeeId(emppin){
    var emplst = $.jStorage.get('empInfo');
    jQuery.map(emplst, function(obj) {
        if(obj.employee_pin == emppin)
            return obj.id;
    });
}


function changePageTitle(title){
//    document.title = "SBICLOUD - HR - " + title;
    document.title =  title;
}