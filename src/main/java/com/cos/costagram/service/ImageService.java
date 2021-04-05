package com.cos.costagram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costagram.config.auth.PrincipalDetails;
import com.cos.costagram.domain.image.Image;
import com.cos.costagram.domain.image.ImageRepository;
import com.cos.costagram.domain.tag.Tag;
import com.cos.costagram.domain.tag.TagRepository;
import com.cos.costagram.utils.TagUtils;
import com.cos.costagram.web.dto.image.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진(int principalId){
		return imageRepository.mExplore(principalId);
	}
	
	@Value("${file.path}") // @value + $() => application.yml 파일에 접근 가능
	private String uploadFolder;
	
	public Page<Image> 피드이미지(int principalId, Pageable pageable) {
		
		// 1. principalId로 내가 팔로우하고 있는 사용자를 찾아야 됨. (한개이거나 컬렉션이거나) 
		// select * from image where userId in (select toUserId from follow where fromUserId = 1); 
		
		// 좋아요 하트 색깔 로직 + 좋아요 카운트 로직
		Page<Image> images = imageRepository.mFeed(principalId, pageable);
		
		images.forEach((image) -> {
			
			int likeCount = image.getLikes().size();
			image.setLikeCount(likeCount);
			
			image.getLikes().forEach((like) -> {
				if(like.getUser().getId() == principalId) {
					image.setLikeState(true);
				}
			});
		});
		
		return images;
	}
	
	@Transactional
	public void 사진업로드(ImageReqDto imageReqDto, PrincipalDetails principalDetails) {
		
		UUID uuid = UUID.randomUUID(); // 사진 충돌 안 나게 하기 위해서 사용
		String imageFileName = uuid + "_" + imageReqDto.getFile().getOriginalFilename();
		System.out.println("파일명 : " + imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		System.out.println("파일패스 : " + imageFilePath);
		try {
			Files.write(imageFilePath, imageReqDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 참고 : Image 엔티티에 Tag는 주인이 아니다. Image 엔티티로 통해서 Tag를 Save 할 수 없다.
		
		// 1. Image 저장
		Image image = imageReqDto.toEntity(imageFileName, principalDetails.getUser()); // 내가 만든 객체
		Image imageEntity = imageRepository.save(image); // 영속화된 객체
		
		// 2. Tag 저장
		List<Tag> tags = TagUtils.parsingToTagObject(imageReqDto.getTags(), imageEntity);
		tagRepository.saveAll(tags);
	}
}
