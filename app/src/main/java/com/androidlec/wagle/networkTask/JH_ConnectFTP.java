package com.androidlec.wagle.networkTask;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;


import com.androidlec.wagle.UserInfo;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JH_ConnectFTP extends AsyncTask<Integer, String, String> {

    public FTPClient mFTPClient = null;

    Context context;
    String host;
    String username;
    String password;
    int port;
    Uri file;
    String uId;

    public JH_ConnectFTP(Context context, String host, String username, String password, int port, Uri file, String uId) {
        this.context = context;
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.file = file;
        this.uId = uId;
        mFTPClient = new FTPClient();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        String formatDate = "";

        // FTP 접속 체크
        boolean status = false;
        // FTP 접속 시
        if (status = ftpConnect(host, username, password, port)) {
            String currentPath = ftpGetDirectory() + "userImgs";

            // 파일 업로드시
            if (ftpUploadFile(file, uId + ".jpg", currentPath)) {
            }
        }

        if (status) {
            ftpDisconnect();
        }

        return uId + ".jpg";
    }

    public boolean ftpConnect(String host, String username, String password, int port) {
        boolean result = false;

        try {
            mFTPClient.connect(host, port);
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                result = mFTPClient.login(username, password);
                mFTPClient.enterLocalPassiveMode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean ftpDisconnect() {
        boolean result = false;

        try {
            mFTPClient.logout();
            mFTPClient.disconnect();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String ftpGetDirectory() {
        String directory = null;
        try {
            directory = mFTPClient.printWorkingDirectory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory;
    }

    public boolean ftpChangeDirectory(String directory) {
        try {
            mFTPClient.changeWorkingDirectory(directory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //                                     저장할 파일 이름        저장할 FTP 폴더 경
    public boolean ftpUploadFile(Uri file, String desFileName, String desDriectroy) {
        boolean result = false;
        try {
            InputStream fis = context.getContentResolver().openInputStream(file);
            if (ftpChangeDirectory(desDriectroy)) {
                // FTP File 타입 설정
                mFTPClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                result = mFTPClient.storeFile(desFileName, fis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

