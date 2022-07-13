package com.test.jabis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
public class DataSourceTest {
   @Test
   public void H2ConnectionTest() {
      try(Connection con = DriverManager.getConnection("jdbc:h2:mem:jbs")){
      } catch (Exception e){
         fail(e.getMessage());
      }
   }

}
