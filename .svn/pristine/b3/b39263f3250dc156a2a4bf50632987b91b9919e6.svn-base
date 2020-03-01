package com.docu.security

class UserLockStatus {
    boolean isLocked
    Date LockDate
    Date unlockDate
    String lockedBy
    String unlockedBy
    boolean isServerBusy = false
    static constraints = {
        unlockDate(nullable: true, blank: true)
        unlockedBy(nullable: true, blank: true)
    }
}
