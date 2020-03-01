<script type="text/javascript">
    $(document).ready(function(){
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
    })
</script>
<g:hiddenField name="id" value="${salesHead?.id}"/>
<g:hiddenField name="version" value="${salesHead?.version}"/>
<g:hiddenField name="targetYear" value="${salesHead?.targetYear}"/>
<g:hiddenField name="employee.id" id="employeeId" value="${salesHead?.employee?.id}"/>
<div class="element_container_big">
    <div class="block-title">
        <div class="element-title width190">Annual Sales Target</div>
        <div class="clear"></div>
    </div>
    <div class="block-input">
        <div class="element-input inputContainer value width190">
            <g:textField class="width100 validate[required] alin_right" name="targetAmount" id="targetAmount" value="${salesHead?.targetAmount}" />
        </div>
        <div class="clear"></div>
    </div>
</div>
<h3>Current Sales Head Information</h3>
<div class="element_container_big">
    <div class="block-title">
        <div class="element-title width190">Employee PIN</div>
        <div class="element-title input_width320">Employee Name</div>
        <div class="element-title input_width200">Designation</div>
        <div class="clear"></div>
    </div>
    <div class="block-input">
        <div class="element-input inputContainer value width190">
            <g:textField class="width100 validate[required]" name="pin" id="pin" value="${salesHead?.employee?.code}" readonly="readonly" />
        </div>
        <div class="element-input inputContainer value input_width320">
            <g:textField name="name" class="width300" readonly="readonly" value="${salesHead?.employee?.name}" />
        </div>
        <div class="element-input inputContainer value input_width200">
            <g:textField name="designation" class="width200" readonly="readonly" value="${salesHead?.employee?.designation?.name}" />
        </div>
        <div class="clear"></div>
    </div>
</div>
<h3>Replace Sales Head Information</h3>
<div class="element_container_big">
    <div class="block-title">
        <div class="element-title input_width320">New Sales Head</div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input-td inputContainer width320'>
            <input type="text" id="employeeSearchKey" name="customerSearchKey" class="width290"/>
            <span id="search-btn-employee-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="element_container_big">
    <div class="block-title">
        <div class="element-title width190">Employee PIN</div>
        <div class="element-title input_width320">Employee Name</div>
        <div class="element-title input_width200">Designation</div>
        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value width190">
            <g:textField class="width100" name="employeePin" id="employeePin" value="" readonly="readonly" />
        </div>
        <div class="element-input inputContainer value input_width320">
            <g:textField name="employeeName" class="width300" readonly="readonly" value="" />
        </div>
        <div class="element-input inputContainer value input_width200">
            <g:textField name="employeeDesignation" class="width200" readonly="readonly" value="" />
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="create-button-salesHead"
        class="ui-button ui-widget ui-state-default ui-corner-all"
        value="Update"
        onclick="executeAjaxSalesHead();"/>
    </span>
</div>