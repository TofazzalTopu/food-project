
dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
//    driverClassName = "org.postgresql.Driver"
//    dialect = "com.docu.postgres.BitsPostgreSQLDialect"

    properties {
        initialSize = 5
        maxActive = 10
        minIdle = 5
        maxIdle = 5
        maxWait = 100000
        validationQuery = "SELECT 1"
        minEvictableIdleTimeMillis = 60000
        timeBetweenEvictionRunsMillis = 60000
    }

}
hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            loggingSql = true

              url = "jdbc:mysql://10.42.65.12/bdfe0404DEV"
//              url = "jdbc:mysql://10.42.65.12/bdfe010119DEV"
              username = "bdfeapp"
              password = "bdfeapp123BD"
        }

    }
    test {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            loggingSql = true
            url = "jdbc:mysql://10.42.53.12/bdfe_20181202"
            username = "bdfeapp"
            password = "bdfeapp12345"
        }
    }
    production {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            loggingSql = false
            url = ""
            username = ""
            password = ""

            properties {
                initialSize = 5
                maxActive = 100
                minIdle = 5
                maxIdle = 5
                maxWait = 100000
                validationQuery = "SELECT 1"
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
            }
        }
    }
}