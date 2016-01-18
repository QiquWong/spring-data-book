package com.oreilly.springdata.jdbc.repository;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.types.MappingProjection;
import com.mysema.query.types.QBean;
import com.oreilly.springdata.jdbc.TestConfig;
import com.oreilly.springdata.jdbc.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Thomas Risberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@DirtiesContext
public class QueryDslCustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    DataSource dataSource;

    @Test
    public void testFindByEmail() {
        Customer result = repository.findByEmailAddress(new EmailAddress("bob@doe.com"));
        assertThat(result, is(notNullValue()));
        assertThat(result.getFirstName(), is("Bob"));
    }

    @Test
    public void saveNewCustomer() {
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
    public void testFindAll() {
        List<Customer> results = repository.findAll();
        assertThat(results, is(notNullValue()));
        assertThat(results, hasSize(3));
        assertThat(results.get(0), notNullValue());
        assertThat(results.get(1), notNullValue());
        assertThat(results.get(2), notNullValue());
    }

}
