%{--<script type="text/javascript">--}%
    %{--var jqGridDPList = null;--}%
    %{--$(document).ready(function(){--}%
        %{--$("#distributionPointList").flexbox('Select Distribution Point', {--}%
            %{--watermark: "Select Distribution Point",--}%
            %{--width: 260,--}%
            %{--onSelect: function () {--}%
                %{--$("#distributionPoint").val($('#distributionPointList_hidden').val());--}%
            %{--}--}%
        %{--});--}%
        %{--jqGridDPList = $("#jqgrid-grid-distributionPoint").jqGrid({--}%
            %{--url:'',--}%
            %{--datatype: "local",--}%
            %{--colNames:[--}%
                %{--'ID',--}%
                %{--'applicationUserDistributionPointId',--}%
                %{--'Enterprise',--}%
                %{--'Distribution Point',--}%
                %{--'Address',--}%
                %{--''--}%
            %{--],--}%
            %{--colModel:[--}%
                %{--{name:'id', index:'id', width:0, hidden:true},--}%
                %{--{name:'applicationUserDistributionPointId', index:'applicationUserDistributionPointId', hidden: true},--}%
                %{--{name:'enterprise', index:'enterprise', width:150, align:'left'},--}%
                %{--{name:'distributionPoint', index:'distributionPoint', width:200, align:'left'},--}%
                %{--{name:'address', index:'address', width:350, align:'left'},--}%
                %{--{name:'delete', index:'delete', width:20, align:'center', sortable:false}--}%
            %{--],--}%
            %{--rowNum: -1,--}%
            %{--viewrecords:true,--}%
            %{--caption:"Distribution Point List",--}%
            %{--autowidth:true,--}%
            %{--height:true,--}%
            %{--scrollOffset:0,--}%
            %{--gridview:true,--}%
%{--//            multiselect: true,--}%
            %{--rownumbers: true,--}%
            %{--loadError: function (jqXHR, textStatus, errorThrown) {--}%
                %{--MessageRenderer.renderErrorText(jqXHR.responseText,'');--}%
            %{--}--}%
        %{--});--}%
        %{--console.log("Initialized");--}%
    %{--});--}%
    %{--function loadDistributionPointByEnterprise(enterpriseId) {--}%
        %{--$("#distributionPointList").empty();--}%
        %{--$("#distributionPoint").val("");--}%
        %{--if(enterpriseId){--}%
            %{--jQuery.ajax({--}%
                %{--type: "POST",--}%
                %{--url: '${resource(dir:'distributionPoint', file:'loadDistributionPoint')}?enterpriseId=' + enterpriseId,--}%
                %{--dataType: 'json',--}%
                %{--success: function (data, textStatus) {--}%
                    %{--$("#distributionPointList").flexbox(data, {--}%
                        %{--watermark: "Select Distribution Point",--}%
                        %{--width: 260,--}%
                        %{--onSelect: function () {--}%
                            %{--$("#distributionPoint").val($('#distributionPointList_hidden').val());--}%
                        %{--}--}%
                    %{--});--}%
                    %{--$('#distributionPointList_input').blur(function () {--}%
                        %{--if ($('#distributionPointList_input').val() == '') {--}%
                            %{--$("#distributionPoint").val("");--}%
                        %{--}--}%
                    %{--});--}%

                %{--},--}%
                %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
                %{--},--}%
                %{--complete: function () {--}%
                %{--}--}%
            %{--});--}%
        %{--}--}%
    %{--}--}%
    %{--function addDistributionPointToGrid(){--}%
        %{--var enterpriseId = $("#enterpriseConfiguration").val();--}%
        %{--if(!enterpriseId){--}%
            %{--MessageRenderer.renderErrorText("Enterprise is not selected");--}%
            %{--return--}%
        %{--}--}%
        %{--var distributionPointId = $("#distributionPoint").val();--}%
        %{--if(!distributionPointId){--}%
            %{--MessageRenderer.renderErrorText("Distribution Point is not selected");--}%
            %{--return--}%
        %{--}--}%

        %{--var enterprise =  $("#enterpriseList_input").val();--}%
        %{--var distributionPoint =  $("#distributionPointList_input").val();--}%

        %{--var rowTo = jqGridDPList.getRowData(distributionPointId.toString());--}%
        %{--if (!rowTo.id) {--}%
            %{--var newRowData = [--}%
                %{--{--}%
                    %{--'id':distributionPointId.toString(), 'applicationUserDistributionPointId': '', 'enterprise':enterprise, 'distributionPoint':distributionPoint,--}%
                    %{--'address':'', 'delete': '<a  href="javascript:deAllocateDP(' + distributionPointId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'--}%
                %{--}--}%
            %{--];--}%
            %{--jqGridDPList.addRowData(distributionPointId.toString(), newRowData[0]);--}%
            %{--$("#distributionPointList_input").val("");--}%
            %{--$("#distributionPoint").val("");--}%
        %{--}else{--}%
            %{--MessageRenderer.renderErrorText("Selected DP is already added to grid");--}%
        %{--}--}%

    %{--}--}%
    %{--function loadUserDistributionPointList(userId){--}%
        %{--alert(userId);--}%
        %{--$("#jqgrid-grid-distributionPoint").jqGrid().setGridParam({url:--}%
                %{--"${resource(dir:'userAccount', file:'listUserAccountDistributionPoint')}?id=" + userId,--}%
            %{--datatype: "json", mtype: "POST"}).trigger("reloadGrid");--}%
        %{--jQuery("#jqgrid-grid-distributionPoint").jqGrid().setGridParam({url:"${resource(dir:'userAccount', file:'listUserAccountDistributionPoint')}?"--}%
                %{--+ "id=" + userId,--}%
            %{--mtype: "POST",--}%
            %{--datatype:"json"--}%
        %{--}).trigger("reloadGrid");--}%
        %{--console.log("Reload");--}%
    %{--}--}%
    %{--function deAllocateDP(distributionPointId){--}%
        %{--$('#jqgrid-grid-distributionPoint').jqGrid('delRowData', distributionPointId);--}%
    %{--}--}%
%{--</script>--}%
%{--<div class="element_row_content_container lightColorbg pad_bot0">--}%
    %{--<label class="txtright bold hight1x width1x">--}%
        %{--Enterprise--}%
        %{--<span class="mendatory_field"> * </span>--}%
    %{--</label>--}%
    %{--<g:if test="${enterpriseList}">--}%
        %{--<g:if test="${enterpriseList.size() == 1}">--}%
            %{--<div class='element-input inputContainer'>--}%

                %{--<g:textField name="enterPriseName" readonly="readonly" value="${enterpriseList[0].name}"/>--}%
                %{--<g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration"--}%
                               %{--value="${enterpriseList[0].id}"/>--}%
            %{--</div>--}%
        %{--</g:if>--}%
        %{--<g:else>--}%
            %{--<div class='element-input inputContainer'>--}%
                %{--<div id="enterpriseList" style="width: 350px;"></div>--}%
                %{--<script type="text/javascript">--}%

                    %{--jQuery(document).ready(function () {--}%
                        %{--var data = ${enterpriseFlexData};--}%
                        %{--$("#enterpriseList").empty();--}%
                        %{--$("#enterpriseList").flexbox(data, {--}%
                            %{--watermark: "Select Enterprise",--}%
                            %{--width: 260,--}%
                            %{--onSelect: function () {--}%
                                %{--$("#enterpriseConfiguration").val($('#enterpriseList_hidden').val());--}%
                                %{--loadDistributionPointByEnterprise($('#enterpriseList_hidden').val());--}%
                            %{--}--}%
                        %{--});--}%
                        %{--$('#enterpriseList_input').blur(function () {--}%
                            %{--if ($('#enterpriseList_hidden').val() == '') {--}%
                                %{--$("#enterpriseList").val("");--}%
                                %{--$("#enterpriseConfiguration").val("");--}%
                            %{--}--}%
                        %{--});--}%

                    %{--});--}%
                %{--</script>--}%

                %{--<g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>--}%

            %{--</div>--}%
        %{--</g:else>--}%

    %{--</g:if>--}%
    %{--<g:else>--}%
        %{--<div class='element-input inputContainer'>--}%
            %{--<g:textField name="enterPriseName" readonly="readonly" value=""/>--}%
            %{--<script type="text/javascript">--}%
                %{--jQuery(document).ready(function () {--}%
                    %{--MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)--}%
                %{--});--}%
            %{--</script>--}%
        %{--</div>--}%
    %{--</g:else>--}%
%{--</div>--}%

%{--<div class="element_row_content_container lightColorbg pad_bot0">--}%
    %{--<label class="txtright bold hight1x width1x">--}%
        %{--Distribution Point--}%
    %{--</label>--}%

    %{--<div class='element-input inputContainer'>--}%

        %{--<div id="distributionPointList" class="width350"></div>--}%

    %{--</div>--}%
    %{--<g:hiddenField name="distributionPoint.id" id="distributionPoint" value=""/>--}%
    %{--<div class="buttons" style="padding-left: 450px;">--}%
        %{--<span class="button"><input type="button" name="add-button" id="add-button"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
                                    %{--value="Add"--}%
                                    %{--onclick="addDistributionPointToGrid();"/></span>--}%
    %{--</div>--}%
%{--</div>--}%
%{--<div class="element_row_content_container lightColorbg pad_bot0">--}%
    %{--<div class="jqgrid-container">--}%
        %{--<table id="jqgrid-grid-distributionPoint"></table>--}%
    %{--</div>--}%
%{--</div>--}%

