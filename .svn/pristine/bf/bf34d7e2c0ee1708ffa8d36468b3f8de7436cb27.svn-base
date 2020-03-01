<%@ page import="com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolume" %>

<div id="spinnerMonthlySalesTargetByVolume" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormMonthlySalesTargetByVolume' id='gFormMonthlySalesTargetByVolume'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <div id="remote-content-monthlySalesTargetByAmount"></div>

    <div>

        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    Target Year
                </div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer'>
                    <g:if test="${yearlySalesTargetByVolumeList.size()}">
                        <g:select name="targetYear"  from="${yearlySalesTargetByVolumeList}" optionKey="targetYear" optionValue="targetYear" value="" noSelection="['Select Target Year':'']" onchange="changeYear(this.value)"/>
                    </g:if>
                    <g:else>
                        <g:select name="targetYear"  from="${salesHeadByVolumeList}" optionKey="targetYear" optionValue="targetYear" value="" noSelection="['Select Target Year':'']" onchange="changeYear(this.value)"/>
                    </g:else>
                </div>

                <div class="clear"></div>
            </div>
        </div>

    </div>
    <div class="clear"></div>
    <div id="monthWiseTarget"></div>
    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button-monthlySalesTargetByVolume" id="create-button-monthlySalesTargetByVolume"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Submit Target"
                                    onclick="executeAjaxMonthlySalesTargetByVolume();"/></span>

    </div>
</form>
