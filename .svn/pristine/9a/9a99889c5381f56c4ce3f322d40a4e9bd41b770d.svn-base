package com.bits.bdfp.common

class Nationality {
    String          name
    String          note

    static constraints = {
        name(blank: false, nullable: false, maxSize: 30, unique: true)
        note(blank: true, nullable: true, maxSize: 255)
    }
    static mapping = {
        cache: 'read-only'
    }

    def String toString() {
        return name;
    }
}
