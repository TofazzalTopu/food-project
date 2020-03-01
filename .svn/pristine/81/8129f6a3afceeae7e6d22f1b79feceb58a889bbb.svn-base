package com.bits.bdfp.accounts

import com.bits.bdfp.accounts.uploads.OpeningStockUploadAction
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

class OpeningStockUploadController {
    @Autowired
    OpeningStockUploadAction openingStockUploadAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    private final static int PRODUCT_NAME = 0
    private final static int PRODUCT_ID = 1
    private final static int QUANTITY = 2
    private final static int PER_UNIT_COST_VALUE = 3
    private final static int BATCH_NUMBER = 4
    private final static int BATCH_CONTROLLED = 5
    private final static int BATCH_DATE = 6
    private final static int BATCH_TIME = 7
    private final static int DESTINATION_INVENTORY = 8
    private final static int INVENTORY_ID = 9
    private final static int SUB_INVENTORY = 10
    private final static int SUB_INVENTORY_ID = 11

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

                        LabelCell productName = sheet.getCell(PRODUCT_NAME, row).getContents()?sheet.getCell(PRODUCT_NAME, row):null
                        NumberCell productId = sheet.getCell(PRODUCT_ID, row).getContents()?sheet.getCell(PRODUCT_ID, row):null
                        NumberCell quantity = sheet.getCell(QUANTITY, row).getContents()?sheet.getCell(QUANTITY, row):null
                        NumberCell perUnitCostValue = sheet.getCell(PER_UNIT_COST_VALUE, row).getContents()?sheet.getCell(PER_UNIT_COST_VALUE, row):null
                        LabelCell batchNumber = sheet.getCell(BATCH_NUMBER, row).getContents()?sheet.getCell(BATCH_NUMBER, row):null
                        LabelCell batchControlled = sheet.getCell(BATCH_CONTROLLED, row).getContents()?sheet.getCell(BATCH_CONTROLLED, row):null
                        DateCell batchDate = sheet.getCell(BATCH_DATE, row).getContents()?sheet.getCell(BATCH_DATE, row):null
                        LabelCell batchTime = sheet.getCell(BATCH_TIME, row).getContents()?sheet.getCell(BATCH_TIME, row):null
                        LabelCell destinationInventory = sheet.getCell(DESTINATION_INVENTORY, row).getContents()?sheet.getCell(DESTINATION_INVENTORY, row):null
                        NumberCell inventoryId = sheet.getCell(INVENTORY_ID, row).getContents()?sheet.getCell(INVENTORY_ID, row):null
                        LabelCell subInventory = sheet.getCell(SUB_INVENTORY, row).getContents()?sheet.getCell(SUB_INVENTORY, row):null
                        NumberCell subInventoryId = sheet.getCell(SUB_INVENTORY_ID, row).getContents()?sheet.getCell(SUB_INVENTORY_ID, row):null

                        data.put("productName",productName?productName.string:'')
                        data.put("productId",productId?productId.value:'')
                        data.put("quantity",quantity?quantity.value:'')
                        data.put("perUnitCostValue",perUnitCostValue?perUnitCostValue.value:'')
                        data.put("batchNumber",batchNumber?batchNumber.string:'')
                        data.put("batchControlled",batchControlled?batchControlled.string:'')
                        data.put("batchDate",batchDate?batchDate.date:'')
                        data.put("batchTime",batchTime?batchTime.string:'')
                        data.put("destinationInventory",destinationInventory?destinationInventory.string:'')
                        data.put("inventoryId",inventoryId?inventoryId.value:'')
                        data.put("subInventory",subInventory?subInventory.string:'')
                        data.put("subInventoryId",subInventoryId?subInventoryId.value:'')

                        dataList.add(data)
                    }
                    render(view: 'showData', model: [result:result as JSON, list:list, dataList: dataList as JSON, msg: '', msgErr:'', rowNum: dataList.size()])
                } else {
                    render(view: 'showData', model: [result:result as JSON, list:list, dataList: [], msg: 'No data found!', msgErr:'', rowNum: 0])
                }
            } else {
                render(view: 'showData', model: [result:[], list:[], dataList: [], msg: 'No data found!', msgErr:'', rowNum: 0])
            }
        }catch(Exception ex){
            render(view: 'showData', model: [result:[], list:[], dataList: [], msg: '', msgErr: "Error Details: "+ex.message, rowNum: 0])
        }
    }

    def ajaxUpload = {
        ApplicationUser applicationUser = session?.applicationUser
        if(params.confirm == 'true'){
            Message message = openingStockUploadAction.execute(applicationUser,params)
            render message as JSON
        }else{
            Map map = openingStockUploadAction.preCondition(params,applicationUser)
            render map as JSON
        }
    }
}
