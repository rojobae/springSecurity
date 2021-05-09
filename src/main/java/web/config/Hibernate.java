package web.config;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class Hibernate {

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db.driver")));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManager() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        entityManagerFactoryBean.setJpaProperties(properties);
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager getTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(getEntityManager().getObject());
        return jpaTransactionManager;
    }

}
