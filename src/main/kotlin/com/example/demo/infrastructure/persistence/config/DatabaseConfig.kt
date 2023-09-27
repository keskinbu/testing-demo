package com.example.demo.infrastructure.persistence.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["com.example.demo.infrastructure.persistence.repositories"])
class DatabaseConfig(@Autowired private val environment: Environment) {

    @Bean(name = ["dataSource"])
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        environment.getProperty("spring.datasource.driver-class-name")?.let { dataSource.setDriverClassName(it) }
        dataSource.url = environment.getProperty("spring.datasource.url")
        dataSource.username = environment.getProperty("spring.datasource.username")
        dataSource.password = environment.getProperty("spring.datasource.password")
        return dataSource
    }

    @Bean(name = ["entityManagerFactory"])
    fun entityManagerFactory(@Qualifier("dataSource") dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan("com.example.demo.infrastructure.persistence.entities")

        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        em.setJpaProperties(additionalProperties())

        return em
    }

    @Bean(name = ["transactionManager"])
    fun transactionManager(@Qualifier("entityManagerFactory") emf: EntityManagerFactory): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = emf
        return transactionManager
    }

    private fun additionalProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"))
        properties.setProperty("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"))
        return properties
    }
}
