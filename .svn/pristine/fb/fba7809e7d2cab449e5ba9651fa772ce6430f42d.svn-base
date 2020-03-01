package com.bits.bdfp.common

class DocumentType {
    String              name
    String              note

    static constraints = {
        name(blank: false, nullable: false, unique: true)
        note(blank: true, nullable: true)
    }

    @Override
    String toString() {
        return name
    }
}
