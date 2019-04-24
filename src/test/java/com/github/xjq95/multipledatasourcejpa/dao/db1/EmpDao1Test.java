package com.github.xjq95.multipledatasourcejpa.dao.db1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EmpDao1Test {

    @Autowired
    EmpDao1 empDao1;

    @Test
    void testFindOne() throws InterruptedException {
        Assertions.assertThat(empDao1.findById("1").get().getName()).isEqualTo("张1");
    }

    @Test
    void testCount() {
        Assertions.assertThat(empDao1.count()).isEqualTo(2);
    }
}