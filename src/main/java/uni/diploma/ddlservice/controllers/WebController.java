package uni.diploma.ddlservice.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import uni.diploma.ddlservice.entities.SQLSchema;
import uni.diploma.ddlservice.entities.Session;
import uni.diploma.ddlservice.enums.SQLColTypes;
import uni.diploma.ddlservice.enums.SQLConTypes;

import javax.servlet.http.HttpSession;

import static uni.diploma.ddlservice.DdlServiceApplication.webSessions;


@RestController
public class WebController {

    @GetMapping("/new")
    public ModelAndView newSchema(Model model, HttpSession session) {
        Session localSession = webSessions.stream().filter(s -> s.getSessionID().equals(session.getId()))
                .findFirst().orElse(null);
        SQLSchema schema = localSession == null ? new SQLSchema() : localSession.getSessionSchema();
        model.addAttribute("coltypes", SQLColTypes.values());
        model.addAttribute("contypes", SQLConTypes.values());
        model.addAttribute("schema", schema);
        return new ModelAndView("new");
    }

    @GetMapping("/drop")
    public RedirectView drop(Model model, HttpSession session) {
        APIController.dropSession(session);
        newSchema(model, session);
        return new RedirectView("/new");
    }
}
