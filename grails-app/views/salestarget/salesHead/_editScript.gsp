<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSalesHead").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSalesHead").validationEngine('attach');
        reset_form("#gFormSalesHead");

        $("#targetAmount").format({precision: 2, allow_negative: false, autofix: true});
    });
    function loadEmployeeDataInField(employeeId, cusName, code, designation) {
        $("#employeeId").val(employeeId);
        $("#employeePin").val(code);
        $("#employeeName").val(cusName);
        $("#employeeDesignation").val(designation);
    }

    function executePreConditionSalesHead() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormSalesHead").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxSalesHead() {
        if (!executePreConditionSalesHead()) {
            return false;
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormSalesHead").serialize(),
            url: "${request.contextPath}/${params.controller}/update",
            success: function (data, textStatus) {
                executePostConditionSalesHead(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    reset_form('#gFormSalesHead');
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
    function executePostConditionSalesHead(result) {
        if (result.type == 1) {
            reset_form('#gFormSalesHead');
        }
        MessageRenderer.render(result);
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = "";
            }
        });
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').hide();
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }
    function changeYear(salesHeadId){
        $("#salesHeadId").html("");
        if(salesHeadId){
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${request.contextPath}/${params.controller}/saleHeadDetails?id=" + salesHeadId,
                success: function (data) {
                    $("#salesHeadId").html(data)
                },
                complete: function(){
                    SubmissionLoader.hideFrom();
                },
                dataType: 'html'
            });
        }
    }
</script>