package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class AmazonService {
    private AmazonS3 amazonS3;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        var provider = new AWSStaticCredentialsProvider(credentials);

        this.amazonS3 = AmazonS3ClientBuilder
                .standard().withRegion(Regions.SA_EAST_1)
                .withCredentials(provider).build();


    }

    public List<Bucket> listOfBuckets() {
        //Amazon
        // amazonS3.upload(presignedUrlUploadRequest)
        return amazonS3.listBuckets();

    }

    public File convertMultiPartToFile(MultipartFile file) {
        File convFile = null;
        try {

            String[] pathNames = {"files", file.getOriginalFilename()};
            String path = String.join(File.separator, pathNames);
            System.out.println("PATHH " + path);

            convFile = new File(path);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        if (convFile == null) return convFile;


        try {
            FileOutputStream fileOutputStream = new FileOutputStream(convFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }

        return convFile;

    }

    public File convertMultiPartToFile(MultipartFile file, File filePointer) {

        filePointer.getParentFile().mkdirs();
        try {
            filePointer.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePointer);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }

        return filePointer;

    }

    public PutObjectResult uploadSetFile(String bucketName, String fileKey,
                                         File localFile) {
        try {
            System.out.println("fileKey"+fileKey);
            return amazonS3.putObject(bucketName, fileKey, localFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public String uploadFile(String bucketName, MultipartFile multiPartFile) {
        PutObjectResult result;
        try {
            File file = convertMultiPartToFile(multiPartFile);
            result = amazonS3.putObject(bucketName, file.getName(), file);

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "err";
        }

    }


    private boolean writeFile(File convFile, S3ObjectInputStream stream) {
        try {
            convFile.getParentFile().mkdirs();


            System.out.println("ccccc" + convFile.getAbsolutePath());
            FileOutputStream outputStream = new FileOutputStream(convFile);
            byte[] readBuffer = new byte[1024];
            int readLength = 0;

            while ((readLength = stream.read(readBuffer)) > 0) {
                outputStream.write(readBuffer, 0, readLength);
            }

            stream.close();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean getFileFrom(String bucketName, String key, File filePointer) {
        try {

            S3Object s3Object = amazonS3.getObject(bucketName, key);
            S3ObjectInputStream stream = s3Object.getObjectContent();

            return writeFile(filePointer, stream);

        } catch (Exception er) {
            System.out.println(er.getMessage());
        }
        return false;
    }


    public List<S3ObjectSummary> listObjects(String bucketName) {
        //amazonS3.update
        //amazonS3.bucket
        ListObjectsV2Result listResults = amazonS3.listObjectsV2(bucketName);
        List<S3ObjectSummary> list = listResults.getObjectSummaries();
        return list;
    }
}
