<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 4/28/11
  Time: 10:59 AM
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript">
    var feature_action_template_index = ${actionsize} %{--${featureInstance?.featureaction?.size()}--}%

            function addActionTemplate(table, row, index) {

                var tr = $.templateRenderer(row.text(), {i: index});
                table.append(tr)
                showMenuUrls(index, '');
                reIndexPosition();
                $("#feature-action-table").bind();
            }

    function reIndexPosition() {
        $('.items-position ').each(function (index, element) {
            $(element).val(index + 1);
        })
    }

</script>

<script type="text/html" id="feature_action_template">
    <g:render plugin="docuaccesscontrol" template="tabs/featureAction" model="['i':'#{i}']"/>
</script>

<div>
    <fieldset>
        <div>

            <div class="block-title">
                <div class="element-title" style="width:250px;">Feature Name</div>
                <div class="element-title">Description</div>
                <div class="clear"></div>
            </div>
            <div class="block-input">
                <g:hiddenField name="featureInfo.id" id="featureInfoId" value='${featureInstance?.id}'/>
                <div class="element-input inputContainer value ${hasErrors(bean: featureInstance, field: 'featureName', 'errors')}" style="width:250px;">
                    <g:textField name="featureInfo.featureName" id="featureName" value='${featureInstance?.featureName}' style="width:250px;" class="validate[required,maxSize[45]]"/>
                </div>
                <div class="element-input inputContainer value ${hasErrors(bean: featureInstance, field: 'description', 'errors')}"><g:textField name="featureInfo.description" id="description" value='${featureInstance?.description}' style="width:250px;" class="validate[required,maxSize[45]]"/></div>
                <div class="clear"></div>
            </div>
        </div>
        <br/>
    </fieldset>
    <fieldset>

        <div><g:render plugin="docuaccesscontrol" template="tabs/featureActions" model="[ ]"/></div>

        <div id="feature-action-table">
            <g:if test="${featureInstance !=null}">
                <g:each var="featureActionInstance" in="${featureInstance?.nFeatureActions?.sort{it.position}}" status="i">
                    <g:render plugin="docuaccesscontrol" template="tabs/featureAction" model="['featureActionInstance':featureActionInstance,'i':i]"/>
                    <script type="text/javascript">
                        showMenuUrls(${i},'${featureActionInstance?.menuUrl}');
                    </script>
                </g:each>
            </g:if>
            <g:else>
            %{--<g:render template="tabs/featureAction" model="['i':i]"/>--}%
            </g:else>
        </div>
        <br/>
        <div>
            <span class="button"><input type="button"
                                        name="submit" class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Add Action"
                                        onclick="addActionTemplate($('#feature-action-table'), $('#feature_action_template'), feature_action_template_index);
                                        feature_action_template_index++;"/></span>
        </div>
    </fieldset>
</div>