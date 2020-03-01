<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<style>
.customLabel {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin: 0;
    padding: 5px 10px 0;
    width: 160px;
}

.customLabel4 {
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin: 0;
    padding: 5px 10px 0;
    width: 160px;
}

.customLabel1 {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin-left: 4px;
    padding: 5px 10px 0;
    width: 160px;
}

.cutomInput {
    height: 18px;
    width: 160px;
}
</style>
<g:form name='gFormHoReceiveReport' id='gFormHoReceiveReport'>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <g:hiddenField name="customerId" value=""/>
    <div style="float:left;">
        <table style="width: auto">
            <g:if test="${size == 1}">
                <script type="text/javascript">
                    $(document).ready(function () {
                     /*   setId(${enterpriseList}[0].id);
                    });*/
                </script>
            </g:if>
            <g:else>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label for="userOrderPlaced" class="txtright bold hight1x width1x"
                               style="width: 160px;"><g:message
                                code="primaryDemandOrder.userOrderPlaced.label"
                                default="Enterprise"/>
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="ent"
                                  class="validate[required]"
                                  style="width: 350px; height: 20px;" id="ent"
                                  optionKey="id" value="" noSelection="['': 'Please Select']"
                                  onchange="setId(this.value);"/>
                    </td>
                </tr>
                <script type="text/javascript">
                    $(document).ready(function () {
                        $('#ent').val('');
                        var options = '<option value="">Please Select</option>';
                        for (var i = 0; i < ${size}; i++) {
                            options += '<option value="' + ${enterpriseList}[i].id + '">' + ${enterpriseList}[i].name + '</option>';
                        }
                        $("#ent").html(options);
                    });
                </script>
            </g:else>
            <tr>
                <td>
                    <table style="width: 100%;">
                        <tr class="element_row_content_container lightColorbg pad_bot0">
                            <td>
                                <label class="txtright bold hight1x width1x" style="width: 100px;">
                                    From Date
                                    <span class="mendatory_field">*</span>
                                </label>
                            </td>
                            <td><g:textField name="fromDate" id="fromDate" value="" class="validate[required]"/></td>

                            <td>
                                <label class="txtright bold hight1x width1x" style="width: 100px;">
                                    To Date
                                    <span class="mendatory_field">*</span>
                                </label>
                            </td>
                            <td><g:textField name="toDate" id="toDate" value="" class="validate[required]"/></td>
                        </tr>
                    </table>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <g:if test="${customerCategoryList}">
                    <td>
                        <label for="customerCategory" class="txtright bold hight1x width100"
                               style="width: 160px;"> Customer Category:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="customerCategory"
                                  class="validate[required]"
                                  style="width: 200px; height: 20px;" id="customerCategoryId"
                                  optionKey="id" value="${customerCategoryList}"/>
                    </td>
                </g:if>
                <g:if test="${salesChannelList}">
                    <td>
                        <label for="salesChannel" class="txtright bold hight1x width100"
                               style="width: 160px;">Sales Channel:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="salesChannel"
                                  class="validate[required]"
                                  style="width: 200px; height: 20px;" id="salesChannel"
                                  optionKey="id" value="${salesChannelList}"/>
                    </td>
                </g:if>
            </tr>

            <script type="text/javascript">
                $(document).ready(function () {
                    $('#customerCategoryId').val('');
                    var options = '';
                    for (var i = 0; i < ${customerSize}; i++) {
                        options += '<option value="' + ${customerCategoryList}[i].id + '">' + ${customerCategoryList}[i].name + '</option>';
                    }
                    $("#customerCategoryId").html(options);

                    $('#salesChannel').val('');
                    var options = '<option value="">Please Select</option>';
                    for (var i = 0; i < ${salesChannelSize}; i++) {
                        options += '<option value="' + ${salesChannelList}[i].id + '">' + ${salesChannelList}[i].name + '</option>';
                    }
                    $("#salesChannel").html(options);
                });
            </script>


            <tr>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="search" id="search-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Generate Report"
                                                    onclick="generateReport();"/></span>
                    </div>
                </td>
            </tr>
        </table>

    </div>

    <div style="clear:both;"></div>
    <br/>
%{--</fieldset>--}%

    <div class="buttons">

    </div>
</g:form>
