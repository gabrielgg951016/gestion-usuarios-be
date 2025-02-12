package com.xideral.gestion_usuarios_be;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class GestionUsuariosBeApplicationTests {

	@Test
void contextLoads() {
	assertDoesNotThrow(() -> GestionUsuariosBeApplication.main(new String[] {}));
}

}
