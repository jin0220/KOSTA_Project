package com.planit.service.sns;

import java.util.List;

import com.planit.domain.sns.CommentDTO;
import com.planit.domain.sns.FilesDTO;
import com.planit.domain.sns.LikesDTO;
import com.planit.domain.sns.PostDTO;
import com.planit.domain.sns.PostDetailDTO;
import com.planit.domain.sns.PostFilesDTO;
import com.planit.domain.sns.UserToPlantsDTO;
import com.planit.domain.sns.WeatherLocationDTO;

public interface PostService {
	
	public PostDTO getBoardDetail(Long postNo);
	public void deleteYN(Long postNo);
	public void modify(PostDetailDTO params);
	public List<CommentDTO> getCommentDetail(Long postNo);
	public void insertComment(CommentDTO params);
	public void deleteComment(CommentDTO params);
	public FilesDTO insertFiles(FilesDTO params);
	public void deleteFile(long no);
	public void insertPost(PostDetailDTO params);
	public List<UserToPlantsDTO> selectPlantsCate(String userId);
	public String getLikes(LikesDTO params);
	public void insertLikes(LikesDTO params);
	public void deleteLikes(LikesDTO params);
	public void likeControl(LikesDTO params, String isLike);
	public List<FilesDTO> getFiles(Long postNo);
}
