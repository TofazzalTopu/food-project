<div id="tab_history_list">
    %{--<div class="element_row_content_container lightColorbg pad_bot0">--}%
        <g:set var="pendingCount" value="${0}"/>
        <g:if test="${pendingUserList.size() > 0}">
            <h3>Pending Approval</h3>
            <table class="simple-table-css">
                <tr>
                    <td style="font-size:12px;width:5px; color:#BB0077">SL</td>
                    <td style="font-size:12px;width:165px; color:#BB0077">Actor</td>
                    <td style="font-size:12px;width:120px; color:#BB0077">Designation</td>
                    <td style="font-size:12px;width:110px; color:#BB0077">Sequence</td>
                </tr>
                <g:each in="${pendingUserList}" status="i" var="pendingUser">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td style="color:#BB0077"><g:set var="pendingCount" value="${pendingCount + 1}"/>${pendingCount}</td>
                        <td style="width:165px; color:#BB0077">${pendingUser.pendingUser}</td>
                        <td style="width:150px; color:#BB0077">${pendingUser.designation}</td>
                        <td style="width:110px; color:#BB0077">${pendingUser?.priority_sequence}</td>
                    </tr>
                </g:each>
            </table>
            <br/>
        </g:if>
    %{--</div>--}%

    <h3>Primary Demand Order History</h3>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <g:if test="${initiatorList.size() > 0}">
            <table>
                <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width70">
                            Initiated By:
                        </label>
                    </td>
                    <td>
                        <g:textField class="width200" name="initiator" value="${initiatorList[0].initiator}" readonly="readonly"/>
                    </td>
                    <td>
                        <label class="txtright bold hight1x width100">
                            Initiation Time:
                        </label>
                    </td>
                    <td>
                        <g:textField class="width150" name="initiationDate" value="${initiatorList[0].initiationDate}" readonly="readonly"/>
                    </td>
                    <td>
                        <label class="txtright bold hight1x width70">
                            Designation:
                        </label>
                    </td>
                    <td>
                        <g:textField class="width180" name="designation" value="${initiatorList[0].designation}" readonly="readonly"/>
                    </td>
                </tr>
            </table>
        </g:if>
    </div>
    <br/>
    <g:set var="historyCount" value="${0}"/>
    <g:if test="${approvalUserList}">
        <table class="simple-table-css">
            <tr>
                <td style="font-size:12px;width:5px;">SL</td>
                <td style="font-size:12px;width:165px;">Actor</td>
                <td style="font-size:12px;width:120px;">Designation</td>
                <td style="font-size:12px;width:110px;">Action Time</td>
                <td style="font-size:12px;width:80px;">Operation</td>
                <td style="font-size:12px;width:200px;">Remarks</td>
            </tr>
            <g:each in="${approvalUserList}" status="i" var="approvalUser">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td><g:set var="historyCount" value="${historyCount + 1}"/>${historyCount}</td>
                    <td style="width:165px;">${approvalUser.approvedBy}</td>
                    <td style="width:150px;">${approvalUser.designation}</td>
                    <td style="width:110px;">${approvalUser?.approvalDate}</td>
                    <td style="width:80px;">${approvalUser?.orderStatus}</td>
                    <td style="width:200px;">${approvalUser?.remarks}</td>
                </tr>
            </g:each>
        </table>
    </g:if>
</div>