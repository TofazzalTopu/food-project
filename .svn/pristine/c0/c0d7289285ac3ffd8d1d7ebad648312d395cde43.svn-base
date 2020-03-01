<%@ page import="com.bits.bdfp.setup.salestarget.SalesHeadByVolume" %>


<div id="spinnerSalesHeadByVolume" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormSalesHeadByVolume' id='gFormSalesHeadByVolume'>
    <div id="remote-content-salesHeadByVolume"></div>
    <div>
        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    Select Target Year
                    <span class="mendatory_field">*</span>
                </div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer'>
                    <g:select name="year" from="${salesHeadByVolumeList}" value="" optionKey="id" optionValue="targetYear" noSelection="['Select Target Year': '']" onchange="changeYear(this.value)"/>
                </div>
                <div class="clear"></div>
            </div>
        </div>

    </div>

    <div class="clear"></div>
    <div id="salesHeadByVolumeId">
    </div>
</form>
