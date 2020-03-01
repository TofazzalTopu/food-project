/*
 * ****************************************************************
 * Copyright ? 2010 Documentatm (TM) Limited. All rights reserved.
 * DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * This software is the confidential and proprietary information of
 * Documentatm (TM) Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Documentatm (TM) Limited.
 * *****************************************************************
 */
/*  Client Side validation message */
var CURRENCY_FORMAT = "UGX ";

Date.prototype.addDays = function (days) {
    this.setDate(this.getDate() + days);
    return this;
};

$(document).ready(function () {
    // Disable message log of dynatree
    _canLog = false;
});

$(document).ready(function () {
    $('.readonly-input').each(function () {
        $(this).attr('readOnly', 'readOnly');
    });
});

$(document).ready(function () {
    $('[readonly$=true], .readonly-input').each(function () {
        $(this).css('background-color', '#FFFCE8');
    });
});

function addRemoveCheckBox(object) {
    if (object.checked) {
        object.value = 'true';
    }
    else {
        object.value = 'false';
    }
}

$(document).ready(function () {
    $.extend($.fn.jqGrid, { setData: function (data) {
        this[0].p.data = data;
        return true;
    }});

    //HOW TO USE: $("#jqgrid-grid").jqGrid('addParam', 'whois', 'feroz');
    $.extend($.fn.jqGrid, { addParam: function (key, val) {
        var postData = this.getGridParam('postData');
        postData[key] = val;
        this.setGridParam({postData: postData});
        return true;
    }});

});

$(document).ready(function () {
    $('.thousand-separator').each(function () {
        $(this).val(thousandsSeparator($(this).val()));
    });
});

// Added by Lton for preventing field value
$(document).ready(function () {
//  Check Numerical Field
    $(".check-numerical").format({precision: 0, allow_negative: false, autofix: true});
//  Check Fractional Field
    $('.check-fractional').keypress(function(event) {
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
            ((event.which < 48 || event.which > 57) &&
            (event.which != 0 && event.which != 8))) {
            event.preventDefault();
        }
        var text = $(this).val();
        if ((text.indexOf('.') != -1) &&
            (text.substring(text.indexOf('.')).length > 2) &&
            (event.which != 0 && event.which != 8) &&
            ($(this)[0].selectionStart >= text.length - 2)) {
            event.preventDefault();
        }
    });
});

var Pad = {
    format: function (str, max) {
        return str.length < max ? Pad.format("0" + str, max) : str;
    }
}

var LoginAjax = {
    showForm: function () {
        DocuAjax.html(CONTEXT_PATH + '/login/ajaxLoginForm.gsp', {}, function (response) {
            $.fancybox(response, {'hideOnOverlayClick': false, 'showCloseButton': false, 'enableEscapeButton': false});
        });
    },
    changeOffice: function () {
        DocuAjax.html(CONTEXT_PATH + '/officeDashboard/changeOffice', {}, function (response) {
            $.fancybox(response, {'hideOnOverlayClick': false, 'showCloseButton': true, 'enableEscapeButton': false});
        });
    },

    login: function () {
        $('#login-message-id').html("");
        var url = CONTEXT_PATH + '/j_spring_security_check';
        var data = $('#login-ajax-form').serialize();
        DocuAjax.html(url, data, function (response) {
            var json = null;
            try {
                json = JSON.parse(response);
                if (typeof json == "object") {
                    if (json.error) {
                        $('#login-message-id').html(json.error);
                        return false;
                    }
                    else if (json.hasError) {
                        //$('#login-message-id').html(json.message);
                        var params = "";
                        if (json.params) {
                            params = $.param(json.params)
                        }
                        location.href = CONTEXT_PATH + json.controller + "/" + json.action + "?" + params;
                        return false;
                    }
                    else {
                        $.fancybox.close();
                        //location.reload();
                    }
                }
                else {
                    json = null;
                }
            }
            catch (e) {
                json = null;
            }

            if (json == null) {
                $.fancybox(response, {'hideOnOverlayClick': false, 'showCloseButton': false, 'enableEscapeButton': false});
            }
        });
    },

    setOffice: function () {
        if (!$('input:radio[name=office]').is(':checked')) {
            alert("Please select an office.")
            return false;
        }

        $('#login-message-id').html("");
        var url = CONTEXT_PATH + '/officeDashboard/saveAjax';
        var data = $('#office-ajax-form').serialize();
        DocuAjax.html(url, data, function (response) {
            var json = null;
            try {
                json = JSON.parse(response);
                if (typeof json == "object") {
                    if (json.error) {
                        $('#login-message-id').html(json.error);
                        return false;
                    }
                    else if (json.hasError) {
                        //$('#login-message-id').html(json.message);
                        var params = "";
                        if (json.params) {
                            params = $.param(json.params)
                        }
                        location.href = CONTEXT_PATH + json.controller + "/" + json.action + "?" + params;
                        return false;
                    }
                    else {
                        $.fancybox.close();
                        //location.reload();
                    }
                }
                else {
                    json = null;
                }
            }
            catch (e) {
                json = null;
            }

            if (json == null) {
                $.fancybox(response, {'hideOnOverlayClick': false, 'showCloseButton': false, 'enableEscapeButton': false});
            }
        });
    }
};


var DocuAjax = {
    encodeUrl: function (url) {
        //if(url.substring(0, 1) == '/'){
        //    url = url.substring(1);
        //}
        //return encodeURIComponent(url);
        return url;
    },

    freezeButton: function (buttonId) {
        $('#' + buttonId).addClass("docu-ajax-spinner").attr("disabled", "disabled");
    },

    unFreezeButton: function (buttonId) {
        $('#' + buttonId).removeClass("docu-ajax-spinner").removeAttr("disabled");
    },

    freezeContent: function (selector) {
        var offset = $('#' + selector).offset();
        var _top = 1; //offset.top;
        var _left = 1; //offset.left;
        var _width = $('#' + selector).width() - 2;
        var _height = $('#' + selector).height() - 2;
        //var _div = '<div id="docu-overlay" style="background:rgba(255, 255, 255, 0.6);z-index:100;position:absolute;top:'+_top+'px;left:'+_left+'px;width:'+_width+'px;height:'+_height+'px;"></div>';
        var _div = '<div id="docu-overlay-' + selector + '" style="display:none; float: left; background-color:white; z-index:100; position:absolute; top:' + _top + 'px; left:' + _left + 'px; width:' + _width + 'px; height:' + _height + 'px;"></div>';
        $('#' + selector).css({position: 'relative'}).append(_div);
        $('#docu-overlay-' + selector).fadeTo('slow', '0.7');
    },

    unFreezeContent: function (selector) {
        //$('#docu-overlay').remove();
        $('#docu-overlay-' + selector).fadeOut('fast', function () {
            $('#docu-overlay-' + selector).remove();
        });
    },

    json: function (url, data, callback) {
        callback = typeof callback == "function" ? callback : function () {
        };
        if (data.buttonId) {
            DocuAjax.freezeButton(data.buttonId);
        }
        if (data.containerId) {
            DocuAjax.freezeContent(data.containerId);
        }
        $.ajax({
            url: DocuAjax.encodeUrl(url),
            type: 'post',
            dataType: "json",
            data: data,
            success: function (response, textStatus, jqXHR) {
                //if (response.statusCode == 1001) {
                //    LoginAjax.showForm();
                //}
                //else {
                //    callback(response);
                //}
                callback(response);
                if (data.buttonId) {
                    DocuAjax.unFreezeButton(data.buttonId);
                }
                if (data.containerId) {
                    DocuAjax.unFreezeContent(data.containerId);
                }
            },
            error: function (xhr, textStatus, err) {
                var errorMessage = "";
                if (xhr.status == 403 || xhr.status == 1001) {
                    LoginAjax.showForm();
                    return;
                }
                SubmissionLoader.hideFrom()
                if (xhr.status == 302 || xhr.status == 401) {
                    SubmissionLoader.hideFrom()
                    MessageRenderer.renderErrorText("You are not authorized to do the operation.");
                    return
                }

                if (xhr.status == 0) {
                    errorMessage = "You are offline!! Please Check Your Network.";
                } else if (xhr.status == 404) {
                    errorMessage = "Requested page not found.";
                } else if (xhr.status == 500) {
                    errorMessage = "Internal Server Error.";
                } else if (err == 'parsererror') {
                    errorMessage = "Error: Parsing JSON Request failed.";
                } else if (err == 'timeout') {
                    errorMessage = "Request Time out.";
                } else if (xhr.status == 402) {
                    alert("Sorry you are not authorize to view this page");
                } else {
                    errorMessage = "Unknown Error." + xhr.responseText;
                }
                MessageRenderer.renderErrorText(errorMessage)

            }
        });
    },

    html: function (url, data, callback) {
        callback = typeof callback == "function" ? callback : function () {
        };
        if (data.buttonId) {
            DocuAjax.freezeButton(data.buttonId);
        }
        if (data.containerId) {
            DocuAjax.freezeContent(data.containerId);
        }
        SubmissionLoader.showTo();
        $.ajax({
            url: DocuAjax.encodeUrl(url),
            type: 'post',
            dataType: "html",
            data: data,
            success: function (response, textStatus, jqXHR) {
                //var json = null;
                //try {
                //    json = JSON.parse(response);
                //    if (typeof json == "object" && json.statusCode == 1001) {
                //        LoginAjax.showForm();
                //    }
                //    else {
                //        json = null;
                //    }
                //}
                //catch (e) {
                //    json = null;
                //}
                //
                //if (json == null) {
                //    callback(response);
                //}

                callback(response);
                if (data.buttonId) {
                    DocuAjax.unFreezeButton(data.buttonId);
                }
                if (data.containerId) {
                    DocuAjax.unFreezeContent(data.containerId);
                }
                SubmissionLoader.hideFrom();
            },
            error: function (xhr, textStatus, err) {
                var errorMessage = "";
                if (xhr.status == 403 || xhr.status == 1001) {
                    LoginAjax.showForm();
                    return;
                }

                SubmissionLoader.hideFrom();
//                if (jqXHR.status == 500) {
//                    MessageRenderer.renderErrorText("Unable to process request <br/>" + jqXHR.statusText + " (Response status: " + jqXHR.status + ")");
//                    return
//                }
//                if(jqXHR.responseText !=""){
//                     MessageRenderer.renderErrorText(jqXHR.responseText);
//                    return
//                 }
                if (xhr.status == 302 || xhr.status == 401) {
                    MessageRenderer.renderErrorText("You are not authorized to do the operation.");
                    return
                }
                if (xhr.status == 0) {
                    errorMessage = "You are offline!! Please Check Your Network.";
                } else if (xhr.status == 404) {
                    errorMessage = "Requested page not found.";
                } else if (xhr.status == 500) {
                    errorMessage = "Internal Server Error.";
                } else if (err == 'parsererror') {
                    errorMessage = "Error: Parsing JSON Request failed.";
                } else if (err == 'timeout') {
                    errorMessage = "Request Time out.";
                } else if (xhr.status == 402) {
                    alert("Sorry you are not authorize to view this page");
                } else {
                    errorMessage = "Unknown Error." + xhr.responseText;
                }
                MessageRenderer.renderErrorText(errorMessage)
            }
        });
    },
    loadContent: function (url, params) {
        DocuAjax.html(url, params, function (html) {
            $('#layout-body-ajax').html(html);
        });
    }
};

var DocuAsynchronousAjax = {
    encodeUrl: function (url) {
        //if(url.substring(0, 1) == '/'){
        //    url = url.substring(1);
        //}
        //return encodeURIComponent(url);
        return url;
    },

    json: function (url, data, callback) {
        callback = typeof callback == "function" ? callback : function () {
        };
        $.ajax({
            url: DocuAsynchronousAjax.encodeUrl(url),
            type: 'post',
            dataType: "json",
            async: false,
            data: data,
            success: function (response, textStatus, jqXHR) {
                callback(response);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText("Unable to process request");
            }
        });
    },

    html: function (url, data, callback) {
        callback = typeof callback == "function" ? callback : function () {
        };
        $.ajax({
            url: DocuAsynchronousAjax.encodeUrl(url),
            type: 'post',
            dataType: "html",
            async: false,
            data: data,
            success: function (response, textStatus, jqXHR) {
                callback(response);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText("Unable to process request");
            }
        });
    }
};

var DocuAutoComplete = {
    spinnerSelector: null,
    setSpinnerSelector: function (selector) {
        DocuAutoComplete.spinnerSelector = selector;
        return DocuAutoComplete;
    },
    execute: function (response, url, data, onFormat, onSuccess) {
        onFormat = typeof onFormat == "function" ? onFormat : function () {
        };
        onSuccess = typeof onSuccess == "function" ? onSuccess : function () {
        };
        if (DocuAutoComplete.spinnerSelector) {
            $('#' + DocuAutoComplete.spinnerSelector).addClass('icon-spinner-tiny');
        }
        DocuAjax.json(url, data, function (json) {
            if (json.length) {
                response($.map(json, function (item) {
                    return onFormat(item)
                }));
            }
            else {
                response([
                    {id: "", value: "", label: "No record(s) found"}
                ]);
            }
            if (DocuAutoComplete.spinnerSelector) {
                $('#' + DocuAutoComplete.spinnerSelector).removeClass('icon-spinner-tiny');
            }
            onSuccess(json);
        });
    }
};

var MenuManager = {
    setLeftMenu: function (moduleInfoId, redirectTo) {
        var url = CONTEXT_PATH + "/menu/setLeftMenu";
        var data = {moduleInfoId: moduleInfoId};
        DocuAjax.json(url, data, function (response) {
            if (!response.isError) {
                location.href = redirectTo;
            }
        });
    }
};

var TreeAction = {
    expandAll: function (treeId) {
        $("#" + treeId).dynatree("getRoot").visit(function (node) {
            node.expand(true);
        });
    },
    collapseAll: function (treeId) {
        $("#" + treeId).dynatree("getRoot").visit(function (node) {
            node.expand(false);
        });
    },
    selectAll: function (treeId) {
        $("#" + treeId).dynatree("getRoot").visit(function (node) {
            node.select(true);
        });
    },
    unselectAll: function (treeId) {
        $("#" + treeId).dynatree("getRoot").visit(function (node) {
            node.select(false);
        });
    },
    search: function (treeId, needle) {
        TreeAction.clearSearch(treeId);
        if ($.trim(needle) == '') {
            return false;
        }

        //if(needle.indexOf('"') == 0 && needle.lastIndexOf('"') == (needle.length - 1){}
        var needleList = needle.split(' ');
        $("#" + treeId).dynatree("getRoot").visit(function (node) {
            for (needleIndex in needleList) {
                if (node.data.title.toLowerCase().indexOf(needleList[needleIndex].toLowerCase()) > -1) {
                    $(node.span).find('a.dynatree-title').addClass('tree-searched-node');
                }
            }
        });
    },
    clearSearch: function (treeId) {
        $('#' + treeId + ' a.dynatree-title').removeClass('tree-searched-node');
    },
    prepareContextMenu: function (treeId, contextMenuId) {
        // Add context menu to all nodes:
        $("#" + treeId + " span.dynatree-node")
            .destroyContextMenu()// unbind first, to prevent duplicates
            .contextMenu({menu: contextMenuId}, function (action, el, pos) {
                // The event was bound to the <span> tag, but the node object
                // is stored in the parent <li> tag
                var node = el.parents("[dtnode]").attr("dtnode");
                eval(action + "(node)");
            });

        $('#' + treeId + ' .dynatree-container').click(function () {
            if ($("#" + contextMenuId + ":visible").length > 0) {
                $("#" + contextMenuId).hide();
            }
        });
    },
    getTotalNodeCount: function (treeId) {
        var nc = 0;
        $("#" + treeId).dynatree("getRoot").visit(function (node) {
            nc++;
        });
        return nc;
    }
};

var SearchConfigurationAjax = {
    SEARCH_CONFIGURATION_ID: '',
    KEY_WORD: '',
    PAGE_ACTION_URL: '',

    search: function (data) {
        if (!data['pageIndex']) {
            data['pageIndex'] = 0;
        }

        data['searchConfigurationId'] = SearchConfigurationAjax.SEARCH_CONFIGURATION_ID;
        data['keyword'] = SearchConfigurationAjax.KEY_WORD;
        DocuAjax.html(SearchConfigurationAjax.PAGE_ACTION_URL, data, function (response) {
            $("#search-result").hide().html(response).fadeIn('slow');
            //$("#search-result").html(response).animate({opacity: 100}, 500 );

            if ($('#pagination').is(':hidden')) {
                $('#pagination').fadeIn('slow');
            }
        });
    },

    /**
     * Callback function that displays the content.
     * Gets called every time the user clicks on a pagination link.
     *
     * @param {int}page_index New Page index
     * @param {jQuery} jq the container with the pagination links as a jQuery object
     */
    pageSelectCallback: function (pageIndex, jQueryObject) {
        //alert(pageIndex)
        //var new_content = $('#hiddenresult div.result:eq(' + pageIndex + ')').clone();
        //$('#Searchresult').empty().append(new_content);
        SearchConfigurationAjax.search({pageIndex: pageIndex});
        return false;
    },

    /**
     * Callback function for the AJAX content loader.
     */
    initPagination: function (params) {
        SearchConfigurationAjax.PAGE_ACTION_URL = params.pageActionUrl;
        SearchConfigurationAjax.SEARCH_CONFIGURATION_ID = params.searchConfigurationId;
        SearchConfigurationAjax.KEY_WORD = params.keyword;

        var num_entries = params.resultCount; //$('#hiddenresult div.result').length;
        if (num_entries < 2) {
            SearchConfigurationAjax.search({pageIndex: 0});
            return false;
        }

        $("#pagination").pagination(num_entries, {
            num_edge_entries: 0,
            num_display_entries: 10,
            items_per_page: 50,
            callback: SearchConfigurationAjax.pageSelectCallback
        });
    }
};

var JsUtil = {
    formatMysqlDate: function (mysqlDate) {
        var _date = new Date(mysqlDate);
        var _day = _date.getDate().toString().length == 1 ? "0" + _date.getDate().toString() : _date.getDate().toString();
        var _month = _date.getMonth().toString().length == 1 ? "0" + _date.getMonth().toString() : _date.getMonth().toString();
        var _year = _date.getFullYear().toString();
        return _day + "-" + _month + "-" + _year;
    },

    ageCalculation: function (currentDate, dateOfBirth) {
        var DOB = new Date(dateOfBirth);
        var pDay = getDate(currentDate);
        var age = pDay.getFullYear() - DOB.getFullYear();
        if (DOB.getMonth() > pDay.getMonth() || (DOB.getMonth() == pDay.getMonth() && DOB.getDate() > pDay.getDate)) {
            age--;
        }
        return age
    },

    countDaysFromDates: function (strCurrentDate, strGivenDate) {
        var _currentDate = getDate(strCurrentDate);
        var _givenDate = getDate(strGivenDate);
        var days = 0;
        if (!isNaN(_currentDate) && !isNaN(_givenDate)) {
            var difference = _givenDate - _currentDate;
            days = difference / (1000 * 60 * 60 * 24);
        }
        return days
    },

    removeThousandsSeparator: function (value) {
        return Number(value.replace(/[^0-9\.-]+/g, ""));
    },

    getDate: function (dateStr) {
        dateArr = dateStr.split('-')
        formatedStr = dateArr[2] - dateArr[1] - dateArr[0]
        formatedDate = new Date(dateArr[2], parseInt(dateArr[1] - 1), dateArr[0])
        return formatedDate
    },

    hidePrompt: function (formId) {
        $('#' + formId).validationEngine('hideAll');
    },

    hasPdfPluginInBrowser: function () {
        var flag = null;
        if (navigator.userAgent.indexOf('MSIE') > -1) {
            try {
                // AcroPDF.PDF is used by version 7 and later
                flag = new ActiveXObject('AcroPDF.PDF');
            } catch (e) {
                flag = null;
            }
            if (!flag) {
                try {
                    // PDF.PdfCtrl is used by version 6 and earlier
                    flag = new ActiveXObject('PDF.PdfCtrl');
                }
                catch (e) {
                    flag = null;
                }
            }
        }
        else {
            var plugins = window.navigator.plugins;
            for (k in plugins) {
                if (plugins[k].name == 'Adobe Acrobat') {
                    flag = plugins[k];
                    break;
                }
            }
        }

        if (flag) {
            return true;
        }

        return false;
    },
    getFormAsArray: function (formId) {
        var items = $('#' + formId).serializeArray();
        var params = {};
        for (key in items) {
            params[items[key].name] = items[key].value;
        }
        return params;
    },


    /* Example */
//    strLeftPadding(12, 5); // returns 00012
//    strLeftPadding('abc', 6, '#'); // returns ###abc
    strLeftPadding: function (i, l, s) {
        var o = i.toString();
        if (!s) {
            s = '0';
        }
        while (o.length < l) {
            o = s + o;
        }
        return o;
    }
};

var DocuDateUtil = {
    createDateFromString: function (dateString) {
        var items = dateString.split('-');
        return new Date(items[2], items[1] - 1, items[0]);
    },

    createDateFromMysql: function (mysqlDate) {
        var _date = new Date(mysqlDate);
        return DocuDateUtil.formatDate(_date);
    },

    formatDate: function (_date, _format) {
        var _day = _date.getDate().toString().length == 1 ? "0" + _date.getDate().toString() : _date.getDate().toString();
        var _month = _date.getMonth() + 1
        _month = _month.toString().length == 1 ? "0" + _month.toString() : _month.toString();
        var _year = _date.getFullYear().toString();
        return _day + "-" + _month + "-" + _year;
    },

    isDate: function (value) {
        value = $.trim(value);
        var d = DocuDateUtil.createDateFromString(value);
        if (isNaN(d.getTime())) {
            return false;
        }
        var dmy = value.split('-');
        if (d.getFullYear() != parseInt(dmy[2]) || d.getMonth() != parseInt(dmy[1]) - 1 || d.getDate() != parseInt(dmy[0])) {
            return false;
        }
        return true;
    },
    compareDates: function () {

    },
    isFutureDate: function (fromDateText, toDateText) {
        return DocuDateUtil.createDateFromString(fromDateText) >= DocuDateUtil.createDateFromString(toDateText)
    }
};

var DocuTimeUtil = {
    timeMask: null,
    initMaskDefinitions: function () {
        $.mask.definitions['g'] = "[ ]";
        $.mask.definitions['h'] = "[aApP]";
        $.mask.definitions['i'] = "[mM]";
        $.mask.definitions['2'] = "[0-1]";
        $.mask.definitions['6'] = "[0-5]";
        DocuTimeUtil.timeMask = "29:69 hi";
    },
    getTimeParts: function (_timeText) {
        _timeText = _timeText.toLowerCase();
        _timeText = _timeText.split(' ');
        var _ampm = _timeText[1]
        _timeText = _timeText[0].split(':');
        var _hour = _timeText[0];
        var _minute = _timeText[1];
        return {hour: _hour, minute: _minute, meridiem: _ampm};
    },
    calculateMinutes: function (_timeText) {
        var _timeParts = DocuTimeUtil.getTimeParts(_timeText);
        var _hour = _timeParts.hour;
        var _minute = _timeParts.minute;
        if (_timeParts.meridiem == 'pm') {
            _hour = parseInt(_hour) + 12;
        }
        return parseInt(_hour) * 60 + parseInt(_minute);
    },
    createTimeString: function (_timeText) {
        _timeText = _timeText.toLowerCase();
        _timeText = _timeText.split(' ');
        var _ampm = _timeText[1]
        _timeText = _timeText[0].split(':');
        var _hour = _timeText[0];
        var _minute = _timeText[1];
        if (_ampm == 'pm') {
            _hour = parseInt(_hour) + 12;
        }
        return _hour + ":" + _minute + ":0";
    }
};

var HtmlUtil = {
    clearDropdown: function (selector, noSelect) {
        $('#' + selector).children().remove().end();
        if (noSelect) {
            $('#' + selector).append('<option value="null">' + noSelect + '</option>');
        }
    },

    createDropdown: function (selector, data, noSelect, optionValue, optionText) {
        optionValue = optionValue ? optionValue : 'id';
        //optionText = optionText ? optionText : 'value';
        var callback = optionText;
        if (typeof callback != "function") {
            callback = function (item) {
                return optionText ? item[optionText] : item['value']
            };
        }
        HtmlUtil.clearDropdown(selector, noSelect);
        for (key in data) {
            //$('#' + selector).append('<option value="' + data[key][optionValue] + '">' + data[key][optionText] + '</option>');
            $('#' + selector).append('<option value="' + data[key][optionValue] + '">' + callback(data[key]) + '</option>');
        }
    },

    clearDropdownWithNoSelectValue: function (selector, noSelectValue, noSelect) {
        $('#' + selector).children().remove().end();
        if (noSelect) {
            $('#' + selector).append('<option value="' + noSelectValue + '">' + noSelect + '</option>');
        }
    },

    createDropdownWithNoSelectValue: function (selector, data, noSelectValue, noSelect, optionValue, optionText) {
        optionValue = optionValue ? optionValue : 'id';
        //optionText = optionText ? optionText : 'value';
        var callback = optionText;
        if (typeof callback != "function") {
            callback = function (item) {
                return optionText ? item[optionText] : item['value']
            };
        }
        HtmlUtil.clearDropdownWithNoSelectValue(selector, noSelectValue, noSelect);
        for (key in data) {
            //$('#' + selector).append('<option value="' + data[key][optionValue] + '">' + data[key][optionText] + '</option>');
            $('#' + selector).append('<option value="' + data[key][optionValue] + '">' + callback(data[key]) + '</option>');
        }
    },

    compositeDropdown: function (selector, data, noSelect, optionValue, optionText1, optionText2) {
        optionValue = optionValue ? optionValue : 'id';
        $('#' + selector).children().remove().end();
        $('#' + selector).append('<option value="null">' + noSelect + '</option>');
        for (key in data) {
            $('#' + selector).append('<option value="' + data[key][optionValue] + '">' + '[' + data[key][optionText1] + '] ' + data[key][optionText2] + '</option>');
        }
    },

    getFormAsArray: function (formId) {
        var items = $('#' + formId).serializeArray();
        var params = {};
        for (key in items) {
            params[items[key].name] = items[key].value;
        }
        return params;
    }
};

var DocuBrowserTab = {
    openTab: function (url, params) {
        if (typeof params !== "string") {
            params = $.param(params);
        }
        window.open(url + "?" + params, 'new-tab-pdf-');
    },
    openNewTab: function (url, params, tabName) {
        if (typeof params !== "string") {
            params = $.param(params);
        }
        if (!tabName) {
            var _date = new Date();
            tabName = _date.getTime();
        }
        window.open(url + "?" + params, tabName);
    },
    openPdfTab: function (gspTitle, url, params) {
        if (typeof params !== "string") {
            params['DocuReportUrl'] = url;
            params['GspTitle'] = gspTitle;
        }
        else {
            params = params + "&DocuReportUrl=" + url + "&GspTitle=" + gspTitle;
        }
        DocuBrowserTab.openTab(CONTEXT_PATH + "/docuJasper/openPdfReportTab", params)
        //DocuBrowserTab.openTab(url, params);
    },
    redirectLinkUrl: function (url, isNewTab) {
        var a = document.createElement('a');
        a.href = url;
        if (isNewTab == true) {
            a.target = '_blank';
        }
        document.body.appendChild(a);
        a.click();
    }
};

//var HtmlForm = {
//    form: null,
//
//    init: function(formId) {
//        HtmlForm.form = $('#' + formId);
//        return HtmlForm;
//    },
//
//    validate: function() {
//        return true;
//    }
//};

var DocuSpinner = {
    URL_TINY: CONTEXT_PATH + '/images/spinner.gif',
    URL_TINY_BLUE: CONTEXT_PATH + '/images/tiny-spinner-blue.gif',
    URL_TINY_PINK: CONTEXT_PATH + '/images/docu-ajax-spinner-pink.gif',
    URL_MEDIUM: CONTEXT_PATH + '/images/layout/ajax-loader.gif',
    URL_LARGE: CONTEXT_PATH + '/images/layout/ajax-loader.gif',
    start: function (selector, url) {
        $(selector).html('<img align="middle" src="' + url + '" />');
    },
    stop: function (selector) {
        $(selector).html('');
    }
};

var AjaxLoader = {
    showTo: function (divId) {
        $('#' + divId).html('<div style="border:0px #4169e1 solid; text-align:center;"><img src="' + AJAX_LOADER_ICON_URL + '" /></div>');
    },

    hideFrom: function (divId) {
        $('#' + divId).html('');
    }
};

var SubmissionLoader = {
    showTo: function () {
        $.blockUI({
            overlayCSS: {
                backgroundColor: '#F0F0F0', //'#D7DFE7' //'#F0F4F7' '#E8F3FB' '#D7DFE7'
                opacity: .3
            },
//            message: '<img src="' + SUBMISSION_LOADER + '" />',
            message: SUBMISSION_LOADER,
            css: {
                width: '50px',
                height: '40px',
                border: 'none',
                borderColor: 'none',
                backgroundColor: 'none',
                top: ($(window).height()) / 2 + 'px',
                left: ($(window).width()) / 2 + 'px'
            }
        });
    },
    hideFrom: function () {
        $.unblockUI()
    },
    ajaxHide: function () {
        $(document).ajaxStop($.unblockUI);
    }
};

var FaceBookAjaxLoader = {
    showTo: function (divId) {
        $('#' + divId).html('<span style="border:0px #4169e1 solid; text-align:center;"><img height="16px" src="' + FACE_BOOK_AJAX_LOADER_ICON_URL + '" /></span>');
    },

    hideFrom: function (divId) {
        $('#' + divId).html('');
    }
};

/**
 * @Depricated
 */
var SmartAjax = {
    url: '',
    params: {},

    setUrl: function (remoteUrl) {
        SmartAjax.url = remoteUrl;
        return SmartAjax;
    },

    setParams: function (params) {
        SmartAjax.params = params;
        return SmartAjax;
    },

    getHtml: function (tagId) {
        var url = SmartAjax.url;
        var data = SmartAjax.params;
        DocuAjax.html(url, data, function (html) {
            $('#' + tagId).html(html);
        });

    },

    getJson: function (tagIds) {
        var url = SmartAjax.url;
        var data = SmartAjax.params;
        DocuAjax.json(url, data, function (json) {
            for (key in json) {
                $('#' + key).html(json[key]);
                $('#' + key).val(json[key]);
            }
        });
    }
};

var CountryCityModel = {
    url: null,
    country: null,
    city: null,
    callback: null,

    init: function (params) {
        CountryCityModel.url = params.url;
        CountryCityModel.country = $('#' + params.countryId);
        CountryCityModel.city = $('#' + params.cityId);
        CountryCityModel.callback = params.callback;

        CountryCityModel.country.change(function () {
            CountryCityModel.city.children().remove().end();
            if (CountryCityModel.country.val() != 'null') {
                CountryCityModel.reloadCountryCities();
            }
        });
    },

    reloadCountryCities: function () {
        var url = CountryCityModel.url;
        var data = {countryId: CountryCityModel.country.val(), ajax: 'true'};
        DocuAjax.json(url, data, function (response) {
            if (response.cityList) {
                CountryCityModel.city.append('<option value="null">-Select City-</option>')
                for (index in response.cityList) {
                    CountryCityModel.city.append('<option value="' + response.cityList[index].optionValue + '">' + response.cityList[index].optionDisplay + '</option>')
                }
            }
            if (CountryCityModel.callback) {
                eval(CountryCityModel.callback + "('" + response + "');");
            }
        });
    }
};

var UplodifyObject = {
    progressBarHtml: "<div class='progress_container' id='progress_container'><a href='javascript:void(0);' id='close-progress-bar'></a>" +
        "<div id='progress-percentage'></div>" +
        "<div class='progress_bar_container'>" +
        "<div id='progress-bar'></div></div>" +

        " </div>",

    initializeUplodify: function (formName, data, callBackSelect, callBackComplete, callbackCancel, callbackOpen) {
        callBackComplete = typeof callBackComplete == "function" ? callBackComplete : function () {
        };

        callbackCancel = typeof callbackCancel == "function" ? callbackCancel : function () {
        };

        callBackSelect = typeof callBackSelect == "function" ? callBackSelect : function () {
        };

        callbackOpen = typeof callbackOpen == "function" ? callbackOpen : function () {
        };


        var params = {
            'uploader': CONTEXT_PATH + '/js/jquery/uploadify/uploadify.swf',
            'script': CONTEXT_PATH + '/ajaxUploadify/upload',
            'cancelImg': CONTEXT_PATH + '/js/jquery/uploadify/cancel.png',
            'auto': true,
            'folder': '/tmp',
            'removeCompleted': false,
            'onSelectOnce': function (event, ID, fileObj) {
                callBackSelect(event, ID, fileObj)
                $("#close-progress-bar").click(function () {
                    $('#' + formName).uploadifyCancel($('.uploadifyQueueItem').first().attr('id').replace(formName, ''))
                    UplodifyObject.hidePercentageBlock()
                })
                $('.uploadifyQueueItem').hide();
            },
            'onComplete': function (event, queueID, fileObj, response) {
                UplodifyObject.hidePercentageBlock()
                callBackComplete(event, queueID, fileObj, response)
            },
            'onCancel': function (event, ID, fileObj, data) {
                callbackCancel(event, ID, fileObj, data)
            },
            'onProgress': function (event, ID, fileObj, data) {
                var percentage = data.percentage + "%"
                UplodifyObject.showPercentageBlock(percentage)
            },
            'onOpen': function (event, ID, fileObj) {
                callbackOpen(event, ID, fileObj)
            }
        };

        for (key in data) {
            params[key] = data[key];
        }

        $('#' + formName).uploadify(params);
    },
    showPercentageBlock: function (percentage) {
        $("#progress-percentage").html(percentage)
        $("#progress-bar").css('width', percentage)
        return false;
    },
    hidePercentageBlock: function () {
        $("#progress-bar").css('width', 1)
        $("#progress_container").hide()
    }

};

var ProjectInfoAjax = {
    handlers: [],

    listen: function (handle) {
        ProjectInfoAjax.handlers.push(handle);
    },

    trigger: function (action) {
        for (var key in ProjectInfoAjax.handlers) {
            var handle = ProjectInfoAjax.handlers[key];
            eval(handle + "('" + action + "');");
        }
    },

    lookupProjectAssociation: function (projectId, projectAssociationLookupMapList) {
        var needle = "";
        var lookupMap = $.grep(projectAssociationLookupMapList, function (item, index) {
            return item[projectId];
        });
        if (lookupMap[0]) {
            needle = lookupMap[0][projectId];
        }
        switch (needle) {
            case 'GROUP':
                $('#groupLookupHtml').slideDown('slow');
                ProjectInfoAjax.trigger('ASSOCIATE_WITH_GROUP');
                break;
            case 'MEMBER':
                $('#groupLookupHtml').slideUp('slow');
                ProjectInfoAjax.trigger('ASSOCIATE_WITH_MEMBER');
                break;
            default:
                $('#groupLookupHtml').slideUp('slow');
                ProjectInfoAjax.trigger('NO_ASSOCIATION_FOUND');
        }
        ProjectInfoAjax.trigger('change');

        /*
         DocuAjax.json(url, {projectId:projectId}, function(json) {
         switch (json.flag) {
         case 'ASSOCIATE_WITH_GROUP' :
         $('#groupLookupHtml').slideDown('slow');
         ProjectInfoAjax.trigger('ASSOCIATE_WITH_GROUP');
         break;
         case 'ASSOCIATE_WITH_MEMBER' :
         $('#groupLookupHtml').slideUp('slow');
         ProjectInfoAjax.trigger('ASSOCIATE_WITH_MEMBER');
         break;
         case 'NO_ASSOCIATION_FOUND' :
         $('#groupLookupHtml').slideUp('slow');
         ProjectInfoAjax.trigger('NO_ASSOCIATION_FOUND');
         break;
         }
         });
         ProjectInfoAjax.trigger('change');
         */
    },

    countProjectGroup: function (url, projectId) {
        DocuAjax.json(url, {projectId: projectId}, function (json) {
            if (!json.projectGroupCount) {
                $('#groupLookupHtml').slideUp('slow');
                ProjectInfoAjax.trigger('ASSOCIATE_WITH_MEMBER');
            } else {
                $('#groupLookupHtml').slideDown('slow');
                ProjectInfoAjax.trigger('ASSOCIATE_WITH_GROUP');
            }
        });
        ProjectInfoAjax.trigger('change');
    },

    countProjectGroupForMember: function (url, projectId) {
        if (projectId == 'null') {
            $('#groupLookupHtml').slideUp('slow', function () {
                EnterKeyListener.init();
            });
            ProjectInfoAjax.trigger('null');
            return false;
        }

        DocuAjax.json(url, {projectId: projectId}, function (json) {
            if (json.projectGroupCount == 0) {
                MessageRenderer.render({"messageBody": "No group found for this project", "messageTitle": "Project Info", "type": "0"});
                $('#groupLookupHtml').slideUp('slow', function () {
                    EnterKeyListener.init();
                });
                return;
            }
            else if (json.associatedWithMember) {
                ProjectInfoAjax.trigger('associatedWithMember');
                $('#groupLookupHtml').slideUp('slow', function () {
                    EnterKeyListener.init();
                });
            }
            else if (json.notAssociatedWithMemberOrGroup) {
                MessageRenderer.render({"messageBody": "This project is not associated with Group or Member. Please define project policy", "messageTitle": "Project Info", "type": "0"});
                return;
            }
            else if (json.projectGroupCount) {
                $('#groupLookupHtml').slideDown('slow', function () {
                    EnterKeyListener.init();
                });
            }
        });
        ProjectInfoAjax.trigger('change');
    },

    countProjectGroupForSavingsAccount: function (url, projectId) {
        DocuAjax.json(url, {projectId: projectId}, function (json) {
            getProjectWiseProductList();
            if (!json.projectGroupCount) {
                //if(!$('#groupLookupHtml').is(':hidden')){
                $('#groupLookupHtml').slideUp('slow');
                //}
            } else {
                $('#groupLookupHtml').slideDown('slow');
            }
        });
        ProjectInfoAjax.trigger('change');
    },

    countProjectGroupForLoanProposal: function (url, projectId) {
        DocuAjax.json(url, {projectId: projectId}, function (json) {
            getProjectWiseLoanProductList();
            if (!json.projectGroupCount) {
                //if(!$('#groupLookupHtml').is(':hidden')){
                $('#groupLookupHtml').slideUp('slow');
                //}
            } else {
                $('#groupLookupHtml').slideDown('slow');
            }
        });
        ProjectInfoAjax.trigger('change');
    }


};

var GroupInfoAjax = {
    handlers: [],

    listen: function (handle) {
        GroupInfoAjax.handlers.push(handle);
    },

    trigger: function (action) {
        for (var key in GroupInfoAjax.handlers) {
            var handle = GroupInfoAjax.handlers[key];
            eval(handle + "('" + action + "');");
        }
    },

    getGroupNumbers: function (url, data, request, response) {
        data['term'] = request.term;
        DocuAjax.json(url, data, function (data) {
            response($.map(data, function (item) {
                item.label = item.label + " (" + item.group_name + ")";

                item.employee_name = item.employee_name + " (" + item.pin_number + ")";

                var list = item.value.split('-');
                item.value = list[list.length - 1];
                return item
            }));
        });
    },

    getGroupNumbersFilteringCurrentGroupId: function (url, data, request, response, currentGroupInfoId) {
        data['term'] = request.term;
        DocuAjax.json(url, data, function (data) {
            response($.map(data, function (item) {
                if (item.id != currentGroupInfoId) {
                    item.label = item.label + " (" + item.group_name + ")";

                    item.employee_name = item.employee_name + " (" + item.pin_number + ")";

                    var list = item.value.split('-');
                    item.value = list[list.length - 1];
                    return item
                }
            }));
        });
    }
};

var MemberInfoAjax = {
    handlers: [],

    listen: function (handle) {
        MemberInfoAjax.handlers.push(handle);
    },

    trigger: function (action) {
        for (var key in MemberInfoAjax.handlers) {
            var handle = MemberInfoAjax.handlers[key];
            eval(handle + "('" + action + "');");
        }
    },

    getMemberNumbers: function (url, data, request, response) {
        data['term'] = request.term;
        DocuAjax.json(url, data, function (data) {
            response($.map(data, function (item) {
                item.label = item.label + " (" + item.memberName + ")";

                var list = item.value.split('-');
                item.value = list[list.length - 1];
                return item
            }));
        });
    }
};

var EntityIdAjax = {
    handlers: [],

    listen: function (handle) {
        EntityIdAjax.handlers.push(handle);
    },

    trigger: function (action) {
        for (var key in EntityIdAjax.handlers) {
            var handle = EntityIdAjax.handlers[key];
            eval(handle + "('" + action + "');");
        }
    },

    getEntityIds: function (url, data, request, response) {
        data['term'] = request.term;
        DocuAjax.json(url, data, function (data) {
            response($.map(data, function (item) {
//                var list = item.value.split('-');
//                item.value = list[list.length - 1];
                return item
            }));
        });
    }
};

var JqGridManager = {

    prepareEditColumn: function (gridInstance, actionUrl) {
        var ids = gridInstance.jqGrid('getDataIDs');

        for (key in ids) {
            gridInstance.jqGrid('setRowData', ids[key], {edit: '<a href="' + actionUrl + '/' + ids[key] + '">Edit</a>'});
        }
    },

    checkAllCheckbox: function (_class) {
        $('.' + _class).each(function () {
            $(this).attr('checked', 'checked');
        });
    },

    unCheckAllCheckbox: function (_class) {
        $('.' + _class).each(function () {
            $(this).removeAttr('checked');
        });
    }
};

var SearchToll = {

    toggleField: function (operatorValue, fieldName) {
        if (!operatorValue) {
            $('#from_' + fieldName).val('');
            $('#to_' + fieldName).val('');
        }
        if (operatorValue == 'between') {
            $('#to_' + fieldName + '_html').show();
        } else {
            $('#to_' + fieldName + '_html').hide();
            $('#to_' + fieldName).val('');
        }
    },

    getDate: function (pDate) {
        var data = pDate.split("-");
        var date = "";
        if (data[0] != null && data[1] != null && data[2] != null) {
            date = data[2] + "-" + data[1] + "-" + data[0];
        }
        return date;
    },
    prepareDateQuery: function (fieldName, dbTableName, fromDate, toDate) {
        var isError = false
        if (fromDate && toDate) {
            $('#' + fieldName).val(dbTableName + "." + fieldName + " >= '" + fromDate + "' AND " + dbTableName + "." + fieldName + " <= '" + toDate + "'");
        } else if (fromDate) {
            $('#' + fieldName).val(dbTableName + "." + fieldName + " = '" + fromDate + "'");
        } else if (toDate) {
//        $('#' + fieldName).val(fieldName + " <= '" + toDate + "'");
            $('#' + fieldName).val('');
            alert("Please give from date of '" + fieldName + "'");
            isError = true;
        } else {
            $('#' + fieldName).val('');
        }
        return isError;
    },

    prepareBetweenClause: function (fieldName) {
        var operatorValue = $('#operators_' + fieldName).val();
        if (!operatorValue) {
            $('#' + fieldName).val('');
            return false;
        }

        if (operatorValue == 'between') {
            $('#' + fieldName).val(fieldName + " >= '" + $('#from_' + fieldName).val() + "' AND " + fieldName + " <= '" + $('#to_' + fieldName).val() + "'");
        } else {
            $('#' + fieldName).val(fieldName + " " + operatorValue + " '" + $('#from_' + fieldName).val() + "'");
        }
    }
};

var DocuGridFormatter = {
    dateFormatter: function (val, options, cellobject) {
        var dateFmt = options.colModel.formatoptions.format
        var _date = new Date(val)
        var _day = _date.getDate().toString()
        var _month = (_date.getMonth() + 1).toString()

        if (_day.length == 1) {
            _day = "0" + _day;
        }

        if (_month.length == 1) {
            _month = "0" + _month;
        }

        return _day + '-' + _month + '-' + _date.getFullYear();

//        if(dateFmt== 'dmY'){
//          return _day + "-" + _month + "-" + _date.getFullYear()
//        }else if(dateFmt=='mdY') {
//          return  _month + "_" + _day + "-" + _date.getFullYear()
//        }else if(dateFmt=='Ymd'){
//           return _date.getFullYear() + "-" + _month + "-" + _day
//        }

    },

    accountBalanceFormatter: function (val, options, cellobject) {
        var fornatted = 0;
        if (val < 0) {
            fornatted = "(" + thousandsSeparator(val * -1) + ")";
        }
        else {
            fornatted = thousandsSeparator(val);
        }
        return fornatted;
    }
};


var EnterKeyListener = {
    excludeFieldIds: [],
    elements: null,
    init: function (excludeFieldIdList) {
        if (excludeFieldIdList) {
            EnterKeyListener.excludeFieldIds = excludeFieldIdList;
        }
        var isExcluded = null;
        EnterKeyListener.elements = $(".ui-layout-center input:visible:not(:file,[disabled],[readonly]),select:visible:not(div:hidden), textarea, button").filter(function () {
            if ($(this).attr("readonly")) {
                return false;
            }
            else if ($(this).is(':disabled')) {
                return false;
            }
            else if ($(this).is(':hidden')) {
                return false;
            }
            isExcluded = $.inArray($(this).attr('id'), EnterKeyListener.excludeFieldIds);
            if (isExcluded > -1) {
                return false;
            }

//            else if(!$(this).attr('id') || !$(this).attr('name')){
//                return false;
//            }
            return true;
        });
        EnterKeyListener.attachEnterEvent(EnterKeyListener.elements);
    },

    removeEnterEvent: function (elements) {
        if ($.browser.mozilla) {
            $(elements).unbind('keypress', EnterKeyListener.onEnterEvent);
        }
        else {
            $(elements).unbind('keydown', EnterKeyListener.onEnterEvent);
        }
    },

    attachEnterEvent: function (elements) {
        if ($.browser.mozilla) {
            $(elements).keypress(EnterKeyListener.onEnterEvent);
        }
        else {
            $(elements).keydown(EnterKeyListener.onEnterEvent);
        }
    },

    reloadListener: function () {
        EnterKeyListener.removeEnterEvent(EnterKeyListener.elements);
        EnterKeyListener.init();
    },

    onEnterEvent: function (event) {
        if (event.keyCode == 13 || event.keyCode == 9) {
            EnterKeyListener.reloadListener();

            //var inputElementList = [];
            //EnterKeyListener.elements.each(function(index, thisItem) {
            //var parent = $(this).parents().closest(':hidden');
            //if (!$(parent).attr('id')) {
            //if (!$(this).is(':disabled') || !$(this).is(':visible')) {
            //if (!$(this).attr("readonly")) {
            //if (thisItem.id || thisItem.name) {
            //inputElementList.push(thisItem);
            //}
            //}
            //}
            //}
            //});

//            var s = []
//            for (k in EnterKeyListener.elements) {
//                s.push(EnterKeyListener.elements[k].id + " : " + EnterKeyListener.elements[k].tagName )
//            }
//            EnterKeyListener.debug(s);

            currentBoxNumber = $.inArray(this, EnterKeyListener.elements);
            if (EnterKeyListener.elements[currentBoxNumber + 1] != null) {
                nextBox = EnterKeyListener.elements[currentBoxNumber + 1]
                nextBox.focus();
                if (nextBox.type == 'select-one') {
                    $(nextBox).addClass('inputFocus');
                    $(nextBox).blur(function () {
                        $(this).removeClass('inputFocus');
                    });
                }
                event.preventDefault();
                return false;
            }
        }
    },

    debug: function (list) {
        $('#layout-body-ajax').before("<div id='debug'>---DEBUG---</div>");
        $('#debug').html(list.join("</br>"));
    }
}


var CursorListener = {
    setFocusOnFirstField: function () {
        var elements = $(".ui-layout-center input:visible:not(:file,[disabled],[readonly]),select:visible:not(div:hidden), textarea, button").filter(function () {
            if ($(this).attr("readonly")) {
                return false;
            }
            else if ($(this).is(':disabled')) {
                return false;
            }
            else if ($(this).is(':hidden')) {
                return false;
            }
//            else if(!$(this).attr('id') || !$(this).attr('name')){
//                return false;
//            }
            return true;
        });

        $(elements[0]).focus();
    }
}

//function changeTabToEnterEvent() {
//    if ($.browser.mozilla) {
//        $(".enter").keypress(checkForEnter);
//    } else {
//        $(".enter").keydown(checkForEnter);
//    }
//}
//
//function checkForEnter(event) {
//    var lfound = false
//    if (event.keyCode == 13) {
//        var obj = this;
//        $(".enter").each(function() {
//            if (this == obj) {
//                lfound = true
//            } else {
//                if (lfound) {
//                    $(this).focus()
//                    $(this).select();
//                    event.preventDefault();
//                    return false;
//                }
//            }
//        });
//    }
//}

function emptyJqGrid(jqGridVar) {
    // iterate through the rows and delete each of them
    var rowIds = jqGridVar.jqGrid('getDataIDs');
    for (var i = 0, len = rowIds.length; i < len; i++) {
        var currRow = rowIds[i];
        jqGridVar.jqGrid('delRowData', currRow);
    }
}

function openPopUp(url) {
    window.open(url, "popUpWindow", "width=850,height=800, screenX=250, menubar=no, location=no, status=no, directories=no, titlebar=no, resizable=no, menubar=no, scrollbars=yes");
}

function changeTabToEnterEven() {
    // get only input tags with class data-entry
    textboxes = $("input:visible:not(:file,[disabled],[readonly]),select,textarea, button");

    // now we check to see which browser is being used

    if ($.browser.mozilla) {
        $(textboxes).keypress(checkForEnter);
    } else {
        $(textboxes).keydown(checkForEnter);
    }
}

function checkForEnter(event) {
    if (event.keyCode == 13 || event.keyCode == 9) {
        currentBoxNumber = textboxes.index(this);
        if (textboxes[currentBoxNumber + 1] != null) {
            nextBox = textboxes[currentBoxNumber + 1]
            nextBox.focus();
//            nextBox.select();
            event.preventDefault();
            return false;
        }
    }
}

function thousandsSeparator(n, swap) {
    var ts = ",", ds = "."; // thousands and decimal separators
    if (swap) {
        ts = ",";
        ts = ".";
    } // swap if requested

    var num = new Number(n);
    var ns = String(num.toFixed(2)), ps = ns, ss = "";
    var i = ns.indexOf(".");
    if (i != -1) { // if ".", then split:
        ps = ns.substring(0, i);
        ss = ds + ns.substring(i + 1);
    }
    return ps.replace(/(\d)(?=(\d{3})+([.]|$))/g, "$1" + ts) + ss;
}


function getDate(dateStr) {
    dateArr = dateStr.split('-');
    formatedStr = dateArr[2] + '-' + dateArr[1] + '-' + dateArr[0];
    var dYear = dateArr[2] - 0;
    var dMon = dateArr[1] - 1;
    var dDay = dateArr[0] - 0;
    formatedDate = new Date(dYear, dMon, dDay);
    if (formatedDate) {
        if (formatedDate.getFullYear() != dYear || formatedDate.getMonth() != dMon || formatedDate.getDate() != dDay) {
            return null
        }
    }
    return formatedDate
}

function isValidDateRange(startDate, endDate) {
    strDate = getDate(startDate)
    enDate = getDate(endDate)
    if (strDate > enDate) {
        return false
    }
    return true
}

function isFutureDate(businessDate, inputDate) {
    strDate = getDate(inputDate);
    enDate = getDate(businessDate);
    if (strDate > enDate) {
        return false
    }
    return true
}

function isPastDate(inputDate, businessDate) {
    strDate = getDate(inputDate);
    enDate = getDate(businessDate);
    if (strDate < enDate) {
        return false
    }
    return true
}

function trimForm() {
    $(":input").not(":button, :submit, :reset").each(function () {
        this.value = $.trim(this.value);
    });
}

function clearForm(formId) {
    $("#" + formId + " input,textArea").not(":button, :submit, :reset,:radio,:checkbox,[disabled],[readonly]").each(function () {
        this.value = "";
    });

    $("#" + formId + " select").each(function () {
        this.value = 'null'
    })
}

var portletAjax = {
    _numOfColumn: 3,
    _height: "200px",
    _parentDiv: "portlet_parent",
    _userPreferenceUpdateUrl: "/myDashboard/updateUserPortletPreference",
    _loaderHtml: "<div style='border:0px #4169e1 solid; text-align:center;'><img src='" + AJAX_LOADER_ICON_URL + "' /></div>",
    _portletArray: [],
    _columnObject: {},
    init: function (dashBoardBusinessDate) {
        portletAjax.createColumn()
        portletAjax.buildPortletContainer()
        portletAjax.executePortlet(dashBoardBusinessDate)
    },
    executePortlet: function (dashBoardBusinessDate) {
        $(".column").sortable({
            connectWith: ".column",
            scroll: true,
            cursor: 'crosshair',
            stop: function () {
                var userPortletPreferenceArr = [];
                var data = {};
                var i = 0
                $.each($(".column"), function (colIndex, value) {
                    userPortletPreferenceArr = $(value).sortable("toArray");
                    $.each(userPortletPreferenceArr, function (rowIndex, value) {
                        data['items.userPortletPreference[' + i + '].id'] = value;
                        data['items.userPortletPreference[' + i + '].rowIndex'] = rowIndex;
                        data['items.userPortletPreference[' + i + '].colIndex'] = colIndex;
                        i++
                    })
                })
                portletAjax.updateUserPortletPreference(data)
            }
        });

        $(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
            .find(".portlet-header")
            .addClass("ui-widget-header ui-corner-all")
            .prepend("<span class='ui-icon ui-icon-minusthick'></span>")
            .end()
            .find(".portlet-content");

        $(".portlet-content").css({'height': portletAjax._height, 'overflow': 'auto'})
        $(".portlet-content").bind("mousedown", function () {
            return false;
        });
        $(".title_portlet").each(function () {
            if ($(this).outerHeight() > 14) {
                $(this).css({'margin-top': '0'});
            }
        })


        $(".portlet-header .ui-icon").click(function () {
            $(this).toggleClass("ui-icon-minusthick").toggleClass("ui-icon-plusthick");
            $(this).parents(".portlet:first").find(".portlet-content").toggle();
        });

        $(".column").disableSelection();
        portletAjax.loanContent(dashBoardBusinessDate)
        setTimeout(portletAjax.autoLoadContent, 5000);
    },
    addPortlets: function (portlets) {
        portletAjax._portletArray = portlets
    },
    createPortletContainerHtml: function (value) {
        var _portletContainerHtml = ""
        _portletContainerHtml += "<div class='portlet' id='" + value.userPortletPreferenceId + "'>"
        _portletContainerHtml += "<div class='portlet-header'><span class='icon_portlet'></span><span class='title_portlet'>" + value.title + "</span></div>"
        _portletContainerHtml += "<div class='portlet-content' id='container_" + value.id + "'>" + portletAjax._loaderHtml + "</div>"
        _portletContainerHtml += "</div>"
        return _portletContainerHtml;
    },
    createColumn: function () {
        var _columnHtml = ""
        for (i = 0; i < portletAjax._numOfColumn; i++) {
            _columnHtml += "<div class='column' id='column_" + i + "'></div>"
        }
        $("#" + portletAjax._parentDiv).html(_columnHtml)
    },
    buildPortletContainer: function () {
        $.each(portletAjax._portletArray, function (index, value) {
            if (!portletAjax._columnObject[value.colIndex]) {
                portletAjax._columnObject[value.colIndex] = []
            }
            portletAjax._columnObject[value.colIndex][value.rowIndex] = value
        })
        $.each(portletAjax._columnObject, function (_colIndex, _colObj) {
            var _columnHtml = ""
            $.each(_colObj, function (_rowIndex, _cellObject) {
                if (_cellObject)
                    _columnHtml += portletAjax.createPortletContainerHtml(_cellObject)
            })
            $("#column_" + _colIndex).html(_columnHtml)
        })
    },
    /*    loanContent:function () {
     $.each(portletAjax._portletArray, function (index, value) {
     DocuAjax.html(CONTEXT_PATH + value.url, {}, function (html) {
     $('#container_' + value.id).html(html);
     });
     })
     },*/
    loanContent: function (dashBoardBusinessDate) {
        $.each(portletAjax._portletArray, function (index, value) {
            DocuAjax.html(CONTEXT_PATH + value.url, {dashBoardBusinessDate: dashBoardBusinessDate}, function (html) {
                $('#container_' + value.id).html(html);
            });
        })
    },
    autoLoadContent: function () {
        $.each(portletAjax._portletArray, function (index, value) {
            if (value.isAuto) {
                DocuAjax.html(CONTEXT_PATH + value.url, {}, function (html) {
                    $('#container_' + value.id).html(html);
                });
            }
        })
        setTimeout(portletAjax.autoLoadContent, 5000);
    },
    updateUserPortletPreference: function (data) {
        DocuAjax.json(CONTEXT_PATH + portletAjax._userPreferenceUpdateUrl, data, function (res) {
        });
    }
};


function DocuWidget(widgetId, widgetTitle) {
    this.breadcrumbItems = {};
    this.widgetId = widgetId;
    this.widgetTitle = widgetTitle;
    this.headerId = 'docu-widget-header-' + widgetId;
    this.titleId = 'docu-widget-title-' + widgetId;
    this.lebelId = 'docu-widget-label-' + widgetId;
    this.breadcrumbId = 'docu-widget-breadcrumb-' + widgetId;
    this.contentId = 'docu-widget-content-' + widgetId;
    this.spinnerId = 'docu-widget-spinner-' + widgetId;

    var thisWidgetKey = this.widgetId;
    thisWidgetKey = thisWidgetKey.replace(/-/gi, "");
    thisWidgetKey = thisWidgetKey.replace(/[0-9]/gi, "");
    thisWidgetKey = thisWidgetKey + new Date().getTime();
    window[thisWidgetKey] = this;

    this.createWidget = function () {
        var tags = [];
        tags.push('<div id="' + this.headerId + '" class="docu-widget-header">');
        tags.push('<div id="' + this.titleId + '" class="docu-widget-title">');
        tags.push('<h1 id="' + this.lebelId + '" class="docu-widget-label">' + this.widgetTitle + '</h1>');
        tags.push('<div id="' + this.spinnerId + '" class="docu-widget-spinner"></div>');
        tags.push('<div style="clear:both;"></div>');
        tags.push('</div>');
        tags.push('<div id="' + this.breadcrumbId + '" class="docu-widget-breadcrumb"></div>');
        tags.push('</div>');

        tags.push('<div id="' + this.contentId + '" class="docu-widget-content" style="overflow:auto;"></div>');
        $('#' + this.widgetId).html(tags.join(''));

        var contentWidth = $('#' + this.widgetId).parent().width();
        $('#' + this.contentId).width(contentWidth);
        $('#' + this.titleId).width(contentWidth - 16);

        this.breadcrumbItems = {};
    };

    this.ajax = function (url, params) {
        var key = params.key;
        var label = params.label;
        if (key && label) {
            delete params["key"];
            delete params["label"];
            this.breadcrumbItems[key] = {key: key, label: label, url: url, params: params};
        }
        this.createBreadcrumb();

        var title = params.title;
        if (title) {
            delete params["title"];
            $('#' + this.lebelId).html($('#' + this.lebelId).html() + " (" + title + ")");
        }

        var contentId = this.contentId;
        var spinnerId = this.spinnerId;
        var contentHeight = $('#' + this.widgetId).parent().height() - $('#' + this.headerId).height() - 5;
        this.startSpinner();

        DocuAjax.html(url, params, function (html) {
            $('#' + spinnerId).html('');
            $('#' + contentId).html(html);
            $('#' + contentId).height(contentHeight);
            //$('#'+contentId).show("explode", { pieces: 20 }, 1200);
        });
    };

    this.createBreadcrumb = function () {
        var links = [];
        var link = "";
        var counter = 0;
        var counterMax = this.countBreadcrumbItem();
        for (key in this.breadcrumbItems) {
            counter++;
            if (counter < counterMax) {
                link = '<a href="javascript:void(0);" onclick="' + thisWidgetKey + '.reload(\'' + key + '\')">' + this.breadcrumbItems[key].label + '</a>';
            }
            else {
                link = "<span>" + this.breadcrumbItems[key].label + "</span>";
            }
            links.push(link);
        }
        $('#' + this.breadcrumbId).html(links.join('&nbsp;>&nbsp;'));
    };

    this.reload = function (currentBreadcrumbKey) {
        var items = {};
        var currentBreadcrumb = this.breadcrumbItems[currentBreadcrumbKey];
        for (key in this.breadcrumbItems) {
            items[key] = this.breadcrumbItems[key];
            if (key == currentBreadcrumbKey) {
                break;
            }
        }
        this.breadcrumbItems = items;
        this.createBreadcrumb();

        var contentId = this.contentId;
        var spinnerId = this.spinnerId;
        var contentHeight = $('#' + this.widgetId).parent().height() - $('#' + this.headerId).height() - 5;
        this.startSpinner();
        DocuAjax.html(currentBreadcrumb.url, currentBreadcrumb.params, function (html) {
            $('#' + spinnerId).html('');
            $('#' + contentId).html(html);
            $('#' + contentId).height(contentHeight);
            //$('#'+contentId).show("explode", { pieces: 20 }, 1200);
        });
    };

    this.startSpinner = function () {
        $('#' + this.spinnerId).html('<img height="12px" src="' + FACE_BOOK_AJAX_LOADER_ICON_URL + '" />');
    };

    this.stopSpinner = function () {
        $('#' + this.spinnerId).html('');
    };

    this.countBreadcrumbItem = function () {
        var size = 0;
        for (key in this.breadcrumbItems) {
            size++;
        }
        return size;
    };
}

/*
 * Add dynamic rows in grid using js
 */
function DocuGrid(gridId) {
    this.gridId = gridId;

    this.addRows = function (dataList) {
        for (var i = 0; i < dataList.length; i++) {
            $('#variableInstallmentGrid').jqGrid('addRowData', i, dataList[i]);
        }
    };
}

function DocuDataTable(selector, fields, onCellDraw) {
    this.table = null;
    this.fields = fields;
    this.aoColumns = [];
    this.oSettings = null;
    for (var key in this.fields) {
        if (this.fields[key].toLowerCase() == 'id') {
            this.aoColumns.push({"mDataProp": this.fields[key], "bVisible": false});
        } else {
            this.aoColumns.push({"mDataProp": this.fields[key]});
        }
    }
    onCellDraw = onCellDraw ? onCellDraw : function (a, b, c) {
        return a;
    };
    this.table = $('#' + selector).dataTable({
        "bDestroy": true,
        "aaData": [],
        "bPaginate": true,
        "bJQueryUI": true,
        "aoColumns": this.aoColumns,
        "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            //var fname = aData.fname.toString() + " ["+aData.pin+"]";
            //$('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + fname + '\')">' + aData.pin + '</a>');
            //return nRow;
            onCellDraw(nRow, aData, iDisplayIndex);
        }
    });
    this.oSettings = this.table.fnSettings();

    this.addRow = function (map) {
        this.table.oApi._fnAddData(this.oSettings, map);
        this.table.fnDraw();
    }

    this.addRows = function (data) {
        this.table.fnClearTable();
        for (var i = 0; i < data.length; i++) {
            this.table.oApi._fnAddData(this.oSettings, data[i]);
        }
        //oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
        this.table.fnDraw();
    }
}

function setPreviousDatePicker(field) {
    $('#' + field).datepicker(
        {
            dateFormat:'dd-mm-yy',
            changeMonth:true,
            changeYear:true,
            maxDate:new Date()
        });
}

function setAdvanceDatePicker(field) {
    $('#' + field).datepicker(
        {
            dateFormat:'dd-mm-yy',
            changeMonth:true,
            changeYear:true,
            minDate: new Date()
        });
}


function setCommonDatePicker(field) {
    $('#' + field).datepicker({
            dateFormat:'dd-mm-yy',
            changeMonth:true,
            changeYear:true
        });
}
function setDateRange(from, to) {
    var dates = $("#" + from + " , #" + to).datepicker({
        maxDate:new Date(),
        dateFormat:'dd-mm-yy',
        changeMonth:true,
        changeYear:true,
        onSelect:function (selectedDate, inst) {
            var option = this.id == from ? "minDate" : "maxDate",
                instance = $(this).data("datepicker");
            date = $.datepicker.parseDate(
                instance.settings.dateFormat,
                selectedDate, instance.settings);
            dates.not(this).datepicker("option", option, date);
        }
    });
}

function setDateRangeNoLimit(from, to) {
    var dates = $("#" + from + " , #" + to).datepicker({
        dateFormat:'dd-mm-yy',
        changeMonth:true,
        changeYear:true,
        onSelect:function (selectedDate, inst) {
            var option = this.id == from ? "minDate" : "maxDate",
                instance = $(this).data("datepicker");
            date = $.datepicker.parseDate(
                instance.settings.dateFormat,
                selectedDate, instance.settings);
            dates.not(this).datepicker("option", option, date);
        }
    });
}

function DocuFlaxBox(selectorDiv, selectorInput, args) {
    this.flaxBox = null;
    this.selectorDiv = selectorDiv;
    this.selectorInput = selectorInput;
    this.args = prepareArguments(this, args);
    this.hiddenField = $.fn.flexbox.defaults.hiddenValue;
    this.displayField = $.fn.flexbox.defaults.displayValue;
    this.data = [];

    make(this, []);

    function make(_docuFlaxBox, _data) {
        $('#' + _docuFlaxBox.selectorDiv).empty();
        _docuFlaxBox.flaxBox = $('#' + _docuFlaxBox.selectorDiv).flexbox({results: _data, total: _data.length}, _docuFlaxBox.args);
        $('#' + _docuFlaxBox.selectorDiv + '_input').blur(function () {
            if ($('#' + _docuFlaxBox.selectorDiv + '_hidden').val() != $('#' + _docuFlaxBox.selectorInput).val()) {
                _docuFlaxBox.val("null");
            }
        });
    };

    function prepareArguments(_docuFlaxBox, _args) {
        args = args ? args : {};
        if (args.hiddenValue) {
            _docuFlaxBox.hiddenField = args.hiddenValue;
        }
        if (args.displayValue) {
            _docuFlaxBox.displayField = args.displayValue;
        }
        if (!args.watermark) {
            args.watermark = '';
        }
        if (!args.resultTemplate) {
            args.resultTemplate = '<div class="floatL col1">{id}</div><div class="floatL col2">{name}</div>';
        }
        if (!args.paging) {
            args.paging = {pageSize: 5};
        }
        if (!args.noResultsText) {
            args.noResultsText = 'No data found';
        }
        if (!args.maxCacheBytes) {
            args.maxCacheBytes = 32768;
        }
        var callback = args.onSelect ? args.onSelect : function () {
        };
        args.onSelect = function () {
            var hiddenValue = $('#' + _docuFlaxBox.selectorDiv + '_hidden').val();
            var displayValue = $('#' + _docuFlaxBox.selectorDiv + '_input').val();
            var map = _docuFlaxBox.find(hiddenValue);
            _docuFlaxBox.val(hiddenValue);
            callback(map);
        }
        return args;
    };

    this.setData = function (data) {
        make(this, data);
        this.data = data;
        return this;
    };

    this.clear = function () {
        make(this, []);
        this.data = [];
        $('#' + this.selectorInput).val("null");
        return this;
    };

    this.find = function (needle) {
        var fb = this;
        var map = $.grep(fb.data, function (element, index) {
            return element[fb.hiddenField] == needle;
        });
        if (map.length) {
            map = map[0];
        }
        else {
            map = null;
        }
        return map;
    };

    this.val = function (val) {
        if (val != null) {

            if (val == 'null' && $("#" + this.selectorDiv + "_hidden").val() != "") {

            }
            else {
                $('#' + this.selectorInput).val(val);
            }


            //var map = this.find(val);
            //if(map){
            //    this.flaxBox.setValue(map[this.args.displayValue]);
            //}
        }
        else {
            return $('#' + this.selectorInput).val();
        }
    };

    this.text = function () {
        return $('#' + this.selectorDiv + '_input').val();
    };

    this.select = function (hiddenValue, displayValue) {
        $('#' + this.selectorDiv + '_hidden').val(hiddenValue);
        $('#' + this.selectorDiv + '_input').val(displayValue);
        $('#' + this.selectorInput).val(hiddenValue);
        $('#' + this.selectorDiv + '_input').removeClass('watermark');
    }
}

