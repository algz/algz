package algz.platform.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
 
@Autowired
private SQLiteService service;
  
    @RequestMapping(value = "/s")  
    public String home() { 
    	System.out.println("controll");
        service.getTestDate();
    	return "index";  
    }  
  
//    @RequestMapping(value = "/getTime")  
//    public String helloWorld(Model model) {  
//        model.addAttribute("TimeIs", myService.getCurrentTimeInMilliseconds());  
//        return "getTime";  
//    } 
}
