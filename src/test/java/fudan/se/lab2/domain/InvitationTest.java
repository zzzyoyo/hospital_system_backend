package fudan.se.lab2.domain;

import fudan.se.lab2.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class InvitationTest {
    @Autowired
    private AuthService authService;

    @Test
    @Transactional
    @Rollback

    void setState() {
    }

    @Test
    void getPCmemberUsername() {
    }

    @Test
    void getChairUsername() {
    }
}