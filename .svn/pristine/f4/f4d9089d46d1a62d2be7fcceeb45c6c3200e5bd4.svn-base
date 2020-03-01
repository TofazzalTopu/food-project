<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSalesHeadByVolume").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSalesHeadByVolume").validationEngine('attach');
//        reset_form("#gFormSalesHeadByVolume");

        $("#targetAmount").format({precision: 2, allow_negative: false, autofix: true});
    });

    function executePreConditionSalesHeadByVolume() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormSalesHeadByVolume").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxSalesHeadByVolume() {
        var data = jQuery("#gFormSalesHeadByVolume").serializeArray();
        var gd = $("#jqgrid-grid-finishProduct").jqGrid('getRowData');
        var itemCount = gd.length;
        if(itemCount <= 0){
            MessageRenderer.renderErrorText("No Product Item Selected");
            return;
        }
        for (var i=0; i < itemCount; ++i) {
            data.push({'name':'salesHeadFinishProduct['+i+'].finishProductId', 'value': gd[i].id});
            data.push({'name':'salesHeadFinishProduct['+i+'].id', 'value': gd[i].salesHeadFinishProductId});
            data.push({'name':'salesHeadFinishProduct['+i+'].quantity', 'value': gd[i].quantity});
        }
        data.push({'name':'itemCount', 'value': itemCount});
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/update",
            success: function (data, textStatus) {
                executePostConditionSalesHeadByVolume(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    reset_form('#gFormSalesHeadByVolume');
                    $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');

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
        return;
    }
    function executePostConditionSalesHeadByVolume(result) {
        if (result.type == 1) {
            reset_form('#gFormSalesHeadByVolume');
            $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
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
    function changeYear(salesHeadByVolumeId){
        $("#salesHeadByVolumeId").html("");
        if(salesHeadByVolumeId){
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${request.contextPath}/${params.controller}/saleHeadByVolumeDetails?id=" + salesHeadByVolumeId,
                success: function (data) {
                    $("#salesHeadByVolumeId").html(data)
                },
                complete: function(){
                    SubmissionLoader.hideFrom();
                },
                dataType: 'html'
            });
        }
    }

</script>