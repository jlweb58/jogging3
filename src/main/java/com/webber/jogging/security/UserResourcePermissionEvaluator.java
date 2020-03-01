/*
 *
 * com.webber.jogging.security.UserResourcePermissionEvaluator
 * 
 *
 */
package com.webber.jogging.security;

import com.webber.jogging.domain.User;
import com.webber.jogging.domain.UserResource;
import com.webber.jogging.repository.RunRepository;
import com.webber.jogging.repository.ShoesRepository;
import com.webber.jogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

/**
 * @author John Webber
 * @version $Revision$ $Date$
 * 
 *          This class checks whether a protected resource belongs to the authenticated user.
 */
public class UserResourcePermissionEvaluator implements PermissionEvaluator {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RunRepository runRepository;
  
  @Autowired
  private ShoesRepository shoesRepository;

  /**
   * If the target domain object is an instance of UserResource, checks whether the authenticated
   * user is the owner. If it's not a UserResource permission is granted. 
   * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication,
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    if (targetDomainObject == null) {
      return false;
    }
    Object principal = authentication.getPrincipal();
    String username;
    if (principal instanceof UserDetails) {
      username = ((UserDetails)principal).getUsername();
    } else {
      username = principal.toString();
    }
    User user = userRepository.findByUsername(username);
    if (user == null) {
      return false;
    }
    if (targetDomainObject instanceof UserResource) {
      UserResource userResource = (UserResource)targetDomainObject;
      if (!user.equals(userResource.getUser())) {
        return false;
      }
    }
    // Either it's not a UserResource, or the authenticated user has permission.
    return true;
  }

  /**
   * Attempts to load the indicated target domain object and then delegates to {@link #hasPermission(Authentication, Object, Object)}.
   * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication,
   * java.io.Serializable, java.lang.String, java.lang.Object)
   */
  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
    Object domainObject = null;
    if (targetType.equals("com.webber.jogging.model.Run")) {
      domainObject = runRepository.findById((Long)targetId);
    } else if (targetType.equals("com.webber.jogging.model.Shoes")) {
      domainObject = shoesRepository.findById((Long)targetId);
    }
    
    return hasPermission(authentication, domainObject, permission);
  }

}
