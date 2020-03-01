package com.bits.bdfp.accounts

import com.bits.bdfp.accounts.uploads.ChartOfAccountUploadAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import jxl.DateCell
import jxl.LabelCell
import jxl.NumberCell
import jxl.Sheet
import jxl.Workbook
import org.springframework.beans.factory.annotation.Autowired

import java.text.DateFormat
import java.text.SimpleDateFormat

class ChartOfAccountUploadController {
    @Autowired
    ChartOfAccountUploadAction chartOfAccountUploadAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    private final static int CUSTOMER_PARTY_PREFIX = 0
    private final static int PREFIX_CODE = 1
    private final static int ACCOUNT_HEAD = 2
    private final static int ACCOUNT_CODE = 3
    private final static int DR_BALANCE = 4
    private final static int CR_BALANCE = 5
    private final static int LEDGER_BOOK_POST_FIX = 6
    private final static int POST_FIX_CODE = 7

    def index = {
        render(view: 'index')
    }

    def upload = {
        try{
            ApplicationUser applicationUser = session?.applicationUser
            List list = enterpriseAutocompleteListAction.execute(applicationUser,null)
            Map result = ["results": list, "total": list.size()]


            if(params.uploadFile) {
                def file = request.getFile('uploadFile')
                Workbook workbook = Workbook.getWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheet(0);

                List<Map> dataList = []

                if (file && sheet.getRows() > 1) {
                    // skip first row (row 0) by starting from 1
                    for (int row = 1; row < sheet.getRows(); row++) {
                        Map data = [:]

                        LabelCell customerPartyPrefix = sheet.getCell(CUSTOMER_PARTY_PREFIX, row).getContents()?sheet.getCell(CUSTOMER_PARTY_PREFIX, row):null
                        NumberCell prefixCode = sheet.getCell(PREFIX_CODE, row).getContents()?sheet.getCell(PREFIX_CODE, row):null
                        LabelCell accountHead = sheet.getCell(ACCOUNT_HEAD, row).getContents()?sheet.getCell(ACCOUNT_HEAD, row):null
                        NumberCell accountCode = sheet.getCell(ACCOUNT_CODE, row).getContents()?sheet.getCell(ACCOUNT_CODE, row):null
                        NumberCell drBalance = sheet.getCell(DR_BALANCE, row).getContents()?sheet.getCell(DR_BALANCE, row):null
                        NumberCell crBalance = sheet.getCell(CR_BALANCE, row).getContents()?sheet.getCell(CR_BALANCE, row):null
                        LabelCell ledgerBookPostFix = sheet.getCell(LEDGER_BOOK_POST_FIX, row).getContents()?sheet.getCell(LEDGER_BOOK_POST_FIX, row):null
                        NumberCell postFixCode = sheet.getCell(POST_FIX_CODE, row).getContents()?sheet.getCell(POST_FIX_CODE, row):null

                        data.put("customerPartyPrefix",customerPartyPrefix?customerPartyPrefix.string:'')
                        data.put("prefixCode",prefixCode?prefixCode.value:'')
                        data.put("accountHead",accountHead?accountHead.string:'')
                        data.put("accountCode",accountCode?accountCode.value:'')
                        data.put("drBalance",drBalance?drBalance.value:'')
                        data.put("crBalance",crBalance?crBalance.value:'')
                        data.put("ledgerBookPostFix",ledgerBookPostFix?ledgerBookPostFix.string:'')
                        data.put("postFixCode",postFixCode?postFixCode.value:'')

                        dataList.add(data)
                    }
                    render(view: 'showData', model: [result:result as JSON, list:list, personList: dataList as JSON, msg: '', msgErr:'', rowNum: dataList.size()])
                } else {
                    render(view: 'showData', model: [result:result as JSON, list:list, personList: [], msg: 'No data found!', msgErr:'', rowNum: 0])
                }
            } else {
                render(view: 'showData', model: [result:[], list:[], personList: [], msg: 'No data found!', msgErr:'', rowNum: 0])
            }
        }catch(Exception ex){
            render(view: 'showData', model: [result:[], list:[], personList: [], msg: '', msgErr: "Error Details: "+ex.message, rowNum: 0])
        }
    }

    def ajaxUpload = {
        ApplicationUser applicationUser = session?.applicationUser
        if(params.confirm == 'true'){
            Message message = chartOfAccountUploadAction.execute(applicationUser,params)
            render message as JSON
        }else{
            Map map = chartOfAccountUploadAction.preCondition(params,applicationUser)
            render map as JSON
        }
    }
}
