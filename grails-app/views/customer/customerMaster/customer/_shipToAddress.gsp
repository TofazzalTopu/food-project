<table>
    <tr>
        <td>
            <label class="txtright bold hight1x width160">
                Customer Name
            </label>
        </td>
        <td><g:textField id="shipName" name="shipName" value="${customerMaster?.name}" size="43" readonly="readonly"/></td>
    </tr>
    <tr>
        <td>
            <label class="txtright bold hight3x width1x" style="width: 160px;">
                <g:message code="customerMaster.presentAddress.label" default="Address"/>
            </label>
        </td>
        <td>
            <g:textArea name="address" id="address" value="" rows="4"
                        cols="40"/>
        </td>
    </tr>
</table>


<div class="buttons">
    <span class="button"><input type="button" name="add-button" id="add-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Add"
                                onclick="addNewItemToCollectionGrid();"/></span>
</div>
<br/>
<table id="ship-address-grid"></table>
<div id="ship-address-grid-pager"></div>