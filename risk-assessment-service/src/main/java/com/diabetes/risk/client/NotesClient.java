package com.diabetes.risk.client;

import com.diabetes.risk.client.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
	    name = "notes-service",
	    url = "http://notes-service:8083"
	)	
public interface NotesClient {

    @GetMapping("/notes/patient/{patientId}")
    List<NoteDTO> getNotesByPatientId(@PathVariable("patientId") Long patientId);
}