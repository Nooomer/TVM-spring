package com.nooomer.tvmspring.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableJpaRepositories(basePackages = ["com.nooomer.tvmspring", "com.nooomer.tvmspring.db.models"])
class DataBaseConfig : HikariConfig() {
    @Bean
    fun dataSource(): DataSource {
        return HikariDataSource(this)
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource()
        entityManagerFactoryBean.setPackagesToScan("com.nooomer.tvmspring", "com.nooomer.tvmspring.db")
        val vendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactoryBean.jpaVendorAdapter = vendorAdapter
        val props = Properties()
        props["hibernate.hbm2ddl.auto"] = "update"
        props["hibernate.open-in-view"] = "false"
        props["hibernate.globally_quoted_identifiers"] = "true"
        entityManagerFactoryBean.setJpaProperties(props)
        return entityManagerFactoryBean
    }

    @Bean
    fun transactionManager(): JpaTransactionManager? {
        return Objects.requireNonNull(entityManagerFactory().getObject())?.let { JpaTransactionManager(it) }
    }
}