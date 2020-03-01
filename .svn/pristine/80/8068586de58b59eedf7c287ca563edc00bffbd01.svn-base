
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Chart Of Account Upload</title>

<div class="main_container">
    <div class="content_container">
        <h1>Chart Of Account Upload</h1>
        <h3>Chart Of Account Data Upload Info</h3>

        <div style="width:100%;">
           <form name="chartOfAccountUploadForm" id="chartOfAccountUploadForm" action="${request.contextPath}/${params.controller}/upload" method="post" enctype="multipart/form-data">
               <div class="element_row_content_container lightColorbg pad_bot0">
                   <label class="txtright bold hight1x width1x">
                       Select Excel Data File
                       <span class="mendatory_field"> * </span>
                   </label>

                   <div class='element-input inputContainer'>
                       <input type="file" class="validate[required] block-input" name="uploadFile" id="uploadFile" value="" />
                   </div>
                   <div class='element-input inputContainer'>
                       %{--<progress id="pBar" value="0" min="0" max="100"></progress>--}%
                   </div>
               </div>

               <div class="buttons">
                   <span class="button">
                       %{--<input type="button" name="upload-button" id="upload-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Upload" onclick="executeAjaxUpload();"/>--}%
                       <input type="submit" name="upload-button" id="upload-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Upload"/>
                   </span>
               </div>
           </form>

            <g:render template="script" />
            %{--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>--}%
        </div>



        <div id="dialog-confirm-chargeType" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>



