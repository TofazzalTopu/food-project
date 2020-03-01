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
        jQuery('#employeeSearchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'salesHead', file:'listEmployee')}?query=' + $('#employeeSearchKey').val();
                DocuAutoComplete.setSpinnerSelector('employeeSearchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadEmployeeDataInField(ui.item.id, ui.item.name, ui.item.code, ui.item.designation);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "PIN: " + item.code + "; Name: " + item.name + "; " + "Enterprise: " + item.enterprise + "; " + "Designation: " + item.designation + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
        $('#search-btn-employee-id').click(function () {
            var url = '${resource(dir:'salesHead', file:'popupEmployeeListPanel')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        });

        $("#targetAmount").format({precision: 2, allow_negative: false, autofix: true});
//        $("#year").format({precision: 0, allow_negative: false, autofix: true});
        $("#targetYear").mask("2099");
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
            url: "${request.contextPath}/${params.controller}/create",
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
                this.value = this.defaultValue;
            }
        });
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }
</script>