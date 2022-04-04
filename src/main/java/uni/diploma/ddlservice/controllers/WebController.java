package uni.diploma.ddlservice.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import uni.diploma.ddlservice.enums.SQLColTypes;
import uni.diploma.ddlservice.enums.SQLConTypes;

@RestController
public class WebController {

    @GetMapping("/new")
    public ModelAndView newSchema(Model model) {
        model.addAttribute("coltypes", SQLColTypes.values());
        model.addAttribute("contypes", SQLConTypes.values());
        return new ModelAndView("new");
    }
}
