<%@ page import="com.bits.bdfp.setup.salestarget.VisitPlan" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="visitPlan.create.label" default="Visit Plan"/></title>
<script type="text/javascript">
    $(document).ready(function(){
        initiateDatePickers();
        initializeGrid();
        initiateAutoComplete();
    });
    function initiateDatePickers(){
        $("#dateFrom, #dateTo").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
//                    minDate: 0
                });
        $('#dateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#dateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
    }
    function initializeGrid() {
        $("#jqgrid-grid").jqGrid({
            datatype: "json",
            colNames: [
                'ID',
                'Address To Visit',
                'Date From',
                'Date To',
                'Time From',
                'Time To',
                'Purpose',
                'Remarks'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'place_of_visit', index: 'place_of_visit', width: 200, align: 'left', hidden: false},
                {name: 'date_from', index: 'date_from', width: 100, align: 'left'},
                {name: 'date_to', index: 'date_to', width: 100, align: 'center'},
                {name: 'time_from', index: 'time_from', width: 100, align: 'center'},
                {name: 'time_to', index: 'time_to', width: 100, align: 'center'},
                {name: 'purpose', index: 'purpose', width: 100, align: 'right'},
                {name: 'remarks', index: 'remarks', width: 100, align: 'right'}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#customer-order-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Secondary Order List",
            autowidth: false,
            width: 950,
            height: 450,
            scrollOffset: 0
        });
        $("#customer-order-grid").jqGrid('navGrid', '#customer-order-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });

    }
   function initiateAutoComplete(){
       $('#employeeSearchKey').blur(function () {
           if ($('#employeeSearchKey').val() == '') {
               clearEmployeeDataField();
           }
       });
       $('#employeeSearchKey').autocomplete({
           minLength: '1',
           source: function (request, response) {
               var data = {searchKey: request.term};
               var url = '${resource(dir:'userAccount', file:'listEmployee')}?query=' + $('#employeeSearchKey').val();
               DocuAutoComplete.setSpinnerSelector('employeeSearchKey').execute(response, url, data, function (item) {
                   item['label'] = "[" + item['code'] + "] " + item['name'];
                   item['value'] = item['label'];
                   return item;
               });
           },
           select: function (event, ui) {
               loadEmployeeDataInField(ui.item.id);
           }
       }).data("autocomplete")._renderItem = function (ul, item) {
           var accountstype = "";
           if (item) {
               accountstype = '<div style="font-size: 9px; color:#326E93;">' + "PIN: " + item.code + "; Name: " + item.name + "; " + "Enterprise: " + item.enterprise + "; " + "Address: " + item.present_address + '</div>';
           }
           return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
       };
   }
    function clearEmployeeDataField() {
        $("#employeeId").val("");
    }
    function loadEmployeeDataInField(employeeId) {
        $("#employeeId").val(employeeId);
    }
    function searchGridItems(){
        var employeeId = "";
        var dateFrom = "";
        var dateTo = "";
        employeeId = $("#employeeId").val();
        dateFrom = $("#dateFrom").val();
        dateTo = $("#dateTo").val();

        $("#jqgrid-grid").setGridParam({
            url: "${request.contextPath}/${params.controller}/getListForGrid?employeeId=" + employeeId + "&dateFrom=" + dateFrom + "&dateTo=" + dateTo
        });
        $("#jqgrid-grid").trigger("reloadGrid");
        return false;
    }
</script>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="visitPlan.create.label" default="Search Visit Plan"/></h1>

        <h3><g:message code="visitPlan.Info.label" default="Visit Plan"/></h3>
        <div class="element_row_content_container lightColorbg pad_bot0">
            <table>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <g:hiddenField name="employeeId" id="employeeId" value=""/>
                        <label for="employeeSearchKey" class="txtright bold hight1x width1x"
                               style="width: 160px;">Name<g:message
                                default="Name/PIN"/>

                        </label>
                    </td>
                    <td>
                        <g:textField name="employeeSearchKey" id="employeeSearchKey"
                                     value="" style="width: 160px;"/>
                    </td>

                </tr>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label for="dateFrom" class="txtright bold hight1x width1x"
                               style="width: 160px;">Date From<g:message
                                default="dateFrom"/>

                        </label>
                    </td>
                    <td>
                        <g:textField name="dateFrom" id="dateFrom"
                                     value="" style="width: 160px;"/>
                    </td>
                    <td>
                        <label for="dateTo" class="txtright bold hight1x width1x"
                               style="width: 160px;">Date To<g:message
                                default="dateTo"/>

                        </label>
                    </td>
                    <td>
                        <g:textField name="dateTo" id="dateTo"
                                     value="" style="width: 160px;"/>
                    </td>
                </tr>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <div class="buttons">
                            <span class="button" id="merge-span"><input type="button" name="merge-button" id="merge-order-button"
                                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                                        value="Search"
                                                                        onclick="searchGridItems();"/></span>

                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="clear height5"></div>


        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>



        <div id="dialog-confirm-warehouse" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
