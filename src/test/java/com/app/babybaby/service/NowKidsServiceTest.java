package com.app.babybaby.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
public class NowKidsServiceTest {

    @Test
    public void getAllInfoForListTest(){
    }
}
