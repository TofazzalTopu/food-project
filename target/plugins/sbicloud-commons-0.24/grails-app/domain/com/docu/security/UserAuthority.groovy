
package com.docu.security

class UserAuthority {
	String authority
    String role

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
        role blank: false, unique: true
	}

    def String toString() {
        return role;
    }

}
