package com.concretepage.auth.sucurity.util;

import com.concretepage.auth.entity.User;
import com.concretepage.auth.entity.UserRole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateUserService {
    private String username;
    private String password;
    private List<String> grantedAuthority = new ArrayList<>();

    //get user from the database, via Hibernate

    public  CreateUserService(){};


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getGrantedAuthority() {
        return grantedAuthority;
    }

    public void setGrantedAuthority(List<String> grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    public CreateUserService loadUserByUsername(User user){

       if (user != null){
        List<String > authorities =
                buildUserAuthority(user.getUserRole());

        return build(user, authorities);}
        else  return null;
    }

    // Converts com.mkyong.users.model.User user to
    // org.springframework.security.core.userdetails.User
    private CreateUserService build(com.concretepage.auth.entity.User user,
                                            List<String> authorities) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.grantedAuthority = authorities;
        return this;
    }

    private List<String> buildUserAuthority(Set<UserRole> userRoles) {

        Set<String> setAuths = new HashSet<String>();

        // Build user's authorities
        for (UserRole userRole : userRoles) {
            setAuths.add(userRole.getRole());
        }

        List<String> Result = new ArrayList<String>(setAuths);

        return Result;
    }
}
