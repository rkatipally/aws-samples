package com.fundrise.codeartifactproxy.controller;

import com.amazonaws.services.codeartifact.model.ListPackageVersionsResult;
import com.fundrise.codeartifactproxy.service.ProxyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ProxyController {

    private ProxyService proxyService;

    @GetMapping("/{packageName}")
    public ResponseEntity<Object> getNpmPackage(@PathVariable String packageName) throws IOException, InterruptedException {
        return proxyService.getPackage(packageName);
    }
}
