package com.bits.bdfp.bonus
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class BonusCriteriaSetupService extends Service {
   static transactional = false
   DataSource dataSource
   Sql sql
  @Transactional
  public BonusCriteriaSetup create(Object object) {
    BonusCriteriaSetup bonusCriteriaSetup = (BonusCriteriaSetup) object
    if (bonusCriteriaSetup.save(false)) {
      return bonusCriteriaSetup
    }
    return null
  }

@Transactional
  public Integer update(Object object) {
    BonusCriteriaSetup bonusCriteriaSetup = (BonusCriteriaSetup) object
    if (bonusCriteriaSetup.save()) {
      return new Integer(1)
    }
    else {
       return new Integer(0)
    }
  }

  @Transactional
  public Integer delete(Object object) {
    BonusCriteriaSetup bonusCriteriaSetup = (BonusCriteriaSetup) object
    bonusCriteriaSetup.delete()
    return new Integer(1)
  }

  @Transactional(readOnly = true) 
  public Map getListForGrid(Action action) {
    List<BonusCriteriaSetup> objList = BonusCriteriaSetup.withCriteria {
     if(action.resultPerPage !=-1){
            maxResults(action.resultPerPage)
        }
      firstResult(action.start)
      order(action.sortCol, action.sortOrder)
    }
    long total = BonusCriteriaSetup.count()
    return [objList: objList, count: total]
  }

  @Transactional(readOnly = true)
  public List<BonusCriteriaSetup> list() {
    return BonusCriteriaSetup.list()
  }

  @Transactional(readOnly = true)
  public BonusCriteriaSetup read(Long id) {
    return BonusCriteriaSetup.read(id)
  }

  @Transactional(readOnly = true)
  public BonusCriteriaSetup search(String fieldName, String fieldValue) {
    String query = "from BonusCriteriaSetup as docu where docu."+ fieldName +" = '" + fieldValue +"'"
    return BonusCriteriaSetup.find(query)
  }
}
