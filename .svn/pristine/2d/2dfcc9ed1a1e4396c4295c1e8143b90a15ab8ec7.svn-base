/**
 * Created by .
 * User: Shompa
 * Date: 6/8/11
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
var docuRequired = "* Required";
var docuName = "* Name only";
var docuFloatNumber = "* Floating-point number with max 2 digit after decimal";
var docuFloatPositiveNumber = "* Value cannot be negative";
var docuFloatNumberGreaterZero = "* Value should be greater than zero";
var docuIntegerNumber = "* Integer number only";
var docuIntegerNumberGreaterZero = "* Value should be greater than zero";
var docuPhone = "* Phone number only";
var docuTimeMsg = "* Time only";

var docuInvalidHour = "* Invalid value for hours: ";
var docuInvalidMinute = "* Invalid value for minutes: ";
var docuInvalidTimeFormat = "* Invalid time format: ";
var docuPositiveNumber = "* Negative amount is not allowed.";

var letterDotSpace = "* Name only";
/* Letter, dot and space only     */
var floatAndBetweenZeroAndHundred = "* value must be between zero and hundred";
var floatPercentageNumber = "* Invalid percentage number";
var docuAlphaNumericValueMsg = "* Value should be Alpha-numeric";


/*
 Validates a field using a third party function call.
 If a validation error occurs, the function must return an error message that will automatically show in the error prompt.
 */
function isDropdownSelected(field, rules, i, options) {
    if (field.val() == "null") {
        //return options.allrules.validate2fields.alertText;
        return docuRequired;
    }
    if (field.val() == '') {
        //return options.allrules.validate2fields.alertText;
        return docuRequired;
    }
}

/*
 Validates a name field using a third party function call.
 If a validation error occurs, the function must return an error message that will automatically show in the error prompt.
 This methods check name field that allow ony Letters and special characters -(minus),  (space), and .(dot) only
 Creation date:10/03/2011
 */
// docuAlphanumericValue
function docuCharactersForNameField(field, rules, i, options) {
    var objRegExp = /^[^0-9][A-Za-z0-9 -./_%&#()[\\\]]+$/;
    if (!objRegExp.test(field.val())) {
        //return options.allrules.validate2fields.alertText;
        return "* Required a valid name";
    }
}

function docuNameFieldStartsWithNumber(field, rules, i, options) {
    var objRegExp = /^[A-Za-z0-9 -./_%&#()]+$/;
    if (!objRegExp.test(field.val())) {
        //return options.allrules.validate2fields.alertText;
        return "* Required a valid name";
    }
}
function docuAlphaNumericValue(field, rules, i, options) {
    var objRegExp = /^[A-Za-z0-9 -]+$/;
    if (!objRegExp.test(field.val())) {
        //return options.allrules.validate2fields.alertText;
        return docuAlphaNumericValueMsg;
    }
}
function setAlphaNumericField(inputId){
    $("#"+inputId).keypress(function (e) {
        var regex = new RegExp("^[a-zA-Z0-9\b]+$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
            return true;
        }

        e.preventDefault();
        return false;
    });
}
function setAlphaNumericPlusField(inputId){
    $("#"+inputId).keypress(function (e) {
        var regex = new RegExp("^[a-zA-Z0-9-. #\b]+$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
            return true;
        }

        e.preventDefault();
        return false;
    });
}
function setNameFieldOnlyByClass(inputId){
    $("."+inputId).keypress(function (e) {
        if(e.keyCode == 9){
            return true;
        }
        var regex = new RegExp("^[a-zA-Z,-. '_\b]+$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
            return true;
        }
        e.preventDefault();
        return false;
    });
}
function setAlphaNumericFieldByClass(inputId){
    $("#"+inputId).keypress(function (e) {
        if(e.keyCode == 9){
            return true;
        }
        var regex = new RegExp("^[a-zA-Z0-9\b]+$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
            return true;
        }

        e.preventDefault();
        return false;
    });
}

function setNumericField(inputId) {
    $("#" + inputId).format({precision:0,allow_negative:false,autofix:true});
}

function setNumericFieldByClass(inputFieldClass) {
    $("." + inputFieldClass).format({precision:2,allow_negative:false,autofix:true});
}
function setNumericFieldForCurrencyExchange(inputId) {
    $("#" + inputId).format({precision:8,allow_negative:false,autofix:true});
}

function setPhoneNumber(inputId) {
    $("#" + inputId).numeric({allow:"+-() "});
}

function setIntegerField(inputId) {
    $("#" + inputId).format({precision:0,allow_negative:false,autofix:true});
}

function setNumericFieldWithNegative(inputId) {
    $("#" + inputId).format({precision:2,allow_negative:true,autofix:true});
}

function percentageChecking(field, rules, i, options) {
    if (parseFloat(field.val()) > 100) {
        return "Interest Rate should be less than 100"
    }
}

function docuNameSp(field, rules, i, options) {
    var objRegExp = /^[A-Za-z -._]+$/;
    if (!objRegExp.test(field.val())) {
        //return options.allrules.validate2fields.alertText;
        return docuName;
    }
}

function docuNameBlockingScript(field, rules, i, options) {
    //var objRegExp = /^[A-Za-z -._]+$/;
    var objRegExp = /^[A-Za-z _.-]+$/;
    if (!objRegExp.test(field.val())) {
        //return options.allrules.validate2fields.alertText;
        return docuName;
    }
}

function docuFloatSp(field, rules, i, options) {
    var objRegExp = /^[0-9.-]+$/;
    if (!objRegExp.test(field.val())) {
        return docuFloatNumber;
    }
}
function docuNonNegativeFloat(field, rules, i, options) {
    var objRegExp = /^[0-9.-]+$/;
    if (!objRegExp.test(field.val())) {
        return docuFloatNumber;
    } else if (parseFloat(field.val()) < 0) {
        return docuFloatPositiveNumber;
    }
}

function docuFloatAndGreaterZero(field, rules, i, options) {
    //var objRegExp = /^[0-9.-]+$/;
    var objRegExp = /^\s*-?(\d+(\.\d{1,2})?|\.\d{1,2})\s*$/;
    if (!objRegExp.test(field.val())) {
        return docuFloatNumber;
    } else if (parseFloat(field.val()) <= 0) {
        return docuFloatNumberGreaterZero;
    }
}
function docuGreaterZero(field, rules, i, options) {
    var objRegExp = /^[0-9.,-]+$/;
    if (!objRegExp.test(field.val())) {
        return docuFloatNumber;
    } else if (parseFloat(field.val()) <= 0) {
        return docuFloatNumberGreaterZero;
    }
}


function docuIntegerAndGreaterZero(field, rules, i, options) {
    var objRegExp = /^[0-9]+$/;
    if (!objRegExp.test(field.val())) {
        return docuIntegerNumber;
    } else if (parseInt(field.val()) <= 0) {
        return docuIntegerNumberGreaterZero;
    }
}

function docuPhoneSp(field, rules, i, options) {
    var objRegExp = /^[0-9+()-]+$/;
    if (!objRegExp.test(field.val())) {
        return docuPhone;
    }
}

function docuTime(field, rules, i, options) {
    var objRegExp = /^[0-9+()-]+$/;
    if (!objRegExp.test(field.val())) {
        //return "* Phone number only [0-9,+,-,(,)]";
        return docuTimeMsg;
    }
}


/* This method  mainly used in setup to validate name such as salutation, occupation  etc     */
function docuLetterDotSpaceOnly(field, rules, i, options) {
    var objRegExp = /^[A-Za-z. ]+$/;
    if (!objRegExp.test(field.val())) {
        return letterDotSpace;
    }
}

function docuFloatAndBetweenZeroAndHundred(field, rules, i, options) {
    var objRegExp = /^[0-9.-]+$/;
    if (!objRegExp.test(field.val())) {
        return docuFloatNumber;
    } else if (parseFloat(field.val()) <= 0 || parseFloat(field.val()) > 100) {
        return floatAndBetweenZeroAndHundred;
    }
}
function docuFloatPercentage(field, rules, i, options) {
    var objRegExp = /^[0-9.-]+$/;
    if (!objRegExp.test(field.val())) {
        return docuFloatNumber;
    } else if (parseFloat(field.val()) < 0 || parseFloat(field.val()) > 100) {
        return floatPercentageNumber;
    }
}
function docuCheckTime(field, rules, i, options) {
    var objRegExp = /^(\d{1,2}):(\d{2})\s(:00)?([aApP][mM])?$/;

    if (field.val() != '') {
        fVal = field.val()
        if (regs = fVal.match(objRegExp)) {

            if (regs[4]) {
                if (regs[1] < 1 || regs[1] > 12) {
                    return docuInvalidHour + regs[1];
                }
            }
            if (regs[2] > 59) {
                return docuInvalidMinute + regs[2];
            }
        } else {
            return docuInvalidTimeFormat + fVal;
        }
    }
}

function docuEmail(field, rules, i, options) {
    var pattern =  /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
//    var objRegExp = /^[^0-9][A-Za-z0-9 -./_%&#()[\\\]]+$/;
    if (field.val() == '') {
        return ;
    }else if (!pattern.test(field.val())) {
        return "* Invalid email address";
    }
}

function docuAccountNumber(field, rules, i, options) {
    var objRegExp = /^[A-Za-z0-9 -./_%&#()]+$/;
    if (field.val() == '') {
        return ;
    }else if (!objRegExp.test(field.val())) {
        //return options.allrules.validate2fields.alertText;
        return "* Required a valid number";
    }
}
function docuPhoneNumber(field, rules, i, options) {
    var objRegExp = /^([\+][0-9]{1,3}[ \.\-])?([\(]{1}[0-9]{2,6}[\)])?([0-9 \.\-\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$/;
    if (field.val() == '') {
        return ;
    }else if (!objRegExp.test(field.val())) {
        //return options.allrules.validate2fields.alertText;
        return "* Invalid phone number";
    }
}

