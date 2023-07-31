package com.example.service;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFound;
import com.example.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttachService {

    @Value("${attach.folder.name}")
    private String folderName;
    @Value("${attach.url}")
    private String attachUrl;
    @Autowired
    private AttachRepository attachRepository;
    //private final String folderName = "attaches";

    public String saveToSystem(MultipartFile file) {
        //        System.out.println(file.getSize());
//        System.out.println(file.getName());
//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getContentType());
        try {
            File folder = new File("attaches");
            if (!folder.exists()) {
                folder.mkdir();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get("attaches/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public byte[] loadImage(String fileName) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("attaches/" + fileName));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);

            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public AttachDTO save(MultipartFile file) {
        String pathFolder = getYmDString();// 2022/04/23
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
        String extension = getExtension(file.getOriginalFilename()); // jpg
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);
            // attaches/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setId(key);
            entity.setPath(pathFolder); // 2022/04/23
            entity.setSize(file.getSize());
            entity.setOriginalName(file.getOriginalFilename());
            entity.setExtension(extension);
            attachRepository.save(entity);

            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(key);
            attachDTO.setOriginalName(entity.getOriginalName());
            attachDTO.setSize(entity.getSize());
            attachDTO.setPath(entity.getPath());
            attachDTO.setCreatedData(entity.getCreatedData());
            attachDTO.setUrl(getUrl(entity.getId()));
            // any think you want mazgi
            return attachDTO;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public String getUrl(String id){
         return attachUrl+"/open/"+id+"/img";
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public byte[] loadImageById(String id) {
        AttachEntity entity = get(id);
        try {
            String url = folderName + "/" + entity.getPath() + "/" + id + "." + entity.getExtension();
            BufferedImage originalImage = ImageIO.read(new File(url));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, entity.getExtension(), baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public AttachDTO getAttachWithURL(String attachId) {
        if (attachId == null) {
            return null;
        }
        return new AttachDTO(attachId, getUrl(attachId));
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new AppBadRequestException("File not found");
        });
    }

    public byte[] loadByIdGeneral(String id) {
        AttachEntity entity = get(id);
        try {
            String url = folderName + "/" + entity.getPath() + "/" + id + "." + entity.getExtension();
            File file = new File(url);

            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public PageImpl<AttachDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttachEntity> pageObj = attachRepository.findAll(pageable);
        List<AttachDTO> attachDTOS = pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(attachDTOS, pageable, pageObj.getTotalElements());
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginalName(entity.getOriginalName());
        attachDTO.setSize(entity.getSize());
        attachDTO.setPath(entity.getPath());
        attachDTO.setCreatedData(entity.getCreatedData());
        attachDTO.setExtension(entity.getExtension());
        return attachDTO;
    }

    public Boolean delete(String id) {
        System.out.println(id);
        Optional<AttachEntity> optional = attachRepository.findById(id);
        boolean b = false;
        if (optional.isEmpty()) {
            throw new ItemNotFound("Not found");
        }
        AttachEntity entity = optional.get();
        attachRepository.delete(entity);
        File file = new File("attaches/" + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension());
        if (file.exists()) {
            b = file.delete();
        }
        return b;
    }
    public ResponseEntity<Resource> download(String id) {
        AttachEntity entity = get(id);
        try {
            String url = folderName + "/" + entity.getPath() + "/" + id + "." + entity.getExtension();

            Path file = Paths.get(url);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
