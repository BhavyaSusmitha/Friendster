package com.app.Configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.app.Models.BlogComment;
import com.app.Models.BlogPost;
import com.app.Models.BlogPostLikes;
import com.app.Models.Friend;
import com.app.Models.Job;
import com.app.Models.ProfilePicture;
import com.app.Models.User;

@Configuration
@EnableTransactionManagement
public class DBConfiguration {
  public DBConfiguration(){
	 System.out.println("DBConfiguration class is instantiated"); 
  }
  @Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder lsf=
				new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties=new Properties();
		hibernateProperties.setProperty(
				"hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
		
		lsf.addProperties(hibernateProperties);
		Class classes[]=new Class[]{User.class,Job.class,BlogPost.class,BlogPostLikes.class,BlogComment.class,ProfilePicture.class,Friend.class};//class objects of all entities
	    return lsf.addAnnotatedClasses(classes).buildSessionFactory();
	}
	@Bean
	public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
	    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
	    dataSource.setUsername("project2");
	    dataSource.setPassword("project2");
	    return dataSource;	    
	}
	@Bean
	public HibernateTransactionManager hibTransManagement(){
		return new HibernateTransactionManager(sessionFactory());
	}
}




