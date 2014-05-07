grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenRepo "http://docker:8081/content/groups/public/"
        mavenLocal()
        mavenCentral()

        // uncomment these (or post new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        // runtime 'mysql:mysql-connector-java:5.1.22' ..
        compile (group:'de.gzockoll', name: 'money', version:'[0.1.3-SNAPSHOT,)') { changing:true }
        compile 'com.ibm.icu:icu4j:51.2'
        compile "org.jadira.usertype:usertype.jodatime:1.9"
        compile 'org.apache.httpcomponents:httpclient:4.2.5'
        compile 'com.google.code.gson:gson:2.2.4'
    }

    plugins {
        runtime ":hibernate:3.6.10.10" // or ":hibernate4:4.3.1.1"
        runtime ":jquery:1.8.3"
        runtime ":resources:1.1.6"

        // Uncomment these (or post new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.5"

        build ":tomcat:7.0.52.1"

        runtime ":database-migration:1.3.2"

        compile ':cache:1.0.1'
        compile ":cache-ehcache:1.0.0"
        compile ":joda-time:1.4"
        compile ":scaffolding:2.0.2"
    }
}
