/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fitec.metier.User;
import fitec.service.HbnFactory;
import fitec.validator.UserValidator;

/**
 *
 * @author Fitec
 */
@Controller
//@SessionAttributes(value = {"userLogin","luser"})
/// @RequestMapping("/user")
public class ConnexionController {

    private HbnFactory factory;
//    private ServiceUser service;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
    	
        model.addAttribute("user", new User());
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(User user,Model model ,BindingResult result) {
    	
        UserValidator userValidator = new UserValidator();
        
        userValidator.validate(user, result);
        
        if(result.hasErrors())
        {
        	 return "/login";
        } 
    	
    	user = factory.authenticate(user.getEmail(), user.getPassword());

        if (user != null && user.getId() != 0) {
        	model.addAttribute("user", user);
        	
        	return "index";
        } else {
        	 return "/login";
        }  

    }

    
    @InitBinder
    public void validation(WebDataBinder binder)
    {
        binder.setValidator(new UserValidator());
    }

//	public HbnFactory getFactory() {
//		return factory;
//	}

//	@Autowired
//	public void setFactory(HbnFactory factory) {
//		this.factory = factory;
//	}

    
    
//    public ServiceUser getService() {
//        return service;
//    }
//
//    @Autowired
//    public void setService(ServiceUser service) {
//        this.service = service;
//    }
//   
//    public FactoryDao getFactory() {
//        return factory;
//    }
//
//    @Autowired
//    public void setFactory(FactoryDao factory) {
//        this.factory = factory;
//    }

}
