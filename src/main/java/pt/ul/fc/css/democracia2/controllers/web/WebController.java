package pt.ul.fc.css.democracia2.controllers.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    Logger logger = LoggerFactory.getLogger(WebController.class);

    public WebController() {
        super();
    }
    @RequestMapping("/")
    public String getIndex(Model model) {
        return "index";
    }
}
