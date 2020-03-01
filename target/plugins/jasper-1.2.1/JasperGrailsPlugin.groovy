class JasperGrailsPlugin {
  def version = "1.2.1"
  def grailsVersion = "1.3.0 > *"

  def developers = [
            [ name: "Sebastian Hohns", email: "sebastian.hohns@googlemail.com" ],
            [ name: "Marcos Pereira", email: "mfpereira@gmail.com" ] ]

  def license = "Apache 2.0"
  def issueManagement = [ system: "JIRA", url: "http://jira.codehaus.org/browse/GRAILSPLUGINS/component/13393" ]
  def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/grails-jasper" ]

  def author = "Marcos Pereira"
  def authorEmail = "mfpereira@gmail.com"
  //def contributer = "Sebastian Hohns"
  //def contributerEmail = "sebastian.hohns@googlemail.com"
  //def contributer = "Aaron Eischeid"
  //def contributerEmail = "a.eischeid@gmail.com"

  List pluginExcludes = [
          'grails-app/views/**',
          'grails-app/controllers/org/codehaus/groovy/grails/plugins/jasper/JasperDemoController.groovy',
          'src/groovy/org/codehaus/groovy/grails/plugins/jasper/demo/**',
          'docs/**',
          'src/docs/**'
  ]

  def title = "Easily launch Jasper reports from a Grails application."
  def description = '''
	This plugin adds easy support for launching jasper reports from GSP pages.
	After installing this plugin, run your application and request (app-url)/jasper/demo for a demonstration and instructions.
    '''
  def documentation = "http://www.grails.org/plugin/jasper"
  def dependsOn = [:]

  def doWithSpring = {
    // TODO Implement runtime spring config (optional)
  }

  def doWithApplicationContext = { applicationContext ->
    // TODO Implement post initialization spring config (optional)
  }

  def doWithWebDescriptor = { xml ->
    // TODO Implement additions to web.xml (optional)
  }

  def doWithDynamicMethods = { ctx ->
    // TODO Implement registering dynamic methods to classes (optional)
  }

  def onChange = { event ->
    // TODO Implement code that is executed when this class plugin class is changed
    // the event contains: event.application and event.applicationContext objects
  }

  def onApplicationChange = { event ->
    // TODO Implement code that is executed when any class in a GrailsApplication changes
    // the event contain: event.source, event.application and event.applicationContext objects
  }
}
 