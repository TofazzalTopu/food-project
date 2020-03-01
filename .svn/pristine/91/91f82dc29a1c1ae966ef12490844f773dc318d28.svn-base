<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormChartOfAccountsMapping").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormChartOfAccountsMapping").validationEngine('attach');
//        reset_form("#gFormChartOfAccountsMapping");
    });

    function executePreConditionChartOfAccountsMapping() {
        // trim field vales before process.
//        trim_form();
        if (!$("#gFormChartOfAccountsMapping").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxChartOfAccountsMapping() {
        if (!executePreConditionChartOfAccountsMapping()) {
            return false;
        }
        var enterpriseId = $("#enterpriseId").val();
        if(!enterpriseId){
            MessageRenderer.renderErrorText("Enterprise is not selected");
            return
        }
        var coaType = $("#coaType").val();
        if(!coaType){
            MessageRenderer.renderErrorText("Chart of Accounts Type is not selected");
            return
        }
        var coaList = $("#coaMapping-list").val();
//        console.log(coaList.toString());
//        return
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {enterpriseId: enterpriseId, coaType: coaType, coaList: coaList.toString()},
//            data: jQuery("#gFormChartOfAccountsMapping").serialize(),
            url: "${request.contextPath}/${params.controller}/create",
            success: function (data) {
                executePostConditionChartOfAccountsMapping(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
        return false;
    }
    function executePostConditionChartOfAccountsMapping(result) {
        if (result.type == 1) {
//            reset_form('#gFormChartOfAccountsMapping');
        }
        MessageRenderer.render(result);
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Submit');
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

    function loadMappingData(){
        var enterpriseId = $("#enterpriseId").val();
        if(!enterpriseId){
            MessageRenderer.renderErrorText("Enterprise is not selected");
            return
        }
        var coaType = $("#coaType").val();
        if(!coaType){
            MessageRenderer.renderErrorText("Chart of Accounts Type is not selected");
            return
        }
        $("#chartOfAccountsMappingBlock").html("");
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {enterpriseId: enterpriseId, coaType: coaType},
            url: "${request.contextPath}/${params.controller}/showCoaMappingData",
            success: function (result) {
                $("#chartOfAccountsMappingBlock").html(result);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#chartOfAccountsMappingBlock").html("");
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'html'
        });
    }
</script>