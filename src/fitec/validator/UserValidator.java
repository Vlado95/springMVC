/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitec.validator;

import fitec.metier.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Fitec
 */
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.isAssignableFrom(User.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User u = (User) o;
//        if(u.getEmail().isEmpty())
//        {
//            errors.rejectValue("email", "","l'email ne doit pas être vide!");
//        }
        ValidationUtils.rejectIfEmpty(errors, "email", "", "l'email ne doit pas être vide");
        ValidationUtils.rejectIfEmpty(errors, "password", "", "le nom ne doit pas être vide");
    }

}
