package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class MonthlySalesTargetByVolumeService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql
    SpringSecurityService  springSecurityService

    @Transactional(readOnly = true)
    public MonthlySalesTargetByVolume read(Long id) {
        return MonthlySalesTargetByVolume.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<MonthlySalesTargetByVolume> monthlySalesTargetByVolumeList = MonthlySalesTargetByVolume.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long monthlySalesTargetByVolumeCount = MonthlySalesTargetByVolume.count()
            return [objList: monthlySalesTargetByVolumeList, count: monthlySalesTargetByVolumeCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<MonthlySalesTargetByVolume> list() {
        return MonthlySalesTargetByVolume.list()
    }

    @Transactional
    public MonthlySalesTargetByVolume create(Object object) {
        try {
            MonthlySalesTargetByVolume monthlySalesTargetByVolume = (MonthlySalesTargetByVolume) object
            return monthlySalesTargetByVolume.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            MonthlySalesTargetByVolume monthlySalesTargetByVolume = (MonthlySalesTargetByVolume) object
            if (monthlySalesTargetByVolume.save(vaidate: false)) {
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        try {
            MonthlySalesTargetByVolume monthlySalesTargetByVolume = (MonthlySalesTargetByVolume) object
            monthlySalesTargetByVolume.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public MonthlySalesTargetByVolume search(String fieldName, String fieldValue) {
        String query = "from MonthlySalesTargetByVolume as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return MonthlySalesTargetByVolume.find(query)
    }

    @Transactional(readOnly = true)
    public Map monthWiseYearlyTargetList(int targetYear, CustomerMaster employee) {
        try{
            List<List> monthWiseDataList = new ArrayList<List>()
            List<Map> yearlyTargetQuantityList = new ArrayList<Map>()
            YearlySalesTargetByVolume yearlySalesTargetByVolume = YearlySalesTargetByVolume.findWhere(targetYear: targetYear, employee: employee)
            if(yearlySalesTargetByVolume){
                List<YearlySalesTargetFinishProduct> yearlySalesTargetFinishProductList = YearlySalesTargetFinishProduct.findAllByYearlySalesTargetByVolume(yearlySalesTargetByVolume)
                yearlySalesTargetFinishProductList.each { yearlySalesTargetFinishProduct->
                    Map yearlyTargetQuantity = [:]
                    FinishProduct finishProduct = yearlySalesTargetFinishProduct.finishProduct
                    yearlyTargetQuantity.put("" + finishProduct.id.toString(), yearlySalesTargetFinishProduct.quantity)
                    yearlyTargetQuantityList.add(yearlyTargetQuantity)
                    List<MonthlySalesTargetFinishProduct> monthlySalesTargetFinishProductList = new ArrayList<MonthlySalesTargetFinishProduct>()
                    for(int i = 1; i <= 12; i++){
                        MonthlySalesTargetFinishProduct monthlySalesTargetFinishProduct = null
                        MonthlySalesTargetByVolume monthlySalesTargetByVolume = MonthlySalesTargetByVolume.findWhere(targetYear: targetYear, targetMonth: i, employee: employee)
                        if(monthlySalesTargetByVolume){
                            monthlySalesTargetFinishProduct = MonthlySalesTargetFinishProduct.findWhere(monthlySalesTargetByVolume: monthlySalesTargetByVolume, finishProduct: finishProduct)
                            if(!monthlySalesTargetFinishProduct){
                                monthlySalesTargetFinishProduct = new MonthlySalesTargetFinishProduct(monthlySalesTargetByVolume: monthlySalesTargetByVolume, quantity: 0, finishProduct: finishProduct)
                            }
                        }else{
                            monthlySalesTargetByVolume = new MonthlySalesTargetByVolume(targetYear: targetYear, targetMonth: i, employee: employee)
                            monthlySalesTargetFinishProduct = new MonthlySalesTargetFinishProduct(monthlySalesTargetByVolume: monthlySalesTargetByVolume, quantity: 0, finishProduct: finishProduct)
                        }
                        monthlySalesTargetFinishProductList.add(monthlySalesTargetFinishProduct)
                    }
                    monthWiseDataList.add(monthlySalesTargetFinishProductList)
                }
            }else{
                SalesHeadByVolume salesHeadByVolume = SalesHeadByVolume.findWhere(targetYear: targetYear, employee: employee, isActive: true)
                if(salesHeadByVolume){
                    List<SalesHeadFinishProduct> salesHeadFinishProductList = SalesHeadFinishProduct.findAllBySalesHeadByVolume(salesHeadByVolume)
                    salesHeadFinishProductList.each { salesHeadFinishProduct->
                        Map yearlyTargetQuantity = [:]
                        FinishProduct finishProduct = salesHeadFinishProduct.finishProduct
                        yearlyTargetQuantity.put("" + finishProduct.id.toString(), salesHeadFinishProduct.quantity)
                        yearlyTargetQuantityList.add(yearlyTargetQuantity)
                        List<MonthlySalesTargetFinishProduct> monthlySalesTargetFinishProductList = new ArrayList<MonthlySalesTargetFinishProduct>()
                        for(int i = 1; i <= 12; i++){
                            MonthlySalesTargetFinishProduct monthlySalesTargetFinishProduct = null
                            MonthlySalesTargetByVolume monthlySalesTargetByVolume = new MonthlySalesTargetByVolume(targetYear: targetYear, targetMonth: i, employee: employee)
                            monthlySalesTargetFinishProduct = new MonthlySalesTargetFinishProduct(monthlySalesTargetByVolume: monthlySalesTargetByVolume, quantity: 0, finishProduct: finishProduct)
                            monthlySalesTargetFinishProductList.add(monthlySalesTargetFinishProduct)
                        }
                        monthWiseDataList.add(monthlySalesTargetFinishProductList)
                    }
                }
            }
            return [monthWiseDataList: monthWiseDataList, yearlyTargetQuantityList: yearlyTargetQuantityList]
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Boolean createNewSalesTarget(Map data){
        try {
            YearlySalesTargetByVolume yearlySalesTargetByVolume = (YearlySalesTargetByVolume) data.get("yearlySalesTargetByVolume")
            yearlySalesTargetByVolume.save()

            List<YearlySalesTargetFinishProduct> yearlySalesTargetFinishProductList = (List<YearlySalesTargetFinishProduct>) data.get("yearlySalesTargetFinishProductList")
            yearlySalesTargetFinishProductList.each { yearlySalesTargetFinishProduct->
                yearlySalesTargetFinishProduct.save()
            }

            List<YearlySalesTargetByVolume> subordinateYearlySalesTargetByVolumeList = (List<YearlySalesTargetByVolume>) data.get("subordinateYearlySalesTargetByVolumeList")
            subordinateYearlySalesTargetByVolumeList.each { subordinateYearlySalesTargetByVolume->
                subordinateYearlySalesTargetByVolume.save()
            }

            List<YearlySalesTargetFinishProduct> subordinateYearlySalesTargetFinishProductList = (List<YearlySalesTargetFinishProduct>) data.get("subordinateYearlySalesTargetFinishProductList")
            subordinateYearlySalesTargetFinishProductList.each { subordinateYearlySalesTargetFinishProduct->
                subordinateYearlySalesTargetFinishProduct.save()
            }

            List<MonthlySalesTargetByVolume> monthlySalesTargetByVolumeList = (List<MonthlySalesTargetByVolume>) data.get("monthlySalesTargetByVolumeList")
            monthlySalesTargetByVolumeList.each { monthlySalesTargetByVolume->
                monthlySalesTargetByVolume.save()
            }

            List<MonthlySalesTargetFinishProduct> monthlySalesTargetFinishProductList = (List<MonthlySalesTargetFinishProduct>) data.get("monthlySalesTargetFinishProductList")
            monthlySalesTargetFinishProductList.each { monthlySalesTargetFinishProduct->
                monthlySalesTargetFinishProduct.save()
            }

            List<MonthlySalesTargetByVolume> subordinateMonthlySalesTargetByVolumeList = (List<MonthlySalesTargetByVolume>) data.get("subordinateMonthlySalesTargetByVolumeList")
            subordinateMonthlySalesTargetByVolumeList.each { subordinateMonthlySalesTargetByVolume->
                subordinateMonthlySalesTargetByVolume.save()
            }

            List<MonthlySalesTargetFinishProduct> subordinateMonthlySalesTargetFinishProductList = (List<MonthlySalesTargetFinishProduct>) data.get("subordinateMonthlySalesTargetFinishProductList")
            subordinateMonthlySalesTargetFinishProductList.each { subordinateMonthlySalesTargetFinishProduct->
                subordinateMonthlySalesTargetFinishProduct.save()
            }

            List<DailySalesTargetByVolume> dailySalesTargetByVolumeList = (List<DailySalesTargetByVolume>) data.get("dailySalesTargetByVolumeList")
            dailySalesTargetByVolumeList.each { dailySalesTargetByVolume->
                dailySalesTargetByVolume.save()
            }

            List<DailySalesTargetFinishProduct> dailySalesTargetFinishProductList = (List<DailySalesTargetFinishProduct>) data.get("dailySalesTargetFinishProductList")
            dailySalesTargetFinishProductList.each { dailySalesTargetFinishProduct->
                dailySalesTargetFinishProduct.save()
            }
            return Boolean.TRUE
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listSubordinateMonthLySalesTarget(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String query = """
                SELECT `productId`, `productCode`, `productName`, `employeeId`, `employeeCode`, `employeeName`, SUM(`jan`) AS `jan`
                    , SUM(`feb`) AS `feb`, SUM(`mar`) AS `mar`, SUM(`apr`) AS `apr`, SUM(`may`) AS `may`, SUM(`jun`) AS `jun`, SUM(`jul`) AS `jul`
                    , SUM(`aug`) AS `aug`, SUM(`sep`) AS `sep`, SUM(`oct`) AS `oct`, SUM(`nov`) AS `nov` , SUM(`dec`) AS `dec`
                FROM( SELECT `finish_product`.`id` AS `productId`, `finish_product`.`code` AS `productCode`, `finish_product`.`name` AS `productName`
                        , `customer_master`.`id` AS `employeeId`, `customer_master`.`code` AS `employeeCode`, `customer_master`.`name` AS `employeeName`
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 1,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'jan'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 2,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'feb'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 3,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'mar'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 4,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'apr'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 5,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'may'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 6,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'jun'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 7,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'jul'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 8,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'aug'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 9,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'sep'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 10,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'oct'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 11,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'nov'
                        , IF(`monthly_sales_target_by_volume`.`target_month` = 12,`monthly_sales_target_finish_product`.`quantity`, 0) AS 'dec'
                    FROM
                        `monthly_sales_target_finish_product`
                        INNER JOIN `monthly_sales_target_by_volume`
                            ON (`monthly_sales_target_finish_product`.`monthly_sales_target_by_volume_id` = `monthly_sales_target_by_volume`.`id`)
                        INNER JOIN `finish_product`
                            ON (`monthly_sales_target_finish_product`.`finish_product_id` = `finish_product`.`id`)
                        INNER JOIN `customer_master`
                            ON (`monthly_sales_target_by_volume`.`employee_id` = `customer_master`.`id`)
                    WHERE `monthly_sales_target_by_volume`.`target_year` = :targetYear
                    AND `monthly_sales_target_by_volume`.`is_active` = TRUE
                    AND `monthly_sales_target_by_volume`.`yearly_sales_target_by_volume_id` = (SELECT `id` FROM `yearly_sales_target_by_volume` WHERE `employee_id` = ${applicationUser.customerMasterId} AND `target_year` = :targetYear)
                    AND `monthly_sales_target_by_volume`.`employee_id` != ${applicationUser.customerMasterId}
                    ) AS `tbl`
                GROUP BY `productId`, `employeeId`
            """
            List resultList = sql.rows(query, params)
            return resultList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Boolean updateSalesTarget(Map data, CustomerMaster currentEmployee){
        try {
            YearlySalesTargetByVolume yearlySalesTargetByVolume = (YearlySalesTargetByVolume) data.get("yearlySalesTargetByVolume")
            yearlySalesTargetByVolume.save()

            List<YearlySalesTargetFinishProduct> yearlySalesTargetFinishProductList = (List<YearlySalesTargetFinishProduct>) data.get("yearlySalesTargetFinishProductList")
            yearlySalesTargetFinishProductList.each { yearlySalesTargetFinishProduct ->
                yearlySalesTargetFinishProduct.save()
            }

            List<MonthlySalesTargetByVolume> monthlySalesTargetByVolumeListTobeDeleted = MonthlySalesTargetByVolume.findAllByYearlySalesTargetByVolumeAndEmployeeNotEqual(yearlySalesTargetByVolume, currentEmployee)
            monthlySalesTargetByVolumeListTobeDeleted.each {  monthlySalesTargetByVolume ->
                MonthlySalesTargetFinishProduct.executeUpdate("delete from MonthlySalesTargetFinishProduct mstfp where mstfp.monthlySalesTargetByVolume = ?", [monthlySalesTargetByVolume])
            }

            // Delete Subordinate Monthly Sales Target
//            monthlySalesTargetByVolumeListTobeDeleted.removeAll()
            MonthlySalesTargetByVolume.executeUpdate("delete from MonthlySalesTargetByVolume mstv where mstv.yearlySalesTargetByVolume = :yearlySalesTarget and mstv.employee != :employee", [yearlySalesTarget: yearlySalesTargetByVolume, employee: currentEmployee])

            List<DailySalesTargetByVolume> dailySalesTargetByVolumeListTobeDeleted = DailySalesTargetByVolume.findAllByYearlySalesTargetByVolume(yearlySalesTargetByVolume)
            dailySalesTargetByVolumeListTobeDeleted.each { dailySalesTargetByVolume ->
                DailySalesTargetFinishProduct.executeUpdate("delete from DailySalesTargetFinishProduct dstfp where dstfp.dailySalesTargetByVolume = ?", [dailySalesTargetByVolume])
            }

            // Delete Subordinate Daily Sales Target
//            dailySalesTargetByVolumeListTobeDeleted.removeAll()
            DailySalesTargetByVolume.executeUpdate("delete from DailySalesTargetByVolume dstv where dstv.yearlySalesTargetByVolume = ?", [yearlySalesTargetByVolume])


            List<YearlySalesTargetByVolume> subordinateYearlySalesTargetByVolumeList = (List<YearlySalesTargetByVolume>) data.get("subordinateYearlySalesTargetByVolumeList")
            subordinateYearlySalesTargetByVolumeList.each { subordinateYearlySalesTargetByVolume->
                subordinateYearlySalesTargetByVolume.save()
            }

            List<YearlySalesTargetFinishProduct> subordinateYearlySalesTargetFinishProductList = (List<YearlySalesTargetFinishProduct>) data.get("subordinateYearlySalesTargetFinishProductList")
            subordinateYearlySalesTargetFinishProductList.each { subordinateYearlySalesTargetFinishProduct->
                subordinateYearlySalesTargetFinishProduct.save()
            }

            List<MonthlySalesTargetByVolume> monthlySalesTargetByVolumeList = (List<MonthlySalesTargetByVolume>) data.get("monthlySalesTargetByVolumeList")
            monthlySalesTargetByVolumeList.each { monthlySalesTargetByVolume->
                monthlySalesTargetByVolume.save()
            }

            List<MonthlySalesTargetFinishProduct> monthlySalesTargetFinishProductList = (List<MonthlySalesTargetFinishProduct>) data.get("monthlySalesTargetFinishProductList")
            monthlySalesTargetFinishProductList.each { monthlySalesTargetFinishProduct->
                monthlySalesTargetFinishProduct.save()
            }

            List<MonthlySalesTargetByVolume> subordinateMonthlySalesTargetByVolumeList = (List<MonthlySalesTargetByVolume>) data.get("subordinateMonthlySalesTargetByVolumeList")
            subordinateMonthlySalesTargetByVolumeList.each { subordinateMonthlySalesTargetByVolume->
                subordinateMonthlySalesTargetByVolume.save()
            }

            List<MonthlySalesTargetFinishProduct> subordinateMonthlySalesTargetFinishProductList = (List<MonthlySalesTargetFinishProduct>) data.get("subordinateMonthlySalesTargetFinishProductList")
            subordinateMonthlySalesTargetFinishProductList.each { subordinateMonthlySalesTargetFinishProduct->
                subordinateMonthlySalesTargetFinishProduct.save()
            }

            List<DailySalesTargetByVolume> dailySalesTargetByVolumeList = (List<DailySalesTargetByVolume>) data.get("dailySalesTargetByVolumeList")
            dailySalesTargetByVolumeList.each { dailySalesTargetByVolume->
                dailySalesTargetByVolume.save()
            }

            List<DailySalesTargetFinishProduct> dailySalesTargetFinishProductList = (List<DailySalesTargetFinishProduct>) data.get("dailySalesTargetFinishProductList")
            dailySalesTargetFinishProductList.each { dailySalesTargetFinishProduct->
                dailySalesTargetFinishProduct.save()
            }
            return Boolean.TRUE
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

}
