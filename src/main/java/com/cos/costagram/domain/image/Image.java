package com.cos.costagram.domain.image;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.costagram.domain.comment.Comment;
import com.cos.costagram.domain.likes.Likes;
import com.cos.costagram.domain.tag.Tag;
import com.cos.costagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String caption;
	
	private String postImageUrl;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	// mappedBy -> DB에 Tag리스트가 생기지 않게 함.
	@OneToMany(mappedBy = "image")
	private List<Tag> tags;
	
	@OneToMany(mappedBy = "image")
	private List<Likes> likes; // A 이미지에 홍길동, 장보고, 임꺽정 좋아요.
	
	// comment (댓글)
	@OneToMany(mappedBy = "image")
	private List<Comment> comments;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	@Transient // 칼럼이 만들어지지 않는다.
	private int likeCount;
	
	@Transient
	private boolean likeState;
}
