/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitec.controller;


import java.controller.HttpSession;
import java.controller.User;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fitec.dao.IDao;
import fitec.metier.Editeur;
import fitec.service.HbnFactory;
import fitec.service.HbnFactory.DaoMetier;

//import fitec.dba.dao.IDao;
//import fitec.dba.factory.HbnFactory.DaoMetier;
//import fitec.dba.metier.Auteur;
//import fitec.service.HbnFactory;


/**
 *
 * @author Fitec
 */
@Controller
//@RequestMapping("/livre")
public class EditeurController {

    private HbnFactory factory;
	
    @RequestMapping("/auteurs")
    public String listeLivre(Model model) {
    	IDao<Editeur> daoAuteur = (IDao<Editeur>) factory.getDAO(DaoMetier.Editeur);
        l = service.liste(new Livre());
        model.addAttribute("listAuteur", l);
        return "/auteur/listeAuteur";
    }
    
    
    @RequestMapping("/listeUser")
    public String users(HttpSession session,Model model) {
    User user = (User) session.getAttribute("userLogin");
        if (user != null && user.getId()!=0) {
            List<User> l = getAllUser();
            model.addAttribute("luser", l);
            return "/user/listeUser";
        }else{
            return "redirect:/user/login.htm";
        }

    }

//    @ModelAttribute(value = "listE")
//    public List<Metier> listeEditeur() {
//    	IDao<Auteur> daoAuteur = (IDao<Auteur>) hbn.getDAO(DaoMetier.Auteur);
//        Idao dao = factory.getDao(new Editeur());
//        List<Metier> le = dao.selectAll();
//        return le;
//    }
//
//    @ModelAttribute(value = "listA")
//    public List<Metier> listeAuteur() {
//        Idao dao = factory.getDao(new Auteur());
//        List<Metier> la = dao.selectAll();
//        return la;
//    }

//    @InitBinder
//    public void validation(WebDataBinder binder) {
//        binder.setValidator(new LivreValidator());
//    }

   

}
