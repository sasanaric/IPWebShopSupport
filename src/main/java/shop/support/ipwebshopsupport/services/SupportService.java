package shop.support.ipwebshopsupport.services;

import org.mindrot.jbcrypt.BCrypt;
import shop.support.ipwebshopsupport.DAO.SupportDAO;
import shop.support.ipwebshopsupport.beans.SupportBean;

public class SupportService {

    public boolean registerSupportUser(SupportBean supportBean){
        String plainPassword = supportBean.getPassword();
        supportBean.setPassword(hashPassword(plainPassword));
        return SupportDAO.registerSupportUser(supportBean);
    }

    public SupportBean getSupportUser(String username, String plainPassword){
        SupportBean supportBean = SupportDAO.getSupportUserByUsername(username);
        if(supportBean != null  &&  checkPassword(plainPassword,supportBean.getPassword())){
            return supportBean;
        }else{
            return null;
        }
    }

    public String getUserEmailById(Integer id){
        return SupportDAO.getEmailById(id);
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    private boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
