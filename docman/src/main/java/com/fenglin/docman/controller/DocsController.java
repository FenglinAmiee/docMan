package com.fenglin.docman.controller;

import com.fenglin.docman.model.Document;
import com.fenglin.docman.service.DocsSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/docs")
public class DocsController {
    @Value("${defalut-save-file-directory}")
    private String path;

    @Autowired
    private DocsSerivce docsSerivce;

    @RequestMapping("/search")
    @ResponseBody
    public Map search(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                      @RequestParam(name = "limit", defaultValue = "10") Integer limit,
                      @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pageable pageable = PageRequest.of(offset, limit);

        Page<Document> page;
        if (user instanceof UserDetails) {
            page = docsSerivce.findAll(keyword, pageable);
        } else {
            page = docsSerivce.findAllPublic(keyword, pageable);
        }

        Map<String, Object> map = new HashMap<>();

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        return map;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map add(Document document) {
        return docsSerivce.save(document);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map modify(Document document) {
        return docsSerivce.save(document);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Map remove(@RequestParam Long id) {
        return docsSerivce.delete(id);
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam(name = "files") MultipartFile file) throws Exception {
        Map<String, String> map = new HashMap<>();
        if (!file.isEmpty()) {

            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String oName = file.getOriginalFilename();
            path += UUID.randomUUID().toString() + oName.substring(oName.lastIndexOf("."));

            File nFile = new File(path);
            if (!nFile.exists()) {
                nFile.createNewFile();
            }

            file.transferTo(nFile);
            map.put("path", path);
            map.put("realName", oName);
            map.put("status", "succeed");
        } else {
            map.put("status", "failed");
        }

        return map;
    }

    @RequestMapping("/download")
    @ResponseBody
    public void download(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        Document document = docsSerivce.findDocumentById(id);
        String path = document.getPath();
        String filename = document.getRealName();

        InputStream in = new FileInputStream(path);

        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(filename, "UTF-8") + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        while ((len = in.read(b)) > 0) {
            response.getOutputStream().write(b, 0, len);
        }

        in.close();
    }

    @RequestMapping("/preview")
    @ResponseBody
    public void preview(@RequestParam("path") String path, HttpServletResponse response) {
        File pdfFile = new File(path);
        if (pdfFile.exists()){

            response.setContentType("application/pdf");

            byte[] data = null;
            try {
                FileInputStream input = new FileInputStream(pdfFile);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }else {
            throw new RuntimeException(path +": 文件不存在");
        }
    }
}
