package com.docu.security

class Requestmap {

	String url
	String configAttribute
    long  featureControllerMappingId
    long moduleId
	static mapping = {
		cache true
	}

	static constraints = {
		url blank: false,unique: true
		configAttribute (blank: false, maxSize: 5000)
        featureControllerMappingId blank: false
	}

    def String toString() {
        return url;
    }

}
