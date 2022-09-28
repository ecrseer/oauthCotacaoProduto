package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.dtos;

import java.io.File;

public class LocalFileAndAWSKey {
    File localFile;
    String fileKeyOnAWS;
    
    public LocalFileAndAWSKey(File localFile, String fileKeyOnAWS) {
        this.localFile = localFile;
        this.fileKeyOnAWS = fileKeyOnAWS;
    }
    
    public File getLocalFile() {
        return localFile;
    }
    public void setLocalFile(File localFile) {
        this.localFile = localFile;
    }
    
    public String getFileKeyOnAWS() {
        return fileKeyOnAWS;
    }
    public void setFileKeyOnAWS(String fileKeyOnAWS) {
        this.fileKeyOnAWS = fileKeyOnAWS;
    }

}
