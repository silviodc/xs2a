package de.adorsys.aspsp.xs2a.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Currency;

@Data
@ApiModel(description = "Amount information", value = "Amount")
public class Amount {

	@ApiModelProperty(value = "currency", required = true, example = "EUR")
	private Currency currency;

	@ApiModelProperty(value = "content", required = true, example = "1000.00")
	private String content;
}
