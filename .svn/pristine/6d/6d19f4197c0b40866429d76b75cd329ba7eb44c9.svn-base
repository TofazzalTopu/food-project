<script type="text/javascript" language="Javascript">
    var tabIndex = '';
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormMiscellaneousTransactions").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormMiscellaneousTransactions").validationEngine('attach');
        $(function () {
            $("#tabs").tabs();
        });
    });

</script>