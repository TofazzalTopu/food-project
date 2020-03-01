package com.bits.bdfp.inventory.setup
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class ChargeTypeService extends Service {
   static transactional = false
   DataSource dataSource
   Sql sql
  @Transactional
  public ChargeType create(Object object) {
    ChargeType chargeType = (ChargeType) object
    if (chargeType.save(false)) {
      return chargeType
    }
    return null
  }

@Transactional
  public Integer update(Object object) {
    ChargeType chargeType = (ChargeType) object
    if (chargeType.save()) {
      return new Integer(1)
    }
    else {
       return new Integer(0)
    }
  }

  @Transactional
  public Integer delete(Object object) {
    ChargeType chargeType = (ChargeType) object
    chargeType.delete()
    return new Integer(1)
  }

  @Transactional(readOnly = true) 
  public Map getListForGrid(Action action) {
    List<ChargeType> objList = ChargeType.withCriteria {
     if(action.resultPerPage !=-1){
            maxResults(action.resultPerPage)
        }
      firstResult(action.start)
      order(action.sortCol, action.sortOrder)
    }
    long total = ChargeType.count()
    return [objList: objList, count: total]
  }

  @Transactional(readOnly = true)
  public List<ChargeType> list() {
    return ChargeType.list()
  }

  @Transactional(readOnly = true)
  public ChargeType read(Long id) {
    return ChargeType.read(id)
  }

  @Transactional(readOnly = true)
  public ChargeType search(String fieldName, String fieldValue) {
    String query = "from ChargeType as docu where docu."+ fieldName +" = '" + fieldValue +"'"
    return ChargeType.find(query)
  }
}
