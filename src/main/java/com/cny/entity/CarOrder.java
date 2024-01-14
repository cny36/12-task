package com.cny.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : chennengyuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarOrder {

    private int id;
    //0:未派送，1:已派送
    private int status;
}
