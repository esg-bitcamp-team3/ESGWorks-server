package com.esgworks.controller;

import com.esgworks.service.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esgworks.domain.Corporation;


@RestController
@RequestMapping("/api/corporations")
public class CorporationController {
    @Autowired
    private CorporationService corporationService;

    @GetMapping("/{corpId}")
    public ResponseEntity<Corporation> getCorporation(@PathVariable String corpId) {
        Corporation corporation = corporationService.getCorporationById(corpId);
        return ResponseEntity.ok(corporation);
    }

}
