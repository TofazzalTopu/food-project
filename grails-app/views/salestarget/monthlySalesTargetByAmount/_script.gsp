<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormMonthlySalesTargetByAmount").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormMonthlySalesTargetByAmount").validationEngine('attach');
//        reset_form("#gFormMonthlySalesTargetByAmount");
        $("#create-button-monthlySalesTargetByAmount").hide();
    });

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
    function changeYear(targetYear){
        $("#monthWiseTarget").html("");
        if(targetYear){
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${request.contextPath}/${params.controller}/showMonthlyTargetByCurrentUser?targetYear=" + targetYear,
                success: function (data) {
                    $("#monthWiseTarget").html(data)
                },
                complete: function(){
                    SubmissionLoader.hideFrom();
                },
                dataType: 'html'
            });
        }
    }
</script>