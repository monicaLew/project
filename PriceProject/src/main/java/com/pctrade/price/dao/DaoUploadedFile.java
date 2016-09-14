package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.UploadedFile;

public interface DaoUploadedFile {

	List<UploadedFile> showAllUploadedFileInfoList() throws IllegalAccessException;

	UploadedFile showUploadedFileInfoById(Integer uploadedFiletId) throws IllegalAccessException;

	void createUploadedFileInfo(UploadedFile file) throws IllegalAccessException;  //+

	void deleteUploadedFileInfo(Integer uploadedFiletId) throws IllegalAccessException;

	void clearTable() throws IllegalAccessException;

}
