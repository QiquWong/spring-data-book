package com.oreilly.springdata.jdbc.repository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.types.MappingProjection;
import com.mysema.query.types.QBean;
import com.oreilly.springdata.jdbc.TestConfig;
import com.oreilly.springdata.jdbc.domain.Address;
import com.oreilly.springdata.jdbc.domain.Customer;
import com.oreilly.springdata.jdbc.domain.EmailAddress;
import com.oreilly.springdata.jdbc.domain.QAddress;
import com.oreilly.springdata.jdbc.domain.QCustomer;

/**
 * @author Thomas Risberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class QueryDslCustomerRepositoryTest {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    CustomerRepository repository;

    @Autowired
    DataSource dataSource;

    @Test
    public void saveNewCustomer() {
        String[] beanNames = ctx.getBeanDefinitionNames();
        System.out.println("Total Beans: " + beanNames.length); // 15
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getAutowireCapableBeanFactory();
        registry.removeBeanDefinition("testConfig");
        String[] beanNamesAgain = ctx.getBeanDefinitionNames();
        System.out.println("Total Beans: " + beanNamesAgain.length); // the rest of beans is 14
        Customer c = new Customer();
        c.setFirstName("Sven");
        c.setLastName("Svensson");
        c.setEmailAddress(new EmailAddress("sven@svensson.org"));
        Address a = new Address("Storgaten 6", "Trosa", "Sweden");
        c.addAddress(a);
        repository.save(c);
        System.out.println(repository.findAll());
        Customer result = repository.findById(c.getId());
        assertThat(result, is(notNullValue()));
        assertThat(result.getFirstName(), is("Sven"));
        assertThat(result.getEmailAddress().toString(), is(notNullValue()));
    }

    @Test
    public void saveNewCustomerWithoutEmail() {
        String[] beanNames = ctx.getBeanDefinitionNames();
        System.out.println("Total Beans: " + beanNames.length); // back to origin applicationContext, beans is 15
        Customer c = new Customer();
        c.setFirstName("Sven");
        c.setLastName("Svensson");
        Address a = new Address("Storgaten 6", "Trosa", "Sweden");
        c.addAddress(a);
        repository.save(c);
        System.out.println(repository.findAll());
        Customer result = repository.findById(c.getId());
        assertThat(result, is(notNullValue()));
        assertThat(result.getFirstName(), is("Sven"));
        assertThat(result.getEmailAddress(), is(nullValue()));
    }
}
