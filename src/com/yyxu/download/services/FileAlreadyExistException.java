package com.yyxu.download.services;

public class FileAlreadyExistException extends DownloadException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FileAlreadyExistException(String message)
    {

        super(message);
    }

}
