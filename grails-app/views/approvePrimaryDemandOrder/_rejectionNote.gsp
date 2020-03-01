<div id="application_top_panel">
    <g:form name='primaryDemandOrderApprovalRejectionForm' method="post">
        <g:hiddenField name="id" id="id" value="id"/>
        <g:hiddenField name="version"/>

        <table class='create-form-table'>
            <tr>
                <td colspan="4" class="create-form-table-heading">Rejection Note</td>
            </tr>
            <tr>
                <td width="33%">
                    <table class="create-internal-table">
                        <tr>
                            <td class="label-holder-req">
                                <label class='create-form-label'>Note:</label>
                            </td>
                            <td class='create-form-field'>
                                <textarea tabindex="1" id="confirmRejectionNote" name="confirmRejectionNote"
                                          maxlength="100" class="required k-textbox"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

    %{--<div class="buttons">--}%
        <div class="buttons">
            <input type="button" id="createButton" class="save k-button" tabindex="2" value="Reject" onclick="rejecting()"/>
        </div>
    </g:form>

</div>