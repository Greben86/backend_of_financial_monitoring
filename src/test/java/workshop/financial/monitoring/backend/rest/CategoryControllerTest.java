package workshop.financial.monitoring.backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import workshop.financial.monitoring.backend.domain.dto.JwtAuthenticationResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Тест добавления категории")
    @Test
    void testAddCategory() throws Exception {
        byte[] answerSignUp = mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign/up")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"username\":\"Viktor\", \"password\":\"password123\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();
        var token = getTokenFromAnswer(answerSignUp);

        mockMvc.perform(MockMvcRequestBuilders.post("/category/add")
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"name\":\"Доходы\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Доходы"));
    }

    private String getTokenFromAnswer(final byte[] answer) throws IOException {
        final var str = new String(answer, StandardCharsets.UTF_8);
        final var objectMapper = new ObjectMapper();
        try (final var response = objectMapper.createParser(str)) {
            final var jwt = response.readValueAs(JwtAuthenticationResponse.class);
            return jwt.token();
        }
    }
}