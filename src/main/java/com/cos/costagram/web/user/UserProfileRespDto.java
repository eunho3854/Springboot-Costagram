package com.cos.costagram.web.user;

import com.cos.costagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRespDto {

	private boolean followState;
	private int followCount;
	private int imageCount;
	private User user;
}
