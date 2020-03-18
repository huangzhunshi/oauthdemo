package so.dian.oauthdemo.oauthdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping("/order")
    @ResponseBody
    public String order(){
        return "order";
    }
}
