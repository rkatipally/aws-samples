package com.fundrise.codeartifactproxy.controller;

import com.amazonaws.util.IOUtils;
import com.fundrise.codeartifactproxy.service.ProxyService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ProxyController {

    private ProxyService proxyService;

    @GetMapping(value = "/{packageName}/**" , produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE} )
    public ResponseEntity getNpmPackage(@PathVariable String packageName, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, InterruptedException {
        String path =
                httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        if(StringUtils.isNotEmpty(path)) {
            path = path.substring(1);
            if (path.contains("tgz") || path.contains("zip")) {
                var responseEntity  =  proxyService.getPackageContent(path, response);
                return responseEntity;
            }
            return proxyService.getPackageMetadata(path);
        }
        return ResponseEntity.badRequest().build();
    }
}
