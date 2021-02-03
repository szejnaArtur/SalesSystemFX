package sample.dto;

import lombok.Data;

import java.util.Map;

@Data
public class OrderDTO {

    private Long idOrder;
    private Map<String, Integer> orderPosition;

}
