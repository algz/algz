package algz.platform.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;


/**
 * @EnableWebMvc导入spring_mvc需要的诸多bean，再配合@ComponentScan扫描包里面所有@Component(@Repository @Service  @Constroller)，基本的mvc配置就完成了。
 * @EnableTransactionManagement  //启动事务管理配置
* @ImportResource导入基于XML方式的配置文件，多个配置文件采{"",""}数组。
* 
* @author algz
*
*/ 

@Configuration//@ImportResource({"/WEB-INF/spring/appServlet/springmvc-context.xml","/WEB-INF/spring/appServlet/spring-context.xml"})
@ComponentScan(basePackages = {"algz.platform.test"})
@EnableWebMvc
@EnableTransactionManagement  //声明式事务管理，通过spring root application context扫描包septem.config.app：
//@PropertySource("/conf/jdbc.properties")
public class AppConfig {

 
	
//    @Bean
//    ViewResolver viewResolver(){
//          InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//          resolver.setPrefix( "WEB-INF/views/");
//          resolver.setSuffix( ".jsp");  
//           return resolver;
//    }
	
//    @Bean
//    @Autowired
//    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
// 
//        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactory.setDataSource(dataSource);
//        entityManagerFactory.setPackagesToScan(new String[] { "jpa.config.java" });
//        entityManagerFactory.setPersistenceProvider(new HibernatePersistence());
//        entityManagerFactory.afterPropertiesSet();
//        return entityManagerFactory.getObject();
//    }

//    @Bean
//    @Autowired
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory, DataSource dataSource) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
//        transactionManager.setDataSource(dataSource);
//        transactionManager.setJpaDialect(new HibernateJpaDialect());
//        return transactionManager;
//    }



/* other version of the same beans - no autowiring, CGLIB magic only

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan(new String[] { "jpa.config.java" });
        entityManagerFactory.setPersistenceProvider(new HibernatePersistence());
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
        transactionManager.setDataSource(dataSource());
        transactionManager.setJpaDialect(new HibernateJpaDialect());
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseFactoryBean bean = new EmbeddedDatabaseFactoryBean();
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("hibernate/config/java/schema.sql"));
        bean.setDatabasePopulator(databasePopulator);
        bean.afterPropertiesSet(); // necessary because
                                    // EmbeddedDatabaseFactoryBean is a
                                    // FactoryBean
        return bean.getObject();
    }

*/

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		HibernateTransactionManager bean = new HibernateTransactionManager();
		bean.setSessionFactory(sessionFactory().getObject());
		return bean;
	}


//    //*********************springmvc开始配置*************************
//    /**
//     * Resolve logical view names to .jsp resource in the /WEB-INF/views directory
//     * 解析所有视图为前缀(/WEB-INF/views)+视图名称+后缀(.jsp)的文件.
//     springmvc XML:
//         <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
//          <property name="prefix">WEB-INF/views/</property>
//          <property name="suffix">.jsp</property>
//        </bean>
//     */
    @Bean
    ViewResolver viewResolver(){
        InternalResourceViewResolver resolver =new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
//    
//    //****************spring开始配置***************************
//    
////    private @Value("#{jdbcProperties.url}") String jdbcUrl="jdbc\\:sqlite\\:c\\:/sample.db";
////    private @Value("#{jdbcProperties.username}") String username;
////    private @Value("#{jdbcProperties.password}") String password;
//


    @Bean
    public LocalSessionFactoryBean  sessionFactory() throws Exception{
      // more configuration...
      Properties properties=new Properties();
//      properties.put("hibernate.connection.driver_class", "org.sqlite.JDBC");
      properties.put("hibernate.dialect", "algz.platform.util.database.dialect.SQLiteDialect");
//      properties.put("hibernate.connection.url", "jdbc:sqlite:c:/sample.db");
//      properties.put("hibernate.connection.username", "");
//      properties.put("hibernate.connection.password", "");
      
        LocalSessionFactoryBean factoryBean=new LocalSessionFactoryBean();
//        factoryBean.setPackagesToScan(new String[]{"algz.platform.test"});//扫描model类
        factoryBean.setHibernateProperties(properties);
        factoryBean.setDataSource(dataSource());
        factoryBean.afterPropertiesSet();
        return factoryBean;

//        LocalSessionFactoryBuilder factoryBuilder =new LocalSessionFactoryBuilder(dataSource());
////        factoryBuilder.scanPackages("algz.platform.test");
//        factoryBuilder.setProperties(properties);
////        factoryBuilder.afterPropertiesSet();
//        return factoryBuilder.buildSessionFactory();
    }

    @Bean
    public DataSource dataSource() {
//        //JDBC连接
    	DriverManagerDataSource dm=new DriverManagerDataSource("jdbc:sqlite:c:/sample.db","","");
        dm.setDriverClassName("org.sqlite.JDBC");
    	return dm;
    }
}