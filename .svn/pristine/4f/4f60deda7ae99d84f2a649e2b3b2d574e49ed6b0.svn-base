

<script type='text/javascript'>
$(document).ready(function () {
jQuery('#customerList').autocomplete({
    minLength:'2',
    source:function (request, response) {
        //EmployeeRegister.employeeCoreInfoId = null;
        $('#popEmpDetails').html("");
        var data = {searchKey:request.term};
        var url = '${resource(dir:'customerMaster', file:'listCustomer')}';
            DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                item['label'] = item['legacy_id'] + " [" + item['code'] + "] " + item['name'];
                item['value'] = item['label'];
                return item;
            });
        },
        select:function (event, ui) {

            $('#hCustomerId').val(ui.item.id);
            $('#name').val(ui.item.name);
           // getNetReceiveAble(ui.item.id);
        }
    }).data("autocomplete")._renderItem = function (ul, item) {
        var accountstype = "";
        if (item.id) {
            accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Legacy ID: " + item.legacy_id + ", Code: " +item.code+", Name: "+item.name+",Address: "+item.present_address + '</div>';
        }
        return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

    }

})
</script>