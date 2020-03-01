<%--
  Created by IntelliJ IDEA.
  User: liton.miah
  Date: 3/15/2017
  Time: 12:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="docuThemeRollerLayout">
    <title>Rollback Deposit To Ho</title>
</head>

<body>
<div class="main_container">
    <div class="content_container">
        <h1>Rollback Deposit To Ho</h1>
        <h3>Rollback Deposit To Ho Information</h3>
        <form name='gRollbackDepositToHo' id='gRollbackDepositToHo'>
            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hightx width1x">
                                Distribution Point
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <g:select name="dp" id="dp" from="${dpList}"
                                      optionKey="id"
                                      optionValue="name"
                                      class="validate[required] width200"
                                      onchange="getDepositPool(this.value);"
                                      noSelection="['':'Select distribution point...']"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="txtright bold hightx width1x">
                                Transaction No
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <g:select name="transactionNo" id="transactionNo" from=""
                                      optionKey="id"
                                      optionValue="name"
                                      class="validate[required] width200"
                                      onchange="setAmount();"
                                      noSelection="['':'Select transaction no...']"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="txtright bold hightx width1x">
                                Total Amount
                            </label>
                        </td>
                        <td>
                            <g:textField name="totalAmount" value="" readonly="true"/>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
        <div class="clear"></div>
        <div class="buttons">
            <span class="button">
                <input type="button" name="rollback-button" id="rollback-button"
                       class="ui-button ui-widget ui-state-default ui-corner-all"
                       value="Rollback"
                       onclick="executeRollback();"/>
            </span>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $("#gRollbackDepositToHo").validationEngine({
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gRollbackDepositToHo").validationEngine('attach');

        <g:if test="${dpList.size() == 1}" >
            $('#dp').val(${dpList.first().id});
            $('#dp').attr("disabled",true);
            getDepositPool($('#dp').val());
        </g:if>
    });

    function setAmount(){
        var amount = $('#transactionNo option:selected').attr('amount');;
        $('#totalAmount').val(amount);
    }

    function getDepositPool(id){
        SubmissionLoader.showTo();
        $.ajax({
            url: "${request.contextPath}/${params.controller}/getDepositPool",
            data: {dpId:id},
            type: 'post',
            datatype: 'json',
            success: function(res){
                $('#totalAmount').val('');
                var options = '<option value="">Select transaction no...</option>'
                if(res.length == 1){
                    options = '<option value="'+res[0].transactionNo+'" amount="'+res[0].total+'">'+res[0].transactionNo+'</option>';
                    $('#totalAmount').val(res[0].total);
                }else{
                    $.each(res, function(key, value){
                        options += '<option value="'+value.transactionNo+'"amount="'+value.total+'">'+value.transactionNo+'</option>';
                    });
                }
                $('#transactionNo').html(options);
            },
            complete: function(){
                SubmissionLoader.hideFrom();
            }
        });
    }

    function executeRollback(){
        if(!$("#gRollbackDepositToHo").validationEngine('validate')){
            MessageRenderer.renderErrorText("Please fill out the required field.");
            return false;
        }
        var transactionNo = $('#transactionNo').val();
        SubmissionLoader.showTo();
        $.ajax({
            url: "${request.contextPath}/${params.controller}/executeRollback",
            data: {transactionNo:transactionNo},
            type: 'post',
            datatype: 'json',
            success: function(res){
                MessageRenderer.render(res);
                if(res.type == 1){
                    getDepositPool($('#dp').val());
//                    reset_form('#gRollbackDepositToHo')
                }
            },
            complete: function(){
                SubmissionLoader.hideFrom();
            }
        });
    }
</script>

</body>
</html>