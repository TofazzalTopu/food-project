package com.bits.bdfp.dashboard

import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.docu.commons.CommonConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class SearchDashboardInfoService {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Autowired
    SpringSecurityService springSecurityService

    def serviceMethod() {

    }


// R & D HighChart

    @Transactional(readOnly = true)
    public List salesVsCollection() {
        Sql sql = new Sql(dataSource)
        String query = """ SELECT csc.`name`,ROUND(SUM(inv.`invoice_amount`),2)/1000 AS sales,ROUND(SUM(inv.`paid_amount`),2)/1000 AS collection
                    FROM `invoice` inv

                INNER JOIN `customer_master` cm ON cm.id=inv.`default_customer_id`
                INNER JOIN `customer_sales_channel` csc ON csc.id=cm.`customer_sales_channel_id`
                INNER JOIN `customer_category` cc ON cc.id=cm.`category_id`

              WHERE
                 inv.`transaction_date` BETWEEN '2015-01-01' AND '2016-12-31' AND cc.`name` NOT IN ('Sales Man','Retail Outlet')
                GROUP BY csc.id """
        return sql.rows(query)
    }


    @Transactional(readOnly = true)
    public List salesVsCollectionclaim() {
        Sql sql = new Sql(dataSource)
        String query = """ SELECT a.name,SUM(a.salescurrent) AS c , SUM(a.salesprevious) AS p

                        FROM (
                        SELECT csc.`name`, ROUND(SUM(inv.`invoice_amount`),2)AS salescurrent, 0 AS salesprevious
                                            FROM `invoice` inv

                                        INNER JOIN `customer_master` cm ON cm.id=inv.`default_customer_id`
                                        INNER JOIN `customer_sales_channel` csc ON csc.id=cm.`customer_sales_channel_id`
                                        INNER JOIN `customer_category` cc ON cc.id=cm.`category_id`

                                      WHERE
                                        MONTH(inv.transaction_date) = MONTH(CURRENT_DATE - INTERVAL 2 MONTH)  AND cc.`name` NOT IN ('Sales Man','Retail Outlet')
                                        GROUP BY csc.id

                    UNION  ALL

                        SELECT csc.`name`,0 AS salescurrent,ROUND(SUM(inv.`invoice_amount`),2) AS salesprevious

                                        FROM `invoice` inv

                                        INNER JOIN `customer_master` cm ON cm.id=inv.`default_customer_id`
                                        INNER JOIN `customer_sales_channel` csc ON csc.id=cm.`customer_sales_channel_id`
                                        INNER JOIN `customer_category` cc ON cc.id=cm.`category_id`

                                      WHERE
                                        MONTH(inv.transaction_date) = MONTH(CURRENT_DATE - INTERVAL 3 MONTH)  AND cc.`name` NOT IN ('Sales Man','Retail Outlet')
                                        GROUP BY csc.id  )AS a GROUP BY a.name """
        return sql.rows(query)
    }


    @Transactional(readOnly = true)
    public List increasedecreasesales() {
        Sql sql = new Sql(dataSource)
        String query = """ SELECT  csc.`name`, ROUND(SUM(inv.`invoice_amount`),2)AS sales, MONTH(inv.transaction_date) AS mon
                    FROM `invoice` inv

                INNER JOIN `customer_master` cm ON cm.id=inv.`default_customer_id`
                INNER JOIN `customer_sales_channel` csc ON csc.id=cm.`customer_sales_channel_id`
                INNER JOIN `customer_category` cc ON cc.id=cm.`category_id`

              WHERE
               YEAR(inv.transaction_date) =   YEAR(CURDATE())   AND cc.`name` NOT IN ('Sales Man','Retail Outlet')
                GROUP BY csc.id, MONTH(inv.transaction_date)"""
        return sql.rows(query)
    }


    @Transactional(readOnly = true)
    public List getStockOutReport(Object params) {
        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        String qry = CommonConstants.EMPTY_STRING;
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList = null;

        if (params.fromDate) {
            String date1 = params.fromDate
            Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
            String newfromDate = oldfromDate.format('yyyy-MM-dd')


            String date2 = params.toDate
            Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
            String newtoDate = oldtoDate.format('yyyy-MM-dd')

            qry =" AND  (DATE(sdod.date_created) BETWEEN '${newfromDate}' AND '${newtoDate}') ";
        }
        if (params.monthYr) {
            String mnthParam = params.monthYr
            Date oldMnth = Date.parse('MM-yyyy', mnthParam)
            String newMonth = oldMnth.format('yyyy-MM')
            qry=" AND sdod.date_created LIKE '"+newMonth+"-%'"
        }

        strSql = """
            SELECT i.code, sdod.date_created, fp.name as productName , sdod.quantity, sdod.rate, ROUND(sdod.amount,2) AS amount , ROUND((ids.quantity*ppp.price),2) AS total
            ,pc.short_name, ppp.price , mp.name as masterProductName,entp.name AS EnterpriseName, dp.`name` AS DPName,DATE_FORMAT(sdod.date_created, '%M') AS month_of_yr
            FROM invoice AS i,invoice_details AS ids, finish_product AS fp, product_price_product AS ppp, pricing_category AS pc,
            secondary_demand_order_details AS sdod, master_product AS mp , enterprise_configuration AS entp, distribution_point AS dp
            WHERE i.id=ids.invoice_id
            AND ids.finish_product_id=fp.id
            AND mp.id=fp.master_product_id
            AND ids.finish_product_id=ppp.finish_product_id
            AND pc.id=ppp.pricing_category_id
            AND pc.short_name='TP'
            AND sdod.secondary_demand_order_id=i.secondary_demand_order_id
            AND ids.finish_product_id=sdod.finish_product_id
            AND entp.id =fp.enterprise_configuration_id
            AND fp.enterprise_configuration_id = dp.enterprise_configuration_id
            ${qry}
            GROUP BY sdod.date_created,i.code
        """
        objList = sql.rows(strSql)

        return objList;
    }


    @Transactional(readOnly = true)
    public List projectedsales() {
        Sql sql = new Sql(dataSource)
        String query = """ SELECT DISTINCT  SUM(`target_amount`) AS target
                    FROM `monthly_sales_target_by_amount`

                    LEFT JOIN invoice ON invoice.`default_customer_id` = monthly_sales_target_by_amount.`employee_id`
                    LEFT JOIN `customer_master` ON customer_master.`id` = invoice.`default_customer_id`
                    INNER JOIN `customer_sales_channel` ON `customer_sales_channel`.id = `customer_master`.`customer_sales_channel_id`
                WHERE YEAR(`monthly_sales_target_by_amount`.`end_date`) = YEAR(CURRENT_DATE)"""
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List targetvsachievement() {
        Sql sql = new Sql(dataSource)
        String query = """   SELECT `customer_sales_channel`.`name` as name
,SUM(dstba.`target_amount`) AS t
,IFNULL((SELECT SUM(`invoice_details`.`quantity` * `invoice_details`.`unit_price`)
FROM invoice

INNER JOIN `invoice_details`
ON `invoice_details`.`invoice_id`= `invoice`.`id`
INNER JOIN `finish_product`
ON `finish_product`.`id`= invoice_details.`finish_product_id`
INNER JOIN `customer_master`
ON `customer_master`.id = `invoice`.`default_customer_id`
WHERE
 customer_master.`id` IN
   (SELECT DISTINCT dstba.`employee_id` FROM `daily_sales_target_by_amount` dstba

INNER JOIN `customer_master`
ON `customer_master`.id = dstba.`employee_id`
INNER JOIN `customer_sales_channel`
ON `customer_sales_channel`.`id` = `customer_master`.`customer_sales_channel_id`

WHERE   dstba.`yearly_sales_target_by_amount_id` IN (SELECT `id` FROM `yearly_sales_target_by_amount` WHERE   target_year = 2017))  ),0) AS a

FROM `daily_sales_target_by_amount` dstba

INNER JOIN `customer_master`
ON `customer_master`.id = dstba.`employee_id`
INNER JOIN `customer_sales_channel`
ON `customer_sales_channel`.`id` = `customer_master`.`customer_sales_channel_id`

WHERE
 dstba.`yearly_sales_target_by_amount_id` IN (SELECT `id` FROM `yearly_sales_target_by_amount` WHERE   target_year = 2017)

GROUP BY `customer_master`.`customer_sales_channel_id` """
        return sql.rows(query)
    }



    @Transactional(readOnly = true)
    public List getBranchStockReport(Object params) {
        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        String qry = CommonConstants.EMPTY_STRING;
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList = null;

        if (params.monthYr) {
            String mnthParam = params.monthYr
            Date oldMnth = Date.parse('MM-yyyy', mnthParam)
            String newMonth = oldMnth.format('yyyy-MM')
            qry=" AND sdod.date_created LIKE '"+newMonth+"-%'"
        }

        Calendar cal = Calendar.getInstance();
        String mnthParam = params.monthYr
        Date oldMnth = Date.parse('MM-yyyy', mnthParam)
        String newMonth = oldMnth.format('yyyy-MM')

        cal.setTime(oldMnth);
        cal.add(cal.MONTH, -1);
        int mnth=cal.get(cal.MONTH);
        int yr=cal.get(cal.YEAR);
        //cal.set(dt, cal.getActualMaximum(dt));

        int lastDate=cal.getActualMaximum(mnth);

        String dpst='';
        String fgst='';
        String pmr='';
        String fgs='';
        if(mnth<9){
             dpst= "'"+yr+"-0"+(mnth+1)+"%"+"'";
             fgst= "'"+yr+"-0"+(mnth+1)+"%"+"'";
             pmr= "'"+yr+"-0"+(mnth-1)+"%"+"'";
             fgs="'"+yr+"-0"+(mnth+1)+"%"+"'";
        }else{
             dpst= "'"+yr+"-"+(mnth+1)+"%"+"'";
             fgst= "'"+yr+"-"+(mnth+1)+"%"+"'";
             pmr= "'"+yr+"-"+(mnth-1)+"%"+"'";
             fgs="'"+yr+"-0"+(mnth+1)+"%"+"'";
        }

        if(params.dp=="null"){
            List distributionPointList= distributionPointForLoggedInUser()
            strSql = """

                SELECT fp.name AS productName, SUM(dpst.in_quantity) AS branchStock, (SUM(dpst.in_quantity)*ppp.price) AS bsprice ,
                SUM(fgst.in_quantity) AS factoryStock, (SUM(fgst.in_quantity)*ppp.price) AS fsprice, ( SUM(dpst.in_quantity)+SUM(fgst.in_quantity)) AS openingStock,
                fgst.in_quantity AS inq, ((fgst.in_quantity)*ppp.price) AS inPrice,  SUM(fgst.out_quantity) AS outq, (SUM(fgst.out_quantity)*ppp.price) AS OutPrice ,
                (dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) AS stbranch,
                ((dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))*ppp.price) AS stbranchPrice,
                ((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) AS stfactory,
                ((dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))+((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))) AS closingStock,
                (((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))*ppp.price) AS stfactoryPrice,
                SUM(mrd.quantity) AS claim, (SUM(mrd.quantity)*ppp.price) AS claimPrice, SUM(pmrd.qc_quantity) AS adjust, (SUM(pmrd.qc_quantity)*ppp.price) AS adjustPrice,
                SUM(pmrd.qc_failed_quantity) AS actual, (SUM(pmrd.qc_failed_quantity)*ppp.price) AS actualPrice
                , ppp.price AS price

                FROM distribution_point_stock AS dps, distribution_point_stock_transaction AS dpst,
                finish_good_stock AS fgs, finish_good_stock_transaction AS fgst ,finish_product AS fp,
                market_return_details AS mrd,  process_market_return AS pmr, process_market_return_details AS pmrd,
                sub_warehouse AS sw , distribution_point_warehouse AS dpw, product_price_product AS ppp, pricing_category AS pc

                WHERE dpst.distribution_point_stock_id IN (SELECT dp.id
                                                            FROM application_user_distribution_point AS audp , distribution_point AS dp
                                                            WHERE audp.application_user_id=15
                                                            AND dp.id=audp.distribution_point_id
                                                            )
                AND sw.warehouse_id=dpw.warehouse_id
                AND dps.sub_warehouse_id=sw.id
                AND dpst.distribution_point_stock_id=dps.id
                AND dpst.transaction_date LIKE  ${dpst}
                AND fgst.finish_good_stock_id=fgs.id
                AND fgst.transaction_date LIKE ${fgst}
                AND fgs.date_created LIKE ${fgst}
                AND fgs.finish_product_id=fp.id
                AND mrd.finish_product_id=fp.id
                AND mrd.mr_type='Market Return'
                AND pmr.date_created LIKE ${pmr}
                AND pmrd.process_market_return_id=pmr.id
                AND pmrd.finish_product_id=fp.id
                AND ppp.finish_product_id=fgs.finish_product_id
                AND ppp.pricing_category_id=pc.id
                AND pc.short_name="DP"

                GROUP BY fgs.finish_product_id,fgs.finish_product_id,mrd.finish_product_id, pmrd.finish_product_id
                """
        }
        else{

            strSql = """

                SELECT fp.name AS productName, SUM(dpst.in_quantity) AS branchStock, (SUM(dpst.in_quantity)*ppp.price) AS bsprice ,
                SUM(fgst.in_quantity) AS factoryStock, (SUM(fgst.in_quantity)*ppp.price) AS fsprice, ( SUM(dpst.in_quantity)+SUM(fgst.in_quantity)) AS openingStock,
                fgst.in_quantity AS inq, ((fgst.in_quantity)*ppp.price) AS inPrice,  SUM(fgst.out_quantity) AS outq, (SUM(fgst.out_quantity)*ppp.price) AS OutPrice ,
                (dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) AS stbranch,
                ((dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))*ppp.price) AS stbranchPrice,
                ((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) AS stfactory,
                ((dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))+((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))) AS closingStock,
                (((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))*ppp.price) AS stfactoryPrice,
                SUM(mrd.quantity) AS claim, (SUM(mrd.quantity)*ppp.price) AS claimPrice, SUM(pmrd.qc_quantity) AS adjust, (SUM(pmrd.qc_quantity)*ppp.price) AS adjustPrice,
                SUM(pmrd.qc_failed_quantity) AS actual, (SUM(pmrd.qc_failed_quantity)*ppp.price) AS actualPrice
                , ppp.price AS price

                FROM distribution_point_stock AS dps, distribution_point_stock_transaction AS dpst,
                finish_good_stock AS fgs, finish_good_stock_transaction AS fgst ,finish_product AS fp,
                market_return_details AS mrd,  process_market_return AS pmr, process_market_return_details AS pmrd,
                sub_warehouse AS sw , distribution_point_warehouse AS dpw, product_price_product AS ppp, pricing_category AS pc

                WHERE dpst.distribution_point_stock_id=${params.dp}
                AND sw.warehouse_id=dpw.warehouse_id
                AND dps.sub_warehouse_id=sw.id
                AND dpst.distribution_point_stock_id=dps.id
                AND dpst.transaction_date LIKE  ${dpst}
                AND fgst.finish_good_stock_id=fgs.id
                AND fgst.transaction_date LIKE ${fgst}
                AND fgs.date_created LIKE ${fgst}
                AND fgs.finish_product_id=fp.id
                AND mrd.finish_product_id=fp.id
                AND mrd.mr_type='Market Return'
                AND pmr.date_created LIKE ${pmr}
                AND pmrd.process_market_return_id=pmr.id
                AND pmrd.finish_product_id=fp.id
                AND ppp.finish_product_id=fgs.finish_product_id
                AND ppp.pricing_category_id=pc.id
                AND pc.short_name="DP"

                GROUP BY fgs.finish_product_id,fgs.finish_product_id,mrd.finish_product_id, pmrd.finish_product_id
                """

        }


        objList = sql.rows(strSql)

        return objList;
    }


}
