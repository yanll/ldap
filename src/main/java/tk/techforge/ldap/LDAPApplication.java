package tk.techforge.ldap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.techforge.ldap.configuration.FrameworkUtilConfiguration;


/**
 * Created by breez on 2016/03/30.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan(
        basePackageClasses = {FrameworkUtilConfiguration.class},
        basePackages = {"tk.techforge.ldap.web"})
@Slf4j
public class LDAPApplication {


    public static void main(String[] args) {
        SpringApplication.run(LDAPApplication.class, args);
    }
}