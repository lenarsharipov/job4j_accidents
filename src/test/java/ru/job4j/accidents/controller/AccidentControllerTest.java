package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccidentService accidents;

    @Test
    @WithMockUser
    public void createAccidentShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void formUpdateAccidentShouldReturnDefaultMessage() throws Exception {
        var accident = new Accident(
                1, "name", new AccidentType(),
                Collections.emptySet(), "text", "address");
        when(accidents.findById(accident.getId())).thenReturn(Optional.of(accident));
        this.mockMvc
                .perform(get("/formUpdateAccident")
                        .param("id", String.valueOf(accident.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("updateAccident"));
    }

    @Test
    @WithMockUser
    public void saveAccidentShouldReturnDefaultMessage() throws Exception {
        var rIds = new String[] {"1", "3"};
        var type = new AccidentType(1, null);
        var accident = new Accident(
                0, "name", type, Collections.emptySet(),
                "text", "address");

        this.mockMvc.perform(post("/saveAccident")
                        .param("id", String.valueOf(accident.getId()))
                        .param("name", accident.getName())
                        .param("type.id", String.valueOf(accident.getType().getId()))
                        .param("rIds", rIds[0], rIds[1])
                        .param("text", accident.getText())
                        .param("address", accident.getAddress()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> rIdsCapture = ArgumentCaptor.forClass(String[].class);

        verify(accidents).save(accidentCaptor.capture(), rIdsCapture.capture());

        assertThat(accidentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(accident);

        assertThat(rIdsCapture.getValue())
                .usingRecursiveComparison()
                .isEqualTo(rIds);
    }

    @Disabled
    @Test
    @WithMockUser
    public void updateAccidentShouldReturnDefaultMessage() throws Exception {
        var rIds = new String[] {"1", "3"};
        var type = new AccidentType(1, null);
        var accident = new Accident(
                0, "name", type, Collections.emptySet(),
                "text", "address");

        this.mockMvc.perform(post("/updateAccident")
                        .param("id", String.valueOf(accident.getId()))
                        .param("name", accident.getName())
                        .param("type.id", String.valueOf(accident.getType().getId()))
                        .param("rIds", rIds[0], rIds[1])
                        .param("text", accident.getText())
                        .param("address", accident.getAddress()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> rIdsCapture = ArgumentCaptor.forClass(String[].class);

        verify(accidents).update(accidentCaptor.capture(), rIdsCapture.capture());

        assertThat(accidentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(accident);

        assertThat(rIdsCapture.getValue())
                .usingRecursiveComparison()
                .isEqualTo(rIds);

    }

}