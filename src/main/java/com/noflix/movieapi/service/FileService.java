package com.noflix.movieapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    //upload the file first along with entity details and get the url
    String uploadFile(String path, MultipartFile file) throws IOException;

    //get file from database and generate posterurl to serve in the frontend
    InputStream getResourceFile(String path,String name) throws FileNotFoundException;

}
