package com.bits.bdfp.inventory.warehouse.warehouse

import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.inventory.warehouse.SubWarehouseType
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.WarehouseService
import com.bits.bdfp.inventory.warehouse.SubWarehouseService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createWarehouseAction")
class CreateWarehouseAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WarehouseService warehouseService

    @Autowired
    SubWarehouseService subWarehouseService

    public Object preCondition(Object user, Object object) {
        try {
            Warehouse warehouse = (Warehouse) object
            ApplicationUser applicationUser = (ApplicationUser) user
            warehouse.userCreated = applicationUser
            warehouse.dateCreated = new Date()
            if (!warehouse.validate()) {
                return null
            }
            return warehouse
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object object) {
        try {
            Warehouse warehouse = (Warehouse) object
            int totalSubInventoryType = SubWarehouseType.count()
            List<SubWarehouseType> subWarehouseTypeList = SubWarehouseType.list()
            List<SubWarehouse> subWarehouseList =  new ArrayList<SubWarehouse>()
            for(int i = 0; i < totalSubInventoryType; i++)
            {
                SubWarehouse subWarehouse = new SubWarehouse()
                subWarehouse.name = warehouse.name + " " + subWarehouseTypeList[i]
                subWarehouse.warehouse = warehouse
                subWarehouse.subWarehouseType = subWarehouseTypeList[i]
                subWarehouse.dateCreated = new Date()
                subWarehouse.userCreated = object.userCreated

                subWarehouseList.add(subWarehouse)
            }
            Map map = new LinkedHashMap()
            map.put('wareHouse', warehouse)
            map.put('subWareHouse', subWarehouseList)
            warehouseService.create(map)
            return warehouse

//            return warehouseService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}