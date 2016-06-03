package co.bandsportal.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.bandsportal.model.manager.MemberManager;
import co.bandsportal.model.bo.Member;

@Controller
public class LoginController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private MemberManager memberManager;
    
    /**
     * Metodo que inicia la aplicacion y carga el login
     */
    @RequestMapping(value="/login.htm")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String now = (new Date()).toString();
        logger.info("Returning hello view with " + now);

        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("now", now);
        myModel.put("products", new Member());

        return new ModelAndView("login", "model", myModel);
    }
    
    /**
     * Metodo que redirecciona a la pantalla de registro
     */
    @RequestMapping(value="/register.htm")
    public ModelAndView register(HttpServletRequest request, HttpServletResponse response)
            						throws ServletException, IOException {

        return new ModelAndView("register", "model",  new HashMap<String, Object>());
    }

    /**
     * Metodo que registra una banda
     */
    @RequestMapping(value="/registerBand.htm")
    public ModelAndView registerBand(HttpServletRequest request, HttpServletResponse response)
            						throws ServletException, IOException {

        String now = (new Date()).toString();
        logger.info("Returning hello view with " + now);

        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("now", now);

        return new ModelAndView("paso_1_logo", "model", myModel);
    }

    
    public void setMemberManager(MemberManager memberManager) {
        this.memberManager = memberManager;
    }
}