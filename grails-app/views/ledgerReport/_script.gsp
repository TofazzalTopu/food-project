
<script>
    $(document).ready(function () {

        loadAutoPrefixCode();
        loadAutoPostfixCode();

        selectCOAAccountHead();

    })

    function selectCOAAccountHead() {

        var size;
        var options = '';
        if ('${coaList}' != '') {
            size = ${coaListSize};
            if (size == 1) {
                options = '<option value="' + ${coaList}[0].id + '">' + ${coaList}[0].charOfAccountName + '</option>';
            } else {
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${coaList}[i].id + '">' + ${coaList}[i].charOfAccountName +"-["+ ${coaList}[i].charOfAccountCodeUser +"]"+  '</option>';
                }
            }
            $("#chartOfAccountsId").html(options);
        }
    }
    var loadAutoPrefixCode=function()
    {
        jQuery('#prefixCode').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'ledgerReport', file:'listPrefixCode')}?query=' + $('#prefixCode').val();
                DocuAutoComplete.setSpinnerSelector('prefixCode').execute(response, url, data, function (item) {

                    item['label'] = item['prefixCode'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadPrefixData(ui.item.prefixCode);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "Prefix: " + item.prefixCode + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['prefixCode'] + '</a>').appendTo(ul);
        };
    }

    var loadAutoPostfixCode=function()
    {
        jQuery('#postfixCode').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'ledgerReport', file:'listPostfixCode')}?query=' + $('#postfixCode').val();
                DocuAutoComplete.setSpinnerSelector('postfixCode').execute(response, url, data, function (item) {

                    item['label'] = item['postfixCode'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadPostfixData(ui.item.postfixCode);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "PostFix: " + item.postfixCode + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['postfixCode'] + '</a>').appendTo(ul);
        };
    }


    var loadPrefixData=function(prefixCode) {
        $('#prefixCode').val(prefixCode)
    }


    var loadPostfixData=function(postfixCode){
        $('#postfixCode').val(postfixCode)
    }

</script>