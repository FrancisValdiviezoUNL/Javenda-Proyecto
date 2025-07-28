package edu.unl.cc.javenda.controllers.security;

import edu.unl.cc.javenda.bussiness.SecurityFacade;
import edu.unl.cc.javenda.domain.security.User;
import edu.unl.cc.javenda.exception.EntityNotFoundException;
import edu.unl.cc.javenda.faces.FacesUtil;
import edu.unl.cc.javenda.util.EncryptorManager;
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
    @Inject
    private UserSession userSession;

    public UserHome() {
        user = new User();
    }

    public void loadUser() {
        try {
            User currentUser = userSession.getUser();
            if (currentUser == null || currentUser.getId() == null) {
                FacesUtil.addErrorMessage("No hay usuario autenticado correctamente.");
                return;
            }
            user = securityFacade.find(currentUser.getId());
            if (user.getPerson() == null) {
                user.setPerson(new edu.unl.cc.javenda.domain.common.Person());
            }
            decryptPassword(user);
        } catch (EntityNotFoundException e) {
            FacesUtil.addErrorMessage("No se pudo encontrar el usuario actual.");
        }
    }

    private void decryptPassword(User user){
        String pwdDecrypted = null;
        try {
            if (user.getPassword() != null && !user.getPassword().isEmpty()){
                logger.info("Password no nulo y no vacio: " + user.getPassword());
                pwdDecrypted = EncryptorManager.decrypt(user.getPassword());
                user.setPassword(pwdDecrypted);
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.addErrorMessage(e.getMessage(), "Invconveniente al decifrar la clave: " + e.getMessage());
        }

    }

    public String create() {
        try {
            user = securityFacade.create(user);
            //decryptPassword(user);
            FacesUtil.addSuccessMessageAndKeep("Usuario creado correctamente");
            return "login?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al crear usuario: " + e.getMessage());
            return null;
        }
    }

    public String update() {
        try {
            securityFacade.update(user);
            //decryptPassword(user);
            FacesUtil.addSuccessMessageAndKeep("Usuario actualizado correctamente");
            return "../dashboard?faces-redirect=true";
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Inconveniente al actualizar usuario: " + e.getMessage());
            return null;
        }
    }

    public boolean isManaged(){
        return this.user.getId() != null;
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

