package tk.techforge.ldap.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by YANLL on 2016/03/30.
 */

@RestController
@RequestMapping
@Slf4j
public class HomeController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpServletRequest request) {
        System.out.println(request.getHeader("host"));
        return "OK";
    }
}