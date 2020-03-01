/* Copyright 2006-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import groovy.text.SimpleTemplateEngine

includeTargets << grailsScript('_GrailsBootstrap')

overwriteAll = false
templateAttributes = [:]
packageName  =""
//templateDir = "$springSecurityCorePluginDir/src/templates"
//temp="$pluginaccesscontrolPluginDir"
//println temp
templateDir="$docuaccesscontrolPluginDir/src/templates"
appDir = "$basedir/grails-app"
rootDir="$basedir"
templateEngine = new SimpleTemplateEngine()

packageToDir = { String packageName ->
    packageName=packageName
	String dir = ''
	if (packageName) {
		dir = packageName.replaceAll('\\.', '/') + '/'
	}

	return dir
}

okToWrite = { String dest ->

	def file = new File(dest)
	if (overwriteAll || !file.exists()) {
		return true
	}

//	String propertyName = "file.overwrite.$file.name"
//	ant.input(addProperty: propertyName, message: "$dest exists, ok to overwrite?",
//	          validargs: 'y,n,a', defaultvalue: 'y')
//
//	if (ant.antProject.properties."$propertyName" == 'n') {
//		return false
//	}
//
//	if (ant.antProject.properties."$propertyName" == 'a') {
//		overwriteAll = true
//	}
    overwriteAll = true
	true
}

existSpringFrameWork = { String dest ->

	def file = new File(dest)
	if (!file.exists()) {
        ant.echo message: "Spring Framework doesn't exist Install it First"
		return false
	}
    true
}

generateFile = { String templatePath, String outputPath ->

    if (!existSpringFrameWork(outputPath)) {
        System.exit(1)
		//return
	}

	if (!okToWrite(outputPath)) {
		return
	}

	File templateFile = new File(templatePath)
	if (!templateFile.exists()) {
		ant.echo message: "\nERROR: $templatePath doesn't exist"
		return
	}

	File outFile = new File(outputPath)

	// in case it's in a package, create dirs
	ant.mkdir dir: outFile.parentFile

	outFile.withWriter { writer ->
		templateEngine.createTemplate(templateFile.text).make(templateAttributes).writeTo(writer)
	}

	ant.echo message: "generated $outFile.absolutePath"
}

splitClassName = { String fullName ->

	int index = fullName.lastIndexOf('.')
	String packageName = ''
	String className = ''
	if (index > -1) {
		packageName = fullName[0..index-1]
		className = fullName[index+1..-1]
	}
	else { 
		packageName = ''
		className = fullName
	}

	[packageName, className]
}

checkValue = { String value, String attributeName ->
	if (!value) {
		ant.echo message: "\nERROR: Cannot generate; grails.plugins.springsecurity.$attributeName isn't set"
		System.exit 1
	}
}

copyFile = { String from, String to ->
	if (!okToWrite(to)) {
		return
	}

	ant.copy file: from, tofile: to, overwrite: true
}