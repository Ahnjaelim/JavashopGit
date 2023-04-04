package kr.co.javashop.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.javashop.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long cateCode;
    private String cateName;
    private Long cateDepth;
    private Long cateParentCode;
    private List<Category> cateChildren = new ArrayList<>();
   	
}
