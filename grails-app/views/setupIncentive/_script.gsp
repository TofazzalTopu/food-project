<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('SetupIncentive');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSetupIncentive").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSetupIncentive").validationEngine('attach');

        $(function () {
            $("#tabs").tabs();
        });

        if(${incentiveType == 'tbi'}){
            $('#tabs').tabs('select', '#fragment-1');
            $('#tbicList').show();
        }else{
            $('#tbicList').hide();
        }

        if(${incentiveType == 'sabi'}){
            $('#tabs').tabs('select', '#fragment-2');
            $('#sabicList').show();
        }else{
            $('#sabicList').hide();
        }

        if(${incentiveType == 'qbi'}){
            $('#tabs').tabs('select', '#fragment-3');
            $('#qbicList').show();
        }else{
            $('#qbicList').hide();
        }

        if(${incentiveType == 'vbi'}){
            $('#tabs').tabs('select', '#fragment-4');
            $('#vbicList').show();
        }else{
            $('#vbicList').hide();
        }
    });

    function getRowIdsAll(selectedRows){
        var result = [];
        $.each(selectedRows, function(i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function(n){ return n != undefined });
        var ids = ''
        for(key in selectedRowsFinal){
            if(ids){
                ids += ','+selectedRowsFinal[key]
            }else{
                ids = selectedRowsFinal[key]
            }
        }
        return ids;
    }

    function filterListOfIds(idsArray){
        var ids = [];
        $.each(idsArray, function(i, e) {
            if ($.inArray(e, ids) == -1) ids.push(e);
        });
        ids = ids.filter(function(n){ return n != undefined });
        return ids;
    }

</script>