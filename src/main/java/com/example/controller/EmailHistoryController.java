package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.enums.ProfileRole;
import com.example.service.EmailHistoryService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emailHistory")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getByEmail/{email}")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getByDate/{date}")
    public ResponseEntity<List<EmailHistoryDTO>> getByGivenDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(emailHistoryService.getByGivenDate(date));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin/getAll")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> getAll(@RequestParam("page") int page,
                                                            @RequestParam("size") int size) {
        return ResponseEntity.ok(emailHistoryService.pagination( page - 1, size));
    }


}
