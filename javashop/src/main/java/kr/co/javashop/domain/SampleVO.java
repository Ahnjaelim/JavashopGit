package kr.co.javashop.domain;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SampleVO {
	private String resultCode, resultMessage, routeId, 	routeName, routeTypeCd, routeTypeName, startStationId, startStationName;
}
