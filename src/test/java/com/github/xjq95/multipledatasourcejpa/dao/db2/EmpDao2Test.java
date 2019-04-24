package com.github.xjq95.multipledatasourcejpa.dao.db2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EmpDao2Test {

    @Autowired
    EmpDao2 empDao2;

    @Test
    void testFindOne() {
        Assertions.assertThat(empDao2.findById("1").get().getName()).isEqualTo("张2");
    }
}