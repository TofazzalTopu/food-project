package com.bits.bdfp.inventory.setup

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DeliveryTruckService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public DeliveryTruck create(Object object) {
        DeliveryTruck deliveryTruck = (DeliveryTruck) object
        if (deliveryTruck.save(false)) {
            return deliveryTruck
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        DeliveryTruck deliveryTruck = (DeliveryTruck) object
        if (deliveryTruck.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        DeliveryTruck deliveryTruck = (DeliveryTruck) object
        deliveryTruck.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<DeliveryTruck> objList = DeliveryTruck.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = DeliveryTruck.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<DeliveryTruck> list() {
        return DeliveryTruck.list()
    }

    @Transactional(readOnly = true)
    public DeliveryTruck read(Long id) {
        return DeliveryTruck.read(id)
    }

    @Transactional(readOnly = true)
    public DeliveryTruck search(String fieldName, String fieldValue) {
        String query = "from DeliveryTruck as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return DeliveryTruck.find(query)
    }

    @Transactional(readOnly = true)
    public Map listTruck(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = ""
        String offSet = ""
        String searchCondition = ""
        String loadingSizeStr = ""
        double loadingSize = 0
        if(params.invoiceNos){
            String invoiceNumbers = ''
            String[] invoiceNoList = params.invoiceNos.split(',')
            for (int i = 0; i < invoiceNoList.length; i++) {
                if(invoiceNumbers){
                    invoiceNumbers += ",'"+invoiceNoList[i]+ "'"
                }else{
                    invoiceNumbers += "'"+invoiceNoList[i]+ "'"
                }
            }

            loadingSizeStr = """
                SELECT t1.capacity + t2.capacity AS capacity

                FROM (SELECT
                  (SUM(CASE LOWER(pt.name)
                       WHEN 'crate' THEN
                       CASE LOWER(muc.name)
                        WHEN 'liter' THEN
                         (IFNULL(ROUND((id.quantity * pp.pack_size), 2), 0))
                        WHEN 'ml' THEN
                         (IFNULL(ROUND(((id.quantity * pp.pack_size) / 1000), 2), 0))
                        ELSE 0
                       END
                       WHEN 'carton' THEN
                        CASE LOWER(muc.name)
                        WHEN 'gm' THEN
                         (IFNULL(ROUND(((id.quantity * pp.pack_size) / 1000), 2), 0))
                        WHEN 'kg' THEN
                         (IFNULL(ROUND((id.quantity * pp.pack_size), 2), 0))
                        ELSE 0
                       END
                       ELSE 0
                  END) / 1000)
                  AS "capacity"

                FROM invoice i
                INNER JOIN invoice_details id
                        ON id.invoice_id = i.id
                INNER JOIN finish_product fp
                        ON fp.id = id.finish_product_id
                LEFT JOIN measure_unit_configuration muc
                       ON muc.id = fp.measure_unit_configuration_id
                LEFT JOIN product_package pp
                       ON pp.finish_product_id = fp.id
                LEFT JOIN package_type pt
                       ON pt.id = pp.package_type_id

                WHERE i.code IN (${invoiceNumbers})) AS t1,

                (SELECT
                (SUM(CASE LOWER(pt.name)
                       WHEN 'crate' THEN
                       CASE LOWER(muc.name)
                        WHEN 'liter' THEN
                         (IFNULL(ROUND((qbb.quantity * pp.pack_size), 2), 0))
                        WHEN 'ml' THEN
                         (IFNULL(ROUND(((qbb.quantity * pp.pack_size) / 1000), 2), 0))
                        ELSE 0
                       END
                       WHEN 'carton' THEN
                        CASE LOWER(muc.name)
                        WHEN 'gm' THEN
                         (IFNULL(ROUND(((qbb.quantity * pp.pack_size) / 1000), 2), 0))
                        WHEN 'kg' THEN
                         (IFNULL(ROUND((qbb.quantity * pp.pack_size), 2), 0))
                        ELSE 0
                       END
                       ELSE 0
                  END) / 1000)
                  AS "capacity"

                FROM invoice i
                INNER JOIN quantity_based_bonus qbb
                        ON qbb.invoice_id = i.id
                INNER JOIN finish_product fp
                        ON fp.id = qbb.finish_product_id
                LEFT JOIN measure_unit_configuration muc
                       ON muc.id = fp.measure_unit_configuration_id
                LEFT JOIN product_package pp
                       ON pp.finish_product_id = fp.id
                LEFT JOIN package_type pt
                       ON pt.id = pp.package_type_id
                WHERE i.code IN (${invoiceNumbers})) AS t2
            """

            /*
            loadingSizeStr = """
                SELECT
                  (SUM(CASE LOWER(pt.name)
                       WHEN 'crate' THEN
                       CASE LOWER(muc.name)
                            WHEN 'liter' THEN
                                 (IFNULL(ROUND((fpbd.quantity * pp.pack_size), 2), 0))
                            WHEN 'ml' THEN
                                 (IFNULL(ROUND(((fpbd.quantity * pp.pack_size) / 1000), 2), 0))
                            ELSE 0
                       END
                       WHEN 'carton' THEN
                            CASE LOWER(muc.name)
                            WHEN 'gm' THEN
                                 (IFNULL(ROUND(((fpbd.quantity * pp.pack_size) / 1000), 2), 0))
                            WHEN 'kg' THEN
                                 (IFNULL(ROUND((fpbd.quantity * pp.pack_size), 2), 0))
                            ELSE 0
                       END
                       ELSE 0
                  END) / 1000)
                  AS "capacity"

                FROM finished_product_booked fpb
                INNER JOIN finished_product_booked_details fpbd
                   ON fpbd.finished_product_booked_id = fpb.id
                INNER JOIN finish_product fp
                   ON fp.id = fpbd.finish_product_id
                LEFT JOIN measure_unit_configuration muc
                  ON muc.id = fp.measure_unit_configuration_id
                LEFT JOIN product_package pp
                  ON pp.finish_product_id = fp.id
                LEFT JOIN package_type pt
                  ON pt.id = pp.package_type_id

                WHERE fpb.invoice_no IN (${invoiceNumbers})
            """ */

            Object object = sql.firstRow(loadingSizeStr.toString())
            loadingSize = object.capacity
        }
        if(loadingSize > 0){
            searchCondition += """ AND `delivery_truck`.`loading_capacity` >= ${loadingSize}"""
        }
        if (params.entId) {
            searchCondition += """ AND `delivery_truck`.`enterprise_configuration_id` = ${params.entId}
        """
        }
        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """SELECT `delivery_truck`.`id`,`delivery_truck`.`name`, `delivery_truck`.`vehicle_number`,
                        `delivery_truck`.`loading_capacity`,`distribution_point`.`name` AS dpName,
                        delivery_truck.truck_height as truckHeight,
                        delivery_truck.truck_width as truckWidth, delivery_truck.truck_length as truckLength
                        FROM `delivery_truck`
                        INNER JOIN `distribution_point` ON `distribution_point`.`id` = `delivery_truck`.`distribution_point_id`

                        WHERE 1=1
                            ${searchCondition}
                        ORDER BY `delivery_truck`.`loading_capacity` DESC
                            ${strLIMIT}
                            ${offSet}
                          """
        List objList = sql.rows(strSql.toString())
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }
}
