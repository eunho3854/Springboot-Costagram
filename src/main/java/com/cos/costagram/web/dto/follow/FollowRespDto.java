package com.cos.costagram.web.dto.follow;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowRespDto {

	private int userId;
	private String username;
	private String profileImageUrl;
	private BigInteger followState;
	private BigInteger equalState;
}
