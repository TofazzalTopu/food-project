<meta name="layout" content="docuThemeRollerLayout"/>
<title> Promotion Setup </title>
<style>
.formError {position: relative;}
</style>
<div class="main_container">
    <div class="content_container">
        <h1> Promotion Setup</h1>

        <h3> Promotion Setup Info</h3>

        <div style="width:100%;">
            <g:render template='create' model="[asOfDate : asOfDate]"/>
            <br/>
            <g:render template="script"/>
        </div>

        <div id="dialog-confirm-promotion" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>


