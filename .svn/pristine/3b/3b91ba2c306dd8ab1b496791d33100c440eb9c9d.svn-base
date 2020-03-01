    <table>
        <tr>
            <td>
                <label class="txtright bold hight1x width1x " style="width: 160px;">
                    <g:message code="customerMaster.enterpriseConfiguration.label"
                               default="Enterprise Configuration "/>
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td class="element-input inputContainer input_width280">
                <g:if test="${list.size() == 1}">
                    <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly" class="width180"
                                 value="${list[0].name}"/>
                    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration"
                                   value=""/>
                    <script type="text/javascript">
                        $(document).ready(function () {
                            if(!'${customerMaster}') {
                                setEnterpriseId(${list[0].id});
                            }
                            %{--$('#idEnterprise').val(${enterpriseList[0].id});--}%
                        });
                    </script>
                </g:if>
                <g:else>
                    <g:select class="validate[required]"
                              name="enterpriseConfiguration.id"
                              id="enterpriseConfiguration"
                              noSelection="['': 'Please Select']"
                              from="${list}" optionKey="id"
                              optionValue="name"
                              onchange="listTerritory(this.value);"
                              style="width: 350px"
                              value=""/>
                </g:else>
            </td>
        </tr>
        <tr>
            <td>
                <label class="txtright bold hight1x width1x" style="width: 160px;">
                    <g:message code="customerMaster.territoryConfiguration.label"
                               default="Territory Configuration"/>
                    %{--<span class="mendatory_field">*</span>--}%
                </label>
            </td>
            <td class="element-input inputContainer input input_width280">
                <g:select name="territoryConfiguration.id" class="width200"
                          id="territoryConfiguration"
                          noSelection="['': 'Please Select']"
                          optionKey="id"
                          style="width: 350px"
                          value="" onchange="populateGrid(this.value);"/>
            </td>
        </tr>
    </table>
    <br/>

    <table>
        <tr>
            <td style="float: left">
                <div class="jqgrid-container">
                    <table id="territory-detail-grid"></table>

                    <div id="territory-detail-grid-pager"></div>
                </div>
            </td>
            <td style="float: right">
                <div class="jqgrid-container">
                    <table id="added-territory-detail-grid"></table>

                    <div id="added-territory-detail-grid-pager"></div>
                </div>
            </td>
        </tr>
    </table>