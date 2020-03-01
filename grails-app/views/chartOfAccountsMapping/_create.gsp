<%@ page import="com.bits.bdfp.accounts.COAType; com.bits.bdfp.accounts.ChartOfAccountsMapping" %>
<link rel="stylesheet" href="${resource(dir: 'js/jquery/multiselect/', file: 'ui.multiselect.css')}"/>
<g:javascript src="jquery/ui/jquery.ui.draggable.min.js"/>
<g:javascript src="jquery/multiselect/jquery.scrollTo-min.js"/>
<g:javascript src="jquery/multiselect/ui.multiselect.js"/>
<div id="spinnerChartOfAccountsMapping" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormChartOfAccountsMapping' id='gFormChartOfAccountsMapping'>
    <g:hiddenField name="enterpriseId" id="enterpriseId" value=""/>
    <div id="remote-content-chartOfAccountsMapping"></div>
    <div>
        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    Enterprise
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    Chart of Accounts Type
                    <span class="mendatory_field">*</span>
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer'>
                    <g:if test="${list}">
                        <g:if test="${list.size() == 1}">
                            <g:textField name="enterPriseName" disabled="disabled" value="${list[0].name}"/>
                            <script type="text/javascript">
                                $(document).ready(function () {
                                    $("#enterpriseId").val("${list[0].id}");
                                })
                            </script>
                        </g:if>
                        <g:else>
                            <div id="enterpriseWarList" style="width: 350px;"></div>
                            <script type="text/javascript">
                                jQuery(document).ready(function () {
                                    var data = ${result}
                                    $("#enterpriseWarList").empty();
                                    $("#enterpriseWarList").flexbox(data, {
                                        watermark: "Select Enterprise",
                                        width: 260,
                                        onSelect: function () {
                                            loadMappingData();
                                            $("#enterpriseId").val($('#enterpriseWarList_hidden').val());
                                        }
                                    });
                                    $('#enterpriseWarList_input').blur(function () {
                                        if ($('#enterpriseWarList_input').val() == '') {
                                            $("#enterpriseWarList").val("");
                                            $("#enterpriseId").val("");
                                        }
                                    });
                                });
                            </script>
                        </g:else>
                    </g:if>
                    <g:else>
                        <g:textField name="enterPriseName" readonly="readonly" value=""/>
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                            });
                        </script>
                    </g:else>
                </div>
                <div class='element-input inputContainer '><g:select name="coaType" id="coaType" class="validate[required]"
                                                                     from="${COAType?.values()}" optionKey="code" onchange="loadMappingData()"
                                                                     value="" noSelection="['':'Select Type']"/></div>

                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="clear"></div>
    <div id="chartOfAccountsMappingBlock">
    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-chartOfAccountsMapping"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Submit"
                                    onclick="executeAjaxChartOfAccountsMapping();"/></span>
    </div>
</form>
