<%@ page import="com.bits.bdfp.customer.CustomerMaster" %>
<form name='gFormSearchCustomerMaster' id='gFormSearchCustomerMaster'>
    <div id="remote-content-customerMaster"></div>
    <br>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label for='customerList' class='txtright bold hight1x width1x'>
            Search Customer
        </label>

        <div class="element-input inputContainer width400">
            <input type="text" id="customerList" name="customerList" class="width350"/>
            <span id="search-btn-customer-register-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span>
        </div>
    </div>

    <div class="clear"></div>

    <div class="clear"></div>

    <div id="popEmpDetails" class="width1021">
    </div>
</form>

<script>
    $(document).ready(function(){
        reset_form("#gFormSearchCustomerMaster");
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

                EmployeeRegister.setEmployeeInformation(ui.item.id, ui.item.value);
                EmployeeRegister.showEmployeeDetail();
                $('#name').val(ui.item.name);
//        CustomerRegister.showCustomerDetail(ui.item.account_no);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item.id) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Legacy ID: " + item.legacy_id + ", Code: " +item.code+", Name: "+item.name+",Address: "+item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }

        $('#search-btn-customer-register-id').click(function(){
            EmployeeRegister.popupEmployeeListPanel();
        });
        $("#tabs span.ui-icon-close").live("click", function () {

            var index = $("li", $tabs).index($(this).parent());
            if (index > 0) {
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 140,
                    modal: true,
                    autoOpen: false,
                    buttons: {
                        "Yes": function () {
                            $(this).dialog("close");
                            $tabs.tabs("remove", index);
                        },
                        Cancel: function () {
                            $(this).dialog("close");
                        }
                    }
                });
                $("#dialog-confirm").dialog('open');
            }
        });
    });
    var EmployeeRegister = {
        employeeCoreInfoId: null,
        popupEmployeeListPanel: function(){
            var url = "${request.contextPath}/${params.controller}/popupCustomerListPanel";
            var params = {unqId:"employeeRegisterId"};
            DocuAjax.html(url, params, function(html){
                $.fancybox(html);
            });
        },

        showEmployeeDetailsById: function(employeeCoreInfoId){
            EmployeeRegister.employeeCoreInfoId = employeeCoreInfoId
            EmployeeRegister.showEmployeeDetail();
            EmployeeRegister.employeeCoreInfoId = null
        }
        ,
        showEmployeeDetail: function(){
            if(!EmployeeRegister.employeeCoreInfoId){
                MessageRenderer.renderErrorText("Select an employee.", "Employee Detail Information");
                return false;
            }
            var url = "${request.contextPath}/${params.controller}/edit";
            var params = {id:EmployeeRegister.employeeCoreInfoId};
            AjaxLoader.showTo('popEmpDetails');
            DocuAjax.html(url, params, function(html){
                $('#popEmpDetails').html(html);
            });
        },
        setEmployeeInformation: function(employeeCoreInfoId, employeeCoreInfo){
            $("#employeeCoreInfo").val(employeeCoreInfo);
            EmployeeRegister.employeeCoreInfoId = employeeCoreInfoId;
        }
    };
</script>