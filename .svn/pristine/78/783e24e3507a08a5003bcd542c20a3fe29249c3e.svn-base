/*************************************************************************************************
 * Created By: Rafiq(jr)
 * Creation Date:23-04-2011
 * Purposes:Passing control between field using keyboard. Initially control/focus on second field (After reportFormat dropdown).
 * When user press on 'enter' or 'tab' key focus goes to next field
 * When user press on left arrow focus goes to previous field
 * when user press on submit button or button having property onclick, control goes to appropriate action
 * Caution : Press on submit button or button having property onclick only works if it exist at last, otherwise control goes to next field
 * Uses: All the report .GSP pages
 * Methods:passControlBetweenFields(),checkForKeys(event)
 ********************************************************/

var wmProjectSelectLabel =  'Enter Project Code/Name';
var wmCostCenterSelectLabel =  'Enter Cost Center Name';
var wmSelectVoucherLabel =  'Enter VIN';
var wmSelectChartOfAccountLabel =  'Enter Account Code/Name';
var wmSelectSubsidiaryAccountEntityLabel =  'Enter Entity Code/Name';
var wmSelectSubsidiaryAccountFilterLabel =  'Enter Entity Filter Code/Name';

var wmFromDateLabel =  'DD-MM-YYYY';
var wmToDateLabel =  'DD-MM-YYYY';
var wmOnDateLabel =  'DD-MM-YYYY';

jQuery.fn.exists = function() {
    return (this.length > 0);
};


function docuDateComparision(field, rules, i, options) {

    var fromDateArray = $('#fromDate').val().split("-");
    var toDateArray = $('#toDate').val().split("-");

    var fromDate = new Date(fromDateArray[2], fromDateArray[1] - 1, fromDateArray[0]);
    var toDate = new Date(toDateArray[2], toDateArray[1] - 1, toDateArray[0]);

    var msg = '';

    if (field.attr('id') == 'fromDate') {
        msg = 'From Date cannot be greater than To Date';
    } else {
        msg = 'To Date cannot be lower than From Date';
    }
    if (fromDate > toDate) {
        return msg;
    }

}


var ReportTemplate = {
    width: function(id, width) {
        if ($('#' + id).exists()) {
            $('#' + id).width(width);
            $('#div-label-' + id).width(width);
            $('#div-input-' + id).width(width);
        }
    },
    checkInput: function(visibleFieldId, hiddenFieldId) {
        if ($("#" + visibleFieldId).exists()) {
            if ($("#" + hiddenFieldId).val() == '') {
                $("#" + visibleFieldId).val('');
            }
        }
    },

    checkDependency:function(data, request, response) {
        if ($('#' + data.dependable).exists()) {
            if ($('#' + data.dependable).val() == '') {
                var msg = {value : ""};
                msg.label=data.message;
                response([msg]);
                return false;
            }
        }
        return true;
    },
    getSelectProjectMessage:function(){
        return "Select Project First";
    },
    getSelectCoMessage:function(){
        return "Select CO First";
    },
    getGroupMessage:function(){
        return "Select Group First";
    },
    getVoucherMessage:function(){
        return "Select Cost Center First";
    },
    getGroupNumbersForAutoComplete:function(url, data, request, response) {
        data['term'] = request.term;
        $.ajax({
            url: url,
            dataType: "json",
            data: data,
            success: function(data) {
                response($.map(data, function(item) {
                    item.label = item.label + " (" + item.group_name + ")";

                    item.employee_name = item.employee_name + " (" + item.pin_number + ")";

                    var list = item.value.split('-');
                    item.value = list[list.length - 1];
                    return item
                }));

                if (data.length == 0) {
                    response([
                        {"id":"","label":"No Group found","value":""}
                    ]);
                }
            }
        });
    },
    getMemberNumbersForAutoComplete: function(url, data, request, response) {
        data['term'] = request.term;
        $.ajax({
            url: url,
            dataType: "json",
            data: data,
            success: function(data) {
                response($.map(data, function(item) {
                    item.label = item.label + " (" + item.memberName + ")";

                    var list = item.value.split('-');
                    item.value = list[list.length - 1];
                    return item
                }));

                if (data.length == 0) {
                    response([
                        {"id":"","label":"No Member found","value":""}
                    ]);
                }
            }
        });
    }
}



var next = 0;
function groupLookupForm() {
    var jump = 0;
    if ($('#group_number').exists()) {
        if (!$('#group_number').is(':visible')) {
            jump = 1;
        }
    }
    return  jump;
}

var currentFieldNumber = 0;

function passControlBetweenFields() {

    allowedFieldWithAttributes = $("input:visible:not(:file,[disabled],[readonly]),select:visible:not([disabled],[readonly]),textarea, button");
    initialFocusField = $("input:visible:not(:file,[disabled],[readonly]),select,textarea, button");
    initialFocusField[initialFocusField.index($("#reportFormat")) + 1].focus();

    if ($.browser.mozilla) {
        $(allowedFieldWithAttributes).keypress(checkForKeys);
    } else {
        $(allowedFieldWithAttributes).keydown(checkForKeys);
    }
}
function checkForKeys(event) {
    if (event.keyCode == 13 || event.keyCode == 9) {  //keyCode = 13 for enter key  and event.keyCode == 9 for tab key
        currentFieldNumber = allowedFieldWithAttributes.index(this);

        if ($(allowedFieldWithAttributes[currentFieldNumber]).attr("name") == 'project_code') {
            //ProjectInfoAjax.listen('groupLookupForm');
            //currentFieldNumber += next;
            currentFieldNumber += groupLookupForm();

            if ((allowedFieldWithAttributes[currentFieldNumber + 1] != null) && (!$(allowedFieldWithAttributes[currentFieldNumber + 1]).is(':visible'))) {
                currentFieldNumber++;
            }
        }

        if (allowedFieldWithAttributes[currentFieldNumber + 1] != null) {
            nextField = allowedFieldWithAttributes[currentFieldNumber + 1];
            nextField.focus();

            event.preventDefault();
            return false;
        }
    } else if (event.keyCode == 37) {  //keyCode = 37 for left arrow key
        currentFieldNumber = allowedFieldWithAttributes.index(this);

        if ($(allowedFieldWithAttributes[currentFieldNumber - 1]).attr("name") == 'group_number') {
            //ProjectInfoAjax.listen('groupLookupForm');
            //currentFieldNumber -= next;
            currentFieldNumber -= groupLookupForm();
        }

        if (allowedFieldWithAttributes[currentFieldNumber - 1] != null) {
            previousField = allowedFieldWithAttributes[currentFieldNumber - 1];
            previousField.focus();
            event.preventDefault();
            return false;
        }
    }
}


function loadHTMLUsingAjax(url, params, where) {
    SmartAjax.setUrl(url)
            .setParams(params)
            .getHtml(where);
}




    /*   Water Mark  Class begin here  */


        /*  Super class declaration*/

        function SbiWaterMark() {
        }

        SbiWaterMark.prototype.init = function (id, msg) {
            this.id = id;
            this.message = msg;
        };


        SbiWaterMark.prototype.doWaterMark = function() {
            //if($('#' + this.id).exists()){
              $('#' + this.id).watermark(this.message);
           // }
        };


        /* sub classes begin here */

        ProjectWaterMark.prototype = new SbiWaterMark();
        ProjectWaterMark.prototype.constructor = ProjectWaterMark;

        function ProjectWaterMark(id, msg) {
            this.init(id ? id : this.id, msg ? msg : this.message);
        }

        ProjectWaterMark.prototype.id = 'project_code';
        ProjectWaterMark.prototype.message = "Enter Project Code/Name";

        LoanAccountWaterMark.prototype = new SbiWaterMark();
        LoanAccountWaterMark.prototype.constructor = LoanAccountWaterMark;

        function LoanAccountWaterMark(id, msg) {
            this.init(id ? id : this.id, msg ? msg : this.message);
        }

        LoanAccountWaterMark.prototype.id = 'loan_no';
        LoanAccountWaterMark.prototype.message = "Enter Loan No.";


        GroupWaterMark.prototype = new SbiWaterMark();
        GroupWaterMark.prototype.constructor = GroupWaterMark;

        function GroupWaterMark(id, msg) {
            this.init(id ? id : this.id, msg ? msg : this.message);
        }

        GroupWaterMark.prototype.id = 'group_number';
        GroupWaterMark.prototype.message = "Enter Group Code/Name";

        MemberWaterMark.prototype = new SbiWaterMark();
        MemberWaterMark.prototype.constructor = MemberWaterMark;

        function MemberWaterMark(id, msg) {
            this.init(id ? id : this.id, msg ? msg : this.message);
        }

        MemberWaterMark.prototype.id = 'membershipNumber';
        MemberWaterMark.prototype.message = "Enter Member Number/Name";







