package resource_server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void petsGet_noAuth_returnsUnauthorized() throws Exception {
        this.mockMvc.perform(get("/pets"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void petsGet_withValidJwtToken_returnsOk() throws Exception {
        this.mockMvc.perform(get("/pets")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_pet-store.user"))))
                .andExpect(status().isOk());
    }

    @Test
    void petsPost_withMissingAuthorities_returnsForbidden() throws Exception {

        var newPetJson = """
                {
                    "id": 123,
                    "name": "jack",
                    "type": "cat"
                }
                """;

        this.mockMvc.perform(post("/pets")
                        .content(newPetJson)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_pet-store.user"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void petsPost_withRequiredAuthorities_returnsCreated() throws Exception {

        var newPetJson = """
                {
                    "id": 123,
                    "name": "jack",
                    "type": "cat"
                }
                """;

        this.mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPetJson)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_pet-store.admin"))))
                .andExpect(status().isCreated());
    }
}
