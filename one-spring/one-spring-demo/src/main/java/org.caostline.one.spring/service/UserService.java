package org.caostline.one.spring.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jay.H.Zou
 * @date 2021/1/24
 */
public class UserService {

    @Transactional(rollbackFor = Exception.class)
    public void updateUser() {
        System.out.println("");
    }

}
