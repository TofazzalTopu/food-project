/*
 Created by IntelliJ IDEA.
 User: feroz
 Date: 3/29/11
 Time: 12:14 PM
 To change this template use File | Settings | File Templates.
 */

var ChartOfAccountsAjax = {
    ACTION_MASTER_CHART_OF_ACCOUNTS_ALL_HEAD: CONTEXT_PATH + "/accountsAjaxFeed/jsonMasterChartOfAccounts",
    ACTION_CHART_OF_ACCOUNTS_ALL_HEAD: CONTEXT_PATH + "/accountsAjaxFeed/jsonChartOfAccounts",
    ACTION_CHART_OF_ACCOUNTS_NON_CASH_BANK_HEAD: CONTEXT_PATH + "/accountsAjaxFeed/getAllNonCashNonBankAccountsHead",

    setAutoComplete: function (url, params, visibleSelector, hiddenSelector, onSuccessAction, onSelectAction) {
        $('#' + visibleSelector).autocomplete({
            source: function (request, response) {
                $('#' + hiddenSelector).val('');
                params['term'] = request.term;
                $('#' + visibleSelector).addClass('icon-spinner-tiny');
                DocuAutoComplete.execute(response, url, params, function (item) {
                    if (item['accountName']) {
                        item['label'] = item['label'] + ' - ' + item['accountName'];
                    }
                    return item;
                }, function () {
                    $('#' + visibleSelector).removeClass('icon-spinner-tiny');
                    onSuccessAction;
                });
            },
            select: function (event, ui) {
                $('#' + hiddenSelector).val(ui.item.id);
                if (onSelectAction && typeof onSelectAction == "function") {
                    onSelectAction(ui.item);
                }
            }
        });
    },

    createManagementReportWizardAutoComplete: function (url, params, visibleSelector, hiddenSelector, beforeRequest, onSuccessAction, onSelectAction) {
        $('#' + visibleSelector).autocomplete({
            source: function (request, response) {
                var bool = beforeRequest(params);
                if (!bool) {
                    return false;
                }

                $('#' + hiddenSelector).val('');
                params['term'] = request.term;
                $('#' + visibleSelector).addClass('icon-spinner-tiny');
                DocuAutoComplete.execute(response, url, params, function (item) {
                    if (item['accountName']) {
                        item['label'] = item['label'] + ' - ' + item['accountName'];
                    }
                    return item;
                }, function () {
                    $('#' + visibleSelector).removeClass('icon-spinner-tiny');
                    onSuccessAction();
                });
            },
            select: function (event, ui) {
                $('#' + hiddenSelector).val(ui.item.id);
                if (onSelectAction && typeof onSelectAction == "function") {
                    onSelectAction(ui.item);
                }
            }
        });
    }
}