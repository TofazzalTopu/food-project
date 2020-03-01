
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Chart Of Account Upload</title>

<div class="main_container">
    <div class="content_container">
        <h1>Chart Of Account Upload</h1>
        <h3>Chart Of Account Data Upload Info</h3>

        <div style="width:100%;">
            <g:if test="${msg}">
                <h4>${msg}</h4>
                <div class="buttons"><span class="button"><a href="${request.contextPath}/${params.controller}/index"><b>Back</b></a></span></div>
            </g:if>
            <g:elseif test="${msgErr}">
                <h4>No data found!</h4>
                <p>${msgErr}</p>
                <div class="buttons"><span class="button"><a href="${request.contextPath}/${params.controller}/index"><b>Back</b></a></span></div>
            </g:elseif>
            <g:else>
                <g:render template="showDataScript" model="[personList:personList, rowNum:rowNum]"/>

                <form name="chartOfAccountUploadForm" id="chartOfAccountUploadForm">
                    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
                    <div class="element_row_content_container lightColorbg pad_bot0">
                        <label class="txtright bold hight1x width1x" style="width: 165px;">
                            Enterprise
                            <span class="mendatory_field">*</span>
                        </label>

                        <g:if test="${result}">
                            <g:if test="${list.size() == 1}">
                                <div class='element-input inputContainer'>
                                    <g:textField name="enterPriseName" id="enterPriseName" disabled="disabled" value="${list[0].name}"/>
                                </div>
                                <script type="text/javascript">
                                    $(document).ready(function(){
                                        $("#enterpriseConfiguration").val("${list[0].id}");
                                    })
                                </script>
                            </g:if>
                            <g:else>
                                <div id="enterpriselist" class="validate[required]"></div>
                                <script type="text/javascript">
                                    jQuery(document).ready(function () {
                                        $("#enterpriselist").empty();
                                        $("#enterpriselist").flexbox(${result}, {
                                            watermark: "Select Enterprise",
                                            width: 317,
                                            onSelect: function () {
                                                $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                            }
                                        });
                                        $('#enterpriselist_input').blur(function () {
                                            if ($('#enterpriselist_input').val() == '') {
                                                $("#enterpriseConfiguration").val("");
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

                        <label class="txtright bold hight1x width1x" style="margin-left: 20px">
                            Transaction Date
                            <span class="mendatory_field">*</span>
                        </label>
                        <div class='element-input inputContainer'>
                            <g:textField name="transactionDate" id="transactionDate" class="validate[required]" value=""/>
                        </div>

                    </div>

                    <div style="clear: both; margin-bottom: 5px;"></div>

                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-uploadDataInfo"></table>
                    </div>

                    <div class="buttons">
                        <g:if test="${msg}">
                            <span class="button"><a href="${request.contextPath}/${params.controller}/index"><b>Back</b></a></span>
                        </g:if>
                        <g:else>
                            <span class="button">
                                <input type="button" name="upload-button" id="upload-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Execute Upload" onclick="executeAjaxUpload();"/>
                            </span>
                            <span class="button"><a href="${request.contextPath}/${params.controller}/index"><b>Back</b></a></span>
                        </g:else>
                    </div>
                </form>
            </g:else>
        </div>

        <div id="dialog-confirm-chargeType" title="Delete alert" style="display: none">
            <p>
                <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
                <span id="custom_message">These item(s) will be permanently deleted and cannot be recovered. Are you sure?</span>
            </p>
        </div>
    </div>
</div>
<script>
    <g:if test="${msgErr}">
        MessageRenderer.render({
            "messageBody": "Excel data format doesn't match. Please insert excel data file with the desired format.",
            "messageTitle": "Chart Of Account Upload",
            "type": "0"
        });
    </g:if>

    <g:if test="${msg}">
        MessageRenderer.render({
            "messageBody": "No record found from your inserted excel file.",
            "messageTitle": "Chart Of Account Upload",
            "type": "0"
        });
    </g:if>
</script>


