package kr.co.javashop.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
	
	private Long revId;
	
	@NotNull
	private Long prodId;
	
	@NotEmpty
	private String revText;
	
	@NotEmpty
	private String revName;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime regDate;
	
	@JsonIgnore
	private LocalDateTime modDate;
	
}
