package fitec.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import fitec.metier.Editeur;


public class EditeurValidator implements  Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.isAssignableFrom(Editeur.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Editeur e = (Editeur) o;
        ValidationUtils.rejectIfEmpty(errors, "nom", "", "le nom ne doit pas être vide");
    }

}