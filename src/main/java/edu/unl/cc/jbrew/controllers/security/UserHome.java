package edu.unl.cc.jbrew.controllers.security;

import edu.unl.cc.jbrew.bussiness.SecurityFacade;
import edu.unl.cc.jbrew.domain.common.Person;
import edu.unl.cc.jbrew.domain.security.User;
import edu.unl.cc.jbrew.exception.EntityNotFoundException;
import edu.unl.cc.jbrew.faces.FacesUtil;
import edu.unl.cc.jbrew.util.EncryptorManager;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.util.logging.Logger;

@Named
@ViewScoped
public class UserHome implements java.io.Serializable{

    private static Logger logger = Logger.getLogger(UserHome.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private Long selectedUserId;

    private User user;

    @Inject
    SecurityFacade securityFacade;


    public UserHome() {

    }
    @PostConstruct
    public void init() {
        if (user == null) {
            user = new User();
            user.setPerson(new Person());
        }
    }

//    public void loadUser() {
//        if (selectedUserId != null) {
//            try {
//                user = securityFacade.find(selectedUserId);
//                if (user.getPerson() == null) {
//                    user.setPerson(new edu.unl.cc.jbrew.domain.common.Person());
//                }
//            } catch (EntityNotFoundException e) {
//                FacesUtil.addErrorMessage("No se pudo encontrar el usuario con id: " + selectedUserId);
//            }
//        } else {
//            user = new User();
//        }
//        decryptPassword(user);
//    }

    private void decryptPassword(User user){
        String pwdDecrypted = null;
        try {
            if (user.getPassword() != null && !user.getPassword().isEmpty()){
                logger.info("Password no nulo y no vacio: " + user.getPassword());
                pwdDecrypted = EncryptorManager.decrypt(user.getPassword());
                user.setPassword(pwdDecrypted);
            }

        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage(), "Invconveniente al decifrar la clave: " + e.getMessage());
        }

    }

    public String create() {
        try {
            if (user == null) {
                user = new User();
            }
            user = securityFacade.create(user);
            FacesUtil.addMessageAndKeep(FacesMessage.SEVERITY_INFO, "Usuario creado exitosamente", "Inicia Sesión con tu usuario.");
            return "login?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace(); // para ver el error real
            FacesUtil.addErrorMessage("Inconveniente al crear usuario: " + e.getMessage());
            return null;
        }
    }

//    public String update() {
////        try {
////            user = securityFacade.update(user);
////            //decryptPassword(user);
////            FacesUtil.addSuccessMessageAndKeep("Usuario creado correctamente");
////            return "login?faces-redirect=true";
////        } catch (Exception e) {
////            FacesUtil.addErrorMessage("Inconveniente al actualizar usuario: " + e.getMessage());
////            return null;
////        }
//    }

    public boolean isManaged() {
        return this.user != null && this.user.getId() != null;
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

